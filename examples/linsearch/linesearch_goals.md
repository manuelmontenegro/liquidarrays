# Goals for `linsearch_typecheck.clir`

## Goal `G_1`

Precondition of linsearch must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`

**Prove:** `true`

## Goal `G_2`

Precondition of linsearch must imply the qualifier of its parameter arr

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`

**Prove:** `true`

## Goal `G_3`

The qualified type of the result of linsearch must imply its postcondition

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `idx` of type `int`
  * `dummy` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) idx) (@ <= idx (@ len arr)) (-> (@ < idx (@ len arr)) (@ = (@ get-array arr idx) x)))`
  * `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i idx)) (not (@ = (@ get-array arr i) x))))`

**Prove:** `(and (@ <= (the int 0) idx) (@ <= idx (@ len arr)) (-> (@ < idx (@ len arr)) (@ = (@ get-array arr idx) x)) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i idx)) (not (@ = (@ get-array arr i) x)))))`

## Goal `G_4`

Precondition of linsearch-rec must imply the qualifier of its parameter k

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`

**such that**:

  * `false`

**Prove:** `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`

## Goal `G_5`

The qualified type of the result of linsearch-rec must imply its postcondition

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `idx` of type `int`
  * `dummy` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(and (@ <= (the int 0) idx) (@ <= idx (@ len arr)) (-> (@ < idx (@ len arr)) (@ = (@ get-array arr idx) x)))`
  * `(@ _mu_linsearch-rec_dummy dummy x arr k idx)`

**Prove:** `true`

## Goal `G_6`

Precondition of parameter x_0 in call to len

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `_NU_1` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(and (@ =[] _NU_1 arr) (@ = (@ len _NU_1) (@ len arr)))`

**Prove:** `true`

## Goal `G_7`

Precondition of parameter x_0 in call to <

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(@ = _NU_2 k)`

**Prove:** `true`

## Goal `G_8`

Precondition of parameter x_1 in call to <

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `_NU_3` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(@ = _NU_3 larr)`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter i in call to get-array

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_NU_6` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = _NU_6 k)`

**Prove:** `(and (@ <= (the int 0) _NU_6) (@ < _NU_6 (@ len arr)))`

## Goal `G_10`

Precondition of parameter x_0 in call to =

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `_NU_7` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(@ = _NU_7 arr_k)`

**Prove:** `true`

## Goal `G_11`

Precondition of parameter x_1 in call to =

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `_NU_8` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(@ = _NU_8 x)`

**Prove:** `true`

## Goal `G_12`

The type of (tuple k arr) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_X_5` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(<-> b2 (@ = arr_k x))`
  * `b2`
  * `(@ = _X_5 k)`

**Prove:** `(and (@ <= (the int 0) _X_5) (@ <= _X_5 (@ len arr)) (-> (@ < _X_5 (@ len arr)) (@ = (@ get-array arr _X_5) x)))`

## Goal `G_13`

The type of (tuple k arr) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_X_5` of type `int`
  * `_X_6` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(<-> b2 (@ = arr_k x))`
  * `b2`
  * `(@ = _X_5 k)`
  * `(and (@ =[] _X_6 arr) (@ = (@ len _X_6) (@ len arr)))`

**Prove:** `(@ _mu_linsearch-rec_dummy _X_6 x arr k _X_5)`

## Goal `G_14`

Precondition of parameter x_0 in call to +

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_NU_12` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = _NU_12 k)`

**Prove:** `true`

## Goal `G_15`

Precondition of parameter x_1 in call to +

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_NU_13` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = _NU_13 (the int 1))`

**Prove:** `true`

## Goal `G_16`

Precondition of parameter k in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_NU_14` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_14 k1)`

**Prove:** `(and (@ <= (the int 0) _NU_14) (@ <= _NU_14 (@ len arr)))`

## Goal `G_17`

The type of (@ linsearch-rec k1) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_X_8` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ <= (the int 0) _X_8) (@ <= _X_8 (@ len arr)) (-> (@ < _X_8 (@ len arr)) (@ = (@ get-array arr _X_8) x)))`

**Prove:** `(and (@ <= (the int 0) _X_8) (@ <= _X_8 (@ len arr)) (-> (@ < _X_8 (@ len arr)) (@ = (@ get-array arr _X_8) x)))`

## Goal `G_18`

The type of (@ linsearch-rec k1) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_X_8` of type `int`
  * `_X_9` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ <= (the int 0) _X_8) (@ <= _X_8 (@ len arr)) (-> (@ < _X_8 (@ len arr)) (@ = (@ get-array arr _X_8) x)))`
  * `(@ _mu_linsearch-rec_dummy _X_9 x arr k1 _X_8)`

**Prove:** `(@ _mu_linsearch-rec_dummy _X_9 x arr k _X_8)`

## Goal `G_19`

The type of (tuple k arr) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_X_10` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `(not b1)`
  * `(@ = _X_10 k)`

**Prove:** `(and (@ <= (the int 0) _X_10) (@ <= _X_10 (@ len arr)) (-> (@ < _X_10 (@ len arr)) (@ = (@ get-array arr _X_10) x)))`

## Goal `G_20`

The type of (tuple k arr) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_X_10` of type `int`
  * `_X_11` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k) (@ <= k (@ len arr)))`
  * `(@ = larr (@ len arr))`
  * `(<-> b1 (@ < k larr))`
  * `(not b1)`
  * `(@ = _X_10 k)`
  * `(and (@ =[] _X_11 arr) (@ = (@ len _X_11) (@ len arr)))`

**Prove:** `(@ _mu_linsearch-rec_dummy _X_11 x arr k _X_10)`

## Goal `G_21`

Precondition of parameter k in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_NU_17` of type `int`

**such that**:

  * `(@ = _NU_17 (the int 0))`

**Prove:** `(and (@ <= (the int 0) _NU_17) (@ <= _NU_17 (@ len arr)))`

## Goal `G_22`

The type of (@ linsearch-rec (the int 0)) must match the type of the result #1 of linsearch

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_X_12` of type `int`

**such that**:

  * `(and (@ <= (the int 0) _X_12) (@ <= _X_12 (@ len arr)) (-> (@ < _X_12 (@ len arr)) (@ = (@ get-array arr _X_12) x)))`

**Prove:** `(and (@ <= (the int 0) _X_12) (@ <= _X_12 (@ len arr)) (-> (@ < _X_12 (@ len arr)) (@ = (@ get-array arr _X_12) x)))`

## Goal `G_23`

The type of (@ linsearch-rec (the int 0)) must match the type of the result #2 of linsearch

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_X_12` of type `int`
  * `_X_13` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) _X_12) (@ <= _X_12 (@ len arr)) (-> (@ < _X_12 (@ len arr)) (@ = (@ get-array arr _X_12) x)))`
  * `(@ _mu_linsearch-rec_dummy _X_13 x arr (the int 0) _X_12)`

**Prove:** `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i _X_12)) (not (@ = (@ get-array arr i) x))))`

