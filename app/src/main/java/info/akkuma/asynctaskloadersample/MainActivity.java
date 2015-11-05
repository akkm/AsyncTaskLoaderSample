package info.akkuma.asynctaskloadersample;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.akkuma.asynctaskloadersample.api.FeedEntity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<FeedEntity>> {
    private static final String TAG = "MainActivity";
    private static final String TAG2 = "LoaderCallbacks";
    private static final String ARG_LIMIT = "limit";
    private static final String ARG_UNTIL = "until";
    private static final String ARG_IS_REFRESH = "isRefresh";

    private TimelineArrayAdapter mAdapter;
    private List<FeedEntity> mList;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = new ArrayList<>();
        mAdapter = new TimelineArrayAdapter(this, mList);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        TextView footer = (TextView) getLayoutInflater().inflate(R.layout.footer_item, null, false);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.isEmpty()) return;
                FeedEntity oldest = mList.get(mList.size() - 1);
                Bundle args = new Bundle();
                args.putInt(ARG_UNTIL, oldest.getTimeStamp() - 1);
                args.putBoolean(ARG_IS_REFRESH, false);
                getSupportLoaderManager().restartLoader(R.id.loader_id_timeline, args, MainActivity.this);
            }
        });
        listView.addFooterView(footer);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSupportLoaderManager().restartLoader(R.id.loader_id_timeline, new Bundle(), MainActivity.this);
            }
        });

        getSupportLoaderManager().initLoader(R.id.loader_id_timeline, new Bundle(), this);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart called.");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume called.");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause called.");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop called.");
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Cancel").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getSupportLoaderManager().getLoader(R.id.loader_id_timeline).cancelLoad();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy called.");
        getSupportLoaderManager().destroyLoader(R.id.loader_id_timeline);
        super.onDestroy();
    }

    @Override
    public Loader<List<FeedEntity>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG2, "onCreateLoader called.");
        int limit = args.getInt(ARG_LIMIT, 10);
        int until = args.getInt(ARG_UNTIL, 0);
        boolean isRefresh = args.getBoolean(ARG_IS_REFRESH, true);
        return new TimelineLoader(this, limit, until, isRefresh);
    }

    @Override
    public void onLoadFinished(Loader<List<FeedEntity>> loader, List<FeedEntity> data) {
        Log.d(TAG2, "onLoadFinished called.");
        if (data == null) {
            return;
        }
        mAdapter.markAllAsRead();
        TimelineLoader originalLoader = (TimelineLoader) loader;

        if (originalLoader.isRefresh()) {
            mList.clear();
        }
        mList.addAll(data);
        mAdapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<FeedEntity>> loader) {
        Log.d(TAG2, "onLoaderReset called.");
    }
}
