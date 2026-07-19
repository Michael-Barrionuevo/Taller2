package modelo.efectos;

import java.awt.image.BufferedImage;

public class EfectoBlancoNegro implements IEfecto {

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {

        int r, g, b;
        int ancho, alto, pixel, pixelNuevo;

        ancho = buffer1.getWidth();
        alto = buffer1.getHeight();

        BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {

                pixel = buffer1.getRGB(x, y);

                r = (pixel >> 16) & 0xFF;
                g = (pixel >> 8) & 0xFF;
                b = (pixel >> 0) & 0xFF;

                r = valorColor(r);
                g = valorColor(g);
                b = valorColor(b);

                r = b = g;

                pixelNuevo = (r << 16) | (g << 8) | (b << 0);
                buffer2.setRGB(x, y, pixelNuevo);
            }
        }
        return buffer2;
    }

    private int valorColor(int valor) {
        if (valor > 128) {
            valor = 255;
        } else {
            valor = 0;
        }
        return valor;
    }

}
