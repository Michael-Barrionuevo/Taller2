package modelo.efectos;

import java.awt.image.BufferedImage;

public class RecorteBits implements IEfecto {

    private int bits;

    public RecorteBits(int bits) {
        this.bits = bits;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho, alto, pixel, pixelNuevo;
        int r, g, b, a;

        ancho = buffer1.getWidth();
        alto = buffer1.getHeight();

        BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

        int bitsRestantes = 8 - bits;
        int mascara = (1 << bitsRestantes) - 1; // ej: bitsRestantes=2 → 0b11 = 3
        int maxValor = mascara;

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                pixel = buffer1.getRGB(x, y);
                a = (pixel >> 24) & 0xFF;
                r = (pixel >> 16) & 0xFF;
                g = (pixel >> 8) & 0xFF;
                b = (pixel >> 0) & 0xFF;

                // Recorte a de bits
                r = (r >> bits) & mascara;
                g = (g >> bits) & mascara;
                b = (b >> bits) & mascara;

                // Estirar
                // aplicar regla de tres
                r = (r * 255) / maxValor;
                g = (g * 255) / maxValor;
                b = (b * 255) / maxValor;

                pixelNuevo = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                buffer2.setRGB(x, y, pixelNuevo);
            }
        }
        return buffer2;
    }

}
