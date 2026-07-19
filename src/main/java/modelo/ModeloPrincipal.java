package modelo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import modelo.efectos.IEfecto;
import util.ConversorImagen;

public class ModeloPrincipal {

    private BufferedImage imagenOriginal;
    private BufferedImage imagenResultado;
    private File archivoActual;

    public void cargarImagen(File archivo) throws IOException {
        this.imagenOriginal = ConversorImagen.cargarDesdeArchivo(archivo);
        this.imagenResultado = imagenOriginal;
        this.archivoActual = archivo;
    }

    public BufferedImage aplicarEfecto(IEfecto efecto) {
        verificarImagenCargada();
        imagenResultado = efecto.aplicar(imagenOriginal);
        return imagenResultado;
    }

    public BufferedImage encadenarEfecto(IEfecto efecto) {
        verificarImagenCargada();
        imagenResultado = efecto.aplicar(imagenResultado);
        return imagenResultado;
    }

    public void restaurarOriginal() {
        verificarImagenCargada();
        imagenResultado = imagenOriginal;
    }

    public void guardarResultado(File destino) throws IOException {
        verificarImagenCargada();
        ConversorImagen.guardarEnArchivo(imagenResultado, destino);
    }

    public BufferedImage getImagenOriginal() {
        return imagenOriginal;
    }

    public BufferedImage getImagenResultado() {
        return imagenResultado;
    }

    public File getArchivoActual() {
        return archivoActual;
    }

    public boolean hayImagenCargada() {
        return imagenOriginal != null;
    }

    private void verificarImagenCargada() {
        if (imagenOriginal == null) {
            throw new IllegalStateException("No hay ninguna imagen cargada.");
        }
    }

    public void setImagenResultado(BufferedImage img) {
        this.imagenResultado = img;
    }
}
