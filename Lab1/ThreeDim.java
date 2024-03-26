public class ThreeDim implements Printing
{
    private final Figure base;
    private final double height;
    private final double area, volume;

    ThreeDim(Figure base, double height) throws Exception
    {
        if(height == 0 )
            throw new Exception("height can't be 0");

        this.base = base;
        this. height = height;

        this.area = this.calculateArea();
        this.volume = this.calculateVolume();
    }

    double calculateArea()
    {
        //2 * pole podostawy + obwód podtsawy  * wysokosc
        double baseTotal = base.calculateArea() * 2;
        double sidesTotal = base.calculatePerimeter() * height;

        return baseTotal + sidesTotal;
    }

    double calculateVolume() { return base.calculateArea() * height; }

    public void print()
    {
        System.out.println("------------------");
        System.out.println("Ksztalt: Graniastoslup prosty");
        System.out.println("Wysokosc: " + height);
        System.out.println("Pole: " + area + " ^2, Objetosc: " + volume + " ^3");
    }
}
