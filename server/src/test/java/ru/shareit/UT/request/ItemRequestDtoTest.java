package ru.shareit.UT.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.shareit.request.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Item request dto test
 */

@JsonTest
class ItemRequestDtoTest {

    @Autowired
    private JacksonTester<ItemRequestDto> json;

    /**
     * To item request dto
     */

    @Test
    void toItemRequestDto() throws Exception {
        LocalDateTime time = LocalDateTime.now();
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "description",
                time, new ArrayList<>());

        JsonContent<ItemRequestDto> result = json.write(itemRequestDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathArrayValue("$.items").isEqualTo(new ArrayList<>());
    }
}