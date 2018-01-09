(ns flocktory-test.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :as jetty]
            [environ.core :as env]
            [flocktory-test.statistics :as stat]))

(defn to-vector [x]
  (if (vector? x)
    x
    [x]))

(defroutes app-routes
  (GET "/search"
       {{:strs [query]} :query-params}
       (->> query
            to-vector
            stat/frequencies-by-words
            str))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn -main []
  (jetty/run-jetty app {:port (Integer/parseInt (env/env :port))}))
