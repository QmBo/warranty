package ru.qmbo.warranty.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.qmbo.warranty.domain.Product;

/**
 * InfoController class.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 30.08.2022
 */
@Controller
@RequestMapping("/info")
public class InfoController {

    @GetMapping
    public String getDefaultPage(Model model) {
        return "info";
    }

    @GetMapping("/{serialNumber}")
    public String getInformationAboutSN(Model model, @PathVariable("serialNumber") String serialNumber) {
        //TODO product finder!!
        Product product = new Product();
        product.setName("Dune HD Magic 4K");
        model.addAttribute("answer", product);
        return "info";
    }
}
