import java.io.IOException;
import java.util.Scanner;

public class Main {
    static int k=0;
    public static void main(String[] args) throws IOException {
        Main main=new Main();
        main.writeKConsole();

        KMeans kMeans=new KMeans();
        kMeans.kMeans(k);
        

    }
    private void writeKConsole(){
       Scanner sc=new Scanner(System.in);
        System.out.println("Wpisz k do algortymu k-Means, wieksze od zera");
        try {
            k=sc.nextInt();
            if(k<=0)
                writeKConsole();

        }catch(Exception ex){
            System.out.println("K zostalo zle wpisane");
            writeKConsole();
        }

    }
}
