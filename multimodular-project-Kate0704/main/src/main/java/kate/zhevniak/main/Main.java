package kate.zhevniak.main;

import kate.zhevniak.algorithm.BinarySearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        int valueToSearch = 50;
        int index = BinarySearch.find(new int[]{-4, -3, -2, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12},
                valueToSearch);
        logger.debug("The end of the algorithm.\n\t** Search index: {} **\n\t** Search element: {} **",
                index,
                valueToSearch);
    }
}
