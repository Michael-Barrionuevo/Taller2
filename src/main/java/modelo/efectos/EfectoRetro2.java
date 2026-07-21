package modelo.efectos;

import modelo.efectos.IEfecto;
import java.awt.image.BufferedImage;

public class EfectoRetro2 implements IEfecto {
    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int p = buffer.getRGB(x, y);
                int r = (p >> 16) & 0xFF;
                int g = (p >> 8) & 0xFF;
                int b = p & 0xFF;

                int tr = Math.min(255, (int)(0.393*r + 0.769*g + 0.189*b));
                int tg = Math.min(255, (int)(0.349*r + 0.686*g + 0.168*b));
                int tb = Math.min(255, (int)(0.272*r + 0.534*g + 0.131*b));

                salida.setRGB(x, y, (tr << 16) | (tg << 8) | tb);
            }
        }
        return salida;
    }
}
