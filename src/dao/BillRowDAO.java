package dao;

import entity.Bill;
import entity.BillRow;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class BillRowDAO extends SimpleDAO<BillRow>{
    public List<BillRow> getByBill(Bill bill){
        return em.createQuery("SELECT br FROM BillRow br WHERE br.bill = :bill").setParameter("bill", bill).getResultList();
    }

    public double getBillSum(Bill bill){
        List<BillRow> billRowls = getByBill(bill);

        return billRowls.stream().mapToDouble(br->br.getUnitPrice() * br.getAmount()).sum();
    }
}
