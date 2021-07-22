# Extending Liquid Types to Arrays

This tool analyses a program written in the [CAVI-ART intermediate representation](https://doi.org/10.1007/978-3-319-27436-2_14) (CLIR) and verifies it against the preconditions and postconditions given by the user. These pre/postconditions may include quantified assertions on arrays. The goal of this work is to assess the feasibility of extending [Liquid Types](https://ucsd-progsys.github.io/liquidhaskell-blog/about.html) in order to allow quantified properties on arrays (see [Bradley et al.](https://doi.org/10.1007/11609773_28)).

This repository contains a prototype that implements the ideas explained in the following papers:

* M. Montenegro, S. Nieva, R. Peña, C. Segura. [_Liquid Types for Array Invariant Synthesis_](https://10.1007/978-3-319-68167-2_20). Proceedings of the 15th International Symposium on Automated Verification and Analysis, ATVA 2017.

* M. Montenegro, S. Nieva, R. Peña, C. Segura. _Extending Liquid Types to Arrays_. Submitted to Transactions on Computer Logic, 2019.


## Building liquidarrays

### Requirements

* Z3 version 4.8.11 [https://github.com/Z3Prover/z3/wiki], although newer versions may work. **Z3 Java bindings are also required**.
* Java SE runtime environment (ver. 8 or greater). [https://www.oracle.com/technetwork/es/java/javase/overview/index.html]
* Maven v3.6 or greater. [https://maven.apache.org]

 
### Build instructions

This repository contains a Maven project which depends on the Z3 bindings for Java. Since this dependency does not appear in Maven central repositories, it has to be manually installed in the local repository *after installing Z3*.

Go to the directory where the file `com.microsoft.z3.jar` is located. This file is included with Z3 Java bindings. In Fedora Linux it is located under `/usr/lib64/z3` directory. Execute the following in the shell:

```
mvn install:install-file -Dfile=com.microsoft.z3.jar -DgroupId=com.microsoft -DartifactId=z3 -Dversion=4.8.11 -Dpackaging=jar
```

Checkout the Git repository and compile the project:

```
git clone https://github.com/manuelmontenegro/liquidarrays.git
cd liquidarrays
mvn compile assembly:single
```

After compilation, an standalone file `liquidarrays-0.1-jar-with-dependencies.jar` should have been created under the `target` directory.

Optionally, run unit tests by typing:

```
mvn test
```

## Usage

The generated `.jar` file after compilation is an standalone file containing all the dependencies. It can be run in the `target` directory by using:

```
java -jar liquidarrays-0.1-jar-with-dependencies.jar FILENAME [OPTIONS]
```

where `FILENAME` is the name of the CLIR file to be analysed (see `examples` directory for some of them), and `OPTIONS` is an optional list of command-line options. The list of available options is shown with the `--help` option (or `-h`). The following options are allowed:

* `--expand-only CLIRFILE` (or `-e CLIRFILE`)

  Stop after computing $$\mathbb{Q}^\star$$, $$\mathbb{Q}^\star_I$$, etc. sets for each template qualifier. It decorates each parameter with a template variable and it generates directives which specify the $$\mathbb{Q}^\star$$ corresponding to each template variable. The resulting program is written to `CLIRFILE`, again in CLIR format. This allows one to manually remove qualifiers in the resulting `CLIRFILE`, and run the tool on the tweaked `CLIRFILE` as input in order to obtain faster execution times. The iterative weakening algorithm will not be applied in presence of the `-e` or `--expand-only` option. 

* `--goals MDFILE` (or `-g MDFILE`)

  It generates a file `MDFILE` with the goals to be solved before applying iterative weakening. These goals depend on template predicate symbols (`_kappa_*`, `_mu_*`). Output file is in Markdown format.
  
* `--output MDFILE` (or `-o MDFILE`)

  It specifies the name of the output file containing the solution found. For each `_kappa_*` or `_mu_*` variable, its corresponding conjunction of predicates will be given. Output file is in Markdown format. If this option is not specified, the solution will be flushed to standard output.
  
* `--prune-trivial` (or `-p`)

  _[Experimental]_ It removes trivial quantified refinements in each weakening step. A quantified refinement $$\forall i,  j. P \Leftrightarrow Q$$ is trivial when the left-hand side of the implication (i.e. $$P$$) is logically equivalent to $$false$$. This provides simplified results, but checking trivial refinements may imply overhead.
  
  
* ``--timeout NUMBER``

   Specifies how much time is given to Z3 in order to determine whether a formula is valid or not. If Z3 yields no response after `NUMBER` milliseconds, the formula is assumed to be `false`. Default timeout is 5000 ms. If the given limit is too low, Z3 might fail to verify a valid formula, possibly leading to nontermination of iterative weakening or false negatives. Use with care.
   
   
### Examples

The `examples` directory contains some case studies. Most of them are implementations of usual algorithms on arrays (sorting, searching, etc.)

* `fill`: Given an array `a` and a value `x`, it returns an array with the same length as `a`, but with all positions initialized to `x`.
* `linsearch`: Linear search. Given an array `a` and an element `x`, it returns the index of the leftmost occurence of `x` in `a`. If `x` does not appear in `a`, then `len(a)` is returned.
* `binsearch`: Binary search. The same as above, but assuming that the array is sorted. If the given element is not found, it returns `-1`.
* `binsearch_simple`: Binary search simplified. The same as `binsearch`, but if the element is not found it returns the index in which it should be inserted.
* `dutchflag`: a solution to the [Dutch national flag problem](https://en.wikipedia.org/wiki/Dutch_national_flag_problem).
* `insert`: Given an increasingly sorted array `a` and an element `x`, it returns the result of inserting `x` into `a`, so that the result remains sorted.
* `insert_sort`: [Insertion sort](https://en.wikipedia.org/wiki/Insertion_sort) algorithm, using the `insert` function above.
* `partition`: Partition exchange algorithm used in [Quicksort](https://en.wikipedia.org/wiki/Quicksort), similar to Dutch national flag algorithm.
* `qsort`: [Quicksort](https://en.wikipedia.org/wiki/Quicksort) algorithm.
* `selsort`: [Selection sort](https://en.wikipedia.org/wiki/Selection_sort) algorithm.
* `selsort_divided`: The same algorithm as above, but split into two functions: one for computing the lowest element in an array, and another one for sorting the array by using the previous function.

#### Usage example

In the `target` directory, execute the following:

```
java -jar liquidarrays-0.1-jar-with-dependencies.jar ../examples/linsearch/linsearch.clir
```

## TODO

- [ ] Simplify output:
	- [ ] By pruning trivial quantified refinements.
	- [ ] By joining quantified refinements with the same LHS.

- [ ] Send triggers to Z3 to improve performance.