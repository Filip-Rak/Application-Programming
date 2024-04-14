public class Rectangle extends Figure implements Printing
{
    private final double a, b;
    private final double area, perimeter;

    Rectangle(double a, double b) throws Exception
    {
        if(a == 0|| b == 0)
            throw new Exception("Invalid rectangle");

        this.a = a;
        this.b = b;

        this.area = this.calculateArea();
        this.perimeter = this.calculatePerimeter();
    }

    double calculateArea() { return a * b; }
    double calculatePerimeter() { return (a + b) * 2; }

    public void print()
    {
        System.out.println("------------------");
        System.out.println("Ksztalt: Prostokat");
        System.out.println("Boki: " + a + ", " + b);
        System.out.println("Obwod: " + perimeter + ", Pole: " + area + " ^2");
    }
}
