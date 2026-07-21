package modelo.efectos;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EfectoBlending3 implements IEfecto {
    private BufferedImage img2;
    private BufferedImage img3;
    private float alpha = 0.5f;

    public EfectoBlending3(BufferedImage img2, BufferedImage img3) {
        this.img2 = img2;
        this.img3 = img3;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho = buffer1.getWidth();
        int alto = buffer1.getHeight();

        // Usamos nombres distintos (buffer2, buffer3) para no chocar con las variables locales del bucle
        BufferedImage buffer2 = escalar(img2, ancho, alto);
        BufferedImage buffer3 = escalar(img3, ancho, alto);

        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int p1 = buffer1.getRGB(x, y);
                int p2 = buffer2.getRGB(x, y);
                int p3 = buffer3.getRGB(x, y);

                // Extracción de canales RGB
                int r1 = (p1 >> 16) & 0xFF; int g1 = (p1 >> 8) & 0xFF; int b1 = p1 & 0xFF;
                int r2 = (p2 >> 16) & 0xFF; int g2 = (p2 >> 8) & 0xFF; int b2 = p2 & 0xFF;
                int r3 = (p3 >> 16) & 0xFF; int g3 = (p3 >> 8) & 0xFF; int b3 = p3 & 0xFF;

                // Blending 1 + 2
                int rTemp = (int)((1 - alpha) * r1 + alpha * r2);
                int gTemp = (int)((1 - alpha) * g1 + alpha * g2);
                int bTemp = (int)((1 - alpha) * b1 + alpha * b2);

                // Blending Resultado + 3
                int r = (int)((1 - alpha) * rTemp + alpha * r3);
                int g = (int)((1 - alpha) * gTemp + alpha * g3);
                int b = (int)((1 - alpha) * bTemp + alpha * b3);

                salida.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return salida;
    }

    private BufferedImage escalar(BufferedImage img, int w, int h) {
        Image tmp = img.getScaledInstance(w, h, Image.SCALE_FAST);
        BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = res.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();
        return res;
    }
}