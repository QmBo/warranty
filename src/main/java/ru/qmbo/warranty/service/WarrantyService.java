package ru.qmbo.warranty.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.repository.WarrantyRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        return result.get().setBuildDate(this.calcBuildDate(serialToUpper));
    }

    private Date calcBuildDate(final String serialNumber) {
        Date result = null;
        try {
            int year = Integer.parseInt(serialNumber.substring(9, 11));
            int week = Integer.parseInt(serialNumber.substring(11, 13));
            Calendar calendar = new GregorianCalendar();
            calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.YEAR, year);
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if (calendar.compareTo(Calendar.getInstance()) <= 0) {
                result = calendar.getTime();
            }
        } catch (NumberFormatException e) {
            log.warn("Convert serial number to build date error. {}", e.getMessage());
        }
        return result;
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
                                    .setDate(this.parsDateOrCurrent(date))
                                    .setProduct(product)
                                    .setSerialNumber(serialToUpper);
                            if (product != null) {
                                result.set(this.warrantyRepository.save(warranty));
                            } else {
                                result.set(new Warranty().setSerialNumber(serialToUpper));
                            }
                        }
                );
        Date buildDate = this.calcBuildDate(serialToUpper);
        if (buildDate != null) {
            result.set(result.get().setBuildDate(buildDate));
        }
        return result.get();
    }

    private Date parsDateOrCurrent(final String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final Date result = calendar.getTime();
        if (date != null && !date.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                result.setTime(dateFormat.parse(date).getTime());
            } catch (ParseException e) {
                log.error("Data parsing error. {}", e.getMessage());
            }
        }
        return result;
    }
}
