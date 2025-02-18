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


        try {

            socketWriter1.println("Nombre del Jugador 1: ");
            socketWriter2.println("Nombre del Jugador 2: ");
            var nombre1 = socketReader1.readLine();
            var nombre2 = socketReader2.readLine();

            socketWriter1.println("Tu enemigo es: " + nombre2);
            socketWriter2.println("Tu enemigo es: " + nombre1);

            Jugador jugador1 = new Jugador(nombre1);
            Jugador jugador2 = new Jugador(nombre2);
            int turnoActual = 0;
            int cantidadDadosInicial = 5;

            jugador1.lanzarDados(cantidadDadosInicial);
            jugador2.lanzarDados(cantidadDadosInicial);

            System.out.println("¡El juego comienza!");
            socketWriter1.println("¡El juego comienza!");
            socketWriter2.println("¡El juego comienza!");
            socketWriter1.println(jugador1.mostrarDados());
            socketWriter2.println(jugador2.mostrarDados());

            boolean jugando = true;
            int cantidadDeclarada = 0;
            int numeroDeclarado = 0;

            while (jugando) {
                Jugador jugadorActual = turnoActual == 0 ? jugador1 : jugador2;
                Jugador jugadorOponente = turnoActual == 0 ? jugador2 : jugador1;

                PrintWriter socketPlayerW;
                BufferedReader socketPlayerR;
                PrintWriter socketNotPlayerW;
                BufferedReader socketNotPlayerR;

                socketWriter1.println("\nTurno de " + jugadorActual.getNombre());
                socketWriter2.println("\nTurno de " + jugadorActual.getNombre());

                if (jugadorActual.getNombre().equals(nombre1)){
                    socketPlayerW = socketWriter1;
                    socketPlayerR = socketReader1;
                    socketNotPlayerW = socketWriter2;
                    socketNotPlayerR = socketReader2;

                }else {
                    socketPlayerW = socketWriter2;
                    socketPlayerR = socketReader2;
                    socketNotPlayerW = socketWriter1;
                    socketNotPlayerR = socketReader1;
                }

                boolean declaracionValida = false;

                while (!declaracionValida) {
                    socketPlayerW.println(jugadorActual.getNombre() + ", ¿Cuántos dados hay?: ");
                    int cantidad = Integer.parseInt(socketPlayerR.readLine());;
                    socketPlayerW.println(jugadorActual.getNombre() + ", ¿De qué número?: ");
                    int numero = Integer.parseInt(socketPlayerR.readLine());

                    if (cantidad > cantidadDeclarada || (cantidad == cantidadDeclarada && numero > numeroDeclarado)) {
                        cantidadDeclarada = cantidad;
                        numeroDeclarado = numero;
                        declaracionValida = true;
                    } else {
                        socketPlayerW.println("Declaración no válida. Intenta de nuevo.");
                    }
                }

                socketNotPlayerW.println("Declaración actual: " +
                        (cantidadDeclarada == 0 ? "Ninguna" : cantidadDeclarada + " dados de " + numeroDeclarado));
                socketNotPlayerW.println(jugadorOponente.getNombre() + ", ¿quieres desafiar? (s/n): ");
                String respuesta = socketNotPlayerR.readLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    int total = 0;
                    for (int dado : jugador1.getDados()) {
                        if (dado == numeroDeclarado) total++;
                    }
                    for (int dado : jugador2.getDados()) {
                        if (dado == numeroDeclarado) total++;
                    }
                    socketPlayerW.println("El rival te ha desafiado");
                    socketPlayerW.println("Revelando los dados...");
                    socketNotPlayerW.println("Revelando los dados...");

                    socketWriter1.println(jugador1.mostrarDados());
                    socketWriter2.println(jugador1.mostrarDados());

                    socketWriter1.println(jugador2.mostrarDados());
                    socketWriter2.println(jugador2.mostrarDados());

                    if (total >= cantidadDeclarada) {
                        socketWriter1.println("La declaración es correcta. " + jugadorOponente.getNombre() + " pierde un dado.");
                        socketWriter2.println("La declaración es correcta. " + jugadorOponente.getNombre() + " pierde un dado.");
                        jugadorOponente.perderDado();
                    } else {
                        socketWriter1.println("La declaración es falsa. " + jugadorActual.getNombre() + " pierde un dado.");
                        socketWriter2.println("La declaración es falsa. " + jugadorActual.getNombre() + " pierde un dado.");
                        jugadorActual.perderDado();
                    }

                    jugador1.lanzarDados(jugador1.getDados().size());
                    jugador2.lanzarDados(jugador2.getDados().size());

                    cantidadDeclarada = 0;
                    numeroDeclarado = 0;
                    socketWriter1.println("Dados relanzados. ¡Nueva ronda!");
                    socketWriter2.println("Dados relanzados. ¡Nueva ronda!");

                    socketWriter1.println(jugador1.mostrarDados());
                    socketWriter2.println(jugador2.mostrarDados());

                    if (jugador1.getDados().isEmpty()) {
                        socketWriter1.println(jugador1.getNombre() + " se ha quedado sin dados. ¡" + jugador2.getNombre() + " gana!");
                        socketWriter2.println(jugador1.getNombre() + " se ha quedado sin dados. ¡" + jugador2.getNombre() + " gana!");
                        jugando = false;
                    } else if (jugador2.getDados().isEmpty()) {
                        socketWriter1.println(jugador2.getNombre() + " se ha quedado sin dados. ¡" + jugador1.getNombre() + " gana!");
                        socketWriter2.println(jugador2.getNombre() + " se ha quedado sin dados. ¡" + jugador1.getNombre() + " gana!");
                        jugando = false;
                    }
                }

                turnoActual = 1 - turnoActual;
            }
        }catch (Exception e){

        }
    }
}