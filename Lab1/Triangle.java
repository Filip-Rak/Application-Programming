public class Triangle extends Figure implements Printing
{
    private final double a, b, c;
    private final double area, perimeter;

    Triangle(double a, double b, double c) throws Exception
    {
        if(a + b <= c || a + c <= b || b + c <= a)
            throw new Exception("Invalid triangle");

        this.a = a;
        this.b = b;
        this.c = c;

        this.area = this.calculateArea();
        this.perimeter = this.calculatePerimeter();
    }

    double calculateArea()
    {
        double p =  (this.a + this.b + this.c) / 2;
        return Math.sqrt(p * (p-a) * (p-b) * (p - c));
    }

    double calculatePerimeter() {
        return this.a + this.b + this.c;
    }

    public void print()
    {
        System.out.println("------------------");
        System.out.println("Ksztalt: Trojkat");
        System.out.println("Boki: " + a + ", " + b + ", " + c);
        System.out.println("Obwod: " + perimeter + ", Pole: " + area + " ^2");
    }
}
