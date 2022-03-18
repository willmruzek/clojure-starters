(ns myusername.mynewapp.system
  (:require [myusername.mynewapp.handler :refer [app]]
            [ring.adapter.jetty :as jetty]
            [integrant.core :as ig]
            [nrepl.server :as nrepl]
            [cider.nrepl :refer [cider-nrepl-handler]]))

(def config
  {:backend/jetty {:port 8080, :handler (ig/ref :backend/run-app)}
   :backend/run-app {}
   :backend/nrepl {:bind "0.0.0.0"
                   :port 3177}})

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

(defmethod ig/init-key :backend/nrepl [_ {:keys [bind port]}]
  (if (and bind port)
    (nrepl/start-server :bind bind :port port :handler cider-nrepl-handler)
    nil))

(defmethod ig/halt-key! :backend/nrepl [_ this]
  (if this
    (nrepl/stop-server this)))

(defmethod ig/suspend-key! :backend/nrepl [_ this]
  this)

(defmethod ig/resume-key :backend/nrepl [_ _ _ old-impl]
  old-impl)
