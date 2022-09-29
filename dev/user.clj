(ns user
  (:gen-class)
  (:require
   [integrant.repl :as igr]
   [user.auto-reset :refer [start-auto-reset stop-auto-reset]]
   [user.testing :refer [run-unit start-watch-unit stop-watch-unit]]
   [user.nrepl]
   [myusername.mynewapp.system]))

(println "dev/user.clj loaded")

(defn go []
  (igr/set-prep! #(merge myusername.mynewapp.system/config
                         user.nrepl/config))
  (igr/go))

(defn halt []
  (igr/halt))

(def reset igr/reset)

(def reset-all igr/reset-all)

(defn -main []
  (go))

(comment
  (go)
  (halt)

  (reset)
  (reset-all)

  (start-auto-reset)
  (stop-auto-reset)

  (run-unit)
  (start-watch-unit)
  (stop-watch-unit))
