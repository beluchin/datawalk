package utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.function.Supplier;

import static java.lang.Math.abs;
import static lang.Collections.toArray;

@SuppressWarnings({"WeakerAccess", "unused"})
@UtilityClass
public final class Random {
    private static final java.util.Random JavaUtilRandom = new java.util.Random();
    public static final ImmutableMap<Class<?>, Supplier<Object>> PrimitiveRandoms = ImmutableMap.of(
            int.class, Random::randomInt,
            boolean.class, Random::randomBoolean
    );

    public static <T> T anyOf(Collection<T> ts) {
        return anyOf(toArray(ts));
    }

    @SafeVarargs
    public static <T> T anyOf(T... ts) {
        Preconditions.checkArgument(ts.length != 0);
        return ts[abs(randomInt() % ts.length)];
    }

    public static boolean randomBoolean() {
        return JavaUtilRandom.nextBoolean();
    }

    public static int randomInt() {
        return JavaUtilRandom.nextInt();
    }

    public static <T> T randomPrimitive(Class<T> primitiveType) {
        Preconditions.checkArgument(PrimitiveRandoms.containsKey(primitiveType),
                                    "not a primitive type");
        //noinspection unchecked
        return (T) PrimitiveRandoms.get(primitiveType).get();
    }

    public static Class<?> randomPrimitiveType() {
        return anyOf(PrimitiveRandoms.keySet());
    }
}
