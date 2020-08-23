
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
    /////////////////////
    private static class TableEntry<T> {
        private final String key;
        private final T value;

        public TableEntry(String key, T value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }


    ///////////////////////////

    static String filePath;
    static String filePathSearch;
    static Map<String, Long> contacts = new HashMap<>();
    static List<String> search = new ArrayList<>();

    static String input() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    static void reading() throws IOException {
        try {
            String[] stringsAll = new String[2];
            filePath = "C:\\Users\\gleb7\\Downloads\\directory.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                Long number = Long.parseLong(line.substring(0, line.indexOf(" ")));
                String name = line.substring(line.indexOf(" ") + 1, line.length());
                contacts.put(name, number);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("File not Found");
        }
    }

    static void reading2(HashMap<String, Long> table3) throws IOException {
        try {
            String[] stringsAll = new String[2];
            filePath = "C:\\Users\\gleb7\\Downloads\\directory.txt";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                Long number = Long.parseLong(line.substring(0, line.indexOf(" ")));
                String name = line.substring(line.indexOf(" ") + 1, line.length());
                table3.put(name, number);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("File not Found");
        }
    }


    static void readSearch() throws FileNotFoundException {
        try {
            filePathSearch = "C:\\Users\\gleb7\\Downloads\\find.txt";
            File file = new File(filePathSearch);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                search.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }

    }

    static void linearSearching(long startTime) {
        System.out.println("Start searching (linear search)...");
        long start = startTime;
        int searchCount = search.size();
        int count = 0;
        for (String xxx : search) {
            if (contacts.containsKey(xxx)) {
                count++;
            }
            if (count == searchCount) {
                break;
            }
        }
        long finish = System.currentTimeMillis();
        long milliseconds = finish - start;
        int millisecondsOstat = (int) milliseconds % 1000;
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        System.out.println("Found " + searchCount + " / " + count + " entries.");
        System.out.printf("Time taken: %d min. %d sec. %d ms.", minutes, seconds, millisecondsOstat);
        System.out.println();
    }

    public static String[] bubbleSort(String[] array) {
        List<String> st = new ArrayList<>();
        st = Arrays.asList(array);
        Arrays.sort(array);
        return array;
//        long time = System.currentTimeMillis();
//        for (int i = 0; i < array.length - 1; i++) {
//            for (int j = 0; j < array.length - i - 1; j++) {
//                /* if a pair of adjacent elements has the wrong order it swaps them */
//                if (array[j].compareTo(array[j + 1]) > 0) {
//                    String temp = array[j];
//                    array[j] = array[j + 1];
//                    array[j + 1] = temp;
//                }
//            }
//            System.out.println(((System.currentTimeMillis() - time) % 60000) / 1000);
//        }
//        return array;
    }

    static void jumpSearch() {
        System.out.println("Start searching (bubble sort + jump search)... ");
        String[] names = contacts.keySet().toArray(new String[0]);
        int count = 0;
        long allStartTime = System.currentTimeMillis();
        long startBubble = System.currentTimeMillis();
        String[] sortedNames = bubbleSort(names);
        long finalBubble = System.currentTimeMillis() - startBubble;
        long searchingStartTime = System.currentTimeMillis();
        if (finalBubble / 60000 >= 10) {
            for (String xxx : search) {
                if (contacts.containsKey(xxx)) {
                    count++;
                }
                if (count == search.size()) {
                    break;
                }
            }
            long searchingFinalTime = System.currentTimeMillis() - searchingStartTime;
            long allFinalTime = System.currentTimeMillis() - allStartTime;
            System.out.print("Found " + count + " / " + search.size() + " entries. ");
            System.out.println("Time taken: " + allFinalTime / 60000 + " min. " +
                    (allFinalTime % 60000) / 1000 + " sec. " + ((allFinalTime % 60000) % 1000) + " ms. ");
            System.out.println("Sorting time: " + finalBubble / 60000 + " min. " +
                    (finalBubble % 60000) / 1000 + " sec. " + ((finalBubble % 60000) % 1000) + " ms. " +
                    " - STOPPED, moved to linear search ");
            System.out.println("Searching time: " + searchingFinalTime / 60000 + " min. " +
                    (searchingFinalTime % 60000) / 1000 + " sec. " + ((searchingFinalTime % 60000) % 1000) + " ms. ");

        } else {

            for (String str : search) {
                if (jumpSearch1(sortedNames, str) != -1) {
                    count++;
                }
            }
            long searchingFinalTime = System.currentTimeMillis() - searchingStartTime;
            long allFinalTime = System.currentTimeMillis() - allStartTime;
            System.out.print("Found " + count + " / " + search.size() + " entries. ");
            System.out.println("Time taken: " + allFinalTime / 60000 + " min. " +
                    (allFinalTime % 60000) / 1000 + " sec. " + ((allFinalTime % 60000) % 1000) + " ms. ");
            System.out.println("Sorting time: " + finalBubble / 60000 + " min. " +
                    (finalBubble % 60000) / 1000 + " sec. " + ((finalBubble % 60000) % 1000) + " ms. ");
            System.out.println("Searching time: " + searchingFinalTime / 60000 + " min. " +
                    (searchingFinalTime % 60000) / 1000 + " sec. " + ((searchingFinalTime % 60000) % 1000) + " ms. ");
        }
    }

    static int jumpSearch1(String[] array, String target) {
        int currentRight = 0; // right border of the current block
        int prevRight = 0; // right border of the previous block

        /* If array is empty, the element is not found */
        if (array.length == 0) {
            return -1;
        }

        /* Check the first element */
        if (array[currentRight].equals(target)) {
            return 0;
        }

        /* Calculating the jump length over array elements */
        int jumpLength = (int) Math.sqrt(array.length);

        /* Finding a block where the element may be present */
        while (currentRight < array.length - 1) {

            /* Calculating the right border of the following block */
            currentRight = Math.min(array.length - 1, currentRight + jumpLength);

            if (array[currentRight].compareTo(target) >= 0) {
                break; // Found a block that may contain the target element
            }

            prevRight = currentRight; // update the previous right block border
        }

        /* If the last block is reached and it cannot contain the target value => not found */
        if ((currentRight == array.length - 1) && target.compareTo(array[currentRight]) > 0) {
            return -1;
        }

        /* Doing linear search in the found block */
        return backwardSearch(array, target, prevRight, currentRight);
    }

    public static int backwardSearch(String[] array, String target, int leftExcl, int rightIncl) {
        for (int i = rightIncl; i > leftExcl; i--) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static void quickSearchMain() {
        System.out.println("Start searching (quick sort + binary search)... ");
        String[] names = contacts.keySet().toArray(new String[0]);
        int count = 0;
        long allStartTime = System.currentTimeMillis();
        long startSort = System.currentTimeMillis();
        quickSort(names, 0, names.length - 1);
        long finishSort = System.currentTimeMillis() - startSort;
        long startSearch = System.currentTimeMillis();
        for (String st : search) {
            if (binarySearch(names, st, 0, names.length - 1) != -1) {
                count++;
            }
        }
        long finishSearch = System.currentTimeMillis() - startSearch;
        long allFinalTime = System.currentTimeMillis() - allStartTime;
        System.out.print("Found " + count + " / " + search.size() + " entries. ");
        System.out.println("Time taken: " + allFinalTime / 60000 + " min. " +
                (allFinalTime % 60000) / 1000 + " sec. " + ((allFinalTime % 60000) % 1000) + " ms. ");
        System.out.println("Sorting time: " + finishSort / 60000 + " min. " +
                (finishSort % 60000) / 1000 + " sec. " + ((finishSort % 60000) % 1000) + " ms. ");
        System.out.println("Searching time: " + finishSearch / 60000 + " min. " +
                (finishSearch % 60000) / 1000 + " sec. " + ((finishSearch % 60000) % 1000) + " ms. ");
    }

    public static void quickSort(String[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right); // the pivot is already on its place
            quickSort(array, left, pivotIndex - 1);  // sort the left subarray
            quickSort(array, pivotIndex + 1, right); // sort the right subarray
        }
    }

    private static int partition(String[] array, int left, int right) {
        String pivot = array[right];  // choose the rightmost element as the pivot
        int partitionIndex = left; // the first element greater than the pivot

        /* move large values into the right side of the array */
        for (int i = left; i < right; i++) {
            if (array[i].compareTo(pivot) <= 0) { // may be used '<' as well
                swap(array, i, partitionIndex);
                partitionIndex++;
            }
        }

        swap(array, partitionIndex, right); // put the pivot on a suitable position

        return partitionIndex;
    }

    private static void swap(String[] array, int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static int binarySearch(String[] array, String elem, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2; // the index of the middle element

            if (elem.compareTo(array[mid]) == 0) {
                return mid; // the element is found, return its index
            } else if (elem.compareTo(array[mid]) < 0) {
                right = mid - 1; // go to the left subarray
            } else {
                left = mid + 1;  // go the the right subarray
            }
        }
        return -1; // the element is not found
    }

    public static void hashSearch() {
        System.out.println("Start searching (hash table)... ");
        int count = 0;
        long allStartTime = System.currentTimeMillis();
        long startSort = System.currentTimeMillis();
        HashMap<String, Long> table = new HashMap<>();
        table = (HashMap<String, Long>) contacts;
        long finishSort = System.currentTimeMillis() - startSort;
        long startSearch = System.currentTimeMillis();
        for (String st : search) {
            if (table.get(st) != null) {
                count++;
            }
        }
        long finishSearch = System.currentTimeMillis() - startSearch;
        long allFinalTime = System.currentTimeMillis() - allStartTime;
        System.out.print("Found " + count + " / " + search.size() + " entries. ");
        System.out.println("Time taken: " + allFinalTime / 60000 + " min. " +
                (allFinalTime % 60000) / 1000 + " sec. " + ((allFinalTime % 60000) % 1000) + " ms. ");
        System.out.println("Creating time: " + finishSort / 60000 + " min. " +
                (finishSort % 60000) / 1000 + " sec. " + ((finishSort % 60000) % 1000) + " ms. ");
        System.out.println("Searching time: " + finishSearch / 60000 + " min. " +
                (finishSearch % 60000) / 1000 + " sec. " + ((finishSearch % 60000) % 1000) + " ms. ");
    }


    public static class HashTable<T> {
        private int size;
        private TableEntry[] table;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
        }

        public boolean put(String key, T value) {
            // put your code here
            int idx = findKey(key);
            while (idx == -1) {
                rehash();
                idx = findKey(key);
                //return false;
            }
            table[idx] = new TableEntry(key, value);
            return true;

        }

        public T get(String key) {
            int idx = findKey(key);

            if (idx == -1 || table[idx] == null) {
                return null;
            }

            return (T) table[idx].getValue();
        }

        private int findKey(String key) {
            int hash = 0;

            while (!(table[hash] == null || table[hash].getKey().equals(key))) {
                hash = (hash + 1) % size;

                if (hash == 0) {
                    return -1;
                }
            }

            return hash;
        }

        private void rehash() {
            // put your code here
            int factor = 2;
            this.size = this.size * factor;
            TableEntry[] newTable = new TableEntry[size];
            TableEntry[] temp = this.table;
            this.table = newTable;
            for (TableEntry t : temp) {
                put(t.key, (T) t.getValue());
            }
        }

    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        reading();
        readSearch();
        linearSearching(startTime);
        System.out.println();
        jumpSearch();
        System.out.println();
        quickSearchMain();
        System.out.println();
        hashSearch();
    }
}

