package plug.language.soup.examples;

import plug.language.soup.examples.mutex.peterson.AliceBobPeterson;
import plug.language.soup.examples.mutex.peterson.Configuration;
import plug.language.soup.examples.nbits.NBits;
import plug.language.soup.examples.nflags.NFlags;
import plug.language.soup.model.BehaviorSoup;

public class ExampleModels {
    public static BehaviorSoup<Integer> counter(int max) {
        BehaviorSoup<Integer> soup = new BehaviorSoup<>(0);

        soup.add("reset", i -> i == max, i -> 0);
        soup.add("inc", i -> i < max, i -> i+1);

        return soup;
    }

    public static BehaviorSoup<int[]> counters(int size, int max) {
        BehaviorSoup<int[]> soup = new BehaviorSoup<>(new int[size]);

        for (int i = 0; i < size; i++) {
            final int idx = i;
            soup.add("reset["+idx+"]",
                    e -> e[idx] == max,
                    e -> { e[idx] = 0; return e; });
            soup.add("inc["+idx+"]",
                    e -> e[idx] < max,
                    e -> { e[idx]++; return e; });
        }
        return soup;
    }

    public static BehaviorSoup<Configuration> aliceBobPeterson() {
        return new AliceBobPeterson().model();
    }

    public static BehaviorSoup<plug.language.soup.examples.nflags.Configuration> nFlags(int n) {
        return new NFlags().model(n);
    }

    public static BehaviorSoup<plug.language.soup.examples.nbits.Configuration> nbits(int n) {
        return new NBits().model(n);
    }
}

