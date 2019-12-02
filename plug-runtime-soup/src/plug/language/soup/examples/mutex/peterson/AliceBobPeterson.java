package plug.language.soup.examples.mutex.peterson;

import plug.language.soup.model.Behavior;
import plug.language.soup.model.BehaviorSoup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AliceBobPeterson {
        List<Behavior<Configuration>> behaviors(Actor actor) {
            List<Behavior<Configuration>> behaviors = new ArrayList<>();

            Behavior<Configuration> i2w =
                    new Behavior<>(
                            actor.name() + "_wantsIn",
                            (c) -> c.state[actor.ordinal()] == State.IDLE,
                            (c) -> {
                                int id = actor.ordinal();
                                c.state[id] = State.WAITING;
                                c.flags[id] = true;
                                c.turn = Actor.values()[1-id];
                                return c;
                            });
            Behavior<Configuration> w2c =
                    new Behavior<>(
                            actor.name() + "_getsIn",
                            (c) ->     c.state[actor.ordinal()] == State.WAITING
                                    && (c.turn == actor
                                    || !c.flags[1-actor.ordinal()]),
                            (c) -> {
                                c.state[actor.ordinal()] = State.CRITICAL;
                                return c;
                            });
            Behavior<Configuration> c2i =
                    new Behavior<>(
                            actor.name() + "_getsOut",
                            (c) -> c.state[actor.ordinal()] == State.CRITICAL,
                            (c) -> {
                                int id = actor.ordinal();
                                c.state[id] = State.IDLE;
                                c.flags[id] = false;
                                return c;
                            });
            return Arrays.asList(i2w, w2c, c2i);
        }
        public BehaviorSoup<Configuration> model() {
            BehaviorSoup<Configuration> soup = new BehaviorSoup<>(new Configuration());
            soup.behaviors.addAll(behaviors(Actor.Alice));
            soup.behaviors.addAll(behaviors(Actor.Bob));
            return soup;
        }

}
