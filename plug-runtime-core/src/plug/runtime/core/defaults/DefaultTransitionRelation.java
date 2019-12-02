package plug.runtime.core.defaults;

import plug.runtime.core.IAtomEvaluator;
import plug.runtime.core.ITransitionRelation;
import plug.runtime.core.LanguageModule;

public abstract class DefaultTransitionRelation<C, F, P> implements ITransitionRelation<C, F, P> {
    LanguageModule<C, F, P> module;
    @Override
    public LanguageModule<C, F, P> getModule() {
        return module;
    }

    @Override
    public void setModule(LanguageModule<C, F, P> module) {
        this.module = module;
    }
}
