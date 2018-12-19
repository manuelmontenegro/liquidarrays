# Goals for `qsort.clir`

## Goal `G_1`

Precondition of qsort must imply the qualifier of its parameter a

**For all**:

  * `a` of type `(array int)`

**Prove:** `(@ _mu_qsort_a a)`

## Goal `G_2`

The qualified type of the result of qsort must imply its postcondition

**For all**:

  * `a` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _mu_qsort_res res a)`

**Prove:** `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len res))) (@ <= (@ get-array res i) (@ get-array res j))))`

## Goal `G_3`

Precondition of qsort-rec must imply the qualifier of its parameter c

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `false`

**Prove:** `(@ _kappa_qsort-rec_c c a)`

## Goal `G_4`

Precondition of qsort-rec must imply the qualifier of its parameter f

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `false`

**Prove:** `(@ _kappa_qsort-rec_f f a c)`

## Goal `G_5`

Precondition of qsort-rec must imply the qualifier of its parameter a_1

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `false`

**Prove:** `(@ _mu_qsort-rec_a a_1 a c f)`

## Goal `G_6`

The qualified type of the result of qsort-rec must imply its postcondition

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(@ _mu_qsort-rec_res res c f a_1)`

**Prove:** `true`

## Goal `G_7`

Precondition of parameter x_0 in call to <=

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `_NU_1` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(@ = _NU_1 f)`

**Prove:** `true`

## Goal `G_8`

Precondition of parameter x_1 in call to <=

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `_NU_2` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(@ = _NU_2 c)`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter c in call to partition

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `_NU_4` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(@ = _NU_4 c)`

**Prove:** `(@ <= (the int 0) _NU_4)`

## Goal `G_10`

Precondition of parameter f in call to partition

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `_NU_5` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(@ = _NU_5 f)`

**Prove:** `(@ <= c _NU_5)`

## Goal `G_11`

Precondition of parameter a in call to partition

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `_NU_6` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ =[] _NU_6 a_1) (@ = (@ len _NU_6) (@ len a_1)))`

**Prove:** `(and (@ <= f (@ len _NU_6)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ <= (@ get-array _NU_6 i) (@ get-array _NU_6 j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len _NU_6))) (@ <= (@ get-array _NU_6 i) (@ get-array _NU_6 j)))))`

## Goal `G_12`

Precondition of parameter c in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `_NU_7` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(@ = _NU_7 c)`

**Prove:** `(@ _kappa_qsort-rec_c _NU_7 resP)`

## Goal `G_13`

Precondition of parameter f in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `_NU_8` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(@ = _NU_8 p)`

**Prove:** `(@ _kappa_qsort-rec_f _NU_8 resP c)`

## Goal `G_14`

Precondition of parameter a in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `_NU_9` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(and (@ =[] _NU_9 resP) (@ = (@ len _NU_9) (@ len resP)))`

**Prove:** `(@ _mu_qsort-rec_a _NU_9 resP c p)`

## Goal `G_15`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `q1` of type `(array int)`
  * `_NU_10` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(@ _mu_qsort-rec_res q1 c p resP)`
  * `(@ = _NU_10 p)`

**Prove:** `true`

## Goal `G_16`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `q1` of type `(array int)`
  * `_NU_11` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(@ _mu_qsort-rec_res q1 c p resP)`
  * `(@ = _NU_11 (the int 1))`

**Prove:** `true`

## Goal `G_17`

Precondition of parameter c in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `q1` of type `(array int)`
  * `p1` of type `int`
  * `_NU_12` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(@ _mu_qsort-rec_res q1 c p resP)`
  * `(@ = p1 (@ + p (the int 1)))`
  * `(@ = _NU_12 p1)`

**Prove:** `(@ _kappa_qsort-rec_c _NU_12 q1)`

## Goal `G_18`

Precondition of parameter f in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `q1` of type `(array int)`
  * `p1` of type `int`
  * `_NU_13` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(@ _mu_qsort-rec_res q1 c p resP)`
  * `(@ = p1 (@ + p (the int 1)))`
  * `(@ = _NU_13 f)`

**Prove:** `(@ _kappa_qsort-rec_f _NU_13 q1 p1)`

## Goal `G_19`

Precondition of parameter a in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `q1` of type `(array int)`
  * `p1` of type `int`
  * `_NU_14` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(@ _mu_qsort-rec_res q1 c p resP)`
  * `(@ = p1 (@ + p (the int 1)))`
  * `(and (@ =[] _NU_14 q1) (@ = (@ len _NU_14) (@ len q1)))`

**Prove:** `(@ _mu_qsort-rec_a _NU_14 q1 p1 f)`

## Goal `G_20`

The type of q2 must match the type of the result #1 of qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `p` of type `int`
  * `resP` of type `(array int)`
  * `q1` of type `(array int)`
  * `p1` of type `int`
  * `q2` of type `(array int)`
  * `_X_7` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `(not b)`
  * `(and (@ <= c p) (@ < p f))`
  * `(and (@ = (@ len resP) (@ len a_1)) (forall ((i int)) (-> (and (@ <= c i) (@ < i p)) (@ <= (@ get-array resP i) (@ get-array resP p)))) (forall ((i int)) (-> (and (@ < p i) (@ < i f)) (@ <= (@ get-array resP p) (@ get-array resP i)))) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i c)) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int)) (-> (and (@ <= f i) (@ < i (@ len resP))) (@ = (@ get-array resP i) (@ get-array a_1 i)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i c) (@ <= c j) (@ < j f)) (@ = (@ get-array resP i) (@ get-array resP j)))) (forall ((i int) (j int)) (-> (and (@ <= c i) (@ < i f) (@ <= f j) (@ < j (@ len resP))) (@ = (@ get-array resP i) (@ get-array resP j)))))`
  * `(@ _mu_qsort-rec_res q1 c p resP)`
  * `(@ = p1 (@ + p (the int 1)))`
  * `(@ _mu_qsort-rec_res q2 p1 f q1)`
  * `(and (@ =[] _X_7 q2) (@ = (@ len _X_7) (@ len q2)))`

**Prove:** `(@ _mu_qsort-rec_res _X_7 c f a_1)`

## Goal `G_21`

The type of a_1 must match the type of the result #1 of qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `c` of type `int`
  * `f` of type `int`
  * `a_1` of type `(array int)`
  * `b` of type `bool`
  * `_X_8` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ _kappa_qsort-rec_c c a)`
  * `(@ _kappa_qsort-rec_f f a c)`
  * `(@ _mu_qsort-rec_a a_1 a c f)`
  * `(<-> b (@ <= f c))`
  * `b`
  * `(and (@ =[] _X_8 a_1) (@ = (@ len _X_8) (@ len a_1)))`

**Prove:** `(@ _mu_qsort-rec_res _X_8 c f a_1)`

## Goal `G_22`

Precondition of parameter x_0 in call to len

**For all**:

  * `a` of type `(array int)`
  * `_NU_17` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(and (@ =[] _NU_17 a) (@ = (@ len _NU_17) (@ len a)))`

**Prove:** `true`

## Goal `G_23`

Precondition of parameter c in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `la` of type `int`
  * `_NU_18` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ = la (@ len a))`
  * `(@ = _NU_18 (the int 0))`

**Prove:** `(@ _kappa_qsort-rec_c _NU_18 a)`

## Goal `G_24`

Precondition of parameter f in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `la` of type `int`
  * `_NU_19` of type `int`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ = la (@ len a))`
  * `(@ = _NU_19 la)`

**Prove:** `(@ _kappa_qsort-rec_f _NU_19 a (the int 0))`

## Goal `G_25`

Precondition of parameter a in call to qsort-rec

**For all**:

  * `a` of type `(array int)`
  * `la` of type `int`
  * `_NU_20` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ = la (@ len a))`
  * `(and (@ =[] _NU_20 a) (@ = (@ len _NU_20) (@ len a)))`

**Prove:** `(@ _mu_qsort-rec_a _NU_20 a (the int 0) la)`

## Goal `G_26`

The type of (@ qsort-rec (the int 0) la a) must match the type of the result #1 of qsort

**For all**:

  * `a` of type `(array int)`
  * `la` of type `int`
  * `_X_10` of type `(array int)`

**such that**:

  * `(@ _mu_qsort_a a)`
  * `(@ = la (@ len a))`
  * `(@ _mu_qsort-rec_res _X_10 (the int 0) la a)`

**Prove:** `(@ _mu_qsort_res _X_10 a)`

