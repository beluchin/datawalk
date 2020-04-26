package lang;

import java.util.Collection;

public final class Collections {
    public static <T> T[] toArray(Collection<T> coll) {
        //noinspection unchecked
        return coll.toArray((T[]) (new Object[0]));
    }
}
