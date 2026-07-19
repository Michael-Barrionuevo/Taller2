package modelo.matrizColores;

public class Matrizes {

    public static final float[][] coloresEscalaGrises = {
            { 0.299f, 0.299f, 0.299f, 0.0f },
            { 0.299f, 0.299f, 0.299f, 0.0f },
            { 0.299f, 0.299f, 0.299f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    public static final float[][] coloresFiltroSepia = {
            { 0.393f, 0.769f, 0.189f, 0.0f },
            { 0.349f, 0.686f, 0.168f, 0.0f },
            { 0.272f, 0.534f, 0.131f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    public static final float[][] aumentarBrillo = {
            { 1.0f, 0.0f, 0.0f, 0.3f },
            { 0.0f, 1.0f, 0.0f, 0.3f },
            { 0.0f, 0.0f, 1.0f, 0.3f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    public static final float[][] saturacionAlta = {
            { 1.5f, -0.25f, -0.25f, 0.0f },
            { -0.25f, 1.5f, -0.25f, 0.0f },
            { -0.25f, -0.25f, 1.5f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    public static final float[][] polaroid = {
            { 1.438f, -0.062f, -0.062f, 0.0f },
            { -0.122f, 1.378f, -0.122f, 0.0f },
            { -0.016f, -0.016f, 1.483f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    public static final float[][] realzarRojos = {
            { 1.5f, 0.0f, 0.0f, 0.0f },
            { 0.0f, 0.9f, 0.0f, 0.0f },
            { 0.0f, 0.0f, 0.9f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    public static final float[][] fotoVieja = {
            { 0.7f, 0.2f, 0.1f, 0.1f },
            { 0.1f, 0.6f, 0.1f, 0.1f },
            { 0.0f, 0.1f, 0.5f, 0.05f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    public static final float[][] realzarAzules = {
            { 0.9f, 0.0f, 0.0f, 0.0f },
            { 0.0f, 0.9f, 0.0f, 0.0f },
            { 0.0f, 0.0f, 1.5f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    public enum NombreMatriz {
        ESCALA_GRISES("Escala de Grises", Matrizes.coloresEscalaGrises),
        SEPIA("Filtro Sepia", Matrizes.coloresFiltroSepia),
        BRILLO("Aumentar Brillo", Matrizes.aumentarBrillo),
        SATURACION("Saturación Alta", Matrizes.saturacionAlta),
        POLAROID("Efecto Polaroid", Matrizes.polaroid),
        REALZAR_ROJOS("Realzar Rojos", Matrizes.realzarRojos),
        REALZAR_AZULES("Realzar Azules", Matrizes.realzarAzules),
        FOTO_VIEJA("Foto Antigua", Matrizes.fotoVieja);

        private final String etiqueta;
        private final float[][] matrizColor;

        NombreMatriz(String etiqueta, float[][] matrizColor) {
            this.etiqueta = etiqueta;
            this.matrizColor = matrizColor;
        }

        public float[][] getMatrizColor() {
            return matrizColor;
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        @Override
        public String toString() {
            return etiqueta;
        }
    }

}
