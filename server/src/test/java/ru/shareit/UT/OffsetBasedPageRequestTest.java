package ru.shareit.UT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.shareit.OffsetBasedPageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Offset based page request test
 */

class OffsetBasedPageRequestTest {

    /**
     * Before all
     */

    @BeforeAll
    static void beforeAll() {
        OffsetBasedPageRequest ofpg1 = new OffsetBasedPageRequest(0, 1);
    }

    /**
     * Should exception illegal argument exception by offset
     */

    @Test
    void shouldExceptionIllegalArgumentExceptionByOffset() {
        final IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new OffsetBasedPageRequest(-1, 1)
        );

        assertEquals("Offset index must not be less than zero!",
                exception.getMessage());
    }

    /**
     * Should exception illegal argument exception by limit
     */

    @Test
    void shouldExceptionIllegalArgumentExceptionByLimit() {
        final IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new OffsetBasedPageRequest(0, 0)
        );

        assertEquals("Limit must not be less than one!",
                exception.getMessage());
    }

    /**
     * Should get correct page number
     */

    @Test
    void shouldGetCorrectPageNumber() {
        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(10, 5);
        assertEquals(2, pageRequest.getPageNumber());
    }

    /**
     * Should get correct equals
     */

    @Test
    void shouldGetCorrectEquals() {
        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(10, 5);
        OffsetBasedPageRequest pageRequest2 = new OffsetBasedPageRequest(10, 5);
        assertEquals(pageRequest2, pageRequest);
    }

    /**
     * Should get correct to string
     */

    @Test
    void shouldGetCorrectToString() {
        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(10, 5);
        assertEquals(pageRequest.toString(), pageRequest.toString());
    }

    /**
     * Should get correct hash code
     */

    @Test
    void shouldGetCorrectHashCode() {
        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(10, 5);
        assertEquals(pageRequest.hashCode(), pageRequest.hashCode());
    }
}