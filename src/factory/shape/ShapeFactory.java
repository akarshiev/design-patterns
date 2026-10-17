package factory.shape;

public class ShapeFactory {
    public static Shape createShape(String shapeType, double... args) {
        if (shapeType == null || shapeType.isBlank()) {
            return null;
        }

        return switch (shapeType.trim().toLowerCase()) {
            case "circle" -> new Circle(args[0]);
            case "rectangle" -> new Rectangle(args[0], args[1]);
            case "square" -> new Square(args[0]);
            default -> throw new IllegalArgumentException("Unknown shape type" + shapeType);
        };
    }
}
