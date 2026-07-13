package com.hrubizna.simple_library_management_system.mappers.implementation;

import com.hrubizna.simple_library_management_system.domain.dto.CopyDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import com.hrubizna.simple_library_management_system.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CopyMapperImplementation implements Mapper<BookCopyEntity, CopyDto> {

    private final ModelMapper modelMapper;

    public CopyMapperImplementation(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CopyDto mapTo(BookCopyEntity bookCopyEntity) {
        return modelMapper.map(bookCopyEntity, CopyDto.class);
    }

    @Override
    public BookCopyEntity mapFrom(CopyDto copyDto) {
        return modelMapper.map(copyDto, BookCopyEntity.class);
    }
}
