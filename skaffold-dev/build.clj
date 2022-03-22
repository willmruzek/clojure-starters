(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as bb]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.pprint :refer [pprint]]
            [jibbit.core :as jibbit]))

(def lib 'net.clojars.myusername/mynewapp)
(def version "0.1.0-SNAPSHOT")
(def main 'myusername.mynewapp)

(defn run [_]
  (bb/run-task {} [:run]))

(defn run-dev [_]
  (bb/run-task
   {:main-args ["-m" "user"]}
   [:env/dev :env/test]))

(defn build-container [_]
  (let [image-name (System/getenv "IMAGE")
        push-image? (boolean (Boolean/valueOf "PUSH_IMAGE"))
        ;; TODO Support platforms
        ;; For now, use base image matching your host CPU arch (amd64/arm64)
        ;; platforms (get-env :string "PLATFORMS")

        config {:aliases [:env/dev :env/test]
                :main 'user
                :working-dir "/home/app"
                :base-image {:image-name "arm64v8/openjdk:11.0.14.1-jdk-bullseye"
                             :type :registry}
                :target-image {:image-name "myusername/mynewapp"
                               :type :docker}}

        config-with-env (-> config
                            (cond-> image-name (assoc-in [:target-image :image-name] image-name))
                            (cond-> (not (nil? push-image?)) (assoc-in [:target-image :type]
                                                                       (if push-image? :registry :docker))))
        _ (do (println "Building container with jibbit:")
              (pprint config-with-env))]
    (jibbit/build {:config config-with-env})))

(defn test "Run the tests." [opts]
  (-> opts
      (assoc :main-args ["-m" "kaocha.runner"])
      (assoc :aliases [:env/test])
      (bb/run-tests)))

;; Not using right now
#_(defn ci "Run the CI pipeline of tests (and build the uberjar)." [opts]
    (-> opts
        (assoc :lib lib :version version :main main)
        (bb/run-tests)
        (bb/clean)
        (bb/uber)))
