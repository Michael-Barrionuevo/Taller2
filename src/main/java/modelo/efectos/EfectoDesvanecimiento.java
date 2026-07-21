package modelo.efectos;

import modelo.efectos.IEfecto;
import java.awt.image.BufferedImage;

public class EfectoDesvanecimiento implements IEfecto {
    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int pixel = buffer.getRGB(x, y);
                float factor = 1.0f - ((float) x / ancho);

                int r = (int) (((pixel >> 16) & 0xFF) * factor);
                int g = (int) (((pixel >> 8) & 0xFF) * factor);
                int b = (int) ((pixel & 0xFF) * factor);

                salida.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return salida;
    }
}
