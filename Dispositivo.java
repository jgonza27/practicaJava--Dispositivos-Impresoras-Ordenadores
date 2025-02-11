import java.util.*;
import java.io.*;

public class Dispositivo {

    Scanner sc = new Scanner(System.in);

    private int id;
    private String marca;
    private String modelo;
    private boolean estado;
    private int tipo;
    private boolean borrado;
    private int idAjeno;

    public Dispositivo(String marca, String modelo, boolean estado) {
        this.id = getUltimoId() + 1;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
        this.tipo = 1;
        this.borrado = false;
        this.idAjeno = -1;

    }

    public Dispositivo(int id) {
        this.id = id;
        this.marca = "";
        this.modelo = "";
        this.estado = true;
    }

    public int save() {
        int longitudFija = 20;

        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "rw")) {

            raf.seek(raf.length());

            raf.writeInt(id);

            long posAntes = raf.getFilePointer();
            raf.writeUTF(this.marca);
            long posDespues = raf.getFilePointer();
            long bytesEscritos = posDespues - posAntes;
            for (int i = 0; i < longitudFija - bytesEscritos; i++) {
                raf.writeByte(0);
            }

            posAntes = raf.getFilePointer();
            raf.writeUTF(this.modelo);
            posDespues = raf.getFilePointer();
            bytesEscritos = posDespues - posAntes;
            for (int i = 0; i < longitudFija - bytesEscritos; i++) {
                raf.writeByte(0);
            }
            raf.writeBoolean(this.estado);
            raf.writeBoolean(this.borrado);
            raf.writeInt(tipo);
            raf.writeInt(idAjeno);

        } catch (IOException e) {
            return 1;
        }

        return 0;
    }

    public int mostrarDispositivos() {
        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "rw");) {
            while (raf.getFilePointer() < raf.length()) {
                id = raf.readInt();
                marca = leerCadenaFija(raf, 20);
                modelo = leerCadenaFija(raf, 20);
                estado = raf.readBoolean();
                borrado = raf.readBoolean();
                tipo = raf.readInt();
                idAjeno = raf.readInt();

                System.out.println(toString());

            }

        } catch (Exception e) {
            return 1;
        }

        return 0;

    }

    public int load() {
        int a;
        boolean encontrado = false;

        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "r")) {
            while (raf.getFilePointer() < raf.length() && !encontrado) {
                long b = raf.getFilePointer();
                a = raf.readInt();

                if (a == id) {
                    marca = leerCadenaFija(raf, 20);
                    modelo = leerCadenaFija(raf, 20);
                    estado = raf.readBoolean();
                    borrado = raf.readBoolean();
                    tipo = raf.readInt();
                    idAjeno = raf.readInt();
                    encontrado = true;
                } else {
                    raf.seek(b + 54);
                }
            }

        } catch (Exception e) {

            return 1;
        }

        return 0;
    }

    private String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        byte[] buffer = new byte[longitud];
        raf.readFully(buffer);
        return new String(buffer, "UTF-8").trim();
    }

    public int delete() {
        boolean encontrado = false;

        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "rw")) {
            while (raf.getFilePointer() < raf.length() && !encontrado) {
                long b = raf.getFilePointer();
                int a = raf.readInt();

                if (a == id) {
                    raf.seek(b + 45);
                    raf.writeBoolean(true);
                    encontrado = true;
                } else {
                    raf.seek(b + 54);
                }
            }

        } catch (Exception e) {

            return 1;
        }

        return 0;
    }

    public int cambioEstado() {
        boolean encontrado = false;

        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "rw")) {
            while (raf.getFilePointer() < raf.length() && !encontrado) {
                long b = raf.getFilePointer();
                int a = raf.readInt();

                if (a == id) {
                    raf.seek(b + 44);
                    boolean cambio = raf.readBoolean();
                    if (cambio == true) {
                        cambio = false;
                    } else {
                        cambio = true;
                    }
                    raf.seek(raf.getFilePointer() - 1);
                    raf.writeBoolean(cambio);
                    encontrado = true;
                } else {
                    raf.seek(b + 54);
                }
            }

        } catch (Exception e) {

            return 1;
        }

        return 0;

    }

    public int modificarDispositivo(int id) {
        boolean encontrado = false;
        int longitudFija = 20;
        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "rw")) {
            while (raf.getFilePointer() < raf.length() && !encontrado) {
                long b = raf.getFilePointer();
                int a = raf.readInt();

                if (a == id) {
                    raf.seek(b);
                    raf.writeInt(id);

                    long posAntes = raf.getFilePointer();
                    raf.writeUTF(this.marca);
                    long posDespues = raf.getFilePointer();
                    long bytesEscritos = posDespues - posAntes;
                    for (int i = 0; i < longitudFija - bytesEscritos; i++) {
                        raf.writeByte(0);
                    }

                    posAntes = raf.getFilePointer();
                    raf.writeUTF(this.modelo);
                    posDespues = raf.getFilePointer();
                    bytesEscritos = posDespues - posAntes;
                    for (int i = 0; i < longitudFija - bytesEscritos; i++) {
                        raf.writeByte(0);
                    }
                    raf.writeBoolean(this.estado);
                    raf.writeBoolean(this.borrado);
                    raf.writeInt(tipo);
                    raf.writeInt(idAjeno);

                } else {
                    raf.seek(b + 54);
                }

            }
        } catch (Exception e) {

            return 1;
        }

        return 0;

    }

    private int getUltimoId() {
        int ultimoId = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "rw");
            if (raf.length() != 0) {
                raf.seek(raf.length() - 54);
                ultimoId = raf.readInt();
            } else {
                ultimoId = 0;
            }

        } catch (Exception e) {
        }
        return ultimoId;
    }

    @Override
    public String toString() {
        return "ID " + id + " Marca: " + marca + ". Modelo: " + modelo + ". Estado: "
                + (estado ? "Funciona" : "No Funciona")
                + ". Borrado: " + (borrado ? "Borrado" : "No Borrado") + "." + " Tipo: " + tipo + ". IdAjeno "
                + idAjeno;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public int getIdAjeno() {
        return idAjeno;
    }

    public void setIdAjeno(int idAjeno) {
        this.idAjeno = idAjeno;
    }
}
