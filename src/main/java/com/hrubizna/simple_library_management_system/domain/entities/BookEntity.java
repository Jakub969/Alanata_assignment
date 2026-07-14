package com.hrubizna.simple_library_management_system.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
    name = "books",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = "isbn"),
            @UniqueConstraint(columnNames = "title")
    }
)
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(
            name = "books_id_seq",
            sequenceName = "books_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private Integer publishedYear;

    @OneToMany(mappedBy = "book")
    private List<BookCopyEntity> copies = new ArrayList<>();
}
