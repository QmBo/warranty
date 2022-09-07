package ru.qmbo.warranty.service;

import org.springframework.stereotype.Service;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.repository.ProductRepository;

import java.util.Optional;

/**
 * ProductService class.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 05.09.2022
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Instantiates a new Product service.
     *
     * @param productRepository the product repository
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Gets product abbreviature.
     *
     * @param abbr the abbr
     * @return the product abbreviature
     */
    public Optional<Product> getProductByAbbreviature(final String abbr) {
        return this.productRepository.findByAbbreviature(abbr.toUpperCase());
    }

    /**
     * Gets product by serial number.
     *
     * @param serialNumber the serial number
     * @return the product serial number
     */
    public Optional<Product> getProductBySerialNumber(final String serialNumber) {
        String abb = serialNumber.substring(2, 6);
        return this.getProductByAbbreviature(abb);
    }
}
