import java.io.*;

public class Ordenador extends Dispositivo {
    private int ram;
    private String procesador;
    private int tamDisco;
    private int tipoDisco;
    private int tipo;
    private int ultimoId;

    public Ordenador(String marca, String modelo, boolean estado, int ram, String procesador, int tamDisco,
            int tipoDisco) {
        super(marca, modelo, estado);
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = tipoDisco;
        this.tipo = 2;

    }

    public Ordenador(String marca, String modelo, boolean estado) {
        super(marca, modelo, estado);
        this.ram = 0;
        this.procesador = "";
        this.tamDisco = 0;
        this.tipoDisco = 0;
    }

    public int save() {
        // super.save();
        int longitudFija = 20;

        try (RandomAccessFile raf = new RandomAccessFile("Ordenador.bin", "rw")) {

            if (raf.length() != 0) {
                raf.seek(raf.length());
                long a = raf.getFilePointer() - 36;
                raf.seek(a);
                ultimoId = raf.readInt();

                raf.seek(raf.length());

                raf.writeInt(ultimoId + 1);
                raf.writeInt(ram);
                long posAntes = raf.getFilePointer();
                raf.writeUTF(this.procesador);
                long posDespues = raf.getFilePointer();
                long bytesEscritos = posDespues - posAntes;
                for (int i = 0; i < longitudFija - bytesEscritos; i++) {
                    raf.writeByte(0);
                }

                raf.writeInt(tamDisco);
                raf.write(tipoDisco);

            } else {
                raf.writeInt(1);
                raf.writeInt(ram);
                long posAntes = raf.getFilePointer();
                raf.writeUTF(this.procesador);
                long posDespues = raf.getFilePointer();
                long bytesEscritos = posDespues - posAntes;
                for (int i = 0; i < longitudFija - bytesEscritos; i++) {
                    raf.writeByte(0);
                }

                raf.writeInt(tamDisco);
                raf.write(tipoDisco);
                añadirIdAjeno();
            }

        } catch (IOException e) {
            return 1;
        }

        return 0;
    }

    public void añadirIdAjeno() {
        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo", "rw")) {

            raf.seek(raf.length());
            long a = raf.getFilePointer();
            raf.seek(a);
            raf.writeInt(ultimoId + 1);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*
     * public int load(String nombreFichero) {
     * System.out.println("Introduce el ID:");
     * 
     * int idBuscar = sc.nextInt();
     * 
     * try (RandomAccessFile raf = new RandomAccessFile(nombreFichero, "r")) {
     * while (raf.getFilePointer() < raf.length()) {
     * int idLeido = raf.readInt();
     * String marcaLeida = raf.readUTF();
     * String modeloLeido = raf.readUTF();
     * boolean estadoLeido = raf.readBoolean();
     * boolean borradoLeido = raf.readBoolean();
     * int ramLeida = raf.readInt();
     * String procesadorLeido = raf.readUTF();
     * int tamDiscoLeido = raf.readInt();
     * int tipoDiscoLeido = raf.readInt();
     * 
     * if (idLeido == idBuscar) {
     * System.out.println(toString());
     * return 0;
     * }
     * }
     * System.out.println("Ese dispositivo no se encuentra en el fichero.");
     * } catch (Exception e) {
     * return 1;
     * }
     * return 0;
     * }
     */
    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public int getTamDisco() {
        return tamDisco;
    }

    public void setTamDisco(int tamDisco) {
        this.tamDisco = tamDisco;
    }

    public int getTipoDisco() {
        return tipoDisco;
    }

    public void setTipoDisco(int tipoDisco) {
        this.tipoDisco = tipoDisco;
    }

    public String toString() {

        String tipoDiscoStr;
        switch (tipoDisco) {
            case 0:
                tipoDiscoStr = " mecánico";
                break;
            case 1:
                tipoDiscoStr = " SSD";
                break;
            case 2:
                tipoDiscoStr = " NVMe";
                break;
            case 3:
                tipoDiscoStr = " otros";
                break;
            default:
                tipoDiscoStr = " desconocido";
                break;
        }
        return super.toString() + " Procesador: " + procesador + "." + " Memoria: " + ram + "GB." + " Almacenamiento: "
                + tamDisco + "GB" + tipoDiscoStr;
    }
}
