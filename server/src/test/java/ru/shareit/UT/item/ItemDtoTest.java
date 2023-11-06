package ru.shareit.UT.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.shareit.booking.BookingForItemDto;
import ru.shareit.item.ItemDto;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Item dto test
 */

@JsonTest
class ItemDtoTest {

    @Autowired
    private JacksonTester<ItemDto> json;

    /**
     * Test item dto
     */

    @Test
    void testItemDto() throws Exception {
        ItemDto itemDto = new ItemDto(1L, "test", "description", true, 1L,
                new BookingForItemDto(), new BookingForItemDto(), new ArrayList<>());

        JsonContent<ItemDto> result = json.write(itemDto);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("test");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(true);
        assertThat(result).extractingJsonPathArrayValue("$.comments").isEqualTo(new ArrayList<>());
    }
}