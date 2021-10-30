# Kate Zhevniak - ISsoft_Java - Multimodular Binary Search
Original GitHub account: https://github.com/Kate0704

### Modules:
- algorithm
- main
### Packages:
- kate.zhevniak.algorithm
- kate.zhevniak.main
### Files:
Name | Location | Description
--- | --- | ---
**Main.java** | module: <u>main</u>, package: <u>kate.zhevniak.main</u> | class for running the algorithm
**BinarySearch.java** | module: <u>algorithm</u>, package: <u>kate.zhevniak.algorithm</u> | class with recursive binary search algorithm implementation
**logback.xml** | module: <u>main</u>, folder: <u>src/main/resources</u> | loggers' configuration file
### BinarySearch class interface:
```java
// fields
private static final Logger logger;
private static int recursionDepth;
// methods
public static int find(int[] sortedArray, int valueToSearch); // public wrapper on recursive binary search
private static void checkSorted(int[] array);                 // checks if passed array is sorted
private static int recursiveBinarySearch(                     // rbs algorithm
        int[] array, int value, int fromIndex, int toIndex);
```
### Logs:
According to logback.xml logs are stored in **logs/debug** folder.
