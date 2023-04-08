# Principios SOLID, Arquitecturas y Patrones de diseño

![logo](https://rubensa.files.wordpress.com/2021/05/spring-boot-logo.png)

- [Principios SOLID, Arquitecturas y Patrones de diseño](#principios-solid-arquitecturas-y-patrones-de-diseño)
  - [Principios SOLID](#principios-solid)
    - [Single Responsibility Principle (Principio de Responsabilidad Única)](#single-responsibility-principle-principio-de-responsabilidad-única)
    - [Open-Closed Principle (Principio de Abierto-Cerrado)](#open-closed-principle-principio-de-abierto-cerrado)
    - [Liskov Substitution Principle (Principio de Sustitución de Liskov)](#liskov-substitution-principle-principio-de-sustitución-de-liskov)
    - [Interface Segregation Principle (Principio de Segregación de Interfaces)](#interface-segregation-principle-principio-de-segregación-de-interfaces)
    - [Dependency Inversion Principle (Principio de Inversión de Dependencias)](#dependency-inversion-principle-principio-de-inversión-de-dependencias)
  - [Arquitecturas Software](#arquitecturas-software)
  - [Patrones de diseño](#patrones-de-diseño)
  - [DTO y Mapeadores](#dto-y-mapeadores)

 ## Principios SOLID
Principios SOLID son una serie de principios de diseño orientado a objetos que nos ayudan a crear código más mantenible y reutilizable. Estos principios fueron definidos por Robert C. Martin (Uncle Bob) en su libro “Agile Software Development, Principles, Patterns, and Practices” en 2000.

Los principios SOLID son:
- S: Single Responsibility Principle (Principio de Responsabilidad Única)
- O: Open-Closed Principle (Principio de Abierto-Cerrado)
- L: Liskov Substitution Principle (Principio de Sustitución de Liskov)
- I: Interface Segregation Principle (Principio de Segregación de Interfaces)
- D: Dependency Inversion Principle (Principio de Inversión de Dependencias)

### Single Responsibility Principle (Principio de Responsabilidad Única)
Este principio nos dice que una clase debe tener una única responsabilidad, es decir, que debe tener una única razón para cambiar. Esto nos ayuda a crear clases más pequeñas y con una única responsabilidad, lo que facilita su mantenimiento y reutilización. Por ejemplo, si tenemos una clase que se encarga de gestionar los usuarios de nuestra aplicación, esta clase debería tener las siguientes responsabilidades: CRUD de usuarios

### Open-Closed Principle (Principio de Abierto-Cerrado)
Este principio nos dice que las entidades de nuestro código (clases, módulos, funciones, etc.) deben estar abiertas a la extensión pero cerradas a la modificación. Esto nos ayuda a crear código más mantenible y reutilizable. Es decir, si tenemos una clase que implementa una funcionalidad, no deberíamos modificar esa clase para añadir nuevas funcionalidades, sino que deberíamos crear una nueva clase que herede de la clase base y que implemente la nueva funcionalidad.

### Liskov Substitution Principle (Principio de Sustitución de Liskov)
Este principio nos dice que las clases derivadas deben ser sustituibles por sus clases base. Esto nos ayuda a crear código más mantenible y reutilizable. Es decir, si tenemos una clase base que implementa una funcionalidad, las clases derivadas deben poder usar esa funcionalidad sin tener que modificar el código de la clase base.

### Interface Segregation Principle (Principio de Segregación de Interfaces)
Este principio nos dice que las interfaces deben ser lo más pequeñas posibles. Esto nos ayuda a crear código más mantenible y reutilizable. Es decir, si tenemos una interfaz que define una funcionalidad, no deberíamos añadir más funcionalidades a esa interfaz, sino que deberíamos crear una nueva interfaz que herede de la interfaz base y que defina la nueva funcionalidad.

### Dependency Inversion Principle (Principio de Inversión de Dependencias)
Este principio nos dice que las clases de alto nivel no deben depender de las clases de bajo nivel, sino que ambas deben depender de abstracciones. Esto nos ayuda a crear código más mantenible y reutilizable. Es decir, si tenemos una clase que implementa una funcionalidad, no deberíamos depender de la implementación de esa funcionalidad, sino que deberíamos depender de una interfaz que defina la funcionalidad.

## Arquitecturas Software 
Llamamos arquitectura de software a la estructura de un sistema de software, es decir, a la forma en la que se organizan los componentes del sistema. Las arquitecturas de software se pueden clasificar en diferentes tipos, como arquitecturas de capas, arquitecturas basadas en componentes, arquitecturas basadas en microservicios, etc. En este curso vamos a ver las siguientes arquitecturas de software:
- Arquitectura de capas: la arquitectura de capas es una arquitectura de software en la que los componentes del sistema se organizan en capas. Cada capa se encarga de una parte del sistema, y las capas se comunican entre sí mediante interfaces.
- Arquitectura basada en componentes: la arquitectura basada en componentes es una arquitectura de software en la que los componentes del sistema se organizan en componentes. Cada componente se encarga de una parte del sistema, y los componentes se comunican entre sí mediante interfaces.
- Arquitectura MVC: la arquitectura MVC es una arquitectura de software en la que los componentes del sistema se organizan en tres capas: Modelo, Vista y Controlador. Cada capa se encarga de una parte del sistema, y las capas se comunican entre sí mediante interfaces.
- Arquitectura basada en microservicios: la arquitectura basada en microservicios es una arquitectura de software en la que los componentes del sistema se organizan en microservicios. Cada microservicio se encarga de una parte del sistema, y los microservicios se comunican entre sí mediante interfaces.

## Patrones de diseño
Los [patrones de diseño](https://refactoring.guru/es/design-patterns) son soluciones a problemas comunes de diseño de software. Los patrones de diseño se pueden clasificar en diferentes tipos, como patrones de creación, patrones estructurales y patrones de comportamiento. Son numerosos y los veremos a lo largo del curso los que más vamos a usar.

## DTO y Mapeadores
Los data transfer objects (DTO) son objetos que se utilizan para transferir datos entre distintas capas de una aplicación. A su vez nos sirve para transferir datos entre distintas aplicaciones y fusionar datos de distintas fuentes o tipo, ya que nos permiten encapsular los datos y ocultarlos de las capas superiores. De esta forma, si cambiamos la estructura de los datos, no tendremos que modificar las capas superiores, ya que los DTOs encapsulan los datos y las capas superiores no tienen que conocer la estructura de los datos.

Los mapeadores son clases que se encargan de convertir objetos de un tipo a objetos de otro tipo. Por ejemplo, si tenemos una clase Coche y queremos convertirla a un DTO, podemos crear un mapeador que se encargue de convertir un objeto de tipo Coche a un objeto de tipo CocheDTO.

```java
public class Coche {
  private String marca;
  private String modelo;
  private String color;
  private Motor motor;

  public Coche(String marca, String modelo, String color, Motor motor) {
    this.marca = marca;
    this.modelo = modelo;
    this.color = color;
    this.motor = motor;
  }
}

public class Motor {
  private String tipo;
  private int cilindrada;

  public Motor(String tipo, int cilindrada) {
    this.tipo = tipo;
    this.cilindrada = cilindrada;
  }
}

public class CocheDTO {
  private String marca;
  private String modelo;
  private String color;
  private String tipoMotor;
  private int cilindradaMotor;

  public CocheDTO(String marca, String modelo, String color, String tipoMotor, int cilindradaMotor) {
    this.marca = marca;
    this.modelo = modelo;
    this.color = color;
    this.tipoMotor = tipoMotor;
    this.cilindradaMotor = cilindradaMotor;
  }
}

public class CocheMapper {
  public static CocheDTO map(Coche coche) {
    return new CocheDTO(
      coche.getMarca(),
      coche.getModelo(),
      coche.getColor(),
      coche.getMotor().getTipo(),
      coche.getMotor().getCilindrada()
    );
  }
}