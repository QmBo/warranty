package ru.qmbo.warranty.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.repository.WarrantyRepository;
import ru.qmbo.warranty.service.ProductService;
import ru.qmbo.warranty.service.WarrantyService;
import ru.qmbo.warranty.utils.DateUtility;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ProductService class.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 02.09.2022
 */
@Service
@Log4j2
public class WarrantyServiceDefault implements WarrantyService {
    private final WarrantyRepository warrantyRepository;
    private final ProductService productService;

    public WarrantyServiceDefault(WarrantyRepository warrantyRepository, ProductService productService) {
        this.warrantyRepository = warrantyRepository;
        this.productService = productService;
    }

    @Override
    public Warranty getBySerialNumber(final String serialNumber) {
        AtomicReference<Warranty> result = new AtomicReference<>(new Warranty());
        String serialToUpper = serialNumber.toUpperCase();
        this.warrantyRepository.findBySerialNumber(serialToUpper)
                .ifPresentOrElse(
                        result::set,
                        () -> this.productService.getProductBySerialNumber(serialToUpper)
                                .ifPresentOrElse(
                                        product -> result.set(
                                                new Warranty().setProduct(product).setSerialNumber(serialToUpper)),
                                        () -> result.set(new Warranty().setSerialNumber(serialToUpper))
                                )
                );
        return result.get().setBuildDate(DateUtility.calcBuildDate(serialToUpper));
    }

    @Override
    public Warranty writeWarrantyIfNotExist(final String serialNumber, final String date) {
        AtomicReference<Warranty> result = new AtomicReference<>(new Warranty());
        String serialToUpper = serialNumber.toUpperCase();
        this.warrantyRepository.findBySerialNumber(serialToUpper)
                .ifPresentOrElse(result::set, () -> {
                            Product product = this.productService.getProductBySerialNumber(serialToUpper)
                                    .orElse(null);
                            Warranty warranty = new Warranty()
                                    .setDate(date)
                                    .setProduct(product)
                                    .setSerialNumber(serialToUpper);
                            if (product != null) {
                                result.set(this.warrantyRepository.save(warranty));
                            } else {
                                result.set(new Warranty().setSerialNumber(serialToUpper));
                            }
                        }
                );
        return result.get().setBuildDate(DateUtility.calcBuildDate(serialToUpper));
    }

    @Override
    public List<Warranty> findAll() {
        return this.warrantyRepository.findAll();
    }

    @Override
    public boolean updateWarranty(Warranty warranty) {
        AtomicReference<Warranty> result = new AtomicReference<>();
        warranty.setSerialNumber(warranty.getSerialNumber().toUpperCase());
        this.productService.getProductBySerialNumber(warranty.getSerialNumber())
                .ifPresent(
                        product -> this.warrantyRepository
                                .findBySerialNumber(
                                        warranty
                                                .setProduct(product)
                                                .getSerialNumber()
                                )
                                .ifPresentOrElse(
                                        findWarranty -> {
                                            if (findWarranty.getId().equals(warranty.getId())) {
                                                result.set(this.warrantyRepository.save(warranty));
                                            }
                                        },
                                        () -> result.set(this.warrantyRepository.save(warranty)))
                );
        return result.get() != null;
    }

    @Override
    public void deleteWarrantyById(Integer id) {
        this.warrantyRepository.deleteById(id);
    }
}
