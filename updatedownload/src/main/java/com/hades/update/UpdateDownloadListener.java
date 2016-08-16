package com.hades.update;

/**
 * Created by Hades on 2016/8/15.
 */
public interface UpdateDownloadListener {
    void onStarted();
    void onProgressChanged(int progress, String downloadUrl);
    void onFinished(int completeSize, String downloadUrl);
    void onFailure();
}
