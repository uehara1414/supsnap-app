package net.beaconfun.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by akiya on 2017/10/09.
 */

class HistoryAdapter extends RealmBaseAdapter<History> implements ListAdapter{
    AsyncNetwork task = new AsyncNetwork();

    private static class ViewHolder {
        ImageView thumImage;
        TextView locationName;
    }

    public HistoryAdapter(@Nullable OrderedRealmCollection<History> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_card_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.locationName = (TextView) convertView.findViewById(R.id.locationName);
            viewHolder.thumImage = (ImageView) convertView.findViewById(R.id.thumImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        History history = adapterData.get(position);
        byte[] b = history.getThumbnail();
        if (b != null) {
            DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
            int realWid = dm.widthPixels;
            Bitmap bitmap = BitmapFactory.decodeByteArray(b , 0, b .length);
            float rate = realWid / bitmap.getWidth();
            int wi = (int) (bitmap.getWidth() * rate);
            int he = (int) (bitmap.getHeight() * rate);
            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, wi, he, false);
            viewHolder.thumImage.setImageBitmap(bitmap2);
        } else {
            viewHolder.thumImage.setImageResource(R.drawable.p350x150);
        }
        viewHolder.locationName.setText(history.getLocation());
        return convertView;
    }
}

