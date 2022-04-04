# Clojure Starters

All starters include:

  - REPL setup (w/ cider-nrepl)
  - Basic testing setup (CLI and REPL)
  - Namespace auto-reload on save when enabled

## List of starters (separated by branch)
  - [x] Base - `starter-base`: Basic web server
    - Ring (server framework)
    - Compjure (routing library) 
    - Integrant (system lifecycle manger)
    - integrant.repl (REPL workflow for Integrant)
    - VSCode [devcontainer](https://code.visualstudio.com/docs/remote/containers) setup
    - [![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/mruzekw/clojure-starters)
  - [ ] Skaffold - `starter-skaffold`: Basic web server with [Skaffold](https://skaffold.dev/) setup
  - [ ] Jib - `starter-jib`: Basic web server with the [Jib](https://github.com/GoogleContainerTools/jib) container builder via [Jibbit](https://github.com/atomisthq/jibbit)
