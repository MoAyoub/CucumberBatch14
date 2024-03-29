package runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        //features we use to provide the path of all the feature files
        features = "src/test/resources/features/",
        glue = "steps",
        dryRun= false,
        //when you set dry run to true, it stops actual execution
        //it will quickly scan all the gherkin steps whether they are
        //implemented or not

//if you wanna run specific sprint
        tags="@database",

//to remove irrelevant information from console, you need to set monochrome to true
        monochrome = true,
    //pretty keyword prints the steps in the console to increase readability

        //to generate the reports we need plugin of runner class
        //when we generate any report this should be under target folder
        plugin = {"pretty","html:target/cucumber.html","json:target/cucumber.json","rerun:target/failed.txt"}
//this failed.txt file holds all the scenarios which are failed during execution


)

public class SmokeRunner {
}
