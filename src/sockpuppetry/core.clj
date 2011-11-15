(ns sockpuppetry.core
  (:require [lamina.core :as lamina]
            [aleph.http :as http]))

(def broadcast-channel (lamina/channel))

(defn chat-handler [ch handshake]
  (lamina/receive ch
    (fn [name]
      (lamina/siphon (lamina/map* #(str name ": " %) ch) broadcast-channel)
      (lamina/siphon broadcast-channel ch))))

(defn start []
  (http/start-http-server chat-handler {:port 8080 :websocket true}))

(defn -main []
  (start))
