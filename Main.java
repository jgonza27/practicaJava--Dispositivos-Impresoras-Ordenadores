import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dispositivo dis = new Dispositivo("wewe", "s12312", true);
        Ordenador or = new Ordenador(null, null, false);
        ArrayList<Dispositivo> ListaDispositivos = new ArrayList<>();
        int a = 1;
        do {
            System.out.println("MENÚ PRINCIPAL\r\n" + //
                    "--------------\r\n" + //
                    "1. Añadir dispositivo\r\n" + //
                    "2. Mostrar dispositivos\r\n" + //
                    "3. Buscar dispositivo\r\n" + //
                    "4. Borrar dispositivo\r\n" + //
                    "5. Cambiar estado dispositivo\r\n" + //
                    "6. Modificar dispositivo\r\n" + //
                    "0. Salir");
            a = sc.nextInt();
            switch (a) {
                case 1:
                    System.out.println(
                            "Introduce 1,si quieres introducir un dispositivo,introduce un 2 si quieres introducir un ordenador,introduce un 3 si quieres introducir una impresora");
                    int b = sc.nextInt();

                    switch (b) {
                        case 1:
                            int c = dis.save();

                            if (c == 0) {
                                System.out.println("Todo correcto!");

                            } else {
                                System.out.println("Halgo ha salido mal");
                            }

                            break;

                    } 
                    break;

                case 2:
                    b = dis.mostrarDispositivos();
                    if (b == 0) {
                        System.out.println("Todo correcto!");

                    } else {
                        System.out.println("Halgo ha salido mal");
                    }
                    break;

                case 3:

                    b = dis.load();
                    if (b == 0) {
                        System.out.println("Todo correcto!");

                    } else {
                        System.out.println("Halgo ha salido mal");
                    }
                    break;

                case 4:
                    b = dis.delete();
                    if (b == 0) {
                        System.out.println("Todo correcto!");

                    } else {
                        System.out.println("Halgo ha salido mal");
                    }

                    break;

                case 0:
                    System.out.println("¡Hasta Luego!");
                    break;

            }
        } while (a != 0);

    }

}