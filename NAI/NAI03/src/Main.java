
import java.util.*;

public class Main {

    public static void main (String ... args){
        final ArrayList<SingleFile> allRecords= FileWalker.processDir(System.getProperty("user.dir")+ "\\mpp3\\Languages" );
        final ArrayList<SingleFile> trainingRecords= new ArrayList<>();
        final ArrayList<SingleFile> testRecords = new ArrayList<>();
        final ArrayList<Perceptron> perceptrons = new ArrayList<>();

        //podział zbioru danych na treningowe i testowe
        for(int i =0; i <allRecords.size(); i=i+2){
            trainingRecords.add(allRecords.get(i));
            testRecords.add(allRecords.get(i+1));
        }
        System.out.println(trainingRecords.size() + " " + testRecords.size());

        String language = "";

        //tworzenie tylu perceptronow ile mamy folderów w różnych językach
        for (int i=0; i<trainingRecords.size(); i++){
            if(!trainingRecords.get(i).getProperFileLanguage().equals(language)){
                language=trainingRecords.get(i).getProperFileLanguage();
                perceptrons.add(new Perceptron(0.5,Math.random(),language));
            }
        }

        //wyuczenie każdego perceptronu, żeby rozpoznawał przypisany sobie język
            perceptrons.forEach(perceptron -> {
                while (perceptron.getAccuracy()!=1.0){
                    perceptron.resetTests();
                    Collections.shuffle(trainingRecords);
                    trainingRecords.forEach(
                            singleFile->{
                                perceptron.learn(singleFile.getParameters(), singleFile.getProperFileLanguage());
                                //System.out.println(perceptron.getForLanguage() + " params : " + singleFile.getParameters());
                                //System.out.println(perceptron.getForLanguage() + " weight : " + perceptron.getWeights());
                            }
                    );
                    //System.out.println(perceptron.getForLanguage() + " positive tests : " + perceptron.getPositiveTests() + " out of " + (perceptron.getPositiveTests()+perceptron.getNegativeTests()));
                    //System.out.println(perceptron.getForLanguage() + " accuracy : " + perceptron.getAccuracy());
                }
                System.out.println("Perceptron " + perceptron.getForLanguage() + " wyuczony");
            });
        System.out.println("Wszystkie perceptrony wyuczone");


        //testowanie perceptronów
        perceptrons.forEach(perceptron -> {
                perceptron.resetTests();
                Collections.shuffle(testRecords);
                testRecords.forEach(
                        singleFile->{
                            perceptron.test(singleFile.getParameters(), singleFile.getProperFileLanguage());
                            //System.out.println(perceptron.getForLanguage() + " params : " + singleFile.getParameters());
                            //System.out.println(perceptron.getForLanguage() + " weight : " + perceptron.getWeights());
                        }
                );
                System.out.println(perceptron.getForLanguage() + " positive tests : " + perceptron.getPositiveTests() + " out of " + (perceptron.getPositiveTests()+perceptron.getNegativeTests()));
                System.out.println(perceptron.getForLanguage() + " accuracy : " + perceptron.getAccuracy());
                System.out.println(perceptron.getScores());

                }
        );

        final Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to write new word? [Y/N]");
        String decision=sc.nextLine();

        while (decision.equals("Y")){
            System.out.println("Write your sentence");
            final String values = sc.nextLine();

            if (decision.equals("Y")) {
                perceptrons.forEach(perceptron -> {
                    perceptron.test(FileWalker.countLetters(values), "Niepotrzebny string zamiast przeciążenia funkcji");
                    if (perceptron.getActualActivity())
                        System.out.println("Given sentence is written in " + perceptron.getForLanguage());
                    else
                        System.out.println("Given sentence is not written in  " + perceptron.getForLanguage());
                });
            }

            System.out.println("Do you want to write new sentence? [Y/N]");
            decision = sc.nextLine();

        }

    }

}
