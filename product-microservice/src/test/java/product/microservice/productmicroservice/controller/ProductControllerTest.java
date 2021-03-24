package product.microservice.productmicroservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc MockMVC;

    @Test
    void allProducts() throws Exception {
        MockMVC.perform(MockMvcRequestBuilders.get("/product/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}