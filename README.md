# Argo

A small HTTP Kit server for playing around with JSON.


## Usage

### Run Server from a REPL

Use `clj` to start a REPL, then,
```clojure
user=> (require '[argo.server :as argo])
nil

user=> (argo/-main)
#object[clojure.lang.AFunction$1 0x2b8bb184 "clojure.lang.AFunction$1@2b8bb184"]
```

### Run Server from Command Line

To run the server straight from the command line:
```bash
clj -m argo.server
```

### Send Messages to the Server

To send a JSON body into the server:
```bash
curl -H "Content-Type: application/json" -X POST \
  -d "{\"transaction-id\":\"$(uuidgen)\"}" \
  'http://localhost:8080/'
```
