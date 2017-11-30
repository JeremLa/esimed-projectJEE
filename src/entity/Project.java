package entity;

import entity.enumerable.ProjectStatus;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "mep_project")
public class Project extends BaseEntity implements Serializable{
    private ProjectStatus status;
    private String name;

    @ManyToOne
    private Client client;

    public Project(){

    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Project{" +
                "status=" + status +
                ", name='" + name + '\'' +
                ", client=" + client +
                ", id=" + id +
                "} " + super.toString();
    }
}
