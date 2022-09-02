package ru.qmbo.warranty.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.qmbo.warranty.domain.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findByAbbreviature(String abbr);
}
