package controller.list;

import dao.BillDAO;
import dao.BillRowDAO;
import entity.*;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class BillListController implements Serializable{
    @Inject
    private BillDAO billDAO;
    @Inject
    private BillRowDAO billRowDAO;
    @Inject
    private User user;
    private Client client;
    private Project project;
    private List<Bill> bills;
    private Boolean fromProject;
    private Boolean fromClient;

    @PostConstruct
    public void init(){
        fromProject = false;
        fromClient = false;
    }

    public Double getBillAmount(Bill bill){
        return billRowDAO.getBillSum(bill);
    }

    public void getAll(){
        bills = billDAO.getByUser(user);
    }

    public void getByClient(Client client){
        bills = billDAO.getByClient(client);
    }

    public void getByProject(Project project){
        bills = billDAO.getByProject(project);
        fromProject = true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Boolean getFromProject() {
        return fromProject;
    }

    public void setFromProject(Boolean fromProject) {
        this.fromProject = fromProject;
    }

    public Boolean getFromClient() {
        return fromClient;
    }

    public void setFromClient(Boolean fromClient) {
        this.fromClient = fromClient;
    }
}
