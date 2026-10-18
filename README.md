# 🕌 Salati

<div align="center">

<p>
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=flat&logo=android&logoColor=white" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white" alt="Language">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=flat&logo=jetpackcompose&logoColor=white" alt="UI">
  <img src="https://img.shields.io/badge/Architecture-Clean%20%2B%20MVI-6DB33F?style=flat" alt="Architecture">
  <img src="https://img.shields.io/badge/Database-Room%20%26%20DataStore-FF8800?style=flat" alt="Database">
</p>

An offline-first Android prayer times app built with **Kotlin**, **Jetpack Compose**, **Ktor**, **MVI** and **Clean Architecture**.<br>
It fetches prayer timings using AlAdhan API, plays Adhan with notifications, schedules custom alarms via `AlarmManager`.<br>
**Salati** handles background alarms and notifications, and integrates with Nominatim for location services.

</div>

---

## 🚀 About the Project

**Salati** is an offline-first app with localization and dark mode support<br>
Users can find Qibla direction, set custom alarms (periodic or one-off) before or after prayers and track prayers streak.<br>
Users can also track minor timings such as: Shorouq, First and last Thirds of the night to verify prayer times.<br>

---

## 🛠️ Tech Stack & Libraries

- **Language:** [Kotlin](https://kotlinlang.org/) — 100% concise and safe codebase.
- **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) & [Material 3](https://m3.material.io/) — Modern declarative UI framework.
- **Architecture:** **MVI (Model-View-Intent)** and **MVVM** with Clean Architecture and Unidirectional Data Flow (UDF).
- **Concurrency:** [Kotlin Coroutines & Flow](https://kotlinlang.org/docs/coroutines-guide.html) — Asynchronous operations and reactive stream handling.
- **Persistence:** [Room](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) — Type-safe relational storage and multi-day range queries for streak analysis.
- **`AlarmManager` & Foreground Services:** Precise prayer notifications, audio focus management, and immediate alarm re-arming (`rescheduleAlarmsQuietly`).
- **Networking:** [Retrofit](https://square.github.io/retrofit/) & [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) — REST API integration and type-safe payload parsing.
- **Location & Geocoding:** Nominatim API with typed error states and rate-compliance execution cooldowns.

---

## 🏛️ Architecture & Design Pattern

The application strictly separates concerns across layers to ensure high testability, scalability, and maintainability:

- **Presentation Layer (Compose UI & ViewModels):** Renders UI states declaratively, handles user inputs, manages navigation state, and processes UI events through reactive `StateFlow` and event channels.
- **Domain Layer (Use Cases & Models):** Encapsulates core business logic (e.g., rolling streak evaluation via `GetCurrentStreakUseCase`, prayer range calculations, custom notification offsets) independent of UI or data frameworks.
- **Data Layer (Repositories, Room DAOs, & DTOs):** Acts as a single source of truth, abstracting local database operations, multi-day range queries (`getForRange`), and remote network data sources.

---

## ✨ Key Features

- **Precise Prayer Times & Countdown:**
  - Real-time countdown tickers to upcoming prayers with accurate local calculations.
  - Multi-day range data retrieval (`getForRange`) for comprehensive historical streak analysis.
- **Custom Alarms & Sound-Specific Notifications:**
  - Dynamic notification channels (`CHANNEL_ID_PREFIX_soundName`) tailored to specific sounds, ensuring audio updates apply correctly since Android locks channel audio settings upon creation.
  - Contextual notification text (`buildOffsetBody`) displaying precise timing offsets (minutes before or after a prayer).
  - Immediate background alarm re-arming (`rescheduleAlarmsQuietly`) upon saving or toggling custom alarms, removing dependency on Home screen reload cycles.
- **Adhan Playback & Audio Management:**
  - Robust `AdhanPlaybackService` with a 5-second timeout (`MUTED_AUTO_DISMISS_MS`) for muted prayers to automatically terminate foreground notifications.
  - Unified player cleanup (`releasePlayer()`) consolidating `MediaPlayer` release logic and audio focus abandonment to eliminate state drift.
- **Interactive Location Resolution & Picker:**
  - Typed `LocationResolution` interface covering network availability, GPS status, permissions, and geocoding failures.
  - Rate-compliant OpenStreetMap Nominatim lookups with a 1-second execution cooldown and explicit trigger mechanisms in `LocationPickerViewModel`.
- **Prayer Tracker & Rolling Streaks:**
  - Historical completion tracking across sliding windows without arbitrary duration caps using `GetCurrentStreakUseCase`.

---

## 📡 Data Sources & APIs

Salati integrates with robust external APIs:
- **Prayer Data:**<br>
  Powered by AlAdhan API for fetching Prayers timings based on location, madhab and multiple parameters.
- **Location Search & Geocoding:**<br>
  Powered by OpenStreetMap Nominatim for location autocomplete and resolution.

---

## 📸 Screenshots

<div align="center">

| Prayer Times | Qibla | Prayers Tracker | Custom Alarms | App Settings |
| :---: | :---: | :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/7daa6791-cb71-4f2b-bafd-81f527a7c0f9" width="200" alt="Home Dashboard" /> | <img src="https://github.com/user-attachments/assets/c95e53ce-92f9-4a4e-a500-5b8d4104985c" width="200" alt="Qibla" /> | <img src="https://github.com/user-attachments/assets/c3b1a5a6-b55c-4601-b0f3-d4f06c92479a" width="200" alt="Tracker" /> | <img src="https://github.com/user-attachments/assets/97cd2882-96ba-4264-a6cd-1e51f6013f5f" width="200" alt="Alarms" /> | <img src="https://github.com/user-attachments/assets/36a31308-8adf-43b0-a7a3-6ad122cddb4c" width="200" alt="Settings" /> |
| <sub><i>Figure 1: Home (Light Mode)</i></sub> | <sub><i>Figure 2: Qibla (Dark Mode)</i></sub> | <sub><i>Figure 3: Tracker (Dark Mode)</i></sub> | <sub><i>Figure 4: Alarms (Light Mode)</i></sub> | <sub><i>Figure 5: Settings (Light/Arabic)</i></sub> |

</div>

---

## ⚙️ Getting Started

To get a local copy up and running, follow these simple steps:

### Prerequisites
- **Android Studio** (Hedgehog / Iguana or newer recommended)
- **JDK 17** or higher
- Android SDK (API level 24 or higher)

### Installation & Setup
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/mhq93/Salati.git](https://github.com/mhq93/Salati.git)
