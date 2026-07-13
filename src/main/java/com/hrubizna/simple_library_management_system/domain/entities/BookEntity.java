package com.hrubizna.simple_library_management_system.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(
            name = "books_id_seq",
            sequenceName = "books_id_seq",
            allocationSize = 1
    )
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private Integer publishedYear;
}
