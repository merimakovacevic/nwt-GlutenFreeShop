package product.microservice.productmicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class ProductControllerTest {
//
//    @Autowired
//    private MockMvc MockMVC;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    void allProducts() throws Exception {
//        MockMVC.perform(MockMvcRequestBuilders.get("/product/all"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    void getProductById() throws Exception {
//        MockMVC.perform(MockMvcRequestBuilders.get("/product/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    void getProductByName() throws Exception {
//        MockMVC.perform(MockMvcRequestBuilders.get("/product/product/carrot"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @Test
//    void getProductByProductTypeId() throws Exception {
//        MockMVC.perform(MockMvcRequestBuilders.get("/product/producttype/2"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @Test
//    void getProductByProductTypeName() throws Exception {
//        MockMVC.perform(MockMvcRequestBuilders.get("/product/producttypename/vegetables"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @Test
//    void addNewProduct() throws Exception {
//        ProductType type = new ProductType("TestName");
//        Product product = new Product("NewTestProduct", "This is new webshop product", 3.0, 12, type);
//        Gson gson = new Gson();
//        String json = gson.toJson(product);
//
//        MockMVC.perform(MockMvcRequestBuilders.post("/product/add")
//                .contentType(MediaType.APPLICATION_JSON).content(json))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
}