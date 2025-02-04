import java.io.IOException;
import java.io.RandomAccessFile;

public class Ordenador extends Dispositivo {
    private int ram;
    private String procesador;
    private int tamDisco;
    private int tipoDisco;

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

    public int save(String nombreFichero) {
        super.save();
        try (RandomAccessFile raf = new RandomAccessFile(nombreFichero, "rw")) {
            boolean encontrado = false;
            int nuevoId = 1;

            while (raf.getFilePointer() < raf.length()) {
                int idLeido = raf.readInt();
                String marcaLeida = raf.readUTF();
                String modeloLeido = raf.readUTF();
                boolean estadoLeido = raf.readBoolean();
                boolean borradoLeido = raf.readBoolean();
                int ramLeida = raf.readInt();
                String procesadorLeido = raf.readUTF();
                int tamDiscoLeido = raf.readInt();
                int tipoDiscoLeido = raf.readInt();

                nuevoId = idLeido + 1;
            }

            if (!encontrado) {
                raf.seek(raf.length());
                raf.writeInt(nuevoId);
                raf.writeUTF(super.getMarca());
                raf.writeUTF(super.getModelo());
                raf.writeBoolean(super.isEstado());
                raf.writeInt(ram);
                raf.writeUTF(procesador);
                raf.writeInt(tamDisco);
                raf.writeInt(tipoDisco);

            }
        } catch (IOException e) {
            return 1;
        }
        return 0;
    }

    public int load(String nombreFichero) {
        System.out.println("Introduce el ID:");

        int idBuscar = sc.nextInt();

        try (RandomAccessFile raf = new RandomAccessFile(nombreFichero, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int idLeido = raf.readInt();
                String marcaLeida = raf.readUTF();
                String modeloLeido = raf.readUTF();
                boolean estadoLeido = raf.readBoolean();
                boolean borradoLeido = raf.readBoolean();
                int ramLeida = raf.readInt();
                String procesadorLeido = raf.readUTF();
                int tamDiscoLeido = raf.readInt();
                int tipoDiscoLeido = raf.readInt();

                if (idLeido == idBuscar) {
                    System.out.println(toString());
                    return 0;
                }
            }
            System.out.println("Ese dispositivo no se encuentra en el fichero.");
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

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
