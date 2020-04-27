package lang;

import lombok.Data;

import java.util.function.Consumer;

public abstract class Either<T1, T2> {
    @Data
    private final static class First<T1, T2> extends Either<T1, T2> {
        final T1 t;

        @Override
        public void continued(Consumer<T1> consumer, Consumer<T2> __) {
            consumer.accept(t);
        }
    }

    @Data
    private final static class Second<T1, T2> extends Either<T1, T2> {
        final T2 t;

        @Override
        public void continued(Consumer<T1> __, Consumer<T2> consumer) {
            consumer.accept(t);
        }
    }

    private Either() {
    }

    public abstract void continued(Consumer<T1> consumer1, Consumer<T2> consumer2);

    public static <T1, T2> Either<T1, T2> first(T1 t) {
        return new First<>(t);
    }

    public static <T1, T2> Either<T1, T2> second(T2 t) {
        return new Second<>(t);
    }
}
