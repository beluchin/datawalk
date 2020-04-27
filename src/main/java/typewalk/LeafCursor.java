package typewalk;

import lombok.Data;

@Data
final class LeafCursor {
    private final Class<?> type;

    LeafCursor(Class<?> type) {
        this.type = type;
    }
}
