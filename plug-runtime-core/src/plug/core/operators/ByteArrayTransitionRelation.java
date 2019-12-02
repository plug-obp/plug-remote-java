package plug.core.operators;

import plug.runtime.core.Fanout;
import plug.runtime.core.IMarshaller;
import plug.runtime.core.LanguageModule;
import plug.runtime.core.defaults.DefaultTransitionRelation;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ByteArrayTransitionRelation extends DefaultTransitionRelation<byte[], byte[], byte[]> {
    LanguageModule<Object, Object, Object> operand;

    public ByteArrayTransitionRelation(LanguageModule<Object, Object, Object> operand) {
        this.operand = operand;
    }

    @Override
    public Set<byte[]> initialConfigurations() {
        IMarshaller<Object, Object, Object> marshaller = operand.getMarshaller();
        return operand.getTransitionRelation()
                .initialConfigurations()
                .stream()
                .map(marshaller::serializeConfiguration)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<byte[]> fireableTransitionsFrom(byte[] source) {
        IMarshaller<Object, Object, Object> marshaller = operand.getMarshaller();
        Object configuration = marshaller.deserializeConfiguration(source);
        return operand.getTransitionRelation()
                .fireableTransitionsFrom(configuration)
                .stream()
                .map(marshaller::serializeFireable)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Fanout<byte[], byte[]>> fireOneTransition(byte[] source, byte[] transition) {
        IMarshaller<Object, Object, Object> marshaller = operand.getMarshaller();
        Object configuration = marshaller.deserializeConfiguration(source);
        Object fireable = marshaller.deserializeFireable(transition);

        return operand.getTransitionRelation()
                .fireOneTransition(configuration, fireable)
                .stream()
                .map(fanout -> new Fanout<>(marshaller.serializePayload(fanout.payload), marshaller.serializeConfiguration(fanout.target)))
                .collect(Collectors.toList());
    }
}
