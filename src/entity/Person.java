package entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person extends BaseEntity {
    private String firstName;
    private String lastName;
    private String adress;
    private String zipcode;
    private String city;
    private String phone;

    public Person() {

    }

    public Person(String firstName, String lastName, String adress, String zipcode, String city, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adress='" + adress + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                "} " + super.toString();
    }
}