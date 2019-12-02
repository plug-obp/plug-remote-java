package plug.language.soup.module;

import org.apache.commons.jexl3.*;
import plug.language.soup.model.Behavior;
import plug.runtime.core.defaults.DefaultAtomEvaluator;

public class SoupAtomEvaluator extends DefaultAtomEvaluator<Object, Behavior<Object>, Void> {
    // Create or retrieve an engine
    JexlEngine jexl = new JexlBuilder().create();

    JexlExpression expressions[];

    @Override
    public int[] registerAtomicPropositions(String[] atomicPropositions) throws Exception {
        expressions = new JexlExpression[atomicPropositions.length];
        int indices[] = new int[atomicPropositions.length];

        for (int i = 0; i < atomicPropositions.length; i++) {
            expressions[i] = jexl.createExpression(atomicPropositions[i]);
            indices[i] = i;
        }

        return indices;
    }

    @Override
    public boolean[] getAtomicPropositionValuations(Object configuration) {

        // Create a context and add data
        JexlContext context = new ClassAwareContext();
        context.set("t", configuration );

        return evaluateOn(context);
    }

    @Override
    public boolean[] getAtomicPropositionValuations(Object source, Behavior<Object> fireable, Void payload, Object target) {
        // Create a context and add data
        JexlContext context = new ClassAwareContext();
        context.set("s", source );
        context.set("f", fireable );
        context.set("p", payload );
        context.set("t", target );

        return evaluateOn(context);
    }

    boolean[] evaluateOn(JexlContext context) {
        // Now evaluate the expression, getting the result
        boolean results[] = new boolean[expressions.length];
        for (int i=0; i<results.length; i++) {
            results[i] = (boolean)expressions[i].evaluate(context);
        }

        return results;
    }

    public static class ClassAwareContext extends MapContext {
        @Override
        public boolean has(String name) {
            try {
                return super.has(name) || Class.forName(name) != null;
            } catch (ClassNotFoundException xnf) {
                return false;
            }
        }

        @Override
        public Object get(String name) {
            try {
                Object found = super.get(name);
                if (found == null && !super.has(name)) {
                    found = Class.forName(name);
                }
                return found;
            } catch (ClassNotFoundException xnf) {
                return null;
            }
        }
    }
}

