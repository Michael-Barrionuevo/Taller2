package controlador;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;
import modelo.ModeloPrincipal;
import modelo.convolucion.EfectoConvolucion;
import modelo.efectos.Duplicado;
import modelo.efectos.EfectoBlancoNegro;
import modelo.efectos.EfectoBrillo;
import modelo.efectos.EfectoEscalaGrises;
import modelo.efectos.EfectoGananciaColor;
import modelo.efectos.EfectoNegativo;
import modelo.efectos.EfectoRetro;
import modelo.efectos.IEfecto;
import modelo.efectos.ImagenPersonalizada;
import modelo.efectos.ModeloHsv;
import modelo.efectos.RecorteBits;
import modelo.histogramas.Histograma;
import modelo.kernels.Kernels;
import modelo.matrizColores.MatrizColores;
import modelo.matrizColores.Matrizes;
import util.ConversorImagen;

public class Controlador {

    private final ModeloPrincipal modelo;

    public Controlador() {
        this.modelo = new ModeloPrincipal();
    }

    // ── Carga y guardado

    /**
     * Carga una imagen
     */
    public Image cargarImagen(File archivo) throws IOException {
        modelo.cargarImagen(archivo);
        return ConversorImagen.aImagenJavaFX(modelo.getImagenOriginal());
    }

    /**
     * Guarda el resultado
     */
    public void guardarImagen(File destino) throws IOException {
        modelo.guardarResultado(destino);
    }

    /** Restaura la imagen */
    public Image restaurarOriginal() {
        modelo.restaurarOriginal();
        return ConversorImagen.aImagenJavaFX(modelo.getImagenResultado());
    }

    public boolean hayImagenCargada() {
        return modelo.hayImagenCargada();
    }

    // Efectos

    /* Duplicado */
    public Image aplicarDuplicado() {
        return aplicar(new Duplicado());
    }

    /* Imagen Personalizada */
    public Image aplicarImagenPersonalizada() {
        BufferedImage resultado = new ImagenPersonalizada().aplicar(null);
        modelo.setImagenResultado(resultado); // guarda para poder exportar
        return ConversorImagen.aImagenJavaFX(resultado);
    }

    /** Escala de Grises */
    public Image aplicarEscalaGrises() {
        return aplicar(new EfectoEscalaGrises());
    }

    /** Negativo */
    public Image aplicarNegativo() {
        return aplicar(new EfectoNegativo());
    }

    /* Brillo */
    public Image aplicarBrillo(int brillo) {
        return aplicar(new EfectoBrillo(brillo));
    }

    /** Modelo HSV */
    public Image aplicarModeloHsv(float factorS, float factorB, float factorT) {
        return aplicar(new ModeloHsv(factorS, factorB, factorT));
    }

    /* Recorte de bits */
    public Image aplicarRecorteBits(int recorte) {
        return aplicar(new RecorteBits(recorte));
    }

    /** Blanco y Negro */
    public Image aplicarBlancoNegro() {
        return aplicar(new EfectoBlancoNegro());
    }

    /** Color personalizado */
    public Image aplicarGananciaColor(float deltaR, float deltaG, float deltaB) {
        return aplicar(new EfectoGananciaColor(deltaR, deltaG, deltaB));
    }

    /** Aplica reducción de colores por canal RGB */
    public Image aplicarReducirColor(int valor) {
        return aplicar(new EfectoRetro(valor));
    }

    /** Convolución con kernel y múltiples pasadas */
    public Image aplicarConvolucion(Kernels.NombreKernel kernel, int pasadas) {
        return aplicar(new EfectoConvolucion(kernel, pasadas));
    }

    /** Matriz de colores */
    public Image aplicarMatrizColores(Matrizes.NombreMatriz matriz){
        return aplicar(new MatrizColores(matriz));
    }

    /** Matriz de colores */
    public Image aplicarHistograma(Histograma.TipoHistograma color){
        return aplicar(new Histograma(color));
    }

    private Image aplicar(IEfecto efecto) {
        if (!modelo.hayImagenCargada()) {
            throw new IllegalStateException("No hay ninguna imagen cargada.");
        }
        BufferedImage resultado = modelo.aplicarEfecto(efecto);
        return ConversorImagen.aImagenJavaFX(resultado);
    }
}
