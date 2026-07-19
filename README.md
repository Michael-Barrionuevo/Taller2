# Trabajo Grupal – Procesamiento de Imágenes con JavaFX

Aplicación de escritorio para aplicar efectos visuales sobre imágenes,
construida con Java 21 + JavaFX siguiendo el patrón **MVC**.

---

# Integrantes:
- Barrionuevo Michael
- Cruz Kevin
- Lechon Cristian

---

## Estructura del Proyecto

```
ImageFX/
├── pom.xml                          ← Configuración Maven (dependencias + build)
├── README.md
└── src/main/java/
    │
    ├── app/
    │   └── MainApp.java             ← Punto de entrada (Application JavaFX)
    │
    ├── controlador/
    │   └── Controlador.java         ← Puente Vista ↔ Modelo (MVC)
    │
    ├── modelo/
    │   ├── ModeloPrincipal.java     ← Estado y lógica de negocio (sin JavaFX)
    │   │
    │   ├── efectos/
    │   │   ├── IEfecto.java                  ← Interfaz base de todos los efectos
    │   │   ├── Duplicado.java                ← Ejercicio 1
    │   │   ├── ImagenPersonalizada.java      ← Ejercicio 2
    │   │   ├── EfectoRetro.java              ← Ejercicio 3
    │   │   ├── EfectoNegativo.java           ← Ejercicio 4
    │   │   ├── EfectoBlancoNegro.java        ← Ejercicio 5
    │   │   ├── EfectoEscalaGrises.java       ← Ejercicio 6
    │   │   ├── EfectoBrillo.java             ← Ejercicio 7
    │   │   ├── RecorteBits.java              ← Ejercicio 8
    │   │   ├── ModeloHsv.java                ← Ejercicio 9
    │   │   └── EfectoGananciaColor.java      ← Ejercicio 10
    │   │
    │   ├── convolucion/
    │   │   └── EfectoConvolucion.java        ← Convolucion.java + ConvolucionOP.java
    │   │
    │   └── kernels/
    │       └── Kernels.java                  ← Biblioteca de kernels + enum UI
    │
    ├── vista/
    │   └── VistaPrincipal.java      ← Interfaz JavaFX completa (MVC Vista)
    │
    └── util/
        └── ConversorImagen.java     ← BufferedImage ↔ Image JavaFX ↔ Archivo
```

---

## Arquitectura MVC

```
  [VistaPrincipal]  ──llama──▶  [Controlador]  ──usa──▶  [ModeloPrincipal]
       JavaFX                    (intermediario)             (BufferedImage)
         ▲                                                         │
         └────────── recibe Image JavaFX ◀── ConversorImagen ──────┘
```

| Capa          | Responsabilidad                                      |
|---------------|------------------------------------------------------|
| `vista`       | Interfaz gráfica JavaFX, sliders, botones, imágenes  |
| `controlador` | Conecta la vista con el modelo, convierte formatos   |
| `modelo`      | Lógica pura de procesamiento (sin JavaFX)            |
| `util`        | Conversores de imagen reutilizables                  |

---

## Cómo Ejecutar

### Requisitos
- Java 21 o superior
- Maven 3.8+

---
