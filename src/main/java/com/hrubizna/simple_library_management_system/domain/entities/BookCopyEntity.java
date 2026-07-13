package com.hrubizna.simple_library_management_system.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "copies")
public class BookCopyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "copies_id_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private BookEntity bookId;

    private boolean available;
}
