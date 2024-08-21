package com.example.testhook;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;


public class MyInstrumentation {

    private static final String TAG = "ExampleInstrumentedTest";

    public static void useAppContext(Context context, String source, String argument) {
        // Context of the app under test.

        try {

      /*      Class<?> aClass = context.getClassLoader().loadClass("android.app.Activity");
            XposedHelpers.findAndHookMethod(aClass, "onPause", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.e(TAG, "=====>Activity onPause");
                }
            });*/
            Log.e(TAG, "====> unhooks: " + LoadEntry.unhooks.size());

            if (LoadEntry.unhooks.size() > 0) {
                Log.e(TAG, "====> clear hooks: ");


                Iterator<Object> iterator = LoadEntry.unhooks.iterator();
                while (iterator.hasNext()) {
                    Object unhook = iterator.next();
                    Log.e(TAG, "====>  hooks: " + unhook.getClass());
                    if (unhook.getClass().equals(XC_MethodHook.UnhookParamClass)) {
                        try {
                            Method findField = XC_MethodHook.UnhookParamClass.getDeclaredMethod("unhook");
                            findField.invoke(unhook);
                            iterator.remove();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }


            Set<String> strings = com.example.testhook.LoadEntry.valueMap.keySet();
            for (String string : strings) {
                Object object = com.example.testhook.LoadEntry.valueMap.get(string);
                Log.e(TAG, "key: " + string + ",value:" + object);
            }

            String XposedHelpersstr = (String) com.example.testhook.LoadEntry.valueMap.get("de.robv.android.xposed.XposedHelpers");
            XposedHelpers.XposedHelpersClass = LoadEntry.class.getClassLoader().loadClass(XposedHelpersstr);
            Log.e(TAG, "XposedHelpersClass: " + XposedHelpers.XposedHelpersClass);

            String XposedBridgestr = (String) com.example.testhook.LoadEntry.valueMap.get("de.robv.android.xposed.XposedBridge");
            XposedBridge.XposedBridgeClass = LoadEntry.class.getClassLoader().loadClass(XposedBridgestr);
            Log.e(TAG, "XposedBridgeClass: " + XposedBridge.XposedBridgeClass);

            String XC_MethodHookStr = (String) com.example.testhook.LoadEntry.valueMap.get("de.robv.android.xposed.XC_MethodHook");
            XC_MethodHook.XC_MethodHookClass = LoadEntry.class.getClassLoader().loadClass(XC_MethodHookStr);
            Log.e(TAG, "XC_MethodHookClass: " + XC_MethodHook.XC_MethodHookClass);

            String MethodHookParamStr = (String) com.example.testhook.LoadEntry.valueMap.get("de.robv.android.xposed.XC_MethodHook$MethodHookParam");
            XC_MethodHook.MethodHookParamClass = LoadEntry.class.getClassLoader().loadClass(MethodHookParamStr);
            Log.e(TAG, "MethodHookParamClass: " + XC_MethodHook.MethodHookParamClass);

            String UnhookStr = (String) com.example.testhook.LoadEntry.valueMap.get("de.robv.android.xposed.XC_MethodHook$Unhook");
            XC_MethodHook.UnhookParamClass = LoadEntry.class.getClassLoader().loadClass(UnhookStr);
            Log.e(TAG, "UnhookParamClass: " + XC_MethodHook.UnhookParamClass);

            String XC_MethodReplacementStr = (String) com.example.testhook.LoadEntry.valueMap.get("de.robv.android.xposed.XC_MethodReplacement");
            XC_MethodReplacement.XC_MethodReplacementClass = LoadEntry.class.getClassLoader().loadClass(XC_MethodReplacementStr);
            Log.e(TAG, "UnhookParamClass: " +  XC_MethodReplacement.XC_MethodReplacementClass);


            Class<?> LoadEntry_bak = context.getClassLoader().loadClass("com.hepta.dumpdex.LoadEntry_bak");

            XposedHelpers.findAndHookMethod(LoadEntry_bak, "entry", Context.class, String.class, String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.e(TAG, "entry afterHookedMethod: ====> Test1");
                    Toast.makeText(context, "Test10", Toast.LENGTH_SHORT).show();
                }
            });

            XposedHelpers.findAndHookConstructor(StringBuilder.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.e(TAG, "afterHookedMethod: StringBuilder" );
                }
            });
            XposedBridge.hookAllConstructors(JSONObject.class,new XC_MethodHook(){
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.e(TAG, "afterHookedMethod: init JSONObject" );
                }
            });
            XposedBridge.hookAllMethods(JSONObject.class,"put",new XC_MethodHook(){
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.e(TAG, "===> afterHookedMethod: put" );
                }
            });


            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(1000);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("1",1);
                        jsonObject.put("1","123");
                        StringBuilder stringBuilder = new StringBuilder(jsonObject.toString());
                        Log.i(TAG, "run1: "+stringBuilder.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }.start();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
