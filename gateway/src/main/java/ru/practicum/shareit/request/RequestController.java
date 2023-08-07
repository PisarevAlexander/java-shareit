package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid RequestDto requestDto,
                                          @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Creating request {}", requestDto);
        return requestClient.create(requestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllRequest(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get requests for userId={}", userId);
        return requestClient.getAll(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findItem(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                           @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get requests with userId={}, from={}, size={}", userId, from, size);
        return requestClient.findItem(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findRequestById(@PathVariable long requestId,
                                                  @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get requests by id={}", requestId);
        return requestClient.findRequestById(requestId, userId);
    }
}