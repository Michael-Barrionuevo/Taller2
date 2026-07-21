package modelo.efectos;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EfectoBlending implements IEfecto {
    private BufferedImage imagenSecundaria;
    private float alpha;

    public EfectoBlending(BufferedImage imagenSecundaria, float alpha) {
        this.imagenSecundaria = imagenSecundaria;
        this.alpha = alpha;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho = buffer1.getWidth();
        int alto = buffer1.getHeight();

        // Escalamos la segunda imagen al tamaño de la primera (como en tu código original)
        Image imgTemp = imagenSecundaria.getScaledInstance(ancho, alto, Image.SCALE_FAST);
        BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D grTemp = buffer2.createGraphics();
        grTemp.drawImage(imgTemp, 0, 0, null);
        grTemp.dispose();

        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int p1 = buffer1.getRGB(x, y);
                int p2 = buffer2.getRGB(x, y);

                int r = (int) (((1 - alpha) * ((p1 >> 16) & 0xFF)) + (alpha * ((p2 >> 16) & 0xFF)));
                int g = (int) (((1 - alpha) * ((p1 >> 8) & 0xFF)) + (alpha * ((p2 >> 8) & 0xFF)));
                int b = (int) (((1 - alpha) * (p1 & 0xFF)) + (alpha * (p2 & 0xFF)));

                salida.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return salida;
    }
}