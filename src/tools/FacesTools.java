package tools;

import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;

@Named
@Singleton
public class FacesTools {
    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static void redirect(String url) {
        ExternalContext ec = getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/" + url);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String digestSHA256Hex(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                hexString.append(Integer.toString((aHash & 0xff) + 0x100, 16).substring(1));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            return text;
        }
    }

    public static String canonicolize(String string){
        String convert = string.toLowerCase();
        convert = Normalizer.normalize(convert, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");

        return convert;
    }

    public static void addFlashMessage(FacesMessage.Severity severity, String msg, Object... args) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);
        facesContext.addMessage(null, new FacesMessage(severity, String.format(msg, args), ""));
    }

    @Named
    public static String currentUserName(){
        return getRequest().getUserPrincipal().getName();
    }
}
