(ns telepathic.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [telepathic.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[telepathic started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[telepathic has shut down successfully]=-"))
   :middleware wrap-dev})
