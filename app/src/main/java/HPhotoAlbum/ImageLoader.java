package HPhotoAlbum;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Xiunaer on 2016/5/3.
 */
public class ImageLoader {
    private static ImageLoader mImageLoader;

    private LruCache<String, Bitmap> mLruCache;

    private ExecutorService mThreaPool;

    private static final int DEAFULT = 1;

    private Type mType = Type.LIFO;

    private LinkedList<Runnable> mTaskQueue;

    private Thread mPoolThread;

    private Handler mPoolThreadHandler;

    private Handler mUIHandler;

    public enum Type{
        FIFO,
        LIFO;
    }

    private ImageLoader(int mThreadCount, Type type){
        init(mThreadCount, type);
    }

    private void init(int threadCount, Type type) {
        mPoolThread = new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        // 线程池取出一个任务执行
                        mThreaPool.execute(getTask());
                    }
                };
                Looper.loop();
            };
        };

        mPoolThread.start();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory/8;

        mLruCache = new LruCache<String, Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        mThreaPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;
    }

    private Runnable getTask() {

        if (mType == Type.FIFO){
            return mTaskQueue.removeFirst();
        }else if (mType == Type.LIFO){
            return mTaskQueue.removeLast();
        }
        return null;
    }

    public static ImageLoader getInstance(){
        if (mImageLoader == null){
            synchronized (ImageLoader.class){
                if (mImageLoader == null){
                    mImageLoader = new ImageLoader(DEAFULT, Type.LIFO);
                }
            }
        }
        return mImageLoader;
    }

    public void loadImage(final String path, final ImageView imageView){
        imageView.setTag(path);

        if (mUIHandler == null){
            mUIHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {

                    // 获取图片， 为imageView回调设置图片

                    ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imageView1 = holder.imageView;
                    String path = holder.path;

                    if (imageView1.getTag().toString().equals(path)){
                        imageView1.setImageBitmap(bm);
                    }


                }
            };
        }

        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null){
            refreashBitmap(bm, path, imageView);

        }else {
            addTasks(new Runnable(){
                @Override
                public void run() {
                    // 加载图片
                    // 图片压缩
                    ImageSize imageSize = getImageViewSize(imageView);
                    Bitmap bm = decodeSampleBitmapFromPath(path, imageSize.width, imageSize.height);

                    addBitmapToLruCache(path, bm);

                    refreashBitmap(bm, path, imageView);

                }
            });
        }



    }

    private void refreashBitmap(Bitmap bm, String path, ImageView imageView) {
        Message message = Message.obtain();
        ImageBeanHolder holder = new ImageBeanHolder();
        holder.bitmap = bm;
        holder.path = path;
        holder.imageView = imageView;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    private void addBitmapToLruCache(String path, Bitmap bm) {
        if (getBitmapFromLruCache(path) == null){
            if (bm != null){
                mLruCache.put(path, bm);
            }
        }
    }

    private Bitmap decodeSampleBitmapFromPath(String path, int width, int height) {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight){
            int widthRadio = (int) Math.round(width * 0.1/reqWidth);
            int heightRadio = (int) Math.round(height * 0.1/reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private ImageSize getImageViewSize(ImageView imageView) {

        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

        ImageSize imageSize = new ImageSize();

        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

        int width = imageView.getWidth();

        if (width <= 0){
            width = lp.width;
        }
        if (width <= 0){
            width = imageView.getMaxWidth();
        }
        if (width <= 0){
            width = displayMetrics.widthPixels;
        }


        int height = imageView.getHeight();

        if (height <= 0){
            height = lp.height;
        }
        if (height <= 0){
            height = imageView.getMaxHeight();
        }
        if (height <= 0){
            height = displayMetrics.heightPixels;
        }
        imageSize.height = height;
        imageSize.width = width;
        return imageSize;
    }

    private void addTasks(Runnable runnable) {
        mTaskQueue.add(runnable);
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    private class ImageSize{
        int width;
        int height;
    }

    private class ImageBeanHolder{
        Bitmap bitmap;
        ImageView imageView;

        String path;
    }
}
