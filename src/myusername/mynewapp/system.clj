(ns myusername.mynewapp.system
  (:require [myusername.mynewapp.handler :refer [app]]
            [ring.adapter.jetty :as jetty]
            [integrant.core :as ig]
            [nrepl.server :as nrepl]
            [cider.nrepl :refer [cider-nrepl-handler]]))

(def config
  {:backend/run-app {}
   :backend/jetty {:port 8080, :handler (ig/ref :backend/run-app)}})

(defmethod ig/init-key :backend/jetty [_ opts]
  (let [handler (atom (delay (:handler opts)))
        options (-> opts (dissoc :handler) (assoc :join? false))]
    {:handler handler
     :server  (jetty/run-jetty (fn [req] (@@handler req)) options)}))

(defmethod ig/halt-key! :backend/jetty [_ {:keys [server]}]
  (.stop server))

(defmethod ig/suspend-key! :backend/jetty [_ {:keys [handler]}]
  (reset! handler (promise)))

(defmethod ig/resume-key :backend/jetty [key opts old-opts old-impl]
  (if (= (dissoc opts :handler) (dissoc old-opts :handler))
    (do (deliver @(:handler old-impl) (:handler opts))
        old-impl)
    (do (ig/halt-key! key old-impl)
        (ig/init-key key opts))))

(defmethod ig/resolve-key :backend/jetty [_ {:keys [server]}]
  server)

(defmethod ig/init-key :backend/run-app [_ _]
  app)
