
# QHook

### 介绍
qhook,是一个xposed的模块,帮助用户在不重启应用的情况下,更新hook,目标是像frida那样进行快速hook


### 平台支持
目前只支持lsposed框架,第一次需要root来初始化,将注入模块放到/data/local/tmp中设置权限



### 功能原理
+ 参考rxposed的进程注入方式,使用ptrace注入so来加载apk到目标
+ 因为lsposed将XposedHelpers,XposedBridge类重新命名了,所以需要进行hook将新的名称给获取到,然后hook classloader将xposed api的classloader传递给新的注入的插件
+ 使用代理的方式将本地的xposed api转为远程目标中的xposed,并进行动态hook
+ 每次使用脚本会将自动构建apk,并将apk推送到/data/local/tmp的目录中,然后目标加载该apk,执行其中的方法
+ 保存所有Unhook的钩子，在下一次启动时执行unhook动作


### 使用说明
+ 正常编译为xposed模块,第一次需要root权限打开进行初始化
+ app中的buildPushAndGetPid 为自动处理任务,运行直接可以执行,需要设置参数
  <br/>
```
def type = 'lsposed'  #目标框架类型
def apk_path = '/data/local/tmp/base' + generateRandomString(5) + '.apk'   #apk的位置
def classname = 'com.example.testhook.MyInstrumentation'   #加载apk后执行的类
def method = 'useAppContext'  #方法
def argument = 'kk'  #参数,传递参数到apk中
def packageName = "com.hepta.dumpdex"   #目标的包名
```
+ 如果注入的apk中包含的类和xposed中包含的类相同,则注入的apk中的类将不会更新,因此在编译xposed时需要排除一些类,这样在注入后的apk中用到的将会是更新后的类,配置类的位置在 build.gradle文件中的excludedClass中




### 参考
https://github.com/Thehepta/RXPosed.git



