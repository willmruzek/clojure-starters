(ns user.testing
  (:require
   [clojure.tools.namespace.repl]
   [kaocha.repl :as k]
   [kaocha.watch :as kw]))

(clojure.tools.namespace.repl/disable-reload!)

(defn run-unit []
  (k/run :unit))

(defonce stop-watch-fn (atom #()))

(defn start-watch-unit []
  (let [[_ finish!] (kw/run (k/config))]
    (reset! stop-watch-fn finish!)))

(defn stop-watch-unit []
  (@stop-watch-fn))
