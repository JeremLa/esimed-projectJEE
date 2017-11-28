package entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mep_client")
public class Client extends Person {
    private Boolean isEnterprise;
    private String mail;
    private String contactName;

    public Client (){

    }

    public Boolean getEnterprise() {
        return isEnterprise;
    }

    public void setEnterprise(Boolean enterprise) {
        isEnterprise = enterprise;
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
}