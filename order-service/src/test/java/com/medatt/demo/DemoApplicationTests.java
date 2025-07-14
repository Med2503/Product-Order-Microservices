package com.medatt.demo;

import medatt.demo.dto.OrderLineItemsRequest;
import medatt.demo.dto.OrderRequest;
import medatt.demo.model.Order;
import medatt.demo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class DemoApplicationTests {
@Autowired
private MockMvc mockMvc;
@Autowired
private ObjectMapper objectMapper;
@Autowired
private OrderRepository orderRepository;
	@Container
	static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

   @DynamicPropertySource
   public static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
	   dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
   }

	@Test
	void shouldCreateProduct() throws Exception {
    OrderRequest orderRequest = getOrderRequest();
    String ord1 = objectMapper.writeValueAsString(orderRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ord1))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1,orderRepository.findAll().size());
	}

	private OrderRequest getOrderRequest() {
		List<OrderLineItemsRequest> list = new ArrayList<>();
		list.add(new OrderLineItemsRequest("007",BigDecimal.valueOf(1200),10));

    return OrderRequest.builder()
			.OrderNumber(UUID.randomUUID().toString())
			.orderLineItemsRequest(list)
			.build();
	}

}
