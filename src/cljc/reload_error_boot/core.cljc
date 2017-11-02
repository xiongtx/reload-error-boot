(ns reload-error-boot.core
  (:require [clojure.spec.alpha :as s]
            [clojure.test.check.generators :as gen]))

(let [spec (s/int-in 0 10)
      g (s/gen spec)]
  (println "clojure.test.check.generators Generator? " (gen/generator? g))
  (gen/such-that #(s/valid? spec %) g 100))
