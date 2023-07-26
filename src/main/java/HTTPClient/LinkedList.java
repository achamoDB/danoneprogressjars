// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

class LinkedList
{
    private LinkElement head;
    private LinkElement tail;
    private LinkElement next_enum;
    
    LinkedList() {
        this.head = null;
        this.tail = null;
        this.next_enum = null;
    }
    
    public synchronized void addToHead(final Object o) {
        this.head = new LinkElement(o, this.head);
        if (this.head.next == null) {
            this.tail = this.head;
        }
    }
    
    public synchronized void addToEnd(final Object o) {
        if (this.head == null) {
            final LinkElement linkElement = new LinkElement(o, null);
            this.tail = linkElement;
            this.head = linkElement;
        }
        else {
            final LinkElement tail = this.tail;
            final LinkElement linkElement2 = new LinkElement(o, null);
            tail.next = linkElement2;
            this.tail = linkElement2;
        }
    }
    
    public synchronized void remove(final Object o) {
        if (this.head == null) {
            return;
        }
        if (this.head.element == o) {
            this.head = this.head.next;
            return;
        }
        for (LinkElement tail = this.head; tail.next != null; tail = tail.next) {
            if (tail.next.element == o) {
                if (tail.next == this.tail) {
                    this.tail = tail;
                }
                tail.next = tail.next.next;
                return;
            }
        }
    }
    
    public synchronized Object getFirst() {
        if (this.head == null) {
            return null;
        }
        return this.head.element;
    }
    
    public synchronized Object enumerate() {
        if (this.head == null) {
            return null;
        }
        this.next_enum = this.head.next;
        return this.head.element;
    }
    
    public synchronized Object next() {
        if (this.next_enum == null) {
            return null;
        }
        final Object element = this.next_enum.element;
        this.next_enum = this.next_enum.next;
        return element;
    }
    
    public static void main(final String[] array) throws Exception {
        System.err.println("\n*** Linked List Tests ...");
        final LinkedList list = new LinkedList();
        list.addToHead("One");
        list.addToEnd("Last");
        if (!list.getFirst().equals("One")) {
            throw new Exception("First element wrong");
        }
        if (!list.enumerate().equals("One")) {
            throw new Exception("First element wrong");
        }
        if (!list.next().equals("Last")) {
            throw new Exception("Last element wrong");
        }
        if (list.next() != null) {
            throw new Exception("End of list wrong");
        }
        list.remove("One");
        if (!list.getFirst().equals("Last")) {
            throw new Exception("First element wrong");
        }
        list.remove("Last");
        if (list.getFirst() != null) {
            throw new Exception("End of list wrong");
        }
        final LinkedList list2 = new LinkedList();
        list2.addToEnd("Last");
        list2.addToHead("One");
        if (!list2.getFirst().equals("One")) {
            throw new Exception("First element wrong");
        }
        if (!list2.enumerate().equals("One")) {
            throw new Exception("First element wrong");
        }
        if (!list2.next().equals("Last")) {
            throw new Exception("Last element wrong");
        }
        if (list2.next() != null) {
            throw new Exception("End of list wrong");
        }
        if (!list2.enumerate().equals("One")) {
            throw new Exception("First element wrong");
        }
        list2.remove("One");
        if (!list2.next().equals("Last")) {
            throw new Exception("Last element wrong");
        }
        list2.remove("Last");
        if (list2.next() != null) {
            throw new Exception("End of list wrong");
        }
        final LinkedList list3 = new LinkedList();
        list3.addToEnd("Last");
        list3.addToHead("Two");
        list3.addToHead("One");
        if (!list3.getFirst().equals("One")) {
            throw new Exception("First element wrong");
        }
        if (!list3.enumerate().equals("One")) {
            throw new Exception("First element wrong");
        }
        if (!list3.next().equals("Two")) {
            throw new Exception("Second element wrong");
        }
        if (!list3.next().equals("Last")) {
            throw new Exception("Last element wrong");
        }
        if (list3.next() != null) {
            throw new Exception("End of list wrong");
        }
        list3.remove("Last");
        list3.remove("Two");
        list3.remove("One");
        if (list3.getFirst() != null) {
            throw new Exception("Empty list wrong");
        }
        System.err.println("\n*** Tests finished successfuly");
    }
}
