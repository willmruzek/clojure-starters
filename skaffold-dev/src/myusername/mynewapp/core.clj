(ns myusername.mynewapp.core
  (:require
   [integrant.core :as ig]
   [myusername.mynewapp.system :refer [config]]))

(defn run []
  (ig/init config))
