(ns footie-cljs2.core
  (:require [entitas-clj.system :as system]
            [entitas-clj.repository :as repo]
            [entitas-clj.collection :as coll]
            [entitas-clj.repository-observer :as observer]
            [entitas-clj.component :as component]
            [entitas-clj.entity :as entity])
  (:require-macros [entitas-clj.macros :as macros]))

(enable-console-print!)

(println "Hello entitas!")

(def systems (atom (system/create-systems)))
(def repository (atom (repo/create)))
(defn add-system! [s]
  (swap! systems (fn [] (system/add @systems s))))
(defn add-entity! [e]
  (swap! repository (fn [] (repo/add-entity @repository e))))
(defn collection-for-types! [types]
  (let [r (repo/collection-for-types @repository types)
        repo (first r)
        coll (second r)]
        (swap! repository (fn [] repo))
        coll))

(def test-system (system/create nil #(println "repo" %1)))
; (add-system! test-system)

(macros/defcomponent Player)
(macros/defcomponent Ball)
(macros/defcomponent Position x y)
(macros/defcomponent Drawable texture)

(def entity (entity/create :doo (Position. 10 12)))


(println "repository" @repository)

(println "get-type" (component/get-type (Player.)))

(def players (collection-for-types! #{(component/get-type (Position. 0 0))}))
(add-entity! entity)

(println "players" @players)
; (println "count players" (count players))
(println "players-entities" (coll/entities players))
(println "repository" @repository)


(def renderer (js/PIXI.autoDetectRenderer 400 300))
(.appendChild (.-body js/document) (.-view renderer))
(def stage (js/PIXI.Stage. 0x66ff99))
(def bunny-texture (js/PIXI.Texture.fromImage "resources/bunny.png"))
(defn set-position [sprite x y]
  (set! (.-position.x sprite) x)
  (set! (.-position.y sprite) y))

(defn set-anchor [sprite x y]
  (set! (.-anchor.x sprite) x)
  (set! (.-anchor.y sprite) y))

(defn create-simple [texture]
  (def sprite (js/PIXI.Sprite. texture))
  (set-position sprite 200 100)
  (set-anchor sprite 0.5 0.5)
  (. stage addChild sprite)
  sprite)


(create-simple bunny-texture)

(defn tick []
  (system/execute @systems @repository)
  (. renderer render stage)
  (.requestAnimationFrame js/window tick))

(tick)
