(declare-fun arr_length () Int)
(declare-fun dummy_length () Int)
(declare-fun _kappa_maximum_begin (Int) Bool)
(declare-fun begin () Int)
(declare-fun _kappa_maximum_end (Int Int) Bool)
(declare-fun end () Int)
(declare-fun _mu_maximum_arr ((Array Int Int) Int Int Int) Bool)
(declare-fun arr () (Array Int Int))
(declare-fun _kappa_maximum_res (Int Int Int (Array Int Int) Int) Bool)
(declare-fun res () Int)
(declare-fun _mu_maximum_dummy
             ((Array Int Int) Int Int (Array Int Int) Int Int Int)
             Bool)
(declare-fun dummy () (Array Int Int))
(assert (>= arr_length 0))
(assert (>= dummy_length 0))
(assert (and (<= 0 begin)))
(assert (and (< begin end)))
(assert (_mu_maximum_arr arr begin end arr_length))
(assert (and (>= arr_length 0) (<= begin res) (< res end)))
(assert (and (>= dummy_length 0) (>= arr_length 0) (forall ((i Int))
               (=> (and (>= i 0) (< i dummy_length))
                   (and (<= (select dummy res) (select dummy i))))) (= dummy arr) (= dummy_length arr_length)))
(assert 
  (not (and  (forall ((i Int))
             (=> (and (<= begin i) (< i end))
                 (<= (select arr res) (select arr i)))))))
  
(assert (forall ((nu Int)) (= (_kappa_maximum_begin nu) (and (<= 0 nu)))))
(assert (forall ((nu Int) (begin Int))
  (= (_kappa_maximum_end nu begin) (and (< begin nu)))))
(assert (forall ((nu Int) (begin Int) (end Int) (arr (Array Int Int)) (arr_length Int))
  (= (_kappa_maximum_res nu begin end arr arr_length)
     (and (>= arr_length 0) (<= begin nu) (< nu end)))))
(assert (forall ((nu (Array Int Int)) (begin Int) (end Int) (nu_length Int))
  (= (_mu_maximum_arr nu begin end nu_length)
     (and (>= nu_length 0) (<= end nu_length)))))
(assert (forall ((nu (Array Int Int))
         (begin Int)
         (end Int)
         (arr (Array Int Int))
         (res Int)
         (nu_length Int)
         (arr_length Int))
  (let ((a!1 (forall ((i Int))
               (=> (and (>= i 0) (< i nu_length))
                   (and (<= (select nu res) (select nu i)))))))
    (= (_mu_maximum_dummy nu begin end arr res nu_length arr_length)
       (and (>= nu_length 0) (>= arr_length 0) a!1 (= nu arr))))))
(check-sat)