import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public abstract class DummyClass implements Runnable {
    public static final String TAG = "dummy psf TAG";
    private final int value = 0;
    protected char[] chars = new char[10];
    float floats[] = {1f, (float) 2., 3};
    @Nullable
    transient String nullableTransient;

//  todo:
//    {
//        value = 0;
//    }

    DummyClass() {
        super();//dummy empty package local constructor
    }

    public DummyClass(int a) {
        this();
    }

    @SuppressWarnings("unused")
    double dummyMethod(int a, Float b) {
        return a + (b != null ? b : 0);
    }

    public abstract static class InClass<T extends DummyClass & Cloneable>
            extends DummyClass
            implements Cloneable, Runnable, Collection<T> {
        T generic;

        @Override
        public void run() {

        }

        /**
         * Non parsable method
         *
         * @param inParamGeneric
         * @param <Q>
         * @return
         */
        @NotNull
        @SuppressWarnings("unused")
        public <Q> T getGeneric(@Nullable List<? super Q> inParamGeneric, int x) throws IOException, IOException {
            try {
                Object o = Class.forName("tests.DummyClass")
                        .getDeclaredConstructor(int.class)
                        .newInstance(1);
            } catch (ClassNotFoundException | ClassCastException | NoSuchMethodException t) {
                t.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                System.out.print("finally");
            }
            throw new IOException();
        }
    }

    int method() {
        try {
            int x = (int) (1000. * Math.random());
            if(x > 10 && x < 100) {
                if(x == 11) {
                    System.out.println("start");
                } else if(x == 99) {
                    System.out.println("end");
                } else if(x % 2 == 0){
                    outFor : for(int i=0; i<x; i++) {
                        for(int j=i; j >= 0; j--) {
                            if(x % i == x % j) {
                                System.out.println("catch!");
                                break outFor;
                            } else if(i == j / 2) {
                                return x + i > j ? i : j;
                            }
                        }
                    }
                } else {
                    switch (x) {
                        case 15:
                        case 17:
                            return 17;
                        case 19:
                            return -19;
                        default: return x;
                    }
                }
            } else return -x;
        } finally {
            System.out.println("finally");
        }
        return Integer.MIN_VALUE;
    }

}

final class OutClass extends DummyClass {
    int a = 0;

    public void run() {

    }
}
