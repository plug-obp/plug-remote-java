package plug.runtime.core;

public interface ITreeProjector<C, F, P> extends ILanguageService<C, F, P> {

    TreeItem projectConfiguration(C configuration);

    TreeItem projectFireable(F fireable);

    TreeItem projectPayload(P payload);

}
