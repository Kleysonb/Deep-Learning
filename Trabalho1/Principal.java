import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

public class Principal {

    // private int[][] matrizPixels;

    // private void imagemMatriz(File arquivo) throws IOException {
    // BufferedImage image = ImageIO.read(arquivo);
    // this.matrizPixels = GetPixels.gerarMatrizPixel(image);
    // int[][] matrizBinaria = GetPixels.converterParaBinaria(image,
    // this.matrizPixels);
    // int[] vetorImagem = GetPixels.matrizParaVetor(matrizBinaria);
    // GetPixels.escreverVetor(vetorImagem);
    // }

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

    private double[][] recuperarEntradas() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("dataset/treino.txt"));
        double[][] entradas = new double[2000][784];
        int cont = 0;
        while (br.ready()) {
            String linha = br.readLine();
            double aux[] = new double[784];
            for (int caractere = 0; caractere < linha.length(); caractere += 2) {
                aux[caractere] = Double.parseDouble(Character.toString(linha.charAt(caractere)));
            }
            entradas[cont] = aux;
        }
        br.close();
        return entradas;
    }

    public static void main(String[] args) throws IOException{
        Principal p = new Principal();

        BPN2 rede = new BPN2(784, 392, 1, 0.2, 1, 2);
        int iteracao = 2; // inicializando a itereacao com 2
        rede.ErroTotal = 1; // inicializando o erro total com 1

        double[][] entradas = p.recuperarEntradas();
        double targets[][] = new double[2000][1];
        for (int i = 0; i < 2000; i++) {
            if (i < 1000) {
                double aux[] = { 0 };
                targets[i] = aux;
            } else {
                double aux[] = { 1 };
                targets[i] = aux;
            }
        }

        while (iteracao < 1 && rede.ErroTotal > 0.002) {
            iteracao = iteracao + 1; // incrementando a iteracao
            rede.aprendizado(entradas[iteracao % entradas.length], targets[iteracao % entradas.length]);
        }

        // i - Nomero de Neuronios da Camada de Entrada
        // h - Nomero de Neuronios da Camada Escondida
        // o - Nomero de Neuronios da Camada de Saoda
        // ei - Taxa de Aprendizado
        // th - Limiar
        // el - Elasticidade

        // try {
        // // Lendo um arquivo
        // // File imagem = new File("../mnist_png-master/mnist_png/training/0/1.png");
        // // p.imagemMatriz(imagem);
        // // GetPixels.visualizarArquivos();
        // // p.popularTxt("9");

        // } catch (IOException e) {
        // System.err.println("Erro");
        // System.out.println(e.getMessage());
        // }

    }
}