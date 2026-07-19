package modelo.efectos;

import java.awt.image.BufferedImage;

public class EfectoRetro implements IEfecto {

    private int valor;

    public EfectoRetro(int valor) {
        this.valor = valor;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho, alto, pixel, pixelNuevo;
        int r, g, b, a;

        ancho = buffer1.getWidth();
        alto = buffer1.getHeight();

        BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                pixel = buffer1.getRGB(x, y);
                a = (pixel >> 24) & 0xFF;
                r = (pixel >> 16) & 0xFF;
                g = (pixel >> 8) & 0xFF;
                b = (pixel >> 0) & 0xFF;

                r = valorColor(r, valor);
                g = valorColor(g, valor);
                b = valorColor(b, valor);

                pixelNuevo = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                buffer2.setRGB(x, y, pixelNuevo);
            }
        }
        return buffer2;
    }

    private int valorColor(int valor, int numColor) {
        int salto = 255 / (numColor - 1);
        int nivel = 256 / numColor;
        int resultado = (valor / nivel) * salto;
        return resultado;
    }

}
