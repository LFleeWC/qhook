//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.testhook;

import android.content.res.Resources;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import java.util.WeakHashMap;

public class XposedHelpers {


    public static Class<?> XposedHelpersClass;

    public XposedHelpers() {
    }

    public static Class<?> findClass(String className, ClassLoader classLoader) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findClass", String.class, ClassLoader.class);
            return (Class<?>) findField.invoke(null, className, classLoader);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field findField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findField", Class.class, String.class);
            return (Field) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Field findFieldRecursiveImpl(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findFieldRecursiveImpl", Class.class, String.class);
            return (Field) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field findFirstFieldByExactType(Class<?> clazz, Class<?> type) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findFirstFieldByExactType", Class.class, Class.class);
            return (Field) findField.invoke(null, clazz, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findAndHookMethod", Class.class, String.class, Object[].class);
            XC_MethodHook.replace(parameterTypesAndCallback);

            Object result = findField.invoke(null, clazz, methodName, parameterTypesAndCallback);
            LoadEntry.unhooks.add(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findClass", String.class, ClassLoader.class);
            Class<?> classclz = (Class<?>) findField.invoke(null, className, classLoader);
            return findAndHookMethod(classclz, methodName, parameterTypesAndCallback);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method findMethodExact(Class<?> clazz, String methodName, Object... parameterTypes) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findMethodExact", Class.class, String.class, Object[].class);
            return (Method) findField.invoke(null, clazz, methodName, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method findMethodExact(String className, ClassLoader classLoader, String methodName, Object... parameterTypes) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findMethodExact", String.class, ClassLoader.class, String.class, Object[].class);
            return (Method) findField.invoke(null, className, classLoader, methodName, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method findMethodExact(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findMethodExact", Class.class, String.class, Class[].class);
            return (Method) findField.invoke(null, clazz, methodName, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method[] findMethodsByExactParameters(Class<?> clazz, Class<?> returnType, Class<?>... parameterTypes) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findMethodsByExactParameters", Class.class, Class.class, Class[].class);
            return (Method[]) findField.invoke(null, clazz, returnType, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method findMethodBestMatch(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findMethodBestMatch", Class.class, String.class, Class[].class);
            return (Method) findField.invoke(null, clazz, methodName, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method findMethodBestMatch(Class<?> clazz, String methodName, Object... args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findMethodBestMatch", Class.class, String.class, Object[].class);
            return (Method) findField.invoke(null, clazz, methodName, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method findMethodBestMatch(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findMethodBestMatch", Class.class, String.class, Class[].class, Object[].class);
            return (Method) findField.invoke(null, clazz, methodName, parameterTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?>[] getParameterTypes(Object... args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getParameterTypes", Object[].class);
            return (Class<?>[]) findField.invoke(null, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Class<?>[] getParameterClasses(ClassLoader classLoader, Object[] parameterTypesAndCallback) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getParameterClasses", ClassLoader.class, Object[].class);
            return (Class<?>[]) findField.invoke(null, classLoader, parameterTypesAndCallback);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?>[] getClassesAsArray(Class<?>... clazzes) {
        return clazzes;
    }

    private static String getParametersString(Class<?>... clazzes) {
        StringBuilder sb = new StringBuilder("(");
        boolean first = true;
        Class[] var6 = clazzes;
        int var5 = clazzes.length;

        for (int var4 = 0; var4 < var5; ++var4) {
            Class<?> clazz = var6[var4];
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }

            if (clazz != null) {
                sb.append(clazz.getCanonicalName());
            } else {
                sb.append("null");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public static Constructor<?> findConstructorExact(Class<?> clazz, Object... parameterTypes) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findConstructorExact", Class.class, Object[].class);
            return (Constructor<?>) findField.invoke(null, clazz, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Constructor<?> findConstructorExact(String className, ClassLoader classLoader, Object... parameterTypes) {
        return findConstructorExact(findClass(className, classLoader), getParameterClasses(classLoader, parameterTypes));
    }

    public static Constructor<?> findConstructorExact(Class<?> clazz, Class<?>... parameterTypes) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findConstructorExact", Class.class, Class[].class);
            return (Constructor<?>) findField.invoke(null, clazz, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object findAndHookConstructor(Class<?> clazz, Object... parameterTypesAndCallback) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findAndHookConstructor", Class.class, Object[].class);
            XC_MethodHook.replace(parameterTypesAndCallback);
            Object result = findField.invoke(null, clazz, parameterTypesAndCallback);
            LoadEntry.unhooks.add(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object findAndHookConstructor(String className, ClassLoader classLoader, Object... parameterTypesAndCallback) {
        return findAndHookConstructor(findClass(className, classLoader), parameterTypesAndCallback);
    }

    public static Constructor<?> findConstructorBestMatch(Class<?> clazz, Class<?>... parameterTypes) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findConstructorBestMatch", Class.class, Class[].class);
            return (Constructor<?>) findField.invoke(null, clazz, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Constructor<?> findConstructorBestMatch(Class<?> clazz, Object... args) {
        return findConstructorBestMatch(clazz, getParameterTypes(args));
    }

    public static Constructor<?> findConstructorBestMatch(Class<?> clazz, Class<?>[] parameterTypes, Object[] args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("findConstructorBestMatch", Class.class, Class[].class, Object[].class);
            return (Constructor<?>) findField.invoke(null, clazz, parameterTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setObjectField(Object obj, String fieldName, Object value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setObjectField", Object.class, String.class, Object.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBooleanField(Object obj, String fieldName, boolean value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setBooleanField", Object.class, String.class, boolean.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setByteField(Object obj, String fieldName, byte value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setByteField", Object.class, String.class, byte.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCharField(Object obj, String fieldName, char value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setCharField", Object.class, String.class, char.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDoubleField(Object obj, String fieldName, double value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setCharField", Object.class, String.class, double.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFloatField(Object obj, String fieldName, float value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setCharField", Object.class, String.class, float.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setIntField(Object obj, String fieldName, int value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setIntField", Object.class, String.class, int.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLongField(Object obj, String fieldName, long value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setLongField", Object.class, String.class, long.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setShortField(Object obj, String fieldName, short value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setLongField", Object.class, String.class, short.class);
            findField.invoke(null, obj, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getObjectField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getObjectField", Object.class, String.class);
            return findField.invoke(null, obj, fieldName, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getSurroundingThis(Object obj) {
        return getObjectField(obj, "this$0");
    }

    public static boolean getBooleanField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getBooleanField", Object.class, String.class);
            return (boolean) findField.invoke(null, obj, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte getByteField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getByteField", Object.class, String.class);
            return (byte) findField.invoke(null, obj, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Byte(null);
        }
    }

    public static char getCharField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getCharField", Object.class, String.class);
            return (char) findField.invoke(null, obj, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double getDoubleField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getDoubleField", Object.class, String.class);
            return (double) findField.invoke(null, obj, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static float getFloatField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getFloatField", Object.class, String.class);
            return (float) findField.invoke(null, obj, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getIntField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getIntField", Object.class, String.class);
            return (int) findField.invoke(null, obj, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getLongField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getLongField", Object.class, String.class);
            return (long) findField.invoke(null, obj, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static short getShortField(Object obj, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getShortField", Object.class, String.class);
            return (short) findField.invoke(null, obj, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setStaticObjectField(Class<?> clazz, String fieldName, Object value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticObjectField", Class.class, String.class, Object.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStaticBooleanField(Class<?> clazz, String fieldName, boolean value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticBooleanField", Class.class, String.class, boolean.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStaticByteField(Class<?> clazz, String fieldName, byte value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticByteField", Class.class, String.class, byte.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStaticCharField(Class<?> clazz, String fieldName, char value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticCharField", Class.class, String.class, char.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStaticDoubleField(Class<?> clazz, String fieldName, double value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticDoubleField", Class.class, String.class, double.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStaticFloatField(Class<?> clazz, String fieldName, float value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticFloatField", Class.class, String.class, float.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStaticIntField(Class<?> clazz, String fieldName, int value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticIntField", Class.class, String.class, int.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStaticLongField(Class<?> clazz, String fieldName, long value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticLongField", Class.class, String.class, long.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStaticShortField(Class<?> clazz, String fieldName, short value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setStaticShortField", Class.class, String.class, short.class);
            findField.invoke(null, clazz, fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getStaticObjectField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticObjectField", Class.class, String.class);
            return findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean getStaticBooleanField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticBooleanField", Class.class, String.class);
            return (boolean) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte getStaticByteField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticByteField", Class.class, String.class);
            return (byte) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static char getStaticCharField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticCharField", Class.class, String.class);
            return (char) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double getStaticDoubleField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticDoubleField", Class.class, String.class);
            return (double) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static float getStaticFloatField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticFloatField", Class.class, String.class);
            return (float) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getStaticIntField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticIntField", Class.class, String.class);
            return (int) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getStaticLongField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticLongField", Class.class, String.class);
            return (long) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static short getStaticShortField(Class<?> clazz, String fieldName) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getStaticShortField", Class.class, String.class);
            return (short) findField.invoke(null, clazz, fieldName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Object callMethod(Object obj, String methodName, Object... args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("callMethod", Object.class, String.class, Object[].class);
            return findField.invoke(null, obj, methodName, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object callMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("callMethod", Object.class, String.class, Class[].class, Object[].class);
            return findField.invoke(null, obj, methodName, parameterTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object callStaticMethod(Class<?> clazz, String methodName, Object... args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("callStaticMethod", Class.class, String.class, Object[].class);
            return findField.invoke(null, clazz, methodName, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object callStaticMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("callStaticMethod", Class.class, String.class, Class[].class, Object[].class);
            return findField.invoke(null, clazz, methodName, parameterTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object newInstance(Class<?> clazz, Object... args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("newInstance", Class[].class, Object[].class);
            return findField.invoke(null, clazz, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object... args) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("newInstance", Class.class, Class[].class, Object[].class);
            return findField.invoke(null, clazz, parameterTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object setAdditionalInstanceField(Object obj, String key, Object value) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("setAdditionalInstanceField", Object.class, String.class, Object.class);
            return findField.invoke(null, obj, key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getAdditionalInstanceField(Object obj, String key) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("getAdditionalInstanceField", Object.class, String.class);
            return findField.invoke(null, obj, key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object removeAdditionalInstanceField(Object obj, String key) {
        try {
            Method findField = XposedHelpersClass.getDeclaredMethod("removeAdditionalInstanceField", Object.class, String.class);
            return findField.invoke(null, obj, key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object setAdditionalStaticField(Object obj, String key, Object value) {
        return setAdditionalInstanceField(obj.getClass(), key, value);
    }

    public static Object getAdditionalStaticField(Object obj, String key) {
        return getAdditionalInstanceField(obj.getClass(), key);
    }

    public static Object removeAdditionalStaticField(Object obj, String key) {
        return removeAdditionalInstanceField(obj.getClass(), key);
    }

    public static Object setAdditionalStaticField(Class<?> clazz, String key, Object value) {
        return setAdditionalInstanceField(clazz, key, value);
    }

    public static Object getAdditionalStaticField(Class<?> clazz, String key) {
        return getAdditionalInstanceField(clazz, key);
    }

    public static Object removeAdditionalStaticField(Class<?> clazz, String key) {
        return removeAdditionalInstanceField(clazz, key);
    }

    public static byte[] assetAsByteArray(Resources res, String path) throws IOException {
        InputStream is = res.getAssets().open(path);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];

        int read;
        while ((read = is.read(temp)) > 0) {
            buf.write(temp, 0, read);
        }

        is.close();
        return buf.toByteArray();
    }

    public static String getMD5Sum(String file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            InputStream is = new FileInputStream(file);
            byte[] buffer = new byte[8192];

            int read;
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }

            is.close();
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            return bigInt.toString(16);
        } catch (NoSuchAlgorithmException var7) {
            return "";
        }
    }

    public static class ClassNotFoundError extends Error {
        private static final long serialVersionUID = -1070936889459514628L;

        public ClassNotFoundError(Throwable cause) {
            super(cause);
        }

        public ClassNotFoundError(String detailMessage, Throwable cause) {
            super(detailMessage, cause);
        }
    }

    public static class InvocationTargetError extends Error {
        private static final long serialVersionUID = -1070936889459514628L;

        public InvocationTargetError(Throwable cause) {
            super(cause);
        }

        public InvocationTargetError(String detailMessage, Throwable cause) {
            super(detailMessage, cause);
        }
    }
}
