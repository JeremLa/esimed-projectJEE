package dao;

import entity.BaseEntity;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;

public abstract class SimpleDAO<T extends BaseEntity>{

    @PersistenceContext
    protected EntityManager em;

    @Resource
    protected UserTransaction ut;

    @Transactional
    public void insert (T object){
        em.persist(object);
    }

    @Transactional
    public T update (T object){
        return em.merge(object);
    }

    @Transactional
    public void delete (T object){
        em.remove(em.find(object.getClass(), object.getId()));
    }

    public T get (Class<T> aClass, Integer id){
        return em.find(aClass, id);
    }

    public List<T> getAll (Class<T> tClass){
        return em.createQuery("Select t From "+tClass.getName()+" t" ).getResultList();
    }
}