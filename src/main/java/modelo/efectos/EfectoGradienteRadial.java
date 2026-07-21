package modelo.efectos;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EfectoGradienteRadial implements IEfecto {
    private Color colorCentro;
    private Color colorBorde;
    private float radio; // Controla qué tan grande es el círculo del degradado

    public EfectoGradienteRadial(Color colorCentro, Color colorBorde, float radio) {
        this.colorCentro = colorCentro;
        this.colorBorde = colorBorde;
        this.radio = radio;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        double cx = ancho / 2.0;
        double cy = alto / 2.0;

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2));

                // Normalizamos la distancia respecto al radio definido
                // Si la distancia es mayor al radio, queda como colorBorde
                float ratio = (float) (dist / (radio * Math.min(cx, cy)));
                if (ratio > 1.0f) ratio = 1.0f;

                int r = (int) (colorCentro.getRed() * (1 - ratio) + colorBorde.getRed() * ratio);
                int g = (int) (colorCentro.getGreen() * (1 - ratio) + colorBorde.getGreen() * ratio);
                int b = (int) (colorCentro.getBlue() * (1 - ratio) + colorBorde.getBlue() * ratio);

                salida.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return salida;
    }
}