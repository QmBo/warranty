package ru.qmbo.warranty.service.impl;

import org.springframework.stereotype.Service;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.repository.ProductRepository;
import ru.qmbo.warranty.service.ProductService;

import java.util.*;

/**
 * ProductService class.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 13.09.2022
 */
@Service
public class ProductServiceDefault implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Instantiates a new Product service.
     *
     * @param productRepository the product repository
     */
    public ProductServiceDefault(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Gets product abbreviature.
     *
     * @param abbr the abbr
     * @return the product abbreviature
     */
    @Override
    public Optional<Product> getProductByAbbreviature(final String abbr) {
        return this.productRepository.findByAbbreviature(abbr.toUpperCase());
    }

    /**
     * Gets product by serial number.
     *
     * @param serialNumber the serial number
     * @return the product serial number
     */
    @Override
    public Optional<Product> getProductBySerialNumber(final String serialNumber) {
        String abb = serialNumber.substring(2, 6);
        return this.getProductByAbbreviature(abb);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @Override
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    /**
     * Save.
     *
     * @param product the product
     * @return the optional
     */
    @Override
    public Optional<Product> save(Product product) {
        return this.productRepository.findByAbbreviature(product.getAbbreviature())
                .or(() -> this.productRepository.findByModelName(product.getModelName())
                        .or(() -> {
                            this.productRepository.save(this.productToUpperCase(product));
                            return Optional.empty();
                        })
                );
    }

    /**
     * Delete product by id.
     *
     * @param id the id
     */
    @Override
    public void deleteProductById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must bee not null.");
        }
        this.productRepository.delete(new Product().setId(id));
    }

    /**
     * Update product.
     *
     * @param product the product
     */
    @Override
    public void updateProduct(Product product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("Id must bee not null.");
        }
        this.productRepository.save(this.productToUpperCase(product));
    }

    private Product productToUpperCase(Product product) {
        return product
                .setModelName(product.getModelName().toUpperCase())
                .setAbbreviature(product.getAbbreviature().toUpperCase());
    }
}
