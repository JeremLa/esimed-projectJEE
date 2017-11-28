package controller;

import dao.ClientDao;
import entity.Client;
import tools.FacesTools;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ViewScoped
public class ClientController implements Serializable{
    @Inject
    private ClientDao clientDao;

    private Client client = new Client();

    private String canonicalizeFullName(String firstName, String lastName){
        return FacesTools.canonicolize(firstName) + " " + FacesTools.canonicolize(lastName);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
