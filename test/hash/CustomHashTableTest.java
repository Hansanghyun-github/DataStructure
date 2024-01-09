package hash;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomHashTableTest {
    CustomHashTable hashTable = new CustomHashTable<Integer, Integer>();

    @Test
    @DisplayName("HashTest")
    void hashTest() throws Exception {
        System.out.println("start");
        // given
        hashTable.put(1, 2);
        hashTable.put(2, 4);
        hashTable.put(3, 5);

        // when

        // then
        System.out.println(hashTable.get(1));
        System.out.println(hashTable.get(2));
        System.out.println(hashTable.get(3));

        hashTable.remove(1);

        System.out.println(hashTable.get(1));
        System.out.println(hashTable.get(2));
        System.out.println(hashTable.get(3));

        System.out.println("--------------------------");

        System.out.println(hashTable.containsKey(1));
        System.out.println(hashTable.containsKey(2));
        System.out.println(hashTable.containsKey(3));

        System.out.println(hashTable.containsValue(2));
        System.out.println(hashTable.containsValue(4));
        System.out.println(hashTable.containsValue(5));

    }
}