(ns argo.server
  "An HTTP Kit based server."
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [org.httpkit.server :as server])
  (:import [org.apache.logging.log4j LogManager Logger]))

(defn app [req]
  (log/info (json/write-str (:headers req) :key-fn name))
  (with-open [reader (clojure.java.io/reader (:body req))]
    (log/info (json/write-str (clojure.string/join (line-seq reader)))))
  {:status 200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/write-str
           {:message "hello"
            :ref {:first "Michael"
                  :last "Daines"}})})

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately.
    (@server :timeout 100)
    (reset! server nil)))

(defn -main [& args]
  (reset! server (server/run-server #'app {:port 8080}))
  (log/info "Server ready!"))
