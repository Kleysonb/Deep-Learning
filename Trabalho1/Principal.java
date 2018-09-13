import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

public class Principal {

    public double[][] entradas;
    public double[][] classe;

    private void popularTxt(String pasta) throws IOException {
        File imagens[] = GetPixels.visualizarArquivos(pasta);
        for (int i = 0; i < 1000; i++) {
            System.out.println((i + 1) + "/" + imagens.length);
            File arquivo = new File("../mnist_png-master/mnist_png/training/" + pasta + "/" + imagens[i].getName());
            BufferedImage imagem = ImageIO.read(arquivo);
            int[][] matrizPixels = GetPixels.gerarMatrizPixel(imagem);
            int[][] matrizBinaria = GetPixels.converterParaBinaria(matrizPixels);
            int[] vetorImagem = GetPixels.matrizParaVetor(matrizBinaria);
            GetPixels.escreverVetor(vetorImagem, pasta);
        }
    }

    private void recuperarEntradas() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("dataset/treinamento.txt"));
        this.entradas = new double[10000][784];
        this.classe = new double[10000][9];

        int cont = 0;
        int flag;
        while (br.ready()) {
            String linha = br.readLine();
            flag = 0;
            for (int caractere = 0; caractere < linha.length(); caractere++) {
                if (linha.charAt(caractere) == '1') {
                    this.entradas[cont][flag] = -1;
                    flag++;
                } else {
                    if (linha.charAt(caractere) == '0') {
                        this.entradas[cont][flag] = 1;
                        flag++;
                    }
                }
            }

            if (cont < 1000) {
                double aux[] = { 1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
                this.classe[cont] = aux;
            } else {
                if (cont < 2000) {
                    double aux[] = { -1, 1, -1, -1, -1, -1, -1, -1, -1, -1 };
                    this.classe[cont] = aux;
                } else {
                    if (cont < 3000) {
                        double aux[] = { -1, -1, 1, -1, -1, -1, -1, -1, -1, -1 };
                        this.classe[cont] = aux;
                    } else {
                        if (cont < 4000) {
                            double aux[] = { -1, -1, -1, 1, -1, -1, -1, -1, -1, -1 };
                            this.classe[cont] = aux;
                        } else {
                            if (cont < 5000) {
                                double aux[] = { -1, -1, -1, -1, 1, -1, -1, -1, -1, -1 };
                                this.classe[cont] = aux;
                            } else {
                                if (cont < 6000) {
                                    double aux[] = { -1, -1, -1, -1, -1, 1, -1, -1, -1, -1 };
                                    this.classe[cont] = aux;
                                } else {
                                    if (cont < 7000) {
                                        double aux[] = { -1, -1, -1, -1, -1, -1, 1, -1, -1, -1 };
                                        this.classe[cont] = aux;
                                    } else {
                                        if (cont < 8000) {
                                            double aux[] = { -1, -1, -1, -1, -1, -1, -1, 1, -1, -1 };
                                            this.classe[cont] = aux;
                                        } else {
                                            if (cont < 9000) {
                                                double aux[] = { -1, -1, -1, -1, -1, -1, -1, -1, 1, -1 };
                                                this.classe[cont] = aux;
                                            } else {
                                                if (cont < 10000) {
                                                    double aux[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, 1 };
                                                    this.classe[cont] = aux;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            cont++;
        }
        br.close();
    }

    public static void main(String[] args) throws IOException {
        Principal principal = new Principal();
        principal.recuperarEntradas();

        RedeNeural rn = new RedeNeural(principal.entradas, principal.classe, 0.2, 1, 1, 784, 16, 10, 10000);
        rn.aprendizagem();
    }
}