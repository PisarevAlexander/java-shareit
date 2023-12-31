package ru.shareit.IT.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shareit.OffsetBasedPageRequest;
import ru.shareit.booking.Booking;
import ru.shareit.booking.BookingMapper;
import ru.shareit.booking.BookingRepository;
import ru.shareit.booking.Status;
import ru.shareit.comment.Comment;
import ru.shareit.comment.CommentDto;
import ru.shareit.comment.CommentRepository;
import ru.shareit.exception.BadRequestException;
import ru.shareit.exception.NotFoundException;
import ru.shareit.item.*;
import ru.shareit.user.User;
import ru.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * The type Item service impl test
 */

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    private User user;
    private ItemDto itemDto;
    private Item item;
    private Booking booking;
    private Comment comment;

    /**
     * Sets up
     */

    @BeforeEach
    public void setUp() {
        user = new User(1L, "test", "test@gmail.com");
        itemDto = new ItemDto(null, "test", "description", true, 1L,
                null, null, null);
        item = new Item(null, "test", "description", true, 1L, 1L);
        booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now(), null, user, Status.APPROVED);
        comment = new Comment(1L, "comment", item, "vasia", LocalDateTime.now());
    }

    /**
     * Create test
     */

    @Test
    void create() {
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRepository.save(item))
                .thenReturn(item);

        Item actualItem = itemService.create(itemDto, 1L);

        assertEquals(item, actualItem);
    }

    /**
     * Gets item dto by id where status approved get last booking test
     */

    @Test
    void getItemDtoById_WhereStatusApproved_GetLastBooking() {
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));
        when(bookingRepository.findAllByItem_IdAndItem_OwnerAndStatus(1L, 1L, Status.APPROVED))
                .thenReturn(List.of(booking));
        when(commentRepository.findAllByItem_Id(1L))
                .thenReturn(List.of(comment));
        itemDto.setComments(List.of(comment));
        itemDto.setLastBooking(BookingMapper.bookingForItemDto(booking));


        ItemDto actualItem = itemService.getItemDtoById(1L, 1L);

        assertEquals(itemDto, actualItem);
    }

    /**
     * Gets item dto by id where status rejected test
     */

    @Test
    void getItemDtoById_WhereStatusRejected() {
        booking.setStatus(Status.REJECTED);
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));
        when(bookingRepository.findAllByItem_IdAndItem_OwnerAndStatus(1L, 1L, Status.APPROVED))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItem_Id(1L))
                .thenReturn(List.of(comment));
        itemDto.setComments(List.of(comment));

        ItemDto actualItem = itemService.getItemDtoById(1L, 1L);

        assertEquals(itemDto, actualItem);
    }

    /**
     * Gets item dto by id where status approved and get next booking test
     */

    @Test
    void getItemDtoById_WhereStatusApproved_AndGetNextBooking() {
        booking.setStart(LocalDateTime.now().plusDays(3));
        booking.setEnd(LocalDateTime.now().plusDays(5));
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));
        when(bookingRepository.findAllByItem_IdAndItem_OwnerAndStatus(1L, 1L, Status.APPROVED))
                .thenReturn(List.of(booking));
        when(commentRepository.findAllByItem_Id(1L))
                .thenReturn(List.of(comment));
        itemDto.setComments(List.of(comment));
        itemDto.setNextBooking(BookingMapper.bookingForItemDto(booking));

        ItemDto actualItem = itemService.getItemDtoById(1L, 1L);

        assertEquals(itemDto, actualItem);
    }

    /**
     * Gets item by id when item found return item test
     */

    @Test
    void getItemById_WhenItemFound_ReturnItem() {
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));

        Item actualItem = itemService.getItemById(1L);

        assertEquals(item, actualItem);
    }

    /**
     * Gets item by id when item not found return not found exception test
     */

    @Test
    void getItemById_WhenItemNotFound_ReturnNotFoundException() {
        when(itemRepository.findItemById(2L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.getItemById(2L));
    }

    /**
     * Update test
     */

    @Test
    void update() {
        ItemDto changedItemDto = new ItemDto(1L, "test1", "description1", false, 1L,
                null, null, null);
        Item expectedItem = ItemMapper.toItem(changedItemDto);
        expectedItem.setOwner(1L);
        itemDto.setId(1L);
        item.setOwner(1L);
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));
        when(itemRepository.save(item))
                .thenReturn(item);

        Item actualItem = itemService.update(changedItemDto, 1L, 1L);

        assertEquals(expectedItem, actualItem);
    }

    /**
     * Search when text not null test
     */

    @Test
    void search_WhenTextNotNull() {
        when(itemRepository
                .findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue("test",
                        "test", new OffsetBasedPageRequest(1, 1)))
                .thenReturn(List.of(item));

        List<Item> items = itemService.search("test", 1, 1);

        assertEquals(List.of(item), items);
    }

    /**
     * Search when text null test
     */

    @Test
    void search_WhenTextNull() {
        List<Item> items = itemService.search("", 1, 1);

        assertEquals(List.of(), items);
    }

    /**
     * Save commit where bookings not empty test
     */

    @Test
    void saveCommit_WhereBookingsNotEmpty() {
        CommentDto commentDto = new CommentDto("comment");
        LocalDateTime time = LocalDateTime.now();
        comment.setCreated(time);
        comment.setId(null);
        comment.setAuthorName("test");
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));
        when(bookingRepository.findBookingByBooker_IdAndItem_IdAndEndBefore(1, 1, time))
                .thenReturn(List.of(booking));
        when(commentRepository.save(comment))
                .thenReturn(comment);

        Comment actualComment = itemService.saveCommit(commentDto, 1L, 1L, time);

        assertEquals(comment, actualComment);
    }

    /**
     * Save commit where bookings empty test
     */

    @Test
    void saveCommit_WhereBookingsEmpty() {
        CommentDto commentDto = new CommentDto("comment");
        LocalDateTime time = LocalDateTime.now();
        comment.setCreated(time);
        comment.setId(null);
        comment.setAuthorName("test");
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));
        when(bookingRepository.findBookingByBooker_IdAndItem_IdAndEndBefore(1, 1, time))
                .thenReturn(List.of());

        assertThrows(BadRequestException.class, () -> itemService.saveCommit(commentDto, 1L, 1L, time));
    }
}