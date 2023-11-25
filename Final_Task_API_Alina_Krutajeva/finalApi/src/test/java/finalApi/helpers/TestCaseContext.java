package finalApi.helpers;

import finalApi.domain.Color;
import finalApi.domain.Project;
import io.cucumber.java.Scenario;
import lombok.Getter;
import lombok.Setter;

public class TestCaseContext {

    @Setter @Getter
    private static Project project;

    @Setter @Getter
    private static Color color;

    @Setter @Getter
    private static Scenario scenario;

    public static void init(){
        project = null;
        color = null;
        scenario = null;
    }
}
