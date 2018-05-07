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
  * `arr_1` of type `(array int)`

**such that**:

  * `false`

**Prove:** `(and (@ <= (the int 0) k))`

## Goal `G_5`

Precondition of linsearch-rec must imply the qualifier of its parameter arr_1

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `false`

**Prove:** `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`

## Goal `G_6`

The qualified type of the result of linsearch-rec must imply its postcondition

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `idx` of type `int`
  * `dummy` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(and (@ <= (the int 0) idx) (@ <= idx (@ len arr_1)) (-> (@ < idx (@ len arr_1)) (@ = (@ get-array arr_1 idx) x)))`
  * `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i idx)) (not (@ = (@ get-array arr_1 i) x))))`

**Prove:** `true`

## Goal `G_7`

Precondition of parameter x_0 in call to len

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `_NU_1` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(and (@ =[] _NU_1 arr_1) (@ = (@ len _NU_1) (@ len arr_1)))`

**Prove:** `true`

## Goal `G_8`

Precondition of parameter x_0 in call to <

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(@ = _NU_2 k)`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter x_1 in call to <

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `_NU_3` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(@ = _NU_3 larr)`

**Prove:** `true`

## Goal `G_10`

Precondition of parameter i in call to get-array

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_NU_6` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = _NU_6 k)`

**Prove:** `(and (@ <= (the int 0) _NU_6) (@ < _NU_6 (@ len arr_1)))`

## Goal `G_11`

Precondition of parameter x_0 in call to =

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `_NU_7` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(@ = _NU_7 arr_k)`

**Prove:** `true`

## Goal `G_12`

Precondition of parameter x_1 in call to =

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `_NU_8` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(@ = _NU_8 x)`

**Prove:** `true`

## Goal `G_13`

The type of (tuple k arr_1) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_X_5` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x))`
  * `b2`
  * `(@ = _X_5 k)`

**Prove:** `(and (@ <= (the int 0) _X_5) (@ <= _X_5 (@ len arr_1)) (-> (@ < _X_5 (@ len arr_1)) (@ = (@ get-array arr_1 _X_5) x)))`

## Goal `G_14`

The type of (tuple k arr_1) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_X_5` of type `int`
  * `_X_6` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x))`
  * `b2`
  * `(@ = _X_5 k)`
  * `(and (@ =[] _X_6 arr_1) (@ = (@ len _X_6) (@ len arr_1)))`

**Prove:** `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i _X_5)) (not (@ = (@ get-array arr_1 i) x))))`

## Goal `G_15`

Precondition of parameter x_0 in call to +

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_NU_12` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = _NU_12 k)`

**Prove:** `true`

## Goal `G_16`

Precondition of parameter x_1 in call to +

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_NU_13` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = _NU_13 (the int 1))`

**Prove:** `true`

## Goal `G_17`

Precondition of parameter k in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_NU_14` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_14 k1)`

**Prove:** `(and (@ <= (the int 0) _NU_14))`

## Goal `G_18`

Precondition of parameter arr in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_NU_15` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ =[] _NU_15 arr_1) (@ = (@ len _NU_15) (@ len arr_1)))`

**Prove:** `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k1)) (not (@ = (@ get-array _NU_15 i) x)))) (@ <= k1 (@ len _NU_15)))`

## Goal `G_19`

The type of (@ linsearch-rec k1 arr_1) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_X_8` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ <= (the int 0) _X_8) (@ <= _X_8 (@ len arr_1)) (-> (@ < _X_8 (@ len arr_1)) (@ = (@ get-array arr_1 _X_8) x)))`

**Prove:** `(and (@ <= (the int 0) _X_8) (@ <= _X_8 (@ len arr_1)) (-> (@ < _X_8 (@ len arr_1)) (@ = (@ get-array arr_1 _X_8) x)))`

## Goal `G_20`

The type of (@ linsearch-rec k1 arr_1) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_X_8` of type `int`
  * `_X_9` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ <= (the int 0) _X_8) (@ <= _X_8 (@ len arr_1)) (-> (@ < _X_8 (@ len arr_1)) (@ = (@ get-array arr_1 _X_8) x)))`
  * `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i _X_8)) (not (@ = (@ get-array arr_1 i) x))))`

**Prove:** `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i _X_8)) (not (@ = (@ get-array arr_1 i) x))))`

## Goal `G_21`

The type of (tuple k arr_1) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_X_10` of type `int`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `(not b1)`
  * `(@ = _X_10 k)`

**Prove:** `(and (@ <= (the int 0) _X_10) (@ <= _X_10 (@ len arr_1)) (-> (@ < _X_10 (@ len arr_1)) (@ = (@ get-array arr_1 _X_10) x)))`

## Goal `G_22`

The type of (tuple k arr_1) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_X_10` of type `int`
  * `_X_11` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k))`
  * `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (not (@ = (@ get-array arr_1 i) x)))) (@ <= k (@ len arr_1)))`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `(not b1)`
  * `(@ = _X_10 k)`
  * `(and (@ =[] _X_11 arr_1) (@ = (@ len _X_11) (@ len arr_1)))`

**Prove:** `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i _X_10)) (not (@ = (@ get-array arr_1 i) x))))`

## Goal `G_23`

Precondition of parameter k in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_NU_18` of type `int`

**such that**:

  * `(@ = _NU_18 (the int 0))`

**Prove:** `(and (@ <= (the int 0) _NU_18))`

## Goal `G_24`

Precondition of parameter arr in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_NU_19` of type `(array int)`

**such that**:

  * `(and (@ =[] _NU_19 arr) (@ = (@ len _NU_19) (@ len arr)))`

**Prove:** `(and (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i (the int 0))) (not (@ = (@ get-array _NU_19 i) x)))) (@ <= (the int 0) (@ len _NU_19)))`

## Goal `G_25`

The type of (@ linsearch-rec (the int 0) arr) must match the type of the result #1 of linsearch

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_X_12` of type `int`

**such that**:

  * `(and (@ <= (the int 0) _X_12) (@ <= _X_12 (@ len arr)) (-> (@ < _X_12 (@ len arr)) (@ = (@ get-array arr _X_12) x)))`

**Prove:** `(and (@ <= (the int 0) _X_12) (@ <= _X_12 (@ len arr)) (-> (@ < _X_12 (@ len arr)) (@ = (@ get-array arr _X_12) x)))`

## Goal `G_26`

The type of (@ linsearch-rec (the int 0) arr) must match the type of the result #2 of linsearch

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_X_12` of type `int`
  * `_X_13` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) _X_12) (@ <= _X_12 (@ len arr)) (-> (@ < _X_12 (@ len arr)) (@ = (@ get-array arr _X_12) x)))`
  * `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i _X_12)) (not (@ = (@ get-array arr i) x))))`

**Prove:** `(forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i _X_12)) (not (@ = (@ get-array arr i) x))))`

