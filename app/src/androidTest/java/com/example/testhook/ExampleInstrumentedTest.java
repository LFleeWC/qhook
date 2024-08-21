package com.example.testhook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;

import dalvik.system.PathClassLoader;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    public static final String PACKAGE_NAME = "com.network.proxy";
    public static final String Tool_Path = "/data/local/tmp/inject/";

    public static boolean is64bit;

    public ExampleInstrumentedTest() {
    }
    @Test
    public  void startHook() {
        String pid = rootRun("pidof " + PACKAGE_NAME); //获取pid

        String exe = rootRun("cat /proc/" + pid + "/exe | file -");
        if (exe.contains("64-bit")) {
            is64bit = true;
        } else {
            is64bit = false;
        }


        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(is64bit?Tool_Path+LoadEntry.arm64_InjectTool:Tool_Path+LoadEntry.arm32_InjectTool);
        stringBuilder.append(" -p ");
        stringBuilder.append(pid);
        stringBuilder.append(" -so ");
        stringBuilder.append(is64bit?Tool_Path+LoadEntry.load_64so:Tool_Path+LoadEntry.load_32so);
        stringBuilder.append(" -symbols  _Z14Inject_PorcessPKc ");
        stringBuilder.append("lsposed:"); //lsposed
        stringBuilder.append("/data/local/tmp/inject/base.apk:"); //apk的路径
        stringBuilder.append(ExampleInstrumentedTest.class.getName());   //执行的类名
        stringBuilder.append(":useAppContext:");  //执行的方法名
        stringBuilder.append("kk"); //参数名,argument

        rootRun(stringBuilder.toString());

    }



    public static void useAppContext(Context context, String source, String argument) {
        // Context of the app under test.
      /*  Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getClassLoader());
        System.out.println(appContext.getPackageName());
      //  assertEquals("com.example.testhook", appContext.getPackageName());
        Intent intent = new Intent("org.updatedex.test");
        intent.putExtra("package","com.fbank.mobile");
        intent.putExtra("apkPath",appContext.getApplicationInfo().sourceDir);
        appContext.sendBroadcast(intent);*/
        try {

      /*      Class<?> aClass = context.getClassLoader().loadClass("android.app.Activity");
            XposedHelpers.findAndHookMethod(aClass, "onPause", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.e(TAG, "=====>Activity onPause");
                }
            });*/

            Class<?> aClass1 = LoadEntry.class.getClassLoader().loadClass("dldON.N.qacWqfzgNGh.iF.XposedBridge");
            Log.e(TAG, "useAppContext: ===>XposedBridge:" + aClass1);
            String classPath = System.getProperty("java.class.path", ".");
            String librarySearchPath = System.getProperty("java.library.path", "");

            ClassLoader pathClassLoader = new PathClassLoader(classPath, librarySearchPath + LoadEntry.FEATURE, null); //通过hook返回xposed的classloader
            //  InMemoryDexClassLoader inMemoryDexClassLoader = (InMemoryDexClassLoader) pathClassLoader;

            Log.e(TAG, "inMemoryDexClassLoader: " + pathClassLoader);
            Log.e(TAG, "inMemoryDexClassLoader: " + pathClassLoader.getParent());
            Class<?> aClass = pathClassLoader.getParent().loadClass("dldON.N.qacWqfzgNGh.iF.XposedBridge");

            Class<?> LoadEntry = pathClassLoader.getParent().loadClass("com.example.testhook.LoadEntry");


//            InMemoryDexClassLoader inMemoryDexClassLoader = (InMemoryDexClassLoader) pathClassLoader.getParent();

            Log.e(TAG, "useAppContext: XposedHelpers:" + aClass);
            Log.e(TAG, "useAppContext: XposedHelpers:" + LoadEntry);

            Set<String> strings = com.example.testhook.LoadEntry.valueMap.keySet();
            for (String string : strings) {
                Object object = com.example.testhook.LoadEntry.valueMap.get(string);
                Log.e(TAG, "key: " + string + ",value:" + object);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static String rootRun(String cmd) {
        Log.e(TAG, cmd);
        String Result = "";
        try {
            // 申请获取root权限
            Process process = Runtime.getRuntime().exec("su"); //"/system/xbin/su"
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            InputStream is = process.getInputStream();
            InputStream es = process.getErrorStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            int code = process.waitFor();
            String is_line = null;
            String es_line = null;
//            Log.d(TAG, "Run:\"" + cmd +"\", "+"process.waitFor() = " + code);
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((is_line = br.readLine()) != null) {
                Log.d(TAG, "cmd > " + is_line);
                Result = Result + is_line + "\n";
            }

            br = new BufferedReader(new InputStreamReader(es, "UTF-8"));
            while ((es_line = br.readLine()) != null) {
//                Log.d(TAG, "cmd > "+es_line);
//                Result += es_line;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return Result;
    }
}