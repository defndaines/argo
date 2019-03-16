# Argo

A small HTTP Kit server for playing around with JSON logging.

*This project is now dormant. It was a learning and exploration project for
me.*

I started looking at how to log JSON when I came across a set of security
vulnerabilities in another logging library. Putting that up against
performance benchmarks I'd seen which indicated that Log4J2 was the current
state of the art and that there is a [JSON
Layout](https://logging.apache.org/log4j/2.x/manual/layouts.html#JSONLayout)
option native to Log4J2, I thought it would be simple to just get that
working.

_I was wrong._ I was surprised to find hardly any documentation and answered
questions online on this topic which were not simply tied to using the old
approach of a patterned layout. What I expected was some functionality which
would automatically turn logged messages into JSON if they were passed as a
JSON-able structure. I thought the `objectMessageAsJsonObject` would
accomplish this. The [PR that added this
flag](https://github.com/apache/logging-log4j2/pull/141), however, showed me
that this was not the immediate goal of that feature. The single test adding
this feature only proves that an integer passed as the `message` to the
logging statement will be treated as a numeric value instead of a string. What
I was expecting was something like passing a `HashMap` and having it converted
to a richer JSON structure.

I think there is a path forward here, which it to implement and explicitly add
a Codec which handles data structures passed to the logging function. This
seems like a solvable problem, but outside the scope of what I was looking to
explore at this time.


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
