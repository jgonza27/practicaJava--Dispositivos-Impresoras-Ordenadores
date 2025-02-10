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

            if (raf.length() != 0) {
                raf.seek(raf.length());

                raf.writeInt(this.id);
                raf.writeInt(tipoImpresora);
                raf.writeBoolean(color);
                raf.writeBoolean(scanner);

            } else {
                raf.writeInt(1);
                raf.writeInt(tipoImpresora);

                raf.writeBoolean(color);
                raf.writeBoolean(scanner);

            }

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

    private int getUltimoId() {
        int ultimoId = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile("Impresora.bin", "rw");
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

}
