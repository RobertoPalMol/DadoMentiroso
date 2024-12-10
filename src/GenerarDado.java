import java.util.Random;

public class GenerarDado {

    static int Dado(){
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
