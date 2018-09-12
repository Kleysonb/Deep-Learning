import Neuronio.java;

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

    // Erro da Saída
    private double ErroSaida[];

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
    private double erroTotal;

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

        this.ErroSaida = new double[this.qtdNeuroniosSaida];

        this.erroTotal = 0;

        this.inicializarPesos();
    }

    private void inicializarPesos() {
        for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
            for (int entrada = 0; entrada < this.qtdEntradas; entrada++) {
                this.PesoEntradaOculta[neuronioOculto][entrada] = this.fRandom(-1, 1);
            }
        }

        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
                this.PesoOcultaSaida[neuronioSaida][neuronioOculto] = this.fRandom(-1, 1);
            }
        }
    }

    private double fRandom(int min, int max) {
        return Math.random() * (max - min) + min;
    }

    public void aprendizagem() {
        for (int itera = 0; itera < this.iteracao; itera++) {
            for (int amostra = 0; amostra < this.X.length; amostra++) {
                this.Forward(amostra);
            }
        }
    }

    private void Forward(int amostra) {
        // Calculando Entrada e Saída da Camada Oculta
        for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
            this.EntradaOculta[neuronioOculto] = Neuronio.Propagacao(this.X[amostra],
                    this.PesoEntradaOculta[neuronioOculto]);
            this.SaidaOculta[neuronioOculto] = Neuronio.tanh(this.EntradaOculta[neuronioOculto], this.elasticidade,
                    this.theta);
        }

        // Calculando Entrada e Saída da Camada de Saída
        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {
            this.EntradaSaida[neuronioSaida] = Neuronio.Propagacao(this.SaidaOculta,
                    this.PesoOcultaSaida[neuronioSaida]);
            this.SaidaFinal[neuronioSaida] = Neuronio.tanh(this.EntradaSaida[neuronioSaida], this.elasticidade,
                    this.theta);
        }
    }

    private void Backward(int amostra) {

        for (int neuronioSaida = 0; neuronioSaida < this.qtdNeuroniosSaida; neuronioSaida++) {

            this.ErroSaida[neuronioSaida] = (this.classe[amostra] - this.SaidaFinal[neuronioSaida])
                    * Neuronio.tanh(this.EntradaSaida[neuronioSaida], this.elasticidade, this.theta);

            for (int neuronioOculto = 0; neuronioOculto < this.qtdNeuroniosOculta; neuronioOculto++) {
                this.PesoOcultaSaida[neuronioOculto][neuronioSaida] += this.ErroSaida[neuronioSaida]
                        * this.taxa_aprendizagem * this.SaidaOculta[neuronioOculto];
            }
        }

        // Atualizar Pesos da Última Camada

    }

    public void atualizarPesoSaida(int amostra, int neuronioSaida) {

    }

    public static void main(String[] args) {

    }
}