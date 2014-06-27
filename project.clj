(defproject footie-cljs2 "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/core.async "0.1.278.0-76b25b-alpha"]
                 [camel-snake-kebab "0.1.5"]]

  :plugins [[lein-cljsbuild "1.0.2"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "footie-cljs2"
              :source-paths ["src"]
              :compiler {
                :output-to "footie_cljs2.js"
                :output-dir "out"
                :externs ["resources/js/pixi.dev.js"]
                :optimizations :none
                :source-map true}}]})
