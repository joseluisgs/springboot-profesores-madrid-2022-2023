# Spring Boot para Profesores de la Comunidad de Madrid 2022-2022

HTTP: métodos, status, cabeceras, etc.

![logo](https://rubensa.files.wordpress.com/2021/05/spring-boot-logo.png)

- [Spring Boot para Profesores de la Comunidad de Madrid 2022-2022](#spring-boot-para-profesores-de-la-comunidad-de-madrid-2022-2022)
  - [HTTP](#http)
  - [Métodos HTTP](#métodos-http)
  - [Status HTTP](#status-http)
  - [Content Negotiation](#content-negotiation)
  - [CORS](#cors)

## HTTP
HTTP es el protocolo de comunicación de la Web. Es un protocolo de capa de aplicación, es decir, que se encarga de la comunicación entre dos aplicaciones. HTTP es un protocolo orientado a la transferencia de información, es decir, que no mantiene una conexión estable entre el cliente y el servidor, sino que cada petición se realiza de forma independiente.

HTTP es un protocolo de texto plano, es decir, que los mensajes que se intercambian entre cliente y servidor son cadenas de texto. Estas cadenas de texto se componen de una serie de líneas, cada una de las cuales termina con un salto de línea (CR+LF). Cada línea contiene un campo y un valor separados por dos puntos (:). El primer campo de la cabecera es el método, que indica la acción que se va a realizar sobre el recurso. El segundo campo es la URL del recurso, que indica el recurso sobre el que se va a realizar la acción. El tercer campo es la versión del protocolo HTTP que se está utilizando. El resto de campos son cabeceras adicionales que pueden ser necesarias para la petición.

## Métodos HTTP
Los métodos HTTP son los verbos que se utilizan para indicar la acción que se va a realizar sobre un recurso. Los métodos HTTP más utilizados son en este curso son:
- GET: se utiliza para obtener un recurso. Es el método más utilizado en la Web. Por ejemplo, cuando se accede a una página web, se está realizando una petición GET al servidor para obtener el recurso que representa la página web.
- POST: se utiliza para enviar datos al servidor. Por ejemplo, cuando se rellena un formulario y se pulsa el botón de enviar, se está realizando una petición POST al servidor para enviar los datos del formulario.
- PUT: se utiliza para actualizar un recurso. Por ejemplo, cuando se actualiza un perfil de usuario, se está realizando una petición PUT al servidor para actualizar los datos del perfil.
- PATCH: se utiliza para actualizar parcialmente un recurso. Por ejemplo, cuando se actualiza el nombre de un perfil de usuario, se está realizando una petición PATCH al servidor para actualizar el nombre del perfil.
- DELETE: se utiliza para eliminar un recurso. Por ejemplo, cuando se elimina un perfil de usuario, se está realizando una petición DELETE al servidor para eliminar el perfil.
- HEAD: se utiliza para obtener los metadatos de un recurso. Por ejemplo, cuando se accede a una página web, se está realizando una petición HEAD al servidor para obtener los metadatos de la página web.

## Status HTTP
Los status HTTP son los códigos de estado que se utilizan para indicar el resultado de una petición HTTP. Los status HTTP más utilizados son en este curso son:
- 200 OK: indica que la petición se ha realizado correctamente.
- 201 Created: indica que la petición se ha realizado correctamente y que se ha creado un nuevo recurso.
- 204 No Content: indica que la petición se ha realizado correctamente pero que no se ha devuelto ningún contenido.
- 400 Bad Request: indica que la petición no se ha podido procesar porque el servidor no ha entendido el mensaje.
- 401 Unauthorized: indica que la petición no se ha podido procesar porque el cliente no está autenticado.
- 403 Forbidden: indica que la petición no se ha podido procesar porque el cliente no tiene permisos para acceder al recurso.
- 404 Not Found: indica que la petición no se ha podido procesar porque el recurso no existe.
- 500 Internal Server Error: indica que la petición no se ha podido procesar porque el servidor ha encontrado un error interno.

## Content Negotiation
La negociación de contenido es un mecanismo que permite a un cliente indicar qué tipo de contenido es capaz de procesar y qué tipo de contenido prefiere recibir. El servidor puede utilizar esta información para enviar el contenido adecuado. La negociación de contenido se realiza a través de las cabeceras de la petición HTTP. El contenido más usado será JSON, pero también se puede usar XML o HTML.

## CORS
CORS es una especificación que define un mecanismo que permite a los navegadores obtener permiso para acceder a recursos que se encuentran en un origen distinto (dominio, protocolo o puerto) al que pertenece la página web que está realizando la petición. Sus siglas vienen de Cross-Origin Resource Sharing, es decir, Compartir Recursos Entre Orígenes. Por ejemplo, una página web de un dominio puede acceder a recursos de un dominio de una API. De esta manera podemos controlar qué dominios pueden acceder a nuestros recursos. CORS se utiliza para evitar ataques entre otras cosas.
