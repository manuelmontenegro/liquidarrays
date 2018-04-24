# Goals for `linsearch.clir`

## Goal `G_1`

Precondition of linsearch must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`

**Prove:** `(@ _kappa_linsearch_x x)`

## Goal `G_2`

Precondition of linsearch must imply the qualifier of its parameter arr

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`

**Prove:** `(@ _mu_linsearch_arr arr x)`

## Goal `G_3`

The qualified type of the result of linsearch must imply its postcondition

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `idx` of type `int`
  * `dummy` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch_idx idx x arr)`
  * `(@ _mu_linsearch_dummy dummy x arr idx)`

**Prove:** `(and (@ <= (the int 0) idx) (@ <= idx (@ len arr)) (-> (@ < idx (@ len arr)) (@ = (@ get-array arr idx) x)) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i idx)) (not (@ = (@ get-array arr i) x)))))`

## Goal `G_4`

Precondition of linsearch-rec must imply the qualifier of its parameter k

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `false`

**Prove:** `(@ _kappa_linsearch-rec_k k x arr)`

## Goal `G_5`

Precondition of linsearch-rec must imply the qualifier of its parameter x_1

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `false`

**Prove:** `(@ _kappa_linsearch-rec_x x_1 x arr k)`

## Goal `G_6`

Precondition of linsearch-rec must imply the qualifier of its parameter arr_1

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `false`

**Prove:** `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`

## Goal `G_7`

The qualified type of the result of linsearch-rec must imply its postcondition

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `idx` of type `int`
  * `dummy` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ _kappa_linsearch-rec_idx idx k x_1 arr_1)`
  * `(@ _mu_linsearch-rec_dummy dummy k x_1 arr_1 idx)`

**Prove:** `true`

## Goal `G_8`

Precondition of parameter x_0 in call to len

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `_NU_1` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(and (@ =[] _NU_1 arr_1) (@ = (@ len _NU_1) (@ len arr_1)))`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter x_0 in call to <

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(@ = _NU_2 k)`

**Prove:** `true`

## Goal `G_10`

Precondition of parameter x_1 in call to <

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `_NU_3` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(@ = _NU_3 larr)`

**Prove:** `true`

## Goal `G_11`

Precondition of parameter i in call to get-array

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_NU_6` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = _NU_6 k)`

**Prove:** `(and (@ <= (the int 0) _NU_6) (@ < _NU_6 (@ len arr_1)))`

## Goal `G_12`

Precondition of parameter x_0 in call to =

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `_NU_7` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(@ = _NU_7 arr_k)`

**Prove:** `true`

## Goal `G_13`

Precondition of parameter x_1 in call to =

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `_NU_8` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(@ = _NU_8 x_1)`

**Prove:** `true`

## Goal `G_14`

The type of (tuple k arr_1) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_X_5` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `b2`
  * `(@ = _X_5 k)`

**Prove:** `(@ _kappa_linsearch-rec_idx _X_5 k x_1 arr_1)`

## Goal `G_15`

The type of (tuple k arr_1) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_X_5` of type `int`
  * `_X_6` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `b2`
  * `(@ = _X_5 k)`
  * `(and (@ =[] _X_6 arr_1) (@ = (@ len _X_6) (@ len arr_1)))`

**Prove:** `(@ _mu_linsearch-rec_dummy _X_6 k x_1 arr_1 _X_5)`

## Goal `G_16`

Precondition of parameter x_0 in call to +

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_NU_12` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `(not b2)`
  * `(@ = _NU_12 k)`

**Prove:** `true`

## Goal `G_17`

Precondition of parameter x_1 in call to +

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `_NU_13` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `(not b2)`
  * `(@ = _NU_13 (the int 1))`

**Prove:** `true`

## Goal `G_18`

Precondition of parameter k in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_NU_14` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_14 k1)`

**Prove:** `(@ _kappa_linsearch-rec_k _NU_14 x_1 arr_1)`

## Goal `G_19`

Precondition of parameter x in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_NU_15` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_15 x_1)`

**Prove:** `(@ _kappa_linsearch-rec_x _NU_15 x_1 arr_1 k1)`

## Goal `G_20`

Precondition of parameter arr in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_NU_16` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ =[] _NU_16 arr_1) (@ = (@ len _NU_16) (@ len arr_1)))`

**Prove:** `(@ _mu_linsearch-rec_arr _NU_16 arr_1 k1 x_1)`

## Goal `G_21`

The type of (@ linsearch-rec k1 x_1 arr_1) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_X_8` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ _kappa_linsearch-rec_idx _X_8 k1 x_1 arr_1)`

**Prove:** `(@ _kappa_linsearch-rec_idx _X_8 k x_1 arr_1)`

## Goal `G_22`

The type of (@ linsearch-rec k1 x_1 arr_1) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `arr_k` of type `int`
  * `b2` of type `bool`
  * `k1` of type `int`
  * `_X_8` of type `int`
  * `_X_9` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `b1`
  * `(@ = arr_k (@ get-array arr_1 k))`
  * `(<-> b2 (@ = arr_k x_1))`
  * `(not b2)`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ _kappa_linsearch-rec_idx _X_8 k1 x_1 arr_1)`
  * `(@ _mu_linsearch-rec_dummy _X_9 k1 x_1 arr_1 _X_8)`

**Prove:** `(@ _mu_linsearch-rec_dummy _X_9 k x_1 arr_1 _X_8)`

## Goal `G_23`

The type of (tuple k arr_1) must match the type of the result #1 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_X_10` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `(not b1)`
  * `(@ = _X_10 k)`

**Prove:** `(@ _kappa_linsearch-rec_idx _X_10 k x_1 arr_1)`

## Goal `G_24`

The type of (tuple k arr_1) must match the type of the result #2 of linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `k` of type `int`
  * `x_1` of type `int`
  * `arr_1` of type `(array int)`
  * `larr` of type `int`
  * `b1` of type `bool`
  * `_X_10` of type `int`
  * `_X_11` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_k k x arr)`
  * `(@ _kappa_linsearch-rec_x x_1 x arr k)`
  * `(@ _mu_linsearch-rec_arr arr_1 arr k x_1)`
  * `(@ = larr (@ len arr_1))`
  * `(<-> b1 (@ < k larr))`
  * `(not b1)`
  * `(@ = _X_10 k)`
  * `(and (@ =[] _X_11 arr_1) (@ = (@ len _X_11) (@ len arr_1)))`

**Prove:** `(@ _mu_linsearch-rec_dummy _X_11 k x_1 arr_1 _X_10)`

## Goal `G_25`

Precondition of parameter k in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_NU_19` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ = _NU_19 (the int 0))`

**Prove:** `(@ _kappa_linsearch-rec_k _NU_19 x arr)`

## Goal `G_26`

Precondition of parameter x in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_NU_20` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ = _NU_20 x)`

**Prove:** `(@ _kappa_linsearch-rec_x _NU_20 x arr (the int 0))`

## Goal `G_27`

Precondition of parameter arr in call to linsearch-rec

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_NU_21` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(and (@ =[] _NU_21 arr) (@ = (@ len _NU_21) (@ len arr)))`

**Prove:** `(@ _mu_linsearch-rec_arr _NU_21 arr (the int 0) x)`

## Goal `G_28`

The type of (@ linsearch-rec (the int 0) x arr) must match the type of the result #1 of linsearch

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_X_12` of type `int`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_idx _X_12 (the int 0) x arr)`

**Prove:** `(@ _kappa_linsearch_idx _X_12 x arr)`

## Goal `G_29`

The type of (@ linsearch-rec (the int 0) x arr) must match the type of the result #2 of linsearch

**For all**:

  * `x` of type `int`
  * `arr` of type `(array int)`
  * `_X_12` of type `int`
  * `_X_13` of type `(array int)`

**such that**:

  * `(@ _kappa_linsearch_x x)`
  * `(@ _mu_linsearch_arr arr x)`
  * `(@ _kappa_linsearch-rec_idx _X_12 (the int 0) x arr)`
  * `(@ _mu_linsearch-rec_dummy _X_13 (the int 0) x arr _X_12)`

**Prove:** `(@ _mu_linsearch_dummy _X_13 x arr _X_12)`

