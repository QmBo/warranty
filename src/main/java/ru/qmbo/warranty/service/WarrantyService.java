package ru.qmbo.warranty.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.domain.Warranty;
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
@Log4j2
public class WarrantyService {
    private final WarrantyRepository warrantyRepository;
    private final ProductService productService;

    /**
     * Instantiates a new Warranty service.
     *
     * @param warrantyRepository the warranty repository
     * @param productService     the product service
     */
    public WarrantyService(WarrantyRepository warrantyRepository, ProductService productService) {
        this.warrantyRepository = warrantyRepository;
        this.productService = productService;
    }

    /**
     * Gets warranty.
     * Try to find warranty and if not find then return warranty wis serial number and product by model abbreviature.
     *
     * @param serialNumber the serial number
     * @return the warranty
     */
    public Warranty getWarranty(final String serialNumber) {
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
        return result.get().setBuildDate(DataService.calcBuildDate(serialToUpper));
    }

    /**
     * Write warranty if not exist else return warranty from database.
     *
     * @param serialNumber the serial number
     * @param date         the date
     * @return the warranty
     */
    public Warranty writeWarrantyIfNotExist(final String serialNumber, final String date) {
        AtomicReference<Warranty> result = new AtomicReference<>(new Warranty());
        String serialToUpper = serialNumber.toUpperCase();
        this.warrantyRepository.findBySerialNumber(serialToUpper)
                .ifPresentOrElse(result::set, () -> {
                            Product product = this.productService.getProductBySerialNumber(serialToUpper)
                                    .orElse(null);
                            Warranty warranty = new Warranty()
                                    .setDate(DataService.parsDateOrCurrent(date))
                                    .setProduct(product)
                                    .setSerialNumber(serialToUpper);
                            if (product != null) {
                                result.set(this.warrantyRepository.save(warranty));
                            } else {
                                result.set(new Warranty().setSerialNumber(serialToUpper));
                            }
                        }
                );
        return result.get().setBuildDate(DataService.calcBuildDate(serialToUpper));
    }

}
