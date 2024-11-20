public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String city;
    private String groupNumber;

    public User(Long id, String firstName, String lastName, Integer age, String email, String phoneNumber, String city, String groupNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.groupNumber = groupNumber;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", age=" + age +
                ", email=" + email +
                ", phoneNumber=" + phoneNumber +
                ", city=" + city +
                ", groupNumber=" + groupNumber;
    }
}
