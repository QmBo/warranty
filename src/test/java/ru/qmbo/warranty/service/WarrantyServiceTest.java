package ru.qmbo.warranty.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.repository.ProductRepository;
import ru.qmbo.warranty.repository.WarrantyRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
@SpringBootTest
public class WarrantyServiceTest {

    @Autowired
    private WarrantyRepository warrantyRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenGetByIdThenReturnProduct() {
        WarrantyService service = new WarrantyService(warrantyRepository, productRepository);
        String sn = "FC175Q001212100976";
        Warranty result = service.getWarranty(sn);
        assertThat(result.getSerialNumber(), is(sn));
    }

    @Test
    public void whenGetSN175QThenReturnProductMagic() {
        WarrantyService service = new WarrantyService(warrantyRepository, productRepository);
        String sn = "FC175Q001212100976";
        Product product = service.getWarranty(sn).getProduct();
        assertThat(product.getName(), is("Dune HD Magic 4K"));
        assertThat(product.getModelName(), is("TV-175Q"));
        assertThat(product.getAbbreviature(), is("175Q"));
    }
}
