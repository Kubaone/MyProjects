import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main (String ... args){

        final ArrayList<SingleFile> trainingRecords= FileWalker.processDir(System.getProperty("user.dir")+ "\\mpp3\\Languages" );
        final ArrayList<Perceptron> perceptrons = new ArrayList<>();



        String language = "";

        for (int i=0; i<trainingRecords.size(); i++){
            if(!trainingRecords.get(i).getProperFileLanguage().equals(language)){
                language=trainingRecords.get(i).getProperFileLanguage();
                perceptrons.add(new Perceptron(0.5,Math.random(),language));
            }
        }

        /*
        try {




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

        final Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to write new vector? [Y/N]");
        String decision=sc.nextLine();

        while (decision.equals("Y")){
            System.out.println("Write 4 attributes divided by space");
            final String values = sc.nextLine();

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

*/


    }

}
