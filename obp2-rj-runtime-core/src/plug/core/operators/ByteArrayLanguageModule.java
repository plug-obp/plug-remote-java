package plug.core.operators;

import plug.runtime.core.LanguageModule;

public class ByteArrayLanguageModule extends LanguageModule<byte[], byte[], byte[]> {
    public ByteArrayLanguageModule(LanguageModule<Object, Object, Object> operand) {
        super(
                new ByteArrayTransitionRelation(operand),
                new ByteArrayAtomEvaluator(operand),
                new ByteArrayTreeProjector(operand),
                new ByteArrayMarshaller(operand));
    }
}
