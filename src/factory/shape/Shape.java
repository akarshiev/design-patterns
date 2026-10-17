package factory.shape;

public abstract class Shape {
    public abstract double area();

    public abstract double perimeter();

    public void description() {
        System.out.printf("%s: Area=%.2f, Perimeter=%.2f\n",
                this.getClass().getSimpleName(),
                this.perimeter(),
                this.area());
    }
}
