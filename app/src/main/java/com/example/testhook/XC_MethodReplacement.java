package com.example.testhook;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;

public class XC_MethodReplacement {
    public static Class<?> XC_MethodReplacementClass;
    public Object xC_MethodReplacement;

    public XC_MethodReplacement(Object o) {
        xC_MethodReplacement = o;
    }


    protected final void beforeHookedMethod(com.example.testhook.XC_MethodHook.MethodHookParam param) throws Throwable {
        try {
            Method findField = XC_MethodReplacementClass.getDeclaredMethod("beforeHookedMethod", com.example.testhook.XC_MethodHook.MethodHookParamClass);
            findField.invoke(xC_MethodReplacement,param.methodHookParamObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected final void afterHookedMethod(com.example.testhook.XC_MethodHook.MethodHookParam param) throws Throwable {

        try {
            Method findField = XC_MethodReplacementClass.getDeclaredMethod("afterHookedMethod", com.example.testhook.XC_MethodHook.MethodHookParamClass);
            findField.invoke(xC_MethodReplacement, param.methodHookParamObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Object replaceHookedMethod(com.example.testhook.XC_MethodHook.MethodHookParam var1) throws Throwable {

        try {
            Method findField = XC_MethodReplacementClass.getDeclaredMethod("replaceHookedMethod", com.example.testhook.XC_MethodHook.MethodHookParamClass);
            return findField.invoke(xC_MethodReplacement, var1.methodHookParamObj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
