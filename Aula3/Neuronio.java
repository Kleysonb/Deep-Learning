public class Neuronio {

    private static double FuncaoDegrau(double u) {
        return u <= 0 ? 0 : 1;
    }

    public static double Propagacao(double X[], double w[]) {
        double u = 0;
        for (int j = 0; j < X.length; j++) { // Percorrendo cada atributo
            u += X[j] * w[j]; // Função de Acumulação ou Combinador Linear
        }
        return u;
    }

    public static void Treinamento(double X[][], double classe[], double w[], double y[], double u[],
            double taxa_aprendizagem, int epocas) {
        double taxa_erro[] = new double[X.length];
        boolean erro;
        for (int i = 0; i < epocas; i++) {
            System.out.println((i+1) + "/" + epocas);
            erro = false;
            for (int amostras = 0; amostras < X.length; amostras++) {

                u[amostras] = Neuronio.Propagacao(X[amostras], w);
                y[amostras] = Neuronio.FuncaoDegrau(u[amostras]);

                // System.out.println("u(" + amostras + "): " + u[amostras]);
                // System.out.println("y(" + amostras + "): " + y[amostras]);

                taxa_erro[amostras] = classe[amostras] - y[amostras];

                if(classe[amostras] != y[amostras]){ // Errou na classificação
                    System.out.println("Ajustando Pesos");                    
                    for(int atributo = 0; atributo < X[amostras].length; atributo++){ // Atualizar cada peso
                        w[atributo] += (taxa_aprendizagem * taxa_erro[amostras] * X[amostras][atributo]);
                    }
                    erro = true;
                }
            }
            if(!erro) break;
        }

        double erroTotal = 0;
        for (int amostras = 0; amostras < X.length; amostras++) {
            erroTotal += taxa_erro[amostras];
        }   

        System.out.println("Erro Total: " + erroTotal);
        System.out.println("Pesos: ");
        for(int peso = 0; peso < w.length; peso++){
            System.out.print(w[peso] + " ");
        }
        System.out.println();
    }

    public static double fRandom(int min, int max) {
        return Math.random() * (max - min) + min;
    }

    public static void main(String[] args){
        // Entrada para Funções Or
        double X[][] = {{0, 0, 1}, {0, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        double classe[] = {0, 1, 1, 1};

        // Pesos
        int min = 0;
        int max = 1;
        double w[] = {Neuronio.fRandom(min, max), Neuronio.fRandom(min, max), Neuronio.fRandom(min, max)};

        // Taxa de aprendizagem
        double taxa_aprendizagem = 0.02;

        // Contador de Iterações
        int epocas = 100;

        // Sinal de Saída
        double y[] = new double[X.length];

        // Potencial de Ativação
        double u[] = new double[X.length];

        Neuronio.Treinamento(X, classe, w, y, u, taxa_aprendizagem, epocas);
    }
}