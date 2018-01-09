(defproject flocktory-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.0"]
                 [ring/ring-defaults "0.3.1"]
                 [ring "1.6.0"]
                 [environ "1.1.0"]
                 [adamwynne/feedparser-clj "0.5.2"]
                 [com.climate/claypoole "1.1.4"]]
  :aot [flocktory-test.handler]
  :main flocktory-test.handler
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler flocktory-test.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
