import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Parte1 {

    public static class Instancia {

        public Integer classe;
        public List<Double> features;

        public Instancia(List<Double> features, Integer classe){
            this.features = features;
            this.classe = classe;
        }

        public Instancia(List<Double> features){
            this.features = features;
        }
    }

    public static Double distanciaEuclidiana(Instancia a, Instancia b){
        Double total = 0.0;
        for(int i = 0 ; i < a.features.size(); i++){
            total += Math.pow((a.features.get(i) - b.features.get(i)), 2);
        }
        return Math.sqrt(total);
    }

    public static void verificarClasse(List<Instancia> instancias, Instancia instaciaTeste, int K){
        int classeA = 0;
        int classeB = 0;
        List<Instancia> aux = instancias;
        int salva = 0;

        for(int j = 0; j < K; j++){
            Double distanciaEuclidiana = 10000000000.0;
            for(int i = 0; i < aux.size(); i++){

                Double teste = Parte1.distanciaEuclidiana(aux.get(i), instaciaTeste);
                if(teste < distanciaEuclidiana){
                    distanciaEuclidiana = teste;
                    salva = i;
                }
       
            }
            int classe = aux.get(salva).classe;
            if(classe == 0){
                classeA++;
                System.out.println("Classe A: " + distanciaEuclidiana);
            }else{
                classeB++;
                System.out.println("Classe B: " + distanciaEuclidiana);
            }
            aux.remove(salva);
        }

        if(classeA > classeB){
            System.out.println("Classificado como A: " + classeA);
        }else{
            if(classeB > classeA){
                System.out.println("Classificado como B: " + classeB);    
            }
            else{
                System.out.println("Empate");    
            }
        }
    }   

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("Informe o Valor de K:");
        int K = in.nextInt();

        List<Instancia> instancias = new ArrayList<Instancia>();

        List<Double> f1 = new ArrayList<Double>(java.util.Arrays.asList(1.23, 2.19));
        List<Double> f2 = new ArrayList<Double>(java.util.Arrays.asList(1.41, 2.02));
        List<Double> f3 = new ArrayList<Double>(java.util.Arrays.asList(0.73, 1.95));

        List<Double> t1 = new ArrayList<Double>(java.util.Arrays.asList(1.34, 2.45));

        List<Double> f4 = new ArrayList<Double>(java.util.Arrays.asList(1.52, 2.07));
        List<Double> f5 = new ArrayList<Double>(java.util.Arrays.asList(1.83, 2.33));
        List<Double> f6 = new ArrayList<Double>(java.util.Arrays.asList(1.07, 2.12));
        
        List<Double> t2 = new ArrayList<Double>(java.util.Arrays.asList(1.23, 3.45));

        instancias.add(new Instancia(f1, 0));
        instancias.add(new Instancia(f2, 0));
        instancias.add(new Instancia(f3, 0));
        instancias.add(new Instancia(f4, 1));
        instancias.add(new Instancia(f5, 1));
        instancias.add(new Instancia(f6, 1));


        Parte1.verificarClasse(instancias, new Instancia(t1), K);
        //Parte1.verificarClasse(instancias, new Instancia(t2), K);

    }
}