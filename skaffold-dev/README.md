# Clojure + deps.edn + Skaffold

## Quick start

```sh
minikube start
skaffold dev --no-prune=false --cache-artifacts=false --auto-build=false
```

Connect to nrepl at localhost:3177.

## TODO

- [ ] Seperate dev and prod system configs
- [ ] Add auto-namespace reloading as part of dev config
