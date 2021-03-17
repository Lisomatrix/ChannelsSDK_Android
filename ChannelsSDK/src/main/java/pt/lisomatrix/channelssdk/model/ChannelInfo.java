package pt.lisomatrix.channelssdk.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ChannelInfo {

    private String id;
    private String appID;
    private String name;
    private long createdAt;
    private boolean isClosed;
    private String extra;
    @SerializedName("isPersistent")
    private boolean isPersistent;
    @SerializedName("isPrivate")
    private boolean isPrivate;
    @SerializedName("isPresence")
    private boolean isPresence;

    public ChannelInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isPresence() {
        return isPresence;
    }

    public void setPresence(boolean presence) {
        isPresence = presence;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ChannelInfo fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ChannelInfo.class);
    }
}
