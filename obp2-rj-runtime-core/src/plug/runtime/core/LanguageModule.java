package plug.runtime.core;

public class LanguageModule<C, F, P> {
    ITransitionRelation<C, F, P> transitionRelation;
    IAtomEvaluator<C, F, P> atomEvaluator;
    ITreeProjector<C, F, P> treeProjector;
    IMarshaller<C, F, P> marshaller;

    public LanguageModule(
            ITransitionRelation transitionRelation,
            IAtomEvaluator atomEvaluator,
            ITreeProjector treeProjector,
            IMarshaller marshaller)
    {
        this.transitionRelation = transitionRelation;
        this.atomEvaluator = atomEvaluator;
        this.treeProjector = treeProjector;
        this.marshaller = marshaller;
        //link back from each service
        transitionRelation.setModule(this);
        atomEvaluator.setModule(this);
        treeProjector.setModule(this);
        marshaller.setModule(this);
    }

    public ITransitionRelation<C, F, P> getTransitionRelation() {
        return transitionRelation;
    }

    public IAtomEvaluator<C, F, P> getAtomEvaluator() {
        return atomEvaluator;
    }

    public ITreeProjector<C, F, P> getTreeProjector() {
        return treeProjector;
    }

    public IMarshaller<C, F, P> getMarshaller() {
        return marshaller;
    }
}
