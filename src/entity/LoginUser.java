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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginUser)) return false;

        LoginUser loginUser = (LoginUser) o;

        if (getUserName() != null ? !getUserName().equals(loginUser.getUserName()) : loginUser.getUserName() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(loginUser.getPassword()) : loginUser.getPassword() != null)
            return false;
        return getGroups() != null ? getGroups().equals(loginUser.getGroups()) : loginUser.getGroups() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getGroups() != null ? getGroups().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
