package plug.core.operators;

import plug.runtime.core.IMarshaller;
import plug.runtime.core.LanguageModule;
import plug.runtime.core.defaults.DefaultAtomEvaluator;

public class ByteArrayAtomEvaluator extends DefaultAtomEvaluator<byte[], byte[], byte[]> {
    LanguageModule<Object, Object, Object> operand;

    public ByteArrayAtomEvaluator(LanguageModule<Object, Object, Object> operand) {
        this.operand = operand;
    }

    @Override
    public int[] registerAtomicPropositions(String[] atomicPropositions) throws Exception {
        return operand.getAtomEvaluator().registerAtomicPropositions(atomicPropositions);
    }

    @Override
    public boolean[] getAtomicPropositionValuations(byte[] configuration) {
        IMarshaller<Object, Object, Object> marshaller = operand.getMarshaller();
        Object config = marshaller.deserializeConfiguration(configuration);
        return operand.getAtomEvaluator().getAtomicPropositionValuations(config);
    }

    @Override
    public boolean[] getAtomicPropositionValuations(byte[] source, byte[] fireable, byte[] payload, byte[] target) {
        IMarshaller<Object, Object, Object> marshaller = operand.getMarshaller();
        Object config = marshaller.deserializeConfiguration(source);
        Object tran = marshaller.deserializeFireable(fireable);
        Object pay = marshaller.deserializePayload(payload);
        Object tar = marshaller.deserializeConfiguration(target);
        return operand.getAtomEvaluator().getAtomicPropositionValuations(config, tran, pay, tar);
    }
}
