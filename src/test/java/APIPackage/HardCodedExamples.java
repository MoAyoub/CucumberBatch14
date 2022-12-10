package APIPackage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
//to change the method execution we use this fix method approach
@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class HardCodedExamples {
    //remember while working in postman we have base url and end point

    //end point can not come with request

    //bas URI - base URL
    //end then using when keyword, we will send the end point
//we need to add http://
String baseURI= RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api";

//we need to perform CRUD operation

    //we need now token first
    String token= "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NzQwODc5NTAsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY3NDEzMTE1MCwidXNlcklkIjoiNDczOSJ9._7Cj2S0E_NnjnWlJkI_C7Cf-cO578L8TqHGWXBOCDDg";
static String employee_id;
    @Test
    public void bgetOneEmployee(){

        //prepare the request
//to prepare request we use request specification
        RequestSpecification request= given().header("Authorization",token)
                .header("Content-Type","application/json")
                .queryParam("employee_id",employee_id);
//to hit the end point/ to make the request which will return response
     Response response= request.when().get("/getOneEmployee.php");

     //this step is only to print to test our hard code
       // System.out.println(response.asString());
//this command to print command into console
        response.prettyPrint();
//this will verify the status code, when we run succefully we should get the code 200
        response.then().assertThat().statusCode(200);

//using jsonpath method we are extracting value from response body
String firstname=response.jsonPath().getString("employee.emp_firstname");
        System.out.println(firstname);
        //first way of assertion
        Assert.assertEquals(firstname,"X123");
        //second way of assertion
        //import class for equalTo from hamcrest matchers
        response.then().assertThat().body("employee.emp_firstname", equalTo("X123"));

    }

    @Test

public void acreateEmployee(){
        RequestSpecification request = given().header("Authorization",token)
                .header("Content-Type","application/json").body("{\n" +
                        "  \"emp_firstname\": \"X123\",\n" +
                        "  \"emp_lastname\": \"Y123\",\n" +
                        "  \"emp_middle_name\": \"M\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2005-01-14\",\n" +
                        "  \"emp_status\": \"confirmed\",\n" +
                        "  \"emp_job_title\": \"QA Engineer\"\n" +
                        "}");
       Response response= request.when().post("/createEmployee.php");

       response.prettyPrint();
       response.then().assertThat().statusCode(201);
       //getting the employee id from the response and use it as static one
       employee_id=response.jsonPath().getString("Employee.employee_id");
    System.out.println(employee_id);
    //verify body
    response.then().assertThat().body("Employee.emp_lastname", equalTo("Y123"));
        response.then().assertThat().body("Employee.emp_middle_name", equalTo("M"));
    //verify header
        response.then().assertThat().header("Server","Apache/2.4.39 (Win64) PHP/7.2.18");

}
@Test
public void cupdateEmployee(){

    RequestSpecification request = given().header("Authorization",token)
            .header("Content-Type","application/json").body("{\n" +
                    "        \"employee_id\": \""+employee_id+"\",\n" +
                    "        \"emp_firstname\": \"Xyz\",\n" +
                    "        \"emp_lastname\": \"Zyx\",\n" +
                    "        \"emp_middle_name\": \"A\",\n" +
                    "        \"emp_gender\": \"F\",\n" +
                    "        \"emp_birthday\": \"2010-01-15\",\n" +
                    "        \"emp_status\": \"probation\",\n" +
                    "        \"emp_job_title\": \"QA\"\n" +
                    "        }");



    Response response=request.when().put("/updateEmployee.php");

    //verification

    response.then().assertThat().statusCode(200);
    response.then().assertThat().body("Message", equalTo("Employee record Updated"));

}

@Test
    public void dgetUpdatedEmployee(){

    RequestSpecification request= given().header("Authorization",token)
            .header("Content-Type","application/json")
            .queryParam("employee_id",employee_id);
//to hit the end point/ to make the request which will return response
    Response response= request.when().get("/getOneEmployee.php");

    //this step is only to print to test our hard code
    // System.out.println(response.asString());
//this command to print command into console
    response.prettyPrint();
//this will verify the status code, when we run succefully we should get the code 200
    response.then().assertThat().statusCode(200);
    response.then().assertThat().body("employee.emp_job_title", equalTo("QA"));

}

}


