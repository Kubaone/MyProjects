import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main (String ... args){
        Perceptron perceptron = new Perceptron(0.5,Math.random());

        try {
            // w argumentach 1 i 2 programu podajemy ścieżkę plików txt
            //jako 3 argument podajemy nazwę kwiatka względem którego chcemy trenować perceptron
            final List<ParametersAndValue> trainingRecords= Files.lines(Paths.get(System.getProperty("user.dir") + args[0])).map(ParametersAndValue::new).collect(Collectors.toList());
            final List<ParametersAndValue> testRecords= Files.lines(Paths.get(System.getProperty("user.dir") + args[1])).map(ParametersAndValue::new).collect(Collectors.toList());
            final String flower = args[2];


            while (perceptron.getAccuracy()!=1.0){
                perceptron.resetTests();
                Collections.shuffle(trainingRecords);
                trainingRecords.forEach(
                        record->{
                            perceptron.learn(record.getParameters(), flower.equals(record.getValue()) ? 1 : 0);
                            //System.out.println(flower + " " + record.getValue());
                        }

                );
                System.out.println("Positive tests : " + perceptron.getPositiveTests() + " out of " + (perceptron.getPositiveTests()+perceptron.getNegativeTests()));
                System.out.println("Accuracy : " + perceptron.getAccuracy());
            }

            System.out.println("Perceptron wyuczony");

            perceptron.resetTests();
            testRecords.forEach(
                    record-> perceptron.test(record.getParameters(), flower.equals(record.getValue()) ? 1 : 0)
            );
            System.out.println("Positive tests : " + perceptron.getPositiveTests() + " out of " + (perceptron.getPositiveTests()+perceptron.getNegativeTests()));
            System.out.println("Accuracy : " + perceptron.getAccuracy());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to write new vector? [Y/N]");
        String decision=sc.nextLine();

        while (decision.equals("Y")){
            System.out.println("Write 4 attributes divided by space");
            String values = sc.nextLine();

            if (decision.equals("Y")) {
                perceptron.test(Arrays.stream(values.split("\\s"))
                        .map(Double::parseDouble)
                        .collect(Collectors.toList()), 111);
            }
            if (perceptron.getActualActivity())
                System.out.println("Given vector is " + args[2]);
            else
                System.out.println("Given vector is not " + args[2]);

            System.out.println("Do you want to write new vector? [Y/N]");
            decision = sc.nextLine();

        }




    }

}
