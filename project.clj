(defproject bitclock-clj "0.1.0-SNAPSHOT"
  :description "A command-line clock made of binary integers to help you practice reading binary."
  :url "https://github.com/lucidmachine/bitclock-clj"
  :license {:name "MIT"}
  :main ^:skip-aot bitclock-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-auto "0.1.3"]
            ;; [lein-checkall "0.1.1"] TODO: Literally all of these fail?
            [lein-shell "0.5.0"]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clojure.java-time "0.3.2"]]
  :aliases {"native" ["shell"
                      "native-image"
                      "--report-unsupported-elements-at-runtime"
                      "--initialize-at-build-time"
                      "-jar" "./target/uberjar/${:uberjar-name:-${:name}-${:version}-standalone.jar}"
                      "-H:Name=./target/${:name}"]})
