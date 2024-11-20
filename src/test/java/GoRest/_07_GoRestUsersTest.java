package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _07_GoRestUsersTest {

    Faker randomUretici = new Faker();
    RequestSpecification reqSpec;
    int userID = 0;


    @BeforeClass
    public void Setup(){
        baseURI ="https://gorest.co.in/public/v2/";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer f92bf3f56439b631d9ed928b3540968e747c8e75309c876420fb349cbb420ed1")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void CreateUser(){
        String rndFullName = randomUretici.name().fullName();
        String rndEMail = randomUretici.internet().emailAddress();

        Map<String,String> newUser = new HashMap<>();
        newUser.put("name",rndFullName);
        newUser.put("gender","Male");
        newUser.put("email",rndEMail);
        newUser.put("status","active");


        userID =
                given()
                        .spec(reqSpec)
                        .body(newUser)

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")

                ;

        System.out.println("userID = " + userID);
    }


    @Test(dependsOnMethods = "CreateUser")
    public void GetUserById(){

        given()
                .spec(reqSpec)
                .log().uri()

                .when()
                .get("users/"+ userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
        ;
    }

    @Test(dependsOnMethods = "GetUserById")
    public void UpdateUser(){

        String updName = "Hatoko Piramiko";

        HashMap<String,String> updUsers = new HashMap<>();
        updUsers.put("name",updName);

        given()

                .spec(reqSpec)
                .body(updUsers)

                .when()
                .put("users/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name",equalTo(updName))

        ;
    }

    @Test(dependsOnMethods = "UpdateUser")
    public void DeleteUser(){


        given()
                .spec(reqSpec)

                .when()
                .delete("users/"+userID)

                .then()
                .statusCode(204)

        ;
    }

    @Test(dependsOnMethods = "DeleteUser")
    public void DeleteUserNegative()
    {
        given()
                .spec(reqSpec)

                .when()
                .delete("users/"+userID)

                .then()
                .statusCode(404)
        ;
    }

}
















