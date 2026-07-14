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
    @SequenceGenerator(
            name = "copies_id_seq",
            sequenceName = "copies_id_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @Column(nullable = false)
    private boolean available = true;
}
