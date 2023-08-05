package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.BookingForItemDto;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exception.ErrorHandler;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerIntegrationTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mvc;
    private ItemDto itemDto;
    private Item item;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .setControllerAdvice(new ErrorHandler())
                .build();

        itemDto = new ItemDto(1L, "test", "description", true, 1L,
                new BookingForItemDto(), new BookingForItemDto(), new ArrayList<>());
        item = new Item(1L, "test", "description", true, 1L, 1L);
    }

    @Test
    void createNewItem_WithValidItemDto_ReturnItem() throws Exception {
        when(itemService.create(itemDto, 1L))
                .thenReturn(item);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemDto.getId()))
                .andExpect(jsonPath("$.name").value(itemDto.getName()))
                .andExpect(jsonPath("$.description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$.available").value(itemDto.getAvailable()))
                .andExpect(jsonPath("$.requestId").value(itemDto.getRequestId()));
    }

    @Test
    void createNewItem_WithNotValidItemDtoName_ReturnBadRequest() throws Exception {
        itemDto.setName(null);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify((itemService), never()).create(itemDto, 1L);
    }

    @Test
    void createNewItem_WithNotValidItemDtoDescription_ReturnBadRequest() throws Exception {
        itemDto.setDescription(null);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify((itemService), never()).create(itemDto, 1L);
    }

    @Test
    void createNewItem_WithNotValidItemDtoAvailable_ReturnBadRequest() throws Exception {
        itemDto.setAvailable(null);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify((itemService), never()).create(itemDto, 1L);
    }

    @Test
    void update() throws Exception {
        when(itemService.update(itemDto, 1L, 1L))
                .thenReturn(item);

        mvc.perform(patch("/items/{itemId}", 1L)
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemDto.getId()))
                .andExpect(jsonPath("$.name").value(itemDto.getName()))
                .andExpect(jsonPath("$.description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$.available").value(itemDto.getAvailable()))
                .andExpect(jsonPath("$.requestId").value(itemDto.getRequestId()));
    }

    @Test
    void findItem() throws Exception {
        when(itemService.getItemDtoById(1L, 1L))
                .thenReturn(itemDto);

        mvc.perform(get("/items/{itemId}", 1L)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemDto.getId()))
                .andExpect(jsonPath("$.name").value(itemDto.getName()))
                .andExpect(jsonPath("$.description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$.available").value(itemDto.getAvailable()))
                .andExpect(jsonPath("$.requestId").value(itemDto.getRequestId()));
    }

    @Test
    void findAll_WithValidParam_ReturnListItem() throws Exception {
        when(itemService.getAll(1L, 1, 1))
                .thenReturn(List.of(itemDto));

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .param("from", "1")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(itemDto.getId()))
                .andExpect(jsonPath("$[0].name").value(itemDto.getName()))
                .andExpect(jsonPath("$[0].description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$[0].available").value(itemDto.getAvailable()))
                .andExpect(jsonPath("$[0].requestId").value(itemDto.getRequestId()));
    }

    @Test
    void findItemByText() throws Exception {
        when(itemService.search("test", 1, 1))
                .thenReturn(List.of(item));

        mvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", 1L)
                        .param("text", "test")
                        .param("from", "1")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(itemDto.getId()))
                .andExpect(jsonPath("$[0].name").value(itemDto.getName()))
                .andExpect(jsonPath("$[0].description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$[0].available").value(itemDto.getAvailable()))
                .andExpect(jsonPath("$[0].requestId").value(itemDto.getRequestId()));
    }

    @Test
    void findComment_WithNotValidCommit_ReturnBadRequest() throws Exception {
        CommentDto commentDto = new CommentDto(null);

        mvc.perform(post("/items/{itemId}/comment", "1")
                        .content(mapper.writeValueAsString(commentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().is4xxClientError());

        verify((itemService), never()).saveCommit(commentDto, 1L, 1L, LocalDateTime.now());
    }
}