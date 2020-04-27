package typewalk;

import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Random.randomPrimitiveType;

final class CursorTest {
    @Test
    void ofPrimitiveCannotAdvance() {
        val cursor = Cursor.of(randomPrimitiveType());

        assertThat(cursor.advance()).isEmpty();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void ofSimpleWrapperAdvanceOnce() {
        @Data
        class Wrapper {
            int t;
        }
        val cursor = Cursor.of(Wrapper.class);

        assertThat(
                cursor.advance().get().advance()
        )
                .isEmpty();
    }

}
