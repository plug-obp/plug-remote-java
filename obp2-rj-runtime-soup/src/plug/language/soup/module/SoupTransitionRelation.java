package plug.language.soup.module;

import plug.language.soup.model.Behavior;
import plug.language.soup.model.BehaviorSoup;
import plug.runtime.core.Fanout;
import plug.runtime.core.defaults.DefaultTransitionRelation;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class SoupTransitionRelation extends DefaultTransitionRelation<Object, Behavior<Object>, Void> {
    BehaviorSoup<Object> soup;
    public SoupTransitionRelation(BehaviorSoup<Object> soup) {
        this.soup = soup;
    }

    @Override
    public Set<Object> initialConfigurations() {
        return Collections.singleton(soup.state);
    }

    @Override
    public Collection<Behavior<Object>> fireableTransitionsFrom(Object source) {
        return soup.behaviors.stream().filter(b -> b.guard.test(source)).collect(Collectors.toList());
    }

    @Override
    public Collection<Fanout<Void, Object>> fireOneTransition(Object source, Behavior<Object> transition) {
        Object target = transition.action.apply(source);
        return Collections.singleton(new Fanout<>(null, target));
    }
}
