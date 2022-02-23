(ns telepathic.routes.home
  (:require
   [telepathic.layout :as layout]
   [clojure.java.io :as io]
   [taoensso.sente.server-adapters.immutant      :refer (get-sch-adapter)]
   [telepathic.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
   [taoensso.sente :as sente]))

(defn home-page [request]
  (layout/render request "home.html"))

(let [{:keys [ch-recv send-fn connected-uids
              ajax-post-fn ajax-get-or-ws-handshake-fn]}
      (sente/make-channel-socket! (get-sch-adapter) {})]

  (def ring-ajax-post                ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk                       ch-recv) ; ChannelSocket's receive channel
  (def chsk-send!                    send-fn) ; ChannelSocket's send API fn
  (def connected-uids                connected-uids) ; Watchable, read-only atom
  )

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/docs" {:get (fn [_]
                    (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                        (response/header "Content-Type" "text/plain; charset=utf-8")))}]
   ["/chsk" {:get (fn [req] (ring-ajax-get-or-ws-handshake req))
						 :post (fn [req] (ring-ajax-post req))}]])
