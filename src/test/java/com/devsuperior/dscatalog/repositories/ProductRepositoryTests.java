package com.devsuperior.dscatalog.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTests {
    
    @Autowired
    private ProductRepository repository;
    
    private  long existingId;
    private  long nonExistingId;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 1000L;
    }


    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){
        Product product = new Produ
    }

    @Test
    public void deleteShoudDeleteObjectWhenIdExists(){
       
        repository.deleteById(existingId);
        Optional<Product> result = repository.findById(existingId);
        assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyDataAccessExceptionWhenIdDoesNotExist(){
        assertThrows(EmptyResultDataAccessException.class, () -> {
           
            repository.deleteById(nonExistingId);
        });
    }
}