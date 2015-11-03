package info.akkuma.asynctaskloadersample;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import info.akkuma.asynctaskloadersample.api.FeedEntity;
import info.akkuma.asynctaskloadersample.api.TimelineApiClient;

/**
 * Created by akkuma on 2015/11/03.
 */
public class TimelineLoader extends AsyncTaskLoader<List<FeedEntity>> {
    private static int sInstanceNumber = 0;
    private final String mTag;

    private int mLimit;
    private int mUntil;
    private boolean mIsRefresh;

    private List<FeedEntity> mList;
    private TimelineApiClient mClient;

    public TimelineLoader(Context context, int limit, int until, boolean isRefresh) {
        super(context);
        mLimit = limit;
        mUntil = until;
        mIsRefresh = isRefresh;
        mClient = new TimelineApiClient();
        mTag = "TimelineLoader[" + sInstanceNumber + "]";
        sInstanceNumber++;
    }

    @Override
    protected void onStartLoading() {
        Log.d(mTag, "onStartLoading called.");
        if (mList != null) {
            deliverResult(mList);
        }
        if (takeContentChanged() || mList == null) {
            forceLoad();
        }
    }

    @Override
    public List<FeedEntity> loadInBackground() {
        Log.d(mTag, "loadInBackground called.");
        return mClient.getTimeline(mLimit, mUntil);
    }

    @Override
    public void deliverResult(List<FeedEntity> data) {
        Log.d(mTag, "deliverResult called.");
        mList = data;
        super.deliverResult(data);
    }

    @Override
    protected void onAbandon() {
        Log.d(mTag, "onAbandon called.");
    }

    @Override
    protected void onStopLoading() {
        Log.d(mTag, "onStopLoading called.");
    }

    @Override
    public void onCanceled(List<FeedEntity> data) {
        Log.d(mTag, "onCanceled called.");
    }

    @Override
    protected void onReset() {
        Log.d(mTag, "onReset called.");
        mClient.close();
    }

    public int getLimit() {
        return mLimit;
    }

    public int getUntil() {
        return mUntil;
    }

    public boolean isRefresh() {
        return mIsRefresh;
    }
}
