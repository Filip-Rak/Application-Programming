public class Diamond extends Figure implements Printing
{
    private final double sideLength, height;
    private final double area, perimeter;

    Diamond(double sideLength, double height) throws Exception
    {
        if(!(sideLength > 0 && height > 0 && height <= sideLength))
            throw new Exception("Invalid Diamond");

        this.sideLength = sideLength;
        this.height = height;

        this.area = this.calculateArea();
        this.perimeter = this.calculatePerimeter();
    }

    double calculateArea() { return sideLength * height; }
    double calculatePerimeter() { return sideLength * 4; }

    public void print()
    {
        System.out.println("------------------");
        System.out.println("Ksztalt: Romb");
        System.out.println("Bok: " + sideLength);
        System.out.println("Wysokosc: " + height);
        System.out.println("Obwod: " + perimeter + ", Pole: " + area + " ^2");
    }
}
