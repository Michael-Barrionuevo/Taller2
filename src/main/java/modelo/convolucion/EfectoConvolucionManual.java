package modelo.convolucion;

import modelo.efectos.IEfecto;
import java.awt.image.BufferedImage;

public class EfectoConvolucionManual implements IEfecto {

    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        int mascara = 0xFF;

        // Matriz de 9x9 (81 elementos)
        float[] matriz = new float[81];
        float valor = 1f / 81f;
        for (int i = 0; i < 81; i++) {
            matriz[i] = valor;
        }

        BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        // Ajuste de bordes: para una matriz 9x9, el radio es 4.
        for (int y = 4; y < alto - 4; y++) {
            for (int x = 4; x < ancho - 4; x++) {

                float sumaR = 0, sumaG = 0, sumaB = 0;
                int indice = 0;

                // Recorrido de la máscara 9x9 (de -4 a 4)
                for (int i = -4; i <= 4; i++) {
                    for (int j = -4; j <= 4; j++) {
                        int pixel = buffer.getRGB(x + i, y + j);

                        // Extraemos canales
                        int r = (pixel >> 16) & mascara;
                        int g = (pixel >> 8) & mascara;
                        int b = (pixel) & mascara;

                        // Sumamos la contribución ponderada
                        sumaR += r * matriz[indice];
                        sumaG += g * matriz[indice];
                        sumaB += b * matriz[indice];
                        indice++;
                    }
                }

                int rNuevo = clamp((int) sumaR);
                int gNuevo = clamp((int) sumaG);
                int bNuevo = clamp((int) sumaB);

                // Reconstruimos el pixel
                int pixelNuevo = (rNuevo << 16) | (gNuevo << 8) | bNuevo;
                buffer2.setRGB(x, y, pixelNuevo);
            }
        }
        return buffer2;
    }

    private int clamp(int valor) {
        if (valor < 0) return 0;
        if (valor > 255) return 255;
        return valor;
    }
}