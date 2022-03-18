(ns user
  (:gen-class)
  (:require
   [clojure.tools.namespace.repl]
   [integrant.repl :as igr]
   [myusername.mynewapp.system :refer [config]]
   [user.auto-reset :refer [auto-reset halt-auto-reset]]))

(println "dev/user.clj loaded")
;;(clojure.tools.namespace.repl/disable-reload!)

(defn go []
  (igr/set-prep! (constantly config))
  (igr/go)
  (auto-reset))

(defn halt []
  (igr/halt)
  (halt-auto-reset))

(def reset igr/reset)

(def reset-all igr/reset-all)

(defn -main []
  (go))

(comment
  (go)
  (halt)
  (reset)
  (reset-all)
  (auto-reset)
  (halt-auto-reset))
