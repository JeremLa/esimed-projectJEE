package controller;

import dao.ClientDao;
import dao.UserDAO;
import entity.Client;
import entity.User;
import org.primefaces.context.RequestContext;
import tools.FacesTools;

import javax.annotation.PostConstruct;
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
    private Client client;
    private List<Client> clients;
    private Boolean fromList;
    private String search;

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

    public void searchAction(){
        if("".equals(search) || search == null){
            initList();
        }else{
            clients = clientDao.searchClient(search);
        }
    }

    public void fromList(){
        this.fromList = true;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
