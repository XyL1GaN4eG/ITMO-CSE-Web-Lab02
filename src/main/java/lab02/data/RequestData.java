package lab02.data;

public record RequestData(double[] x, double y, double r) {
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("x = ");
        if (x != null) {
            for (double element : x) {
                res.append(element).append(", ");
            }
        }
        res.append("y = ").append(y).append(", r = ").append(r);
        return res.toString();
    }
}
