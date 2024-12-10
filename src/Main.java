import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int dadosRestantes=5;
        Scanner sc = new Scanner(System.in);

        while(dadosRestantes>0){

            int[] tusDados = new int[dadosRestantes];
            for(int i=0; i<dadosRestantes;i++){
                tusDados[i] = GenerarDado.Dado();
            }
            System.out.println("Resultados de tus dados:");
            for (int dado : tusDados) {
                System.out.println(dado);
            }

            System.out.println("Empiezas tu");
            System.out.println("Cuantos dados hay?");
            int dados = sc.nextInt();
            System.out.println("De que numero");
            int numero = sc.nextInt();


            dadosRestantes--;
        }
    }
}