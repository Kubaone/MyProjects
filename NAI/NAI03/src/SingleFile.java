import java.util.List;

public class SingleFile {

    private List<Double> parameters;
    private String properFileLanguage;

    public SingleFile(List<Double> fileContent, String properFileLanguage) {
        this.parameters = fileContent;
        this.properFileLanguage = properFileLanguage;
    }

    public String getProperFileLanguage() {
        return properFileLanguage;
    }

    public List<Double> getParameters() {
        return parameters;
    }
}
