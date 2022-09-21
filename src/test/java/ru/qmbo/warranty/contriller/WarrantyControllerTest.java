package ru.qmbo.warranty.contriller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.repository.WarrantyRepository;

import java.util.*;

import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;


/** @noinspection ConstantConditions*/
@SpringBootTest
@AutoConfigureMockMvc
public class WarrantyControllerTest {

    @MockBean
    private WarrantyRepository warrantyRepository;

    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    @Autowired
    private MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<Warranty> captor;

    @Test
    public void whenGetInfoThenDefaultPage() throws Exception {
        this.mockMvc.perform(get("/warranty"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/show"));
    }

    @Test
    public void whenGetInfoAndSNThenDefaultPageAndAnswer() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/warranty?serialNumber=FC175Q001212100976"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/show"))
                .andReturn();
        Warranty resultWarranty = (Warranty) result.getModelAndView().getModel().get("warranty");
        assertThat(resultWarranty.getSerialNumber(), is("FC175Q001212100976"));
    }


    @Test
    @WithMockUser(authorities = {"MODER", "ADMIN"})
    public void whenGetAddFormThenAddForm() throws Exception {
        this.mockMvc.perform(get("/warranty/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/add"));
    }

    @Test
    @WithMockUser(authorities = {"MODER", "ADMIN"})
    public void whenPostSNThenWriteAndReturnWarranty() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        when(warrantyRepository.findBySerialNumber("FC175Q001212100976")).thenReturn(Optional.empty());
        when(warrantyRepository.save(any())).thenReturn(
                new Warranty()
                        .setSerialNumber("FC175Q001212100976")
                        .setProduct(new Product().setName("Dune HD Magic 4K"))
        );
        MvcResult result = this.mockMvc.perform(post("/warranty/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("sn=FC175q001212100976"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/add"))
                .andReturn();
        Warranty resultWarranty = (Warranty) result.getModelAndView().getModel().get("warranty");
        verify(warrantyRepository).save(captor.capture());
        assertThat(resultWarranty.getSerialNumber(), is("FC175Q001212100976"));
        assertThat(resultWarranty.getProduct().getName(), is("Dune HD Magic 4K"));
        assertThat(captor.getValue().getSerialNumber(), is("FC175Q001212100976"));
        assertThat(captor.getValue().getDate().getTime(), is(calendar.getTimeInMillis()));
    }

    @Test
    @WithMockUser(authorities = {"MODER", "ADMIN"})
    public void whenPostSNExistThenReturnWarranty() throws Exception {
        Warranty warranty = new Warranty().setSerialNumber("testNumber");
        when(warrantyRepository.findBySerialNumber("FC175Q001212100976")).thenReturn(Optional.of(warranty));
        MvcResult result = this.mockMvc.perform(post("/warranty/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("sn=FC175q001212100976"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/add"))
                .andReturn();
        Warranty resultWarranty = (Warranty) result.getModelAndView().getModel().get("warranty");
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(2021, Calendar.MAY, 30, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertThat(resultWarranty.getBuildDate().getTime(), is(calendar.getTimeInMillis()));
        assertThat(resultWarranty.getSerialNumber(), is("testNumber"));
    }

    @Test
    @WithMockUser(authorities = {"MODER", "ADMIN"})
    public void whenAllThenShowAllWarrantyPage() throws Exception {
        Product product = new Product()
                .setId(1)
                .setAbbreviature("175Q")
                .setModelName("Q")
                .setName("175 Q");
        Warranty first = new Warranty()
                .setId(1)
                .setSerialNumber("FC175Q001212100976")
                .setDate(new Date(System.currentTimeMillis()))
                .setProduct(product);
        Warranty second = new Warranty()
                .setId(2)
                .setSerialNumber("FC175Q001212100977")
                .setDate(new Date(System.currentTimeMillis()))
                .setProduct(product);
        when(warrantyRepository.findAll()).thenReturn(Arrays.asList(first, second));
        MvcResult result = this.mockMvc.perform(get("/warranty/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/all"))
                .andReturn();
        //noinspection unchecked
        List<Warranty> warranties = (List<Warranty>) result.getModelAndView().getModel().get("warranties");
        assertThat(warranties, contains(first, second));
    }

}
