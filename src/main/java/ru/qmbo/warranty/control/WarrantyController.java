package ru.qmbo.warranty.control;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.service.WarrantyService;

import java.security.Principal;

/**
 * InfoController class.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 30.08.2022
 */
@Controller
@RequestMapping("/warranty")
public class WarrantyController {

    public static final String PRODUCT_TYPE_ERROR = "Продукт этого типа не зарегистрирован. Создайте новый продукт чтобы добавить гарантию.";
    public static final String EMPTY_SERIAL_NUMBER_ERROR = "Серийный номер не может быть пустым!";
    public static final String ERROR = "error";
    public static final String WARRANTY = "warranty";
    private final WarrantyService warrantyService;

    /**
     * Instantiates a new Warranty controller.
     *
     * @param warrantyService the warranty service
     */
    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    /**
     * Gets information about sn.
     *
     * @param model        the model
     * @param serialNumber the serial number
     * @return the information about sn
     */
    @GetMapping
    public String getInformationAboutSN(Model model, @RequestParam(required = false) String serialNumber, Principal principal) {
        if (serialNumber != null && !serialNumber.isEmpty()) {
            model.addAttribute(WARRANTY, warrantyService.getWarranty(serialNumber));
        } else if (serialNumber != null) {
            model.addAttribute(ERROR, EMPTY_SERIAL_NUMBER_ERROR);
        }
        return "warranty/show";
    }


    /**
     * Warranty add form string.
     *
     * @return the string
     */
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('MODER') || hasAuthority('ADMIN')")
    public String getWarrantyAddPage() {
        return "warranty/add";
    }

    /**
     * Add product to db string.
     *
     * @param model        the model
     * @param serialNumber the serial number
     * @return the string
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('MODER') || hasAuthority('ADMIN')")
    public String addProductToDB(
            Model model, @RequestParam(name = "sn") String serialNumber,
            @RequestParam(required = false) String date) {
        if (serialNumber != null && !serialNumber.isEmpty()) {
            Warranty warranty = this.warrantyService.writeWarrantyIfNotExist(serialNumber, date);
            if (warranty.getId() == null) {
                model.addAttribute(ERROR, PRODUCT_TYPE_ERROR);
            }
            model.addAttribute(WARRANTY, warranty);
        } else {
            model.addAttribute(ERROR, EMPTY_SERIAL_NUMBER_ERROR);
        }
        return "warranty/add";
    }
}
