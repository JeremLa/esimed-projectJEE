package controller;

import dao.ClientDao;
import dao.ProjectDao;
import dao.UserDAO;
import entity.Client;
import entity.User;
import org.primefaces.context.RequestContext;
import tools.FacesTools;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class ClientController implements Serializable{
    @Inject
    private ClientDao clientDao;
    @Inject
    private UserDAO userDAO;
    @Inject
    private ProjectDao projectDao;

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
        clients = clientDao.getAllByUser(userDAO.findByUserName(FacesTools.currentUserName()));
    }

    public void insertClient(){

        User connectedUser = userDAO.findByUserName(FacesTools.currentUserName());
        client.setUser(connectedUser);
        clientDao.insert(client);

        initList();

        if(fromList){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('createClient').hide();");
            context.update("listClients");
        }
    }

    public void removeClient(Client client){

        System.out.println("//////////////////////////////////// "+ client.getId() + "////////////////////////////////////");

        clientDao.delete(client);
        clients.remove(client);
    }

    public void updateClient(){
        clientDao.update(client);

        FacesTools.addFlashMessage(FacesMessage.SEVERITY_INFO, "Les modifications ont été enregistré.");
    }

    public void displayClient(Client client){
        FacesTools.redirect("/client/display.xhtml?id="+client.getId());
    }

    public void initAskedClient(Integer id){
        if(id != null){
            argId = id;
        }

        if(argId != null){
            client = clientDao.get(Client.class, argId);

            if(client == null){
                FacesTools.redirect("/client/listClient.xhtml");
                FacesTools.addFlashMessage(FacesMessage.SEVERITY_INFO, "L'utilisateur demandé n'existe pas.");

                RequestContext.getCurrentInstance().update("growl");
            }
        }
    }

    public void searchAction(){
        if("".equals(search) || search == null){
            initList();
        }else{
            clients = clientDao.searchClient(search);
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
        return projectDao.clientHasProject(client);
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
