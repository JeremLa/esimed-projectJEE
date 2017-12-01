package controller.form;

import dao.BillDAO;
import dao.BillRowDAO;
import dao.ProjectDAO;
import entity.Bill;
import entity.BillRow;
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
import java.util.Date;
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

    private Bill bill = new Bill();
    private List<Project> projects;
    private BillStatus[] billStatuses;
    private PaymentMethods[] paymentMethods;
    private Boolean editMode = false;
    private Boolean renderAlert = false;

    @PostConstruct
    private void init(){
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
        renderAlert = false;
        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Les modifications de la facture ont bien été enregistré.");
    }

    public void delete (Bill bill) {
        List<BillRow> billRows = billRowDAO.getByBill(bill);

        for(BillRow billRow : billRows){
            billRowDAO.delete(billRow);
        }

        billDAO.delete(bill);

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "La facture a bien été supprimé.");
    }

    public Boolean latePaid(Bill bill){
        return bill.getPaidLimiteDate().before(new Date()) && bill.getBillStatus() == BillStatus.SENT;
    }

    public Boolean isPaid(){
        System.out.println(bill);
        if(bill.getId() != null){
            Bill billbon = billDAO.get(Bill.class, bill.getId());
            System.out.println("bilbon "+billbon);
            return billbon.getBillStatus() == BillStatus.PAID;
        }

        return bill.getBillStatus() == BillStatus.PAID;
    }

    public void StatusChange(){
        if(bill.getBillStatus() == BillStatus.PAID){
            bill.setPaidDate(new Date());
            renderAlert = true;
        }else{
            renderAlert = false;
        }
    }

    public Boolean getRenderAlert() {
        return renderAlert;
    }

    public void setRenderAlert(Boolean renderAlert) {
        this.renderAlert = renderAlert;
    }

    public Boolean isPaid(Bill bill){

            return bill.getBillStatus() == BillStatus.PAID;

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

        System.out.println(this.bill);
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