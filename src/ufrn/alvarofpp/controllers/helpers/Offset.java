package ufrn.alvarofpp.controllers.helpers;

public class Offset {
    private double xOffset;
    private double yOffset;

    public Offset() {
        this.xOffset = 0.0;
        this.yOffset = 0.0;
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }
}
