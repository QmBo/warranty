package ru.qmbo.warranty.service;

import ru.qmbo.warranty.domain.Warranty;

public interface WarrantyService {
    Warranty getWarranty(String serialNumber);

    Warranty writeWarrantyIfNotExist(String serialNumber, String date);
}
