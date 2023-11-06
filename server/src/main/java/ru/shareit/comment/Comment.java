package ru.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.shareit.item.Item;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type Comment
 */

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @ToString.Exclude
    private Item item;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime created;
}