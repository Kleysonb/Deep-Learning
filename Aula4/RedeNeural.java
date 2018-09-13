// import Neuronio.java;

public class RedeNeural {

    // Amostras de Treinamento
    private double X[][];
    // Saída para cada amostra
    private double classe[];

    // Matriz de Pesos entre a camada de entrada e a camada oculta
    private double PesoEntradaOculta[][];
    // Matriz de Pesos entra a camada oculta e a camada de saída
    private double PesoOcultaSaida[][];

    // Potencial de Ativação (Entrada da camada oculta)
    private double EntradaOculta[];
    // Potencial de Ativação (Entrada da camada de saída)
    private double EntradaSaida[];

    // Saída da camada oculta
    private double SaidaOculta[];
    // Saída da camada de saída
    private double SaidaFinal[];

    // Sigma da Saída
    private double SigmaSaida[];
    // Sigmas da Camada Oculta
    private double SigmaOculto[];

    // Taxa de aprendizagem
    private double taxa_aprendizagem;

    // Limiar da funcao sigmoide
    public double theta;
    // Elasticidade da funcao sigmoide
    public double elasticidade;

    // Contador de Iterações
    private int iteracao;

    private int qtdEntradas;
    private int qtdNeuroniosOculta;
    private int qtdNeuroniosSaida;

    // Erro Total
    // private double erroTotal;

    RedeNeural(double X[][], double classe[], double taxa_aprendizagem, double theta, double elasticidade,
            int qtdEntradas, int qtdNeuroniosOculta, int qtdNeuroniosSaida, int iteracao) {

        this.X = X;
        this.classe = classe;
        this.taxa_aprendizagem = taxa_aprendizagem;
        this.theta = theta;
        this.elasticidade = elasticidade;
        this.qtdEntradas = qtdEntradas;
        this.qtdNeuroniosOculta = qtdNeuroniosOculta;
        this.qtdNeuroniosSaida = qtdNeuroniosSaida;
        this.iteracao = iteracao;

        this.PesoEntradaOculta = new double[this.qtdNeuroniosOculta][this.qtdEntradas];
        this.PesoOcultaSaida = new double[this.qtdNeuroniosSaida][this.qtdNeuroniosOculta];

        this.SaidaOculta = new double[this.qtdNeuroniosOculta];
        this.SaidaFinal = new double[this.qtdNeuroniosSaida];

        this.EntradaOculta = new double[this.qtdNeuroniosOculta];
        this.EntradaSaida = new double[this.qtdNeuroniosSaida];

        this.SigmaSaida = new double[this.qtdNeuroniosSaida];
        this.SigmaOculto = new double[this.qtdNeuroniosOculta];

        // this.erroTotal = 0;

        this.inicializarPesos();
    }

    private void inicializarPesos() {
        for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
            for (int entrada = 0; entrada < this.qtdEntradas; entrada++) {
                this.PesoEntradaOculta[neuronioOculto][entrada] = this.fRandom(-1, 1);
                // this.PesoEntradaOculta[neuronioOculto][entrada] = 0;
            }
        }

        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
                this.PesoOcultaSaida[neuronioSaida][neuronioOculto] = this.fRandom(-1, 1);
                // this.PesoOcultaSaida[neuronioSaida][neuronioOculto] = 0;
            }
        }

        // this.mostrarPesos();
    }

    private double fRandom(int min, int max) {
        return Math.random() * (max - min) + min;
    }

    public void aprendizagem() {
        for (int itera = 0; itera < this.iteracao; itera++) {
            System.out.println((itera + 1) + "/" + this.iteracao);
            for (int amostra = 0; amostra < this.X.length; amostra++) {
                this.Forward(amostra);
                this.Backward(amostra);
                // System.out.println();
                // this.mostrarPesos();
            }
            // this.mostrarErroTotal();
        }
        this.mostrarPesos();
    }

    public void mostrarErroTotal() {
        double erroTotal = 0;
        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            erroTotal += this.SigmaSaida[neuronioSaida];
        }
        System.out.println("Erro Total = " + erroTotal);
    }

    public void mostrarPesos() {
        System.out.println("Pesos entre a entrada e a camada oculta: ");
        for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
            for (int entrada = 0; entrada < this.qtdEntradas; entrada++) {
                System.out.print(this.PesoEntradaOculta[neuronioOculto][entrada] + " ");
            }
        }

        System.out.println("\nPesos entre a camada oculta e a saída: ");
        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
                System.out.print(this.PesoOcultaSaida[neuronioSaida][neuronioOculto] + " ");
            }
        }
        System.out.println("\n\n");


        for (int amostra = 0; amostra < this.X.length; amostra++) {
            this.ForwardTeste(amostra);
        }

    }

    private void ForwardTeste(int amostra) {
        // Calculando Entrada e Saída da Camada Oculta
        double aux;
        // Calculando Entrada e Saída da Camada Oculta
        for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
            this.EntradaOculta[neuronioOculto] = Neuronio.Propagacao(this.X[amostra],
                    this.PesoEntradaOculta[neuronioOculto]);

            aux = Neuronio.tanh(this.EntradaOculta[neuronioOculto]);
            // System.out.println(aux);
            if (aux > 0)
                this.SaidaOculta[neuronioOculto] = 1;
            else
                this.SaidaOculta[neuronioOculto] = -1;

            // System.out.println(this.SaidaOculta[neuronioOculto]);
        }

        // Calculando Entrada e Saída da Camada de Saída
        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            this.EntradaSaida[neuronioSaida] = Neuronio.Propagacao(this.SaidaOculta,
                    this.PesoOcultaSaida[neuronioSaida]);
            aux = Neuronio.tanh(this.EntradaSaida[neuronioSaida]);
            if (aux > 0)
                this.SaidaFinal[neuronioSaida] = 1;
            else
                this.SaidaFinal[neuronioSaida] = -1;

            System.out.println(this.SaidaFinal[neuronioSaida]);
        }

    }

    private void Forward(int amostra) {

        double aux;
        // Calculando Entrada e Saída da Camada Oculta
        for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
            this.EntradaOculta[neuronioOculto] = Neuronio.Propagacao(this.X[amostra],
                    this.PesoEntradaOculta[neuronioOculto]);

            aux = Neuronio.tanh(this.EntradaOculta[neuronioOculto]);
            // System.out.println(aux);
            if (aux > 0)
                this.SaidaOculta[neuronioOculto] = 1;
            else
                this.SaidaOculta[neuronioOculto] = -1;

            // System.out.println(this.SaidaOculta[neuronioOculto]);
        }

        // Calculando Entrada e Saída da Camada de Saída
        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            this.EntradaSaida[neuronioSaida] = Neuronio.Propagacao(this.SaidaOculta,
                    this.PesoOcultaSaida[neuronioSaida]);
            aux = Neuronio.tanh(this.EntradaSaida[neuronioSaida]);
            if (aux > 0)
                this.SaidaFinal[neuronioSaida] = 1;
            else
                this.SaidaFinal[neuronioSaida] = -1;
        }
    }

    private void Backward(int amostra) {
        // Atualizando os pesos entre a camada oculta e a camada de saída
        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            this.SigmaSaida[neuronioSaida] = this.calcularSigma(amostra, neuronioSaida);
            for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
                this.PesoOcultaSaida[neuronioSaida][neuronioOculto] += (this.taxa_aprendizagem
                        * this.SigmaSaida[neuronioSaida] * this.SaidaOculta[neuronioOculto]);
                // this.PesoOcultaSaida[neuronioSaida][neuronioOculto] +=
                // (this.taxa_aprendizagem
                // * this.SigmaSaida[neuronioSaida]);
            }
        }

        // Atualizando os pesos entre a entrada e a camada oculta
        for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
            this.SigmaOculto[neuronioOculto] = this.calcularSigmaOculto(amostra, neuronioOculto);
            for (int entrada = 0; entrada < this.qtdEntradas; entrada++) {
                this.PesoEntradaOculta[neuronioOculto][entrada] += (this.taxa_aprendizagem
                        * this.SigmaOculto[neuronioOculto] * this.X[amostra][entrada]);
                // this.PesoEntradaOculta[neuronioOculto][entrada] += (this.taxa_aprendizagem
                // * this.SigmaOculto[neuronioOculto]);
            }
        }
    }

    private double calcularSigmaOculto(int amostra, int neuronio) {
        double sigmaOculta = 0;
        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            sigmaOculta += (this.SigmaSaida[neuronioSaida] * this.PesoOcultaSaida[neuronioSaida][neuronio]);
        }
        sigmaOculta *= Neuronio.derivadaTanh(this.EntradaOculta[neuronio]) * -1;
        // sigmaOculta *= Neuronio.derivadaSigmoid(this.EntradaOculta[neuronio],
        // this.elasticidade, this.theta);
        return sigmaOculta;
    }

    private double calcularSigma(int amostra, int neuronio) {
        return (this.classe[amostra] - this.SaidaFinal[neuronio])
                * (Neuronio.derivadaTanh(this.EntradaSaida[neuronio]));
        // return (this.classe[amostra] - this.SaidaFinal[neuronio]);
    }

    public static void main(String[] args) {
        double VERDADEIRO = 1;
        double NEUTRO = 0.0;
        double FALSO = -1;

        double amostras[][] = { { FALSO, FALSO, 1 }, { FALSO, VERDADEIRO, 1 }, { VERDADEIRO, FALSO, 1 },
                { VERDADEIRO, VERDADEIRO, 1 } };
        double classes[] = { FALSO, VERDADEIRO, VERDADEIRO, FALSO };
        // double classes[] = { FALSO, FALSO, FALSO, VERDADEIRO };

        RedeNeural redeNeural = new RedeNeural(amostras, classes, 0.02, 1, 2, 3, 4, 1, 10000);
        redeNeural.aprendizagem();
    }
}