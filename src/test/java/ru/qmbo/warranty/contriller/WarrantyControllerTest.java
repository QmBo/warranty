package ru.qmbo.warranty.contriller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.qmbo.warranty.domain.Warranty;
import ru.qmbo.warranty.domain.Product;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class WarrantyControllerTest {

    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetInfoThenDefaultPage() throws Exception {
        this.mockMvc.perform(get("/warranty"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/show"));
    }

    @Test
    public void whenGetInfoAndSNThenDefaultPageAndAnswer() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/warranty?sn=FC175Q001212100976"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/show"))
                .andReturn();
        Warranty resultWarranty = (Warranty) result.getModelAndView().getModel().get("warranty");
        assertThat(resultWarranty.getSerialNumber(), is("FC175Q001212100976"));
    }


    @Test
    public void whenGetAddFormThenAddForm() throws Exception {
        this.mockMvc.perform(get("/warranty/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/add"));
    }

    @Test
    public void whenPostSNThenWriteAndReturnProduct() throws Exception {
        Product product = new Product().setName("Dune HD Magic 4K");
        Warranty warranty = new Warranty().setProduct(product);
        this.mockMvc.perform(post("/warranty/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("sn=FC175Q001212100976"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("warranty/add"))
                .andExpect(model().attribute("warranty", warranty));
    }
}
