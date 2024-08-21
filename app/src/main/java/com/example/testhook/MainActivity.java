package com.example.testhook;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        String apk_path =  MainActivity.this.getApplicationInfo().sourceDir;

        File cacheDir = MainActivity.this.getCacheDir();
        File arm64_InjectTool = new File(cacheDir,LoadEntry.arm64_InjectTool);
        File arm32_InjectTool = new File(cacheDir,LoadEntry.arm32_InjectTool);
        File load_64so = new File(cacheDir,LoadEntry.load_64so);
        File load_32so = new File(cacheDir,LoadEntry.load_32so);
        extractLibFolder(apk_path,"lib/arm64-v8a/arm64_InjectTool.so",arm64_InjectTool.getAbsolutePath());
        extractLibFolder(apk_path,"lib/armeabi-v7a/armv7_InjectTool.so",arm32_InjectTool.getAbsolutePath());
        extractLibFolder(apk_path,"lib/arm64-v8a/libtest.so",load_64so.getAbsolutePath());
        extractLibFolder(apk_path,"lib/armeabi-v7a/libtest.so",load_32so.getAbsolutePath());

        rootRun("mkdir /data/local/tmp/inject/");
        rootRun("cp "+arm64_InjectTool.getAbsolutePath() +" /data/local/tmp/inject/");
        rootRun("cp "+arm32_InjectTool.getAbsolutePath() +" /data/local/tmp/inject/");
        rootRun("cp "+load_64so.getAbsolutePath() +" /data/local/tmp/inject/");
        rootRun("cp "+load_32so.getAbsolutePath() +" /data/local/tmp/inject/");
        rootRun("chmod 777 -R /data/local/tmp/inject/");

        File file = new File("/data/local/tmp/inject/"+LoadEntry.load_32so);

        if (file.exists()) {
            Toast.makeText(this, "初始化完成!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "初始化失败!", Toast.LENGTH_SHORT).show();
        }

    }

    public static void extractLibFolder(String apkPath,String name,String outpath) {
        try {
            ZipFile zipFile = new ZipFile(apkPath);

            ZipEntry entry = zipFile.getEntry(name);
            if (entry != null) {
                File outputFile = new File(outpath);
                File parentDir = outputFile.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }

                // 提取文件并写入目标路径
                InputStream inputStream = zipFile.getInputStream(entry);
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();

                Log.e(TAG, "File extracted to: " + outputFile.getAbsolutePath());
            } else {
                Log.e(TAG, "File not found in APK: " + name);
            }

            zipFile.close();
        } catch (Exception e) {
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