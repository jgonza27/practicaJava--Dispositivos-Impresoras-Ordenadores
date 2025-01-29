import java.io.*;

public class Main {

    public static void main(String[] args) {

        Dispositivo dis = new Dispositivo("BMW", "serie 3", true);
        dis.save("Dispositivo.bin");
        // dis.leerArchivo("Dispositivo.bin");

    }

}