package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UpdateStockDto;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity add(ProductDto request) {
        ProductEntity product = new ProductEntity();
        product.setName(request.getProductName());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());

        return productRepository.save(product);
    }

    public List<ProductEntity> fetch(long maxPrice, boolean isInStock){
        if(maxPrice != 0 && !isInStock) {
            return fetchAllUnderMaxPrice(maxPrice);
        } else if(maxPrice != 0 && isInStock){
            return fetchAllUnderMaxPriceAndInStock(maxPrice);
        } else if(maxPrice == 0 && !isInStock){
            return fetchAll();
        } else {
            return fetchAllInStock();
        }
    }

    public List<ProductEntity> fetchAll(){
        return (List<ProductEntity>) productRepository.findAll();
    }

    public List<ProductEntity> fetchAllInStock(){
        return productRepository.findByStockGreaterThan(0);
    }

    public List<ProductEntity> fetchAllUnderMaxPrice(long maxPrice){
        return productRepository.findByPriceLessThanEqual(maxPrice);
    }

    public List<ProductEntity> fetchAllUnderMaxPriceAndInStock(long maxPrice){
        return productRepository.findByStockGreaterThanAndPriceLessThanEqual(0, maxPrice);
    }

    public void delete(long id){
        productRepository.deleteById(id);
    }

    public ProductEntity getById(long id){
        return productRepository.findById(id).orElse(new ProductEntity());
    }

    public ProductEntity updateStock(UpdateStockDto request) {
        ProductEntity product = getById(request.getId());

        long currentStock = product.getStock();
        long updatedStock = currentStock + request.getNumberOfStock();
        product.setStock(updatedStock);

        return productRepository.save(product);
    }
}
