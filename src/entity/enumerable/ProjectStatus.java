package entity.enumerable;

public enum  ProjectStatus {
    PROSPECT("prospect"),
    DEVIS("devis"),
    SEND("envoyé"),
    ACCEPTED("devis accepté"),
    STARTED("démarré"),
    FINISHED("terminé"),
    CANCELED("annulé");

    private String status = "";

    ProjectStatus (String status){
        this.status = status;
    }

    public String toString(){
        return status;
    }
}
