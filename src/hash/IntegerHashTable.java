package hash;

public class IntegerHashTable {
    // 해시 테이블의 크기, 해시 함수, 충돌 전략, (Optional) load factor
    // 충돌 전략은 separate chainning을 사용 - 구현하는 것이 open addressing보다 간단

    private Entry[] table;
    private int count = 0; // entry의 개수

    public IntegerHashTable() {
        this(11);
    }

    public IntegerHashTable(int initialCapacity) {
        table = new Entry[initialCapacity];
    }

    private int hashFunction(int hash){
        return (hash & 0x7FFFFFFF) % table.length;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean containskey(Integer key) {
        if(key == null)
            throw new NullPointerException();

        int hash = key.hashCode();
        int index = hashFunction(hash);

        for(Entry e = table[index]; e != null; e = e.next){
            if(e.hash == hash && e.key.equals(key))
                return true;
        }

        return false;
    }

    public boolean containsvalue(Integer value) {
        if(value == null)
            throw new NullPointerException();

        Entry tab[] = table;
        for (int index = tab.length; --index >= 0; ){
            for(Entry e = tab[index]; e != null; e = e.next){
                if(e.value.equals(value))
                    return true;
            }
        }
        return false;
    }

    public Integer get(Integer key) {
        int hash = key.hashCode();
        int index = hashFunction(hash);
        for(Entry e = table[index]; e != null; e = e.next){
            if(e.hash == hash && e.key.equals(key))
                return (Integer) e.value;
        }

        return null;
    }

    public Integer put(Integer key, Integer value) {
        if(key == null || value == null)
            throw new NullPointerException();

        int hash = key.hashCode();
        int index = hashFunction(hash);

        Entry[] tab = table;
        for(Entry e = tab[index]; e != null; e = e.next){
            if(e.hash == hash && e.key.equals(key)){ // replace oldvalue -> newvalue
                Integer oldVal = e.value;
                e.value = value;
                return oldVal;
            }
        }

        // add new Entry
        Entry entry = new Entry(hash, key, value, tab[index]);// 현재 table이 가리키고 있는 위치를 next로 설정(separate chainning)
        tab[index] = entry;
        count++;
        return null;
    }

    public Integer remove(Integer key) {
        if(key == null)
            throw new NullPointerException();

        int hash = key.hashCode();
        int index = hashFunction(hash);
        Entry e = table[index];
        for(Entry prev = null; e != null; prev = e, e = e.next){
            if(e.hash == hash && e.key.equals(key)){ // find target
                if(prev != null) // table[index]의 첫번째 노드가 아닌, 다음 노드들 중 하나에서 발견
                    prev.next = e.next; // 이전 노드가 다음 노드의 위치를 저장
                else // table[index]의 첫번째 노드에서 발견
                    table[index] = e.next; // 다음 노드의 위치를 table[index]에 저장
                Integer value = e.value;
                e.value = null;
                count--;
                return value;
            }
        }

        return null;
    }
     
    public void clear() {
        Entry tab[] = table;
        for (int index = tab.length; --index >= 0; )
            tab[index] = null;
        count = 0;
    }

    private class Entry {
        final int hash; // hashcode 구하는 시간 단축시키기 위해 저장
        final Integer key;
        Integer value;
        Entry next;

        public Entry(int hash, Integer key, Integer value, Entry next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        protected Object clone() {
            return new Entry(hash, key, value,
                    (next==null ? null : (Entry) next.clone()));
        }

        public Integer setvalue(Integer value) {
            if(value == null) throw new NullPointerException();
            Integer oldVal = this.value;
            this.value = value;
            return oldVal;
        }
    }
}
