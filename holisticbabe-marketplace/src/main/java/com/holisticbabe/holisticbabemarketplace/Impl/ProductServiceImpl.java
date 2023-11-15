package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Dtos.ProductDto;
import com.holisticbabe.holisticbabemarketplace.Models.*;
import com.holisticbabe.holisticbabemarketplace.Repositories.*;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductItemRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import com.holisticbabe.holisticbabemarketplace.Services.ProductItemService;
import com.holisticbabe.holisticbabemarketplace.Services.ProductService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    // private final FileService fileService;
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;
    private final MultimediaRepository multimediaRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private VariationOptionRepository variationOptionRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private ProductItemService productItemService;

    @Override
    public List<ProductDto> getAllProducts() {
        try {
            return productRepository.findAll().stream().map((p) -> modelMapper.map(p, ProductDto.class)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching all products.");
        }
    }

    @Override
    public Product getProductById(Long id) {
        try {
            return productRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the product by ID.");
        }
    }


    @Override
    public Product addProductWithItems(ProductRequest productRequest, Long userId, Long categoryId, List<String> variationOptionValues, List<MultipartFile> images) {
        try {
            _User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
            Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

            Product product = new Product();
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setDateCreated(LocalDateTime.now());
            product.setShortDescription(product.getShortDescription());
            product.setOwner(user);
            product.setCategory(category);

            List<ProductItem> productItems = new ArrayList<>();

            for (ProductItemRequest itemRequest : productRequest.getProductItems()) {
                ProductItem productItem = new ProductItem();
                productItem.setProduct(product);
                productItem.setSKU(itemRequest.getSKU());
                productItem.setQuantityInStock(itemRequest.getQuantityInStock());

                if (variationOptionValues != null && !variationOptionValues.isEmpty()) {
                    List<VariationOption> variationOptions = new ArrayList<>();
                    for (String optionValue : variationOptionValues) {
                        VariationOption option = variationOptionRepository.findByValue(optionValue);
                        if (option != null) {
                            variationOptions.add(option);
                        }
                    }
                    productItem.setVariationOptions(variationOptions);
                }

                productItems.add(productItem);
            }

            product.setProductItems(productItems);

            Product savedProduct = productRepository.save(product);
            saveProductImages(savedProduct, images);

            return productRepository.findById(savedProduct.getId_product()).orElse(null);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
             throw new RuntimeException("An error occurred while adding  product .");
        }
    }


    private void saveProductImages(Product product, List<MultipartFile> images) {
        images.forEach(image -> {
            String url = null;
            try {
                url = fileUploadService.uploadFile(image, "product-multimedia");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Multimedia imageToSave = new Multimedia();
            imageToSave.setName(image.getOriginalFilename());
            imageToSave.setType(image.getContentType());
            imageToSave.setUrl(url);
            imageToSave.setProduct(product);
            multimediaRepository.save(imageToSave);
        });
    }
    @Override
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setCategory(updatedProduct.getCategory());
            Product updatedProd = productRepository.save(existingProduct);

            log.info("Product with ID {} updated successfully.", id);

            return updatedProd;
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while updating the product.");
        }
    }

    @Override
    public Product addNewImage(Long productId, MultipartFile newImage) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            Multimedia newMultimedia = new Multimedia();
            String url = fileUploadService.uploadFile(newImage, "product-multimedia");
            newMultimedia.setName(newImage.getOriginalFilename());
            newMultimedia.setType(newImage.getContentType());
            newMultimedia.setUrl(url);
            newMultimedia.setProduct(product);
            multimediaRepository.save(newMultimedia);

            return product;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while adding a new image to the product.");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while deleting the product.");
        }
    }

    @Override
    public List<ProductDto> getNewProducts(int limit) {
        try {
            return productRepository.findTopNByOrderByDateCreatedDesc(limit).stream().map((p) -> modelMapper.map(p, ProductDto.class)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching new products.");
        }
    }

    @Override
    public List<ProductDto> getProductsInCategory(String name) {
        try {
            return productRepository.findByCategoryName(name).stream().map((p) -> modelMapper.map(p, ProductDto.class)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching products in the category.");
        }
    }

    @Override
    public boolean productExists(Long productId) {
        try {
            Product product = productRepository.findById(productId).orElse(null);
            return product != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while checking if the product exists.");
        }
    }

    @Override
    public List<Multimedia> getProductImages(Long productId) {
        try {
            Product product = modelMapper.map(getProductById(productId), Product.class);
            if (product != null) {
                return product.getImages();
            }
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching product images.");
        }
    }
/**
    @Override
    public List<ProductDto> filterProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        try {
            if (minPrice != null && maxPrice != null) {
                return productRepository.findByPriceBetween(minPrice, maxPrice).stream().map(p -> modelMapper.map(p,ProductDto.class)).toList();
            } else if (minPrice != null) {
                return productRepository.findByPriceGreaterThanEqual(minPrice).stream().map(p->modelMapper.map(p,ProductDto.class)).toList();
            } else if (maxPrice != null) {
                return productRepository.findByPriceLessThanEqual(maxPrice).stream().map(p->modelMapper.map(p,ProductDto.class)).toList();
            } else {
                return productRepository.findAll().stream().map(p->modelMapper.map(p,ProductDto.class)).toList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while filtering products by price.");
        }
    }

    @Override
    public List<ProductDto> getProductsByPrice(String sortDirection) {
        try {
            List<ProductDto> products = productRepository.findAll().stream().map(p->modelMapper.map(p,ProductDto.class)).toList();
            if ("ascending".equalsIgnoreCase(sortDirection)) {
                products.sort(Comparator.comparing(ProductDto::getPrice));
            } else if ("descending".equalsIgnoreCase(sortDirection)) {
                products.sort(Comparator.comparing(ProductDto::getPrice).reversed());
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching products by price.");
        }
    }

   @Override
    public List<ProductDto> getProductsBySize(String size) {
        try {
            return  productRepository.findBySize(size).stream().map(p->modelMapper.map(p,ProductDto.class)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching products by size.");
        }
    }*/

    @Override
    public ProductDto searchProductsByName(String name) {
        try {
            return modelMapper.map(productRepository.findByName(name),ProductDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while searching for products by name.");
        }
    }

    @Override
    public List<ProductShop> listProductShop() {
        return productRepository.listProductShop();
    }
}