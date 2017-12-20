(def project 'reload-error-boot)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources"}
          :source-paths   #{"src/cljc"}
          :dependencies '[[adzerk/boot-cljs "2.1.4" :exclusions [org.clojure/clojurescript]]
                          [org.clojure/tools.namespace "0.2.11"]
                          [org.clojure/spec.alpha "0.1.143"]
                          [org.clojure/clojure "1.9.0"]
                          [org.clojure/clojurescript "1.9.946"]
                          [org.clojure/test.check "0.10.0-alpha2" :scope "test"]])

(require
 '[adzerk.boot-cljs :refer [cljs]])
