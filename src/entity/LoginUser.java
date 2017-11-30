package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "security_user")
public class LoginUser extends BaseEntity implements Serializable{
    @Column(nullable = false, unique = true)
    private String userName;
    private String password;

    @ManyToMany
    @JoinTable(name = "security_user_group",
            joinColumns = @JoinColumn(name = "userName",
                    referencedColumnName = "userName"),
            inverseJoinColumns = @JoinColumn(name = "groupName",
                    referencedColumnName = "groupName"))
    private List<Group> groups = new ArrayList<Group>();

    public LoginUser() {
    }

    public LoginUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group){
        this.groups.add(group);
    }

    public void removeGroup(Group group){
        this.groups.remove(group);
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
