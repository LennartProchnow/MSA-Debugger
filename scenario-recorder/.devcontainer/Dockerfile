FROM mcr.microsoft.com/devcontainers/java:0-17

ARG MAVEN_VERSION="3.9.1"
RUN su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""