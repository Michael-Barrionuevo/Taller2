package modelo.histogramas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;

public class HistoPanelComponent extends Canvas {
    public HistoPanelComponent() {
        setWidth(300);
        setHeight(150);
    }

    public void dibujarHistograma(BufferedImage img) {
        if (img == null) return;
        GraphicsContext gc = getGraphicsContext2D();
        int[] h = new int[256];
        // Histograma solo del canal Rojo (o promedio, como prefieras)
        for (int y = 0; y < img.getHeight(); y++)
            for (int x = 0; x < img.getWidth(); x++)
                h[(img.getRGB(x, y) >> 16) & 0xFF]++;

        int max = 0;
        for(int val : h) if(val > max) max = val;

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.setStroke(Color.BLACK);
        for (int i = 0; i < 256; i++) {
            double x = i * getWidth() / 256;
            double y = getHeight() - (h[i] * getHeight() / (max == 0 ? 1 : max));
            gc.strokeLine(x, getHeight(), x, y);
        }
    }
}