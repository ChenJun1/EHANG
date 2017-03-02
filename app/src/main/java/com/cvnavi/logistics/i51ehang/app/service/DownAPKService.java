package com.cvnavi.logistics.i51ehang.app.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;

import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.ItemEvent;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * @author xiechengfa2000@163.com
 * @Desp:Service下载apk通过notification显示下载进度 下载完成自动安装
 * @date 2015-9-6 下午5:48:32
 */
public class DownAPKService extends Service {
    public static final String ACTION = "android.intent.action.UpdateSoftService";
    private static final String KEY_URL = "url";
    private static final String KEY_SIZE = "size";
    private static final String KEY_NAME = "name";

    private final int TYPE_DOWNLOAD_FAIL = 1;// 下载失败
    private final int TYPE_DOWNLOAD_SUCC = 2;// 下载成功
    private final int TYPE_DOWNLOAD_PORGRESS = 3;// 下载中

    private PendingIntent contentIntent = null;
    private NotificationManager notificationManager = null;
    private HashMap<String, NotificationInfo> myMap = null;

    public static void startService(Activity activity, String name, String url,
                                    int size) {
        Intent intent = new Intent(activity, DownAPKService.class);
        intent.putExtra(DownAPKService.KEY_NAME, name);
        intent.putExtra(DownAPKService.KEY_URL, url);
        intent.putExtra(DownAPKService.KEY_SIZE, size);
        activity.startService(intent);
    }

    /**
     * @see Service#onBind(Intent)
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent == null) {
            return;
        }

        final String name = intent.getStringExtra(KEY_NAME);
        final String url = intent.getStringExtra(KEY_URL);
        final int size = intent.getIntExtra(KEY_SIZE, 0);

        if (getHashMap().containsKey(url)) {
            return;
        }

        sendNotification(name, url, size);
        new MyTask(url, size).execute();
    }

    private class MyTask extends AsyncTask<Object, Integer, Message> {
        private String url = null;
        private int size = 0;

        public MyTask(String url, int size) {
            this.url = url;
            this.size = size;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Message doInBackground(Object... params) {
            Message message = Message.obtain();
            message.what = TYPE_DOWNLOAD_FAIL;

            String filePath = getStoragePath() + "/"
                    + getFileNameOfTailFromUrl(url);
            File file = new File(filePath);
            filePath = null;
            boolean res = download(url, file, size,
                    new AppDownUpdateListener() {
                        @Override
                        public void onUpdate(int percent) {
                            publishProgress(percent);
                        }
                    });
            if (res) {
                message.what = TYPE_DOWNLOAD_SUCC;
                message.obj = url;
            } else {
                message.what = TYPE_DOWNLOAD_FAIL;
                message.obj = url;
            }
            return message;
        }

        @Override
        protected void onPostExecute(Message result) {
            super.onPostExecute(result);
            handMessage(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Message msg = Message.obtain();
            msg.what = TYPE_DOWNLOAD_PORGRESS;
            msg.arg1 = values[0];
            msg.obj = url;
            handMessage(msg);
        }
    }

    // 发送通知
    public void sendNotification(String name, String url, int size) {

        Notification notification = new Notification(R.mipmap.i51yun,
                "开始下载" + name, System.currentTimeMillis());

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(this, name, "已下载完成:" + "0%",
                geUpdateIntent());
//		RemoteViews mRemoteView=new RemoteViews();

        int id = (int) System.currentTimeMillis();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);

        getHashMap().put(url, new NotificationInfo(id, name, notification));
    }

    private HashMap<String, NotificationInfo> getHashMap() {
        if (myMap == null) {
            myMap = new HashMap<>();
        }

        return myMap;
    }

    private PendingIntent geUpdateIntent() {
        if (contentIntent == null) {
            Intent intent = new Intent();
            contentIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
        return contentIntent;
    }

    private PendingIntent getInstallIntent(String path) {
        Uri uri = Uri.fromFile(new File(path));
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        PendingIntent updatePendingIntent = PendingIntent.getActivity(this, 0,
                installIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return updatePendingIntent;
    }

    /**
     * 开始下载文件
     *
     * @param
     */
    private boolean download(String fileUrl, File file, int fileSize,
                             AppDownUpdateListener listener) {
        boolean res = false;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "*/*");
            fileSize = conn.getContentLength();
            byte[] buffer = new byte[1024];
            int len = 0;
            int startPos = 0;
            int currPos = 0;
            int percent = 0;

            if (!file.exists()) {
                file.createNewFile();
            } else {
                startPos = (int) file.length();
                // 断点下载,设置获取数据的范围
                conn.setRequestProperty("Range", "bytes=" + startPos + "-"
                        + fileSize);
                if (startPos == fileSize) {
                    return true;
                }
            }

            currPos = startPos;
            percent = (int) (1.0 * startPos / fileSize * 100);
            if (listener != null) {
                listener.onUpdate(percent);
            }

            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK
                    || resCode == HttpURLConnection.HTTP_PARTIAL) {
                inputStream = conn.getInputStream();
                outputStream = new FileOutputStream(file, true);
                do {
                    len = inputStream.read(buffer);
                    if (len > 0) {
                        // 写入数据时要使用write(byte[],offer,count);,不要使用write(byte[]);
                        outputStream.write(buffer, 0, len);
                        currPos = currPos + len;

                        int tempPercent = (int) (1.0 * currPos / fileSize * 100);
                        if (tempPercent > percent && listener != null) {
                            percent = tempPercent;
                            listener.onUpdate(percent);
                        }
                    }
                } while (len != -1);
                res = true;
            } else {
                res = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        } finally {
            if (conn != null) {
                conn = null;
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    private void handMessage(Message msg) {
        if (msg == null) {
            return;
        }

        switch (msg.what) {
            case TYPE_DOWNLOAD_FAIL:
                // 下载失败
                onDownFail((String) msg.obj);
                break;
            case TYPE_DOWNLOAD_SUCC:
                // 下载完成
                String url = (String) msg.obj;
                installApk(getStoragePath() + "/" + getFileNameOfTailFromUrl(url));
                onDownOver(url);
                break;
            case TYPE_DOWNLOAD_PORGRESS:
                // 下载中
                onUpdateProgress((String) msg.obj, msg.arg1);
                break;
        }
    }

    private void onDownFail(String url) {
        NotificationInfo info = getHashMap().get(url);
        if (info != null) {
            info.notification.setLatestEventInfo(this, info.name, "下载失败,请重新下载",
                    geUpdateIntent());
            notificationManager.notify(info.id, info.notification);
            getHashMap().remove(url);
        }

        if (getHashMap().size() <= 0) {
            DownAPKService.this.stopSelf();
        }
    }

    private void onDownOver(String url) {
        NotificationInfo info = getHashMap().get(url);
        if (info != null) {
            info.notification.setLatestEventInfo(this, info.name, "下载完成,点击安装",
                    getInstallIntent(getStoragePath() + "/"
                            + getFileNameOfTailFromUrl(url)));
            notificationManager.notify(info.id, info.notification);
            getHashMap().remove(url);
        }

        if (getHashMap().size() <= 0) {
            DownAPKService.this.stopSelf();
        }
    }

    private void onUpdateProgress(String url, int percent) {
        NotificationInfo info = getHashMap().get(url);
        if (info != null) {
            info.notification.setLatestEventInfo(this, info.name, "已下载完成:"
                    + percent + "%", geUpdateIntent());
//            EventBus.getDefault().post(percent);
            notificationManager.notify(info.id, info.notification);
//            EventBus.getDefault().post(new ItemEvent(percent));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class NotificationInfo {
        public int id = 0;
        public String name = null;
        public Notification notification = null;

        public NotificationInfo(int id, String name, Notification notification) {
            this.id = id;
            this.name = name;
            this.notification = notification;
        }
    }

    private interface AppDownUpdateListener {
        public void onUpdate(int percent);
    }

    public static String getStoragePath() {
        /* 检测是否存在SD卡 */
        File filePath = null;
        String exStorageState = Environment.getExternalStorageState();
        if (exStorageState != null
                && Environment.MEDIA_SHARED.equals(exStorageState)) {
            // 有SD卡，手机直接连接到电脑作为u盘使用之后的状态
            return null;
        }

        if (exStorageState != null
                && Environment.MEDIA_MOUNTED.equals(exStorageState)) {
            // sd卡在手机上正常使用状态
            // 是否存在外存储器(优先判断)
            filePath = Environment.getExternalStorageDirectory();
        } else if (isExitInternalStorage()) {
            // 如果外存储器不存在，则判断内存储器
            filePath = MyApplication.getInstance().getFilesDir();
        }

        if (filePath != null) {
            return filePath.getPath() + "/";
        } else {
            return null;
        }
    }

    // 是否存在内存储器
    private static boolean isExitInternalStorage() {
        File file = MyApplication.getInstance().getFilesDir();

        if (file != null) {
            return true;
        }

        return false;
    }

    /**
     * 通过Url获得文件名(带后缀名)
     */
    private String getFileNameOfTailFromUrl(String url) {
        String fileName = "";

        if (url == null || url.trim().length() <= 0) {
            return fileName;
        }

        int start = url.lastIndexOf("/") + 1;
        int end = url.length();

        if (start > 0 && end > 0 && end > start) {
            fileName = url.substring(start, end);
        }

        return fileName;
    }

    /**
     * 安装版本更新的APK
     */
    public static void installApk(String path) {
        File file = new File(path);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        // 设置数据类型
        intent.setDataAndType(Uri.fromFile(file), type);
        MyApplication.getInstance().startActivity(intent);
    }
}