# **Prompts Utilizados Durante el Desarrollo**

## **1\. Arquitectura y Estructura del Proyecto**

* Estoy empezando a hacer una aplicación de un buscador de libros y necesito armar la estructura de carpetas en Android Studio para no tener todo en el MainActivity ¿Cómo me recomiendas organizarlo y que dependencias / librerias de base son indispensables que deba agregar para que funcione?  
* La estructura de las pantallas se sigue viendo confusa en un solo archivo .kt, ¿No seria mejor organizarlas en diferentes archivos para que sea más legible?

## **2\. Consumo de API y Obtenció n de Datos**

* ¿Qué API recomiendas para una app de búsqueda de libros? Se nos dio de ejemplo OpenLibrary, pero unos compañeros usaron Google Books.  
* Estoy intentando consumir una API con Retrofit pero me aparece el error “Unable to create converter”. ¿Qué puede estar faltando?  
* Estoy con Retrofit pero me está costando configurar el conversor de JSON. ¿Qué le falta al Retrofit.Builder para que pueda leer los datos de la API correctamente?

## **3\. Interfaz de Usuario (UI/UX)**

* Necesito ayuda para implementar un modo oscuro en las vistas, que interactúe con un switch en la vista de Settings.  
* El modo oscuro no cambia correctamente los colores de algunos componentes de Material 3\. ¿Cómo puedo revisar si el tema está implementado de forma correcta?   
* Quiero que las portadas se carguen de internet de forma eficiente para que la interfaz se sienta fluida cuando aparecen los resultados.  
* En la pantalla de detalle, busco que cuando toque el botón (corazón) se guarden en de favoritos y que cambie según si ya guardé el libro. Estoy buscando la forma de que el botón consulte la base de datos y se refresque automáticamente.  
* Cree un filtro para buscar libros por su nombre o autor pero no está mostrando solo lo que le mando

## **4\. Persistencia de Datos (Room)**

* Ya tengo la entidad y el DAO creados, pero necesito organizar correctamente Repository, ViewModel y Base de Datos ¿Podrías mostrarme una estructura recomendada y ejemplos básicos de implementación?   
* Estoy armando la base de datos, pero me está saltando el error de que falta el archivo AppDatabase\_Impl. ¿Cómo puedo arreglar la configuración de KSP?  
* Tengo un problema para recuperar los datos desde la tabla de favoritos. Al ejecutar la consulta @Query("SELECT \* FROM favorite\_books"), la lista me devuelve vacía. ¿Podría ser un tema de falta de dependencias en el build.gradle.kts o hay algún paso de configuración en el DAO o en la Entidad que me estoy olvidando hacer?  
* Ahora quiero pasar de la lista de libros a una pantalla con más detalle al tocar un elemento. ¿Cómo recomendación me haces para configurar la navegación y que la segunda pantalla reciba los datos del libro que elegí?  
* Tengo que cumplir con la consigna de componentes nativos. Voy a armar un BroadcastReceiver para avisar si se corta el Wi-Fi y además configurar un ContentProvider para los libros. Necesito ver un ejemplo cualquiera de cómo se integran ambos componentes para hacerlos compatibles con Room.

## **5\. Calidad y Automatización**

* La app ya es funcional, así que voy a usar Fastlane para automatizar la generación del APK. Estoy intentando arrancar con esto y necesito saber qué instalar en la PC para que Fastlane funcione.  
* Logramos instalar SAST y ahora estamos intentando instalar el dependency check, carga hasta el 60% y se queda congelado ahi, sabes que puede estar causando que no avance mas de ese porcentaje?   
* Necesito configurar el Fastfile para que los reportes de seguridad del Detekt se exporten automáticamente a la carpeta docs/security/ cada vez que ejecute el análisis.   
  


