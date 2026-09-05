# Catatan Rilis & Commit (internal, jangan bingung lagi)

## Versi saat ini
- `versionName`: **0.1** — `versionCode`: **1** (di `app/build.gradle.kts`)
- Tag git: `v0.1.0` — release: https://github.com/rianprojects/android-dcplugin/releases/tag/v0.1.0

## Kalau naikin versi
Edit 3 tempat ini biar konsisten (jangan cuma `build.gradle.kts`):
1. `app/build.gradle.kts` → `versionCode` (naik 1 tiap rilis) & `versionName`
2. `app/src/main/res/values/strings.xml` → `app_description` (ada `(vX.X)` di dalamnya)
3. `app/src/main/res/layout/fragment_camera.xml` → teks footer `"BroadcastKit vX.X"`
4. `app/src/main/java/com/dcplugin/cam/MjpegServer.kt` → `<h2>BroadcastKit vX.X</h2>` (halaman web MJPEG viewer)

`app_name` ("BroadcastKit") sudah di `strings.xml`, tidak perlu diubah tiap rilis.

## Build & sign release APK (debug keystore, buat sideload)
```bash
./gradlew assembleRelease --console=plain

BT="/c/Users/PC/AppData/Local/Android/Sdk/build-tools/36.0.0"
cp app/build/outputs/apk/release/app-release-unsigned.apk app-release-aligned.apk
"$BT/zipalign.exe" -f 4 app-release-aligned.apk app-release-aligned2.apk
"$BT/apksigner.bat" sign --ks "/c/Users/PC/.android/debug.keystore" \
  --ks-pass pass:android --key-pass pass:android \
  --out app-release-signed.apk app-release-aligned2.apk
rm -f app-release-aligned.apk app-release-aligned2.apk
```
Hasil: `app-release-signed.apk` (ke-gitignore, jangan di-commit).

Kalau `versionCode` turun dari yang terinstall di device, `adb install -r` akan gagal
(`INSTALL_FAILED_VERSION_DOWNGRADE`) — harus `adb uninstall com.dcplugin.cam` dulu.

## Commit & push
Repo git ada di **folder `android/` ini sendiri** (bukan di `plugin/` root — itu bukan git repo).
Remote: `origin` → https://github.com/rianprojects/android-dcplugin.git, branch `master`.

```bash
git add -A
git commit -m "pesan singkat"
git push origin master
```

## Bikin release baru di GitHub
```bash
git tag vX.X.0
git push origin vX.X.0
gh release create vX.X.0 app-release-signed.apk --title "BroadcastKit vX.X.0" --notes "..."
```

Kalau cuma mau ganti APK di release yang sudah ada (tanpa tag baru):
```bash
gh release upload vX.X.0 app-release-signed.apk --clobber
```

## Hal-hal yang pernah bikin bingung (biar ga keulang)
- `dock_item_bg.xml` ripple **wajib** `android:id="@android:id/mask"` di item shape-nya,
  kalau nggak, dia jadi background permanen (putih), bukan cuma efek klik.
- `<include layout="..." />` yang root-nya `<merge>` **tidak** dapat ViewBinding id
  kalau di-include tanpa `android:id` ke parent non-`<layout>`. Kasih `android:id` di
  `<include>`, dan hindari `<merge>` sebagai root kalau mau akses field-nya lewat binding
  nested (`binding.appToolbar.xxx`).
- Tema app `Theme.CameraPlugin` pakai `...NoActionBar` → `supportActionBar` selalu `null`,
  makanya title/back button toolbar harus diatur manual lewat `toolbar_simple.xml` yang di-include.
