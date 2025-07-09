## Proyecto de ejemplo para aprender MOCKITO Y TESTING en Java
Este proyecto es heredado del canal de youtube [TutorialesVip](https://www.youtube.com/playlist?list=PL2z4kxQgmfSF6-yGrHPgCSrvUw3uFnunZ), pero con la diferencia de que
se ha adaptado a una version de SpringBoot compatible (debido a que el proyecto original esta obsoleto y fallaban dependencias).

### Conclusiones aprendidas
- Se ha aprendido hacer pruebas unitarias con JUnit 5 y Mockito.
- Se ha usado BeforeEach
> BeforeEach es una anotación de JUnit que se utiliza para indicar que un método debe ejecutarse antes de cada prueba 
> en una clase de prueba. Es útil para configurar el entorno de prueba, como inicializar objetos o establecer condiciones previas.

> AfterEach es una anotación de JUnit que se utiliza para indicar que un método debe ejecutarse después de cada prueba
> en una clase de prueba. Es útil para limpiar recursos

- En el BeforeEach se ha instanciado el controllador correspondiente.