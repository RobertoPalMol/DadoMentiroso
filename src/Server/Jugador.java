package Server;

import java.util.ArrayList;

class Jugador {
    private String nombre;
    private ArrayList<Integer> dados;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.dados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Integer> getDados() {
        return dados;
    }

    public void lanzarDados(int cantidad) {
        dados.clear();
        for (int i = 0; i < cantidad; i++) {
            dados.add(GenerarDado.Dado());
        }
    }

    public void mostrarDados() {
        System.out.println(nombre + " tiene los dados: " + dados);
    }

    public void perderDado() {
        if (!dados.isEmpty()) {
            dados.remove(0);
        }
    }
}