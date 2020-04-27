package typewalk;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static utils.Random.randomPrimitiveType;

final class CursorOfPrimitiveTest {
    @Test
    void asLeaf() {
        val cursor = Cursor.of(randomPrimitiveType());

        cursor.continued(leaf -> {},
                         innerNode -> fail("not a leaf"));
    }

    @Test
    void cannotAdvance() {
        val cursor = Cursor.of(randomPrimitiveType());

        assertThat(cursor.advance()).isEmpty();
    }
}
