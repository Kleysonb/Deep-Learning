public class Neuronio {

    public static double FuncaoDegrau(double u) {
        return u <= 0 ? 0 : 1;
    }

    public static double tanh(double x, double elasticidade, double theta) {
        return 2 * (Math.exp(-1 * elasticidade * x - theta) / (1 + Math.exp(-2 * elasticidade + x - theta)));
    }

    private double sigmoide(double x, double elasticidade, double theta) {
        // recebe como argumento a saída x do neurônio
        // cálculo da função
        double sig = (1.0 / (1.0 + Math.exp(-1.0 * elasticidade * x + theta)) * 2.0 - 1.0);
        return sig;
    }

    private double d1sigmoid(double x, double elasticidade, double theta) {
        // double sig = sigmoid(n,x);
        return 2.0 * Math.exp(-1.0 * elasticidade * x - theta) / (1 + Math.exp(-2.0 * elasticidade * x - theta));
    }

    public static double Propagacao(double X[], double w[]) {
        double u = 0;
        for (int j = 0; j < X.length; j++) { // Percorrendo cada atributo
            u += X[j] * w[j]; // Função de Acumulação ou Combinador Linear
        }
        return u;
    }
}               