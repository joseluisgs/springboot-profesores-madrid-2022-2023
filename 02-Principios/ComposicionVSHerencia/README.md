# Composición VS Herencia

Tanto la herencia como la composición son dos técnicas muy comunes aplicadas a la reutilización de código. 

## Herencia
La herencia permite crear clases que heredan de otras clases. Es decir, podemos reutilizar código de una clase en otra clase, de esta manera heredamos atributos y métodos de una clase padre hacia una clase hija.

## Composición
La composición se basa en ensamblar objetos diferentes para obtener una funcionalidad más compleja. Es decir, estamos delegando las tareas que nos mandan a hacer a aquella pieza de código que sabe hacerlas. El código que ejecuta esa tarea concreta está sólo en esa pieza y todos delegan el ella para ejecutar dicha tarea. Por lo tanto estamos reutilizando código de nuevo.

## Ser VS Tener
La connotación de la relación “es un” es más fuerte de lo que creemos, porque ha de ser de por vida, ¿qué quiere decir esto? lo vemos mejor con un ejemplo:

A ti te encanta correr, y podríamos decir que “eres un runner”, pero claro, es posible que cuando tengas 10 nietos y 80 años prefieras dedicar tu tiempo a la fotografía por ejemplo y dejes de ser un runner. Pues ahí tienes una falsa relación “es un”.

Hubiera sido un error plantear la clase que representa tu persona como herencia de runner porque tú no eres un runner de por vida (lo siento por mucho que queramos, y eso que a mi también me gusta correr). Simplemente en ese momento de tu vida tenías un Rol. Y te pido que de nuevo recuerdes, esta vez, el verbo “Tener”.

¿Por qué tanto énfasis en el significado de “ser o no ser” en la herencia? Porque muchas veces queremos acoplar algo de por vida, cuando quizás esa naturaleza sea algo temporal, o simplemente es algo que tiene en ese momento pudiendo cambiar.

Por otro lado la composición nos permite mantener cada clase encapsulada y centrada en su tarea (principio de responsabilidad única).

Conclusión: Pregúntate siempre si la clase que hereda es realmente un hijo, o simplemente tiene elementos del padre.