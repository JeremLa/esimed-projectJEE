package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mep_user")
public class User extends Person implements Serializable{
    private Date birthDate;
    private Double CAMax;
    private Double taxes;

    @ManyToOne
    private LoginUser loginUser;

    public User(){
    }

    public User(String firstName, String lastName, String adress, String zipcode, String city, String phone, Date birthDate, Double CAMax, Double taxes){
        super(firstName, lastName, adress, zipcode, city, phone);
        this.birthDate = birthDate;
        this.CAMax = CAMax;
        this.taxes = taxes;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Double getCAMax() {
        return CAMax;
    }

    public void setCAMax(Double CAMax) {
        this.CAMax = CAMax;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "birthDate=" + birthDate +
                ", CAMax=" + CAMax +
                ", taxes=" + taxes +
                '}';
    }
}
