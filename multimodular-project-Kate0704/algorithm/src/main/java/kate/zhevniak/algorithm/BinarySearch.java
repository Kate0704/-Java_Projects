package kate.zhevniak.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public final class BinarySearch {
    private static final Logger logger = LoggerFactory.getLogger(BinarySearch.class);
    private static int recursionDepth = 0;

    /**
     * Recursive binary search function wrapper for the users' convenience
     *
     * @param sortedArray   the array to be searched
     * @param valueToSearch the value to be searched for
     * @return result of recursive binary rearch
     */
    public static int find(int[] sortedArray, int valueToSearch) {
        logger.debug("""
                
                #----------------------------------------------------------------------------
                # BinarySearch.find
                #----------------------------------------------------------------------------""");
        logger.debug("""
                        In method kate.zhevniak.algorithm.BinarySearch.find:
                        \t* search value: {};
                        \t* array length: {}
                        \t* sorted array: {}""",
                valueToSearch, sortedArray.length, sortedArray);
        recursionDepth = 0;
        checkSorted(sortedArray);
        return recursiveBinarySearch(sortedArray, valueToSearch,
                0, sortedArray.length - 1);
    }

    /** Checks if the array parameter passed to find method is sorted.
     *
     * @param array the array to be checked
     */
    private static void checkSorted(int[] array) {
        int[] arrayCopy = Arrays.copyOf(array, array.length);
        Arrays.sort(array);
        if (!Arrays.equals(arrayCopy, array)){
            logger.error("Array must be sorted!");
            throw new IllegalArgumentException("Array must be sorted!");
        }
    }

    /**
     * Searches the specified sorted array of integers for the specified value
     * using the binary search algorithm.
     * If it is not sorted, the results are undefined.
     *
     * @param array     the array to be searched
     * @param value     the value to be searched for
     * @param fromIndex the index of the first element to be searched
     * @param toIndex   the index of the last element to be searched
     * @return index of the search value or -1 if not found
     */
    private static int recursiveBinarySearch(
            int[] array, int value, int fromIndex, int toIndex) {
        logger.debug("\nRecursion iteration: {}", recursionDepth++);
        logger.debug("Search from index {} to index {}.", fromIndex, toIndex);

        if (fromIndex > toIndex) {
            logger.debug("STOP! Value not found: return -1.");
            return -1;
        }

        int middle = (fromIndex + toIndex) / 2;
        logger.debug("Middle element: array[{}] = {}", middle, array[middle]);

        if (array[middle] > value) {
            logger.debug("Array[middle] > search value ({} > {}). New recursive call.", array[middle], value);
            return recursiveBinarySearch(array, value, fromIndex, middle - 1);
        }
        if (array[middle] < value) {
            logger.debug("Array[middle] < search value ({} < {}). New recursive call.", array[middle], value);
            return recursiveBinarySearch(array, value, middle + 1, toIndex);
        }
        logger.debug("STOP! Search value index found: array[{}] = {}", middle, array[middle]);
        return middle;
    }
}
