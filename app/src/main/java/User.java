public class User {

    private int id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;

    public User(int id, String email, String password, String name, String surname, String phoneNumber){
        this.id=id;
        this.email=email;
        this.password=password;
        this.name=name;
        this.surname=surname;
        this.phoneNumber=phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
