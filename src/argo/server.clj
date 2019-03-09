(ns argo.server
  "An HTTP Kit based server."
  (:require [clojure.data.json :as json]
            [org.httpkit.server :as server]))

(defn app [req]
  #_(println (:headers req))
  (with-open [reader (clojure.java.io/reader (:body req))]
    (spit (str (java.time.LocalDateTime/now) ".json")
          (clojure.string/join "\n" (line-seq reader))))
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
  (reset! server (server/run-server #'app {:port 8080})))
