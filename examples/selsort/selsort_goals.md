# Goals for `selsort.clir`

## Goal `G_1`

Precondition of selsort must imply the qualifier of its parameter arr

**For all**:

  * `arr` of type `(array int)`

**Prove:** `(@ _mu_selsort_arr arr)`

## Goal `G_2`

The qualified type of the result of selsort must imply its postcondition

**For all**:

  * `arr` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _mu_selsort_res res arr)`

**Prove:** `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j (@ len res))) (@ <= (@ get-array res i) (@ get-array res j))))`

## Goal `G_3`

Precondition of outer-loop must imply the qualifier of its parameter k

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `false`

**Prove:** `(@ _kappa_outer-loop_k k arr)`

## Goal `G_4`

Precondition of outer-loop must imply the qualifier of its parameter res_in

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `false`

**Prove:** `(@ _mu_outer-loop_res_in res_in arr k)`

## Goal `G_5`

The qualified type of the result of outer-loop must imply its postcondition

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `res_out` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ _mu_outer-loop_res_out res_out arr k res_in)`

**Prove:** `true`

## Goal `G_6`

Precondition of parameter x_0 in call to len

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `_NU_1` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(and (@ =[] _NU_1 res_in) (@ = (@ len _NU_1) (@ len res_in)))`

**Prove:** `true`

## Goal `G_7`

Precondition of parameter x_0 in call to -

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = _NU_2 lres)`

**Prove:** `true`

## Goal `G_8`

Precondition of parameter x_1 in call to -

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `_NU_3` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = _NU_3 (the int 1))`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter x_0 in call to <

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `_NU_4` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(@ = _NU_4 k)`

**Prove:** `true`

## Goal `G_10`

Precondition of parameter x_1 in call to <

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `_NU_5` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(@ = _NU_5 lres1)`

**Prove:** `true`

## Goal `G_11`

Precondition of parameter x_0 in call to +

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `b` of type `bool`
  * `_NU_7` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(<-> b (@ < k lres1))`
  * `b`
  * `(@ = _NU_7 k)`

**Prove:** `true`

## Goal `G_12`

Precondition of parameter x_1 in call to +

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `b` of type `bool`
  * `_NU_8` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(<-> b (@ < k lres1))`
  * `b`
  * `(@ = _NU_8 (the int 1))`

**Prove:** `true`

## Goal `G_13`

Precondition of parameter k in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `b` of type `bool`
  * `k1` of type `int`
  * `_NU_9` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(<-> b (@ < k lres1))`
  * `b`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_9 k)`

**Prove:** `(@ _kappa_inner-loop_k _NU_9 arr)`

## Goal `G_14`

Precondition of parameter min in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `b` of type `bool`
  * `k1` of type `int`
  * `_NU_10` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(<-> b (@ < k lres1))`
  * `b`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_10 k)`

**Prove:** `(@ _kappa_inner-loop_min _NU_10 arr k)`

## Goal `G_15`

Precondition of parameter l in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `b` of type `bool`
  * `k1` of type `int`
  * `_NU_11` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(<-> b (@ < k lres1))`
  * `b`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_11 k1)`

**Prove:** `(@ _kappa_inner-loop_l _NU_11 arr k k)`

## Goal `G_16`

Precondition of parameter res_in in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `b` of type `bool`
  * `k1` of type `int`
  * `_NU_12` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(<-> b (@ < k lres1))`
  * `b`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ =[] _NU_12 res_in) (@ = (@ len _NU_12) (@ len res_in)))`

**Prove:** `(@ _mu_inner-loop_res_in _NU_12 arr k k k1)`

## Goal `G_17`

The type of (@ inner-loop k k k1 res_in) must match the type of the result #1 of outer-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `b` of type `bool`
  * `k1` of type `int`
  * `_X_5` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(<-> b (@ < k lres1))`
  * `b`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ _mu_inner-loop_res_out _X_5 arr k k k1 res_in)`

**Prove:** `(@ _mu_outer-loop_res_out _X_5 arr k res_in)`

## Goal `G_18`

The type of res_in must match the type of the result #1 of outer-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `lres1` of type `int`
  * `b` of type `bool`
  * `_X_6` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_outer-loop_k k arr)`
  * `(@ _mu_outer-loop_res_in res_in arr k)`
  * `(@ = lres (@ len res_in))`
  * `(@ = lres1 (@ - lres (the int 1)))`
  * `(<-> b (@ < k lres1))`
  * `(not b)`
  * `(and (@ =[] _X_6 res_in) (@ = (@ len _X_6) (@ len res_in)))`

**Prove:** `(@ _mu_outer-loop_res_out _X_6 arr k res_in)`

## Goal `G_19`

Precondition of inner-loop must imply the qualifier of its parameter k

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `false`

**Prove:** `(@ _kappa_inner-loop_k k arr)`

## Goal `G_20`

Precondition of inner-loop must imply the qualifier of its parameter min

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `false`

**Prove:** `(@ _kappa_inner-loop_min min arr k)`

## Goal `G_21`

Precondition of inner-loop must imply the qualifier of its parameter l

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `false`

**Prove:** `(@ _kappa_inner-loop_l l arr k min)`

## Goal `G_22`

Precondition of inner-loop must imply the qualifier of its parameter res_in

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `false`

**Prove:** `(@ _mu_inner-loop_res_in res_in arr k min l)`

## Goal `G_23`

The qualified type of the result of inner-loop must imply its postcondition

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `res_out` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ _mu_inner-loop_res_out res_out arr k min l res_in)`

**Prove:** `true`

## Goal `G_24`

Precondition of parameter x_0 in call to len

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `_NU_14` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(and (@ =[] _NU_14 res_in) (@ = (@ len _NU_14) (@ len res_in)))`

**Prove:** `true`

## Goal `G_25`

Precondition of parameter x_0 in call to <

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `_NU_15` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(@ = _NU_15 l)`

**Prove:** `true`

## Goal `G_26`

Precondition of parameter x_1 in call to <

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `_NU_16` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(@ = _NU_16 lres)`

**Prove:** `true`

## Goal `G_27`

Precondition of parameter i in call to get-array

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `_NU_19` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = _NU_19 l)`

**Prove:** `(and (@ <= (the int 0) _NU_19) (@ < _NU_19 (@ len res_in)))`

## Goal `G_28`

Precondition of parameter i in call to get-array

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `_NU_21` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = _NU_21 min)`

**Prove:** `(and (@ <= (the int 0) _NU_21) (@ < _NU_21 (@ len res_in)))`

## Goal `G_29`

Precondition of parameter x_0 in call to <

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `_NU_22` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = _NU_22 res_l)`

**Prove:** `true`

## Goal `G_30`

Precondition of parameter x_1 in call to <

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `_NU_23` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = _NU_23 res_min)`

**Prove:** `true`

## Goal `G_31`

Precondition of parameter x_0 in call to +

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `_NU_24` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = _NU_24 l)`

**Prove:** `true`

## Goal `G_32`

Precondition of parameter x_1 in call to +

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `_NU_25` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = _NU_25 (the int 1))`

**Prove:** `true`

## Goal `G_33`

Precondition of parameter k in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_NU_27` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `b2`
  * `(@ = _NU_27 k)`

**Prove:** `(@ _kappa_inner-loop_k _NU_27 arr)`

## Goal `G_34`

Precondition of parameter min in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_NU_28` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `b2`
  * `(@ = _NU_28 l)`

**Prove:** `(@ _kappa_inner-loop_min _NU_28 arr k)`

## Goal `G_35`

Precondition of parameter l in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_NU_29` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `b2`
  * `(@ = _NU_29 l1)`

**Prove:** `(@ _kappa_inner-loop_l _NU_29 arr k l)`

## Goal `G_36`

Precondition of parameter res_in in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_NU_30` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `b2`
  * `(and (@ =[] _NU_30 res_in) (@ = (@ len _NU_30) (@ len res_in)))`

**Prove:** `(@ _mu_inner-loop_res_in _NU_30 arr k l l1)`

## Goal `G_37`

The type of (@ inner-loop k l l1 res_in) must match the type of the result #1 of inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_X_13` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `b2`
  * `(@ _mu_inner-loop_res_out _X_13 arr k l l1 res_in)`

**Prove:** `(@ _mu_inner-loop_res_out _X_13 arr k min l res_in)`

## Goal `G_38`

Precondition of parameter k in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_NU_31` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `(not b2)`
  * `(@ = _NU_31 k)`

**Prove:** `(@ _kappa_inner-loop_k _NU_31 arr)`

## Goal `G_39`

Precondition of parameter min in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_NU_32` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `(not b2)`
  * `(@ = _NU_32 min)`

**Prove:** `(@ _kappa_inner-loop_min _NU_32 arr k)`

## Goal `G_40`

Precondition of parameter l in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_NU_33` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `(not b2)`
  * `(@ = _NU_33 l1)`

**Prove:** `(@ _kappa_inner-loop_l _NU_33 arr k min)`

## Goal `G_41`

Precondition of parameter res_in in call to inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_NU_34` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `(not b2)`
  * `(and (@ =[] _NU_34 res_in) (@ = (@ len _NU_34) (@ len res_in)))`

**Prove:** `(@ _mu_inner-loop_res_in _NU_34 arr k min l1)`

## Goal `G_42`

The type of (@ inner-loop k min l1 res_in) must match the type of the result #1 of inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_l` of type `int`
  * `res_min` of type `int`
  * `b2` of type `bool`
  * `l1` of type `int`
  * `_X_14` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `b1`
  * `(@ = res_l (@ get-array res_in l))`
  * `(@ = res_min (@ get-array res_in min))`
  * `(<-> b2 (@ < res_l res_min))`
  * `(@ = l1 (@ + l (the int 1)))`
  * `(not b2)`
  * `(@ _mu_inner-loop_res_out _X_14 arr k min l1 res_in)`

**Prove:** `(@ _mu_inner-loop_res_out _X_14 arr k min l res_in)`

## Goal `G_43`

Precondition of parameter i in call to get-array

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `_NU_36` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = _NU_36 min)`

**Prove:** `(and (@ <= (the int 0) _NU_36) (@ < _NU_36 (@ len res_in)))`

## Goal `G_44`

Precondition of parameter i in call to get-array

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_min` of type `int`
  * `_NU_38` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = _NU_38 k)`

**Prove:** `(and (@ <= (the int 0) _NU_38) (@ < _NU_38 (@ len res_in)))`

## Goal `G_45`

Precondition of parameter i in call to set-array

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_min` of type `int`
  * `res_k` of type `int`
  * `_NU_40` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = res_k (@ get-array res_in k))`
  * `(@ = _NU_40 k)`

**Prove:** `(and (@ <= (the int 0) _NU_40) (@ < _NU_40 (@ len res_in)))`

## Goal `G_46`

Precondition of parameter i in call to set-array

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_min` of type `int`
  * `res_k` of type `int`
  * `resp1` of type `(array int)`
  * `_NU_43` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = res_k (@ get-array res_in k))`
  * `(and (@ =[] resp1 (@ set-array res_in k res_min)) (@ = (@ len resp1) (@ len res_in)))`
  * `(@ = _NU_43 min)`

**Prove:** `(and (@ <= (the int 0) _NU_43) (@ < _NU_43 (@ len resp1)))`

## Goal `G_47`

Precondition of parameter x_0 in call to +

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_min` of type `int`
  * `res_k` of type `int`
  * `resp1` of type `(array int)`
  * `resp2` of type `(array int)`
  * `_NU_45` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = res_k (@ get-array res_in k))`
  * `(and (@ =[] resp1 (@ set-array res_in k res_min)) (@ = (@ len resp1) (@ len res_in)))`
  * `(and (@ =[] resp2 (@ set-array resp1 min res_k)) (@ = (@ len resp2) (@ len resp1)))`
  * `(@ = _NU_45 k)`

**Prove:** `true`

## Goal `G_48`

Precondition of parameter x_1 in call to +

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_min` of type `int`
  * `res_k` of type `int`
  * `resp1` of type `(array int)`
  * `resp2` of type `(array int)`
  * `_NU_46` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = res_k (@ get-array res_in k))`
  * `(and (@ =[] resp1 (@ set-array res_in k res_min)) (@ = (@ len resp1) (@ len res_in)))`
  * `(and (@ =[] resp2 (@ set-array resp1 min res_k)) (@ = (@ len resp2) (@ len resp1)))`
  * `(@ = _NU_46 (the int 1))`

**Prove:** `true`

## Goal `G_49`

Precondition of parameter k in call to outer-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_min` of type `int`
  * `res_k` of type `int`
  * `resp1` of type `(array int)`
  * `resp2` of type `(array int)`
  * `k1` of type `int`
  * `_NU_47` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = res_k (@ get-array res_in k))`
  * `(and (@ =[] resp1 (@ set-array res_in k res_min)) (@ = (@ len resp1) (@ len res_in)))`
  * `(and (@ =[] resp2 (@ set-array resp1 min res_k)) (@ = (@ len resp2) (@ len resp1)))`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ = _NU_47 k1)`

**Prove:** `(@ _kappa_outer-loop_k _NU_47 arr)`

## Goal `G_50`

Precondition of parameter res_in in call to outer-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_min` of type `int`
  * `res_k` of type `int`
  * `resp1` of type `(array int)`
  * `resp2` of type `(array int)`
  * `k1` of type `int`
  * `_NU_48` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = res_k (@ get-array res_in k))`
  * `(and (@ =[] resp1 (@ set-array res_in k res_min)) (@ = (@ len resp1) (@ len res_in)))`
  * `(and (@ =[] resp2 (@ set-array resp1 min res_k)) (@ = (@ len resp2) (@ len resp1)))`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(and (@ =[] _NU_48 resp2) (@ = (@ len _NU_48) (@ len resp2)))`

**Prove:** `(@ _mu_outer-loop_res_in _NU_48 arr k1)`

## Goal `G_51`

The type of (@ outer-loop k1 resp2) must match the type of the result #1 of inner-loop

**For all**:

  * `arr` of type `(array int)`
  * `k` of type `int`
  * `min` of type `int`
  * `l` of type `int`
  * `res_in` of type `(array int)`
  * `lres` of type `int`
  * `b1` of type `bool`
  * `res_min` of type `int`
  * `res_k` of type `int`
  * `resp1` of type `(array int)`
  * `resp2` of type `(array int)`
  * `k1` of type `int`
  * `_X_20` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _kappa_inner-loop_k k arr)`
  * `(@ _kappa_inner-loop_min min arr k)`
  * `(@ _kappa_inner-loop_l l arr k min)`
  * `(@ _mu_inner-loop_res_in res_in arr k min l)`
  * `(@ = lres (@ len res_in))`
  * `(<-> b1 (@ < l lres))`
  * `(not b1)`
  * `(@ = res_min (@ get-array res_in min))`
  * `(@ = res_k (@ get-array res_in k))`
  * `(and (@ =[] resp1 (@ set-array res_in k res_min)) (@ = (@ len resp1) (@ len res_in)))`
  * `(and (@ =[] resp2 (@ set-array resp1 min res_k)) (@ = (@ len resp2) (@ len resp1)))`
  * `(@ = k1 (@ + k (the int 1)))`
  * `(@ _mu_outer-loop_res_out _X_20 arr k1 resp2)`

**Prove:** `(@ _mu_inner-loop_res_out _X_20 arr k min l res_in)`

## Goal `G_52`

Precondition of parameter k in call to outer-loop

**For all**:

  * `arr` of type `(array int)`
  * `_NU_49` of type `int`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ = _NU_49 (the int 0))`

**Prove:** `(@ _kappa_outer-loop_k _NU_49 arr)`

## Goal `G_53`

Precondition of parameter res_in in call to outer-loop

**For all**:

  * `arr` of type `(array int)`
  * `_NU_50` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(and (@ =[] _NU_50 arr) (@ = (@ len _NU_50) (@ len arr)))`

**Prove:** `(@ _mu_outer-loop_res_in _NU_50 arr (the int 0))`

## Goal `G_54`

The type of (@ outer-loop (the int 0) arr) must match the type of the result #1 of selsort

**For all**:

  * `arr` of type `(array int)`
  * `_X_21` of type `(array int)`

**such that**:

  * `(@ _mu_selsort_arr arr)`
  * `(@ _mu_outer-loop_res_out _X_21 arr (the int 0) arr)`

**Prove:** `(@ _mu_selsort_res _X_21 arr)`

