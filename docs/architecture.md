# Salati Architecture Guide

## 1. Architectural Rules

The Salati codebase adheres to Clean Android Architecture with MVI:

- **Layers per Feature**: Each feature is structured into up to six explicit packages: `<feature>/{domain, data, datasource, presentation, ui, di}`.
- **Domain Layer**: Pure Kotlin only. No dependencies on Android SDK, Jetpack Compose, or UI layers. Contains business entities, repository interfaces, and use cases.
- **Data Layer**: Bridges Domain and DataSource. Contains repository implementations (`*RepoImpl`) and mappers that convert data/persistence models into pure domain entities.
- **DataSource Layer**: Handles I/O and device boundaries (Room DAOs/Entities, Ktor REST clients, Jetpack DataStore preferences, system managers like `AlarmManager` and `SensorManager`).
- **Presentation Layer**: Pure presentation logic (State, Intent, Effect). Exposes reactive state via `StateFlow` and single-shot events via `Channel`. Must not depend on Android Context, resources (`R.*`), or UI packages.
- **UI Layer**: Depends strictly on Presentation. Contains Jetpack Compose screens and components. Dispatches user actions as MVI intents.
- **Use Cases & Intents**:
  - Exactly one use case per file with an `operator fun invoke()`.
  - Intent names represent events describing what happened (e.g., `RetryClicked`, `AlarmToggled`), never imperative commands describing what to do (e.g., `LoadData`, `SaveAlarm`).
- **Code Standards**:
  - One public composable per file matching the file name (private helper composables and `@Preview` functions are permitted within the same file).
  - Delete commented-out code in all touched files.
  - Work strictly on the `dev` branch, one backlog item at a time, building and verifying with Gradle after each change.

---

## 2. Current Package Structure & Layer Wiring

### 2.1 Package Organization (`com.mhq.salati`)

The app is currently a single-module Android project (`:app`):

- **Full-layer Features**: `adhan`, `alarms`, `onboarding`, `permissions`, `prayertracker`, `qibla`, `settings`.
- **Asymmetric / Partial Features**:
  - `location`: Contains `data`, `datasource`, `di`, and `domain`.
  - `locationpicker`: Contains only `presentation` and `ui` (presentation & UI for location selection).
  - `prayertimes`: Contains `data`, `datasource`, `di`, and `domain` (rendered primarily through `home`).
  - `home`: Contains `domain`, `presentation`, and `ui` (composes use cases across features).
  - `connectivity`: Contains `data`, `datasource`, `di`, `domain`, and `ui` (network monitoring).
  - `language`: Contains `domain` and `ui` (language selector dialog).
  - `splash`: Contains `domain` and `presentation`.
  - `shared`: Foundational app infrastructure (`app`, `data`, `datasource`, `di`, `domain`, `presentation`, `ui`).

### 2.2 Layer Wiring & Data Flow

1. **System / Network → DataSource**: Room entities and DAOs (`SalatiDatabase`), Ktor HTTP clients (`KtorClient`), DataStore preferences, and device sensors/alarms.
2. **DataSource → Data**: Repository implementations (`*RepoImpl`) query data sources, map DTOs/entities into domain models, and expose Kotlin `Flow` or suspend functions.
3. **Data → Domain**: Domain repository interfaces define the contracts implemented by Data. Domain use cases encapsulate discrete business operations.
4. **Domain → Presentation**: MVI ViewModels inject domain use cases, handle incoming `Intent`s, update `StateFlow<State>`, and send one-off events via `Channel<Effect>`.
5. **Presentation → UI**: Jetpack Compose screens observe `StateFlow`, trigger side effects via `LaunchedEffect`, and pass user intents into ViewModel functions.
6. **Dependency Injection**: Hilt modules in `<feature>/di/` and `shared/di/` bind interfaces to implementations (`@Binds`) and supply singletons (`@Provides`).

---

## 3. Known Remaining Violations

The following inconsistencies with the target architecture remain in the backlog to be addressed incrementally:

1. **Feature Layer Asymmetry**:
   - `location` and `locationpicker` are split across separate packages instead of forming a unified feature.
   - `home`, `prayertimes`, `connectivity`, `language`, and `splash` lack standard layer parity.
   - Background components (`BroadcastReceiver`, `Service`, `NotificationHelper`) reside in `ui/` rather than `datasource` or a dedicated system component layer.
   - `adhan` retains a `presentation/presenter` subpackage for receiver callbacks.
2. **Presentation Layer Android Dependencies**:
   - **Resource IDs**: ViewModels and Contracts directly reference Android string resources (`R.string.*` in `AlarmsContract`, `AlarmsViewModel`, `HomeViewModel`, `LocationPickerViewModel`, `SettingsViewModel`, etc.).
   - **UI Utility Leak**: Presentation references `UiText` from `shared.ui`, which resolves Android resources via `Context`.
   - **Android / AndroidX SDK Classes**: `SplashViewModel` directly imports `AppCompatDelegate` and `LocaleListCompat`; `HomeViewModel` uses `android.annotation.SuppressLint`; `SettingsViewModel` imports `androidx.annotation.StringRes`; ViewModels inherit from `androidx.lifecycle.ViewModel`.
3. **UI Layer Layering Leaks**:
   - Compose screens and components directly import domain models (`CustomAlarm`, `PrayerName`, `PrayerTimings`, `DayStatus`, `PrayerStatus`, `CompassAccuracy`, `AppLanguage`) instead of dedicated UI models.
   - `CustomAlarmNotifier` (in `ui`) directly injects domain use cases (`ObserveSettingsUseCase`).
   - `AdhanPlaybackService` and `CustomAlarmNotifier` directly import mappers from `shared.data.mapper`.
4. **Imperative Intent Naming**:
   - Several contracts use imperative command names (e.g., `LoadAlarms`, `AddAlarm`, `SaveAlarm`, `DeleteAlarm`, `LoadPrayerTimes`, `ToggleMute`, `SelectTheme`) instead of past-tense event names (e.g., `ScreenLoaded`, `SaveClicked`, `AlarmToggled`).
5. **Commented-Out Code**:
   - Inactive code blocks remain in untouched files (e.g., `StopAdhanPlaybackUseCase.kt`, `ResolveLocationUseCase.kt`, and the deferred autocomplete block in `LocationPickerViewModel.kt`).
