package typewalk;

import lang.Either;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

@Data
final class Cursor {
    final Either<LeafCursor, InnerNodeCursor> either;
    @Nullable final Cursor next;

    Optional<Cursor> advance() {
        return Optional.ofNullable(next);
    }

    void continued(Consumer<LeafCursor> leafConsumer,
                   Consumer<InnerNodeCursor> innerNodeCursorConsumer) {
        either.continued(leafConsumer, innerNodeCursorConsumer);
    }

    static Cursor of(Class<?> type) {
        if (type.isPrimitive()) {
            return new Cursor(Either.first(new LeafCursor(type)), null);
        }
        throw new UnsupportedOperationException();
    }
}
