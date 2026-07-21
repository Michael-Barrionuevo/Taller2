package modelo.efectos;

import java.awt.image.BufferedImage;

public class EfectoDesvanecimientoCircular implements IEfecto {

    @Override
    public BufferedImage aplicar(BufferedImage buffer) {
        int ancho = buffer.getWidth();
        int alto = buffer.getHeight();
        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        double cx = ancho / 2.0;
        double cy = alto / 2.0;
        double radioMax = Math.min(cx, cy); // Radio máximo para el desvanecimiento

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int pixel = buffer.getRGB(x, y);

                // Calcular distancia al centro
                double dist = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2));

                // Factor de desvanecimiento (1 en el centro, 0 en el borde)
                double factor = 1.0 - (dist / radioMax);
                if (factor < 0) factor = 0;

                int r = (int) (((pixel >> 16) & 0xFF) * factor);
                int g = (int) (((pixel >> 8) & 0xFF) * factor);
                int b = (int) ((pixel & 0xFF) * factor);

                salida.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return salida;
    }
}