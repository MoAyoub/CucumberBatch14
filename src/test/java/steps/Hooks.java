package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.CommonMethods;

public class Hooks extends CommonMethods {
    @Before //use io.cucumber.java to import this hook
    public void preCondition(){
        openBrowserAndLaunchApplication();
    }


    //we use special class called scenrio class from cucumber
    //this class holds the complete information of your execution

    @After
    public void postCondition(Scenario scenario){
        byte[] pic;
        //failed screenshot will be available in this folder
if(scenario.isFailed()){
    pic=takeScreenshot("failed/"+scenario.getName());


}else {
    pic=takeScreenshot("passed/"+scenario.getName());
}

//to attach the screenshot in our report
        scenario.attach(pic,"image/png", scenario.getName() );


        closeBrowser();
    }


}
