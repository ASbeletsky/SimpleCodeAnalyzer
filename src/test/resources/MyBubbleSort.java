public class MyBubbleSort {


    public static void main(String[] args) {
        int[] input = {4, 2, 9, 6, 23, 12, 34, 0, 1};

        int n = input.length;
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (input[i] > input[k]) {
                    int temp;
                    temp = input[i];
                    input[i] = input[k];
                    input[k] = temp;
                }
            }

        }
    }
}