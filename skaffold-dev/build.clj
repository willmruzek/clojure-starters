(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as bb]
            [skaffold :as sk]))

(def lib 'net.clojars.myusername/mynewapp)
(def version "0.1.0-SNAPSHOT")
(def main 'myusername.mynewapp)

(defn run [_]
  (bb/run-task {} [:run]))

(defn run-dev [_]
  (bb/run-task
   {:main-args ["-m" "user"]}
   [:env/dev :env/test]))

(defn skaffold-dev [_]
  (sk/exec-jib {:aliases [:env/dev :env/test]
                :main 'user
                :working-dir "/home/app"
                :base-image {:image-name "arm64v8/openjdk:11.0.14.1-jdk-bullseye"
                             :type :registry}
                :target-image {:image-name "myusername/mynewapp"
                               :type :docker}}))

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
