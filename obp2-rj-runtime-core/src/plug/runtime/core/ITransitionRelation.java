package plug.runtime.core;

import java.util.Collection;
import java.util.Set;

public interface ITransitionRelation<C, F, P> extends ILanguageService<C, F, P> {

    /**
     * Building the initial configurations for the model associated with this language runtime.
     * Typically there is only one initial configuration. But generally there can be more than one (e.g. TLA+)
     */
    Set<C> initialConfigurations();

    /**
     * Computes the next state relations from a given configuration
     * This method returns a collection so that language runtimes that care about multiple transition generating the
     * same 'next' configuration can use a List. Language runtimes that do not support "transition" events can safely
     * use a Set.
     * ClockRDL runtime uses a List
     * LGuardAction uses a Set
     * {@link #fireableTransitionsFrom(C source)} should not only return the target states but also the events generated by the transitions
     */
    Collection<F> fireableTransitionsFrom(C source);

    /**
     * The Language runtime has to implement this method
     * This method executes one unit of behavior.
     * Returning null means that the execution blocked
     */
    Collection<Fanout<P, C>> fireOneTransition(C source, F transition);
}
