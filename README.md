# 📱 MiTuxtlaApp: Guía de Lugares Populares de Tuxtla Gutiérrez-

Aplicación Android para conocer los lugares más populares de Tuxtla Gutiérrez, Chiapas, organizados por categorias. Cada lugar se presenta con una ficha informativas que incluye una imagen, ubicación, descripción y datos de contacto. Además, los usuarios pueden guardar los lugares como favoritos para acceder a ellos sin necesidad de acceso a internet.

## 🎯 Propósito del Proyecto
Este proyecto nace como parte de mi proceso de aprendizaje en el desarrollo de aplicaciones móviles Android. Mi objetivo fue aplicar Jetpack Compose para la UI, gestionar el estado con ViewModel + StateFlow, consumir una API REST y trabajar con persistencia de datos local. También consolidé conocimientos sobre arquitectura de aplicaciones, programación asíncrona y reactiva.

La aplicación se conecta a una **API REST** que proporciona la información de los lugares consultados, enlazándose con el servicio de **Google Places API** y utilizando el modelo de IA **GPT-4o Min** para proporcionar una descripción adecuada al lugar con base a la ubicación y comentarios.

🔗 **Repositorio de la API**: [Ver backend en GitHub](https://github.com/EonOohx/mituxtla-api)

## 🔧 Tecnologías y herramientas
- Kotlin
- Jetpack Compose
- MVVM
- Retrofit
- Coil 
- Room
- DataStore
- Navigation Compose
- JUnit
- Git + GitHub

## Aprendizajes
- Manejo de estado en Compose (`remember`, `rememberSavable`, `mutableStateOf`)
- Separación de lógica de presentación con ViewModel
- Obtención y Manejo de Datos Asincronos con Corrutinas
- Carga de Imagenes con Coil
- Persistencia local con Room, DAO, DataStore (Selecccion del Tema de la App)
- Navegación entre Pantallas con Navigation Compose
- Tests Unitarios e Instrumentación con JUnit
- Arquitectura MVVM, Repository Pattern

📦 Descarga el APK: [mituxtla-v1.0.apk](https://github.com/EonOohx/mituxtla-app/releases/download/v1.0.0/mituxtlapp-release.apk)
