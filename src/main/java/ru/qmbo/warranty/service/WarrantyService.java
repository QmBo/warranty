package ru.qmbo.warranty.service;

import org.springframework.stereotype.Service;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.repository.ProductRepository;
import ru.qmbo.warranty.repository.WarrantyRepository;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ProductService class.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 02.09.2022
 */
@Service
public class WarrantyService {
    private final WarrantyRepository warrantyRepository;
    private final ProductRepository productRepository;

    public WarrantyService(WarrantyRepository warrantyRepository, ProductRepository productRepository) {
        this.warrantyRepository = warrantyRepository;
        this.productRepository = productRepository;
    }

    public Warranty getWarranty(String serialNumber) {
        AtomicReference<Warranty> result = new AtomicReference<>(new Warranty());
        String abb = serialNumber.substring(2, 6);
        this.warrantyRepository.findBySerialNumber(serialNumber)
                .ifPresentOrElse(
                        result::set,
                        () -> productRepository.findByAbbreviature(abb)
                                .ifPresentOrElse(product -> result.set(new Warranty()
                                        .setProduct(product)
                                        .setSerialNumber(serialNumber)),
                                        () -> result.set(new Warranty().setSerialNumber(serialNumber))
                                )
                );
        return result.get();
    }
}
