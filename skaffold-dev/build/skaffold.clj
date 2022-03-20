(ns skaffold
  (:require
   [clojure.java.io :as io]
   [clojure.data.json :as json]
   [clojure.pprint :refer [pprint]]
   [jibbit.core :as jibbit]
   [clojure.edn :as edn]))

(defmulti get-env (fn [type env-var] type))
(defmethod get-env :string [type env-var] (System/getenv env-var))
(defmethod get-env :bool [type env-var] (boolean (Boolean/valueOf env-var)))

(defn get-jib-config [opts]
  (let [image-name (get-env :string "IMAGE")
        push-image? (get-env :bool "PUSH_IMAGE")
        ;; TODO Support platforms
        ;; For now, use base image matching your host CPU arch (amd64/arm64)
        ;; platforms (get-env :string "PLATFORMS")
        ]
    (-> opts
        (cond-> image-name (assoc-in [:target-image :image-name] image-name))
        (cond-> (not (nil? push-image?)) (assoc-in [:target-image :type]
                                                   (if push-image? :registry :docker))))))

(defn exec-jib [opts]
  (println "Running jib via jibbit with config:")
  (pprint (get-jib-config opts))
  (jibbit/build {:config (get-jib-config opts)}))

(comment
  (exec-jib {:main "myapp.core"
             :target-image {:image-name "org/image"}})

  (exec-jib full-config-example)

  (def full-config-example {:main "myapp.core"
                            :git-url "https://github.com"
                            :user "nobody"
                            :aot true
                            :aliases [:production]
                            :base-image {:image-name "gcr.io/distroless/java"
                                         :type :registry}
                            :target-image {:image-name "gcr.io/my-project/my-repository"
                                           :type :registry
                                           :authorizer {:fn 'jibbit.gcloud/authorizer}}}))
