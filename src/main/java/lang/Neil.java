package lang;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import lombok.Data;

@Data
public final class Neil<T> {
    public final ImmutableList<T> list;

    public T last() {
        return list.get(list.size() - 1);
    }

    public static <T> Neil<T> of(ImmutableList<T> list) {
        Preconditions.checkArgument(!list.isEmpty());
        return new Neil(list);
    }
}
