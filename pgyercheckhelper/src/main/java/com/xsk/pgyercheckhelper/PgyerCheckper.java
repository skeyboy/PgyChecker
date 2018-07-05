package com.xsk.pgyercheckhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PgyerCheckper {
    Activity activity;
    String _api_key;
    String buildShortcutUrl;
    String localVersion;
    String buildPassword;
    OkHttpClient client = new OkHttpClient();

    public PgyerCheckper(Activity activity) {
        this.activity = activity;
        this._api_key = _api_key;
        this.buildShortcutUrl = buildShortcutUrl;
        ApplicationInfo ai = null;
        try {
            ai = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);

            Bundle bundle = ai.metaData;
            _api_key = bundle.getString("pgyer_api_key");
            buildShortcutUrl = bundle.getString("pgyer_buildShortcutUrl");
            buildPassword = bundle.getString("pgyer_buildPassword");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        PackageInfo packageInfo = null;
        try {
            packageInfo = activity.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0);
            localVersion = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /*检测版本信息*/
    private static String getByShortcut = "https://www.pgyer.com/apiv2/app/getByShortcut";
    private static String install = "https://www.pgyer.com/apiv2/app/install";

    public void check(final PgyerShorCutImp pgyerShorCutImp) {

        pgyerShorCutImp.onCheckStart();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("_api_key", _api_key);
        formBody.add("buildShortcutUrl", buildShortcutUrl);
        Request request = new Request.Builder()
                .url(getByShortcut)
                .post(formBody.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG)
                        .show();
                pgyerShorCutImp.onCheckFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pgyerShorCutImp.onCheckFinished();

                if (response.isSuccessful()) {

                    String responseStr = response.body().string();
                    PgyerShorCutBean pgyerShorCutBean = new Gson().fromJson(responseStr, PgyerShorCutBean.class);
                    boolean needUpdate = !pgyerShorCutBean.data.buildBuildVersion.equals(localVersion);

                    if (needUpdate) {
                        showWarning(pgyerShorCutBean);
                    }
                    Log.d("resp", pgyerShorCutBean.toString());
                } else {
                    Toast.makeText(activity, "网络出现问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showWarning(final PgyerShorCutBean pgyerShorCutBean) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(pgyerShorCutBean.data.buildFileName);
                builder.setMessage(pgyerShorCutBean.data.buildUpdateDescription);
                boolean forceUpdate = pgyerShorCutBean.data.buildUpdateDescription.contains("强制") || pgyerShorCutBean.data.buildUpdateDescription.contains("强制");
                if (!forceUpdate) {
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }
                builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        "https://www.pgyer.com/apiv2/app/install?_api_key=26c2b54eda974d583ffc7893079df776&appKey=aAMAS&buildPassword=amas";
                        String downURl = install + "?_api_key=" + _api_key + "&appKey=" + pgyerShorCutBean.data.buildShortcutUrl + "&buildPassword=" + buildPassword;
                        downLoad(downURl, pgyerShorCutBean.data.buildFileName);

                    }
                });
                builder.show();
            }
        });
    }

    private void downLoad(String url, String fileName) {
        /*
         * 1. 封装下载请求
         */

// http 下载链接（该链接为 CSDN APP 的下载链接，仅做参考）
        String downloadUrl = url;

// 创建下载请求
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        /*
         * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
         *    VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
         *    VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
         *    VISIBILITY_HIDDEN:                    始终不显示通知
         */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

// 设置通知的标题和描述
        request.setTitle("通知标题XXX");
        request.setDescription("对于该请求文件的描述");

        /*
         * 设置允许使用的网络类型, 可选值:
         *     NETWORK_MOBILE:      移动网络
         *     NETWORK_WIFI:        WIFI网络
         *     NETWORK_BLUETOOTH:   蓝牙网络
         * 默认为所有网络都允许
         */
// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

// 添加请求头
// request.addRequestHeader("User-Agent", "Chrome Mozilla/5.0");

// 设置下载文件的保存位置
        File saveFile = new File(Environment.getExternalStorageDirectory(), fileName);
        request.setDestinationUri(Uri.fromFile(saveFile));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        /*
         * 2. 获取下载管理器服务的实例, 添加下载任务
         */
        DownloadManager manager = (DownloadManager) activity.getSystemService(activity.DOWNLOAD_SERVICE);

// 将下载请求加入下载队列, 返回一个下载ID
        long downloadId = manager.enqueue(request);

// 如果中途想取消下载, 可以调用remove方法, 根据返回的下载ID取消下载, 取消下载后下载保存的文件将被删除
// manager.remove(downloadId);
    }
}

