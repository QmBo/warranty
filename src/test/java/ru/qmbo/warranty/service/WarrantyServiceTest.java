package ru.qmbo.warranty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.repository.ProductRepository;
import ru.qmbo.warranty.repository.WarrantyRepository;
import ru.qmbo.warranty.service.impl.ProductServiceDefault;
import ru.qmbo.warranty.service.impl.WarrantyServiceDefault;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WarrantyServiceTest {

    @Captor
    private ArgumentCaptor<String> stringCaptor;
    @Captor
    private ArgumentCaptor<Warranty> warrantyCaptor;
    @Mock
    private WarrantyRepository warrantyRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceDefault productService;

    private Calendar calendar;

    @BeforeEach
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    @Test
    public void whenGetByIdThenReturnProduct() {
        String sn = "FC175Q001212100976";
        when(warrantyRepository.findBySerialNumber(sn)).thenReturn(Optional.empty());
        when(productRepository.findByAbbreviature(anyString())).thenReturn(Optional.empty());
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        Warranty result = service.getBySerialNumber(sn);
        assertThat(result.getSerialNumber(), is(sn));
    }

    @Test
    public void whenGetSN175QThenReturnProductMagic() {
        String sn = "FC175Q001212100976";
        String abbr = "175Q";
        String modelName = "TV-175Q";
        String name = "Dune HD Magic 4K";
        Product result = new Product().setAbbreviature(abbr).setModelName(modelName).setName(name);
        when(warrantyRepository.findBySerialNumber(sn)).thenReturn(Optional.empty());
        when(productRepository.findByAbbreviature(anyString())).thenReturn(Optional.of(result));
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        Warranty resultWarranty = service.getBySerialNumber(sn);
        Product resultProduct = resultWarranty.getProduct();
        assertThat(resultProduct.getName(), is(name));
        assertThat(resultProduct.getModelName(), is(modelName));
        assertThat(resultProduct.getAbbreviature(), is(abbr));
        assertThat(resultWarranty.getSerialNumber(), is(sn));
    }

    @Test
    public void whenGetSN175fThenReturnProductNeoPlus() {
        String sn = "FC175f001212100976";
        String abbr = "175F";
        String modelName = "TV-175F";
        String name = "Dune HD Neo 4K Plus";
        Product result = new Product().setAbbreviature(abbr).setModelName(modelName).setName(name);
        when(warrantyRepository.findBySerialNumber(sn)).thenReturn(Optional.empty());
        when(productRepository.findByAbbreviature(abbr)).thenReturn(Optional.of(result));
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        Warranty resultWarranty = service.getBySerialNumber(sn);
        Product resultProduct = resultWarranty.getProduct();
        assertThat(resultProduct.getName(), is(name));
        assertThat(resultProduct.getModelName(), is(modelName));
        assertThat(resultProduct.getAbbreviature(), is(abbr));
        assertThat(resultWarranty.getSerialNumber(), is("FC175F001212100976"));
        verify(productRepository).findByAbbreviature(stringCaptor.capture());
        assertThat(stringCaptor.getAllValues().contains(abbr), is(true));
    }

    @Test
    public void whenGetSNThenReturnProductWithWarranty() {
        String sn = "FC175F001212100999";
        String abbr = "175F";
        String modelName = "TV-175F";
        String name = "Dune HD Neo 4K Plus";
        Date date = new Date(System.currentTimeMillis());
        Product product = new Product().setAbbreviature(abbr).setModelName(modelName).setName(name);
        Warranty warranty = new Warranty().setProduct(product).setSerialNumber(sn).setDate(date);
        when(warrantyRepository.findBySerialNumber(sn)).thenReturn(Optional.of(warranty));
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        Warranty resultWarranty = service.getBySerialNumber(sn);
        Product resultProduct = warranty.getProduct();
        assertThat(resultProduct.getName(), is(name));
        assertThat(resultProduct.getModelName(), is(modelName));
        assertThat(resultProduct.getAbbreviature(), is(abbr));
        assertThat(resultWarranty.getSerialNumber(), is(sn));
        assertThat(resultWarranty.getDate(), is(date));
    }

    @Test
    public void whenPutSNThenReturnWarrantyWisDate() {
        String sn = "FC175F001212100999";
        String abbr = "175F";
        String modelName = "TV-175F";
        String name = "Dune HD Neo 4K Plus";
        Date date = new Date(System.currentTimeMillis());
        Product product = new Product().setAbbreviature(abbr).setModelName(modelName).setName(name);
        Warranty warranty = new Warranty().setProduct(product).setSerialNumber(sn).setDate(date);
        when(warrantyRepository.findBySerialNumber(sn)).thenReturn(Optional.empty());
        when(warrantyRepository.save(any(Warranty.class))).thenReturn(warranty);
        when(productRepository.findByAbbreviature(abbr)).thenReturn(Optional.of(product));
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        Warranty resultWarranty = service.writeWarrantyIfNotExist(sn, "");
        assertThat(resultWarranty.getSerialNumber(), is(sn));
        assertThat(resultWarranty.getDate(), is(date));
    }

    @Test
    public void whenPutSNAndDateThenReturnWarranty() {
        String sn = "FC175F001212100999";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.SEPTEMBER, 23, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Warranty warranty = new Warranty().setSerialNumber(sn).setDate(calendar.getTime());
        when(productRepository.findByAbbreviature(anyString())).thenReturn(Optional.of(new Product()));
        when(warrantyRepository.save(any(Warranty.class))).thenReturn(warranty);
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        Warranty resultWarranty = service.writeWarrantyIfNotExist(sn, "2022-09-23");
        assertThat(resultWarranty.getDate().getTime(), is(calendar.getTimeInMillis()));
    }

    @Test
    public void whenPutSNAndWrongDateThenReturnWarranty() {
        String sn = "FC175F001212100999";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Warranty warranty = new Warranty().setSerialNumber(sn).setDate(calendar.getTime());
        when(warrantyRepository.save(any(Warranty.class))).thenReturn(warranty);
        when(productRepository.findByAbbreviature(anyString())).thenReturn(Optional.of(new Product()));
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        Warranty resultWarranty = service.writeWarrantyIfNotExist(sn, "2022-23");
        assertThat(resultWarranty.getDate().getTime(), is(calendar.getTimeInMillis()));
    }

    @Test
    public void whenUpdateWarrantyThenTrue() {
        Warranty warranty = new Warranty()
                .setId(10)
                .setDate("2020-05-09")
                .setSerialNumber("FC175F001212100976");
        when(productRepository.findByAbbreviature("175F"))
                .thenReturn(Optional.of(new Product()));
        when(warrantyRepository.findBySerialNumber("FC175F001212100976"))
                .thenReturn(Optional.of(new Warranty().setId(10)));
        when(warrantyRepository.save(any(Warranty.class))).thenReturn(new Warranty());
        calendar.set(2020, Calendar.MAY, 9);
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        assertThat(service.updateWarranty(warranty), is(true));
        verify(warrantyRepository).save(warrantyCaptor.capture());
        assertThat(warrantyCaptor.getValue().getId(), is(10));
        assertThat(warrantyCaptor.getValue().getDate().getTime(), is(calendar.getTimeInMillis()));
    }

    @Test
    public void whenUpdateSerialNumberWarrantyThenTrue() {
        Warranty warranty = new Warranty()
                .setId(10)
                .setDate("2020-05-09")
                .setSerialNumber("FC175f001212100976");
        when(productRepository.findByAbbreviature("175F"))
                .thenReturn(Optional.of(new Product()));
        when(warrantyRepository.findBySerialNumber("FC175F001212100976"))
                .thenReturn(Optional.empty());
        when(warrantyRepository.save(any(Warranty.class))).thenReturn(new Warranty());
        calendar.set(2020, Calendar.MAY, 9);
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        assertThat(service.updateWarranty(warranty), is(true));
        verify(warrantyRepository).save(warrantyCaptor.capture());
        assertThat(warrantyCaptor.getValue().getId(), is(10));
        assertThat(warrantyCaptor.getValue().getDate().getTime(), is(calendar.getTimeInMillis()));
        assertThat(warrantyCaptor.getValue().getSerialNumber(), is("FC175F001212100976"));
    }

    @Test
    public void whenUpdateSerialNumberAlreadyExistWarrantyThenFalse() {
        Warranty warranty = new Warranty()
                .setId(10)
                .setDate("2020-05-09")
                .setSerialNumber("FC175F001212100976");
        when(productRepository.findByAbbreviature("175F"))
                .thenReturn(Optional.of(new Product()));
        when(warrantyRepository.findBySerialNumber("FC175F001212100976"))
                .thenReturn(Optional.of(new Warranty().setId(1)));
        WarrantyService service = new WarrantyServiceDefault(warrantyRepository, productService);
        assertThat(service.updateWarranty(warranty), is(false));
    }
}
