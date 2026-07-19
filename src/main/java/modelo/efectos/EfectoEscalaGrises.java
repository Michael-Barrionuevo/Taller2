package modelo.efectos;

import java.awt.image.BufferedImage;

public class EfectoEscalaGrises implements IEfecto {

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho, alto, pixel, pixelNuevo;
        int r, g, b;
        int gris;
        ancho = buffer1.getWidth();
        alto = buffer1.getHeight();

        BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {

                pixel = buffer1.getRGB(x, y);
                r = (pixel >> 16) & 0xFF;
                g = (pixel >> 8) & 0xFF;
                b = (pixel >> 0) & 0xFF;

                gris = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
                pixelNuevo = (gris << 16) | (gris << 8) | (gris << 0);
                buffer2.setRGB(x, y, pixelNuevo);
            }
        }
        return buffer2;
    }
}
