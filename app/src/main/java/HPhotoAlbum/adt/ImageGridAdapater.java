package HPhotoAlbum.adt;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hades.Sample.R;
import com.hades.Sample.act.ImageGridActivity;
import com.lidroid.xutils.BitmapUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import HPhotoAlbum.Model.ImageItem;

/**
 * Created by Administrator on 2015/8/23.
 */
public class ImageGridAdapater extends BaseAdapter {
    private Activity act;
    private List<ImageItem> dataList;
    public static Set<String> mSelect = new HashSet<>();

    private Handler mHandler;

    private int selectTotal = 0;

    private BitmapUtils bitmapUtils ;

    public ImageGridAdapater(Activity act, final List<ImageItem> list, Handler mHandler) {
        this.act = act;
        dataList = list;
        this.mHandler = mHandler;
        bitmapUtils = new BitmapUtils(act);
        selectTotal = mSelect.size();
    }

    @Override
    public int getCount() {

        int count = 0;

        if (dataList != null){
            count = dataList.size();
        }

        return count;
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(act, R.layout.item_image_grid, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.image);
            holder.selected = (ImageView) convertView
                    .findViewById(R.id.isselected);
            holder.text = (TextView) convertView
                    .findViewById(R.id.item_image_grid_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final ImageItem item = dataList.get(position);

        holder.iv.setTag(item.imagePath);
        bitmapUtils.display(holder.iv,
                (item.thumbnailPath == null || item.thumbnailPath.equals("")) ? item.imagePath : item.thumbnailPath);


        if (item.isSelected) {
            holder.selected.setImageResource(R.drawable.icon_data_select);
            holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
        } else {
            holder.selected.setImageResource(R.color.white);
            holder.text.setBackgroundResource(R.color.white);
         }

        holder.iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String path = dataList.get(position).imagePath;
                if (selectTotal < ImageGridActivity.MAX_PHOTO_COUNT) {
                    item.isSelected = !item.isSelected;
                    if (item.isSelected) {
                        holder.selected
                                .setImageResource(R.drawable.icon_data_select);
                        holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
                        selectTotal++;
                        mSelect.add(path);
                    } else if (!item.isSelected) {
                        holder.selected.setImageResource(-1);
                        holder.text.setBackgroundResource(R.color.white);
                        selectTotal--;
                        mSelect.remove(path);
                    }
                } else {
                    if (item.isSelected) {
                        item.isSelected = !item.isSelected;
                        holder.selected.setImageResource(R.color.white);
                        selectTotal--;
                        mSelect.remove(path);
                    } else {
                        Message message = Message.obtain(mHandler, 0);
                        message.sendToTarget();
                    }
                }
            }

        });

        return convertView;
    }

    class Holder {
        private ImageView iv;
        private ImageView selected;
        private TextView text;
    }

}
