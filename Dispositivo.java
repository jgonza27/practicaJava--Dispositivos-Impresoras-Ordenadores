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
            int longitudFija = 20;
            if (marca.length() < longitudFija) {
                marca = String.format("%-" + longitudFija + "s", marca);
            }

            if (modelo.length() < longitudFija) {
                modelo = String.format("%-" + longitudFija + "s", modelo);
            }
            raf.writeInt(id);
            raf.writeBytes(marca);
            raf.writeBytes(modelo);
            raf.writeBoolean(estado);

            raf.seek(4);

            
            System.out.println(raf.length());

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
