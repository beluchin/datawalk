package typewalk;

import com.google.common.collect.ImmutableList;
import lang.Either;
import lang.Neil;
import lang.Streams;
import lombok.Data;
import lombok.val;

import java.util.Optional;

import static java.util.stream.Stream.iterate;

@Data
public final class Cursor {
    @Data
    private static final class NodeExceptRoot {
        final Neil<Class> branch; // root at 0, parent's last
        final int index;

        Cursor advance() {
            return index != lastPropertyIndex(parent())
                    ? newNodeCursor(new NodeExceptRoot(branch, index + 1))
                    : newRootCursor();
        }

        private Class<?> parent() {
            return branch.last();
        }

        private static int lastPropertyIndex(Class<?> type) {
            return type.getFields().length - 1;
        }
    }

    @Data
    private static final class Root {}

    private final Either<Root, NodeExceptRoot> either;

    public Optional<Cursor> advance() {
        return Optional.ofNullable(either.join(__ -> null,
                                               NodeExceptRoot::advance));
    }

    public static Cursor of(Class<?> type) {
        return type.isPrimitive()
                ? newRootCursor()
                : newFirstDepthNode(type);
    }

    private static Neil<Class> firstDepthFirstBranch(Class<?> type) {
        val builder = ImmutableList.<Class>builder();
        Streams.<Class>takeWhile(t -> !t.isPrimitive(),
                                 iterate(type, Cursor::firstProperty))
                .forEach(builder::add);
        return Neil.of(builder.build());
    }

    private static Class<?> firstProperty(Class<?> type) {
        return type.getFields()[0].getType();
    }

    private static Cursor newFirstDepthNode(Class<?> type) {
        return new Cursor(Either.second(new NodeExceptRoot(firstDepthFirstBranch(type), 0)));
    }

    private static Cursor newNodeCursor(NodeExceptRoot node) {
        return new Cursor(Either.second(node));
    }

    private static Cursor newRootCursor() {
        return new Cursor(Either.first(new Root()));
    }
}
