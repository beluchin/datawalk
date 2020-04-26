package lang;

import lombok.experimental.UtilityClass;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;

@UtilityClass
public final class Streams {
    // missing in Java 8 - available on Java 9
    public static <T> Stream<T> takeWhile(Predicate<T> pred, Stream<T> ts) {
        return StreamSupport.stream(takeWhileSpliterator(pred, ts), false);
    }

    private static <T> Spliterator<T> takeWhileSpliterator(Predicate<T> pred, Stream<T> ts) {
        return new Spliterators.AbstractSpliterator<T>(Long.MAX_VALUE, ORDERED) {
            final Spliterator<T> source = ts.spliterator();
            boolean conditionHolds = true;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                return conditionHolds && source.tryAdvance(t -> {
                    if (pred.test(t)) {
                        action.accept(t);
                    } else {
                        conditionHolds = false;
                    }
                });
            }
        };
    }
}
