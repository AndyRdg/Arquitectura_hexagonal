package com.example.application;

import com.example.domain.model.Order;
import com.example.domain.model.OrderItem;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@QuarkusTest
public class OrderResourceTest {

    @Test
    public void testCreateOrderEndPoint() {
        Order order = new Order(LocalDateTime.now(), "PENDIENTE");
        given()
          .contentType("application/json")
          .body(order)
          .when().post("/orders")
          .then()
             .statusCode(201)
                .body("status", is("PENDIENTE"));
    }

    @Test
    public void testAddItemToOrderEndPoint(){
        Order order = new Order(LocalDateTime.now(), "PENDIENTE");
        given()
          .contentType("application/json")
          .body(order)
          .when().post("/orders")
          .then()
             .statusCode(201);

        OrderItem item = new OrderItem("item1", 2 , new BigDecimal("50.0"));
        given()
          .contentType("application/json")
          .body(item)
          .when().post("/orders/{orderId}/items", order.getId())
          .then()
             .statusCode(200);
    }

    @Test
    public void testUpdateOrderStatusEndPoint() {
        Order order = new Order(LocalDateTime.now(), "PENDIENTE");
        given()
          .contentType("application/json")
          .body(order)
          .when().post("/orders")
          .then()
             .statusCode(201);

        given()
            .contentType("application/json")
            .body("CONFIRMADO")
            .when().put("/orders/{orderId}/status", order.getId())
            .then()
                 .statusCode(200)
                 .body("status", is("CONFIRMADO"));
    }
}
