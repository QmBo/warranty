package ru.qmbo.warranty.contriller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.qmbo.warranty.domain.Product;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetInfoThenDefaultPage() throws Exception {
        this.mockMvc.perform(get("/info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("info"));
    }

    @Test
    public void whenGetInfoAndSNThenDefaultPageAndAnswer() throws Exception {
        Product product = new Product();
        product.setName("Dune HD Magic 4K");
        this.mockMvc.perform(get("/info/FC175Q001212100976"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("info"))
                .andExpect(model().attribute("answer", product));
    }
}
