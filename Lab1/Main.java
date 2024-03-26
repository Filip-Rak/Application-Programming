import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scanner = new Scanner(System.in);

            while(true)
            {
                System.out.println("############");
                System.out.println("1: Triangle, 2: Rectangle, 3: Diamond, 0: exit");

                int input = scanner.nextInt();
                double a, b, c, h;
                Figure f = new Rectangle(2, 2);

                switch(input)
                {
                    case 1:
                        System.out.print("A B C:");
                        a = scanner.nextDouble();
                        b = scanner.nextDouble();
                        c = scanner.nextDouble();

                        f = new Triangle(a, b, c);
                        f.print();
                        break;
                    case 2:
                        System.out.print("A B:");
                        a = scanner.nextDouble();
                        b = scanner.nextDouble();

                        f = new Rectangle(a, b);
                        f.print();
                        break;
                    case 3:
                        System.out.print("A H: ");
                        a = scanner.nextDouble();
                        b = scanner.nextDouble();

                        f = new Diamond(a, b);
                        f.print();

                        break;
                    case 0:
                        System.out.println("Adios");
                        System.exit(0);
                        break;
                }


                System.out.print("\nWysokosc dla graniastoslupa (0 - nie tworz, pomin): ");
                h = scanner.nextDouble();
                if(h != 0)
                {
                    ThreeDim td = new ThreeDim(f, h);
                    td.print();
                }
                else
                    System.out.println("Graniastoslup pominiety");

                System.out.print("\n");
            }

        }
        catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.exit(1);
        }

    }
}

