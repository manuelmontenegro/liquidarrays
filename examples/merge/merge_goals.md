# Goals for `merge_typecheck.clir`

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

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ = (@ len res) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array res i) (@ get-array res j)))))`

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `false`

**Prove:** `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(and (@ = (@ len res_out) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array res_out i) (@ get-array res_out j)))))`

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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

**Prove:** `(and (@ = (@ len _NU_24) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i kp) (@ <= cap j) (@ < j m)) (@ <= (@ get-array _NU_24 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i kp) (@ <= cm j) (@ < j b)) (@ <= (@ get-array _NU_24 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j kp)) (@ <= (@ get-array _NU_24 i) (@ get-array _NU_24 j)))))`

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (@ = (@ len _X_9) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_9 i) (@ get-array _X_9 j)))))`

**Prove:** `(and (@ = (@ len _X_9) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_9 i) (@ get-array _X_9 j)))))`

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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

**Prove:** `(and (@ = (@ len _NU_35) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i kp) (@ <= ca j) (@ < j m)) (@ <= (@ get-array _NU_35 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i kp) (@ <= cmp j) (@ < j b)) (@ <= (@ get-array _NU_35 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j kp)) (@ <= (@ get-array _NU_35 i) (@ get-array _NU_35 j)))))`

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
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
  * `(and (@ = (@ len _X_13) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_13 i) (@ get-array _X_13 j)))))`

**Prove:** `(and (@ = (@ len _X_13) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_13 i) (@ get-array _X_13 j)))))`

## Goal `G_39`

Precondition of parameter ca in call to f3

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(@ = _NU_36 ca)`

**Prove:** `(and (@ <= a _NU_36) (@ <= _NU_36 m))`

## Goal `G_40`

Precondition of parameter k in call to f3

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
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(@ = _NU_37 k)`

**Prove:** `(@ = (@ + _NU_37 m) (@ + ca b))`

## Goal `G_41`

Precondition of parameter res_in in call to f3

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
  * `_NU_38` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(and (@ =[] _NU_38 res_in) (@ = (@ len _NU_38) (@ len res_in)))`

**Prove:** `(and (@ = (@ len _NU_38) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array _NU_38 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array _NU_38 i) (@ get-array _NU_38 j)))))`

## Goal `G_42`

The type of (@ f3 ca k res_in) must match the type of the result #1 of f1

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
  * `_X_14` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `b1`
  * `(not b2)`
  * `(and (@ = (@ len _X_14) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_14 i) (@ get-array _X_14 j)))))`

**Prove:** `(and (@ = (@ len _X_14) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_14 i) (@ get-array _X_14 j)))))`

## Goal `G_43`

Precondition of parameter cm in call to f2

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
  * `_NU_39` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(@ = _NU_39 cm)`

**Prove:** `(and (@ <= m _NU_39) (@ <= _NU_39 b))`

## Goal `G_44`

Precondition of parameter res_in in call to f2

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
  * `_NU_40` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(and (@ =[] _NU_40 res_in) (@ = (@ len _NU_40) (@ len res_in)))`

**Prove:** `(and (@ = (@ len _NU_40) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array _NU_40 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array _NU_40 i) (@ get-array _NU_40 j)))))`

## Goal `G_45`

The type of (@ f2 cm res_in) must match the type of the result #1 of f1

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
  * `_X_15` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(@ = (@ + k m) (@ + ca cm))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b1 (@ < ca m))`
  * `(<-> b2 (@ < cm b))`
  * `(not b1)`
  * `(and (@ = (@ len _X_15) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_15 i) (@ get-array _X_15 j)))))`

**Prove:** `(and (@ = (@ len _X_15) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_15 i) (@ get-array _X_15 j)))))`

## Goal `G_46`

Precondition of f2 must imply the qualifier of its parameter cm

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `false`

**Prove:** `(and (@ <= m cm) (@ <= cm b))`

## Goal `G_47`

Precondition of f2 must imply the qualifier of its parameter res_in

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `false`

**Prove:** `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`

## Goal `G_48`

The qualified type of the result of f2 must imply its postcondition

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `res_out` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(and (@ = (@ len res_out) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array res_out i) (@ get-array res_out j)))))`

**Prove:** `true`

## Goal `G_49`

Precondition of parameter x_0 in call to <

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `_NU_41` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(@ = _NU_41 cm)`

**Prove:** `true`

## Goal `G_50`

Precondition of parameter x_1 in call to <

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `_NU_42` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(@ = _NU_42 b)`

**Prove:** `true`

## Goal `G_51`

Precondition of parameter i in call to get-array

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `b3` of type `bool`
  * `_NU_45` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b3 (@ < cm b))`
  * `b3`
  * `(@ = _NU_45 cm)`

**Prove:** `(and (@ <= (the int 0) _NU_45) (@ < _NU_45 (@ len arr)))`

## Goal `G_52`

Precondition of parameter i in call to set-array

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `b3` of type `bool`
  * `arr_cm` of type `int`
  * `_NU_47` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b3 (@ < cm b))`
  * `b3`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(@ = _NU_47 cm)`

**Prove:** `(and (@ <= (the int 0) _NU_47) (@ < _NU_47 (@ len res_in)))`

## Goal `G_53`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `b3` of type `bool`
  * `arr_cm` of type `int`
  * `resp` of type `(array int)`
  * `_NU_49` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b3 (@ < cm b))`
  * `b3`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(and (@ =[] resp (@ set-array res_in cm arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = _NU_49 cm)`

**Prove:** `true`

## Goal `G_54`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `b3` of type `bool`
  * `arr_cm` of type `int`
  * `resp` of type `(array int)`
  * `_NU_50` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b3 (@ < cm b))`
  * `b3`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(and (@ =[] resp (@ set-array res_in cm arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = _NU_50 (the int 1))`

**Prove:** `true`

## Goal `G_55`

Precondition of parameter cm in call to f2

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `b3` of type `bool`
  * `arr_cm` of type `int`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `_NU_51` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b3 (@ < cm b))`
  * `b3`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(and (@ =[] resp (@ set-array res_in cm arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(@ = _NU_51 cmp)`

**Prove:** `(and (@ <= m _NU_51) (@ <= _NU_51 b))`

## Goal `G_56`

Precondition of parameter res_in in call to f2

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `b3` of type `bool`
  * `arr_cm` of type `int`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `_NU_52` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b3 (@ < cm b))`
  * `b3`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(and (@ =[] resp (@ set-array res_in cm arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(and (@ =[] _NU_52 resp) (@ = (@ len _NU_52) (@ len resp)))`

**Prove:** `(and (@ = (@ len _NU_52) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cmp) (@ <= cmp j) (@ < j b)) (@ <= (@ get-array _NU_52 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cmp)) (@ <= (@ get-array _NU_52 i) (@ get-array _NU_52 j)))))`

## Goal `G_57`

The type of (@ f2 cmp resp) must match the type of the result #1 of f2

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `b3` of type `bool`
  * `arr_cm` of type `int`
  * `resp` of type `(array int)`
  * `cmp` of type `int`
  * `_X_20` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b3 (@ < cm b))`
  * `b3`
  * `(@ = arr_cm (@ get-array arr cm))`
  * `(and (@ =[] resp (@ set-array res_in cm arr_cm)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = cmp (@ + cm (the int 1)))`
  * `(and (@ = (@ len _X_20) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_20 i) (@ get-array _X_20 j)))))`

**Prove:** `(and (@ = (@ len _X_20) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_20 i) (@ get-array _X_20 j)))))`

## Goal `G_58`

The type of res_in must match the type of the result #1 of f2

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `cm` of type `int`
  * `res_in` of type `(array int)`
  * `b3` of type `bool`
  * `_X_21` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= m cm) (@ <= cm b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i cm) (@ <= cm j) (@ < j b)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j cm)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b3 (@ < cm b))`
  * `(not b3)`
  * `(and (@ =[] _X_21 res_in) (@ = (@ len _X_21) (@ len res_in)))`

**Prove:** `(and (@ = (@ len _X_21) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_21 i) (@ get-array _X_21 j)))))`

## Goal `G_59`

Precondition of f3 must imply the qualifier of its parameter ca

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `false`

**Prove:** `(and (@ <= a ca) (@ <= ca m))`

## Goal `G_60`

Precondition of f3 must imply the qualifier of its parameter k

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `false`

**Prove:** `(@ = (@ + k m) (@ + ca b))`

## Goal `G_61`

Precondition of f3 must imply the qualifier of its parameter res_in

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `false`

**Prove:** `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`

## Goal `G_62`

The qualified type of the result of f3 must imply its postcondition

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `res_out` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(and (@ = (@ len res_out) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array res_out i) (@ get-array res_out j)))))`

**Prove:** `true`

## Goal `G_63`

Precondition of parameter x_0 in call to <

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `_NU_54` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(@ = _NU_54 ca)`

**Prove:** `true`

## Goal `G_64`

Precondition of parameter x_1 in call to <

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `_NU_55` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(@ = _NU_55 m)`

**Prove:** `true`

## Goal `G_65`

Precondition of parameter i in call to get-array

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `_NU_58` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = _NU_58 ca)`

**Prove:** `(and (@ <= (the int 0) _NU_58) (@ < _NU_58 (@ len arr)))`

## Goal `G_66`

Precondition of parameter i in call to set-array

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `_NU_60` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(@ = _NU_60 k)`

**Prove:** `(and (@ <= (the int 0) _NU_60) (@ < _NU_60 (@ len res_in)))`

## Goal `G_67`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `resp` of type `(array int)`
  * `_NU_62` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = _NU_62 k)`

**Prove:** `true`

## Goal `G_68`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `resp` of type `(array int)`
  * `_NU_63` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = _NU_63 (the int 1))`

**Prove:** `true`

## Goal `G_69`

Precondition of parameter x_0 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `resp` of type `(array int)`
  * `kp` of type `int`
  * `_NU_64` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = _NU_64 ca)`

**Prove:** `true`

## Goal `G_70`

Precondition of parameter x_1 in call to +

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `resp` of type `(array int)`
  * `kp` of type `int`
  * `_NU_65` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = _NU_65 (the int 1))`

**Prove:** `true`

## Goal `G_71`

Precondition of parameter ca in call to f3

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `resp` of type `(array int)`
  * `kp` of type `int`
  * `cap` of type `int`
  * `_NU_66` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = _NU_66 cap)`

**Prove:** `(and (@ <= a _NU_66) (@ <= _NU_66 m))`

## Goal `G_72`

Precondition of parameter k in call to f3

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `resp` of type `(array int)`
  * `kp` of type `int`
  * `cap` of type `int`
  * `_NU_67` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(@ = _NU_67 kp)`

**Prove:** `(@ = (@ + _NU_67 m) (@ + cap b))`

## Goal `G_73`

Precondition of parameter res_in in call to f3

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `resp` of type `(array int)`
  * `kp` of type `int`
  * `cap` of type `int`
  * `_NU_68` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(and (@ =[] _NU_68 resp) (@ = (@ len _NU_68) (@ len resp)))`

**Prove:** `(and (@ = (@ len _NU_68) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i kp) (@ <= cap j) (@ < j m)) (@ <= (@ get-array _NU_68 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j kp)) (@ <= (@ get-array _NU_68 i) (@ get-array _NU_68 j)))))`

## Goal `G_74`

The type of (@ f3 cap kp resp) must match the type of the result #1 of f3

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `arr_ca` of type `int`
  * `resp` of type `(array int)`
  * `kp` of type `int`
  * `cap` of type `int`
  * `_X_27` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `b4`
  * `(@ = arr_ca (@ get-array arr ca))`
  * `(and (@ =[] resp (@ set-array res_in k arr_ca)) (@ = (@ len resp) (@ len res_in)))`
  * `(@ = kp (@ + k (the int 1)))`
  * `(@ = cap (@ + ca (the int 1)))`
  * `(and (@ = (@ len _X_27) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_27 i) (@ get-array _X_27 j)))))`

**Prove:** `(and (@ = (@ len _X_27) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_27 i) (@ get-array _X_27 j)))))`

## Goal `G_75`

The type of res_in must match the type of the result #1 of f3

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `ca` of type `int`
  * `k` of type `int`
  * `res_in` of type `(array int)`
  * `b4` of type `bool`
  * `_X_28` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ <= a ca) (@ <= ca m))`
  * `(@ = (@ + k m) (@ + ca b))`
  * `(and (@ = (@ len res_in) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i k) (@ <= ca j) (@ < j m)) (@ <= (@ get-array res_in i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j k)) (@ <= (@ get-array res_in i) (@ get-array res_in j)))))`
  * `(<-> b4 (@ < ca m))`
  * `(not b4)`
  * `(and (@ =[] _X_28 res_in) (@ = (@ len _X_28) (@ len res_in)))`

**Prove:** `(and (@ = (@ len _X_28) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_28 i) (@ get-array _X_28 j)))))`

## Goal `G_76`

Precondition of parameter ca in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_NU_70` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(@ = _NU_70 a)`

**Prove:** `(and (@ <= a _NU_70) (@ <= _NU_70 m))`

## Goal `G_77`

Precondition of parameter cm in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_NU_71` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(@ = _NU_71 m)`

**Prove:** `(and (@ <= m _NU_71) (@ <= _NU_71 b))`

## Goal `G_78`

Precondition of parameter k in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_NU_72` of type `int`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(@ = _NU_72 a)`

**Prove:** `(@ = (@ + _NU_72 m) (@ + a m))`

## Goal `G_79`

Precondition of parameter res_in in call to f1

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_NU_73` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ =[] _NU_73 arr) (@ = (@ len _NU_73) (@ len arr)))`

**Prove:** `(and (@ = (@ len _NU_73) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i a) (@ <= a j) (@ < j m)) (@ <= (@ get-array _NU_73 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ < i a) (@ <= m j) (@ < j b)) (@ <= (@ get-array _NU_73 i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j a)) (@ <= (@ get-array _NU_73 i) (@ get-array _NU_73 j)))))`

## Goal `G_80`

The type of (@ f1 a m a arr) must match the type of the result #1 of merge

**For all**:

  * `a` of type `int`
  * `m` of type `int`
  * `b` of type `int`
  * `arr` of type `(array int)`
  * `_X_29` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) a)`
  * `(@ <= a m)`
  * `(@ <= m b)`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j m)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ < j b)) (@ <= (@ get-array arr i) (@ get-array arr j)))) (@ <= b (@ len arr)))`
  * `(and (@ = (@ len _X_29) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_29 i) (@ get-array _X_29 j)))))`

**Prove:** `(and (@ = (@ len _X_29) (@ len arr)) (forall ((i int) (j int)) (-> (and (@ <= a i) (@ <= i j) (@ < j b)) (@ <= (@ get-array _X_29 i) (@ get-array _X_29 j)))))`

