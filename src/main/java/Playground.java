import java.util.Scanner;

public class Playground {
    private static int sum(String str) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            res += (str.charAt(i) - 'a' + 1);
        }
        return res;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("choices: ");
        String str = scanner.next();
        System.out.println(sum(str));
        System.out.println(sum(str) / 3);
    }
}
