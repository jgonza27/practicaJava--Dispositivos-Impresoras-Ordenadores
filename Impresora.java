import java.io.IOException;
import java.io.RandomAccessFile;

public class Impresora extends Dispositivo {

    private int tipoImpresora;
    private boolean color;
    private boolean scanner;
    private int id;
    private int tipo;
    private int ultimoId;

    public Impresora(String marca, String modelo, boolean estado, int tipoImpresora, boolean color, boolean scanner) {
        super(marca, modelo, estado);
        this.tipoImpresora = tipoImpresora;
        this.color = color;
        this.scanner = scanner;
        this.tipo = 3;

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
                raf.seek(raf.length() - 10);
                ultimoId = raf.readInt();
                raf.seek(raf.length());

                raf.writeInt(ultimoId + 1);
                raf.writeInt(tipoImpresora);
                raf.writeBoolean(color);
                raf.writeBoolean(scanner);

                añadirTipo();
                añadirIdAjeno();

            } else {
                raf.writeInt(1);
                raf.writeInt(tipoImpresora);

                raf.writeBoolean(color);
                raf.writeBoolean(scanner);

                añadirTipo();
                añadirIdAjeno();
            }

        } catch (IOException e) {
            return 1;
        }

        return 0;
    }

    public void añadirIdAjeno() {
        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "rw")) {

            raf.seek(raf.length() - 4);
            raf.writeInt(ultimoId + 1);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void añadirTipo() {
        try (RandomAccessFile raf = new RandomAccessFile("Dispositivo.bin", "rw")) {

            raf.seek(raf.length() - 8);
            raf.writeInt(tipo);

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
