package pt.lisomatrix.channelssdk.network.converter;

import com.squareup.tape2.ObjectQueue;

import java.io.IOException;
import java.io.OutputStream;

import pt.lisomatrix.channelssdk.network.model.Channels;

public class NewEventsConverter implements ObjectQueue.Converter<Channels.NewEvent> {
    @Override
    public Channels.NewEvent from(byte[] source) throws IOException {
        return Channels.NewEvent.parseFrom(source);
    }

    @Override
    public void toStream(Channels.NewEvent value, OutputStream sink) throws IOException {
        value.writeTo(sink);
    }
}
