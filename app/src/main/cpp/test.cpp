//
// Created by thehepta on 2024/2/19.
//

#include <android/log.h>
#include <stdio.h>
#include <dlfcn.h>
#include "android_util_api.h"
#include <iostream>
#include <string>
#include <random>
#include <algorithm>

#define LOG_TAG "Rxposed_Test"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)


#ifdef __aarch64__
#define APK_NATIVE_LIB "lib/arm64"

#else
#define APK_NATIVE_LIB "lib/arm"

#endif


// 获取 gJavaVM 指针的函数原型
typedef jint (*JNI_GetCreatedJavaVMsFunc)(JavaVM **, jsize, jsize *);
std::string getClassName(JNIEnv *env, jclass cls);
std::string jobjectToString(JNIEnv *env, jobject obj);

struct ThreadData {
    const char* type;
    const char* apkpath;
    const char* classname;
    const char* method;
    const char* argument;
};


JavaVM *g_vm = nullptr;

std::string generateRandomString(size_t length) {
    const std::string charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    std::random_device rd;
    std::mt19937 generator(rd());
    std::uniform_int_distribution<> distribution(0, charset.size() - 1);

    std::string random_string;
    for (size_t i = 0; i < length; ++i) {
        random_string += charset[distribution(generator)];
    }

    return random_string;
}


void *thread_entry(void *args) {
    JNIEnv *env = nullptr;
    if (g_vm->AttachCurrentThread(&env, nullptr) != JNI_OK) {
        LOGD("Failed to attach current thread");
        return nullptr;
    }

    // 确保没有未处理的异常
    if (env->ExceptionCheck()) {
        env->ExceptionClear();
    }

    // 执行 JNI 操作
    // 示例：获取应用程序上下文
    jclass activityThreadClass = env->FindClass("android/app/ActivityThread");
    jmethodID currentApplicationMethod;
    jobject application;
    jclass contextClass;
    jmethodID getClassLoaderMethod;
    jobject classLoader;
    jclass classLoaderClass;
    jmethodID loadClassMethod;
    jstring targetClassName;
    jobject targetClass;


    //
    ThreadData* data = static_cast<ThreadData*>(args);
    std::string source = data->apkpath;

    std::string base = "base.apk";
    std::string hide = "false";

    std::string Entry_class = data->classname;
    LOGE("Entry_class:%s", Entry_class.c_str());
    std::string  Entry_method = data->method;
    LOGE("Entry_method:%s", Entry_method.c_str());
    std::string  argument =  data->argument;
    LOGE("argument:%s", argument.c_str());
    size_t startPost = source.find(base);
   // std::string  NativelibPath = source.c_str().replace(startPost, base.length(), APK_NATIVE_LIB);

    std::string  NativelibPath =  "/data/local/tmp/inject/lib/arm64" + generateRandomString(5) + ".testhook";
    LOGE("source:%s", source.c_str());
    LOGE("NativelibPath:%s", NativelibPath.c_str());


    if (activityThreadClass == nullptr) {
        LOGD("Failed to find ActivityThread class");
        goto DETACH;
    }

    currentApplicationMethod = env->GetStaticMethodID(activityThreadClass, "currentApplication",
                                                      "()Landroid/app/Application;");
    if (currentApplicationMethod == nullptr) {
        LOGD("Failed to find currentApplication method");
        goto DETACH;
    }

    application = env->CallStaticObjectMethod(activityThreadClass, currentApplicationMethod);
    if (application == nullptr) {
        LOGD("Failed to get Application instance");
        goto DETACH;
    }

    // 从 Application 获取 ClassLoader
    contextClass = env->FindClass("android/content/Context");
    if (contextClass == nullptr) {
        LOGD("Failed to find Context class");
        goto DETACH;
    }

    getClassLoaderMethod = env->GetMethodID(contextClass, "getClassLoader",
                                            "()Ljava/lang/ClassLoader;");
    if (getClassLoaderMethod == nullptr) {
        LOGD("Failed to find getClassLoader method");
        goto DETACH;
    }

    classLoader = env->CallObjectMethod(application, getClassLoaderMethod);
    if (classLoader == nullptr) {
        LOGD("Failed to get ClassLoader");
        goto DETACH;
    }

    // 使用自定义的 ClassLoader 查找目标类
    classLoaderClass = env->FindClass("java/lang/ClassLoader");
    loadClassMethod = env->GetMethodID(classLoaderClass, "loadClass",
                                       "(Ljava/lang/String;)Ljava/lang/Class;");



//加载apk
    load_apk_And_Call_Class_Entry_Method(env,application,source,NativelibPath,Entry_class,Entry_method,hide,argument);

  //  targetClassName = env->NewStringUTF("com.example.testhook.ExampleInstrumentedTest"); // 替换为目标类的全限定名
  //  targetClass = env->CallObjectMethod(classLoader, loadClassMethod, targetClassName);
    if (env->ExceptionCheck()) {
        env->ExceptionDescribe();
        env->ExceptionClear();
        LOGD("Failed to load target class");
        goto DETACH;
    }

    // 成功获取目标类，可以继续进行后续操作
    LOGD("Successfully loaded target class");
    // ... 其他操作 ...

    DETACH:
    g_vm->DetachCurrentThread();
    LOGD("Detached current thread");
    return nullptr;
}

std::string getClassName(JNIEnv *env, jclass cls) {
    jclass classClass = env->GetObjectClass(cls);
    jmethodID getNameMethod = env->GetMethodID(classClass, "getName", "()Ljava/lang/String;");
    jstring className = (jstring) env->CallObjectMethod(cls, getNameMethod);

    const char *classNameCStr = env->GetStringUTFChars(className, nullptr);
    std::string classNameStr(classNameCStr);
    env->ReleaseStringUTFChars(className, classNameCStr);

    return classNameStr;
}

std::string jobjectToString(JNIEnv *env, jobject obj) {
    jclass objectClass = env->GetObjectClass(obj);
    jmethodID toStringMethod = env->GetMethodID(objectClass, "toString",
                                                "()Ljava/lang/String;");
    jstring strObj = (jstring) env->CallObjectMethod(obj, toStringMethod);

    const char *strCStr = env->GetStringUTFChars(strObj, nullptr);
    std::string str(strCStr);
    env->ReleaseStringUTFChars(strObj, strCStr);

    return str;
}



// 获取 JavaVM 的函数
bool get_java_vm() {
    void *handle = dlopen("libandroid_runtime.so", RTLD_NOW);
    if (!handle) {
        LOGD("Failed to load libandroid_runtime.so");
        return false;
    }

    typedef jint (*JNI_GetCreatedJavaVMs_t)(JavaVM **, jsize, jsize *);
    JNI_GetCreatedJavaVMs_t JNI_GetCreatedJavaVMs = (JNI_GetCreatedJavaVMs_t) dlsym(handle,
                                                                                    "JNI_GetCreatedJavaVMs");
    if (!JNI_GetCreatedJavaVMs) {
        LOGD("Failed to find JNI_GetCreatedJavaVMs");
        dlclose(handle);
        return false;
    }

    jsize vm_count = 0;
    if (JNI_GetCreatedJavaVMs(&g_vm, 1, &vm_count) != JNI_OK || vm_count == 0) {
        LOGD("Failed to get JavaVM");
        dlclose(handle);
        return false;
    }

    dlclose(handle);
    return true;
}



void Ptrace_Zygotes(const char *AUTHORITY) {
    printf("[-][function:%s] ====> invoke Ptrace_Zygotes,AUTHORITY_pkgName: %s\n", __func__,
           AUTHORITY);
    LOGE("Rxposed_Test inject successful");
}


void Inject_Porcess(const char *AUTHORITY_pkgName) {
    printf("[-][function:%s] ====> invoke Inject_Porcess,AUTHORITY_pkgName: %s\n", __func__,
           AUTHORITY_pkgName);
    LOGE("Rxposed_Test inject successful");

    try {
        std::vector<std::string> info = string_split(AUTHORITY_pkgName,
                                                     ":");//: 分隔  lsposed:lsposed位置:入口类名:其他
        if (!info[0].empty()) {
            if (std::equal(info[0].begin(), info[0].end(), "lsposed")) {

                //
                LOGD("Initializing injected code");

                if (!get_java_vm()) {
                    LOGD("Failed to get JavaVM");
                    return;
                }
                pthread_t thread;
                ThreadData data;
                data.type = "lsposed";
                data.apkpath = info[1].c_str();
                data.classname =  info[2].c_str();
                data.method =  info[3].c_str();
                data.argument =  info[4].c_str();
                if (pthread_create(&thread, nullptr, thread_entry, &data) != 0) {
                    LOGD("Failed to create new thread");
                    return;
                }
                // 等待线程完成
                // pthread_join(thread, nullptr);
            }

        }
    } catch (const std::exception &e) {
        LOGE("work on xposed error!");
    }


}
