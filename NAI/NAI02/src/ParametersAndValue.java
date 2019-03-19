import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParametersAndValue {
    private final List<Double> parameters;
    private final String value;

    public ParametersAndValue(String line) {

        List<String> tokens = Arrays.stream(line.split("\\s+"))
                .map(s -> s.replaceAll(",", "."))
                .filter(s-> !s.isEmpty())
                .collect(Collectors.toList());

        value = tokens.remove(tokens.size() - 1);
        parameters = tokens.stream()
                .map(Double::parseDouble).collect(Collectors.toList());
    }
    public List<Double> getParameters() {
        return parameters;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return parameters + " " + value;
    }
}
