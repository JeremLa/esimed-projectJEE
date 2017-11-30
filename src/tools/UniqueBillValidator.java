package tools;

import dao.BillDAO;
import dao.UserDAO;

import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("uniqueBillValidator")
public class UniqueBillValidator implements Validator {

    private BillDAO billDAO;

    public UniqueBillValidator(){
        this.billDAO = CDI.current().select(BillDAO.class).get();
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        Integer number = (Integer) value;

        if (billDAO.existBillNumber(number)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ce numéro de facture existe déjà.", null));
        }
    }
}
