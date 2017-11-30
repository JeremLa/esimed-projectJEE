package entity.enumerable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum  BillStatus {
    EDITED("édité"),
    SENT("envoyé"),
    PAID("payé");

    private String status = "";

    BillStatus (String status){
        this.status = status;
    }

    public String toString(){
        return status;
    }

    private static final List<BillStatus> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static BillStatus randomStatus()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
