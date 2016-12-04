public class MyTest {
    public static void main(String[] args) {
        int i=55;
        int j=177;

        if(i > 10) {
            switch (j) {
                case 1:
                    i++;
                    break;
                case 2:
                    i--;
                    break;
                case 3:
                    i = 0;
                    break;
            }
        } else {
            i = j / i;
            while (i < j) {
                i++;
                System.out.println(i);
            }
        }
    }
}
