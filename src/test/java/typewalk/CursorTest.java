package typewalk;

import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static utils.Random.randomPrimitiveType;
import static lang.Streams.takeWhile;
import static java.util.stream.Stream.iterate;
import static org.assertj.core.api.Assertions.assertThat;

final class CursorTest {
    @Test
    void primitive() {
        val cursor = Cursor.of(randomPrimitiveType());

        assertThat(numberOfAdvance(cursor)).isEqualTo(0);
    }

    @Test
    void wrapper() {
        @Data
        class T {
            int t;
        }

        val cursor = Cursor.of(T.class);

        assertThat(numberOfAdvance(cursor)).isEqualTo(1);
    }

    @Test
    void tuple() {
        @Data
        class Tuple {
            Object first;
            Object second;
        }

        val cursor = Cursor.of(Tuple.class);

        assertThat(numberOfAdvance(cursor)).isEqualTo(2);
    }

    private static long numberOfAdvance(Cursor c) {
        return takeWhile(Optional::isPresent,
                         iterate(c.advance(),
                                 opt -> opt.flatMap(Cursor::advance)))
                .count();
    }
}
