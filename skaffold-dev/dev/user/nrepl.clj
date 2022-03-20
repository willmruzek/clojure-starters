(ns user.nrepl
  (:require [integrant.core :as ig]
            [nrepl.server :as nrepl]
            [cider.nrepl :refer [cider-nrepl-handler]]))

(def config {:backend/nrepl {:bind "0.0.0.0"
                             :port 3177}})

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
