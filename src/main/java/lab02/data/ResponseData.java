package lab02.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private double x;
    private double y;
    private double r;
    private boolean isIn;
    private long executionTime;
    private String serverTime;

    public ResponseData (double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isIn = false;
    }
}
