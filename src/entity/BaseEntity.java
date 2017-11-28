package entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    protected Integer id;

    protected void setId(Integer id){
        this.id = id;
    };

    public Integer getId(){
        return this.id;
    };
}
