package dao;

import entity.Bill;
import entity.Client;
import entity.Project;
import entity.User;
import entity.enumerable.BillStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class BillDAO extends SimpleDAO<Bill>{

    public List<Bill> getByUser(User user){
        return em.createQuery("SELECT b from Bill b WHERE b.project.client.user = :user ORDER BY b.billNumber DESC").setParameter("user", user).getResultList();
    }

    public List<Bill> getByProject(Project project){
        return em.createQuery("SELECT b from Bill b WHERE b.project = :project ORDER BY b.billNumber DESC").setParameter("project", project).getResultList();
    }

    public List<Bill> getByClient(Client client){
        return em.createQuery("SELECT b from Bill b WHERE b.project.client = :client ORDER BY b.billNumber DESC").setParameter("client", client).getResultList();
    }

    public Integer getSequentNumber(User user){
        Integer max = (Integer) em.createQuery("SELECT MAX(b.billNumber) FROM Bill b WHERE b.project.client.user = :user").setParameter("user", user).getSingleResult();

        return max == 0 ? 1 : max + 1;
    }

    public Boolean existBillNumber(Integer number, Integer billId){
        return em.createQuery("SELECT b FROM Bill b WHERE b.billNumber = :number AND b.id <> :billId")
                .setParameter("number", number)
                .setParameter("billId", billId)
                .getResultList().size() > 0;
    }

    public Integer isHigherLastSent(Integer number){
        List<Bill> resultat = em.createQuery("SELECT b FROM Bill b WHERE (b.billStatus = :send OR b.billStatus = :paid) AND b.billNumber > :number")
                .setParameter("number", number)
                .setParameter("send", BillStatus.SENT)
                .setParameter("paid", BillStatus.PAID)
                .getResultList();
        if(resultat.size() > 0){
            resultat.sort((a,b)->b.getBillNumber()-a.getBillNumber());
            return resultat.get(0).getBillNumber();
        }

        return 0;
    }

    public Double getCurrentYearCA(User user, Integer year){

        Calendar dStart = Calendar.getInstance();
        Calendar dEnd = Calendar.getInstance();

        dStart.set(year, 1, 1);
        dEnd.set(year, 12, 31);

        return (Double) em.createQuery("SELECT SUM (br.amount * br.unitPrice) FROM BillRow br " +
                "WHERE br.bill.project.client.user = :user " +
                "AND br.bill.billStatus = :paid " +
                "AND br.bill.paidDate > :dateStart " +
                "AND br.bill.paidDate < :dateEnd")
                .setParameter("user", user)
                .setParameter("paid", BillStatus.PAID)
                .setParameter("dateStart", dStart.getTime())
                .setParameter("dateEnd", dEnd.getTime())
                .getSingleResult();
    }
}
