import java.io.*;

public class Dispositivo {

    private int id = 4;

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

    }

    public Dispositivo(int id) {
        this.id = id;

    }

    public int save(String nombreFichero) {
        try {
            RandomAccessFile raf = new RandomAccessFile(nombreFichero, "rw");

            for (int i = 0; i < raf.length(); i++) {
                raf.seek(i);
                int b = raf.read();
                if (b == id) {
                    int longitudFija = 20;
                    if (marca.length() < longitudFija) {
                        marca = String.format("%-" + longitudFija + "s", marca).replace(' ', '0');
                    }

                    if (modelo.length() < longitudFija) {
                        modelo = String.format("%-" + longitudFija + "s", modelo).replace(' ', '0');
                    }

                    raf.writeInt(id);
                    raf.writeUTF(marca);
                    raf.writeUTF(modelo);
                    raf.writeBoolean(estado);

                } else if (i == raf.length()) {
                    raf.writeInt(id);
                    raf.writeUTF(marca);
                    raf.writeUTF(modelo);
                    raf.writeBoolean(estado);

                }
            }

        } catch (IOException e) {

        }
        return 1;
    }

    public String toString() {

        return "Marca: " + marca + "." + "Modelo: " + modelo + "." + "Estado: " + (estado ? "Funciona" : "No Funciona")
                + ".";
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
