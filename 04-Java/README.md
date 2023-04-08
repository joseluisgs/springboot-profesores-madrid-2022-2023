# Elementos de avanzados de Java

![logo](https://rubensa.files.wordpress.com/2021/05/spring-boot-logo.png)

- [Elementos de avanzados de Java](#elementos-de-avanzados-de-java)
  - [Herencia](#herencia)
  - [Interfaces](#interfaces)
  - [Composición e inyección de dependencias](#composición-e-inyección-de-dependencias)
  - [Optional](#optional)
  - [Streams](#streams)
    - [Filter](#filter)
    - [Map](#map)
    - [FlatMap](#flatmap)
    - [GroupBy](#groupby)
    - [Reduce](#reduce)
    - [Count](#count)
    - [Skip y Limit](#skip-y-limit)
    - [Collectors](#collectors)
    - [Terminal Operations](#terminal-operations)
    - [Lazy Evaluation](#lazy-evaluation)
    - [Parallel Streams](#parallel-streams)
  - [Comparable y Comparator](#comparable-y-comparator)
  - [Lombok](#lombok)

## Herencia
La herencia es una de las características más importantes de la programación orientada a objetos. La herencia permite que una clase herede los atributos y métodos de otra clase. La clase que hereda se denomina subclase o clase hija y la clase de la que hereda se denomina superclase o clase padre. En Java, la herencia se realiza mediante la palabra clave extends y es una relación de tipo "es un" y simple, es decir, una clase solo puede heredar de un "padre". Siempre hay herencia implícita de la clase Object, que es la clase base de todas las clases en Java.

Cuando queremos cambiar el comportamiento de un método de la clase padre usaremos la palabra clave @Override. Esto es muy importante ya que si no lo hacemos, estaremos creando un nuevo método en la clase hija y no sobreescribiendo el de la clase padre.

La herencia tiene un problema, y es su fuerte acoplamiento. Si queremos cambiar el comportamiento de un método de la clase padre, tendremos que cambiarlo en todas las clases hijas. Para evitar esto, podemos usar la composición. Es útil solo para la herencia del estado, no del comportamiento, para el comportamiento usaremos las interfaces.

```java
public class Persona {
    private String nombre;
    private String apellidos;
    private String dni;
    private String fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;

    public Persona(String nombre, String apellidos, String dni, String fechaNacimiento, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

```java
public class Alumno extends Persona {
    private String matricula;
    private String curso;
    private String grupo;

    public Alumno(String nombre, String apellidos, String dni, String fechaNacimiento, String direccion, String telefono, String email, String matricula, String curso, String grupo) {
        super(nombre, apellidos, dni, fechaNacimiento, direccion, telefono, email);
        this.matricula = matricula;
        this.curso = curso;
        this.grupo = grupo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "matricula='" + matricula + '\'' +
                ", curso='" + curso + '\'' +
                ", grupo='" + grupo + '\'' +
                "} " + super.toString();
    }
}
```

## Interfaces
Las interfaces son un tipo de clase abstracta que solo contiene métodos abstractos y constantes. Las interfaces son muy útiles para definir comportamientos. Una clase puede implementar varias interfaces, pero solo puede heredar de una clase. Las interfaces son muy útiles para definir comportamientos, además podemos crear jerarquías de interfaces.

```java
public interface IReproducir {
    void reproducir();
}
```

```java
public interface IGuardar {
    void guardar();
}
```

```java
public interface IGuardarReproducir extends IGuardar, IReproducir {
}
```

```java
public class Video implements IGuardarReproducir {
    private String titulo;
    private String formato;
    private String duracion;

    public Video(String titulo, String formato, String duracion) {
        this.titulo = titulo;
        this.formato = formato;
        this.duracion = duracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Video{" +
                "titulo='" + titulo + '\'' +
                ", formato='" + formato + '\'' +
                ", duracion='" + duracion + '\'' +
                '}';
    }

    @Override
    public void reproducir() {
        System.out.println("Reproduciendo video");
    }

    @Override
    public void guardar() {
        System.out.println("Guardando video");
    }
}
```

## Composición e inyección de dependencias
A la hora de crear una clase, debemos pensar en que la clase debe ser lo más independiente posible. Esto es muy importante ya que si una clase depende de otra, si queremos cambiar la clase que depende, tendremos que cambiar la clase que depende de ella. Para evitar esto, podemos usar la composición. La composición es una forma de reutilizar código, es decir, podemos crear una clase que contenga el código que queremos reutilizar y luego crear una clase que contenga la clase que hemos creado. Posteriormente inyectaremos la dependencia en la clase que queremos reutilizar el código.

```java
public class Coche {
    private String marca;
    private String modelo;
    private Motor motor; // dependencia de la clase Coche

    // inyección de dependencias por constructor
    public Coche(String marca, String modelo, Motor motor) {
        this.marca = marca;
        this.modelo = modelo;
        this.motor = motor;
    }

    // getter y setter

}
```

```java
public class Motor {
    private String tipo;
    private int cilindrada;

    // getter y setter

}
```

```java
public class Main {
    public static void main(String[] args) {
        Motor motor = new Motor("Diesel", 2000);
        Coche coche = new Coche("Seat", "Ibiza", motor);
    }
}
```

## Optional
El tipo [Optional](https://www.baeldung.com/java-optional) es un contenedor que puede contener un valor o no. Es muy útil cuando queremos devolver un valor que puede ser nulo. De esta manera podemos evitar los NullPointerException.

```java
public class Main {
    public static void main(String[] args) {
        Optional<String> optional = Optional.of("Hola");
        System.out.println(optional.isPresent());
        System.out.println(optional.get());
        System.out.println(optional.orElse("Adiós"));
        optional.ifPresent(System.out::println);
    }
}
```
## Streams
Los [streams](https://www.baeldung.com/java-streams) son una forma de procesar colecciones en Java. Un stream es una secuencia de elementos que soporta operaciones siguiendo la filosofía de programación funcional. Los streams son muy útiles para procesar colecciones de datos, ya que nos permite realizar operaciones de forma declarativa y usando un prcoesamiento Lazy vertical.

### Filter
El método filter nos permite filtrar los elementos de una colección.

```java
public class Main {
    public static void main(String[] args) {
        List<String> lista = Arrays.asList("Hola", "Adiós", "Buenos días", "Buenas tardes", "Buenas noches");
        lista.stream()
                .filter(s -> s.length() > 5)
                .forEach(System.out::println); // Buenos días, Buenas tardes, Buenas noches
    }
}
```

### Map
El método map nos permite transformar los elementos de una colección.

```java
public class Main {
    public static void main(String[] args) {
        List<String> lista = Arrays.asList("Hola", "Adiós", "Buenos días", "Buenas tardes", "Buenas noches");
        lista.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println); // HOLA, ADIÓS, BUENOS DÍAS, BUENAS TARDES, BUENAS NOCHES
    }
}
```

### FlatMap
El método flatMap nos permite transformar los elementos de una colección en una secuencia de elementos.

```java
public class Main {
    public static void main(String[] args) {
        List<String> lista = Arrays.asList("Hola", "Adiós", "Buenos días", "Buenas tardes", "Buenas noches");
        lista.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .forEach(System.out::println); // Hola, Adiós, Buenos, días, Buenas, tardes, Buenas, noches
    }
}
```

### GroupBy
El método groupBy nos permite agrupar los elementos de una colección en un mapa.

```java
public class Main {
    public static void main(String[] args) {
        List<String> lista = Arrays.asList("Hola", "Adiós", "Buenos días", "Buenas tardes", "Buenas noches");
        Map<Integer, List<String>> map = lista.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(map); // {4=[Hola, Adiós], 11=[Buenas tardes, Buenas noches], 12=[Buenos días]}
    }
}
```

### Reduce
El método reduce nos permite reducir los elementos de una colección a un único valor.

```java
public class Main {
    public static void main(String[] args) {
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5);
        int suma = lista.stream()
                .reduce(0, Integer::sum);
        System.out.println(suma); // 15
    }
}
```

### Count
El método count nos permite contar los elementos de una colección.

```java
public class Main {
    public static void main(String[] args) {
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5);
        long count = lista.stream()
                .count();
        System.out.println(count); // 5
    }
}
```
### Skip y Limit
Los métodos skip y limit nos permiten saltar o limitar el número de elementos de una colección.

```java
public class Main {
    public static void main(String[] args) {
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5);
        lista.stream()
                .skip(2)
                .limit(2)
                .forEach(System.out::println); // 3, 4
    }
}
```

### Collectors
Para transformar un stream en una colección podemos usar el método collect. El método collect recibe un Collector, que es una interfaz que nos permite definir como queremos transformar el stream en una colección. Existen varios métodos estáticos en la clase Collectors que nos permiten crear Collectors. También existe la versión corta de estos métodos, que son métodos de instancia de la clase Collectors, como .toList(), .toSet(), .toMap()...
- toList: transforma el stream en una lista.
- toSet: transforma el stream en un conjunto.
- toMap: transforma el stream en un mapa.

```java
public class Main {
    public static void main(String[] args) {
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> lista2 = lista.stream()
                .collect(Collectors.toList());
        Set<Integer> set = lista.stream()
                .collect(Collectors.toSet());
        Map<Integer, Integer> map = lista.stream()
                .collect(Collectors.toMap(i -> i, i -> i * 2));
        System.out.println(lista2); // [1, 2, 3, 4, 5]
        System.out.println(set); // [1, 2, 3, 4, 5]
        System.out.println(map); // {1=2, 2=4, 3=6, 4=8, 5=10}
    }
}
```

### Terminal Operations
Las operaciones terminales son las que ejecutan el procesamiento del stream. Las operaciones terminales son las que devuelven un valor, como forEach, count, reduce, collect, etc. Si no se ejecuta una operación terminal, el procesamiento del stream no se ejecuta.

```java
public class Main {
    public static void main(String[] args) {
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5);
        lista.stream()
                .filter(i -> i % 2 == 0)
                .map(i -> i * 2)
                .forEach(System.out::println); // 4, 8, 12
    }
}
```

### Lazy Evaluation
El procesamiento de un stream se realiza de forma perezosa, es decir, se realiza cuando se ejecuta una operación terminal.

```java
public class Main {
    public static void main(String[] args) {
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = lista.stream()
                .filter(i -> {
                    System.out.println("Filtrando " + i);
                    return i % 2 == 0;
                })
                .map(i -> {
                    System.out.println("Mapeando " + i);
                    return i * 2;
                });
        System.out.println("Stream creado");
        stream.forEach(System.out::println); // Stream creado, Filtrando 1, Filtrando 2, Mapeando 2, Filtrando 3, Filtrando 4, Mapeando 4, Filtrando 5, Stream terminado
    }
}
```

### Parallel Streams
Se pueden crear streams paralelos, que se ejecutan en varios hilos. Para crear un stream paralelo, se usa el método parallelStream() en lugar del método stream(). Es útil cuando se tienen colecciones grandes y se quiere aprovechar la potencia de los procesadores modernos.

```java
public class Main {
    public static void main(String[] args) {
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5);
        lista.parallelStream()
                .filter(i -> i % 2 == 0)
                .map(i -> i * 2)
                .forEach(System.out::println); // 4, 8, 12
    }
}
```

## Comparable y Comparator
Se pueden ordenar colecciones de objetos que implementen la interfaz Comparable. La interfaz Comparable tiene un método compareTo que recibe un objeto de la misma clase y devuelve un entero. Si el entero es negativo, el objeto es menor que el objeto recibido. Si el entero es positivo, el objeto es mayor que el objeto recibido. Si el entero es 0, el objeto es igual que el objeto recibido.

```java
public class Persona implements Comparable<Persona> {
    private String nombre;
    private int edad;
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    public String getNombre() {
        return nombre;
    }
    public int getEdad() {
        return edad;
    }
    @Override
    public int compareTo(Persona o) {
        return this.edad - o.edad;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        List<Persona> lista = Arrays.asList(
                new Persona("Juan", 20),
                new Persona("Ana", 30),
                new Persona("Luis", 10)
        );
        lista.sort(Comparator.naturalOrder());
        lista.forEach(p -> System.out.println(p.getNombre())); // Luis, Juan, Ana
    }
}
```

Con la interfaz Comparator podemos ordenar colecciones de objetos de una clase que no implemente la interfaz Comparable. La interfaz Comparator tiene un método compare que recibe dos objetos de la misma clase y devuelve un entero. Si el entero es negativo, el primer objeto es menor que el segundo objeto. Si el entero es positivo, el primer objeto es mayor que el segundo objeto. Si el entero es 0, el primer objeto es igual que el segundo objeto.

```java
public class Persona {
    private String nombre;
    private int edad;
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    public String getNombre() {
        return nombre;
    }
    public int getEdad() {
        return edad;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        List<Persona> lista = Arrays.asList(
                new Persona("Juan", 20),
                new Persona("Ana", 30),
                new Persona("Luis", 10)
        );
        lista.sort(Comparator.comparing(Persona::getEdad));
        lista.forEach(p -> System.out.println(p.getNombre())); // Luis, Juan, Ana
    }
}
```

## Lombok
[Lombok](https://www.baeldung.com/intro-to-project-lombok) es una librería que nos permite generar código automáticamente. Para usar Lombok, hay que añadir la dependencia en el pom.xml.

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
    <scope>provided</scope>
</dependency>
```

Lombok nos ofrece una serie de anotaciones que nos permiten generar código automáticamente y nos ayudará a reducir el código que tenemos que escribir.



