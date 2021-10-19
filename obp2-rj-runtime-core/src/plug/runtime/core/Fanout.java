package plug.runtime.core;

public class Fanout<P, C> {

    public P payload;
    public C target;

    public Fanout(P payload, C target) {
        this.payload = payload;
        this.target = target;
    }
}
