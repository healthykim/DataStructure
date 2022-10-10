//자료구조 수업자료를 활용하여 작성
public class MyLinkedList<E extends Comparable<E>>  {
    private ListNode<E> head;
    private int num;
    public MyLinkedList(){
        num=0;
        head=new ListNode<>(null, null);
    }

    public void add(int idx, E x) {
        if(idx>=0&&idx<=num){
            ListNode<E> prev = getNode(idx-1);
            ListNode<E> newNode = new ListNode<>(x, prev.next);
            prev.next = newNode;
            num++;
        }
        else
            throw new Error("insert error");
    }

    public void append(E x) {
        ListNode<E> prev = head;
        while (prev.next != null) {
            prev = prev.next;
        }
        ListNode<E> newNode = new ListNode<>(x, prev.next);
        prev.next = newNode;
        num++;
    }

    public E remove(int idx){
        if(idx >=0 && idx<=num-1){
            ListNode<E> prev = getNode(idx-1);
            ListNode<E> curr = prev.next;
            prev.next = curr.next;
            num--;
            return curr.item;
        }
        else {
            throw new Error("remove error");
        }
    }

    public boolean removeItem(E x){
        ListNode<E> curr = head;
        for(int i=0; i<=num-1; i++){
            ListNode<E> prev = curr;
            curr =prev.next;
            if(curr.item.compareTo(x)==0){
                prev.next = curr.next;
                num--;
                return true;
            }
        }
        return false;
    }

    public E get(int idx){
        if(idx>=0&&idx<=num-1)
            return getNode(idx).item;
        else
            return null;
    }

    public void set(int idx, E x){
        if(idx>=0&&idx<=num-1){
            getNode(idx).item = x;
        }
        else
            throw new Error("set error");
    }

    private ListNode<E> getNode(int idx){
        if(idx>=-1&&idx<=num-1){
            ListNode<E> curr = head;
            for(int i=0; i<=idx; i++){
                curr = curr.next;
            }
            return curr;
        }
        return null;
    }

    private final int NOT_FOUND = -1;
    public int indexOf(E x){
        ListNode<E> curr = head;
        for(int i=0; i<=num-1; i++){
            curr = curr.next;
            if(curr.item.compareTo(x)==0){
                return i;
            }
        }
        return NOT_FOUND;
    }
    public int size(){return num;}
    public boolean isEmpty(){return num==0;}
    public void clear(){this.num=0;this.head = new ListNode<E>(null, null);}
    public String toString(){
        String result = "";
        ListNode<E> curr = head;
        for(int i=0; i<=num-1; i++){
            curr = curr.next;
            result += curr.toString();
            result+=" ";
        }
        curr = curr.next;
        result+=curr.toString();
        return result;
    }

}
