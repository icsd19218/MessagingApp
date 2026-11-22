# Messaging App
___

## Απαιτήσεις
Android SDK: 37.1

## Εργαλεία ανάπτηξης
Jetpack Compose version: 2026.3 x86_64

Android extension: 263.8311.30 x86_64

---
## Kατασκευής εφαρμογής σε Linux μηχανή

### Debug
./gradlew assembleDebug

### Release
./gradelew assembleRelease

___
## Εκτέλεση εφαρμογής
### Εμφάνιση των Android emulator
$ANDROID_SDK_ROOT/emulator/emulator -list-avds

### Εκκίνηση του emulator
$ANDROID_SDK_ROOT/emulator/emulator -avd <avd_name> &

### Εγκατάσταση εφαρμογής μέσω Android γέφυρας
adb install app/build/outputs/apk/release/app-debug.apk
adb adb shell am start -n icsd19218.messagingapp/.MainActivity