# Goals for `fill_typecheck.clir`

## Goal `G_1`

Precondition of fill must imply the qualifier of its parameter xs

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`

**Prove:** `(@ _mu_fill_xs xs)`

## Goal `G_2`

Precondition of fill must imply the qualifier of its parameter elem

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`

**Prove:** `(@ _kappa_fill_elem elem xs)`

## Goal `G_3`

The qualified type of the result of fill must imply its postcondition

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `res` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem))))`

**Prove:** `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem))))`

## Goal `G_4`

Precondition of filln must imply the qualifier of its parameter n

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `false`

**Prove:** `(and (@ <= (the int 0) n))`

## Goal `G_5`

Precondition of filln must imply the qualifier of its parameter elem_1

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `false`

**Prove:** `(@ _kappa_filln_elem elem_1 xs elem n)`

## Goal `G_6`

Precondition of filln must imply the qualifier of its parameter xs_1

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `false`

**Prove:** `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`

## Goal `G_7`

The qualified type of the result of filln must imply its postcondition

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `res` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem_1))))`

**Prove:** `true`

## Goal `G_8`

Precondition of parameter x_0 in call to len

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `_NU_1` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(and (@ =[] _NU_1 xs_1) (@ = (@ len _NU_1) (@ len xs_1)))`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter x_0 in call to >=

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(@ = _NU_2 n)`

**Prove:** `true`

## Goal `G_10`

Precondition of parameter x_1 in call to >=

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `_NU_3` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(@ = _NU_3 l)`

**Prove:** `true`

## Goal `G_11`

The type of xs_1 must match the type of the result #1 of filln

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `b` of type `bool`
  * `_X_3` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(<-> b (@ >= n l))`
  * `b`
  * `(and (@ =[] _X_3 xs_1) (@ = (@ len _X_3) (@ len xs_1)))`

**Prove:** `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len _X_3)) (@ = (@ get-array _X_3 i) elem_1))))`

## Goal `G_12`

Precondition of parameter i in call to set-array

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `b` of type `bool`
  * `_NU_7` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(<-> b (@ >= n l))`
  * `(not b)`
  * `(@ = _NU_7 n)`

**Prove:** `(and (@ <= (the int 0) _NU_7) (@ < _NU_7 (@ len xs_1)))`

## Goal `G_13`

Precondition of parameter x_0 in call to +

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `b` of type `bool`
  * `xsp` of type `(array int)`
  * `_NU_9` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(<-> b (@ >= n l))`
  * `(not b)`
  * `(and (@ =[] xsp (@ set-array xs_1 n elem_1)) (@ = (@ len xsp) (@ len xs_1)))`
  * `(@ = _NU_9 n)`

**Prove:** `true`

## Goal `G_14`

Precondition of parameter x_1 in call to +

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `b` of type `bool`
  * `xsp` of type `(array int)`
  * `_NU_10` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(<-> b (@ >= n l))`
  * `(not b)`
  * `(and (@ =[] xsp (@ set-array xs_1 n elem_1)) (@ = (@ len xsp) (@ len xs_1)))`
  * `(@ = _NU_10 (the int 1))`

**Prove:** `true`

## Goal `G_15`

Precondition of parameter n in call to filln

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `b` of type `bool`
  * `xsp` of type `(array int)`
  * `n1` of type `int`
  * `_NU_11` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(<-> b (@ >= n l))`
  * `(not b)`
  * `(and (@ =[] xsp (@ set-array xs_1 n elem_1)) (@ = (@ len xsp) (@ len xs_1)))`
  * `(@ = n1 (@ + n (the int 1)))`
  * `(@ = _NU_11 n1)`

**Prove:** `(and (@ <= (the int 0) _NU_11))`

## Goal `G_16`

Precondition of parameter elem in call to filln

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `b` of type `bool`
  * `xsp` of type `(array int)`
  * `n1` of type `int`
  * `_NU_12` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(<-> b (@ >= n l))`
  * `(not b)`
  * `(and (@ =[] xsp (@ set-array xs_1 n elem_1)) (@ = (@ len xsp) (@ len xs_1)))`
  * `(@ = n1 (@ + n (the int 1)))`
  * `(@ = _NU_12 elem_1)`

**Prove:** `(@ _kappa_filln_elem _NU_12 xsp elem_1 n1)`

## Goal `G_17`

Precondition of parameter xs in call to filln

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `b` of type `bool`
  * `xsp` of type `(array int)`
  * `n1` of type `int`
  * `_NU_13` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(<-> b (@ >= n l))`
  * `(not b)`
  * `(and (@ =[] xsp (@ set-array xs_1 n elem_1)) (@ = (@ len xsp) (@ len xs_1)))`
  * `(@ = n1 (@ + n (the int 1)))`
  * `(and (@ =[] _NU_13 xsp) (@ = (@ len _NU_13) (@ len xsp)))`

**Prove:** `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n1) (@ = (@ get-array _NU_13 i) elem_1))))`

## Goal `G_18`

The type of (@ filln n1 elem_1 xsp) must match the type of the result #1 of filln

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `n` of type `int`
  * `elem_1` of type `int`
  * `xs_1` of type `(array int)`
  * `l` of type `int`
  * `b` of type `bool`
  * `xsp` of type `(array int)`
  * `n1` of type `int`
  * `_X_6` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ <= (the int 0) n))`
  * `(@ _kappa_filln_elem elem_1 xs elem n)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i n) (@ = (@ get-array xs_1 i) elem_1))))`
  * `(@ = l (@ len xs_1))`
  * `(<-> b (@ >= n l))`
  * `(not b)`
  * `(and (@ =[] xsp (@ set-array xs_1 n elem_1)) (@ = (@ len xsp) (@ len xs_1)))`
  * `(@ = n1 (@ + n (the int 1)))`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len _X_6)) (@ = (@ get-array _X_6 i) elem_1))))`

**Prove:** `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len _X_6)) (@ = (@ get-array _X_6 i) elem_1))))`

## Goal `G_19`

Precondition of parameter n in call to filln

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `_NU_14` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(@ = _NU_14 (the int 0))`

**Prove:** `(and (@ <= (the int 0) _NU_14))`

## Goal `G_20`

Precondition of parameter elem in call to filln

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `_NU_15` of type `int`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(@ = _NU_15 elem)`

**Prove:** `(@ _kappa_filln_elem _NU_15 xs elem (the int 0))`

## Goal `G_21`

Precondition of parameter xs in call to filln

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `_NU_16` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(and (@ =[] _NU_16 xs) (@ = (@ len _NU_16) (@ len xs)))`

**Prove:** `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (the int 0)) (@ = (@ get-array _NU_16 i) elem))))`

## Goal `G_22`

The type of (@ filln (the int 0) elem xs) must match the type of the result #1 of fill

**For all**:

  * `xs` of type `(array int)`
  * `elem` of type `int`
  * `_X_7` of type `(array int)`

**such that**:

  * `(@ _mu_fill_xs xs)`
  * `(@ _kappa_fill_elem elem xs)`
  * `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len _X_7)) (@ = (@ get-array _X_7 i) elem))))`

**Prove:** `(forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len _X_7)) (@ = (@ get-array _X_7 i) elem))))`

