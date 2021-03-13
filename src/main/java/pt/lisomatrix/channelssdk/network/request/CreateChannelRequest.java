package pt.lisomatrix.channelssdk.network.request;

import com.google.gson.annotations.SerializedName;

public class CreateChannelRequest {

    private String channelID;
    private String name;
    @SerializedName("persistent")
    private boolean isPersistent;
    @SerializedName("private")
    private boolean isPrivate;
    @SerializedName("presence")
    private boolean isPresence;
    String extra;

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
