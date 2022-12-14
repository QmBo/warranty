package ru.qmbo.warranty.control;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.service.ProductService;
import ru.qmbo.warranty.utils.ControllerUtils;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("products")
@PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
public class ProductController {
    public static final String PRODUCT = "product";
    public static final String PRODUCTS = "products";
    public static final String ERROR = "error";
    public static final String PRODUCT_ALREADY_EXISTS = "Такой продукт уже существует";
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showAllProducts(Model model, Principal principal) {
        model.addAttribute(PRODUCTS, this.productService.getAll());
        return "products/all";
    }

    @GetMapping("/add")
    public String showAddProducts() {
        return "products/add";
    }

    @PostMapping("/create")
    public String createProducts(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute(PRODUCT, product);
            return "products/add";
        }
        boolean createOk = this.productService.save(product).isEmpty();
        if (!createOk) {
            model.addAttribute(ERROR, PRODUCT_ALREADY_EXISTS);
            model.addAttribute(PRODUCT, product);
            return "products/add";
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{product}")
    public String showEditProduct(Model model, @PathVariable Product product) {
        model.addAttribute(PRODUCT, product);
        return "products/edit";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam Integer id) {
        this.productService.deleteProductById(id);
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute(PRODUCT, product);
            return "products/edit/" + product.getId();
        }
        this.productService.updateProduct(product);
        return "redirect:/products/edit/" + product.getId();
    }
}
