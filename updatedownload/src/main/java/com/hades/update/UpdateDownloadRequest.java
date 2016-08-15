package com.hades.update;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by Hades on 2016/8/15.
 */
public class UpdateDownloadRequest implements Runnable {



    private String downloadUrl;
    private String localFilePath;
    private UpdateDownloadListener downloadListener;
    private boolean isDownloading = false;
    private long currentLength;

    private DownloadRequestHandler downloadRequestHandler;

    public UpdateDownloadRequest(String downloadUrl, String localFilePath, UpdateDownloadListener listener){

        this.downloadUrl = downloadUrl;
        this.localFilePath = localFilePath;
        this.downloadListener = listener;
        this.isDownloading = true;
        this.downloadRequestHandler = new DownloadRequestHandler();
    }

    @Override
    public void run() {

        try {
            makeRequest();
        }catch (IOException e){

        }catch (InterruptedException e){

        }

    }

    private void makeRequest() throws IOException, InterruptedException{
        if (!Thread.currentThread().isInterrupted()){
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.connect();
                currentLength = connection.getContentLength();
                if (!Thread.currentThread().isInterrupted()){
                    downloadRequestHandler.sendRespondMessage(connection.getInputStream());
                }
            }catch (IOException e){
                throw e;
            }
        }
    }

    /**
     * 格式化数字
     * @param value
     * @return
     */
    private String getTwoPointFloatStr(float value){
        DecimalFormat fnum = new DecimalFormat("0.00");
        return fnum.format(value);
    }

    /**
     * 包含了下载过程中所有可能出现的异常情况
     */
    public enum FailureCode{
        UnKnownHost, Socket, SocketTimeout, ConnectTimeout,
        IO, HttpResponse, JSON, Interrupted
    }

    public class DownloadRequestHandler{
        protected static final int SUCCESS_MESSAGE = 0;
        protected static final int FAILURE_MESSAGE = 1;
        protected static final int START_MESSAGE = 2;
        protected static final int FINISH_MESSAGE = 3;
        protected static final int NETWORK_OFF = 4;
        protected static final int PROGRESS_CHANGED = 5;

        private int mCompleteSize = 0;
        private int progress = 0;

        private Handler handler;
        public DownloadRequestHandler(){
            handler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    handleSelfMessage(msg);
                }


            };
        }


        protected void sendFinishMessage(){
            sendMessage(obtainMessage(FINISH_MESSAGE, null));
        }

        private void sendProgressChangedMessage(int progress){
            sendMessage(obtainMessage(PROGRESS_CHANGED, new Object[]{progress}));
        }

        private void sendFailureMessage(FailureCode failureCode){
            sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{failureCode}));
        }

        protected void sendMessage(Message msg){
            if (handler != null){
                handler.sendMessage(msg);
            }else {
                handleSelfMessage(msg);
            }
        }

        protected Message obtainMessage(int responseMessage, Object response){
            Message msg = null;
            if (handler != null){
                msg = handler.obtainMessage(responseMessage, response);
            }else {
                msg = Message.obtain();
                msg.what = responseMessage;
                msg.obj = response;
            }
            return msg;
        }

        private void handleSelfMessage(Message msg) {
            Object[] response;
            switch (msg.what){
                case FAILURE_MESSAGE:
                    response = (Object[]) msg.obj;
                    handleFailureMessage((FailureCode) response[0]);
                    break;
                case PROGRESS_CHANGED:
                    response = (Object[])msg.obj;
                    sendProgressChangedMessage(
                            ((Integer)response[0]).intValue());
                    break;
                case FINISH_MESSAGE:
                    onFinish();
                    break;
            }

        }

        protected void handleFailureMessage(FailureCode code){

        }

        protected void onFinish(){

        }

        // 文件下载方法，
        void sendRespondMessage(InputStream is){
            RandomAccessFile randomAccessFile = null;
            mCompleteSize = 0;
            try{
                byte[] buffer = new byte[1024];
                int length = -1;
                int limit = 0;
                randomAccessFile = new RandomAccessFile(localFilePath, "rwd");
                while ((length = is.read(buffer)) != -1){
                    randomAccessFile.write(buffer, 0, length);
                    mCompleteSize += length;
                    if (mCompleteSize < currentLength){
                        progress = (int) Float.parseFloat(
                                getTwoPointFloatStr(mCompleteSize / currentLength));
                        if (limit/30 == 0 || progress <= 100){
                            // 为了限制一下notification的更新频率
                            sendProgressChangedMessage(progress);
                        }

                        limit++;
                    }
                }
                sendFinishMessage();
            }catch (IOException e){
                sendFailureMessage(FailureCode.IO);
            }finally {
                try {
                    if (is != null){
                        is.close();
                    }
                    if (randomAccessFile != null){
                        randomAccessFile.close();
                    }
                }catch (IOException e){
                    sendFailureMessage(FailureCode.IO);
                }
            }
        }
    }

}
