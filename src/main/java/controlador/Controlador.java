package controlador;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;
import modelo.ModeloPrincipal;
import modelo.convolucion.EfectoConvolucion;
import modelo.efectos.*;
import modelo.histogramas.EcualizadorHistograma;
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

        // FORZAMOS LA SINCRONIZACIÓN:
        // Aseguramos que el resultado sea igual a la original tras restaurar
        BufferedImage original = modelo.getImagenOriginal();
        BufferedImage copia = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copia.getGraphics();
        g.drawImage(original, 0, 0, null);
        g.dispose();
        modelo.setImagenResultado(copia);

        return ConversorImagen.aImagenJavaFX(modelo.getImagenResultado());
    }

    public BufferedImage getImagenActual() {
        return modelo.getImagenResultado();
    }

    public void aplicarEcualizacionDirecta(float factor) {
        BufferedImage original = modelo.getImagenOriginal();
        if (original == null) return;

        // 1. Si no existe el lienzo, créalo
        if (modelo.getImagenResultado() == null) {
            BufferedImage copia = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
            modelo.setImagenResultado(copia);
        }

        BufferedImage resultado = modelo.getImagenResultado();

        // 2. Procesar sobre el buffer ya existente
        EcualizadorHistograma eq = new EcualizadorHistograma(original);
        eq.procesar(original, resultado, factor);

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

    /** Aplica efecto de distorsión tipo vidrio */
    public Image aplicarVidrio() {
        return aplicar(new EfectoVidrio());
    }

    /** Aplica efecto de desvanecimiento lineal */
    public Image aplicarDesvanecimiento() {
        return aplicar(new EfectoDesvanecimiento());
    }

    /** Aplica efecto de desvanecimiento circular */
    public Image aplicarDesvanecimientoCircular() {
        return aplicar(new EfectoDesvanecimientoCircular());
    }

    /** Aplica efecto retro estilo sepia */
    public Image aplicarRetroSepia() {
        return aplicar(new EfectoRetro2());
    }

    /** Aplica el efecto retro con nivel personalizado */
    public Image aplicarRetro(int valor) {
        return aplicar(new EfectoRetro(valor));
    }

    /** Aplica el efecto Degradado Horizontal*/
    public Image aplicarDegradadoHorizontal(Color inicio, Color fin) {
        return aplicar(new EfectoDegradadoHorizontal(inicio, fin));
    }
    /** Aplica el efecto Degradado Vertical*/
    public Image aplicarDegradadoVertical(Color inicio, Color fin) {
        return aplicar(new EfectoDegradadoVertical(inicio, fin));
    }

    /** Aplica el efecto Degradado Radial*/
    public Image aplicarDegradadoRadial(Color centro, Color borde) {
        return aplicar(new EfectoDegradadoRadial(centro, borde));
    }
    /** Aplica el efecto Degradado Gradiente Radial*/
    public Image aplicarGradienteRadial(Color centro, Color borde, float radio) {
        return aplicar(new EfectoGradienteRadial(centro, borde, radio));
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

    /** Convolución Manual 9x9 */
    public Image aplicarConvolucionManual() {
        return aplicar(new modelo.convolucion.EfectoConvolucionManual());
    }


    /** Matriz de colores */
    public Image aplicarMatrizColores(Matrizes.NombreMatriz matriz){
        return aplicar(new MatrizColores(matriz));
    }

    /** Matriz de colores */
    public Image aplicarHistograma(Histograma.TipoHistograma color){
        return aplicar(new Histograma(color));
    }

    /** Blending */
    public Image aplicarBlending(BufferedImage segundaImagen, float alpha) {
        return aplicar(new EfectoBlending(segundaImagen, alpha));
    }

    /** Blending x 3*/
    public Image aplicarBlending3(BufferedImage img2, BufferedImage img3) {
        return aplicar(new EfectoBlending3(img2, img3));
    }

    private Image aplicar(IEfecto efecto) {
        if (!modelo.hayImagenCargada()) {
            throw new IllegalStateException("No hay ninguna imagen cargada.");
        }
        BufferedImage resultado = modelo.aplicarEfecto(efecto);
        return ConversorImagen.aImagenJavaFX(resultado);
    }
}
