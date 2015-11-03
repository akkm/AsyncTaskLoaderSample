package info.akkuma.asynctaskloadersample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import info.akkuma.asynctaskloadersample.api.FeedEntity;

/**
 * Created by akkuma on 2015/11/03.
 */
public class TimelineArrayAdapter extends ArrayAdapter<FeedEntity> {

    private static HashMap<Integer, Boolean> sHistoryMap = new HashMap<>();

    public TimelineArrayAdapter(Context context, List<FeedEntity> objects) {
        super(context, R.layout.list_item, R.id.timestamp, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView timestamp = (TextView) view.findViewById(R.id.timestamp);
        TextView userName = (TextView) view.findViewById(R.id.user_name);
        TextView body = (TextView) view.findViewById(R.id.body);

        FeedEntity item = getItem(position);

        timestamp.setText("[" + item.getTimeStamp() + "]");
        userName.setText(item.getName());
        body.setText(item.getBody());

        Boolean isRead = sHistoryMap.get(item.getTimeStamp());
        if (isRead != null && isRead) {
            timestamp.setTextColor(0xFF000000);
        } else {
            timestamp.setTextColor(0xFFFF2222);
        }

        return view;
    }

    public void markAllAsRead() {
        for (int i = 0; i < getCount(); i++) {
            FeedEntity feed = getItem(i);
            sHistoryMap.put(feed.getTimeStamp(), new Boolean(true));
        }
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }
}
