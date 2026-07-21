package modelo.histogramas;

import java.awt.image.BufferedImage;

public class EcualizadorHistograma {
    private int[] lut;

    public EcualizadorHistograma(BufferedImage img) {
        calcularLUT(img);
    }

    private void calcularLUT(BufferedImage img) {
        int[] histo = new int[256];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                histo[(img.getRGB(x, y) >> 16) & 0xFF]++;
            }
        }
        int total = img.getWidth() * img.getHeight();
        int acu = 0;
        lut = new int[256];
        for (int i = 0; i < 256; i++) {
            acu += histo[i];
            lut[i] = Math.round((float) acu / total * 255);
        }
    }

    public void procesar(BufferedImage original, BufferedImage resultado, float factor) {
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int p = original.getRGB(x, y);
                int r = (p >> 16) & 0xFF;
                int g = (p >> 8) & 0xFF;
                int b = p & 0xFF;

                int nR = (int)(r + factor * (lut[r] - r));
                int nG = (int)(g + factor * (lut[g] - g));
                int nB = (int)(b + factor * (lut[b] - b));
                resultado.setRGB(x, y, (nR << 16) | (nG << 8) | nB);
            }
        }
    }
}