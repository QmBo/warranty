package ru.qmbo.warranty.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.qmbo.warranty.domain.Warranty;

import java.util.Optional;

@Repository
public interface WarrantyRepository extends CrudRepository<Warranty, Integer> {
    Optional<Warranty> findBySerialNumber(String serialNumber);
}
