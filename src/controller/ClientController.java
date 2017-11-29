package controller;

import dao.ClientDao;
import entity.Client;
import tools.FacesTools;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class ClientController implements Serializable{
    @Inject
    private ClientDao clientDao;

    private Client client = new Client();
    private List<Client> clients = new ArrayList<Client>();

    private Boolean enterprise = false;

    public Boolean getEnterprise() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+this.enterprise);

        return enterprise;
    }

    public void addMessage() {
        String summary = enterprise ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void setEnterprise(Boolean enterprise) {
        this.enterprise = enterprise;

        System.out.println("//////////////////////////////////////////// "+this.enterprise);
    }

    public void createUserLink(){
        FacesTools.redirect("/client/createClient.xhtml");
    }

    public void initList(){
        clients = clientDao.getAll(Client.class);
    }

    public void insertClient(){
        System.out.println(client);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
