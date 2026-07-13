package com.hrubizna.simple_library_management_system.services.implementation;

import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import com.hrubizna.simple_library_management_system.repositories.CopyRepository;
import com.hrubizna.simple_library_management_system.services.CopyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyServiceImplementation implements CopyService {
    private final CopyRepository copyRepository;

    public CopyServiceImplementation(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }


    @Override
    public List<BookCopyEntity> findById(Long id) {
        return copyRepository.findByBookId(id);
    }

    @Override
    public BookCopyEntity save(BookCopyEntity bookCopyEntity) {
        return copyRepository.save(bookCopyEntity);
    }

    @Override
    public BookCopyEntity update(BookCopyEntity bookCopyEntity) {
        return copyRepository.findById(bookCopyEntity.getId()).map(existingCopy -> {
            existingCopy.setAvailable(bookCopyEntity.isAvailable());
            return copyRepository.save(existingCopy);
        }).orElseThrow(() -> new RuntimeException("Copy of the book does not exist"));
    }
}
