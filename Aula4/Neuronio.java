public class Neuronio {

    public static double FuncaoDegrau(double u) {
        return u <= 0 ? 0 : 1;
    }

    public static double tanh(double x) {
        // return 2 * (Math.exp(-1 * elasticidade * x - theta) / (1 + Math.exp(-2 * elasticidade + x - theta)));
        return Math.tanh(x);
        // return 2 * Neuronio.sigmoide(x, elasticidade, theta) * (2*x) - 1
    }

    public static double derivadaTanh(double x) {
        return 1- Math.pow(Math.tanh(x), 2);
    }

    public static double sigmoide(double x, double elasticidade, double theta) {
        // recebe como argumento a saída x do neurônio
        // return (1.0 / (1.0 + Math.exp(-1.0 * elasticidade * x + theta)) * 2.0 - 1.0);
        return 1 / (1 + Math.exp(-1 * elasticidade * x - theta));
    }

    public static double derivadaSigmoid(double x, double elasticidade, double theta) {
        // return 2.0 * Math.exp(-1.0 * el asticidade * x - theta) / (1 + Math.exp(-2.0 * elasticidade * x - theta));
        return (Math.exp(-x))/(Math.pow(1+Math.exp(-x), 2));
    }          
  
    public static double Propagacao(double X[], double w[]) {
        double u = 0;
        for (int j = 0 ; j < X .length; j++) { // Percorrendo cada atributo
            u += X[j] * w[j]; // Função de Acumulação ou Combinador Linear
        }
        return u;
    }
}               