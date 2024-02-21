"# Reto8-MyPersonalBank-API" 

EQUIPO 2
• Alexandru Maftei
• Jeroni Paul
• Ernesto Munoz
• Juan José Presa Domínguez

El cliente ha quedado sorprendido con el resultado y ¡en tan poco tiempo!. Pero se ha dado cuenta que necesitamos centrarnos en el objetivo original del proyecto, crear una API.
Por ello nuestro siguiente paso es implementar la API para la aplicación.
Los requisitos e historias para esta etapa son los siguientes:

Tú y tu equipo deberéis:
- Definir la API según requisitos del cliente.
- Refactorizar el proyecto para que sea una aplicación REST.
- Añadir las dependencias necesarias.
- Implementar la capa de vista.

PAUTAS RETO 8: MyPersonalBank-API (Spring-Boot)
- Trabajar historia a historia
*Se pueden reordenar las historias
- Capa de vista:
1. Definir la API recurso a recurso (rutas, verbos, status codes)
	a. Sólo representación JSON
2. Inhabilitar/vaciar los test de controler
3. Implementar usando los controllers para los happy-path.
	a. Si es necesario, añadir clases de servicio
	b. Comprobar con RESTer
	c. Genera recursos puros (@JsonIgnore). Evitar relaciones.
4. Añadir gestión de excepciones y validación.
5. Añadir dependencia swagger y documentar.
6. Añadir HTTPS.

RÚBRICAS DE CALIFICACIÓN RETO 8
1. API Restful
2. Spring Boot
3. Implementación REST
4. Gestión de excepciones
5. Validación REST (+ Negociado)
6. HTTPS
7. Documentación API (Swagger)
8. Completitud historias
