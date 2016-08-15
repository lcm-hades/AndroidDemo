package com.hades.Sample.Network;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Hades on 2015/10/21.
 */
public class Network {



    public static void post(){
        String url = "http://api.gitopen.com/api/editcustomer";
        RequestParams params = new RequestParams();
        // params.addHeader("name", "value");
        // params.addQueryStringParameter("name", "value");
        // 只包含字符串参数时默认使用BodyParamsEntity，
        // 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        params.addBodyParameter("app_secret", "88ff518f4840efcd08008f96d60ad5fb");
        params.addBodyParameter("platform", "2");
        params.addBodyParameter("cid", "2");
        params.addBodyParameter("parent_id", "12");
        params.addBodyParameter("photo", "");
        // 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
        // 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
        // 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
        // MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
        // 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
        // params.addBodyParameter("file", new File("path"));
        HttpUtils http = new HttpUtils();
//        http.send(HttpRequest.HttpMethod.POST,
//                url,
//                params,
//                new RequestCallBack<String>() {
//
//                    @Override
//                    public void onStart() {
//                    }
//                    @Override
//                    public void onLoading(long total, long current, boolean isUploading) {
////                        if (isUploading) {
////                            testTextView.setText("upload: " + current + "/" + total);
////                        } else {
////                            testTextView.setText("reply: " + current + "/" + total);
////                        }
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        Log.i("test", responseInfo.result);
//                    }
//
//                    @Override
//                    public void onFailure(HttpException error, String msg) {
//                        Log.i("test", error.getExceptionCode() + ":" + msg);
//                    }
//                });
    }
}
