package tools;

import dao.ClientDAO;
import entity.Client;

import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Client.class)
public class ClientConverter implements Converter{

    private ClientDAO clientDAO = CDI.current().select(ClientDAO.class).get();

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        return clientDAO.get(Client.class, Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Client)o).getId().toString();
    }
}
