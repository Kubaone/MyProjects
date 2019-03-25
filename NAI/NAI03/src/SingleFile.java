import java.util.ArrayList;

public class SingleFile {

    private ArrayList<Double> fileContent;
    private String properFileLanguage;

    public SingleFile(ArrayList<Double> fileContent, String properFileLanguage) {
        this.fileContent = fileContent;
        this.properFileLanguage = properFileLanguage;
    }

    public String getProperFileLanguage() {
        return properFileLanguage;
    }

    public ArrayList<Double> getFileContent() {
        return fileContent;
    }
}
