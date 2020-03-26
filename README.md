# ZIO Bingo

Created following "ZIO by example" blog post https://scalac.io/write-command-line-application-with-zio/

```shell script
sbt bingo/run
```

## Using graalVM

```shell script
sbt graalvm-native-image:packageBin
bingo/target/graalvm-native-image/bingo
```