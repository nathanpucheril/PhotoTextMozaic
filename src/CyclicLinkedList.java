
public class CyclicLinkedList <V> {
    private Node head, tail, curr;
    
    private int length = 0;

    public void add(V value) {
        if (head == null) {
            head = new Node(head, value);
            tail = head;
            curr = head;
        } else {
            tail.next = new Node(head, value);
            tail = tail.next;
        }
        length++;
    }

    public V getHead() {
        return head.value;
    }

    public V getTail() {
        return tail.value;
    }
    
    public int size() {
        return length;
    }

    public V next() {
        V retVal = curr.value;
        curr = curr.next;
        return retVal;
    }

    public void printList() {
        Node temp = head;
        for (int i = 0; i < length; i++) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    public class Node {
        private Node next;
        private V value;

        private Node(Node n, V value) {
            this.next = n;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        CyclicLinkedList<Integer> cll = new CyclicLinkedList<Integer>();
        cll.add(1);
        cll.add(0);
        cll.add(3);
        cll.printList();
    }

}
