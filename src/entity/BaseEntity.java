package entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
