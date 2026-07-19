package vista;

import java.io.File;
import java.io.IOException;

import controlador.Controlador;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.histogramas.Histograma;
import modelo.kernels.Kernels;
import modelo.matrizColores.Matrizes;

public class VistaPrincipal {

    private static final String COLOR_FONDO = "#FFFFCC";
    private static final String COLOR_PANEL = "#FEE187";
    private static final String COLOR_BORDE = "#FEAB49";
    private static final String COLOR_TEXTO = "#400013";
    private static final String COLOR_TEXTO_SUAVE = "#800026";
    private static final String COLOR_ACENTO = "#D41020";

    private static final int ANCHO_PANEL_GRANDE = 310;
    private static final int ANCHO_PANEL_MEDIANO = 200;
    private static final int ANCHO_PANEL_PEQUENO = 160;

    // Breakpoints
    private static final double BP_GRANDE = 1100;
    private static final double BP_MEDIANO = 800;

    private final Stage stage;
    private final Controlador controlador;

    private ImageView vistaOriginal;
    private ImageView vistaResultado;
    private Label lblEstado;
    private Label lblNombreArchivo;

    // Referencias para responsividad
    private Button btnCargar;
    private Button btnGuardar;
    private Button btnRestaurar;
    private Label titulo;
    private ScrollPane panelEfectos;
    private BorderPane raiz;
    private HBox areaImagenes;
    private VBox panelOriginal;
    private VBox panelResultado;

    public VistaPrincipal(Stage stage) {
        this.stage = stage;
        this.controlador = new Controlador();
    }

    public void mostrar() {
        stage.setTitle("Procesamiento de Imágenes");
        stage.setMinWidth(500);
        stage.setMinHeight(400);

        raiz = new BorderPane();
        raiz.setStyle("-fx-background-color: " + COLOR_FONDO + ";");

        raiz.setTop(construirBarraHerramientas());
        raiz.setLeft(construirPanelEfectos());
        raiz.setCenter(construirAreaImagenes());
        raiz.setBottom(construirBarraEstado());

        Scene escena = new Scene(raiz, 1100, 650);
        stage.setScene(escena);
        escena.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                stage.close();
            }
        });
        stage.show();

        // Aplicar responsividad al inicio y en cada cambio de tamaño
        aplicarResponsividad(stage.getWidth());
        stage.widthProperty().addListener((obs, oldVal, newVal) -> aplicarResponsividad(newVal.doubleValue()));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> aplicarAlturaResponsiva(newVal.doubleValue()));
    }

    // Responsividad central
    private void aplicarResponsividad(double ancho) {
        if (ancho >= BP_GRANDE) {
            aplicarModoGrande();
        } else if (ancho >= BP_MEDIANO) {
            aplicarModoMediano();
        } else {
            aplicarModoPequeno();
        }
        actualizarImageViews(ancho);
    }

    private void aplicarModoGrande() {
        // Barra: texto completo
        titulo.setFont(Font.font("System", FontWeight.BOLD, 18));
        titulo.setText("Grupo 4");
        btnCargar.setText("📂  Cargar imagen");
        btnGuardar.setText("💾  Guardar");
        btnRestaurar.setText("↩  Restaurar");
        lblNombreArchivo.setVisible(true);
        lblNombreArchivo.setManaged(true);

        // Panel lateral: ancho completo
        panelEfectos.setPrefWidth(ANCHO_PANEL_GRANDE);
        panelEfectos.setMinWidth(ANCHO_PANEL_GRANDE);

        // Imágenes: lado a lado
        mostrarImagenesHorizontal();
    }

    private void aplicarModoMediano() {
        // Barra: texto reducido
        titulo.setFont(Font.font("System", FontWeight.BOLD, 15));
        titulo.setText("Taller 2");
        btnCargar.setText("Cargar");
        btnGuardar.setText("Guardar");
        btnRestaurar.setText("↩");
        lblNombreArchivo.setVisible(true);
        lblNombreArchivo.setManaged(true);

        // Panel lateral: más angosto
        panelEfectos.setPrefWidth(ANCHO_PANEL_MEDIANO);
        panelEfectos.setMinWidth(ANCHO_PANEL_MEDIANO);

        // Imágenes: lado a lado
        mostrarImagenesHorizontal();
    }

    private void aplicarModoPequeno() {
        // Barra: solo iconos
        titulo.setFont(Font.font("System", FontWeight.BOLD, 13));
        titulo.setText("T2");
        btnCargar.setText("📂");
        btnGuardar.setText("💾");
        btnRestaurar.setText("↩");
        lblNombreArchivo.setVisible(false);
        lblNombreArchivo.setManaged(false);

        // Panel lateral: mínimo
        panelEfectos.setPrefWidth(ANCHO_PANEL_PEQUENO);
        panelEfectos.setMinWidth(ANCHO_PANEL_PEQUENO);

        // Imágenes: apiladas verticalmente
        mostrarImagenesVertical();
    }

    private void mostrarImagenesHorizontal() {
        if (raiz.getCenter() instanceof HBox)
            return;
        areaImagenes.getChildren().clear();
        areaImagenes.getChildren().addAll(panelOriginal, panelResultado);
        HBox.setHgrow(panelOriginal, Priority.ALWAYS);
        HBox.setHgrow(panelResultado, Priority.ALWAYS);
        raiz.setCenter(areaImagenes);
    }

    private void mostrarImagenesVertical() {

        if (raiz.getCenter() instanceof VBox)
            return;
        VBox vertical = new VBox(1, panelOriginal, panelResultado);
        VBox.setVgrow(panelOriginal, Priority.ALWAYS);
        VBox.setVgrow(panelResultado, Priority.ALWAYS);
        vertical.setStyle("-fx-background-color: " + COLOR_FONDO + ";");
        raiz.setCenter(vertical);
    }

    private void actualizarImageViews(double ancho) {

        double anchoPanel = panelEfectos.getPrefWidth();
        double anchoDisponible = ancho - anchoPanel - 20;
        boolean esVertical = raiz.getCenter() instanceof VBox;

        if (esVertical) {
            vistaOriginal.setFitWidth(anchoDisponible * 0.9);
            vistaResultado.setFitWidth(anchoDisponible * 0.9);
        } else {
            vistaOriginal.setFitWidth(anchoDisponible / 2.1);
            vistaResultado.setFitWidth(anchoDisponible / 2.1);
        }
    }

    private void aplicarAlturaResponsiva(double altura) {
        double alturaImagenes = altura - 130;
        vistaOriginal.setFitHeight(alturaImagenes);
        vistaResultado.setFitHeight(alturaImagenes);

        if (lblEstado != null) {
            lblEstado.setFont(Font.font(altura < 500 ? 10 : 12));
        }
    }

    // Construcción de la barra

    private HBox construirBarraHerramientas() {
        HBox barra = new HBox(8);
        barra.setPadding(new Insets(10, 14, 10, 14));
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setStyle("-fx-background-color: " + COLOR_PANEL + ";"
                + "-fx-border-color: " + COLOR_BORDE + ";"
                + "-fx-border-width: 0 0 1 0;");

        titulo = new Label("Taller 2");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 18));
        titulo.setTextFill(Color.web(COLOR_ACENTO));

        lblNombreArchivo = new Label("Sin imagen cargada");
        lblNombreArchivo.setTextFill(Color.web(COLOR_TEXTO_SUAVE));
        lblNombreArchivo.setFont(Font.font(12));
        lblNombreArchivo.setMaxWidth(180);

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        btnCargar = crearBoton("Cargar imagen", true);
        btnGuardar = crearBoton("Guardar", true);
        btnRestaurar = crearBoton("Restaurar", true);

        btnCargar.setOnAction(e -> accionCargar());
        btnGuardar.setOnAction(e -> accionGuardar());
        btnRestaurar.setOnAction(e -> accionRestaurar());

        // Grupo de botones con ancho mínimo fijo para que no colapsen
        HBox grupoBotones = new HBox(6, btnRestaurar, btnGuardar, btnCargar);
        grupoBotones.setAlignment(Pos.CENTER_RIGHT);
        grupoBotones.setMinWidth(Region.USE_PREF_SIZE);

        barra.getChildren().addAll(titulo, lblNombreArchivo, espaciador, grupoBotones);
        return barra;
    }

    // Panel de efectos

    private ScrollPane construirPanelEfectos() {
        Accordion acordeon = new Accordion();
        acordeon.setStyle("-fx-background-color: " + COLOR_PANEL + ";");

        acordeon.getPanes().addAll(
                panelDuplicar(),
                panelImagenPersonalizada(),
                panelEscalaGrises(),
                panelNegativo(),
                panelBrillo(),
                panelModeloHsv(),
                panelRecorteBits(),
                panelBlancoNegro(),
                panelReducirColor(),
                panelGananciaColor(),
                panelConvolucion(),
                panelMatrizColores(),
                panelHistograma());

        panelEfectos = new ScrollPane(acordeon);
        panelEfectos.setFitToWidth(true);
        panelEfectos.setPrefWidth(ANCHO_PANEL_GRANDE);
        panelEfectos.setMinWidth(ANCHO_PANEL_GRANDE);
        panelEfectos.setStyle("-fx-background-color: " + COLOR_PANEL + ";"
                + "-fx-border-color: " + COLOR_BORDE + ";"
                + "-fx-border-width: 0 1 0 0;");
        return panelEfectos;
    }

    // Área de imágenes

    private HBox construirAreaImagenes() {
        vistaOriginal = crearImageView();
        vistaResultado = crearImageView();

        panelOriginal = panelImagen("  Original", vistaOriginal);
        panelResultado = panelImagen("  Resultado", vistaResultado);

        areaImagenes = new HBox(1, panelOriginal, panelResultado);
        HBox.setHgrow(panelOriginal, Priority.ALWAYS);
        HBox.setHgrow(panelResultado, Priority.ALWAYS);
        areaImagenes.setStyle("-fx-background-color: " + COLOR_FONDO + ";");
        return areaImagenes;
    }

    private VBox panelImagen(String titulo, ImageView imageView) {
        Label lbl = new Label(titulo);
        lbl.setTextFill(Color.web(COLOR_TEXTO_SUAVE));
        lbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        lbl.setPadding(new Insets(8, 12, 4, 12));

        StackPane stack = new StackPane(imageView);
        stack.setStyle("-fx-background-color: #111122;");
        VBox.setVgrow(stack, Priority.ALWAYS);

        VBox panel = new VBox(lbl, stack);
        panel.setStyle("-fx-background-color: " + COLOR_FONDO + ";"
                + "-fx-border-color: " + COLOR_BORDE + ";"
                + "-fx-border-width: 0 1 0 0;");
        VBox.setVgrow(stack, Priority.ALWAYS);
        return panel;
    }

    private ImageView crearImageView() {
        ImageView iv = new ImageView();
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        return iv;
    }

    // Barra de estado

    private HBox construirBarraEstado() {
        lblEstado = new Label("Listo. Carga una imagen para comenzar.");
        lblEstado.setTextFill(Color.web(COLOR_TEXTO_SUAVE));
        lblEstado.setFont(Font.font(12));

        HBox barra = new HBox(lblEstado);
        barra.setPadding(new Insets(6, 12, 6, 12));
        barra.setStyle("-fx-background-color: " + COLOR_PANEL + ";"
                + "-fx-border-color: " + COLOR_BORDE + ";"
                + "-fx-border-width: 1 0 0 0;");
        return barra;
    }

    // Paneles de efectos

    private TitledPane panelDuplicar() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Duplica la imagen, solo lee los pixeles de la imagen original.");
        Button btn = crearBotonEfecto("Duplicar");
        btn.setOnAction(e -> aplicarEfecto(() -> controlador.aplicarDuplicado(), "Duplicado"));
        cuerpo.getChildren().addAll(desc, btn);
        return titledPane("Duplicado", cuerpo);
    }

    private TitledPane panelImagenPersonalizada() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Generar una imagen con pixeles randoms.");
        Button btn = crearBotonEfecto("Imagen Personalizada");
        btn.setOnAction(e -> {
            Image resultado = controlador.aplicarImagenPersonalizada();
            vistaResultado.setImage(resultado);
            setEstado("Efecto aplicado: Imagen Personalizada");
        });
        cuerpo.getChildren().addAll(desc, btn);
        return titledPane("Imagen Personalizada", cuerpo);
    }

    private TitledPane panelEscalaGrises() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Convierte a escala de grises.");
        Button btn = crearBotonEfecto("Aplicar Escala de Grises");
        btn.setOnAction(e -> aplicarEfecto(() -> controlador.aplicarEscalaGrises(), "Escala Grises"));
        cuerpo.getChildren().addAll(desc, btn);
        return titledPane("Escala de Grises", cuerpo);
    }

    private TitledPane panelNegativo() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Efecto negativo.");
        Button btn = crearBotonEfecto("Aplicar Negativo");
        btn.setOnAction(e -> aplicarEfecto(() -> controlador.aplicarNegativo(), "Negativo"));
        cuerpo.getChildren().addAll(desc, btn);
        return titledPane("Negativo", cuerpo);
    }

    private TitledPane panelBrillo() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Aplicar Brillo.");
        Label lblU = new Label("Brillo: 128");
        lblU.setTextFill(Color.web(COLOR_TEXTO));
        Slider slider = crearSlider(0, 255, 240);
        slider.valueProperty().addListener((o, ov, nv) -> lblU.setText("Brillo: " + nv.intValue()));
        Button btn = crearBotonEfecto("Brillo");
        btn.setOnAction(e -> aplicarEfecto(() -> controlador.aplicarBrillo((int) slider.getValue()),
                "Brillo =" + (int) slider.getValue()));
        cuerpo.getChildren().addAll(desc, lblU, slider, btn);
        return titledPane("Aplicar Brillo", cuerpo);
    }

    private TitledPane panelModeloHsv() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Ajusta saturación, brillo y transparencia en modelo HSV.");
        Label lblS = new Label("Saturación (S): 1.0");
        lblS.setTextFill(Color.web(COLOR_TEXTO));
        Label lblB = new Label("Brillo (V): 1.0");
        lblB.setTextFill(Color.web(COLOR_TEXTO));
        Label lblT = new Label("Transparencia (A): 1.0");
        lblT.setTextFill(Color.web(COLOR_TEXTO));
        Slider sS = crearSlider(0.0, 2.0, 1.0);
        Slider sB = crearSlider(0.0, 2.0, 1.0);
        Slider sT = crearSlider(0.0, 2.0, 1.0);
        sS.valueProperty()
                .addListener((o, ov, nv) -> lblS.setText("Saturación (S): " + String.format("%.2f", nv.floatValue())));
        sB.valueProperty()
                .addListener((o, ov, nv) -> lblB.setText("Brillo (V): " + String.format("%.2f", nv.floatValue())));
        sT.valueProperty().addListener(
                (o, ov, nv) -> lblT.setText("Transparencia (A): " + String.format("%.2f", nv.floatValue())));
        Button btn = crearBotonEfecto("Aplicar HSV");
        btn.setOnAction(e -> aplicarEfecto(() -> controlador.aplicarModeloHsv(
                (float) sS.getValue(),
                (float) sB.getValue(),
                (float) sT.getValue()),
                "HSV S=" + String.format("%.2f", sS.getValue())
                        + " V=" + String.format("%.2f", sB.getValue())
                        + " A=" + String.format("%.2f", sT.getValue())));
        cuerpo.getChildren().addAll(desc, lblS, sS, lblB, sB, lblT, sT, btn);
        return titledPane("Modelo HSV", cuerpo);
    }

    private TitledPane panelRecorteBits() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion(
                "Elimina los N bits menos significativos de cada canal RGB y estira el resultado.");
        Label lblU = new Label("Bits a recortar");
        lblU.setTextFill(Color.web(COLOR_TEXTO));
        Slider slider = crearSlider(1, 7, 1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener((o, ov, nv) -> {
            int n = (int) Math.round(nv.doubleValue());
            lblU.setText("Bits a recortar: " + n);
        });
        Button btn = crearBotonEfecto("Aplicar Recorte de Bits");
        btn.setOnAction(e -> {
            int n = (int) Math.round(slider.getValue());
            aplicarEfecto(() -> controlador.aplicarRecorteBits(n), "Recorte de bits");
        });
        cuerpo.getChildren().addAll(desc, lblU, slider, btn);
        return titledPane("Recorte de Bits", cuerpo);
    }

    private TitledPane panelBlancoNegro() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Hacer una imagen en blanco y negro.");
        Button btn = crearBotonEfecto("Aplicar Blanco y Negro");
        btn.setOnAction(e -> aplicarEfecto(() -> controlador.aplicarBlancoNegro(),
                "Blanco y Negro"));
        cuerpo.getChildren().addAll(desc, btn);
        return titledPane("Blanco y Negro", cuerpo);
    }

    private TitledPane panelReducirColor() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion(
                "Reduce los colores de cada canal RGB a N niveles, preservando el canal Alpha.");
        Label lblSeleccion = new Label("Número de colores por canal (N):");
        lblSeleccion.setTextFill(Color.web(COLOR_TEXTO));
        ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(2, 4, 8, 64, 128, 255);
        choiceBox.setValue(8);
        choiceBox.setMaxWidth(Double.MAX_VALUE);
        Label lblValorActual = new Label("N = 8");
        lblValorActual.setTextFill(Color.web(COLOR_TEXTO));
        choiceBox.valueProperty()
                .addListener((o, ov, nv) -> lblValorActual.setText("N = " + nv));
        Button btn = crearBotonEfecto("Aplicar Reducir Color");
        btn.setOnAction(e -> {
            int n = choiceBox.getValue();
            aplicarEfecto(() -> controlador.aplicarReducirColor(n),
                    "Reducir Color");
        });
        cuerpo.getChildren().addAll(desc, lblSeleccion, choiceBox, lblValorActual, btn);
        return titledPane("Reducir Color", cuerpo);
    }

    private TitledPane panelGananciaColor() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Suma/resta un delta a cada canal RGB para colorear la imagen.");
        Label lblR = new Label("Rojo (R): 0");
        lblR.setTextFill(Color.RED);
        Label lblG = new Label("Verde (G): 0");
        lblG.setTextFill(Color.GREEN);
        Label lblB = new Label("Azul (B): 0");
        lblB.setTextFill(Color.CORNFLOWERBLUE);
        Slider sR = crearSlider(0.0, 2.0, 0.0);
        Slider sG = crearSlider(0.0, 2.0, 0.0);
        Slider sB = crearSlider(0.0, 2.0, 0.0);
        sR.valueProperty().addListener((o, ov, nv) -> lblR.setText("Rojo : " + String.format("%.2f", nv.floatValue())));
        sG.valueProperty()
                .addListener((o, ov, nv) -> lblG.setText("Verde : " + String.format("%.2f", nv.floatValue())));
        sB.valueProperty().addListener((o, ov, nv) -> lblB.setText("Azul: " + String.format("%.2f", nv.floatValue())));
        Button btn = crearBotonEfecto("Aplicar Ganancia de Color");
        btn.setOnAction(e -> aplicarEfecto(
                () -> controlador.aplicarGananciaColor((float) sR.getValue(), (float) sG.getValue(),
                        (float) sB.getValue()),
                "Ganancia R=" + String.format("%.2f", sR.getValue()) + " G=" + String.format("%.2f", sG.getValue())
                        + " B=" + String.format("%.2f", sB.getValue())));
        cuerpo.getChildren().addAll(desc, lblR, sR, lblG, sG, lblB, sB, btn);
        return titledPane("Ganancia de Color", cuerpo);
    }

    private TitledPane panelConvolucion() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Aplica un kernel de convolución 3×3 N veces consecutivas.");
        Label lblKernel = new Label("Kernel:");
        lblKernel.setTextFill(Color.web(COLOR_TEXTO));
        ComboBox<Kernels.NombreKernel> combo = new ComboBox<>();
        combo.getItems().addAll(Kernels.NombreKernel.values());
        combo.setValue(Kernels.NombreKernel.ENFOQUE);
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setStyle("-fx-background-color: white; -fx-text-fill: " + COLOR_TEXTO + ";");
        Label lblP = new Label("Pasadas: 1");
        lblP.setTextFill(Color.web(COLOR_TEXTO));
        Slider sPasadas = crearSlider(1, 10, 1);
        sPasadas.setMajorTickUnit(1);
        sPasadas.setSnapToTicks(true);
        sPasadas.valueProperty().addListener((o, ov, nv) -> lblP.setText("Pasadas: " + nv.intValue()));
        Button btn = crearBotonEfecto("Aplicar Convolución");
        btn.setOnAction(
                e -> aplicarEfecto(() -> controlador.aplicarConvolucion(combo.getValue(), (int) sPasadas.getValue()),
                        "Convolución: " + combo.getValue().getEtiqueta()));
        cuerpo.getChildren().addAll(desc, lblKernel, combo, lblP, sPasadas, btn);
        return titledPane("Convolución", cuerpo);
    }

    private TitledPane panelMatrizColores() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Aplica una matriz de colores.");
        Label lblKernel = new Label("Matriz:");
        lblKernel.setTextFill(Color.web(COLOR_TEXTO));
        ComboBox<Matrizes.NombreMatriz> combo = new ComboBox<>();
        combo.getItems().addAll(Matrizes.NombreMatriz.values());
        combo.setValue(Matrizes.NombreMatriz.ESCALA_GRISES);
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setStyle("-fx-background-color: white; -fx-text-fill: " + COLOR_TEXTO + ";");
        Button btn = crearBotonEfecto("Aplicar Matriz");
        btn.setOnAction(
                e -> aplicarEfecto(() -> controlador.aplicarMatrizColores(combo.getValue()),
                        "Matriz: " + combo.getValue().getEtiqueta()));
        cuerpo.getChildren().addAll(desc, lblKernel, combo, btn);
        return titledPane("Matriz de Colores", cuerpo);
    }

    private TitledPane panelHistograma() {
        VBox cuerpo = new VBox(10);
        cuerpo.setPadding(new Insets(12));
        Label desc = etiquetaDescripcion("Generar un Histograma.");
        Label lblKernel = new Label("Histograma:");
        lblKernel.setTextFill(Color.web(COLOR_TEXTO));
        ComboBox<Histograma.TipoHistograma> combo = new ComboBox<>();
        combo.getItems().addAll(Histograma.TipoHistograma.values());
        combo.setValue(Histograma.TipoHistograma.ROJO);
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setStyle("-fx-background-color: white; -fx-text-fill: " + COLOR_TEXTO + ";");
        Button btn = crearBotonEfecto("Generar Histograma");
        btn.setOnAction(
                e -> aplicarEfecto(() -> controlador.aplicarHistograma(combo.getValue()),
                        "Histograma: " + combo.getValue()));
        cuerpo.getChildren().addAll(desc, lblKernel, combo, btn);
        return titledPane("Histogramas", cuerpo);
    }

    // Acciones
    private void accionCargar() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar imagen");
        fc.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"));
        File archivo = fc.showOpenDialog(stage);
        if (archivo == null)
            return;
        try {
            Image imagen = controlador.cargarImagen(archivo);
            vistaOriginal.setImage(imagen);
            vistaResultado.setImage(null); // <-- Solo se muestra en la izquierda inicialmente
            lblNombreArchivo.setText(archivo.getName());
            setEstado("Imagen cargada: " + archivo.getName());
            aplicarResponsividad(stage.getWidth());
        } catch (IOException ex) {
            mostrarError("No se pudo cargar la imagen", ex.getMessage());
        }
    }

    private void accionGuardar() {
        if (!controlador.hayImagenCargada()) {
            mostrarAlerta("Sin imagen", "Primero carga una imagen.");
            return;
        }
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar imagen");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fc.setInitialFileName("resultado.png");
        File destino = fc.showSaveDialog(stage);
        if (destino == null)
            return;
        try {
            controlador.guardarImagen(destino);
            setEstado("✔  Imagen guardada en: " + destino.getAbsolutePath());
        } catch (IOException ex) {
            mostrarError("Error al guardar", ex.getMessage());
        }
    }

    private void accionRestaurar() {
        if (!controlador.hayImagenCargada())
            return;
        vistaResultado.setImage(controlador.restaurarOriginal());
        setEstado("↩  Imagen restaurada al original.");
    }

    private void mostrarResultado(Image resultado, String nombreEfecto) {
        vistaResultado.setImage(resultado);
        setEstado("Efecto aplicado: " + nombreEfecto);
    }

    private void aplicarEfecto(java.util.function.Supplier<Image> accion, String nombreEfecto) {
        try {
            mostrarResultado(accion.get(), nombreEfecto);
        } catch (IllegalStateException ex) {
            mostrarAlerta("Sin imagen", ex.getMessage());
        }
    }

    // Utilidades

    private Button crearBoton(String texto, boolean primario) {
        Button btn = new Button(texto);
        btn.setFont(Font.font(13));
        btn.setCursor(javafx.scene.Cursor.HAND);
        btn.setMinWidth(Region.USE_PREF_SIZE);
        btn.setStyle(primario ? estiloBotonPrimario() : estiloBotonSecundario());
        return btn;
    }

    private Button crearBotonEfecto(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setFont(Font.font(13));
        btn.setCursor(javafx.scene.Cursor.HAND);
        btn.setStyle(estiloBotonPrimario());
        return btn;
    }

    private Slider crearSlider(double min, double max, double valor) {
        Slider s = new Slider(min, max, valor);
        s.setShowTickLabels(true);
        s.setShowTickMarks(true);
        s.setStyle("-fx-control-inner-background: " + COLOR_FONDO + ";");
        return s;
    }

    private Label etiquetaDescripcion(String texto) {
        Label lbl = new Label(texto);
        lbl.setWrapText(true);
        lbl.setTextFill(Color.web(COLOR_TEXTO_SUAVE));
        lbl.setFont(Font.font(12));
        return lbl;
    }

    private TitledPane titledPane(String titulo, VBox contenido) {
        TitledPane tp = new TitledPane(titulo, contenido);
        tp.setStyle("-fx-text-fill: " + COLOR_TEXTO + "; -fx-background-color: " + COLOR_PANEL + ";");
        return tp;
    }

    private void setEstado(String mensaje) {
        Platform.runLater(() -> lblEstado.setText(mensaje));
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(titulo);
        a.setContentText(mensaje);
        a.showAndWait();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }

    private String estiloBotonPrimario() {
        return "-fx-background-color: " + COLOR_ACENTO
                + "; -fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 7 16 7 16;";
    }

    private String estiloBotonSecundario() {
        return "-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: " + COLOR_BORDE
                + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 7 16 7 16;";
    }
}