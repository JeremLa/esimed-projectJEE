package controller.form;

import dao.BillDAO;
import dao.BillRowDAO;
import dao.ProjectDAO;
import entity.Bill;
import entity.Project;
import entity.User;
import entity.enumerable.BillStatus;
import entity.enumerable.PaymentMethods;
import tools.FacesTools;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class BillFormController  implements Serializable{
    @Inject
    private BillDAO billDAO;
    @Inject
    private BillRowDAO billRowDAO;
    @Inject
    private ProjectDAO projectDAO;
    @Inject
    private User user;

    private Bill bill;
    private List<Project> projects;
    private BillStatus[] billStatuses;
    private PaymentMethods[] paymentMethods;
    private Boolean editMode = false;

    @PostConstruct
    private void init(){
        bill = new Bill();
        projects = projectDAO.getAllByUser(user);
        billStatuses = BillStatus.values();
        paymentMethods = PaymentMethods.values();
        generateBillNumber();
    }

    public void save (){
        billDAO.insert(bill);

        bill = new Bill();
        generateBillNumber();

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "La facture a été créé avec succès.");
    }

    public void update (){
        billDAO.update(bill);

        editMode = false;
        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Les modifications de la facture ont bien été enregistré.");
    }

    public void delete (Bill bill) {
        billDAO.delete(bill);

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "La facture a bien été supprimé.");
    }

    public boolean haveBillRow(Bill bill){
        return billRowDAO.getByBill(bill).size() > 0;
    }

    private void generateBillNumber(){
        Integer number = billDAO.getSequentNumber(user);
        bill.setBillNumber(number);
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        editMode = true;
        this.bill = bill;
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public BillStatus[] getBillStatuses() {
        return billStatuses;
    }

    public void setBillStatuses(BillStatus[] billStatuses) {
        this.billStatuses = billStatuses;
    }

    public PaymentMethods[] getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods[] paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}