package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) throws IOException {

        ServerSocket socket = new ServerSocket(9999);
        Socket socket1 = socket.accept();


        PrintWriter socketWriter1 = new PrintWriter(socket1.getOutputStream(), true);
        var socketReader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        socketWriter1.println("Esperando al jugador 2...");

        Socket socket2 = socket.accept();
        PrintWriter socketWriter2 = new PrintWriter(socket2.getOutputStream(), true);
        var socketReader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));


        socketWriter2.println("Empieza el juego");
        socketWriter1.println("Empieza el juego");



        socketWriter1.println("Nombre del Jugador 1: ");
        socketWriter2.println("Nombre del Jugador 2: ");
        System.out.println("hola");
        var nombre1 = socketReader1.readLine();
        var nombre2 = socketReader2.readLine();


        System.out.println("tu enemigo es:"+nombre1);
        System.out.println("tu enemigo es:"+nombre2);
        socketWriter1.println("tu enemigo es:"+nombre2);
        socketWriter2.println("tu enemigo es:"+nombre1);



        Jugador jugador1 = new Jugador(nombre1);
        Jugador jugador2 = new Jugador(nombre2);
        int turnoActual = 0;
        int cantidadDadosInicial = 5;

            Scanner sc = new Scanner(System.in);

            jugador1.lanzarDados(cantidadDadosInicial);
            jugador2.lanzarDados(cantidadDadosInicial);

            System.out.println("¡El juego comienza!");
            jugador1.mostrarDados();
            jugador2.mostrarDados();

            boolean jugando = true;
            int cantidadDeclarada = 0;
            int numeroDeclarado = 0;

            while (jugando) {
                Jugador jugadorActual = turnoActual == 0 ? jugador1 : jugador2;
                Jugador jugadorOponente = turnoActual == 0 ? jugador2 : jugador1;

                System.out.println("\nTurno de " + jugadorActual.getNombre());


                boolean declaracionValida = false;

                while (!declaracionValida) {
                    System.out.print(jugadorActual.getNombre() + ", ¿Cuántos dados hay?: ");
                    int cantidad = sc.nextInt();
                    System.out.print(jugadorActual.getNombre() + ", ¿De qué número?: ");
                    int numero = sc.nextInt();

                    if (cantidad > cantidadDeclarada || (cantidad == cantidadDeclarada && numero > numeroDeclarado)) {
                        cantidadDeclarada = cantidad;
                        numeroDeclarado = numero;
                        declaracionValida = true;
                    } else {
                        System.out.println("Declaración no válida. Intenta de nuevo.");
                    }
                }

                System.out.println("Declaración actual: " +
                        (cantidadDeclarada == 0 ? "Ninguna" : cantidadDeclarada + " dados de " + numeroDeclarado));
                System.out.print(jugadorOponente.getNombre() + ", ¿quieres desafiar? (s/n): ");
                String respuesta = sc.next();
                if (respuesta.equalsIgnoreCase("s")) {
                    int total = 0;
                    for (int dado : jugador1.getDados()) {
                        if (dado == numeroDeclarado) total++;
                    }
                    for (int dado : jugador2.getDados()) {
                        if (dado == numeroDeclarado) total++;
                    }
                    System.out.println("Revelando los dados...");
                    jugador1.mostrarDados();
                    jugador2.mostrarDados();

                    if (total >= cantidadDeclarada) {
                        System.out.println("La declaración es correcta. " + jugadorOponente.getNombre() + " pierde un dado.");
                        jugadorOponente.perderDado();
                    } else {
                        System.out.println("La declaración es falsa. " + jugadorActual.getNombre() + " pierde un dado.");
                        jugadorActual.perderDado();
                    }

                    jugador1.lanzarDados(jugador1.getDados().size());
                    jugador2.lanzarDados(jugador2.getDados().size());

                    cantidadDeclarada = 0;
                    numeroDeclarado = 0;
                    System.out.println("Dados relanzados. ¡Nueva ronda!");

                    jugador1.mostrarDados();
                    jugador2.mostrarDados();

                    if (jugador1.getDados().isEmpty()) {
                        System.out.println(jugador1.getNombre() + " se ha quedado sin dados. ¡" + jugador2.getNombre() + " gana!");
                        jugando = false;
                    } else if (jugador2.getDados().isEmpty()) {
                        System.out.println(jugador2.getNombre() + " se ha quedado sin dados. ¡" + jugador1.getNombre() + " gana!");
                        jugando = false;
                    }
                }

                turnoActual = 1 - turnoActual;
            }



    }
}