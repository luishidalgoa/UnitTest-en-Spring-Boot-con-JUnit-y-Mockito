# Proyecto de ejemplo para aprender Mockito y Testing en Java

Este proyecto está basado en el canal de YouTube [TutorialesVip](https://www.youtube.com/playlist?list=PL2z4kxQgmfSF6-yGrHPgCSrvUw3uFnunZ), pero ha sido actualizado para funcionar con versiones actuales de Spring Boot, ya que el proyecto original presentaba errores de compatibilidad por dependencias obsoletas.

---

## 🧠 Conclusiones aprendidas

### 🧪 Tipos de Tests y Casos de Uso

#### ✅ Tests Unitarios Puros

Los **tests unitarios puros** validan una clase o método de forma **aislada**, sin dependencias externas (como repositorios, servicios, base de datos, etc.).

Para esto se usan herramientas como **Mockito**, que permiten **simular el comportamiento de las dependencias** mediante mocks. Esto hace que las pruebas sean:

- Rápidas
- Reproducibles
- Independientes de infraestructura

**Ejemplo típico:** testear un controlador pasando mocks del repositorio y del servicio que necesita.

#### 🧩 Tests de Integración

Los **tests de integración** validan cómo **colaboran varios componentes reales** entre sí (por ejemplo: controlador → servicio → repositorio → base de datos).

Para esto se requiere que **Spring levante el contexto completo**, y se suele usar una base de datos en memoria (como H2) o incluso mocks en determinadas capas si no queremos tests de extremo a extremo.

**Ventajas:**

- Verifican el comportamiento real de varios beans colaborando
- Detectan errores de wiring, transacciones, validaciones, etc.

---

## 📋 Checklist aprendido en este proyecto

- Uso de **JUnit 5** junto con **Mockito** para testear lógica de negocio
- Uso de **`@BeforeEach`** para inicializar datos antes de cada prueba
- Uso de **Mockito.mock(...)** y de anotaciones `@Mock` / `@InjectMocks`
- Clarificación de los tipos de test (unitarios vs integración)

### Sobre `@BeforeEach` y `@AfterEach`

- **`@BeforeEach`**: ejecuta código antes de cada test individual. Útil para inicializar mocks, controladores, servicios, etc.
- **`@AfterEach`**: ejecuta código después de cada test. Útil para liberar recursos o limpiar el estado.

---

## 📌 Anotaciones útiles en Testing

### 🧪 Para Tests Unitarios (Mockito puro)

#### `@Mock`

Crea un mock de una clase o interfaz. No ejecuta lógica real.

````java
@Mock
private CountryRepository countryRepository;
````

#### `@InjectMocks`

Crea una instancia real de la clase a testear e **inyecta automáticamente los mocks** en su constructor o atributos.

````java
@InjectMocks
private IndependencyController controller;
````

#### `@ExtendWith(MockitoExtension.class)`
habilita el uso de anotaciones de Mockito (`@Mock`, `@InjectMocks`, etc.) con JUnit 5 sin necesidad de inicializarlos manualmente con `MockitoAnnotations.openMocks(this)`


**Caso de uso típico:**
````java
@ExtendWith(MockitoExtension.class)
class MyServiceTest {

    @Mock
    private MyRepository myRepository;

    @InjectMocks
    private MyService myService;

    @Test
    void testSomething() {
        when(myRepository.find(...)).thenReturn(...);
        ...
    }
}
````

#### `MockitoAnnotations.openMocks(this)`

Necesario si usas anotaciones `@Mock` y `@InjectMocks` sin `@ExtendWith(MockitoExtension.class)`, para inicializar los mocks.

````java
@BeforeEach
void setUp() {
MockitoAnnotations.openMocks(this);
}
````

#### 🧪 Caso de uso típico

````java
@ExtendWith(MockitoExtension.class)
class IndependencyControllerTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private DiferenciaEntreFechas diferenciaEntreFechas;

    @InjectMocks
    private IndependencyController controller;

    @Test
    void getDetails() {
        when(countryRepository.findCountryByIsoCode("DO")).thenReturn(new Country(...));
        ResponseEntity<CountryResponse> response = controller.getCountryDetails("DO");
        assertEquals("Republica Dominicana", response.getBody().getCountryName());
    }
}
````

---

### 🧩 Para Tests de Integración (Spring Boot)

#### `@SpringBootTest`

Arranca el contexto completo de Spring y permite inyectar beans reales.

#### `@Autowired`

Inyecta beans reales del contexto de Spring, como si estuvieras en una clase del propio proyecto.

#### `@MockBean`

Reemplaza un bean real del contexto por un mock. Ideal si quieres que todo Spring funcione pero **controlar una dependencia concreta**.

#### `@DataJpaTest`
Está enfocado a tests en JPA/Hibernate, donde solo queremos comprobar que la creación de las tablas y relaciones son correctas
Levanta un contexto minimo con solo las capas necesarias para probar JPA, sin levantar todo el contexto de Spring Boot, lo cual lo hace más rápido de ejecutar

> 👁️ Nota: Esta anotación sustituye la base de datos real por una en memoria (como H2) para pruebas rápidas. Si deseas usar la anotación complementaria `@AutoConfigureTestDatabase` puedes configurarla para usar una base de datos real o una en memoria.
#### `@AutoConfigureTestDatabase`
Permite configurar el tipo de base de datos que se usará en los tests, ya sea una base de datos en memoria (como H2) o una base de datos real.

```java
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Usa la bbdd real configurada.
class example{}
````

| Valor  | Qué hace                                                                                                                         |
| ------ | -------------------------------------------------------------------------------------------------------------------------------- |
| `ANY`  | **(Por defecto)** Reemplaza cualquier fuente de datos (base real o en memoria) por una base de datos en memoria durante el test. |
| `AUTO` | Reemplaza solo si hay una base de datos real configurada, por una base en memoria. Similar a `ANY`, pero con lógica más fina.    |
| `NONE` | **No reemplaza nada.** Usa la base de datos configurada en el proyecto (real o la que tengas en `application.properties`).       |


---


### 🧩 Casos de uso reales

#### `@Autowired` con `@SpringBootTest` (todo real)

````java
@SpringBootTest
class IndependencyIntegrationTest {

    @Autowired
    private IndependencyController controller;

    @Autowired
    private CountryRepository repository; // bean real

    @Test
    void shouldFindCountryInRealDB() {
        repository.save(new Country(...));
        var response = controller.getCountryDetails("DO");
        assertNotNull(response.getBody());
    }
}
````

#### `@MockBean` para aislar una capa en integración

````java
@SpringBootTest
class IndependencyControllerPartialMockTest {

    @Autowired
    private IndependencyController controller;

    @MockBean
    private CountryRepository countryRepository;

    @Test
    void shouldUseMockedRepositoryInContext() {
        when(countryRepository.findCountryByIsoCode("DO")).thenReturn(new Country(...));
        var response = controller.getCountryDetails("DO");
        assertEquals("Republica Dominicana", response.getBody().getCountryName());
    }
}
````

#### `@DataJpaTest` para pruebas de JPA

````java
@DataJpaTest
class CountryRepositoryTest {
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void shouldSaveAndFindCountry() {
        Country country = new Country("DO", "Republica Dominicana");
        countryRepository.save(country);
        Optional<Country> found = countryRepository.findById("DO");
        assertTrue(found.isPresent());
        assertEquals("Republica Dominicana", found.get().getName());
    }
}
````
> También puede ser útil para testear modelos de dominio
---

## 🔍 Diferencias clave entre `@Mock`, `@MockBean` y `@Autowired`

| Anotación     | Contexto                  | Crea Bean Spring | Reemplaza Bean | Caso de uso principal |
|---------------|---------------------------|------------------|----------------|------------------------|
| `@Mock`       | Unit Test (sin Spring)     | ❌               | ❌             | Simular dependencia sin contexto |
| `@MockBean`   | Integration Test (con Spring) | ✅               | ✅             | Mockear bean real dentro de Spring |
| `@Autowired`  | Solo con Spring Context     | ✅               | ❌             | Inyectar dependencia real |

---

## ✅ Recomendación final

- Usa **Mockito y tests unitarios** para lógica de negocio aislada (rápidos y limpios).
- Usa **SpringBootTest** cuando quieras probar integración real.
- Usa **`@MockBean`** cuando quieres tests de integración **con alguna dependencia simulada**.
- **No mezcles innecesariamente tipos de test**, eso solo complica el mantenimiento.
