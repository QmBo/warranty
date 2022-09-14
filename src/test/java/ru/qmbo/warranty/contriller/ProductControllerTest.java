package ru.qmbo.warranty.contriller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.qmbo.warranty.domain.Product;
import ru.qmbo.warranty.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductRepository productRepository;

    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    @Autowired
    private MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<Product> captor;

    /** @noinspection unchecked*/
    @Test
    public void whenGetAllThenReturnAll() throws Exception {
        Product product = new Product()
                .setId(1)
                .setName("Test name")
                .setModelName("TEST")
                .setAbbreviature("T");
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        MvcResult result = this.mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("products/all"))
                .andReturn();
        //noinspection ConstantConditions
        List<Product> resultProducts = (List<Product>) result.getModelAndView().getModel().get("products");
        assertThat(resultProducts.get(0), is(product));
    }

    @Test
    public void whenGetAddThenReturnAdd() throws Exception {
        this.mockMvc.perform(get("/products/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("products/add"));
    }

    @Test
    public void whenPostCreateThenCreate() throws Exception {
        when(productRepository.findByAbbreviature("TN")).thenReturn(Optional.empty());
        when(productRepository.findByModelName("T")).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(new Product().setId(1));
        this.mockMvc.perform(post("/products/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("name=TestName&modelName=T&abbreviature=TN"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));
    }

    @Test
    public void whenPostProductWisTooLongABBRThenShowAdd() throws Exception {
        String expected = "Аббревиатура слишком длинная (больше чем 4 символов)";
        MvcResult result = this.mockMvc.perform(post("/products/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("name=TestName&modelName=T&abbreviature=12345"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("products/add"))
                .andReturn();
        //noinspection ConstantConditions
        String error = (String) result.getModelAndView().getModel().get("abbreviatureError");
        assertThat(error, is(expected));
    }

    @Test
    public void whenPostUpdateThenUpdate() throws Exception {
        Product product = new Product()
                .setId(1)
                .setName("Test name")
                .setModelName("TEST")
                .setAbbreviature("T");
        when(productRepository.save(any(Product.class))).thenReturn(product);
        this.mockMvc.perform(post("/products/update")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=1&name=TestName&modelName=T&abbreviature=1234"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products/edit/1"))
                .andReturn();
        verify(productRepository).save(captor.capture());
        assertThat(captor.getValue().getName(), is("TestName"));
    }

    @Test
    public void whenPostUpdateWisTooLongModelNameThenShowAdd() throws Exception {
        String expected = "Название модели слишком длинное (больше чем 10 символов)";
        MvcResult result = this.mockMvc.perform(post("/products/update")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=5&name=TestName&modelName=12345678901&abbreviature=1234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("products/edit/5"))
                .andReturn();
        //noinspection ConstantConditions
        String error = (String) result.getModelAndView().getModel().get("modelNameError");
        assertThat(error, is(expected));
    }

    @Test
    public void whenPostCreateSameABBRThenShowAdd() throws Exception {
        when(productRepository.findByAbbreviature("TN")).thenReturn(Optional.of(new Product()));
        this.mockMvc.perform(post("/products/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("name=TestName&modelName=T&abbreviature=TN"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("products/add"));
    }
}
