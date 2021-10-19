package plug.runtime.core;

public interface ILanguageService<C, F, P> {
    LanguageModule<C, F, P> getModule();
    void setModule(LanguageModule<C, F, P> module);
}
