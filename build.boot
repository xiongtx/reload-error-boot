(def project 'reload-error-boot)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources"}
          :source-paths   #{"src/cljc"}
          :dependencies '[[adzerk/boot-cljs "2.1.4" :exclusions [org.clojure/clojurescript]]
                          [clojure-future-spec "1.9.0-beta4"]
                          [org.clojure/clojure "1.8.0"]
                          [org.clojure/clojurescript "1.9.946"]
                          [org.clojure/test.check "0.9.0" :scope "test"]])

(require
 '[adzerk.boot-cljs :refer [cljs]])
