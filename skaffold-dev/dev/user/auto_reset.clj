(ns user.auto-reset
  (:require
   [clojure.tools.namespace.repl]
   [integrant.repl :as igr]
   [hawk.core :as hawk]
   [nextjournal.beholder :as beholder]))

(clojure.tools.namespace.repl/disable-reload!)

(defn- auto-reset-handler [event]
  (prn event)
  (binding [*ns* *ns*]
    (integrant.repl/reset)))

(defonce ^:private watcher (atom nil))

(defn stop-auto-reset []
  (beholder/stop @watcher))

(defn start-auto-reset
  "Automatically reset the system when a Clojure or edn file is changed in
  `src` or `resources`."
  []
  (reset! watcher
          (beholder/watch auto-reset-handler
                          "src" "dev" "resources" "test")))

(comment
  (start-auto-reset)
  (stop-auto-reset))
