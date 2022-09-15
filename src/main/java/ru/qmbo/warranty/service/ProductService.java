package ru.qmbo.warranty.service;

import ru.qmbo.warranty.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductByAbbreviature(String abbr);

    Optional<Product> getProductBySerialNumber(String serialNumber);

    List<Product> getAll();

    Optional<Product> save(Product product);

    void deleteProductById(Integer id);

    void updateProduct(Product product);
}
