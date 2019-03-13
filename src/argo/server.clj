(ns argo.server
  "An HTTP Kit based server."
  (:require [cheshire.core :as json]
            [clojure.tools.logging :as log]
            [org.httpkit.server :as server])
  (:import [org.apache.logging.log4j LogManager Logger]))

; (def logger (LogManager/getLogger (str *ns*)))

(defn app [req]
  ;; TODO Want this to be JSON in logs, not String.
  (log/info (select-keys req [:headers]))
  ; (.info logger (select-keys req [:headers]))
  (with-open [reader (clojure.java.io/reader (:body req))]
    (let [body (json/parse-string (clojure.string/join (line-seq reader)) true)]
      ; (.info logger body)
      (log/info body)
      )
    )
  {:status 200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string
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
  ; (.info logger "Server ready.")
  (log/info "Server ready.")
  (println "Server ready!"))
