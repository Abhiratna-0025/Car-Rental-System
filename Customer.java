package model;

public abstract class User {
    protected int id;
    protected String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

package model;

public class Customer extends User {

    private String email;
    private String phone;

    public Customer(int id, String name, String email, String phone) {
        super(id, name);
        this.email = email;
        this.phone = phone;
    }

    // getters
}
