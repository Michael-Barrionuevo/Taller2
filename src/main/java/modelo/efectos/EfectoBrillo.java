package modelo.efectos;

import java.awt.image.BufferedImage;

public class EfectoBrillo implements IEfecto {

    private int brillo;

    public EfectoBrillo(int brillo) {
        this.brillo = Math.max(2, brillo);
    }

    @Override
    public BufferedImage aplicar(BufferedImage buffer1) {
        int ancho, alto, pixel, pixelNuevo;
        int r, g, b;

        int brillo = this.brillo;
        ancho = buffer1.getWidth();
        alto = buffer1.getHeight();

        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                pixel = buffer1.getRGB(x, y);
                r = (pixel >> 16) & 0xFF;
                g = (pixel >> 8) & 0xFF;
                b = (pixel >> 0) & 0xFF;

                r = Math.min(255, (r + brillo));
                g = Math.min(255, (g + brillo));
                b = Math.min(255, (b + brillo));

                pixelNuevo = (r << 16) | (g << 8) | (b << 0);
                salida.setRGB(x, y, pixelNuevo);
            }
        }
        return salida;
    }

    public int getBrillo() {
        return brillo;
    }

    public void setBrillo(int n) {
        this.brillo = Math.max(2, n);
    }

}
