package com.example.demo.controller;

import com.example.demo.dto.CommonResponse;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ProductReturnDto;
import com.example.demo.dto.UpdateStockDto;
import com.example.demo.entity.ProductEntity;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<ProductEntity> getProducts(@RequestParam(value = "maxPrice", defaultValue = "0") long maxPrice, @RequestParam(value = "InStock", defaultValue = "0") boolean isInStock) {
        return productService.fetch(maxPrice, isInStock);
    }

    @GetMapping("{id}")
    public ProductReturnDto getProduct(@PathVariable("id") String id) {
        ProductEntity entity = productService.getById(Long.parseLong(id));
        ProductReturnDto result = new ProductReturnDto(); //atau menggunakan constructor
        result.setName(entity.getName());
        result.setPrice(entity.getPrice());
        result.setStock(entity.getStock());
        result.setDesc("Ini Deskripsi");
        return result;
    }

    @PostMapping("")
    public ProductEntity addProduct(@RequestBody ProductDto productDto) {
        return productService.add(productDto);
    }

    @PutMapping("/stock")
    public ProductEntity updateStock(@RequestBody UpdateStockDto request) {
        return productService.updateStock(request);
    }

    @DeleteMapping("{id}")
    public CommonResponse deleteProduct(@PathVariable("id") String id) {
        productService.delete(Long.parseLong(id));
        return new CommonResponse("Successfully delete product");
    }
}
