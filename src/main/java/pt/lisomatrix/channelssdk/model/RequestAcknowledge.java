package pt.lisomatrix.channelssdk.model;

import pt.lisomatrix.channelssdk.network.model.Channels;

public class RequestAcknowledge {

    private final int replyTo;
    private final boolean status;

    public RequestAcknowledge(int replyTo, boolean status) {
        this.replyTo = replyTo;
        this.status = status;
    }

    public RequestAcknowledge(Channels.PublishAck ack) {
        this.replyTo = ack.getReplyTo();
        this.status = ack.getStatus();
    }

    public int getReplyTo() {
        return replyTo;
    }

    public boolean isStatus() {
        return status;
    }
}
