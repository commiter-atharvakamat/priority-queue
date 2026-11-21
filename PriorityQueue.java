import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class PriorityQueue<T> {

    private final Comparator<T> comparator;

    private final ArrayList<T> heap;

    private PriorityQueue() {
        throw new UnsupportedOperationException("Use the PriorityQueue.fromComparator() instead");
    }

    private PriorityQueue(Comparator<T> clientComparator) {
        this.heap = new ArrayList<>();
        this.comparator = Objects.requireNonNull(clientComparator, "comparator");
    }

    public static <T> PriorityQueue<T> fromComparator(Comparator<T> clientComparator) {
        return new PriorityQueue<T>(clientComparator);
    }

    public void push(T n) {
        heap.add(Objects.requireNonNull(n, "pushed null element"));
        heapifyUp(heap.size() - 1);
    }

    private void heapifyUp(int i) {
        while (i > 0) {
            int parent_i = (i - 1) / 2;
            if (parent_i < 0 || comparator.compare(heap.get(i), heap.get(parent_i)) <= 0) {
                break;
            }
            swap(i, parent_i);
            i = parent_i;
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public T poll() {
        if (heap.isEmpty()) {
            return null;
        }
        T top = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return top;
    }

    private void heapifyDown(int i) {
        int n = heap.size();
        while (true) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && comparator.compare(heap.get(left), heap.get(largest)) > 0) {
                largest = left;
            }
            if (right < n && comparator.compare(heap.get(right), heap.get(largest)) > 0) {
                largest = right;
            }
            if (largest == i) {
                break;
            }
            swap(i, largest);
            i = largest;
        }
    }

    public int size() {
        return this.heap.size();
    }

    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    public T peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public void clear() {
        this.heap.clear();
    }

}