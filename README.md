# Calculator — Android APK with CI/CD

A minimal Android calculator app built and packaged automatically with GitHub Actions. No local Android SDK or Gradle installation is required; the build environment is declared in the workflow YAML.

## Project layout

```
app/
  src/main/
    AndroidManifest.xml
    java/com/example/calculator/MainActivity.java
    res/layout/activity_main.xml
    res/values/strings.xml, styles.xml
.github/workflows/
  android-debug.yml    # debug APK on push / pull request / manual run
  android-release.yml   # signed release APK on a v* tag
```

Toolchain: AGP 9.0.1, Gradle 9.1.0, compileSdk 35, minSdk 21, JDK 17.

## Debug build

Triggered on push to `main`/`develop`, pull request to `main`, or manual dispatch.
The workflow sets up JDK 17, caches Gradle, runs `./gradlew assembleDebug`, and uploads `app-debug.apk` as a workflow artifact (`app-debug`).

## Signed release build

Triggered by pushing a `v*` tag or manual dispatch. Before the first release, add these secrets in **Settings → Secrets and variables → Actions**:

| Secret | Value |
|---|---|
| `KEYSTORE_BASE64` | Base64-encoded `.jks` keystore |
| `KEYSTORE_PASSWORD` | Keystore password |
| `KEY_ALIAS` | Key alias |
| `KEY_PASSWORD` | Key password |

The keystore is decoded into `$RUNNER_TEMP` at build time; it is never committed or uploaded. The signing config reads these environment variables in `app/build.gradle`.

Generate a keystore (locally or in Codespaces):

```bash
keytool -genkey -v -keystore upload-keystore.jks \
  -alias my-app -keyalg RSA -keysize 2048 -validity 10000
base64 -w0 upload-keystore.jks
```

## Local build (optional)

```bash
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```
