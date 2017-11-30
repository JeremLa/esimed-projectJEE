package controller;

import dao.ClientDAO;
import dao.ProjectDAO;
import dao.UserDAO;
import entity.Client;
import entity.Project;
import entity.User;
import org.primefaces.context.RequestContext;
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
public class ClientController implements Serializable{
    @Inject
    private ClientDAO clientDAO;
    @Inject
    private UserDAO userDAO;
    @Inject
    private ProjectDAO projectDAO;
    @Inject
    private User user;

    private Client client;
    private List<Client> clients;
    private Boolean fromList;
    private String search;
    private Boolean editMode;
    private Integer argId;

    @PostConstruct
    public void init(){
        this.client = new Client();
        this.fromList = false;
        initList();
    }

    public void initList(){
        clients = clientDAO.getAllByUser(userDAO.findByUserName(FacesTools.currentUserName()));
    }

    public void insertClient(){
        client.setUser(user);
        clientDAO.insert(client);

        initList();

        if(fromList){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('createClient').hide();");
            context.update("listClients");
        }
    }

    public void removeClient(Client client){

        System.out.println("//////////////////////////////////// "+ client.getId() + "////////////////////////////////////");

        clientDAO.delete(client);
        clients.remove(client);
    }

    public void updateClient(){
        clientDAO.update(client);

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Les modifications ont été enregistré.");
    }

    public void displayClient(Client client){
        FacesTools.redirect("/client/displayClient.xhtml?id="+client.getId());
    }

    public void initAskedClient(Integer id){
        if(id != null){
            argId = id;
        }

        if(argId != null){
            client = clientDAO.get(Client.class, argId);

            if(client == null){
                FacesTools.redirect("/client/listClient.xhtml");
                FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "L'utilisateur demandé n'existe pas.");

                RequestContext.getCurrentInstance().update("growl");
            }
        }
    }

    public List<Project> getClientProjects(){
        return projectDAO.getAllByClient(client);
    }

    public void searchAction(){
        if("".equals(search) || search == null){
            initList();
        }else{
            clients = clientDAO.searchClient(search);
        }
    }

    public void setEditMode(Boolean bool){
        this.editMode = bool;
    }

    public Boolean getEditMode(){
        return this.editMode;
    }

    public void fromList(){
        this.fromList = true;
    }

    public Client getClient() {
        return client;
    }

    public Boolean haveProject(Client client){
        return projectDAO.clientHasProject(client);
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getArgId() {
        return argId;
    }

    public void setArgId(Integer argId) {
        this.argId = argId;
    }
}
