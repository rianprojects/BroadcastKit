# DC Plugin — Android Camera & Control App

Aplikasi Android pendamping untuk OBS: streaming kamera HP sebagai sumber video (MJPEG) ke OBS/PC di jaringan yang sama, plus kontrol tambahan seperti tally light, timer, dan soundboard.

## Fitur

- **Camera streaming** — stream kamera HP via MJPEG server ke OBS/browser di jaringan lokal.
- **Tally light** — indikator status live/preview dari OBS.
- **Timer** — activity timer untuk kebutuhan produksi.
- **Soundboard** — pemutar sound effect cepat.
- **Stats** — informasi statistik koneksi/stream.
- **OBS WebSocket integration** — komunikasi dua arah dengan OBS Studio.
- Foreground service agar streaming tetap jalan saat aplikasi di-background.

## Struktur Proyek

```
android/
├── app/
│   └── src/main/
│       ├── java/com/dcplugin/cam/   # Source code Kotlin
│       ├── res/                     # Layout, drawable, values
│       └── AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew / gradlew.bat
```

## Requirements

- Android Studio (Koala atau lebih baru direkomendasikan)
- Android SDK
- Perangkat Android dengan kamera (min. sesuai `minSdk` di `app/build.gradle.kts`)
- OBS Studio dengan plugin/WebSocket aktif (opsional, untuk integrasi kontrol)

## Cara Build & Jalankan

1. Clone repo:
   ```bash
   git clone https://github.com/rianprojects/android-dcplugin.git
   cd android-dcplugin
   ```
2. Buka folder ini di Android Studio, biarkan Gradle sync.
3. Sambungkan device/emulator, lalu jalankan (`Run ▶`) atau via CLI:
   ```bash
   ./gradlew assembleDebug
   ```
4. Install APK hasil build ke device, pastikan device dan OBS/PC berada di jaringan Wi-Fi yang sama.

## Izin yang Digunakan

| Permission | Kegunaan |
|---|---|
| `CAMERA` | Mengambil feed kamera untuk streaming |
| `INTERNET` | Server MJPEG & komunikasi WebSocket |
| `ACCESS_WIFI_STATE` / `ACCESS_NETWORK_STATE` | Deteksi alamat IP jaringan lokal |
| `FOREGROUND_SERVICE` / `FOREGROUND_SERVICE_CAMERA` | Menjaga streaming tetap aktif di background |

## Kontribusi & Penggunaan Bebas

Proyek ini **open source dan bebas digunakan**. Silakan:

- Fork, redesign, atau build ulang sesuai kebutuhan/selera kamu.
- Modifikasi fitur, UI, atau arsitektur tanpa perlu izin.
- Gunakan untuk proyek pribadi maupun komersial.

Tidak ada batasan berekspresi — cukup pertahankan notice lisensi di bawah saat mendistribusikan ulang.

## Lisensi

Proyek ini dilisensikan di bawah [MIT License](LICENSE) — bebas dipakai, dimodifikasi, dan didistribusikan ulang, dengan atau tanpa perubahan, untuk keperluan apa pun.
