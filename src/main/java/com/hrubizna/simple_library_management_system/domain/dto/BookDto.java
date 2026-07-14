package com.hrubizna.simple_library_management_system.domain.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(
            regexp = "^(97(8|9))?[-0-9]{10,17}$",
            message = "ISBN format is invalid"
    )
    private String isbn;

    @NotNull(message = "Published year is required")
    @Min(value = 1000, message = "Published year must be valid")
    @Max(value = 9999, message = "Published year must be valid")
    private Integer publishedYear;

}
