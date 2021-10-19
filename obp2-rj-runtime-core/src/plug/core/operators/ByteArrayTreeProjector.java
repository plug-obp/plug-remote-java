package plug.core.operators;

import plug.runtime.core.IMarshaller;
import plug.runtime.core.LanguageModule;
import plug.runtime.core.TreeItem;
import plug.runtime.core.defaults.DefaultTreeProjector;

public class ByteArrayTreeProjector extends DefaultTreeProjector<byte[], byte[], byte[]> {
    LanguageModule<Object, Object, Object> operand;

    public ByteArrayTreeProjector(LanguageModule<Object, Object, Object> operand) {
        this.operand = operand;
    }

    public TreeItem projectConfiguration(byte[] configuration) {
        IMarshaller<Object, Object, Object> marshaller = operand.getMarshaller();
        Object conf = marshaller.deserializeConfiguration(configuration);
        return operand.getTreeProjector().projectConfiguration(conf);
    }

    public TreeItem projectFireable(byte[] fireable) {
        IMarshaller<Object, Object, Object> marshaller = operand.getMarshaller();
        Object transition = marshaller.deserializeFireable(fireable);
        return operand.getTreeProjector().projectConfiguration(transition);
    }

    public TreeItem projectPayload(byte[] payload) {
        IMarshaller<Object, Object, Object> marshaller = operand.getMarshaller();
        Object pay = marshaller.deserializePayload(payload);
        return operand.getTreeProjector().projectConfiguration(pay);
    }
}