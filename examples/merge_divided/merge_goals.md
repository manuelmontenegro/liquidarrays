# Goals for `merge_pruned.clir`

## Goal `G_1`

Precondition of merge must imply the qualifier of its parameter a

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) a) (@ <= a m) (@ <= m b) (@ <= b (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))))`

**Prove:** `(@ <= (the int 0) a)`

## Goal `G_2`

Precondition of merge must imply the qualifier of its parameter m

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(and (@ <= (the int 0) a) (@ <= a m) (@ <= m b) (@ <= b (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))))`

**Prove:** `(@ <= a m)`

## Goal `G_3`

Precondition of merge must imply the qualifier of its parameter b

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(and (@ <= (the int 0) a) (@ <= a m) (@ <= m b) (@ <= b (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))))`

**Prove:** `(@ <= m b)`

## Goal `G_4`

Precondition of merge must imply the qualifier of its parameter arr

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (@ <= (the int 0) a) (@ <= a m) (@ <= m b) (@ <= b (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))))`

**Prove:** `(@ _mu_merge_arr arr a m b)`

## Goal `G_5`

The qualified type of the result of merge must imply its postcondition

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(@ _mu_merge_res res a m b arr)`

**Prove:** `(and (@ = (@ len res) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array res i) (@ get-array res j)))))`

## Goal `G_6`

Precondition of f1 must imply the qualifier of its parameter ca

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `false`

**Prove:** `(and (@ <= a ca) (@ <= ca m))`

## Goal `G_7`

Precondition of f1 must imply the qualifier of its parameter cm

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `false`

**Prove:** `(and (@ <= m cm) (@ <= cm b))`

## Goal `G_8`

Precondition of f1 must imply the qualifier of its parameter k

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `false`

**Prove:** `(@ = (@ + k m) (@ + ca cm))`

## Goal `G_9`

Precondition of f1 must imply the qualifier of its parameter res_in

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `false`

**Prove:** `(@ _mu_f1_res_in res_in a m b arr ca cm k)`

## Goal `G_10`

The qualified type of the result of f1 must imply its postcondition

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `res_out` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(@ _mu_f1_res_out res_out a m b arr ca cm k res_in)`

**Prove:** `true`

## Goal `G_11`

Precondition of parameter x_0 in call to <

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `_NU_1` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(@ = _NU_1 ca)`

**Prove:** `true`

## Goal `G_12`

Precondition of parameter x_1 in call to <

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `_NU_2` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(@ = _NU_2 m)`

**Prove:** `true`

## Goal `G_13`

Precondition of parameter x_0 in call to <

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `_NU_3` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(@ = _NU_3 cm)`

**Prove:** `true`

## Goal `G_14`

Precondition of parameter x_1 in call to <

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `_NU_4` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(@ = _NU_4 b)`

**Prove:** `true`

## Goal `G_15`

Precondition of parameter i in call to get-array

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `_NU_8` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = _NU_8 ca)`

**Prove:** `(and (@ <= (the int 0) _NU_8) (@ < _NU_8 (@ len arr)))`

## Goal `G_16`

Precondition of parameter i in call to get-array

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `_NU_10` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = _NU_10 cm)`

**Prove:** `(and (@ <= (the int 0) _NU_10) (@ < _NU_10 (@ len arr)))`

## Goal `G_17`

Precondition of parameter x_0 in call to <=

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `_NU_11` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(@ = _NU_11 arr_ca)`

**Prove:** `true`

## Goal `G_18`

Precondition of parameter x_1 in call to <=

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `_NU_12` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(@ = _NU_12 arr_cm)`

**Prove:** `true`

## Goal `G_19`

Precondition of parameter i in call to set-array

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `_NU_15` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(@ = _NU_15 k)`

**Prove:** `(and (@ <= (the int 0) _NU_15) (@ < _NU_15 (@ len res_in)))`

## Goal `G_20`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `_NU_17` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = _NU_17 ca)`

**Prove:** `true`

## Goal `G_21`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `_NU_18` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = _NU_18 (the int 1))`

**Prove:** `true`

## Goal `G_22`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cap` of type `int`
  * `_NU_19` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = _NU_19 k)`

**Prove:** `true`

## Goal `G_23`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cap` of type `int`
  * `_NU_20` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = _NU_20 (the int 1))`

**Prove:** `true`

## Goal `G_24`

Precondition of parameter ca in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cap` of type `int`
  * `kp` of type `int`
  * `_NU_21` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = _NU_21 cap)`

**Prove:** `(and (@ <= a _NU_21) (@ <= _NU_21 m))`

## Goal `G_25`

Precondition of parameter cm in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cap` of type `int`
  * `kp` of type `int`
  * `_NU_22` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = _NU_22 cm)`

**Prove:** `(and (@ <= m _NU_22) (@ <= _NU_22 b))`

## Goal `G_26`

Precondition of parameter k in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cap` of type `int`
  * `kp` of type `int`
  * `_NU_23` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = _NU_23 kp)`

**Prove:** `(@ = (@ + _NU_23 m) (@ + cap cm))`

## Goal `G_27`

Precondition of parameter res_in in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cap` of type `int`
  * `kp` of type `int`
  * `_NU_24` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(and (@ =[] _NU_24 resp) (@ = (@ len _NU_24) (@ len resp)))`

**Prove:** `(@ _mu_f1_res_in _NU_24 a m b arr cap cm kp)`

## Goal `G_28`

The type of (@ f1 cap cm kp resp) must match the type of the result #1 of f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cap` of type `int`
  * `kp` of type `int`
  * `_X_9` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `b3`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ _mu_f1_res_out _X_9 a m b arr cap cm kp resp)`

**Prove:** `(@ _mu_f1_res_out _X_9 a m b arr ca cm k res_in)`

## Goal `G_29`

Precondition of parameter i in call to set-array

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `_NU_26` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(@ = _NU_26 k)`

**Prove:** `(and (@ <= (the int 0) _NU_26) (@ < _NU_26 (@ len res_in)))`

## Goal `G_30`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `_NU_28` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = _NU_28 cm)`

**Prove:** `true`

## Goal `G_31`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `_NU_29` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = _NU_29 (the int 1))`

**Prove:** `true`

## Goal `G_32`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `_NU_30` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(@ = _NU_30 k)`

**Prove:** `true`

## Goal `G_33`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `_NU_31` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(@ = _NU_31 (the int 1))`

**Prove:** `true`

## Goal `G_34`

Precondition of parameter ca in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `kp` of type `int`
  * `_NU_32` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = _NU_32 ca)`

**Prove:** `(and (@ <= a _NU_32) (@ <= _NU_32 m))`

## Goal `G_35`

Precondition of parameter cm in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `kp` of type `int`
  * `_NU_33` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = _NU_33 cmp)`

**Prove:** `(and (@ <= m _NU_33) (@ <= _NU_33 b))`

## Goal `G_36`

Precondition of parameter k in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `kp` of type `int`
  * `_NU_34` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = _NU_34 kp)`

**Prove:** `(@ = (@ + _NU_34 m) (@ + ca cmp))`

## Goal `G_37`

Precondition of parameter res_in in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `kp` of type `int`
  * `_NU_35` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(and (@ =[] _NU_35 resp) (@ = (@ len _NU_35) (@ len resp)))`

**Prove:** `(@ _mu_f1_res_in _NU_35 a m b arr ca cmp kp)`

## Goal `G_38`

The type of (@ f1 ca cmp kp resp) must match the type of the result #1 of f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `arr_ca` of type `int`
  * `arr_cm` of type `int`
  * `b3` of type `bool`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `kp` of type `int`
  * `_X_13` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `b2`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(<-> b3 (@ <= arr_ca arr_cm))`
  * `(not b3)`
  * `(and (@ =[] resp (@ set-array res_in k arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ _mu_f1_res_out _X_13 a m b arr ca cmp kp resp)`

**Prove:** `(@ _mu_f1_res_out _X_13 a m b arr ca cm k res_in)`

## Goal `G_39`

Precondition of parameter x_0 in call to -

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `_NU_36` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(@ = _NU_36 m)`

**Prove:** `true`

## Goal `G_40`

Precondition of parameter x_1 in call to -

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `_NU_37` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(@ = _NU_37 ca)`

**Prove:** `true`

## Goal `G_41`

Precondition of parameter from in call to copy

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `m_ca` of type `int`
  * `_NU_39` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(@ = m_ca (@ - m ca))`
  * `(@ = _NU_39 ca)`

**Prove:** `(@ <= (the int 0) _NU_39)`

## Goal `G_42`

Precondition of parameter length in call to copy

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `m_ca` of type `int`
  * `_NU_40` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(@ = m_ca (@ - m ca))`
  * `(@ = _NU_40 m_ca)`

**Prove:** `(and (@ <= (the int 0) _NU_40) (@ <= (@ + ca _NU_40) (@ len arr)))`

## Goal `G_43`

Precondition of parameter dest_from in call to copy

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `m_ca` of type `int`
  * `_NU_42` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(@ = m_ca (@ - m ca))`
  * `(@ = _NU_42 k)`

**Prove:** `(and (@ <= (the int 0) _NU_42) (@ <= (@ + _NU_42 m_ca) (@ len res_in)))`

## Goal `G_44`

The type of (@ copy arr ca m_ca res_in k) must match the type of the result #1 of f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `m_ca` of type `int`
  * `_X_15` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(@ = m_ca (@ - m ca))`
  * `(and (@ = (@ len _X_15) (@ len res_in)) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (@ = (@ get-array _X_15 i) (@ get-array res_in i)))) (forall ((i int)) (-> (and (@ <= (@ + k m_ca) i) (@ < i (@ len _X_15))) (@ = (@ get-array _X_15 i) (@ get-array res_in i)))) (forall ((i int) (j int)) (-> (and (@ <= ca i) (@ < i (@ + ca m_ca)) (@ <= k j) (@ < j (@ + k m_ca)) (@ = (@ - i ca) (@ - j k))) (@ = (@ get-array _X_15 j) (@ get-array arr i)))))`

**Prove:** `(@ _mu_f1_res_out _X_15 a m b arr ca cm k res_in)`

## Goal `G_45`

Precondition of parameter x_0 in call to -

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `_NU_43` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(@ = _NU_43 b)`

**Prove:** `true`

## Goal `G_46`

Precondition of parameter x_1 in call to -

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `_NU_44` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(@ = _NU_44 cm)`

**Prove:** `true`

## Goal `G_47`

Precondition of parameter from in call to copy

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `b_cm` of type `int`
  * `_NU_46` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(@ = b_cm (@ - b cm))`
  * `(@ = _NU_46 cm)`

**Prove:** `(@ <= (the int 0) _NU_46)`

## Goal `G_48`

Precondition of parameter length in call to copy

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `b_cm` of type `int`
  * `_NU_47` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(@ = b_cm (@ - b cm))`
  * `(@ = _NU_47 b_cm)`

**Prove:** `(and (@ <= (the int 0) _NU_47) (@ <= (@ + cm _NU_47) (@ len arr)))`

## Goal `G_49`

Precondition of parameter dest_from in call to copy

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `b_cm` of type `int`
  * `_NU_49` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(@ = b_cm (@ - b cm))`
  * `(@ = _NU_49 k)`

**Prove:** `(and (@ <= (the int 0) _NU_49) (@ <= (@ + _NU_49 b_cm) (@ len res_in)))`

## Goal `G_50`

The type of (@ copy arr cm b_cm res_in k) must match the type of the result #1 of f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `cm` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b1` of type `bool`
  * `b2` of type `bool`
  * `b_cm` of type `int`
  * `_X_17` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(@ _mu_f1_res_in res_in a m b arr ca cm k)`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(@ = b_cm (@ - b cm))`
  * `(and (@ = (@ len _X_17) (@ len res_in)) (forall ((i int)) (-> (and (@ <= (the int 0) i) (@ < i k)) (@ = (@ get-array _X_17 i) (@ get-array res_in i)))) (forall ((i int)) (-> (and (@ <= (@ + k b_cm) i) (@ < i (@ len _X_17))) (@ = (@ get-array _X_17 i) (@ get-array res_in i)))) (forall ((i int) (j int)) (-> (and (@ <= cm i) (@ < i (@ + cm b_cm)) (@ <= k j) (@ < j (@ + k b_cm)) (@ = (@ - i cm) (@ - j k))) (@ = (@ get-array _X_17 j) (@ get-array arr i)))))`

**Prove:** `(@ _mu_f1_res_out _X_17 a m b arr ca cm k res_in)`

## Goal `G_51`

Precondition of parameter ca in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_NU_50` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(@ = _NU_50 a)`

**Prove:** `(and (@ <= a _NU_50) (@ <= _NU_50 m))`

## Goal `G_52`

Precondition of parameter cm in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_NU_51` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(@ = _NU_51 m)`

**Prove:** `(and (@ <= m _NU_51) (@ <= _NU_51 b))`

## Goal `G_53`

Precondition of parameter k in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_NU_52` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(@ = _NU_52 a)`

**Prove:** `(@ = (@ + _NU_52 m) (@ + a m))`

## Goal `G_54`

Precondition of parameter res_in in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_NU_53` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(and (@ =[] _NU_53 arr) (@ = (@ len _NU_53) (@ len arr)))`

**Prove:** `(@ _mu_f1_res_in _NU_53 a m b arr a m a)`

## Goal `G_55`

The type of (@ f1 a m a arr) must match the type of the result #1 of merge

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_X_18` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(@ _mu_merge_arr arr a m b)`
  * `(@ _mu_f1_res_out _X_18 a m b arr a m a arr)`

**Prove:** `(@ _mu_merge_res _X_18 a m b arr)`

