package modelo.histogramas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import modelo.efectos.IEfecto;

public class Histograma implements IEfecto {

    public enum TipoHistograma {
        ROJO("Histograma Rojo"),
        VERDE("Histograma Verde"),
        AZUL("Histograma Azul"),
        RGB_UNIDOS("Histograma Completo RGB");

        private final String etiqueta;

        TipoHistograma(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        @Override
        public String toString() {
            return etiqueta;
        }
    }

    private TipoHistograma tipo;

    public Histograma(TipoHistograma tipo) {
        this.tipo = tipo;
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho = buffer1.getWidth();
        int alto = buffer1.getHeight();

        int anchoHisto = 800;
        int altoHisto = 600;

        int[] histoR = new int[256];
        int[] histoG = new int[256];
        int[] histoB = new int[256];

        int mascara = 0xFF;
        int pixel, r, g, b;

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                pixel = buffer1.getRGB(x, y);

                r = (pixel >> 16) & mascara;
                g = (pixel >> 8) & mascara;
                b = (pixel >> 0) & mascara;

                histoR[r]++;
                histoG[g]++;
                histoB[b]++;
            }
        }

        // Preparar el lienzo donde se dibujará el histograma
        BufferedImage buffer2 = new BufferedImage(anchoHisto, altoHisto, BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = buffer2.createGraphics();
        
        gr.setColor(Color.white);
        gr.fillRect(0, 0, anchoHisto, altoHisto);
        gr.setStroke(new BasicStroke(2));

        int maxR = maximo(histoR);
        int maxG = maximo(histoG);
        int maxB = maximo(histoB);
        int maximoGlobal = 0;

        switch (tipo) {
            case ROJO: maximoGlobal = maxR; break;
            case VERDE: maximoGlobal = maxG; break;
            case AZUL: maximoGlobal = maxB; break;
            case RGB_UNIDOS: maximoGlobal = Math.max(maxR, Math.max(maxG, maxB)); break;
        }

        if (maximoGlobal == 0) maximoGlobal = 1;

        float escalaX = anchoHisto / 256.0f;
        float escalaY = (altoHisto * 1.0f) / maximoGlobal;

        if (tipo == TipoHistograma.ROJO || tipo == TipoHistograma.RGB_UNIDOS) {
            gr.setColor(Color.red);
            dibujarLineaHistograma(gr, histoR, escalaX, escalaY, altoHisto);
        }
        
        if (tipo == TipoHistograma.VERDE || tipo == TipoHistograma.RGB_UNIDOS) {
            gr.setColor(Color.green);
            dibujarLineaHistograma(gr, histoG, escalaX, escalaY, altoHisto);
        }
        
        if (tipo == TipoHistograma.AZUL || tipo == TipoHistograma.RGB_UNIDOS) {
            gr.setColor(Color.blue);
            dibujarLineaHistograma(gr, histoB, escalaX, escalaY, altoHisto);
        }

        gr.dispose();
        return buffer2;
    }

    private void dibujarLineaHistograma(Graphics2D gr, int[] histo, float escalaX, float escalaY, int altoHisto) {
        for (int i = 1; i < histo.length; i++) {
            int x1 = (int) (escalaX * (i - 1));
            int y1 = altoHisto - (int) (escalaY * histo[i - 1]);
            int x2 = (int) (escalaX * i);
            int y2 = altoHisto - (int) (escalaY * histo[i]);
            gr.drawLine(x1, y1, x2, y2);
        }
    }

    private static int maximo(int[] h) {
        return Arrays.stream(h).max().getAsInt();
    }
}
