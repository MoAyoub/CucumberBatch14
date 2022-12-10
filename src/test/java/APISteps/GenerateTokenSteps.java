package APISteps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GenerateTokenSteps {

   String baseURI= RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api";

   //to use my token in the project i am using static public

    public static String token;

    @Given("a JWT is generated")
    public void a_jwt_is_generated() {

        RequestSpecification request=given().header("Content-Type","application/json")
                .body("{\n" +
                        "\n" +
                        "  \"email\": \"mo@test.com\",\n" +
                        "  \"password\": \"Test@123\"\n" +
                        "\n" +
                        "\n" +
                        "}");

        Response response=request.when().post("/generateToken.php");
        //printing the response body
        response.prettyPrint();




    }

}
