package typewalk;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.function.Function;

@UtilityClass
public final class TypeWalk {
    public static <T> T typeWalk(Class<T> type, Function<Class<?>, T> fn) {
        //noinspection unchecked
        return (T) TypeUnsafe.typeWalk(type, fn);
    }

    @UtilityClass
    public static final class TypeUnsafe {
        public static Object typeWalk(Class<?> type, Function<Class<?>, ?> fn) {
            val cursor = Cursor.of(type);
            return fn.apply(cursor.type);
        }
    }
}
