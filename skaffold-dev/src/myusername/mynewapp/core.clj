(ns myusername.mynewapp.core
  (:gen-class)
  (:require
   [integrant.core :as ig]
   [myusername.mynewapp.system :refer [config]]))

(defn -main []
  (ig/init config))
