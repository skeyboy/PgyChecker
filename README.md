# PgyChecker
蒲公英App分发平台检测更新和下载最新apk助手,按照libray导入到项目中即可
# 配置
项目实例：https://www.pgyer.com/iAMAS

用户可以去[蒲公英官网](https://www.pgyer.com/doc/view/api#paramInfo)查看自己的_api_key
1.gradle.properties配置对应需要的参数

```
 api_key=pgyer中的个人项目apikey
 buildShortcutUrl=应用设置的端连接【https://www.pgyer.com/aAMAS 对应的为 aAMAS】
 buildPassword=发布应用时设置的私人密码
 
```
2. build.grable中配置对应信息
```
 defaultConfig {
        applicationId "com.xsk.pgychecker"
        minSdkVersion 19
        targetSdkVersion 27
        versionCode 1
        versionName "${vName}"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        manifestPlaceholders = [pgyer_api_key:  api_key, pgyer_buildShortcutUrl:  buildShortcutUrl, pgyer_buildPassword:  buildPassword] //对应的pgyer_xx 必须照抄，不可修改
    }
```
3.manifests中配置meta-data
```
 <meta-data
            android:name="pgyer_api_key"
            android:value="${pgyer_api_key}" />
        <meta-data
            android:name="pgyer_buildShortcutUrl"
            android:value="${pgyer_buildShortcutUrl}" />
        <meta-data
            android:name="pgyer_buildPassword"
            android:value="${pgyer_buildPassword}" />
```
