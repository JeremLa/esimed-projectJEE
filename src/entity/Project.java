package entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mep_project")
public class Project extends BaseEntity implements Serializable{
    @ManyToOne
    Client client;
}
