package plug.runtime.core;

public interface IAtomEvaluator<C, F, P> extends ILanguageService<C, F, P> {

    //to register the atomic propositions
    int[] registerAtomicPropositions(String atomicPropositions[]) throws Exception;

    default int registerAtomicProposition(String atomicProposition) throws Exception {
        return registerAtomicPropositions(new String[] { atomicProposition})[0];
    }

    //implement this to evaluate an atomic proposition on a configuration
    default boolean[] getAtomicPropositionValuations(C configuration) {
        return getAtomicPropositionValuations(configuration, null, null, null);
    }

    //implement this to evaluate an atomic proposition on a tuple <source, fireable, payload, target>
    default boolean[] getAtomicPropositionValuations(C source, F fireable, P payload, C target) {
        //[1] if Kripke to transition-based BA transformation then give evaluation in the target
        //[2] if state-based BA then give the evaluation in the source
        //[3] if besides state-based queries, the diagnosis language supports:
        //  - queries on the fireable transitions (ie. t'isEnabled)
        //  - queries on the meta-informations produced during firing (ie. informal, or event tags)
        //  - queries on the target, or on the couple source-target (x'=x+1)
        //  then evaluate them in the context of the arguments of this method
        return getAtomicPropositionValuations(source);
    }

    default boolean getAtomicPropositionValuation(C configuration, int atomPropositionId) {
        return getAtomicPropositionValuations(configuration)[atomPropositionId];
    }
}
