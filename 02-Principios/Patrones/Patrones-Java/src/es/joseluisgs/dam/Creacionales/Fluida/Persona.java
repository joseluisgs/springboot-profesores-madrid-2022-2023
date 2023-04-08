package es.joseluisgs.dam.Creacionales.Fluida;

/**
 * La interfaz Fluida nos permite simular el comportamiento de crear objetos de manera fluida.
 * No es exactamente un Builder, pero es una forma de crear objetos de manera fluida nos da agilidad
 * en la creación de objetos si estos dependen de muchos campos.
 */
public class Persona {
    //Todos los atrubutos finales son requeridos
    private final String firstName;
    private final String lastName;
    private int age; // opcional
    private String phone; // opcional
    private String address; // opcional

    //Constructor vacío
    public Persona(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Persona of(String firstName, String lastName) {
        return new Persona(firstName, lastName);
    }

    // Lo importante es que los setter en vez de void devulven el objeto actual, es decir, this.

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Persona age(int age) {
        this.age = age;
        return this;
    }

    public Persona phone(String phone) {
        this.phone = phone;
        return this;
    }

    public Persona address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    // Voy a implementar toda la interfaz fluida con otros metodos


}