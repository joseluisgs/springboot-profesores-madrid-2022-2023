package es.joseluisgs.dam.Creacionales.Builder;

/**
 * Imagina un objeto complejo que requiere una inicialización laboriosa, paso a paso,
 * de muchos campos y objetos anidados.
 * Normalmente, este código de inicialización está sepultado dentro de un monstruoso constructor
 * con una gran cantidad de parámetros. O, peor aún: disperso por todo el código
 * cliente con getter y setters
 */
public class User
{
    //Todos los atrubutos finales son requeridos
    private final String firstName;
    private final String lastName;
    private final int age; // opcional
    private final String phone; // opcional
    private final String address; // opcional

    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    //Todos son getter, pero no setter para asegurar la inmutabilidad en este caso...
    // si quieres puedes ponerlos.
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getAge() {
        return age;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", phone=" + phone + ", address=" + address + '}';
    }

    // Builder nos permite cosntruir las cosas como queramos... En este caso lo obligatorio son los final.
    // de esta manera no tenermos 20 constructores :)
    // en el fondo tiene una interfaz fluida, que es lo que nos permite hacerlo más fácil.
    // Devolviendo el objeto
    public static class UserBuilder
    {
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;
        private String address;

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }
        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }
        // Devolvemos el objeto
        public User build() {
            User user =  new User(this);
            validateUserObject(user);
            return user;
        }
        private void validateUserObject(User user) {
            // Podemos validar aquí el objeto para cumplir reglas de integridad.
            // Por ejemplo, que no sea null, que no tenga nombre vacío, etc.
            // Si no se cumplen las reglas, lanzamos una excepción.
        }
    }
}