package com.hades.update;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Hades on 2016/8/15.
 */
public class UpdateDownloadManager {

    private static UpdateDownloadManager manager;
    private ThreadPoolExecutor threadPoolExecutor;
    private UpdateDownloadRequest request;

    private UpdateDownloadManager(){
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    static {
        manager = new UpdateDownloadManager();
    }

    public static UpdateDownloadManager getInstance(){
        return manager;
    }

    public void startDowndloads(String downloadUrl, String localPath, UpdateDownloadListener listener){
        if (request != null){
            return;
        }
        checkLocalFilePath(localPath);
        request = new UpdateDownloadRequest(downloadUrl, localPath, listener);
        Future<?> future = threadPoolExecutor.submit(request);
    }

    private void checkLocalFilePath(String localPath) {
        File dir = new File(localPath.substring(0, localPath.lastIndexOf("/") + 1));
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(localPath);
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){

            }
        }
    }
}
