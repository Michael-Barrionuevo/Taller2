package modelo.efectos;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EfectoDegradadoHorizontal implements IEfecto {
    private Color colorInicio;
    private Color colorFin;

    public EfectoDegradadoHorizontal(Color colorInicio, Color colorFin) {
        this.colorInicio = colorInicio;
        this.colorFin = colorFin;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < ancho; x++) {
            // Calculamos el ratio de mezcla (0.0 a 1.0)
            float ratio = (float) x / (ancho - 1);

            int r = (int) (colorInicio.getRed() * (1 - ratio) + colorFin.getRed() * ratio);
            int g = (int) (colorInicio.getGreen() * (1 - ratio) + colorFin.getGreen() * ratio);
            int b = (int) (colorInicio.getBlue() * (1 - ratio) + colorFin.getBlue() * ratio);

            int colorPixel = (r << 16) | (g << 8) | b;

            // Pintamos toda la columna vertical con este color degradado
            for (int y = 0; y < alto; y++) {
                salida.setRGB(x, y, colorPixel);
            }
        }
        return salida;
    }
}