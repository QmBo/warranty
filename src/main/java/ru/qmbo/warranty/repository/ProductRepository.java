package ru.qmbo.warranty.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.qmbo.warranty.domain.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @NonNull
    @Override
    List<Product> findAll();
    Optional<Product> findByAbbreviature(String abbr);
    Optional<Product> findByModelName(String abbr);
}
