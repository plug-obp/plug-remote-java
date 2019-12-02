package plug.language.soup.model;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Behavior<Env> {
    public String name;
    public Predicate<Env> guard;
    public Function<Env, Env> action;

    public Behavior(String name, Predicate<Env> guard, Function<Env, Env> action) {
        this.name = name;
        this.guard = guard;
        this.action = action;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Behavior) {
            Behavior other = (Behavior)obj;
            return Objects.equals(name, other.name)
                    && Objects.equals(guard, other.guard)
                    && Objects.equals(action, other.action);
        }
        return false;
    }
}
