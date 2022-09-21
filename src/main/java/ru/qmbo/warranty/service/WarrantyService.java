package ru.qmbo.warranty.service;

import ru.qmbo.warranty.domain.Warranty;

import java.util.List;

/**
 * WarrantyService class.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 19.09.2022
 */
public interface WarrantyService {

    /**
     * Gets warranty by serial number.
     *
     * @param serialNumber the serial number
     * @return the by serial number
     */
    Warranty getBySerialNumber(String serialNumber);

    /**
     * Write warranty if not exist warranty.
     *
     * @param serialNumber the serial number
     * @param date         the date
     * @return the warranty
     */
    Warranty writeWarrantyIfNotExist(String serialNumber, String date);

    /**
     * Find all warranties.
     *
     * @return the list of warranties
     */
    List<Warranty> findAll();

    /**
     * Update warranty.
     *
     * @param warranty the warranty
     * @return the warranty
     */
    boolean updateWarranty(Warranty warranty);

    /**
     * Delete warranty by id.
     *
     * @param id the id
     */
    void deleteWarrantyById(Integer id);
}
