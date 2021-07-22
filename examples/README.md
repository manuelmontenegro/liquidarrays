# Examples

This directory contains some case studies. Most of them are implementations of usual algorithms on arrays (sorting, searching, etc.)

* `fill`: Given an array `a` and a value `x`, it returns an array with the same length as `a`, but with all positions initialized to `x`. 
* 
  **Precondition**: `true`.
  
  **Postcondition**: If `res` denotes the result, `∀i. 0 ≤ i < len(res) --> res[i] = x`. 

* `linsearch`: Linear search. Given an array `a` and an element `x`, it returns the index of the leftmost occurence of `x` in `a`. If `x` does not appear in `a`, then `len(a)` is returned.

  **Precondition**: `true`
  
  **Postcondition**: If `idx` denotes the result, `0 ≤ idx ≤ len(a) && (idx < len(a) --> a[idx] = x) && (∀i. 0 ≤ i < idx --> a[i] ≠ x)`

  
* `binsearch`: Binary search. The same as above, but assuming that the array is sorted. If the given element is not found, it returns `-1`.

  **Precondition**: `∀i, j. 0 ≤ i < j < len(a) --> a[i] < a[j]`
  
  **Postcondition**: If `idx` denotes the result, `-1 ≤ idx && (idx ≠ -1 --> idx < len(a) && a[idx] = x) && (idx = -1 --> ∀i. 0 ≤ i < len(a) --> a[i] ≠ x)`


* `binsearch_simple`: Binary search simplified. The same as `binsearch`, but if the element is not found it returns the index in which it should be inserted.

  **Precondition**: `∀i, j. 0 ≤ i < j < len(a) --> a[i] < a[j]`
  
  **Postcondition**: If `idx` denotes the result, `0 ≤ idx ≤ len(a) && (∀i. 0 ≤ i < idx --> a[i] < x) && (∀i. idx ≤ i < len(a) --> x ≤ a[i])`

* `dutchflag`: a solution to the [Dutch national flag problem](https://en.wikipedia.org/wiki/Dutch_national_flag_problem).

  **Precondition**: If `a` is the input array: `∀i. 0 ≤ i < len(a) --> 0 ≤ a[i] ≤ 2`
  
  **Postcondition**: If `res` denotes the result, and `l` and `m` the partition indices in the array: `0 ≤ idx ≤ len(a) && (∀i. 0 ≤ i < idx --> a[i] < x) && (∀i. idx ≤ i < len(a) --> x ≤ a[i])`


* `insert`: Given an increasingly sorted array `a` and an element `x`, it returns the result of inserting `x` into `a`, so that the result remains sorted.
* `insert_sort`: [Insertion sort](https://en.wikipedia.org/wiki/Insertion_sort) algorithm, using the `insert` function above.
* `partition`: Partition exchange algorithm used in [Quicksort](https://en.wikipedia.org/wiki/Quicksort), similar to Dutch national flag algorithm.
* `qsort`: [Quicksort](https://en.wikipedia.org/wiki/Quicksort) algorithm.
* `selsort`: [Selection sort](https://en.wikipedia.org/wiki/Selection_sort) algorithm.
* `selsort_divided`: The same algorithm as above, but split into two functions: one for computing the lowest element in an array, and another one for sorting the array by using the previous function.
