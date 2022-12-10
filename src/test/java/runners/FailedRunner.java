package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//this runner class only for failed test cases


@RunWith(Cucumber.class)
@CucumberOptions(

        features = "@target/failed.txt",
        glue = "steps",
        monochrome = true,
        plugin = {"pretty"}
)

        public class FailedRunner {
}