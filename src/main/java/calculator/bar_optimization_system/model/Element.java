package calculator.bar_optimization_system.model;

public class Element {
    private int position;
    private int quantity;
    private int diameter;
    private int totalLength;

    public Element(int position,
     int quantity,
     int diameter,
     int totalLength) {
        this.position = position;
        this.quantity = quantity;
        this.diameter = diameter;
        this.totalLength = totalLength;
    }

    public int getPosition() {
        return position;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getTotalLength() {
        return totalLength;
    }

    @Override
    public String toString() {
        return "Elemento [Posição=" + position + ", Quantidade=" + quantity +
                ", Diâmetro=" + diameter + ", Comprimento=" + totalLength + "] \n";
    }
}
