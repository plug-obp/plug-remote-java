package plug.runtime.core.defaults;

import plug.runtime.core.ITreeProjector;
import plug.runtime.core.LanguageModule;

public abstract class DefaultTreeProjector<C, F, P> implements ITreeProjector<C, F, P> {
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
