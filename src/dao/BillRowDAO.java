package dao;

import entity.Bill;
import entity.BillRow;

import javax.faces.bean.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BillRowDAO extends SimpleDAO<BillRow>{

    @Transactional
    public List<BillRow> getByBill(Bill bill){
        return em.createQuery("SELECT br FROM BillRow br WHERE br.bill = :bill").setParameter("bill", bill).getResultList();
    }
}
