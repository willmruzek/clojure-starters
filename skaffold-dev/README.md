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

## TODO

- [x] Workflow: Develop
- [x] Workflow: Test
  - [x] `clj -T:build test`
  - [x] Test after Skaffold builds
  - [x] Testing in REPL
- [ ] Workflow: Push to GCP GCR
- [ ] Workflow: Push to GCP GAR
- [ ] Workflow: Push to AWS ECR
- [ ] Workflow: Push to AWS ACR
- [ ] Workflow: Push to local docker
