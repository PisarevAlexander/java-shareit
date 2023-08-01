package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.OffsetBasedPageRequest;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    public void setUp() {
        user = new User(1L, "test", "test@gmail.com");
        itemDto = new ItemDto(null, "test", "description", true, 1L,
                null, null, null);
        item = new Item(null, "test", "description", true, 1L, 1L);
        booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now(), null, user, Status.APPROVED);
        comment = new Comment(1L, "comment", item, "vasia", LocalDateTime.now());
    }

    @Test
    void create() {
        when(userService.getById(1L))
                .thenReturn(user);
        when(itemRepository.save(item))
                .thenReturn(item);

        Item actualItem = itemService.create(itemDto, 1L);

        assertEquals(item, actualItem);
    }

    @Test
    void getItemDtoById() {
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

    @Test
    void getItemById_WhenItemFound_ReturnItem() {
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));

        Item actualItem = itemService.getItemById(1L);

        assertEquals(item, actualItem);
    }

    @Test
    void getItemById_WhenItemNotFound_ReturnNotFoundException() {
        when(itemRepository.findItemById(2L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.getItemById(2L));
    }

    @Test
    void getAll() {
        item.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDateTime.now().plusDays(1));
        booking.setEnd(LocalDateTime.now().plusDays(5));
        comment.setItem(item);
        when(itemRepository.findAllByOwner(1L, new OffsetBasedPageRequest(1, 1)))
                .thenReturn(List.of(item));
        when(bookingRepository.findAllByItem_OwnerAndStatus(1L, Status.APPROVED))
                .thenReturn(List.of(booking));
        when(commentRepository.findAll())
                .thenReturn(List.of(comment));
        itemDto.setId(1L);
        itemDto.setComments(List.of(comment));
        itemDto.setNextBooking(BookingMapper.bookingForItemDto(booking));

        List<ItemDto> items = itemService.getAll(1L, 1, 1);

        assertEquals(List.of(itemDto), items);
    }

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

    @Test
    void search() {
        when(itemRepository
                .findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue("test",
                        "test", new OffsetBasedPageRequest(1, 1)))
                .thenReturn(List.of(item));

        List<Item> items = itemService.search("test", 1, 1);

        assertEquals(List.of(item), items);
    }

    @Test
    void saveCommit() {
        CommentDto commentDto = new CommentDto("comment");
        LocalDateTime time = LocalDateTime.now();
        comment.setCreated(time);
        comment.setId(null);
        comment.setAuthorName("test");
        when(itemRepository.findItemById(1L))
                .thenReturn(Optional.of(item));
        when(bookingRepository.findBookingByBooker_IdAndItem_IdAndAndEndBefore(1, 1, time))
                .thenReturn(List.of(booking));
        when(commentRepository.save(comment))
                .thenReturn(comment);

        Comment actualComment = itemService.saveCommit(commentDto, 1L, 1L, time);

        assertEquals(comment, actualComment);
    }
}