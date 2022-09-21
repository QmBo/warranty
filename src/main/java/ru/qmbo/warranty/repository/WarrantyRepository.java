package ru.qmbo.warranty.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.domain.Warranty;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarrantyRepository extends CrudRepository<Warranty, Integer> {
    @NonNull
    @Override
    List<Warranty> findAll();
    Optional<Warranty> findBySerialNumber(String serialNumber);
}
