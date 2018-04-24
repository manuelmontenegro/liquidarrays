## Step 0

Starting from solution:





*Checking goal* `G_1`: Precondition of merge must imply the qualifier of its parameter a

```lisp
(declare-fun arr_length () Int)
(declare-fun arr () (Array Int Int))
(declare-fun b () Int)
(declare-fun m () Int)
(declare-fun a () Int)
(assert (>= arr_length 0))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and (<= 0 a) (<= a m) (<= m b) (<= b arr_length) a!1 a!2)))
(assert (not (<= 0 a)))
```

*Result:* UNSATISFIABLE



## Step 1

Starting from solution:





*Checking goal* `G_2`: Precondition of merge must imply the qualifier of its parameter m

```lisp
(declare-fun arr_length () Int)
(declare-fun a () Int)
(declare-fun arr () (Array Int Int))
(declare-fun b () Int)
(declare-fun m () Int)
(assert (>= arr_length 0))
(assert (<= 0 a))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and (<= 0 a) (<= a m) (<= m b) (<= b arr_length) a!1 a!2)))
(assert (not (<= a m)))
```

*Result:* UNSATISFIABLE



## Step 2

Starting from solution:





*Checking goal* `G_3`: Precondition of merge must imply the qualifier of its parameter b

```lisp
(declare-fun arr_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun arr () (Array Int Int))
(declare-fun b () Int)
(assert (>= arr_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and (<= 0 a) (<= a m) (<= m b) (<= b arr_length) a!1 a!2)))
(assert (not (<= m b)))
```

*Result:* UNSATISFIABLE



## Step 3

Starting from solution:





*Checking goal* `G_4`: Precondition of merge must imply the qualifier of its parameter arr

```lisp
(declare-fun arr_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(assert (>= arr_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and (<= 0 a) (<= a m) (<= m b) (<= b arr_length) a!1 a!2)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (not (and a!1 a!2 (<= b arr_length)))))
```

*Result:* UNSATISFIABLE



## Step 4

Starting from solution:





*Checking goal* `G_5`: The qualified type of the result of merge must imply its postcondition

```lisp
(declare-fun arr_length () Int)
(declare-fun res_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun res () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select res i) (select res j))))))
  (and (= res_length arr_length) a!1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select res i) (select res j))))))
  (not (and (= res_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 5

Starting from solution:





*Checking goal* `G_15`: Precondition of parameter i in call to get-array

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun _NU_8 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= _NU_8 ca))
(assert (not (and (<= 0 _NU_8) (< _NU_8 arr_length))))
```

*Result:* UNSATISFIABLE



## Step 6

Starting from solution:





*Checking goal* `G_16`: Precondition of parameter i in call to get-array

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun _NU_10 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= _NU_10 cm))
(assert (not (and (<= 0 _NU_10) (< _NU_10 arr_length))))
```

*Result:* UNSATISFIABLE



## Step 7

Starting from solution:





*Checking goal* `G_19`: Precondition of parameter i in call to set-array

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun _NU_15 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert b3)
(assert (= _NU_15 k))
(assert (not (and (<= 0 _NU_15) (< _NU_15 res_in_length))))
```

*Result:* UNSATISFIABLE



## Step 8

Starting from solution:





*Checking goal* `G_24`: Precondition of parameter ca in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cap () Int)
(declare-fun kp () Int)
(declare-fun _NU_21 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert b3)
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= cap (+ ca 1)))
(assert (= kp (+ k 1)))
(assert (= _NU_21 cap))
(assert (not (and (<= a _NU_21) (<= _NU_21 m))))
```

*Result:* UNSATISFIABLE



## Step 9

Starting from solution:





*Checking goal* `G_25`: Precondition of parameter cm in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cap () Int)
(declare-fun kp () Int)
(declare-fun _NU_22 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert b3)
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= cap (+ ca 1)))
(assert (= kp (+ k 1)))
(assert (= _NU_22 cm))
(assert (not (and (<= m _NU_22) (<= _NU_22 b))))
```

*Result:* UNSATISFIABLE



## Step 10

Starting from solution:





*Checking goal* `G_26`: Precondition of parameter k in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cap () Int)
(declare-fun kp () Int)
(declare-fun _NU_23 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert b3)
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= cap (+ ca 1)))
(assert (= kp (+ k 1)))
(assert (= _NU_23 kp))
(assert (not (= (+ _NU_23 m) (+ cap cm))))
```

*Result:* UNSATISFIABLE



## Step 11

Starting from solution:





*Checking goal* `G_27`: Precondition of parameter res_in in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun _NU_24_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cap () Int)
(declare-fun kp () Int)
(declare-fun _NU_24 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (>= _NU_24_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert b3)
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= cap (+ ca 1)))
(assert (= kp (+ k 1)))
(assert (and (= _NU_24 resp) (= _NU_24_length resp_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i kp) (<= cap j) (< j m))
                 (<= (select _NU_24 i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i kp) (<= cm j) (< j b))
                 (<= (select _NU_24 i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j kp))
                 (<= (select _NU_24 i) (select _NU_24 j))))))
  (not (and (= _NU_24_length arr_length) a!1 a!2 a!3))))
```

*Result:* UNSATISFIABLE



## Step 12

Starting from solution:





*Checking goal* `G_28`: The type of (@ f1 cap cm kp resp) must match the type of the result #1 of f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun _X_9_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cap () Int)
(declare-fun kp () Int)
(declare-fun _X_9 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (>= _X_9_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert b3)
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= cap (+ ca 1)))
(assert (= kp (+ k 1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_9 i) (select _X_9 j))))))
  (and (= _X_9_length arr_length) a!1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_9 i) (select _X_9 j))))))
  (not (and (= _X_9_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 13

Starting from solution:





*Checking goal* `G_29`: Precondition of parameter i in call to set-array

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun _NU_26 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert (not b3))
(assert (= _NU_26 k))
(assert (not (and (<= 0 _NU_26) (< _NU_26 res_in_length))))
```

*Result:* UNSATISFIABLE



## Step 14

Starting from solution:





*Checking goal* `G_34`: Precondition of parameter ca in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cmp () Int)
(declare-fun kp () Int)
(declare-fun _NU_32 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert (not b3))
(assert (and (= resp (store res_in k arr_cm)) (= resp_length res_in_length)))
(assert (= cmp (+ cm 1)))
(assert (= kp (+ k 1)))
(assert (= _NU_32 ca))
(assert (not (and (<= a _NU_32) (<= _NU_32 m))))
```

*Result:* UNSATISFIABLE



## Step 15

Starting from solution:





*Checking goal* `G_35`: Precondition of parameter cm in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cmp () Int)
(declare-fun kp () Int)
(declare-fun _NU_33 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert (not b3))
(assert (and (= resp (store res_in k arr_cm)) (= resp_length res_in_length)))
(assert (= cmp (+ cm 1)))
(assert (= kp (+ k 1)))
(assert (= _NU_33 cmp))
(assert (not (and (<= m _NU_33) (<= _NU_33 b))))
```

*Result:* UNSATISFIABLE



## Step 16

Starting from solution:





*Checking goal* `G_36`: Precondition of parameter k in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cmp () Int)
(declare-fun kp () Int)
(declare-fun _NU_34 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert (not b3))
(assert (and (= resp (store res_in k arr_cm)) (= resp_length res_in_length)))
(assert (= cmp (+ cm 1)))
(assert (= kp (+ k 1)))
(assert (= _NU_34 kp))
(assert (not (= (+ _NU_34 m) (+ ca cmp))))
```

*Result:* UNSATISFIABLE



## Step 17

Starting from solution:





*Checking goal* `G_37`: Precondition of parameter res_in in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun _NU_35_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cmp () Int)
(declare-fun kp () Int)
(declare-fun _NU_35 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (>= _NU_35_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert (not b3))
(assert (and (= resp (store res_in k arr_cm)) (= resp_length res_in_length)))
(assert (= cmp (+ cm 1)))
(assert (= kp (+ k 1)))
(assert (and (= _NU_35 resp) (= _NU_35_length resp_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i kp) (<= ca j) (< j m))
                 (<= (select _NU_35 i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i kp) (<= cmp j) (< j b))
                 (<= (select _NU_35 i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j kp))
                 (<= (select _NU_35 i) (select _NU_35 j))))))
  (not (and (= _NU_35_length arr_length) a!1 a!2 a!3))))
```

*Result:* UNSATISFIABLE



## Step 18

Starting from solution:





*Checking goal* `G_38`: The type of (@ f1 ca cmp kp resp) must match the type of the result #1 of f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun _X_13_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun arr_ca () Int)
(declare-fun arr_cm () Int)
(declare-fun b3 () Bool)
(declare-fun resp () (Array Int Int))
(declare-fun cmp () Int)
(declare-fun kp () Int)
(declare-fun _X_13 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (>= _X_13_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert b2)
(assert (= arr_ca (select arr ca)))
(assert (= arr_cm (select arr cm)))
(assert (= b3 (<= arr_ca arr_cm)))
(assert (not b3))
(assert (and (= resp (store res_in k arr_cm)) (= resp_length res_in_length)))
(assert (= cmp (+ cm 1)))
(assert (= kp (+ k 1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_13 i) (select _X_13 j))))))
  (and (= _X_13_length arr_length) a!1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_13 i) (select _X_13 j))))))
  (not (and (= _X_13_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 19

Starting from solution:





*Checking goal* `G_39`: Precondition of parameter ca in call to f3

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun _NU_36 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert (not b2))
(assert (= _NU_36 ca))
(assert (not (and (<= a _NU_36) (<= _NU_36 m))))
```

*Result:* UNSATISFIABLE



## Step 20

Starting from solution:





*Checking goal* `G_40`: Precondition of parameter k in call to f3

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun _NU_37 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert (not b2))
(assert (= _NU_37 k))
(assert (not (= (+ _NU_37 m) (+ ca b))))
```

*Result:* UNSATISFIABLE



## Step 21

Starting from solution:





*Checking goal* `G_41`: Precondition of parameter res_in in call to f3

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun _NU_38_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun _NU_38 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= _NU_38_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert (not b2))
(assert (and (= _NU_38 res_in) (= _NU_38_length res_in_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select _NU_38 i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select _NU_38 i) (select _NU_38 j))))))
  (not (and (= _NU_38_length arr_length) a!1 a!2))))
```

*Result:* UNSATISFIABLE



## Step 22

Starting from solution:





*Checking goal* `G_42`: The type of (@ f3 ca k res_in) must match the type of the result #1 of f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun _X_14_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun _X_14 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= _X_14_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert b1)
(assert (not b2))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_14 i) (select _X_14 j))))))
  (and (= _X_14_length arr_length) a!1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_14 i) (select _X_14 j))))))
  (not (and (= _X_14_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 23

Starting from solution:





*Checking goal* `G_43`: Precondition of parameter cm in call to f2

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun _NU_39 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert (not b1))
(assert (= _NU_39 cm))
(assert (not (and (<= m _NU_39) (<= _NU_39 b))))
```

*Result:* UNSATISFIABLE



## Step 24

Starting from solution:





*Checking goal* `G_44`: Precondition of parameter res_in in call to f2

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun _NU_40_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun _NU_40 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= _NU_40_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert (not b1))
(assert (and (= _NU_40 res_in) (= _NU_40_length res_in_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i cm) (<= cm j) (< j b))
                 (<= (select _NU_40 i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j cm))
                 (<= (select _NU_40 i) (select _NU_40 j))))))
  (not (and (= _NU_40_length arr_length) a!1 a!2))))
```

*Result:* UNSATISFIABLE



## Step 25

Starting from solution:





*Checking goal* `G_45`: The type of (@ f2 cm res_in) must match the type of the result #1 of f1

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun _X_15_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun cm () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b1 () Bool)
(declare-fun b2 () Bool)
(declare-fun _X_15 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= _X_15_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (and (<= m cm) (<= cm b)))
(assert (= (+ k m) (+ ca cm)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2 a!3)))
(assert (= b1 (< ca m)))
(assert (= b2 (< cm b)))
(assert (not b1))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_15 i) (select _X_15 j))))))
  (and (= _X_15_length arr_length) a!1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_15 i) (select _X_15 j))))))
  (not (and (= _X_15_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 26

Starting from solution:





*Checking goal* `G_51`: Precondition of parameter i in call to get-array

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun cm () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b3 () Bool)
(declare-fun _NU_45 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= m cm) (<= cm b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i cm) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j cm))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b3 (< cm b)))
(assert b3)
(assert (= _NU_45 cm))
(assert (not (and (<= 0 _NU_45) (< _NU_45 arr_length))))
```

*Result:* UNSATISFIABLE



## Step 27

Starting from solution:





*Checking goal* `G_52`: Precondition of parameter i in call to set-array

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun cm () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b3 () Bool)
(declare-fun arr_cm () Int)
(declare-fun _NU_47 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= m cm) (<= cm b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i cm) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j cm))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b3 (< cm b)))
(assert b3)
(assert (= arr_cm (select arr cm)))
(assert (= _NU_47 cm))
(assert (not (and (<= 0 _NU_47) (< _NU_47 res_in_length))))
```

*Result:* UNSATISFIABLE



## Step 28

Starting from solution:





*Checking goal* `G_55`: Precondition of parameter cm in call to f2

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun cm () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b3 () Bool)
(declare-fun arr_cm () Int)
(declare-fun resp () (Array Int Int))
(declare-fun cmp () Int)
(declare-fun _NU_51 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= m cm) (<= cm b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i cm) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j cm))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b3 (< cm b)))
(assert b3)
(assert (= arr_cm (select arr cm)))
(assert (and (= resp (store res_in cm arr_cm)) (= resp_length res_in_length)))
(assert (= cmp (+ cm 1)))
(assert (= _NU_51 cmp))
(assert (not (and (<= m _NU_51) (<= _NU_51 b))))
```

*Result:* UNSATISFIABLE



## Step 29

Starting from solution:





*Checking goal* `G_56`: Precondition of parameter res_in in call to f2

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun _NU_52_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun cm () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b3 () Bool)
(declare-fun arr_cm () Int)
(declare-fun resp () (Array Int Int))
(declare-fun cmp () Int)
(declare-fun _NU_52 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (>= _NU_52_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= m cm) (<= cm b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i cm) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j cm))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b3 (< cm b)))
(assert b3)
(assert (= arr_cm (select arr cm)))
(assert (and (= resp (store res_in cm arr_cm)) (= resp_length res_in_length)))
(assert (= cmp (+ cm 1)))
(assert (and (= _NU_52 resp) (= _NU_52_length resp_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i cmp) (<= cmp j) (< j b))
                 (<= (select _NU_52 i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j cmp))
                 (<= (select _NU_52 i) (select _NU_52 j))))))
  (not (and (= _NU_52_length arr_length) a!1 a!2))))
```

*Result:* UNSATISFIABLE



## Step 30

Starting from solution:





*Checking goal* `G_57`: The type of (@ f2 cmp resp) must match the type of the result #1 of f2

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun _X_20_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun cm () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b3 () Bool)
(declare-fun arr_cm () Int)
(declare-fun resp () (Array Int Int))
(declare-fun cmp () Int)
(declare-fun _X_20 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (>= _X_20_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= m cm) (<= cm b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i cm) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j cm))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b3 (< cm b)))
(assert b3)
(assert (= arr_cm (select arr cm)))
(assert (and (= resp (store res_in cm arr_cm)) (= resp_length res_in_length)))
(assert (= cmp (+ cm 1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_20 i) (select _X_20 j))))))
  (and (= _X_20_length arr_length) a!1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_20 i) (select _X_20 j))))))
  (not (and (= _X_20_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 31

Starting from solution:





*Checking goal* `G_58`: The type of res_in must match the type of the result #1 of f2

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun _X_21_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun cm () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b3 () Bool)
(declare-fun _X_21 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= _X_21_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= m cm) (<= cm b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i cm) (<= cm j) (< j b))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j cm))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b3 (< cm b)))
(assert (not b3))
(assert (and (= _X_21 res_in) (= _X_21_length res_in_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_21 i) (select _X_21 j))))))
  (not (and (= _X_21_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 32

Starting from solution:





*Checking goal* `G_65`: Precondition of parameter i in call to get-array

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b4 () Bool)
(declare-fun _NU_58 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (= (+ k m) (+ ca b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b4 (< ca m)))
(assert b4)
(assert (= _NU_58 ca))
(assert (not (and (<= 0 _NU_58) (< _NU_58 arr_length))))
```

*Result:* UNSATISFIABLE



## Step 33

Starting from solution:





*Checking goal* `G_66`: Precondition of parameter i in call to set-array

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b4 () Bool)
(declare-fun arr_ca () Int)
(declare-fun _NU_60 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (= (+ k m) (+ ca b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b4 (< ca m)))
(assert b4)
(assert (= arr_ca (select arr ca)))
(assert (= _NU_60 k))
(assert (not (and (<= 0 _NU_60) (< _NU_60 res_in_length))))
```

*Result:* UNSATISFIABLE



## Step 34

Starting from solution:





*Checking goal* `G_71`: Precondition of parameter ca in call to f3

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b4 () Bool)
(declare-fun arr_ca () Int)
(declare-fun resp () (Array Int Int))
(declare-fun kp () Int)
(declare-fun cap () Int)
(declare-fun _NU_66 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (= (+ k m) (+ ca b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b4 (< ca m)))
(assert b4)
(assert (= arr_ca (select arr ca)))
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= kp (+ k 1)))
(assert (= cap (+ ca 1)))
(assert (= _NU_66 cap))
(assert (not (and (<= a _NU_66) (<= _NU_66 m))))
```

*Result:* UNSATISFIABLE



## Step 35

Starting from solution:





*Checking goal* `G_72`: Precondition of parameter k in call to f3

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b4 () Bool)
(declare-fun arr_ca () Int)
(declare-fun resp () (Array Int Int))
(declare-fun kp () Int)
(declare-fun cap () Int)
(declare-fun _NU_67 () Int)
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (= (+ k m) (+ ca b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b4 (< ca m)))
(assert b4)
(assert (= arr_ca (select arr ca)))
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= kp (+ k 1)))
(assert (= cap (+ ca 1)))
(assert (= _NU_67 kp))
(assert (not (= (+ _NU_67 m) (+ cap b))))
```

*Result:* UNSATISFIABLE



## Step 36

Starting from solution:





*Checking goal* `G_73`: Precondition of parameter res_in in call to f3

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun _NU_68_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b4 () Bool)
(declare-fun arr_ca () Int)
(declare-fun resp () (Array Int Int))
(declare-fun kp () Int)
(declare-fun cap () Int)
(declare-fun _NU_68 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (>= _NU_68_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (= (+ k m) (+ ca b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b4 (< ca m)))
(assert b4)
(assert (= arr_ca (select arr ca)))
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= kp (+ k 1)))
(assert (= cap (+ ca 1)))
(assert (and (= _NU_68 resp) (= _NU_68_length resp_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i kp) (<= cap j) (< j m))
                 (<= (select _NU_68 i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j kp))
                 (<= (select _NU_68 i) (select _NU_68 j))))))
  (not (and (= _NU_68_length arr_length) a!1 a!2))))
```

*Result:* UNSATISFIABLE



## Step 37

Starting from solution:





*Checking goal* `G_74`: The type of (@ f3 cap kp resp) must match the type of the result #1 of f3

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun resp_length () Int)
(declare-fun _X_27_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b4 () Bool)
(declare-fun arr_ca () Int)
(declare-fun resp () (Array Int Int))
(declare-fun kp () Int)
(declare-fun cap () Int)
(declare-fun _X_27 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= resp_length 0))
(assert (>= _X_27_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (= (+ k m) (+ ca b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b4 (< ca m)))
(assert b4)
(assert (= arr_ca (select arr ca)))
(assert (and (= resp (store res_in k arr_ca)) (= resp_length res_in_length)))
(assert (= kp (+ k 1)))
(assert (= cap (+ ca 1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_27 i) (select _X_27 j))))))
  (and (= _X_27_length arr_length) a!1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_27 i) (select _X_27 j))))))
  (not (and (= _X_27_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 38

Starting from solution:





*Checking goal* `G_75`: The type of res_in must match the type of the result #1 of f3

```lisp
(declare-fun arr_length () Int)
(declare-fun res_in_length () Int)
(declare-fun _X_28_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun ca () Int)
(declare-fun k () Int)
(declare-fun res_in () (Array Int Int))
(declare-fun b4 () Bool)
(declare-fun _X_28 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= res_in_length 0))
(assert (>= _X_28_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (<= a ca) (<= ca m)))
(assert (= (+ k m) (+ ca b)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i k) (<= ca j) (< j m))
                 (<= (select res_in i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j k))
                 (<= (select res_in i) (select res_in j))))))
  (and (= res_in_length arr_length) a!1 a!2)))
(assert (= b4 (< ca m)))
(assert (not b4))
(assert (and (= _X_28 res_in) (= _X_28_length res_in_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_28 i) (select _X_28 j))))))
  (not (and (= _X_28_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



## Step 39

Starting from solution:





*Checking goal* `G_76`: Precondition of parameter ca in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun _NU_70 () Int)
(assert (>= arr_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (= _NU_70 a))
(assert (not (and (<= a _NU_70) (<= _NU_70 m))))
```

*Result:* UNSATISFIABLE



## Step 40

Starting from solution:





*Checking goal* `G_77`: Precondition of parameter cm in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun _NU_71 () Int)
(assert (>= arr_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (= _NU_71 m))
(assert (not (and (<= m _NU_71) (<= _NU_71 b))))
```

*Result:* UNSATISFIABLE



## Step 41

Starting from solution:





*Checking goal* `G_78`: Precondition of parameter k in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun _NU_72 () Int)
(assert (>= arr_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (= _NU_72 a))
(assert (not (= (+ _NU_72 m) (+ a m))))
```

*Result:* UNSATISFIABLE



## Step 42

Starting from solution:





*Checking goal* `G_79`: Precondition of parameter res_in in call to f1

```lisp
(declare-fun arr_length () Int)
(declare-fun _NU_73_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun _NU_73 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= _NU_73_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (and (= _NU_73 arr) (= _NU_73_length arr_length)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i a) (<= a j) (< j m))
                 (<= (select _NU_73 i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= a i) (< i a) (<= m j) (< j b))
                 (<= (select _NU_73 i) (select arr j)))))
      (a!3 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j a))
                 (<= (select _NU_73 i) (select _NU_73 j))))))
  (not (and (= _NU_73_length arr_length) a!1 a!2 a!3))))
```

*Result:* UNSATISFIABLE



## Step 43

Starting from solution:





*Checking goal* `G_80`: The type of (@ f1 a m a arr) must match the type of the result #1 of merge

```lisp
(declare-fun arr_length () Int)
(declare-fun _X_29_length () Int)
(declare-fun a () Int)
(declare-fun m () Int)
(declare-fun b () Int)
(declare-fun arr () (Array Int Int))
(declare-fun _X_29 () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= _X_29_length 0))
(assert (<= 0 a))
(assert (<= a m))
(assert (<= m b))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j m))
                 (<= (select arr i) (select arr j)))))
      (a!2 (forall ((i Int) (j Int))
             (=> (and (<= m i) (<= i j) (< j b))
                 (<= (select arr i) (select arr j))))))
  (and a!1 a!2 (<= b arr_length))))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_29 i) (select _X_29 j))))))
  (and (= _X_29_length arr_length) a!1)))
(assert (let ((a!1 (forall ((i Int) (j Int))
             (=> (and (<= a i) (<= i j) (< j b))
                 (<= (select _X_29 i) (select _X_29 j))))))
  (not (and (= _X_29_length arr_length) a!1))))
```

*Result:* UNSATISFIABLE



