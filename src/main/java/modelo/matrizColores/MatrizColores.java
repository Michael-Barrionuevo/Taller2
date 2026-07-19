package modelo.matrizColores;

import java.awt.image.BufferedImage;
import modelo.efectos.IEfecto;
import modelo.matrizColores.Matrizes.NombreMatriz;

public class MatrizColores implements IEfecto {

        private Matrizes.NombreMatriz matrizSeleccionada;

        public MatrizColores(NombreMatriz matrizSeleccionado) {
                this.matrizSeleccionada = matrizSeleccionado;
        }

        @Override
        public BufferedImage aplicar(BufferedImage buffer1) {
                float[][] matriz = matrizSeleccionada.getMatrizColor();
                int ancho, alto, pixelNuevo, pixel;
                int r, g, b, a;
                int r1, g1, b1;

                int mascara = 0xFF;

                ancho = buffer1.getWidth();
                alto = buffer1.getHeight();
                BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

                for (int y = 0; y < alto; y++) {
                        for (int x = 0; x < ancho; x++) {
                                pixel = buffer1.getRGB(x, y);

                                a = (pixel >> 24) & mascara;
                                r = (pixel >> 16) & mascara;
                                g = (pixel >> 8) & mascara;
                                b = (pixel >> 0) & mascara;

                                r1 = (int) (matriz[0][0] * r + matriz[0][1] * g + matriz[0][2] * b + (matriz[0][3] * 255));
                                g1 = (int) (matriz[1][0] * r + matriz[1][1] * g + matriz[1][2] * b + (matriz[1][3] * 255));
                                b1 = (int) (matriz[2][0] * r + matriz[2][1] * g + matriz[2][2] * b + (matriz[2][3] * 255));

                                r1 = Math.min(255, Math.max(r1, 0));
                                g1 = Math.min(255, Math.max(g1, 0));
                                b1 = Math.min(255, Math.max(b1, 0));

                                pixelNuevo = (a << 24) | (r1 << 16) | (g1 << 8) | (b1 << 0);
                                buffer2.setRGB(x, y, pixelNuevo);
                        }
                }
                return buffer2;
        }

        public Matrizes.NombreMatriz getMatrizSeleccionada() {
                return matrizSeleccionada;
        }

        public void setMatrizSeleccionada(Matrizes.NombreMatriz matrizSeleccionado) {
                this.matrizSeleccionada = matrizSeleccionado;
        }

}
