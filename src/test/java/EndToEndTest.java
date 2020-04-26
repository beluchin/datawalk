import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static typewalk.TypeWalk.TypeUnsafe.typeWalk;
import static utils.Random.randomPrimitive;
import static utils.Random.randomPrimitiveType;

final class EndToEndTest {
    @Test
    void primitives() {
        val type = randomPrimitiveType();

        assertThat(
                typeWalk(type, x -> randomPrimitive(type))
        )
                .isNotNull();
    }
}
