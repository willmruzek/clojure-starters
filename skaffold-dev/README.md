# Clojure + deps.edn + Skaffold

## Quick start

```sh
minikube start
skaffold dev --auto-build=false --no-prune=false --cache-artifacts=false
```

| Options                                    | Reason                                                                     |
| ------------------------------------------ | -------------------------------------------------------------------------- |
| `--auto-build=false`                       | Use file sync only, allows for REPL workflow                               |
| `--no-prune=false --cache-artifacts=false` | Always start fresh and clean up after                                      |
| `--skip-tests`                             | Skip tests (useful for `skaffold dev` rebuild with currently broken tests) |

Connect to nrepl at `localhost:3177`.

## Usage

### Testing

#### One-time

To run tests on demand, connect to the nREPL and run:

```clj
(start-auto-reset) ; Run once
(run-unit) ; Run as desired
```

#### Auto-run

Unfortunately in-REPL auto-run testing with kaocha `(start-watch-unit)` does not work at the moment.

Thankfully, `skaffold dev` will automatically re-run tests when they change, assuming the `--skip-tests` flag is not applied.

## TODO

- [ ] Add usage docs
  - [ ] Development
  - [x] Testing
  - [ ] Deploy to registry
- [x] Workflow: Develop
  - [ ] Allow dev to `start-auto-reset` on launch via `clj -T:build run-dev [opts]` (otherwise you need to start a REPL and invoke `start-auto-reset`)
- [x] Workflow: Test
  - [x] `clj -T:build test`
  - [x] Test after Skaffold builds
  - [x] Testing in REPL
- [ ] Workflow: Push to GCP GCR
- [ ] Workflow: Push to GCP GAR
- [ ] Workflow: Push to AWS ECR
- [ ] Workflow: Push to AWS ACR
- [ ] Workflow: Push to local docker
