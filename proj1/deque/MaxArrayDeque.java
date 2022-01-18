package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> com;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        com = c;
    }

    public T max() {
        if (size() == 0) {
            return null;
        }
        T tMax = get(0);
        for (int i = 1; i < size(); i++) {
            tMax = (com.compare(tMax, get(i)) > 0 ? tMax : get(i));
        }
        return tMax;
    }

    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        }
        T tMax = get(0);
        for (int i = 1; i < size(); i++) {
            tMax = (c.compare(tMax, get(i)) > 0 ? tMax : get(i));
        }
        return tMax;
    }
}
