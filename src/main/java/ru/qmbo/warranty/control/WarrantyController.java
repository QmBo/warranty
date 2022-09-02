package ru.qmbo.warranty.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.service.WarrantyService;

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

    private final WarrantyService warrantyService;

    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    @GetMapping
    public String getInformationAboutSN(Model model, @RequestParam(name = "sn", required = false) String serialNumber) {
        if (serialNumber != null && !serialNumber.isEmpty()) {
            model.addAttribute("warranty", warrantyService.getWarranty(serialNumber));
        }
        return "warranty/show";
    }


    @GetMapping("/add")
    public String warrantyAddForm() {
        return "warranty/add";
    }

    @PostMapping("/add")
    public String addProductToDB(Model model, @RequestParam(name = "sn") String serialNumber) {
        if (serialNumber != null && !serialNumber.isEmpty()) {
            Product product = new Product();
            product.setName("Dune HD Magic 4K");
            model.addAttribute("warranty", new Warranty().setProduct(product));
        } else {
            model.addAttribute("Error", "Серийный номер не может быть пустым!");
        }
        return "warranty/add";
    }
}
