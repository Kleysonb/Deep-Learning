import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

public class GetPixels {
    public static int[][] gerarMatrizPixel(BufferedImage img) {
        //Recuperando Altura e Largura da Imagem
        int altura = img.getWidth();
        int largura = img.getHeight();
        // System.out.println("Altura: " + altura + "\nLargura: " + largura + "\n");

        //Criando matriz que receberá os pixels da imagem real
        int matrizPixel[][] = new int[altura][largura];
        //Populando matriz com os pixels
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                matrizPixel[i][j] = img.getRGB(i, j);
                // System.out.print(" " + matrizPixel[i][j]);
            }
            // System.out.println();
        }

        return matrizPixel;
    }

    public static int[][] converterParaBinaria(int[][] matrizPixels) throws IOException {
        // System.out.println("Convertendo imagem para binária");
        int altura = matrizPixels.length;
        int largura = matrizPixels[0].length;

        int novaMatrizPixel[][] = new int[altura][largura];

        Color preto = new Color(0,0,0,255);
        Color branco = new Color(255,255,255, 255);

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {

                if ((matrizPixels[i][j] & 0xff) > 200) {
                    //Branco
                    novaMatrizPixel[i][j] = 0;
                    // System.out.print(" 0");
                } else {
                    //Preto
                    novaMatrizPixel[i][j] = 1;
                    // System.out.print(" 1");
                }
            }
            // System.out.println();
        }
        return novaMatrizPixel;
    }

    public static int[] matrizParaVetor(int[][] matrizPixel){

        int altura = matrizPixel.length;
        int largura = matrizPixel[0].length;
        
        int vetorImagem[] = new int[altura * largura];
        
        int itera = 0;

        for(int i = 0; i < altura; i++){
            for(int j = 0; j < largura; j++){
                vetorImagem[itera] = matrizPixel[i][j];
                itera++;
            }
        }

        return vetorImagem;
    }

    public static void escreverVetor(int[] vetor, String pasta) throws IOException{
        File dir = new File("dataset");
        File arq = new File(dir, pasta+".txt");
        FileWriter fileWriter = new FileWriter(arq, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for(int i = 0; i < vetor.length; i++){
            printWriter.print(vetor[i] + ";");
        }
        printWriter.println();

        printWriter.flush();
        printWriter.close();
    }

    public static File[] visualizarArquivos(String pasta) throws IOException {
        File file = new File("../mnist_png-master/mnist_png/training/"+pasta+"/");
        return file.listFiles();
    }
}