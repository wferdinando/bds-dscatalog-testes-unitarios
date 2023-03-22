package com.devsuperior.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@WebMvcTest(ProductResource.class)
public class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    private PageImpl<ProductDTO> page;
    private ProductDTO productDTO;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));

        existingId = 1L;
        nonExistingId = 2L;

        when(service.findAllPaged(any())).thenReturn(page);

        when(service.findById(existingId)).thenReturn(productDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnProductWhenIdExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/products/{id}", existingId)
        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId)
        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
