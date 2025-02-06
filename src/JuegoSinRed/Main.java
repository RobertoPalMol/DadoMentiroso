package JuegoSinRed;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nombre del Jugador 1: ");
        String nombre1 = sc.nextLine();
        System.out.print("Nombre del Jugador 2: ");
        String nombre2 = sc.nextLine();

        Juego juego = new Juego(nombre1, nombre2);
        juego.iniciar();
    }
}