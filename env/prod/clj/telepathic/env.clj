(ns telepathic.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[telepathic started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[telepathic has shut down successfully]=-"))
   :middleware identity})
