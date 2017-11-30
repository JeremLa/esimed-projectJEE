package entity.enumerable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PaymentMethods {
    CHECK("chèque"),
    TRANSFER("virement"),
    PAYPAL("paypal"),
    OTHER("autre");

    private String status = "";

    PaymentMethods(String status){
        this.status = status;
    }

    public String toString(){
        return status;
    }

    private static final List<PaymentMethods> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static PaymentMethods randomStatus()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
