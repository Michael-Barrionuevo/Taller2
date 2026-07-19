package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ConversorImagen {

    private ConversorImagen() {
    }

    public static Image aImagenJavaFX(BufferedImage buffered) {
        return SwingFXUtils.toFXImage(buffered, null);
    }

    public static BufferedImage aBufferedImage(Image imagenFX) {
        return SwingFXUtils.fromFXImage(imagenFX, null);
    }

    public static BufferedImage cargarDesdeArchivo(File archivo) throws IOException {
        BufferedImage img = ImageIO.read(archivo);
        if (img == null) {
            throw new IOException("El archivo no es una imagen válida: " + archivo.getName());
        }
        if (img.getType() != BufferedImage.TYPE_INT_ARGB) {
            BufferedImage normalizada = new BufferedImage(
                    img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g2d = normalizada.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();
            return normalizada;
        }
        return img;
    }

    public static void guardarEnArchivo(BufferedImage imagen, File archivo) throws IOException {
        String nombre = archivo.getName().toLowerCase();
        String formato = nombre.endsWith(".jpg") || nombre.endsWith(".jpeg") ? "jpg" : "png";
        ImageIO.write(imagen, formato, archivo);
    }

    public static WritableImage aWritableImage(Image imagen) {
        WritableImage writable = new WritableImage(
                (int) imagen.getWidth(),
                (int) imagen.getHeight()
        );
        writable.getPixelWriter().setPixels(
                0, 0,
                (int) imagen.getWidth(),
                (int) imagen.getHeight(),
                imagen.getPixelReader(),
                0, 0
        );
        return writable;
    }
}
