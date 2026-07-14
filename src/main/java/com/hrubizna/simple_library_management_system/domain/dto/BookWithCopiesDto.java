package com.hrubizna.simple_library_management_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookWithCopiesDto {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
    private List<CopyDto> copies;
}
