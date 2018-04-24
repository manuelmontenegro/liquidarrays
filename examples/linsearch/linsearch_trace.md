## Step 0

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) k))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) k))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) idx))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) idx))))`
    - `(@ <= x (@ len nu))`
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) idx))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) idx))))`
    - `(@ <= x (@ len nu))`
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_3`: The qualified type of the result of linsearch must imply its postcondition

```lisp
(declare-fun arr_length () Int)
(declare-fun dummy_length () Int)
(declare-fun x () Int)
(declare-fun idx () Int)
(declare-fun arr () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= dummy_length 0))
(assert (and (<= 0 idx)
     (<= idx arr_length)
     (=> (< idx arr_length) (= (select arr idx) x))))
(assert (forall ((i Int)) (=> (and (<= 0 i) (< i idx)) (not (= (select arr i) x)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i idx)) (not (= (select arr i) x))))))
(let ((a!2 (and (<= 0 idx)
                (<= idx arr_length)
                (=> (< idx arr_length) (= (select arr idx) x))
                a!1)))
  (not a!2))))
```

*Result:* UNSATISFIABLE



## Step 1

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) k))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) k))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) idx))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) idx))))`
    - `(@ <= x (@ len nu))`
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) idx))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) idx))))`
    - `(@ <= x (@ len nu))`
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_9`: Precondition of parameter i in call to get-array

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun _NU_6 () Int)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= _NU_6 k))
(assert (not (and (<= 0 _NU_6) (< _NU_6 arr_length))))
```

*Result:* UNSATISFIABLE



## Step 2

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) k))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) k))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) idx))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) idx))))`
    - `(@ <= x (@ len nu))`
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) idx))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) idx))))`
    - `(@ <= x (@ len nu))`
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_12`: The type of (tuple k arr) must match the type of the result #1 of linsearch-rec

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (not (_kappa_linsearch-rec_idx _X_5 x arr k arr_length)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (<= 0 nu)
                  (<= nu arr_length)
                  (=> (< nu arr_length) (= (select arr nu) x))
                  (=> (< nu arr_length) (= (select arr nu) k)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1))))
```

*Result:* SATISFIABLE

### Weakening `_kappa_linsearch-rec_idx`

  Trying: `(forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (= (_kappa_linsearch-rec_idx nu x arr k arr_length)
     (and (>= arr_length 0) (<= 0 nu))))`

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (not (_kappa_linsearch-rec_idx _X_5 x arr k arr_length)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (= (_kappa_linsearch-rec_idx nu x arr k arr_length)
     (and (>= arr_length 0) (<= 0 nu)))))
```

*Result:* UNSATISFIABLE

  Trying: `(forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (= (_kappa_linsearch-rec_idx nu x arr k arr_length)
     (and (>= arr_length 0) (<= nu arr_length))))`

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (not (_kappa_linsearch-rec_idx _X_5 x arr k arr_length)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (= (_kappa_linsearch-rec_idx nu x arr k arr_length)
     (and (>= arr_length 0) (<= nu arr_length)))))
```

*Result:* UNSATISFIABLE

  Trying: `(forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (=> (< nu arr_length) (= (select arr nu) x)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1)))`

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (not (_kappa_linsearch-rec_idx _X_5 x arr k arr_length)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (=> (< nu arr_length) (= (select arr nu) x)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1))))
```

*Result:* UNSATISFIABLE

  Trying: `(forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (=> (< nu arr_length) (= (select arr nu) k)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1)))`

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (not (_kappa_linsearch-rec_idx _X_5 x arr k arr_length)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (=> (< nu arr_length) (= (select arr nu) k)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1))))
```

*Result:* UNKNOWN



## Step 3

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) idx))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) idx))))`
    - `(@ <= x (@ len nu))`
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (not (@ = (@ get-array nu i) idx))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) x))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) k))))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len arr))) (not (@ = (@ get-array arr i) idx))))`
    - `(@ <= x (@ len nu))`
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_13`: The type of (tuple k arr) must match the type of the result #2 of linsearch-rec

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) x)))))
                 (=> (and (>= i 0) (< i nu_length)) a!1))))
        (a!2 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) k)))))
                 (=> (and (>= i 0) (< i nu_length)) a!1))))
        (a!3 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) idx)))))
                 (=> (and (>= i 0) (< i nu_length)) a!1))))
        (a!4 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) x)))))
                 (=> (and (>= i 0) (< i arr_length)) a!1))))
        (a!5 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) k)))))
                 (=> (and (>= i 0) (< i arr_length)) a!1))))
        (a!6 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) idx)))))
                 (=> (and (>= i 0) (< i arr_length)) a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0)
            (>= arr_length 0)
            a!1
            a!2
            a!3
            a!4
            a!5
            a!6
            (<= x nu_length)
            (<= k nu_length)
            (<= idx nu_length))))))
```

*Result:* UNKNOWN

### Weakening `_mu_linsearch-rec_dummy`

#### Clasifying single refinements

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) x)))))
                 (=> (and (>= i 0) (< i nu_length)) a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) k)))))
                 (=> (and (>= i 0) (< i nu_length)) a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) idx)))))
                 (=> (and (>= i 0) (< i nu_length)) a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) x)))))
                 (=> (and (>= i 0) (< i arr_length)) a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) k)))))
                 (=> (and (>= i 0) (< i arr_length)) a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) idx)))))
                 (=> (and (>= i 0) (< i arr_length)) a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) x)))))
                 (=> (and (>= i 0) (< i nu_length) (< i x) (< i k) (< i idx))
                     a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) k)))))
                 (=> (and (>= i 0) (< i nu_length) (< i x) (< i k) (< i idx))
                     a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select nu i) idx)))))
                 (=> (and (>= i 0) (< i nu_length) (< i x) (< i k) (< i idx))
                     a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) x)))))
                 (=> (and (>= i 0) (< i arr_length) (< i x) (< i k) (< i idx))
                     a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) k)))))
                 (=> (and (>= i 0) (< i arr_length) (< i x) (< i k) (< i idx))
                     a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (let ((a!1 (and (not (= (select arr i) idx)))))
                 (=> (and (>= i 0) (< i arr_length) (< i x) (< i k) (< i idx))
                     a!1)))))
    (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1)))))
```

*Result:* UNKNOWN

#### Clasifying double refinements

  Weakening length refinement:

 ```lisp
(forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0) (>= arr_length 0) (<= x nu_length))))
```

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0) (>= arr_length 0) (<= x nu_length)))))
```

*Result:* SATISFIABLE

    Result: SATISFIABLE
  Weakening length refinement:

 ```lisp
(forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0) (>= arr_length 0) (<= k nu_length))))
```

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0) (>= arr_length 0) (<= k nu_length)))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE
  Weakening length refinement:

 ```lisp
(forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0) (>= arr_length 0) (<= idx nu_length))))
```

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun _X_5 () Int)
(declare-fun _X_6 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_6_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert b2)
(assert (= _X_5 k))
(assert (and (= _X_6 arr) (= _X_6_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_6 x arr k _X_5 _X_6_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0) (>= arr_length 0) (<= idx nu_length)))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE


## Step 4

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_16`: Precondition of parameter k in call to linsearch-rec

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun k1 () Int)
(declare-fun _NU_14 () Int)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert (not b2))
(assert (= k1 (+ k 1)))
(assert (= _NU_14 k1))
(assert (not (and (<= 0 _NU_14) (<= _NU_14 arr_length))))
```

*Result:* UNSATISFIABLE



## Step 5

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_17`: The type of (@ linsearch-rec k1) must match the type of the result #1 of linsearch-rec

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun k1 () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(declare-fun _X_8 () Int)
(declare-fun _kappa_linsearch-rec_idx_clone_1
             (Int Int (Array Int Int) Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert (not b2))
(assert (= k1 (+ k 1)))
(assert (_kappa_linsearch-rec_idx _X_8 x arr k1 arr_length))
(assert (not (_kappa_linsearch-rec_idx_clone_1 _X_8 x arr k arr_length)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (<= 0 nu)
                  (<= nu arr_length)
                  (=> (< nu arr_length) (= (select arr nu) x)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1))))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (<= 0 nu)
                  (<= nu arr_length)
                  (=> (< nu arr_length) (= (select arr nu) x)))))
    (= (_kappa_linsearch-rec_idx_clone_1 nu x arr k arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 6

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_18`: The type of (@ linsearch-rec k1) must match the type of the result #2 of linsearch-rec

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_9_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun arr () (Array Int Int))
(declare-fun arr_k () Int)
(declare-fun x () Int)
(declare-fun b2 () Bool)
(declare-fun k1 () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(declare-fun _X_8 () Int)
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(declare-fun _X_9 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy_clone_2
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(assert (>= arr_length 0))
(assert (>= _X_9_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert b1)
(assert (= arr_k (select arr k)))
(assert (= b2 (= arr_k x)))
(assert (not b2))
(assert (= k1 (+ k 1)))
(assert (_kappa_linsearch-rec_idx _X_8 x arr k1 arr_length))
(assert (_mu_linsearch-rec_dummy _X_9 x arr k1 _X_8 _X_9_length arr_length))
(assert (not (_mu_linsearch-rec_dummy_clone_2 _X_9 x arr k _X_8 _X_9_length arr_length)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (<= 0 nu)
                  (<= nu arr_length)
                  (=> (< nu arr_length) (= (select arr nu) x)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1))))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0)
          (>= arr_length 0)
          (<= k nu_length)
          (<= idx nu_length)))))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy_clone_2 nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0)
          (>= arr_length 0)
          (<= k nu_length)
          (<= idx nu_length)))))
```

*Result:* UNSATISFIABLE



## Step 7

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_19`: The type of (tuple k arr) must match the type of the result #1 of linsearch-rec

```lisp
(declare-fun arr_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun _X_10 () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(declare-fun arr () (Array Int Int))
(declare-fun x () Int)
(assert (>= arr_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert (not b1))
(assert (= _X_10 k))
(assert (not (_kappa_linsearch-rec_idx _X_10 x arr k arr_length)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (<= 0 nu)
                  (<= nu arr_length)
                  (=> (< nu arr_length) (= (select arr nu) x)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 8

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_20`: The type of (tuple k arr) must match the type of the result #2 of linsearch-rec

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_11_length () Int)
(declare-fun k () Int)
(declare-fun larr () Int)
(declare-fun b1 () Bool)
(declare-fun _X_10 () Int)
(declare-fun arr () (Array Int Int))
(declare-fun _X_11 () (Array Int Int))
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(declare-fun x () Int)
(assert (>= arr_length 0))
(assert (>= _X_11_length 0))
(assert (and (<= 0 k) (<= k arr_length)))
(assert (= larr arr_length))
(assert (= b1 (< k larr)))
(assert (not b1))
(assert (= _X_10 k))
(assert (and (= _X_11 arr) (= _X_11_length arr_length)))
(assert (not (_mu_linsearch-rec_dummy _X_11 x arr k _X_10 _X_11_length arr_length)))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0)
          (>= arr_length 0)
          (<= k nu_length)
          (<= idx nu_length)))))
```

*Result:* UNSATISFIABLE



## Step 9

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_21`: Precondition of parameter k in call to linsearch-rec

```lisp
(declare-fun arr_length () Int)
(declare-fun _NU_17 () Int)
(assert (>= arr_length 0))
(assert (= _NU_17 0))
(assert (not (and (<= 0 _NU_17) (<= _NU_17 arr_length))))
```

*Result:* UNSATISFIABLE



## Step 10

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_22`: The type of (@ linsearch-rec (the int 0)) must match the type of the result #1 of linsearch

```lisp
(declare-fun arr_length () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(declare-fun arr () (Array Int Int))
(declare-fun x () Int)
(declare-fun _X_12 () Int)
(assert (>= arr_length 0))
(assert (_kappa_linsearch-rec_idx _X_12 x arr 0 arr_length))
(assert (let ((a!1 (and (<= 0 _X_12)
                (<= _X_12 arr_length)
                (=> (< _X_12 arr_length) (= (select arr _X_12) x)))))
  (not a!1)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (<= 0 nu)
                  (<= nu arr_length)
                  (=> (< nu arr_length) (= (select arr nu) x)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 11

Starting from solution:

 * `_kappa_linsearch-rec_idx nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`
 * `_kappa_linsearch-rec_idx_clone_1 nu x arr k`: `(@ <= (the int 0) nu)`, `(@ <= nu (@ len arr))`, `(-> (@ < nu (@ len arr)) (@ = (@ get-array arr nu) x))`

 * `_mu_linsearch-rec_dummy nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`
 * `_mu_linsearch-rec_dummy_clone_2 nu x arr k idx`: conjunction of the following
    - `(@ <= k (@ len nu))`
    - `(@ <= idx (@ len nu))`

*Checking goal* `G_23`: The type of (@ linsearch-rec (the int 0)) must match the type of the result #2 of linsearch

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_13_length () Int)
(declare-fun _kappa_linsearch-rec_idx (Int Int (Array Int Int) Int Int) Bool)
(declare-fun arr () (Array Int Int))
(declare-fun x () Int)
(declare-fun _X_12 () Int)
(declare-fun _mu_linsearch-rec_dummy
             ((Array Int Int) Int (Array Int Int) Int Int Int Int)
             Bool)
(declare-fun _X_13 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= _X_13_length 0))
(assert (_kappa_linsearch-rec_idx _X_12 x arr 0 arr_length))
(assert (_mu_linsearch-rec_dummy _X_13 x arr 0 _X_12 _X_13_length arr_length))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_12)) (not (= (select arr i) x))))))
  (not a!1)))
(assert (forall ((nu Int) (x Int) (arr (Array Int Int)) (k Int) (arr_length Int))
  (let ((a!1 (and (>= arr_length 0)
                  (<= 0 nu)
                  (<= nu arr_length)
                  (=> (< nu arr_length) (= (select arr nu) x)))))
    (= (_kappa_linsearch-rec_idx nu x arr k arr_length) a!1))))
(assert (forall ((nu (Array Int Int))
         (x Int)
         (arr (Array Int Int))
         (k Int)
         (idx Int)
         (nu_length Int)
         (arr_length Int))
  (= (_mu_linsearch-rec_dummy nu x arr k idx nu_length arr_length)
     (and (>= nu_length 0)
          (>= arr_length 0)
          (<= k nu_length)
          (<= idx nu_length)))))
```

*Result:* SATISFIABLE



