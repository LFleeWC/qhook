//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.testhook;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public final class XposedBridge {

    public static Class<?> XposedBridgeClass;


    public XposedBridge() {
    }


    private static void determineXposedVersion() throws IOException {

        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("determineXposedVersion");
            findField.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static int extractIntPart(String str) {
        int result = 0;
        int length = str.length();

        for(int offset = 0; offset < length; ++offset) {
            char c = str.charAt(offset);
            if ('0' > c || c > '9') {
                break;
            }

            result = result * 10 + (c - 48);
        }

        return result;
    }

    private static void initXbridgeZygote() throws Throwable {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("initXbridgeZygote");
            findField.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static void hookResources() throws Throwable {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("hookResources");
            findField.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static void hookXposedInstaller(ClassLoader classLoader) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("hookXposedInstaller",ClassLoader.class);
            findField.invoke(null,classLoader);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static void loadModules(String startClassName) throws IOException {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("loadModules",String.class);
            findField.invoke(null,startClassName);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void loadModule(String apk, String startClassName) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("loadModule",String.class,String.class);
            findField.invoke(null,apk,startClassName);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static synchronized void log(String text) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("log",String.class);
            findField.invoke(null,text);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static synchronized void log(Throwable t) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("log",Throwable.class);
            findField.invoke(null,t);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static Object hookMethod(Member hookMethod, XC_MethodHook callback) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("hookMethod",Member.class,XC_MethodHook.XC_MethodHookClass);
            Object result =  findField.invoke(null,hookMethod,callback.xC_MethodHookObj);
            LoadEntry.unhooks.add(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void unhookMethod(Member hookMethod, XC_MethodHook callback) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("unhookMethod",Member.class,XC_MethodHook.XC_MethodHookClass);
            findField.invoke(null,hookMethod,callback.xC_MethodHookObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object hookAllMethods(Class<?> hookClass, String methodName, XC_MethodHook callback) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("hookAllMethods",Class.class,String.class,XC_MethodHook.XC_MethodHookClass);
            Object result = findField.invoke(null,hookClass,methodName,callback.xC_MethodHookObj);
            LoadEntry.unhooks.add(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object hookAllConstructors(Class<?> hookClass, XC_MethodHook callback) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("hookAllConstructors",Class.class,XC_MethodHook.XC_MethodHookClass);
            Object result =  findField.invoke(null,hookClass,callback.xC_MethodHookObj);
            LoadEntry.unhooks.add(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object handleHookedMethod(Member method, int originalMethodId, Object additionalInfoObj, Object thisObject, Object[] args) throws Throwable {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("handleHookedMethod",Member.class,int.class,Object.class,Object.class,Object[].class);
            XC_MethodHook.replace(args);
            Object result =  findField.invoke(null,method,originalMethodId,additionalInfoObj,thisObject,args);
            LoadEntry.unhooks.add(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Object invokeOriginalMethod(Member method, Object thisObject, Object[] args) throws NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("invokeOriginalMethod",Member.class,Object.class,Object[].class);
            return findField.invoke(null,method,thisObject,args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setObjectClass(Object obj, Class<?> clazz) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("setObjectClass",Object.class,Class.class);
            findField.invoke(null,obj,clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Object cloneToSubclass(Object obj, Class<?> targetClazz) {
        try {
            Method findField = XposedBridgeClass.getDeclaredMethod("cloneToSubclass",Object.class,Class.class);
            return findField.invoke(null,obj,targetClazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
