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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        if (getEnterprise() != null ? !getEnterprise().equals(client.getEnterprise()) : client.getEnterprise() != null)
            return false;
        if (getMail() != null ? !getMail().equals(client.getMail()) : client.getMail() != null) return false;
        return getContactName() != null ? getContactName().equals(client.getContactName()) : client.getContactName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getEnterprise() != null ? getEnterprise().hashCode() : 0);
        result = 31 * result + (getMail() != null ? getMail().hashCode() : 0);
        result = 31 * result + (getContactName() != null ? getContactName().hashCode() : 0);
        return result;
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