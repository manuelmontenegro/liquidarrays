# Goals for `insert_typecheck.clir`

## Goal `G_1`

Precondition of insert must imply the qualifier of its parameter k

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`

**such that**:

  * `(and (@ <= (the int 0) k) (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`

**Prove:** `(@ <= (the int 0) k)`

## Goal `G_2`

Precondition of insert must imply the qualifier of its parameter a

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ <= (the int 0) k) (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`

**Prove:** `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`

## Goal `G_3`

The qualified type of the result of insert must imply its postcondition

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array res i) (@ get-array res j))))`

**Prove:** `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array res i) (@ get-array res j))))`

## Goal `G_4`

Precondition of f1 must imply the qualifier of its parameter m

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `false`

**Prove:** `(@ <= (the int 0) m)`

## Goal `G_5`

Precondition of f1 must imply the qualifier of its parameter k_1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `false`

**Prove:** `(and (@ <= m k_1))`

## Goal `G_6`

Precondition of f1 must imply the qualifier of its parameter a_1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `false`

**Prove:** `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`

## Goal `G_7`

The qualified type of the result of f1 must imply its postcondition

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array res i) (@ get-array res j)))))`

**Prove:** `true`

## Goal `G_8`

Precondition of parameter x_0 in call to >

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `_NU_1` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(@ = _NU_1 m)`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter x_1 in call to >

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `_NU_2` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(@ = _NU_2 (the int 0))`

**Prove:** `true`

## Goal `G_10`

Precondition of parameter x_0 in call to -

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `_NU_4` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = _NU_4 m)`

**Prove:** `true`

## Goal `G_11`

Precondition of parameter x_1 in call to -

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `_NU_5` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = _NU_5 (the int 1))`

**Prove:** `true`

## Goal `G_12`

Precondition of parameter i in call to get-array

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `_NU_7` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = _NU_7 m1)`

**Prove:** `(and (@ <= (the int 0) _NU_7) (@ < _NU_7 (@ len a_1)))`

## Goal `G_13`

Precondition of parameter i in call to get-array

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `_NU_9` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = _NU_9 m)`

**Prove:** `(and (@ <= (the int 0) _NU_9) (@ < _NU_9 (@ len a_1)))`

## Goal `G_14`

Precondition of parameter x_0 in call to >

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `_NU_10` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(@ = _NU_10 x1)`

**Prove:** `true`

## Goal `G_15`

Precondition of parameter x_1 in call to >

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `_NU_11` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(@ = _NU_11 x2)`

**Prove:** `true`

## Goal `G_16`

Precondition of parameter i in call to set-array

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `b2` of type `bool`
  * `_NU_14` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(<-> b2 (@ > x1 x2))`
  * `b2`
  * `(@ = _NU_14 m1)`

**Prove:** `(and (@ <= (the int 0) _NU_14) (@ < _NU_14 (@ len a_1)))`

## Goal `G_17`

Precondition of parameter i in call to set-array

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `b2` of type `bool`
  * `a1` of type `(array int)`
  * `_NU_17` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(<-> b2 (@ > x1 x2))`
  * `b2`
  * `(and (@ =[] a1 (@ set-array a_1 m1 x2)) (@ = (@ len a1) (@ len a_1)))`
  * `(@ = _NU_17 m)`

**Prove:** `(and (@ <= (the int 0) _NU_17) (@ < _NU_17 (@ len a1)))`

## Goal `G_18`

Precondition of parameter m in call to f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `b2` of type `bool`
  * `a1` of type `(array int)`
  * `a2` of type `(array int)`
  * `_NU_19` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(<-> b2 (@ > x1 x2))`
  * `b2`
  * `(and (@ =[] a1 (@ set-array a_1 m1 x2)) (@ = (@ len a1) (@ len a_1)))`
  * `(and (@ =[] a2 (@ set-array a1 m x1)) (@ = (@ len a2) (@ len a1)))`
  * `(@ = _NU_19 m1)`

**Prove:** `(@ <= (the int 0) _NU_19)`

## Goal `G_19`

Precondition of parameter k in call to f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `b2` of type `bool`
  * `a1` of type `(array int)`
  * `a2` of type `(array int)`
  * `_NU_20` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(<-> b2 (@ > x1 x2))`
  * `b2`
  * `(and (@ =[] a1 (@ set-array a_1 m1 x2)) (@ = (@ len a1) (@ len a_1)))`
  * `(and (@ =[] a2 (@ set-array a1 m x1)) (@ = (@ len a2) (@ len a1)))`
  * `(@ = _NU_20 k_1)`

**Prove:** `(and (@ <= m1 _NU_20))`

## Goal `G_20`

Precondition of parameter a in call to f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `b2` of type `bool`
  * `a1` of type `(array int)`
  * `a2` of type `(array int)`
  * `_NU_21` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(<-> b2 (@ > x1 x2))`
  * `b2`
  * `(and (@ =[] a1 (@ set-array a_1 m1 x2)) (@ = (@ len a1) (@ len a_1)))`
  * `(and (@ =[] a2 (@ set-array a1 m x1)) (@ = (@ len a2) (@ len a1)))`
  * `(and (@ =[] _NU_21 a2) (@ = (@ len _NU_21) (@ len a2)))`

**Prove:** `(and (@ < k_1 (@ len _NU_21)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m1)) (@ <= (@ get-array _NU_21 i) (@ get-array _NU_21 j)))) (forall ((i int) (j int)) (-> (and (@ <= m1 i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array _NU_21 i) (@ get-array _NU_21 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m1) (@ < m1 j) (@ <= j k_1)) (@ <= (@ get-array _NU_21 i) (@ get-array _NU_21 j)))))`

## Goal `G_21`

The type of (@ f1 m1 k_1 a2) must match the type of the result #1 of f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `b2` of type `bool`
  * `a1` of type `(array int)`
  * `a2` of type `(array int)`
  * `_X_8` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(<-> b2 (@ > x1 x2))`
  * `b2`
  * `(and (@ =[] a1 (@ set-array a_1 m1 x2)) (@ = (@ len a1) (@ len a_1)))`
  * `(and (@ =[] a2 (@ set-array a1 m x1)) (@ = (@ len a2) (@ len a1)))`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array _X_8 i) (@ get-array _X_8 j)))))`

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array _X_8 i) (@ get-array _X_8 j)))))`

## Goal `G_22`

The type of a_1 must match the type of the result #1 of f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `m1` of type `int`
  * `x1` of type `int`
  * `x2` of type `int`
  * `b2` of type `bool`
  * `_X_9` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `b1`
  * `(@ = m1 (@ - m (the int 1)))`
  * `(@ = x1 (@ get-array a_1 m1))`
  * `(@ = x2 (@ get-array a_1 m))`
  * `(<-> b2 (@ > x1 x2))`
  * `(not b2)`
  * `(and (@ =[] _X_9 a_1) (@ = (@ len _X_9) (@ len a_1)))`

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array _X_9 i) (@ get-array _X_9 j)))))`

## Goal `G_23`

The type of a_1 must match the type of the result #1 of f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `m` of type `int`
  * `k_1` of type `int`
  * `a_1` of type `(array int)`
  * `b1` of type `bool`
  * `_X_10` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ <= (the int 0) m)`
  * `(and (@ <= m k_1))`
  * `(and (@ < k_1 (@ len a_1)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j m)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= m i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i m) (@ < m j) (@ <= j k_1)) (@ <= (@ get-array a_1 i) (@ get-array a_1 j)))))`
  * `(<-> b1 (@ > m (the int 0)))`
  * `(not b1)`
  * `(and (@ =[] _X_10 a_1) (@ = (@ len _X_10) (@ len a_1)))`

**Prove:** `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k_1)) (@ <= (@ get-array _X_10 i) (@ get-array _X_10 j)))))`

## Goal `G_24`

Precondition of parameter m in call to f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `_NU_24` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ = _NU_24 k)`

**Prove:** `(@ <= (the int 0) _NU_24)`

## Goal `G_25`

Precondition of parameter k in call to f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `_NU_25` of type `int`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(@ = _NU_25 k)`

**Prove:** `(and (@ <= k _NU_25))`

## Goal `G_26`

Precondition of parameter a in call to f1

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `_NU_26` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(and (@ =[] _NU_26 a) (@ = (@ len _NU_26) (@ len a)))`

**Prove:** `(and (@ < k (@ len _NU_26)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array _NU_26 i) (@ get-array _NU_26 j)))) (forall ((i int) (j int)) (-> (and (@ <= k i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array _NU_26 i) (@ get-array _NU_26 j)))) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ < i k) (@ < k j) (@ <= j k)) (@ <= (@ get-array _NU_26 i) (@ get-array _NU_26 j)))))`

## Goal `G_27`

The type of (@ f1 k k a) must match the type of the result #1 of insert

**For all**:

  * `k` of type `int`
  * `a` of type `(array int)`
  * `_X_11` of type `(array int)`

**such that**:

  * `(@ <= (the int 0) k)`
  * `(and (@ < k (@ len a)) (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ < j k)) (@ <= (@ get-array a i) (@ get-array a j)))))`
  * `(and (forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array _X_11 i) (@ get-array _X_11 j)))))`

**Prove:** `(forall ((i int) (j int)) (-> (and (@ <= (the int 0) i) (@ <= i j) (@ <= j k)) (@ <= (@ get-array _X_11 i) (@ get-array _X_11 j))))`

