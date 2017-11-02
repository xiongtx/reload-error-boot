(ns reload-error-boot.core
  (:require [clojure.spec.alpha :as s]
            [clojure.test.check.generators :as gen]))

(let [spec (s/int-in 0 10)
      g (s/gen spec)
      h (gen/->Generator (constantly 1))]
  (println "clojure.spec.alpha generator's classloader: " (.getClassLoader (type g)))
  (println "clojure.test.check.generators generator's classloader: " (.getClassLoader (type h))))
