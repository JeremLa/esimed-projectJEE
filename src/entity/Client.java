package entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mep_client")
public class Client extends Person implements Serializable {
    private Boolean enterprise;
    private String mail;
    private String contactName;

    @ManyToOne
    private User user;

    public Client (){
        this.enterprise = false;
    }

    public Boolean isEnterprise() {
        return enterprise;
    }

    public Boolean getEnterprise(){
        return enterprise;
    }

    public void setEnterprise(Boolean enterprise) {
        this.enterprise = enterprise;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Client{" +
                "enterprise=" + enterprise +
                ", mail='" + mail + '\'' +
                ", contactName='" + contactName + '\'' +
                    "} " + super.toString();
    }
}