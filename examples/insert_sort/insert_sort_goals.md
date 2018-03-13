# Goals for `insert_sort_typecheck.clir`

## Goal `G_1`

Precondition of insert-sort must imply the qualifier of its parameter a

**For all**:

  * `a` of type `(array int)`

**Prove:** `true`

## Goal `G_2`

The qualified type of the result of insert-sort must imply its postcondition

**For all**:

  * `a` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len res))) (@ <= (@ get-array res i) (@ get-array res j))))`

**Prove:** `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len res))) (@ <= (@ get-array res i) (@ get-array res j))))`

## Goal `G_3`

Precondition of f1 must imply the qualifier of its parameter k

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`

**such that**:

  * `false`

**Prove:** `(@ <= (the int 0) k)`

## Goal `G_4`

Precondition of f1 must imply the qualifier of its parameter a_1

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `false`

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`

## Goal `G_5`

The qualified type of the result of f1 must imply its postcondition

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len res))) (@ <= (@ get-array res i) (@ get-array res j)))) (@ = (@ len res) (@ len a_1)))`

**Prove:** `true`

## Goal `G_6`

Precondition of parameter x_0 in call to len

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `_NU_1` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(and (@ =[] _NU_1 a_1) (@ = (@ len _NU_1) (@ len a_1)))`

**Prove:** `true`

## Goal `G_7`

Precondition of parameter x_0 in call to <

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(@ = _NU_2 k)`

**Prove:** `true`

## Goal `G_8`

Precondition of parameter x_1 in call to <

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `_NU_3` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(@ = _NU_3 la)`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter k in call to insert

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `b` of type `bool`
  * `_NU_5` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(<-> b (@ < k la))`
  * `b`
  * `(@ = _NU_5 k)`

**Prove:** `(@ <= (the int 0) _NU_5)`

## Goal `G_10`

Precondition of parameter a in call to insert

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `b` of type `bool`
  * `_NU_6` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(<-> b (@ < k la))`
  * `b`
  * `(and (@ =[] _NU_6 a_1) (@ = (@ len _NU_6) (@ len a_1)))`

**Prove:** `(and (@ < k (@ len _NU_6)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array _NU_6 i) (@ get-array _NU_6 j)))))`

## Goal `G_11`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `b` of type `bool`
  * `ap` of type `(array int)`
  * `_NU_7` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(<-> b (@ < k la))`
  * `b`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array ap i) (@ get-array ap j)))) (@ = (@ len ap) (@ len a_1)))`
  * `(@ = _NU_7 k)`

**Prove:** `true`

## Goal `G_12`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `b` of type `bool`
  * `ap` of type `(array int)`
  * `_NU_8` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(<-> b (@ < k la))`
  * `b`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array ap i) (@ get-array ap j)))) (@ = (@ len ap) (@ len a_1)))`
  * `(@ = _NU_8 (the int 1))`

**Prove:** `true`

## Goal `G_13`

Precondition of parameter k in call to f1

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `b` of type `bool`
  * `ap` of type `(array int)`
  * `k1` of type `int`
  * `_NU_9` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(<-> b (@ < k la))`
  * `b`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array ap i) (@ get-array ap j)))) (@ = (@ len ap) (@ len a_1)))`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_9 k1)`

**Prove:** `(@ <= (the int 0) _NU_9)`

## Goal `G_14`

Precondition of parameter a in call to f1

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `b` of type `bool`
  * `ap` of type `(array int)`
  * `k1` of type `int`
  * `_NU_10` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(<-> b (@ < k la))`
  * `b`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array ap i) (@ get-array ap j)))) (@ = (@ len ap) (@ len a_1)))`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ =[] _NU_10 ap) (@ = (@ len _NU_10) (@ len ap)))`

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k1)) (@ <= (@ get-array _NU_10 i) (@ get-array _NU_10 j)))) (@ <= k1 (@ len ap)))`

## Goal `G_15`

The type of (@ f1 k1 ap) must match the type of the result #1 of f1

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `b` of type `bool`
  * `ap` of type `(array int)`
  * `k1` of type `int`
  * `_X_5` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(<-> b (@ < k la))`
  * `b`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array ap i) (@ get-array ap j)))) (@ = (@ len ap) (@ len a_1)))`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len _X_5))) (@ <= (@ get-array _X_5 i) (@ get-array _X_5 j)))) (@ = (@ len _X_5) (@ len ap)))`

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len _X_5))) (@ <= (@ get-array _X_5 i) (@ get-array _X_5 j)))) (@ = (@ len _X_5) (@ len a_1)))`

## Goal `G_16`

The type of a_1 must match the type of the result #1 of f1

**For all**:

  * `a` of type `(array int)`
  * `k` of type `int`
  * `a_1` of type `(array int)`
  * `la` of type `int`
  * `b` of type `bool`
  * `_X_6` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (@ <= k (@ len a)))`
  * `(@ = la (@ len a_1))`
  * `(<-> b (@ < k la))`
  * `(not b)`
  * `(and (@ =[] _X_6 a_1) (@ = (@ len _X_6) (@ len a_1)))`

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len _X_6))) (@ <= (@ get-array _X_6 i) (@ get-array _X_6 j)))) (@ = (@ len _X_6) (@ len a_1)))`

## Goal `G_17`

Precondition of parameter k in call to f1

**For all**:

  * `a` of type `(array int)`
  * `_NU_12` of type `int`

**such that**:

  * `(@ = _NU_12 (the int 0))`

**Prove:** `(@ <= (the int 0) _NU_12)`

## Goal `G_18`

Precondition of parameter a in call to f1

**For all**:

  * `a` of type `(array int)`
  * `_NU_13` of type `(array int)`

**such that**:

  * `(and (@ =[] _NU_13 a) (@ = (@ len _NU_13) (@ len a)))`

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (the int 0))) (@ <= (@ get-array _NU_13 i) (@ get-array _NU_13 j)))) (@ <= (the int 0) (@ len a)))`

## Goal `G_19`

The type of (@ f1 (the int 0) a) must match the type of the result #1 of insert-sort

**For all**:

  * `a` of type `(array int)`
  * `_X_7` of type `(array int)`

**such that**:

  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len _X_7))) (@ <= (@ get-array _X_7 i) (@ get-array _X_7 j)))) (@ = (@ len _X_7) (@ len a)))`

**Prove:** `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len _X_7))) (@ <= (@ get-array _X_7 i) (@ get-array _X_7 j))))`

