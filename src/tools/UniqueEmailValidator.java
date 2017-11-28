package tools;

import dao.UserDAO;

import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("uniqueEmailValidator")
public class UniqueEmailValidator implements Validator {

    private UserDAO userDAO;

    public UniqueEmailValidator (){
        this.userDAO = CDI.current().select(UserDAO.class).get();
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        String mail = (String) value;

        if (userDAO.existEmail(mail)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ce mail est déjà utilisé.", null));
        }
    }
}
