package entity.enumerable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    private static final List<ProjectStatus> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static ProjectStatus randomStatus()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
