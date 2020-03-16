package concurrent;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ShaoJiale
 * Date: 2020/3/16
 */
public class ConcurrentStack<E> {
    AtomicReference<Node<E>> top = new AtomicReference<>();

    public void push(E item) {
        Node<E> head = new Node<>(item);
        Node<E> old;
        do {
            old = top.get();
            head.next = old;
        } while (!top.compareAndSet(old, head));
    }

    public E pop() {
        Node<E> old;
        Node<E> cur;
        do {
            old = top.get();
            if (old == null) {
                return null;
            }
            cur = old.next;
        } while (!top.compareAndSet(old, cur));
        return old.item;
    }

    @RequiredArgsConstructor
    public class Node<E> {
        @NonNull public final E item;
        public Node<E> next;
    }

    public static void main(String[] args) {
        ConcurrentStack<Integer> stack = new ConcurrentStack<>();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("t1: " + stack.pop());
                stack.push(i);
            }
        });

        Thread t2 = new Thread(() -> {
           for (int i = 0; i < 20; i++) {
               stack.push(i + 10);
               System.out.println("t2: " + stack.pop());
           }
        });
        t2.start();
        t1.start();
    }
}
