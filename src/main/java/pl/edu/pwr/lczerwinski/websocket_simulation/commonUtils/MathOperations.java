package pl.edu.pwr.lczerwinski.websocket_simulation.commonUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathOperations {
    public static int randomInt (int rangeStart, int rangeEnd)
    {
        Random random = new Random();
        return (random.nextInt(rangeEnd-rangeStart+1)+rangeStart);
    }
    public static double randomDouble(double rangeStart, double rangeEnd)
    {
        Random random = new Random();
        return (double) (random.nextDouble()*rangeEnd-rangeStart+1);
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static double calculatePercentage(double status, double maxValue)
    {
        return status/maxValue;
    }
}
