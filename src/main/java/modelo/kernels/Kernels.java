package modelo.kernels;


public class Kernels {

    // Kernels disponibles 

    /** Sin modificación */
    public static final float[] NORMAL = {
        0f, 0f, 0f,
        0f, 1f, 0f,
        0f, 0f, 0f
    };

    /** Enfoque / Sharpen */
    public static final float[] ENFOQUE = {
        -1f, -1f, -1f,
        -1f,  9f, -1f,
        -1f, -1f, -1f
    };

    /** Desenfoque suave (blur 3x3) */
    public static final float[] DESENFOQUE = {
        1f/9, 1f/9, 1f/9,
        1f/9, 1f/9, 1f/9,
        1f/9, 1f/9, 1f/9
    };

    /** Desenfoque fuerte  */
    public static final float[] DESENFOQUE_FUERTE = {
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,
        1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81,1f/81, 1f/81, 1f/81
    };

    /** Detección de bordes suave */
    public static final float[] BORDES = {
        -0.5f, -0.5f, -0.5f,
        -0.5f,  4.0f, -0.5f,
        -0.5f, -0.5f, -0.5f
    };

    /** Detección de bordes fuerte */
    public static final float[] BORDES_FUERTE = {
        -1.0f, -1.0f, -1.0f,
        -1.0f,  8.0f, -1.0f,
        -1.0f, -1.0f, -1.0f
    };

    /** Aclarar imagen */
    public static final float[] ACLARAR = {
        0.1f, 0.1f, 0.1f,
        0.1f, 1.0f, 0.1f,
        0.1f, 0.1f, 0.1f
    };

    /** Oscurecer imagen */
    public static final float[] OSCURECER = {
        0.01f, 0.01f, 0.01f,
        0.01f, 0.5f, 0.01f,
        0.01f, 0.01f, 0.01f
    };

    // Enum para la interfaz 

    
    public enum NombreKernel {
        NORMAL       ("Normal (sin cambio)",        Kernels.NORMAL),
        ENFOQUE      ("Enfoque / Sharpen",           Kernels.ENFOQUE),
        DESENFOQUE   ("Desenfoque suave",            Kernels.DESENFOQUE),
        DESENFOQUE_FUERTE("Desenfoque fuerte",       Kernels.DESENFOQUE_FUERTE),
        BORDES       ("Bordes suave",                Kernels.BORDES),
        BORDES_FUERTE("Bordes fuerte",               Kernels.BORDES_FUERTE),
        ACLARAR      ("Aclarar",                     Kernels.ACLARAR),
        OSCURECER    ("Oscurecer",                   Kernels.OSCURECER);

        private final String etiqueta;
        private final float[] kernel;

        NombreKernel(String etiqueta, float[] kernel) {
            this.etiqueta = etiqueta;
            this.kernel   = kernel;
        }

        public float[] getKernel()   { return kernel; }
        public String  getEtiqueta() { return etiqueta; }

        @Override
        public String toString() { return etiqueta; }
    }
}
