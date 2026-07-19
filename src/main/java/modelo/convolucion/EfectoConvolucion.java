package modelo.convolucion;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import modelo.efectos.IEfecto;
import modelo.kernels.Kernels;

public class EfectoConvolucion implements IEfecto {

    private Kernels.NombreKernel kernelSeleccionado;
    private int pasadas;

    public EfectoConvolucion(Kernels.NombreKernel kernel, int pasadas) {
        this.kernelSeleccionado = kernel;
        this.pasadas = Math.max(1, Math.min(10, pasadas));
    }

    public EfectoConvolucion() {
        this(Kernels.NombreKernel.NORMAL, 1);
    }

    public Kernels.NombreKernel getKernelSeleccionado() {
        return kernelSeleccionado;
    }

    public void setKernelSeleccionado(Kernels.NombreKernel k) {
        this.kernelSeleccionado = k;
    }

    public int getPasadas() {
        return pasadas;
    }

    public void setPasadas(int p) {
        this.pasadas = Math.max(1, Math.min(10, p));
    }

    @Override
    public BufferedImage aplicar(BufferedImage img) {
        float[] matrizKernel = kernelSeleccionado.getKernel();

        Kernel kernel = new Kernel((int) Math.sqrt(matrizKernel.length), (int) Math.sqrt(matrizKernel.length), matrizKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage buffer2 = convertirARGB(img);

        for (int i = 0; i < pasadas; i++) {
            buffer2 = op.filter(buffer2, null);
        }

        return buffer2;
    }

    private BufferedImage convertirARGB(BufferedImage original) {
        if (original.getType() == BufferedImage.TYPE_INT_RGB) {
            return original;
        }
        BufferedImage convertida = new BufferedImage(
                original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g2d = convertida.createGraphics();
        g2d.drawImage(original, 0, 0, null);
        g2d.dispose();
        return convertida;
    }

}
