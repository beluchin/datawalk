package typewalk;

import lombok.Data;

import javax.annotation.Nullable;
import java.util.Optional;

@Data
final class Cursor {
    @Nullable final Cursor toAdvance;
    final Class<?> type;

    Optional<Cursor> advance() {
        return Optional.ofNullable(toAdvance);
    }

    static Cursor of(Class<?> type) {
        if (type.isPrimitive()) {
            return new Cursor(null, type);
        }
        return new Cursor(new Cursor(null, null), type);
    }
}
