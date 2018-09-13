// package xor_eduardo;

/**************************************
 * Trabalho realizado por Eduardo Ribeiro Universidade Federal de Uberlondia
 * Mestrado em Ciência da Computaooo Qualquer problema, sugestoo, etc.:
 * ufg.eduardo@gmail.com
 *
 * @author PROPP
 *
 */
public class BPN2 {

	// declarando as constantes utilizadas como entradas e saodas da rede
	public static final double VERDADEIRO = 1;
	public static final double NEUTRO = 0.0;
	public static final double FALSO = -1;

	// declarando o ErroTotal a ser calculado durante a propagaooo
	public double ErroTotal;

	// declarando as varioveis utilizadas na rede
	public double inpA[]; /* Sinais de Entrada da Rede */
	private double hidA[]; /* Sinais de Saoda de cada Neuronio da Camada escondida */
	private double hidN[]; /* Vetor de saodas para cada neuronio da Camada escondida */
	private double hidW[][]; /*
								 * Pesos entre a camada de entrada e a camada escondida [Camada
								 * Escondida][Neuronios de entrada]
								 */
	public double outA[]; /* Saoda da Rede */
	private double outN[]; /* Soma dos Produtos da Saoda da Rede */
	private double outD[]; /* Erro da Saoda */
	private double outW[][]; /* Pesos da camada de saoda [Neuronios de Saoda][Camada escondida] */
	private int Ninp; /* Nomero de Neuronios da camada de entrada */
	private int Nhid; /* Nomero de Neuronios da camada escondida */
	private int Nout; /* Nomero de Neuronios da camada de saoda */
	public double eida; /* Taxa de aprendizado */
	private double theta; /* Limiar da funooo Sigmoide */
	private double elast; /* Elasticidade da funooo Sigmoide */

	/*************************************************************************************************************
	 * construtor da classe *
	 **************************************************************************************************************/

	public BPN2(int i, int h, int o, double ei, double th, double el) {
		// motodo construtor da classe que recebe os seguintes argumentos:
		// i - Nomero de Neuronios da Camada de Entrada
		// h - Nomero de Neuronios da Camada Escondida
		// o - Nomero de Neuronios da Camada de Saoda
		// ei - Taxa de Aprendizado
		// th - Limiar
		// el - Elasticidade

		// atribuindo os valores de entrada os varioveis da rede
		Ninp = i;
		Nhid = h;
		Nout = o;

		// atribuindo os vetores com os tamanhos fornecidos pela chamada do motodo
		// constutor
		this.inpA = new double[i]; /* Sinais de Entrada da Rede */

		this.hidW = new double[h][i]; /*
										 * Pesos entre a camada de entrada e a camada escondida [No Camada Escondida][No
										 * Entrada]
										 */

		this.hidA = new double[h]; /* Sinais de Saoda de cada Neuronio da Camada escondida */
		this.hidN = new double[h]; /* Soma dos Produtos da camada escondida */

		this.outW = new double[o][h]; /* Pesos da camada de saoda [No Saoda][No Camada Escondida] */

		this.outA = new double[o]; /* Saoda da Rede */
		this.outN = new double[o]; /* Soma dos Produtos da Saoda da Rede */
		this.outD = new double[o]; /* Erro da Saoda */

		// atribuindo os valores de entrada os varioveis da rede
		eida = ei;
		theta = th;
		elast = el;

		// iniciando todos os vetores com valores aleatorios
		this.inicia();

	}

	/*************************************************************************************************************
	 * Motodo para inicializar o vetores *
	 **************************************************************************************************************/
	public void inicia() {

		int i, m; // varioveis auxiliares

		for (i = 0; i < Ninp; i++) // percorre todos os neuronios da camada de entrada
			inpA[i] = frandom(-1.0, 1.0); // sinais de entrada da rede inicializados com valores randomicos entre -1 e 1
		for (i = 0; i < Nhid; i++) { // percorre todos os neuronios da camada escondida
			hidA[i] = frandom(-1.0, 1.0); // sinais de Saoda da camada escondida inicializados com valores randomicos
											// entre -1 e 1
			for (m = 0; m < Ninp; m++) // percorre todos os pesos entre a camada de entrada e a camada escondida
				hidW[i][m] = frandom(-1.0, 1.0); // pesos entre a camada de entrada e a camada escondida inicializados
													// com valores randomicos entre -1 e 1
		}
		for (i = 0; i < Nout; i++) // percorre todos os neuronios da camada de saoda
			for (m = 0; m < Nhid; m++) //// percorre todos os pesos entre a camada escondida e a camada de entrada
				outW[i][m] = frandom(-1.0, 1.0); // pesos entre a camada escondida e a camada de saoda inicializados com
													// valores randomicos entre -1 e 1

		ErroTotal = 0.0; // inicializa o Erro Total com 0;
	}

	/*************************************************************************************************************
	 * Motodo para gerar valores aleatorios entre o intervalo: [min][max] *
	 **************************************************************************************************************/
	public double frandom(double min, double max) {
		return Math.random() * (max - min) + min;
	}

	/*************************************************************************************************************
	 * Motodo do Aprendizado Neural *
	 **************************************************************************************************************/
	public void aprendizado(double[] in, double out[]) throws ArrayIndexOutOfBoundsException {
		// motodo que o chamado pela classe principal para realizar o aprendizado e que
		// recebe os seguintes argumentos:
		// in[] um sinal de entrada qualquer, por EX: in = {1, 1, 1} (no caso de dois
		// neuronios na camada de entrada + o bias - 1 elemento do vetor)
		// out [] target, por Ex: out = {1} (no caso de um neuronio na camada de saoda)
		int i, j; // varioveis auxiliares
		if (in.length != Ninp) // caso o tamanho do vetor de entradas seja diferente com p nomero de neuronios
								// da camada de entradas
			throw new ArrayIndexOutOfBoundsException(
					"Erro: Tamanho do vetor de entradas noo compatovel com o nomero de entradas!");
		if (out.length != Nout) // caso o tamanho do vetor de entradas seja diferente com p nomero de neuronios
								// da camada de entradas
			throw new ArrayIndexOutOfBoundsException(
					"Erro: Tamanho do vetor de saodas noo compatovel com o nomero de saodas!");

		for (i = 0; i < Ninp; i++) // percorrendo os neuronios da camada de entrada
			inpA[i] = in[i]; // o sinal de entrada do neuronio i recebe a entrada i
		for (i = 0; i < Nout; i++) // percorrendo os neuronios da camada de saoda
			outA[i] = out[i]; // o target do neuronio i recebe o target i

		this.feedForward(); // realiza a alimentaooo do sistema

		ErroTotal = 0.0; // zerando o erro
		for (j = 0; j < Nout; j++) { // percorrendo todos os neuronios da camada de saoda para calcular o erro total
			this.ErroTotal += Math.abs(this.calculaDelta(j)); // calcula o erro total de acordo com o erro de cada
																// neuronio de saoda J, e ainda atualiza os pesos da
																// camada de saoda
		}

		this.atualizaPesos(); // atualiza os pesos

	}

	/*************************************************************************************************************
	 * Funooo Para Propagar os pesos e armazenar no vetor de saodas das camadas:
	 * escondida e saoda *
	 *************************************************************************************************************/
	public void feedForward() {
		int i, j; // variaveis auxiliares
		double sum2; // somatorio para receber os sinais ponderados pelos pesos de cada neuronio
						// (como o net do caderno)

		for (i = 0; i < Nhid; i++) { // percorrendo todos os neuronio da camada escondida
			sum2 = 0.0; // inicializando o somatorio com 0
			for (j = 0; j < Ninp; j++) // percorrendo todas as entradas para cada neuronio da camada escondida
				sum2 += hidW[i][j] * inpA[j]; // realizando o somatorio
			hidN[i] = sum2; // atribuindo o resultado do somatorio no vetor de resultados para cada neuronio
							// (como o net' do caderno)
			hidA[i] = funoooSigmoide(sum2); // calculando a funooo sigmoide do somatorio e armazenando no vetor de
											// sinais de saoda da camada escondida
		}

		for (i = 0; i < Nout; i++) { // percorrendo todos os neuronios da camada de saoda
			sum2 = 0.0; // inicializando o somatorio com 0
			for (j = 0; j < Nhid; j++)
				sum2 += outW[i][j] * hidA[j]; // calculando o somatorio para a camada de saoda pegando os pesos entre a
												// camada de saoda e a camada escondida e multiplicando pelo resultado
												// de cada sinal resultante da camada escondida
			outN[i] = sum2; // armazenando o valor do somatorio no vetor de saodas do neuronio i.
		}
	}

	/*************************************************************************************************************
	 * Funooo Para Calcular o erro de cada neuronio de saoda *
	 *************************************************************************************************************/
	public double calculaDelta(int m) {
		// recebe como parometro o nomero do neuronio de saoda
		int i;
		// o erro de saoda o calculado a partir do target OutA subtraodo com a saoda
		// obtida
		outD[m] = (outA[m] - funoooSigmoide(outN[m])) * (d1sigmoid(outN[m]) + 0.1);

		for (i = 0; i < Nhid; i++) // percorrendo todos os neuronios da camada escondida
			outW[m][i] += outD[m] * hidA[i] * eida; // calculando os novos pesos para servirem de parametro de ajuste
													// para os pesos da camada de saoda

		return outD[m]; // retornando o erro para o neuronio de saoda m
	}

	/*************************************************************************************************************
	 * Funooo Sigmoide *
	 **************************************************************************************************************/
	private double funoooSigmoide(double x) {
		// recebe como argumento a saoda x do neuronio

		// colculo da funooo
		double sig = (1.0 / (1.0 + Math.exp(-1.0 * elast * x + theta)) * 2.0 - 1.0);

		return sig;
	}

	/*************************************************************************************************************
	 * Funooo D1 Sigmoide *
	 **************************************************************************************************************/
	private double d1sigmoid(double x) {
		// double sig = sigmoid(n,x);

		return 2.0 * Math.exp(-1.0 * elast * x - theta) / (1 + Math.exp(-2.0 * elast * x - theta));
	}

	/*************************************************************************************************************
	 * Funooo para atualizar os pesos *
	 **************************************************************************************************************/
	public void atualizaPesos() {
		int i, m; // varioveis auxuliares
		double sum2; // somatorio

		for (m = 0; m < Nhid; m++) { // percorrendo todos os neuronios da camada escondida para atulizar seus pesos
			sum2 = 0.0; // zerando o somatorio
			for (i = 0; i < Nout; i++) { // percorrendo os neuronios da camada de saoda para calcular o somatorio
				sum2 += outD[i] * outW[i][m]; // realiza o somatorio ponderado com as entradas
			}
			;
			sum2 *= d1sigmoid(hidN[m]); // aplica a funooo sigmoide
			for (i = 0; i < Ninp; i++) // percorre os neuronio de entrada
				hidW[m][i] += eida * sum2 * inpA[i]; // atualiza os pesos entre a camada escondida e a camada de saoda
		}
	}

	/*************************************************************************************************************
	 * Funooo para propagar os pesos com as entradas e retornar o valor final das
	 * saodas *
	 **************************************************************************************************************/
	public void propagacao(double[] vetorx) throws ArrayIndexOutOfBoundsException {
		// recebe como parometro o vetor de entradas X um sinal de entrada qualquer, por
		// EX: in = {1, 1, 1} (no caso de dois neuronios na camada de entrada + o bias -
		// 1 elemento do vetor)
		int i, j; // varioveis auxiliares
		double sum2; // somatorio

		if (vetorx.length != Ninp) // verificando se o vetor de entradas o compatovel com o nomero de neuronios de
									// entrada
			throw new ArrayIndexOutOfBoundsException("Erro: Tamanho do vetor noo compatovel com o nomero de entradas!");

		for (i = 0; i < Ninp; i++) // percorrendo os neuronios de entrada
			inpA[i] = vetorx[i]; // atribuindo o valor da entrada para o neuronio de entrada

		for (i = 0; i < Nhid; i++) { // percorrendo os neuronios da camada escondida
			sum2 = 0.0; // zerando o somatorio
			for (j = 0; j < Ninp; j++) // percorrendo cada neuronio da camada de entrada
				sum2 += hidW[i][j] * inpA[j]; // calculando o somatorio
			hidA[i] = funoooSigmoide(sum2); // aplicando a funooo sigmoide e armazenando no vetor de resultados
			// System.out.println("Hida["+i+"]:"+hidA[i]);
		}
		for (i = 0; i < Nout; i++) { // percorrendo os neuronios da camada de saoda
			sum2 = 0.0; // zerando o somatorio
			for (j = 0; j < Nhid; j++) // percorrendo os neuronios da camada escondida
				sum2 += outW[i][j] * hidA[j]; // realizando o somaorio
			outA[i] = funoooSigmoide(sum2); // aplicando a funooo sigmoide e armazenando no vetor de resultados
		}
	}

}
