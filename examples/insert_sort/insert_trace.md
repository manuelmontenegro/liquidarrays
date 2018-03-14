## Step 0

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_1`: Precondition of insert-sort must imply the qualifier of its parameter a

```lisp
(declare-fun a_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(assert (>= a_length 0))
(assert (not (_mu_insert-sort_a a a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0) a!1)))))
```

*Result:* UNKNOWN

### Weakening `_mu_insert-sort_a`

#### Clasifying single refinements

#### Clasifying double refinements

```lisp
(declare-fun a_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(assert (>= a_length 0))
(assert (not (_mu_insert-sort_a a a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun a_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(assert (>= a_length 0))
(assert (not (_mu_insert-sort_a a a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0) a!1)))))
```

*Result:* UNKNOWN



## Step 1

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_2`: The qualified type of the result of insert-sort must imply its postcondition

```lisp
(declare-fun a_length () Int)
(declare-fun res_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _mu_insert-sort_res ((Array Int Int) (Array Int Int) Int Int) Bool)
(declare-fun res () (Array Int Int))
(assert (>= a_length 0))
(assert (>= res_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_mu_insert-sort_res res a res_length a_length))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (< j res_length))
                 (<= (select res i) (select res j))))))
  (not a!1)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_res nu a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE



## Step 2

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_9`: Precondition of parameter k in call to insert

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _NU_5 () Int)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (= _NU_5 k))
(assert (not (<= 0 _NU_5)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 3

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_10`: Precondition of parameter a in call to insert

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _NU_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _NU_6 () (Array Int Int))
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _NU_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (and (= _NU_6 a_1) (= _NU_6_length a_1_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (< j k))
                 (<= (select _NU_6 i) (select _NU_6 j))))))
  (not (and (< k _NU_6_length) a!1))))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 4

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_13`: Precondition of parameter k in call to f1

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_9 () Int)
(declare-fun _kappa_f1_k_clone_1 (Int (Array Int Int) Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (= _NU_9 k1))
(assert (not (_kappa_f1_k_clone_1 _NU_9 ap ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k_clone_1 nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
```

*Result:* UNSATISFIABLE



## Step 5

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_14`: Precondition of parameter a in call to f1

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _NU_10_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_10 () (Array Int Int))
(declare-fun _mu_f1_a_clone_2
             ((Array Int Int) (Array Int Int) Int Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _NU_10_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (and (= _NU_10 ap) (= _NU_10_length ap_length)))
(assert (not (_mu_f1_a_clone_2 _NU_10 ap k1 _NU_10_length ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNKNOWN

### Weakening `_mu_f1_a_clone_2`

#### Clasifying single refinements

#### Clasifying double refinements

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _NU_10_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_10 () (Array Int Int))
(declare-fun _mu_f1_a_clone_2
             ((Array Int Int) (Array Int Int) Int Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _NU_10_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (and (= _NU_10 ap) (= _NU_10_length ap_length)))
(assert (not (_mu_f1_a_clone_2 _NU_10 ap k1 _NU_10_length ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _NU_10_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_10 () (Array Int Int))
(declare-fun _mu_f1_a_clone_2
             ((Array Int Int) (Array Int Int) Int Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _NU_10_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (and (= _NU_10 ap) (= _NU_10_length ap_length)))
(assert (not (_mu_f1_a_clone_2 _NU_10 ap k1 _NU_10_length ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE

Trying new superset.

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _NU_10_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_10 () (Array Int Int))
(declare-fun _mu_f1_a_clone_2
             ((Array Int Int) (Array Int Int) Int Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _NU_10_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (and (= _NU_10 ap) (= _NU_10_length ap_length)))
(assert (not (_mu_f1_a_clone_2 _NU_10 ap k1 _NU_10_length ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNKNOWN

Trying new superset.

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _NU_10_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_10 () (Array Int Int))
(declare-fun _mu_f1_a_clone_2
             ((Array Int Int) (Array Int Int) Int Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _NU_10_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (and (= _NU_10 ap) (= _NU_10_length ap_length)))
(assert (not (_mu_f1_a_clone_2 _NU_10 ap k1 _NU_10_length ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNKNOWN

Trying new superset.

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _NU_10_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_10 () (Array Int Int))
(declare-fun _mu_f1_a_clone_2
             ((Array Int Int) (Array Int Int) Int Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _NU_10_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (and (= _NU_10 ap) (= _NU_10_length ap_length)))
(assert (not (_mu_f1_a_clone_2 _NU_10 ap k1 _NU_10_length ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE

  Weakening length refinement:

 ```lisp
(forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
     (and (>= nu_length 0) (>= a_length 0) (<= k nu_length))))
```

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _NU_10_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_10 () (Array Int Int))
(declare-fun _mu_f1_a_clone_2
             ((Array Int Int) (Array Int Int) Int Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _NU_10_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (and (= _NU_10 ap) (= _NU_10_length ap_length)))
(assert (not (_mu_f1_a_clone_2 _NU_10 ap k1 _NU_10_length ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
     (and (>= nu_length 0) (>= a_length 0) (<= k nu_length)))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE


## Step 6

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_15`: The type of (@ f1 k1 ap) must match the type of the result #1 of f1

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _X_5_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(declare-fun _X_5 () (Array Int Int))
(declare-fun _mu_f1_res_clone_3
             ((Array Int Int) Int (Array Int Int) Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _X_5_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (_mu_f1_res _X_5 k1 ap _X_5_length ap_length))
(assert (not (_mu_f1_res_clone_3 _X_5 k a_1 _X_5_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res_clone_3 nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 7

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_16`: The type of a_1 must match the type of the result #1 of f1

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _X_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert (not b))
(assert (and (= _X_6 a_1) (= _X_6_length a_1_length)))
(assert (not (_mu_f1_res _X_6 k a_1 _X_6_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNKNOWN

### Weakening `_mu_f1_res`

#### Clasifying single refinements

#### Clasifying double refinements

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _X_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert (not b))
(assert (and (= _X_6 a_1) (= _X_6_length a_1_length)))
(assert (not (_mu_f1_res _X_6 k a_1 _X_6_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _X_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert (not b))
(assert (and (= _X_6 a_1) (= _X_6_length a_1_length)))
(assert (not (_mu_f1_res _X_6 k a_1 _X_6_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE

Trying new superset.

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _X_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert (not b))
(assert (and (= _X_6 a_1) (= _X_6_length a_1_length)))
(assert (not (_mu_f1_res _X_6 k a_1 _X_6_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE

Trying new superset.

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _X_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert (not b))
(assert (and (= _X_6 a_1) (= _X_6_length a_1_length)))
(assert (not (_mu_f1_res _X_6 k a_1 _X_6_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNKNOWN

  Weakening length refinement:

 ```lisp
(forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (= (_mu_f1_res nu k a nu_length a_length)
     (and (>= nu_length 0) (>= a_length 0) (<= k nu_length))))
```

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _X_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert (not b))
(assert (and (= _X_6 a_1) (= _X_6_length a_1_length)))
(assert (not (_mu_f1_res _X_6 k a_1 _X_6_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (= (_mu_f1_res nu k a nu_length a_length)
     (and (>= nu_length 0) (>= a_length 0) (<= k nu_length)))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE


## Step 8

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_17`: Precondition of parameter k in call to f1

```lisp
(declare-fun a_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _NU_12 () Int)
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(assert (>= a_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (= _NU_12 0))
(assert (not (_kappa_f1_k _NU_12 a a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
```

*Result:* UNSATISFIABLE



## Step 9

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_18`: Precondition of parameter a in call to f1

```lisp
(declare-fun a_length () Int)
(declare-fun _NU_13_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _NU_13 () (Array Int Int))
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(assert (>= a_length 0))
(assert (>= _NU_13_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (and (= _NU_13 a) (= _NU_13_length a_length)))
(assert (not (_mu_f1_a _NU_13 a 0 _NU_13_length a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 10

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu))) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_19`: The type of (@ f1 (the int 0) a) must match the type of the result #1 of insert-sort

```lisp
(declare-fun a_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(declare-fun _X_7 () (Array Int Int))
(declare-fun _mu_insert-sort_res ((Array Int Int) (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= _X_7_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_mu_f1_res _X_7 0 a _X_7_length a_length))
(assert (not (_mu_insert-sort_res _X_7 a _X_7_length a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_res nu a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNKNOWN

### Weakening `_mu_insert-sort_res`

#### Clasifying single refinements

#### Clasifying double refinements

```lisp
(declare-fun a_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(declare-fun _X_7 () (Array Int Int))
(declare-fun _mu_insert-sort_res ((Array Int Int) (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= _X_7_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_mu_f1_res _X_7 0 a _X_7_length a_length))
(assert (not (_mu_insert-sort_res _X_7 a _X_7_length a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0) (>= j 0) (< i nu_length) (< j nu_length))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_res nu a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun a_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(declare-fun _X_7 () (Array Int Int))
(declare-fun _mu_insert-sort_res ((Array Int Int) (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= _X_7_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_mu_f1_res _X_7 0 a _X_7_length a_length))
(assert (not (_mu_insert-sort_res _X_7 a _X_7_length a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_res nu a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE

Trying new superset.

```lisp
(declare-fun a_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(declare-fun _X_7 () (Array Int Int))
(declare-fun _mu_insert-sort_res ((Array Int Int) (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= _X_7_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_mu_f1_res _X_7 0 a _X_7_length a_length))
(assert (not (_mu_insert-sort_res _X_7 a _X_7_length a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_res nu a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE



## Step 11

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_1`: Precondition of insert-sort must imply the qualifier of its parameter a

```lisp
(declare-fun a_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(assert (>= a_length 0))
(assert (not (_mu_insert-sort_a a a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
```

*Result:* UNSATISFIABLE



## Step 12

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_14`: Precondition of parameter a in call to f1

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _NU_10_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_10 () (Array Int Int))
(declare-fun _mu_f1_a_clone_2
             ((Array Int Int) (Array Int Int) Int Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _NU_10_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (and (= _NU_10 ap) (= _NU_10_length ap_length)))
(assert (not (_mu_f1_a_clone_2 _NU_10 ap k1 _NU_10_length ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a_clone_2 nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 13

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_9`: Precondition of parameter k in call to insert

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _NU_5 () Int)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (= _NU_5 k))
(assert (not (<= 0 _NU_5)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 14

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_10`: Precondition of parameter a in call to insert

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _NU_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _NU_6 () (Array Int Int))
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _NU_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (and (= _NU_6 a_1) (= _NU_6_length a_1_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (< j k))
                 (<= (select _NU_6 i) (select _NU_6 j))))))
  (not (and (< k _NU_6_length) a!1))))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 15

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_13`: Precondition of parameter k in call to f1

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _NU_9 () Int)
(declare-fun _kappa_f1_k_clone_1 (Int (Array Int Int) Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (= _NU_9 k1))
(assert (not (_kappa_f1_k_clone_1 _NU_9 ap ap_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k_clone_1 nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
```

*Result:* UNSATISFIABLE



## Step 16

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_15`: The type of (@ f1 k1 ap) must match the type of the result #1 of f1

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun ap_length () Int)
(declare-fun _X_5_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun ap () (Array Int Int))
(declare-fun k1 () Int)
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(declare-fun _X_5 () (Array Int Int))
(declare-fun _mu_f1_res_clone_3
             ((Array Int Int) Int (Array Int Int) Int Int)
             Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= ap_length 0))
(assert (>= _X_5_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert b)
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (<= j k))
                 (<= (select ap i) (select ap j))))))
  (and a!1 (= ap_length a_1_length))))
(assert (= k1 (+ k 1)))
(assert (_mu_f1_res _X_5 k1 ap _X_5_length ap_length))
(assert (not (_mu_f1_res_clone_3 _X_5 k a_1 _X_5_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res_clone_3 nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 17

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_16`: The type of a_1 must match the type of the result #1 of f1

```lisp
(declare-fun a_length () Int)
(declare-fun a_1_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _kappa_f1_k (Int (Array Int Int) Int) Bool)
(declare-fun k () Int)
(declare-fun _mu_f1_a ((Array Int Int) (Array Int Int) Int Int Int) Bool)
(declare-fun a_1 () (Array Int Int))
(declare-fun la () Int)
(declare-fun b () Bool)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= a_1_length 0))
(assert (>= _X_6_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_kappa_f1_k k a a_length))
(assert (_mu_f1_a a_1 a k a_1_length a_length))
(assert (= la a_1_length))
(assert (= b (< k la)))
(assert (not b))
(assert (and (= _X_6 a_1) (= _X_6_length a_1_length)))
(assert (not (_mu_f1_res _X_6 k a_1 _X_6_length a_1_length)))
(assert (forall ((nu Int) (a (Array Int Int)) (a_length Int))
  (= (_kappa_f1_k nu a a_length) (and (>= a_length 0) (<= 0 nu)))))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (k Int)
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j)
                        (< j k))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_a nu a k nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
```

*Result:* UNSATISFIABLE



## Step 18

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_2`: The qualified type of the result of insert-sort must imply its postcondition

```lisp
(declare-fun a_length () Int)
(declare-fun res_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _mu_insert-sort_res ((Array Int Int) (Array Int Int) Int Int) Bool)
(declare-fun res () (Array Int Int))
(assert (>= a_length 0))
(assert (>= res_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_mu_insert-sort_res res a res_length a_length))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= 0 i) (<= i j) (< j res_length))
                 (<= (select res i) (select res j))))))
  (not a!1)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_res nu a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE



## Step 19

Starting from solution:

 * `_kappa_f1_k nu a`: `(@ <= (the int 0) nu)`
 * `_kappa_f1_k_clone_1 nu a`: `(@ <= (the int 0) nu)`

 * `_mu_insert-sort_a nu`: *true*
 * `_mu_f1_a nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_insert-sort_res nu a`: `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
 * `_mu_f1_a_clone_2 nu a k`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j) (@ < j k)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`
 * `_mu_f1_res_clone_3 nu k a`: conjunction of the following
    - `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ <= (the int 0) j) (@ < j (@ len nu)) (@ <= i j)) (@ <= (@ get-array nu i) (@ get-array nu j))))`
    - `(@ <= k (@ len nu))`

*Checking goal* `G_19`: The type of (@ f1 (the int 0) a) must match the type of the result #1 of insert-sort

```lisp
(declare-fun a_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_insert-sort_a ((Array Int Int) Int) Bool)
(declare-fun a () (Array Int Int))
(declare-fun _mu_f1_res ((Array Int Int) Int (Array Int Int) Int Int) Bool)
(declare-fun _X_7 () (Array Int Int))
(declare-fun _mu_insert-sort_res ((Array Int Int) (Array Int Int) Int Int) Bool)
(assert (>= a_length 0))
(assert (>= _X_7_length 0))
(assert (_mu_insert-sort_a a a_length))
(assert (_mu_f1_res _X_7 0 a _X_7_length a_length))
(assert (not (_mu_insert-sort_res _X_7 a _X_7_length a_length)))
(assert (forall ((nu (Array Int Int)) (nu_length Int))
  (= (_mu_insert-sort_a nu nu_length) (and (>= nu_length 0)))))
(assert (forall ((nu (Array Int Int))
         (k Int)
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_f1_res nu k a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1 (<= k nu_length))))))
(assert (forall ((nu (Array Int Int))
         (a (Array Int Int))
         (nu_length Int)
         (a_length Int))
  (let ((a!1 (forall ((i Int) (j Int))
               (=> (and (>= i 0)
                        (>= j 0)
                        (< i nu_length)
                        (< j nu_length)
                        (<= i j))
                   (and (<= (select nu i) (select nu j)))))))
    (= (_mu_insert-sort_res nu a nu_length a_length)
       (and (>= nu_length 0) (>= a_length 0) a!1)))))
```

*Result:* UNSATISFIABLE



