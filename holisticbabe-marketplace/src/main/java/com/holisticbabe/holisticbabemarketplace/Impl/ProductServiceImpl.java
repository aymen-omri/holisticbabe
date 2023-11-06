package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Repositories.CategoryRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import com.holisticbabe.holisticbabemarketplace.Services.ProductService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
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
    private final CategoryRepository categoryRepository ;

    @Override
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching all products.");
        }
    }

    @Override
    public Product getProductById(Long id) {
        try {
            return productRepository.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the product by ID.");
        }
    }

    @Override
    public Product createProduct(String name , String description , BigDecimal price, Long id_user , Long id_category , List<MultipartFile> images) {
        _User user = userRepository.findById(id_user).orElse(null);
        Category category = categoryRepository.findById(id_category).orElse(null);
        if(user == null || category == null){
            return null ;
        }
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setOwner(user);
        product.setCategory(category);
        Product productToSave = productRepository.save(product);

        images.forEach(image -> {
            String url = null;
            try {
                url = fileUploadService.uploadFile(image , "product-multimedia");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Multimedia imageToSave = new Multimedia();
            imageToSave.setName(image.getOriginalFilename());
            imageToSave.setType(image.getContentType());
            imageToSave.setUrl(url);
            imageToSave.setProduct(productToSave);
            multimediaRepository.save(imageToSave);
        });
        return productRepository.findById(productToSave.getId_product()).orElse(null);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantityInStock(updatedProduct.getQuantityInStock());
            existingProduct.setColor(updatedProduct.getColor());
            existingProduct.setSize(updatedProduct.getSize());
            existingProduct.setMaterial(updatedProduct.getMaterial());
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
    public Product addNewImage(Long productId, MultipartFile newImage) throws IOException {
        try {
            Product product = getProductById(productId);
            // fileService.uploadFile(newImage);
            Multimedia newMultimedia = new Multimedia();
            String url = fileUploadService.uploadFile(newImage, "product-multimedia");
            newMultimedia.setName(newImage.getOriginalFilename());
            newMultimedia.setType(newImage.getContentType());
            newMultimedia.setUrl(url);
            newMultimedia.setProduct(product);
            multimediaRepository.save(newMultimedia);
            return productRepository.findById(productId).get();
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
    public List<Product> getNewProducts(int limit) {
        try {
            return productRepository.findTopNByOrderByDateCreatedDesc(limit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching new products.");
        }
    }

    @Override
    public List<Product> getProductsInCategory(String name) {
        try {
            return productRepository.findByCategoryName(name);
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
            Product product = getProductById(productId);
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
    public List<Product> filterProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        try {
            if (minPrice != null && maxPrice != null) {
                return productRepository.findByPriceBetween(minPrice, maxPrice);
            } else if (minPrice != null) {
                return productRepository.findByPriceGreaterThanEqual(minPrice);
            } else if (maxPrice != null) {
                return productRepository.findByPriceLessThanEqual(maxPrice);
            } else {
                return productRepository.findAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while filtering products by price.");
        }
    }

    @Override
    public List<Product> getProductsByPrice(String sortDirection) {
        try {
            List<Product> products = productRepository.findAll();
            if ("ascending".equalsIgnoreCase(sortDirection)) {
                products.sort(Comparator.comparing(Product::getPrice));
            } else if ("descending".equalsIgnoreCase(sortDirection)) {
                products.sort(Comparator.comparing(Product::getPrice).reversed());
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching products by price.");
        }
    }

    @Override
    public List<Product> getProductsBySize(String size) {
        try {
            return productRepository.findBySize(size);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching products by size.");
        }
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        try {
            return productRepository.findByName(name);
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
