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

            if (raf.length() != 0) {
                raf.seek(raf.length() - 54);

                int ultimoId = raf.readInt();

                raf.seek(raf.length());

                raf.writeInt(ultimoId + 1);

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
                raf.writeInt(1);
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

            }

        } catch (IOException e) {
            return 1;
        }

        return 0;
    }

    public int load() {
        int a;
        int contador = 0;
        boolean encontrado = false;
        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "r")) {

            do {
                long b = raf.getFilePointer();
                a = raf.readInt();
                if (a == id) {

                    long posAntes = raf.getFilePointer();
                    marca = raf.readUTF();
                    System.out.println(marca);
                    long posDespues = raf.getFilePointer();

                    for (posAntes = 0; posAntes <= posDespues; posAntes++) {
                        contador++;
                    }
                    int espacioMarca = 20 - contador;
                    raf.seek(posDespues + espacioMarca + 1);
                    posAntes = raf.getFilePointer();
                    modelo = raf.readUTF();
                    System.out.println(modelo);
                    posDespues = raf.getFilePointer();

                    for (posAntes = 0; posAntes <= posDespues; posAntes++) {
                        contador++;
                    }
                    int espacioModelo = 20 - contador;
                    raf.seek(posDespues + espacioModelo);
                    estado = raf.readBoolean();
                    borrado = raf.readBoolean();
                    tipo = raf.readInt();
                    idAjeno = raf.readInt();
                    encontrado = true;
                } else {
                    raf.seek(b + 54);

                }

            } while (raf.getFilePointer() < raf.length() && !encontrado);
            System.out.println(toString());
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Marca: " + marca + ". Modelo: " + modelo + ". Estado: " + (estado ? "Funciona" : "No Funciona") + "."
                + ". Borrado: " + (borrado ? "Borrado" : "No Borrado");
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
