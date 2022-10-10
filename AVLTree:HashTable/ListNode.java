//자료구조 수업자료를 활용하여 작성
public class ListNode<E extends Comparable<E>> {
    public E item;
    public ListNode<E> next;
    public ListNode(E item){
        this.item = item;
        next = null;
    }
    public ListNode(E item, ListNode<E> next){
        this.item = item;
        this.next = next;
    }

    public String toString(){
        return this.item.toString();
    }
}
