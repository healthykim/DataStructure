public class MyHashTable<E extends Comparable<E>, K extends Comparable<K>> {
    public HashSlot<E, K>[] table;
    int num;
    static final Integer DELETED = -13, NOT_FOUND = -1;
    public MyHashTable(){
        table = new HashSlot[100];
        num = 0;
    }
    private int hash(K key){
        String st = (String)key;
        int prehash =0;
        for(int i=0; i<st.length(); i++){
            prehash+=st.charAt(i);
        }
        return prehash%100;
    }
    public int search(K key){
        int slot;
        slot = hash(key);
        if(table[slot] == null)
            return NOT_FOUND;
        else
            return slot;
    }
    public void insert(E x, K key){
        int slot = hash(key);
        if(table[slot]==null) {
            table[slot] = new HashSlot<>();
        }
        table[slot].tree.insert(x, key);
//        System.out.println("insert "+key+" to TreeNode, location "+x+", slot number "+ slot);
        num++;
    }

}
