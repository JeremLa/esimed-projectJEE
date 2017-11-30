package entity;

import entity.enumerable.BillStatus;
import entity.enumerable.PaymentMethods;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mep_bill")
public class Bill extends BaseEntity implements Serializable{
    private int billNumber;
    private BillStatus billStatus;
    private Date editedDate;
    private Date paidLimiteDate;
    private Date paidDate;
    private PaymentMethods paymentMethods;
    private String note;

    @ManyToOne
    private Project project;

    public Bill() {
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public BillStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(BillStatus billStatus) {
        this.billStatus = billStatus;
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }

    public Date getPaidLimiteDate() {
        return paidLimiteDate;
    }

    public void setPaidLimiteDate(Date paidLimiteDate) {
        this.paidLimiteDate = paidLimiteDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
