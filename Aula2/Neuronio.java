public class Neuronio {

    public static void Propagacao(double X[][], double classe[], double w[], double y[], double u[]){
        for(int i = 0; i < X.length; i++){ // Percorrer os exemplos de entradas
            for(int j = 0; j < X[i].length; j++){ // Percorrendo cada atributo
                u[i] += X[i][j] * w[j];    // Função de Acumulação ou Combinador Linear
            }

            // Limiar de Ativação
            if(u[i] <= 0) y[i] = 0;
            else y[i] = 1;
            
            System.out.println("y(" + i + "): " + y[i]);

            if(y[i] == classe[i]) System.out.println("Acertou");
            else System.out.println("Errou");
        }
    }

    public static void main(String[] args){
        // Entrada para Funções And
        double X[][] = {{0, 0, 1}, {0, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        double classe[] = {0, 0, 0, 1};

        // Pesos
        double w[] = {0, 0, 0};
        // double w[] = {-1, 1, 0.5};
        // double w[] = {1, -1, 0.5};

        // Sinal de Saída
        double y[] = new double[X.length];

        // Potencial de Ativação
        double u[] = new double[X.length];

        Neuronio.Propagacao(X, classe, w, y, u);
    }
}