package controller.form;

import dao.BillDAO;
import entity.Bill;
import entity.User;
import tools.FacesTools;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class BillFormController  implements Serializable{
    @Inject
    private BillDAO billDAO;
    @Inject
    private User user;

    private Bill bill;
    private Boolean editMode = false;

    @PostConstruct
    private void init(){
        bill = new Bill();
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

    private void generateBillNumber(){
        Integer number = billDAO.getSequentNumber(user);

        bill.setBillNumber(number);
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }
}