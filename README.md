# lein-heroku-deploy

Leinengen plugin to simplify Heroku deploy.

## Usage

Put `[lein-heroku-deploy "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your project.clj.

Add heroku app info to project.clj

```clojure
:heroku {
  :app-name "still-sands-6666"
  :app-url "http://still-sands-6666.herokuapp.com"
}
```

Then simply run

    $ lein heroku-deploy

It will

 * activate maintenance mode
 * push your app to heroku
 * restart app
 * deactivate maintenance mode
 * warm up app

## License

Copyright © 2013 Max Prokopiev

Distributed under the Eclipse Public License, the same as Clojure.
