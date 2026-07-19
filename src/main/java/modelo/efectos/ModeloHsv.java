package modelo.efectos;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ModeloHsv implements IEfecto {

    private final float factorS, factorB, factorT;

    public ModeloHsv(float factorS, float factorB, float factorT) {
        this.factorS = factorS;
        this.factorB = factorB;
        this.factorT = factorT;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho, alto, pixel, pixelNuevo;
        int r, g, b, a;

        float[] hsv = new float[3];
        float h, s, v;

        ancho = buffer1.getWidth();
        alto = buffer1.getHeight();

        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                pixel = buffer1.getRGB(x, y);
                a = (pixel >> 24) & 0xFF;
                r = (pixel >> 16) & 0xFF;
                g = (pixel >> 8) & 0xFF;
                b = (pixel >> 0) & 0xFF;

                hsv = Color.RGBtoHSB(r, g, b, null);
                h = hsv[0];
                s = hsv[1];
                v = hsv[2];

                s = Math.min(1, (s * factorS));
                v = Math.min(1, (v * factorB));
                a = (int) Math.min(255, (a * factorT));

                pixelNuevo = Color.HSBtoRGB(h, s, v);
                pixelNuevo = (a << 24) | (pixelNuevo & 0x00FFFFFF);
                salida.setRGB(x, y, pixelNuevo);
            }
        }
        return salida;
    }

}
