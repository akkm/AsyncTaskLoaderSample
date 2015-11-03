package info.akkuma.asynctaskloadersample.api;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by akkuma on 2015/11/02.
 */
public class TimelineApiClient {

    private static final int INIT_TIMESTAMP = 1000;

    private static final String[] NAMES = {
            "Annie", "Eddie", "Ellie", "Jeff", "Jack", "Susie", "Steve", "Tess", "Harry", "Bob"
    };

    private static int sLatestTimeStamp = INIT_TIMESTAMP;
    private static HashMap<Integer, FeedEntity> sTimelineMap = new HashMap<>();


    public List<FeedEntity> getTimeline(int limit, int until) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // do nothing
        }

        if (until == 0) {
            until = sLatestTimeStamp;
        }

        List<FeedEntity> list = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            int timestamp = until - i;
            FeedEntity feed = sTimelineMap.get(timestamp);
            if (feed == null) {
                String name = NAMES[RandomUtils.nextInt(0, NAMES.length)];
                String body = RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(30, 60));
                feed = new FeedEntity(timestamp, name, body);
                sTimelineMap.put(timestamp, feed);
            }
            list.add(feed);
        }

        sLatestTimeStamp++;
        return list;
    }

    public void close() {
        // dummy do nothing
    }
}
