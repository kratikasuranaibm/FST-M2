package RestAssuredProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredProject {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String SSHKey;
    int tokenId;

    @BeforeClass
    public void setUp(){
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token xxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .setBaseUri("https://api.github.com")
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType("application/json")
                .build();

        SSHKey = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIPhzne6IzZUA2ecBOA3Facba402DsgMvLeS9YMzAZXxF";
    }

    @Test(priority = 1)
    public void postMethod(){

        String reqBody = "{\"title\": \"RestAssuredAPIKey\",  \"key\": \""+SSHKey+"\" }";

        Response response=given().spec(requestSpec).body(reqBody).when().post("/user/keys");
        String resBody= response.getBody().asPrettyString();
        System.out.println(resBody);
        System.out.println("POST Response Status: "+response.getStatusCode());
        tokenId=response.then().extract().path("id");
        System.out.println("Token ID: "+tokenId);
        Assert.assertEquals(response.getStatusCode(), 201, "Passed: Correct POST status code");

    }

    @Test(priority = 2)
    public void getMethod(){
        Response response = given().spec(requestSpec).when().get("/user/keys");
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Passed: Correct GET status code");
        System.out.println("GET Response Status: "+response.getStatusCode());

    }


    @Test(priority = 3)
    public void deleteMethod(){
        Response response = given().spec(requestSpec).when().delete("/user/keys/"+tokenId); // Send DELETE request
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 204, "Passed: Correct DELETE status code");
        System.out.println("DELETE Response Status: "+response.getStatusCode());

    }


}


