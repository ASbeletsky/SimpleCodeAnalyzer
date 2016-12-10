public class GraphTest {
    public static void main(String[] args) {
        int i = (int) (Math.random() * 1000);
        int j = (int) (Math.random() * 1000);
        int k = (int) (Math.random() * 1000);
        int r = (int) (Math.random() * 1000);
        int max = 0;
        int tmp = 0;

        //sort
        int array[] = new int[4];
        array[0] = i;
        array[1] = j;
        array[2] = k;
        array[3] = r;

        int index1 = 0;
        int index2 = 0;
        while(index1 < array.length) {
            while(index2 < array.length) {
                if(index1 != index2) {
                    if(array[index1] > array[index2]) {
                        tmp = array[index1];
                        array[index1] = array[index2];
                        array[index2] = tmp;
                    }
                }

                index2++;
            }

            index1++;
        }




    }
}
