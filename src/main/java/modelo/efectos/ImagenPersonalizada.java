package modelo.efectos;

import java.awt.image.BufferedImage;
import java.util.Random;

public class ImagenPersonalizada implements IEfecto {

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho, alto, pixelNuevo;
        int r, g, b;

        ancho = 900;
        alto = 500;

        Random rd = new Random();

        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {

                r = rd.nextInt(256);
                g = rd.nextInt(256);
                b = 250;

                pixelNuevo = (r << 16) | (g << 8) | (b << 0);
                salida.setRGB(x, y, pixelNuevo);
            }
        }
        return salida;
    }
}
