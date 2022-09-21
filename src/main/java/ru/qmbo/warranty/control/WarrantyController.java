package ru.qmbo.warranty.control;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.service.ProductService;
import ru.qmbo.warranty.service.WarrantyService;
import ru.qmbo.warranty.utils.ControllerUtils;

import javax.validation.Valid;
import java.util.Calendar;

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

    private static final String PRODUCT_TYPE_ERROR = "Продукт этого типа не зарегистрирован. Создайте новый продукт чтобы добавить гарантию.";
    private static final String EMPTY_SERIAL_NUMBER_ERROR = "Серийный номер не может быть пустым!";
    private static final String ERROR_DESCRIPTION = "Проверьте серийный номер. Возможно такого продукта не существует.";
    private static final String PRODUCTS = "products";
    private static final String ERROR = "error";
    private static final String WARRANTY = "warranty";
    private static final String WARRANTIES = "warranties";
    private static final String TIME_ZONE = "timeZone";
    private final WarrantyService warrantyService;
    private final ProductService productService;

    /**
     * Instantiates a new Warranty controller.
     *
     * @param warrantyService the warranty service
     * @param productService  the product service
     */
    public WarrantyController(WarrantyService warrantyService, ProductService productService) {
        this.warrantyService = warrantyService;
        this.productService = productService;
    }

    /**
     * Gets information about sn.
     *
     * @param model        the model
     * @param serialNumber the serial number
     * @return the information about sn
     */
    @GetMapping
    public String getInformationAboutSN(Model model, @RequestParam(required = false) String serialNumber) {
        if (serialNumber != null && !serialNumber.isEmpty()) {
            model.addAttribute(WARRANTY, warrantyService.getBySerialNumber(serialNumber));
        } else if (serialNumber != null) {
            model.addAttribute(ERROR, EMPTY_SERIAL_NUMBER_ERROR);
        }
        return "warranty/show";
    }


    /**
     * Gets warranty add page.
     *
     * @return the warranty add page
     */
    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public String getWarrantyAddPage() {
        return "warranty/add";
    }

    /**
     * Add product to db string.
     *
     * @param model        the model
     * @param serialNumber the serial number
     * @param date         the date
     * @return the string
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public String addProductToDB(
            Model model, @RequestParam(name = "sn") String serialNumber,
            @RequestParam(required = false) String date) {
        if (serialNumber != null && !serialNumber.isEmpty()) {
            Warranty warranty = this.warrantyService.writeWarrantyIfNotExist(serialNumber, date);
            if (warranty.getId() == null) {
                model.addAttribute(ERROR, PRODUCT_TYPE_ERROR);
            }
            model.addAttribute(WARRANTY, warranty);
            model.addAttribute(TIME_ZONE, Calendar.getInstance().getTimeZone().getID());
        } else {
            model.addAttribute(ERROR, EMPTY_SERIAL_NUMBER_ERROR);
        }
        return "warranty/add";
    }

    /**
     * Gets all warranty page.
     *
     * @param model the model
     * @return the warranty all page
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public String getWarrantyAllPage(Model model) {
        model.addAttribute(WARRANTIES, this.warrantyService.findAll());
        return "warranty/all";
    }

    /**
     * Gets warranty edit page.
     *
     * @param model    the model
     * @param warranty the warranty
     * @return the warranty edit page
     */
    @GetMapping("/edit/{warranty}")
    public String getWarrantyEditPage(Model model, @PathVariable Warranty  warranty) {
        model.addAttribute(WARRANTY, warranty);
        model.addAttribute(PRODUCTS, this.productService.getAll());
        model.addAttribute(TIME_ZONE, Calendar.getInstance().getTimeZone().getID());
        return "warranty/edit";
    }


    /**
     * Update warranty.
     *
     * @param warranty      the warranty
     * @param date          the date
     * @param bindingResult the binding result
     * @param model         the model
     * @return the warranty edit page
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public String updateWarranty(
            @Valid Warranty warranty, @RequestParam(name = "newDate") String date, BindingResult bindingResult, Model model) {
        warranty.setDate(date);
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute(WARRANTY, warranty);
            model.addAttribute(TIME_ZONE, Calendar.getInstance().getTimeZone().getID());
            return "warranty/edit";
        }
        boolean updateOk = this.warrantyService.updateWarranty(warranty);
        if (!updateOk) {
            model.addAttribute(ERROR, ERROR_DESCRIPTION);
            model.addAttribute(WARRANTY, warranty);
            model.addAttribute(TIME_ZONE, Calendar.getInstance().getTimeZone().getID());
            return "warranty/edit";
        }
        return "redirect:/warranty/edit/" + warranty.getId();
    }

    /**
     * Delete warranty.
     *
     * @param id the id
     * @return the warranties page
     */
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    @PostMapping("/delete")
    public String deleteWarranty(@RequestParam Integer id) {
        this.warrantyService.deleteWarrantyById(id);
        return "redirect:/warranty/all";
    }
}
