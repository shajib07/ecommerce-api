package com.atahar.ecommerceapi.controllers;

import com.atahar.ecommerceapi.dtos.ProductDto;
import com.atahar.ecommerceapi.entities.Category;
import com.atahar.ecommerceapi.entities.Product;
import com.atahar.ecommerceapi.mappers.ProductMapper;
import com.atahar.ecommerceapi.repositories.CategoryRepository;
import com.atahar.ecommerceapi.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    private Product testProduct;
    private ProductDto testProductDto;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();

        // Setup test data
        testCategory = new Category("Test Category");
        testCategory.setId((byte) 1);

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setCategory(testCategory);

        testProductDto = new ProductDto();
        testProductDto.setId(1L);
        testProductDto.setName("Test Product");
        testProductDto.setDescription("Test Description");
        testProductDto.setPrice(new BigDecimal("99.99"));
        testProductDto.setCategoryId((byte) 1);
    }

    @Test
    void getAllProducts_ShouldReturnOkStatus() throws Exception {
        when(productRepository.findAllWithCategory()).thenReturn(Collections.singletonList(testProduct));
        when(productMapper.toDto(any(Product.class))).thenReturn(testProductDto);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").value(99.99));
    }

    @Test
    void getAllProducts_WithCategoryId_ShouldReturnFilteredProducts() throws Exception {
        when(productRepository.findByCategoryId((byte) 1)).thenReturn(Collections.singletonList(testProduct));
        when(productMapper.toDto(any(Product.class))).thenReturn(testProductDto);

        mockMvc.perform(get("/products")
                .param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    void getProduct_WhenProductExists_ShouldReturnProduct() throws Exception {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productMapper.toDto(testProduct)).thenReturn(testProductDto);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void getProduct_WhenProductDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProduct_WhenCategoryDoesNotExist_ShouldReturnBadRequest() throws Exception {
        when(categoryRepository.findById((byte) 1)).thenReturn(Optional.empty());

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProductDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteProduct_WhenProductExists_ShouldDeleteProduct() throws Exception {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productRepository, times(1)).delete(testProduct);
    }

    @Test
    void deleteProduct_WhenProductDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound());

        verify(productRepository, never()).delete(any(Product.class));
    }
}
