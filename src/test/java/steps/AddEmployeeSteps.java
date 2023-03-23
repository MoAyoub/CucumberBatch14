package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.CommonMethods;
import utils.Constants;
import utils.DBUtility;
import utils.ExcelReader;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeSteps extends CommonMethods {
    String id;
    String fName,lName;
    @When("user clicks on PIM option")
    public void user_clicks_on_pim_option() {
       // WebElement pimOption=driver.findElement(By.id("menu_pim_viewPimModule"));
       // pimOption.click();
        click(dashboard.pimOption);
    }


    @When("user clicks on Add Employee button")
    public void user_clicks_on_add_employee_button() {
       // WebElement addEmployeeOption = driver.findElement(By.id("menu_pim_addEmployee"));
        //addEmployeeOption.click();
        click(dashboard.addEmployeeOption);

    }
    @When("user enter firstname and lastname")
    public void user_enter_firstname_and_lastname() {
       // WebElement firstName = driver.findElement(By.id("firstName"));
        //firstName.sendKeys("Mo");

        sendText(addEmployee.firstNameField,"Joshpan");

       // WebElement lastName = driver.findElement(By.id("lastName"));
        //lastName.sendKeys("Ayoub");

        sendText((addEmployee.lastNameField),"Barsik");
    }
    @When("user clicks on save button")
    public void user_clicks_on_save_button() {
       // WebElement saveButton = driver.findElement(By.id("btnSave"));
        //saveButton.click();
        click(addEmployee.saveButton);

    }
    @Then("employee added successfully")
    public void employee_added_successfully() {
        System.out.println("Employee added");
    }

    @When("user enter {string} and {string}")
    public void user_enter_and(String firstName, String lastName) {
        fName=firstName;
        lName=lastName;
        sendText(addEmployee.firstNameField,firstName);
        sendText(addEmployee.lastNameField, lastName);
    }

    @When("user enter {string} and {string} for adding multiple employees")
    public void user_enter_and_for_adding_multiple_employees(String firstNameValue, String lastNameValue) {
        sendText(addEmployee.firstNameField, firstNameValue);
        sendText(addEmployee.lastNameField, lastNameValue);
    }
    @When("user adds multiple employees and verify they are added successfully")
    public void user_adds_multiple_employees_and_verify_they_are_added_successfully(DataTable dataTable) throws InterruptedException {
        List<Map<String,String>>employeesNames= dataTable.asMaps();
        for (Map<String,String>employee:employeesNames) {
        String firstNameValue =  employee.get("firstName");
        String middleNameValue=employee.get("middleName");
        String lastNameValue=employee.get("lastName");

            sendText(addEmployee.firstNameField, firstNameValue);
            sendText(addEmployee.middleNameField, middleNameValue);
            sendText(addEmployee.lastNameField, lastNameValue);

            click(addEmployee.saveButton);

            //till this point one employee has been added only
            Thread.sleep(2000);
            //verify the is home-work

            click(dashboard.addEmployeeOption);

            Thread.sleep(2000);


        }

    }

    @When("user adds multiple employee from excel using {string} and verify it")
    public void user_adds_multiple_employee_from_excel_using_and_verify_it(String sheetName) throws InterruptedException {
      List<Map<String,String>> empFromExcel= ExcelReader.excelListIntoMap(Constants.TESTDATA_FILEPATH,sheetName);
//it returns one map from list of maps
        Iterator<Map<String,String>>itr=empFromExcel.iterator();
        while (itr.hasNext()){
            //it returns the key and value for employee from excel
            Map<String,String> mapNewEmp=itr.next();

            sendText(addEmployee.firstNameField, mapNewEmp.get("firstName"));
            sendText(addEmployee.middleNameField, mapNewEmp.get("middleName"));
            sendText(addEmployee.lastNameField, mapNewEmp.get("lastName"));
            String empIdValue= addEmployee.empIdLocator.getAttribute("value");

            sendText(addEmployee.photograph, mapNewEmp.get("photograph"));

            if(!addEmployee.checkBox.isSelected()){
                click(addEmployee.checkBox);
            }
            sendText(addEmployee.createusernameField, mapNewEmp.get("username"));
            sendText(addEmployee.createpasswordField, mapNewEmp.get("password"));
            sendText(addEmployee.confirmpasswordField, mapNewEmp.get("confirmPassword"));



            click(addEmployee.saveButton);
            //verification is in home-work
            Thread.sleep(2000);

            click(dashboard.empListOption);
            //to search the employee, we use emp id what we captured from attribute
            sendText(employeeList.empSearchIdField,empIdValue);
            click(employeeList.searchButton);

            //verify the employee added from the excel file

            List<WebElement>rowData=driver.findElements(By.xpath("//*[@id='resultTable']/tbody/tr"));

            for (int i = 0; i < rowData.size(); i++) {
                System.out.println("I'm inside the loop");
                //getting the text of every element from here and storing it into string
              String rowText=  rowData.get(i).getText();
                System.out.println(rowText);
                String expectedData=empIdValue+" "+mapNewEmp.get("firstName")+" "+mapNewEmp.get("middleName")+" "+mapNewEmp.get("lastName");
                Assert.assertEquals(expectedData,rowText);
            }



            Thread.sleep(2000);
            click(dashboard.addEmployeeOption);
            Thread.sleep(2000);

        }

    }

    @When("user captures employee id")
    public void user_captures_employee_id() {
        id=addEmployee.empIdLocator.getAttribute("value");
    }

    @Then("added employee is displayed in database")
    public void added_employee_is_displayed_in_database() {

        String query=DatabaseSteps.getFnameLnameQuery()+id;

        //System.out.println(query);

        List<Map<String, String>> dataFromDatabase=DBUtility.getListOfMapsFromRset(query);

        System.out.println(dataFromDatabase);

       String fNameFromDb= dataFromDatabase.get(0).get("emp_firstname");
        String lNameFromDb= dataFromDatabase.get(0).get("emp_lastname");

        Assert.assertEquals(fName,fNameFromDb);
        Assert.assertEquals(lName, lNameFromDb);
    }

    @When("added employee is available in my database")
    public void added_employee_is_available_in_my_database() {
    String query= "select * from hs_hr_employees where employee_id='"+id+"'";
        List<Map<String, String>> dataFromDatabase=DBUtility.getListOfMapsFromRset(query);

        System.out.println(dataFromDatabase);

        String fNameFromDb= dataFromDatabase.get(0).get("emp_firstname");
        String lNameFromDb= dataFromDatabase.get(0).get("emp_lastname");
        String empId=dataFromDatabase.get(0).get("employee_id");

        Assert.assertEquals("Oliver",fNameFromDb);
        Assert.assertEquals("Kahn", lNameFromDb);
        Assert.assertEquals(id,empId);
    }


}


