
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perceptron {

    private final ArrayList<Double> weights;
    private final Double alpha;
    private int negativeTests;
    private int positiveTests;
    private int actualActivity;


    public Perceptron (Double alpha, Double bias) {
        weights =new ArrayList<>(Arrays.asList(Math.random(),Math.random(),Math.random(),Math.random()));
        this.alpha = alpha;
        weights.add(bias);
        negativeTests=0;
        positiveTests=0;
    }

    public void learn(List<Double> parameters, int expectedActivity) {
        ArrayList<Double> parametersExtended = new ArrayList<>(parameters);
        parametersExtended.add(-1.0);
        double net = 0;
        final int actualActivity;

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

    public void test(List<Double> parameters, int expectedActivity) {
        ArrayList<Double> parametersExtended = new ArrayList<>(parameters);
        parametersExtended.add(-1.0);
        double net = 0;
        final int actualActivity;

        if (weights.size() != parametersExtended.size())
            System.err.println("Macierzy nie da się pomnożyć, będzie exception");

        for (int i = 0; i < parametersExtended.size(); i++)
            net += parametersExtended.get(i) * weights.get(i);
        //System.out.println("Wagi przed: " + weights + " Net: " +net);

        actualActivity = net > 0 ? 1 : 0;

        this.actualActivity=actualActivity;

        if (actualActivity != expectedActivity) {
            negativeTests++;
        }else
            positiveTests++;
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
    }


}
