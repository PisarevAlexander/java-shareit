package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
class ItemRequestControllerIntegrationTest {

    @Mock
    private ItemRequestService itemRequestService;

    @InjectMocks
    private ItemRequestController itemRequestController;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;
    private ItemRequest itemRequest;
    private ItemRequestDto itemRequestDto;
    LocalDateTime time;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(itemRequestController)
                .build();

        time = LocalDateTime.now();
        itemRequest = new ItemRequest(1L, "description", 1L, time);
        itemRequestDto = new ItemRequestDto(1L, "description",
                time, new ArrayList<>());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createRequest_WithValidItemRequestDto_ReturnItem() throws Exception {
        mvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createRequest_WithNotValidItemRequestDto_ReturnNotFoundException() throws Exception {
        itemRequestDto.setDescription(null);

        mvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify((itemRequestService), never()).create(itemRequestDto, 1L, LocalDateTime.now());
    }


    @Test
    void findAllRequest() throws Exception {
        when(itemRequestService.getAll(1L))
                .thenReturn(List.of(itemRequestDto));

        mvc.perform(get("/requests")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(itemRequestDto.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestDto.getDescription()));
    }

    @Test
    void findRequest() throws Exception {
        when(itemRequestService.getAllWithSize(1, 1, 1L))
                .thenReturn(List.of(itemRequestDto));

        mvc.perform(get("/requests/all")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", 1L)
                        .param("from", "1")
                        .param("size", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(itemRequestDto.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestDto.getDescription()));

    }

    @Test
    void findRequestById() throws Exception {
        when(itemRequestService.getById(1, 1L))
                .thenReturn(itemRequestDto);

        mvc.perform(get("/requests/{requestId}","1")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestDto.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestDto.getDescription()));
    }
}