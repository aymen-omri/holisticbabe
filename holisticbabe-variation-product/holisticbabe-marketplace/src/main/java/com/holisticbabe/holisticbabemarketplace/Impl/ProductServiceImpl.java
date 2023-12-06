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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private final PromotionRepository promotionRepository;

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
    public Product addProductWithItems(ProductRequest productRequest/*Long userId*/, Long categoryId) {
        try {
            ///_User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
            Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

            Product product = new Product();
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setShortDescription(productRequest.getShortDescription());
            product.setQuantityInStock(productRequest.getQuantityInStock());
            product.setDateCreated(LocalDate.now());
            product.setDateProduction(productRequest.getDateProduction());
            product.setSku(productRequest.getSku());
            product.setIsApproved(0);
           // product.setOwner(user);
            product.setCategory(category);
             Product savedProduct=productRepository.save(product);
            List<ProductItem> productItems = new ArrayList<>();
            for (ProductItemRequest itemRequest : productRequest.getProductItems()) {
                ProductItem productItem = new ProductItem();
                productItem.setSKU(itemRequest.getSku());
                productItem.setPrice(itemRequest.getPrice());
                productItem.setQuantityInStock(itemRequest.getQuantity());
                List<VariationOption> variationOptions = new ArrayList<>();
                for(String option :itemRequest.getOptions()){
                    VariationOption variationOption=variationOptionRepository.findByValue(option);
                    variationOptions.add(variationOption);
                }
                productItem.setVariationOptions(variationOptions);
                productItem.setProduct(savedProduct);
                productItemRepository.save(productItem);
            }
            return productRepository.findById(savedProduct.getId_product()).orElse(null);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
             throw new RuntimeException("An error occurred while adding  product .");
        }
    }

    @Override
    public Product saveProductImages(Long productId, List<MultipartFile> images) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            for (MultipartFile image : images) {
                String url = null;
                try {
                    url = fileUploadService.uploadFile(image, "product-multimedia");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Multimedia newMultimedia = new Multimedia(); // Create a new Multimedia object for each image
                newMultimedia.setName(image.getOriginalFilename());
                newMultimedia.setType(image.getContentType());
                newMultimedia.setUrl(url);
                newMultimedia.setProduct(product); // Set the product for the multimedia

                multimediaRepository.save(newMultimedia);
            }

            return product;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while adding new images to the product.");
        }
    }



    @Override
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct,Long CategoryId,Long PromotionId) {
        try {
            Category existingCategory=categoryRepository.findById(CategoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            Promotion existingPromotion=promotionRepository.findById(PromotionId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setShortDescription(updatedProduct.getShortDescription());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPromotions(existingPromotion);
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantityInStock(updatedProduct.getQuantityInStock());
            existingProduct.setIsApproved(updatedProduct.getIsApproved());
            existingProduct.setDateCreated(LocalDate.now());
            existingProduct.setSku(updatedProduct.getSku());
            existingProduct.setDateProduction(updatedProduct.getDateProduction());
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

    @Transactional
    public void deleteProduct(Long productId) {
        multimediaRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
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
        List<Object[]> results = productRepository.listProductShop();
        return results.stream()
                .map(obj -> new ProductShop(
                        (Long) obj[0],
                        (String) obj[1],
                        (BigDecimal) obj[2],
                        (String) obj[3],
                        (double) obj[4],
                        (BigDecimal) obj[5],
                        (String) obj[6],
                        (Integer) obj[7]
                ))
                .collect(Collectors.toList());
    }

}