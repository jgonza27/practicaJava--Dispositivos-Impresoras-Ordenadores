import java.io.*;

public class Ordenador extends Dispositivo {

    private int ram;
    private String procesador;
    private int tamDisco;
    private int tipoDisco;
    private int id;
    private int tipo;

    public Ordenador(String marca, String modelo, boolean estado, int ram, String procesador, int tamDisco,
            int tipoDisco) {
        super(marca, modelo, estado);
        this.id = getUltimoId() + 1;
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = tipoDisco;
        super.setIdAjeno(this.id);
        super.setTipo(2);

    }

    public Ordenador(int id) {
        super(id);
        super.load();
        this.id = super.getIdAjeno();
        this.ram = 0;
        this.procesador = "";
        this.tamDisco = 0;
        this.tipoDisco = 0;
        this.tipo = super.getTipo();

    }

    public int save() {
        super.save();
        int longitudFija = 20;

        try (RandomAccessFile raf = new RandomAccessFile("Ordenador.bin", "rw")) {

            if (raf.length() != 0) {
                raf.seek(raf.length());
                raf.writeInt(this.id);
                raf.writeInt(this.ram);
                long posAntes = raf.getFilePointer();
                raf.writeUTF(this.procesador);
                long posDespues = raf.getFilePointer();
                long bytesEscritos = posDespues - posAntes;
                for (int i = 0; i < longitudFija - bytesEscritos; i++) {
                    raf.writeByte(0);
                }

                raf.writeInt(tamDisco);
                raf.writeInt(tipoDisco);

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
                raf.writeInt(tipoDisco);

            }

        } catch (IOException e) {
            return 1;
        }

        return 0;
    }

    public int load() {
        int a;
        boolean encontrado = false;
        if (tipo == 2) {
            try (RandomAccessFile raf = new RandomAccessFile("Ordenador.bin", "r")) {
                while (raf.getFilePointer() < raf.length() && !encontrado) {
                    long b = raf.getFilePointer();
                    a = raf.readInt();

                    if (a == id) {
                        ram = raf.readInt();
                        procesador = leerCadenaFija(raf, 20);
                        tamDisco = raf.readInt();
                        tipoDisco = raf.readInt();
                        encontrado = true;
                    } else {
                        raf.seek(b + 36);
                    }
                }

                System.out.println(toString());

            } catch (Exception e) {

                return 1;
            }

        } else if (tipo == 3) {
            Impresora im = new Impresora(id);
            im.load();
        }
        return 0;
    }

    private String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        byte[] buffer = new byte[longitud];
        raf.readFully(buffer);
        return new String(buffer, "UTF-8").trim();
    }

    public int modificarDispositivo(int id) {
        super.modificarDispositivo(id);
        int idAjeno = getIdAjeno();
        boolean encontrado = false;
        int longitudFija = 20;

        try (RandomAccessFile raf = new RandomAccessFile("Ordenador.bin", "rw")) {
            while (raf.getFilePointer() < raf.length() && !encontrado) {
                long b = raf.getFilePointer();
                int a = raf.readInt();

                if (a == idAjeno) {
                    raf.seek(b);

                    raf.writeInt(idAjeno);
                    raf.writeInt(ram);

                    long posAntes = raf.getFilePointer();
                    raf.writeUTF(this.procesador);
                    long posDespues = raf.getFilePointer();
                    long bytesEscritos = posDespues - posAntes;
                    for (int i = 0; i < longitudFija - bytesEscritos; i++) {
                        raf.writeByte(0);
                    }

                    raf.writeInt(tamDisco);
                    raf.writeInt(tipoDisco);

                    encontrado = true;
                } else {
                    raf.seek(b + 36);
                }
            }
        } catch (IOException e) {

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
        return super.toString() + " ID " + id + " Procesador: " + procesador + "." + " Memoria: " + ram + "GB."
                + " Almacenamiento: "
                + tamDisco + "GB" + tipoDiscoStr;
    }

    private int getUltimoId() {
        int ultimoId = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("Ordenador.bin", "rw");
            if (raf.length() != 0) {
                raf.seek(raf.length() - 36);
                ultimoId = raf.readInt();
            } else {
                ultimoId = 0;
            }

        } catch (Exception e) {
        }
        return ultimoId;
    }
}
