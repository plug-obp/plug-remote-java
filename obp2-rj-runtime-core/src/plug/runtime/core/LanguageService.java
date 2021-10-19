package plug.runtime.core;

public class LanguageService<C, F, P> implements ILanguageService<C, F, P> {
    LanguageModule module;

    @Override
    public LanguageModule<C, F, P> getModule() {
        return module;
    }

    @Override
    public void setModule(LanguageModule<C, F, P> module) {
        this.module = module;
    }
}
