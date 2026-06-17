# BiblioApp - Buscador de libros

BiblioApp es una aplicación móvil desarrollada para la plataforma Android, enfocada en la búsqueda y gestión de libros. Este proyecto ha sido diseñado bajo estándares de arquitectura limpia y procesos de automatización.

---

## Integrantes
*   Nahuel Leguisamon
*   Mateo Sparano

---

## Funcionalidades Principales
- **Búsqueda de Libros:** Consulta avanzada de volúmenes mediante la **Google Books API**, obteniendo resultados en tiempo real.
- **Persistencia Local:** Almacenamiento de libros favoritos y gestión de la base de datos interna mediante Room.
- **Detalle de Libros:** Visualización completa de información bibliográfica, autores y reseñas.
- **Interfaz Adaptativa:** Experiencia de usuario optimizada con componentes modernos de UI, incluyendo la opción de cambiar entre modo claro y modo oscuro desde los ajustes.

---

## Arquitectura
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
| **Componentes Android** | Broadcast Receiver & Content Provider |
| **Inyección/Gestión** | StateFlow & ViewModel |

---

## Clases Principales
Para facilitar la revisión técnica, se detallan los componentes clave:

*   **Vistas:** `SearchScreen`, `FavoritesScreen`, `DetailScreen`, `SettingsScreen`.
*   **ViewModels:** `BookViewModel`, `SearchViewModel`.
*   **Repositorio:** `BookRepository`.
*   **Servicios de Red:** `ApiService`, `RetrofitInstance`.
*   **Datos y Filtros:** `BookItem`, `SearchFilter`.
*   **Persistencia:** `AppDatabase`, `BookDao`, `BookEntity`.

---

## Compilar y Ejecutar (Build Completo)
Utilizamos **Fastlane** para automatizar un pipeline que integra calidad, seguridad y construcción en un solo paso. Para ejecutar el proceso completo, incluyendo el análisis estático (SAST), usá:

```powershell
bundle exec fastlane android build
```
*Este comando compila el proyecto, valida las reglas de estilo/seguridad con Detekt y genera el APK.*

---

## Seguridad y Calidad (SAST)
El análisis estático asegura el cumplimiento de las mejores prácticas de programación. A continuación, el estado del último reporte:

| Métrica | Resultado |
| :--- | :--- |
| **Archivos Analizados** | 19 |
| **Líneas de Código** | 1,069 |
| **Problemas Detectados** | **0** |
| **Complejidad** | 75 |

#### Generar Reporte Detallado
*El informe se encuentra en: `docs/security/detekt-report.html`*

---

## Manual de Usuario
1.  **Inicio:** Escribí el título o autor del libro y tocá Buscar para obtener resultados en tiempo real.
2.  **Exploración:** Seleccioná un libro de la lista para ver su descripción detallada.
3.  **Favoritos:** Tocá el icono del corazón para guardar un libro. En la sección de **Favoritos**, podés consultar tu lista guardada y eliminar libros tocando el corazón rojo.
4.  **Ajustes:** Cambiá el tema de la aplicación (claro/oscuro) desde la configuración.

---

## API Reference
La aplicación consume datos de la API: **Google Books API**.

---
