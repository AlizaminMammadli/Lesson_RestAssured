import Model.pojoExtract;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.assertEquals;

public class _05_Tasks {

    /**

     Task 1
     create a request to https://jsonplaceholder.typicode.com/todos/2
     expect status 200
     expect content type JSON
     expect title in response body to be "quis ut nam facilis et officia qui"*/

    @Test
    public void Soru1() {


        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;

    }

    @Test
    public void Soru2() {
        /**

         Task 2
         create a request to https://jsonplaceholder.typicode.com/todos/2
         expect status 200
         expect content type JSON
         *a)expect response completed status to be false(hamcrest)
         * *b) extract completed field and testNG assertion(testNG)*/


        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false))
        ;


        boolean complatedAnswer =
        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("completed")
        ;

        Assert.assertFalse(complatedAnswer);
    }

    @Test
    public void Soru3() {
/** Task 3

 create a request to https://jsonplaceholder.typicode.com/todos/2
 Converting Into POJO body data and write*/

        pojoExtract pojoExtract =
        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .extract().as(pojoExtract.class)
        ;

        assertEquals(1,pojoExtract.getUserId(), "User ID eşleşmiyor");
        assertEquals(2, pojoExtract.getId(), "ID eşleşmiyor");
        assertEquals("quis ut nam facilis et officia qui", pojoExtract.getTitle(), "Title eşleşmiyor");
        assertEquals(false, pojoExtract.isCompleted(), "Completed durumu eşleşmiyor");
    }

}
