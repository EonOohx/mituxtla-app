# 📱 MiTuxtlaApp: Guía de Lugares Populares de Tuxtla Gutiérrez
![Inicio del proyecto](https://img.shields.io/badge/Inicio-Marzo%202025-blue)
![Finalización del proyecto](https://img.shields.io/badge/Finalizado-Abril%202025-green)

Aplicación Android para conocer los lugares más populares de Tuxtla Gutiérrez, Chiapas, organizados por categorias. Cada lugar se presenta con una ficha informativas que incluye una imagen, ubicación, descripción y datos de contacto. Además, los usuarios pueden guardar los lugares como favoritos para acceder a ellos sin necesidad de acceso a internet.
<p align="center">
  <a href="./assets/demo-home.gif" target="_blank">
    <img src="./assets/demo-home.gif" alt="Demo de pantalla de inicio" width="300"/>
  </a>    
  <a href="./assets/demo-favoritos.gif" target="_blank">
    <img src="./assets/demo-favoritos.gif" alt="Demo de favoritos" width="300"/>
  </a>
</p>

## 🎯 Propósito del Proyecto
Este proyecto nace como parte de mi proceso de aprendizaje en el desarrollo de aplicaciones móviles Android. Mi objetivo fue aplicar Jetpack Compose para la UI, gestionar el estado con ViewModel + StateFlow, consumir una API REST y trabajar con persistencia de datos local. Ademas de ayudarme a reforzar mi comprensión sobre la asincronía, la reactividad y la estructura limpia de aplicaciones móviles.

## 📚 Aprendizajes
- Manejo de estado en Compose (`remember`, `rememberSavable`, `mutableStateOf`)
- Separación de lógica de presentación con ViewModel
- Obtención y Manejo de Datos Asincronos con Corrutines y Retrofit
- Carga de Imagenes con Coil
- Persistencia local con Room, DAO, DataStore (Selecccion del Tema de la App)
- Navegación entre Pantallas con Navigation Compose
- Tests Unitarios e Instrumentación con JUnit
- Arquitectura MVVM y Repository Pattern

## 🛠️ Tecnologías utilizadas
| Categoría         | Tecnologías                         |
|------------------|-------------------------------------|
| Lenguaje          | Kotlin                              |
| UI                | Jetpack Compose, Material 3         |
| Arquitectura      | MVVM, ViewModel, StateFlow, Repository Pattern          |
| Red               | Retrofit, API REST propia           |
| Persistencia      | Room, DataStore                     |
| Imágenes          | Coil                                |
| Testing           | JUnit                               |
| Control de versión| Git + GitHub                        |

## 🔗 API asociada
La aplicación se conecta a una **API REST** que proporciona la información de los lugares consultados, enlazándose con el servicio de **Google Places API** y utilizando el modelo de IA **GPT-4o Mini** para proporcionar una descripción adecuada al lugar con base a la ubicación y comentarios.

**Repositorio de la API**: [Ver backend en GitHub](https://github.com/EonOohx/mituxtla-api)

## 📥 Instalación
### Requisitos
- Android 5.0 (API 21) o superior

### Procedimiento
1. Descarga el archivo `.apk` del siguiente enlance :
[mituxtlaapp-v1.0.apk](https://github.com/EonOohx/mituxtla-app/releases/download/v1.0.0/mituxtlaapp-release.apk)
2. Ábrelo en tu dispositivo Android y acepta los permisos de instalación de fuentes externas
3. ¡Listo para explorar!

⚠️ Nota sobre el rendimiento del servidor.
- La API de esta aplicación está alojada en Render bajo un plan gratuito. Debido a ello, el servidor puede entrar en reposo tras períodos de inactividad. Como resultado, las primeras solicitudes realizadas después de un tiempo sin uso podría demorar algunos segundos en responder con la información de los lugares, esto mientras se reactiva el servicio.

## 📬 Contacto

Si deseas dejar comentarios, sugerencias o contactarme:

📧 Correo: [RaulAlejandro_RodriguezR@hotmail.com]
