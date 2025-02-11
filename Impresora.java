import java.io.IOException;
import java.io.RandomAccessFile;

public class Impresora extends Dispositivo {

    private int tipoImpresora;
    private boolean color;
    private boolean scanner;
    private int id;
    private int tipo;

    public Impresora(String marca, String modelo, boolean estado, int tipoImpresora, boolean color, boolean scanner) {
        super(marca, modelo, estado);
        this.id = getUltimoId() + 1;
        this.tipoImpresora = tipoImpresora;
        this.color = color;
        this.scanner = scanner;
        super.setIdAjeno(this.id);
        super.setTipo(3);

    }

    public Impresora(int id) {
        super("", "", true);
        this.id = id;
        this.tipoImpresora = 0;
        this.color = false;
        this.scanner = false;
        this.tipo = 3;
    }

    public int save() {
        super.save();
        int longitudFija = 20;

        try (RandomAccessFile raf = new RandomAccessFile("Impresora.bin", "rw")) {

            raf.seek(raf.length());

            raf.writeInt(this.id);
            raf.writeInt(tipoImpresora);
            raf.writeBoolean(color);
            raf.writeBoolean(scanner);

        } catch (IOException e) {
            return 1;
        }

        return 0;
    }

    public int load() {
        int a;
        boolean encontrado = false;

        try (RandomAccessFile raf = new RandomAccessFile("Impresora.bin", "r")) {
            while (raf.getFilePointer() < raf.length() && !encontrado) {
                long b = raf.getFilePointer();
                a = raf.readInt();

                if (a == id) {
                    tipoImpresora = raf.readInt();
                    color = raf.readBoolean();
                    scanner = raf.readBoolean();
                    encontrado = true;
                } else {
                    raf.seek(b + 10);
                }
            }

            System.out.println(toString());

        } catch (Exception e) {

            return 1;
        }
        return 0;
    }

    public int modificarDispositivo(int id) {
        super.modificarDispositivo(id);
        int idAjeno = getIdAjeno();
        boolean encontrado = false;

        try (RandomAccessFile raf = new RandomAccessFile("Impresora.bin", "rw")) {
            while (raf.getFilePointer() < raf.length() && !encontrado) {
                long b = raf.getFilePointer();
                int a = raf.readInt();

                if (a == idAjeno) {
                    raf.seek(b);

                    raf.writeInt(idAjeno);
                    raf.writeInt(tipoImpresora);

                    raf.writeBoolean(color);
                    raf.writeBoolean(scanner);

                    encontrado = true;
                } else {
                    raf.seek(b + 10);
                }
            }
        } catch (IOException e) {

            return 1;
        }

        return 0;
    }

    private int getUltimoId() {
        int ultimoId = 0;
        try (RandomAccessFile raf = new RandomAccessFile("Impresora.bin", "rw")) {

            if (raf.length() != 0) {
                raf.seek(raf.length() - 10);
                ultimoId = raf.readInt();
            } else {
                ultimoId = 0;
            }
        } catch (Exception e) {

        }
        return ultimoId;
    }

    public String toString() {

        String tipoImpresora1 = "";
        switch (tipoImpresora) {
            case 0:
                tipoImpresora1 = " laser";
                break;
            case 1:
                tipoImpresora1 = " inyección de tinta";
                break;
            case 2:
                tipoImpresora1 = " otros";
                break;

        }
        return super.toString() + " ID " + id + " tipoImpresora: " + tipoImpresora1 + "." + " color:"
                + (color ? "Si tiene color" : "No tiene color") + " scanner:"
                + (scanner ? " la impresora tiene escáner" : " la impresora no tiene escáner");
    }

    public int getTipoImpresora() {
        return tipoImpresora;
    }

    public void setTipoImpresora(int tipoImpresora) {
        this.tipoImpresora = tipoImpresora;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isScanner() {
        return scanner;
    }

    public void setScanner(boolean scanner) {
        this.scanner = scanner;
    }

    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
