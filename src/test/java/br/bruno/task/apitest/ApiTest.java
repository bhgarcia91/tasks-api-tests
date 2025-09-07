package br.bruno.task.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiTest {

    @BeforeClass
    public static void Setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTasks() {
        RestAssured.given()
                .when()
                     .get("/todo")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    @Test
    public void addTarefaSucesso(){
        RestAssured.given()
                        .body("{\"task\": \"Integration Test\", \"dueDate\": \"2025-12-12\" }")
                        .contentType(ContentType.JSON)
                    .when()
                        .post("/todo")
                    .then()
                        .statusCode(201)
                .log().all();
    }

    @Test
    public void naoDeveAddTarefaInvalida(){
        RestAssured.given()
                .body("{\"task\": \"Integration Test\", \"dueDate\": \"2010-12-12\" }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(400)
                .log().all()
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }



}



