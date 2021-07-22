package com.shenkong.bzzmaster.common.utils.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.shenkong.bzzmaster.common.utils.LoggerUtils;

import java.io.File;

public class DownloadUtilBeiYong {
    public static final String rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
    private static final String TAG = "DownloadUtil";
    private Context context;
    private DownloadManager downloadManager;
    private long enqueue;
    private String versionUrl;
    private String versionName;

    public DownloadUtilBeiYong(Context context) {
        this.context = context;
    }

    //广播接受者，接收下载状态
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };

    //使用系统下载器下载
    public long downloadAPK(String versionUrl, String versionName) {
        // 删除之前下载的相同的文件
        File file = new File(rootPath, "/shenkong/" + versionName);
        if (file.exists()) {
            boolean delete = file.delete();
            LoggerUtils.d(TAG, delete ? "删除成功" : "删除失败");
        }

        this.versionUrl = versionUrl;
        this.versionName = versionName;

        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(versionUrl));
        request.setAllowedOverRoaming(true);//漫游网络是否可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.allowScanningByMediaScanner();

        //设置文件类型，可以在下载结束后自动打开该文件
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(versionUrl));
        request.setMimeType(mimeString);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);

        //sdcard的目录下的download文件夹，必须设置 //
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/shenkong/" + versionName);

        //将下载请求加入下载队列
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
        enqueue = downloadManager.enqueue(request);
        //注册广播接收者，监听下载状态
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        filter.addAction(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
        context.registerReceiver(receiver, filter);

        return enqueue;
    }

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(enqueue);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    LoggerUtils.d(TAG, ">>>下载暂停");
                    break;
                case DownloadManager.STATUS_PENDING:
                    LoggerUtils.d(TAG, ">>>下载延迟");
                    break;
                case DownloadManager.STATUS_RUNNING:
                    LoggerUtils.d(TAG, ">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    LoggerUtils.d(TAG, ">>>下载完成");
                    //下载完成安装APK
                    //String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/apk/" + versionName;
                    IntentUtil.installApk(context, rootPath + "/shenkong/" + versionName, AppFileProvider.AUTHORITY);
                    break;
                case DownloadManager.STATUS_FAILED:
                    LoggerUtils.d(TAG, ">>>下载失败");
                    break;
            }
        }
    }
}
