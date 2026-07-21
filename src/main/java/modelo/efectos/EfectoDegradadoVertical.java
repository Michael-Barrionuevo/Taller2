package modelo.efectos;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EfectoDegradadoVertical implements IEfecto {
    private Color colorInicio;
    private Color colorFin;

    public EfectoDegradadoVertical(Color colorInicio, Color colorFin) {
        this.colorInicio = colorInicio;
        this.colorFin = colorFin;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            // Calculamos el ratio basado en Y
            float ratio = (float) y / (alto - 1);

            int r = (int) (colorInicio.getRed() * (1 - ratio) + colorFin.getRed() * ratio);
            int g = (int) (colorInicio.getGreen() * (1 - ratio) + colorFin.getGreen() * ratio);
            int b = (int) (colorInicio.getBlue() * (1 - ratio) + colorFin.getBlue() * ratio);

            int colorPixel = (r << 16) | (g << 8) | b;

            // Pintamos toda la fila horizontal con este color
            for (int x = 0; x < ancho; x++) {
                salida.setRGB(x, y, colorPixel);
            }
        }
        return salida;
    }
}