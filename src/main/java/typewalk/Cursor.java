package typewalk;

import lang.Either;
import lombok.Data;

import java.util.Optional;

@Data
public final class Cursor {
    @Data
    private static final class Node {
        final Cursor next;
    }

    @Data
    private static final class Root {}

    private final Either<Root, Node> either;

    public Optional<Cursor> advance() {
        return Optional.ofNullable(either.join(__ -> null,
                                               node -> node.next));
    }

    public static Cursor of(Class<?> type) {
        return type.isPrimitive()
                ? newRoot()
                : newNode(newRoot());
    }

    private static Class<?> firstProperty(Class<?> type) {
        return type.getDeclaredFields()[0].getType();
    }

    private static Cursor newNode(Cursor next) {
        return new Cursor(Either.second(new Node(next)));
    }

    private static Cursor newRoot() {
        return new Cursor(Either.first(new Root()));
    }

}
