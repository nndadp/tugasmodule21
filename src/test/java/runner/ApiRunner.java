package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber-reports.json",
                "junit:target/cucumber-reports/api-test-report.xml",
                "timeline:target/cucumber-reports/timeline",
                "rerun:target/cucumber-reports/rerun.txt"},

        glue = {"stepDef"},
        features = {"src/test/java/features"},
        tags = "@api or @ui",
        monochrome = true
)
public class ApiRunner {

}
