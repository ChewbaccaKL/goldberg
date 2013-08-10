(ns goldberg.variations.canone-alla-quarta
  (:use
    [leipzig.scale]
    [leipzig.melody]
    [leipzig.canon]
    [leipzig.live]
    [goldberg.instrument]
    [overtone.live :only [midi->hz now at ctl]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Abstractions                                 ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn run [[from & tos]]
  (if-let [to (first tos)]
    (let [up-or-down (if (<= from to)
                       (range from to)
                       (reverse (range (inc to) (inc from))))]
      (concat up-or-down (run tos)))
    [from]))

(def repeats (partial mapcat #(apply repeat %)))
(def runs (partial mapcat run))
(def triples (partial mapcat #(repeat 3 %)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Part I                                       ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def melody1 
  (let [call
        (phrase
          (repeats [[2 1/4] [1 1/2] [14 1/4] [1 3/2]]) 
          (runs [[0 -1 3 0] [4] [1 8]])) 
        response
        (phrase
          (repeats [[10 1/4] [1 1/2] [2 1/4] [1 9/4]]) 
          (runs [[7 -1 0] [0 -3]])) 
        development
        (phrase
          (repeats [[1 1] [11 1/4] [1 1/2] [1 1] [1 3/4]
                    [11 1/4] [1 13/4]])
          (runs [[4] [2 -3] [-1 -2] [0] [3 5] [1] [1 2]
                 [-1 1 -1] [5 0]]))
        interlude 
        (phrase (repeats [[15 1/4] [1 10/4]]) 
                (runs [[-1 5] [6 -3]]))
        buildup 
        (phrase
          (repeats [[1 3/4] [7 1/4] [1 1/2] [2 1/4] [1 5/4] [11 1/4] [1 6/4] [4 1/2]])
          (runs [[3 1 7] [0 -1 0] [2 -2 0 -1] [1 -2] [4 1]])) 
        finale
        (phrase
          (repeats [[1 1/2] [1 6/4] [1 1/2] [2 1/4] [1 1] [3 1/4] [1 1/2] [1 1/4] [1 1]])             
          (runs [[6] [0 -2] [1 -2 -1] [4 3 4]]))]
    (->> (after 1/2 call)
         (then response)
         (then development)
         (then interlude)
         (then buildup)
         (then finale)
         (where :part (is :dux)))))

(def bass1
  (let [crotchets-a
        (phrase (repeat 9 1)
                (triples (run [-7 -9]))) 
        twiddle 
        (phrase (repeats [[1 1/4] [1 5/4] [2 1/4] [2 1/2]])
                (runs [[-10] [-17] [-11 -13] [-11]])) 
        crotchets-b
        (phrase
          (repeat 9 1)
          (triples (run [-12 -10]))) 
        elaboration
        (phrase
          (repeats [[1 3/4] [9 1/4] [1 1/2] [1 1] [2 1/4] [3 1/2] [1 1]])
          (runs [[-7] [-12] [-9 -11] [-9 -13 -12] [-14] [-7 -8 -7] [-9 -8] [-5]])) 
        busy 
        (phrase
          (repeats [[2 1/4] [2 1/2] [4 1/4] [4 1/2] [4 1/4] [3 1/2] [1 7/4]])
          (runs [[-12 -10] [-12] [-9 -7 -9 -8 -11 -9 -11] [-9] [-11] [-13]])) 
        finale 
        (phrase
          (repeats [[7 1/4] [1 1/2] [1 3/4] [23 1/4] [2 1/2] [1 1]])
          (runs [[-10 -6 -8 -7] [-14] [-9 -6] [-8 -10] [-5] [-12] [-9 -11] [-13]
                 [-10] [-7 -6] [-9] [-11] [-13] [-10 -9 -11 -10] [-13] [-17]]))] 
    (->> crotchets-a (then twiddle) (then crotchets-b) (then elaboration) (then busy) (then finale)
         (where :part (is :bass)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Part II                                      ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def melody2
  (let [theme
        (phrase (repeats [[2 1/4] [1 1/2] [6 1/4] [1 5/4] [5 1/4] [1 1/2] [1 3/4] [3 1/4] [1 1/2] [1 1]])
                (runs [[-3 -2 -6 -3] [-10 -9] [-11 -9 -10] [-8 -9 -8 -10 -9] [-4]]))
        response
        (phrase (repeats [[1 1/2] [12 1/4]])
                (runs [[-9 -10 -9 -11 -2]]))
        complicated 
        (phrase (repeats [[1 7/2] [2 1/4] [3 1/2] [2 1/4] [1 2]])
                (runs [[-2 -3 -2 -3 -2] [-2] [-2 -4 -3]]))
        thence 
        (phrase (repeats [[1 5/4] [11 1/4] [1 13/4]])
                (runs [[-1 -3] [-1 -4] [-4 -8 -7]]))
        blah 
        (phrase (repeats [[11 1/4] [1 7/2] [3 1/2] [4 1/4] [4 1/2] [1 3/4] [1 1/4]])
                (runs [[1 -1 0 -3 -2 -5 -4] [0 -3] [-5 -4] [-6 -4 -8]])) 
        finale 
        (phrase (repeats [[1 5/4] [11 1/4] [1 1/2] [1 3/4] [1 1/4] [1 1/2] [1 3]])
                (runs [[-7] [-5 -12] [-10] [-7] [-5] [-3] [-7 -6] [-8 -7]]))] 
    (->> (after 1/2 theme) (then response) (then complicated) (then thence) (then blah) (then finale) (where :part (is :dux)))))

(def bass2
  (let [intro
        (phrase (repeats [[3 1] [5 1/2] [2 1/4] [1 1/2] [1 1] [4 1/2] [1 7/2]])
                (runs [[-10] [-10 -12 -11 -14 -11 -12 -11] [-9] [-13] [-11 -12]])) 
        development 
        (phrase (repeats [[5 1/2] [24 1/4]])
                (runs [[-9 -3 -5 -4 -7 -6] [-8 -4 -6 -5] [-8] [-10] [-8] [-12] [-10 -12]]))
        up-n-down 
        (phrase (repeats [[8 1/4] [3 1/2] [1 3/4] [7 1/4] [1 1/2] [1 3/4] [7 1/4] [1 1/2] [1 3/4]])
                (runs [[-9] [-11 -14] [-12] [-9 -10 -9 -11] [-4] [-9 -11 -10 -13 -12] [-5] [-10 -12 -11 -14 -13] [-6]]))
        down 
        (phrase (repeats [[27 1/4] [1 5/4] [3 1/4] [1 3/2]])
                (runs [[-5 -7 -6 -9 -8 -11 -10 -13 -12 -15 -14] [-6 -8 -7 -10 -9 -11] [-9 -10]])) 
        finale 
        (phrase (repeats [[1 2] [3 1/2] [1 5/4] [3 1/4] [1 1]])
                (runs [[-13] [-9] [-11 -10] [-14] [-12] [-10] [-8 -7]]))] 
    (->> intro (then development) (then up-n-down) (then down) (then finale)
         (where :part (is :bass)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Canon                                        ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn canone-alla-quarta [f notes]
  (canon
    (comp (interval -3)
          mirror
          (simple 3)
          (partial where :part (is :comes))
          f)
    notes))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Arrangement                                  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;(defmethod play-note :default [{midi :pitch}] (-> midi midi->hz harpsichord))

; Warning: Using the sampled-piano will download and cache 200MB of samples
(comment
  (use 'overtone.inst.sampled-piano) 
  (defmethod play-note :default [{midi :pitch, start :time, duration :duration}]
    (let [id (sampled-piano midi)]
      (at (+ start duration) (ctl id :gate 0))))
) 

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Play                                         ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def piece 
  (->>; (canone-alla-quarta #(drop-last 6 %) melody1)
      ; (with bass1) 
      ; (then
         (with
               (canone-alla-quarta #(drop-last 4 %) melody2)
               bass2)
       ;  ) 
       (where :pitch (comp G major))
       (where :time (bpm 90)) 
       (where :duration (bpm 90)))) 

(comment
  (play piece)
)
