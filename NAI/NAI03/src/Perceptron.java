
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Perceptron {

    private final ArrayList<Double> weights;
    private final Double alpha;
    private int x=100;

    public HashMap<String, Double> getScores() {
        return scores;
    }

    private final HashMap<String,Double> scores;
    private final String forLanguage;
    private int negativeTests;
    private int positiveTests;
    private int actualActivity;
    private double trueNegative=0;
    private double truePositive=0;
    private double falsePositive=0;
    private double falseNegative=0;


    public Perceptron (Double alpha, Double bias, String forLanguage) {
        weights =new ArrayList<>(Collections.nCopies(26,Math.random()));
        this.alpha = alpha;
        this.forLanguage = forLanguage;
        scores = new HashMap<>();
        weights.add(bias);
        negativeTests=0;
        positiveTests=0;
    }

    public void learn(List<Double> parameters, String properFileLanguage) {
        ArrayList<Double> parametersExtended = new ArrayList<>(parameters);
        parametersExtended.add(-1.0);
        double net = 0;
        final int actualActivity;
        final int expectedActivity;
        if(properFileLanguage.equals(forLanguage))
            expectedActivity=1;
        else
            expectedActivity=0;

        if (weights.size() != parametersExtended.size())
            System.err.println("Macierzy nie da się pomnożyć, będzie exception");

        for (int i = 0; i < parametersExtended.size(); i++)
            net += parametersExtended.get(i) * weights.get(i);
        //System.out.println("Wagi przed: " + weights + " Net: " +net);

        actualActivity = net > 0 ? 1 : 0;

        if (actualActivity != expectedActivity) {
            for (int i = 0; i < parametersExtended.size(); i++)
                weights.set(i, weights.get(i) + (expectedActivity - actualActivity) * alpha * parametersExtended.get(i));
            negativeTests++;
            //System.out.println("negative " + negativeTests);
        }else
            positiveTests++;
    }

    public void test(List<Double> parameters, String properFileLanguage) {
        ArrayList<Double> parametersExtended = new ArrayList<>(parameters);
        parametersExtended.add(-1.0);
        double net = 0;
        final int expectedActivity;

        expectedActivity= properFileLanguage.equals(forLanguage) ? 1 : 0;

        if (weights.size() != parametersExtended.size())
            System.err.println("Macierzy nie da się pomnożyć, będzie exception");

        for (int i = 0; i < parametersExtended.size(); i++)
            net += parametersExtended.get(i) * weights.get(i);

        actualActivity = net > 0 ? 1 : 0;

            if (actualActivity==1 && expectedActivity==1)
                truePositive++;
            if (actualActivity!=1 && expectedActivity==1)
                falseNegative++;
            if (actualActivity==1 &&expectedActivity!=1)
                falsePositive++;
            if (actualActivity!=1 && expectedActivity!=1)
                trueNegative++;

        if (actualActivity != expectedActivity)
            negativeTests++;
        else
            positiveTests++;


        scores.put("Accuracy",(truePositive+trueNegative)/(trueNegative+truePositive
            +falseNegative + falsePositive));
        scores.put("Precision",(truePositive)/(truePositive + falsePositive));
        scores.put("Recall",(truePositive)/(truePositive + falseNegative));
        scores.put("F-Miara",(2*truePositive)/(truePositive + falsePositive) * (truePositive)/(truePositive + falseNegative)/((truePositive)/(truePositive + falseNegative)+(truePositive)/(truePositive + falsePositive)));

    }

    public int getNegativeTests() {
        return negativeTests;
    }
    public int getPositiveTests() {
        return positiveTests;
    }
    public double getAccuracy() {
        return (double)positiveTests/(positiveTests +negativeTests);
    }
    public boolean getActualActivity() {
        return actualActivity == 1;
    }

    public void resetTests() {
        negativeTests=0;
        positiveTests=0;
        trueNegative=0;
        truePositive=0;
        falseNegative=0;
        falsePositive=0;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public String getForLanguage() {
        return forLanguage;
    }
}
