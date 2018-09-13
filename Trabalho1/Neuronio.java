public class Neuronio {

    public static double FuncaoDegrau(double u) {
        return u <= 0 ? 0 : 1;
    }

    public static double tanh(double x) {
        return Math.tanh(x);
    }

    public static double derivadaTanh(double x) {
        return 1- Math.pow(Math.tanh(x), 2);
    }
  
    public static double Propagacao(double X[], double w[]) {
        double u = 0;
        for (int j = 0 ; j < X .length; j++) { // Percorrendo cada atributo
            u += X[j] * w[j]; // Função de Acumulação ou Combinador Linear
        }
        return u;
    }
}               