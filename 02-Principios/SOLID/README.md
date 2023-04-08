# SOLID
SOLID es el acrónimo que acuñó Michael Feathers, basándose en los principios de la programación orientada a objetos que Robert C. Martin había recopilado en el año 2000 en su paper “Design Principles and Design Patterns”. Ocho años más tarde, el tío Bob siguió compendiando consejos y buenas prácticas de desarrollo y se convirtió en el padre del código limpio con su célebre libro Clean Code.

## Los principios SOLID
Los 5 principios SOLID de diseño de aplicaciones de software son:

S – Single Responsibility Principle (SRP)
O – Open/Closed Principle (OCP)
L – Liskov Substitution Principle (LSP)
I – Interface Segregation Principle (ISP)
D – Dependency Inversion Principle (DIP)
Entre los objetivos de tener en cuenta estos 5 principios a la hora de escribir código encontramos:

Crear un software eficaz: que cumpla con su cometido y que sea robusto y estable.
Escribir un código limpio y flexible ante los cambios: que se pueda modificar fácilmente según necesidad, que sea reutilizable y mantenible.
Permitir escalabilidad: que acepte ser ampliado con nuevas funcionalidades de manera ágil.
En definitiva, desarrollar un software de calidad.

En este sentido la aplicación de los principios SOLID está muy relacionada con la comprensión y el uso de patrones de diseño, que nos permitirán mantener una alta cohesión y, por tanto, un bajo acoplamiento de software.

## ¿Qué son la cohesión y el acoplamiento?
Son dos conceptos muy relevantes a la hora de diseñar y desarrollar software. Veamos en qué consisten.

### Acoplamiento
El acoplamiento se refiere al grado de interdependencia que tienen dos unidades de software entre sí, entendiendo por unidades de software: clases, subtipos, métodos, módulos, funciones, bibliotecas, etc.

Si dos unidades de software son completamente independientes la una de la otra, decimos que están desacopladas.

### Cohesión
La cohesión de software es el grado en que elementos diferentes de un sistema permanecen unidos para alcanzar un mejor resultado que si trabajaran por separado. Se refiere a la forma en que podemos agrupar diversas unidades de software para crear una unidad mayor.

## 1. Principio de Responsabilidad Única (SRP)
"Nunca debería haber más de una razón para cambiar una clase." Una clase debe tener una sola responsabilidad. ¿Pero qué es una responsabilidad? Responsabilidad hace referencia a aquellos actores (fuentes de cambio) que podrían reclamar diferentes modificaciones en un determinado módulo. 

Por lo tanto, un módulo debe ser responsable de uno, y solo un actor.

## 2. Principio de Abierto/Cerrado (OCP)
"Deberías ser capaz de extender el comportamiento de una clase, sin modificarla". En otras palabras: las clases que usas deberían estar abiertas para poder extenderse y cerradas para modificarse.

Nos indica que en los casos que se introduzcan nuevos comportamientos en sistemas existentes, en lugar de modificar los componentes antiguos, se deben crear componentes nuevos. El motivo, es que si están siendo usados en otra parte del sistema, al modificarlos estaremos afectando a esa parte del sistema.

## 3. Principio de Sustitución de Liskov (LSP)
"Los objetivos de una clase deben ser los mismos que los objetivos de sus subclases". En otras palabras: las clases que heredan de otras clases deben tener los mismos objetivos que sus subclases. En otras palabras: Las funciones que utilicen referencias a clases base, deben ser capaces de usar objetos de clases derivadas sin saberlo: "Si S es un subtipo de T, el programa P no debería cambiar su comportamiento si cambiamos los objetos de tipo S por objetos de tipo T". 

Esto significa que los objetos deben poder ser reemplazados por instancias de sus subtipos sin alterar el correcto funcionamiento del sistema o lo que es lo mismo: si en un programa utilizamos cierta clase, deberíamos poder usar cualquiera de sus subclases sin interferir en la funcionalidad del programa.  

### 4. Principio de Segregación de Interfaces (ISP)
"Los clientes no deberían estar obligados a depender de interfaces que no utilicen". Es decir, una clase, no debería depender de métodos o propiedades que no necesitan.
Segregamos las interfaces, en aspectos comunes y específicos. 

Cada clase implementa las interfaces que realmente necesitan. En este sentido, según el Interface Segregation Principle (ISP), es preferible contar con muchas interfaces que definan pocos métodos que tener una interface forzada a implementar muchos métodos a los que no dará uso.

### 5. Principio de Inversión de Dependencias (DIP)
"Depende de abstracciones, no de clases concretas". Los módulos de alto nivel no deben depender de los módulos de bajo nivel, ya que ambos deben depender de abstracciones. Las abstracciones no deben depender de los detalles, porque los detalles deben depender de las abstracciones.






