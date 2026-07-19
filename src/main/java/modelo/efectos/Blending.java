package modelo.efectos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Blending {
    public static void main(String[] args) {
        // blendingMano();
        //blendingFuncion();
        blendingFuncion2();

    }

    private static void blendingMano() {
        File file1 = new File("Imagenes/universo.jpg");
        File file2 = new File("Imagenes/superior.jpg");
        File file3 = new File("Imagenes/Blending/blending.jpg");

        int ancho, alto, pixel1, pixel2, pixelBlending;
        int r1, g1, b1, r2, b2, g2, r, g, b;
        int mascara = 0xFF;
        float alpha = 0.5f;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            BufferedImage buffer2 = ImageIO.read(file2);

            // ancho = Math.min(buffer1.getWidth(), buffer2.getWidth());
            // alto = Math.min(buffer1.getHeight(), buffer2.getHeight());
            ancho = buffer2.getWidth();
            alto = buffer2.getHeight();

            BufferedImage bufferBlend = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {

                    pixel1 = buffer1.getRGB(x, y);
                    pixel2 = buffer2.getRGB(x, y);

                    r1 = (pixel1 >> 16) & mascara;
                    g1 = (pixel1 >> 8) & mascara;
                    b1 = (pixel1 >> 0) & mascara;

                    r2 = (pixel2 >> 16) & mascara;
                    g2 = (pixel2 >> 8) & mascara;
                    b2 = (pixel2 >> 0) & mascara;

                    r = (int) ((1 - alpha) * r1 + alpha * r2);
                    g = (int) ((1 - alpha) * g1 + alpha * g2);
                    b = (int) ((1 - alpha) * b1 + alpha * b2);

                    pixelBlending = (r << 16) | (g << 8) | (b << 0);
                    bufferBlend.setRGB(x, y, pixelBlending);

                }
            }
            ImageIO.write(bufferBlend, "jpg", file3);
            System.out.println("Imagen creada correctamente");
        } catch (Exception e) {
            System.out.println("Error al leer la imagen: " + e.getMessage());
        }
    }

    private static void blendingFuncion2() {
        File file1 = new File("Imagenes/fondo.jpg");
        File file2 = new File("Imagenes/superior.jpg");
        File file3 = new File("Imagenes/Blending/blending_funcion_2.jpg");

        int ancho, alto, pixel1, pixel2, pixelBlending;
        int r1, g1, b1, r2, b2, g2, r, g, b;
        int mascara = 0xFF;
        float alpha = 0.5f;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            BufferedImage buffer2 = ImageIO.read(file2);

            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();

            // Escalamos una imagen
            Image imgTmp = buffer2.getScaledInstance(ancho, alto, Image.SCALE_FAST);
            BufferedImage bufferTmp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D grTmp = bufferTmp.createGraphics();
            grTmp.drawImage(imgTmp, 0, 0, null);
            grTmp.dispose();

            buffer2 = bufferTmp;

            BufferedImage bufferBlend = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {

                    pixel1 = buffer1.getRGB(x, y);
                    pixel2 = buffer2.getRGB(x, y);

                    r1 = (pixel1 >> 16) & mascara;
                    g1 = (pixel1 >> 8) & mascara;
                    b1 = (pixel1 >> 0) & mascara;

                    r2 = (pixel2 >> 16) & mascara;
                    g2 = (pixel2 >> 8) & mascara;
                    b2 = (pixel2 >> 0) & mascara;


                    r = (int) ((1 - alpha) * r1 + alpha * r2);
                    g = (int) ((1 - alpha) * g1 + alpha * g2);
                    b = (int) ((1 - alpha) * b1 + alpha * b2);

                    r = Math.min(255, Math.max(0, r));
                    g = Math.min(255, Math.max(0, g));
                    b = Math.min(255, Math.max(0, b));

                    pixelBlending = (r << 16) | (g << 8) | (b << 0);
                    bufferBlend.setRGB(x, y, pixelBlending);

                }
            }
            ImageIO.write(bufferBlend, "jpg", file3);
            System.out.println("Imagen creada correctamente");
        } catch (Exception e) {
            System.out.println("Error al leer la imagen: " + e.getMessage());
        }
    }

    private static void blendingFuncion() {
        File file1 = new File("Imagenes/fondo.jpg");
        File file2 = new File("Imagenes/superior.jpg");
        File file4 = new File("Imagenes/atardecer.jpg");
        File file3 = new File("Imagenes/Blending/blending_funcion_3.jpg");

        int ancho, alto, pixel1, pixel2, pixel3, pixelBlending;
        int r1, g1, b1, r2, b2, g2, r3, b3, g3, r, g, b;
        int mascara = 0xFF;
        float alpha = 0.5f;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            BufferedImage buffer2 = ImageIO.read(file2);
            BufferedImage buffer3 = ImageIO.read(file4);

            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();

            // Escalamos una imagen
            Image imgTmp = buffer2.getScaledInstance(ancho, alto, Image.SCALE_FAST);
            BufferedImage bufferTmp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D grTmp = bufferTmp.createGraphics();
            grTmp.drawImage(imgTmp, 0, 0, null);
            grTmp.dispose();

            // Escalamos una imagen
            Image imgTmp2 = buffer3.getScaledInstance(ancho, alto, Image.SCALE_FAST);
            BufferedImage bufferTmp2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D grTmp2 = bufferTmp2.createGraphics();
            grTmp2.drawImage(imgTmp2, 0, 0, null);
            grTmp2.dispose();

            buffer3 = bufferTmp2;
            buffer2 = bufferTmp;

            BufferedImage bufferBlend = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {

                    pixel1 = buffer1.getRGB(x, y);
                    pixel2 = buffer2.getRGB(x, y);
                    pixel3 = buffer3.getRGB(x, y);

                    r1 = (pixel1 >> 16) & mascara;
                    g1 = (pixel1 >> 8) & mascara;
                    b1 = (pixel1 >> 0) & mascara;

                    r2 = (pixel2 >> 16) & mascara;
                    g2 = (pixel2 >> 8) & mascara;
                    b2 = (pixel2 >> 0) & mascara;

                    r3 = (pixel3 >> 16) & mascara;
                    g3 = (pixel3 >> 8) & mascara;
                    b3 = (pixel3 >> 0) & mascara;

                    r = (int) ((1 - alpha) * r1 + alpha * r2 + alpha * r3);
                    g = (int) ((1 - alpha) * g1 + alpha * g2 + alpha * g3);
                    b = (int) ((1 - alpha) * b1 + alpha * b2 + alpha * b3);

                    r = Math.min(255, Math.max(0, r));
                    g = Math.min(255, Math.max(0, g));
                    b = Math.min(255, Math.max(0, b));

                    // Sumative blending
                    // r = Math.min(255, r1 + r2);
                    // g = Math.min(255, g1 + g2);
                    // b = Math.min(255, b1 + b2);

                    // Multiplicative blending
                    // r = (r1 * r2)/255;
                    // g = (g1 * g2)/255;
                    // b = (b1 * b2)/255;

                    pixelBlending = (r << 16) | (g << 8) | (b << 0);
                    bufferBlend.setRGB(x, y, pixelBlending);

                }
            }
            ImageIO.write(bufferBlend, "jpg", file3);
            System.out.println("Imagen creada correctamente");
        } catch (Exception e) {
            System.out.println("Error al leer la imagen: " + e.getMessage());
        }
    }

}
