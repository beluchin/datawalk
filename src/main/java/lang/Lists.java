package lang;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public final class Lists {
    public static <T> T last(List<T> ts) {
        return ts.get(ts.size() - 1);
    }
}
