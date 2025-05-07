# 🌐 API REST para la App de MiTuxtlaApp

Esta API expone endpoints REST para obtener un listado de lugares en Tuxtla Gutiérrez, Chiapas, organizados por categoría, junto con información detallada de cada uno. Para ello, integra el servicio de **Google Places API** y el modelo de inteligencia artificial **GPT-4o Mini** (vía Azure API) para brindar descripciones ajustadas a cada lugar.

## 🔧 Tecnologías y herramientas
- Node.js + Express
- Axious
- Dotenv
- Google Places API
- Azure OpenAI API (GPT-4o Mini)

## 📱 Cliente asociado

La aplicación que consume esta API forma parte del proyecto de la **aplicación Android "MiTuxtlaApp"**, desarrollada con Kotlin y Jetpack Compose; Permite a los usuarios:

- Visualizar una lista de lugares clasificados por categoría.
- Consultar información detallada de cada lugar, incluyendo descripción, ubicación, imagen y datos de contacto.
- Guardar lugares como favoritos en el dispositivo (funcionalidad local).

📱 **Repositorio de la app móvil**: [Ver app en GitHub](https://github.com/EonOohx/mituxtla-app)

## 🚀 Cómo ejecutar este servidor localmente

1. Clona el repositorio:
```bash
git clone https://github.com/EonOohx/mituxtla-api.git
```
2. Instala las dependencias:
```bash
npm install
```
3. Crea un archivo .env en la raíz del proyecto con tus claves de API.
4. Ejecuta el servidor en modo desarrollo:
```bash
npm run dev
```
