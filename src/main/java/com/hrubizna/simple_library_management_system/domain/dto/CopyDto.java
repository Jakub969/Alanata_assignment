package com.hrubizna.simple_library_management_system.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CopyDto {

    private Long id;

    @NotNull(message = "Availability must be specified")
    private Boolean available;
}
