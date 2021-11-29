import java.io.*;

//class made with help from https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/
public class HashTable {
     static class HashNode<K, V>{
        String key;
        DrillingRecord record;
        final int hashCode;
        HashNode<K, V> next;
        public HashNode(DrillingRecord record, int hashCode )
        {
            this.key = record.getTime();
            this.record = record;
            this.hashCode = hashCode;
        }


    }

     public static class Map<K, V>{
        private HashNode[] bucketArray;

        private int numBuckets; //capacity of array
        private int size; //size of array
        private int prime; //used to make primes
        public Map(int length)
        {
            prime = length ;
            if(isPrime((6*prime)+1)){
                bucketArray = new HashNode[(6*prime)+1];
            }else{
                bucketArray = new HashNode[(6*prime)-1];
            }

            numBuckets = bucketArray.length;
            size = 0;


        }

         public int size() { return size; }

        public boolean isEmpty() { return size() == 0; }

        public int hash(String key){
            int code = 0;
            char[] arr = key.toCharArray();
            for(char ch : arr){
                int ascii = ch;
                code += ascii;
            }
            return code;
        }
        private int getBucketIndex(String key)
        {
            int hashCode = hash(key);
            int index = hashCode % bucketArray.length;
            // check if not negative
            index = index < 0 ? index * -1 : index;
            return index;
        }
        public HashNode[] getBucketArray(){
            return this.bucketArray;
        }
        public V get(String key)
        {
            // Find head of chain for given key
            int bucketIndex = getBucketIndex(key);
            int hashCode = hash(key);

            HashNode head = bucketArray[bucketIndex];

            // Search key in chain
            while (head != null) {
                if (head.key.equals(key) && head.hashCode == hashCode)
                    return (V) head.record;
                head = head.next;
            }

            // If key not found
            return null;
        }

        public int getCapacity(){
            return  numBuckets;
         }
         public double getLoadFactor(){
           return (1.0 * size)/(1.0* numBuckets);
         }
        public void add(K key, DrillingRecord value) {
            // Find head of chain for given key
            int bucketIndex = getBucketIndex((String) key);
            int hashCode = hash((String) key);
            HashNode head = bucketArray[bucketIndex];

            // Check if key is already present
            while (head != null) {
                if (head.key.equals(key) && head.hashCode == hashCode) {
                    head.record = value;
                    return;
                }
                head = head.next;
            }

            // Insert key in chain
            size++;
            head = bucketArray[hashCode % bucketArray.length];
            HashNode<K, V> newNode
                    = new HashNode<K, V>( value, hashCode);
            newNode.next = head;
            bucketArray[bucketIndex] = newNode;

            // If load factor goes beyond threshold, then
            // double hash table size
            if (this.getLoadFactor() >= 0.8) {
                prime++;
                if(isPrime(prime)){
                    prime++;
                }

                HashNode[] temp = bucketArray;
                if(isPrime((6*prime)+1)){
                    bucketArray = new HashNode[(6*prime)+1];
                }else{
                    bucketArray = new HashNode[(6*prime)-1];
                }
                numBuckets = bucketArray.length;
                int i = 0;
                for (HashNode<K, V> headNode : temp) {
                    while (headNode != null) {
                        bucketArray[i] = headNode;
                        headNode = headNode.next;
                    }
                    i++;
                }
            }
            if (this.getLoadFactor() <= 0.3) {
                prime= prime / 2;
                HashNode[] temp = bucketArray;
                if(isPrime(prime) && prime != 1){
                    prime--;
                }else{
                    prime++;
                }

                if(isPrime((6*prime)+1)){
                    bucketArray = new HashNode[(6*prime)+1];
                }else{
                    bucketArray = new HashNode[(6*prime)-1];
                }
                numBuckets = bucketArray.length;
                int i = 0;
                for (HashNode<K, V> headNode : temp) {
                    while (headNode != null) {
                        bucketArray[i] = headNode;
                        headNode = headNode.next;
                        i++;
                    }
                }
            }
        }

        //method to check if prime made with https://www.educba.com/prime-numbers-in-java/
         public boolean isPrime(int num){
             boolean prime = true;
             for(int i = 2; i <= num/2; ++i)
             {
                 if(num % i == 0)
                 {
                     prime = false;
                     break;
                 }
             }
             return prime;

     }

        //method to output as string
         @Override
        public String toString(){
            //make StringBuilder object to hold string data
            StringBuilder hashStr = new StringBuilder();
            //loop through bucketArray to add elements to hashStr
            for(int i = 0; i< bucketArray.length; i++){
                //append the index and separator
                //append the value first found in that index
                if (bucketArray[i] != null) {
                    hashStr.append(i);
                    hashStr.append(": ");
                    hashStr.append(bucketArray[i].record.toStringHash() + "\n");

                    //if there are elements in the overflow begin to loop through the linked list
                    if (bucketArray[i].next != null) {
                        //store head node as the first element in the particular index
                        HashNode head = bucketArray[i];
                        //append th eoverflow identifier
                        hashStr.append("OVERFLOW: ");
                        //iterate to next node value since the head is already stored
                        head = head.next;
                        //loop through the linked list and store the values in the hashStr
                        while (head != null) {
                            hashStr.append(head.record.toStringHash());
                            if (head.next == null) {
                                hashStr.append("\n");
                            }
                            head = head.next;
                        }
                    }
                }
            }
            return hashStr.toString();
        }

        //method that takes a name of a file to write the hashmap output to
         //it will create a new file so there is no need for routing or manually creating a new file
        public void writeToFile(String fileName) {
            File file = new File(fileName);
            BufferedWriter writer = null;
            try{
                //creates a new file for the results. It will appear in the directory when the program terminates
                file.createNewFile();
                //pass the file to the bufferedWriter object
               writer = new BufferedWriter( new FileWriter(file));
               System.out.println("Writing result to file");
                //use the previous toString method to write to the new file
                writer.write(this.toString());
                writer.flush();
            }catch(IOException io){
                //if it is unable to write to the file it will go to standard out
                System.out.println("Error finding file");
                System.out.println("Printing result on system.out");
                System.out.println(this.toString());
            }finally{
                //finally close the writer object
                try{
                    writer.close();
                    System.out.println("Writer closed");
                }catch(NullPointerException | IOException e){
                    System.out.println("No file to close");
                }
            }


        }
    }
}
