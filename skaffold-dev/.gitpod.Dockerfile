FROM gitpod/workspace-full:latest

RUN brew install openjdk@11 clojure clojure-lsp

RUN echo 'export PATH="/home/linuxbrew/.linuxbrew/opt/openjdk@11/bin:$PATH"' >> /home/gitpod/.bashrc
