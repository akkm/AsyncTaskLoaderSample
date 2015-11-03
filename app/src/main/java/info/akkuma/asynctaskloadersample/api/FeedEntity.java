package info.akkuma.asynctaskloadersample.api;

/**
 * Created by akkuma on 2015/11/02.
 */
public class FeedEntity {

    private int timeStamp;
    private String name;
    private String body;

    public FeedEntity(int timeStamp, String name, String body) {
        this.timeStamp = timeStamp;
        this.name = name;
        this.body = body;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
