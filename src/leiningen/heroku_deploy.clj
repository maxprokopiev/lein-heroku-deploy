(ns leiningen.heroku-deploy
  (:use clansi)
  (:require [clojure.java.shell :refer [sh]]
            [clojure.string :as string]))

(def ^:dynamic *heroku-app-name* nil)

(defmacro with-app-name
  "Sets the app name to use with heroku"
  [app-name & forms]
  `(binding [*heroku-app-name* ~app-name]
     ~@forms))

(defn print-command
  [{exit :exit out :out err :err}]
  (if (string/blank? out)
    (println (style (string/trim-newline err) :red))
    (println (style (string/trim-newline out) :green))))

(defn deploy []
  (print-command
    (sh "git" "push" "-f" "heroku" "master")))

(defn heroku-command
  [command]
  (print-command
    (sh "heroku" command "--app" *heroku-app-name*)))

(defn restart []
  (heroku-command "restart"))

(defn enable-maintenance []
  (heroku-command "maintenance:on"))

(defn disable-maintenance []
  (heroku-command "maintenance:off"))

(defn warm-instance
  [app-url]
  (Thread/sleep 5000)
  (println (str "Accessing " app-url " to warm up your application"))
  (print-command
    (sh "curl" "-Il" app-url)))

(defn heroku-deploy
  "Deploys app to heroku"
  [project & args]
  (with-app-name (:app-name (:heroku project))
    (println (str "Deploying "*heroku-app-name* "..."))
    (enable-maintenance)
    (deploy)
    (restart)
    (disable-maintenance)
    (warm-instance (:app-url (:heroku project)))))
