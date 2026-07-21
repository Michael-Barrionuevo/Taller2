package modelo.efectos;

import modelo.efectos.IEfecto;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EfectoVidrio implements IEfecto {
    private final int radio = 5;

    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int rx = Math.min(Math.max(x + rand.nextInt(radio * 2) - radio, 0), ancho - 1);
                int ry = Math.min(Math.max(y + rand.nextInt(radio * 2) - radio, 0), alto - 1);
                salida.setRGB(x, y, buffer.getRGB(rx, ry));
            }
        }
        return salida;
    }
}