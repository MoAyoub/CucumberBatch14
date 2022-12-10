package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import utils.CommonMethods;

import java.util.ArrayList;
import java.util.List;

public class DashboardSteps extends CommonMethods {
    @Then("user verify dashboard page")
    public void user_verify_dashboard_page() {
        System.out.println("I'm dashboard code ");
    }

    @Then("user verify all the dashboard tabs")
    public void user_verify_all_the_dashboard_tabs(DataTable dataTable) {
        List<String> expectedTabs=dataTable.asList();
        List<String> actualTabs=new ArrayList<>();

        for (WebElement ele:dashboard.dashboardTabs) {
            actualTabs.add(ele.getText());

        }
//printing the text for bothfeature file and from applications
        System.out.println(expectedTabs);
        System.out.println(actualTabs);

        //.equal is the method we use to compare 2 lists together
        //aserTrue is a boolean condition which returns true if condidtion is satisty
        Assert.assertTrue(expectedTabs.equals(actualTabs));

    }



}
