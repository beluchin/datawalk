package typewalk;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.function.Consumer;

@UtilityClass
public final class TypeWalk {
    public static void typeWalk(Class<?> type, TypeWalkConsumer consumer) {
        val cursor = Cursor.of(type);
        cursor.continued(leaf -> consumer.consumeLeaf(),
                         __ -> {throw new UnsupportedOperationException();});
    }
}
