/**
 * Represents a person for a plane seat.
 */
public class Person {
    private String name;
    private String surname;
    private String email;

    /**
     * Construct a no-argument person with default values.
     */
    public Person() {
        this.name = "";
        this.surname = "";
        this.email = "";
    }

    /**
     * Overrides the default toString in Object.java to display personal details.
     *
     * @return Uses to display and print Person details
     */
    @Override
    public String toString() {
        return "\nPerson Details\n\n" + "Name:     " + getName() + "\n" + "Surname:  " + getSurname() + "\n" + "Email:    " + getEmail() + "\n";
    }

    //getters

    /**
     * Gets the name of a person.
     *
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the surname of a person.
     *
     * @return The surname of a person.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the email of a person.
     *
     * @return The email of a person.
     */
    public String getEmail() {
        return email;
    }

    //setters

    /**
     * Sets the name of a person.
     *
     * @param name The name of a person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the surname of a person.
     *
     * @param surname The surname of a person.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets the email of a person.
     *
     * @param email The email of a person.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
