package plug.core.operators;

import plug.runtime.core.LanguageModule;
import plug.runtime.core.defaults.DefaultMarshaller;

public class ByteArrayMarshaller extends DefaultMarshaller<byte[], byte[], byte[]> {

    public ByteArrayMarshaller(LanguageModule<Object, Object, Object> operand) {
    }

    @Override
    public byte[] serializeConfiguration(byte[] configuration) {
        return configuration;
    }

    @Override
    public byte[] serializeFireable(byte[] fireable) {
        return fireable;
    }

    @Override
    public byte[] serializePayload(byte[] payload) {
        return payload;
    }

    @Override
    public byte[] deserializeConfiguration(byte[] buffer) {
        return buffer;
    }

    @Override
    public byte[] deserializeFireable(byte[] buffer) {
        return buffer;
    }

    @Override
    public byte[] deserializePayload(byte[] buffer) {
        return buffer;
    }
}
