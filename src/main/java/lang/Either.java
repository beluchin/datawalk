package lang;

import lombok.Data;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Either<T1, T2> {
    @Data
    private final static class First<T1, T2> extends Either<T1, T2> {
        final T1 t;

        @Override
        public void continued(Consumer<T1> consumer, Consumer<T2> __) {
            consumer.accept(t);
        }

        @Override
        public <R> R join(Function<T1, R> mapper, Function<T2, R> __) {
            return mapper.apply(t);
        }
    }

    @Data
    private final static class Second<T1, T2> extends Either<T1, T2> {
        final T2 t;

        @Override
        public void continued(Consumer<T1> __, Consumer<T2> consumer) {
            consumer.accept(t);
        }

        @Override
        public <R> R join(Function<T1, R> __, Function<T2, R> mapper) {
            return mapper.apply(t);
        }
    }

    private Either() {
    }

    public abstract void continued(Consumer<T1> consumer1, Consumer<T2> consumer2);

    public abstract <R> R join(Function<T1, R> mapper1, Function<T2, R> mapper2);

    public static <T1, T2> Either<T1, T2> first(T1 t) {
        return new First<>(t);
    }

    public static <T1, T2> Either<T1, T2> second(T2 t) {
        return new Second<>(t);
    }
}
