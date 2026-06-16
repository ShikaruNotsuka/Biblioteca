[README BIBLIOAPP.txt](https://github.com/user-attachments/files/29022932/README.BIBLIOAPP.txt)
# BiblioApp - Sistema de Gestión Bibliotecaria

BiblioApp es una aplicación móvil desarrollada para la plataforma Android, enfocada en la eficiencia de procesos de búsqueda, organización y gestión de catálogos de libros. Este proyecto ha sido diseñado bajo estándares de arquitectura limpia y procesos de automatización industrial.

---

## Integrantes
*   Nahuel Leguisamon
*   Mateo Sparano

---

## Funcionalidades Principales
- **Gestión de Catálogo:** Búsqueda avanzada de libros consumiendo servicios externos en tiempo real.
- **Persistencia Local:** Almacenamiento de libros favoritos y gestión de base de datos interna mediante Room.
- **Detalle Expandido:** Visualización completa de información bibliográfica, autores y reseñas.
- **Interfaz Adaptativa:** Experiencia de usuario optimizada con componentes modernos de UI.

---

## Arquitectura del Sistema
El proyecto implementa el patrón de arquitectura **MVVM (Model-View-ViewModel)**, garantizando una separación clara de responsabilidades y facilitando la escalabilidad:

1.  **UI Layer (Compose):** Vistas declarativas que reaccionan al estado del ViewModel.
2.  **ViewModel Layer:** Manejo de la lógica de negocio y preparación de datos para la UI.
3.  **Data Layer (Repository):** Fuente única de verdad que decide entre datos de red (Retrofit) o persistencia local (Room).

| Componente | Tecnología |
| :--- | :--- |
| **Lenguaje** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Networking** | Retrofit 2 + OkHttp |
| **Persistencia** | Room Database |
| **Inyección/Gestión** | StateFlow & ViewModel |

---

## Pipeline de Calidad y Seguridad (SAST)
Este proyecto integra un pipeline de **Integración Continua (CI)** profesional gestionado por **Fastlane**. El pilar fundamental de nuestra construcción es el **"Build Blindado"**.

### Análisis Estático con Detekt
Implementamos **Detekt** como herramienta de SAST para asegurar que cada línea de código cumpla con los estándares de calidad definidos:
- **Validación Automática:** Se analizan métricas de complejidad, estilo y posibles fallos lógicos.
- **Bloqueo de Compilación:** Si Detekt encuentra problemas, el pipeline aborta la generación del binario. No se genera ningún APK si existen errores de código o deuda técnica detectada.

Para ejecutar el pipeline completo de validación y generación, utilizamos:
```powershell
bundle exec fastlane android build
```

---

## Instrucciones de Compilación

### Requisitos Técnicos
- Android Studio Ladybug o superior.
- JDK 17.
- Ruby 3.x + Bundler.

### Comandos de Consola
| Objetivo | Comando |
| :--- | :--- |
| **Instalar dependencias Ruby** | `bundle install` |
| **Limpiar Proyecto** | `./gradlew clean` |
| **Ejecutar SAST (Detekt)** | `bundle exec fastlane android sast` |
| **Generar APK (Debug)** | `bundle exec fastlane android build` |

> El APK resultante se encontrará en: `app/build/outputs/apk/debug/app-debug.apk`

---

## API Reference
La aplicación consume datos de la API: **Google Books API**, 

---

