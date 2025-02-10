import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Byte> listaDispositivos = new ArrayList<>();

        int a;
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
                            "Introduce 1 para añadir un dispositivo,2 para añadir un ordenador , 3 para añadir una impresora");
                    int b = sc.nextInt();

                    switch (b) {
                        case 1:

                            System.out.println("Escribe la marca");
                            String marca = sc.next();

                            System.out.println("Escribe el modelo");
                            String modelo = sc.next();

                            Dispositivo dis = new Dispositivo(marca, modelo, true);
                            b = dis.save();

                            if (b == 0) {
                                System.out.println("Todo correcto!");

                            } else {
                                System.out.println("Halgo ha salido mal");
                            }

                            break;

                        case 2:

                            System.out.println("Escribe la marca");
                            marca = sc.nextLine();
                            sc.nextLine();
                            System.out.println("Escribe el modelo");
                            modelo = sc.nextLine();
                            System.out.println("Escribe la ram");
                            int ram = sc.nextInt();
                            System.out.println("Escribe el procesador");
                            String procesador = sc.nextLine();
                            sc.nextLine();
                            System.out.println("Escribe el tamaño del disco");
                            int tamDisco = sc.nextInt();
                            System.out.println("Escribe el tipo de disco");
                            int tipoDisco = sc.nextInt();
                            Ordenador or = new Ordenador(marca, modelo, true, ram, procesador, tamDisco, tipoDisco);

                            b = or.save();

                            if (b == 0) {
                                System.out.println("Todo correcto!");

                            } else {
                                System.out.println("Halgo ha salido mal");
                            }

                            break;
                        case 3:
                            System.out.println("Escribe la marca");
                            marca = sc.nextLine();
                            sc.nextLine();
                            System.out.println("Escribe el modelo");
                            modelo = sc.nextLine();
                            System.out.println("Escribe el tipo");
                            int tipoImpresora = sc.nextInt();
                            System.out.println("Escribe el color");
                            boolean color = sc.nextBoolean();
                            System.out.println("Escribe el tipo de scanner");
                            boolean scanner = sc.nextBoolean();
                            Impresora im = new Impresora(marca, modelo, true, tipoImpresora, color, scanner);
                            b = im.save();
                            if (b == 0) {
                                System.out.println("Todo correcto!");

                            } else {
                                System.out.println("Halgo ha salido mal");
                            }

                            break;

                    }
                    break;
                case 2:
                    System.out.println("Introduce el id para mostrar el dispositivo");
                    a = sc.nextInt();
                    Dispositivo dis = new Dispositivo(a);

                    b = dis.load();

                    if (b == 0) {
                        System.out.println("Todo correcto!");

                    } else {
                        System.out.println("Halgo ha salido mal");
                    }

                    break;

            }
        } while (a != 0);

    }

}