package plug.runtime.core;

public interface IMarshaller<C, F, P> extends ILanguageService<C, F, P> {

    byte[] serializeConfiguration(C configuration);

    byte[] serializeFireable(F fireable);

    byte[] serializePayload(P payload);

    C deserializeConfiguration(byte[] buffer);

    F deserializeFireable(byte[] buffer);

    P deserializePayload(byte[] buffer);
}
