"# Reto8-MyPersonalBank-API" 

EQUIPO 2
• Alexandru Maftei
• Jeroni Paul
• Ernesto Munoz
• Juan José Presa Domínguez

¡Súper! Tenemos la magia de Spring para JPA incorporada en nuestra aplicación.
Ahora necesitamos ir un paso más allá y usar la magia de Spring-Data para facilitar aún más la implementación de la capa de persistencia..
Los requisitos e historias para esta etapa son los siguientes:

Tú y tu equipo deberéis completar las historias no implementadas
- Usando Spring-Data para implementar los repositorios.
- Definir las reglas de las transacciones que apliquen en el proyecto.
- Repositorio GitHub: Juan-JosePresaDominguez/Reto7-Spring-Data (github.com)

PAUTAS RETO 8: MyPersonalBank-API
 - Trabajar historia a historia
Persistencia:
1. Genera el repositorio basado en JpaRepository
2. Copia y actualiza el test de persistencia.
3. Paso los tests

Controller:
1. Actualiza el controller para usar el nuevo repo.
2. Añade @Transactional y la estrategia de propagación en el controller.
3. Añade @Transactional y la estrategia de propagación en el repo o en el método.
