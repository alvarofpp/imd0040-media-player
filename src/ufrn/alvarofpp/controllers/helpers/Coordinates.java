package ufrn.alvarofpp.controllers.helpers;

/**
 * Essa classe guardará as coordendas X e Y das interfaces de usuários.
 * Será usada, principalmente quando as interfaces forem arrastadas.
 */
public class Coordinates {
    /**
     * Coordenada X
     */
    private double xOffset;
    /**
     * Coordenada Y
     */
    private double yOffset;

    public Coordinates() {
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
