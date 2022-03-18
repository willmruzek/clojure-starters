(ns userr.auto-reset
  (:require
   [clojure.tools.namespace.repl]
   [integrant.repl :as igr]
   [hawk.core :as hawk]))

(clojure.tools.namespace.repl/disable-reload!)

(defn- clojure-file? [_ {:keys [file]}]
  (re-matches #"[^.].*(\.clj|\.edn)$" (.getName file)))

(defn- auto-reset-handler [ctx event]
  (binding [*ns* *ns*]
    (integrant.repl/reset)
    ctx))

(def watcher (atom nil))

(defn halt-auto-reset []
  (hawk/stop! @watcher))

(defn auto-reset
  "Automatically reset the system when a Clojure or edn file is changed in
  `src` or `resources`."
  []
  (let [watcher-ref
        (hawk/watch! [{:paths ["src/" "resources/" #_"dev/src/" #_"dev/resources/"]
                       :filter clojure-file?
                       :handler auto-reset-handler}])]
    (swap! watcher (constantly watcher-ref))))

(comment
  (auto-reset)
  (halt-auto-reset))
