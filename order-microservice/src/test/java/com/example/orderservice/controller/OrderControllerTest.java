//package com.example.orderservice.controller;
//
//import com.example.orderservice.controller.dto.OrderRatingDto;
//import com.example.orderservice.controller.dto.OrderRatingItem;
//import com.example.orderservice.model.Order;
//import com.example.orderservice.model.User;
//import com.example.orderservice.service.OrderService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.Data;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.*;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.hasValue;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.willThrow;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class OrderControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//    @MockBean
//    private OrderService orderService;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    @Test
//    public void givenNonexistingOrderId_getThrowResponseStatusException() throws Exception {
//        when(orderService.findById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(
//                get("/order/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
//    }
//
//
//    @Test
//    public void givenExistingOrderId_getReturnObject() throws Exception {
//        User user = new User(1L, "Test User");
//
//        Order order = new Order(2L, user, "Test 1 Address", Order.OrderStatus.Pending, null);
//
//        when(orderService.findById(2L)).thenReturn(Optional.of(order));
//
//        mockMvc.perform(
//                get("/order/2"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.id").value(2L))
//                .andExpect(jsonPath("$.deliveryAddress").value("Test 1 Address"))
//                .andExpect(jsonPath("$.orderStatus").value("Created"))
//                .andExpect(jsonPath("$.user.id").value(1L))
//                .andExpect(jsonPath("$.user.name").value("Test User"));
//
//    }
//
//
//    @Test
//    public void givenNonexistingOrderId_deleteThrowResponseStatusException() throws Exception {
//        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")).given(orderService).deleteById(2L);
//
//        mockMvc.perform(
//                delete("/order/2"))
//                .andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
//    }
//
//
//    @Test
//    public void givenExistingOrderId_deleteStatusOk() throws Exception {
//        Order order = new Order();
//        order.setId(2L);
//
//        when(orderService.findById(2L)).thenReturn(Optional.of(order));
//        mockMvc.perform(
//                delete("/order/2"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void givenManyOrdersPresent_expectThemInJsonBody() throws Exception {
//        User user1 = new User(1L, "User One");
//        User user2 = new User(2L, "User Two");
//
//        List<Order> orders = new ArrayList<>();
//        orders.add(new Order(1L, user1, "Address One", Order.OrderStatus.Pending, null));
//        orders.add(new Order(2L, user2, "Address Two", Order.OrderStatus.Pending, null));
//        orders.add(new Order(3L, user1, "Address Three", Order.OrderStatus.Finished, null));
//
//        when(orderService.findAll()).thenReturn(orders);
//
//
//        mockMvc.perform(
//                get("/order"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//
//                .andExpect(jsonPath("$.[0].id").value(1L))
//                .andExpect(jsonPath("$.[0].deliveryAddress").value("Address One"))
//                .andExpect(jsonPath("$.[0].orderStatus").value(Order.OrderStatus.Pending.name()))
//                .andExpect(jsonPath("$.[0].user.id").value(1L))
//                .andExpect(jsonPath("$.[0].user.name").value("User One"))
//
//                .andExpect(jsonPath("$.[1].id").value(2L))
//                .andExpect(jsonPath("$.[1].deliveryAddress").value("Address Two"))
//                .andExpect(jsonPath("$.[1].orderStatus").value(Order.OrderStatus.Pending.name()))
//                .andExpect(jsonPath("$.[1].user.id").value(2L))
//                .andExpect(jsonPath("$.[1].user.name").value("User Two"))
//
//                .andExpect(jsonPath("$.[2].id").value(3L))
//                .andExpect(jsonPath("$.[2].deliveryAddress").value("Address Three"))
//                .andExpect(jsonPath("$.[2].orderStatus").value(Order.OrderStatus.Finished.name()))
//                .andExpect(jsonPath("$.[2].user.id").value(1L))
//                .andExpect(jsonPath("$.[2].user.name").value("User One"));
//    }
//
//    @Test
//    public void givenNoOrdersPresent_expectEmptyArray() throws Exception {
//
//        when(orderService.findAll()).thenReturn(new ArrayList<>()); // empty list
//
//        mockMvc.perform(
//                get("/order"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(0)));
//    }
//
//    public void prepareOrdersPerUser() {
//        User user1 = new User(1L, "User One");
//        User user2 = new User(2L, "User Two");
//
//        Order order1 = new Order(1L, user1, "Address One", Order.OrderStatus.Pending, null);
//        Order order2 = new Order(2L, user2, "Address Two", Order.OrderStatus.Pending, null);
//        Order order3 = new Order(3L, user1, "Address Three", Order.OrderStatus.Finished, null);
//
//        when(orderService.findAllOrdersByUser(user1.getId())).thenReturn(List.of(order1, order3));
//
//        when(orderService.findAllOrdersByUser(user2.getId())).thenReturn(List.of(order2));
//    }
//
//    @Test
//    public void givenOrdersPerUser_expectThemByUserOneInJsonBody() throws Exception {
//        prepareOrdersPerUser();
//
//        mockMvc.perform(
//                get("/order/by-user/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//
//                .andExpect(jsonPath("$.[0].id").value(1L))
//                .andExpect(jsonPath("$.[0].deliveryAddress").value("Address One"))
//                .andExpect(jsonPath("$.[0].orderStatus").value(Order.OrderStatus.Pending.name()))
//                .andExpect(jsonPath("$.[0].user.id").value(1L))
//                .andExpect(jsonPath("$.[0].user.name").value("User One"))
//
//                .andExpect(jsonPath("$.[1].id").value(3L))
//                .andExpect(jsonPath("$.[1].deliveryAddress").value("Address Three"))
//                .andExpect(jsonPath("$.[1].orderStatus").value(Order.OrderStatus.Finished.name()))
//                .andExpect(jsonPath("$.[1].user.id").value(1L))
//                .andExpect(jsonPath("$.[1].user.name").value("User One"));
//    }
//
//    @Test
//    public void givenOrdersPerUser_expectThemByUserTwoInJsonBody() throws Exception {
//        prepareOrdersPerUser();
//
//        mockMvc.perform(
//                get("/order/by-user/2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//
//                .andExpect(jsonPath("$.[0].id").value(2L))
//                .andExpect(jsonPath("$.[0].deliveryAddress").value("Address Two"))
//                .andExpect(jsonPath("$.[0].orderStatus").value(Order.OrderStatus.Pending.name()))
//                .andExpect(jsonPath("$.[0].user.id").value(2L))
//                .andExpect(jsonPath("$.[0].user.name").value("User Two"));
//    }
//
//    @Test
//    public void givenValidOrder_ExpectStatusCreatedAndResponseBody() throws Exception {
//
//        Order order = new Order(1L, new User(1L, "User One"), "Address One", Order.OrderStatus.Pending, new ArrayList<>());
//
//        when(orderService.saveNewOrder(order)).thenReturn(order);
//
//
//        mockMvc.perform(
//                post("/order/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(order)))
//                .andDo(print())
//                .andExpect(status().isCreated())
//
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.deliveryAddress").value("Address One"))
//                .andExpect(jsonPath("$.orderStatus").value(Order.OrderStatus.Pending.name()))
//                .andExpect(jsonPath("$.user.id").value(1L))
//                .andExpect(jsonPath("$.user.name").value("User One"));
//    }
//
//    @Test
//    public void givenInvalidOrder_ExpectStatusBadRequestAndErrorMessage() throws Exception {
//
//        Order order = new Order(1L, new User(1L, "User One"), "test", Order.OrderStatus.Pending, new ArrayList<>());
//        // minimum valid deliveryAddress length is 5 characters
//
//        mockMvc.perform(
//                post("/order/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(order)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//
//                .andExpect(jsonPath("$.message").value("Delivery address length must be at least 5 characters."))
//                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
//    }
//
//
//    @Test
//    public void givenValidOrderRatingDto_ExpectRatingsRetrievedFromRatingService() throws Exception {
//
//        List<OrderRatingItem> ratingItems = List.of(
//                new OrderRatingItem(1L, 1),
//                new OrderRatingItem(2L, 2)
//        );
//
//        OrderRatingDto orderRatingDto = new OrderRatingDto(1L, ratingItems);
//
//
//        mockMvc.perform(
//                post("/order/rate")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(orderRatingDto)));
//
//
//        RatingDTO[] ratingDto = restTemplate.getForObject("http://rating-microservice/rating", RatingDTO[].class);
//
//        boolean foundFirst = false;
//        boolean foundSecond = false;
//
//        assertNotNull(ratingDto);
//
//        for (RatingDTO ratingDTO : ratingDto) {
//            if (ratingDTO.getProduct().getId() == 1L && ratingDTO.getRate() == 1 && ratingDTO.getUser().getId() == 1L) {
//                foundFirst = true;
//            }
//
//            if (ratingDTO.getProduct().getId() == 2L && ratingDTO.getRate() == 2 && ratingDTO.getUser().getId() == 1L) {
//                foundSecond = true;
//            }
//        }
//
//        assertTrue(foundFirst);
//        assertTrue(foundSecond);
//
//    }
//
//    @Test
//    public void giveEmptyRatingDto_ExpectBadRequestException() throws Exception {
//        List<OrderRatingItem> ratingItems = new ArrayList<>(); // prazna lista
//
//        OrderRatingDto orderRatingDto = new OrderRatingDto(1L, ratingItems);
//
//
//
//        mockMvc.perform(
//                post("/order/rate")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(orderRatingDto)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//
//                .andExpect(jsonPath("$.message").value("Rating items list must contain at least one element"))
//                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
//
//    }
//
//
//
////    @Test
////    public void givenNoRatingForArguments_whenGetRating_thenNotFound() throws Exception {
////        when(orderService.getRatingOfProduct(1L, 1L)).thenReturn(Optional.empty());
////
////        mockMvc.perform(
////                get("/rating/get")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .param("productId", "1")
////                        .param("userId", "1"))
////                .andDo(print())
////                .andExpect(status().isNotFound());
////    }
////
////    @Test
////    public void givenBadArguments_whenGetRating_thenBadRequest() throws Exception {
////        mockMvc.perform(
////                get("/rating/get")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .param("productId", "")
////                        .param("userId", ""))
////                .andDo(print())
////                .andExpect(status().isBadRequest());
////    }
////
////    @Test
////    public void givenBadArguments_whenAddRating_thenBadRequest() throws Exception {
////        RatingDto ratingDto = new RatingDto();
////        ratingDto.setRate(3);
////
////        mockMvc.perform(post("/rating/add")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(asJsonString(ratingDto)))
////                .andDo(print())
////                .andExpect(status().isBadRequest());
////    }
////
////    @Test
////    public void givenValidArguments_whenAddRating_thenOk() throws Exception {
////        RatingDto ratingDto = RatingDto.builder()
////                .rate(1)
////                .productId(1L)
////                .userId(1L)
////                .build();
////
////        mockMvc.perform(post("/rating/add")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(asJsonString(ratingDto)))
////                .andDo(print())
////                .andExpect(status().isOk());
////    }
////
////    @Test
////    public void givenValidRatingRequest_whenAddRating_thenValidResponse() throws Exception {
////        RatingDto ratingDto = RatingDto.builder()
////                .rate(1)
////                .productId(1L)
////                .userId(1L)
////                .build();
////
////        when(orderService.addRating(any(RatingDto.class))).thenReturn(ratingDto);
////
////        mockMvc.perform(post("/rating/add")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(asJsonString(ratingDto)))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.status").value("OK"))
////                .andExpect(jsonPath("$.message").value("Rating successfully saved."));
////    }
////
////    @Test
////    public void givenBadArguments_whenUpdateRating_thenBadRequestAndValidMessage() throws Exception {
////        RatingDto ratingDto = RatingDto.builder()
////                .rate(1)
////                .productId(1L)
////                .build();
////
////        mockMvc.perform(put("/rating/update")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(asJsonString(ratingDto)))
////                .andDo(print())
////                .andExpect(status().isBadRequest())
////                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
////                .andExpect(jsonPath("$.message").value("Field 'userId' cannot be empty."));
////    }
////
////    @Test
////    public void givenValidRatingRequest_whenUpdateRating_thenValidResponse() throws Exception {
////        RatingDto ratingDto = RatingDto.builder()
////                .rate(1)
////                .productId(1L)
////                .userId(1L)
////                .build();
////
////        when(orderService.updateRating(any(RatingDto.class))).thenReturn(ratingDto);
////
////        mockMvc.perform(put("/rating/update")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(asJsonString(ratingDto)))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.status").value("OK"))
////                .andExpect(jsonPath("$.message").value("Rating successfully updated."));
////    }
////
////    @Test
////    public void givenBadArguments_whenDeleteRating_thenBadRequest() throws Exception {
////        mockMvc.perform(
////                delete("/rating/delete")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .param("productId", "")
////                        .param("userId", ""))
////                .andDo(print())
////                .andExpect(status().isBadRequest());
////    }
////
////    @Test
////    public void givenValidArgumentsButNoElement_whenDeleteRating_thenNotFound() throws Exception {
////        when(orderService.deleteRating(1L, 1L)).thenThrow(NoSuchElementException.class);
////
////        mockMvc.perform(
////                delete("/rating/delete")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .param("productId", "1")
////                        .param("userId", "1"))
////                .andDo(print())
////                .andExpect(status().isNotFound());
////    }
////
////    @Test
////    public void givenValidArgumentsAndElementPresent_whenDeleteRating_thenValidResponse() throws Exception {
////        when(orderService.deleteRating(1L, 1L)).thenReturn(Optional.of(new RatingDto()));
////
////        mockMvc.perform(
////                delete("/rating/delete")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .param("productId", "1")
////                        .param("userId", "1"))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.message").value("Rating successfully deleted."));
////    }
//}
//
//
//@Data
//class RatingDTO {
//    private Long id;
//    private ProductFromRatingService product;
//    private UserFromRatingService user;
//    private Integer rate;
//}
//
//@Data
//class ProductFromRatingService {
//    private Long id;
//}
//
//@Data
//class UserFromRatingService {
//    private Long id;
//}