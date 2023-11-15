package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.ProductItem;
import com.holisticbabe.holisticbabemarketplace.Models.VariationOption;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductItemRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.VariationOptionRepository;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductItemRequest;
import com.holisticbabe.holisticbabemarketplace.Services.ProductItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductItemServiceImpl implements ProductItemService {
    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private VariationOptionRepository variationOptionRepository;
    @Autowired
    private ProductRepository productRepository;


    public List<ProductItem> getAllProductItems()  {
        return productItemRepository.findAll();
    }

    @Override
    public ProductItem createProductItem(ProductItem productItem, Long productId, List<String> variationOptionValues) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        productItem.setProduct(product);
        List<VariationOption> variationOptions = new ArrayList<>();
        for (String option : variationOptionValues) {
            VariationOption variationOption = variationOptionRepository.findByValue(option);

            if (variationOption != null) {
                variationOptions.add(variationOption);
            } else {
                throw new RuntimeException("error");
            }
        }

        productItem.setVariationOptions(variationOptions);
        return productItemRepository.save(productItem);
    }
    @Override
    public List<ProductItem> getProductItemsByProduct(Product product) {
        return productItemRepository.findByProduct(product);
    }
    @Override
    public ProductItem updateProductItem(Long productItemId, ProductItemRequest updatedProductItemRequest,List<String>variationOptionValues) {
        ProductItem existingProductItem = productItemRepository.findById(productItemId)
                .orElseThrow(() -> new EntityNotFoundException("ProductItem not found"));

        existingProductItem.setSKU(updatedProductItemRequest.getSKU());
        existingProductItem.setQuantityInStock(updatedProductItemRequest.getQuantityInStock());
        existingProductItem.setPrice(updatedProductItemRequest.getPrice());
        List<VariationOption> existingOptions = existingProductItem.getVariationOptions();

        List<VariationOption> updatedOptions = new ArrayList<>();
        for (String optionValue : variationOptionValues) {
            VariationOption option = variationOptionRepository.findByValue(optionValue);
            if (option != null && existingOptions.contains(option)) {
                updatedOptions.add(option);
            }
        }
        existingOptions.removeIf(option -> !updatedOptions.contains(option));
        existingProductItem.setVariationOptions(updatedOptions);

        return productItemRepository.save(existingProductItem);
    }

    @Override
    public long countProductItemsByProduct(Long productId) {
        return productItemRepository.countProductItems(productId);
    }
    @Override
    public void deleteProductItem(Long id) {
        productItemRepository.deleteById(id);
    }

    public ProductItem getProductItemById(Long id) {
        return productItemRepository.findById(id).orElse(null);
    }
}
