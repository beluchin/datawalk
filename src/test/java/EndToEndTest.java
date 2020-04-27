import lombok.val;
import org.junit.jupiter.api.Test;
import typewalk.TypeWalkConsumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static typewalk.TypeWalk.typeWalk;
import static utils.Random.randomPrimitiveType;

final class EndToEndTest {
    @Test
    void callsConsumerOnce() {
        val consumer = mock(TypeWalkConsumer.class);

        typeWalk(randomPrimitiveType(), consumer);

        verify(consumer, times(1)).consumeLeaf();
    }
}
