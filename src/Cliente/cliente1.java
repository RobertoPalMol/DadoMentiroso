package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class cliente1 {
    public static void main(String[] args) throws IOException {
        String ip ="localhost";
        String port ="9999";

        Socket socket = new Socket(ip, Integer.parseInt(port));
        var writer = new PrintWriter(socket.getOutputStream(), true);
        var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var scanner = new Scanner(System.in);


        while (true) {

            var message = reader.readLine();
            System.out.println(message);

            if(message.contains("Nombre")) {
                var hand = scanner.nextLine();
                writer.println(hand);
            }else if (message.contains("Cuántos")) {
                var hand = scanner.nextLine();
                writer.println(hand);
            } else if (message.contains("número")) {
                var hand = scanner.nextLine();
                writer.println(hand);
            } else if (message.contains("desafiar")) {
                var hand = scanner.nextLine();
                writer.println(hand);
            }
        }
    }
}
