package com.example.testhook;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class XC_MethodHook {
    public static Class<?> XC_MethodHookClass;
    public static Class<?> MethodHookParamClass;
    public static Class<?> UnhookParamClass;
    public Object xC_MethodHookObj;

    public static void replace(Object[] objects) {

/*        for (Object object : objects) {
            Log.e("dumpdex", "replace old: " + object.getClass());
        }

        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof XC_MethodHook) {
                objects[i] =XC_MethodHookClass.cast(((XC_MethodHook) objects[i]));
            }
            if (objects[i] instanceof MethodHookParam) {
                objects[i] = MethodHookParamClass.cast(((MethodHookParam) objects[i]).methodHookParamObj);
            }
            if (objects[i] instanceof Unhook) {
                objects[i] = UnhookParamClass.cast(((Unhook) objects[i]).unhookObj);
            }
        }
        for (Object object : objects) {
            Log.e("dumpdex", "replace: " + object.getClass());
        }*/
    }

    public XC_MethodHook() {


    }

    public XC_MethodHook(int priority) {

    }

    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
/*        try {
            Method findField = XC_MethodHookClass.getDeclaredMethod("beforeHookedMethod", MethodHookParamClass);
            findField.invoke(null, param.methodHookParamObj);
        } catch (Exception e) {
            e.printStackTrace();

        }*/
    }

    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
   /*     try {
            Method findField = XC_MethodHookClass.getDeclaredMethod("afterHookedMethod", MethodHookParamClass);
            findField.invoke(null, param.methodHookParamObj);
        } catch (Exception e) {
            e.printStackTrace();

        }*/
    }

/*    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Method findField = XC_MethodHook.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return findField.invoke(this, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public static class MethodHookParam {

        public Object methodHookParamObj;

        public MethodHookParam(Object o) {
            methodHookParamObj = o;
        }

        public Object getResult() {
            try {
                Method findField = MethodHookParamClass.getDeclaredMethod("getResult");
                return findField.invoke(methodHookParamObj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;

            }
        }

        public void setResult(Object result) {
            try {
                Method findField = MethodHookParamClass.getDeclaredMethod("getResult", Object.class);
                findField.invoke(methodHookParamObj, result);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        public Throwable getThrowable() {
            try {
                Method findField = MethodHookParamClass.getDeclaredMethod("getThrowable");
                return (Throwable) findField.invoke(methodHookParamObj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;

            }
        }

        public boolean hasThrowable() {
            try {
                Method findField = MethodHookParamClass.getDeclaredMethod("hasThrowable");
                return (boolean) findField.invoke(methodHookParamObj);
            } catch (Exception e) {
                e.printStackTrace();
                return false;

            }
        }

        public void setThrowable(Throwable throwable) {
            try {
                Method findField = MethodHookParamClass.getDeclaredMethod("setThrowable", Throwable.class);
                findField.invoke(methodHookParamObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Object getResultOrThrowable() throws Throwable {
            try {
                Method findField = MethodHookParamClass.getDeclaredMethod("getResultOrThrowable");
                return findField.invoke(methodHookParamObj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


    }

    public class Unhook {
        public Object unhookObj;

        public Unhook(Member hookMethod) {
            try {
                Constructor<?> constructor = UnhookParamClass.getConstructor(Member.class);
                unhookObj = constructor.newInstance(hookMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Member getHookedMethod() {
            try {
                Method findField = UnhookParamClass.getDeclaredMethod("getHookedMethod");
                return (Member) findField.invoke(unhookObj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public Object getCallback() {
            try {
                Method findField = UnhookParamClass.getDeclaredMethod("getCallback");
                return findField.invoke(unhookObj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public void unhook() {
            try {
                Method findField = UnhookParamClass.getDeclaredMethod("unhook");
                findField.invoke(unhookObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
