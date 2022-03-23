# Change Log

All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

Because these are just starters, they will not be officially versioned.

<!--
## yyyy-mm-dd

### Added
  - Added ...
### Changed
### Deprecated
### Removed
### Fixed
### Security
-->

## 2022-03-23

### Fixed

- Test workflow
  - Load test path via :env/test
  - Add `src` and `resources` to Skaffold test dependencies

## 2022-03-22

### Changed

- Rename `clj -T:build skaffold-dev` -> `clj -T:build build-container`
- Consolidate `build/skaffold.clj` logic into `build.clj` and remove `build/skaffold.clj`

## 2022-03-20

### Added

- Document `skaffold dev` args in README
- Add TODOs for further workflows
- Add [lambdaisland/kaocha](https://github.com/lambdaisland/kaocha) as test runner
- Use tools.build as entry point for clojure-specific dev workflows, e.g. `clj -T:build test`, see `build.clj`
- Add `clj -T:build ...` and REPL testing workflow
- Add test workflow via `clj -T:build test` and `run-unit`, etc. in `user` namespace

### Changed

- Move nREPL setup to dev only
- Switch to [beholder](https://github.com/nextjournal/beholder) over [hawk](https://github.com/wkf/hawk) for file watching for auto namespace reloading (more performant)

### Fixed

- Fix call to `skaffold/exec-jib`, use `:config`

## 2022-03-18

### Added

- Add basic Ring backend with (auto) namespace reloading via [Integrant](https://github.com/weavejester/integrant).
- Add working `skaffold dev` setup
