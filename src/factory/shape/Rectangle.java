package factory.shape;

public class Rectangle extends Shape {
    double width;
    double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double perimeter() {
        return width * height;
    }

    @Override
    public double area() {
        return 2 * (width + height);
    }
}
