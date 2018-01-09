(ns flocktory-test.statistics
  (:require [feedparser-clj.core :as feedp]
            [environ.core :as env]
            [com.climate.claypoole :as cp]))

(def pool (cp/threadpool (Integer/parseInt (env/env :max-conn))))

(defn url-by-word
  "return request url for a word"
  [word]
  (str "https://www.bing.com/search?q=" word "&format=rss&count=10"))

(defn links-by-url
  "request url and return urls from search result"
  [url]
  (map :link (:entries (feedp/parse-feed url))))

(defn links-by-url-limited
  "request url and return urls from search result in threadpool"
  [url]
  (let [fut (cp/future pool (links-by-url url))]
     @fut))

(defn parse-domain
  "return second level domain from given url"
  [url]
  (second (re-find #"^[^\/]+\/{2}(?:[^\/]*?)([^\.\/]+\.[^\.\/]+)(\/|$)" url)))

(defn links-by-word
  "make search by given word and return urls from search result"
  [word]
  (->> word
       url-by-word
       links-by-url-limited))

(defn frequencies-by-words
  "make search by given words and return domains frequencies from search results"
  [words]
  (->> words
       (pmap links-by-word)
       (mapcat identity)
       set
       (map parse-domain)
       frequencies))
