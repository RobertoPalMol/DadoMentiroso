import java.util.Scanner;

class Juego {
    private Jugador jugador1;
    private Jugador jugador2;
    private int turnoActual;
    private int cantidadDadosInicial = 5;

    public Juego(String nombreJugador1, String nombreJugador2) {
        this.jugador1 = new Jugador(nombreJugador1);
        this.jugador2 = new Jugador(nombreJugador2);
        this.turnoActual = 0;
    }

    public void iniciar() {
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
            System.out.println("Declaración actual: " +
                    (cantidadDeclarada == 0 ? "Ninguna" : cantidadDeclarada + " dados de " + numeroDeclarado));

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

            System.out.print(jugadorOponente.getNombre() + ", ¿quieres desafiar? (s/n): ");
            String respuesta = sc.next();
            if (respuesta.equalsIgnoreCase("s")) {
                int total = contarDados(numeroDeclarado);
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

        sc.close();
    }

    private int contarDados(int numero) {
        int total = 0;
        for (int dado : jugador1.getDados()) {
            if (dado == numero) total++;
        }
        for (int dado : jugador2.getDados()) {
            if (dado == numero) total++;
        }
        return total;
    }

    private void mostrarEstado() {
        System.out.println("\nEstado actual del juego:");
        jugador1.mostrarDados();
        jugador2.mostrarDados();
    }
}
