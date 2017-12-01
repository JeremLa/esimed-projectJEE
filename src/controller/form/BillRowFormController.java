package controller.form;

import dao.BillRowDAO;
import entity.Bill;
import entity.BillRow;
import org.primefaces.event.RowEditEvent;
import tools.FacesTools;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class BillRowFormController implements Serializable{
    @Inject
    private BillRowDAO billRowDAO;
    private List<BillRow> billRows;
    private Bill bill;

    public void onRowEdit(RowEditEvent event) {

        BillRow billRow = (BillRow) event.getObject();

        if(billRow.getId() != null){
            billRowDAO.update(billRow);
        }else{
            billRow.setBill(bill);
            billRowDAO.insert(billRow);
        }

        addNewLine();

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO,"Ligne édité");
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edition annulé", (event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void populateListByBill(Bill bill){
        this.bill = bill;
        billRows = billRowDAO.getByBill(bill);
        billRows.add(new BillRow());

        addNewLine();
    }

    private void addNewLine(){
        if(billRows.stream().filter(br->br.getId() == null).count() == 0){
            billRows.add(new BillRow());
        }
    }

    public List<BillRow> getBillRows() {
        return billRows;
    }

    public void setBillRows(List<BillRow> billRows) {
        this.billRows = billRows;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
