import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.Test;
import typewalk.Cursor;

import java.util.Optional;

import static java.util.stream.Stream.iterate;
import static lang.Streams.takeWhile;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Random.randomPrimitiveType;

final class CursorTest {
    @Test
    void ofPrimitiveCannotAdvance() {
        val cursor = Cursor.of(randomPrimitiveType());

        assertThat(advances(cursor)).isEqualTo(0);
    }

    @Test
    void ofSimpleWrapperAdvancesOnce() {
        @Data
        class Wrapper {
            public int t;
        }
        val cursor = Cursor.of(Wrapper.class);

        assertThat(advances(cursor)).isEqualTo(1);
    }

    @Test
    void ofSimpleAggregateAdvancesTwice() {
        @Data
        class Aggregate {
            public int t;
            public char c;
        }
        val cursor = Cursor.of(Aggregate.class);

        assertThat(advances(cursor)).isEqualTo(2);
    }

    private static long advances(Cursor c) {
        return takeWhile(Optional::isPresent,
                         iterate(c.advance(),
                                 opt -> opt.flatMap(Cursor::advance)))
                .count();
    }

}
