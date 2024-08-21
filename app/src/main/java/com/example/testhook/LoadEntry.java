package com.example.testhook;

import android.content.res.XModuleResources;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.InMemoryDexClassLoader;
import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class LoadEntry implements IXposedHookZygoteInit, IXposedHookLoadPackage, IXposedHookInitPackageResources {

    static String TAG = "LoadEntry";
    public static String FEATURE = ".testhook";
    public static final String arm64_InjectTool = "arm64_InjectTool";
    public static final String arm32_InjectTool = "arm32_InjectTool";
    public static final String load_64so = "libtest64.so";
    public static final String load_32so = "libtest32.so";
    public static XModuleResources res;

    public static Map<String, Object> valueMap = new HashMap<>();
    public static Set<Object> unhooks = new HashSet<>(); //钩子集合，运行时把上一次的钩子失效


    public static String getCurrentProcessName() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method getProcessName = activityThread.getDeclaredMethod("currentProcessName");
            if (getProcessName != null) {
                return (String) getProcessName.invoke(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        try {
            Log.e(TAG, "handleLoadPackage: ====>");
            if (!loadPackageParam.packageName.equals(getCurrentProcessName())) {
                return;
            }

            //设置环境
            valueMap.put("de.robv.android.xposed.XposedBridge", XposedBridge.class.getName());
            valueMap.put("de.robv.android.xposed.XposedHelpers", XposedHelpers.class.getName());
            valueMap.put("de.robv.android.xposed.XC_MethodHook", XC_MethodHook.class.getName());
            valueMap.put("de.robv.android.xposed.XC_MethodHook$MethodHookParam", XC_MethodHook.MethodHookParam.class.getName());
            valueMap.put("de.robv.android.xposed.XC_MethodHook$Unhook", XC_MethodHook.Unhook.class.getName());
            valueMap.put("de.robv.android.xposed.XC_MethodReplacement", XC_MethodReplacement.class.getName());

            valueMap.put("pid", Process.myPid());
            valueMap.put("packageName", loadPackageParam.packageName);

            Log.e(TAG, "replaceHookedMethod: PathClassLoader :" + LoadEntry.class.getClassLoader());
            Log.e(TAG, "replaceHookedMethod: PathClassLoader :" + LoadEntry.class.getClassLoader().getClass());
            // Class<?> LspModuleClassLoader = LoadEntry.class.getClassLoader().loadClass("org.lsposed.lspd.util.LspModuleClassLoader");
//            Field apk = LspModuleClassLoader.getDeclaredField("apk");
//            String apk_path = (String) apk.get(LoadEntry.class.getClassLoader());


/*            Class<?> LspModuleClassLoader =  LoadEntry.class.getClassLoader().getClass();

            Field[] declaredFields = LspModuleClassLoader.getDeclaredFields();
            String apk_path = null; //获取lsposed中的apk path
            for (Field declaredField : declaredFields) {
                if (declaredField.getType().equals(String.class)) {
                    apk_path = (String) declaredField.get( LoadEntry.class.getClassLoader());
                }

            }
            if (apk_path == null) {
                return;
            }*/


            XposedHelpers.findAndHookConstructor(DexClassLoader.class, String.class, String.class, String.class, ClassLoader.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    Log.e(TAG, "replaceHookedMethod: PathClassLoader,path:" + methodHookParam.args[0]);
                    String librarySearchPath = (String) methodHookParam.args[1];
                    if (librarySearchPath.endsWith(FEATURE)) {
                        librarySearchPath = librarySearchPath.replace(FEATURE, "");
                        methodHookParam.args[1] = librarySearchPath;

                        Log.e(TAG, "replaceHookedMethod: PathClassLoader  args1");
                        Log.e(TAG, "replaceHookedMethod: PathClassLoader :" + LoadEntry.class.getClassLoader());

                        ClassLoader classLoader;
                        if (XposedBridge.class.getClassLoader() instanceof InMemoryDexClassLoader) {
                            classLoader = (LoadEntry.class.getClassLoader());
                            methodHookParam.args[2] = null;
                            methodHookParam.args[3] = classLoader;
                        }
                        //   methodHookParam.thisObject = XposedBridge.class.getClassLoader() ;
                        // return  new DexClassLoader((String) methodHookParam.args[0], (String) methodHookParam.args[1], null, (ClassLoader) methodHookParam.args[3]);
                        return XposedBridge.invokeOriginalMethod(methodHookParam.method, methodHookParam.thisObject, methodHookParam.args);
                        // XposedBridge.class.getClassLoader();

                        //  return null;
                    }

                    return XposedBridge.invokeOriginalMethod(methodHookParam.method, methodHookParam.thisObject, methodHookParam.args);
                }
            });


            XposedHelpers.findAndHookMethod(com.example.testhook.XposedHelpers.class, "findAndHookMethod", Class.class, String.class, Object[].class, new ProxyXC(ProxyXC.FINDANDHOOKMETHOD));
            XposedHelpers.findAndHookMethod(com.example.testhook.XposedHelpers.class, "findAndHookConstructor", Class.class, Object[].class, new ProxyXC(ProxyXC.FINDANDHOOKCONSTRUCTOR));
            XposedHelpers.findAndHookMethod(com.example.testhook.XposedBridge.class, "hookMethod", Member.class, com.example.testhook.XC_MethodHook.class, new ProxyXC(ProxyXC.HOOKMETHOD));
            XposedHelpers.findAndHookMethod(com.example.testhook.XposedBridge.class, "hookAllMethods", Class.class, String.class, com.example.testhook.XC_MethodHook.class, new ProxyXC(ProxyXC.HOOKALLMETHODS));
            XposedHelpers.findAndHookMethod(com.example.testhook.XposedBridge.class, "hookAllConstructors", Class.class, com.example.testhook.XC_MethodHook.class, new ProxyXC(ProxyXC.HOOKALLCONSTRUCTORS));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {

    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        try {
            res = XModuleResources.createInstance(startupParam.modulePath, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ProxyXC extends XC_MethodReplacement {

        public static final String FINDANDHOOKMETHOD = "findAndHookMethod";
        public static final String FINDANDHOOKCONSTRUCTOR = "findAndHookConstructor";
        public static final String HOOKMETHOD = "hookMethod";
        public static final String HOOKALLMETHODS = "hookAllMethods";
        public static final String HOOKALLCONSTRUCTORS = "hookAllConstructors";
        public String method_name;

        public ProxyXC(String name) {
            method_name = name;
        }

        @Override
        protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
            if (methodHookParam.args[methodHookParam.args.length - 1] instanceof com.example.testhook.XC_MethodHook) {
                com.example.testhook.XC_MethodHook callback = (com.example.testhook.XC_MethodHook) methodHookParam.args[methodHookParam.args.length - 1];

                XC_MethodHook xcMethodHook = new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        callback.beforeHookedMethod(new com.example.testhook.XC_MethodHook.MethodHookParam(param));
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        callback.afterHookedMethod(new com.example.testhook.XC_MethodHook.MethodHookParam(param));
                    }
                };
                Unhook unhook;
                switch (method_name) {
                    case HOOKMETHOD:
                        unhook = XposedBridge.hookMethod((Member) methodHookParam.args[0], xcMethodHook);
                        unhooks.add(unhook);
                        return unhook;
                    case HOOKALLMETHODS:
                        Set<Unhook> unhooks1 = XposedBridge.hookAllMethods((Class<?>) methodHookParam.args[0], (String) methodHookParam.args[1], xcMethodHook);
                        unhooks.addAll(unhooks1);
                        return unhooks1;
                    case HOOKALLCONSTRUCTORS:
                        Set<Unhook> unhooks2 = XposedBridge.hookAllConstructors((Class<?>) methodHookParam.args[0], xcMethodHook);
                        unhooks.addAll(unhooks2);
                        return unhooks2;
                    default:
                        return null;
                }


            } else if (methodHookParam.args[methodHookParam.args.length - 1] instanceof com.example.testhook.XC_MethodReplacement) {
                com.example.testhook.XC_MethodReplacement callback = (com.example.testhook.XC_MethodReplacement) methodHookParam.args[methodHookParam.args.length - 1];

                XC_MethodHook xcMethodHook = new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                        return callback.replaceHookedMethod(new com.example.testhook.XC_MethodHook.MethodHookParam(methodHookParam));
                    }
                };

                switch (method_name) {
                    case HOOKMETHOD:
                        Unhook unhook = XposedBridge.hookMethod((Member) methodHookParam.args[0], xcMethodHook);
                        unhooks.add(unhook);
                        return unhook;
                    case HOOKALLMETHODS:
                        Set<Unhook> unhooks1 = XposedBridge.hookAllMethods((Class<?>) methodHookParam.args[0], (String) methodHookParam.args[1], xcMethodHook);
                        unhooks.addAll(unhooks1);
                        return unhooks1;
                    case HOOKALLCONSTRUCTORS:
                        Set<Unhook> unhooks2 = XposedBridge.hookAllConstructors((Class<?>) methodHookParam.args[0], xcMethodHook);
                        unhooks.addAll(unhooks2);
                        return unhooks2;
                    default:
                        return null;
                }


            } else if (methodHookParam.args[methodHookParam.args.length - 1] instanceof Object[]) {

                Object[] parameterTypesAndCallback = (Object[]) methodHookParam.args[methodHookParam.args.length - 1];
                if (parameterTypesAndCallback.length != 0 && parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof com.example.testhook.XC_MethodHook) {
                    com.example.testhook.XC_MethodHook xcMethodHooks = (com.example.testhook.XC_MethodHook) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
                    XC_MethodHook xcMethodHook = new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            xcMethodHooks.beforeHookedMethod(new com.example.testhook.XC_MethodHook.MethodHookParam(param));
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            xcMethodHooks.afterHookedMethod(new com.example.testhook.XC_MethodHook.MethodHookParam(param));
                        }
                    };
                    parameterTypesAndCallback[parameterTypesAndCallback.length - 1] = xcMethodHook;


                    switch (method_name) {
                        case FINDANDHOOKMETHOD:
                            Unhook andHookMethod = XposedHelpers.findAndHookMethod((Class<?>) methodHookParam.args[0], (String) methodHookParam.args[1], parameterTypesAndCallback);
                            unhooks.add(andHookMethod);
                            return andHookMethod;
                        case FINDANDHOOKCONSTRUCTOR:
                            Unhook andHookConstructor = XposedHelpers.findAndHookConstructor((Class<?>) methodHookParam.args[0], parameterTypesAndCallback);
                            unhooks.add(andHookConstructor);
                            return andHookConstructor;
                        default:
                            return null;
                    }

                } else if (parameterTypesAndCallback.length != 0 && parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof com.example.testhook.XC_MethodReplacement) {

                    com.example.testhook.XC_MethodReplacement xcMethodHooks = (com.example.testhook.XC_MethodReplacement) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
                    XC_MethodHook xcMethodHook = new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                            return xcMethodHooks.replaceHookedMethod(new com.example.testhook.XC_MethodHook.MethodHookParam(methodHookParam));
                        }
                    };
                    parameterTypesAndCallback[parameterTypesAndCallback.length - 1] = xcMethodHook;

                    switch (method_name) {
                        case FINDANDHOOKMETHOD:
                            Unhook andHookMethod = XposedHelpers.findAndHookMethod((Class<?>) methodHookParam.args[0], (String) methodHookParam.args[1], parameterTypesAndCallback);
                            unhooks.add(andHookMethod);
                            return andHookMethod;
                        case FINDANDHOOKCONSTRUCTOR:
                            Unhook andHookConstructor = XposedHelpers.findAndHookConstructor((Class<?>) methodHookParam.args[0], parameterTypesAndCallback);
                            unhooks.add(andHookConstructor);
                            return andHookConstructor;
                        default:
                            return null;
                    }

                } else {

                    return null;
                }
            } else {
                return null;
            }
        }
    }

}