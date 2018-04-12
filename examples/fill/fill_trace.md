## Step 0

Starting from solution:

 * `_kappa_fill_elem nu xs`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_1`: Precondition of fill must imply the qualifier of its parameter xs

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_1 () Bool)
(assert (>= xs_length 0))
(assert (not _mu_fill_xs__INST_1))
(assert (= _mu_fill_xs__INST_1 and))
```

*Result:* UNSATISFIABLE



## Step 1

Starting from solution:

 * `_kappa_fill_elem nu xs`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_2`: Precondition of fill must imply the qualifier of its parameter elem

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_2 () Bool)
(declare-fun _kappa_fill_elem__INST_3 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_2)
(assert (not _kappa_fill_elem__INST_3))
(assert (= _mu_fill_xs__INST_2 and))
(assert (= _kappa_fill_elem__INST_3 (and (<= 0 elem) (< elem xs_length))))
```

*Result:* SATISFIABLE

### Weakening `_kappa_fill_elem__INST_3`

  Trying: `(= _kappa_fill_elem__INST_3 (and (<= 0 elem)))`

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_2 () Bool)
(declare-fun _kappa_fill_elem__INST_3 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_2)
(assert (not _kappa_fill_elem__INST_3))
(assert (= _mu_fill_xs__INST_2 and))
(assert (= _kappa_fill_elem__INST_3 (and (<= 0 elem))))
```

*Result:* SATISFIABLE

  Trying: `(= _kappa_fill_elem__INST_3 (and (< elem xs_length)))`

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_2 () Bool)
(declare-fun _kappa_fill_elem__INST_3 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_2)
(assert (not _kappa_fill_elem__INST_3))
(assert (= _mu_fill_xs__INST_2 and))
(assert (= _kappa_fill_elem__INST_3 (and (< elem xs_length))))
```

*Result:* SATISFIABLE



## Step 2

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_3`: The qualified type of the result of fill must imply its postcondition

```lisp
(declare-fun xs_length () Int)
(declare-fun res_length () Int)
(declare-fun _mu_fill_xs__INST_4 () Bool)
(declare-fun _kappa_fill_elem__INST_5 () Bool)
(declare-fun _mu_fill_res__INST_6 () Bool)
(declare-fun elem () Int)
(declare-fun res () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= res_length 0))
(assert _mu_fill_xs__INST_4)
(assert _kappa_fill_elem__INST_5)
(assert _mu_fill_res__INST_6)
(assert (let ((a!1 (forall ((i Int))
             (=> (<= 0 i) (=> (< i res_length) (= (select res i) elem))))))
  (not a!1)))
(assert (= _kappa_fill_elem__INST_5 and))
(assert (= _mu_fill_xs__INST_4 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i res_length)) (and (= (select res i) elem))))))
  (= _mu_fill_res__INST_6 (and a!1 (<= elem res_length)))))
```

*Result:* UNSATISFIABLE



## Step 3

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_11`: The type of xs_1 must match the type of the result #1 of filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun _X_3_length () Int)
(declare-fun _mu_fill_xs__INST_7 () Bool)
(declare-fun _kappa_fill_elem__INST_8 () Bool)
(declare-fun _kappa_filln_n__INST_9 () Bool)
(declare-fun _kappa_filln_elem__INST_10 () Bool)
(declare-fun _mu_filln_xs__INST_11 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun xs_1 () (Array Int Int))
(declare-fun _X_3 () (Array Int Int))
(declare-fun _mu_filln_res__INST_12 () Bool)
(declare-fun elem_1 () Int)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= _X_3_length 0))
(assert _mu_fill_xs__INST_7)
(assert _kappa_fill_elem__INST_8)
(assert _kappa_filln_n__INST_9)
(assert _kappa_filln_elem__INST_10)
(assert _mu_filln_xs__INST_11)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert b)
(assert (and (= _X_3 xs_1) (= _X_3_length xs_1_length)))
(assert (not _mu_filln_res__INST_12))
(assert (= _kappa_fill_elem__INST_8 and))
(assert (= _kappa_filln_n__INST_9 (and (<= 0 n) (< n xs_length))))
(assert (= _kappa_filln_elem__INST_10 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_7 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_11
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_3_length)) (and (= (select _X_3 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_3_length))
                 (and (= (select _X_3 i) elem_1))))))
  (= _mu_filln_res__INST_12
     (and a!1 a!2 (<= n _X_3_length) (<= elem_1 _X_3_length)))))
```

*Result:* UNSATISFIABLE



## Step 4

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_12`: Precondition of parameter i in call to set-array

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun _mu_fill_xs__INST_13 () Bool)
(declare-fun _kappa_fill_elem__INST_14 () Bool)
(declare-fun _kappa_filln_n__INST_15 () Bool)
(declare-fun _kappa_filln_elem__INST_16 () Bool)
(declare-fun _mu_filln_xs__INST_17 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun _NU_7 () Int)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert _mu_fill_xs__INST_13)
(assert _kappa_fill_elem__INST_14)
(assert _kappa_filln_n__INST_15)
(assert _kappa_filln_elem__INST_16)
(assert _mu_filln_xs__INST_17)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (= _NU_7 n))
(assert (not (and (<= 0 _NU_7) (< _NU_7 xs_1_length))))
(assert (= _kappa_fill_elem__INST_14 and))
(assert (= _kappa_filln_n__INST_15 (and (<= 0 n) (< n xs_length))))
(assert (= _kappa_filln_elem__INST_16 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_13 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_17
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
```

*Result:* UNSATISFIABLE



## Step 5

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_15`: Precondition of parameter n in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _mu_fill_xs__INST_18 () Bool)
(declare-fun _kappa_fill_elem__INST_19 () Bool)
(declare-fun _kappa_filln_n__INST_20 () Bool)
(declare-fun _kappa_filln_elem__INST_21 () Bool)
(declare-fun _mu_filln_xs__INST_22 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_11 () Int)
(declare-fun _kappa_filln_n_clone_1__INST_23 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert _mu_fill_xs__INST_18)
(assert _kappa_fill_elem__INST_19)
(assert _kappa_filln_n__INST_20)
(assert _kappa_filln_elem__INST_21)
(assert _mu_filln_xs__INST_22)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (= _NU_11 n1))
(assert (not _kappa_filln_n_clone_1__INST_23))
(assert (= _kappa_fill_elem__INST_19 and))
(assert (= _kappa_filln_n__INST_20 (and (<= 0 n) (< n xs_length))))
(assert (= _kappa_filln_elem__INST_21 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_18 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_22
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (= _kappa_filln_n_clone_1__INST_23 (and (<= 0 _NU_11) (< _NU_11 xsp_length))))
```

*Result:* SATISFIABLE

### Weakening `_kappa_filln_n_clone_1__INST_23`

  Trying: `(= _kappa_filln_n_clone_1__INST_23 (and (<= 0 _NU_11)))`

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _mu_fill_xs__INST_18 () Bool)
(declare-fun _kappa_fill_elem__INST_19 () Bool)
(declare-fun _kappa_filln_n__INST_20 () Bool)
(declare-fun _kappa_filln_elem__INST_21 () Bool)
(declare-fun _mu_filln_xs__INST_22 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_11 () Int)
(declare-fun _kappa_filln_n_clone_1__INST_23 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert _mu_fill_xs__INST_18)
(assert _kappa_fill_elem__INST_19)
(assert _kappa_filln_n__INST_20)
(assert _kappa_filln_elem__INST_21)
(assert _mu_filln_xs__INST_22)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (= _NU_11 n1))
(assert (not _kappa_filln_n_clone_1__INST_23))
(assert (= _kappa_fill_elem__INST_19 and))
(assert (= _kappa_filln_n__INST_20 (and (<= 0 n) (< n xs_length))))
(assert (= _kappa_filln_elem__INST_21 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_18 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_22
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (= _kappa_filln_n_clone_1__INST_23 (and (<= 0 _NU_11))))
```

*Result:* UNSATISFIABLE

  Trying: `(= _kappa_filln_n_clone_1__INST_23 (and (< _NU_11 xsp_length)))`

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _mu_fill_xs__INST_18 () Bool)
(declare-fun _kappa_fill_elem__INST_19 () Bool)
(declare-fun _kappa_filln_n__INST_20 () Bool)
(declare-fun _kappa_filln_elem__INST_21 () Bool)
(declare-fun _mu_filln_xs__INST_22 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_11 () Int)
(declare-fun _kappa_filln_n_clone_1__INST_23 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert _mu_fill_xs__INST_18)
(assert _kappa_fill_elem__INST_19)
(assert _kappa_filln_n__INST_20)
(assert _kappa_filln_elem__INST_21)
(assert _mu_filln_xs__INST_22)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (= _NU_11 n1))
(assert (not _kappa_filln_n_clone_1__INST_23))
(assert (= _kappa_fill_elem__INST_19 and))
(assert (= _kappa_filln_n__INST_20 (and (<= 0 n) (< n xs_length))))
(assert (= _kappa_filln_elem__INST_21 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_18 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_22
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (= _kappa_filln_n_clone_1__INST_23 (and (< _NU_11 xsp_length))))
```

*Result:* UNKNOWN



## Step 6

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_16`: Precondition of parameter elem in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _mu_fill_xs__INST_24 () Bool)
(declare-fun _kappa_fill_elem__INST_25 () Bool)
(declare-fun _kappa_filln_n__INST_26 () Bool)
(declare-fun _kappa_filln_elem__INST_27 () Bool)
(declare-fun _mu_filln_xs__INST_28 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_12 () Int)
(declare-fun _kappa_filln_elem_clone_2__INST_29 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert _mu_fill_xs__INST_24)
(assert _kappa_fill_elem__INST_25)
(assert _kappa_filln_n__INST_26)
(assert _kappa_filln_elem__INST_27)
(assert _mu_filln_xs__INST_28)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (= _NU_12 elem_1))
(assert (not _kappa_filln_elem_clone_2__INST_29))
(assert (= _kappa_fill_elem__INST_25 and))
(assert (= _kappa_filln_n__INST_26 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_27 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_24 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_28
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (= _kappa_filln_elem_clone_2__INST_29 (and (<= 0 _NU_12) (< _NU_12 xsp_length))))
```

*Result:* UNSATISFIABLE



## Step 7

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_17`: Precondition of parameter xs in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _NU_13_length () Int)
(declare-fun _mu_fill_xs__INST_30 () Bool)
(declare-fun _kappa_fill_elem__INST_31 () Bool)
(declare-fun _kappa_filln_n__INST_32 () Bool)
(declare-fun _kappa_filln_elem__INST_33 () Bool)
(declare-fun _mu_filln_xs__INST_34 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_13 () (Array Int Int))
(declare-fun _mu_filln_xs_clone_3__INST_35 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _NU_13_length 0))
(assert _mu_fill_xs__INST_30)
(assert _kappa_fill_elem__INST_31)
(assert _kappa_filln_n__INST_32)
(assert _kappa_filln_elem__INST_33)
(assert _mu_filln_xs__INST_34)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (and (= _NU_13 xsp) (= _NU_13_length xsp_length)))
(assert (not _mu_filln_xs_clone_3__INST_35))
(assert (= _kappa_fill_elem__INST_31 and))
(assert (= _kappa_filln_n__INST_32 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_33 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_30 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_34
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_13_length))
                 (and (= (select _NU_13 i) n1)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_13_length))
                 (and (= (select _NU_13 i) elem_1))))))
  (= _mu_filln_xs_clone_3__INST_35
     (and a!1 a!2 (<= n1 _NU_13_length) (<= elem_1 _NU_13_length)))))
```

*Result:* SATISFIABLE

### Weakening `_mu_filln_xs_clone_3`

#### Clasifying single refinements

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _NU_13_length () Int)
(declare-fun _mu_fill_xs__INST_30 () Bool)
(declare-fun _kappa_fill_elem__INST_31 () Bool)
(declare-fun _kappa_filln_n__INST_32 () Bool)
(declare-fun _kappa_filln_elem__INST_33 () Bool)
(declare-fun _mu_filln_xs__INST_34 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_13 () (Array Int Int))
(declare-fun _mu_filln_xs_clone_3__INST_35 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _NU_13_length 0))
(assert _mu_fill_xs__INST_30)
(assert _kappa_fill_elem__INST_31)
(assert _kappa_filln_n__INST_32)
(assert _kappa_filln_elem__INST_33)
(assert _mu_filln_xs__INST_34)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (and (= _NU_13 xsp) (= _NU_13_length xsp_length)))
(assert (not _mu_filln_xs_clone_3__INST_35))
(assert (= _kappa_fill_elem__INST_31 and))
(assert (= _kappa_filln_n__INST_32 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_33 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_30 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_34
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_13_length))
                 (and (= (select _NU_13 i) n1))))))
  (= _mu_filln_xs_clone_3__INST_35 (and a!1))))
```

*Result:* SATISFIABLE

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _NU_13_length () Int)
(declare-fun _mu_fill_xs__INST_30 () Bool)
(declare-fun _kappa_fill_elem__INST_31 () Bool)
(declare-fun _kappa_filln_n__INST_32 () Bool)
(declare-fun _kappa_filln_elem__INST_33 () Bool)
(declare-fun _mu_filln_xs__INST_34 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_13 () (Array Int Int))
(declare-fun _mu_filln_xs_clone_3__INST_35 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _NU_13_length 0))
(assert _mu_fill_xs__INST_30)
(assert _kappa_fill_elem__INST_31)
(assert _kappa_filln_n__INST_32)
(assert _kappa_filln_elem__INST_33)
(assert _mu_filln_xs__INST_34)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (and (= _NU_13 xsp) (= _NU_13_length xsp_length)))
(assert (not _mu_filln_xs_clone_3__INST_35))
(assert (= _kappa_fill_elem__INST_31 and))
(assert (= _kappa_filln_n__INST_32 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_33 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_30 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_34
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_13_length))
                 (and (= (select _NU_13 i) elem_1))))))
  (= _mu_filln_xs_clone_3__INST_35 (and a!1))))
```

*Result:* UNSATISFIABLE

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _NU_13_length () Int)
(declare-fun _mu_fill_xs__INST_30 () Bool)
(declare-fun _kappa_fill_elem__INST_31 () Bool)
(declare-fun _kappa_filln_n__INST_32 () Bool)
(declare-fun _kappa_filln_elem__INST_33 () Bool)
(declare-fun _mu_filln_xs__INST_34 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_13 () (Array Int Int))
(declare-fun _mu_filln_xs_clone_3__INST_35 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _NU_13_length 0))
(assert _mu_fill_xs__INST_30)
(assert _kappa_fill_elem__INST_31)
(assert _kappa_filln_n__INST_32)
(assert _kappa_filln_elem__INST_33)
(assert _mu_filln_xs__INST_34)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (and (= _NU_13 xsp) (= _NU_13_length xsp_length)))
(assert (not _mu_filln_xs_clone_3__INST_35))
(assert (= _kappa_fill_elem__INST_31 and))
(assert (= _kappa_filln_n__INST_32 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_33 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_30 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_34
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_13_length) (< i n1) (< i elem_1))
                 (and (= (select _NU_13 i) n1))))))
  (= _mu_filln_xs_clone_3__INST_35 (and a!1))))
```

*Result:* UNKNOWN

#### Clasifying double refinements

  Weakening length refinement:

 ```lisp
(= _mu_filln_xs_clone_3__INST_35 (and (<= n1 _NU_13_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _NU_13_length () Int)
(declare-fun _mu_fill_xs__INST_30 () Bool)
(declare-fun _kappa_fill_elem__INST_31 () Bool)
(declare-fun _kappa_filln_n__INST_32 () Bool)
(declare-fun _kappa_filln_elem__INST_33 () Bool)
(declare-fun _mu_filln_xs__INST_34 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_13 () (Array Int Int))
(declare-fun _mu_filln_xs_clone_3__INST_35 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _NU_13_length 0))
(assert _mu_fill_xs__INST_30)
(assert _kappa_fill_elem__INST_31)
(assert _kappa_filln_n__INST_32)
(assert _kappa_filln_elem__INST_33)
(assert _mu_filln_xs__INST_34)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (and (= _NU_13 xsp) (= _NU_13_length xsp_length)))
(assert (not _mu_filln_xs_clone_3__INST_35))
(assert (= _kappa_fill_elem__INST_31 and))
(assert (= _kappa_filln_n__INST_32 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_33 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_30 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_34
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (= _mu_filln_xs_clone_3__INST_35 (and (<= n1 _NU_13_length))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE
  Weakening length refinement:

 ```lisp
(= _mu_filln_xs_clone_3__INST_35 (and (<= elem_1 _NU_13_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _NU_13_length () Int)
(declare-fun _mu_fill_xs__INST_30 () Bool)
(declare-fun _kappa_fill_elem__INST_31 () Bool)
(declare-fun _kappa_filln_n__INST_32 () Bool)
(declare-fun _kappa_filln_elem__INST_33 () Bool)
(declare-fun _mu_filln_xs__INST_34 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_13 () (Array Int Int))
(declare-fun _mu_filln_xs_clone_3__INST_35 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _NU_13_length 0))
(assert _mu_fill_xs__INST_30)
(assert _kappa_fill_elem__INST_31)
(assert _kappa_filln_n__INST_32)
(assert _kappa_filln_elem__INST_33)
(assert _mu_filln_xs__INST_34)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (and (= _NU_13 xsp) (= _NU_13_length xsp_length)))
(assert (not _mu_filln_xs_clone_3__INST_35))
(assert (= _kappa_fill_elem__INST_31 and))
(assert (= _kappa_filln_n__INST_32 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_33 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_30 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length)) (and (= (select xs_1 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_34
     (and a!1 a!2 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (= _mu_filln_xs_clone_3__INST_35 (and (<= elem_1 _NU_13_length))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE


## Step 8

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) n)))`
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_18`: The type of (@ filln n1 elem_1 xsp) must match the type of the result #1 of filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_fill_xs__INST_36 () Bool)
(declare-fun _kappa_fill_elem__INST_37 () Bool)
(declare-fun _kappa_filln_n__INST_38 () Bool)
(declare-fun _kappa_filln_elem__INST_39 () Bool)
(declare-fun _mu_filln_xs__INST_40 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _mu_filln_res__INST_41 () Bool)
(declare-fun _mu_filln_res_clone_4__INST_42 () Bool)
(declare-fun _X_6 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _X_6_length 0))
(assert _mu_fill_xs__INST_36)
(assert _kappa_fill_elem__INST_37)
(assert _kappa_filln_n__INST_38)
(assert _kappa_filln_elem__INST_39)
(assert _mu_filln_xs__INST_40)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert _mu_filln_res__INST_41)
(assert (not _mu_filln_res_clone_4__INST_42))
(assert (= _kappa_fill_elem__INST_37 and))
(assert (= _kappa_filln_n__INST_38 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_39 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_36 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_40 (and a!1 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length)) (and (= (select _X_6 i) n1)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res__INST_41
     (and a!1 a!2 (<= n1 _X_6_length) (<= elem_1 _X_6_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length)) (and (= (select _X_6 i) n)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res_clone_4__INST_42
     (and a!1 a!2 (<= n _X_6_length) (<= elem_1 _X_6_length)))))
```

*Result:* SATISFIABLE

### Weakening `_mu_filln_res_clone_4`

#### Clasifying single refinements

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_fill_xs__INST_36 () Bool)
(declare-fun _kappa_fill_elem__INST_37 () Bool)
(declare-fun _kappa_filln_n__INST_38 () Bool)
(declare-fun _kappa_filln_elem__INST_39 () Bool)
(declare-fun _mu_filln_xs__INST_40 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _mu_filln_res__INST_41 () Bool)
(declare-fun _mu_filln_res_clone_4__INST_42 () Bool)
(declare-fun _X_6 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _X_6_length 0))
(assert _mu_fill_xs__INST_36)
(assert _kappa_fill_elem__INST_37)
(assert _kappa_filln_n__INST_38)
(assert _kappa_filln_elem__INST_39)
(assert _mu_filln_xs__INST_40)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert _mu_filln_res__INST_41)
(assert (not _mu_filln_res_clone_4__INST_42))
(assert (= _kappa_fill_elem__INST_37 and))
(assert (= _kappa_filln_n__INST_38 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_39 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_36 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_40 (and a!1 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length)) (and (= (select _X_6 i) n1)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res__INST_41
     (and a!1 a!2 (<= n1 _X_6_length) (<= elem_1 _X_6_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length)) (and (= (select _X_6 i) n))))))
  (= _mu_filln_res_clone_4__INST_42 (and a!1))))
```

*Result:* SATISFIABLE

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_fill_xs__INST_36 () Bool)
(declare-fun _kappa_fill_elem__INST_37 () Bool)
(declare-fun _kappa_filln_n__INST_38 () Bool)
(declare-fun _kappa_filln_elem__INST_39 () Bool)
(declare-fun _mu_filln_xs__INST_40 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _mu_filln_res__INST_41 () Bool)
(declare-fun _mu_filln_res_clone_4__INST_42 () Bool)
(declare-fun _X_6 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _X_6_length 0))
(assert _mu_fill_xs__INST_36)
(assert _kappa_fill_elem__INST_37)
(assert _kappa_filln_n__INST_38)
(assert _kappa_filln_elem__INST_39)
(assert _mu_filln_xs__INST_40)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert _mu_filln_res__INST_41)
(assert (not _mu_filln_res_clone_4__INST_42))
(assert (= _kappa_fill_elem__INST_37 and))
(assert (= _kappa_filln_n__INST_38 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_39 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_36 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_40 (and a!1 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length)) (and (= (select _X_6 i) n1)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res__INST_41
     (and a!1 a!2 (<= n1 _X_6_length) (<= elem_1 _X_6_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res_clone_4__INST_42 (and a!1))))
```

*Result:* UNSATISFIABLE

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_fill_xs__INST_36 () Bool)
(declare-fun _kappa_fill_elem__INST_37 () Bool)
(declare-fun _kappa_filln_n__INST_38 () Bool)
(declare-fun _kappa_filln_elem__INST_39 () Bool)
(declare-fun _mu_filln_xs__INST_40 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _mu_filln_res__INST_41 () Bool)
(declare-fun _mu_filln_res_clone_4__INST_42 () Bool)
(declare-fun _X_6 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _X_6_length 0))
(assert _mu_fill_xs__INST_36)
(assert _kappa_fill_elem__INST_37)
(assert _kappa_filln_n__INST_38)
(assert _kappa_filln_elem__INST_39)
(assert _mu_filln_xs__INST_40)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert _mu_filln_res__INST_41)
(assert (not _mu_filln_res_clone_4__INST_42))
(assert (= _kappa_fill_elem__INST_37 and))
(assert (= _kappa_filln_n__INST_38 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_39 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_36 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_40 (and a!1 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length)) (and (= (select _X_6 i) n1)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res__INST_41
     (and a!1 a!2 (<= n1 _X_6_length) (<= elem_1 _X_6_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length) (< i n) (< i elem_1))
                 (and (= (select _X_6 i) n))))))
  (= _mu_filln_res_clone_4__INST_42 (and a!1))))
```

*Result:* SATISFIABLE

#### Clasifying double refinements

  Weakening length refinement:

 ```lisp
(= _mu_filln_res_clone_4__INST_42 (and (<= n _X_6_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_fill_xs__INST_36 () Bool)
(declare-fun _kappa_fill_elem__INST_37 () Bool)
(declare-fun _kappa_filln_n__INST_38 () Bool)
(declare-fun _kappa_filln_elem__INST_39 () Bool)
(declare-fun _mu_filln_xs__INST_40 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _mu_filln_res__INST_41 () Bool)
(declare-fun _mu_filln_res_clone_4__INST_42 () Bool)
(declare-fun _X_6 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _X_6_length 0))
(assert _mu_fill_xs__INST_36)
(assert _kappa_fill_elem__INST_37)
(assert _kappa_filln_n__INST_38)
(assert _kappa_filln_elem__INST_39)
(assert _mu_filln_xs__INST_40)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert _mu_filln_res__INST_41)
(assert (not _mu_filln_res_clone_4__INST_42))
(assert (= _kappa_fill_elem__INST_37 and))
(assert (= _kappa_filln_n__INST_38 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_39 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_36 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_40 (and a!1 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length)) (and (= (select _X_6 i) n1)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res__INST_41
     (and a!1 a!2 (<= n1 _X_6_length) (<= elem_1 _X_6_length)))))
(assert (= _mu_filln_res_clone_4__INST_42 (and (<= n _X_6_length))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE
  Weakening length refinement:

 ```lisp
(= _mu_filln_res_clone_4__INST_42 (and (<= elem_1 _X_6_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_fill_xs__INST_36 () Bool)
(declare-fun _kappa_fill_elem__INST_37 () Bool)
(declare-fun _kappa_filln_n__INST_38 () Bool)
(declare-fun _kappa_filln_elem__INST_39 () Bool)
(declare-fun _mu_filln_xs__INST_40 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _mu_filln_res__INST_41 () Bool)
(declare-fun _mu_filln_res_clone_4__INST_42 () Bool)
(declare-fun _X_6 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _X_6_length 0))
(assert _mu_fill_xs__INST_36)
(assert _kappa_fill_elem__INST_37)
(assert _kappa_filln_n__INST_38)
(assert _kappa_filln_elem__INST_39)
(assert _mu_filln_xs__INST_40)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert _mu_filln_res__INST_41)
(assert (not _mu_filln_res_clone_4__INST_42))
(assert (= _kappa_fill_elem__INST_37 and))
(assert (= _kappa_filln_n__INST_38 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_39 (and (<= 0 elem_1) (< elem_1 xs_length))))
(assert (= _mu_fill_xs__INST_36 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_40 (and a!1 (<= n xs_1_length) (<= elem_1 xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length)) (and (= (select _X_6 i) n1)))))
      (a!2 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res__INST_41
     (and a!1 a!2 (<= n1 _X_6_length) (<= elem_1 _X_6_length)))))
(assert (= _mu_filln_res_clone_4__INST_42 (and (<= elem_1 _X_6_length))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE


## Step 9

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_19`: Precondition of parameter n in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_43 () Bool)
(declare-fun _kappa_fill_elem__INST_44 () Bool)
(declare-fun _NU_14 () Int)
(declare-fun _kappa_filln_n__INST_45 () Bool)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_43)
(assert _kappa_fill_elem__INST_44)
(assert (= _NU_14 0))
(assert (not _kappa_filln_n__INST_45))
(assert (= _kappa_fill_elem__INST_44 and))
(assert (= _mu_fill_xs__INST_43 and))
(assert (= _kappa_filln_n__INST_45 (and (<= 0 _NU_14))))
```

*Result:* UNSATISFIABLE



## Step 10

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: `(@ <= (the int 0) nu)`, `(@ < nu (@ len xs))`

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_20`: Precondition of parameter elem in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_46 () Bool)
(declare-fun _kappa_fill_elem__INST_47 () Bool)
(declare-fun elem () Int)
(declare-fun _NU_15 () Int)
(declare-fun _kappa_filln_elem__INST_48 () Bool)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_46)
(assert _kappa_fill_elem__INST_47)
(assert (= _NU_15 elem))
(assert (not _kappa_filln_elem__INST_48))
(assert (= _kappa_fill_elem__INST_47 and))
(assert (= _mu_fill_xs__INST_46 and))
(assert (= _kappa_filln_elem__INST_48 (and (<= 0 _NU_15) (< _NU_15 xs_length))))
```

*Result:* SATISFIABLE

### Weakening `_kappa_filln_elem__INST_48`

  Trying: `(= _kappa_filln_elem__INST_48 (and (<= 0 _NU_15)))`

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_46 () Bool)
(declare-fun _kappa_fill_elem__INST_47 () Bool)
(declare-fun elem () Int)
(declare-fun _NU_15 () Int)
(declare-fun _kappa_filln_elem__INST_48 () Bool)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_46)
(assert _kappa_fill_elem__INST_47)
(assert (= _NU_15 elem))
(assert (not _kappa_filln_elem__INST_48))
(assert (= _kappa_fill_elem__INST_47 and))
(assert (= _mu_fill_xs__INST_46 and))
(assert (= _kappa_filln_elem__INST_48 (and (<= 0 _NU_15))))
```

*Result:* SATISFIABLE

  Trying: `(= _kappa_filln_elem__INST_48 (and (< _NU_15 xs_length)))`

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_46 () Bool)
(declare-fun _kappa_fill_elem__INST_47 () Bool)
(declare-fun elem () Int)
(declare-fun _NU_15 () Int)
(declare-fun _kappa_filln_elem__INST_48 () Bool)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_46)
(assert _kappa_fill_elem__INST_47)
(assert (= _NU_15 elem))
(assert (not _kappa_filln_elem__INST_48))
(assert (= _kappa_fill_elem__INST_47 and))
(assert (= _mu_fill_xs__INST_46 and))
(assert (= _kappa_filln_elem__INST_48 (and (< _NU_15 xs_length))))
```

*Result:* SATISFIABLE



## Step 11

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_21`: Precondition of parameter xs in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun _NU_16_length () Int)
(declare-fun _mu_fill_xs__INST_49 () Bool)
(declare-fun _kappa_fill_elem__INST_50 () Bool)
(declare-fun xs () (Array Int Int))
(declare-fun _NU_16 () (Array Int Int))
(declare-fun _mu_filln_xs__INST_51 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert (>= _NU_16_length 0))
(assert _mu_fill_xs__INST_49)
(assert _kappa_fill_elem__INST_50)
(assert (and (= _NU_16 xs) (= _NU_16_length xs_length)))
(assert (not _mu_filln_xs__INST_51))
(assert (= _kappa_fill_elem__INST_50 and))
(assert (= _mu_fill_xs__INST_49 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_16_length))
                 (and (= (select _NU_16 i) elem))))))
  (= _mu_filln_xs__INST_51
     (and a!1 (<= 0 _NU_16_length) (<= elem _NU_16_length)))))
```

*Result:* SATISFIABLE

### Weakening `_mu_filln_xs`

#### Clasifying single refinements

```lisp
(declare-fun xs_length () Int)
(declare-fun _NU_16_length () Int)
(declare-fun _mu_fill_xs__INST_49 () Bool)
(declare-fun _kappa_fill_elem__INST_50 () Bool)
(declare-fun xs () (Array Int Int))
(declare-fun _NU_16 () (Array Int Int))
(declare-fun _mu_filln_xs__INST_51 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert (>= _NU_16_length 0))
(assert _mu_fill_xs__INST_49)
(assert _kappa_fill_elem__INST_50)
(assert (and (= _NU_16 xs) (= _NU_16_length xs_length)))
(assert (not _mu_filln_xs__INST_51))
(assert (= _kappa_fill_elem__INST_50 and))
(assert (= _mu_fill_xs__INST_49 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_16_length))
                 (and (= (select _NU_16 i) elem))))))
  (= _mu_filln_xs__INST_51 (and a!1))))
```

*Result:* SATISFIABLE

#### Weakening rejected refinement

Trying strongest mapping

```lisp
(declare-fun xs_length () Int)
(declare-fun _NU_16_length () Int)
(declare-fun _mu_fill_xs__INST_49 () Bool)
(declare-fun _kappa_fill_elem__INST_50 () Bool)
(declare-fun xs () (Array Int Int))
(declare-fun _NU_16 () (Array Int Int))
(declare-fun _mu_filln_xs__INST_51 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert (>= _NU_16_length 0))
(assert _mu_fill_xs__INST_49)
(assert _kappa_fill_elem__INST_50)
(assert (and (= _NU_16 xs) (= _NU_16_length xs_length)))
(assert (not _mu_filln_xs__INST_51))
(assert (= _kappa_fill_elem__INST_50 and))
(assert (= _mu_fill_xs__INST_49 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_16_length) (< i 0) (< i elem))
                 (and (= (select _NU_16 i) elem))))))
  (= _mu_filln_xs__INST_51 (and a!1))))
```

*Result:* UNSATISFIABLE

Trying new superset.

```lisp
(declare-fun xs_length () Int)
(declare-fun _NU_16_length () Int)
(declare-fun _mu_fill_xs__INST_49 () Bool)
(declare-fun _kappa_fill_elem__INST_50 () Bool)
(declare-fun xs () (Array Int Int))
(declare-fun _NU_16 () (Array Int Int))
(declare-fun _mu_filln_xs__INST_51 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert (>= _NU_16_length 0))
(assert _mu_fill_xs__INST_49)
(assert _kappa_fill_elem__INST_50)
(assert (and (= _NU_16 xs) (= _NU_16_length xs_length)))
(assert (not _mu_filln_xs__INST_51))
(assert (= _kappa_fill_elem__INST_50 and))
(assert (= _mu_fill_xs__INST_49 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_16_length) (< i 0))
                 (and (= (select _NU_16 i) elem))))))
  (= _mu_filln_xs__INST_51 (and a!1))))
```

*Result:* UNSATISFIABLE

Trying new superset.

```lisp
(declare-fun xs_length () Int)
(declare-fun _NU_16_length () Int)
(declare-fun _mu_fill_xs__INST_49 () Bool)
(declare-fun _kappa_fill_elem__INST_50 () Bool)
(declare-fun xs () (Array Int Int))
(declare-fun _NU_16 () (Array Int Int))
(declare-fun _mu_filln_xs__INST_51 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert (>= _NU_16_length 0))
(assert _mu_fill_xs__INST_49)
(assert _kappa_fill_elem__INST_50)
(assert (and (= _NU_16 xs) (= _NU_16_length xs_length)))
(assert (not _mu_filln_xs__INST_51))
(assert (= _kappa_fill_elem__INST_50 and))
(assert (= _mu_fill_xs__INST_49 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_16_length) (< i elem))
                 (and (= (select _NU_16 i) elem))))))
  (= _mu_filln_xs__INST_51 (and a!1))))
```

*Result:* SATISFIABLE

#### Clasifying double refinements

  Weakening length refinement:

 ```lisp
(= _mu_filln_xs__INST_51 (and (<= 0 _NU_16_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun _NU_16_length () Int)
(declare-fun _mu_fill_xs__INST_49 () Bool)
(declare-fun _kappa_fill_elem__INST_50 () Bool)
(declare-fun xs () (Array Int Int))
(declare-fun _NU_16 () (Array Int Int))
(declare-fun _mu_filln_xs__INST_51 () Bool)
(assert (>= xs_length 0))
(assert (>= _NU_16_length 0))
(assert _mu_fill_xs__INST_49)
(assert _kappa_fill_elem__INST_50)
(assert (and (= _NU_16 xs) (= _NU_16_length xs_length)))
(assert (not _mu_filln_xs__INST_51))
(assert (= _kappa_fill_elem__INST_50 and))
(assert (= _mu_fill_xs__INST_49 and))
(assert (= _mu_filln_xs__INST_51 (and (<= 0 _NU_16_length))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE
  Weakening length refinement:

 ```lisp
(= _mu_filln_xs__INST_51 (and (<= elem _NU_16_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun _NU_16_length () Int)
(declare-fun _mu_fill_xs__INST_49 () Bool)
(declare-fun _kappa_fill_elem__INST_50 () Bool)
(declare-fun xs () (Array Int Int))
(declare-fun _NU_16 () (Array Int Int))
(declare-fun _mu_filln_xs__INST_51 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert (>= _NU_16_length 0))
(assert _mu_fill_xs__INST_49)
(assert _kappa_fill_elem__INST_50)
(assert (and (= _NU_16 xs) (= _NU_16_length xs_length)))
(assert (not _mu_filln_xs__INST_51))
(assert (= _kappa_fill_elem__INST_50 and))
(assert (= _mu_fill_xs__INST_49 and))
(assert (= _mu_filln_xs__INST_51 (and (<= elem _NU_16_length))))
```

*Result:* SATISFIABLE

    Result: SATISFIABLE


## Step 12

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_22`: The type of (@ filln (the int 0) elem xs) must match the type of the result #1 of fill

```lisp
(declare-fun xs_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_fill_xs__INST_52 () Bool)
(declare-fun _kappa_fill_elem__INST_53 () Bool)
(declare-fun _mu_filln_res__INST_54 () Bool)
(declare-fun _mu_fill_res__INST_55 () Bool)
(declare-fun elem () Int)
(declare-fun _X_7 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= _X_7_length 0))
(assert _mu_fill_xs__INST_52)
(assert _kappa_fill_elem__INST_53)
(assert _mu_filln_res__INST_54)
(assert (not _mu_fill_res__INST_55))
(assert (= _kappa_fill_elem__INST_53 and))
(assert (= _mu_fill_xs__INST_52 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_filln_res__INST_54 (and a!1 (<= 0 _X_7_length) (<= elem _X_7_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_fill_res__INST_55 (and a!1 (<= elem _X_7_length)))))
```

*Result:* UNSATISFIABLE



## Step 13

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_2`: Precondition of fill must imply the qualifier of its parameter elem

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_2 () Bool)
(declare-fun _kappa_fill_elem__INST_3 () Bool)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_2)
(assert (not _kappa_fill_elem__INST_3))
(assert (= _mu_fill_xs__INST_2 and))
(assert (= _kappa_fill_elem__INST_3 and))
```

*Result:* UNSATISFIABLE



## Step 14

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_15`: Precondition of parameter n in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _mu_fill_xs__INST_18 () Bool)
(declare-fun _kappa_fill_elem__INST_19 () Bool)
(declare-fun _kappa_filln_n__INST_20 () Bool)
(declare-fun _kappa_filln_elem__INST_21 () Bool)
(declare-fun _mu_filln_xs__INST_22 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_11 () Int)
(declare-fun _kappa_filln_n_clone_1__INST_23 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert _mu_fill_xs__INST_18)
(assert _kappa_fill_elem__INST_19)
(assert _kappa_filln_n__INST_20)
(assert _kappa_filln_elem__INST_21)
(assert _mu_filln_xs__INST_22)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (= _NU_11 n1))
(assert (not _kappa_filln_n_clone_1__INST_23))
(assert (= _kappa_fill_elem__INST_19 and))
(assert (= _kappa_filln_n__INST_20 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_21 and))
(assert (= _mu_fill_xs__INST_18 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_22 (and a!1 (<= n xs_1_length)))))
(assert (= _kappa_filln_n_clone_1__INST_23 (and (<= 0 _NU_11))))
```

*Result:* UNSATISFIABLE



## Step 15

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
    - `(@ <= elem (@ len nu))`

*Checking goal* `G_11`: The type of xs_1 must match the type of the result #1 of filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun _X_3_length () Int)
(declare-fun _mu_fill_xs__INST_7 () Bool)
(declare-fun _kappa_fill_elem__INST_8 () Bool)
(declare-fun _kappa_filln_n__INST_9 () Bool)
(declare-fun _kappa_filln_elem__INST_10 () Bool)
(declare-fun _mu_filln_xs__INST_11 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun xs_1 () (Array Int Int))
(declare-fun _X_3 () (Array Int Int))
(declare-fun _mu_filln_res__INST_12 () Bool)
(declare-fun elem_1 () Int)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= _X_3_length 0))
(assert _mu_fill_xs__INST_7)
(assert _kappa_fill_elem__INST_8)
(assert _kappa_filln_n__INST_9)
(assert _kappa_filln_elem__INST_10)
(assert _mu_filln_xs__INST_11)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert b)
(assert (and (= _X_3 xs_1) (= _X_3_length xs_1_length)))
(assert (not _mu_filln_res__INST_12))
(assert (= _kappa_fill_elem__INST_8 and))
(assert (= _kappa_filln_n__INST_9 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_10 and))
(assert (= _mu_fill_xs__INST_7 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_11 (and a!1 (<= n xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_3_length))
                 (and (= (select _X_3 i) elem_1))))))
  (= _mu_filln_res__INST_12
     (and a!1 (<= n _X_3_length) (<= elem_1 _X_3_length)))))
```

*Result:* SATISFIABLE

### Weakening `_mu_filln_res`

#### Clasifying single refinements

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun _X_3_length () Int)
(declare-fun _mu_fill_xs__INST_7 () Bool)
(declare-fun _kappa_fill_elem__INST_8 () Bool)
(declare-fun _kappa_filln_n__INST_9 () Bool)
(declare-fun _kappa_filln_elem__INST_10 () Bool)
(declare-fun _mu_filln_xs__INST_11 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun xs_1 () (Array Int Int))
(declare-fun _X_3 () (Array Int Int))
(declare-fun _mu_filln_res__INST_12 () Bool)
(declare-fun elem_1 () Int)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= _X_3_length 0))
(assert _mu_fill_xs__INST_7)
(assert _kappa_fill_elem__INST_8)
(assert _kappa_filln_n__INST_9)
(assert _kappa_filln_elem__INST_10)
(assert _mu_filln_xs__INST_11)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert b)
(assert (and (= _X_3 xs_1) (= _X_3_length xs_1_length)))
(assert (not _mu_filln_res__INST_12))
(assert (= _kappa_fill_elem__INST_8 and))
(assert (= _kappa_filln_n__INST_9 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_10 and))
(assert (= _mu_fill_xs__INST_7 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_11 (and a!1 (<= n xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_3_length))
                 (and (= (select _X_3 i) elem_1))))))
  (= _mu_filln_res__INST_12 (and a!1))))
```

*Result:* UNSATISFIABLE

#### Clasifying double refinements

  Weakening length refinement:

 ```lisp
(= _mu_filln_res__INST_12 (and (<= n _X_3_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun _X_3_length () Int)
(declare-fun _mu_fill_xs__INST_7 () Bool)
(declare-fun _kappa_fill_elem__INST_8 () Bool)
(declare-fun _kappa_filln_n__INST_9 () Bool)
(declare-fun _kappa_filln_elem__INST_10 () Bool)
(declare-fun _mu_filln_xs__INST_11 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun xs_1 () (Array Int Int))
(declare-fun _X_3 () (Array Int Int))
(declare-fun _mu_filln_res__INST_12 () Bool)
(declare-fun elem_1 () Int)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= _X_3_length 0))
(assert _mu_fill_xs__INST_7)
(assert _kappa_fill_elem__INST_8)
(assert _kappa_filln_n__INST_9)
(assert _kappa_filln_elem__INST_10)
(assert _mu_filln_xs__INST_11)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert b)
(assert (and (= _X_3 xs_1) (= _X_3_length xs_1_length)))
(assert (not _mu_filln_res__INST_12))
(assert (= _kappa_fill_elem__INST_8 and))
(assert (= _kappa_filln_n__INST_9 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_10 and))
(assert (= _mu_fill_xs__INST_7 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_11 (and a!1 (<= n xs_1_length)))))
(assert (= _mu_filln_res__INST_12 (and (<= n _X_3_length))))
```

*Result:* UNSATISFIABLE

    Result: UNSATISFIABLE
  Weakening length refinement:

 ```lisp
(= _mu_filln_res__INST_12 (and (<= elem_1 _X_3_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun _X_3_length () Int)
(declare-fun _mu_fill_xs__INST_7 () Bool)
(declare-fun _kappa_fill_elem__INST_8 () Bool)
(declare-fun _kappa_filln_n__INST_9 () Bool)
(declare-fun _kappa_filln_elem__INST_10 () Bool)
(declare-fun _mu_filln_xs__INST_11 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun xs_1 () (Array Int Int))
(declare-fun _X_3 () (Array Int Int))
(declare-fun _mu_filln_res__INST_12 () Bool)
(declare-fun elem_1 () Int)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= _X_3_length 0))
(assert _mu_fill_xs__INST_7)
(assert _kappa_fill_elem__INST_8)
(assert _kappa_filln_n__INST_9)
(assert _kappa_filln_elem__INST_10)
(assert _mu_filln_xs__INST_11)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert b)
(assert (and (= _X_3 xs_1) (= _X_3_length xs_1_length)))
(assert (not _mu_filln_res__INST_12))
(assert (= _kappa_fill_elem__INST_8 and))
(assert (= _kappa_filln_n__INST_9 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_10 and))
(assert (= _mu_fill_xs__INST_7 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_11 (and a!1 (<= n xs_1_length)))))
(assert (= _mu_filln_res__INST_12 (and (<= elem_1 _X_3_length))))
```

*Result:* SATISFIABLE

    Result: SATISFIABLE


## Step 16

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_12`: Precondition of parameter i in call to set-array

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun _mu_fill_xs__INST_13 () Bool)
(declare-fun _kappa_fill_elem__INST_14 () Bool)
(declare-fun _kappa_filln_n__INST_15 () Bool)
(declare-fun _kappa_filln_elem__INST_16 () Bool)
(declare-fun _mu_filln_xs__INST_17 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun _NU_7 () Int)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert _mu_fill_xs__INST_13)
(assert _kappa_fill_elem__INST_14)
(assert _kappa_filln_n__INST_15)
(assert _kappa_filln_elem__INST_16)
(assert _mu_filln_xs__INST_17)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (= _NU_7 n))
(assert (not (and (<= 0 _NU_7) (< _NU_7 xs_1_length))))
(assert (= _kappa_fill_elem__INST_14 and))
(assert (= _kappa_filln_n__INST_15 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_16 and))
(assert (= _mu_fill_xs__INST_13 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_17 (and a!1 (<= n xs_1_length)))))
```

*Result:* UNSATISFIABLE



## Step 17

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_17`: Precondition of parameter xs in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _NU_13_length () Int)
(declare-fun _mu_fill_xs__INST_30 () Bool)
(declare-fun _kappa_fill_elem__INST_31 () Bool)
(declare-fun _kappa_filln_n__INST_32 () Bool)
(declare-fun _kappa_filln_elem__INST_33 () Bool)
(declare-fun _mu_filln_xs__INST_34 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_13 () (Array Int Int))
(declare-fun _mu_filln_xs_clone_3__INST_35 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _NU_13_length 0))
(assert _mu_fill_xs__INST_30)
(assert _kappa_fill_elem__INST_31)
(assert _kappa_filln_n__INST_32)
(assert _kappa_filln_elem__INST_33)
(assert _mu_filln_xs__INST_34)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (and (= _NU_13 xsp) (= _NU_13_length xsp_length)))
(assert (not _mu_filln_xs_clone_3__INST_35))
(assert (= _kappa_fill_elem__INST_31 and))
(assert (= _kappa_filln_n__INST_32 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_33 and))
(assert (= _mu_fill_xs__INST_30 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_34 (and a!1 (<= n xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_13_length) (< i n1))
                 (and (= (select _NU_13 i) elem_1))))))
  (= _mu_filln_xs_clone_3__INST_35 (and a!1 (<= n1 _NU_13_length)))))
```

*Result:* UNSATISFIABLE



## Step 18

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_16`: Precondition of parameter elem in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _mu_fill_xs__INST_24 () Bool)
(declare-fun _kappa_fill_elem__INST_25 () Bool)
(declare-fun _kappa_filln_n__INST_26 () Bool)
(declare-fun _kappa_filln_elem__INST_27 () Bool)
(declare-fun _mu_filln_xs__INST_28 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _NU_12 () Int)
(declare-fun _kappa_filln_elem_clone_2__INST_29 () Bool)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert _mu_fill_xs__INST_24)
(assert _kappa_fill_elem__INST_25)
(assert _kappa_filln_n__INST_26)
(assert _kappa_filln_elem__INST_27)
(assert _mu_filln_xs__INST_28)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert (= _NU_12 elem_1))
(assert (not _kappa_filln_elem_clone_2__INST_29))
(assert (= _kappa_fill_elem__INST_25 and))
(assert (= _kappa_filln_n__INST_26 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_27 and))
(assert (= _mu_fill_xs__INST_24 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_28 (and a!1 (<= n xs_1_length)))))
(assert (= _kappa_filln_elem_clone_2__INST_29 and))
```

*Result:* UNSATISFIABLE



## Step 19

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_18`: The type of (@ filln n1 elem_1 xsp) must match the type of the result #1 of filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun xsp_length () Int)
(declare-fun _X_6_length () Int)
(declare-fun _mu_fill_xs__INST_36 () Bool)
(declare-fun _kappa_fill_elem__INST_37 () Bool)
(declare-fun _kappa_filln_n__INST_38 () Bool)
(declare-fun _kappa_filln_elem__INST_39 () Bool)
(declare-fun _mu_filln_xs__INST_40 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun elem_1 () Int)
(declare-fun xs_1 () (Array Int Int))
(declare-fun xsp () (Array Int Int))
(declare-fun n1 () Int)
(declare-fun _mu_filln_res__INST_41 () Bool)
(declare-fun _mu_filln_res_clone_4__INST_42 () Bool)
(declare-fun _X_6 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= xsp_length 0))
(assert (>= _X_6_length 0))
(assert _mu_fill_xs__INST_36)
(assert _kappa_fill_elem__INST_37)
(assert _kappa_filln_n__INST_38)
(assert _kappa_filln_elem__INST_39)
(assert _mu_filln_xs__INST_40)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert (not b))
(assert (and (= xsp (store xs_1 n elem_1)) (= xsp_length xs_1_length)))
(assert (= n1 (+ n 1)))
(assert _mu_filln_res__INST_41)
(assert (not _mu_filln_res_clone_4__INST_42))
(assert (= _kappa_fill_elem__INST_37 and))
(assert (= _kappa_filln_n__INST_38 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_39 and))
(assert (= _mu_fill_xs__INST_36 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_40 (and a!1 (<= n xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res__INST_41 (and a!1 (<= n1 _X_6_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_6_length))
                 (and (= (select _X_6 i) elem_1))))))
  (= _mu_filln_res_clone_4__INST_42 (and a!1 (<= n _X_6_length)))))
```

*Result:* UNSATISFIABLE



## Step 20

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_20`: Precondition of parameter elem in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun _mu_fill_xs__INST_46 () Bool)
(declare-fun _kappa_fill_elem__INST_47 () Bool)
(declare-fun elem () Int)
(declare-fun _NU_15 () Int)
(declare-fun _kappa_filln_elem__INST_48 () Bool)
(assert (>= xs_length 0))
(assert _mu_fill_xs__INST_46)
(assert _kappa_fill_elem__INST_47)
(assert (= _NU_15 elem))
(assert (not _kappa_filln_elem__INST_48))
(assert (= _kappa_fill_elem__INST_47 and))
(assert (= _mu_fill_xs__INST_46 and))
(assert (= _kappa_filln_elem__INST_48 and))
```

*Result:* UNSATISFIABLE



## Step 21

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_21`: Precondition of parameter xs in call to filln

```lisp
(declare-fun xs_length () Int)
(declare-fun _NU_16_length () Int)
(declare-fun _mu_fill_xs__INST_49 () Bool)
(declare-fun _kappa_fill_elem__INST_50 () Bool)
(declare-fun xs () (Array Int Int))
(declare-fun _NU_16 () (Array Int Int))
(declare-fun _mu_filln_xs__INST_51 () Bool)
(declare-fun elem () Int)
(assert (>= xs_length 0))
(assert (>= _NU_16_length 0))
(assert _mu_fill_xs__INST_49)
(assert _kappa_fill_elem__INST_50)
(assert (and (= _NU_16 xs) (= _NU_16_length xs_length)))
(assert (not _mu_filln_xs__INST_51))
(assert (= _kappa_fill_elem__INST_50 and))
(assert (= _mu_fill_xs__INST_49 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _NU_16_length) (< i 0))
                 (and (= (select _NU_16 i) elem))))))
  (= _mu_filln_xs__INST_51 (and a!1 (<= 0 _NU_16_length)))))
```

*Result:* UNSATISFIABLE



## Step 22

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_11`: The type of xs_1 must match the type of the result #1 of filln

```lisp
(declare-fun xs_length () Int)
(declare-fun xs_1_length () Int)
(declare-fun _X_3_length () Int)
(declare-fun _mu_fill_xs__INST_7 () Bool)
(declare-fun _kappa_fill_elem__INST_8 () Bool)
(declare-fun _kappa_filln_n__INST_9 () Bool)
(declare-fun _kappa_filln_elem__INST_10 () Bool)
(declare-fun _mu_filln_xs__INST_11 () Bool)
(declare-fun l () Int)
(declare-fun n () Int)
(declare-fun b () Bool)
(declare-fun xs_1 () (Array Int Int))
(declare-fun _X_3 () (Array Int Int))
(declare-fun _mu_filln_res__INST_12 () Bool)
(declare-fun elem_1 () Int)
(assert (>= xs_length 0))
(assert (>= xs_1_length 0))
(assert (>= _X_3_length 0))
(assert _mu_fill_xs__INST_7)
(assert _kappa_fill_elem__INST_8)
(assert _kappa_filln_n__INST_9)
(assert _kappa_filln_elem__INST_10)
(assert _mu_filln_xs__INST_11)
(assert (= l xs_1_length))
(assert (= b (>= n l)))
(assert b)
(assert (and (= _X_3 xs_1) (= _X_3_length xs_1_length)))
(assert (not _mu_filln_res__INST_12))
(assert (= _kappa_fill_elem__INST_8 and))
(assert (= _kappa_filln_n__INST_9 (and (<= 0 n))))
(assert (= _kappa_filln_elem__INST_10 and))
(assert (= _mu_fill_xs__INST_7 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i xs_1_length) (< i n))
                 (and (= (select xs_1 i) elem_1))))))
  (= _mu_filln_xs__INST_11 (and a!1 (<= n xs_1_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_3_length))
                 (and (= (select _X_3 i) elem_1))))))
  (= _mu_filln_res__INST_12 (and a!1 (<= n _X_3_length)))))
```

*Result:* UNSATISFIABLE



## Step 23

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= elem (@ len nu))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_22`: The type of (@ filln (the int 0) elem xs) must match the type of the result #1 of fill

```lisp
(declare-fun xs_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_fill_xs__INST_52 () Bool)
(declare-fun _kappa_fill_elem__INST_53 () Bool)
(declare-fun _mu_filln_res__INST_54 () Bool)
(declare-fun _mu_fill_res__INST_55 () Bool)
(declare-fun elem () Int)
(declare-fun _X_7 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= _X_7_length 0))
(assert _mu_fill_xs__INST_52)
(assert _kappa_fill_elem__INST_53)
(assert _mu_filln_res__INST_54)
(assert (not _mu_fill_res__INST_55))
(assert (= _kappa_fill_elem__INST_53 and))
(assert (= _mu_fill_xs__INST_52 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_filln_res__INST_54 (and a!1 (<= 0 _X_7_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_fill_res__INST_55 (and a!1 (<= elem _X_7_length)))))
```

*Result:* SATISFIABLE

### Weakening `_mu_fill_res`

#### Clasifying single refinements

```lisp
(declare-fun xs_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_fill_xs__INST_52 () Bool)
(declare-fun _kappa_fill_elem__INST_53 () Bool)
(declare-fun _mu_filln_res__INST_54 () Bool)
(declare-fun _mu_fill_res__INST_55 () Bool)
(declare-fun elem () Int)
(declare-fun _X_7 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= _X_7_length 0))
(assert _mu_fill_xs__INST_52)
(assert _kappa_fill_elem__INST_53)
(assert _mu_filln_res__INST_54)
(assert (not _mu_fill_res__INST_55))
(assert (= _kappa_fill_elem__INST_53 and))
(assert (= _mu_fill_xs__INST_52 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_filln_res__INST_54 (and a!1 (<= 0 _X_7_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_fill_res__INST_55 (and a!1))))
```

*Result:* UNSATISFIABLE

#### Clasifying double refinements

  Weakening length refinement:

 ```lisp
(= _mu_fill_res__INST_55 (and (<= elem _X_7_length)))
```

```lisp
(declare-fun xs_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_fill_xs__INST_52 () Bool)
(declare-fun _kappa_fill_elem__INST_53 () Bool)
(declare-fun _mu_filln_res__INST_54 () Bool)
(declare-fun _mu_fill_res__INST_55 () Bool)
(declare-fun elem () Int)
(declare-fun _X_7 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= _X_7_length 0))
(assert _mu_fill_xs__INST_52)
(assert _kappa_fill_elem__INST_53)
(assert _mu_filln_res__INST_54)
(assert (not _mu_fill_res__INST_55))
(assert (= _kappa_fill_elem__INST_53 and))
(assert (= _mu_fill_xs__INST_52 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_filln_res__INST_54 (and a!1 (<= 0 _X_7_length)))))
(assert (= _mu_fill_res__INST_55 (and (<= elem _X_7_length))))
```

*Result:* SATISFIABLE

    Result: SATISFIABLE


## Step 24

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_3`: The qualified type of the result of fill must imply its postcondition

```lisp
(declare-fun xs_length () Int)
(declare-fun res_length () Int)
(declare-fun _mu_fill_xs__INST_4 () Bool)
(declare-fun _kappa_fill_elem__INST_5 () Bool)
(declare-fun _mu_fill_res__INST_6 () Bool)
(declare-fun elem () Int)
(declare-fun res () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= res_length 0))
(assert _mu_fill_xs__INST_4)
(assert _kappa_fill_elem__INST_5)
(assert _mu_fill_res__INST_6)
(assert (let ((a!1 (forall ((i Int))
             (=> (<= 0 i) (=> (< i res_length) (= (select res i) elem))))))
  (not a!1)))
(assert (= _kappa_fill_elem__INST_5 and))
(assert (= _mu_fill_xs__INST_4 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i res_length)) (and (= (select res i) elem))))))
  (= _mu_fill_res__INST_6 (and a!1))))
```

*Result:* UNSATISFIABLE



## Step 25

Starting from solution:

 * `_kappa_fill_elem nu xs`: *true*
 * `_kappa_filln_n nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem nu xs elem n`: *true*
 * `_kappa_filln_n_clone_1 nu xs elem`: `(@ <= (the int 0) nu)`
 * `_kappa_filln_elem_clone_2 nu xs elem n`: *true*

 * `_mu_fill_xs nu`: *true*
 * `_mu_filln_xs nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_fill_res nu xs elem`: `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
 * `_mu_filln_xs_clone_3 nu xs n elem`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu)) (@ < i n)) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`
 * `_mu_filln_res_clone_4 nu n elem xs`: conjunction of the following
    - `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (@ len nu))) (@ = (@ get-array nu i) elem)))`
    - `(@ <= n (@ len nu))`

*Checking goal* `G_22`: The type of (@ filln (the int 0) elem xs) must match the type of the result #1 of fill

```lisp
(declare-fun xs_length () Int)
(declare-fun _X_7_length () Int)
(declare-fun _mu_fill_xs__INST_52 () Bool)
(declare-fun _kappa_fill_elem__INST_53 () Bool)
(declare-fun _mu_filln_res__INST_54 () Bool)
(declare-fun _mu_fill_res__INST_55 () Bool)
(declare-fun elem () Int)
(declare-fun _X_7 () (Array Int Int))
(assert (>= xs_length 0))
(assert (>= _X_7_length 0))
(assert _mu_fill_xs__INST_52)
(assert _kappa_fill_elem__INST_53)
(assert _mu_filln_res__INST_54)
(assert (not _mu_fill_res__INST_55))
(assert (= _kappa_fill_elem__INST_53 and))
(assert (= _mu_fill_xs__INST_52 and))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_filln_res__INST_54 (and a!1 (<= 0 _X_7_length)))))
(assert (let ((a!1 (forall ((i Int))
             (=> (and (<= 0 i) (< i _X_7_length))
                 (and (= (select _X_7 i) elem))))))
  (= _mu_fill_res__INST_55 (and a!1))))
```

