package modelo.efectos;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EfectoDegradadoRadial implements IEfecto {
    private Color colorCentro;
    private Color colorBorde;

    public EfectoDegradadoRadial(Color colorCentro, Color colorBorde) {
        this.colorCentro = colorCentro;
        this.colorBorde = colorBorde;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        double cx = ancho / 2.0;
        double cy = alto / 2.0;
        // Calculamos la distancia máxima posible (a la esquina) para normalizar el gradiente
        double maxDist = Math.sqrt(Math.pow(cx, 2) + Math.pow(cy, 2));

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                // Distancia euclidiana al centro
                double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2));

                // Ratio: 0 en el centro, 1 en el borde máximo
                float ratio = (float) (dist / maxDist);

                // Mezcla de colores según la distancia
                int r = (int) (colorCentro.getRed() * (1 - ratio) + colorBorde.getRed() * ratio);
                int g = (int) (colorCentro.getGreen() * (1 - ratio) + colorBorde.getGreen() * ratio);
                int b = (int) (colorCentro.getBlue() * (1 - ratio) + colorBorde.getBlue() * ratio);

                salida.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return salida;
    }
}