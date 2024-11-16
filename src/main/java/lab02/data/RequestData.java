package lab02.data;

public record RequestData(int[] x, double y, double r) {
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("x = ");
        if (x != null) {
            for (int element : x) {
                res.append(element).append(", ");
            }
        }
        res.append("y = ").append(y).append(", r = ").append(r);
        return res.toString();
    }
}
