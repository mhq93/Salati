# Salati project export

- Generated: 2026-11-12 00:09
- PowerShell: 5.1.19041.6456
- Files embedded: 422
- Files listed only (binary / too large / sensitive): 29
- Secrets: local.properties, google-services.json, keystores and .env files are skipped; key/secret/password/token string literals are replaced with <REDACTED>.

## Git

```text
branch: dev
last commit: a4d0d79 2026-11-11 ` redesign: redesigned Custom Alarms Screen to handle different screen sizes and device rotation + Code refactor and cleanup + adding tests

recent commits:
  a4d0d79 2026-11-11 ` redesign: redesigned Custom Alarms Screen to handle different screen sizes and device rotation + Code refactor and cleanup + adding tests
  54af52c 2026-11-09 redesign: redesigned Tracker Screen to handle different screen sizes and device rotation
  4c4a5cf 2026-11-09 wip: modified Type.kt to handle vertical expansion in UI elements upon switching app language to Arabic, redesigned Home and Qibla to handle different screen sizes and device rotation. Qibla is confirmed done, pending: rest of the screens
  33831c0 2026-11-08 fix: cancelled notification auto-dismiss
  b3980b3 2026-11-07 fix: fixed the black blinking upon language change by changing ConfigChanges and overriding applyOverrideConfiguration in main activity
  f842990 2026-11-07 redesign: reworked QiblaContentSuccess
  ac707b4 2026-11-07 wip: moved location pin to Home instead of Qibla and will show city name only on Home
  ab50ffd 2026-11-07 refactor: reworked Onboarding flow and design
  b30de55 2026-11-06 wip: reworked Onboarding sequence and location permissions flow
  e66d2aa 2026-11-02 fix: attached Hijri date adjustment to Tracker Calendar View
  fb1f440 2026-11-02 feature: added jump-date to DateBanner on Home Screem and locked prayer countdown to today's next prayer
  607639a 2026-11-02 feature: reworked and refactored Onboarding
  53d5ca3 2026-11-01 refactor: architecture cleanups, refactored datastore models, renamed intents and moved some files
  da49d62 2026-11-01 refactor: stop Presentation from holding state and logic that belongs in Domain
  2cbd0d0 2026-11-01 refactor: removed UiText and R.string references to make UI depend only on Presentation

working tree changes (git status --short, first 80):
   M app/src/main/java/com/mhq/salati/alarms/ui/screens/CustomAlarmsContent.kt
   M app/src/main/java/com/mhq/salati/home/domain/model/PrayerCountdown.kt
   M app/src/main/java/com/mhq/salati/home/domain/model/PrayerProgress.kt
   M app/src/main/java/com/mhq/salati/home/domain/model/PrayerTimesError.kt
   M app/src/main/java/com/mhq/salati/home/domain/model/PrayerTimesLoad.kt
   M app/src/main/java/com/mhq/salati/home/domain/model/PrayerTimesTick.kt
   M app/src/main/java/com/mhq/salati/home/domain/model/PrayerWindow.kt
   M app/src/main/java/com/mhq/salati/home/domain/service/DayNavigator.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/CalculateCurrentPrayerNameUseCase.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/CalculatePastPrayersUseCase.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/CalculatePrayerStatusUseCase.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/CalculatePrayerWindowUseCase.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/LoadPrayerTimesUseCase.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/ObservePrayerCountdownUseCase.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/ObservePrayerTimesTicksUseCase.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/RefreshPrayerStatusUseCase.kt
   M app/src/main/java/com/mhq/salati/home/domain/usecases/ResolvePrayerWindowForDisplayUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/model/CoordinatesLookup.kt
   M app/src/main/java/com/mhq/salati/location/domain/model/GeocodeResult.kt
   M app/src/main/java/com/mhq/salati/location/domain/model/LocationPrerequisites.kt
   M app/src/main/java/com/mhq/salati/location/domain/model/LocationResolution.kt
   M app/src/main/java/com/mhq/salati/location/domain/model/LocationSearchResult.kt
   M app/src/main/java/com/mhq/salati/location/domain/model/SavedLocation.kt
   M app/src/main/java/com/mhq/salati/location/domain/repo/GeocoderProvider.kt
   M app/src/main/java/com/mhq/salati/location/domain/repo/LocationProvider.kt
   M app/src/main/java/com/mhq/salati/location/domain/repo/LocationRepository.kt
   M app/src/main/java/com/mhq/salati/location/domain/service/LocationLocalizer.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/CheckLocationPrerequisitesUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/ClearSavedLocationUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/GetCurrentCoordinatesUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/GetLocalizedLocationUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/GetSavedLocationUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/ResolveLocationUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/ReverseGeocodeLocationUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/SaveManualLocationUseCase.kt
   M app/src/main/java/com/mhq/salati/location/domain/usecases/SearchLocationByNameUseCase.kt
   M app/src/main/java/com/mhq/salati/prayertimes/domain/model/HijriDate.kt
   M app/src/main/java/com/mhq/salati/prayertimes/domain/model/HijriDateAdjustment.kt
   M app/src/main/java/com/mhq/salati/prayertimes/domain/model/PrayerDate.kt
   M app/src/main/java/com/mhq/salati/prayertimes/domain/model/PrayerTimesResult.kt
   M app/src/main/java/com/mhq/salati/prayertimes/domain/model/PrayerTimings.kt
   M app/src/main/java/com/mhq/salati/prayertimes/domain/repo/PrayerTimesRepository.kt
   M app/src/main/java/com/mhq/salati/prayertimes/domain/usecases/GetCachedPrayerTimesUseCase.kt
   M app/src/main/java/com/mhq/salati/prayertimes/domain/usecases/GetPrayerTimesUseCase.kt
   M app/src/main/java/com/mhq/salati/prayertracker/domain/model/DayStatus.kt
   M app/src/main/java/com/mhq/salati/prayertracker/domain/model/PrayerStatus.kt
   M app/src/main/java/com/mhq/salati/prayertracker/domain/repo/PrayerTrackerRepository.kt
   M app/src/main/java/com/mhq/salati/prayertracker/domain/usecases/GetCurrentStreakUseCase.kt
   M app/src/main/java/com/mhq/salati/prayertracker/domain/usecases/GetDuePrayersUseCase.kt
  RM app/src/main/java/com/mhq/salati/home/domain/usecases/ObserveMonthDayStatusUseCase.kt -> app/src/main/java/com/mhq/salati/prayertracker/domain/usecases/ObserveMonthDayStatusUseCase.kt
   M app/src/main/java/com/mhq/salati/prayertracker/domain/usecases/ObservePrayerRecordsForDateUseCase.kt
   M app/src/main/java/com/mhq/salati/prayertracker/domain/usecases/ObservePrayerRecordsForMonthUseCase.kt
   M app/src/main/java/com/mhq/salati/prayertracker/domain/usecases/SetPrayerStatusUseCase.kt
   M app/src/main/java/com/mhq/salati/prayertracker/ui/screens/PrayerTrackerContent.kt
   M app/src/main/java/com/mhq/salati/qibla/ui/components/CompassCalibrationOverlay.kt
   M app/src/main/java/com/mhq/salati/qibla/ui/components/CompassWideLayout.kt
   M app/src/main/java/com/mhq/salati/qibla/ui/screens/QiblaContentSuccess.kt
   M app/src/main/java/com/mhq/salati/settings/ui/screens/SettingsContent.kt
   M app/src/main/java/com/mhq/salati/shared/domain/Clock.kt
   M app/src/main/java/com/mhq/salati/shared/domain/Coordinates.kt
   M app/src/main/java/com/mhq/salati/shared/domain/DateKey.kt
   M app/src/main/java/com/mhq/salati/shared/domain/HijriMonth.kt
   M app/src/main/java/com/mhq/salati/shared/domain/MinuteOfday.kt
   M app/src/main/java/com/mhq/salati/shared/domain/PrayerName.kt
   M app/src/main/java/com/mhq/salati/shared/domain/PrayerNameResolver.kt
   M app/src/main/java/com/mhq/salati/shared/domain/usecases/ResolvePrayerNameUseCase.kt
   M app/src/main/java/com/mhq/salati/shared/ui/ScreenLayout.kt
   M app/src/main/java/com/mhq/salati/shared/ui/components/TabHeader.kt
```

## Project tree

```text
build.gradle.kts  (0.4 KB)
gradle.properties  (1.1 KB)
gradle\gradle-daemon-jvm.properties  (1.1 KB)
gradle\libs.versions.toml  (5.1 KB)
gradle\wrapper\gradle-wrapper.jar  (44.4 KB)  [binary]
gradle\wrapper\gradle-wrapper.properties  (0.4 KB)
settings.gradle.kts  (0.6 KB)
app\build.gradle.kts  (3.9 KB)
app\src\main\AndroidManifest.xml  (3.3 KB)
app\src\main\java\com\mhq\salati\adhan\data\repoimpl\AdhanPlaybackRepoImpl.kt  (1.6 KB)
app\src\main\java\com\mhq\salati\adhan\data\repoimpl\AlarmSchedulerImpl.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\adhan\data\repoimpl\MutedPrayersRepoImpl.kt  (1.8 KB)
app\src\main\java\com\mhq\salati\adhan\datasource\alarm\AndroidPrayerAlarmDataSource.kt  (2.2 KB)
app\src\main\java\com\mhq\salati\adhan\datasource\alarm\PrayerAlarmDataSource.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\adhan\datasource\alarm\PrayerAlarmRequest.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\adhan\datasource\database\MutedPrayerDao.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\adhan\datasource\database\MutedPrayerEntity.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\adhan\datasource\playback\AdhanPlaybackDataSource.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\adhan\datasource\playback\AndroidAdhanPlaybackDataSource.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\adhan\datasource\playback\PlaybackStatus.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\adhan\di\AdhanModule.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\adhan\di\AlarmModule.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\adhan\di\MutedPrayersModule.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\adhan\domain\model\AdhanPlaybackState.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\adhan\domain\model\AlarmPermissions.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\adhan\domain\model\PrayerAlarm.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\adhan\domain\repo\AdhanPlaybackController.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\adhan\domain\repo\AlarmScheduler.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\adhan\domain\repo\MutedPrayersRepository.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\adhan\domain\service\PrayerAlarmCoordinator.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\adhan\domain\usecases\ObserveMutedPrayersUseCase.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\adhan\domain\usecases\RescheduleAllAlarmsUseCase.kt  (3.5 KB)
app\src\main\java\com\mhq\salati\adhan\domain\usecases\StartAdhanPlaybackUseCase.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\adhan\domain\usecases\ToggleMutePrayerUseCase.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\adhan\domain\usecases\UpdateAdhanPlaybackStateUseCase.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\adhan\presentation\presenter\AdhanPlaybackPresenter.kt  (1.5 KB)
app\src\main\java\com\mhq\salati\adhan\presentation\presenter\AlarmReschedulePresenter.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\adhan\presentation\presenter\PrayerAlarmPresenter.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\adhan\ui\notification\NotificationHelper.kt  (1.7 KB)
app\src\main\java\com\mhq\salati\adhan\ui\receiver\BootReceiver.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\adhan\ui\receiver\PrayerAlarmReceiver.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\adhan\ui\service\AdhanPlaybackService.kt  (6.8 KB)
app\src\main\java\com\mhq\salati\alarms\data\mapper\CustomAlarmMapper.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\alarms\data\repoimpl\CustomAlarmRepoImpl.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\alarms\data\repoimpl\CustomAlarmSchedulerImpl.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\alarms\datasource\alarm\AndroidCustomAlarmDataSource.kt  (2.7 KB)
app\src\main\java\com\mhq\salati\alarms\datasource\alarm\CustomAlarmDataSource.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\alarms\datasource\alarm\CustomAlarmRequest.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\alarms\datasource\database\CustomAlarmDao.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\alarms\datasource\database\CustomAlarmEntity.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\di\AlarmsBindsModule.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\alarms\di\AlarmsProvidesModule.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\alarms\domain\model\CustomAlarm.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\domain\model\CustomAlarmDays.kt  (1.6 KB)
app\src\main\java\com\mhq\salati\alarms\domain\model\DayToggleResult.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\domain\model\OffsetDirection.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\alarms\domain\model\SaveAlarmResult.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\alarms\domain\repo\CustomAlarmRepository.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\alarms\domain\repo\CustomAlarmScheduler.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\CreateCustomAlarmUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\DeleteCustomAlarmUseCase.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\ObserveCustomAlarmsUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\RescheduleCustomAlarmsUseCase.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\SaveCustomAlarmUseCase.kt  (1.9 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\ScheduleCustomAlarmsUseCase.kt  (4.1 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\ToggleCustomAlarmDayUseCase.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\ToggleCustomAlarmUseCase.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\alarms\domain\usecases\UpdateCustomAlarmUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\presentation\contract\AlarmsContract.kt  (3.5 KB)
app\src\main\java\com\mhq\salati\alarms\presentation\mapper\CustomAlarmUiMapper.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\alarms\presentation\mapper\OffsetDirectionUiMapper.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\alarms\presentation\model\CustomAlarmUi.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\presentation\model\OffsetDirectionUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\alarms\presentation\presenter\CustomAlarmNotificationPresenter.kt  (1.5 KB)
app\src\main\java\com\mhq\salati\alarms\presentation\viewmodel\AlarmsViewModel.kt  (5.6 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\AddEditAlarmFormContent.kt  (14.9 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\AddEditAlarmSheet.kt  (1.8 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\AlarmTitleEditor.kt  (3.5 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\CustomAlarmCard.kt  (8.1 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\DayBadge.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\DayCircle.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\OffsetDirectionSegment.kt  (1.5 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\OffsetInlineHeadline.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\RepeatTag.kt  (1.8 KB)
app\src\main\java\com\mhq\salati\alarms\ui\components\WeekDays.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\ui\notification\CustomAlarmNotifier.kt  (4.3 KB)
app\src\main\java\com\mhq\salati\alarms\ui\receiver\CustomAlarmIntentKeys.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\alarms\ui\receiver\CustomAlarmReceiver.kt  (1.8 KB)
app\src\main\java\com\mhq\salati\alarms\ui\screens\CustomAlarmsContainer.kt  (1.7 KB)
app\src\main\java\com\mhq\salati\alarms\ui\screens\CustomAlarmsContent.kt  (9.5 KB)
app\src\main\java\com\mhq\salati\alarms\ui\screens\CustomAlarmsContentBlank.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\alarms\ui\state\AlarmFormState.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\connectivity\data\repoimpl\ConnectivityCheckerImpl.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\connectivity\datasource\device\AndroidConnectivityDataSource.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\connectivity\datasource\device\ConnectivityDataSource.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\connectivity\di\ConnectivityModule.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\connectivity\domain\repo\ConnectivityChecker.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\connectivity\ui\components\rememberNetworkConnected.kt  (2.0 KB)
app\src\main\java\com\mhq\salati\home\domain\model\PrayerCountdown.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\home\domain\model\PrayerProgress.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\home\domain\model\PrayerTimesError.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\home\domain\model\PrayerTimesLoad.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\home\domain\model\PrayerTimesTick.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\home\domain\model\PrayerWindow.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\home\domain\service\DayNavigator.kt  (2.0 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\CalculateCurrentPrayerNameUseCase.kt  (1.5 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\CalculatePastPrayersUseCase.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\CalculatePrayerStatusUseCase.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\CalculatePrayerWindowUseCase.kt  (4.4 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\LoadPrayerTimesUseCase.kt  (3.7 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\ObservePrayerCountdownUseCase.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\ObservePrayerTimesTicksUseCase.kt  (2.5 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\RefreshPrayerStatusUseCase.kt  (1.5 KB)
app\src\main\java\com\mhq\salati\home\domain\usecases\ResolvePrayerWindowForDisplayUseCase.kt  (2.2 KB)
app\src\main\java\com\mhq\salati\home\presentation\contract\HomeContract.kt  (3.1 KB)
app\src\main\java\com\mhq\salati\home\presentation\handlers\HomeAlarmsHandler.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\home\presentation\handlers\HomeLocationHandler.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\home\presentation\handlers\HomeLocationLoad.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\home\presentation\handlers\MutedPrayersHandler.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\home\presentation\handlers\PrayerTimesHandler.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\home\presentation\mapper\HomeUiMapper.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\home\presentation\model\HijriDateUi.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\home\presentation\model\PrayerDateUi.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\home\presentation\model\PrayerTimingsUi.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\home\presentation\viewmodel\HomeViewModel.kt  (16.8 KB)
app\src\main\java\com\mhq\salati\home\ui\components\DateBanner.kt  (8.0 KB)
app\src\main\java\com\mhq\salati\home\ui\components\LocationHeader.kt  (3.6 KB)
app\src\main\java\com\mhq\salati\home\ui\components\PrayerCountdownRing.kt  (4.9 KB)
app\src\main\java\com\mhq\salati\home\ui\components\PrayerGridItem.kt  (7.0 KB)
app\src\main\java\com\mhq\salati\home\ui\components\PrayersGrid.kt  (3.6 KB)
app\src\main\java\com\mhq\salati\home\ui\screens\HomeContainer.kt  (3.6 KB)
app\src\main\java\com\mhq\salati\home\ui\screens\HomeContent.kt  (4.3 KB)
app\src\main\java\com\mhq\salati\home\ui\screens\HomeContentError.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\home\ui\screens\HomeContentSuccess.kt  (10.2 KB)
app\src\main\java\com\mhq\salati\location\data\mapper\NominatimMapper.kt  (1.7 KB)
app\src\main\java\com\mhq\salati\location\data\mapper\StoredLocationMapper.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\location\data\repoimpl\GeocoderProviderImpl.kt  (1.7 KB)
app\src\main\java\com\mhq\salati\location\data\repoimpl\LocationProviderImpl.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\location\data\repoimpl\LocationRepoImpl.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\location\datasource\device\DeviceLocation.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\location\datasource\device\FusedGpsDataSource.kt  (4.7 KB)
app\src\main\java\com\mhq\salati\location\datasource\device\GpsDataSource.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\location\datasource\network\dto\NominatimAddressDto.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\location\datasource\network\dto\NominatimReverseDto.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\location\datasource\network\dto\NominatimSearchDto.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\location\datasource\network\service\KtorNominatimApi.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\location\datasource\network\service\NominatimApi.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\location\datasource\network\service\NominatimHttpException.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\location\datasource\preferences\LocationDataStore.kt  (2.2 KB)
app\src\main\java\com\mhq\salati\location\datasource\preferences\StoredLocation.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\location\di\LocationModule.kt  (1.9 KB)
app\src\main\java\com\mhq\salati\location\domain\model\CoordinatesLookup.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\location\domain\model\GeocodeResult.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\location\domain\model\LocationPrerequisites.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\location\domain\model\LocationResolution.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\location\domain\model\LocationSearchResult.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\location\domain\model\SavedLocation.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\location\domain\repo\GeocoderProvider.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\location\domain\repo\LocationProvider.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\location\domain\repo\LocationRepository.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\location\domain\service\LocationLocalizer.kt  (3.6 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\CheckLocationPrerequisitesUseCase.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\ClearSavedLocationUseCase.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\GetCurrentCoordinatesUseCase.kt  (2.2 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\GetLocalizedLocationUseCase.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\GetSavedLocationUseCase.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\ResolveLocationUseCase.kt  (3.3 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\ReverseGeocodeLocationUseCase.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\SaveManualLocationUseCase.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\location\domain\usecases\SearchLocationByNameUseCase.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\location\presentation\handlers\LocationPrerequisitesHandler.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\location\presentation\mapper\PrerequisitesMapper.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\location\presentation\model\PrerequisitesUi.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\location\ui\components\PrerequisiteRow.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\location\ui\components\PrerequisitesError.kt  (6.5 KB)
app\src\main\java\com\mhq\salati\location\ui\LocationPrerequisitesActions.kt  (2.3 KB)
app\src\main\java\com\mhq\salati\location\ui\LocationPrerequisitesObserver.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\locationpicker\presentation\contract\LocationPickerContract.kt  (1.5 KB)
app\src\main\java\com\mhq\salati\locationpicker\presentation\viewmodel\LocationPickerViewModel.kt  (11.0 KB)
app\src\main\java\com\mhq\salati\locationpicker\ui\screens\LocationPickerContainer.kt  (2.0 KB)
app\src\main\java\com\mhq\salati\locationpicker\ui\screens\LocationPickerContent.kt  (10.8 KB)
app\src\main\java\com\mhq\salati\locationpicker\ui\screens\LocationPickerMap.kt  (4.9 KB)
app\src\main\java\com\mhq\salati\onboarding\data\repoimpl\OnboardingRepoImpl.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\onboarding\datasource\preferences\OnboardingDataStore.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\onboarding\di\OnboardingModule.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\onboarding\domain\model\OnboardingPermissions.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\onboarding\domain\repo\OnboardingRepository.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\onboarding\domain\usecases\CheckOnboardingPermissionsUseCase.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\onboarding\domain\usecases\CompleteOnboardingUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\onboarding\presentation\contract\OnboardingContract.kt  (4.5 KB)
app\src\main\java\com\mhq\salati\onboarding\presentation\viewmodel\OnboardingViewModel.kt  (11.2 KB)
app\src\main\java\com\mhq\salati\onboarding\ui\components\OnboardingPageIndicator.kt  (2.0 KB)
app\src\main\java\com\mhq\salati\onboarding\ui\components\OnboardingSelectionSheet.kt  (2.6 KB)
app\src\main\java\com\mhq\salati\onboarding\ui\OnboardingLabels.kt  (1.7 KB)
app\src\main\java\com\mhq\salati\onboarding\ui\screens\OnboardingContainer.kt  (4.2 KB)
app\src\main\java\com\mhq\salati\onboarding\ui\screens\OnboardingContent.kt  (25.3 KB)
app\src\main\java\com\mhq\salati\permissions\data\repoimpl\LocationPermissionRepoImpl.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\permissions\data\repoimpl\PermissionCheckerImpl.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\permissions\datasource\device\AndroidPermissionDataSource.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\permissions\datasource\device\PermissionDataSource.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\permissions\datasource\preferences\LocationPermissionDataStore.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\permissions\di\PermissionModule.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\permissions\domain\repo\LocationPermissionRepository.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\permissions\domain\repo\PermissionChecker.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\permissions\domain\usecases\MarkLocationPermissionRequestedUseCase.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\permissions\domain\usecases\WasLocationPermissionRequestedUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\permissions\ui\rememberLocationPermissionLauncher.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\permissions\ui\rememberLocationServicesEnabled.kt  (1.8 KB)
app\src\main\java\com\mhq\salati\permissions\ui\rememberNotificationPermissionLauncher.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\permissions\ui\ShouldShowLocationRationale.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\prayertimes\data\mapper\CalendarMapper.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\prayertimes\data\mapper\TimingsMapper.kt  (4.8 KB)
app\src\main\java\com\mhq\salati\prayertimes\data\repoimpl\PrayerTimesRepoImpl.kt  (3.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\database\PrayerTimesDao.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\database\PrayerTimesEntity.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\api\AladhanApiService.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\CalendarResponseDto.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\DateDto.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\GregorianDto.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\HijriDto.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\HijriMonthDto.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\MetaDto.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\MonthDto.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\TimingsDataDto.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\TimingsDto.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\prayertimes\di\PrayerTimesModule.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\domain\model\HijriDate.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\prayertimes\domain\model\HijriDateAdjustment.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\prayertimes\domain\model\PrayerDate.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\prayertimes\domain\model\PrayerTimesResult.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\prayertimes\domain\model\PrayerTimings.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\prayertimes\domain\repo\PrayerTimesRepository.kt  (1.7 KB)
app\src\main\java\com\mhq\salati\prayertimes\domain\usecases\GetCachedPrayerTimesUseCase.kt  (1.6 KB)
app\src\main\java\com\mhq\salati\prayertimes\domain\usecases\GetPrayerTimesUseCase.kt  (1.6 KB)
app\src\main\java\com\mhq\salati\prayertracker\data\repoimpl\PrayerTrackerRepoImpl.kt  (2.6 KB)
app\src\main\java\com\mhq\salati\prayertracker\datasource\database\PrayerRecordDao.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\prayertracker\datasource\database\PrayerRecordEntity.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\prayertracker\di\PrayerTrackerModule.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\model\DayStatus.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\model\PrayerStatus.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\repo\PrayerTrackerRepository.kt  (1.5 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\GetCurrentStreakUseCase.kt  (2.3 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\GetDuePrayersUseCase.kt  (2.0 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\ObserveMonthDayStatusUseCase.kt  (1.9 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\ObservePrayerRecordsForDateUseCase.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\ObservePrayerRecordsForMonthUseCase.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\SetPrayerStatusUseCase.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\prayertracker\presentation\contract\PrayerTrackerContract.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\prayertracker\presentation\mapper\PrayerTrackerUiMapper.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\prayertracker\presentation\model\DayStatusUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\prayertracker\presentation\model\PrayerStatusUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\prayertracker\presentation\viewmodel\PrayerTrackerViewModel.kt  (6.9 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\components\CalendarDayCell.kt  (8.4 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\components\CalendarMonthView.kt  (10.7 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\components\DialogChoiceButton.kt  (2.9 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\components\PrayerConfirmDialog.kt  (4.1 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\components\PrayerStatusCard.kt  (6.3 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\components\PrayerTrackerList.kt  (3.4 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\components\StreakBanner.kt  (2.4 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\components\TrackerBadge.kt  (1.5 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\screens\PrayerTrackerContainer.kt  (2.3 KB)
app\src\main\java\com\mhq\salati\prayertracker\ui\screens\PrayerTrackerContent.kt  (9.4 KB)
app\src\main\java\com\mhq\salati\qibla\data\repoimpl\CompassRepoImpl.kt  (1.7 KB)
app\src\main\java\com\mhq\salati\qibla\datasource\device\AndroidRotationSensorDataSource.kt  (5.2 KB)
app\src\main\java\com\mhq\salati\qibla\datasource\device\CompassSample.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\qibla\datasource\device\RotationSensorDatasource.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\qibla\datasource\device\RotationSensorUnavailableException.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\qibla\datasource\device\SensorAccuracy.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\qibla\di\QiblaModule.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\qibla\domain\model\CompassAccuracy.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\qibla\domain\model\CompassReading.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\qibla\domain\model\CompassUnavailableException.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\qibla\domain\repo\CompassRepository.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\qibla\domain\usecases\GetQiblaBearingUseCase.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\qibla\presentation\contract\QiblaContract.kt  (2.0 KB)
app\src\main\java\com\mhq\salati\qibla\presentation\handlers\QiblaCompassHandler.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\qibla\presentation\handlers\QiblaLocationHandler.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\qibla\presentation\handlers\QiblaLocationLoad.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\qibla\presentation\mapper\QiblaUiMapper.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\qibla\presentation\model\CompassAccuracyUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\qibla\presentation\viewmodel\QiblaViewModel.kt  (11.4 KB)
app\src\main\java\com\mhq\salati\qibla\ui\components\CompassCalibrationBanner.kt  (4.5 KB)
app\src\main\java\com\mhq\salati\qibla\ui\components\CompassCalibrationOverlay.kt  (11.8 KB)
app\src\main\java\com\mhq\salati\qibla\ui\components\CompassWideLayout.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\qibla\ui\components\RotatingCompassDial.kt  (17.3 KB)
app\src\main\java\com\mhq\salati\qibla\ui\errors\QiblaSensorUnavailableError.kt  (1.9 KB)
app\src\main\java\com\mhq\salati\qibla\ui\screens\QiblaContainer.kt  (5.0 KB)
app\src\main\java\com\mhq\salati\qibla\ui\screens\QiblaContent.kt  (5.4 KB)
app\src\main\java\com\mhq\salati\qibla\ui\screens\QiblaContentError.kt  (1.6 KB)
app\src\main\java\com\mhq\salati\qibla\ui\screens\QiblaContentSuccess.kt  (10.8 KB)
app\src\main\java\com\mhq\salati\settings\data\mapper\SettingsMapper.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\settings\data\repoimpl\SettingsRepoImpl.kt  (2.1 KB)
app\src\main\java\com\mhq\salati\settings\datasource\preferences\SettingsDataStore.kt  (3.2 KB)
app\src\main\java\com\mhq\salati\settings\datasource\preferences\StoredSettings.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\settings\di\SettingsModule.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\settings\domain\model\AdhanSound.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\domain\model\AppLanguage.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\domain\model\AppSettings.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\settings\domain\model\CalculationMethod.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\settings\domain\model\CustomAlarmSound.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\domain\model\Madhab.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\domain\model\ThemeMode.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\domain\model\TimeFormat.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\settings\domain\repo\SettingsRepository.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\ObserveHijriDateOffsetUseCase.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\ObserveSettingsUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\ObserveTimeFormatUseCase.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateAdhanSoundUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateCalculationMethodUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateCustomAlarmSoundUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateHijriDateOffsetUseCase.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateLanguageUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateMadhabUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateNotificationsEnabledUseCase.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateThemeModeUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateTimeFormatUseCase.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\settings\presentation\contract\SettingsContract.kt  (2.9 KB)
app\src\main\java\com\mhq\salati\settings\presentation\mapper\SettingsUiMapper.kt  (4.9 KB)
app\src\main\java\com\mhq\salati\settings\presentation\model\AdhanSoundUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\presentation\model\AppLanguageUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\presentation\model\CalculationMethodUi.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\settings\presentation\model\CustomAlarmSoundUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\presentation\model\MadhabUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\presentation\model\ThemeModeUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\settings\presentation\model\TimeFormatUi.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\settings\presentation\SelectorType.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\settings\presentation\viewmodel\SettingsViewModel.kt  (9.7 KB)
app\src\main\java\com\mhq\salati\settings\ui\components\SettingsActionRow.kt  (2.7 KB)
app\src\main\java\com\mhq\salati\settings\ui\components\SettingsSectionHeader.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\settings\ui\components\SettingsSelectionSheet.kt  (3.6 KB)
app\src\main\java\com\mhq\salati\settings\ui\components\SettingsStepperRow.kt  (3.1 KB)
app\src\main\java\com\mhq\salati\settings\ui\components\SettingsSwitchRow.kt  (3.1 KB)
app\src\main\java\com\mhq\salati\settings\ui\screens\SettingsContainer.kt  (6.2 KB)
app\src\main\java\com\mhq\salati\settings\ui\screens\SettingsContent.kt  (15.7 KB)
app\src\main\java\com\mhq\salati\settings\ui\SettingsLabels.kt  (2.8 KB)
app\src\main\java\com\mhq\salati\shared\app\SalatiApplication.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\shared\data\mapper\PrayerNameMapper.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\shared\data\repoimpl\PrayerNameResolverImpl.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\shared\data\SystemClock.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\shared\datasource\database\SalatiDatabase.kt  (1.1 KB)
app\src\main\java\com\mhq\salati\shared\datasource\device\ComponentTarget.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\shared\datasource\network\KtorClient.kt  (1.6 KB)
app\src\main\java\com\mhq\salati\shared\di\ClockModule.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\shared\di\DatabaseModule.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\shared\di\NetworkModule.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\shared\di\PrayerNameModule.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\shared\domain\Clock.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\shared\domain\Coordinates.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\shared\domain\DateKey.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\shared\domain\HijriMonth.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\shared\domain\MinuteOfday.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\shared\domain\PrayerName.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\shared\domain\PrayerNameResolver.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\shared\domain\usecases\ResolvePrayerNameUseCase.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\shared\presentation\mapper\HijriMonthUiMapper.kt  (0.8 KB)
app\src\main\java\com\mhq\salati\shared\presentation\mapper\PrayerNameUiMapper.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\shared\presentation\model\HijriMonthUi.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\shared\presentation\model\MessageUi.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\shared\presentation\model\PrayerNameUi.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\ActionRow.kt  (4.8 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\AppLogo.kt  (1.3 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\GroupCard.kt  (1.2 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\GroupDivider.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\IconBadge.kt  (1.8 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\LocalSnackbarHostState.kt  (0.3 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\SelectionSheet.kt  (3.1 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\ShrinkToFitText.kt  (2.4 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\StepBadgeIcon.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\StepHeader.kt  (2.3 KB)
app\src\main\java\com\mhq\salati\shared\ui\components\TabHeader.kt  (6.0 KB)
app\src\main\java\com\mhq\salati\shared\ui\errors\CatchAllError.kt  (2.2 KB)
app\src\main\java\com\mhq\salati\shared\ui\errors\NoConnectionError.kt  (1.8 KB)
app\src\main\java\com\mhq\salati\shared\ui\HijriMonthLabels.kt  (1.0 KB)
app\src\main\java\com\mhq\salati\shared\ui\MainActivity.kt  (2.9 KB)
app\src\main\java\com\mhq\salati\shared\ui\MessageLabels.kt  (2.6 KB)
app\src\main\java\com\mhq\salati\shared\ui\navigation\BottomNavBar.kt  (12.7 KB)
app\src\main\java\com\mhq\salati\shared\ui\navigation\BottomNavBarDefaults.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\shared\ui\navigation\BottomNavBarItem.kt  (0.2 KB)
app\src\main\java\com\mhq\salati\shared\ui\navigation\NavigationScreen.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\shared\ui\PrayerNameLabels.kt  (0.7 KB)
app\src\main\java\com\mhq\salati\shared\ui\SalatiApp.kt  (9.7 KB)
app\src\main\java\com\mhq\salati\shared\ui\ScreenLayout.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\shared\ui\screens\LoadingContent.kt  (1.7 KB)
app\src\main\java\com\mhq\salati\shared\ui\theme\Color.kt  (8.5 KB)
app\src\main\java\com\mhq\salati\shared\ui\theme\Gradients.kt  (1.4 KB)
app\src\main\java\com\mhq\salati\shared\ui\theme\Theme.kt  (3.3 KB)
app\src\main\java\com\mhq\salati\shared\ui\theme\Type.kt  (2.8 KB)
app\src\main\java\com\mhq\salati\shared\ui\TimeFormatting.kt  (0.9 KB)
app\src\main\java\com\mhq\salati\splash\domain\model\StartDestination.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\splash\domain\usecases\GetStartDestinationUseCase.kt  (0.5 KB)
app\src\main\java\com\mhq\salati\splash\presentation\contract\SplashContract.kt  (0.6 KB)
app\src\main\java\com\mhq\salati\splash\presentation\mapper\SplashUiMapper.kt  (0.4 KB)
app\src\main\java\com\mhq\salati\splash\presentation\model\StartDestinationUi.kt  (0.1 KB)
app\src\main\java\com\mhq\salati\splash\presentation\viewmodel\SplashViewModel.kt  (2.4 KB)
app\src\main\res\drawable\ic_salati_colored.xml  (1.1 KB)
app\src\main\res\drawable\ic_salati_white.xml  (0.8 KB)
app\src\main\res\drawable\ic_stop.xml  (0.3 KB)
app\src\main\res\mipmap-anydpi-v26\ic_launcher.xml  (0.3 KB)
app\src\main\res\mipmap-anydpi-v26\ic_launcher_round.xml  (0.3 KB)
app\src\main\res\mipmap-hdpi\ic_launcher.webp  (1.4 KB)  [binary]
app\src\main\res\mipmap-hdpi\ic_launcher_foreground.webp  (1.8 KB)  [binary]
app\src\main\res\mipmap-hdpi\ic_launcher_round.webp  (3.1 KB)  [binary]
app\src\main\res\mipmap-mdpi\ic_launcher.webp  (1.1 KB)  [binary]
app\src\main\res\mipmap-mdpi\ic_launcher_foreground.webp  (1.3 KB)  [binary]
app\src\main\res\mipmap-mdpi\ic_launcher_round.webp  (2.0 KB)  [binary]
app\src\main\res\mipmap-xhdpi\ic_launcher.webp  (1.9 KB)  [binary]
app\src\main\res\mipmap-xhdpi\ic_launcher_foreground.webp  (2.5 KB)  [binary]
app\src\main\res\mipmap-xhdpi\ic_launcher_round.webp  (4.3 KB)  [binary]
app\src\main\res\mipmap-xxhdpi\ic_launcher.webp  (2.7 KB)  [binary]
app\src\main\res\mipmap-xxhdpi\ic_launcher_foreground.webp  (4.1 KB)  [binary]
app\src\main\res\mipmap-xxhdpi\ic_launcher_round.webp  (6.5 KB)  [binary]
app\src\main\res\mipmap-xxxhdpi\ic_launcher.webp  (3.4 KB)  [binary]
app\src\main\res\mipmap-xxxhdpi\ic_launcher_foreground.webp  (4.8 KB)  [binary]
app\src\main\res\mipmap-xxxhdpi\ic_launcher_round.webp  (8.8 KB)  [binary]
app\src\main\res\raw\adhan_egypt.mp3  (180.7 KB)  [binary]
app\src\main\res\raw\adhan_madinah.mp3  (3.8 MB)  [binary]
app\src\main\res\raw\adhan_makkah.mp3  (198.7 KB)  [binary]
app\src\main\res\raw\default_beep.mp3  (38.4 KB)  [binary]
app\src\main\res\raw\minor_timing_alert.mp3  (55.8 KB)  [binary]
app\src\main\res\values\colors.xml  (0.1 KB)
app\src\main\res\values\ic_launcher_background.xml  (0.1 KB)
app\src\main\res\values\strings.xml  (13.6 KB)
app\src\main\res\values\themes.xml  (0.1 KB)
app\src\main\res\values-ar\strings.xml  (16.8 KB)
app\src\main\res\xml\backup_rules.xml  (0.5 KB)
app\src\main\res\xml\data_extraction_rules.xml  (0.5 KB)
app\src\main\ic_launcher-playstore.png  (20.0 KB)  [binary]
app\src\main\keepRules\rules.keep  (0.4 KB)  [binary]
app\src\test\java\com\mhq\salati\home\domain\service\DayNavigatorTest.kt  (4.0 KB)
app\src\test\java\com\mhq\salati\home\domain\usecases\LoadPrayerTimesUseCaseTest.kt  (7.0 KB)
app\src\test\java\com\mhq\salati\home\domain\usecases\ResolvePrayerWindowForDisplayUseCaseTest.kt  (7.5 KB)
app\src\test\java\com\mhq\salati\location\domain\usecases\CheckLocationPrerequisitesUseCaseTest.kt  (3.0 KB)
app\src\test\java\com\mhq\salati\location\domain\usecases\ResolveLocationUseCaseTest.kt  (8.7 KB)
app\src\test\java\com\mhq\salati\prayertracker\domain\usecases\GetCurrentStreakUseCaseTest.kt  (5.2 KB)
app\src\test\java\com\mhq\salati\testutil\FakeClock.kt  (0.6 KB)
app\src\test\java\com\mhq\salati\testutil\FakeConnectivityChecker.kt  (0.2 KB)
app\src\test\java\com\mhq\salati\testutil\FakePermissionChecker.kt  (0.3 KB)
app\src\test\java\com\mhq\salati\testutil\FakePrayerTimesRepository.kt  (1.2 KB)
app\src\test\java\com\mhq\salati\testutil\FakeSettingsRepository.kt  (1.4 KB)
.gitignore  (0.2 KB)
AGENTS.md  (0.8 KB)
app\.gitignore  (0.0 KB)
app\config\detekt\detekt.yml  (21.3 KB)
docs\architecture.md  (6.0 KB)
dump_project.ps1  (7.0 KB)  [binary]
gradlew  (8.5 KB)  [binary]
gradlew.bat  (2.9 KB)  [binary]
local.properties  (0.5 KB)  [skipped: sensitive]
scripts\extract_composables.ps1  (7.8 KB)  [binary]
scripts\extract_kotlin_files.ps1  (3.2 KB)  [binary]
scripts\README.md  (2.9 KB)
```

## Binary resources by type

| Type | Files | Total size |
|---|---|---|
| .webp | 15 | 49.6 KB |
| .mp3 | 5 | 4.3 MB |
| .ps1 | 3 | 18.0 KB |
| .bat | 1 | 2.9 KB |
| (none) | 1 | 8.5 KB |
| .jar | 1 | 44.4 KB |
| .png | 1 | 20.0 KB |
| .keep | 1 | 0.4 KB |

---

## build.gradle.kts

```kotlin
// Top-level build file where you can add configuration options common to all subprojects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
}
```

## gradle.properties

```properties
# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. For more details, visit
# https://developer.android.com/r/tools/gradle-multi-project-decoupled-projects
# org.gradle.parallel=true
# When enabled, the Configuration Cache allows Gradle to skip the configuration
# phase entirely if nothing that affects the build configuration (such as build scripts)
# has changed. Additionally, Gradle applies performance optimizations to task execution.
org.gradle.configuration-cache=true
# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official
```

## gradle\gradle-daemon-jvm.properties

```properties
#This file is generated by updateDaemonJvm
toolchainUrl.FREE_BSD.AARCH64=https\://api.foojay.io/disco/v3.0/ids/ec7520a1e057cd116f9544c42142a16b/redirect
toolchainUrl.FREE_BSD.X86_64=https\://api.foojay.io/disco/v3.0/ids/4c4f879899012ff0a8b2e2117df03b0e/redirect
toolchainUrl.LINUX.AARCH64=https\://api.foojay.io/disco/v3.0/ids/ec7520a1e057cd116f9544c42142a16b/redirect
toolchainUrl.LINUX.X86_64=https\://api.foojay.io/disco/v3.0/ids/4c4f879899012ff0a8b2e2117df03b0e/redirect
toolchainUrl.MAC_OS.AARCH64=https\://api.foojay.io/disco/v3.0/ids/73bcfb608d1fde9fb62e462f834a3299/redirect
toolchainUrl.MAC_OS.X86_64=https\://api.foojay.io/disco/v3.0/ids/846ee0d876d26a26f37aa1ce8de73224/redirect
toolchainUrl.UNIX.AARCH64=https\://api.foojay.io/disco/v3.0/ids/ec7520a1e057cd116f9544c42142a16b/redirect
toolchainUrl.UNIX.X86_64=https\://api.foojay.io/disco/v3.0/ids/4c4f879899012ff0a8b2e2117df03b0e/redirect
toolchainUrl.WINDOWS.AARCH64=https\://api.foojay.io/disco/v3.0/ids/9482ddec596298c84656d31d16652665/redirect
toolchainUrl.WINDOWS.X86_64=https\://api.foojay.io/disco/v3.0/ids/39701d92e1756bb2f141eb67cd4c660e/redirect
toolchainVersion=21
```

## gradle\libs.versions.toml

```toml
[versions]
agp = "9.4.1"
ktor = "3.5.1"
kotlin = "2.4.10"
kotlinxCoroutines = "1.11.0"
kotlinxSerialization = "1.11.0"
coreKtx = "1.19.0"
composeBom = "2026.06.01"
activityCompose = "1.13.0"
lifecycleRuntimeKtx = "2.11.0"
lifecycleViewmodelCompose = "2.11.0"
hilt = "2.60.1"
hiltNavigationCompose = "1.4.0"
ksp = "2.3.10"
playServicesLocation = "21.4.0"
room = "2.8.4"
navigationCompose = "2.9.8"
datastorePreferences = "1.2.1"
junit = "4.13.2"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
foundationLayout = "1.11.4"
ui = "1.11.4"
appcompat = "1.8.0"
ktlint = "12.1.1"
detekt = "1.23.7"
animationGraphics = "1.12.1"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-compose-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-cio = { module = "io.ktor:ktor-client-cio", version.ref = "ktor" }
ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor" }
ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor" }
kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinxSerialization" }
lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycleViewmodelCompose" }
kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "kotlinxCoroutines" }
hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
hilt-compiler = { module = "com.google.dagger:hilt-compiler", version.ref = "hilt" }
androidx-hilt-navigation-compose = { module = "androidx.hilt:hilt-navigation-compose", version.ref = "hiltNavigationCompose" }
play-services-location = { module = "com.google.android.gms:play-services-location", version.ref = "playServicesLocation" }
room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
room-ktx = { module = "androidx.room:room-ktx", version.ref = "room" }
room-compiler = { module = "androidx.room:room-compiler", version.ref = "room" }
androidx-navigation-compose = { module = "androidx.navigation:navigation-compose", version.ref = "navigationCompose" }
androidx-compose-material-icons-extended = { module = "androidx.compose.material:material-icons-extended", version = "1.7.8" }
androidx-datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastorePreferences" }
androidx-compose-foundation-layout = { group = "androidx.compose.foundation", name = "foundation-layout", version.ref = "foundationLayout" }
androidx-ui = { group = "androidx.compose.ui", name = "ui", version.ref = "ui" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
kotlinx-coroutines-test = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-test", version = "1.9.0" }
mockk = { module = "io.mockk:mockk", version = "1.13.13" }
compose-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }
androidx-compose-animation-graphics = { group = "androidx.compose.animation", name = "animation-graphics", version.ref = "animationGraphics" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
ktlint = { id = "org.jlleitschuh.gradle.ktlint", version.ref = "ktlint" }
detekt = { id = "io.gitlab.arturbosch.detekt", version.ref = "detekt" }
```

## gradle\wrapper\gradle-wrapper.properties

```properties
#Fri Jul 17 18:44:34 EEST 2026
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionSha256Sum=bbaeb2fef8710818cf0e261201dab964c572f92b942812df0c3620d62a529a01
distributionUrl=https\://services.gradle.org/distributions/gradle-9.6.0-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

## settings.gradle.kts

```kotlin
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Salati"
include(":app")
 
```

## app\build.gradle.kts

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.mhq.salati"
    compileSdk {
        version =
            release(37) {
                minorApiLevel = 1
            }
    }

    defaultConfig {
        applicationId = "com.mhq.salati"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        val contactEmail = project.findProperty("SALATI_CONTACT_EMAIL") as? String ?: "support@salati.app"
        buildConfigField("String", "CONTACT_EMAIL", "\"$contactEmail\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    //    buildTypes {
    //        release {
    //            isMinifyEnabled = false // Standard way to disable optimization/proguard in newer AGP
    //            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    //        }
    //    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt/detekt.yml")
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.animation.graphics)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation(libs.androidx.ui)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.viewmodel.compose)

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.play.services.location)

    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.compose.material.icons.extended)
    implementation("androidx.compose.material:material-icons-extended")

    implementation("org.osmdroid:osmdroid-android:6.1.20")
    implementation("io.coil-kt.coil3:coil-compose:3.5.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.5.0")

    // --- Local Unit Tests ---
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    // --- Android Instrumentation Tests ---
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // --- Debug Dependencies ---
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
```

## app\src\main\AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
    <uses-permission android:name="android.permission.USE_EXACT_ALARM" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />

    <application
        android:name="com.mhq.salati.shared.app.SalatiApplication"
        android:allowBackup="false"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Salati">

        <service
            android:name="com.mhq.salati.adhan.ui.service.AdhanPlaybackService"
            android:enabled="true"
            android:exported="false"
            android:foregroundServiceType="mediaPlayback" />

        <service
            android:name="androidx.appcompat.app.AppLocalesMetadataHolderService"
            android:enabled="false"
            android:exported="false">
            <meta-data
                android:name="autoStoreLocales"
                android:value="true" />
        </service>

        <receiver
            android:name="com.mhq.salati.adhan.ui.receiver.PrayerAlarmReceiver"
            android:exported="false" />

        <receiver
            android:name=".alarms.ui.receiver.CustomAlarmReceiver"
            android:exported="false" />

        <receiver
            android:name="com.mhq.salati.adhan.ui.receiver.BootReceiver"
            android:exported="true">

            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <action android:name="android.intent.action.TIME_SET" />
                <action android:name="android.intent.action.TIMEZONE_CHANGED" />
                <action android:name="android.intent.action.MY_PACKAGE_REPLACED" />
            </intent-filter>

        </receiver>

        <activity
            android:name="com.mhq.salati.shared.ui.MainActivity"
            android:configChanges="orientation|screenSize|screenLayout|locale|layoutDirection|grammaticalGender"
            android:exported="true"
            android:theme="@style/Theme.Salati"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

## app\src\main\java\com\mhq\salati\adhan\data\repoimpl\AdhanPlaybackRepoImpl.kt

```kotlin
package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.datasource.playback.AdhanPlaybackDataSource
import com.mhq.salati.adhan.datasource.playback.PlaybackStatus
import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import com.mhq.salati.settings.domain.model.AdhanSound
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdhanPlaybackRepoImpl @Inject constructor(
    private val adhanPlaybackDataSource: AdhanPlaybackDataSource
) : AdhanPlaybackController {

    override val playbackState: Flow<AdhanPlaybackState> =
        adhanPlaybackDataSource.status.map { it.toDomain() }

    override fun start(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean, adhanSound: AdhanSound) {
        adhanPlaybackDataSource.start(prayerName, isMinorTiming, isMuted, adhanSound.name)
    }

    override fun stop() {
        adhanPlaybackDataSource.stop()
    }

    override fun updatePlaybackState(state: AdhanPlaybackState) {
        adhanPlaybackDataSource.updateStatus(state.toDataSource())
    }

    private fun PlaybackStatus.toDomain(): AdhanPlaybackState = when (this) {
        is PlaybackStatus.Idle -> AdhanPlaybackState.Idle
        is PlaybackStatus.Playing -> AdhanPlaybackState.Playing(prayerKey)
    }

    private fun AdhanPlaybackState.toDataSource(): PlaybackStatus = when (this) {
        is AdhanPlaybackState.Idle -> PlaybackStatus.Idle
        is AdhanPlaybackState.Playing -> PlaybackStatus.Playing(prayerName)
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\data\repoimpl\AlarmSchedulerImpl.kt

```kotlin
package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.datasource.alarm.PrayerAlarmDataSource
import com.mhq.salati.adhan.datasource.alarm.PrayerAlarmRequest
import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.PrayerName
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val prayerAlarmDataSource: PrayerAlarmDataSource
) : AlarmScheduler {

    override fun schedule(alarm: PrayerAlarm) {
        prayerAlarmDataSource.schedule(
            PrayerAlarmRequest(
                prayerKey = alarm.prayerName.storageKey,
                triggerAtMillis = alarm.triggerAtMillis,
                isMinorTiming = alarm.isMinorTiming,
                isMuted = alarm.isMuted
            )
        )
    }

    override fun cancel(prayerName: PrayerName) {
        prayerAlarmDataSource.cancel(prayerName.storageKey)
    }

    override fun cancelAll() {
        PrayerName.entries.forEach { cancel(it) }
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\data\repoimpl\MutedPrayersRepoImpl.kt

```kotlin
package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.datasource.database.MutedPrayerDao
import com.mhq.salati.adhan.datasource.database.MutedPrayerEntity
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toLocalDateFromKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MutedPrayersRepoImpl @Inject constructor(
    private val mutedPrayerDao: MutedPrayerDao,
    private val clock: Clock
) : MutedPrayersRepository {

    override suspend fun getMutedPrayers(date: String): Set<PrayerName> =
        mutedPrayerDao.getMutedPrayers(date).toPrayerNames()

    override fun observeMutedPrayers(date: String): Flow<Set<PrayerName>> =
        mutedPrayerDao.observeMutedPrayers(date).map { it.toPrayerNames() }

    override suspend fun toggleMute(date: String, prayerName: PrayerName, muted: Boolean) {
        if (muted) {
            val epochDay = date.toLocalDateFromKey().toEpochDay()
            mutedPrayerDao.mute(
                MutedPrayerEntity(date, prayerName.storageKey, epochDay)
            )
        } else {
            mutedPrayerDao.unmute(date, prayerName.storageKey)
        }
    }

    override suspend fun purgePastDates() {
        mutedPrayerDao.purgePast(clock.today().toEpochDay())
    }

    // Rows with a key this version doesn't know are ignored rather than crashing the screen.
    private fun List<String>.toPrayerNames(): Set<PrayerName> =
        mapNotNull { PrayerName.fromStorageKey(it) }.toSet()
}
```

## app\src\main\java\com\mhq\salati\adhan\datasource\alarm\AndroidPrayerAlarmDataSource.kt

```kotlin
package com.mhq.salati.adhan.datasource.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

class AndroidPrayerAlarmDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("prayer_alarm_receiver") private val receiver: ComponentTarget
) : PrayerAlarmDataSource {

    private val alarmManager =
        context.getSystemService(AlarmManager::class.java)

    override fun schedule(request: PrayerAlarmRequest) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            && !alarmManager.canScheduleExactAlarms()
        ) {
            return
        }

        val intent = Intent(context, receiver.componentClass).apply {
            putExtra(EXTRA_PRAYER_NAME, request.prayerKey)
            putExtra(EXTRA_IS_MINOR_TIMING, request.isMinorTiming)
            putExtra(EXTRA_IS_MUTED, request.isMuted)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            request.prayerKey.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            request.triggerAtMillis,
            pendingIntent
        )
    }

    override fun cancel(prayerKey: String) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            prayerKey.hashCode(),
            Intent(context, receiver.componentClass),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private companion object {
        // These keys are read by PrayerAlarmReceiver and must stay identical to its own constants.
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\datasource\alarm\PrayerAlarmDataSource.kt

```kotlin
package com.mhq.salati.adhan.datasource.alarm

interface PrayerAlarmDataSource {
    fun schedule(request: PrayerAlarmRequest)
    fun cancel(prayerKey: String)
}
```

## app\src\main\java\com\mhq\salati\adhan\datasource\alarm\PrayerAlarmRequest.kt

```kotlin
package com.mhq.salati.adhan.datasource.alarm

/** One prayer alarm as the system alarm service needs it.
 * [prayerKey] is the stored name of the prayer. */
data class PrayerAlarmRequest(
    val prayerKey: String,
    val triggerAtMillis: Long,
    val isMinorTiming: Boolean,
    val isMuted: Boolean
)
```

## app\src\main\java\com\mhq\salati\adhan\datasource\database\MutedPrayerDao.kt

```kotlin
package com.mhq.salati.adhan.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MutedPrayerDao {
    @Query("SELECT prayerName FROM muted_prayers WHERE date = :date")
    suspend fun getMutedPrayers(date: String): List<String>

    @Query("SELECT prayerName FROM muted_prayers WHERE date = :date")
    fun observeMutedPrayers(date: String): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun mute(entity: MutedPrayerEntity)

    @Query("DELETE FROM muted_prayers WHERE date = :date AND prayerName = :prayerName")
    suspend fun unmute(date: String, prayerName: String)

    @Query("DELETE FROM muted_prayers WHERE epochDay < :beforeEpochDay")
    suspend fun purgePast(beforeEpochDay: Long)
}
```

## app\src\main\java\com\mhq\salati\adhan\datasource\database\MutedPrayerEntity.kt

```kotlin
package com.mhq.salati.adhan.datasource.database

import androidx.room.Entity

@Entity(
    tableName = "muted_prayers",
    primaryKeys = ["date", "prayerName"]
)
data class MutedPrayerEntity(
    val date: String,
    val prayerName: String,
    val epochDay: Long
)
```

## app\src\main\java\com\mhq\salati\adhan\datasource\playback\AdhanPlaybackDataSource.kt

```kotlin
package com.mhq.salati.adhan.datasource.playback

import kotlinx.coroutines.flow.StateFlow

interface AdhanPlaybackDataSource {
    val status: StateFlow<PlaybackStatus>

    fun start(prayerKey: String, isMinorTiming: Boolean, isMuted: Boolean, soundKey: String)
    fun stop()
    fun updateStatus(status: PlaybackStatus)
}
```

## app\src\main\java\com\mhq\salati\adhan\datasource\playback\AndroidAdhanPlaybackDataSource.kt

```kotlin
package com.mhq.salati.adhan.datasource.playback

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

class AndroidAdhanPlaybackDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("adhan_playback_service") private val service: ComponentTarget
) : AdhanPlaybackDataSource {

    private val _status = MutableStateFlow<PlaybackStatus>(PlaybackStatus.Idle)
    override val status: StateFlow<PlaybackStatus> = _status.asStateFlow()

    override fun start(prayerKey: String, isMinorTiming: Boolean, isMuted: Boolean, soundKey: String) {
        val intent = Intent(context, service.componentClass).apply {
            action = ACTION_START
            putExtra(EXTRA_PRAYER_NAME, prayerKey)
            putExtra(EXTRA_IS_MINOR_TIMING, isMinorTiming)
            putExtra(EXTRA_IS_MUTED, isMuted)
            putExtra(EXTRA_ADHAN_SOUND, soundKey)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    override fun stop() {
        val intent = Intent(context, service.componentClass).apply {
            action = ACTION_STOP
        }
        context.startService(intent)
    }

    override fun updateStatus(status: PlaybackStatus) {
        _status.value = status
    }

    private companion object {
        // These values are read by AdhanPlaybackService and must stay identical to its own constants.
        const val ACTION_START = "com.mhq.salati.action.START_ADHAN"
        const val ACTION_STOP = "com.mhq.salati.action.STOP_ADHAN"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
        const val EXTRA_ADHAN_SOUND = "extra_adhan_sound"
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\datasource\playback\PlaybackStatus.kt

```kotlin
package com.mhq.salati.adhan.datasource.playback

/** What the playback service is doing, as the DataSource remembers it. */
sealed interface PlaybackStatus {
    data object Idle : PlaybackStatus
    data class Playing(val prayerKey: String) : PlaybackStatus
}
```

## app\src\main\java\com\mhq\salati\adhan\di\AdhanModule.kt

```kotlin
package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.data.repoimpl.AdhanPlaybackRepoImpl
import com.mhq.salati.adhan.datasource.playback.AdhanPlaybackDataSource
import com.mhq.salati.adhan.datasource.playback.AndroidAdhanPlaybackDataSource
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import com.mhq.salati.adhan.ui.service.AdhanPlaybackService
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AdhanModule {

    @Binds
    @Singleton
    abstract fun bindAdhanPlaybackController(
        impl: AdhanPlaybackRepoImpl
    ): AdhanPlaybackController

    // Singleton: it holds the playback status shared by the service and every observer.
    @Binds
    @Singleton
    abstract fun bindAdhanPlaybackDataSource(
        impl: AndroidAdhanPlaybackDataSource
    ): AdhanPlaybackDataSource

    companion object {
        @Provides
        @Named("adhan_playback_service")
        fun provideAdhanPlaybackService(): ComponentTarget = ComponentTarget(AdhanPlaybackService::class.java)
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\di\AlarmModule.kt

```kotlin
package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.data.repoimpl.AlarmSchedulerImpl
import com.mhq.salati.adhan.datasource.alarm.AndroidPrayerAlarmDataSource
import com.mhq.salati.adhan.datasource.alarm.PrayerAlarmDataSource
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.adhan.ui.receiver.PrayerAlarmReceiver
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmModule {

    @Binds
    abstract fun bindAlarmScheduler(impl: AlarmSchedulerImpl): AlarmScheduler

    @Binds
    abstract fun bindPrayerAlarmDataSource(impl: AndroidPrayerAlarmDataSource): PrayerAlarmDataSource

    companion object {
        // The DI module is the one place that knows both the DataSource and the receiver class.
        @Provides
        @Named("prayer_alarm_receiver")
        fun providePrayerAlarmReceiver(): ComponentTarget = ComponentTarget(PrayerAlarmReceiver::class.java)
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\di\MutedPrayersModule.kt

```kotlin
package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.datasource.database.MutedPrayerDao
import com.mhq.salati.adhan.data.repoimpl.MutedPrayersRepoImpl
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MutedPrayersModule {
    @Binds
    abstract fun bindMutedPrayersRepository(impl: MutedPrayersRepoImpl): MutedPrayersRepository

    companion object {
        @Provides
        @Singleton
        fun provideMutedPrayerDao(database: SalatiDatabase): MutedPrayerDao {
            return database.mutedPrayerDao()
        }
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\model\AdhanPlaybackState.kt

```kotlin
package com.mhq.salati.adhan.domain.model

sealed interface AdhanPlaybackState {
    data object Idle : AdhanPlaybackState
    data class Playing(val prayerName: String) : AdhanPlaybackState
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\model\AlarmPermissions.kt

```kotlin
package com.mhq.salati.adhan.domain.model

data class AlarmPermissions(
    val hasExactAlarm: Boolean,
    val hasNotification: Boolean
)
```

## app\src\main\java\com\mhq\salati\adhan\domain\model\PrayerAlarm.kt

```kotlin
package com.mhq.salati.adhan.domain.model

import com.mhq.salati.shared.domain.PrayerName

data class PrayerAlarm(
    val prayerName: PrayerName,
    val triggerAtMillis: Long,
    val isMuted: Boolean
) {
    val isMinorTiming: Boolean get() = prayerName.isMinorTiming
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\repo\AdhanPlaybackController.kt

```kotlin
package com.mhq.salati.adhan.domain.repo

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.settings.domain.model.AdhanSound
import kotlinx.coroutines.flow.Flow

interface AdhanPlaybackController {
    val playbackState: Flow<AdhanPlaybackState>
    fun start(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean, adhanSound: AdhanSound)
    fun stop()

    /** Called by the playback service whenever it starts or stops playing. */
    fun updatePlaybackState(state: AdhanPlaybackState)
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\repo\AlarmScheduler.kt

```kotlin
package com.mhq.salati.adhan.domain.repo

import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.shared.domain.PrayerName

interface AlarmScheduler {
    fun schedule(alarm: PrayerAlarm)
    fun cancel(prayerName: PrayerName)
    fun cancelAll()
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\repo\MutedPrayersRepository.kt

```kotlin
package com.mhq.salati.adhan.domain.repo

import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow

interface MutedPrayersRepository {
    suspend fun getMutedPrayers(date: String): Set<PrayerName>
    suspend fun toggleMute(date: String, prayerName: PrayerName, muted: Boolean)
    fun observeMutedPrayers(date: String): Flow<Set<PrayerName>>
    suspend fun purgePastDates()
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\service\PrayerAlarmCoordinator.kt

```kotlin
package com.mhq.salati.adhan.domain.service

import com.mhq.salati.adhan.domain.usecases.RescheduleAllAlarmsUseCase
import com.mhq.salati.adhan.domain.model.AlarmPermissions
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import java.time.LocalDate
import javax.inject.Inject

class PrayerAlarmCoordinator @Inject constructor(
    private val clock: Clock,
    private val permissionChecker: PermissionChecker,
    private val rescheduleAllAlarmsUseCase: RescheduleAllAlarmsUseCase
) {
    suspend fun checkPermissions(): AlarmPermissions = AlarmPermissions(
        hasExactAlarm = permissionChecker.canScheduleExactAlarms(),
        hasNotification = permissionChecker.hasNotificationPermission()
    )

    suspend fun scheduleIfToday(timings: PrayerTimings?, browsedDate: LocalDate) {
        timings ?: return
        if (browsedDate != clock.today()) return
        rescheduleAllAlarmsUseCase()
    }

    /** Checks the permissions and, when exact alarms are allowed, schedules today's alarms again. */
    suspend fun recheck(timings: PrayerTimings?, browsedDate: LocalDate): AlarmPermissions {
        val permissions = checkPermissions()
        if (permissions.hasExactAlarm) {
            scheduleIfToday(timings, browsedDate)
        }
        return permissions
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\usecases\ObserveMutedPrayersUseCase.kt

```kotlin
package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class ObserveMutedPrayersUseCase @Inject constructor(
    private val mutedPrayersRepository: MutedPrayersRepository
) {
    /** The prayers muted on [date]. Rows for days that already passed are cleared first. */
    operator fun invoke(date: LocalDate): Flow<Set<PrayerName>> = flow {
        mutedPrayersRepository.purgePastDates()
        emitAll(mutedPrayersRepository.observeMutedPrayers(date.toDateKey()))
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\usecases\RescheduleAllAlarmsUseCase.kt

```kotlin
package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.alarms.domain.usecases.RescheduleCustomAlarmsUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

/**
 * Arms the next occurrence of every prayer alarm and every custom alarm.
 * Single entry point for: Home load, alarm fired, boot / time change / app update,
 * and the notifications toggle.
 */
class RescheduleAllAlarmsUseCase @Inject constructor(
    private val clock: Clock,
    private val alarmScheduler: AlarmScheduler,
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val rescheduleCustomAlarmsUseCase: RescheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        if (observeSettingsUseCase().first().notificationsEnabled) {
            schedulePrayerAlarms()
        }
        rescheduleCustomAlarmsUseCase()
    }

    private suspend fun schedulePrayerAlarms() {
        val coordinates = getSavedLocationUseCase().first()?.coordinates ?: return
        val now = clock.instant()
        val zone = clock.zone()
        val today = clock.today()
        val tomorrow = today.plusDays(1)

        val todayTimings = cachedTimings(today, coordinates)
        // Year boundary: tomorrow may not be cached yet. Reuse today's clock times
        // (off by a minute or two at most); the next Home load corrects it.
        val tomorrowTimings = cachedTimings(tomorrow, coordinates) ?: todayTimings

        val mutedToday = mutedPrayersRepository.getMutedPrayers(today.toDateKey())
        val mutedTomorrow = mutedPrayersRepository.getMutedPrayers(tomorrow.toDateKey())

        PrayerName.entries.forEach { name ->
            val todayTrigger = todayTimings
                ?.let { today.atTime(it[name]).atZone(zone).toInstant() }

            val alarm = when {
                todayTrigger != null && todayTrigger.isAfter(now) ->
                    PrayerAlarm(name, todayTrigger.toEpochMilli(), name in mutedToday)

                tomorrowTimings != null -> {
                    val trigger = tomorrow.atTime(tomorrowTimings[name]).atZone(zone).toInstant()
                    PrayerAlarm(name, trigger.toEpochMilli(), name in mutedTomorrow)
                }

                else -> null
            }
            alarm?.let { alarmScheduler.schedule(it) }
        }
    }

    private suspend fun cachedTimings(date: LocalDate, coordinates: Coordinates): PrayerTimings? =
        getCachedPrayerTimesUseCase(date.toDateKey(), coordinates)?.timings
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\usecases\StartAdhanPlaybackUseCase.kt

```kotlin
package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class StartAdhanPlaybackUseCase @Inject constructor(
    private val adhanPlaybackController: AdhanPlaybackController,
    private val observeSettingsUseCase: ObserveSettingsUseCase
) {
    suspend operator fun invoke(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean) {
        val adhanSound = observeSettingsUseCase().first().adhanSound
        val isSilenced = !isMinorTiming && adhanSound == AdhanSound.SILENT

        adhanPlaybackController.start(prayerName, isMinorTiming, isMuted || isSilenced, adhanSound)
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\usecases\ToggleMutePrayerUseCase.kt

```kotlin
package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import java.time.LocalDate
import javax.inject.Inject

class ToggleMutePrayerUseCase @Inject constructor(
    private val mutedPrayersRepository: MutedPrayersRepository
) {
    /** Flips the mute state of [prayerName] on [date],
     * based on what is stored (not on what the screen last showed). */
    suspend operator fun invoke(date: LocalDate, prayerName: PrayerName) {
        val dateKey = date.toDateKey()
        val currentlyMuted = prayerName in mutedPrayersRepository.getMutedPrayers(dateKey)
        mutedPrayersRepository.toggleMute(dateKey, prayerName, !currentlyMuted)
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\domain\usecases\UpdateAdhanPlaybackStateUseCase.kt

```kotlin
package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import javax.inject.Inject

class UpdateAdhanPlaybackStateUseCase @Inject constructor(
    private val adhanPlaybackController: AdhanPlaybackController
) {
    operator fun invoke(adhanPlaybackState: AdhanPlaybackState) =
        adhanPlaybackController.updatePlaybackState(adhanPlaybackState)
}
```

## app\src\main\java\com\mhq\salati\adhan\presentation\presenter\AdhanPlaybackPresenter.kt

```kotlin
package com.mhq.salati.adhan.presentation.presenter

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.usecases.UpdateAdhanPlaybackStateUseCase
import com.mhq.salati.settings.presentation.mapper.toAdhanSoundUi
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.shared.domain.usecases.ResolvePrayerNameUseCase
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import javax.inject.Inject

/**
 * What the playback service reports about itself,
 * and how it reads the keys it receives in its Intent.
 */
class AdhanPlaybackPresenter @Inject constructor(
    private val updateAdhanPlaybackStateUseCase: UpdateAdhanPlaybackStateUseCase,
    private val resolvePrayerNameUseCase: ResolvePrayerNameUseCase
) {
    fun onPlaybackStarted(prayerName: String) {
        updateAdhanPlaybackStateUseCase(AdhanPlaybackState.Playing(prayerName))
    }

    fun onPlaybackStopped() {
        updateAdhanPlaybackStateUseCase(AdhanPlaybackState.Idle)
    }

    /** The prayer an Intent key stands for, or null when the key is unknown. */
    fun prayerNameFor(prayerKey: String): PrayerNameUi? =
        resolvePrayerNameUseCase(prayerKey)?.toUi()

    /** The adhan sound an Intent key stands for; the default sound when it is missing or unknown. */
    fun adhanSoundFor(soundKey: String?): AdhanSoundUi = soundKey.toAdhanSoundUi()
}
```

## app\src\main\java\com\mhq\salati\adhan\presentation\presenter\AlarmReschedulePresenter.kt

```kotlin
package com.mhq.salati.adhan.presentation.presenter

import com.mhq.salati.adhan.domain.usecases.RescheduleAllAlarmsUseCase
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

/** What the receivers ask for when alarms need to be armed again. */
class AlarmReschedulePresenter @Inject constructor(
    private val rescheduleAllAlarmsUseCase: RescheduleAllAlarmsUseCase
) {
    suspend fun onRescheduleNeeded() {
        try {
            rescheduleAllAlarmsUseCase()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // A failed re-arm must never crash a receiver.
        }
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\presentation\presenter\PrayerAlarmPresenter.kt

```kotlin
package com.mhq.salati.adhan.presentation.presenter

import com.mhq.salati.adhan.domain.usecases.StartAdhanPlaybackUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/** What the prayer alarm receiver asks for when an alarm goes off. */
class PrayerAlarmPresenter @Inject constructor(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val startAdhanPlaybackUseCase: StartAdhanPlaybackUseCase
) {
    suspend fun onPrayerAlarm(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean) {
        val notificationsEnabled = observeSettingsUseCase().first().notificationsEnabled
        if (notificationsEnabled) {
            startAdhanPlaybackUseCase(prayerName, isMinorTiming, isMuted)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\ui\notification\NotificationHelper.kt

```kotlin
package com.mhq.salati.adhan.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.mhq.salati.adhan.ui.service.AdhanPlaybackService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val CHANNEL_ID = "prayer_times_channel"
        private const val CHANNEL_NAME = "Prayer Times"
        private const val CHANNEL_DESCRIPTION = "Notifications for prayer times"
    }

    fun ensureChannelsCreated() {
        createNotificationChannel()
        createAdhanPlaybackChannel()
    }

    private fun createNotificationChannel() {
        val manager = context.getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = CHANNEL_DESCRIPTION
            setSound(null, null)
        }

        manager.createNotificationChannel(channel)
    }

    private fun createAdhanPlaybackChannel() {
        val channel = NotificationChannel(
            AdhanPlaybackService.CHANNEL_ID,
            "Adhan Playback",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            setSound(null, null)
            description = "Ongoing notification shown while the Adhan is playing"
        }
        context.getSystemService(
            NotificationManager::class.java
        )?.createNotificationChannel(channel)
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\ui\receiver\BootReceiver.kt

```kotlin
package com.mhq.salati.adhan.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mhq.salati.adhan.presentation.presenter.AlarmReschedulePresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Re-arms every alarm after anything that makes the OS forget or invalidate them. */
@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmReschedulePresenter: AlarmReschedulePresenter

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action !in HANDLED_ACTIONS) return

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                alarmReschedulePresenter.onRescheduleNeeded()
            } finally {
                pendingResult.finish()
            }
        }
    }

    private companion object {
        val HANDLED_ACTIONS = setOf(
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_MY_PACKAGE_REPLACED
        )
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\ui\receiver\PrayerAlarmReceiver.kt

```kotlin
package com.mhq.salati.adhan.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mhq.salati.adhan.presentation.presenter.PrayerAlarmPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PrayerAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var prayerAlarmPresenter: PrayerAlarmPresenter

    companion object {
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: return
        val isMinorTiming = intent.getBooleanExtra(EXTRA_IS_MINOR_TIMING, false)
        val isMuted = intent.getBooleanExtra(EXTRA_IS_MUTED, false)

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                prayerAlarmPresenter.onPrayerAlarm(prayerName, isMinorTiming, isMuted)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\adhan\ui\service\AdhanPlaybackService.kt

```kotlin
package com.mhq.salati.adhan.ui.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.mhq.salati.R
import com.mhq.salati.adhan.presentation.presenter.AdhanPlaybackPresenter
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.shared.ui.labelRes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdhanPlaybackService : Service() {

    @Inject
    lateinit var adhanPlaybackPresenter: AdhanPlaybackPresenter
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var focusRequest: AudioFocusRequest? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startPlayback(
                prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: "Prayer",
                isMinorTiming = intent.getBooleanExtra(EXTRA_IS_MINOR_TIMING, false),
                isMuted = intent.getBooleanExtra(EXTRA_IS_MUTED, false),
                adhanSound = adhanPlaybackPresenter.adhanSoundFor(
                    intent.getStringExtra(EXTRA_ADHAN_SOUND)
                )
            )

            ACTION_STOP -> stopPlayback()
        }
        return START_NOT_STICKY
    }

    private fun startPlayback(
        prayerName: String,
        isMinorTiming: Boolean,
        isMuted: Boolean,
        adhanSound: AdhanSoundUi
    ) {
        releasePlayer()

        val notification = buildNotification(prayerName, isMinorTiming)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }

        adhanPlaybackPresenter.onPlaybackStarted(prayerName)

        if (isMuted) {
            return
        }

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
            .setAudioAttributes(audioAttributes)
            .build()

        val focusResult = audioManager?.requestAudioFocus(focusRequest!!)
        if (focusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            stopPlayback()
            return
        }


        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(audioAttributes)
            setDataSource(this@AdhanPlaybackService, soundUri(isMinorTiming, adhanSound))
            setOnPreparedListener { it.start() }
            setOnCompletionListener { onPlaybackCompleted() }
            setOnErrorListener { _, _, _ -> stopPlayback(); true }
            prepareAsync()
        }
    }

    private fun releasePlayer() {
        mediaPlayer?.apply { if (isPlaying) stop(); release() }
        mediaPlayer = null
        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        audioManager = null
    }

    private fun onPlaybackCompleted() {
        releasePlayer()
        stopForeground(STOP_FOREGROUND_DETACH)
    }


    private fun stopPlayback() {
        releasePlayer()
        adhanPlaybackPresenter.onPlaybackStopped()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun soundUri(isMinorTiming: Boolean, adhanSound: AdhanSoundUi): Uri {
        val resId = if (isMinorTiming) {
            R.raw.minor_timing_alert
        } else when (adhanSound) {
            AdhanSoundUi.DEFAULT -> R.raw.default_beep
            AdhanSoundUi.EGYPT -> R.raw.adhan_egypt
            AdhanSoundUi.MAKKAH -> R.raw.adhan_makkah
            AdhanSoundUi.MADINAH -> R.raw.adhan_madinah
            AdhanSoundUi.SILENT -> R.raw.default_beep
        }
        return "android.resource://$packageName/$resId".toUri()
    }

    private fun buildNotification(prayerName: String, isMinorTiming: Boolean): Notification {
        val stopIntent = Intent(
            this,
            AdhanPlaybackService::class.java
        ).apply { action = ACTION_STOP }

        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val localizedPrayerName = adhanPlaybackPresenter
            .prayerNameFor(prayerName)?.let { getString(it.labelRes) } ?: prayerName

        val title = if (isMinorTiming) {
            getString(R.string.minor_timing_playing_title, localizedPrayerName)
        } else {
            getString(R.string.adhan_playing_title)
        }

        val body = if (isMinorTiming) {
            getString(R.string.minor_timing_playing_body, localizedPrayerName)
        } else {
            getString(R.string.adhan_playing_body, localizedPrayerName)
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_salati_white)
            .setSilent(true)
            .setOngoing(false)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.ic_stop, getString(R.string.stop), stopPendingIntent)
            .build()
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        super.onDestroy()
    }

    companion object {
        private const val NOTIFICATION_ID = 501
        const val ACTION_START = "com.mhq.salati.action.START_ADHAN"
        const val ACTION_STOP = "com.mhq.salati.action.STOP_ADHAN"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
        const val EXTRA_ADHAN_SOUND = "extra_adhan_sound"
        const val CHANNEL_ID = "adhan_playback_channel"
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\data\mapper\CustomAlarmMapper.kt

```kotlin
package com.mhq.salati.alarms.data.mapper

import com.mhq.salati.alarms.datasource.database.CustomAlarmEntity
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.PrayerName

// Returns null for a row whose prayer key this version doesn't recognise; the repository skips such rows.
fun CustomAlarmEntity.toDomain(): CustomAlarm? {
    val prayer = PrayerName.fromStorageKey(prayerName) ?: return null
    return CustomAlarm(
        id = id,
        prayerName = prayer,
        label = label,
        offsetMinutes = offsetMinutes,
        offsetDirection = OffsetDirection.valueOf(offsetDirection),
        everyDay = everyDay,
        activeDays = activeDays.split(",").filter { it.isNotBlank() }.map { it.toInt() }.toSet(),
        isEnabled = isEnabled
    )
}

fun CustomAlarm.toEntity(): CustomAlarmEntity =
    CustomAlarmEntity(
        id = id,
        prayerName = prayerName.storageKey,
        label = label,
        offsetMinutes = offsetMinutes,
        offsetDirection = offsetDirection.name,
        everyDay = everyDay,
        activeDays = activeDays.joinToString(","),
        isEnabled = isEnabled
    )
```

## app\src\main\java\com\mhq\salati\alarms\data\repoimpl\CustomAlarmRepoImpl.kt

```kotlin
package com.mhq.salati.alarms.data.repoimpl

import com.mhq.salati.alarms.data.mapper.toDomain
import com.mhq.salati.alarms.data.mapper.toEntity
import com.mhq.salati.alarms.datasource.database.CustomAlarmDao
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomAlarmRepoImpl @Inject constructor(
    private val customAlarmDao: CustomAlarmDao
) : CustomAlarmRepository {
    override fun observeAlarms(): Flow<List<CustomAlarm>> =
        customAlarmDao.observeAllAlarms().map { list -> list.mapNotNull { it.toDomain() } }

    override suspend fun getAlarms(): List<CustomAlarm> =
        customAlarmDao.getAllAlarms().mapNotNull { it.toDomain() }

    override suspend fun getAlarmById(id: Long): CustomAlarm? =
        customAlarmDao.getAlarmById(id)?.toDomain()

    override suspend fun createAlarm(alarm: CustomAlarm): Long =
        customAlarmDao.insertAlarm(alarm.toEntity())

    override suspend fun updateAlarm(alarm: CustomAlarm) =
        customAlarmDao.updateAlarm(alarm.toEntity())

    override suspend fun deleteAlarm(id: Long) = customAlarmDao.deleteAlarmById(id)

    override suspend fun setAlarmEnabled(id: Long, enabled: Boolean) =
        customAlarmDao.setAlarmEnabled(id, enabled)
}
```

## app\src\main\java\com\mhq\salati\alarms\data\repoimpl\CustomAlarmSchedulerImpl.kt

```kotlin
package com.mhq.salati.alarms.data.repoimpl

import com.mhq.salati.alarms.datasource.alarm.CustomAlarmDataSource
import com.mhq.salati.alarms.datasource.alarm.CustomAlarmRequest
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.shared.data.mapper.storageKey
import javax.inject.Inject

class CustomAlarmSchedulerImpl @Inject constructor(
    private val customAlarmDataSource: CustomAlarmDataSource
) : CustomAlarmScheduler {

    override fun schedule(customAlarm: CustomAlarm, triggerAtMillis: Long) {
        customAlarmDataSource.schedule(
            CustomAlarmRequest(
                alarmId = customAlarm.id,
                label = customAlarm.label,
                prayerKey = customAlarm.prayerName.storageKey,
                offsetMinutes = customAlarm.offsetMinutes,
                offsetDirection = customAlarm.offsetDirection.name,
                triggerAtMillis = triggerAtMillis
            )
        )
    }

    override fun cancel(alarmId: Long) {
        customAlarmDataSource.cancel(alarmId)
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\datasource\alarm\AndroidCustomAlarmDataSource.kt

```kotlin
package com.mhq.salati.alarms.datasource.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.getSystemService
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

class AndroidCustomAlarmDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("custom_alarm_receiver") private val receiver: ComponentTarget
) : CustomAlarmDataSource {

    private val alarmManager: AlarmManager? = context.getSystemService()

    override fun schedule(request: CustomAlarmRequest) {
        // Without the exact-alarm permission (Android 12+) scheduling would throw, so skip quietly.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager?.canScheduleExactAlarms() == false) {
            return
        }

        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            request.triggerAtMillis,
            pendingIntentFor(
                request.alarmId,
                request.label,
                request.prayerKey,
                request.offsetMinutes,
                request.offsetDirection
            )
        )
    }

    override fun cancel(alarmId: Long) {
        val pendingIntent = pendingIntentFor(alarmId, "", "", 0, null)
        alarmManager?.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun pendingIntentFor(
        alarmId: Long,
        label: String,
        prayerKey: String,
        offsetMinutes: Int,
        offsetDirection: String?
    ): PendingIntent {
        val intent = Intent(context, receiver.componentClass).apply {
            putExtra(EXTRA_ALARM_ID, alarmId)
            putExtra(EXTRA_LABEL, label)
            putExtra(EXTRA_PRAYER_NAME, prayerKey)
            putExtra(EXTRA_OFFSET_MINUTES, offsetMinutes)
            putExtra(EXTRA_OFFSET_DIRECTION, offsetDirection)
        }
        return PendingIntent.getBroadcast(
            context,
            alarmId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private companion object {
        // These keys are read by CustomAlarmReceiver and must stay identical to its CustomAlarmIntentKeys.
        const val EXTRA_ALARM_ID = "extra_alarm_id"
        const val EXTRA_LABEL = "extra_label"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_OFFSET_MINUTES = "extra_offset_minutes"
        const val EXTRA_OFFSET_DIRECTION = "extra_offset_direction"
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\datasource\alarm\CustomAlarmDataSource.kt

```kotlin
package com.mhq.salati.alarms.datasource.alarm

interface CustomAlarmDataSource {
    fun schedule(request: CustomAlarmRequest)
    fun cancel(alarmId: Long)
}
```

## app\src\main\java\com\mhq\salati\alarms\datasource\alarm\CustomAlarmRequest.kt

```kotlin
package com.mhq.salati.alarms.datasource.alarm

/** One custom alarm as the system alarm service needs it. */
data class CustomAlarmRequest(
    val alarmId: Long,
    val label: String,
    val prayerKey: String,
    val offsetMinutes: Int,
    val offsetDirection: String,
    val triggerAtMillis: Long
)
```

## app\src\main\java\com\mhq\salati\alarms\datasource\database\CustomAlarmDao.kt

```kotlin
package com.mhq.salati.alarms.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomAlarmDao {
    @Query("SELECT * FROM custom_alarms ORDER BY id DESC")
    fun observeAllAlarms(): Flow<List<CustomAlarmEntity>>

    @Query("SELECT * FROM custom_alarms ORDER BY id DESC")
    suspend fun getAllAlarms(): List<CustomAlarmEntity>

    @Query("SELECT * FROM custom_alarms WHERE id = :id")
    suspend fun getAlarmById(id: Long): CustomAlarmEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(entity: CustomAlarmEntity): Long

    @Update
    suspend fun updateAlarm(entity: CustomAlarmEntity)

    @Query("DELETE FROM custom_alarms WHERE id = :id")
    suspend fun deleteAlarmById(id: Long)

    @Query("UPDATE custom_alarms SET isEnabled = :enabled WHERE id = :id")
    suspend fun setAlarmEnabled(id: Long, enabled: Boolean)
}
```

## app\src\main\java\com\mhq\salati\alarms\datasource\database\CustomAlarmEntity.kt

```kotlin
package com.mhq.salati.alarms.datasource.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_alarms")
data class CustomAlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val prayerName: String,
    val label: String,
    val offsetMinutes: Int,
    val offsetDirection: String,
    val everyDay: Boolean,
    val activeDays: String,
    val isEnabled: Boolean
)
```

## app\src\main\java\com\mhq\salati\alarms\di\AlarmsBindsModule.kt

```kotlin
package com.mhq.salati.alarms.di

import com.mhq.salati.alarms.data.repoimpl.CustomAlarmRepoImpl
import com.mhq.salati.alarms.data.repoimpl.CustomAlarmSchedulerImpl
import com.mhq.salati.alarms.datasource.alarm.AndroidCustomAlarmDataSource
import com.mhq.salati.alarms.datasource.alarm.CustomAlarmDataSource
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmsBindsModule {
    @Binds
    @Singleton
    abstract fun bindCustomAlarmRepository(impl: CustomAlarmRepoImpl): CustomAlarmRepository

    @Binds
    @Singleton
    abstract fun bindCustomAlarmScheduler(impl: CustomAlarmSchedulerImpl): CustomAlarmScheduler

    @Binds
    @Singleton
    abstract fun bindCustomAlarmDataSource(impl: AndroidCustomAlarmDataSource): CustomAlarmDataSource
}
```

## app\src\main\java\com\mhq\salati\alarms\di\AlarmsProvidesModule.kt

```kotlin
package com.mhq.salati.alarms.di

import com.mhq.salati.alarms.datasource.database.CustomAlarmDao
import com.mhq.salati.alarms.ui.receiver.CustomAlarmReceiver
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AlarmsProvidesModule {
    @Provides
    fun provideCustomAlarmDao(database: SalatiDatabase): CustomAlarmDao = database.customAlarmDao()

    // The DI module is the one place that knows both the DataSource and the receiver class.
    @Provides
    @Named("custom_alarm_receiver")
    fun provideCustomAlarmReceiver(): ComponentTarget =
        ComponentTarget(CustomAlarmReceiver::class.java)
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\model\CustomAlarm.kt

```kotlin
package com.mhq.salati.alarms.domain.model

import com.mhq.salati.shared.domain.PrayerName

data class CustomAlarm(
    val id: Long = 0L,
    val prayerName: PrayerName,
    val label: String,
    val offsetMinutes: Int,
    val offsetDirection: OffsetDirection,
    val everyDay: Boolean,
    val activeDays: Set<Int>,
    val isEnabled: Boolean = true
)
```

## app\src\main\java\com\mhq\salati\alarms\domain\model\CustomAlarmDays.kt

```kotlin
package com.mhq.salati.alarms.domain.model

import java.util.Calendar

private val AllWeekDays: Set<Int> = (Calendar.SUNDAY..Calendar.SATURDAY).toSet()

/** Days this alarm runs on, as [Calendar] constants. */
private val CustomAlarm.runningDays: Set<Int>
    get() = if (everyDay) AllWeekDays else activeDays

/** Offset from the prayer in minutes: negative before, positive after. */
private val CustomAlarm.signedOffsetMinutes: Int
    get() = if (offsetDirection == OffsetDirection.AFTER) offsetMinutes else -offsetMinutes

/**
 * Returns this alarm with [calendarDay] switched on or off.
 *
 * - Turning off the last remaining day is ignored (the alarm is returned unchanged).
 * - When all seven days end up selected the alarm becomes `everyDay` with an empty day set.
 */
fun CustomAlarm.withDayToggled(calendarDay: Int): CustomAlarm {
    val current = runningDays
    val updated = if (calendarDay in current) current - calendarDay else current + calendarDay

    return when {
        updated.isEmpty() -> this
        updated.size == AllWeekDays.size -> copy(everyDay = true, activeDays = emptySet())
        else -> copy(everyDay = false, activeDays = updated)
    }
}

/**
 * True when this alarm and [other] would fire at the same time on at least one shared day:
 * same prayer, same signed offset (so "0 before" equals "0 after"), and overlapping days.
 */
fun CustomAlarm.conflictsWith(other: CustomAlarm): Boolean =
    prayerName == other.prayerName &&
            signedOffsetMinutes == other.signedOffsetMinutes &&
            runningDays.intersect(other.runningDays).isNotEmpty()
```

## app\src\main\java\com\mhq\salati\alarms\domain\model\DayToggleResult.kt

```kotlin
package com.mhq.salati.alarms.domain.model

/** Outcome of switching one weekday on an alarm. */
enum class DayToggleResult {
    /** The change was saved and alarms were rescheduled. */
    APPLIED,
    /** Nothing changed (alarm missing, or the tap would remove the last remaining day). */
    UNCHANGED,
    /** The change would make this alarm fire at the same time as another alarm. */
    CONFLICT
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\model\OffsetDirection.kt

```kotlin
package com.mhq.salati.alarms.domain.model

enum class OffsetDirection {
    BEFORE,
    AFTER
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\model\SaveAlarmResult.kt

```kotlin
package com.mhq.salati.alarms.domain.model

/** Outcome of saving a custom alarm. */
enum class SaveAlarmResult {
    /** The alarm was created or updated. */
    SAVED,
    /** The alarm would fire at the same time as another alarm, so nothing was saved. */
    CONFLICT
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\repo\CustomAlarmRepository.kt

```kotlin
package com.mhq.salati.alarms.domain.repo

import com.mhq.salati.alarms.domain.model.CustomAlarm
import kotlinx.coroutines.flow.Flow

interface CustomAlarmRepository {
    fun observeAlarms(): Flow<List<CustomAlarm>>
    suspend fun getAlarms(): List<CustomAlarm>
    suspend fun getAlarmById(id: Long): CustomAlarm?
    suspend fun createAlarm(alarm: CustomAlarm): Long
    suspend fun updateAlarm(alarm: CustomAlarm)
    suspend fun deleteAlarm(id: Long)
    suspend fun setAlarmEnabled(id: Long, enabled: Boolean)
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\repo\CustomAlarmScheduler.kt

```kotlin
package com.mhq.salati.alarms.domain.repo

import com.mhq.salati.alarms.domain.model.CustomAlarm

interface CustomAlarmScheduler {
    fun schedule(customAlarm: CustomAlarm, triggerAtMillis: Long)
    fun cancel(alarmId: Long)
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\CreateCustomAlarmUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class CreateCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository
) {
    suspend operator fun invoke(alarm: CustomAlarm): Long = customAlarmRepository.createAlarm(alarm)
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\DeleteCustomAlarmUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class DeleteCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler
) {
    suspend operator fun invoke(id: Long) {
        customAlarmScheduler.cancel(id)
        customAlarmRepository.deleteAlarm(id)
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\ObserveCustomAlarmsUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCustomAlarmsUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository
) {
    operator fun invoke(): Flow<List<CustomAlarm>> = customAlarmRepository.observeAlarms()
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\RescheduleCustomAlarmsUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Re-runs custom-alarm scheduling against today's cached prayer timings.
 * Used by callers outside Home's load flow (the Alarms screen itself) so a
 * newly created/edited/re-enabled alarm takes effect immediately instead of
 * waiting for Home to next reload prayer times.
 */
class RescheduleCustomAlarmsUseCase @Inject constructor(
    private val clock: Clock,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val scheduleCustomAlarmsUseCase: ScheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke() {
        val location = getSavedLocationUseCase().first() ?: return
        val todayKey = clock.today().toDateKey()
        val cached = getCachedPrayerTimesUseCase(todayKey, location.coordinates)
            ?: return
        scheduleCustomAlarmsUseCase(cached.timings)
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\SaveCustomAlarmUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.SaveAlarmResult
import com.mhq.salati.alarms.domain.model.conflictsWith
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

/**
 * Saves [alarm]: a new one when its id is 0, otherwise an update.
 * Refuses an alarm that would ring at the same time as another one,
 * and reschedules afterwards so the change takes effect immediately.
 *
 * Reads the other alarms from the repository (the source of truth), like [ToggleCustomAlarmDayUseCase].
 */
class SaveCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val createCustomAlarmUseCase: CreateCustomAlarmUseCase,
    private val updateCustomAlarmUseCase: UpdateCustomAlarmUseCase,
    private val rescheduleCustomAlarmsUseCase: RescheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke(alarm: CustomAlarm): SaveAlarmResult {
        val hasConflict = customAlarmRepository.getAlarms()
            .any { it.id != alarm.id && it.conflictsWith(alarm) }
        if (hasConflict) return SaveAlarmResult.CONFLICT

        if (alarm.id == 0L) createCustomAlarmUseCase(alarm) else updateCustomAlarmUseCase(alarm)
        rescheduleQuietly()
        return SaveAlarmResult.SAVED
    }

    // Best effort: if today's timings aren't cached yet (e.g. the app was just opened),
    // Home's own load schedules everything anyway. A failure here must not
    // turn a successful save into an error.
    private suspend fun rescheduleQuietly() {
        try {
            rescheduleCustomAlarmsUseCase()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // swallowed intentionally
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\ScheduleCustomAlarmsUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

/** How far ahead to look for an alarm's next active day (today plus a full week). */
private const val MAX_DAYS_AHEAD = 7

/**
 * Arms every enabled custom alarm for its next occurrence, which may be today or a later active day.
 * Disabled alarms, and alarms with no active day, are cancelled.
 */
class ScheduleCustomAlarmsUseCase @Inject constructor(
    private val clock: Clock,
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase
) {
    suspend operator fun invoke(timings: PrayerTimings) {
        val now = clock.now()
        val zone = clock.zone()
        val location = getSavedLocationUseCase().first()

        customAlarmRepository.getAlarms().forEach { alarm ->
            val trigger = if (alarm.isEnabled) {
                nextTrigger(alarm, now, timings) { date ->
                    // The actual cached prayer time for a future day; null when that day isn't cached yet.
                    location?.let {
                        getCachedPrayerTimesUseCase(date.toDateKey(), it.coordinates)
                            ?.timings?.get(alarm.prayerName)
                    }
                }
            } else {
                null
            }

            if (trigger == null) {
                customAlarmScheduler.cancel(alarm.id)
            } else {
                customAlarmScheduler.schedule(alarm, trigger.atZone(zone).toInstant().toEpochMilli())
            }
        }
    }

    /**
     * The first moment after [now] at which [alarm] should ring, or null if it never will.
     *
     * Walks forward day by day, skipping days the alarm doesn't run on. Today uses [todayTimings];
     * later days use [futurePrayerTime], falling back to today's prayer time if that day isn't
     * cached yet (it self-corrects on the next run).
     */
    private suspend fun nextTrigger(
        alarm: CustomAlarm,
        now: LocalDateTime,
        todayTimings: PrayerTimings,
        futurePrayerTime: suspend (LocalDate) -> LocalTime?
    ): LocalDateTime? {
        val today = now.toLocalDate()
        val todayPrayerTime = todayTimings[alarm.prayerName]

        for (daysAhead in 0..MAX_DAYS_AHEAD) {
            val date = today.plusDays(daysAhead.toLong())
            if (!alarm.runsOn(date)) continue

            val prayerTime = if (daysAhead == 0) {
                todayPrayerTime
            } else {
                futurePrayerTime(date) ?: todayPrayerTime
            }
            val trigger = alarm.triggerOn(date, prayerTime)
            if (trigger.isAfter(now)) return trigger
        }
        return null
    }

    private fun CustomAlarm.runsOn(date: LocalDate): Boolean =
        everyDay || date.calendarDayOfWeek() in activeDays

    private fun CustomAlarm.triggerOn(date: LocalDate, prayerTime: LocalTime): LocalDateTime {
        val offset = if (offsetDirection == OffsetDirection.BEFORE) -offsetMinutes else offsetMinutes
        return date.atTime(prayerTime.hour, prayerTime.minute).plusMinutes(offset.toLong())
    }

    // Stored days use java.util.Calendar numbering: Sunday = 1 ... Saturday = 7.
    private fun LocalDate.calendarDayOfWeek(): Int = dayOfWeek.value % 7 + 1
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\ToggleCustomAlarmDayUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.DayToggleResult
import com.mhq.salati.alarms.domain.model.conflictsWith
import com.mhq.salati.alarms.domain.model.withDayToggled
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

/**
 * Switches one weekday on or off for the alarm with the given [id],
 * then reschedules so the change takes effect immediately.
 *
 * Reads the alarm from the repository (the source of truth) so rapid taps never act on a stale copy.
 */
class ToggleCustomAlarmDayUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val rescheduleCustomAlarmsUseCase: RescheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke(id: Long, calendarDay: Int): DayToggleResult {
        val alarm = customAlarmRepository.getAlarmById(id) ?: return DayToggleResult.UNCHANGED
        val updated = alarm.withDayToggled(calendarDay)
        if (updated == alarm) return DayToggleResult.UNCHANGED

        val hasConflict = customAlarmRepository.getAlarms()
            .any { it.id != id && it.conflictsWith(updated) }
        if (hasConflict) return DayToggleResult.CONFLICT

        customAlarmRepository.updateAlarm(updated)
        rescheduleCustomAlarmsUseCase()
        return DayToggleResult.APPLIED
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\ToggleCustomAlarmUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class ToggleCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler,
    private val rescheduleCustomAlarmsUseCase: RescheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke(id: Long, enabled: Boolean) {
        customAlarmRepository.setAlarmEnabled(id, enabled)
        if (enabled) {
            rescheduleCustomAlarmsUseCase()
        } else {
            customAlarmScheduler.cancel(id)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\domain\usecases\UpdateCustomAlarmUseCase.kt

```kotlin
package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class UpdateCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository
) {
    suspend operator fun invoke(alarm: CustomAlarm) = customAlarmRepository.updateAlarm(alarm)
}
```

## app\src\main\java\com\mhq\salati\alarms\presentation\contract\AlarmsContract.kt

```kotlin
package com.mhq.salati.alarms.presentation.contract

import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.shared.presentation.model.MessageUi

/**
 * MVI contract for the custom alarms screen,
 * where users manage alarms that fire at an offset before or after a prayer time
 * (for example, "15 minutes before Fajr").
 *
 * The screen renders a single [State],
 * reports user actions as [Intent]s,
 * and reacts to one-shot [Effect]s
 * (Snack-bars and similar) emitted by the ViewModel.
 */
object AlarmsContract {

    /**
     * Immutable UI state for the alarms screen.
     *
     * @property isLoading
     * `true` until the first emission of the alarm list arrives.
     * Defaults to `true` so the screen shows a loading indicator on first composition.
     * @property alarms
     * All persisted custom alarms, kept in sync with the database.
     * @property isEditorVisible
     * Whether the add/edit alarm editor is currently shown.
     * @property editingAlarm
     * The alarm being edited.
     * `null` while the editor is closed,
     * and also `null` when the editor is open in "create" mode ([isEditorVisible] is `true`).
     * @property errorMessage
     * The latest error to surface inline, or `null` when there is none.
     */
    data class State(
        val isLoading: Boolean = true,
        val alarms: List<CustomAlarmUi> = emptyList(),
        val isEditorVisible: Boolean = false,
        val editingAlarm: CustomAlarmUi? = null,
        val errorMessage: MessageUi? = null
    )

    /** User actions and lifecycle events the alarms screen can send to its ViewModel. */
    sealed interface Intent {

        /** (Re)starts observing the persisted alarm list.
         * The ViewModel also does this on init. */
        data object LoadAlarms : Intent

        /** Opens the editor in "create" mode with no pre-filled alarm. */
        data object AddAlarm : Intent

        /** Opens the editor pre-filled with [alarm]. */
        data class EditAlarm(val alarm: CustomAlarmUi) : Intent

        /**
         * Persists [alarm] and closes the editor.
         *
         * An alarm with `id == 0L` is treated as new and created;
         * any other id updates the existing alarm.
         * Creating an alarm equivalent to an existing one
         * (same prayer, offset,direction and active days) is rejected with [Effect.ShowError].
         */
        data class SaveAlarm(val alarm: CustomAlarmUi) : Intent

        /** Permanently deletes the alarm with the given [id]. */
        data class DeleteAlarm(val id: Long) : Intent

        /** Enables or disables the alarm with the given [id] without deleting it. */
        data class ToggleAlarm(val id: Long, val enabled: Boolean) : Intent

        /** The user tapped [calendarDay] on the card of the alarm with the given [id]. */
        data class AlarmDayToggled(val id: Long, val calendarDay: Int) : Intent

        /** Closes the editor and discards any unsaved edits. */
        data object DismissEditor : Intent
    }

    /** One-shot side effects, consumed once by the container. */
    sealed interface Effect {

        /** Shows [message] to the user as a transient error (for example, in a snackbar). */
        data class ShowError(val message: MessageUi) : Effect

        /** Confirms that an alarm was saved.
         * [message] defaults to the standard "alarm saved" text. */
        data class AlarmSaved(val message: MessageUi = MessageUi.ALARM_SAVED) : Effect
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\presentation\mapper\CustomAlarmUiMapper.kt

```kotlin
package com.mhq.salati.alarms.presentation.mapper

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.shared.presentation.mapper.toDomain
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.alarms.domain.model.conflictsWith as domainConflictsWith

fun CustomAlarm.toUi(): CustomAlarmUi = CustomAlarmUi(
    id = id,
    prayerName = prayerName.toUi(),
    label = label,
    offsetMinutes = offsetMinutes,
    offsetDirection = offsetDirection.toUi(),
    everyDay = everyDay,
    activeDays = activeDays,
    isEnabled = isEnabled
)

fun CustomAlarmUi.toDomain(): CustomAlarm = CustomAlarm(
    id = id,
    prayerName = prayerName.toDomain(),
    label = label,
    offsetMinutes = offsetMinutes,
    offsetDirection = offsetDirection.toDomain(),
    everyDay = everyDay,
    activeDays = activeDays,
    isEnabled = isEnabled
)

/**
 * True when this alarm and [other] would ring at the same time on a shared day.
 * The rule itself stays in the domain; this only lends it to the screens.
 */
fun CustomAlarmUi.conflictsWith(other: CustomAlarmUi): Boolean =
    toDomain().domainConflictsWith(other.toDomain())
```

## app\src\main\java\com\mhq\salati\alarms\presentation\mapper\OffsetDirectionUiMapper.kt

```kotlin
package com.mhq.salati.alarms.presentation.mapper

import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi

fun OffsetDirection.toUi(): OffsetDirectionUi = when (this) {
    OffsetDirection.BEFORE -> OffsetDirectionUi.BEFORE
    OffsetDirection.AFTER -> OffsetDirectionUi.AFTER
}

fun OffsetDirectionUi.toDomain(): OffsetDirection = when (this) {
    OffsetDirectionUi.BEFORE -> OffsetDirection.BEFORE
    OffsetDirectionUi.AFTER -> OffsetDirection.AFTER
}

/**
 * Reads the direction key the custom alarm receiver gets in its Intent.
 * CustomAlarmSchedulerImpl writes it as `OffsetDirection.name`.
 * A missing or unknown key gives null.
 */
fun String?.toOffsetDirectionUiOrNull(): OffsetDirectionUi? =
    this?.let { key -> runCatching { OffsetDirection.valueOf(key) }.getOrNull() }?.toUi()
```

## app\src\main\java\com\mhq\salati\alarms\presentation\model\CustomAlarmUi.kt

```kotlin
package com.mhq.salati.alarms.presentation.model

import com.mhq.salati.shared.presentation.model.PrayerNameUi

/** A custom alarm as the alarms screen shows and edits it. */
data class CustomAlarmUi(
    val id: Long = 0L,
    val prayerName: PrayerNameUi,
    val label: String,
    val offsetMinutes: Int,
    val offsetDirection: OffsetDirectionUi,
    val everyDay: Boolean,
    val activeDays: Set<Int>,
    val isEnabled: Boolean = true
)
```

## app\src\main\java\com\mhq\salati\alarms\presentation\model\OffsetDirectionUi.kt

```kotlin
package com.mhq.salati.alarms.presentation.model

enum class OffsetDirectionUi {
    BEFORE,
    AFTER
}
```

## app\src\main\java\com\mhq\salati\alarms\presentation\presenter\CustomAlarmNotificationPresenter.kt

```kotlin
package com.mhq.salati.alarms.presentation.presenter

import com.mhq.salati.alarms.presentation.mapper.toOffsetDirectionUiOrNull
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.shared.domain.usecases.ResolvePrayerNameUseCase
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/** What the custom alarm notification needs to know when an alarm rings. */
class CustomAlarmNotificationPresenter @Inject constructor(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val resolvePrayerNameUseCase: ResolvePrayerNameUseCase
) {
    /** The sound the user picked for custom alarms. */
    suspend fun customAlarmSound(): CustomAlarmSoundUi =
        observeSettingsUseCase().first().customAlarmSound.toUi()

    /** The prayer an Intent key stands for, or null when the key is unknown. */
    fun prayerNameFor(prayerKey: String): PrayerNameUi? =
        resolvePrayerNameUseCase(prayerKey)?.toUi()

    /** The direction an Intent key stands for, or null when it is missing or unknown. */
    fun offsetDirectionFor(directionKey: String?): OffsetDirectionUi? =
        directionKey.toOffsetDirectionUiOrNull()
}
```

## app\src\main\java\com\mhq\salati\alarms\presentation\viewmodel\AlarmsViewModel.kt

```kotlin
package com.mhq.salati.alarms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.alarms.domain.model.DayToggleResult
import com.mhq.salati.alarms.domain.model.SaveAlarmResult
import com.mhq.salati.alarms.domain.usecases.DeleteCustomAlarmUseCase
import com.mhq.salati.alarms.domain.usecases.ObserveCustomAlarmsUseCase
import com.mhq.salati.alarms.domain.usecases.SaveCustomAlarmUseCase
import com.mhq.salati.alarms.domain.usecases.ToggleCustomAlarmDayUseCase
import com.mhq.salati.alarms.domain.usecases.ToggleCustomAlarmUseCase
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.Effect
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.Intent
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.State
import com.mhq.salati.alarms.presentation.mapper.toDomain
import com.mhq.salati.alarms.presentation.mapper.toUi
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val observeCustomAlarmsUseCase: ObserveCustomAlarmsUseCase,
    private val saveCustomAlarmUseCase: SaveCustomAlarmUseCase,
    private val deleteCustomAlarmUseCase: DeleteCustomAlarmUseCase,
    private val toggleCustomAlarmUseCase: ToggleCustomAlarmUseCase,
    private val toggleCustomAlarmDayUseCase: ToggleCustomAlarmDayUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init { observeAlarms() }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.LoadAlarms -> observeAlarms()
            is Intent.AddAlarm -> _state.update { it.copy(isEditorVisible = true, editingAlarm = null) }
            is Intent.EditAlarm -> _state.update { it.copy(isEditorVisible = true, editingAlarm = intent.alarm) }
            is Intent.DismissEditor -> _state.update { it.copy(isEditorVisible = false, editingAlarm = null) }
            is Intent.SaveAlarm -> saveAlarm(intent.alarm)
            is Intent.DeleteAlarm -> deleteAlarm(intent.id)
            is Intent.ToggleAlarm -> toggleAlarm(intent.id, intent.enabled)
            is Intent.AlarmDayToggled -> toggleAlarmDay(intent.id, intent.calendarDay)
        }
    }

    private fun observeAlarms() {
        viewModelScope.launch {
            try {
                observeCustomAlarmsUseCase().collect { alarms ->
                    _state.update { it.copy(isLoading = false, alarms = alarms.map { alarm -> alarm.toUi() }) }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = MessageUi.COULDNT_LOAD_ALARMS
                _state.update { it.copy(isLoading = false, errorMessage = message) }
                _effect.send(Effect.ShowError(message))
            }
        }
    }

    private fun saveAlarm(alarm: CustomAlarmUi) {
        viewModelScope.launch {
            try {
                when (saveCustomAlarmUseCase(alarm.toDomain())) {
                    SaveAlarmResult.CONFLICT ->
                        _effect.send(Effect.ShowError(MessageUi.DUPLICATE_ALARM_EXISTS))
                    SaveAlarmResult.SAVED -> {
                        _state.update { it.copy(isEditorVisible = false, editingAlarm = null) }
                        _effect.send(Effect.AlarmSaved())
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = MessageUi.COULDNT_SAVE_ALARM
                _state.update { it.copy(errorMessage = message) }
                _effect.send(Effect.ShowError(message))
            }
        }
    }

    private fun deleteAlarm(id: Long) {
        viewModelScope.launch {
            try {
                deleteCustomAlarmUseCase(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(MessageUi.COULDNT_DELETE_ALARM))
            }
        }
    }

    private fun toggleAlarm(id: Long, enabled: Boolean) {
        viewModelScope.launch {
            try {
                toggleCustomAlarmUseCase(id, enabled)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(MessageUi.COULDNT_UPDATE_ALARM))
            }
        }
    }

    private fun toggleAlarmDay(id: Long, calendarDay: Int) {
        viewModelScope.launch {
            try {
                if (toggleCustomAlarmDayUseCase(id, calendarDay) == DayToggleResult.CONFLICT) {
                    _effect.send(Effect.ShowError(MessageUi.DUPLICATE_ALARM_EXISTS))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(MessageUi.COULDNT_UPDATE_ALARM))
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\AddEditAlarmFormContent.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.mapper.conflictsWith
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.alarms.ui.state.AlarmFormState
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.format.TextStyle as DayNameStyle

private const val MAX_OFFSET_MINUTES = 180
private const val MAX_OFFSET_DIGITS = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmFormContent(
    formState: AlarmFormState,
    existingAlarm: CustomAlarmUi?,
    existingAlarms: List<CustomAlarmUi>,
    onSave: (CustomAlarmUi) -> Unit,
    onDismiss: () -> Unit
) {
    var prayerName by formState.prayerName
    var label by formState.label
    var minutesText by formState.minutesText
    var offsetDirection by formState.offsetDirection
    var activeDays by formState.activeDays
    var hasSaved by remember { mutableStateOf(false) }
    var isPrayerMenuExpanded by remember { mutableStateOf(false) }

    val offsetMinutes = minutesText.toIntOrNull()
    val fullWeekSelected = activeDays.size == 7
    val draft = offsetMinutes?.let {
        CustomAlarmUi(
            id = existingAlarm?.id ?: 0L,
            prayerName = prayerName,
            label = label.trim(),
            offsetMinutes = it,
            offsetDirection = offsetDirection,
            everyDay = fullWeekSelected,
            activeDays = if (fullWeekSelected) emptySet() else activeDays,
            isEnabled = existingAlarm?.isEnabled ?: true
        )
    }
    val hasConflict = !hasSaved && draft != null && existingAlarms.any {
        it.id != draft.id && it.conflictsWith(draft)
    }
    val canSave = draft != null && activeDays.isNotEmpty() && !hasConflict

    val configuration = LocalConfiguration.current
    val primaryLocale = configuration.locales
    val isLandscape = isWideLayout()
    val minutesUnit = stringResource(R.string.minutes_short)

    Column(
        modifier = Modifier
            .then(if (isLandscape) Modifier.fillMaxHeight() else Modifier.wrapContentHeight())
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AlarmTitleEditor(
            label = label,
            onLabelChange = { label = it }
        )

        // ROW 1: [Minutes Input] (Left) | [Before/After Vertical Stack] (Center) | [Prayer Dropdown] (Right)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // Left Element: Minutes Input
            OutlinedTextField(
                value = minutesText,
                onValueChange = { input ->
                    val digits = input.filter { it.isDigit() }.take(MAX_OFFSET_DIGITS)
                    val value = digits.toIntOrNull()
                    minutesText = if (value != null && value > MAX_OFFSET_MINUTES) {
                        MAX_OFFSET_MINUTES.toString()
                    } else {
                        digits
                    }
                },
                label = { Text(stringResource(R.string.alarm_minutes_field_label)) },
                suffix = { Text(minutesUnit) },
                singleLine = true,
                isError = hasConflict,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant,
                        RoundedCornerShape(8.dp)
                    )
                    .selectableGroup()
            ) {
                OffsetDirectionSegment(
                    text = stringResource(R.string.before),
                    selected = offsetDirection == OffsetDirectionUi.BEFORE,
                    onClick = { offsetDirection = OffsetDirectionUi.BEFORE },
                    modifier = Modifier.weight(1f)
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                OffsetDirectionSegment(
                    text = stringResource(R.string.after),
                    selected = offsetDirection == OffsetDirectionUi.AFTER,
                    onClick = { offsetDirection = OffsetDirectionUi.AFTER },
                    modifier = Modifier.weight(1f)
                )
            }
            // Right Element: Prayer Dropdown Selection
            ExposedDropdownMenuBox(
                expanded = isPrayerMenuExpanded,
                onExpandedChange = { isPrayerMenuExpanded = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = stringResource(prayerName.labelRes),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.prayer)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPrayerMenuExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
                ExposedDropdownMenu(
                    expanded = isPrayerMenuExpanded,
                    onDismissRequest = { isPrayerMenuExpanded = false }
                ) {
                    PrayerNameUi.entries.forEach { name ->
                        DropdownMenuItem(
                            text = { Text(stringResource(name.labelRes)) },
                            onClick = { prayerName = name; isPrayerMenuExpanded = false }
                        )
                    }
                }
            }
        }

        if (hasConflict) {
            Text(
                text = stringResource(R.string.duplicate_alarm_exists),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        // ROW 2: Balanced 7-day circles line
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.repeat_every_day),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            val primaryLocaleInstance =
                primaryLocale[0] // Extracts a single stable Locale from the list container

            val badges = remember(primaryLocaleInstance) {
                WeekDays.map { (calendarDay, dayOfWeek) ->
                    DayBadge(
                        calendarDay = calendarDay,
                        initial = dayOfWeek.getDisplayName(
                            DayNameStyle.NARROW,
                            primaryLocaleInstance
                        ),
                        fullName = dayOfWeek.getDisplayName(
                            DayNameStyle.FULL,
                            primaryLocaleInstance
                        )
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth()
            ) {
                badges.forEach { badge ->
                    val isDayActive = activeDays.contains(badge.calendarDay)
                    val bgCircleColor = if (isDayActive) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                    }
                    val txtCircleColor = if (isDayActive) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(bgCircleColor)
                            .toggleable(
                                value = isDayActive,
                                role = Role.Checkbox,
                                onValueChange = {
                                    activeDays = if (isDayActive) {
                                        activeDays - badge.calendarDay
                                    } else {
                                        activeDays + badge.calendarDay
                                    }
                                }
                            )
                            .semantics { contentDescription = badge.fullName }
                    ) {
                        Text(
                            text = badge.initial,
                            fontSize = 13.sp,
                            fontWeight = if (isDayActive) FontWeight.Bold else FontWeight.Normal,
                            color = txtCircleColor,
                            modifier = Modifier.clearAndSetSemantics { }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        // Actions Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLandscape) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.cd_back))
                }
            }
            Button(
                onClick = { hasSaved = true; draft?.let(onSave) },
                enabled = canSave && !hasSaved,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.weight(if (isLandscape) 1f else 2f)
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}

@Preview(name = "Form - Portrait", showBackground = true)
@Preview(
    name = "Form - Landscape",
    device = "spec:width=800dp,height=400dp,dpi=440",
    showBackground = true
)
@Composable
private fun AddEditAlarmFormContentPreview() {
    SalatiTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            AddEditAlarmFormContent(
                formState = remember { AlarmFormState(null) },
                existingAlarm = null,
                existingAlarms = emptyList(),
                onSave = {},
                onDismiss = {}
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\AddEditAlarmSheet.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.ui.state.AlarmFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmSheet(
    formState: AlarmFormState,
    existingAlarm: CustomAlarmUi?,
    existingAlarms: List<CustomAlarmUi>,
    onSave: (CustomAlarmUi) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .imePadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            AddEditAlarmFormContent(
                formState = formState,
                existingAlarm = existingAlarm,
                existingAlarms = existingAlarms,
                onSave = onSave,
                onDismiss = onDismiss
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\AlarmTitleEditor.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import com.mhq.salati.R

private const val MAX_LABEL_LENGTH = 30

@Composable
internal fun AlarmTitleEditor(
    label: String,
    onLabelChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultTitle = stringResource(R.string.custom_alarm_default_title)
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isEditing) {
        if (isEditing) focusRequester.requestFocus()
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            OutlinedTextField(
                value = label,
                onValueChange = { onLabelChange(it.take(MAX_LABEL_LENGTH)) },
                placeholder = { Text(defaultTitle) },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { isEditing = false }),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
            )
        } else {
            Text(
                text = label.trim().ifBlank { defaultTitle },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f, fill = false)
                    .pointerInput(Unit) {
                        detectTapGestures(onDoubleTap = { isEditing = true })
                    }
            )
        }
        IconButton(onClick = { isEditing = !isEditing }) {
            Icon(
                imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                contentDescription = stringResource(if (isEditing) R.string.save else R.string.edit),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\CustomAlarmCard.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.shared.ui.labelRes
import java.time.format.TextStyle as DayNameStyle

@Composable
fun CustomAlarmCard(
    customAlarm: CustomAlarmUi,
    onToggle: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDayToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val isEnabled = customAlarm.isEnabled
    val contentAlpha = if (isEnabled) 1f else 0.45f
    val configuration = LocalConfiguration.current

    val containerColor by animateColorAsState(
        targetValue = if (isEnabled) colors.surfaceVariant.copy(alpha = 0.4f) else colors.surface,
        label = "cardContainerColor"
    )
    val borderColor = colors.outline.copy(alpha = if (isEnabled) 0.15f else 0.08f)
    val dividerColor = colors.outline.copy(alpha = 0.12f)

    val prayerName = stringResource(customAlarm.prayerName.labelRes)
    val repeatsEveryDay = customAlarm.everyDay ||
            WeekDays.all { (calendarDay, _) -> calendarDay in customAlarm.activeDays }

    Card(
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f, fill = false)) {
                        if (repeatsEveryDay) {
                            RepeatTag(isEnabled = isEnabled)
                        } else {
                            Spacer(modifier = Modifier.size(1.dp))
                        }
                    }
                    Switch(
                        checked = isEnabled,
                        onCheckedChange = onToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.primary,
                            checkedTrackColor = colors.primary.copy(alpha = 0.5f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OffsetInlineHeadline(
                    customAlarm = customAlarm,
                    prayerName = prayerName,
                    contentAlpha = contentAlpha
                )

                Spacer(modifier = Modifier.height(14.dp))

                val locale = configuration.locales[0]
                val badges = remember(locale) {
                    WeekDays.map { (calendarDay, dayOfWeek) ->
                        DayBadge(
                            calendarDay = calendarDay,
                            initial = dayOfWeek.getDisplayName(DayNameStyle.NARROW, locale),
                            fullName = dayOfWeek.getDisplayName(DayNameStyle.FULL, locale)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    badges.forEach { badge ->
                        DayCircle(
                            badge = badge,
                            isActive = customAlarm.everyDay || badge.calendarDay in customAlarm.activeDays,
                            isAlarmEnabled = isEnabled,
                            onClick = { onDayToggle(badge.calendarDay) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            HorizontalDivider(color = dividerColor)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(vertical = 4.dp)
            ) {
                TextButton(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.edit))
                    }
                }

                VerticalDivider(
                    color = dividerColor,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                )

                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(contentColor = colors.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.delete))
                    }
                }
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\DayBadge.kt

```kotlin
package com.mhq.salati.alarms.ui.components

internal data class DayBadge(
    val calendarDay: Int,
    val initial: String,
    val fullName: String
)
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\DayCircle.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
internal fun DayCircle(
    badge: DayBadge,
    isActive: Boolean,
    isAlarmEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val backgroundColor = when {
        !isActive -> colors.onSurface.copy(alpha = 0.05f)
        isAlarmEnabled -> colors.primary.copy(alpha = 0.12f)
        else -> colors.onSurfaceVariant.copy(alpha = 0.1f)
    }
    val textColor = when {
        !isActive -> colors.onSurfaceVariant.copy(alpha = 0.35f)
        isAlarmEnabled -> colors.primary
        else -> colors.onSurfaceVariant.copy(alpha = 0.7f)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .toggleable(value = isActive, role = Role.Checkbox, onValueChange = { onClick() })
            .semantics { contentDescription = badge.fullName }
    ) {
        Text(
            text = badge.initial,
            fontSize = 13.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
            modifier = Modifier.clearAndSetSemantics { }
        )
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\OffsetDirectionSegment.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.components.ShrinkToFitText

@Composable
internal fun OffsetDirectionSegment(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
    ) {
        ShrinkToFitText(
            text = text,
            fontSize = 11.sp,
            minFontSize = 8.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\OffsetInlineHeadline.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import java.text.NumberFormat

@Composable
internal fun OffsetInlineHeadline(
    customAlarm: CustomAlarmUi,
    prayerName: String,
    contentAlpha: Float
) {
    val colors = MaterialTheme.colorScheme
    val alarmLabel = customAlarm.label.trim()
        .ifBlank { stringResource(R.string.custom_alarm_default_title) }
    val timingDetails = if (customAlarm.offsetMinutes == 0) {
        stringResource(R.string.at_prayer_time, prayerName)
    } else {
        val offsetText = remember(customAlarm.offsetMinutes) {
            NumberFormat.getInstance().format(customAlarm.offsetMinutes)
        }
        val unitText = stringResource(R.string.minutes_short)
        val directionText = stringResource(
            if (customAlarm.offsetDirection == OffsetDirectionUi.BEFORE) {
                R.string.alarm_offset_before
            } else {
                R.string.alarm_offset_after
            },
            prayerName
        )
        "$offsetText $unitText $directionText"
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$alarmLabel  ·  $timingDetails",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface.copy(alpha = contentAlpha),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\RepeatTag.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mhq.salati.R

@Composable
internal fun RepeatTag(isEnabled: Boolean) {
    val colors = MaterialTheme.colorScheme
    val tint = if (isEnabled) colors.primary else colors.onSurfaceVariant.copy(alpha = 0.6f)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(CircleShape)
            .background(tint.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Repeat,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = stringResource(R.string.repeat_every_day),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = tint,
            maxLines = 1
        )
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\components\WeekDays.kt

```kotlin
package com.mhq.salati.alarms.ui.components

import java.time.DayOfWeek
import java.util.Calendar

internal val WeekDays = listOf(
    Calendar.MONDAY to DayOfWeek.MONDAY,
    Calendar.TUESDAY to DayOfWeek.TUESDAY,
    Calendar.WEDNESDAY to DayOfWeek.WEDNESDAY,
    Calendar.THURSDAY to DayOfWeek.THURSDAY,
    Calendar.FRIDAY to DayOfWeek.FRIDAY,
    Calendar.SATURDAY to DayOfWeek.SATURDAY,
    Calendar.SUNDAY to DayOfWeek.SUNDAY
)
```

## app\src\main\java\com\mhq\salati\alarms\ui\notification\CustomAlarmNotifier.kt

```kotlin
package com.mhq.salati.alarms.ui.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.alarms.presentation.presenter.CustomAlarmNotificationPresenter
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.ui.rawRes
import com.mhq.salati.shared.ui.labelRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val CHANNEL_ID_PREFIX = "custom_alarms_channel"

class CustomAlarmNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
    private val presenter: CustomAlarmNotificationPresenter
) {
    @SuppressLint("StringFormatInvalid")
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend fun show(
        alarmId: Long,
        label: String,
        prayerKey: String,
        offsetMinutes: Int,
        offsetDirectionKey: String?
    ) {
        val sound = presenter.customAlarmSound()
        val channelId = channelIdFor(sound)
        ensureChannelExists(channelId, sound)

        val localizedPrayerName =
            presenter.prayerNameFor(prayerKey)?.let { context.getString(it.labelRes) }
                ?: prayerKey

        val title = label.ifBlank {
            context.getString(
                R.string.custom_alarm_default_title,
                localizedPrayerName
            )
        }

        val body = buildOffsetBody(
            localizedPrayerName,
            offsetMinutes,
            presenter.offsetDirectionFor(offsetDirectionKey)
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_salati_white)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(alarmId.toInt(), notification)
    }

    private fun buildOffsetBody(
        localizedPrayerName: String,
        offsetMinutes: Int,
        offsetDirection: OffsetDirectionUi?
    ): String = when {
        offsetMinutes == 0 || offsetDirection == null ->
            context.getString(R.string.at_prayer_time, localizedPrayerName)

        offsetDirection == OffsetDirectionUi.BEFORE ->
            context.resources.getQuantityString(
                R.plurals.minutes_before_prayer, offsetMinutes, offsetMinutes, localizedPrayerName
            )

        else ->
            context.resources.getQuantityString(
                R.plurals.minutes_after_prayer, offsetMinutes, offsetMinutes, localizedPrayerName
            )
    }

    private fun channelIdFor(sound: CustomAlarmSoundUi): String =
        "${CHANNEL_ID_PREFIX}_${sound.name.lowercase()}"

    private fun ensureChannelExists(channelId: String, sound: CustomAlarmSoundUi) {
        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        if (manager.getNotificationChannel(channelId) != null) return

        val soundUri = sound.rawRes?.let {
            "android.resource://${context.packageName}/$it".toUri()
        } ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        val channel = NotificationChannel(
            channelId,
            context.getString(R.string.custom_alarms_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(soundUri, attrs)
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\receiver\CustomAlarmIntentKeys.kt

```kotlin
package com.mhq.salati.alarms.ui.receiver

internal object CustomAlarmIntentKeys {
    const val EXTRA_ALARM_ID = "extra_alarm_id"
    const val EXTRA_LABEL = "extra_label"
    const val EXTRA_PRAYER_NAME = "extra_prayer_name"
    const val EXTRA_OFFSET_MINUTES = "extra_offset_minutes"
    const val EXTRA_OFFSET_DIRECTION = "extra_offset_direction"
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\receiver\CustomAlarmReceiver.kt

```kotlin
package com.mhq.salati.alarms.ui.receiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.mhq.salati.adhan.presentation.presenter.AlarmReschedulePresenter
import com.mhq.salati.alarms.ui.notification.CustomAlarmNotifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CustomAlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var customAlarmNotifier: CustomAlarmNotifier
    @Inject lateinit var alarmReschedulePresenter: AlarmReschedulePresenter

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getLongExtra(CustomAlarmIntentKeys.EXTRA_ALARM_ID, -1L)
        val label = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_LABEL).orEmpty()
        val prayerName = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_PRAYER_NAME).orEmpty()
        val offsetMinutes = intent.getIntExtra(CustomAlarmIntentKeys.EXTRA_OFFSET_MINUTES, 0)
        val offsetDirectionKey = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_OFFSET_DIRECTION)

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                customAlarmNotifier.show(alarmId, label, prayerName, offsetMinutes, offsetDirectionKey)
                // Arm this alarm's next occurrence ("every day" alarms no longer stop after one ring).
                alarmReschedulePresenter.onRescheduleNeeded()
            } finally {
                pendingResult.finish()
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\screens\CustomAlarmsContainer.kt

```kotlin
package com.mhq.salati.alarms.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.alarms.presentation.viewmodel.AlarmsViewModel
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.shared.ui.asString
import kotlinx.coroutines.launch

@Composable
fun CustomAlarmsContainer(
    alarmsViewModel: AlarmsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by alarmsViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        alarmsViewModel.effect.collect { effect ->
            when (effect) {
                is AlarmsContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
                is AlarmsContract.Effect.AlarmSaved -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
            }
        }
    }

    CustomAlarmsContent(
        state = state,
        onIntent = alarmsViewModel::onIntent
    )
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\screens\CustomAlarmsContent.kt

```kotlin
package com.mhq.salati.alarms.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.alarms.ui.components.AddEditAlarmFormContent
import com.mhq.salati.alarms.ui.components.AddEditAlarmSheet
import com.mhq.salati.alarms.ui.components.CustomAlarmCard
import com.mhq.salati.alarms.ui.state.AlarmFormState
import com.mhq.salati.shared.ui.ScreenHorizontalPadding
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults

private val FabColumnWidth = 88.dp // FAB 56 + 20 end margin + 12 gap

@Composable
fun CustomAlarmsContent(
    state: AlarmsContract.State,
    onIntent: (AlarmsContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    var headerHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val isLandscape = isWideLayout()
    val showSidePanel = isLandscape && state.isEditorVisible

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val formState = remember(state.isEditorVisible, state.editingAlarm) {
            AlarmFormState(state.editingAlarm)
        }

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.navigationBars
                            .union(WindowInsets.displayCutout)
                            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                    )
                    .padding(
                        start = ScreenHorizontalPadding,
                        end = FabColumnWidth,
                        top = with(density) { headerHeightPx.toDp() } + 12.dp,
                        bottom = BottomNavBarDefaults.Height
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else if (state.alarms.isEmpty()) {
                        CustomAlarmsContentBlank(modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.alarms, key = { it.id }) { alarm ->
                                CustomAlarmCard(
                                    customAlarm = alarm,
                                    onToggle = { enabled -> onIntent(AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled)) },
                                    onEdit = { onIntent(AlarmsContract.Intent.EditAlarm(alarm)) },
                                    onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) },
                                    onDayToggle = { day -> onIntent(AlarmsContract.Intent.AlarmDayToggled(alarm.id, day)) }
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    if (showSidePanel) {
                        AddEditAlarmFormContent(
                            formState = formState,
                            existingAlarm = state.editingAlarm,
                            existingAlarms = state.alarms,
                            onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) },
                            onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) }
                        )
                    }
                }
            }
        } else {
            // Portrait view footprint restored
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (state.alarms.isEmpty()) {
                CustomAlarmsContentBlank(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = ScreenHorizontalPadding,
                        end = ScreenHorizontalPadding,
                        top = with(density) { headerHeightPx.toDp() } + 16.dp,
                        bottom = BottomNavBarDefaults.Height + 80.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.alarms, key = { it.id }) { alarm ->
                        CustomAlarmCard(
                            customAlarm = alarm,
                            onToggle = { enabled -> onIntent(AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled)) },
                            onEdit = { onIntent(AlarmsContract.Intent.EditAlarm(alarm)) },
                            onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) },
                            onDayToggle = { day -> onIntent(AlarmsContract.Intent.AlarmDayToggled(alarm.id, day)) }
                        )
                    }
                }
            }
        }

        // TabHeader strictly placed at Top across layout contexts
        TabHeader(
            icon = Icons.Default.Alarm,
            title = stringResource(R.string.custom_alarms),
            subtitle = stringResource(R.string.create_custom_alarms_before_or_after_prayers),
            modifier = Modifier.onGloballyPositioned {
                headerHeightPx = it.size.height
            }
        )

        // Floating Action Button perfectly pinned to the absolute bottom-end area across screens
        FloatingActionButton(
            onClick = { onIntent(AlarmsContract.Intent.AddAlarm) },
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .windowInsetsPadding(
                    WindowInsets.navigationBars
                        .union(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                )
                .padding(
                    bottom = BottomNavBarDefaults.Height + 20.dp,
                    end = 20.dp
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_custom_alarm)
            )
        }

        if (!isLandscape && state.isEditorVisible) {
            AddEditAlarmSheet(
                formState = formState,
                existingAlarm = state.editingAlarm,
                existingAlarms = state.alarms,
                onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) },
                onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) }
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\screens\CustomAlarmsContentBlank.kt

```kotlin
package com.mhq.salati.alarms.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R

@Composable
fun CustomAlarmsContentBlank(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(32.dp)
    ) {
        Text(
            text = stringResource(R.string.no_custom_alarms_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.no_custom_alarms_body),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
        )
    }
}
```

## app\src\main\java\com\mhq\salati\alarms\ui\state\AlarmFormState.kt

```kotlin
package com.mhq.salati.alarms.ui.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.alarms.ui.components.WeekDays
import com.mhq.salati.shared.presentation.model.PrayerNameUi

@Stable
class AlarmFormState(existing: CustomAlarmUi?) {
    val prayerName = mutableStateOf(existing?.prayerName ?: PrayerNameUi.entries.first())
    val label = mutableStateOf(existing?.label ?: "")
    val minutesText = mutableStateOf((existing?.offsetMinutes ?: 5).toString())
    val offsetDirection = mutableStateOf(existing?.offsetDirection ?: OffsetDirectionUi.BEFORE)
    val activeDays = mutableStateOf(
        if (existing == null || existing.everyDay) WeekDays.map { it.first }.toSet()
        else existing.activeDays
    )
}
```

## app\src\main\java\com\mhq\salati\connectivity\data\repoimpl\ConnectivityCheckerImpl.kt

```kotlin
package com.mhq.salati.connectivity.data.repoimpl

import com.mhq.salati.connectivity.datasource.device.ConnectivityDataSource
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import javax.inject.Inject

class ConnectivityCheckerImpl @Inject constructor(
    private val connectivityDataSource: ConnectivityDataSource
) : ConnectivityChecker {

    override fun isConnected(): Boolean = connectivityDataSource.isConnected()
}
```

## app\src\main\java\com\mhq\salati\connectivity\datasource\device\AndroidConnectivityDataSource.kt

```kotlin
package com.mhq.salati.connectivity.datasource.device

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidConnectivityDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : ConnectivityDataSource {

    override fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
```

## app\src\main\java\com\mhq\salati\connectivity\datasource\device\ConnectivityDataSource.kt

```kotlin
package com.mhq.salati.connectivity.datasource.device

interface ConnectivityDataSource {
    fun isConnected(): Boolean
}
```

## app\src\main\java\com\mhq\salati\connectivity\di\ConnectivityModule.kt

```kotlin
package com.mhq.salati.connectivity.di

import com.mhq.salati.connectivity.data.repoimpl.ConnectivityCheckerImpl
import com.mhq.salati.connectivity.datasource.device.AndroidConnectivityDataSource
import com.mhq.salati.connectivity.datasource.device.ConnectivityDataSource
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityChecker(impl: ConnectivityCheckerImpl): ConnectivityChecker

    @Binds
    @Singleton
    abstract fun bindConnectivityDataSource(impl: AndroidConnectivityDataSource): ConnectivityDataSource
}
```

## app\src\main\java\com\mhq\salati\connectivity\domain\repo\ConnectivityChecker.kt

```kotlin
package com.mhq.salati.connectivity.domain.repo

interface ConnectivityChecker {
    fun isConnected(): Boolean
}
```

## app\src\main\java\com\mhq\salati\connectivity\ui\components\rememberNetworkConnected.kt

```kotlin
package com.mhq.salati.connectivity.ui.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberNetworkConnected(): State<Boolean> {
    val context = LocalContext.current

    val connectivityManager = remember(context) {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isCurrentlyConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    return produceState(initialValue = isCurrentlyConnected(), connectivityManager) {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                value = isCurrentlyConnected()
            }

            override fun onLost(network: Network) {
                value = false
            }

            override fun onCapabilitiesChanged(
                network: Network,
                capabilities: NetworkCapabilities
            ) {
                value = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        awaitDispose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\home\domain\model\PrayerCountdown.kt

```kotlin
package com.mhq.salati.home.domain.model

import java.time.Duration

/**
 * A snapshot of the countdown to the next prayer at one moment.
 *
 * @property remaining time left until the window ends; zero once it has ended
 * @property progress how far through the window we are, from 0f at its start to 1f at its end
 */
data class PrayerCountdown(
    val remaining: Duration,
    val progress: Float
)
```

## app\src\main\java\com\mhq\salati\home\domain\model\PrayerProgress.kt

```kotlin
package com.mhq.salati.home.domain.model

import com.mhq.salati.shared.domain.PrayerName

/**
 * Where the day stands right now: the countdown window,
 * the prayer in progress and the ones already passed.
 *
 * @property window the countdown window the screen draws its ring and timer from
 * @property currentPrayer the prayer whose window is running now,
 * or null when the browsed day is not today (past and future days have no "current" prayer)
 * @property pastPrayers the day's timings that have already passed
 */
data class PrayerProgress(
    val window: PrayerWindow,
    val currentPrayer: PrayerName?,
    val pastPrayers: Set<PrayerName>
)
```

## app\src\main\java\com\mhq\salati\home\domain\model\PrayerTimesError.kt

```kotlin
package com.mhq.salati.home.domain.model

/**
 * Why loading prayer times failed, in the granularity the UI needs.
 *
 * [Offline] and [TimedOut] get their own user-facing messages;
 * [Other] carries any unexpected failure verbatim.
 */
sealed interface PrayerTimesError {
    /** The device had no usable network connection. */
    data object Offline : PrayerTimesError

    /** The network request exceeded the fetch timeout. */
    data object TimedOut : PrayerTimesError

    /** Any other failure; [message] is the underlying exception's message, when there is one. */
    data class Other(val message: String?) : PrayerTimesError
}
```

## app\src\main\java\com\mhq\salati\home\domain\model\PrayerTimesLoad.kt

```kotlin
package com.mhq.salati.home.domain.model

import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings

/**
 * The steps of loading a day's prayer times, in the order they happen.
 *
 * A load emits [FetchingFromNetwork] only on a cache miss,
 * then either [Loaded] or [Failed] exactly once; the flow completes after that.
 */
sealed interface PrayerTimesLoad {
    /** Nothing was cached, so a network request is now in flight. */
    data object FetchingFromNetwork : PrayerTimesLoad

    /** Times are on screen: the timings themselves, the date they belong to, and the day's status. */
    data class Loaded(
        val timings: PrayerTimings,
        val prayerDate: PrayerDate,
        val status: PrayerProgress
    ) : PrayerTimesLoad

    /** The load failed; see [PrayerTimesError] for why. */
    data class Failed(val error: PrayerTimesError) : PrayerTimesLoad
}
```

## app\src\main\java\com\mhq\salati\home\domain\model\PrayerTimesTick.kt

```kotlin
package com.mhq.salati.home.domain.model

import java.time.LocalDate

/**
 * What happens, in order, while a day's prayer times stay on screen.
 *
 * [Countdown] fires about once a second;
 * [StatusRefreshed] and [DayRolledOver] fire when a countdown window ends.
 */
sealed interface PrayerTimesTick {
    /** One countdown snapshot for the running window. */
    data class Countdown(val countdown: PrayerCountdown) : PrayerTimesTick

    /** A countdown window ended and the next one has started. */
    data class StatusRefreshed(val status: PrayerProgress) : PrayerTimesTick

    /** The window ran into the next day, so the screen should move on to [date]. */
    data class DayRolledOver(val date: LocalDate) : PrayerTimesTick
}
```

## app\src\main\java\com\mhq\salati\home\domain\model\PrayerWindow.kt

```kotlin
package com.mhq.salati.home.domain.model

import com.mhq.salati.shared.domain.PrayerName
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * The stretch of time leading up to [nextPrayer]:
 * it starts at the previous prayer time and ends when [nextPrayer] begins.
 * Home's countdown ring and timer are drawn from it.
 *
 * Validated on construction: [end] may equal [start] (an instant window) but never precede it.
 *
 * @property nextPrayer the prayer whose time [end] points at
 * @property start the instant the window opens, i.e. the previous prayer's time
 * @property end the instant [nextPrayer] begins
 * @throws IllegalArgumentException if [end] is before [start]
 */
data class PrayerWindow(
    val nextPrayer: PrayerName,
    val start: Instant,
    val end: Instant
) {
    init {
        require(!end.isBefore(start)) {
            "A prayer window cannot end before it starts: $start > $end"
        }
    }

    /**
     * Time left until [end] as seen from [now];
     * clamped to zero once the window has ended, so callers never see a negative countdown.
     */
    fun remainingAt(now: Instant): Duration =
        Duration.between(now, end).coerceAtLeast(Duration.ZERO)

    /**
     * How far through the window [now] sits,
     * from 0f at (or before) the start to 1f at (or after) the end.
     * A zero-length window is always 0f.
     */
    fun progressAt(now: Instant): Float {
        val totalMillis = Duration.between(start, end).toMillis()
        if (totalMillis <= 0L) return 0f
        val elapsedMillis = Duration.between(start, now).toMillis()
        return (elapsedMillis.toFloat() / totalMillis.toFloat()).coerceIn(0f, 1f)
    }

    /**
     * True when this window ends on a calendar day later than [day]
     * (e.g. after Isha, running into tomorrow's Fajr);
     * the signal for a day rollover when the browsed day is [day].
     */
    fun endsAfterDay(day: LocalDate, zoneId: ZoneId): Boolean =
        end.atZone(zoneId).toLocalDate().isAfter(day)
}
```

## app\src\main\java\com\mhq\salati\home\domain\service\DayNavigator.kt

```kotlin
package com.mhq.salati.home.domain.service

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.shared.domain.Clock
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

/**
 * The rules for which day the Home screen may show.
 * Centralizes day arithmetic (today check, previous/next, month end, rollover)
 * so every screen and use case browses days by the same rules.
 */
class DayNavigator @Inject constructor(
    private val clock: Clock
) {
    /** True when [date] is today in the device's time zone. */
    fun isToday(date: LocalDate): Boolean =
        date == clock.today()

    /** The day browsing can always return to. */
    fun today(): LocalDate =
        clock.today()

    /**
     * The day before [date], or null when [date] is today:
     * browsing into the past isn't allowed.
     */
    fun previousOrNull(date: LocalDate): LocalDate? =
        if (isToday(date))
            null
        else
            date.minusDays(1)

    /** The day after [date]; browsing forward is always allowed. */
    fun next(date: LocalDate): LocalDate =
        date.plusDays(1)

    /**
     * The following day when [window] runs past [date] (after Isha, into tomorrow's Fajr);
     * otherwise null.
     */
    fun rolloverDate(date: LocalDate, window: PrayerWindow?): LocalDate? =
        if (window?.endsAfterDay(date, clock.zone()) == true)
            date.plusDays(1)
        else
            null

    /**
     * The last day of [date]'s month;
     * when [date] already is that day, the last day of the next month.
     * Used to bound how far ahead one jump can go.
     */
    fun endOfMonth(date: LocalDate): LocalDate {
        val lastDay = date.with(TemporalAdjusters.lastDayOfMonth())
        return if (date == lastDay) {
            date.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth())
        } else {
            lastDay
        }
    }
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\CalculateCurrentPrayerNameUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.MINUTES_PER_DAY
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toMinuteOfDay
import javax.inject.Inject

/**
 * The major prayer whose window is running right now,
 * i.e. the latest of the five daily prayers whose time has already passed.
 *
 * Times before today's Fajr are normalized into the previous day's cycle,
 * so a 03:00 "now" is treated as late in yesterday's timeline rather than before the day began.
 */
class CalculateCurrentPrayerNameUseCase @Inject constructor(
    private val clock: Clock
) {
    /**
     * @param timings the day's prayer timings
     * @return the prayer in progress, or null when no prayer time has passed yet
     */
    operator fun invoke(timings: PrayerTimings): PrayerName? {
        // Anything earlier than Fajr belongs to the tail of the previous day's cycle.
        val fajrMinutes = timings.fajr.toMinuteOfDay()
        fun normalize(minutes: Int) = if (minutes < fajrMinutes) minutes + MINUTES_PER_DAY else minutes
        val nowMinutes = normalize(clock.now().toLocalTime().toMinuteOfDay())

        return PrayerName.majorEntries
            .map { name -> name to normalize(timings[name].toMinuteOfDay()) }
            .filter { (_, minutes) -> minutes <= nowMinutes }
            .maxByOrNull { (_, minutes) -> minutes }
            ?.first
    }
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\CalculatePastPrayersUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.MINUTES_PER_DAY
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toMinuteOfDay
import javax.inject.Inject

/**
 * Every timing of the day (major and minor) whose time has already passed.
 * Unlike [CalculateCurrentPrayerNameUseCase], this includes the minor timings,
 * so Imsak and Shorouq that have gone by also count as past.
 */
class CalculatePastPrayersUseCase @Inject constructor(
    private val clock: Clock
) {
    /**
     * @param timings the day's prayer timings
     * @return the timings whose time is at or before now
     */
    operator fun invoke(timings: PrayerTimings): Set<PrayerName> {
        /** Imsak is the first timing of the day; anything earlier is treated as after midnight. */
        val imsakMinutes = timings.imsak.toMinuteOfDay()
        fun normalize(minutes: Int) =
            if (minutes < imsakMinutes)
                minutes + MINUTES_PER_DAY
            else
                minutes

        val nowMinutes = normalize(clock.now().toLocalTime().toMinuteOfDay())

        return PrayerName.entries
            .filter { name -> normalize(timings[name].toMinuteOfDay()) <= nowMinutes }
            .toSet()
    }
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\CalculatePrayerStatusUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerProgress
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import java.time.LocalDate
import javax.inject.Inject

/**
 * Builds the day's [PrayerProgress] from its timings and countdown window.
 * The current and past prayers only mean something for today, so other days get none.
 */
class CalculatePrayerStatusUseCase @Inject constructor(
    private val clock: Clock,
    private val calculateCurrentPrayerNameUseCase: CalculateCurrentPrayerNameUseCase,
    private val calculatePastPrayersUseCase: CalculatePastPrayersUseCase
) {
    /**
     * @param timings the browsed day's prayer timings
     * @param date the browsed day
     * @param window the countdown window already resolved for that day
     * @return the progress for [date]; current/past prayers are empty unless [date] is today
     */
    operator fun invoke(timings: PrayerTimings, date: LocalDate, window: PrayerWindow): PrayerProgress {
        val isToday = date == clock.today()
        return PrayerProgress(
            window = window,
            currentPrayer = if (isToday) calculateCurrentPrayerNameUseCase(timings) else null,
            pastPrayers = if (isToday) calculatePastPrayersUseCase(timings) else emptySet()
        )
    }
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\CalculatePrayerWindowUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import com.mhq.salati.shared.domain.toLocalDateFromKey
import kotlinx.coroutines.withTimeoutOrNull
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/**
 * The [PrayerWindow] a day's timings put us in right now.
 *
 * Three cases:
 * - midday: the window between the last passed prayer and the next one;
 * - after Isha: a window from Isha to tomorrow's Fajr;
 * - before Fajr: a window from yesterday's Isha to today's Fajr.
 * Yesterday's Isha is taken from cache first, then a short network fetch,
 * then today's Isha as a fallback, so the window is never blocked on the network.
 */
class CalculatePrayerWindowUseCase @Inject constructor(
    private val clock: Clock,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase
) {
    /**
     * @param timings the day's prayer timings
     * @param date the day as its "dd-MM-yyyy" storage key
     * @param coordinates the location the timings were calculated for
     * @return the window that contains the current moment
     */
    suspend operator fun invoke(
        timings: PrayerTimings,
        date: String,
        coordinates: Coordinates
    ): PrayerWindow {
        val zoneId = clock.zone()
        val day = date.toLocalDateFromKey()

        val now = clock.instant()
        val prayerInstants = PrayerName.majorEntries.map { name ->
            name to day.instantAt(timings[name], zoneId)
        }

        val nextIndex = prayerInstants.indexOfFirst { (_, instant) -> instant.isAfter(now) }

        return when (nextIndex) {
            -1 -> {
                PrayerWindow(
                    nextPrayer = PrayerName.FAJR,
                    start = prayerInstants.last().second,
                    end = day.plusDays(1).instantAt(timings.fajr, zoneId)
                )
            }
            0 -> {
                val yesterdayIsha = resolveYesterdayIsha(
                    yesterday = day.minusDays(1),
                    coordinates = coordinates,
                    zoneId = zoneId,
                    fallback = timings.isha
                )

                PrayerWindow(
                    nextPrayer = PrayerName.FAJR,
                    start = yesterdayIsha,
                    end = prayerInstants[0].second
                )
            }
            else -> {
                val (nextName, nextInstant) = prayerInstants[nextIndex]
                val previousInstant = prayerInstants[nextIndex - 1].second
                PrayerWindow(nextName, previousInstant, nextInstant)
            }
        }
    }

    /**
     * Yesterday's Isha instant, resolved from the cheapest source that works:
     * cache, then a 5-second-bounded network fetch,
     * then today's Isha as a fallback (close enough for the window's start).
     */
    private suspend fun resolveYesterdayIsha(
        yesterday: LocalDate,
        coordinates: Coordinates,
        zoneId: ZoneId,
        fallback: LocalTime
    ): Instant {
        val yesterdayKey = yesterday.toDateKey()

        val cached = getCachedPrayerTimesUseCase(yesterdayKey, coordinates)
        if (cached != null) {
            return yesterday.instantAt(cached.timings.isha, zoneId)
        }

        val fetched = withTimeoutOrNull(5_000L.milliseconds) {
            getPrayerTimesUseCase(yesterdayKey, coordinates).getOrNull()
        }
        if (fetched != null) {
            return yesterday.instantAt(fetched.timings.isha, zoneId)
        }

        return yesterday.instantAt(fallback, zoneId)
    }

    /** The instant [time] occurs on this day in [zoneId]. */
    private fun LocalDate.instantAt(time: LocalTime, zoneId: ZoneId): Instant =
        atTime(time).atZone(zoneId).toInstant()
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\LoadPrayerTimesUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.home.domain.model.PrayerTimesError
import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/** Hard cap on the network fetch; past it the load reports [PrayerTimesError.TimedOut]. */
private val FETCH_TIMEOUT = 5_000L.milliseconds

/**
 * Loads a day's prayer times as a one-shot [PrayerTimesLoad] flow.
 *
 * Cache first. It will only emit [PrayerTimesLoad.FetchingFromNetwork] on a cache miss and go online.
 * Offline failures reported as [PrayerTimesError.Offline] regardless of the underlying exception,
 * so the UI can show the right message.
 */
class LoadPrayerTimesUseCase @Inject constructor(
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val resolvePrayerWindowForDisplayUseCase: ResolvePrayerWindowForDisplayUseCase,
    private val calculatePrayerStatusUseCase: CalculatePrayerStatusUseCase,
    private val connectivityChecker: ConnectivityChecker
) {
    /**
     * @param date the day to load
     * @param coordinates the location to load times for
     * @return a flow that emits the load's steps once each, then completes
     */
    operator fun invoke(date: LocalDate, coordinates: Coordinates): Flow<PrayerTimesLoad> = flow {
        val dateKey = date.toDateKey()

        val cached = getCachedPrayerTimesUseCase(dateKey, coordinates)
        if (cached != null) {
            emit(loaded(cached, date, coordinates))
            return@flow
        }

        emit(PrayerTimesLoad.FetchingFromNetwork)

        val result = withTimeoutOrNull(FETCH_TIMEOUT) {
            getPrayerTimesUseCase(dateKey, coordinates)
        }
        if (result == null) {
            emit(PrayerTimesLoad.Failed(failureWhileOnlineOr(PrayerTimesError.TimedOut)))
            return@flow
        }

        val fetched = result.getOrNull()
        if (fetched != null) {
            emit(loaded(fetched, date, coordinates))
        } else {
            val error = PrayerTimesError.Other(result.exceptionOrNull()?.message)
            emit(PrayerTimesLoad.Failed(failureWhileOnlineOr(error)))
        }
    }

    /** Assembles a [PrayerTimesLoad.Loaded]: window and status resolved on top of the timings. */
    private suspend fun loaded(
        result: PrayerTimesResult,
        date: LocalDate,
        coordinates: Coordinates
    ): PrayerTimesLoad.Loaded {
        val window = resolvePrayerWindowForDisplayUseCase(result.timings, date, coordinates)
        return PrayerTimesLoad.Loaded(
            timings = result.timings,
            prayerDate = result.date,
            status = calculatePrayerStatusUseCase(result.timings, date, window)
        )
    }

    /**
     * Keeps [error] when the device is online,
     * but reports [PrayerTimesError.Offline] when the real cause is a missing connection.
     */
    private fun failureWhileOnlineOr(error: PrayerTimesError): PrayerTimesError =
        if (connectivityChecker.isConnected()) error else PrayerTimesError.Offline
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\ObservePrayerCountdownUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerCountdown
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.shared.domain.Clock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/**
 * One [PrayerCountdown] per second while [window] runs.
 *
 * Emits once a second until [window] ends; the flow completes right after the zero emission.
 */
class ObservePrayerCountdownUseCase @Inject constructor(
    private val clock: Clock
) {
    /**
     * @param window the countdown window to observe
     * @return a flow of countdown snapshots, ending with the zero-remaining one
     */
    operator fun invoke(window: PrayerWindow): Flow<PrayerCountdown> = flow {
        while (true) {
            val now = clock.instant()
            val remaining = window.remainingAt(now)
            emit(PrayerCountdown(remaining = remaining, progress = window.progressAt(now)))
            if (remaining.isZero) return@flow
            delay(1_000.milliseconds)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\ObservePrayerTimesTicksUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerTimesTick
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.home.domain.service.DayNavigator
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

/**
 * Counts down [window]; when it ends, either moves on to the next day or starts the next window.
 * Keeps going until the day rolls over, so collect it for as long as these times are on screen.
 *
 * The chain is: countdown ticks until the window ends,
 * then either a day rollover (window crosses midnight past the browsed day)
 * or a status refresh that hands over the next window.
 * A refreshed window that doesn't end later than the one that just ended
 * stops the flow rather than looping forever.
 */
class ObservePrayerTimesTicksUseCase @Inject constructor(
    private val observePrayerCountdownUseCase: ObservePrayerCountdownUseCase,
    private val refreshPrayerStatusUseCase: RefreshPrayerStatusUseCase,
    private val dayNavigator: DayNavigator
) {
    /**
     * @param timings the browsed day's prayer timings
     * @param date the browsed day
     * @param coordinates the location the timings were calculated for
     * @param window the window resolved when these times were loaded
     * @return a flow of ticks that completes on a day rollover or a stalled window
     */
    operator fun invoke(
        timings: PrayerTimings,
        date: LocalDate,
        coordinates: Coordinates,
        window: PrayerWindow
    ): Flow<PrayerTimesTick> = flow {
        var currentWindow = window
        while (true) {
            observePrayerCountdownUseCase(currentWindow).collect { emit(PrayerTimesTick.Countdown(it)) }

            val nextDate = dayNavigator.rolloverDate(date, currentWindow)
            if (nextDate != null) {
                emit(PrayerTimesTick.DayRolledOver(nextDate))
                return@flow
            }

            val status = refreshPrayerStatusUseCase(timings, date, coordinates)
            /** A window that doesn't end later than the one that just ended would loop forever. */
            if (!status.window.end.isAfter(currentWindow.end)) return@flow
            emit(PrayerTimesTick.StatusRefreshed(status))
            currentWindow = status.window
        }
    }
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\RefreshPrayerStatusUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerProgress
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Coordinates
import java.time.LocalDate
import javax.inject.Inject

/**
 * Recomputes the status for already-loaded [timings],
 * e.g. when the previous countdown window ends.
 *
 * Re-resolves the display window (which may have moved to the next prayer)
 * and rebuilds the progress from it, without touching cache or network for the browsed day's timings.
 */
class RefreshPrayerStatusUseCase @Inject constructor(
    private val resolvePrayerWindowForDisplayUseCase: ResolvePrayerWindowForDisplayUseCase,
    private val calculatePrayerStatusUseCase: CalculatePrayerStatusUseCase
) {
    /**
     * @param timings the already-loaded timings for the browsed day
     * @param browsedDate the day currently on screen
     * @param coordinates the location the timings were calculated for
     * @return fresh progress: new window, current prayer and past prayers
     */
    suspend operator fun invoke(
        timings: PrayerTimings,
        browsedDate: LocalDate,
        coordinates: Coordinates
    ): PrayerProgress {
        val window = resolvePrayerWindowForDisplayUseCase(
            browsedTimings = timings,
            browsedDate = browsedDate,
            coordinates = coordinates
        )
        return calculatePrayerStatusUseCase(timings, browsedDate, window)
    }
}
```

## app\src\main\java\com\mhq\salati\home\domain\usecases\ResolvePrayerWindowForDisplayUseCase.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.toDateKey
import java.time.LocalDate
import javax.inject.Inject

/**
 * The window the Home screen should count down, no matter which day is browsed.
 *
 * The countdown always runs to today's real next prayer, whichever day is being browsed.
 * Today's own timings are used when available; the browsed ones are only a last resort.
 * Resolution order for today's timings: the browsed timings when the browsed day is today,
 * then today's cache, then a network fetch, then the browsed timings as a fallback.
 */
class ResolvePrayerWindowForDisplayUseCase @Inject constructor(
    private val clock: Clock,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val calculatePrayerWindowUseCase: CalculatePrayerWindowUseCase
) {
    /**
     * @param browsedTimings the timings already loaded for the browsed day
     * @param browsedDate the day currently on screen
     * @param coordinates the location the timings were calculated for
     * @return the window to today's next prayer, built from today's timings
     */
    suspend operator fun invoke(
        browsedTimings: PrayerTimings,
        browsedDate: LocalDate,
        coordinates: Coordinates
    ): PrayerWindow {
        val today = clock.today()
        val todayKey = today.toDateKey()

        val todayTimings = if (browsedDate == today) {
            browsedTimings
        } else {
            getCachedPrayerTimesUseCase(todayKey, coordinates)?.timings
                ?: getPrayerTimesUseCase(todayKey, coordinates).getOrNull()?.timings
                ?: browsedTimings
        }

        return calculatePrayerWindowUseCase(todayTimings, todayKey, coordinates)
    }
}
```

## app\src\main\java\com\mhq\salati\home\presentation\contract\HomeContract.kt

```kotlin
package com.mhq.salati.home.presentation.contract

import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.home.presentation.model.PrayerTimingsUi
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import java.time.Duration
import java.time.LocalDate

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val locationUiState: LocationUiState = LocationUiState(),
        val prayerTimesUiState: PrayerTimesUiState = PrayerTimesUiState(),
        val timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_12_HOUR,
        val dateBrowserUiState: DateBrowserUiState = DateBrowserUiState(),
        val adhanUiState: AdhanUiState = AdhanUiState(),
        val errorMessage: MessageUi? = null,
        val prerequisites: PrerequisitesUi? = null
    )

    data class PrayerTimesUiState(
        val date: PrayerDateUi? = null,
        val timings: PrayerTimingsUi? = null,
        val currentPrayerName: PrayerNameUi? = null,
        val nextPrayerName: PrayerNameUi? = null,
        val pastPrayers: Set<PrayerNameUi> = emptySet(),
        val remaining: Duration = Duration.ZERO,
        val windowProgress: Float = 0f
    )

    data class LocationUiState(
        val locationName: String? = null
    )

    data class AdhanUiState(
        val mutedPrayers: Set<PrayerNameUi> = emptySet()
    )

    data class DateBrowserUiState(
        val currentDate: LocalDate = LocalDate.now(),
        val isBrowsingToday: Boolean = true
    )

    sealed interface Intent {
        data object LoadPrayerTimes : Intent
        data object RetryRequested : Intent
        data object PreviousDay : Intent
        data object NextDay : Intent
        data object TodayRequested : Intent
        data object EndOfMonthRequested : Intent
        data object LocationPermissionRequested : Intent
        data object LocationPermissionGranted : Intent
        data object AppSettingsRequested : Intent
        data object LocationSettingsRequested : Intent
        data object InternetSettingsRequested : Intent
        data object LocationPickerRequested : Intent
        data class ScreenResumed(val showLocationRationale: Boolean) : Intent
        data class ToggleMute(val prayerName: PrayerNameUi) : Intent
        data class LocationPermissionDenied(val showRationale: Boolean) : Intent
        data class LocationServicesToggled(val enabled: Boolean) : Intent
        data class ConnectivityChanged(val isConnected: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
        data object RequestLocationPermission : Effect
        data object NavigateToAppSettings : Effect
        data object NavigateToLocationSettings : Effect
        data object NavigateToInternetSettings : Effect
        data object NavigateToLocationPicker : Effect
    }
}
```

## app\src\main\java\com\mhq\salati\home\presentation\handlers\HomeAlarmsHandler.kt

```kotlin
package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.adhan.domain.model.AlarmPermissions
import com.mhq.salati.adhan.domain.service.PrayerAlarmCoordinator
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import java.time.LocalDate
import javax.inject.Inject

/** Prayer alarms for the Home screen: when to schedule them again. */
class HomeAlarmsHandler @Inject constructor(
    private val prayerAlarmCoordinator: PrayerAlarmCoordinator
) {
    /** Checks the permissions and, when exact alarms are allowed, schedules today's alarms again. */
    suspend fun recheck(timings: PrayerTimings?, browsedDate: LocalDate): AlarmPermissions =
        prayerAlarmCoordinator.recheck(timings, browsedDate)

    suspend fun schedule(timings: PrayerTimings?, browsedDate: LocalDate) {
        prayerAlarmCoordinator.scheduleIfToday(timings, browsedDate)
    }
}
```

## app\src\main\java\com\mhq\salati\home\presentation\handlers\HomeLocationHandler.kt

```kotlin
package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.location.domain.model.LocationResolution
import com.mhq.salati.location.domain.service.LocationLocalizer
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.ResolveLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * "Where is the user" for the Home screen: the saved place, the GPS lookup and the localized
 * place name. It only reports what happened; the ViewModel decides what the screen shows.
 */
class HomeLocationHandler @Inject constructor(
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val resolveLocationUseCase: ResolveLocationUseCase,
    private val locationLocalizer: LocationLocalizer
) {
    /** The place's name in the new language, each time the app language changes. */
    val localizedNameChanges: Flow<String?> =
        locationLocalizer.observeLanguageChanges().map { it.displayName }

    /** The saved place if there is one, otherwise a fresh GPS lookup. */
    fun load(): Flow<HomeLocationLoad> = flow {
        val saved = getSavedLocationUseCase().first()
        if (saved != null) {
            emit(HomeLocationLoad.Saved(saved))
            return@flow
        }

        emit(HomeLocationLoad.LookingUpCurrentLocation)

        when (val resolution = resolveLocationUseCase()) {
            is LocationResolution.Resolved -> emit(HomeLocationLoad.Found(resolution.location))
            is LocationResolution.Blocked -> emit(HomeLocationLoad.Blocked(resolution.prerequisites))
            is LocationResolution.GeocodingFailed -> emit(HomeLocationLoad.GeocodingFailed)
            is LocationResolution.LocationUnavailable -> emit(HomeLocationLoad.Unavailable)
        }
    }

    /** The place's name in the app language, stored when it changed. */
    suspend fun localizedName(located: HomeLocationLoad.Located): String? =
        locationLocalizer.localizeAndSave(located.location).displayName
}
```

## app\src\main\java\com\mhq\salati\home\presentation\handlers\HomeLocationLoad.kt

```kotlin
package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.model.SavedLocation

/** The steps of working out where the user is, in the order they happen. */
sealed interface HomeLocationLoad {

    /** A place the prayer times can be loaded for. */
    sealed interface Located : HomeLocationLoad {
        val location: SavedLocation
    }

    /** The place stored from an earlier run or picked by hand. */
    data class Saved(override val location: SavedLocation) : Located

    /** The place just found through GPS. */
    data class Found(override val location: SavedLocation) : Located

    /** There is no saved place, so a GPS lookup has started. */
    data object LookingUpCurrentLocation : HomeLocationLoad

    /** Something the lookup needs is missing; [prerequisites] says what. */
    data class Blocked(val prerequisites: LocationPrerequisites) : HomeLocationLoad

    data object GeocodingFailed : HomeLocationLoad
    data object Unavailable : HomeLocationLoad
}
```

## app\src\main\java\com\mhq\salati\home\presentation\handlers\MutedPrayersHandler.kt

```kotlin
package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.adhan.domain.usecases.ObserveMutedPrayersUseCase
import com.mhq.salati.adhan.domain.usecases.ToggleMutePrayerUseCase
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

/** Which prayers have their adhan muted on a given day. */
class MutedPrayersHandler @Inject constructor(
    private val observeMutedPrayersUseCase: ObserveMutedPrayersUseCase,
    private val toggleMutePrayerUseCase: ToggleMutePrayerUseCase
) {
    fun observe(date: LocalDate): Flow<Set<PrayerName>> = observeMutedPrayersUseCase(date)

    suspend fun toggle(date: LocalDate, prayerName: PrayerName) {
        toggleMutePrayerUseCase(date, prayerName)
    }
}
```

## app\src\main\java\com\mhq\salati\home\presentation\handlers\PrayerTimesHandler.kt

```kotlin
package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.home.domain.model.PrayerTimesTick
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.home.domain.usecases.LoadPrayerTimesUseCase
import com.mhq.salati.home.domain.usecases.ObservePrayerTimesTicksUseCase
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

/** Loading a day's prayer times and keeping their countdown going. */

class PrayerTimesHandler @Inject constructor(
    private val loadPrayerTimesUseCase: LoadPrayerTimesUseCase,
    private val observePrayerTimesTicksUseCase: ObservePrayerTimesTicksUseCase
) {
    fun load(date: LocalDate, coordinates: Coordinates): Flow<PrayerTimesLoad> =
        loadPrayerTimesUseCase(date, coordinates)

    /**
     * Counts down [window]; when it ends, either moves on to the next day or starts the next window.
     * Keeps going until the day rolls over, so collect it for as long as these times are on screen.
     */
    fun keepCountingDown(
        timings: PrayerTimings,
        date: LocalDate,
        coordinates: Coordinates,
        window: PrayerWindow
    ): Flow<PrayerTimesTick> = observePrayerTimesTicksUseCase(timings, date, coordinates, window)
}
```

## app\src\main\java\com\mhq\salati\home\presentation\mapper\HomeUiMapper.kt

```kotlin
package com.mhq.salati.home.presentation.mapper

import com.mhq.salati.home.presentation.model.HijriDateUi
import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.home.presentation.model.PrayerTimingsUi
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.mapper.toUi

fun PrayerDate.toUi(): PrayerDateUi = PrayerDateUi(
    gregorianDate = gregorianDate,
    hijriDate = HijriDateUi(
        day = hijriDate.day,
        month = hijriDate.month.toUi(),
        year = hijriDate.year
    )
)

/** One entry per domain PrayerName, and PrayerName <-> PrayerNameUi is exhaustive,
 * so every PrayerNameUi has a time. */
fun PrayerTimings.toUi(): PrayerTimingsUi = PrayerTimingsUi(
    times = PrayerName.entries.associate { name -> name.toUi() to this[name] }
)
```

## app\src\main\java\com\mhq\salati\home\presentation\model\HijriDateUi.kt

```kotlin
package com.mhq.salati.home.presentation.model

import com.mhq.salati.shared.presentation.model.HijriMonthUi

data class HijriDateUi(
    val day: Int,
    val month: HijriMonthUi,
    val year: Int
)
```

## app\src\main\java\com\mhq\salati\home\presentation\model\PrayerDateUi.kt

```kotlin
package com.mhq.salati.home.presentation.model

import java.time.LocalDate

data class PrayerDateUi(
    val gregorianDate: LocalDate,
    val hijriDate: HijriDateUi
)
```

## app\src\main\java\com\mhq\salati\home\presentation\model\PrayerTimingsUi.kt

```kotlin
package com.mhq.salati.home.presentation.model

import com.mhq.salati.shared.presentation.model.PrayerNameUi
import java.time.LocalTime

/**
 * The clock time of every prayer and timing for one day.
 * The mapper fills one entry per [PrayerNameUi], so [get] never misses.
 * Formatting for the screen (12/24 hour, locale) is the UI's job.
 */
data class PrayerTimingsUi(
    private val times: Map<PrayerNameUi, LocalTime>
) {
    operator fun get(name: PrayerNameUi): LocalTime = times.getValue(name)
}
```

## app\src\main\java\com\mhq\salati\home\presentation\viewmodel\HomeViewModel.kt

```kotlin
package com.mhq.salati.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.home.domain.model.PrayerTimesError
import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.home.domain.model.PrayerTimesTick
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.home.domain.service.DayNavigator
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.handlers.HomeAlarmsHandler
import com.mhq.salati.home.presentation.handlers.HomeLocationHandler
import com.mhq.salati.home.presentation.handlers.HomeLocationLoad
import com.mhq.salati.home.presentation.handlers.MutedPrayersHandler
import com.mhq.salati.home.presentation.handlers.PrayerTimesHandler
import com.mhq.salati.home.presentation.mapper.toUi
import com.mhq.salati.location.presentation.handlers.LocationPrerequisitesHandler
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.settings.domain.usecases.ObserveTimeFormatUseCase
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.presentation.mapper.toDomain
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.shared.presentation.mapper.toUiSet
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dayNavigator: DayNavigator,
    private val locationHandler: HomeLocationHandler,
    private val prerequisitesHandler: LocationPrerequisitesHandler,
    private val prayerTimesHandler: PrayerTimesHandler,
    private val mutedPrayersHandler: MutedPrayersHandler,
    private val alarmsHandler: HomeAlarmsHandler,
    private val observeTimeFormatUseCase: ObserveTimeFormatUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = Channel<HomeContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var loadPrayerTimesJob: Job? = null
    private var countdownJob: Job? = null
    private var mutedPrayersJob: Job? = null

    private var loadedTimings: PrayerTimings? = null

    /** Last value the UI reported: the system would still show its dialog for the location permission. */
    private var showLocationRationale = false

    init {
        observeMutedPrayersForCurrentDate()

        locationHandler.localizedNameChanges
            .onEach { name ->
                _state.update { it.copy(locationUiState = it.locationUiState.copy(locationName = name)) }
            }
            .launchIn(viewModelScope)

        observeTimeFormatUseCase()
            .onEach { format -> _state.update { it.copy(timeFormat = format.toUi()) } }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadPrayerTimes -> {
                val current = _state.value
                when {
                    current.prerequisites != null -> reevaluatePrerequisites()
                    current.errorMessage == null -> checkPermissionAndLoad()
                }
            }

            is HomeContract.Intent.RetryRequested -> checkPermissionAndLoad()

            is HomeContract.Intent.PreviousDay -> {
                dayNavigator.previousOrNull(currentDate())?.let { browseDate(it) }
            }

            is HomeContract.Intent.NextDay -> browseDate(dayNavigator.next(currentDate()))
            is HomeContract.Intent.TodayRequested -> {
                if (!dayNavigator.isToday(currentDate())) browseDate(dayNavigator.today())
            }

            is HomeContract.Intent.EndOfMonthRequested ->
                browseDate(dayNavigator.endOfMonth(currentDate()))

            is HomeContract.Intent.LocationPermissionRequested -> {
                viewModelScope.launch {
                    prerequisitesHandler.onPermissionRequested()
                    _effect.send(HomeContract.Effect.RequestLocationPermission)
                }
            }

            is HomeContract.Intent.LocationPermissionGranted -> reevaluatePrerequisites()

            is HomeContract.Intent.LocationPermissionDenied -> {
                showLocationRationale = intent.showRationale
                reevaluatePrerequisites()
            }

            is HomeContract.Intent.AppSettingsRequested ->
                sendEffect(HomeContract.Effect.NavigateToAppSettings)

            is HomeContract.Intent.LocationSettingsRequested ->
                sendEffect(HomeContract.Effect.NavigateToLocationSettings)

            is HomeContract.Intent.InternetSettingsRequested ->
                sendEffect(HomeContract.Effect.NavigateToInternetSettings)

            is HomeContract.Intent.LocationPickerRequested ->
                sendEffect(HomeContract.Effect.NavigateToLocationPicker)

            is HomeContract.Intent.ToggleMute -> {
                viewModelScope.launch {
                    mutedPrayersHandler.toggle(
                        currentDate(),
                        intent.prayerName.toDomain()
                    )
                }
            }

            is HomeContract.Intent.ScreenResumed -> {
                showLocationRationale = intent.showLocationRationale
                val current = _state.value
                viewModelScope.launch {
                    // Exact-alarm access may have been granted in the system settings.
                    alarmsHandler.recheck(loadedTimings, currentDate())

                    when {
                        current.prerequisites != null -> reevaluatePrerequisites()
                        current.prayerTimesUiState.timings == null &&
                                current.errorMessage == null &&
                                !current.isLoading -> checkPermissionAndLoad()
                    }
                }
            }

            is HomeContract.Intent.LocationServicesToggled -> {
                if (_state.value.prerequisites != null) reevaluatePrerequisites()
            }

            is HomeContract.Intent.ConnectivityChanged -> {
                when {
                    _state.value.prerequisites != null -> reevaluatePrerequisites()
                    intent.isConnected && isShowingGenericOfflineError() ->
                        checkPermissionAndLoad(silent = true)
                }
            }
        }
    }

    private fun sendEffect(effect: HomeContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    // ---------------------------------------------------------------- date browsing

    private fun currentDate(): LocalDate = _state.value.dateBrowserUiState.currentDate

    private fun browseDate(date: LocalDate) {
        _state.update {
            it.copy(
                dateBrowserUiState = it.dateBrowserUiState.copy(
                    currentDate = date,
                    isBrowsingToday = dayNavigator.isToday(date)
                )
            )
        }
        observeMutedPrayersForCurrentDate()
        loadPrayerTimes()
    }

    // ---------------------------------------------------------------- loading

    // A failed fetch (the blocked screen is handled by [reevaluatePrerequisites]).
    private fun isShowingGenericOfflineError(): Boolean =
        _state.value.errorMessage != null

    private fun checkPermissionAndLoad(silent: Boolean = false) {
        // A silent re-check keeps the current screen until the new result replaces it.
        if (!silent) _state.update { it.copy(errorMessage = null) }
        loadPrayerTimes()
    }

    /**
     * Re-checks what the blocked screen is waiting for. The rows update in place, with no loading
     * state in between; the lookup only restarts once nothing is missing (or a place was picked).
     */
    private fun reevaluatePrerequisites() {
        viewModelScope.launch {
            val blocked = prerequisitesHandler.blocked(showLocationRationale)
            if (blocked == null) checkPermissionAndLoad(silent = true) else showBlocked(blocked)
        }
    }

    private fun showBlocked(prerequisites: PrerequisitesUi) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                prerequisites = prerequisites
            )
        }
    }

    private fun loadPrayerTimes() {
        loadPrayerTimesJob?.cancel()
        loadPrayerTimesJob = viewModelScope.launch {
            try {
                locationHandler.load().collect { load -> handleLocationLoad(load) }
            } catch (e: SecurityException) {
                reevaluatePrerequisites()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = MessageUi.FAILED_TO_GET_LOCATION
                _state.update { it.copy(isLoading = false, errorMessage = message) }
                _effect.send(HomeContract.Effect.ShowError(message))
            }
        }
    }

    private suspend fun handleLocationLoad(load: HomeLocationLoad) {
        when (load) {
            is HomeLocationLoad.Located -> showLocationAndLoadPrayerTimes(load)

            is HomeLocationLoad.LookingUpCurrentLocation -> {
                // A blocked screen gives way to the spinner; an error screen waits for the result.
                if (_state.value.errorMessage == null) {
                    _state.update { it.copy(isLoading = true, prerequisites = null) }
                }
            }

            is HomeLocationLoad.Blocked ->
                showBlocked(prerequisitesHandler.toUi(load.prerequisites, showLocationRationale))

            is HomeLocationLoad.GeocodingFailed ->
                showError(MessageUi.FAILED_TO_GET_LOCATION_NAME, isOffline = false)

            is HomeLocationLoad.Unavailable ->
                showError(MessageUi.FAILED_TO_GET_LOCATION, isOffline = false)
        }
    }

    private suspend fun showLocationAndLoadPrayerTimes(located: HomeLocationLoad.Located) =
        coroutineScope {
            val location = located.location
            _state.update {
                it.copy(
                    errorMessage = null,
                    prerequisites = null,
                    locationUiState = it.locationUiState.copy(
                        locationName = location.displayName
                    )
                )
            }

            val nameDeferred = async { locationHandler.localizedName(located) }
            val prayerTimesDeferred = async { loadAndApplyPrayerTimes(location.coordinates) }

            val finalName = nameDeferred.await()
            _state.update {
                it.copy(locationUiState = it.locationUiState.copy(locationName = finalName))
            }
            prayerTimesDeferred.await()
        }

    private suspend fun showError(message: MessageUi, isOffline: Boolean) {
        _state.update {
            it.copy(
                isLoading = false,
                prerequisites = null,
                errorMessage = message
            )
        }
        if (!isOffline) {
            _effect.send(HomeContract.Effect.ShowError(message))
        }
    }

    // ---------------------------------------------------------------- prayer times

    private suspend fun loadAndApplyPrayerTimes(coordinates: Coordinates) {
        prayerTimesHandler.load(currentDate(), coordinates).collect { load ->
            when (load) {
                is PrayerTimesLoad.FetchingFromNetwork -> {
                    val isScreenCompletelyEmpty =
                        _state.value.locationUiState.locationName == null &&
                                _state.value.prayerTimesUiState.timings == null
                    if (isScreenCompletelyEmpty) {
                        _state.update { it.copy(isLoading = true) }
                    }
                }

                is PrayerTimesLoad.Loaded -> applyLoadedPrayerTimes(load, coordinates)
                is PrayerTimesLoad.Failed -> showPrayerTimesError(load.error)
            }
        }
    }

    private suspend fun applyLoadedPrayerTimes(
        load: PrayerTimesLoad.Loaded,
        coordinates: Coordinates
    ) {
        loadedTimings = load.timings
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                prayerTimesUiState = it.prayerTimesUiState.copy(
                    timings = load.timings.toUi(),
                    date = load.prayerDate.toUi(),
                    nextPrayerName = load.status.window.nextPrayer.toUi(),
                    currentPrayerName = load.status.currentPrayer?.toUi(),
                    pastPrayers = load.status.pastPrayers.toUiSet()
                )
            )
        }
        startCountdown(load.timings, currentDate(), coordinates, load.status.window)
        rescheduleAlarms()
    }

    private suspend fun showPrayerTimesError(error: PrayerTimesError) {
        val isOffline = error is PrayerTimesError.Offline
        val message = when (error) {
            is PrayerTimesError.Offline -> MessageUi.NO_INTERNET_CONNECTION
            is PrayerTimesError.TimedOut -> MessageUi.REQUEST_TIMED_OUT_CHECK_CONNECTION
            is PrayerTimesError.Other -> MessageUi.SOMETHING_WENT_WRONG
        }
        _state.update {
            it.copy(
                isLoading = false,
                prerequisites = null,
                errorMessage = message
            )
        }
        if (!isOffline) {
            _effect.send(HomeContract.Effect.ShowError(message))
        }
    }

    private fun startCountdown(
        timings: PrayerTimings,
        date: LocalDate,
        coordinates: Coordinates,
        window: PrayerWindow
    ) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            prayerTimesHandler.keepCountingDown(timings, date, coordinates, window)
                .collect { tick ->
                    when (tick) {
                        is PrayerTimesTick.Countdown -> _state.update {
                            it.copy(
                                prayerTimesUiState = it.prayerTimesUiState.copy(
                                    remaining = tick.countdown.remaining,
                                    windowProgress = tick.countdown.progress
                                )
                            )
                        }

                        is PrayerTimesTick.StatusRefreshed -> _state.update {
                            it.copy(
                                prayerTimesUiState = it.prayerTimesUiState.copy(
                                    nextPrayerName = tick.status.window.nextPrayer.toUi(),
                                    currentPrayerName = tick.status.currentPrayer?.toUi(),
                                    pastPrayers = tick.status.pastPrayers.toUiSet()
                                )
                            )
                        }

                        is PrayerTimesTick.DayRolledOver -> browseDate(tick.date)
                    }
                }
        }
    }

    // ---------------------------------------------------------------- alarms

    private suspend fun rescheduleAlarms() {
        alarmsHandler.schedule(loadedTimings, currentDate())
    }

    // ---------------------------------------------------------------- muted prayers

    private fun observeMutedPrayersForCurrentDate() {
        mutedPrayersJob?.cancel()
        mutedPrayersJob = viewModelScope.launch {
            mutedPrayersHandler.observe(currentDate()).collect { mutedPrayers ->
                _state.update {
                    it.copy(adhanUiState = it.adhanUiState.copy(mutedPrayers = mutedPrayers.toUiSet()))
                }
                rescheduleAlarms()
            }
        }
    }

    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }
}
```

## app\src\main\java\com\mhq\salati\home\ui\components\DateBanner.kt

```kotlin
package com.mhq.salati.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.home.presentation.model.HijriDateUi
import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.shared.presentation.model.HijriMonthUi
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DateBanner(
    prayerDate: PrayerDateUi,
    isBrowsingToday: Boolean,
    onToday: () -> Unit,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onEndOfMonth: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val hijriMonthLabel = stringResource(prayerDate.hijriDate.month.labelRes)

    val gregorianLine = remember(prayerDate.gregorianDate) {
        prayerDate.gregorianDate.format(
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault())
        )
    }

    val todayIcon =
        if (isRtl)
            Icons.Default.KeyboardDoubleArrowRight
        else
            Icons.Default.KeyboardDoubleArrowLeft

    val endOfMonthIcon =
        if (isRtl)
            Icons.Default.KeyboardDoubleArrowLeft
        else
            Icons.Default.KeyboardDoubleArrowRight

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(50))
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.dp,
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp)
            .fillMaxWidth(0.85f)
            .height(54.dp)
    ) {
        BannerChevron(
            icon = todayIcon,
            contentDescription = stringResource(R.string.back_to_today),
            onClick = onToday,
            enabled = !isBrowsingToday
        )
        Spacer(Modifier.width(4.dp))
        BannerChevron(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = stringResource(R.string.previous_day),
            onClick = onPreviousDay,
            enabled = !isBrowsingToday
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "${prayerDate.hijriDate.day} $hijriMonthLabel ${prayerDate.hijriDate.year}",
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 12.sp,
                    maxFontSize = 18.sp,
                    stepSize = 1.sp
                ),
                style = TextStyle(
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    )
                )
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = gregorianLine,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    )
                )
            )
        }

        BannerChevron(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = stringResource(R.string.next_day),
            onClick = onNextDay
        )
        Spacer(Modifier.width(4.dp))
        BannerChevron(
            icon = endOfMonthIcon,
            contentDescription = stringResource(R.string.end_of_month),
            onClick = onEndOfMonth
        )
    }
}

@Composable
private fun BannerChevron(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(24.dp)
            .alpha(if (enabled) 1f else 0.38f)
            .background(MaterialTheme.colorScheme.tertiary, CircleShape)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DateBannerPreview() {
    SalatiTheme {
        DateBanner(
            prayerDate = PrayerDateUi(
                gregorianDate = LocalDate.of(2026, 8, 17),
                hijriDate = HijriDateUi(day = 4, month = HijriMonthUi.RABI_AL_AWWAL, year = 1448)
            ),
            isBrowsingToday = false,
            onToday = {},
            onPreviousDay = {},
            onNextDay = {},
            onEndOfMonth = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\home\ui\components\LocationHeader.kt

```kotlin
package com.mhq.salati.home.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.prayerGradient

/** The city name with a pin beside it; tapping it opens the location picker. */
@Composable
fun LocationHeader(
    locationName: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f))
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f),
                shape = RoundedCornerShape(50)
            )
            .clickable(role = Role.Button, onClick = onClick)
            .padding(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = stringResource(R.string.cd_prayer_location),
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = locationName ?: stringResource(R.string.unknown_location),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp
        )
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Composable
private fun LocationHeaderLightPreview() {
    SalatiTheme(darkTheme = false) {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.prayerGradient)
                .padding(24.dp)
        ) {
            LocationHeader(locationName = "Alexandria, Egypt", onClick = {})
        }
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LocationHeaderDarkPreview() {
    SalatiTheme(darkTheme = true) {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.prayerGradient)
                .padding(24.dp)
        ) {
            LocationHeader(locationName = "Alexandria, Egypt", onClick = {})
        }
    }
}
```

## app\src\main\java\com\mhq\salati\home\ui\components\PrayerCountdownRing.kt

```kotlin
package com.mhq.salati.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.countdownSweep
import com.mhq.salati.shared.ui.theme.countdownTrack
import java.time.Duration
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PrayerCountdownRing(
    nextPrayerName: String,
    remaining: Duration,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val countdownLabel = remaining.toCountdownLabel()
    val progressFraction = progress.coerceIn(0f, 1f)

    val trackColor = MaterialTheme.colorScheme.countdownTrack
    val progressBrush = MaterialTheme.colorScheme.countdownSweep
    val markerOuterColor = MaterialTheme.colorScheme.surface
    val markerInnerColor = MaterialTheme.colorScheme.tertiary
    val textColor = MaterialTheme.colorScheme.onSurface

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        /** 1f at the original 220dp design size, shrinks only when space is tighter */
        val scale = (minOf(maxWidth, maxHeight) / 220.dp).coerceIn(0.5f, 1f)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = (16 * scale).dp.toPx()
            val radius = (minOf(size.width, size.height) - strokeWidth) / 2f
            val center = Offset(size.width / 2f, size.height / 2f)
            val topLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(radius * 2, radius * 2)

            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                brush = progressBrush,
                startAngle = -90f,
                sweepAngle = 360f * progressFraction,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            val markerAngleRad = Math.toRadians((-90 + 360 * progressFraction).toDouble())
            val markerPoint = Offset(
                center.x + radius * cos(markerAngleRad).toFloat(),
                center.y + radius * sin(markerAngleRad).toFloat()
            )
            drawCircle(
                color = markerOuterColor,
                radius = strokeWidth / 2f,
                center = markerPoint
            )
            drawCircle(
                color = markerInnerColor,
                radius = strokeWidth / 3.2f,
                center = markerPoint
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.next_prayer, nextPrayerName),
                color = textColor,
                fontSize = (16 * scale).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height((8 * scale).dp))
            Text(
                text = countdownLabel,
                color = textColor,
                fontSize = (34 * scale).sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@SuppressLint("DefaultLocale")
private fun Duration.toCountdownLabel(): String {
    val totalSeconds = this.seconds
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Preview
@Composable
private fun PrayerCountdownRingPreview() {
    SalatiTheme {
        PrayerCountdownRing(
            nextPrayerName = "Fajr",
            remaining = Duration.ofSeconds(3600),
            progress = 0.4f,
            modifier = Modifier.size(220.dp)
        )
    }
}
```

## app\src\main\java\com\mhq\salati\home\ui\components\PrayerGridItem.kt

```kotlin
package com.mhq.salati.home.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun PrayerGridItem(
    prayerName: String,
    prayerTime: String,
    isPrayerHighlighted: Boolean,
    isPrayerAdhanMuted: Boolean,
    isPrayerPassed: Boolean,
    onMutePrayerToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPrayerHighlighted) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isPrayerHighlighted) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f))
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 14.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = prayerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color =
                        if (isPrayerHighlighted)
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = prayerTime,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color =
                        if (isPrayerHighlighted)
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = onMutePrayerToggle,
                enabled = !isPrayerPassed,
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = when {
                            isPrayerHighlighted -> MaterialTheme.colorScheme.tertiary
                            isPrayerAdhanMuted -> MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.12f
                            )

                            else -> MaterialTheme.colorScheme.secondaryContainer
                        },
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isPrayerAdhanMuted) {
                        Icons.Filled.NotificationsOff
                    } else {
                        Icons.Filled.NotificationsActive
                    },
                    tint = when {
                        isPrayerHighlighted -> MaterialTheme.colorScheme.onPrimaryContainer
                        isPrayerAdhanMuted -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (isPrayerPassed) 0.4f else 1f)
                        else -> MaterialTheme.colorScheme.secondary.copy(alpha = if (isPrayerPassed) 0.4f else 1f)
                    },
                    contentDescription =
                        if (isPrayerAdhanMuted)
                            stringResource(R.string.muted)
                        else
                            stringResource(R.string.enabled),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(name = "Light Mode - Active", showBackground = true)
@Preview(
    name = "Dark Mode - Active",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PrayerGridItemActivePreview() {
    SalatiTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            PrayerGridItem(
                prayerName = "Asr",
                prayerTime = "15:30",
                isPrayerHighlighted = true,
                isPrayerAdhanMuted = false,
                isPrayerPassed = false,
                onMutePrayerToggle = {}
            )
        }
    }
}

@Preview(name = "Light Mode - Passed & Muted", showBackground = true)
@Preview(
    name = "Dark Mode - Passed & Muted",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PrayerGridItemMutedPreview() {
    SalatiTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            PrayerGridItem(
                prayerName = "Fajr",
                prayerTime = "04:45",
                isPrayerHighlighted = false,
                isPrayerAdhanMuted = true,
                isPrayerPassed = true,
                onMutePrayerToggle = {}
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\home\ui\components\PrayersGrid.kt

```kotlin
package com.mhq.salati.home.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun PrayersList(
    prayers: List<Pair<PrayerNameUi, String>>,
    minorTimings: List<Pair<PrayerNameUi, String>>,
    currentTimingName: PrayerNameUi?,
    mutedTimings: Set<PrayerNameUi>,
    pastTimings: Set<PrayerNameUi>,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        prayers.forEachIndexed { index, (name, time) ->
            val (minorName, minorTime) = minorTimings[index]

            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Box(modifier = Modifier.weight(1f)) {
                    PrayerGridItem(
                        prayerName = stringResource(name.labelRes),
                        prayerTime = time,
                        isPrayerHighlighted = name == currentTimingName,
                        isPrayerAdhanMuted = name in mutedTimings,
                        isPrayerPassed = name in pastTimings,
                        onMutePrayerToggle = { onIntent(HomeContract.Intent.ToggleMute(name)) }
                    )
                }
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    PrayerGridItem(
                        prayerName = stringResource(minorName.labelRes),
                        prayerTime = minorTime,
                        isPrayerHighlighted = minorName == currentTimingName,
                        isPrayerAdhanMuted = minorName in mutedTimings,
                        isPrayerPassed = minorName in pastTimings,
                        onMutePrayerToggle = { onIntent(HomeContract.Intent.ToggleMute(minorName)) }
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode Grid", showBackground = true)
@Preview(name = "Dark Mode Grid", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrayersListPreview() {
    SalatiTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            PrayersList(
                prayers = listOf("Fajr" to "04:30", "Dhuhr" to "12:00", "Asr" to "15:30").map { PrayerNameUi.FAJR to it.second },
                minorTimings = listOf("Imsak" to "04:15", "Shorouq" to "05:45", "Midnight" to "23:30").map { PrayerNameUi.IMSAK to it.second },
                currentTimingName = PrayerNameUi.FAJR,
                mutedTimings = setOf(PrayerNameUi.IMSAK),
                pastTimings = emptySet(),
                onIntent = {}
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\home\ui\screens\HomeContainer.kt

```kotlin
package com.mhq.salati.home.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.viewmodel.HomeViewModel
import com.mhq.salati.location.ui.LocationPrerequisitesObserver
import com.mhq.salati.location.ui.rememberLocationPrerequisitesActions
import com.mhq.salati.permissions.ui.shouldShowLocationRationale
import com.mhq.salati.shared.ui.asString
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import kotlinx.coroutines.launch

@Composable
fun HomeContainer(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLocationPicker: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    val locationActions = rememberLocationPrerequisitesActions(
        onPermissionGranted = {
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
        },
        onPermissionDenied = { showRationale ->
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionDenied(showRationale))
        }
    )

    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.RequestLocationPermission -> locationActions.requestPermission()
                is HomeContract.Effect.NavigateToAppSettings -> locationActions.openAppSettings()
                is HomeContract.Effect.NavigateToLocationSettings -> locationActions.openLocationSettings()
                is HomeContract.Effect.NavigateToInternetSettings -> locationActions.openInternetSettings()
                is HomeContract.Effect.NavigateToLocationPicker -> onNavigateToLocationPicker()
                is HomeContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                homeViewModel.onIntent(
                    HomeContract.Intent.ScreenResumed(context.shouldShowLocationRationale())
                )
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LocationPrerequisitesObserver(
        onServicesChanged = { enabled ->
            homeViewModel.onIntent(HomeContract.Intent.LocationServicesToggled(enabled))
        },
        onConnectivityChanged = { connected ->
            homeViewModel.onIntent(HomeContract.Intent.ConnectivityChanged(connected))
        }
    )

    LaunchedEffect(Unit) {
        homeViewModel.onIntent(HomeContract.Intent.LoadPrayerTimes)
    }

    HomeContent(
        state = state,
        onIntent = homeViewModel::onIntent
    )
}
```

## app\src\main\java\com\mhq\salati\home\ui\screens\HomeContent.kt

```kotlin
package com.mhq.salati.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.location.ui.components.PrerequisitesError
import com.mhq.salati.shared.presentation.model.MessageUi
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.asString
import com.mhq.salati.shared.ui.screens.LoadingContent
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun HomeContent(
    state: HomeContract.State,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = if (state.prerequisites == null) {
                Modifier
                    .navigationBarsPadding()
                    .padding(bottom = BottomNavBarDefaults.Height)
            } else {
                Modifier.fillMaxSize()
            }
        ) {
            when {
                state.prerequisites != null -> {
                    PrerequisitesError(
                        prerequisites = state.prerequisites,
                        onOpenInternetSettings = { onIntent(HomeContract.Intent.InternetSettingsRequested) },
                        onOpenLocationSettings = { onIntent(HomeContract.Intent.LocationSettingsRequested) },
                        onAllowPermission = { onIntent(HomeContract.Intent.LocationPermissionRequested) },
                        onOpenAppSettings = { onIntent(HomeContract.Intent.AppSettingsRequested) },
                        onChooseLocationManually = { onIntent(HomeContract.Intent.LocationPickerRequested) },
                        modifier = Modifier.weight(1f)
                    )
                }

                state.isLoading -> {
                    LoadingContent(modifier = Modifier.weight(1f))
                }

                state.errorMessage != null -> {
                    HomeContentError(
                        errorMessage = state.errorMessage.asString(),
                        isNoConnection = state.errorMessage == MessageUi.NO_INTERNET_CONNECTION,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }

                state.prayerTimesUiState.timings != null
                        && state.prayerTimesUiState.date != null
                        && state.prayerTimesUiState.nextPrayerName != null -> {
                    HomeContentSuccess(
                        locationName = state.locationUiState.locationName,
                        prayerDate = state.prayerTimesUiState.date,
                        prayerTimings = state.prayerTimesUiState.timings,
                        nextPrayer = state.prayerTimesUiState.nextPrayerName,
                        remaining = state.prayerTimesUiState.remaining,
                        windowProgress = state.prayerTimesUiState.windowProgress,
                        currentPrayerName = state.prayerTimesUiState.currentPrayerName,
                        mutedPrayers = state.adhanUiState.mutedPrayers,
                        pastPrayers = state.prayerTimesUiState.pastPrayers,
                        isToday = state.dateBrowserUiState.isBrowsingToday,
                        timeFormat = state.timeFormat,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Home - Loading")
@Composable
private fun HomeContentLoadingPreview() {
    SalatiTheme {
        HomeContent(
            state = HomeContract.State(isLoading = true),
            onIntent = {},
        )
    }
}
```

## app\src\main\java\com\mhq\salati\home\ui\screens\HomeContentError.kt

```kotlin
package com.mhq.salati.home.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.ui.errors.CatchAllError
import com.mhq.salati.shared.ui.errors.NoConnectionError
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun HomeContentError(
    errorMessage: String,
    isNoConnection: Boolean,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isNoConnection -> {
            NoConnectionError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(HomeContract.Intent.RetryRequested) },
                modifier = modifier
            )
        }

        else -> {
            CatchAllError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(HomeContract.Intent.RetryRequested) },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentErrorPreview() {
    SalatiTheme()  {
        HomeContentError(
            errorMessage = "Some message",
            isNoConnection = false,
            onIntent = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\home\ui\screens\HomeContentSuccess.kt

```kotlin
package com.mhq.salati.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.home.presentation.model.PrayerTimingsUi
import com.mhq.salati.home.ui.components.DateBanner
import com.mhq.salati.home.ui.components.LocationHeader
import com.mhq.salati.home.ui.components.PrayerCountdownRing
import com.mhq.salati.home.ui.components.PrayersList
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.formatForDisplay
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.prayerGradient
import java.time.Duration

@Composable
fun HomeContentSuccess(
    locationName: String?,
    prayerDate: PrayerDateUi,
    prayerTimings: PrayerTimingsUi,
    nextPrayer: PrayerNameUi,
    remaining: Duration,
    windowProgress: Float,
    currentPrayerName: PrayerNameUi?,
    mutedPrayers: Set<PrayerNameUi>,
    pastPrayers: Set<PrayerNameUi>,
    isToday: Boolean,
    timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_12_HOUR,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0]
    val isLandscape = isWideLayout()

    val prayers = remember(prayerTimings, timeFormat, locale) {
        listOf(
            PrayerNameUi.FAJR,
            PrayerNameUi.DHUHR,
            PrayerNameUi.ASR,
            PrayerNameUi.MAGHRIB,
            PrayerNameUi.ISHA
        ).map { name -> name to prayerTimings[name].formatForDisplay(timeFormat, locale) }
    }

    val minorTimings = remember(prayerTimings, timeFormat, locale) {
        listOf(
            PrayerNameUi.IMSAK,
            PrayerNameUi.SHOROUQ,
            PrayerNameUi.FIRST_THIRD,
            PrayerNameUi.MIDNIGHT,
            PrayerNameUi.LAST_THIRD
        ).map { name -> name to prayerTimings[name].formatForDisplay(timeFormat, locale) }
    }

    var headerSizingPx by remember { mutableIntStateOf(0) }
    var bannerHeightPx by remember { mutableIntStateOf(0) }

    Box(modifier = modifier.fillMaxSize()) {
        if (isLandscape) {
            Row(modifier = Modifier.fillMaxSize()) {
                /** Left Column: even 50% split with straight line boundary */
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(brush = MaterialTheme.colorScheme.prayerGradient)
                        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Start))
                        .statusBarsPadding()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    LocationHeader(
                        locationName = locationName,
                        onClick = { onIntent(HomeContract.Intent.LocationPickerRequested) }
                    )
                    PrayerCountdownRing(
                        nextPrayerName = stringResource(nextPrayer.labelRes),
                        remaining = remaining,
                        progress = windowProgress,
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .padding(vertical = 16.dp)
                            .aspectRatio(1f)
                    )
                    DateBanner(
                        prayerDate = prayerDate,
                        isBrowsingToday = isToday,
                        onToday = { onIntent(HomeContract.Intent.TodayRequested) },
                        onPreviousDay = { onIntent(HomeContract.Intent.PreviousDay) },
                        onNextDay = { onIntent(HomeContract.Intent.NextDay) },
                        onEndOfMonth = { onIntent(HomeContract.Intent.EndOfMonthRequested) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                /** Right Column: even 50% split, scrolls when the screen is too short */
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.End))
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    PrayersList(
                        prayers = prayers,
                        minorTimings = minorTimings,
                        currentTimingName = currentPrayerName,
                        mutedTimings = mutedPrayers,
                        pastTimings = pastPrayers,
                        onIntent = onIntent,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { headerSizingPx = it.size.height }
                        .background(
                            brush = MaterialTheme.colorScheme.prayerGradient,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                        .statusBarsPadding()
                        .padding(bottom = 40.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LocationHeader(
                        locationName = locationName,
                        onClick = { onIntent(HomeContract.Intent.LocationPickerRequested) }
                    )
                    PrayerCountdownRing(
                        nextPrayerName = stringResource(nextPrayer.labelRes),
                        remaining = remaining,
                        progress = windowProgress,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .size(220.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    PrayersList(
                        prayers = prayers,
                        minorTimings = minorTimings,
                        currentTimingName = currentPrayerName,
                        mutedTimings = mutedPrayers,
                        pastTimings = pastPrayers,
                        onIntent = onIntent,
                        modifier = Modifier.widthIn(max = 560.dp).fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            DateBanner(
                prayerDate = prayerDate,
                isBrowsingToday = isToday,
                onToday = { onIntent(HomeContract.Intent.TodayRequested) },
                onPreviousDay = { onIntent(HomeContract.Intent.PreviousDay) },
                onNextDay = { onIntent(HomeContract.Intent.NextDay) },
                onEndOfMonth = { onIntent(HomeContract.Intent.EndOfMonthRequested) },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .onGloballyPositioned { bannerHeightPx = it.size.height }
                    .offset { IntOffset(x = 0, y = headerSizingPx - bannerHeightPx / 2) }
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\location\data\mapper\NominatimMapper.kt

```kotlin
package com.mhq.salati.location.data.mapper

import com.mhq.salati.location.datasource.network.dto.NominatimAddressDto
import com.mhq.salati.location.datasource.network.dto.NominatimSearchDto
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.shared.domain.Coordinates

// Prioritizes the most specific place name Nominatim knows.
internal fun NominatimAddressDto.bestCityName(): String? =
    city
        ?: town
        ?: village
        ?: suburb
        ?: municipality
        ?: stateDistrict
        ?: county
        ?: state

internal fun NominatimSearchDto.toDomainOrNull(): LocationSearchResult? {
    val latitude = lat ?: return null
    val longitude = lon ?: return null

    return LocationSearchResult(
        displayName = constructDisplayName(address, displayName),
        coordinates = Coordinates(latitude, longitude)
    )
}

// Builds a clean "City, State, Country" string.
private fun constructDisplayName(address: NominatimAddressDto?, fallback: String): String {
    if (address == null) return fallback.split(",").firstOrNull()?.trim() ?: fallback

    val city = address.bestCityName()
    val state = address.state ?: address.stateDistrict
    val country = address.country

    // Non-null, non-blank parts without duplicates (for example when city and state are both "Cairo")
    val parts = listOfNotNull(city, state, country)
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .distinct()

    return if (parts.isNotEmpty()) {
        parts.joinToString(", ")
    } else {
        // Falls back to the first part of the original display name if the address is useless
        fallback.split(",").firstOrNull()?.trim() ?: fallback
    }
}
```

## app\src\main\java\com\mhq\salati\location\data\mapper\StoredLocationMapper.kt

```kotlin
package com.mhq.salati.location.data.mapper

import com.mhq.salati.location.datasource.preferences.StoredLocation
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.shared.domain.Coordinates

/** A stored pair that isn't a valid coordinate counts as "no saved location" instead of crashing. */
fun StoredLocation.toDomain(): SavedLocation? =
    runCatching { Coordinates(latitude, longitude) }.getOrNull()?.let { coordinates ->
        SavedLocation(
            coordinates = coordinates,
            cityName = cityName,
            countryName = countryName
        )
    }

fun SavedLocation.toStored(): StoredLocation = StoredLocation(
    latitude = coordinates.latitude,
    longitude = coordinates.longitude,
    cityName = cityName,
    countryName = countryName
)
```

## app\src\main\java\com\mhq\salati\location\data\repoimpl\GeocoderProviderImpl.kt

```kotlin
package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.data.mapper.bestCityName
import com.mhq.salati.location.data.mapper.toDomainOrNull
import com.mhq.salati.location.datasource.network.service.NominatimApi
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import javax.inject.Inject

class GeocoderProviderImpl @Inject constructor(
    private val nominatimApi: NominatimApi
) : GeocoderProvider {

    override suspend fun reverseGeocode(
        coordinates: Coordinates,
        acceptLanguage: String?
    ): GeocodeResult = try {
        val dto = nominatimApi.reverse(coordinates.latitude, coordinates.longitude, acceptLanguage)
        if (dto.error != null || dto.address == null) {
            GeocodeResult.NotFound
        } else {
            GeocodeResult.Found(
                cityName = dto.address.bestCityName(),
                countryName = dto.address.country
            )
        }
    } catch (e: TimeoutCancellationException) {
        GeocodeResult.Failed(e)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        GeocodeResult.Failed(e)
    }

    /** Throws on network or server failure; an empty list means the search really found nothing. */
    override suspend fun searchByName(
        query: String,
        acceptLanguage: String?
    ): List<LocationSearchResult> =
        nominatimApi.search(query, acceptLanguage).mapNotNull { it.toDomainOrNull() }
}
```

## app\src\main\java\com\mhq\salati\location\data\repoimpl\LocationProviderImpl.kt

```kotlin
package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.datasource.device.GpsDataSource
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.shared.domain.Coordinates
import javax.inject.Inject

class LocationProviderImpl @Inject constructor(
    private val gpsDataSource: GpsDataSource
) : LocationProvider {

    override fun isLocationEnabled(): Boolean = gpsDataSource.isLocationEnabled()

    override suspend fun getCurrentLocation(): Coordinates {
        val location = gpsDataSource.getCurrentLocation()
        return Coordinates(location.latitude, location.longitude)
    }
}
```

## app\src\main\java\com\mhq\salati\location\data\repoimpl\LocationRepoImpl.kt

```kotlin
package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.data.mapper.toDomain
import com.mhq.salati.location.data.mapper.toStored
import com.mhq.salati.location.datasource.preferences.LocationDataStore
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(
    private val locationDataStore: LocationDataStore
) : LocationRepository {

    override val savedLocation: Flow<SavedLocation?> =
        locationDataStore.storedLocation.map { it?.toDomain() }

    override suspend fun saveManualLocation(location: SavedLocation) {
        locationDataStore.save(location.toStored())
    }

    override suspend fun clearSavedLocation() {
        locationDataStore.clear()
    }
}
```

## app\src\main\java\com\mhq\salati\location\datasource\device\DeviceLocation.kt

```kotlin
package com.mhq.salati.location.datasource.device

/** A position as the GPS client reports it. */
data class DeviceLocation(
    val latitude: Double,
    val longitude: Double
)
```

## app\src\main\java\com\mhq\salati\location\datasource\device\FusedGpsDataSource.kt

```kotlin
package com.mhq.salati.location.datasource.device

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FusedGpsDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : GpsDataSource {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    override fun isLocationEnabled(): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override suspend fun getCurrentLocation(): DeviceLocation {
        val location = getCurrentAndroidLocation()
        return DeviceLocation(location.latitude, location.longitude)
    }

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentAndroidLocation(): Location {
        // No timeout here on purpose: every caller wraps this call in its own withTimeout, which raises a
        // TimeoutCancellationException that composes with cooperative cancellation. A manually thrown
        // exception here would bypass those callers' timeout handling.
        return suspendCancellableCoroutine { continuation ->
            val cancellationTokenSource = CancellationTokenSource()
            var activeCallback: LocationCallback? = null

            continuation.invokeOnCancellation {
                cancellationTokenSource.cancel()
                activeCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
            }

            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )
                .addOnSuccessListener { location ->
                    if (location != null) {
                        continuation.resume(location)
                    } else {
                        activeCallback = requestSingleLocationUpdate(continuation)
                    }
                }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }

    @RequiresPermission(allOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ])
    private fun requestSingleLocationUpdate(
        cont: CancellableContinuation<Location>
    ): LocationCallback {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000L
        )
            .setMaxUpdates(1)
            .setDurationMillis(15000L)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                fusedLocationClient.removeLocationUpdates(this)
                if (!cont.isActive) return
                val location = result.lastLocation
                if (location != null) cont.resume(location)
                else cont.resumeWithException(IllegalStateException("LOCATION_UNAVAILABLE"))
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) {
                    fusedLocationClient.removeLocationUpdates(this)
                    if (!cont.isActive) return
                    cont.resumeWithException(IllegalStateException("LOCATION_UNAVAILABLE"))
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        )
            .addOnFailureListener {
                if (cont.isActive) cont.resumeWithException(it)
            }

        return callback
    }
}
```

## app\src\main\java\com\mhq\salati\location\datasource\device\GpsDataSource.kt

```kotlin
package com.mhq.salati.location.datasource.device

interface GpsDataSource {
    fun isLocationEnabled(): Boolean
    suspend fun getCurrentLocation(): DeviceLocation
}
```

## app\src\main\java\com\mhq\salati\location\datasource\network\dto\NominatimAddressDto.kt

```kotlin
package com.mhq.salati.location.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class NominatimAddressDto(
    val city: String? = null,
    val town: String? = null,
    val village: String? = null,
    val suburb: String? = null,
    val municipality: String? = null,
    val stateDistrict: String? = null,
    val state: String? = null,
    val county: String? = null,
    val country: String? = null,
    val countryCode: String? = null
)
```

## app\src\main\java\com\mhq\salati\location\datasource\network\dto\NominatimReverseDto.kt

```kotlin
package com.mhq.salati.location.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class NominatimReverseDto(
    val error: String? = null,
    val address: NominatimAddressDto? = null
)
```

## app\src\main\java\com\mhq\salati\location\datasource\network\dto\NominatimSearchDto.kt

```kotlin
package com.mhq.salati.location.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NominatimSearchDto(
    @SerialName("display_name")
    val displayName: String = "",
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("lon")
    val lon: Double? = null,
    val address: NominatimAddressDto? = null
)
```

## app\src\main\java\com\mhq\salati\location\datasource\network\service\KtorNominatimApi.kt

```kotlin
package com.mhq.salati.location.datasource.network.service

import com.mhq.salati.location.datasource.network.dto.NominatimReverseDto
import com.mhq.salati.location.datasource.network.dto.NominatimSearchDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class KtorNominatimApi @Inject constructor(
    private val httpClient: HttpClient
) : NominatimApi {

    companion object {
        private const val BASE_URL = "https://nominatim.openstreetmap.org"
        private val REQUEST_TIMEOUT = 8_000L.milliseconds
    }

    override suspend fun reverse(
        latitude: Double,
        longitude: Double,
        acceptLanguage: String?
    ): NominatimReverseDto {
        val response = withTimeout(REQUEST_TIMEOUT) {
            httpClient.get("$BASE_URL/reverse") {
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("format", "json")
                parameter("addressdetails", "1")
                acceptLanguage?.let { parameter("accept-language", it) }
            }
        }
        if (response.status != HttpStatusCode.OK) throw NominatimHttpException(response.status.value)
        return response.body()
    }

    override suspend fun search(
        query: String,
        acceptLanguage: String?
    ): List<NominatimSearchDto> {
        val response = withTimeout(REQUEST_TIMEOUT) {
            httpClient.get("$BASE_URL/search") {
                parameter("q", query)
                parameter("format", "json")
                parameter("addressdetails", "1")
                parameter("limit", "5")
                acceptLanguage?.let { parameter("accept-language", it) }
            }
        }
        if (response.status != HttpStatusCode.OK) throw NominatimHttpException(response.status.value)
        return response.body()
    }
}
```

## app\src\main\java\com\mhq\salati\location\datasource\network\service\NominatimApi.kt

```kotlin
package com.mhq.salati.location.datasource.network.service

import com.mhq.salati.location.datasource.network.dto.NominatimReverseDto
import com.mhq.salati.location.datasource.network.dto.NominatimSearchDto

/** The Nominatim web service.
 * Both calls throw when the request fails,
 * times out or the server says no. */
interface NominatimApi {
    suspend fun reverse(
        latitude: Double,
        longitude: Double,
        acceptLanguage: String?
    ): NominatimReverseDto

    suspend fun search(
        query: String,
        acceptLanguage: String?
    ): List<NominatimSearchDto>
}
```

## app\src\main\java\com\mhq\salati\location\datasource\network\service\NominatimHttpException.kt

```kotlin
package com.mhq.salati.location.datasource.network.service

class NominatimHttpException(val statusCode: Int) : Exception("HTTP $statusCode")
```

## app\src\main\java\com\mhq\salati\location\datasource\preferences\LocationDataStore.kt

```kotlin
package com.mhq.salati.location.datasource.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.locationDataStore: DataStore<Preferences> by preferencesDataStore(name = "location_prefs")

class LocationDataStore @Inject constructor(
    context: Context
) {
    private val dataStore = context.locationDataStore

    private object Keys {
        val LAT = doublePreferencesKey("latitude")
        val LON = doublePreferencesKey("longitude")
        val CITY = stringPreferencesKey("city_name")
        val COUNTRY = stringPreferencesKey("country_name")
    }

    val storedLocation: Flow<StoredLocation?> = dataStore.data.map { prefs ->
        val lat = prefs[Keys.LAT]
        val lon = prefs[Keys.LON]
        if (lat != null && lon != null) {
            StoredLocation(
                latitude = lat,
                longitude = lon,
                cityName = prefs[Keys.CITY],
                countryName = prefs[Keys.COUNTRY]
            )
        } else null
    }

    suspend fun save(location: StoredLocation) {
        dataStore.edit { prefs ->
            prefs[Keys.LAT] = location.latitude
            prefs[Keys.LON] = location.longitude
            if (location.cityName != null)
                prefs[Keys.CITY] = location.cityName
            else
                prefs.remove(Keys.CITY)
            if (location.countryName != null)
                prefs[Keys.COUNTRY] = location.countryName
            else
                prefs.remove(Keys.COUNTRY)
        }
    }

    suspend fun clear() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.LAT)
            prefs.remove(Keys.LON)
            prefs.remove(Keys.CITY)
            prefs.remove(Keys.COUNTRY)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\location\datasource\preferences\StoredLocation.kt

```kotlin
package com.mhq.salati.location.datasource.preferences

/** The saved place exactly as the DataStore keeps it. */
data class StoredLocation(
    val latitude: Double,
    val longitude: Double,
    val cityName: String?,
    val countryName: String?
)
```

## app\src\main\java\com\mhq\salati\location\di\LocationModule.kt

```kotlin
package com.mhq.salati.location.di

import android.content.Context
import com.mhq.salati.location.data.repoimpl.GeocoderProviderImpl
import com.mhq.salati.location.data.repoimpl.LocationProviderImpl
import com.mhq.salati.location.data.repoimpl.LocationRepoImpl
import com.mhq.salati.location.datasource.device.FusedGpsDataSource
import com.mhq.salati.location.datasource.device.GpsDataSource
import com.mhq.salati.location.datasource.network.service.KtorNominatimApi
import com.mhq.salati.location.datasource.network.service.NominatimApi
import com.mhq.salati.location.datasource.preferences.LocationDataStore
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.location.domain.repo.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        impl: LocationRepoImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindLocationProvider(
        impl: LocationProviderImpl
    ): LocationProvider

    @Binds
    @Singleton
    abstract fun bindGpsDataSource(
        impl: FusedGpsDataSource
    ): GpsDataSource

    @Binds
    @Singleton
    abstract fun bindGeocoderProvider(
        impl: GeocoderProviderImpl
    ): GeocoderProvider

    @Binds
    @Singleton
    abstract fun bindNominatimApi(
        impl: KtorNominatimApi
    ): NominatimApi

    companion object {
        @Provides
        @Singleton
        fun provideLocationDataStore(
            @ApplicationContext context: Context
        ): LocationDataStore = LocationDataStore(context)
    }
}
```

## app\src\main\java\com\mhq\salati\location\domain\model\CoordinatesLookup.kt

```kotlin
package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

/**
 * The steps of getting the device's position through GPS, in the order they happen.
 *
 * A lookup emits at most two states: one of the two blockers if a check fails up front,
 * otherwise [Locating] followed by exactly one outcome.
 */
sealed interface CoordinatesLookup {
    /** Location services are off; the user must enable them in system settings. */
    data object ServicesDisabled : CoordinatesLookup

    /** The location permission is not granted. */
    data object PermissionDenied : CoordinatesLookup

    /** The checks passed and the GPS fix is now being awaited. */
    data object Locating : CoordinatesLookup

    /** The GPS fix arrived. */
    data class Found(val coordinates: Coordinates) : CoordinatesLookup

    /** The lookup failed for a reason other than timeout or permission. */
    data class Failed(val message: String?) : CoordinatesLookup

    /** The GPS fix did not arrive within the timeout. */
    data object TimedOut : CoordinatesLookup
}
```

## app\src\main\java\com\mhq\salati\location\domain\model\GeocodeResult.kt

```kotlin
package com.mhq.salati.location.domain.model

/**
 * The outcome of a geocoding call, forward or reverse.
 *
 * [Found] may carry one or both names as null;
 * only a call where both are null counts as [NotFound] at the use-case level.
 */
sealed interface GeocodeResult {
    /** The place was resolved; either name may still be missing. */
    data class Found(val cityName: String?, val countryName: String?) : GeocodeResult

    /** The geo-coder answered but recognized no place. */
    data object NotFound : GeocodeResult

    /** The geo-coder itself errored; [throwable] carries the cause. */
    data class Failed(val throwable: Throwable) : GeocodeResult
}
```

## app\src\main\java\com\mhq\salati\location\domain\model\LocationPrerequisites.kt

```kotlin
package com.mhq.salati.location.domain.model

/**
 * What finding the user's place needs, each checked on its own.
 *
 * All three are evaluated independently
 * so the UI can show every missing one at once, instead of one at a time.
 *
 * @property hasInternet whether the device has a usable network connection
 * @property servicesEnabled whether location services (GPS) are on
 * @property permissionGranted whether the location permission is granted
 */
data class LocationPrerequisites(
    val hasInternet: Boolean,
    val servicesEnabled: Boolean,
    val permissionGranted: Boolean
) {
    /** True only when nothing is missing. */
    val areMet: Boolean
        get() = hasInternet && servicesEnabled && permissionGranted
}
```

## app\src\main\java\com\mhq\salati\location\domain\model\LocationResolution.kt

```kotlin
package com.mhq.salati.location.domain.model

/**
 * What happened when the app tried to find where the user is.
 *
 * This is the one-shot result of the full resolve flow: prerequisites, GPS fix, geocoding and save.
 * A [Resolved] location has already been persisted.
 */
sealed interface LocationResolution {
    /** The place was found and saved. */
    data class Resolved(val location: SavedLocation) : LocationResolution

    /** Something the lookup needs is missing (internet, location services or the permission). */
    data class Blocked(val prerequisites: LocationPrerequisites) : LocationResolution

    /** Coordinates were found but naming them failed. */
    data object GeocodingFailed : LocationResolution

    /** The GPS fix never arrived or the lookup crashed unexpectedly. */
    data object LocationUnavailable : LocationResolution
}
```

## app\src\main\java\com\mhq\salati\location\domain\model\LocationSearchResult.kt

```kotlin
package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

/**
 * One hit from searching a place by name.
 *
 * @property displayName the place's name as the geo-coder formatted it
 * @property coordinates the place's position, ready to save as a location
 */
data class LocationSearchResult(
    val displayName: String,
    val coordinates: Coordinates
)
```

## app\src\main\java\com\mhq\salati\location\domain\model\SavedLocation.kt

```kotlin
package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

/**
 * The location the app prays by: coordinates plus optional place names.
 *
 * Names may be absent right after a GPS-only resolve,
 * before geocoding or localization has filled them in;
 * in that case only [coordinates] are set.
 *
 * @property coordinates the position prayer times are calculated for
 * @property cityName the city part of the display name, when known
 * @property countryName the country part of the display name, when known
 */
data class SavedLocation(
    val coordinates: Coordinates,
    val cityName: String? = null,
    val countryName: String? = null
) {
    /** "City, Country" or whichever part exists;
     * null when the place has no name yet. */
    val displayName: String?
        get() = when {
            cityName != null && countryName != null -> "$cityName, $countryName"
            cityName != null -> cityName
            countryName != null -> countryName
            else -> null
        }
}
```

## app\src\main\java\com\mhq\salati\location\domain\repo\GeocoderProvider.kt

```kotlin
package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.shared.domain.Coordinates

/**
 * Place-name lookups, implemented in Data over the platform geo-coder or a web service.
 *
 * [acceptLanguage] is a BCP-47 language tag forwarded to the geo-coder
 * so names come back in the app's language; null means the geo-coder's default.
 */
interface GeocoderProvider {
    /**
     * Names the place at [coordinates].
     *
     * @return the names, [GeocodeResult.NotFound] when the place is unknown,
     *   or [GeocodeResult.Failed] when the lookup itself errors
     */
    suspend fun reverseGeocode(
        coordinates: Coordinates,
        acceptLanguage: String? = null
    ): GeocodeResult

    /**
     * Finds places matching [query].
     *
     * @return the hits, the best one first; empty when nothing matches
     */
    suspend fun searchByName(
        query: String,
        acceptLanguage: String? = null
    ): List<LocationSearchResult>
}
```

## app\src\main\java\com\mhq\salati\location\domain\repo\LocationProvider.kt

```kotlin
package com.mhq.salati.location.domain.repo

import com.mhq.salati.shared.domain.Coordinates

/**
 * The device's own positioning hardware, implemented in Data over the platform location APIs.
 *
 * Never touches the internet; network-side needs are checked separately by the connectivity layer.
 */
interface LocationProvider {
    /** Whether location services (GPS) are currently on. */
    fun isLocationEnabled(): Boolean

    /**
     * Waits for a GPS fix.
     *
     * @return the device's current position
     * @throws SecurityException if the location permission is not granted
     * @throws kotlinx.coroutines.TimeoutCancellationException if the caller applies a timeout
     */
    suspend fun getCurrentLocation(): Coordinates
}
```

## app\src\main\java\com\mhq\salati\location\domain\repo\LocationRepository.kt

```kotlin
package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.model.SavedLocation
import kotlinx.coroutines.flow.Flow

/**
 * The app's saved prayer location, implemented in Data over persistent storage.
 *
 * A null [savedLocation] means no place is saved and lookups should use the device position.
 */
interface LocationRepository {
    /** The saved place, or null when none is saved; re-emits on every change. */
    val savedLocation: Flow<SavedLocation?>

    /** Stores [location] as the place the app prays by, replacing the previous one. */
    suspend fun saveManualLocation(location: SavedLocation)

    /** Forgets the saved place; [savedLocation] emits null afterwards. */
    suspend fun clearSavedLocation()
}
```

## app\src\main\java\com\mhq\salati\location\domain\service\LocationLocalizer.kt

```kotlin
package com.mhq.salati.location.domain.service

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.usecases.GetLocalizedLocationUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

/** Hard cap on one geocoding lookup; past it the location is left as it is. */
private val LOOKUP_TIMEOUT = 5_000L.milliseconds

/**
 * Puts a place's city and country names into the app language and stores them.
 * One lookup at a time for the app, and none repeated for a place already done in this language.
 *
 * A single [Mutex] serializes all lookups app-wide, and the last successful lookup is remembered
 * so re-checking the same place in the same language is free.
 * Offline, slow, or not-found lookups return the location unchanged rather than erroring.
 */
@Singleton
class LocationLocalizer @Inject constructor(
    private val getLocalizedLocation: GetLocalizedLocationUseCase,
    private val getSavedLocation: GetSavedLocationUseCase,
    private val saveManualLocation: SaveManualLocationUseCase,
    private val observeSettings: ObserveSettingsUseCase,
    private val connectivityChecker: ConnectivityChecker
) {
    private val lookupLock = Mutex()
    private var lastLookup: Lookup? = null

    /** What the memoised result is valid for: the exact place and language it was looked up in. */
    private data class Lookup(
        val coordinates: Coordinates,
        val languageCode: String,
        val result: SavedLocation
    )

    /**
     * [location] with localized names (stored when they changed), or unchanged when offline,
     * the lookup is slow, or nothing is found.
     */
    suspend fun localizeAndSave(location: SavedLocation): SavedLocation = lookupLock.withLock {
        val languageCode = observeSettings().first().language.code

        lastLookup
            ?.takeIf { it.coordinates == location.coordinates && it.languageCode == languageCode }
            ?.let { return@withLock it.result }

        if (!connectivityChecker.isConnected()) return@withLock location

        val localized = withTimeoutOrNull(LOOKUP_TIMEOUT) {
            getLocalizedLocation(location, languageCode)
        } ?: return@withLock location

        lastLookup = Lookup(location.coordinates, languageCode, localized)
        if (localized != location) saveManualLocation(localized)
        localized
    }

    /** The saved location, re-localized each time the app language changes
     * (the initial value is skipped). */
    fun observeLanguageChanges(): Flow<SavedLocation> =
        observeSettings()
            .map { it.language.code }
            .distinctUntilChanged()
            .drop(1)
            .mapNotNull {
                getSavedLocation().first()?.let { saved ->
                    localizeAndSave(saved)
                }
            }
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\CheckLocationPrerequisitesUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import javax.inject.Inject

/**
 * Checks everything an automatic location lookup needs, in one pass:
 * internet, location services and the location permission.
 *
 * Evaluates all three conditions; nothing stops at the first one that fails.
 */
class CheckLocationPrerequisitesUseCase @Inject constructor(
    private val connectivityChecker: ConnectivityChecker,
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker
) {
    /** @return the three checks, each independently true or false */
    suspend operator fun invoke(): LocationPrerequisites = LocationPrerequisites(
        hasInternet = connectivityChecker.isConnected(),
        servicesEnabled = locationProvider.isLocationEnabled(),
        permissionGranted = permissionChecker.hasLocationPermission()
    )
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\ClearSavedLocationUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.repo.LocationRepository
import javax.inject.Inject

/**
 * Forgets the saved place, so the next lookup uses the device location again.
 *
 * Thin pass-through over [LocationRepository.clearSavedLocation].
 */
class ClearSavedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke() {
        locationRepository.clearSavedLocation()
    }
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\GetCurrentCoordinatesUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/** Hard cap on waiting for the GPS fix. */
private val GPS_TIMEOUT = 5_000L.milliseconds

/**
 * The device's position as a short one-shot flow of [CoordinatesLookup] states.
 *
 * Needs location services and permission, never the internet.
 * Ends with the GPS fix or the reason there isn't one.
 * Emits a blocker immediately when a check fails,
 * otherwise [CoordinatesLookup.Locating] and then exactly one outcome within [GPS_TIMEOUT].
 */
class GetCurrentCoordinatesUseCase @Inject constructor(
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker
) {
    /** @return a flow ending in [CoordinatesLookup.Found] or one of the failure states */
    operator fun invoke(): Flow<CoordinatesLookup> = flow {
        if (!locationProvider.isLocationEnabled()) {
            emit(CoordinatesLookup.ServicesDisabled)
            return@flow
        }
        if (!permissionChecker.hasLocationPermission()) {
            emit(CoordinatesLookup.PermissionDenied)
            return@flow
        }

        emit(CoordinatesLookup.Locating)
        emit(currentLocation())
    }

    /** Maps a failed fix to its lookup state; cancellation always propagates. */
    private suspend fun currentLocation(): CoordinatesLookup = try {
        CoordinatesLookup.Found(
            withTimeout(GPS_TIMEOUT) { locationProvider.getCurrentLocation() }
        )
    } catch (e: TimeoutCancellationException) {
        CoordinatesLookup.TimedOut
    } catch (e: SecurityException) {
        CoordinatesLookup.PermissionDenied
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        CoordinatesLookup.Failed(e.message)
    }
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\GetLocalizedLocationUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.SavedLocation
import javax.inject.Inject

/**
 * The same place with its city and country names in [languageCode],
 * or null when the names can't be looked up.
 *
 * A lookup that succeeds but finds no names at all counts as a failure,
 * so callers never store an empty-looking "localization" over existing names.
 */
class GetLocalizedLocationUseCase @Inject constructor(
    private val reverseGeocodeLocation: ReverseGeocodeLocationUseCase
) {
    /**
     * @param savedLocation the place to name
     * @param languageCode the BCP-47 language to request names in
     * @return the place with new names, or null when the geocode failed,
     *   found nothing, or returned no names
     */
    suspend operator fun invoke(
        savedLocation: SavedLocation,
        languageCode: String
    ): SavedLocation? {
        val found = reverseGeocodeLocation(
            savedLocation.coordinates,
            languageCode
        ) as? GeocodeResult.Found ?: return null

        if (found.cityName == null && found.countryName == null)
            return null

        return savedLocation.copy(
            cityName = found.cityName,
            countryName = found.countryName
        )
    }
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\GetSavedLocationUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * The app's saved prayer location, live.
 *
 * Emits null when no place is saved;
 * re-emits whenever the saved place changes (including localization updates).
 */
class GetSavedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<SavedLocation?> =
        locationRepository.savedLocation
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\ResolveLocationUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.LocationResolution
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject

/**
 * The full automatic "find where the user is" flow, as a one-shot [LocationResolution].
 *
 * Steps, in order:
 * check prerequisites (returning [LocationResolution.Blocked] with every missing one if any fail),
 * take the last GPS lookup state (re-mapping blockers onto the prerequisites so UI shows the fix),
 * then geocode the coordinates in the app language and save the place.
 * Coordinates are saved even when the geo-coder recognizes no place;
 * only a geo-coder error stops the save. Cancellation always propagates.
 */
class ResolveLocationUseCase @Inject constructor(
    private val checkLocationPrerequisitesUseCase: CheckLocationPrerequisitesUseCase,
    private val getCurrentCoordinatesUseCase: GetCurrentCoordinatesUseCase,
    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase,
    private val saveManualLocationUseCase: SaveManualLocationUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase
) {
    /** @return what happened; a [LocationResolution.Resolved] place is already saved */
    suspend operator fun invoke(): LocationResolution {
        val prerequisites = checkLocationPrerequisitesUseCase()
        if (!prerequisites.areMet) return LocationResolution.Blocked(prerequisites)

        val coordinates = when (val lookup = getCurrentCoordinatesUseCase().last()) {
            is CoordinatesLookup.Found -> lookup.coordinates
            is CoordinatesLookup.ServicesDisabled ->
                return LocationResolution.Blocked(prerequisites.copy(servicesEnabled = false))
            is CoordinatesLookup.PermissionDenied ->
                return LocationResolution.Blocked(prerequisites.copy(permissionGranted = false))
            is CoordinatesLookup.TimedOut,
            is CoordinatesLookup.Failed,
            is CoordinatesLookup.Locating -> return LocationResolution.LocationUnavailable
        }

        return try {
            val lang = observeSettingsUseCase().first().language.code
            when (val geocode = reverseGeocodeLocationUseCase(coordinates, lang)) {
                is GeocodeResult.Found -> {
                    val location = SavedLocation(coordinates, geocode.cityName, geocode.countryName)
                    saveManualLocationUseCase(location)
                    LocationResolution.Resolved(location)
                }

                is GeocodeResult.NotFound -> {
                    val location = SavedLocation(coordinates)
                    saveManualLocationUseCase(location)
                    LocationResolution.Resolved(location)
                }

                is GeocodeResult.Failed -> LocationResolution.GeocodingFailed
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            LocationResolution.LocationUnavailable
        }
    }
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\ReverseGeocodeLocationUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.shared.domain.Coordinates
import javax.inject.Inject

/**
 * Names the place at [coordinates].
 *
 * Thin pass-through over [GeocoderProvider.reverseGeocode].
 */
class ReverseGeocodeLocationUseCase @Inject constructor(
    private val geocoderProvider: GeocoderProvider
) {
    /**
     * @param coordinates the position to name
     * @param acceptLanguage BCP-47 language tag for the returned names, or null for the default
     * @return the names, not-found, or the lookup failure
     */
    suspend operator fun invoke(
        coordinates: Coordinates,
        acceptLanguage: String? = null
    ): GeocodeResult = geocoderProvider.reverseGeocode(coordinates, acceptLanguage)
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\SaveManualLocationUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import javax.inject.Inject

/**
 * Stores [location] as the place the app prays by.
 *
 * Used both for genuinely manual picks (search, map) and for saving the automatic lookup's result,
 * which is why the name says "manual" but the callers are broader.
 */
class SaveManualLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    /** @param location the place to save, replacing any previous one */
    suspend operator fun invoke(location: SavedLocation) {
        locationRepository.saveManualLocation(location)
    }
}
```

## app\src\main\java\com\mhq\salati\location\domain\usecases\SearchLocationByNameUseCase.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import javax.inject.Inject

/**
 * Finds places matching a name, for the manual location picker.
 *
 * Blank queries are short-circuited to an empty list
 * so the geo-coder is never called with nothing to look for.
 */
class SearchLocationByNameUseCase @Inject constructor(
    private val geocoderProvider: GeocoderProvider
) {
    /**
     * @param query the text to search for
     * @param acceptLanguage BCP-47 language tag for the returned names, or null for the default
     * @return the hits, the best one first; empty for a blank query or no matches
     */
    suspend operator fun invoke(
        query: String,
        acceptLanguage: String? = null
    ): List<LocationSearchResult> {
        if (query.isBlank()) return emptyList()
        return geocoderProvider.searchByName(query, acceptLanguage)
    }
}
```

## app\src\main\java\com\mhq\salati\location\presentation\handlers\LocationPrerequisitesHandler.kt

```kotlin
package com.mhq.salati.location.presentation.handlers

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.usecases.CheckLocationPrerequisitesUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.presentation.mapper.toPrerequisitesUi
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.permissions.domain.usecases.MarkLocationPermissionRequestedUseCase
import com.mhq.salati.permissions.domain.usecases.WasLocationPermissionRequestedUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * What Home and Qibla both need to know about a blocked lookup. [showRationale] comes from the UI
 * (only it can ask the system): false after the system stopped showing the dialog.
 */
class LocationPrerequisitesHandler @Inject constructor(
    private val checkLocationPrerequisitesUseCase: CheckLocationPrerequisitesUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val wasLocationPermissionRequestedUseCase: WasLocationPermissionRequestedUseCase,
    private val markLocationPermissionRequestedUseCase: MarkLocationPermissionRequestedUseCase
) {
    /** What the blocked screen shows now, or null when nothing stands in the way (a saved place counts). */
    suspend fun blocked(showRationale: Boolean): PrerequisitesUi? {
        if (getSavedLocationUseCase().first() != null) return null
        val prerequisites = checkLocationPrerequisitesUseCase()
        return if (prerequisites.areMet) null else toUi(prerequisites, showRationale)
    }

    suspend fun toUi(prerequisites: LocationPrerequisites, showRationale: Boolean): PrerequisitesUi =
        prerequisites.toPrerequisitesUi(
            permissionPermanentlyDenied = !prerequisites.permissionGranted &&
                    wasLocationPermissionRequestedUseCase() &&
                    !showRationale
        )

    /** Remembers that the system dialog was asked for, so a later silent refusal reads as permanent. */
    suspend fun onPermissionRequested() = markLocationPermissionRequestedUseCase()
}
```

## app\src\main\java\com\mhq\salati\location\presentation\mapper\PrerequisitesMapper.kt

```kotlin
package com.mhq.salati.location.presentation.mapper

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.presentation.model.PrerequisitesUi

fun LocationPrerequisites.toPrerequisitesUi(permissionPermanentlyDenied: Boolean) = PrerequisitesUi(
    hasInternet = hasInternet,
    servicesEnabled = servicesEnabled,
    permissionGranted = permissionGranted,
    permissionPermanentlyDenied = permissionPermanentlyDenied && !permissionGranted
)
```

## app\src\main\java\com\mhq\salati\location\presentation\model\PrerequisitesUi.kt

```kotlin
package com.mhq.salati.location.presentation.model

data class PrerequisitesUi(
    val hasInternet: Boolean,
    val servicesEnabled: Boolean,
    val permissionGranted: Boolean,
    /** Asking again is pointless; only app settings can fix it. */
    val permissionPermanentlyDenied: Boolean
)
```

## app\src\main\java\com\mhq\salati\location\ui\components\PrerequisiteRow.kt

```kotlin
package com.mhq.salati.location.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.mhq.salati.shared.ui.components.ActionRow
import com.mhq.salati.shared.ui.components.GroupDivider

/** One missing requirement; it animates away as soon as it is fixed. */
@Composable
fun PrerequisiteRow(
    isMissing: Boolean,
    showDivider: Boolean,
    icon: ImageVector,
    title: String,
    actionLabel: String,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isMissing,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column {
            ActionRow(
                icon = icon,
                title = title,
                valueLabel = actionLabel,
                onClick = onClick
            )
            if (showDivider) GroupDivider()
        }
    }
}
```

## app\src\main\java\com\mhq\salati\location\ui\components\PrerequisitesError.kt

```kotlin
package com.mhq.salati.location.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.StepBadgeIcon
import com.mhq.salati.shared.ui.components.StepHeader
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.prayerGradient

/**
 * Everything still missing, in one place; rows leave by themselves as each one is fixed.
 * The user can always skip it by picking a place by hand.
 *
 * Fills the whole tab, behind the floating bottom bar; the content clears the bar itself.
 */
@Composable
fun PrerequisitesError(
    prerequisites: PrerequisitesUi,
    onOpenInternetSettings: () -> Unit,
    onOpenLocationSettings: () -> Unit,
    onAllowPermission: () -> Unit,
    onOpenAppSettings: () -> Unit,
    onChooseLocationManually: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPermissionBlocked = prerequisites.permissionPermanentlyDenied

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.prayerGradient)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(bottom = BottomNavBarDefaults.Height)
                .padding(horizontal = 32.dp)
        ) {
            StepHeader(
                title = stringResource(R.string.prerequisites_title),
                subtitle = stringResource(R.string.prerequisites_subtitle)
            ) {
                StepBadgeIcon(Icons.Default.LocationOn)
            }
            Spacer(Modifier.height(28.dp))

            GroupCard {
                PrerequisiteRow(
                    icon = Icons.Default.Wifi,
                    title = stringResource(R.string.prerequisite_internet),
                    actionLabel = stringResource(R.string.prerequisite_internet_action),
                    isMissing = !prerequisites.hasInternet,
                    showDivider = !prerequisites.servicesEnabled || !prerequisites.permissionGranted,
                    onClick = onOpenInternetSettings
                )
                PrerequisiteRow(
                    icon = Icons.Default.MyLocation,
                    title = stringResource(R.string.prerequisite_services),
                    actionLabel = stringResource(R.string.action_open_settings),
                    isMissing = !prerequisites.servicesEnabled,
                    showDivider = !prerequisites.permissionGranted,
                    onClick = onOpenLocationSettings
                )
                PrerequisiteRow(
                    icon = Icons.Default.Lock,
                    title = stringResource(R.string.prerequisite_permission),
                    actionLabel = stringResource(
                        if (isPermissionBlocked) {
                            R.string.action_open_settings
                        } else {
                            R.string.prerequisite_action_allow
                        }
                    ),
                    isMissing = !prerequisites.permissionGranted,
                    showDivider = false,
                    onClick = if (isPermissionBlocked) onOpenAppSettings else onAllowPermission
                )
            }

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = onChooseLocationManually,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(text = stringResource(R.string.prerequisite_action_choose_manually))
            }
        }
    }
}

@Preview(name = "All Missing", showBackground = true)
@Composable
private fun PrerequisitesErrorPreview() {
    SalatiTheme {
        PrerequisitesError(
            prerequisites = PrerequisitesUi(
                hasInternet = false,
                servicesEnabled = false,
                permissionGranted = false,
                permissionPermanentlyDenied = false
            ),
            onOpenInternetSettings = {},
            onOpenLocationSettings = {},
            onAllowPermission = {},
            onOpenAppSettings = {},
            onChooseLocationManually = {}
        )
    }
}

@Preview(name = "Blocked Permission", showBackground = true)
@Composable
private fun PrerequisitesErrorBlockedPreview() {
    SalatiTheme {
        PrerequisitesError(
            prerequisites = PrerequisitesUi(
                hasInternet = true,
                servicesEnabled = true,
                permissionGranted = false,
                permissionPermanentlyDenied = true
            ),
            onOpenInternetSettings = {},
            onOpenLocationSettings = {},
            onAllowPermission = {},
            onOpenAppSettings = {},
            onChooseLocationManually = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\location\ui\LocationPrerequisitesActions.kt

```kotlin
package com.mhq.salati.location.ui

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.mhq.salati.permissions.ui.rememberLocationPermissionLauncher

/** What the blocked screen's buttons do on the device; Home and Qibla share it. */
class LocationPrerequisitesActions internal constructor(
    private val context: Context,
    private val permissionLauncher: ActivityResultLauncher<String>
) {
    fun requestPermission() {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivitySafely(intent)
    }

    fun openLocationSettings() {
        context.startActivitySafely(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    fun openInternetSettings() {
        val panel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
        } else {
            Intent(Settings.ACTION_WIRELESS_SETTINGS)
        }
        context.startActivitySafely(panel, fallback = Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
}

@Composable
fun rememberLocationPrerequisitesActions(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (showRationale: Boolean) -> Unit
): LocationPrerequisitesActions {
    val context = LocalContext.current
    val permissionLauncher = rememberLocationPermissionLauncher(
        onGranted = onPermissionGranted,
        onDenied = onPermissionDenied
    )
    return remember(context, permissionLauncher) {
        LocationPrerequisitesActions(context, permissionLauncher)
    }
}

private fun Context.startActivitySafely(intent: Intent, fallback: Intent? = null) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        fallback?.let { runCatching { startActivity(it) } }
    }
}
```

## app\src\main\java\com\mhq\salati\location\ui\LocationPrerequisitesObserver.kt

```kotlin
package com.mhq.salati.location.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.mhq.salati.connectivity.ui.components.rememberNetworkConnected
import com.mhq.salati.permissions.ui.rememberLocationServicesEnabled

/** Reports the two things the blocked screen waits for that change outside the app. */
@Composable
fun LocationPrerequisitesObserver(
    onServicesChanged: (enabled: Boolean) -> Unit,
    onConnectivityChanged: (connected: Boolean) -> Unit
) {
    val servicesEnabled by rememberLocationServicesEnabled()
    val isConnected by rememberNetworkConnected()

    LaunchedEffect(servicesEnabled) { onServicesChanged(servicesEnabled) }
    LaunchedEffect(isConnected) { onConnectivityChanged(isConnected) }
}
```

## app\src\main\java\com\mhq\salati\locationpicker\presentation\contract\LocationPickerContract.kt

```kotlin
package com.mhq.salati.locationpicker.presentation.contract

import com.mhq.salati.shared.presentation.model.MessageUi

object LocationPickerContract {

    data class State(
        val query: String = "",
        val isSearching: Boolean = false,
        val searchResults: List<LocationSearchResult> = emptyList(),
        val selectedLocation: SelectedLocation? = null,
        val isResolvingSelection: Boolean = false,
        val errorMessage: MessageUi? = null,
        val mapCenterLat: Double = 21.4225,
        val mapCenterLng: Double = 39.8262,
        val mapZoom: Int = 5
    )

    data class LocationSearchResult(
        val displayName: String,
        val latitude: Double,
        val longitude: Double
    )

    data class SelectedLocation(
        val displayName: String?,
        val latitude: Double,
        val longitude: Double
    )

    sealed interface Intent {
        data class QueryChanged(val query: String) : Intent
        data object SearchSubmitted : Intent
        data class SearchResultSelected(val result: LocationSearchResult) : Intent
        data class MapPointSelected(val latitude: Double, val longitude: Double) : Intent
        data object LocationConfirmed : Intent
        data object CurrentLocationRequested : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
        data object LocationSaved : Effect
        data object CurrentLocationChosen : Effect
    }
}
```

## app\src\main\java\com\mhq\salati\locationpicker\presentation\viewmodel\LocationPickerViewModel.kt

```kotlin
package com.mhq.salati.locationpicker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.usecases.ClearSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.location.domain.usecases.SearchLocationByNameUseCase
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LocationPickerViewModel @Inject constructor(
    private val searchLocationByNameUseCase: SearchLocationByNameUseCase,
    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase,
    private val saveManualLocationUseCase: SaveManualLocationUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val clearSavedLocation: ClearSavedLocationUseCase,
    private val clock: Clock
) : ViewModel() {

    private val _state = MutableStateFlow(LocationPickerContract.State())
    val state: StateFlow<LocationPickerContract.State> = _state.asStateFlow()

    private val _effect = Channel<LocationPickerContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val immediateSearchFlow = MutableSharedFlow<String>()
    private var lastManualSearchTimeMs = 0L
    private val SEARCH_COOLDOWN_MS = 1000L
    private var reverseGeocodeJob: Job? = null

    init {
        observeSearchQuery()
    }

    fun onIntent(intent: LocationPickerContract.Intent) {
        when (intent) {
            is LocationPickerContract.Intent.QueryChanged -> onQueryChanged(intent.query)
            is LocationPickerContract.Intent.SearchSubmitted -> executeManualSearch(_state.value.query)
            is LocationPickerContract.Intent.SearchResultSelected -> selectResult(intent.result)
            is LocationPickerContract.Intent.MapPointSelected -> selectMapPoint(intent.latitude, intent.longitude)
            is LocationPickerContract.Intent.LocationConfirmed -> confirmSelection()
            is LocationPickerContract.Intent.CurrentLocationRequested -> useCurrentLocation()
        }
    }

    private fun onQueryChanged(query: String) {
        // Typing never triggers a request; the user searches explicitly.
        _state.update {
            it.copy(
                query = query,
                searchResults = emptyList(),
                isSearching = false,
                errorMessage = null
            )
        }
    }

    private fun observeSearchQuery() {
        immediateSearchFlow
            .flatMapLatest { query ->
                flow {
                    val lang = currentLanguageCode()
                    emit(Result.success(searchLocationByNameUseCase(query, lang)))
                }.catch { e ->
                    if (e is CancellationException) throw e
                    emit(Result.failure(e))
                }
            }
            .onEach { result ->
                result.fold(
                    onSuccess = { results ->
                        _state.update {
                            it.copy(
                                isSearching = false,
                                searchResults = results.toContractResults()
                            )
                        }
                    },
                    onFailure = {
                        val message = MessageUi.SEARCH_FAILED_CHECK_CONNECTION
                        _state.update {
                            it.copy(
                                isSearching = false,
                                errorMessage = message
                            )
                        }
                        _effect.send(LocationPickerContract.Effect.ShowError(message))
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    private fun executeManualSearch(query: String) {
        if (query.isBlank()) return

        val currentTime = clock.instant().toEpochMilli()

        // Nominatim's usage policy allows at most one request per second.
        if (currentTime - lastManualSearchTimeMs < SEARCH_COOLDOWN_MS) {
            return
        }

        lastManualSearchTimeMs = currentTime
        _state.update { it.copy(isSearching = true, errorMessage = null) }
        viewModelScope.launch {
            immediateSearchFlow.emit(query)
        }
    }

    private fun selectResult(result: LocationPickerContract.LocationSearchResult) {
        _state.update {
            it.copy(
                selectedLocation = LocationPickerContract.SelectedLocation(
                    displayName = result.displayName,
                    latitude = result.latitude,
                    longitude = result.longitude
                ),
                searchResults = emptyList(),
                query = result.displayName,
                mapCenterLat = result.latitude,
                mapCenterLng = result.longitude,
                mapZoom = 12
            )
        }
    }

    private fun selectMapPoint(latitude: Double, longitude: Double) {
        reverseGeocodeJob?.cancel()
        reverseGeocodeJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isResolvingSelection = true,
                    selectedLocation = LocationPickerContract.SelectedLocation(
                        displayName = null,
                        latitude = latitude,
                        longitude = longitude
                    ),
                    mapCenterLat = latitude,
                    mapCenterLng = longitude,
                    mapZoom = 12
                )
            }
            val lang = currentLanguageCode()
            when (val result = reverseGeocodeLocationUseCase(Coordinates(latitude, normalizeLongitude(longitude)), lang)) {
                is GeocodeResult.Found -> {
                    val name = listOfNotNull(result.cityName, result.countryName)
                        .joinToString(", ")
                        .ifBlank { null }
                    _state.update {
                        it.copy(
                            isResolvingSelection = false,
                            selectedLocation = LocationPickerContract.SelectedLocation(
                                displayName = name,
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }
                }

                is GeocodeResult.NotFound -> {
                    _state.update {
                        it.copy(
                            isResolvingSelection = false,
                            selectedLocation = LocationPickerContract.SelectedLocation(
                                displayName = null,
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }
                }

                is GeocodeResult.Failed -> {
                    _state.update { it.copy(isResolvingSelection = false) }
                    _effect.send(
                        LocationPickerContract.Effect.ShowError(
                            MessageUi.COULDNT_DETERMINE_LOCATION_NAME
                        )
                    )
                }
            }
        }
    }

    private fun confirmSelection() {
        val selected = _state.value.selectedLocation ?: return
        viewModelScope.launch {
            try {
                val (city, country) = parseDisplayName(selected.displayName)
                saveManualLocationUseCase(
                    SavedLocation(
                        coordinates = Coordinates(selected.latitude, normalizeLongitude(selected.longitude)),
                        cityName = city,
                        countryName = country
                    )
                )
                _effect.send(LocationPickerContract.Effect.LocationSaved)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(
                    LocationPickerContract.Effect.ShowError(
                        MessageUi.COULDNT_SAVE_LOCATION
                    )
                )
            }
        }
    }

    /** Forgets the saved place; the screen we return to looks the device location up again. */
    private fun useCurrentLocation() {
        viewModelScope.launch {
            clearSavedLocation()
            _effect.send(LocationPickerContract.Effect.CurrentLocationChosen)
        }
    }

    // Panning the map around the world can push longitude past ±180°; Coordinates only accepts values inside that range.
    private fun normalizeLongitude(longitude: Double): Double = ((longitude + 540.0) % 360.0) - 180.0

    private fun parseDisplayName(displayName: String?): Pair<String?, String?> {
        if (displayName == null) return null to null
        val parts = displayName.split(", ", limit = 2)
        return when (parts.size) {
            2 -> parts[0] to parts[1]
            1 -> parts[0] to null
            else -> null to null
        }
    }

    private suspend fun currentLanguageCode(): String {
        return observeSettingsUseCase().first().language.code
    }

    private fun List<LocationSearchResult>.toContractResults(): List<LocationPickerContract.LocationSearchResult> =
        map { result ->
            LocationPickerContract.LocationSearchResult(
                displayName = result.displayName,
                latitude = result.coordinates.latitude,
                longitude = result.coordinates.longitude
            )
        }
}
```

## app\src\main\java\com\mhq\salati\locationpicker\ui\screens\LocationPickerContainer.kt

```kotlin
package com.mhq.salati.locationpicker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract
import com.mhq.salati.locationpicker.presentation.viewmodel.LocationPickerViewModel
import com.mhq.salati.shared.ui.asString
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import kotlinx.coroutines.launch

@Composable
fun LocationPickerContainer(
    onLocationChosen: () -> Unit,
    onBackClicked: () -> Unit,
    locationPickerViewModel: LocationPickerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by locationPickerViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        locationPickerViewModel.effect.collect { effect ->
            when (effect) {
                is LocationPickerContract.Effect.ShowError -> {
                    scope.launch { snackbarHostState.showSnackbar(effect.message.asString(context)) }
                }

                is LocationPickerContract.Effect.LocationSaved,
                is LocationPickerContract.Effect.CurrentLocationChosen -> onLocationChosen()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LocationPickerContent(
            state = state,
            onIntent = locationPickerViewModel::onIntent,
            onBackClicked = onBackClicked
        )
    }
}
```

## app\src\main\java\com\mhq\salati\locationpicker\ui\screens\LocationPickerContent.kt

```kotlin
package com.mhq.salati.locationpicker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract.Intent
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract.State
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun LocationPickerContent(
    state: State,
    onIntent: (Intent) -> Unit,
    onBackClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LocationPickerMap(
            initialLat = state.mapCenterLat,
            initialLng = state.mapCenterLng,
            initialZoom = state.mapZoom,
            selectedLatitude = state.selectedLocation?.latitude,
            selectedLongitude = state.selectedLocation?.longitude,
            onPointSelected = { lat, lng ->
                onIntent(Intent.MapPointSelected(lat, lng))
            },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                shadowElevation = 6.dp,
                tonalElevation = 2.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 4.dp),
                ) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (state.query.isEmpty()) {
                            Text(
                                text = stringResource(R.string.location_picker_search_hint),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                        TextField(
                            value = state.query,
                            onValueChange = { onIntent(Intent.QueryChanged(it)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { onIntent(Intent.SearchSubmitted) }),
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                disabledContainerColor = MaterialTheme.colorScheme.surface,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    IconButton(onClick = { onIntent(Intent.SearchSubmitted) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.cd_search),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onIntent(Intent.CurrentLocationRequested) }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.location_picker_use_current_location),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            if (state.isSearching) {
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (state.searchResults.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    LazyColumn(modifier = Modifier.heightIn(max = 240.dp)) {
                        items(state.searchResults) { result ->
                            ListItem(
                                headlineContent = { Text(result.displayName) },
                                modifier = Modifier.clickable {
                                    onIntent(Intent.SearchResultSelected(result))
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }

        Button(
            onClick = { onIntent(Intent.LocationConfirmed) },
            enabled = state.selectedLocation != null && !state.isResolvingSelection,
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            Text(
                if (state.isResolvingSelection) stringResource(R.string.location_picker_resolving)
                else state.selectedLocation?.displayName ?: stringResource(R.string.location_picker_confirm)
            )
        }
    }
}

@Preview(name = "Default State", showBackground = true)
@Composable
private fun LocationPickerContentDefaultPreview() {
    SalatiTheme {
        LocationPickerContent(
            state = State(),
            onIntent = {},
            onBackClicked = {}
        )
    }
}

@Preview(name = "Searching State", showBackground = true)
@Composable
private fun LocationPickerContentSearchingPreview() {
    SalatiTheme {
        LocationPickerContent(
            state = State(
                query = "Makkah",
                isSearching = true
            ),
            onIntent = {},
            onBackClicked = {}
        )
    }
}

@Preview(name = "Results State", showBackground = true)
@Composable
private fun LocationPickerContentResultsPreview() {
    SalatiTheme {
        LocationPickerContent(
            state = State(
                query = "Mecca",
                searchResults = listOf(
                    LocationPickerContract.LocationSearchResult("Grand Mosque, Makkah", 21.4225, 39.8262),
                    LocationPickerContract.LocationSearchResult("Makkah Mall", 21.3891, 39.8812)
                )
            ),
            onIntent = {},
            onBackClicked = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\locationpicker\ui\screens\LocationPickerMap.kt

```kotlin
package com.mhq.salati.locationpicker.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.mhq.salati.shared.ui.theme.SalatiTheme
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/** Users have no reason to zoom out past country/continent level to pick a prayer location. */
private const val MIN_ZOOM_LEVEL = 3.0

@SuppressLint("ClickableViewAccessibility")
@Composable
fun LocationPickerMap(
    initialLat: Double,
    initialLng: Double,
    selectedLatitude: Double?,
    selectedLongitude: Double?,
    initialZoom: Int = 15,
    onPointSelected: (Double, Double) -> Unit,
    modifier: Modifier = Modifier
) {

    if (LocalInspectionMode.current) {
        Box(
            modifier = modifier.background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("OSM Map Placeholder (Preview Mode)")
        }
        return
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance()
            .load(
                context,
                context.getSharedPreferences(
                    "osmdroid",
                    Context.MODE_PRIVATE
                )
            )

        Configuration.getInstance().userAgentValue =
            "Salati/1.0 (${context.packageName}; https://github.com/mhq93/salati)"
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                setHorizontalMapRepetitionEnabled(false)
                setVerticalMapRepetitionEnabled(false)
                setScrollableAreaLimitDouble(
                    BoundingBox(
                        MapView.getTileSystem().maxLatitude,
                        MapView.getTileSystem().maxLongitude,
                        MapView.getTileSystem().minLatitude,
                        MapView.getTileSystem().minLongitude
                    )
                )
                minZoomLevel = MIN_ZOOM_LEVEL

                controller.setZoom(initialZoom.toDouble())
                controller.setCenter(GeoPoint(initialLat, initialLng))

                val marker = Marker(this).apply {
                    position = GeoPoint(initialLat, initialLng)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                overlays.add(marker)

                val gestureDetector = GestureDetector(
                    ctx,
                    object : GestureDetector.SimpleOnGestureListener() {
                        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                            val geoPoint =
                                projection.fromPixels(e.x.toInt(), e.y.toInt()) as GeoPoint
                            marker.position = geoPoint
                            onPointSelected(geoPoint.latitude, geoPoint.longitude)
                            return true
                        }
                    }
                )
                setOnTouchListener { _, event ->
                    gestureDetector.onTouchEvent(event)
                    false
                }
            }
        },
        update = { mapView ->
            val targetLat = selectedLatitude ?: initialLat
            val targetLng = selectedLongitude ?: initialLng
            val targetPoint = GeoPoint(targetLat, targetLng)
            mapView.controller.animateTo(targetPoint)
            val marker = mapView.overlays.find { it is Marker } as? Marker
            marker?.position = targetPoint
        },
        onRelease = { mapView ->
            mapView.onDetach()
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun LocationPickerMapPreview() {
    SalatiTheme() {
        LocationPickerMap(
            initialLat = 21.4225,
            initialLng = 39.8262,
            selectedLatitude = null,
            selectedLongitude = null,
            onPointSelected = { _, _ -> }
        )
    }
}
```

## app\src\main\java\com\mhq\salati\onboarding\data\repoimpl\OnboardingRepoImpl.kt

```kotlin
package com.mhq.salati.onboarding.data.repoimpl

import com.mhq.salati.onboarding.datasource.preferences.OnboardingDataStore
import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import javax.inject.Inject

class OnboardingRepoImpl @Inject constructor(
    private val dataStore: OnboardingDataStore
) : OnboardingRepository {

    override suspend fun hasCompletedOnboarding(): Boolean {
        return dataStore.hasCompletedOnboarding()
    }

    override suspend fun setOnboardingCompleted() {
        dataStore.setOnboardingCompleted(true)
    }
}
```

## app\src\main\java\com\mhq\salati\onboarding\datasource\preferences\OnboardingDataStore.kt

```kotlin
package com.mhq.salati.onboarding.datasource.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding_prefs")

@Singleton
class OnboardingDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.onboardingDataStore.edit { prefs ->
            prefs[ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun hasCompletedOnboarding(): Boolean {
        return context.onboardingDataStore.data.first()[ONBOARDING_COMPLETED] ?: false
    }
}
```

## app\src\main\java\com\mhq\salati\onboarding\di\OnboardingModule.kt

```kotlin
package com.mhq.salati.onboarding.di

import com.mhq.salati.onboarding.data.repoimpl.OnboardingRepoImpl
import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingModule {

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(
        impl: OnboardingRepoImpl
    ): OnboardingRepository
}
```

## app\src\main\java\com\mhq\salati\onboarding\domain\model\OnboardingPermissions.kt

```kotlin
package com.mhq.salati.onboarding.domain.model

/** What the user has already granted, as far as onboarding cares. */
data class OnboardingPermissions(
    val hasNotification: Boolean,
    val hasExactAlarm: Boolean
)
```

## app\src\main\java\com\mhq\salati\onboarding\domain\repo\OnboardingRepository.kt

```kotlin
package com.mhq.salati.onboarding.domain.repo

interface OnboardingRepository {
    suspend fun hasCompletedOnboarding(): Boolean
    suspend fun setOnboardingCompleted()
}
```

## app\src\main\java\com\mhq\salati\onboarding\domain\usecases\CheckOnboardingPermissionsUseCase.kt

```kotlin
package com.mhq.salati.onboarding.domain.usecases

import com.mhq.salati.onboarding.domain.model.OnboardingPermissions
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import javax.inject.Inject

class CheckOnboardingPermissionsUseCase @Inject constructor(
    private val permissionChecker: PermissionChecker
) {
    operator fun invoke(): OnboardingPermissions = OnboardingPermissions(
        hasNotification = permissionChecker.hasNotificationPermission(),
        hasExactAlarm = permissionChecker.canScheduleExactAlarms()
    )
}
```

## app\src\main\java\com\mhq\salati\onboarding\domain\usecases\CompleteOnboardingUseCase.kt

```kotlin
package com.mhq.salati.onboarding.domain.usecases

import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import javax.inject.Inject

/** Marks the onboarding flow as done so it is never shown again. */
class CompleteOnboardingUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke() = onboardingRepository.setOnboardingCompleted()
}
```

## app\src\main\java\com\mhq\salati\onboarding\presentation\contract\OnboardingContract.kt

```kotlin
package com.mhq.salati.onboarding.presentation.contract

import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi

object OnboardingContract {

    enum class StepUi { WELCOME, CALCULATION, ALERTS }
    enum class SelectorUi { LANGUAGE, THEME, TIME_FORMAT, CALCULATION_METHOD, MADHAB, ADHAN_SOUND }

    /** What the main button of the current step does. */
    enum class PrimaryActionUi {
        CONTINUE,
        GET_STARTED,
        ALLOW_NOTIFICATIONS,
        OPEN_APP_SETTINGS,
        ALLOW_EXACT_ALARMS
    }

    data class State(
        val step: StepUi = StepUi.WELCOME,
        val isMovingForward: Boolean = true,
        // Welcome choices. Every default mirrors AppSettings, so Continue
        // always works and the user is never forced to pick anything.
        val language: AppLanguageUi = AppLanguageUi.ENGLISH,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_24_HOUR,
        // Calculation choices.
        val calculationMethod: CalculationMethodUi = CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY,
        val madhab: MadhabUi = MadhabUi.SHAFI,
        // Alerts choices.
        val notificationsEnabled: Boolean = true,
        val adhanSound: AdhanSoundUi = AdhanSoundUi.DEFAULT,
        val isNotificationPermissionGranted: Boolean = false,
        val isNotificationPermanentlyDenied: Boolean = false,
        val isExactAlarmGranted: Boolean = false,
        val isLoading: Boolean = false
    ) {
        val isAlarmAccessMissing: Boolean
            get() = notificationsEnabled && (!isNotificationPermissionGranted || !isExactAlarmGranted)

        val primaryAction: PrimaryActionUi
            get() = when (step) {
                StepUi.WELCOME, StepUi.CALCULATION -> PrimaryActionUi.CONTINUE
                StepUi.ALERTS -> when {
                    notificationsEnabled && !isNotificationPermissionGranted ->
                        if (isNotificationPermanentlyDenied) {
                            PrimaryActionUi.OPEN_APP_SETTINGS
                        } else {
                            PrimaryActionUi.ALLOW_NOTIFICATIONS
                        }

                    notificationsEnabled && !isExactAlarmGranted ->
                        PrimaryActionUi.ALLOW_EXACT_ALARMS

                    else -> PrimaryActionUi.GET_STARTED
                }
            }

        /** Steps whose requirement is still unmet can be skipped. */
        val canSkip: Boolean
            get() = when (step) {
                StepUi.ALERTS -> isAlarmAccessMissing
                else -> false
            }
    }

    sealed interface Intent {
        data object ScreenResumed : Intent

        // Welcome choices — each applies live, one intent per selector.
        data class LanguageSelected(val language: AppLanguageUi) : Intent
        data class ThemeSelected(val mode: ThemeModeUi) : Intent
        data class TimeFormatSelected(val format: TimeFormatUi) : Intent

        // Calculation choices.
        data class CalculationMethodSelected(val method: CalculationMethodUi) : Intent
        data class MadhabSelected(val madhab: MadhabUi) : Intent

        // Alerts choices.
        data class NotificationsToggled(val enabled: Boolean) : Intent
        data class AdhanSoundSelected(val sound: AdhanSoundUi) : Intent
        data class NotificationPermissionResult(
            val granted: Boolean,
            val permanentlyDenied: Boolean = false
        ) : Intent

        data object PrimaryActionRequested : Intent
        data object Skipped : Intent
        data object BackRequested : Intent
    }

    sealed interface Effect {
        data object OpenAppSettings : Effect
        data object RequestNotificationPermission : Effect
        data object RequestExactAlarmPermission : Effect

        /** The locale to apply before the app recreates with the new language. */
        data class ApplyLanguage(val languageCode: String) : Effect

        data class ShowError(val message: MessageUi) : Effect
        data object NavigateToHome : Effect
    }
}
```

## app\src\main\java\com\mhq\salati\onboarding\presentation\viewmodel\OnboardingViewModel.kt

```kotlin
package com.mhq.salati.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.onboarding.domain.usecases.CheckOnboardingPermissionsUseCase
import com.mhq.salati.onboarding.domain.usecases.CompleteOnboardingUseCase
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Effect
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Intent
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.PrimaryActionUi
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.StepUi
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.domain.usecases.UpdateAdhanSoundUseCase
import com.mhq.salati.settings.domain.usecases.UpdateCalculationMethodUseCase
import com.mhq.salati.settings.domain.usecases.UpdateLanguageUseCase
import com.mhq.salati.settings.domain.usecases.UpdateMadhabUseCase
import com.mhq.salati.settings.domain.usecases.UpdateNotificationsEnabledUseCase
import com.mhq.salati.settings.domain.usecases.UpdateThemeModeUseCase
import com.mhq.salati.settings.domain.usecases.UpdateTimeFormatUseCase
import com.mhq.salati.settings.presentation.mapper.toDomain
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    observeSettingsUseCase: ObserveSettingsUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase,
    private val updateTimeFormatUseCase: UpdateTimeFormatUseCase,
    private val updateCalculationMethodUseCase: UpdateCalculationMethodUseCase,
    private val updateMadhabUseCase: UpdateMadhabUseCase,
    private val updateAdhanSoundUseCase: UpdateAdhanSoundUseCase,
    private val updateNotificationsEnabledUseCase: UpdateNotificationsEnabledUseCase,
    private val checkOnboardingPermissionsUseCase: CheckOnboardingPermissionsUseCase,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        observeSettingsUseCase()
            .onEach { settings -> applySettingsToState(settings) }
            .launchIn(viewModelScope)

        refreshPermissions()
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.ScreenResumed -> refreshPermissions()
            is Intent.LanguageSelected -> selectLanguage(intent.language)
            is Intent.ThemeSelected -> selectTheme(intent.mode)
            is Intent.TimeFormatSelected -> selectTimeFormat(intent.format)
            is Intent.CalculationMethodSelected -> selectCalculationMethod(intent.method)
            is Intent.MadhabSelected -> selectMadhab(intent.madhab)
            is Intent.AdhanSoundSelected -> selectAdhanSound(intent.sound)
            is Intent.NotificationsToggled -> toggleNotifications(intent.enabled)
            is Intent.NotificationPermissionResult -> onNotificationPermissionResult(intent.granted, intent.permanentlyDenied)
            is Intent.PrimaryActionRequested -> performPrimaryAction()
            is Intent.Skipped -> skipStep()
            is Intent.BackRequested -> goBack()
        }
    }

    private fun applySettingsToState(settings: AppSettings) {
        _state.update {
            it.copy(
                language = settings.language.toUi(),
                themeMode = settings.themeMode.toUi(),
                timeFormat = settings.timeFormat.toUi(),
                calculationMethod = settings.calculationMethod.toUi(),
                madhab = settings.madhab.toUi(),
                adhanSound = settings.adhanSound.toUi(),
                notificationsEnabled = settings.notificationsEnabled
            )
        }
    }

    // ---- Live choices ------------------------------------------------------

    private fun selectLanguage(language: AppLanguageUi) {
        _state.update { it.copy(language = language) }
        viewModelScope.launch {
            try {
                val domainLanguage = language.toDomain()
                updateLanguageUseCase(domainLanguage)
                _effect.send(Effect.ApplyLanguage(domainLanguage.code))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(MessageUi.COULDNT_UPDATE_LANGUAGE))
            }
        }
    }

    private fun selectTheme(mode: ThemeModeUi) {
        _state.update { it.copy(themeMode = mode) }
        persistChoice(MessageUi.COULDNT_UPDATE_THEME) { updateThemeModeUseCase(mode.toDomain()) }
    }

    private fun selectTimeFormat(format: TimeFormatUi) {
        _state.update { it.copy(timeFormat = format) }
        persistChoice(MessageUi.COULDNT_UPDATE_TIME_FORMAT) {
            updateTimeFormatUseCase(format.toDomain())
        }
    }

    private fun selectCalculationMethod(method: CalculationMethodUi) {
        _state.update { it.copy(calculationMethod = method) }
        persistChoice(MessageUi.COULDNT_UPDATE_CALCULATION_METHOD) {
            updateCalculationMethodUseCase(method.toDomain())
        }
    }

    private fun selectMadhab(madhab: MadhabUi) {
        _state.update { it.copy(madhab = madhab) }
        persistChoice(MessageUi.COULDNT_UPDATE_MADHAB) { updateMadhabUseCase(madhab.toDomain()) }
    }

    private fun selectAdhanSound(sound: AdhanSoundUi) {
        _state.update { it.copy(adhanSound = sound) }
        persistChoice(MessageUi.COULDNT_UPDATE_ADHAN_SOUND) { updateAdhanSoundUseCase(sound.toDomain()) }
    }

    /** Persists a live choice; a failure shows a message but never blocks the flow. */
    private fun persistChoice(message: MessageUi, block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(message))
            }
        }
    }

    // ---- Permissions --------------------------------------------------------

    private fun refreshPermissions() {
        val permissions = checkOnboardingPermissionsUseCase()
        _state.update {
            it.copy(
                isNotificationPermissionGranted = permissions.hasNotification,
                isNotificationPermanentlyDenied = it.isNotificationPermanentlyDenied && !permissions.hasNotification,
                isExactAlarmGranted = permissions.hasExactAlarm
            )
        }
    }

    /**
     * Turning on without the OS permission only asks for it; the choice is persisted once
     * the permission is confirmed (same rule as the Settings screen), so state and storage
     * never disagree.
     */
    private fun toggleNotifications(enabled: Boolean) {
        if (enabled && !_state.value.isNotificationPermissionGranted) {
            viewModelScope.launch { requestMissingAlarmAccess() }
            return
        }
        setNotificationsEnabled(enabled)
    }

    private fun onNotificationPermissionResult(granted: Boolean, permanentlyDenied: Boolean) {
        _state.update {
            it.copy(
                isNotificationPermissionGranted = granted,
                isNotificationPermanentlyDenied = permanentlyDenied && !granted
            )
        }
        if (granted) setNotificationsEnabled(true)
    }

    private fun setNotificationsEnabled(enabled: Boolean) {
        _state.update { it.copy(notificationsEnabled = enabled) }
        persistChoice(MessageUi.COULDNT_UPDATE_NOTIFICATION_SETTINGS) {
            updateNotificationsEnabledUseCase(enabled)
        }
    }

    /** Asks for notifications first, then exact alarms; a permanently denied permission opens app settings. */
    private suspend fun requestMissingAlarmAccess() {
        val current = _state.value
        when {
            !current.isNotificationPermissionGranted -> _effect.send(
                if (current.isNotificationPermanentlyDenied) {
                    Effect.OpenAppSettings
                } else {
                    Effect.RequestNotificationPermission
                }
            )
            !current.isExactAlarmGranted -> _effect.send(Effect.RequestExactAlarmPermission)
        }
    }

    // ---- Navigation ---------------------------------------------------------

    private fun performPrimaryAction() {
        viewModelScope.launch {
            when (_state.value.primaryAction) {
                PrimaryActionUi.CONTINUE -> advance()
                PrimaryActionUi.GET_STARTED -> finish()
                PrimaryActionUi.ALLOW_NOTIFICATIONS,
                PrimaryActionUi.OPEN_APP_SETTINGS,
                PrimaryActionUi.ALLOW_EXACT_ALARMS -> requestMissingAlarmAccess()
            }
        }
    }

    private fun skipStep() {
        if (_state.value.step == StepUi.ALERTS) {
            finish()
        } else {
            advance()
        }
    }

    private fun advance() {
        _state.update {
            val next = StepUi.entries.getOrNull(it.step.ordinal + 1) ?: return@update it
            it.copy(step = next, isMovingForward = true)
        }
    }

    private fun goBack() {
        _state.update {
            val previous = StepUi.entries.getOrNull(it.step.ordinal - 1) ?: return@update it
            it.copy(step = previous, isMovingForward = false)
        }
    }

    private fun finish() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                completeOnboardingUseCase()
                _effect.send(Effect.NavigateToHome)
            } catch (e: CancellationException) {
                _state.update { it.copy(isLoading = false) }
                throw e
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _effect.send(Effect.ShowError(MessageUi.SOMETHING_WENT_WRONG))
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\onboarding\ui\components\OnboardingPageIndicator.kt

```kotlin
package com.mhq.salati.onboarding.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun OnboardingPageIndicator(pageCount: Int, currentPage: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(if (isSelected) 24.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        }
                    )
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OnboardingPageIndicatorPreview() {
    SalatiTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            OnboardingPageIndicator(pageCount = 4, currentPage = 1)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\onboarding\ui\components\OnboardingSelectionSheet.kt

```kotlin
package com.mhq.salati.onboarding.ui.components

import androidx.compose.runtime.Composable
import com.mhq.salati.R
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Intent
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.SelectorUi
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.components.SelectionSheet
import com.mhq.salati.shared.ui.components.selectionOptions

/** The choice sheets of onboarding; every pick applies live and closes the sheet. */
@Composable
fun OnboardingSelectionSheet(
    selector: SelectorUi,
    state: OnboardingContract.State,
    onIntent: (Intent) -> Unit,
    onDismiss: () -> Unit
) {
    fun pick(intent: Intent) {
        onIntent(intent)
        onDismiss()
    }

    val (titleRes, options) = when (selector) {
        SelectorUi.LANGUAGE -> R.string.language to selectionOptions(
            AppLanguageUi.entries, state.language, { it.displayNameRes }
        ) { pick(Intent.LanguageSelected(it)) }

        SelectorUi.THEME -> R.string.theme to selectionOptions(
            ThemeModeUi.entries, state.themeMode, { it.displayNameRes }
        ) { pick(Intent.ThemeSelected(it)) }

        SelectorUi.TIME_FORMAT -> R.string.time_format to selectionOptions(
            TimeFormatUi.entries, state.timeFormat, { it.displayNameRes }
        ) { pick(Intent.TimeFormatSelected(it)) }

        SelectorUi.CALCULATION_METHOD -> R.string.calculation_method to selectionOptions(
            CalculationMethodUi.entries, state.calculationMethod, { it.displayNameRes }
        ) { pick(Intent.CalculationMethodSelected(it)) }

        SelectorUi.MADHAB -> R.string.asr_madhab to selectionOptions(
            MadhabUi.entries, state.madhab, { it.displayNameRes }
        ) { pick(Intent.MadhabSelected(it)) }

        SelectorUi.ADHAN_SOUND -> R.string.adhan_sound to selectionOptions(
            AdhanSoundUi.entries, state.adhanSound, { it.displayNameRes }
        ) { pick(Intent.AdhanSoundSelected(it)) }
    }

    SelectionSheet(titleRes = titleRes, options = options, onDismiss = onDismiss)
}
```

## app\src\main\java\com\mhq\salati\onboarding\ui\OnboardingLabels.kt

```kotlin
package com.mhq.salati.onboarding.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.ui.graphics.vector.ImageVector
import com.mhq.salati.R
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.PrimaryActionUi
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.StepUi

/** The step's badge icon, or null when the step shows the app logo instead. */
val StepUi.icon: ImageVector?
    get() = when (this) {
        StepUi.WELCOME -> null
        StepUi.CALCULATION -> Icons.AutoMirrored.Filled.MenuBook
        StepUi.ALERTS -> Icons.Default.NotificationsActive
    }

val StepUi.titleRes: Int
    @StringRes get() = when (this) {
        StepUi.WELCOME -> R.string.app_name
        StepUi.CALCULATION -> R.string.onboarding_times_title
        StepUi.ALERTS -> R.string.notifications
    }

val StepUi.subtitleRes: Int
    @StringRes get() = when (this) {
        StepUi.WELCOME -> R.string.app_motto
        StepUi.CALCULATION -> R.string.onboarding_calculation_subtitle
        StepUi.ALERTS -> R.string.onboarding_notifications_subtitle
    }

val PrimaryActionUi.labelRes: Int
    @StringRes get() = when (this) {
        PrimaryActionUi.CONTINUE -> R.string.onboarding_continue
        PrimaryActionUi.GET_STARTED -> R.string.get_started
        PrimaryActionUi.ALLOW_NOTIFICATIONS -> R.string.onboarding_notifications_action
        PrimaryActionUi.ALLOW_EXACT_ALARMS -> R.string.onboarding_exact_alarm_action
        PrimaryActionUi.OPEN_APP_SETTINGS -> R.string.action_open_settings
    }
```

## app\src\main\java\com\mhq\salati\onboarding\ui\screens\OnboardingContainer.kt

```kotlin
package com.mhq.salati.onboarding.ui.screens

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.viewmodel.OnboardingViewModel
import com.mhq.salati.permissions.ui.rememberNotificationPermissionLauncher
import com.mhq.salati.shared.ui.asString

@Composable
fun OnboardingContainer(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val context = LocalContext.current
    val state by onboardingViewModel.state.collectAsStateWithLifecycle()

    val notificationPermissionLauncher = rememberNotificationPermissionLauncher(
        onGranted = {
            onboardingViewModel.onIntent(OnboardingContract.Intent.NotificationPermissionResult(granted = true))
        },
        onDenied = { permanentlyDenied ->
            onboardingViewModel.onIntent(
                OnboardingContract.Intent.NotificationPermissionResult(
                    granted = false,
                    permanentlyDenied = permanentlyDenied
                )
            )
        }
    )

    LaunchedEffect(Unit) {
        onboardingViewModel.effect.collect { effect ->
            when (effect) {
                is OnboardingContract.Effect.OpenAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivitySafely(intent)
                }

                is OnboardingContract.Effect.RequestNotificationPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        onboardingViewModel.onIntent(
                            OnboardingContract.Intent.NotificationPermissionResult(granted = true)
                        )
                    }
                }

                is OnboardingContract.Effect.RequestExactAlarmPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivitySafely(intent)
                    }
                }

                is OnboardingContract.Effect.ApplyLanguage ->
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(effect.languageCode)
                    )

                is OnboardingContract.Effect.ShowError ->
                    Toast.makeText(context, effect.message.asString(context), Toast.LENGTH_SHORT).show()

                is OnboardingContract.Effect.NavigateToHome -> onFinished()
            }
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onboardingViewModel.onIntent(OnboardingContract.Intent.ScreenResumed)
    }

    OnboardingContent(
        state = state,
        onIntent = onboardingViewModel::onIntent
    )
}

private fun Context.startActivitySafely(intent: Intent, fallback: Intent? = null) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        fallback?.let { runCatching { startActivity(it) } }
    }
}
```

## app\src\main\java\com\mhq\salati\onboarding\ui\screens\OnboardingContent.kt

```kotlin
package com.mhq.salati.onboarding.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Intent
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.StepUi
import com.mhq.salati.onboarding.ui.components.OnboardingPageIndicator
import com.mhq.salati.onboarding.ui.components.OnboardingSelectionSheet
import com.mhq.salati.onboarding.ui.icon
import com.mhq.salati.onboarding.ui.labelRes
import com.mhq.salati.onboarding.ui.subtitleRes
import com.mhq.salati.onboarding.ui.titleRes
import com.mhq.salati.settings.ui.components.SettingsSwitchRow
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.components.ActionRow
import com.mhq.salati.shared.ui.components.AppLogo
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.GroupDivider
import com.mhq.salati.shared.ui.components.StepBadgeIcon
import com.mhq.salati.shared.ui.components.StepHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.prayerGradient

@Composable
fun OnboardingContent(
    state: OnboardingContract.State,
    onIntent: (Intent) -> Unit
) {
    val isFirstStep = state.step == StepUi.WELCOME
    var activeSelector by remember { mutableStateOf<OnboardingContract.SelectorUi?>(null) }
    var isStepSettled by remember { mutableStateOf(true) }

    BackHandler(enabled = !isFirstStep) {
        onIntent(Intent.BackRequested)
    }

    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val stepTransition = updateTransition(targetState = state.step, label = "onboardingStep")
    isStepSettled = !stepTransition.isRunning

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.prayerGradient)
    ) {
        if (isWideLayout()) {
            OnboardingWideLayout(
                state = state,
                stepTransition = stepTransition,
                isRtl = isRtl,
                isFirstStep = isFirstStep,
                isStepSettled = isStepSettled,
                onIntent = onIntent,
                onOpenSelector = { activeSelector = it }
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(56.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    if (!isFirstStep) {
                        IconButton(
                            onClick = { onIntent(Intent.BackRequested) },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.cd_back),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                stepTransition.AnimatedContent(
                    transitionSpec = { stepTransitionSpec(state.isMovingForward, isRtl) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { step ->
                    OnboardingStepBody(
                        step = step,
                        state = state,
                        onIntent = onIntent,
                        onOpenSelector = { activeSelector = it }
                    )
                }

                Surface(
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 20.dp, bottom = 12.dp)
                            .navigationBarsPadding()
                    ) {
                        OnboardingActions(
                            state = state,
                            isStepSettled = isStepSettled,
                            onIntent = onIntent,
                            isCompact = false
                        )
                    }
                }
            }
        }

        activeSelector?.let { selector ->
            OnboardingSelectionSheet(
                selector = selector,
                state = state,
                onIntent = onIntent,
                onDismiss = { activeSelector = null }
            )
        }
    }
}

@Composable
private fun OnboardingStepBody(
    step: StepUi,
    state: OnboardingContract.State,
    onIntent: (Intent) -> Unit,
    onOpenSelector: (OnboardingContract.SelectorUi) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp)
    ) {
        OnboardingStepHeader(step)
        Spacer(modifier = Modifier.height(28.dp))
        when (step) {
            StepUi.WELCOME -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                GroupCard {
                    ActionRow(
                        icon = Icons.Default.Language,
                        title = stringResource(R.string.language),
                        valueLabel = stringResource(state.language.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.LANGUAGE) }
                    )
                    GroupDivider()
                    ActionRow(
                        icon = Icons.Default.DarkMode,
                        title = stringResource(R.string.theme),
                        valueLabel = stringResource(state.themeMode.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.THEME) }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.onboarding_change_later_hint),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.70f),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            StepUi.CALCULATION -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                GroupCard {
                    ActionRow(
                        icon = Icons.Default.Explore,
                        title = stringResource(R.string.calculation_method),
                        valueLabel = stringResource(state.calculationMethod.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.CALCULATION_METHOD) }
                    )
                    GroupDivider()
                    ActionRow(
                        icon = Icons.AutoMirrored.Filled.MenuBook,
                        title = stringResource(R.string.asr_madhab),
                        valueLabel = stringResource(state.madhab.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.MADHAB) }
                    )
                    GroupDivider()
                    ActionRow(
                        icon = Icons.Default.Schedule,
                        title = stringResource(R.string.time_format),
                        valueLabel = stringResource(state.timeFormat.displayNameRes),
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.TIME_FORMAT) }
                    )
                }
            }

            StepUi.ALERTS -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                GroupCard {
                    SettingsSwitchRow(
                        icon = Icons.Default.NotificationsActive,
                        title = stringResource(R.string.onboarding_notifications_toggle_title),
                        subtitle = stringResource(R.string.onboarding_notifications_toggle_subtitle),
                        isChecked = state.notificationsEnabled && state.isNotificationPermissionGranted,
                        onCheckedChange = { onIntent(Intent.NotificationsToggled(it)) }
                    )
                    GroupDivider()
                    ActionRow(
                        icon = Icons.Default.MusicNote,
                        title = stringResource(R.string.adhan_sound),
                        valueLabel = stringResource(state.adhanSound.displayNameRes),
                        isEnabled = state.notificationsEnabled,
                        onClick = { onOpenSelector(OnboardingContract.SelectorUi.ADHAN_SOUND) }
                    )
                }

                if (state.notificationsEnabled &&
                    state.isNotificationPermissionGranted &&
                    !state.isExactAlarmGranted
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.onboarding_exact_alarm_note),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

private fun stepTransitionSpec(isMovingForward: Boolean, isRtl: Boolean): ContentTransform {
    val direction = (if (isMovingForward) 1 else -1) * (if (isRtl) -1 else 1)
    return (slideInHorizontally { it * direction } + fadeIn()) togetherWith
            (slideOutHorizontally { -it * direction } + fadeOut())
}

@Composable
private fun OnboardingStepHeader(step: StepUi) {
    StepHeader(
        title = stringResource(step.titleRes),
        subtitle = stringResource(step.subtitleRes)
    ) {
        val icon = step.icon
        if (icon == null) AppLogo(size = 96.dp) else StepBadgeIcon(icon)
    }
}

@Composable
private fun OnboardingStepFields(
    step: StepUi,
    state: OnboardingContract.State,
    onIntent: (Intent) -> Unit,
    onOpenSelector: (OnboardingContract.SelectorUi) -> Unit
) {
    when (step) {
        StepUi.WELCOME -> Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            GroupCard {
                ActionRow(
                    icon = Icons.Default.Language,
                    title = stringResource(R.string.language),
                    valueLabel = stringResource(state.language.displayNameRes),
                    onClick = { onOpenSelector(OnboardingContract.SelectorUi.LANGUAGE) }
                )
                GroupDivider()
                ActionRow(
                    icon = Icons.Default.DarkMode,
                    title = stringResource(R.string.theme),
                    valueLabel = stringResource(state.themeMode.displayNameRes),
                    onClick = { onOpenSelector(OnboardingContract.SelectorUi.THEME) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.onboarding_change_later_hint),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.70f),
                modifier = Modifier.fillMaxWidth()
            )
        }

        StepUi.CALCULATION -> Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            GroupCard {
                ActionRow(
                    icon = Icons.Default.Explore,
                    title = stringResource(R.string.calculation_method),
                    valueLabel = stringResource(state.calculationMethod.displayNameRes),
                    onClick = { onOpenSelector(OnboardingContract.SelectorUi.CALCULATION_METHOD) }
                )
                GroupDivider()
                ActionRow(
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    title = stringResource(R.string.asr_madhab),
                    valueLabel = stringResource(state.madhab.displayNameRes),
                    onClick = { onOpenSelector(OnboardingContract.SelectorUi.MADHAB) }
                )
                GroupDivider()
                ActionRow(
                    icon = Icons.Default.Schedule,
                    title = stringResource(R.string.time_format),
                    valueLabel = stringResource(state.timeFormat.displayNameRes),
                    onClick = { onOpenSelector(OnboardingContract.SelectorUi.TIME_FORMAT) }
                )
            }
        }

        StepUi.ALERTS -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            GroupCard {
                SettingsSwitchRow(
                    icon = Icons.Default.NotificationsActive,
                    title = stringResource(R.string.onboarding_notifications_toggle_title),
                    subtitle = stringResource(R.string.onboarding_notifications_toggle_subtitle),
                    isChecked = state.notificationsEnabled && state.isNotificationPermissionGranted,
                    onCheckedChange = { onIntent(Intent.NotificationsToggled(it)) }
                )
                GroupDivider()
                ActionRow(
                    icon = Icons.Default.MusicNote,
                    title = stringResource(R.string.adhan_sound),
                    valueLabel = stringResource(state.adhanSound.displayNameRes),
                    isEnabled = state.notificationsEnabled,
                    onClick = { onOpenSelector(OnboardingContract.SelectorUi.ADHAN_SOUND) }
                )
            }

            if (state.notificationsEnabled &&
                state.isNotificationPermissionGranted &&
                !state.isExactAlarmGranted
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.onboarding_exact_alarm_note),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }    }
}

/** Page indicator, primary button and "Not now". [isCompact] trims spacing for short windows. */
@Composable
private fun OnboardingActions(
    state: OnboardingContract.State,
    isStepSettled: Boolean,
    onIntent: (Intent) -> Unit,
    isCompact: Boolean
) {
    OnboardingPageIndicator(
        pageCount = StepUi.entries.size,
        currentPage = state.step.ordinal
    )

    Spacer(modifier = Modifier.height(if (isCompact) 12.dp else 24.dp))

    Button(
        onClick = { onIntent(Intent.PrimaryActionRequested) },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        enabled = !state.isLoading && isStepSettled,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = if (isCompact) 48.dp else 56.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = stringResource(state.primaryAction.labelRes),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.heightIn(min = if (isCompact) 40.dp else 48.dp)
    ) {
        if (state.canSkip && !state.isLoading) {
            TextButton(
                onClick = { onIntent(Intent.Skipped) },
                enabled = isStepSettled
            ) {
                Text(
                    text = stringResource(R.string.onboarding_not_now),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Wide windows (landscape, tablets, foldables, split-screen): header on the start pane, fields
 * and buttons on the end pane. Each pane pads itself by the system-bar and cutout insets on its
 * outer side only, so the surface and gradient still run edge to edge.
 */
@Composable
private fun OnboardingWideLayout(
    state: OnboardingContract.State,
    stepTransition: Transition<StepUi>,
    isRtl: Boolean,
    isFirstStep: Boolean,
    isStepSettled: Boolean,
    onIntent: (Intent) -> Unit,
    onOpenSelector: (OnboardingContract.SelectorUi) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Start + WindowInsetsSides.Vertical)
                )
        ) {
            stepTransition.AnimatedContent(
                transitionSpec = { stepTransitionSpec(state.isMovingForward, isRtl) },
                modifier = Modifier.fillMaxSize()
            ) { step ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    OnboardingStepHeader(step)
                }
            }

            if (!isFirstStep) {
                IconButton(
                    onClick = { onIntent(Intent.BackRequested) },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_back),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Surface(
            shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.End + WindowInsetsSides.Vertical)
                    )
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                stepTransition.AnimatedContent(
                    transitionSpec = { stepTransitionSpec(state.isMovingForward, isRtl) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { step ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 8.dp)
                    ) {
                        OnboardingStepFields(step, state, onIntent, onOpenSelector)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OnboardingActions(
                        state = state,
                        isStepSettled = isStepSettled,
                        onIntent = onIntent,
                        isCompact = true
                    )
                }
            }
        }
    }
}

@Preview(name = "Welcome", showSystemUi = true)
@Preview(name = "Landscape", device = "spec:width=800dp,height=360dp,dpi=420", showSystemUi = true)
@Preview(name = "Arabic landscape, large font", locale = "ar", fontScale = 1.3f, device = "spec:width=800dp,height=360dp,dpi=420", showSystemUi = true)
@Composable
private fun OnboardingWelcomePreview() {
    SalatiTheme {
        OnboardingContent(state = OnboardingContract.State(), onIntent = {})
    }
}
```

## app\src\main\java\com\mhq\salati\permissions\data\repoimpl\LocationPermissionRepoImpl.kt

```kotlin
package com.mhq.salati.permissions.data.repoimpl

import com.mhq.salati.permissions.datasource.preferences.LocationPermissionDataStore
import com.mhq.salati.permissions.domain.repo.LocationPermissionRepository
import javax.inject.Inject

class LocationPermissionRepoImpl @Inject constructor(
    private val dataStore: LocationPermissionDataStore
) : LocationPermissionRepository {

    override suspend fun wasRequested(): Boolean = dataStore.wasRequested()

    override suspend fun markRequested() = dataStore.markRequested()
}
```

## app\src\main\java\com\mhq\salati\permissions\data\repoimpl\PermissionCheckerImpl.kt

```kotlin
package com.mhq.salati.permissions.data.repoimpl

import com.mhq.salati.permissions.domain.repo.PermissionChecker
import com.mhq.salati.permissions.datasource.device.PermissionDataSource
import javax.inject.Inject

class PermissionCheckerImpl @Inject constructor(
    private val permissionDataSource: PermissionDataSource
) : PermissionChecker {

    override fun hasLocationPermission(): Boolean =
        permissionDataSource.hasLocationPermission()

    override fun hasNotificationPermission(): Boolean =
        permissionDataSource.hasNotificationPermission()

    override fun canScheduleExactAlarms(): Boolean =
        permissionDataSource.canScheduleExactAlarms()
}
```

## app\src\main\java\com\mhq\salati\permissions\datasource\device\AndroidPermissionDataSource.kt

```kotlin
package com.mhq.salati.permissions.datasource.device

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidPermissionDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionDataSource {

    override fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun hasNotificationPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun canScheduleExactAlarms(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        return alarmManager.canScheduleExactAlarms()
    }
}
```

## app\src\main\java\com\mhq\salati\permissions\datasource\device\PermissionDataSource.kt

```kotlin
package com.mhq.salati.permissions.datasource.device

interface PermissionDataSource {
    fun hasLocationPermission(): Boolean
    fun hasNotificationPermission(): Boolean
    fun canScheduleExactAlarms(): Boolean
}
```

## app\src\main\java\com\mhq\salati\permissions\datasource\preferences\LocationPermissionDataStore.kt

```kotlin
package com.mhq.salati.permissions.datasource.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.locationPermissionPrefs: DataStore<Preferences> by preferencesDataStore(name = "location_permission_prefs")

@Singleton
class LocationPermissionDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val requestedKey = booleanPreferencesKey("location_permission_requested")

    suspend fun wasRequested(): Boolean =
        context.locationPermissionPrefs.data.first()[requestedKey] ?: false

    suspend fun markRequested() {
        context.locationPermissionPrefs.edit { it[requestedKey] = true }
    }
}
```

## app\src\main\java\com\mhq\salati\permissions\di\PermissionModule.kt

```kotlin
package com.mhq.salati.permissions.di

import com.mhq.salati.permissions.data.repoimpl.LocationPermissionRepoImpl
import com.mhq.salati.permissions.data.repoimpl.PermissionCheckerImpl
import com.mhq.salati.permissions.datasource.device.AndroidPermissionDataSource
import com.mhq.salati.permissions.datasource.device.PermissionDataSource
import com.mhq.salati.permissions.domain.repo.LocationPermissionRepository
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PermissionModule {

    @Binds
    @Singleton
    abstract fun bindPermissionChecker(impl: PermissionCheckerImpl): PermissionChecker

    @Binds
    @Singleton
    abstract fun bindPermissionDataSource(impl: AndroidPermissionDataSource): PermissionDataSource

    @Binds
    @Singleton
    abstract fun bindLocationPermissionRepository(impl: LocationPermissionRepoImpl): LocationPermissionRepository
}
```

## app\src\main\java\com\mhq\salati\permissions\domain\repo\LocationPermissionRepository.kt

```kotlin
package com.mhq.salati.permissions.domain.repo

/** What the app remembers about asking for the location permission. */
interface LocationPermissionRepository {
    suspend fun wasRequested(): Boolean
    suspend fun markRequested()
}
```

## app\src\main\java\com\mhq\salati\permissions\domain\repo\PermissionChecker.kt

```kotlin
package com.mhq.salati.permissions.domain.repo

interface PermissionChecker {
    fun hasLocationPermission(): Boolean
    fun hasNotificationPermission(): Boolean
    fun canScheduleExactAlarms(): Boolean
}
```

## app\src\main\java\com\mhq\salati\permissions\domain\usecases\MarkLocationPermissionRequestedUseCase.kt

```kotlin
package com.mhq.salati.permissions.domain.usecases

import com.mhq.salati.permissions.domain.repo.LocationPermissionRepository
import javax.inject.Inject

class MarkLocationPermissionRequestedUseCase @Inject constructor(
    private val repository: LocationPermissionRepository
) {
    suspend operator fun invoke() = repository.markRequested()
}
```

## app\src\main\java\com\mhq\salati\permissions\domain\usecases\WasLocationPermissionRequestedUseCase.kt

```kotlin
package com.mhq.salati.permissions.domain.usecases

import com.mhq.salati.permissions.domain.repo.LocationPermissionRepository
import javax.inject.Inject

class WasLocationPermissionRequestedUseCase @Inject constructor(
    private val repository: LocationPermissionRepository
) {
    suspend operator fun invoke(): Boolean = repository.wasRequested()
}
```

## app\src\main\java\com\mhq\salati\permissions\ui\rememberLocationPermissionLauncher.kt

```kotlin
package com.mhq.salati.permissions.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberLocationPermissionLauncher(
    onGranted: () -> Unit,
    onDenied: (showRationale: Boolean) -> Unit
): ActivityResultLauncher<String> {
    val context = LocalContext.current

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onGranted() else onDenied(context.shouldShowLocationRationale())
    }
}
```

## app\src\main\java\com\mhq\salati\permissions\ui\rememberLocationServicesEnabled.kt

```kotlin
package com.mhq.salati.permissions.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.State
import androidx.core.content.ContextCompat

@Composable
fun rememberLocationServicesEnabled(): State<Boolean> {

    val context = LocalContext.current

    val locationManager = remember {
        context.getSystemService(LocationManager::class.java)
    }

    val locationServicesEnabled = remember {
        mutableStateOf(
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        )
    }

    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                locationServicesEnabled.value =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            }
        }

        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)

        ContextCompat.registerReceiver(
            context,
            receiver,
            intentFilter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        onDispose { context.unregisterReceiver(receiver) }
    }

    return locationServicesEnabled
}
```

## app\src\main\java\com\mhq\salati\permissions\ui\rememberNotificationPermissionLauncher.kt

```kotlin
package com.mhq.salati.permissions.ui

import android.Manifest
import android.app.Activity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

@Composable
fun rememberNotificationPermissionLauncher(
    onGranted: () -> Unit,
    onDenied: (permanentlyDenied: Boolean) -> Unit
): ManagedActivityResultLauncher<String, Boolean> {
    val activity = LocalContext.current as? Activity

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGranted()
        } else {
            val canAskAgain = activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } ?: true
            onDenied(!canAskAgain)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\permissions\ui\ShouldShowLocationRationale.kt

```kotlin
package com.mhq.salati.permissions.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.core.app.ActivityCompat

/**
 * True while the system would still show its location dialog after a refusal. Only the UI layer can
 * ask the system, so screens report this to their ViewModel.
 */
fun Context.shouldShowLocationRationale(): Boolean {
    var current: Context = this
    while (current is ContextWrapper) {
        if (current is Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale(
                current,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        current = current.baseContext
    }
    return false
}
```

## app\src\main\java\com\mhq\salati\prayertimes\data\mapper\CalendarMapper.kt

```kotlin
package com.mhq.salati.prayertimes.data.mapper

import com.mhq.salati.prayertimes.datasource.network.dto.CalendarResponseDto
import com.mhq.salati.prayertimes.datasource.network.dto.TimingsDataDto
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesEntity

fun CalendarResponseDto.toEntityList(
    latitude: Double,
    longitude: Double,
    method: Int,
    schoolId: Int,
    hijriAdjustment: Int
): List<PrayerTimesEntity> {
    return data.values.flatten().map { dayData: TimingsDataDto ->
        val domainResult = dayData.toDomainResult()
        domainResult.toEntity(
            dateKey = dayData.date.gregorian.date,
            latitude = latitude,
            longitude = longitude,
            method = method,
            schoolId = schoolId,
            hijriAdjustment = hijriAdjustment
        )
    }
}
```

## app\src\main\java\com\mhq\salati\prayertimes\data\mapper\TimingsMapper.kt

```kotlin
package com.mhq.salati.prayertimes.data.mapper

import com.mhq.salati.prayertimes.datasource.network.dto.TimingsDataDto
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesEntity
import com.mhq.salati.prayertimes.domain.model.HijriDate
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.HijriMonth
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**Wire/storage formats. Pinned to Locale.US so digits never depend on the app language.*/
private val DateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)
private val TimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)

/**Shared field-mapping logic, used by both this file and CalendarMapper.kt*/
internal fun TimingsDataDto.toDomainResult(): PrayerTimesResult {
    return PrayerTimesResult(
        timings = PrayerTimings(
            fajr = timings.fajr.toPrayerTime(),
            sunrise = timings.sunrise.toPrayerTime(),
            dhuhr = timings.dhuhr.toPrayerTime(),
            asr = timings.asr.toPrayerTime(),
            sunset = timings.sunset.toPrayerTime(),
            maghrib = timings.maghrib.toPrayerTime(),
            isha = timings.isha.toPrayerTime(),
            imsak = timings.imsak.toPrayerTime(),
            midnight = timings.midnight.toPrayerTime(),
            firstThird = timings.firstThird.toPrayerTime(),
            lastThird = timings.lastThird.toPrayerTime()
        ),
        date = PrayerDate(
            gregorianDate = LocalDate.parse(date.gregorian.date, DateFormatter),
            hijriDate = HijriDate(
                day = date.hijri.day.toInt(),
                month = HijriMonth.fromNumber(date.hijri.month.number),
                year = date.hijri.year.toInt()
            )
        )
    )
}

/**domain -> entity (used after either the single-day or calendar path produces a PrayerTimesResult) */
fun PrayerTimesResult.toEntity(
    dateKey: String,
    latitude: Double,
    longitude: Double,
    method: Int,
    schoolId: Int,
    hijriAdjustment: Int
): PrayerTimesEntity {
    return PrayerTimesEntity(
        date = dateKey,
        fajr = timings.fajr.format(TimeFormatter),
        sunrise = timings.sunrise.format(TimeFormatter),
        dhuhr = timings.dhuhr.format(TimeFormatter),
        asr = timings.asr.format(TimeFormatter),
        sunset = timings.sunset.format(TimeFormatter),
        maghrib = timings.maghrib.format(TimeFormatter),
        isha = timings.isha.format(TimeFormatter),
        imsak = timings.imsak.format(TimeFormatter),
        midnight = timings.midnight.format(TimeFormatter),
        firstThird = timings.firstThird.format(TimeFormatter),
        lastThird = timings.lastThird.format(TimeFormatter),
        gregorianDate = date.gregorianDate.format(DateFormatter),
        hijriDate = String.format(
            Locale.US,
            "%02d-%02d-%d",
            date.hijriDate.day,
            date.hijriDate.month.number,
            date.hijriDate.year
        ),
        hijriDay = date.hijriDate.day.toString().padStart(2, '0'),
        hijriMonthNumber = date.hijriDate.month.number,
        hijriYear = date.hijriDate.year.toString(),
        latitude = latitude,
        longitude = longitude,
        method = method,
        schoolId = schoolId,
        hijriAdjustment = hijriAdjustment
    )
}

/** entity -> domain (used for cache hits) */
fun PrayerTimesEntity.toDomain(): PrayerTimesResult {
    return PrayerTimesResult(
        timings = PrayerTimings(
            fajr = fajr.toPrayerTime(),
            sunrise = sunrise.toPrayerTime(),
            dhuhr = dhuhr.toPrayerTime(),
            asr = asr.toPrayerTime(),
            sunset = sunset.toPrayerTime(),
            maghrib = maghrib.toPrayerTime(),
            isha = isha.toPrayerTime(),
            imsak = imsak.toPrayerTime(),
            midnight = midnight.toPrayerTime(),
            firstThird = firstThird.toPrayerTime(),
            lastThird = lastThird.toPrayerTime()
        ),
        date = PrayerDate(
            gregorianDate = LocalDate.parse(gregorianDate, DateFormatter),
            hijriDate = HijriDate(
                day = hijriDay.toInt(),
                month = HijriMonth.fromNumber(hijriMonthNumber),
                year = hijriYear.toInt()
            )
        )
    )
}

/**Aladhan sends e.g. "04:30 (EET)";
 * stored rows are already plain "04:30".
 * Both go through here, and anything that isn't HH:mm now fails loudly
 * instead of silently becoming 00:00.*/
private fun String.toPrayerTime(): LocalTime =
    LocalTime.parse(substringBefore(" (").trim(), TimeFormatter)
```

## app\src\main\java\com\mhq\salati\prayertimes\data\repoimpl\PrayerTimesRepoImpl.kt

```kotlin
package com.mhq.salati.prayertimes.data.repoimpl

import com.mhq.salati.prayertimes.datasource.network.api.AladhanApiService
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesDao
import com.mhq.salati.prayertimes.data.mapper.toDomain
import com.mhq.salati.prayertimes.data.mapper.toEntityList
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.shared.domain.Coordinates
import kotlin.math.abs

class PrayerTimesRepoImpl(
    private val aladhanApiService: AladhanApiService,
    private val prayerTimesDao: PrayerTimesDao
) : PrayerTimesRepository {

    companion object {
        private const val COORDINATE_TOLERANCE = 0.01
    }

    override suspend fun getCachedTimings(
        date: String,
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): PrayerTimesResult? {
        val cached = prayerTimesDao.getByDate(date)
        val isCacheValid = cached != null &&
                cached.method == method &&
                cached.schoolId == madhab.schoolId &&
                cached.hijriAdjustment == adjustment &&
                abs(cached.latitude - coordinates.latitude) < COORDINATE_TOLERANCE &&
                abs(cached.longitude - coordinates.longitude) < COORDINATE_TOLERANCE

        // A stored row that no longer parses is treated as a cache miss, so the caller falls
        // through to a fresh fetch (which overwrites the bad row) instead of failing.
        return if (isCacheValid) runCatching { cached!!.toDomain() }.getOrNull() else null
    }

    override suspend fun getPrayerTimings(
        date: String,
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): Result<PrayerTimesResult> {
        getCachedTimings(date, coordinates, method, madhab, adjustment)?.let {
            return Result.success(it)
        }

        return try {
            val year = date.substringAfterLast("-").toInt()

            val calendarResponse = aladhanApiService.getCalendar(
                year = year,
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
                method = method,
                school = madhab.schoolId,
                adjustment = adjustment
            )

            val entities = calendarResponse.toEntityList(coordinates.latitude, coordinates.longitude, method, madhab.schoolId, adjustment)
            prayerTimesDao.insertAll(entities)

            // Fixed: pull today's entity straight out of the list we just mapped from the
            // API response instead of round-tripping Room for data we already have in memory.
            val todayEntity = entities.find { it.date == date }
                ?: return Result.failure(
                    IllegalStateException("Requested date not found in fetched calendar")
                )

            Result.success(todayEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\database\PrayerTimesDao.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayerTimesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PrayerTimesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<PrayerTimesEntity>)

    @Query("SELECT * FROM prayer_timings WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): PrayerTimesEntity?
}
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\database\PrayerTimesEntity.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_timings")
data class PrayerTimesEntity(
    @PrimaryKey
    val date: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val sunset: String,
    val maghrib: String,
    val isha: String,
    val imsak: String,
    val midnight: String,
    val firstThird: String,
    val lastThird: String,
    val gregorianDate: String,
    val hijriDate: String,
    val hijriDay: String,
    val hijriMonthNumber: Int,
    val hijriYear: String,
    val latitude: Double,
    val longitude: Double,
    val method: Int,
    val schoolId: Int,
    val hijriAdjustment: Int = 0
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\api\AladhanApiService.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.api

import com.mhq.salati.prayertimes.datasource.network.dto.CalendarResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class AladhanApiService(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.aladhan.com/v1"
    }

    suspend fun getCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int = 5,
        school: Int = 0,
        adjustment: Int = 0  
    ): CalendarResponseDto {
        return client.get("$BASE_URL/calendar") {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("year", year)
            parameter("method", method)
            parameter("school", school)
            parameter("adjustment", adjustment)  
            parameter("annual", true)
        }.body()
    }
}
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\CalendarResponseDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CalendarResponseDto(
    val code: Int,
    val status: String,
    val data: Map<String, List<TimingsDataDto>>
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\DateDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class DateDto(
    val readable: String,
    val timestamp: String,
    val gregorian: GregorianDto,
    val hijri: HijriDto
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\GregorianDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class GregorianDto(
    val date: String,
    val day: String,
    val month: MonthDto,
    val year: String
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\HijriDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class HijriDto(
    val date: String,
    val day: String,
    val month: HijriMonthDto,
    val year: String
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\HijriMonthDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class HijriMonthDto(
    val number: Int,
    val en: String,
    val ar: String
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\MetaDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\MonthDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MonthDto(
    val number: Int,
    val en: String
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\TimingsDataDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TimingsDataDto(
    val timings: TimingsDto,
    val date: DateDto,
    val meta: MetaDto
)
```

## app\src\main\java\com\mhq\salati\prayertimes\datasource\network\dto\TimingsDto.kt

```kotlin
package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimingsDto(
    @SerialName("Fajr")
    val fajr: String,
    @SerialName("Sunrise")
    val sunrise: String,
    @SerialName("Dhuhr")
    val dhuhr: String,
    @SerialName("Asr")
    val asr: String,
    @SerialName("Sunset")
    val sunset: String,
    @SerialName("Maghrib")
    val maghrib: String,
    @SerialName("Isha")
    val isha: String,
    @SerialName("Imsak")
    val imsak: String,
    @SerialName("Midnight")
    val midnight: String,
    @SerialName("Firstthird")
    val firstThird: String,
    @SerialName("Lastthird")
    val lastThird: String
)
```

## app\src\main\java\com\mhq\salati\prayertimes\di\PrayerTimesModule.kt

```kotlin
package com.mhq.salati.prayertimes.di

import com.mhq.salati.prayertimes.datasource.network.api.AladhanApiService
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesDao
import com.mhq.salati.prayertimes.data.repoimpl.PrayerTimesRepoImpl
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrayerTimesModule {

    @Provides
    @Singleton
    fun provideAladhanApiService(client: HttpClient): AladhanApiService {
        return AladhanApiService(client)
    }

    @Provides
    @Singleton
    fun providePrayerTimesDao(database: SalatiDatabase): PrayerTimesDao {
        return database.prayerTimesDao()
    }

    @Provides
    @Singleton
    fun providePrayerTimesRepository(
        apiService: AladhanApiService,
        dao: PrayerTimesDao
    ): PrayerTimesRepository {
        return PrayerTimesRepoImpl(apiService, dao)
    }
}
```

## app\src\main\java\com\mhq\salati\prayertimes\domain\model\HijriDate.kt

```kotlin
package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.HijriMonth

/**
 * A date on the Hijri calendar, as shown next to the Gregorian one.
 *
 * @property day day of the Hijri month, 1-based
 * @property month the Hijri month
 * @property year the Hijri year
 */
data class HijriDate(
    val day: Int,
    val month: HijriMonth,
    val year: Int
)
```

## app\src\main\java\com\mhq\salati\prayertimes\domain\model\HijriDateAdjustment.kt

```kotlin
package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.HijriMonth
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

/**
 * Shifts a date's Hijri part by [offsetDays] while leaving the Gregorian part alone.
 *
 * Users can nudge it when local moon sighting differs from the computed one.
 * A zero offset resets the date otherwise the date is re-derived from the adjusted [HijrahDate],
 * rolling over months and years as needed.
 *
 * @receiver the date whose Hijri part to adjust
 * @param offsetDays days to add (negative to subtract)
 * @return a copy of the date with the adjusted Hijri part
 */
fun PrayerDate.applyHijriAdjustment(offsetDays: Int): PrayerDate {
    if (offsetDays == 0) return this

    val adjustedHijriDate = HijrahDate
        .of(hijriDate.year, hijriDate.month.number, hijriDate.day)
        .plus(offsetDays.toLong(), ChronoUnit.DAYS)

    return copy(
        hijriDate = HijriDate(
            day = adjustedHijriDate.get(ChronoField.DAY_OF_MONTH),
            month = HijriMonth.fromNumber(adjustedHijriDate.get(ChronoField.MONTH_OF_YEAR)),
            year = adjustedHijriDate.get(ChronoField.YEAR)
        )
    )
}
```

## app\src\main\java\com\mhq\salati\prayertimes\domain\model\PrayerDate.kt

```kotlin
package com.mhq.salati.prayertimes.domain.model

import java.time.LocalDate

/**
 * The two calendars a prayer day is shown in.
 *
 * @property gregorianDate the civil date the timings were calculated for
 * @property hijriDate the Hijri date displayed alongside it
 * (already adjusted for the user's offset by the time it reaches the UI)
 */
data class PrayerDate(
    val gregorianDate: LocalDate,
    val hijriDate: HijriDate
)
```

## app\src\main\java\com\mhq\salati\prayertimes\domain\model\PrayerTimesResult.kt

```kotlin
package com.mhq.salati.prayertimes.domain.model

/**
 * A day's prayer times together with the date they belong to.
 *
 * @property timings the day's timings
 * @property date the Gregorian and Hijri dates those timings are for
 */
data class PrayerTimesResult(
    val timings: PrayerTimings,
    val date: PrayerDate
)
```

## app\src\main\java\com\mhq\salati\prayertimes\domain\model\PrayerTimings.kt

```kotlin
package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.PrayerName
import java.time.LocalTime

/**
 * All eleven timings of one day, in the order the calculation API returns them.
 * Every [PrayerName] has a timing here, including the minor ones (Imsak,
 * Shorouq, the third-of-night marks) that are displayed but not tracked.
 */
data class PrayerTimings(
    val imsak: LocalTime,
    val fajr: LocalTime,
    val sunrise: LocalTime,
    val dhuhr: LocalTime,
    val asr: LocalTime,
    val sunset: LocalTime,
    val maghrib: LocalTime,
    val isha: LocalTime,
    val midnight: LocalTime,
    val firstThird: LocalTime,
    val lastThird: LocalTime
) {
    /**
     * The time a [PrayerName] refers to on this day.
     * Note the two renames the API needs: SHOROUQ maps to [sunrise].
     */
    operator fun get(name: PrayerName): LocalTime = when (name) {
        PrayerName.IMSAK -> imsak
        PrayerName.FAJR -> fajr
        PrayerName.DHUHR -> dhuhr
        PrayerName.ASR -> asr
        PrayerName.MAGHRIB -> maghrib
        PrayerName.ISHA -> isha
        PrayerName.SHOROUQ -> sunrise
        PrayerName.FIRST_THIRD -> firstThird
        PrayerName.MIDNIGHT -> midnight
        PrayerName.LAST_THIRD -> lastThird
    }
}
```

## app\src\main\java\com\mhq\salati\prayertimes\domain\repo\PrayerTimesRepository.kt

```kotlin
package com.mhq.salati.prayertimes.domain.repo

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.shared.domain.Coordinates

/**
 * Source of prayer times, implemented in Data over the calculation API and cache.
 * Both methods take the calculation parameters explicitly (with defaults
 * matching the API's own defaults) so callers decide which configuration a
 * result belongs to; cached results are only valid for the same parameters.
 */
interface PrayerTimesRepository {
    /**
     * Fetches the day's timings from the network.
     *
     * @param date the day as its "dd-MM-yyyy" storage key
     * @param coordinates the location to calculate times for
     * @param method the calculation method's API id
     * @param madhab the madhab used for Asr
     * @param adjustment minute-level adjustment applied to every timing
     * @return the timings, or the failure reason
     */
    suspend fun getPrayerTimings(
        date: String,
        coordinates: Coordinates,
        method: Int = 5,
        madhab: Madhab = Madhab.SHAFI,
        adjustment: Int = 0
    ): Result<PrayerTimesResult>

    /**
     * Reads the day's timings from cache only; never touches the network.
     *
     * Same parameters as [getPrayerTimings]; a cache entry stored under
     * different parameters is not returned.
     *
     * @return the cached timings, or null on a cache miss
     */
    suspend fun getCachedTimings(
        date: String,
        coordinates: Coordinates,
        method: Int = 5,
        madhab: Madhab = Madhab.SHAFI,
        adjustment: Int = 0
    ): PrayerTimesResult?
}
```

## app\src\main\java\com\mhq\salati\prayertimes\domain\usecases\GetCachedPrayerTimesUseCase.kt

```kotlin
package com.mhq.salati.prayertimes.domain.usecases

import com.mhq.salati.prayertimes.domain.model.applyHijriAdjustment
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.repo.SettingsRepository
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Reads a day's timings from cache, configured with the user's current settings.
 * Fills in the calculation method and madhab from [SettingsRepository] and
 * applies the stored Hijri date offset to the result, so cached timings come
 * out exactly as the user should see them. Cache only; never goes online.
 */
class GetCachedPrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository,
    private val settingsRepository: SettingsRepository
) {
    /**
     * @param date the day as its "dd-MM-yyyy" storage key
     * @param coordinates the location the cached timings must match
     * @return the adjusted cached timings, or null on a cache miss
     */
    suspend operator fun invoke(
        date: String,
        coordinates: Coordinates
    ): PrayerTimesResult? {
        val settings = settingsRepository.observeSettings().first()
        return prayerTimesRepository.getCachedTimings(
            date,
            coordinates,
            method = settings.calculationMethod.apiMethodId,
            madhab = settings.madhab
        )?.let { result ->
            result.copy(date = result.date.applyHijriAdjustment(settings.hijriDateOffset))
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertimes\domain\usecases\GetPrayerTimesUseCase.kt

```kotlin
package com.mhq.salati.prayertimes.domain.usecases

import com.mhq.salati.prayertimes.domain.model.applyHijriAdjustment
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.repo.SettingsRepository
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Fetches a day's timings from the network, configured with the user's current settings.
 * Fills in the calculation method and madhab from [SettingsRepository]
 * and applies the stored Hijri date offset to the result,
 * mirroring [GetCachedPrayerTimesUseCase] so both sources return identically shaped data.
 */
class GetPrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository,
    private val settingsRepository: SettingsRepository
) {
    /**
     * @param date the day as its "dd-MM-yyyy" storage key
     * @param coordinates the location to calculate times for
     * @return the adjusted timings, or the failure reason
     */
    suspend operator fun invoke(
        date: String,
        coordinates: Coordinates
    ): Result<PrayerTimesResult> {
        val settings = settingsRepository.observeSettings().first()
        return prayerTimesRepository.getPrayerTimings(
            date,
            coordinates,
            method = settings.calculationMethod.apiMethodId,
            madhab = settings.madhab
        ).map { result ->
            result.copy(date = result.date.applyHijriAdjustment(settings.hijriDateOffset))
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\data\repoimpl\PrayerTrackerRepoImpl.kt

```kotlin
package com.mhq.salati.prayertracker.data.repoimpl

import com.mhq.salati.prayertracker.datasource.database.PrayerRecordDao
import com.mhq.salati.prayertracker.datasource.database.PrayerRecordEntity
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PrayerTrackerRepoImpl @Inject constructor(
    private val prayerRecordDao: PrayerRecordDao
) : PrayerTrackerRepository {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun observeRecordsForMonth(yearMonth: YearMonth) =
        prayerRecordDao.observeForRange(
            yearMonth.atDay(1).format(formatter),
            yearMonth.atEndOfMonth().format(formatter)
        )
            .map { entities -> entities.toMonthMap() }
            .flowOn(Dispatchers.IO)

    override fun observeRecordsForDate(date: LocalDate) =
        prayerRecordDao.observeForDate(date.format(formatter))
            .map { it.toStatusMap() }
            .flowOn(Dispatchers.IO)

    override suspend fun setStatus(date: LocalDate, prayer: PrayerName, status: PrayerStatus) {
        prayerRecordDao.upsert(
            PrayerRecordEntity(
                date.format(formatter),
                prayer.storageKey,
                status.name
            )
        )
    }

    private fun List<PrayerRecordEntity>.toStatusMap(): Map<PrayerName, PrayerStatus> =
        mapNotNull { entity ->
            val prayerName = PrayerName.fromStorageKey(entity.prayer) ?: return@mapNotNull null
            val status = runCatching { PrayerStatus.valueOf(entity.status) }.getOrNull()
                ?: return@mapNotNull null
            prayerName to status
        }.toMap()

    private fun List<PrayerRecordEntity>.toMonthMap(): Map<LocalDate, Map<PrayerName, PrayerStatus>> =
        groupBy { LocalDate.parse(it.date, formatter) }
            .mapValues { (_, entries) -> entries.toStatusMap() }

    override suspend fun getRecordsForRange(
        start: LocalDate,
        end: LocalDate
    ): Map<LocalDate, Map<PrayerName, PrayerStatus>> =
        prayerRecordDao.getForRange(start.format(formatter), end.format(formatter)).toMonthMap()
}
```

## app\src\main\java\com\mhq\salati\prayertracker\datasource\database\PrayerRecordDao.kt

```kotlin
package com.mhq.salati.prayertracker.datasource.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerRecordDao {
    @Query("SELECT * FROM prayer_records WHERE date BETWEEN :startDate AND :endDate")
    fun observeForRange(startDate: String, endDate: String): Flow<List<PrayerRecordEntity>>

    @Query("SELECT * FROM prayer_records WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getForRange(startDate: String, endDate: String): List<PrayerRecordEntity>

    @Query("SELECT * FROM prayer_records WHERE date = :date")
    fun observeForDate(date: String): Flow<List<PrayerRecordEntity>>

    @Upsert
    suspend fun upsert(record: PrayerRecordEntity)
}
```

## app\src\main\java\com\mhq\salati\prayertracker\datasource\database\PrayerRecordEntity.kt

```kotlin
package com.mhq.salati.prayertracker.datasource.database

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "prayer_records",
    primaryKeys = ["date", "prayer"],
    indices = [Index(value = ["date"])]
)
data class PrayerRecordEntity(
    val date: String,
    val prayer: String,
    val status: String
)
```

## app\src\main\java\com\mhq\salati\prayertracker\di\PrayerTrackerModule.kt

```kotlin
package com.mhq.salati.prayertracker.di

import com.mhq.salati.prayertracker.datasource.database.PrayerRecordDao
import com.mhq.salati.prayertracker.data.repoimpl.PrayerTrackerRepoImpl
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PrayerTrackerModule {
    @Binds
    abstract fun bindPrayerTrackerRepository(impl: PrayerTrackerRepoImpl): PrayerTrackerRepository

    companion object {
        @Provides
        fun providePrayerRecordDao(db: SalatiDatabase): PrayerRecordDao = db.prayerRecordDao()
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\model\DayStatus.kt

```kotlin
package com.mhq.salati.prayertracker.domain.model

/**
 * How a calendar day went, as color-coded for the tracker calendar.
 * Derived per day from its records; see ObserveMonthDayStatusUseCase for the mapping rules.
 */
enum class DayStatus {
    /** The day has not happened yet; never colored. */
    FUTURE,

    /** All five daily prayers were marked PRAYED. */
    ALL_PRAYED,

    /** At least one prayer was marked MISSED. */
    HAS_MISSED,

    /** The day is today or a past day with neither a miss nor a full set. */
    IN_PROGRESS
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\model\PrayerStatus.kt

```kotlin
package com.mhq.salati.prayertracker.domain.model

/**
 * What the user recorded for one prayer on one day.
 *
 * [PENDING] is the default state of an unmarked prayer;
 * it is usually implicit (absent from storage) rather than written as a row.
 */
enum class PrayerStatus {
    /** Not marked yet. */
    PENDING,

    /** Marked as prayed. */
    PRAYED,

    /** Marked as missed. */
    MISSED
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\repo\PrayerTrackerRepository.kt

```kotlin
package com.mhq.salati.prayertracker.domain.repo

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

/**
 * Storage of the user's prayer records, implemented in Data.
 *
 * Records are keyed by calendar date and prayer;
 * a prayer with no stored row reads as [PrayerStatus.PENDING].
 * The observe methods emit again whenever the underlying records change.
 */
interface PrayerTrackerRepository {
    /**
     * All records for the days of [yearMonth], keyed by date.
     * Days with no records may be absent from the map.
     */
    fun observeRecordsForMonth(yearMonth: YearMonth):
            Flow<Map<LocalDate, Map<PrayerName, PrayerStatus>>>

    /**
     * All records for [date], keyed by prayer.
     * Prayers with no stored row may be absent from the map.
     */
    fun observeRecordsForDate(date: LocalDate):
            Flow<Map<PrayerName, PrayerStatus>>

    /**
     * One-shot read of every record between [start] and [end], both inclusive.
     * Used by random-access walks (e.g. the streak) that must not re-query per day.
     */
    suspend fun getRecordsForRange(start: LocalDate, end: LocalDate):
            Map<LocalDate, Map<PrayerName, PrayerStatus>>

    /** Writes [status] for [prayer] on [date], replacing any previous value. */
    suspend fun setStatus(date: LocalDate, prayer: PrayerName, status: PrayerStatus)
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\GetCurrentStreakUseCase.kt

```kotlin
package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import javax.inject.Inject

/**
 * How many consecutive days (ending today or yesterday) the user prayed all five daily prayers.
 *
 * Today is special: it is still in progress,
 * so it counts toward the streak only if all prayers are marked;
 * being unmarked doesn't break the streak, but a MISSED prayer does.
 */
class GetCurrentStreakUseCase @Inject constructor(
    private val prayerTrackerRepository: PrayerTrackerRepository,
    private val clock: Clock
) {
    /**
     * @return the current streak length in days;
     * 0 when today (or the most recent recorded day) already breaks it
     */
    suspend operator fun invoke(): Int {
        var streak = 0
        var day = clock.today()
        var isToday = true

        /** Walk back one window at a time and stop at the first day that breaks the streak:
         * a short streak reads one small window, a long one reads only as much as it needs,
         * and there is no artificial cap. A day with no records always ends the walk. */
        while (true) {
            val windowStart = day.minusDays(WINDOW_DAYS - 1)
            val records = prayerTrackerRepository.getRecordsForRange(windowStart, day)

            while (day >= windowStart) {
                val dayRecords = records[day].orEmpty()
                val allPrayed = PrayerName.majorEntries.all { dayRecords[it] == PrayerStatus.PRAYED }
                val hasMissed = dayRecords.values.any { it == PrayerStatus.MISSED }

                when {
                    allPrayed -> streak++
                    /** Today in progress: doesn't break the streak unless something was missed. */
                    isToday && !hasMissed -> Unit
                    else -> return streak
                }
                isToday = false
                day = day.minusDays(1)
            }
        }
    }

    /** Days fetched per repository call; a batching detail, not a streak cap. */
    private companion object {
        const val WINDOW_DAYS = 90L
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\GetDuePrayersUseCase.kt

```kotlin
package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

/**
 * The major prayers the user may mark on [date].
 *
 * - Past days: all of them.
 * - Future days: none.
 * - Today: only prayers whose time has arrived.
 *   If today's timings can't be found (no saved location or nothing cached),
 *   every prayer is allowed rather than blocking the user.
 */
class GetDuePrayersUseCase @Inject constructor(
    private val clock: Clock,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase
) {
    /**
     * @param date the day the user is looking at
     * @return the prayers that may be marked; empty for future days
     */
    suspend operator fun invoke(date: LocalDate): Set<PrayerName> {
        val allPrayers = PrayerName.majorEntries.toSet()
        val today = clock.today()

        return when {
            date.isAfter(today) -> emptySet()
            date.isBefore(today) -> allPrayers
            else -> dueToday(allPrayers, today)
        }
    }

    /** For today, a prayer is due once its time has arrived (equal counts as arrived). */
    private suspend fun dueToday(allPrayers: Set<PrayerName>, today: LocalDate): Set<PrayerName> {
        val location = getSavedLocationUseCase().first() ?: return allPrayers
        val timings = getCachedPrayerTimesUseCase(today.toDateKey(), location.coordinates)
            ?.timings ?: return allPrayers

        val timeNow = clock.now().toLocalTime()
        return allPrayers.filterTo(mutableSetOf()) {
            prayer -> !timeNow.isBefore(timings[prayer])
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\ObserveMonthDayStatusUseCase.kt

```kotlin
package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

/**
 * How each recorded day of [yearMonth] went, for the calendar colors.
 *
 * Mapping per day, first rule that applies wins: a day after today is [DayStatus.FUTURE];
 * any MISSED prayer makes it [DayStatus.HAS_MISSED];
 * all five major prayers PRAYED makes it [DayStatus.ALL_PRAYED];
 * anything else is [DayStatus.IN_PROGRESS].
 *
 * Days with no records at all are absent from the map,
 * so the calendar draws them as unmarked.
 */
class ObserveMonthDayStatusUseCase @Inject constructor(
    private val observePrayerRecordsForMonthUseCase: ObservePrayerRecordsForMonthUseCase,
    private val clock: Clock
) {
    /**
     * @param yearMonth the month to summarize
     * @return a live map of day to status for the days that have records
     */
    operator fun invoke(yearMonth: YearMonth): Flow<Map<LocalDate, DayStatus>> =
        observePrayerRecordsForMonthUseCase(yearMonth).map { recordsByDate ->
            val today = clock.today()
            recordsByDate.mapValues { (date, records) -> dayStatusFor(date, records, today) }
        }

    private fun dayStatusFor(
        date: LocalDate,
        records: Map<PrayerName, PrayerStatus>,
        today: LocalDate
    ): DayStatus = when {
        date.isAfter(today) -> DayStatus.FUTURE
        records.values.any { it == PrayerStatus.MISSED } -> DayStatus.HAS_MISSED
        PrayerName.majorEntries.all { records[it] == PrayerStatus.PRAYED } -> DayStatus.ALL_PRAYED
        else -> DayStatus.IN_PROGRESS
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\ObservePrayerRecordsForDateUseCase.kt

```kotlin
package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * Live records for one day, keyed by prayer; re-emits whenever they change.
 *
 * Thin pass-through over [PrayerTrackerRepository.observeRecordsForDate];
 * exists so presentation code goes through the standard use-case seam.
 */
class ObservePrayerRecordsForDateUseCase @Inject constructor(
    private val prayerTrackerRepository: PrayerTrackerRepository
) {
    /** @param date the day to observe */
    operator fun invoke(date: LocalDate) = prayerTrackerRepository.observeRecordsForDate(date)
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\ObservePrayerRecordsForMonthUseCase.kt

```kotlin
package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import java.time.YearMonth
import javax.inject.Inject

/**
 * Live records for every day of a month, keyed by date; re-emits on changes.
 *
 * Thin pass-through over [PrayerTrackerRepository.observeRecordsForMonth];
 * it is what the calendar's day colors are derived from.
 */
class ObservePrayerRecordsForMonthUseCase @Inject constructor(
    private val repository: PrayerTrackerRepository
) {
    /** @param yearMonth the month to observe */
    operator fun invoke(yearMonth: YearMonth) = repository.observeRecordsForMonth(yearMonth)
}
```

## app\src\main\java\com\mhq\salati\prayertracker\domain\usecases\SetPrayerStatusUseCase.kt

```kotlin
package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.PrayerName
import java.time.LocalDate
import javax.inject.Inject

/**
 * Records the user's marking for one prayer on one day.
 *
 * Thin pass-through over [PrayerTrackerRepository.setStatus];
 * whether the marking is allowed (the prayer is due) is decided upstream by GetDuePrayersUseCase.
 */
class SetPrayerStatusUseCase @Inject constructor(
    private val prayerTrackerRepository: PrayerTrackerRepository
) {
    /**
     * @param date the day being marked
     * @param prayer the prayer being marked
     * @param status the new status, replacing any previous one
     */
    suspend operator fun invoke(date: LocalDate, prayer: PrayerName, status: PrayerStatus) {
        prayerTrackerRepository.setStatus(date, prayer, status)
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\presentation\contract\PrayerTrackerContract.kt

```kotlin
package com.mhq.salati.prayertracker.presentation.contract

import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.prayertracker.presentation.model.PrayerStatusUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.presentation.model.MessageUi
import java.time.LocalDate
import java.time.YearMonth

object PrayerTrackerContract {
    data class State(
        val today: LocalDate,
        val selectedMonth: YearMonth,
        val selectedDate: LocalDate,
        val monthDayStatus: Map<LocalDate, DayStatusUi> = emptyMap(),
        val selectedDateRecords: Map<PrayerNameUi, PrayerStatusUi> = emptyMap(),
        val dialogPrayer: PrayerNameUi? = null,
        val currentStreak: Int = 0,
        val isLoading: Boolean = true,
        val duePrayers: Set<PrayerNameUi> = PrayerNameUi.majorEntries.toSet(),
        val hijriDateOffset: Int = 0
    )

    sealed interface Intent {
        data class DateSelected(val date: LocalDate) : Intent
        data class MonthChanged(val delta: Int) : Intent
        data class PrayerSelected(val prayer: PrayerNameUi) : Intent
        data object ConfirmPrayed : Intent
        data object ConfirmMissed : Intent
        data object DialogDismissed : Intent
        data object ScreenResumed : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\presentation\mapper\PrayerTrackerUiMapper.kt

```kotlin
package com.mhq.salati.prayertracker.presentation.mapper

import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.prayertracker.presentation.model.PrayerStatusUi
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi

fun DayStatus.toUi(): DayStatusUi = when (this) {
    DayStatus.FUTURE -> DayStatusUi.FUTURE
    DayStatus.ALL_PRAYED -> DayStatusUi.ALL_PRAYED
    DayStatus.HAS_MISSED -> DayStatusUi.HAS_MISSED
    DayStatus.IN_PROGRESS -> DayStatusUi.IN_PROGRESS
}

fun PrayerStatus.toUi(): PrayerStatusUi = when (this) {
    PrayerStatus.PENDING -> PrayerStatusUi.PENDING
    PrayerStatus.PRAYED -> PrayerStatusUi.PRAYED
    PrayerStatus.MISSED -> PrayerStatusUi.MISSED
}

fun PrayerStatusUi.toDomain(): PrayerStatus = when (this) {
    PrayerStatusUi.PENDING -> PrayerStatus.PENDING
    PrayerStatusUi.PRAYED -> PrayerStatus.PRAYED
    PrayerStatusUi.MISSED -> PrayerStatus.MISSED
}

fun Map<PrayerName, PrayerStatus>.toUiMap(): Map<PrayerNameUi, PrayerStatusUi> =
    entries.associate { (name, status) -> name.toUi() to status.toUi() }
```

## app\src\main\java\com\mhq\salati\prayertracker\presentation\model\DayStatusUi.kt

```kotlin
package com.mhq.salati.prayertracker.presentation.model

enum class DayStatusUi {
    FUTURE,
    ALL_PRAYED,
    HAS_MISSED,
    IN_PROGRESS
}
```

## app\src\main\java\com\mhq\salati\prayertracker\presentation\model\PrayerStatusUi.kt

```kotlin
package com.mhq.salati.prayertracker.presentation.model

enum class PrayerStatusUi {
    PENDING,
    PRAYED,
    MISSED
}
```

## app\src\main\java\com\mhq\salati\prayertracker\presentation\viewmodel\PrayerTrackerViewModel.kt

```kotlin
package com.mhq.salati.prayertracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.usecases.GetCurrentStreakUseCase
import com.mhq.salati.prayertracker.domain.usecases.GetDuePrayersUseCase
import com.mhq.salati.prayertracker.domain.usecases.ObservePrayerRecordsForDateUseCase
import com.mhq.salati.prayertracker.domain.usecases.ObserveMonthDayStatusUseCase
import com.mhq.salati.prayertracker.domain.usecases.SetPrayerStatusUseCase
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract
import com.mhq.salati.prayertracker.presentation.mapper.toUi
import com.mhq.salati.prayertracker.presentation.mapper.toUiMap
import com.mhq.salati.settings.domain.usecases.ObserveHijriDateOffsetUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.presentation.mapper.toDomain
import com.mhq.salati.shared.presentation.mapper.toUiSet
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PrayerTrackerViewModel @Inject constructor(
    private val clock: Clock,
    private val observeMonthDayStatusUseCase: ObserveMonthDayStatusUseCase,
    private val observePrayerRecordsForDateUseCase: ObservePrayerRecordsForDateUseCase,
    private val setPrayerStatusUseCase: SetPrayerStatusUseCase,
    private val getCurrentStreakUseCase: GetCurrentStreakUseCase,
    private val getDuePrayersUseCase: GetDuePrayersUseCase,
    private val observeHijriDateOffsetUseCase: ObserveHijriDateOffsetUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        clock.today().let { today ->
            PrayerTrackerContract.State(
                today = today,
                selectedMonth = YearMonth.from(today),
                selectedDate = today
            )
        }
    )

    val state = _state.asStateFlow()

    private val _effect = Channel<PrayerTrackerContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        state.map { it.selectedMonth }
            .distinctUntilChanged()
            .flatMapLatest { month -> observeMonthDayStatusUseCase(month) }
            .onEach { statusByDate ->
                _state.update {
                    it.copy(
                        today = clock.today(),
                        monthDayStatus = statusByDate.mapValues { (_, status) -> status.toUi() },
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)

        state.map { it.selectedDate }
            .distinctUntilChanged()
            .flatMapLatest { date -> observePrayerRecordsForDateUseCase(date) }
            .onEach { records ->
                _state.update { it.copy(selectedDateRecords = records.toUiMap()) }
            }
            .launchIn(viewModelScope)

        observeHijriDateOffsetUseCase()
            .onEach { offset -> _state.update { it.copy(hijriDateOffset = offset) } }
            .launchIn(viewModelScope)

        refreshStreak()
    }

    fun onIntent(intent: PrayerTrackerContract.Intent) {
        when (intent) {
            is PrayerTrackerContract.Intent.DateSelected -> {
                val today = clock.today()
                if (intent.date.isAfter(today)) return
                _state.update {
                    it.copy(selectedDate = intent.date, selectedDateRecords = emptyMap(), today = today)
                }
                refreshDuePrayers()
            }

            is PrayerTrackerContract.Intent.MonthChanged -> {
                val newMonth = _state.value.selectedMonth.plusMonths(intent.delta.toLong())
                if (newMonth.isAfter(YearMonth.from(clock.today()))) return
                _state.update { it.copy(selectedMonth = newMonth) }
            }

            is PrayerTrackerContract.Intent.PrayerSelected -> {
                // Re-checked at tap time, so a stale screen can never mark a prayer that isn't due.
                viewModelScope.launch {
                    val due = getDuePrayersUseCase(_state.value.selectedDate)
                    _state.update { it.copy(duePrayers = due.toUiSet()) }
                    if (intent.prayer.toDomain() in due) {
                        _state.update { it.copy(dialogPrayer = intent.prayer) }
                    }
                }
            }

            PrayerTrackerContract.Intent.ScreenResumed -> refreshDuePrayers()
            PrayerTrackerContract.Intent.ConfirmPrayed -> applyDialogResult(PrayerStatus.PRAYED)
            PrayerTrackerContract.Intent.ConfirmMissed -> applyDialogResult(PrayerStatus.MISSED)
            PrayerTrackerContract.Intent.DialogDismissed -> _state.update { it.copy(dialogPrayer = null) }
        }
    }

    private fun refreshDuePrayers() {
        viewModelScope.launch {
            try {
                val due = getDuePrayersUseCase(_state.value.selectedDate)
                _state.update { it.copy(today = clock.today(), duePrayers = due.toUiSet()) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Keep the previous lock state; the tap-time check still protects marking.
            }
        }
    }

    private fun applyDialogResult(status: PrayerStatus) {
        val prayer = _state.value.dialogPrayer?.toDomain() ?: return
        val date = _state.value.selectedDate
        viewModelScope.launch {
            try {
                setPrayerStatusUseCase(date, prayer, status)
                _state.update { it.copy(dialogPrayer = null) }
                refreshStreak()
            } catch (e: Exception) {
                _state.update { it.copy(dialogPrayer = null) }
                _effect.send(
                    PrayerTrackerContract.Effect.ShowError(
                        MessageUi.FAILED_TO_UPDATE_PRAYER_STATUS
                    )
                )
            }
        }
    }

    private fun refreshStreak() {
        viewModelScope.launch {
            _state.update { it.copy(currentStreak = getCurrentStreakUseCase()) }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\components\CalendarDayCell.kt

```kotlin
package com.mhq.salati.prayertracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme

/**
 * One day of the month grid.
 *
 * Font sizes are DERIVED from the cell's own size (not fixed sp values) and use a tight line height
 * with trimmed font padding, so the two numbers always fit inside the cell: narrow phones, large
 * screens, landscape and Arabic digits can no longer push the content out of the cell.
 *
 * - Portrait / tall screens: square cell, Gregorian over Hijri, status dot at the bottom.
 * - Wide screens (landscape, tablets): the cell fills the row height; Gregorian and Hijri share one line.
 */
@Composable
fun CalendarDayCell(
    gregorianDay: Int,
    hijriDay: Int,
    isCurrentMonth: Boolean,
    isFuture: Boolean = false,
    isSelected: Boolean,
    status: DayStatusUi?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isWide = isWideLayout()

    val dotIndicatorColor = when (status) {
        DayStatusUi.ALL_PRAYED -> MaterialTheme.colorScheme.primary
        DayStatusUi.HAS_MISSED -> MaterialTheme.colorScheme.error
        DayStatusUi.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
        else -> null
    }

    val cellBackgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        !isCurrentMonth -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surface
    }

    val baseTextColor = MaterialTheme.colorScheme.onSurface

    val gregorianColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.38f)
        else -> baseTextColor
    }
    val hijriColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.24f)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val cellBorderColor = if (isCurrentMonth && !isFuture) {
        MaterialTheme.colorScheme.outlineVariant
    } else {
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.15f)
    }

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .then(if (isWide) Modifier.fillMaxSize() else Modifier.aspectRatio(1f))
            .clip(RoundedCornerShape(8.dp))
            .background(cellBackgroundColor)
            .border(1.dp, cellBorderColor, RoundedCornerShape(8.dp))
            .clickable(enabled = isCurrentMonth && !isFuture, onClick = onClick)
            .padding(2.dp)
    ) {
        if (isWide) {
            /** Single line: size follows the row height, capped by the width so both numbers fit side by side */
            val gregorianSp = minOf(maxHeight.value * 0.70f, maxWidth.value * 0.32f).coerceIn(9f, 14f)
            val hijriSp = minOf(maxHeight.value * 0.58f, maxWidth.value * 0.26f).coerceIn(8f, 12f)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = gregorianDay.toString(),
                    fontWeight = FontWeight.Medium,
                    color = gregorianColor,
                    style = dayTextStyle(gregorianSp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = hijriDay.toString(),
                    fontWeight = FontWeight.Light,
                    color = hijriColor,
                    style = dayTextStyle(hijriSp)
                )
            }

            /** Status dot pinned to the bottom center of the cell */
            StatusDot(
                color = dotIndicatorColor,
                size = 3.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 3.dp)
            )
        } else {
            /** Both numbers scale with the smaller cell side, so the stack + dot always fit */
            val side = minOf(maxWidth, maxHeight).value
            val gregorianSp = (side * 0.40f).coerceIn(10f, 20f)
            val hijriSp = (side * 0.29f).coerceIn(8f, 14f)

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /** Number stack centered in the space above the dot,
                 * so the Hijri number always sits high with clear room below it */
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = gregorianDay.toString(),
                        fontWeight = FontWeight.Medium,
                        color = gregorianColor,
                        style = dayTextStyle(gregorianSp)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = hijriDay.toString(),
                        fontWeight = FontWeight.Light,
                        color = hijriColor,
                        style = dayTextStyle(hijriSp)
                    )
                }

                StatusDot(
                    color = dotIndicatorColor,
                    size = 4.dp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}

/**
 * Tight text style for day numbers: line height is ~1.15x the font size and the extra font
 * padding is trimmed on both sides, so Arabic digits / fonts with tall metrics can't add height.
 */
private fun dayTextStyle(sizeSp: Float) = TextStyle(
    fontSize = sizeSp.sp,
    lineHeight = (sizeSp * 1.15f).sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    )
)

/** Reserves its space even when [color] is null so cells without a status keep the same layout. */
@Composable
private fun StatusDot(
    color: androidx.compose.ui.graphics.Color?,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .size(size)
            .then(if (color != null) Modifier.background(color, CircleShape) else Modifier)
    )
}

@Preview
@Composable
private fun CalendarDayCellPreview() {
    SalatiTheme {
        CalendarDayCell(
            gregorianDay = 1,
            hijriDay = 1,
            isSelected = true,
            isCurrentMonth = true,
            status = DayStatusUi.ALL_PRAYED,
            onClick = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\components\CalendarMonthView.kt

```kotlin
package com.mhq.salati.prayertracker.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.shared.ui.components.ShrinkToFitText
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.chrono.HijrahChronology
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.time.temporal.WeekFields

/**
 * Month grid with Gregorian + Hijri dates.
 *
 * - Tall screens: square cells, width comes from the caller ([modifier]).
 * - Wide screens: the CALLER must give this a bounded height (see PrayerTrackerContent); the rows
 *   then share that height equally with weight(1f).
 *
 * Header and weekday labels are single-line and shrink to fit, so Arabic names (long short-day
 * names, long Hijri month names) never wrap and grow the header.
 */
@Composable
fun CalendarMonthView(
    selectedMonth: YearMonth,
    selectedDate: LocalDate,
    today: LocalDate,
    dayStatus: Map<LocalDate, DayStatusUi>,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    hijriOffsetDays: Int = 0,
) {
    val isWide = isWideLayout()

    val locale = LocalLocale.current.platformLocale
    val hijriChronology = HijrahChronology.INSTANCE

    val firstDayOfTheWeek = WeekFields.of(locale).firstDayOfWeek
    val firstDayOfTheWeekValue = firstDayOfTheWeek.value
    val firstDayOfTheMonth = selectedMonth.atDay(1)
    val firstOfTheMonthDayValue = firstDayOfTheMonth.dayOfWeek.value
    val firstDayOfWeekIndex = (firstOfTheMonthDayValue - firstDayOfTheWeekValue + 7) % 7
    val totalDays = selectedMonth.lengthOfMonth()

    val rows = (totalDays + firstDayOfWeekIndex + 6) / 7

    val gridDates = remember(selectedMonth, firstDayOfWeekIndex, rows) {
        val dates = mutableListOf<LocalDate>()
        for (row in 0 until rows) {
            for (col in 0 until 7) {
                val cellIndex = row * 7 + col
                val dayNum = cellIndex - firstDayOfWeekIndex + 1
                val cellDate = when {
                    dayNum < 1 -> {
                        val previousMonth = selectedMonth.minusMonths(1)
                        previousMonth.atDay(previousMonth.lengthOfMonth() + dayNum)
                    }
                    dayNum > totalDays -> {
                        selectedMonth.plusMonths(1).atDay(dayNum - totalDays)
                    }
                    else -> selectedMonth.atDay(dayNum)
                }
                dates.add(cellDate)
            }
        }
        dates
    }

    val hijriDatesMap = remember(gridDates, hijriOffsetDays) {
        gridDates.associateWith { date ->
            hijriChronology.date(date.plusDays(hijriOffsetDays.toLong()))
        }
    }

    val hijriHeaderFormatter = remember(locale) { DateTimeFormatter.ofPattern("MMMM y", locale) }

    val gregorianHeader = "${selectedMonth.month.getDisplayName(TextStyle.FULL, locale)} ${selectedMonth.year}"
    val hijriHeader = hijriHeaderFormatter.format(hijriDatesMap[firstDayOfTheMonth]!!)

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onMonthChanged(-1) }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.previous_month),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            if (isWide) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.weight(1f)
                ) {
                    ShrinkToFitText(
                        text = gregorianHeader,
                        fontSize = 16.sp,
                        minFontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    ShrinkToFitText(
                        text = hijriHeader,
                        fontSize = 13.sp,
                        minFontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1.4f, fill = false)
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    ShrinkToFitText(
                        text = gregorianHeader,
                        fontSize = 20.sp,
                        minFontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    ShrinkToFitText(
                        text = hijriHeader,
                        fontSize = 16.sp,
                        minFontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            val canGoForward = selectedMonth.isBefore(YearMonth.from(today))
            IconButton(onClick = { onMonthChanged(1) }, enabled = canGoForward) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.next_month),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = if (canGoForward) 1f else 0.38f)
                )
            }
        }
        Spacer(modifier = Modifier.height(if (isWide) 1.dp else 8.dp))

        val weekDays = (0 until 7).map { firstDayOfTheWeek.plus(it.toLong()) }
        Row(modifier = Modifier.fillMaxWidth()) {
            weekDays.forEach { dayOfWeek ->
                ShrinkToFitText(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                        .replaceFirstChar { it.uppercase() },
                    fontSize = if (isWide) 12.sp else 16.sp,
                    minFontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(if (isWide) 2.dp else 6.dp))

        for (row in 0 until rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (isWide) Modifier.weight(1f) else Modifier)
            ) {
                for (col in 0 until 7) {
                    val cellIndex = row * 7 + col
                    val cellDate = gridDates[cellIndex]
                    val hijriDate = hijriDatesMap[cellDate]!!
                    val isCurrentMonth = cellDate.month == selectedMonth.month

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .then(if (isWide) Modifier.fillMaxHeight() else Modifier.aspectRatio(1f))
                            .padding(if (isWide) 1.dp else 2.dp)
                    ) {
                        CalendarDayCell(
                            gregorianDay = cellDate.dayOfMonth,
                            hijriDay = hijriDate.get(ChronoField.DAY_OF_MONTH),
                            isSelected = isCurrentMonth && cellDate == selectedDate,
                            isFuture = cellDate.isAfter(today),
                            isCurrentMonth = isCurrentMonth,
                            status = if (isCurrentMonth) dayStatus[cellDate] ?: DayStatusUi.FUTURE else null,
                            onClick = { onDateSelected(cellDate) },
                            modifier = if (isWide) Modifier.padding(vertical = 1.dp) else Modifier
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CalendarMonthViewPreview() {
    SalatiTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        ) {
            CalendarMonthView(
                selectedMonth = YearMonth.of(2026, 10),
                selectedDate = LocalDate.of(2026, 10, 8),
                today = LocalDate.of(2026, 10, 8),
                dayStatus = mapOf(
                    LocalDate.of(2026, 10, 7) to DayStatusUi.ALL_PRAYED,
                    LocalDate.of(2026, 10, 8) to DayStatusUi.IN_PROGRESS
                ),
                onDateSelected = {},
                onMonthChanged = {},
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\components\DialogChoiceButton.kt

```kotlin
package com.mhq.salati.prayertracker.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.components.ShrinkToFitText
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun DialogChoiceButton(
    label: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color.copy(alpha = 0.12f))
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        ShrinkToFitText(
            text = label,
            fontSize = 13.sp,
            minFontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DialogChoiceButtonPreview() {
    SalatiTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            DialogChoiceButton(
                label = "Mark as Prayed",
                icon = Icons.Default.Check,
                color = MaterialTheme.colorScheme.primary,
                onClick = {}
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\components\PrayerConfirmDialog.kt

```kotlin
package com.mhq.salati.prayertracker.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme


@Composable
fun PrayerConfirmDialog(
    prayer: PrayerNameUi,
    onConfirmPrayed: () -> Unit,
    onConfirmMissed: () -> Unit,
    onDismiss: () -> Unit
) {
    val prayerLabel = stringResource(prayer.labelRes)

    Dialog(onDismissRequest = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
        ) {
            Text(
                text = stringResource(R.string.did_you_pray_x, prayerLabel),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.mark_todays_x_prayer, prayerLabel),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DialogChoiceButton(
                    label = stringResource(R.string.prayed),
                    icon = Icons.Filled.Check,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = onConfirmPrayed,
                    modifier = Modifier.weight(1f)
                )
                DialogChoiceButton(
                    label = stringResource(R.string.missed),
                    icon = Icons.Filled.Close,
                    color = MaterialTheme.colorScheme.error,
                    onClick = onConfirmMissed,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrayerConfirmDialogPreview() {
    SalatiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.5f))
        ) {
            PrayerConfirmDialog(
                prayer = PrayerNameUi.FAJR,
                onConfirmPrayed = {},
                onConfirmMissed = {},
                onDismiss = {}
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\components\PrayerStatusCard.kt

```kotlin
package com.mhq.salati.prayertracker.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.presentation.model.PrayerStatusUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.components.ShrinkToFitText
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun PrayerStatusCard(
    prayer: PrayerNameUi,
    status: PrayerStatusUi,
    locked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && !locked) 0.96f else 1f,
        animationSpec = tween(120),
        label = "cardScale"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .shadow(
                elevation = if (locked) 0.dp else 3.dp,
                shape = RoundedCornerShape(18.dp),
                clip = false,
                ambientColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                spotColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
            )
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .then(
                if (!locked) Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
                    shape = RoundedCornerShape(18.dp)
                ) else Modifier
            )
            .clickable(
                enabled = !locked,
                interactionSource = interactionSource,
                indication = ripple(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)),
                onClick = onClick
            )
            .padding(horizontal = 6.dp, vertical = 14.dp)
    ) {
        ShrinkToFitText(
            text = stringResource(prayer.labelRes),
            fontSize = 14.sp,
            minFontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color =
                if (locked)
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                else
                    MaterialTheme.colorScheme.onSurface
        )

        AnimatedContent(
            targetState = status,
            transitionSpec = {
                (
                        scaleIn(
                            initialScale = 0.7f,
                            animationSpec = tween(200)
                        )
                                + fadeIn(tween(200))
                        )
                    .togetherWith(fadeOut(tween(120)))
            },
            label = "statusBadge"
        ) { animatedStatus ->
            when (animatedStatus) {
                PrayerStatusUi.PRAYED -> StatusBadge(
                    icon = Icons.Filled.Check,
                    color = MaterialTheme.colorScheme.primary
                )

                PrayerStatusUi.MISSED -> StatusBadge(
                    icon = Icons.Filled.Close,
                    color = MaterialTheme.colorScheme.error
                )

                PrayerStatusUi.PENDING -> Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.14f),
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.22f)
                                )
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.25f),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrayerStatusCardPreview() {
    SalatiTheme {
        PrayerStatusCard(
            prayer = PrayerNameUi.FAJR,
            status = PrayerStatusUi.PRAYED,
            locked = true,
            onClick = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\components\PrayerTrackerList.kt

```kotlin
package com.mhq.salati.prayertracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.model.PrayerStatusUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate

/**
 * Title + the five prayer status cards in one row.
 * On wide screens the pane is narrower, so the gap between cards is tighter to leave room for labels.
 */
@Composable
fun PrayerStatusList(
    selectedDate: LocalDate,
    today: LocalDate,
    duePrayers: Set<PrayerNameUi>,
    records: Map<PrayerNameUi, PrayerStatusUi>,
    onPrayerTapped: (PrayerNameUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val isFutureDate = selectedDate.isAfter(today)
    val hasUnduePrayers = selectedDate == today && PrayerNameUi.majorEntries.any { it !in duePrayers }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.track_your_prayers),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(if (isWideLayout()) 6.dp else 10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PrayerNameUi.majorEntries.forEach { prayer ->
                val status = records[prayer] ?: PrayerStatusUi.PENDING
                PrayerStatusCard(
                    prayer = prayer,
                    status = status,
                    locked = isFutureDate || (selectedDate == today && prayer !in duePrayers),
                    onClick = { onPrayerTapped(prayer) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (hasUnduePrayers) {
            Text(
                text = stringResource(R.string.prayer_marking_opens_at_prayer_time),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PrayerStatusListPreview() {
    SalatiTheme() {
        PrayerStatusList(
            selectedDate = LocalDate.of(2026, 10, 8),
            duePrayers = PrayerNameUi.majorEntries.toSet(),
            today = LocalDate.of(2026, 10, 8),
            records = emptyMap(),
            onPrayerTapped = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\components\StreakBanner.kt

```kotlin
package com.mhq.salati.prayertracker.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun StreakBanner(
    streak: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Icon(
            Icons.Filled.LocalFireDepartment,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = pluralStringResource(R.plurals.day_streak, streak, streak),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StreakBannerPreview() {
    SalatiTheme {
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)) {
            StreakBanner(streak = 14)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\components\TrackerBadge.kt

```kotlin
package com.mhq.salati.prayertracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun StatusBadge(
    icon: ImageVector,
    color: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.15f)),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(R.string.cd_status_badge),
            tint = color,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview
@Composable
private fun StatusBadgePreview() {
    SalatiTheme() {
        StatusBadge(
            icon = Icons.Filled.CheckCircle,
            color = Color.Yellow
        )
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\screens\PrayerTrackerContainer.kt

```kotlin
package com.mhq.salati.prayertracker.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.prayertracker.ui.components.PrayerConfirmDialog
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract
import com.mhq.salati.prayertracker.presentation.viewmodel.PrayerTrackerViewModel
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.shared.ui.asString
import kotlinx.coroutines.launch

@Composable
fun PrayerTrackerContainer(
    prayerTrackerViewModel: PrayerTrackerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by prayerTrackerViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        prayerTrackerViewModel.effect.collect { effect ->
            when (effect) {
                is PrayerTrackerContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
            }
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        prayerTrackerViewModel.onIntent(PrayerTrackerContract.Intent.ScreenResumed)
    }

    PrayerTrackerContent(
        state = state,
        onIntent = prayerTrackerViewModel::onIntent
    )

    state.dialogPrayer?.let { prayer ->
        PrayerConfirmDialog(
            prayer = prayer,
            onConfirmPrayed = { prayerTrackerViewModel.onIntent(PrayerTrackerContract.Intent.ConfirmPrayed) },
            onConfirmMissed = { prayerTrackerViewModel.onIntent(PrayerTrackerContract.Intent.ConfirmMissed) },
            onDismiss = { prayerTrackerViewModel.onIntent(PrayerTrackerContract.Intent.DialogDismissed) }
        )
    }
}
```

## app\src\main\java\com\mhq\salati\prayertracker\ui\screens\PrayerTrackerContent.kt

```kotlin
package com.mhq.salati.prayertracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.State
import com.mhq.salati.prayertracker.ui.components.CalendarMonthView
import com.mhq.salati.prayertracker.ui.components.PrayerStatusList
import com.mhq.salati.prayertracker.ui.components.StreakBanner
import com.mhq.salati.shared.ui.ScreenHorizontalPadding
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.YearMonth

/** Tall screens: content never stretches wider than this (big portrait tablets), centered. */
private val TrackerPortraitMaxWidth = 560.dp

/** Wide screens (landscape, tablets, split-screen). */
private object TrackerWideLayout {
    /** Calendar never gets wider than this inside its half of the screen. */
    val CalendarMaxWidth = 340.dp
    /** Below this height the calendar stops shrinking and the pane scrolls instead. */
    val CalendarMinHeight = 216.dp
    /** Calendar height is capped at width x this, so cells don't become tall slivers on tablets. */
    const val CalendarMaxHeightPerWidth = 1.2f
    val PaneSpacing = 24.dp
}

@Composable
fun PrayerTrackerContent(
    state: State,
    onIntent: (Intent) -> Unit
) {
    val isWide = isWideLayout()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = BottomNavBarDefaults.Height)
        ) {
            TabHeader(
                icon = Icons.Default.CheckCircle,
                title = stringResource(R.string.tracker),
                subtitle = stringResource(R.string.track_your_five_daily_prayers),
                trailingContent = {
                    if (state.currentStreak > 0) {
                        StreakBanner(state.currentStreak)
                    }
                }
            )

            if (isWide) {
                TrackerWideContent(state = state, onIntent = onIntent)
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = ScreenHorizontalPadding, vertical = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    CalendarMonthView(
                        selectedMonth = state.selectedMonth,
                        selectedDate = state.selectedDate,
                        today = state.today,
                        dayStatus = state.monthDayStatus,
                        onDateSelected = { onIntent(Intent.DateSelected(it)) },
                        onMonthChanged = { onIntent(Intent.MonthChanged(it)) },
                        hijriOffsetDays = state.hijriDateOffset,
                        modifier = Modifier
                            .widthIn(max = TrackerPortraitMaxWidth)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    PrayerStatusList(
                        duePrayers = state.duePrayers,
                        selectedDate = state.selectedDate,
                        today = state.today,
                        records = state.selectedDateRecords,
                        onPrayerTapped = { onIntent(Intent.PrayerSelected(it)) },
                        modifier = Modifier
                            .widthIn(max = TrackerPortraitMaxWidth)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * Two panes side by side: calendar (left) and prayer checklist (right).
 *
 * - Both panes scroll when the screen is too short (small landscape, large font scale, Arabic).
 *   `fillMaxHeight().verticalScroll()` keeps the content centered while it fits.
 * - The calendar is given an explicit, bounded height (needed for its weighted rows); it is the
 *   available height, clamped between [TrackerWideLayout.CalendarMinHeight] and width x aspect cap.
 * - Display-cutout insets keep both panes clear of a notch in landscape.
 */
@Composable
private fun TrackerWideContent(
    state: State,
    onIntent: (Intent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(TrackerWideLayout.PaneSpacing),
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
            .padding(horizontal = ScreenHorizontalPadding, vertical = 4.dp)
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            val calendarWidth: Dp = maxWidth.coerceAtMost(TrackerWideLayout.CalendarMaxWidth)
            val calendarHeight: Dp = maxHeight
                .coerceAtMost(calendarWidth * TrackerWideLayout.CalendarMaxHeightPerWidth)
                .coerceAtLeast(TrackerWideLayout.CalendarMinHeight)

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CalendarMonthView(
                    selectedMonth = state.selectedMonth,
                    selectedDate = state.selectedDate,
                    today = state.today,
                    dayStatus = state.monthDayStatus,
                    onDateSelected = { onIntent(Intent.DateSelected(it)) },
                    onMonthChanged = { onIntent(Intent.MonthChanged(it)) },
                    hijriOffsetDays = state.hijriDateOffset,
                    modifier = Modifier
                        .width(calendarWidth)
                        .height(calendarHeight)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            PrayerStatusList(
                duePrayers = state.duePrayers,
                selectedDate = state.selectedDate,
                today = state.today,
                records = state.selectedDateRecords,
                onPrayerTapped = { onIntent(Intent.PrayerSelected(it)) }
            )
        }
    }
}

@Preview(name = "Portrait", showSystemUi = true)
@Preview(name = "Landscape", device = "spec:width=800dp,height=400dp,dpi=440", showSystemUi = true)
@Preview(name = "Small landscape", device = "spec:width=640dp,height=320dp,dpi=420", showSystemUi = true)
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240", showSystemUi = true)
@Composable
private fun PrayerTrackerContentPreview() {
    SalatiTheme {
        PrayerTrackerContent(
            state = State(
                today = LocalDate.of(2026, 10, 8),
                selectedMonth = YearMonth.of(2026, 10),
                selectedDate = LocalDate.of(2026, 10, 8)
            ),
            onIntent = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\data\repoimpl\CompassRepoImpl.kt

```kotlin
package com.mhq.salati.qibla.data.repoimpl

import com.mhq.salati.qibla.datasource.device.CompassSample
import com.mhq.salati.qibla.datasource.device.RotationSensorDataSource
import com.mhq.salati.qibla.datasource.device.RotationSensorUnavailableException
import com.mhq.salati.qibla.datasource.device.SensorAccuracy
import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.qibla.domain.model.CompassReading
import com.mhq.salati.qibla.domain.model.CompassUnavailableException
import com.mhq.salati.qibla.domain.repo.CompassRepository
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompassRepoImpl @Inject constructor(
    private val rotationSensorDataSource: RotationSensorDataSource
) : CompassRepository {

    override fun headings(coordinates: Coordinates): Flow<CompassReading> =
        rotationSensorDataSource
            .headings(coordinates.latitude, coordinates.longitude)
            .map { it.toDomain() }
            .catch { e ->
                throw if (e is RotationSensorUnavailableException) CompassUnavailableException() else e
            }

    private fun CompassSample.toDomain() = CompassReading(
        headingDegrees = headingDegrees,
        accuracy = accuracy.toDomain()
    )

    private fun SensorAccuracy.toDomain() = when (this) {
        SensorAccuracy.HIGH -> CompassAccuracy.HIGH
        SensorAccuracy.MEDIUM -> CompassAccuracy.MEDIUM
        SensorAccuracy.LOW -> CompassAccuracy.LOW
        SensorAccuracy.UNRELIABLE -> CompassAccuracy.UNRELIABLE
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\datasource\device\AndroidRotationSensorDataSource.kt

```kotlin
package com.mhq.salati.qibla.datasource.device

import android.content.Context
import android.hardware.GeomagneticField
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.display.DisplayManager
import android.view.Display
import android.view.Surface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AndroidRotationSensorDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : RotationSensorDataSource {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val rotationSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val magneticFieldSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val displayManager =
        context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

    override fun headings(latitude: Double, longitude: Double): Flow<CompassSample> = callbackFlow {
        if (rotationSensor == null) {
            close(RotationSensorUnavailableException())
            return@callbackFlow
        }

        val rotationMatrix = FloatArray(9)
        val adjustedMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)
        var currentAccuracy = SensorAccuracy.HIGH

        val declination = GeomagneticField(
            latitude.toFloat(),
            longitude.toFloat(),
            0f,
            System.currentTimeMillis()
        ).declination

        val rotationListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

                /** The remap must follow the display's current rotation (0/90/180/270). */
                val rotation = displayManager.getDisplay(Display.DEFAULT_DISPLAY)?.rotation
                    ?: Surface.ROTATION_0

                when (rotation) {
                    Surface.ROTATION_90 -> {
                        SensorManager.remapCoordinateSystem(
                            rotationMatrix,
                            SensorManager.AXIS_Y,
                            SensorManager.AXIS_MINUS_X,
                            adjustedMatrix
                        )
                    }
                    Surface.ROTATION_180 -> {
                        SensorManager.remapCoordinateSystem(
                            rotationMatrix,
                            SensorManager.AXIS_MINUS_X,
                            SensorManager.AXIS_MINUS_Y,
                            adjustedMatrix
                        )
                    }
                    Surface.ROTATION_270 -> {
                        SensorManager.remapCoordinateSystem(
                            rotationMatrix,
                            SensorManager.AXIS_MINUS_Y,
                            SensorManager.AXIS_X,
                            adjustedMatrix
                        )
                    }
                    else -> {
                        System.arraycopy(rotationMatrix, 0, adjustedMatrix, 0, rotationMatrix.size)
                    }
                }

                SensorManager.getOrientation(adjustedMatrix, orientationAngles)

                val azimuthRadians = orientationAngles[0]
                var magneticAzimuth = Math.toDegrees(azimuthRadians.toDouble()).toFloat()
                if (magneticAzimuth < 0) magneticAzimuth += 360f

                var trueAzimuth = magneticAzimuth + declination
                if (trueAzimuth < 0) trueAzimuth += 360f
                if (trueAzimuth >= 360) trueAzimuth -= 360f

                trySend(CompassSample(trueAzimuth, currentAccuracy))
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        val magneticFieldListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {}

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                currentAccuracy = when (accuracy) {
                    SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> SensorAccuracy.HIGH
                    SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> SensorAccuracy.MEDIUM
                    SensorManager.SENSOR_STATUS_ACCURACY_LOW -> SensorAccuracy.LOW
                    else -> SensorAccuracy.UNRELIABLE
                }
            }
        }

        sensorManager.registerListener(rotationListener, rotationSensor, SensorManager.SENSOR_DELAY_UI)
        magneticFieldSensor?.let {
            sensorManager.registerListener(magneticFieldListener, it, SensorManager.SENSOR_DELAY_UI)
        }

        awaitClose {
            sensorManager.unregisterListener(rotationListener)
            sensorManager.unregisterListener(magneticFieldListener)
        }
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\datasource\device\CompassSample.kt

```kotlin
package com.mhq.salati.qibla.datasource.device

/** A heading measured against true north, with the sensor's accuracy at that moment. */
data class CompassSample(
    val headingDegrees: Float,
    val accuracy: SensorAccuracy
)
```

## app\src\main\java\com\mhq\salati\qibla\datasource\device\RotationSensorDatasource.kt

```kotlin
package com.mhq.salati.qibla.datasource.device

import kotlinx.coroutines.flow.Flow

interface RotationSensorDataSource {
    /** Fails with an exception when the device has no rotation vector sensor. */
    fun headings(latitude: Double, longitude: Double): Flow<CompassSample>
}
```

## app\src\main\java\com\mhq\salati\qibla\datasource\device\RotationSensorUnavailableException.kt

```kotlin
package com.mhq.salati.qibla.datasource.device

class RotationSensorUnavailableException :
    IllegalStateException("Rotation vector sensor not available on this device")
```

## app\src\main\java\com\mhq\salati\qibla\datasource\device\SensorAccuracy.kt

```kotlin
package com.mhq.salati.qibla.datasource.device

/** How much the sensor trusts its own reading. */
enum class SensorAccuracy { UNRELIABLE, LOW, MEDIUM, HIGH }
```

## app\src\main\java\com\mhq\salati\qibla\di\QiblaModule.kt

```kotlin
package com.mhq.salati.qibla.di

import com.mhq.salati.qibla.data.repoimpl.CompassRepoImpl
import com.mhq.salati.qibla.datasource.device.AndroidRotationSensorDataSource
import com.mhq.salati.qibla.datasource.device.RotationSensorDataSource
import com.mhq.salati.qibla.domain.repo.CompassRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QiblaModule {

    @Binds
    @Singleton
    abstract fun bindCompassRepository(impl: CompassRepoImpl): CompassRepository

    @Binds
    @Singleton
    abstract fun bindRotationSensorDataSource(impl: AndroidRotationSensorDataSource): RotationSensorDataSource
}
```

## app\src\main\java\com\mhq\salati\qibla\domain\model\CompassAccuracy.kt

```kotlin
package com.mhq.salati.qibla.domain.model

enum class CompassAccuracy {
    UNRELIABLE,
    LOW,
    MEDIUM,
    HIGH
}
```

## app\src\main\java\com\mhq\salati\qibla\domain\model\CompassReading.kt

```kotlin
package com.mhq.salati.qibla.domain.model

data class CompassReading(
    val headingDegrees: Float,
    val accuracy: CompassAccuracy
)
```

## app\src\main\java\com\mhq\salati\qibla\domain\model\CompassUnavailableException.kt

```kotlin
package com.mhq.salati.qibla.domain.model

/** The device has no rotation sensor, so there is no compass. */
class CompassUnavailableException : Exception("Compass unavailable")
```

## app\src\main\java\com\mhq\salati\qibla\domain\repo\CompassRepository.kt

```kotlin
package com.mhq.salati.qibla.domain.repo

import com.mhq.salati.qibla.domain.model.CompassReading
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow

interface CompassRepository {
    /** Live compass headings for [coordinates].
     * The flow fails when the device has no rotation sensor. */
    fun headings(coordinates: Coordinates): Flow<CompassReading>
}
```

## app\src\main\java\com\mhq\salati\qibla\domain\usecases\GetQiblaBearingUseCase.kt

```kotlin
package com.mhq.salati.qibla.domain.usecases

import com.mhq.salati.shared.domain.Coordinates
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class GetQiblaBearingUseCase @Inject constructor(){

    companion object {
        private const val KAABA_LATITUDE = 21.4225
        private const val KAABA_LONGITUDE = 39.8262
    }

    operator fun invoke(userLocation: Coordinates): Double {
        val lat1 = Math.toRadians(userLocation.latitude)
        val lat2 = Math.toRadians(KAABA_LATITUDE)
        val deltaLon = Math.toRadians(KAABA_LONGITUDE - userLocation.longitude)

        val y = sin(deltaLon) * cos(lat2)
        val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(deltaLon)

        val bearingRadians = atan2(y, x)
        val bearingDegrees = Math.toDegrees(bearingRadians)

        return (bearingDegrees + 360) % 360
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\presentation\contract\QiblaContract.kt

```kotlin
package com.mhq.salati.qibla.presentation.contract

import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi
import com.mhq.salati.shared.presentation.model.MessageUi

class QiblaContract {

    data class State(
        val isLoading: Boolean = false,
        val qiblaBearing: Float? = null,
        val deviceHeading: Float = 0f,
        val sensorUnavailable: Boolean = false,
        val compassAccuracy: CompassAccuracyUi = CompassAccuracyUi.HIGH,
        val isCalibrationGuideVisible: Boolean = false,
        val errorMessage: MessageUi? = null,
        val prerequisites: PrerequisitesUi? = null
    )

    sealed interface Intent {
        data object LoadQibla : Intent
        data object RetryRequested : Intent
        data object LocationPermissionRequested : Intent
        data object LocationPermissionGranted : Intent
        data object AppSettingsRequested : Intent
        data object LocationSettingsRequested : Intent
        data object InternetSettingsRequested : Intent
        data object LocationPickerRequested : Intent
        data object RecalibrationRequested : Intent
        data object DismissCalibrationGuide : Intent
        data object ScreenPaused : Intent
        data object CompassResumed : Intent
        data class LocationPermissionDenied(val showRationale: Boolean) : Intent
        data class ConnectivityChanged(val isConnected: Boolean) : Intent
        data class ScreenResumed(val showLocationRationale: Boolean) : Intent
        data class LocationServicesToggled(val enabled: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
        data object RequestLocationPermission : Effect
        data object NavigateToAppSettings : Effect
        data object NavigateToLocationSettings : Effect
        data object NavigateToInternetSettings : Effect
        data object NavigateToLocationPicker : Effect
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\presentation\handlers\QiblaCompassHandler.kt

```kotlin
package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.qibla.domain.model.CompassReading
import com.mhq.salati.qibla.domain.repo.CompassRepository
import com.mhq.salati.qibla.domain.usecases.GetQiblaBearingUseCase
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** The Qibla direction for a place and the live compass heading of the device. */
class QiblaCompassHandler @Inject constructor(
    private val compassRepository: CompassRepository,
    private val getQiblaBearingUseCase: GetQiblaBearingUseCase
) {
    fun bearing(coordinates: Coordinates): Float = getQiblaBearingUseCase(coordinates).toFloat()

    /** Fails with an exception when the device has no rotation sensor. */
    fun headings(coordinates: Coordinates): Flow<CompassReading> =
        compassRepository.headings(coordinates)
}
```

## app\src\main\java\com\mhq\salati\qibla\presentation\handlers\QiblaLocationHandler.kt

```kotlin
package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.usecases.CheckLocationPrerequisitesUseCase
import com.mhq.salati.location.domain.usecases.GetCurrentCoordinatesUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/** "Where is the user" for the Qibla screen: the saved place or a fresh GPS fix. Only coordinates matter here. */
class QiblaLocationHandler @Inject constructor(
    private val checkLocationPrerequisitesUseCase: CheckLocationPrerequisitesUseCase,
    private val getCurrentCoordinatesUseCase: GetCurrentCoordinatesUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase
) {
    /** The saved place if there is one, otherwise a fresh GPS lookup. */
    fun load(): Flow<QiblaLocationLoad> = flow {
        val saved = getSavedLocationUseCase().first()
        if (saved != null) {
            emit(QiblaLocationLoad.Located(saved.coordinates))
            return@flow
        }

        val prerequisites = checkLocationPrerequisitesUseCase()
        if (!prerequisites.areMet) {
            emit(QiblaLocationLoad.Blocked(prerequisites))
            return@flow
        }

        getCurrentCoordinatesUseCase().collect { lookup ->
            when (lookup) {
                is CoordinatesLookup.ServicesDisabled -> emit(QiblaLocationLoad.Blocked(prerequisites.copy(servicesEnabled = false)))
                is CoordinatesLookup.PermissionDenied -> emit(QiblaLocationLoad.Blocked(prerequisites.copy(permissionGranted = false)))
                is CoordinatesLookup.Locating -> emit(QiblaLocationLoad.LookingUpCurrentLocation)
                is CoordinatesLookup.TimedOut -> emit(QiblaLocationLoad.TimedOut)
                is CoordinatesLookup.Failed -> emit(QiblaLocationLoad.Failed)
                is CoordinatesLookup.Found -> emit(QiblaLocationLoad.Located(lookup.coordinates))
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\presentation\handlers\QiblaLocationLoad.kt

```kotlin
package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.shared.domain.Coordinates

/** The steps of working out where the user is for the Qibla screen, in the order they happen. */
sealed interface QiblaLocationLoad {

    /** A place the Qibla bearing can be calculated for (the saved place or a fresh GPS fix). */
    data class Located(val coordinates: Coordinates) : QiblaLocationLoad

    /** The checks passed and the GPS fix is being awaited. */
    data object LookingUpCurrentLocation : QiblaLocationLoad

    /** There is no saved place and something needed to find one is missing. */
    data class Blocked(val prerequisites: LocationPrerequisites) : QiblaLocationLoad

    data object TimedOut : QiblaLocationLoad

    data object Failed : QiblaLocationLoad
}
```

## app\src\main\java\com\mhq\salati\qibla\presentation\mapper\QiblaUiMapper.kt

```kotlin
package com.mhq.salati.qibla.presentation.mapper

import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi

fun CompassAccuracy.toUi(): CompassAccuracyUi = when (this) {
    CompassAccuracy.UNRELIABLE -> CompassAccuracyUi.UNRELIABLE
    CompassAccuracy.LOW -> CompassAccuracyUi.LOW
    CompassAccuracy.MEDIUM -> CompassAccuracyUi.MEDIUM
    CompassAccuracy.HIGH -> CompassAccuracyUi.HIGH
}
```

## app\src\main\java\com\mhq\salati\qibla\presentation\model\CompassAccuracyUi.kt

```kotlin
package com.mhq.salati.qibla.presentation.model

enum class CompassAccuracyUi {
    UNRELIABLE,
    LOW,
    MEDIUM,
    HIGH
}
```

## app\src\main\java\com\mhq\salati\qibla\presentation\viewmodel\QiblaViewModel.kt

```kotlin
package com.mhq.salati.qibla.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.location.presentation.handlers.LocationPrerequisitesHandler
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.qibla.domain.model.CompassUnavailableException
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.handlers.QiblaCompassHandler
import com.mhq.salati.qibla.presentation.handlers.QiblaLocationHandler
import com.mhq.salati.qibla.presentation.handlers.QiblaLocationLoad
import com.mhq.salati.qibla.presentation.mapper.toUi
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Drives the Qibla screen: resolves the user's location (saved or fresh GPS fix), computes the
 * Qibla bearing from it, and streams live compass heading while the screen is active.
 *
 * Location resolution hard-blocks on connectivity.
 * With nothing saved, the first lookup needs the internet.
 *
 * Compass streaming is explicitly lifecycle-scoped via [QiblaContract.Intent.ScreenPaused] /
 * [QiblaContract.Intent.CompassResumed] rather than tied to the ViewModel's own lifetime, since a
 * ViewModel surviving a bottom-nav tab switch would otherwise keep the sensor listener alive
 * (and draining battery) while the user is on a different tab.
 */
@HiltViewModel
class QiblaViewModel @Inject constructor(
    private val locationHandler: QiblaLocationHandler,
    private val compassHandler: QiblaCompassHandler,
    private val prerequisitesHandler: LocationPrerequisitesHandler
) : ViewModel() {

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    private val _effect = Channel<QiblaContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var loadQiblaJob: Job? = null
    private var compassJob: Job? = null
    private var compassLocation: Coordinates? = null

    /** Last value the UI reported: the system would still show its dialog for the location permission. */
    private var showLocationRationale = false

    init {
        loadQibla()
    }

    /** Single entry point for all user/UI-driven events, per the MVI contract. */
    fun onIntent(intent: QiblaContract.Intent) {
        when (intent) {
            is QiblaContract.Intent.LoadQibla -> {
                val current = _state.value
                when {
                    current.prerequisites != null -> reevaluatePrerequisites()
                    current.errorMessage == null && loadQiblaJob?.isActive != true -> loadQibla()
                }
            }

            is QiblaContract.Intent.RetryRequested -> loadQibla()

            is QiblaContract.Intent.LocationPermissionRequested -> {
                viewModelScope.launch {
                    prerequisitesHandler.onPermissionRequested()
                    _effect.send(QiblaContract.Effect.RequestLocationPermission)
                }
            }

            is QiblaContract.Intent.LocationPermissionGranted -> reevaluatePrerequisites()

            is QiblaContract.Intent.LocationPermissionDenied -> {
                showLocationRationale = intent.showRationale
                reevaluatePrerequisites()
            }

            is QiblaContract.Intent.AppSettingsRequested ->
                sendEffect(QiblaContract.Effect.NavigateToAppSettings)

            is QiblaContract.Intent.LocationSettingsRequested ->
                sendEffect(QiblaContract.Effect.NavigateToLocationSettings)

            is QiblaContract.Intent.InternetSettingsRequested ->
                sendEffect(QiblaContract.Effect.NavigateToInternetSettings)

            is QiblaContract.Intent.LocationPickerRequested ->
                sendEffect(QiblaContract.Effect.NavigateToLocationPicker)

            is QiblaContract.Intent.RecalibrationRequested -> {
                _state.update { it.copy(isCalibrationGuideVisible = true) }
            }

            is QiblaContract.Intent.DismissCalibrationGuide -> {
                _state.update { it.copy(isCalibrationGuideVisible = false) }
            }

            is QiblaContract.Intent.ScreenResumed -> {
                showLocationRationale = intent.showLocationRationale
                val current = _state.value
                val hasValidData = current.qiblaBearing != null

                when {
                    hasValidData -> onIntent(QiblaContract.Intent.CompassResumed)
                    current.prerequisites != null -> reevaluatePrerequisites()
                    current.errorMessage == null && !current.isLoading -> loadQibla()
                }
            }

            is QiblaContract.Intent.LocationServicesToggled -> {
                if (_state.value.prerequisites != null) reevaluatePrerequisites()
            }

            is QiblaContract.Intent.ConnectivityChanged -> {
                if (_state.value.prerequisites != null) reevaluatePrerequisites()
            }

            is QiblaContract.Intent.ScreenPaused -> stopCompassUpdates()

            is QiblaContract.Intent.CompassResumed -> {
                compassLocation?.let { startCompassUpdates(it) }
            }
        }
    }

    private fun sendEffect(effect: QiblaContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    /**
     * Loads (or reloads) the Qibla screen: prefers a saved manual location, otherwise resolves a
     * fresh GPS fix. Cancels any in-flight load so rapid re-triggers (tab switches, retries)
     * can't race each other.
     *
     * If a bearing + location name are already held in memory, the loading spinner is skipped,
     * which keeps re-entering the tab from flashing back to a loading state when we already have
     * something valid to show.
     */
    private fun loadQibla() {
        val current = _state.value
        val hasCalculatedData = current.qiblaBearing != null
        loadQiblaJob?.cancel()

        loadQiblaJob = viewModelScope.launch {
            try {
                if (!hasCalculatedData) {
                    val latest = _state.value
                    if (latest.errorMessage == null && latest.prerequisites == null) {
                        _state.update { it.copy(isLoading = true) }
                    }
                }

                locationHandler.load().collect { load -> handleLocationLoad(load) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                showLoadError(e)
            }
        }
    }

    private suspend fun handleLocationLoad(load: QiblaLocationLoad) {
        when (load) {
            is QiblaLocationLoad.Located -> showLocation(load.coordinates)

            is QiblaLocationLoad.LookingUpCurrentLocation -> {
                if (_state.value.qiblaBearing == null) {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = null,
                            prerequisites = null,
                            sensorUnavailable = false
                        )
                    }
                }
            }

            is QiblaLocationLoad.Blocked ->
                showBlocked(prerequisitesHandler.toUi(load.prerequisites, showLocationRationale))

            is QiblaLocationLoad.TimedOut ->
                showError(MessageUi.FAILED_TO_GET_LOCATION)

            is QiblaLocationLoad.Failed ->
                showError(MessageUi.FAILED_TO_LOAD_QIBLA_DIRECTION)
        }
    }

    /** Shows the compass for the place and starts the live heading. */
    private fun showLocation(coordinates: Coordinates) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                prerequisites = null,
                qiblaBearing = compassHandler.bearing(coordinates),
                sensorUnavailable = false
            )
        }
        startCompassUpdates(coordinates)
    }

    /**
     * Re-checks what the blocked screen is waiting for. The rows update in place, with no loading
     * state in between; the lookup only restarts once nothing is missing (or a place was picked).
     */
    private fun reevaluatePrerequisites() {
        viewModelScope.launch {
            val blocked = prerequisitesHandler.blocked(showLocationRationale)
            if (blocked == null) loadQibla() else showBlocked(blocked)
        }
    }

    private fun showBlocked(prerequisites: PrerequisitesUi) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                sensorUnavailable = false,
                prerequisites = prerequisites
            )
        }
    }

    private suspend fun showError(message: MessageUi) {
        _state.update {
            it.copy(
                isLoading = false,
                prerequisites = null,
                errorMessage = message
            )
        }
        _effect.send(QiblaContract.Effect.ShowError(message))
    }

    private suspend fun showLoadError(error: Throwable) {
        val isSensorMissing = error is CompassUnavailableException
        val message = MessageUi.FAILED_TO_LOAD_QIBLA_DIRECTION
        _state.update {
            it.copy(
                isLoading = false,
                prerequisites = null,
                errorMessage = message,
                sensorUnavailable = isSensorMissing
            )
        }
        _effect.send(QiblaContract.Effect.ShowError(message))
    }

    /**
     * Starts streaming compass headings for the given coordinates, cancelling any previous
     * stream first. [compassLocation] is cached so [QiblaContract.Intent.CompassResumed] can
     * restart the stream directly without re-running the whole [loadQibla] flow.
     */
    private fun startCompassUpdates(coordinates: Coordinates) {
        compassLocation = coordinates
        compassJob?.cancel()
        compassJob = viewModelScope.launch {
            compassHandler.headings(coordinates)
                .catch { error -> showLoadError(error) }
                .collect { reading ->
                    _state.update {
                        it.copy(
                            deviceHeading = reading.headingDegrees,
                            compassAccuracy = reading.accuracy.toUi()
                        )
                    }
                }
        }
    }

    /** Cancels the active compass stream. Called on tab-pause and on [onCleared]. */
    private fun stopCompassUpdates() {
        compassJob?.cancel()
        compassJob = null
    }

    override fun onCleared() {
        compassJob?.cancel()
        super.onCleared()
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\components\CompassCalibrationBanner.kt

```kotlin
package com.mhq.salati.qibla.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi
import com.mhq.salati.shared.ui.theme.SalatiTheme

/**
 * The sensor-accuracy sentence and the colour of its status dot. Shared by the portrait banner and
 * the landscape card so both always say the same thing.
 */
@Composable
internal fun CompassAccuracyUi.sensorStatus(): Pair<String, Color> = when (this) {
    CompassAccuracyUi.HIGH ->
        stringResource(R.string.sensor_accuracy_is_excellent) to MaterialTheme.colorScheme.primary

    CompassAccuracyUi.MEDIUM ->
        stringResource(R.string.sensor_accuracy_is_moderate) to MaterialTheme.colorScheme.onSurfaceVariant

    else ->
        stringResource(R.string.sensor_accuracy_is_poor) to MaterialTheme.colorScheme.error
}

/**
 * Text styles come from the theme typography (not hard-coded sizes) so the Arabic-safe line
 * heights in Type.kt apply here too.
 */
@Composable
fun CalibrationBanner(
    compassAccuracy: CompassAccuracyUi,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (label, dotColor) = compassAccuracy.sensorStatus()

    Surface(
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 20.dp,
            bottomStart = 20.dp
        ),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(dotColor, CircleShape)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            TextButton(
                onClick = onRecalibrateClick,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.recalibrate),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
private fun CalibrationBannerPreview() {
    SalatiTheme {
        CalibrationBanner(
            compassAccuracy = CompassAccuracyUi.HIGH,
            onRecalibrateClick = {}
        )
    }
}

@Preview(locale = "ar", fontScale = 1.5f)
@Composable
private fun CalibrationBannerArabicLargeFontPreview() {
    SalatiTheme {
        CalibrationBanner(
            compassAccuracy = CompassAccuracyUi.MEDIUM,
            onRecalibrateClick = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\components\CompassCalibrationOverlay.kt

```kotlin
package com.mhq.salati.qibla.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Full-screen calibration guide.
 *
 * The scrim covers the whole screen, but the content is kept inside the safe drawing area (status
 * bar, display cutout, navigation bar) and above the floating bottom bar, which is drawn on top of
 * this overlay. Both layouts scroll when the window is too short (landscape, split-screen, large
 * font, Arabic).
 */
@Composable
fun CompassCalibrationOverlay(
    accuracy: CompassAccuracyUi,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isWide = isWideLayout()

    val infiniteTransition = rememberInfiniteTransition(label = "figure8")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "figure8Progress"
    )

    val scrimColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    val textColor = MaterialTheme.colorScheme.onSurface
    val textSecondaryColor = MaterialTheme.colorScheme.onSurfaceVariant
    val buttonBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

    val (highLabel, highColor) = stringResource(R.string.accuracy_excellent) to MaterialTheme.colorScheme.primary
    val (mediumLabel, mediumColor) = stringResource(R.string.accuracy_moderate) to MaterialTheme.colorScheme.tertiary
    val (lowLabel, lowColor) = stringResource(R.string.accuracy_poor) to MaterialTheme.colorScheme.error
    val (unreliableLabel, unreliableColor) = stringResource(R.string.accuracy_unreliable) to MaterialTheme.colorScheme.error

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(scrimColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { /* absorb clicks */ }
            .safeDrawingPadding()
            .padding(bottom = BottomNavBarDefaults.Height),
    ) {
        if (isWide) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = CompassWideLayout.HorizontalPadding,
                        vertical = CompassWideLayout.VerticalPadding
                    ),
                horizontalArrangement = Arrangement.spacedBy(CompassWideLayout.Spacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(CompassWideLayout.DialPaneWeight),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(130.dp)) {
                        drawFigure8Path(
                            progress = progress,
                            pathColor = textColor.copy(alpha = 0.25f),
                            dotColor = textColor
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(CompassWideLayout.SidePaneWeight)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(R.string.compass_calibrate_title),
                        color = textColor,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(R.string.compass_calibrate_body),
                        color = textSecondaryColor,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    AccuracyStatus(
                        accuracy = accuracy,
                        highLabel = highLabel, highColor = highColor,
                        mediumLabel = mediumLabel, mediumColor = mediumColor,
                        lowLabel = lowLabel, lowColor = lowColor,
                        unreliableLabel = unreliableLabel, unreliableColor = unreliableColor
                    )
                    OutlinedButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor),
                        border = BorderStroke(1.dp, buttonBorderColor),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(stringResource(R.string.compass_calibrate_done))
                    }
                }
            }
        } else {
            /** Centered by the parent Box when it fits; scrolls when it does not. */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp)
            ) {
                Canvas(modifier = Modifier.size(160.dp)) {
                    drawFigure8Path(
                        progress = progress,
                        pathColor = textColor.copy(alpha = 0.25f),
                        dotColor = textColor
                    )
                }

                Text(
                    text = stringResource(R.string.compass_calibrate_title),
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(R.string.compass_calibrate_body),
                    color = textSecondaryColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                AccuracyStatus(
                    accuracy = accuracy,
                    highLabel = highLabel, highColor = highColor,
                    mediumLabel = mediumLabel, mediumColor = mediumColor,
                    lowLabel = lowLabel, lowColor = lowColor,
                    unreliableLabel = unreliableLabel, unreliableColor = unreliableColor
                )

                OutlinedButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor),
                    border = BorderStroke(1.dp, buttonBorderColor)
                ) {
                    Text(stringResource(R.string.compass_calibrate_done))
                }
            }
        }
    }
}

private fun DrawScope.drawFigure8Path(progress: Float, pathColor: Color, dotColor: Color) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f
    val rx = w / 2.4f
    val ry = h / 3.2f

    val path = Path().apply {
        val steps = 120
        for (i in 0..steps) {
            val t = (i / steps.toFloat()) * (2 * PI).toFloat()
            val x = cx + rx * sin(t)
            val y = cy + ry * sin(t) * cos(t)
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }

    drawPath(
        path,
        color = pathColor,
        style = Stroke(width = 3.dp.toPx())
    )

    val dotT = progress * (2 * PI).toFloat()
    val dotX = cx + rx * sin(dotT)
    val dotY = cy + ry * sin(dotT) * cos(dotT)

    drawCircle(
        color = dotColor,
        radius = 8.dp.toPx(),
        center = Offset(dotX, dotY)
    )
}

@Composable
private fun AccuracyStatus(
    accuracy: CompassAccuracyUi,
    highLabel: String, highColor: Color,
    mediumLabel: String, mediumColor: Color,
    lowLabel: String, lowColor: Color,
    unreliableLabel: String, unreliableColor: Color
) {
    val (label, color) = when (accuracy) {
        CompassAccuracyUi.HIGH -> highLabel to highColor
        CompassAccuracyUi.MEDIUM -> mediumLabel to mediumColor
        CompassAccuracyUi.LOW -> lowLabel to lowColor
        CompassAccuracyUi.UNRELIABLE -> unreliableLabel to unreliableColor
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, shape = CircleShape)
        )
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(name = "Light Mode Overlay", showSystemUi = true)
@Preview(name = "Dark Mode Overlay", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Landscape Overlay", widthDp = 800, heightDp = 360)
@Preview(name = "Arabic Large Font", locale = "ar", fontScale = 1.5f, showSystemUi = true)
@Composable
private fun CompassCalibrationOverlayPreview() {
    SalatiTheme {
        CompassCalibrationOverlay(
            accuracy = CompassAccuracyUi.LOW,
            onDismiss = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\components\CompassWideLayout.kt

```kotlin
package com.mhq.salati.qibla.ui.components

import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.ScreenHorizontalPadding

/**
 * Geometry shared by the wide (landscape) Qibla screen and the calibration overlay.
 *
 * Both lay out two panes with the SAME outer padding, spacing and weights, so each pane has the
 * same width and position in both. Content centered inside a pane therefore sits on the same
 * vertical line in both: the compass dial and the figure-8, and the Recalibrate and Done buttons.
 * Change a value here and both screens move together.
 */
internal object CompassWideLayout {
    val HorizontalPadding = ScreenHorizontalPadding
    val VerticalPadding = 16.dp
    val Spacing = 16.dp

    /** Start pane: compass dial / figure-8 animation. */
    const val DialPaneWeight = 1.2f

    /** End pane: status card / calibration text. */
    const val SidePaneWeight = 1f
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\components\RotatingCompassDial.kt

```kotlin
package com.mhq.salati.qibla.ui.components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.theme.SalatiTheme
import kotlin.math.cos
import kotlin.math.sin
import android.graphics.Paint as AndroidPaint

/** Every measurement in the drawing code below is authored for a dial of this size. */
private val DialReferenceSize = 300.dp

/** Labels shrink with the dial but never below these sizes, so small dials stay readable. */
private const val MinDegreeLabelSp = 7f
private const val MinCardinalLabelSp = 13f

/**
 * Square compass dial that fills the largest square inside whatever bounded size the caller gives
 * it (`Modifier.size(...)`, or `fillMaxSize()` inside a bounded parent). It must NOT be placed in
 * an unbounded (scrolling) parent without a size.
 *
 * Everything drawn is authored for [DialReferenceSize] and scaled by `dialSize / DialReferenceSize`,
 * so ticks, labels and the needle keep their proportions on a phone, in landscape or on a tablet.
 */
@Composable
fun RotatingCompassDial(
    deviceHeading: Float,
    qiblaBearing: Float,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val dialSize = minOf(maxWidth, maxHeight)
        CompassDial(
            deviceHeading = deviceHeading,
            qiblaBearing = qiblaBearing,
            scale = dialSize / DialReferenceSize,
            modifier = Modifier.size(dialSize)
        )
    }
}

@Composable
private fun CompassDial(
    deviceHeading: Float,
    qiblaBearing: Float,
    scale: Float,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val shadowColor = MaterialTheme.colorScheme.scrim.toArgb()

    /** The dial is an instrument face: its labels follow the dial size, not the user's font scale. */
    val fixedFontDensity = remember(density.density) { Density(density.density, fontScale = 1f) }

    val smoothedHeading by animateFloatAsState(
        targetValue = deviceHeading,
        animationSpec = spring(stiffness = 150f, dampingRatio = 0.85f),
        label = "smoothHeading"
    )

    val degreeLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    val cardinalLabelColor = MaterialTheme.colorScheme.onSurface
    val northLabelColor = MaterialTheme.colorScheme.primary

    val compassBgGradient = Brush.radialGradient(
        colors = listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface)
    )

    val baseContentColor = MaterialTheme.colorScheme.onSurface
    val outerRingStrokeColor = baseContentColor.copy(alpha = 0.25f)
    val centerGlassOverlayColor = baseContentColor.copy(alpha = 0.02f)

    val pointerColor = MaterialTheme.colorScheme.onSurface
    val pointerSpineColor = MaterialTheme.colorScheme.secondary
    val topIndicatorColor = MaterialTheme.colorScheme.primary
    val spindleShadowColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    val spindleHighlightColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    val spindleGradient = Brush.radialGradient(
        colors = listOf(
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
        )
    )

    /** Keyed on color, size and density so a theme switch or a resize rebuilds the blur. */
    val chassisShadowPaint = remember(shadowColor, scale, density) {
        AndroidPaint().apply {
            color = shadowColor
            alpha = 100
            maskFilter = BlurMaskFilter(
                with(density) { (8.dp.toPx() * scale).coerceAtLeast(1f) },
                BlurMaskFilter.Blur.OUTER
            )
        }
    }

    val pointerShadowPaint = remember(shadowColor, scale, density) {
        AndroidPaint().apply {
            color = shadowColor
            alpha = 140
            maskFilter = BlurMaskFilter(
                with(density) { (2.dp.toPx() * scale).coerceAtLeast(1f) },
                BlurMaskFilter.Blur.NORMAL
            )
        }
    }

    val degreeLabels = remember(textMeasurer, degreeLabelColor, scale, fixedFontDensity) {
        (0 until 360 step 30).associateWith { angle ->
            textMeasurer.measure(
                text = angle.toString().padStart(3, '0'),
                style = TextStyle(
                    color = degreeLabelColor,
                    fontSize = (9f * scale).coerceAtLeast(MinDegreeLabelSp).sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Monospace
                ),
                density = fixedFontDensity
            )
        }
    }

    val cardinalLabels = remember(
        textMeasurer, cardinalLabelColor, northLabelColor, scale, fixedFontDensity
    ) {
        listOf("N" to 0f, "E" to 90f, "S" to 180f, "W" to 270f).map { (label, angleDeg) ->
            Triple(
                label, angleDeg,
                textMeasurer.measure(
                    text = label,
                    style = TextStyle(
                        color = if (label == "N") northLabelColor else cardinalLabelColor,
                        fontSize = (18f * scale).coerceAtLeast(MinCardinalLabelSp).sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Default
                    ),
                    density = fixedFontDensity
                )
            )
        }
    }

    Box(modifier = modifier) {
        /** LAYER 1: ROTATING DIAL FACE */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationZ = -smoothedHeading }
                .drawWithCache {
                    /** [value] dp of the reference dial, scaled to the real dial size. */
                    fun px(value: Float) = value.dp.toPx() * scale

                    val radius = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    onDrawBehind {
                        drawContext.canvas.nativeCanvas.drawCircle(
                            center.x, center.y, radius, chassisShadowPaint
                        )
                        drawCircle(
                            brush = compassBgGradient,
                            radius = radius,
                            center = center
                        )
                        drawCircle(
                            color = baseContentColor.copy(alpha = 0.02f),
                            radius = radius,
                            center = center,
                            style = Stroke(px(2f))
                        )
                        drawCircle(
                            color = outerRingStrokeColor,
                            radius = radius - px(4f),
                            center = center,
                            style = Stroke(px(1f))
                        )
                    }
                }
        ) {
            fun px(value: Float) = value.dp.toPx() * scale

            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            /** Inner dashboard details */
            val centerLensRadius = radius * 0.45f
            val intermediateRingRadius = radius * 0.68f

            drawCircle(
                color = baseContentColor.copy(alpha = 0.10f),
                radius = intermediateRingRadius,
                center = center,
                style = Stroke(px(1f))
            )
            drawCircle(
                color = baseContentColor.copy(alpha = 0.16f),
                radius = centerLensRadius,
                center = center,
                style = Stroke(px(1.5f))
            )
            drawCircle(
                color = centerGlassOverlayColor,
                radius = centerLensRadius,
                center = center
            )

            /** Inner crosshairs */
            val crosshairStartOffset = px(18f)
            val verticalLength = centerLensRadius - px(4f)
            val crosshairColor = baseContentColor.copy(alpha = 0.10f)

            drawLine(color = crosshairColor, start = Offset(center.x, center.y - crosshairStartOffset), end = Offset(center.x, center.y - verticalLength), strokeWidth = px(1f))
            drawLine(color = crosshairColor, start = Offset(center.x, center.y + crosshairStartOffset), end = Offset(center.x, center.y + verticalLength), strokeWidth = px(1f))
            drawLine(color = crosshairColor, start = Offset(center.x - crosshairStartOffset, center.y), end = Offset(center.x - verticalLength, center.y), strokeWidth = px(1f))
            drawLine(color = crosshairColor, start = Offset(center.x + crosshairStartOffset, center.y), end = Offset(center.x + verticalLength, center.y), strokeWidth = px(1f))

            /** Tick scale */
            for (angle in 0 until 360 step 2) {
                val angleRad = Math.toRadians((angle - 90).toDouble())
                val isMajor = angle % 30 == 0
                val isMedium = !isMajor && angle % 10 == 0
                val isMinor = !isMajor && !isMedium && angle % 5 == 0

                val tickLength = when {
                    isMajor -> px(12f)
                    isMedium -> px(8f)
                    isMinor -> px(6f)
                    else -> px(4f)
                }
                val strokeWidth = if (isMajor) px(2f) else if (isMedium) px(1.5f) else px(1f)
                val alphaFactor = if (isMajor) 0.9f else if (isMedium) 0.6f else if (isMinor) 0.4f else 0.2f

                val outerBound = radius - px(5f)
                val innerBound = outerBound - tickLength

                drawLine(
                    color = baseContentColor.copy(alpha = alphaFactor),
                    start = Offset(center.x + outerBound * cos(angleRad).toFloat(), center.y + outerBound * sin(angleRad).toFloat()),
                    end = Offset(center.x + innerBound * cos(angleRad).toFloat(), center.y + innerBound * sin(angleRad).toFloat()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )

                /** Secondary notches on the inner ring */
                if (isMedium || isMajor) {
                    val innerRingTickLength = if (isMajor) px(5f) else px(3f)
                    val innerTickEnd = centerLensRadius + innerRingTickLength

                    drawLine(
                        color = baseContentColor.copy(alpha = if (isMajor) 0.4f else 0.2f),
                        start = Offset(center.x + centerLensRadius * cos(angleRad).toFloat(), center.y + centerLensRadius * sin(angleRad).toFloat()),
                        end = Offset(center.x + innerTickEnd * cos(angleRad).toFloat(), center.y + innerTickEnd * sin(angleRad).toFloat()),
                        strokeWidth = px(1f)
                    )
                }
            }

            /** Degree labels */
            degreeLabels.forEach { (angle, textLayoutResult) ->
                val angleRad = Math.toRadians((angle - 90).toDouble())
                val textRadius = radius - px(26f)
                val x = center.x + textRadius * cos(angleRad).toFloat()
                val y = center.y + textRadius * sin(angleRad).toFloat()
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x - textLayoutResult.size.width / 2f,
                        y - textLayoutResult.size.height / 2f
                    )
                )
            }

            /** Cardinal headings */
            cardinalLabels.forEach { (_, angleDeg, textLayoutResult) ->
                val angleRad = Math.toRadians((angleDeg - 90).toDouble())
                val textRadius = radius - px(46f)
                val x = center.x + textRadius * cos(angleRad).toFloat()
                val y = center.y + textRadius * sin(angleRad).toFloat()
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x - textLayoutResult.size.width / 2f,
                        y - textLayoutResult.size.height / 2f
                    )
                )
            }
        }

        /** LAYER 2: ROTATING QIBLA NEEDLE */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationZ = qiblaBearing - smoothedHeading }
        ) {
            fun px(value: Float) = value.dp.toPx() * scale

            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)
            val outerTrackRadius = radius - px(8f)
            val tip = Offset(center.x, center.y - outerTrackRadius)
            val baseLeft = Offset(center.x - px(14f), center.y - outerTrackRadius + px(22f))
            val baseRight = Offset(center.x + px(14f), center.y - outerTrackRadius + px(22f))
            val spineBase = Offset(center.x, center.y - outerTrackRadius + px(16f))
            val arrowPath = Path().apply {
                moveTo(tip.x, tip.y)
                lineTo(baseLeft.x, baseLeft.y)
                lineTo(spineBase.x, spineBase.y)
                lineTo(baseRight.x, baseRight.y)
                close()
            }
            drawContext.canvas.nativeCanvas.drawPath(arrowPath.asAndroidPath(), pointerShadowPaint)
            drawPath(path = arrowPath, color = pointerColor)
            drawLine(
                color = pointerSpineColor,
                start = tip,
                end = spineBase,
                strokeWidth = px(1.5f),
                cap = StrokeCap.Round
            )
        }

        /** LAYER 3: STATIC TOP INDICATOR AND CENTER SPINDLE */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    fun px(value: Float) = value.dp.toPx() * scale

                    val radius = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    onDrawBehind {
                        drawLine(
                            color = topIndicatorColor,
                            start = Offset(center.x, center.y - radius),
                            end = Offset(center.x, center.y - radius + px(20f)),
                            strokeWidth = px(2f)
                        )
                        drawCircle(
                            color = spindleShadowColor,
                            radius = px(10f),
                            center = center
                        )
                        drawCircle(
                            brush = spindleGradient,
                            radius = px(6f),
                            center = center
                        )
                        drawCircle(
                            color = spindleHighlightColor,
                            radius = px(2f),
                            center = center - Offset(px(1.5f), px(1.5f))
                        )
                    }
                }
        ) {}
    }
}

@Preview(name = "Reference size", showBackground = true)
@Composable
private fun RotatingCompassDialPreview() {
    SalatiTheme {
        RotatingCompassDial(
            deviceHeading = 0f,
            qiblaBearing = 45f,
            modifier = Modifier.size(300.dp)
        )
    }
}

@Preview(name = "Small dial", showBackground = true)
@Composable
private fun RotatingCompassDialSmallPreview() {
    SalatiTheme {
        RotatingCompassDial(
            deviceHeading = 0f,
            qiblaBearing = 45f,
            modifier = Modifier.size(170.dp)
        )
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\errors\QiblaSensorUnavailableError.kt

```kotlin
package com.mhq.salati.qibla.ui.errors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mhq.salati.R

@Composable
fun QiblaSensorUnavailableError(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.error, errorMessage),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.this_device_doesn_t_have_the_sensors_needed_for_a_compass),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.qibla_direction_can_t_be_shown_here),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\screens\QiblaContainer.kt

```kotlin
package com.mhq.salati.qibla.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.location.ui.LocationPrerequisitesObserver
import com.mhq.salati.location.ui.rememberLocationPrerequisitesActions
import com.mhq.salati.permissions.ui.shouldShowLocationRationale
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.viewmodel.QiblaViewModel
import com.mhq.salati.qibla.ui.components.CompassCalibrationOverlay
import com.mhq.salati.shared.ui.asString
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import kotlinx.coroutines.launch

@Composable
fun QiblaContainer(
    qiblaViewModel: QiblaViewModel = hiltViewModel(),
    onNavigateToLocationPicker: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by qiblaViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    val locationActions = rememberLocationPrerequisitesActions(
        onPermissionGranted = {
            qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionGranted)
        },
        onPermissionDenied = { showRationale ->
            qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionDenied(showRationale))
        }
    )

    LaunchedEffect(Unit) {
        qiblaViewModel.effect.collect { effect ->
            when (effect) {
                is QiblaContract.Effect.RequestLocationPermission -> locationActions.requestPermission()
                is QiblaContract.Effect.NavigateToAppSettings -> locationActions.openAppSettings()
                is QiblaContract.Effect.NavigateToLocationSettings -> locationActions.openLocationSettings()
                is QiblaContract.Effect.NavigateToInternetSettings -> locationActions.openInternetSettings()
                is QiblaContract.Effect.NavigateToLocationPicker -> onNavigateToLocationPicker()
                is QiblaContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
            }
        }
    }

    // The destination's own lifecycle is RESUMED only while this tab is visible and the app is in
    // the foreground, so it alone decides when the compass runs.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME ->
                    qiblaViewModel.onIntent(
                        QiblaContract.Intent.ScreenResumed(context.shouldShowLocationRationale())
                    )

                Lifecycle.Event.ON_PAUSE ->
                    qiblaViewModel.onIntent(QiblaContract.Intent.ScreenPaused)

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Safety net: if this composable is disposed outright (tab switch, since NavHost only composes
    // the current destination) the compass still gets stopped.
    DisposableEffect(Unit) {
        onDispose {
            qiblaViewModel.onIntent(QiblaContract.Intent.ScreenPaused)
        }
    }

    LocationPrerequisitesObserver(
        onServicesChanged = { enabled ->
            qiblaViewModel.onIntent(QiblaContract.Intent.LocationServicesToggled(enabled))
        },
        onConnectivityChanged = { connected ->
            qiblaViewModel.onIntent(QiblaContract.Intent.ConnectivityChanged(connected))
        }
    )

    LaunchedEffect(Unit) {
        qiblaViewModel.onIntent(QiblaContract.Intent.LoadQibla)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        QiblaContent(
            state = state,
            onIntent = qiblaViewModel::onIntent
        )

        if (state.isCalibrationGuideVisible) {
            BackHandler {
                qiblaViewModel.onIntent(QiblaContract.Intent.DismissCalibrationGuide)
            }
            CompassCalibrationOverlay(
                accuracy = state.compassAccuracy,
                onDismiss = { qiblaViewModel.onIntent(QiblaContract.Intent.DismissCalibrationGuide) }
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\screens\QiblaContent.kt

```kotlin
package com.mhq.salati.qibla.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.R
import com.mhq.salati.location.ui.components.PrerequisitesError
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi
import com.mhq.salati.shared.presentation.model.MessageUi
import com.mhq.salati.shared.ui.asString
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.screens.LoadingContent
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun QiblaContent(
    state: QiblaContract.State,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (state.prerequisites == null) {
                        Modifier
                            .navigationBarsPadding()
                            .padding(bottom = BottomNavBarDefaults.Height)
                    } else {
                        Modifier
                    }
                )
        ) {
            when {
                state.prerequisites != null -> {
                    PrerequisitesError(
                        prerequisites = state.prerequisites,
                        onOpenInternetSettings = { onIntent(QiblaContract.Intent.InternetSettingsRequested) },
                        onOpenLocationSettings = { onIntent(QiblaContract.Intent.LocationSettingsRequested) },
                        onAllowPermission = { onIntent(QiblaContract.Intent.LocationPermissionRequested) },
                        onOpenAppSettings = { onIntent(QiblaContract.Intent.AppSettingsRequested) },
                        onChooseLocationManually = { onIntent(QiblaContract.Intent.LocationPickerRequested) },
                        modifier = Modifier.weight(1f)
                    )
                }

                state.isLoading -> {
                    LoadingContent(modifier = Modifier.weight(1f))
                }

                state.errorMessage != null -> {
                    QiblaContentError(
                        errorMessage = state.errorMessage.asString(),
                        isNoConnection = state.errorMessage == MessageUi.NO_INTERNET_CONNECTION,
                        sensorUnavailable = state.sensorUnavailable,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }

                state.qiblaBearing != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        TabHeader(
                            icon = Icons.Default.Explore,
                            title = stringResource(R.string.qibla),
                            subtitle = stringResource(R.string.face_the_kaaba_wherever_you_are)
                        )
                        QiblaContentSuccess(
                            deviceHeading = state.deviceHeading,
                            qiblaBearing = state.qiblaBearing,
                            compassAccuracy = state.compassAccuracy,
                            onRecalibrateClick = { onIntent(QiblaContract.Intent.RecalibrationRequested) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                else -> Box(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(name = "Light Mode - Active Compass", showSystemUi = true)
@Preview(
    name = "Dark Mode - Active Compass",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "Landscape - Active Compass", widthDp = 800, heightDp = 360)
@Preview(name = "Arabic Large Font - Active Compass", locale = "ar", fontScale = 1.5f, showSystemUi = true)
@Composable
private fun QiblaContentActivePreview() {
    SalatiTheme {
        QiblaContent(
            state = QiblaContract.State(
                isLoading = false,
                qiblaBearing = 135f,
                deviceHeading = 45f,
                compassAccuracy = CompassAccuracyUi.HIGH
            ),
            onIntent = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\screens\QiblaContentError.kt

```kotlin
package com.mhq.salati.qibla.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.ui.errors.QiblaSensorUnavailableError
import com.mhq.salati.shared.ui.errors.CatchAllError
import com.mhq.salati.shared.ui.errors.NoConnectionError
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun QiblaContentError(
    errorMessage: String,
    isNoConnection: Boolean,
    sensorUnavailable: Boolean,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isNoConnection -> {
            NoConnectionError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(QiblaContract.Intent.RetryRequested) },
                modifier = modifier
            )
        }

        sensorUnavailable -> {
            QiblaSensorUnavailableError(
                errorMessage = errorMessage,
                modifier = modifier
            )
        }

        else -> {
            CatchAllError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(QiblaContract.Intent.RetryRequested) },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun QiblaContentErrorPreview() {
    SalatiTheme()  {
        QiblaContentError(
            errorMessage = "Some message",
            isNoConnection = false,
            sensorUnavailable = false,
            onIntent = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\qibla\ui\screens\QiblaContentSuccess.kt

```kotlin
package com.mhq.salati.qibla.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi
import com.mhq.salati.qibla.ui.components.CalibrationBanner
import com.mhq.salati.qibla.ui.components.CompassWideLayout
import com.mhq.salati.qibla.ui.components.RotatingCompassDial
import com.mhq.salati.qibla.ui.components.sensorStatus
import com.mhq.salati.shared.ui.ScreenHorizontalPadding
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme

private val NarrowCardMargin = ScreenHorizontalPadding
private val NarrowCardMaxWidth = 560.dp

/**
 * Height of everything in the narrow card except the dial (emoji, spacings, banner, margins).
 * Only used to decide how big the dial may be so the card fits without scrolling when it can.
 */
private val NarrowChromeHeight = 200.dp

private val MinDialSize = 180.dp
private val MaxDialSize = 400.dp

/**
 * Compass content for a loaded Qibla bearing.
 *
 * - Wide windows (landscape, tablets, foldables): dial on the start side, status card on the end.
 * - Narrow windows: one centered card; the dial is sized from the space actually available, and
 *   the column scrolls if a very short window or a large font still does not fit.
 *
 * Horizontal display-cutout insets are applied here so nothing sits under a notch in landscape.
 */
@Composable
fun QiblaContentSuccess(
    deviceHeading: Float,
    qiblaBearing: Float,
    compassAccuracy: CompassAccuracyUi,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentModifier = modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))

    if (isWideLayout()) {
        QiblaWideContent(
            deviceHeading = deviceHeading,
            qiblaBearing = qiblaBearing,
            compassAccuracy = compassAccuracy,
            onRecalibrateClick = onRecalibrateClick,
            modifier = contentModifier
        )
    } else {
        QiblaNarrowContent(
            deviceHeading = deviceHeading,
            qiblaBearing = qiblaBearing,
            compassAccuracy = compassAccuracy,
            onRecalibrateClick = onRecalibrateClick,
            modifier = contentModifier
        )
    }
}

@Composable
private fun QiblaWideContent(
    deviceHeading: Float,
    qiblaBearing: Float,
    compassAccuracy: CompassAccuracyUi,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(CompassWideLayout.Spacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(
            horizontal = CompassWideLayout.HorizontalPadding,
            vertical = CompassWideLayout.VerticalPadding)
    ) {
        // The dial takes the largest square that fits this pane (5% left for its shadow).
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(CompassWideLayout.DialPaneWeight)
                .fillMaxHeight()
        ) {
            RotatingCompassDial(
                deviceHeading = deviceHeading,
                qiblaBearing = qiblaBearing,
                modifier = Modifier.size(minOf(maxWidth, maxHeight) * 0.95f)
            )
        }

        QiblaCard(
            modifier = Modifier
                .weight(CompassWideLayout.SidePaneWeight)
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                val (label, dotColor) = compassAccuracy.sensorStatus()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(dotColor, CircleShape)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = label,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(Modifier.height(24.dp))

                TextButton(
                    onClick = onRecalibrateClick,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = stringResource(R.string.recalibrate),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun QiblaNarrowContent(
    deviceHeading: Float,
    qiblaBearing: Float,
    compassAccuracy: CompassAccuracyUi,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val cardWidth = minOf(maxWidth - NarrowCardMargin * 2, NarrowCardMaxWidth)
        // Limited by width first (as before), then by the height that is really left.
        val dialSize = minOf(cardWidth * 0.85f - 16.dp, maxHeight - NarrowChromeHeight)
            .coerceIn(MinDialSize, MaxDialSize)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(NarrowCardMargin)
        ) {
            QiblaCard(
                modifier = Modifier
                    .widthIn(max = NarrowCardMaxWidth)
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "\uD83D\uDD4B",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontSize = 40.sp,
                            lineHeight = 48.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    RotatingCompassDial(
                        deviceHeading = deviceHeading,
                        qiblaBearing = qiblaBearing,
                        modifier = Modifier.size(dialSize)
                    )
                    Spacer(Modifier.height(24.dp))
                }
                CalibrationBanner(
                    compassAccuracy = compassAccuracy,
                    onRecalibrateClick = onRecalibrateClick
                )
            }
        }
    }
}

/** The one card style used by both layouts. */
@Composable
private fun QiblaCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
        modifier = modifier,
        content = content
    )
}

@Preview(name = "Portrait", showSystemUi = true)
@Preview(name = "Portrait short", widthDp = 360, heightDp = 480)
@Preview(name = "Landscape", widthDp = 800, heightDp = 360)
@Preview(name = "Tablet", widthDp = 1024, heightDp = 768)
@Preview(name = "Arabic Large Font", locale = "ar", fontScale = 1.5f, showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QiblaContentSuccessPreview() {
    SalatiTheme {
        QiblaContentSuccess(
            deviceHeading = 1.0f,
            qiblaBearing = 1.0f,
            compassAccuracy = CompassAccuracyUi.HIGH,
            onRecalibrateClick = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\settings\data\mapper\SettingsMapper.kt

```kotlin
package com.mhq.salati.settings.data.mapper

import com.mhq.salati.settings.datasource.preferences.StoredSettings
import com.mhq.salati.settings.domain.model.AppSettings

private inline fun <reified E : Enum<E>> String?.toEnumOrNull(): E? =
    this?.let { stored -> enumValues<E>().firstOrNull { it.name == stored } }

/** A missing or unknown stored choice falls back to the default setting. */
fun StoredSettings.toDomain(): AppSettings {
    val defaults = AppSettings()
    return AppSettings(
        notificationsEnabled = notificationsEnabled,
        calculationMethod = calculationMethod.toEnumOrNull() ?: defaults.calculationMethod,
        madhab = madhab.toEnumOrNull() ?: defaults.madhab,
        themeMode = themeMode.toEnumOrNull() ?: defaults.themeMode,
        language = language.toEnumOrNull() ?: defaults.language,
        adhanSound = adhanSound.toEnumOrNull() ?: defaults.adhanSound,
        customAlarmSound = customAlarmSound.toEnumOrNull() ?: defaults.customAlarmSound,
        hijriDateOffset = hijriDateOffset,
        timeFormat = timeFormat.toEnumOrNull() ?: defaults.timeFormat
    )
}
```

## app\src\main\java\com\mhq\salati\settings\data\repoimpl\SettingsRepoImpl.kt

```kotlin
package com.mhq.salati.settings.data.repoimpl

import com.mhq.salati.settings.data.mapper.toDomain
import com.mhq.salati.settings.datasource.preferences.SettingsDataStore
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override fun observeSettings(): Flow<AppSettings> =
        settingsDataStore.storedSettings.map { it.toDomain() }

    override suspend fun setNotificationsEnabled(enabled: Boolean) =
        settingsDataStore.setNotificationsEnabled(enabled)

    override suspend fun setCalculationMethod(method: CalculationMethod) =
        settingsDataStore.setCalculationMethod(method.name)

    override suspend fun setMadhab(madhab: Madhab) =
        settingsDataStore.setMadhab(madhab.name)

    override suspend fun setThemeMode(mode: ThemeMode) =
        settingsDataStore.setThemeMode(mode.name)

    override suspend fun setLanguage(language: AppLanguage) =
        settingsDataStore.setLanguage(language.name)

    override suspend fun setAdhanSound(sound: AdhanSound) =
        settingsDataStore.setAdhanSound(sound.name)

    override suspend fun setCustomAlarmSound(sound: CustomAlarmSound) =
        settingsDataStore.setCustomAlarmSound(sound.name)

    override suspend fun setHijriDateOffset(offset: Int) =
        settingsDataStore.setHijriDateOffset(offset)

    override suspend fun setTimeFormat(format: TimeFormat) =
        settingsDataStore.setTimeFormat(format.name)
}
```

## app\src\main\java\com\mhq\salati\settings\datasource\preferences\SettingsDataStore.kt

```kotlin
package com.mhq.salati.settings.datasource.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore(name = "settings_prefs")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val CALCULATION_METHOD = stringPreferencesKey("calculation_method")
        val MADHAB = stringPreferencesKey("madhab")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val LANGUAGE = stringPreferencesKey("language")
        val ADHAN_SOUND = stringPreferencesKey("adhan_sound")
        val CUSTOM_ALARM_SOUND = stringPreferencesKey("custom_alarm_sound")
        val HIJRI_OFFSET = intPreferencesKey("hijri_date_offset")
        val TIME_FORMAT = stringPreferencesKey("time_format")
    }

    val storedSettings: Flow<StoredSettings> = context.settingsDataStore.data.map { prefs ->
        StoredSettings(
            notificationsEnabled = prefs[Keys.NOTIFICATIONS_ENABLED] ?: true,
            calculationMethod = prefs[Keys.CALCULATION_METHOD],
            madhab = prefs[Keys.MADHAB],
            themeMode = prefs[Keys.THEME_MODE],
            language = prefs[Keys.LANGUAGE],
            adhanSound = prefs[Keys.ADHAN_SOUND],
            customAlarmSound = prefs[Keys.CUSTOM_ALARM_SOUND],
            hijriDateOffset = prefs[Keys.HIJRI_OFFSET] ?: 0,
            timeFormat = prefs[Keys.TIME_FORMAT]
        )
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[Keys.NOTIFICATIONS_ENABLED] = enabled }
    }

    suspend fun setCalculationMethod(value: String) {
        context.settingsDataStore.edit { it[Keys.CALCULATION_METHOD] = value }
    }

    suspend fun setMadhab(value: String) {
        context.settingsDataStore.edit { it[Keys.MADHAB] = value }
    }

    suspend fun setThemeMode(value: String) {
        context.settingsDataStore.edit { it[Keys.THEME_MODE] = value }
    }

    suspend fun setLanguage(value: String) {
        context.settingsDataStore.edit { it[Keys.LANGUAGE] = value }
    }

    suspend fun setAdhanSound(value: String) {
        context.settingsDataStore.edit { it[Keys.ADHAN_SOUND] = value }
    }

    suspend fun setCustomAlarmSound(value: String) {
        context.settingsDataStore.edit { it[Keys.CUSTOM_ALARM_SOUND] = value }
    }

    suspend fun setHijriDateOffset(offset: Int) {
        context.settingsDataStore.edit { it[Keys.HIJRI_OFFSET] = offset }
    }

    suspend fun setTimeFormat(value: String) {
        context.settingsDataStore.edit { it[Keys.TIME_FORMAT] = value }
    }
}
```

## app\src\main\java\com\mhq\salati\settings\datasource\preferences\StoredSettings.kt

```kotlin
package com.mhq.salati.settings.datasource.preferences

/** The settings exactly as the DataStore keeps them. Choices are stored as text; null means never saved. */
data class StoredSettings(
    val notificationsEnabled: Boolean,
    val calculationMethod: String?,
    val madhab: String?,
    val themeMode: String?,
    val language: String?,
    val adhanSound: String?,
    val customAlarmSound: String?,
    val hijriDateOffset: Int,
    val timeFormat: String?
)
```

## app\src\main\java\com\mhq\salati\settings\di\SettingsModule.kt

```kotlin
package com.mhq.salati.settings.di

import com.mhq.salati.settings.data.repoimpl.SettingsRepoImpl
import com.mhq.salati.settings.domain.repo.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindSettingsRepository(
        impl: SettingsRepoImpl
    ): SettingsRepository
}
```

## app\src\main\java\com\mhq\salati\settings\domain\model\AdhanSound.kt

```kotlin
package com.mhq.salati.settings.domain.model

enum class AdhanSound { DEFAULT, EGYPT, MAKKAH, MADINAH, SILENT }
```

## app\src\main\java\com\mhq\salati\settings\domain\model\AppLanguage.kt

```kotlin
package com.mhq.salati.settings.domain.model

enum class AppLanguage(val code: String) {
    ENGLISH("en"),
    ARABIC("ar")
}
```

## app\src\main\java\com\mhq\salati\settings\domain\model\AppSettings.kt

```kotlin
package com.mhq.salati.settings.domain.model

data class AppSettings(
    val notificationsEnabled: Boolean = true,
    val adhanSound: AdhanSound = AdhanSound.DEFAULT,
    val customAlarmSound: CustomAlarmSound = CustomAlarmSound.DEFAULT,
    val calculationMethod: CalculationMethod = CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY,
    val madhab: Madhab = Madhab.SHAFI,
    val timeFormat: TimeFormat = TimeFormat.FORMAT_12_HOUR,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val hijriDateOffset: Int = 0,
)
```

## app\src\main\java\com\mhq\salati\settings\domain\model\CalculationMethod.kt

```kotlin
package com.mhq.salati.settings.domain.model

enum class CalculationMethod(val apiMethodId: Int) {
    EGYPTIAN_GENERAL_AUTHORITY(5),
    UMM_AL_QURA(4),
    MUSLIM_WORLD_LEAGUE(3),
    ISLAMIC_SOCIETY_OF_NORTH_AMERICA(2),
    UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI(1)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\model\CustomAlarmSound.kt

```kotlin
package com.mhq.salati.settings.domain.model

enum class CustomAlarmSound { DEFAULT, ALERT }
```

## app\src\main\java\com\mhq\salati\settings\domain\model\Madhab.kt

```kotlin
package com.mhq.salati.settings.domain.model

enum class Madhab(val schoolId: Int) {
    SHAFI(0),
    HANAFI(1)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\model\ThemeMode.kt

```kotlin
package com.mhq.salati.settings.domain.model

enum class ThemeMode { SYSTEM, LIGHT, DARK }
```

## app\src\main\java\com\mhq\salati\settings\domain\model\TimeFormat.kt

```kotlin
package com.mhq.salati.settings.domain.model

/** How clock times, such as prayer times, are displayed. */
enum class TimeFormat { FORMAT_12_HOUR, FORMAT_24_HOUR }
```

## app\src\main\java\com\mhq\salati\settings\domain\repo\SettingsRepository.kt

```kotlin
package com.mhq.salati.settings.domain.repo

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.model.TimeFormat
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<AppSettings>
    suspend fun setNotificationsEnabled(enabled: Boolean)
    suspend fun setCalculationMethod(method: CalculationMethod)
    suspend fun setMadhab(madhab: Madhab)
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun setAdhanSound(sound: AdhanSound)
    suspend fun setCustomAlarmSound(sound: CustomAlarmSound)
    suspend fun setHijriDateOffset(offset: Int)
    suspend fun setTimeFormat(format: TimeFormat)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\ObserveHijriDateOffsetUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveHijriDateOffsetUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Int> =
        settingsRepository.observeSettings()
            .map { it.hijriDateOffset }
            .distinctUntilChanged()
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\ObserveSettingsUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<AppSettings> = settingsRepository.observeSettings()
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\ObserveTimeFormatUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** Emits the chosen [TimeFormat], and again only when it actually changes. */
class ObserveTimeFormatUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<TimeFormat> =
        settingsRepository.observeSettings()
            .map { it.timeFormat }
            .distinctUntilChanged()
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateAdhanSoundUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateAdhanSoundUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(sound: AdhanSound) = settingsRepository.setAdhanSound(sound)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateCalculationMethodUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateCalculationMethodUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(method: CalculationMethod) = settingsRepository.setCalculationMethod(method)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateCustomAlarmSoundUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateCustomAlarmSoundUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(sound: CustomAlarmSound) = settingsRepository.setCustomAlarmSound(sound)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateHijriDateOffsetUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateHijriDateOffsetUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(offset: Int) = settingsRepository.setHijriDateOffset(offset)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateLanguageUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: AppLanguage) = settingsRepository.setLanguage(language)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateMadhabUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateMadhabUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(madhab: Madhab) = settingsRepository.setMadhab(madhab)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateNotificationsEnabledUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.adhan.domain.usecases.RescheduleAllAlarmsUseCase
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateNotificationsEnabledUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val settingsRepository: SettingsRepository,
    private val rescheduleAllAlarmsUseCase: RescheduleAllAlarmsUseCase
) {
    suspend operator fun invoke(enabled: Boolean) {
        settingsRepository.setNotificationsEnabled(enabled)
        if (!enabled) {
            alarmScheduler.cancelAll()
            return
        }
        rescheduleAllAlarmsUseCase()
    }
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateThemeModeUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateThemeModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(mode: ThemeMode) = settingsRepository.setThemeMode(mode)
}
```

## app\src\main\java\com\mhq\salati\settings\domain\usecases\UpdateTimeFormatUseCase.kt

```kotlin
package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateTimeFormatUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(format: TimeFormat) = settingsRepository.setTimeFormat(format)
}
```

## app\src\main\java\com\mhq\salati\settings\presentation\contract\SettingsContract.kt

```kotlin
package com.mhq.salati.settings.presentation.contract

import com.mhq.salati.settings.presentation.SelectorType
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi

object SettingsContract {

    data class State(
        val isLoading: Boolean = true,
        val notificationsEnabled: Boolean = true,
        val hasNotificationPermission: Boolean = true,
        val calculationMethod: CalculationMethodUi = CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY,
        val madhab: MadhabUi = MadhabUi.SHAFI,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val language: AppLanguageUi = AppLanguageUi.ENGLISH,
        val adhanSound: AdhanSoundUi = AdhanSoundUi.DEFAULT,
        val customAlarmSound: CustomAlarmSoundUi = CustomAlarmSoundUi.DEFAULT,
        val activeSelector: SelectorType? = null,
        val hijriDateOffset: Int = 0,
        val errorMessage: MessageUi? = null,
        val timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_12_HOUR,
    )

    sealed interface Intent {
        data class ToggleNotifications(val enabled: Boolean) : Intent
        data class NotificationPermissionResult(val granted: Boolean) : Intent
        data object RecheckNotificationPermission : Intent
        data class SelectCalculationMethod(val method: CalculationMethodUi) : Intent
        data class SelectMadhab(val madhab: MadhabUi) : Intent
        data class SelectTheme(val mode: ThemeModeUi) : Intent
        data class SelectLanguage(val language: AppLanguageUi) : Intent
        data class SelectAdhanSound(val sound: AdhanSoundUi) : Intent
        data class SelectCustomAlarmSound(val sound: CustomAlarmSoundUi) : Intent
        data object IncrementHijriOffset : Intent
        data object DecrementHijriOffset : Intent
        data class OpenSelector(val type: SelectorType) : Intent
        data object CloseSelector : Intent
        data object RateApp : Intent
        data object ShareApp : Intent
        data object ContactSupport : Intent
        data class SelectTimeFormat(val format: TimeFormatUi) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
        data class LanguageChangedRestartRequired(val languageCode: String) : Effect
        data object LaunchShareSheet : Effect
        data object OpenPlayStoreListing : Effect
        data object OpenEmailClient : Effect
        data object RequestNotificationPermission : Effect
    }
}
```

## app\src\main\java\com\mhq\salati\settings\presentation\mapper\SettingsUiMapper.kt

```kotlin
package com.mhq.salati.settings.presentation.mapper

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi

/** Every conversion is an exhaustive `when` (no else),
 * so adding a value to one side without the other fails to compile. */

// ---- AdhanSound
fun AdhanSound.toUi(): AdhanSoundUi = when (this) {
    AdhanSound.DEFAULT -> AdhanSoundUi.DEFAULT
    AdhanSound.EGYPT -> AdhanSoundUi.EGYPT
    AdhanSound.MAKKAH -> AdhanSoundUi.MAKKAH
    AdhanSound.MADINAH -> AdhanSoundUi.MADINAH
    AdhanSound.SILENT -> AdhanSoundUi.SILENT
}

fun AdhanSoundUi.toDomain(): AdhanSound = when (this) {
    AdhanSoundUi.DEFAULT -> AdhanSound.DEFAULT
    AdhanSoundUi.EGYPT -> AdhanSound.EGYPT
    AdhanSoundUi.MAKKAH -> AdhanSound.MAKKAH
    AdhanSoundUi.MADINAH -> AdhanSound.MADINAH
    AdhanSoundUi.SILENT -> AdhanSound.SILENT
}

/**
 * Reads the sound key the playback service receives in its Intent.
 * AdhanPlaybackRepoImpl writes it as `AdhanSound.name`.
 * An unknown or missing key falls back to the default sound.
 */
fun String?.toAdhanSoundUi(): AdhanSoundUi =
    this?.let { key -> runCatching { AdhanSound.valueOf(key) }.getOrNull() }
        ?.toUi()
        ?: AdhanSoundUi.DEFAULT

// ---- AppLanguage
fun AppLanguage.toUi(): AppLanguageUi = when (this) {
    AppLanguage.ENGLISH -> AppLanguageUi.ENGLISH
    AppLanguage.ARABIC -> AppLanguageUi.ARABIC
}

fun AppLanguageUi.toDomain(): AppLanguage = when (this) {
    AppLanguageUi.ENGLISH -> AppLanguage.ENGLISH
    AppLanguageUi.ARABIC -> AppLanguage.ARABIC
}

// ---- CalculationMethod
fun CalculationMethod.toUi(): CalculationMethodUi = when (this) {
    CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY -> CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY
    CalculationMethod.UMM_AL_QURA -> CalculationMethodUi.UMM_AL_QURA
    CalculationMethod.MUSLIM_WORLD_LEAGUE -> CalculationMethodUi.MUSLIM_WORLD_LEAGUE
    CalculationMethod.ISLAMIC_SOCIETY_OF_NORTH_AMERICA -> CalculationMethodUi.ISLAMIC_SOCIETY_OF_NORTH_AMERICA
    CalculationMethod.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI -> CalculationMethodUi.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI
}

fun CalculationMethodUi.toDomain(): CalculationMethod = when (this) {
    CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY -> CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY
    CalculationMethodUi.UMM_AL_QURA -> CalculationMethod.UMM_AL_QURA
    CalculationMethodUi.MUSLIM_WORLD_LEAGUE -> CalculationMethod.MUSLIM_WORLD_LEAGUE
    CalculationMethodUi.ISLAMIC_SOCIETY_OF_NORTH_AMERICA -> CalculationMethod.ISLAMIC_SOCIETY_OF_NORTH_AMERICA
    CalculationMethodUi.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI -> CalculationMethod.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI
}

// ---- CustomAlarmSound
fun CustomAlarmSound.toUi(): CustomAlarmSoundUi = when (this) {
    CustomAlarmSound.DEFAULT -> CustomAlarmSoundUi.DEFAULT
    CustomAlarmSound.ALERT -> CustomAlarmSoundUi.ALERT
}

fun CustomAlarmSoundUi.toDomain(): CustomAlarmSound = when (this) {
    CustomAlarmSoundUi.DEFAULT -> CustomAlarmSound.DEFAULT
    CustomAlarmSoundUi.ALERT -> CustomAlarmSound.ALERT
}

// ---- Madhab
fun Madhab.toUi(): MadhabUi = when (this) {
    Madhab.SHAFI -> MadhabUi.SHAFI
    Madhab.HANAFI -> MadhabUi.HANAFI
}

fun MadhabUi.toDomain(): Madhab = when (this) {
    MadhabUi.SHAFI -> Madhab.SHAFI
    MadhabUi.HANAFI -> Madhab.HANAFI
}

// ---- ThemeMode
fun ThemeMode.toUi(): ThemeModeUi = when (this) {
    ThemeMode.SYSTEM -> ThemeModeUi.SYSTEM
    ThemeMode.LIGHT -> ThemeModeUi.LIGHT
    ThemeMode.DARK -> ThemeModeUi.DARK
}

fun ThemeModeUi.toDomain(): ThemeMode = when (this) {
    ThemeModeUi.SYSTEM -> ThemeMode.SYSTEM
    ThemeModeUi.LIGHT -> ThemeMode.LIGHT
    ThemeModeUi.DARK -> ThemeMode.DARK
}

// ---- TimeFormat
fun TimeFormat.toUi(): TimeFormatUi = when (this) {
    TimeFormat.FORMAT_12_HOUR -> TimeFormatUi.FORMAT_12_HOUR
    TimeFormat.FORMAT_24_HOUR -> TimeFormatUi.FORMAT_24_HOUR
}

fun TimeFormatUi.toDomain(): TimeFormat = when (this) {
    TimeFormatUi.FORMAT_12_HOUR -> TimeFormat.FORMAT_12_HOUR
    TimeFormatUi.FORMAT_24_HOUR -> TimeFormat.FORMAT_24_HOUR
}
```

## app\src\main\java\com\mhq\salati\settings\presentation\model\AdhanSoundUi.kt

```kotlin
package com.mhq.salati.settings.presentation.model

enum class AdhanSoundUi {
    DEFAULT,
    EGYPT,
    MAKKAH,
    MADINAH,
    SILENT
}
```

## app\src\main\java\com\mhq\salati\settings\presentation\model\AppLanguageUi.kt

```kotlin
package com.mhq.salati.settings.presentation.model

enum class AppLanguageUi { ENGLISH, ARABIC }
```

## app\src\main\java\com\mhq\salati\settings\presentation\model\CalculationMethodUi.kt

```kotlin
package com.mhq.salati.settings.presentation.model

enum class CalculationMethodUi {
    EGYPTIAN_GENERAL_AUTHORITY,
    UMM_AL_QURA,
    MUSLIM_WORLD_LEAGUE,
    ISLAMIC_SOCIETY_OF_NORTH_AMERICA,
    UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI
}
```

## app\src\main\java\com\mhq\salati\settings\presentation\model\CustomAlarmSoundUi.kt

```kotlin
package com.mhq.salati.settings.presentation.model

enum class CustomAlarmSoundUi {
    DEFAULT,
    ALERT
}
```

## app\src\main\java\com\mhq\salati\settings\presentation\model\MadhabUi.kt

```kotlin
package com.mhq.salati.settings.presentation.model

enum class MadhabUi { SHAFI, HANAFI }
```

## app\src\main\java\com\mhq\salati\settings\presentation\model\ThemeModeUi.kt

```kotlin
package com.mhq.salati.settings.presentation.model

enum class ThemeModeUi { SYSTEM, LIGHT, DARK }
```

## app\src\main\java\com\mhq\salati\settings\presentation\model\TimeFormatUi.kt

```kotlin
package com.mhq.salati.settings.presentation.model

/** How clock times, such as prayer times, are displayed. */
enum class TimeFormatUi { FORMAT_12_HOUR, FORMAT_24_HOUR }
```

## app\src\main\java\com\mhq\salati\settings\presentation\SelectorType.kt

```kotlin
package com.mhq.salati.settings.presentation

enum class SelectorType {
    CalculationMethod,
    Madhab,
    Theme,
    Language,
    AdhanSound,
    CustomAlarmSound,
    TimeFormat
}
```

## app\src\main\java\com\mhq\salati\settings\presentation\viewmodel\SettingsViewModel.kt

```kotlin
package com.mhq.salati.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.domain.usecases.UpdateAdhanSoundUseCase
import com.mhq.salati.settings.domain.usecases.UpdateCalculationMethodUseCase
import com.mhq.salati.settings.domain.usecases.UpdateCustomAlarmSoundUseCase
import com.mhq.salati.settings.domain.usecases.UpdateHijriDateOffsetUseCase
import com.mhq.salati.settings.domain.usecases.UpdateLanguageUseCase
import com.mhq.salati.settings.domain.usecases.UpdateMadhabUseCase
import com.mhq.salati.settings.domain.usecases.UpdateNotificationsEnabledUseCase
import com.mhq.salati.settings.domain.usecases.UpdateThemeModeUseCase
import com.mhq.salati.settings.domain.usecases.UpdateTimeFormatUseCase
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.settings.presentation.mapper.toDomain
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val updateNotificationsEnabledUseCase: UpdateNotificationsEnabledUseCase,
    private val updateCalculationMethodUseCase: UpdateCalculationMethodUseCase,
    private val updateMadhabUseCase: UpdateMadhabUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateAdhanSoundUseCase: UpdateAdhanSoundUseCase,
    private val updateCustomAlarmSoundUseCase: UpdateCustomAlarmSoundUseCase,
    private val updateHijriDateOffsetUseCase: UpdateHijriDateOffsetUseCase,
    private val updateTimeFormatUseCase: UpdateTimeFormatUseCase,
    private val permissionChecker: PermissionChecker
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsContract.State()
    )
    val state: StateFlow<SettingsContract.State> = _state.asStateFlow()

    private val _effect = Channel<SettingsContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        observeSettingsUseCase()
            .onEach { settings -> applySettingsToState(settings) }
            .launchIn(viewModelScope)

        refreshNotificationPermissionState()
    }

    fun onIntent(intent: SettingsContract.Intent) {
        when (intent) {
            is SettingsContract.Intent.ToggleNotifications -> toggleNotifications(intent.enabled)
            is SettingsContract.Intent.NotificationPermissionResult -> onNotificationPermissionResult(intent.granted)
            is SettingsContract.Intent.RecheckNotificationPermission -> refreshNotificationPermissionState()
            is SettingsContract.Intent.SelectCalculationMethod -> updateCalculationMethod(intent.method)
            is SettingsContract.Intent.SelectMadhab -> updateMadhab(intent.madhab)
            is SettingsContract.Intent.SelectTheme -> updateTheme(intent.mode)
            is SettingsContract.Intent.SelectLanguage -> updateLanguage(intent.language)
            is SettingsContract.Intent.SelectAdhanSound -> updateAdhanSound(intent.sound)
            is SettingsContract.Intent.SelectCustomAlarmSound -> updateCustomAlarmSound(intent.sound)
            is SettingsContract.Intent.IncrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset + 1)
            is SettingsContract.Intent.DecrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset - 1)
            is SettingsContract.Intent.OpenSelector -> _state.update { it.copy(activeSelector = intent.type) }
            is SettingsContract.Intent.CloseSelector -> _state.update { it.copy(activeSelector = null) }
            is SettingsContract.Intent.RateApp -> emitEffect(SettingsContract.Effect.OpenPlayStoreListing)
            is SettingsContract.Intent.ShareApp -> emitEffect(SettingsContract.Effect.LaunchShareSheet)
            is SettingsContract.Intent.ContactSupport -> emitEffect(SettingsContract.Effect.OpenEmailClient)
            is SettingsContract.Intent.SelectTimeFormat -> updateTimeFormat(intent.format)
        }
    }

    private fun applySettingsToState(settings: AppSettings) {
        _state.update {
            it.copy(
                isLoading = false,
                notificationsEnabled = settings.notificationsEnabled,
                calculationMethod = settings.calculationMethod.toUi(),
                madhab = settings.madhab.toUi(),
                themeMode = settings.themeMode.toUi(),
                language = settings.language.toUi(),
                adhanSound = settings.adhanSound.toUi(),
                customAlarmSound = settings.customAlarmSound.toUi(),
                hijriDateOffset = settings.hijriDateOffset,
                timeFormat = settings.timeFormat.toUi()
            )
        }
    }

    private fun refreshNotificationPermissionState() = viewModelScope.launch {
        val granted = permissionChecker.hasNotificationPermission()
        _state.update { it.copy(hasNotificationPermission = granted) }
    }

    // Turning the toggle on when the OS permission isn't granted must NOT persist
    // notificationsEnabled=true yet — that's what caused the toggle to show "on"
    // while notifications were actually blocked. Instead, we ask for the
    // permission first and only persist once we know the real outcome.
    private fun toggleNotifications(enabled: Boolean) {
        if (enabled && !_state.value.hasNotificationPermission) {
            emitEffect(SettingsContract.Effect.RequestNotificationPermission)
            return
        }
        updateNotifications(enabled)
    }

    private fun onNotificationPermissionResult(granted: Boolean) {
        _state.update { it.copy(hasNotificationPermission = granted) }
        // Only persist the app-level flag to true once permission is confirmed.
        // On denial, we deliberately leave notificationsEnabled untouched in
        // DataStore and let the UI derive "off" from hasNotificationPermission,
        // so the row doesn't silently flip user intent behind their back.
        if (granted) {
            updateNotifications(true)
        }
    }

    private fun launchUpdate(errorMessage: MessageUi, block: suspend () -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = errorMessage
                _state.update { it.copy(errorMessage = message) }
                _effect.send(SettingsContract.Effect.ShowError(message))
            }
        }

    private fun updateNotifications(enabled: Boolean) =
        launchUpdate(MessageUi.COULDNT_UPDATE_NOTIFICATION_SETTINGS) {
            updateNotificationsEnabledUseCase(enabled)
        }

    private fun updateCalculationMethod(method: CalculationMethodUi) =
        launchUpdate(MessageUi.COULDNT_UPDATE_CALCULATION_METHOD) {
            updateCalculationMethodUseCase(method.toDomain())
        }

    private fun updateMadhab(madhab: MadhabUi) =
        launchUpdate(MessageUi.COULDNT_UPDATE_MADHAB) { updateMadhabUseCase(madhab.toDomain()) }

    private fun updateTheme(mode: ThemeModeUi) =
        launchUpdate(MessageUi.COULDNT_UPDATE_THEME) { updateThemeModeUseCase(mode.toDomain()) }

    private fun updateLanguage(language: AppLanguageUi) =
        launchUpdate(MessageUi.COULDNT_UPDATE_LANGUAGE) {
            val domainLanguage = language.toDomain()
            updateLanguageUseCase(domainLanguage)
            _effect.send(SettingsContract.Effect.LanguageChangedRestartRequired(domainLanguage.code))
        }

    private fun updateAdhanSound(sound: AdhanSoundUi) =
        launchUpdate(MessageUi.COULDNT_UPDATE_ADHAN_SOUND) { updateAdhanSoundUseCase(sound.toDomain()) }

    private fun updateCustomAlarmSound(sound: CustomAlarmSoundUi) =
        launchUpdate(MessageUi.COULDNT_UPDATE_CUSTOM_ALARM_SOUND) {
            updateCustomAlarmSoundUseCase(sound.toDomain())
        }

    private fun updateHijriOffset(offset: Int) =
        launchUpdate(MessageUi.COULDNT_UPDATE_HIJRI_OFFSET) {
            updateHijriDateOffsetUseCase(offset.coerceIn(-2, 2))
        }

    private fun updateTimeFormat(format: TimeFormatUi) =
        launchUpdate(MessageUi.COULDNT_UPDATE_TIME_FORMAT) { updateTimeFormatUseCase(format.toDomain()) }

    private fun emitEffect(effect: SettingsContract.Effect) =
        viewModelScope.launch {
            _effect.send(effect)
        }
}
```

## app\src\main\java\com\mhq\salati\settings\ui\components\SettingsActionRow.kt

```kotlin
package com.mhq.salati.settings.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.components.IconBadge
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun SettingsActionRow(
    icon: ImageVector,
    title: String,
    trailingText: String? = null,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        IconBadge(icon)
        Spacer(Modifier.width(14.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        trailingText?.let {
            Text(
                text = it,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } ?: run {
            if (onClick != null) {
                Icon(
                    imageVector = if (isRtl) Icons.Filled.ChevronLeft else Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsActionRowPreview() {
    SalatiTheme() {
        SettingsActionRow(
            icon = Icons.Filled.CheckCircle,
            title = "title",
            trailingText = "trailingText",
            onClick = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\settings\ui\components\SettingsSectionHeader.kt

```kotlin
package com.mhq.salati.settings.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun SettingsSectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.sp,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
        modifier = modifier.padding(bottom = 8.dp, start = 4.dp)
    )
}

@Preview
@Composable
private fun SettingsSectionHeaderPreview() {
    SalatiTheme() {
        SettingsSectionHeader("SettingsSectionHeader")
    }
}
```

## app\src\main\java\com\mhq\salati\settings\ui\components\SettingsSelectionSheet.kt

```kotlin
package com.mhq.salati.settings.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.SelectorType
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.presentation.contract.SettingsContract.State
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.components.SelectionSheet
import com.mhq.salati.shared.ui.components.selectionOptions
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun SettingsSelectionSheet(
    state: State,
    selector: SelectorType,
    onSelect: (Intent) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (titleRes, options) = when (selector) {
        SelectorType.CalculationMethod -> R.string.calculation_method to selectionOptions(
            CalculationMethodUi.entries, state.calculationMethod, { it.displayNameRes }
        ) { onSelect(Intent.SelectCalculationMethod(it)) }

        SelectorType.Madhab -> R.string.asr_madhab to selectionOptions(
            MadhabUi.entries, state.madhab, { it.displayNameRes }
        ) { onSelect(Intent.SelectMadhab(it)) }

        SelectorType.Theme -> R.string.theme to selectionOptions(
            ThemeModeUi.entries, state.themeMode, { it.displayNameRes }
        ) { onSelect(Intent.SelectTheme(it)) }

        SelectorType.Language -> R.string.language to selectionOptions(
            AppLanguageUi.entries, state.language, { it.displayNameRes }
        ) { onSelect(Intent.SelectLanguage(it)) }

        SelectorType.AdhanSound -> R.string.adhan_sound to selectionOptions(
            AdhanSoundUi.entries, state.adhanSound, { it.displayNameRes }
        ) { onSelect(Intent.SelectAdhanSound(it)) }

        SelectorType.CustomAlarmSound -> R.string.custom_alarm_sound to selectionOptions(
            CustomAlarmSoundUi.entries, state.customAlarmSound, { it.displayNameRes }
        ) { onSelect(Intent.SelectCustomAlarmSound(it)) }

        SelectorType.TimeFormat -> R.string.time_format to selectionOptions(
            TimeFormatUi.entries, state.timeFormat, { it.displayNameRes }
        ) { onSelect(Intent.SelectTimeFormat(it)) }
    }

    SelectionSheet(
        titleRes = titleRes,
        options = options,
        onDismiss = onDismiss,
        modifier = modifier
    )
}

@Preview
@Composable
private fun SettingsSelectionSheetPreview() {
    SalatiTheme {
        SettingsSelectionSheet(
            state = State(
                isLoading = true,
                notificationsEnabled = true,
                calculationMethod = CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY,
                madhab = MadhabUi.SHAFI,
                themeMode = ThemeModeUi.SYSTEM,
                language = AppLanguageUi.ENGLISH,
                adhanSound = AdhanSoundUi.DEFAULT,
                hijriDateOffset = 0
            ),
            selector = SelectorType.CalculationMethod,
            onSelect = {},
            onDismiss = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\settings\ui\components\SettingsStepperRow.kt

```kotlin
package com.mhq.salati.settings.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.components.IconBadge
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun SettingsStepperRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        IconBadge(icon)
        Spacer(Modifier.width(14.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(onClick = onDecrement, modifier = Modifier.size(32.dp)) {
            Icon(
                Icons.Default.RemoveCircleOutline,
                contentDescription = stringResource(R.string.cd_decrease),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = if (value > 0) "+$value" else "$value",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 6.dp)
        )
        IconButton(onClick = onIncrement, modifier = Modifier.size(32.dp)) {
            Icon(
                Icons.Default.AddCircleOutline,
                contentDescription = stringResource(R.string.cd_increase),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun SettingsStepperRowPreview() {
    SalatiTheme() {
        SettingsStepperRow(
            icon = Icons.Filled.CheckCircle,
            title = "title",
            subtitle = "subtitle",
            value = 1,
            onIncrement = {},
            onDecrement = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\settings\ui\components\SettingsSwitchRow.kt

```kotlin
package com.mhq.salati.settings.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.components.IconBadge
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun SettingsSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        IconBadge(icon)
        Spacer(Modifier.width(14.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview(name = "Light Mode Row", showBackground = true)
@Preview(name = "Dark Mode Row", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsSwitchRowPreview() {
    SalatiTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            SettingsSwitchRow(
                icon = Icons.Default.NotificationsActive,
                title = "Push Notifications",
                subtitle = "Receive reminders right at the time of entry",
                isChecked = true,
                onCheckedChange = {}
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\settings\ui\screens\SettingsContainer.kt

```kotlin
package com.mhq.salati.settings.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.core.os.LocaleListCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.BuildConfig
import com.mhq.salati.settings.presentation.contract.SettingsContract.Effect
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.presentation.viewmodel.SettingsViewModel
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.shared.ui.asString
import kotlinx.coroutines.launch
import android.content.Intent as AndroidIntent

@Composable
fun SettingsContainer(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by settingsViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        settingsViewModel.onIntent(Intent.NotificationPermissionResult(granted))
    }

    // Catches the case where the user denies here, later flips it on from
    // system Settings, and comes back — recompose the real permission state
    // rather than trusting whatever we last knew.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                settingsViewModel.onIntent(Intent.RecheckNotificationPermission)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(Unit) {
        settingsViewModel.effect.collect { effect ->
            when (effect) {
                is Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }

                Effect.LaunchShareSheet -> {
                    val shareIntent = AndroidIntent(AndroidIntent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            AndroidIntent.EXTRA_TEXT,
                            "Check out Salati — prayer times & Qibla direction: " +
                                    "https://play.google.com/store/apps/details?id=${context.packageName}"
                        )
                    }
                    context.startActivity(
                        AndroidIntent.createChooser(
                            shareIntent,
                            "Share Salati"
                        )
                    )
                }

                Effect.OpenPlayStoreListing -> {
                    val uri = "market://details?id=${context.packageName}".toUri()
                    runCatching {
                        context.startActivity(
                            AndroidIntent(
                                AndroidIntent.ACTION_VIEW,
                                uri
                            )
                        )
                    }
                        .onFailure {
                            val webUri = "https://play.google.com/store/apps/details?id=${context.packageName}".toUri()
                            context.startActivity(
                                AndroidIntent(AndroidIntent.ACTION_VIEW, webUri)
                            )
                        }
                }

                Effect.OpenEmailClient -> {
                    val emailIntent = AndroidIntent(AndroidIntent.ACTION_SENDTO).apply {
                        data = "mailto:".toUri()
                        putExtra(
                            AndroidIntent.EXTRA_EMAIL,
                            arrayOf(
                                "support@salati-app.com"
                            )
                        )
                        putExtra(
                            AndroidIntent.EXTRA_SUBJECT,
                            "Salati App Feedback"
                        )
                    }
                    context.startActivity(emailIntent)
                }

                is Effect.LanguageChangedRestartRequired -> {
                    val localeList = LocaleListCompat.forLanguageTags(effect.languageCode)
                    AppCompatDelegate.setApplicationLocales(localeList)
                }

                Effect.RequestNotificationPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        // Pre-33: no runtime permission exists, so PermissionChecker
                        // should already report `true` here — nothing to request.
                        settingsViewModel.onIntent(Intent.NotificationPermissionResult(true))
                    }
                }
            }
        }
    }

    SettingsContent(
        state = state,
        appVersion = BuildConfig.VERSION_NAME,
        onIntent = settingsViewModel::onIntent,
        modifier = Modifier.fillMaxSize()
    )
}
```

## app\src\main\java\com\mhq\salati\settings\ui\screens\SettingsContent.kt

```kotlin
package com.mhq.salati.settings.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.SelectorType
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.presentation.contract.SettingsContract.State
import com.mhq.salati.settings.ui.components.SettingsActionRow
import com.mhq.salati.settings.ui.components.SettingsSectionHeader
import com.mhq.salati.settings.ui.components.SettingsSelectionSheet
import com.mhq.salati.settings.ui.components.SettingsStepperRow
import com.mhq.salati.settings.ui.components.SettingsSwitchRow
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.ScreenHorizontalPadding
import com.mhq.salati.shared.ui.components.ActionRow
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.GroupDivider
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme

/** Tall screens: the settings list never stretches wider than this (big portrait tablets). */
private val SettingsPortraitMaxWidth = 560.dp

/** Wide screens (landscape, tablets, foldables, split-screen). */
private object SettingsWideLayout {
    /** A pane's rows never stretch wider than this, even when the pane itself is huge. */
    val PaneMaxWidth = 420.dp

    val PaneSpacing = 24.dp
}

/**
 * Settings tab.
 *
 * - Narrow windows: one centered, lazy list bounded by [SettingsPortraitMaxWidth].
 * - Wide windows: two balanced panes side by side (notifications & calculation on the start
 *   side, appearance, Hijri and about on the end side), each scrolling on its own.
 * - The header sits in the column flow (like Tracker), no measured-height overlay hack is needed,
 *   and it grows safely with font scale or Arabic line heights.
 */
@Composable
fun SettingsContent(
    state: State,
    appVersion: String,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabHeader(
                icon = Icons.Default.Settings,
                title = stringResource(R.string.settings),
                subtitle = stringResource(R.string.customize_your_app_experience)
            )

            if (isWideLayout()) {
                SettingsWideContent(
                    state = state,
                    appVersion = appVersion,
                    onIntent = onIntent,
                    modifier = Modifier.weight(1f)
                )
            } else {
                SettingsNarrowContent(
                    state = state,
                    appVersion = appVersion,
                    onIntent = onIntent,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        state.activeSelector?.let { selector ->
            SettingsSelectionSheet(
                state = state,
                selector = selector,
                onSelect = { intent ->
                    onIntent(intent)
                    onIntent(Intent.CloseSelector)
                },
                onDismiss = { onIntent(Intent.CloseSelector) }
            )
        }
    }
}

/**
 * One centered list. Each section is its own [LazyColumn] item (real laziness), and the list is
 * bounded by [SettingsPortraitMaxWidth] so rows don't stretch on tablets. Display-cutout insets
 * keep the rows clear of a notch.
 */
@Composable
private fun SettingsNarrowContent(
    state: State,
    appVersion: String,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
            .navigationBarsPadding()
            .padding(bottom = BottomNavBarDefaults.Height)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = ScreenHorizontalPadding,
                end = ScreenHorizontalPadding,
                top = 24.dp,
                bottom = 40.dp
            ),
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = SettingsPortraitMaxWidth)
        ) {
            item(key = "notifications") {
                NotificationsSection(state = state, onIntent = onIntent)
            }

            item(key = "calculation") {
                SectionGap()
                PrayerCalculationSection(state = state, onIntent = onIntent)
            }

            item(key = "appearance") {
                SectionGap()
                AppearanceSection(state = state, onIntent = onIntent)
            }

            item(key = "hijri") {
                SectionGap()
                HijriCalendarSection(state = state, onIntent = onIntent)
            }

            item(key = "about") {
                SectionGap()
                AboutSection(appVersion = appVersion, onIntent = onIntent)
            }
        }
    }
}

/**
 * Two panes side by side, balanced by section count.
 *
 * Both panes scroll when the window is too short (small landscape, large font scale, Arabic):
 * `fillMaxHeight().verticalScroll()` keeps the content centered while it fits, exactly like the
 * Tracker panes. Display-cutout insets keep both panes clear of a notch in landscape.
 */
@Composable
private fun SettingsWideContent(
    state: State,
    appVersion: String,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(SettingsWideLayout.PaneSpacing),
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
            .navigationBarsPadding()
            .padding(
                start = ScreenHorizontalPadding,
                end = ScreenHorizontalPadding,
                bottom = BottomNavBarDefaults.Height
            )
    ) {
        SettingsPane(modifier = Modifier.weight(1f)) {
            NotificationsSection(state = state, onIntent = onIntent)
            SectionGap()
            PrayerCalculationSection(state = state, onIntent = onIntent)
        }

        SettingsPane(modifier = Modifier.weight(1f)) {
            AppearanceSection(state = state, onIntent = onIntent)
            SectionGap()
            HijriCalendarSection(state = state, onIntent = onIntent)
            SectionGap()
            AboutSection(appVersion = appVersion, onIntent = onIntent)
        }
    }
}

/**
 * One scrollable, horizontally bounded pane. The inner column is capped at
 * [SettingsWideLayout.PaneMaxWidth] and centered, so rows keep a sane width on tablets.
 */
@Composable
private fun SettingsPane(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = SettingsWideLayout.PaneMaxWidth)
                .fillMaxWidth(),
            content = content
        )
    }
}

@Composable
private fun SectionGap() {
    Spacer(Modifier.height(28.dp))
}

@Composable
private fun NotificationsSection(state: State, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.notifications))
    GroupCard {
        SettingsSwitchRow(
            icon = Icons.Default.NotificationsActive,
            title = stringResource(R.string.prayer_notifications),
            subtitle = stringResource(R.string.get_alerted_and_notified_at_each_prayer_time),
            isChecked = state.notificationsEnabled && state.hasNotificationPermission,
            onCheckedChange = { onIntent(Intent.ToggleNotifications(it)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.MusicNote,
            title = stringResource(R.string.adhan_sound),
            valueLabel = stringResource(state.adhanSound.displayNameRes),
            isEnabled = state.notificationsEnabled,
            onClick = { onIntent(Intent.OpenSelector(SelectorType.AdhanSound)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.MusicNote,
            title = stringResource(R.string.custom_alarm_sound),
            valueLabel = stringResource(state.customAlarmSound.displayNameRes),
            isEnabled = state.notificationsEnabled,
            onClick = { onIntent(Intent.OpenSelector(SelectorType.CustomAlarmSound)) }
        )
    }
}

@Composable
private fun PrayerCalculationSection(state: State, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.prayer_calculation))
    GroupCard {
        ActionRow(
            icon = Icons.Default.Explore,
            title = stringResource(R.string.calculation_method),
            valueLabel = stringResource(state.calculationMethod.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.CalculationMethod)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.School,
            title = stringResource(R.string.asr_madhab),
            valueLabel = stringResource(state.madhab.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.Madhab)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.Schedule,
            title = stringResource(R.string.time_format),
            valueLabel = stringResource(state.timeFormat.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.TimeFormat)) }
        )
    }
}

@Composable
private fun AppearanceSection(state: State, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.appearance))
    GroupCard {
        ActionRow(
            icon = Icons.Default.DarkMode,
            title = stringResource(R.string.theme),
            valueLabel = stringResource(state.themeMode.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.Theme)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.Language,
            title = stringResource(R.string.language),
            valueLabel = stringResource(state.language.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.Language)) }
        )
    }
}

@Composable
private fun HijriCalendarSection(state: State, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.hijri_calendar))
    GroupCard {
        SettingsStepperRow(
            icon = Icons.Default.CalendarMonth,
            title = stringResource(R.string.date_adjustment),
            subtitle = stringResource(R.string.correct_for_local_moon_sighting),
            value = state.hijriDateOffset,
            onIncrement = { onIntent(Intent.IncrementHijriOffset) },
            onDecrement = { onIntent(Intent.DecrementHijriOffset) }
        )
    }
}

@Composable
private fun AboutSection(appVersion: String, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.about))
    GroupCard {
        SettingsActionRow(
            icon = Icons.Default.StarRate,
            title = stringResource(R.string.rate_salati),
            onClick = { onIntent(Intent.RateApp) }
        )
        GroupDivider()
        SettingsActionRow(
            icon = Icons.Default.Share,
            title = stringResource(R.string.share_with_friends),
            onClick = { onIntent(Intent.ShareApp) }
        )
        GroupDivider()
        SettingsActionRow(
            icon = Icons.Default.Email,
            title = stringResource(R.string.contact_support),
            onClick = { onIntent(Intent.ContactSupport) }
        )
        GroupDivider()
        SettingsActionRow(
            icon = Icons.Default.Info,
            title = stringResource(R.string.version),
            trailingText = appVersion,
            onClick = null
        )
    }
}

@Preview(name = "Portrait", showSystemUi = true)
@Preview(name = "Landscape", device = "spec:width=800dp,height=400dp,dpi=440", showSystemUi = true)
@Preview(name = "Small landscape", device = "spec:width=640dp,height=320dp,dpi=420", showSystemUi = true)
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240", showSystemUi = true)
@Preview(name = "Arabic Large Font", locale = "ar", fontScale = 1.5f, showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsContentPreview() {
    SalatiTheme {
        SettingsContent(
            state = State(
                isLoading = false,
                notificationsEnabled = true,
                hasNotificationPermission = true,
                hijriDateOffset = 1
            ),
            appVersion = "1.0.4-release",
            onIntent = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\settings\ui\SettingsLabels.kt

```kotlin
package com.mhq.salati.settings.ui

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi

val AppLanguageUi.displayNameRes: Int
    @StringRes get() = when (this) {
        AppLanguageUi.ENGLISH -> R.string.language_english
        AppLanguageUi.ARABIC -> R.string.language_arabic
    }

val TimeFormatUi.displayNameRes: Int
    @StringRes get() = when (this) {
        TimeFormatUi.FORMAT_12_HOUR -> R.string.time_format_12_hour
        TimeFormatUi.FORMAT_24_HOUR -> R.string.time_format_24_hour
    }

val ThemeModeUi.displayNameRes: Int
    @StringRes get() = when (this) {
        ThemeModeUi.SYSTEM -> R.string.theme_system_default
        ThemeModeUi.LIGHT -> R.string.theme_light
        ThemeModeUi.DARK -> R.string.theme_dark
    }

val MadhabUi.displayNameRes: Int
    @StringRes get() = when (this) {
        MadhabUi.SHAFI -> R.string.madhab_shafi
        MadhabUi.HANAFI -> R.string.madhab_hanafi
    }

val CalculationMethodUi.displayNameRes: Int
    @StringRes get() = when (this) {
        CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY -> R.string.calc_method_egyptian_general_authority
        CalculationMethodUi.UMM_AL_QURA -> R.string.calc_method_umm_al_qura
        CalculationMethodUi.MUSLIM_WORLD_LEAGUE -> R.string.calc_method_muslim_world_league
        CalculationMethodUi.ISLAMIC_SOCIETY_OF_NORTH_AMERICA -> R.string.calc_method_isna
        CalculationMethodUi.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI -> R.string.calc_method_karachi
    }

val AdhanSoundUi.displayNameRes: Int
    @StringRes get() = when (this) {
        AdhanSoundUi.DEFAULT -> R.string.adhan_sound_default
        AdhanSoundUi.EGYPT -> R.string.adhan_sound_egypt
        AdhanSoundUi.MAKKAH -> R.string.adhan_sound_makkah
        AdhanSoundUi.MADINAH -> R.string.adhan_sound_madinah
        AdhanSoundUi.SILENT -> R.string.adhan_sound_silent
    }

val CustomAlarmSoundUi.displayNameRes: Int
    @StringRes get() = when (this) {
        CustomAlarmSoundUi.DEFAULT -> R.string.custom_alarm_sound_default
        CustomAlarmSoundUi.ALERT -> R.string.custom_alarm_sound_alert
    }

/** The bundled sound file, or null to use the system default. */
val CustomAlarmSoundUi.rawRes: Int?
    @RawRes get() = when (this) {
        CustomAlarmSoundUi.DEFAULT -> null
        CustomAlarmSoundUi.ALERT -> R.raw.minor_timing_alert
    }
```

## app\src\main\java\com\mhq\salati\shared\app\SalatiApplication.kt

```kotlin
package com.mhq.salati.shared.app

import android.app.Application
import com.mhq.salati.adhan.ui.notification.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SalatiApplication : Application() {
    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        notificationHelper.ensureChannelsCreated()
    }
}
```

## app\src\main\java\com\mhq\salati\shared\data\mapper\PrayerNameMapper.kt

```kotlin
package com.mhq.salati.shared.data.mapper

import com.mhq.salati.shared.domain.PrayerName

// The text a prayer is saved as in Room rows and Intent extras.
// These values are already stored on users' devices (and inside scheduled alarms), so never change them.

val PrayerName.storageKey: String
    get() = when (this) {
        PrayerName.FAJR -> "Fajr"
        PrayerName.DHUHR -> "Dhuhr"
        PrayerName.ASR -> "Asr"
        PrayerName.MAGHRIB -> "Maghrib"
        PrayerName.ISHA -> "Isha"
        PrayerName.IMSAK -> "Imsak"
        PrayerName.SHOROUQ -> "Shorouq"
        PrayerName.FIRST_THIRD -> "First Third"
        PrayerName.MIDNIGHT -> "Midnight"
        PrayerName.LAST_THIRD -> "Last Third"
    }

// Declared on the companion so existing `PrayerName.fromStorageKey(key)` calls keep working (they only need this import).
fun PrayerName.Companion.fromStorageKey(key: String): PrayerName? =
    PrayerName.entries.find { it.storageKey == key }
```

## app\src\main\java\com\mhq\salati\shared\data\repoimpl\PrayerNameResolverImpl.kt

```kotlin
package com.mhq.salati.shared.data.repoimpl

import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.PrayerNameResolver
import javax.inject.Inject

class PrayerNameResolverImpl @Inject constructor() : PrayerNameResolver {
    override fun resolve(key: String): PrayerName? = PrayerName.fromStorageKey(key)
}
```

## app\src\main\java\com\mhq\salati\shared\data\SystemClock.kt

```kotlin
package com.mhq.salati.shared.data

import com.mhq.salati.shared.domain.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class SystemClock @Inject constructor() : Clock {
    override fun now(): LocalDateTime = LocalDateTime.now()
    override fun today(): LocalDate = LocalDate.now()
    override fun instant(): Instant = Instant.now()
    override fun zone(): ZoneId = ZoneId.systemDefault()
}
```

## app\src\main\java\com\mhq\salati\shared\datasource\database\SalatiDatabase.kt

```kotlin
package com.mhq.salati.shared.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhq.salati.adhan.datasource.database.MutedPrayerDao
import com.mhq.salati.adhan.datasource.database.MutedPrayerEntity
import com.mhq.salati.alarms.datasource.database.CustomAlarmDao
import com.mhq.salati.alarms.datasource.database.CustomAlarmEntity
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesDao
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesEntity
import com.mhq.salati.prayertracker.datasource.database.PrayerRecordDao
import com.mhq.salati.prayertracker.datasource.database.PrayerRecordEntity

@Database(
    entities = [
        PrayerTimesEntity::class,
        MutedPrayerEntity::class,
        PrayerRecordEntity::class,
        CustomAlarmEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SalatiDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
    abstract fun mutedPrayerDao(): MutedPrayerDao
    abstract fun prayerRecordDao(): PrayerRecordDao
    abstract fun customAlarmDao(): CustomAlarmDao
}
```

## app\src\main\java\com\mhq\salati\shared\datasource\device\ComponentTarget.kt

```kotlin
package com.mhq.salati.shared.datasource.device

/**
 * The Android component (a receiver or a service)
 * that an intent should be delivered to.
 * It is provided by the DI modules,
 * so a DataSource never has to import a UI class.
 */
class ComponentTarget(val componentClass: Class<*>)
```

## app\src\main\java\com\mhq\salati\shared\datasource\network\KtorClient.kt

```kotlin
package com.mhq.salati.shared.datasource.network

import com.mhq.salati.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    private const val REQUEST_TIMEOUT_MILLIS = 8_000L
    private const val CONNECT_TIMEOUT_MILLIS = 5_000L
    private const val SOCKET_TIMEOUT_MILLIS = 8_000L

    val instance: HttpClient by lazy {
        HttpClient(CIO) {

            defaultRequest {
                header(HttpHeaders.UserAgent, "SalatiApp/1.0 (${BuildConfig.CONTACT_EMAIL})")
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                level = if (BuildConfig.DEBUG) LogLevel.INFO else LogLevel.NONE
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_MILLIS
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\di\ClockModule.kt

```kotlin
package com.mhq.salati.shared.di

import com.mhq.salati.shared.data.SystemClock
import com.mhq.salati.shared.domain.Clock
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClockModule {

    @Binds
    @Singleton
    abstract fun bindClock(impl: SystemClock): Clock
}
```

## app\src\main\java\com\mhq\salati\shared\di\DatabaseModule.kt

```kotlin
package com.mhq.salati.shared.di

import android.content.Context
import androidx.room.Room
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSalatiDatabase(
        @ApplicationContext context: Context
    ): SalatiDatabase {
        return Room.databaseBuilder(
            context,
            SalatiDatabase::class.java,
            "salati_database"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
}
```

## app\src\main\java\com\mhq\salati\shared\di\NetworkModule.kt

```kotlin
package com.mhq.salati.shared.di

import com.mhq.salati.shared.datasource.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = KtorClient.instance
}
```

## app\src\main\java\com\mhq\salati\shared\di\PrayerNameModule.kt

```kotlin
package com.mhq.salati.shared.di

import com.mhq.salati.shared.data.repoimpl.PrayerNameResolverImpl
import com.mhq.salati.shared.domain.PrayerNameResolver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PrayerNameModule {

    @Binds
    @Singleton
    abstract fun bindPrayerNameResolver(impl: PrayerNameResolverImpl): PrayerNameResolver
}
```

## app\src\main\java\com\mhq\salati\shared\domain\Clock.kt

```kotlin
package com.mhq.salati.shared.domain

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Injectable source of the current date and time.
 *
 * Wraps [java.time] clock reads so domain code never touches the system clock directly.
 * Production binds it to the real system time; tests bind it to a fixed or scripted time,
 * which makes time-dependent use cases deterministic.
 */
interface Clock {
    /** The current date and time in the device's time zone. */
    fun now(): LocalDateTime

    /** The current calendar date in the device's time zone. */
    fun today(): LocalDate

    /** The current moment as a zone-independent [Instant]. */
    fun instant(): Instant

    /** The time zone all other methods resolve their results in. */
    fun zone(): ZoneId
}
```

## app\src\main\java\com\mhq\salati\shared\domain\Coordinates.kt

```kotlin
package com.mhq.salati.shared.domain

/**
 * A geographic position in decimal degrees.
 *
 * Validated on construction: latitude must lie in [-90, 90] and longitude in [-180, 180].
 * This guarantees every [Coordinates] in the app is a usable location,
 * so consumers never re-check the ranges.
 *
 * @property latitude north-south position in degrees, from -90 (South Pole) to 90 (North Pole)
 * @property longitude east-west position in degrees, from -180 to 180
 * @throws IllegalArgumentException if either value is outside its valid range
 */
data class Coordinates(
    val latitude: Double,
    val longitude: Double
) {
    init {
        require(latitude in -90.0..90.0) {
            "Latitude must be between -90 and 90 but was $latitude"
        }

        require(longitude in -180.0..180.0) {
            "Longitude must be between -180 and 180 but was $longitude"
        }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\domain\DateKey.kt

```kotlin
package com.mhq.salati.shared.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Formatter for the stable string key a calendar date is stored under.
 * Pinned to [Locale.US] so the digits never depend on the app or device language;
 * Arabic locale settings must not change what is written to storage.
 */
private val DateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

/**
 * The "dd-MM-yyyy" key that prayer times and muted prayers are stored under.
 * This key is part of the persisted data format:
 * changing the pattern would orphan every previously saved row.
 */
fun LocalDate.toDateKey(): String = format(DateKeyFormatter)

/**
 * Parses a key produced by [toDateKey] back into a [LocalDate].
 *
 * @throws java.time.format.DateTimeParseException if the string is not a valid "dd-MM-yyyy" key
 */
fun String.toLocalDateFromKey(): LocalDate = LocalDate.parse(this, DateKeyFormatter)
```

## app\src\main\java\com\mhq\salati\shared\domain\HijriMonth.kt

```kotlin
package com.mhq.salati.shared.domain

/**
 * The twelve months of the Hijri calendar, in order.
 *
 * @property number the 1-based month index used by Hijri date conversions and stored data
 */
enum class HijriMonth(val number: Int) {
    MUHARRAM(1),
    SAFAR(2),
    RABI_AL_AWWAL(3),
    RABI_AL_THANI(4),
    JUMADA_AL_AWWAL(5),
    JUMADA_AL_THANI(6),
    RAJAB(7),
    SHABAN(8),
    RAMADAN(9),
    SHAWWAL(10),
    DHU_AL_QIDAH(11),
    DHU_AL_HIJJAH(12);

    companion object {
        /**
         * Returns the month with the given [number], or [MUHARRAM] when no month matches.
         * The fallback keeps callers safe against out-of-range values
         * coming from external date converters or corrupted stored data.
         */
        fun fromNumber(number: Int): HijriMonth =
            entries.firstOrNull { it.number == number } ?: MUHARRAM
    }
}
```

## app\src\main\java\com\mhq\salati\shared\domain\MinuteOfday.kt

```kotlin
package com.mhq.salati.shared.domain

import java.time.LocalTime

/** Number of minutes in a full day; the upper bound (exclusive) of any minute-of-day value. */
const val MINUTES_PER_DAY = 24 * 60

/**
 * Converts a time to its minute-of-day value, from 0 (00:00) to 1439 (23:59).
 * Seconds and finer fields are ignored, so a time is identified by its whole minute.
 * This is the unit prayer times are compared and offset in.
 */
fun LocalTime.toMinuteOfDay(): Int = hour * 60 + minute
```

## app\src\main\java\com\mhq\salati\shared\domain\PrayerName.kt

```kotlin
package com.mhq.salati.shared.domain

/**
 * Every named timing the app tracks, major prayers first, then minor timings.
 *
 * @property isMinorTiming true for auxiliary timings (Imsak, Shorouq, the third-of-night marks)
 * displayed for reference but are not one of the five daily prayers a user can mark as prayed
 */
enum class PrayerName(val isMinorTiming: Boolean) {
    FAJR(false),
    DHUHR(false),
    ASR(false),
    MAGHRIB(false),
    ISHA(false),
    IMSAK(true),
    SHOROUQ(true),
    FIRST_THIRD(true),
    MIDNIGHT(true),
    LAST_THIRD(true);

    companion object {
        /** The five daily prayers, in observance order; the ones a user can track. */
        val majorEntries: List<PrayerName> = entries.filter { !it.isMinorTiming }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\domain\PrayerNameResolver.kt

```kotlin
package com.mhq.salati.shared.domain

/**
 * Turns the text a prayer travels as (inside alarm Intents and saved rows) back into a [PrayerName].
 * Implemented in Data, which owns that text.
 *
 * The string keys are owned by the Data layer (alarm Intent extras and persisted rows),
 * so the mapping lives there too. Domain code only sees the resolved [PrayerName],
 * or null when the key is unknown or stale.
 */
interface PrayerNameResolver {
    /**
     * Resolves [key] to the [PrayerName] it stands for.
     *
     * @return the matching prayer, or null when the key is not a known prayer key
     */
    fun resolve(key: String): PrayerName?
}
```

## app\src\main\java\com\mhq\salati\shared\domain\usecases\ResolvePrayerNameUseCase.kt

```kotlin
package com.mhq.salati.shared.domain.usecases

import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.PrayerNameResolver
import javax.inject.Inject

/**
 * Gives the [PrayerName] a stored or Intent key stands for, or null for an unknown key.
 *
 * Thin pass-through over [PrayerNameResolver]; exists so presentation code can resolve keys
 * through the standard use-case seam without reaching into Data.
 */
class ResolvePrayerNameUseCase @Inject constructor(
    private val prayerNameResolver: PrayerNameResolver
) {
    /**
     * @param key the string key carried by an alarm Intent or a saved row
     * @return the matching prayer, or null when the key is unknown or stale
     */
    operator fun invoke(key: String): PrayerName? = prayerNameResolver.resolve(key)
}
```

## app\src\main\java\com\mhq\salati\shared\presentation\mapper\HijriMonthUiMapper.kt

```kotlin
package com.mhq.salati.shared.presentation.mapper

import com.mhq.salati.shared.domain.HijriMonth
import com.mhq.salati.shared.presentation.model.HijriMonthUi

fun HijriMonth.toUi(): HijriMonthUi = when (this) {
    HijriMonth.MUHARRAM -> HijriMonthUi.MUHARRAM
    HijriMonth.SAFAR -> HijriMonthUi.SAFAR
    HijriMonth.RABI_AL_AWWAL -> HijriMonthUi.RABI_AL_AWWAL
    HijriMonth.RABI_AL_THANI -> HijriMonthUi.RABI_AL_THANI
    HijriMonth.JUMADA_AL_AWWAL -> HijriMonthUi.JUMADA_AL_AWWAL
    HijriMonth.JUMADA_AL_THANI -> HijriMonthUi.JUMADA_AL_THANI
    HijriMonth.RAJAB -> HijriMonthUi.RAJAB
    HijriMonth.SHABAN -> HijriMonthUi.SHABAN
    HijriMonth.RAMADAN -> HijriMonthUi.RAMADAN
    HijriMonth.SHAWWAL -> HijriMonthUi.SHAWWAL
    HijriMonth.DHU_AL_QIDAH -> HijriMonthUi.DHU_AL_QIDAH
    HijriMonth.DHU_AL_HIJJAH -> HijriMonthUi.DHU_AL_HIJJAH
}
```

## app\src\main\java\com\mhq\salati\shared\presentation\mapper\PrayerNameUiMapper.kt

```kotlin
package com.mhq.salati.shared.presentation.mapper

import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.model.PrayerNameUi

/** Both directions use an exhaustive `when` (no else):
 * adding a prayer to one enum without the other becomes a compile error */

fun PrayerName.toUi(): PrayerNameUi = when (this) {
    PrayerName.FAJR -> PrayerNameUi.FAJR
    PrayerName.DHUHR -> PrayerNameUi.DHUHR
    PrayerName.ASR -> PrayerNameUi.ASR
    PrayerName.MAGHRIB -> PrayerNameUi.MAGHRIB
    PrayerName.ISHA -> PrayerNameUi.ISHA
    PrayerName.IMSAK -> PrayerNameUi.IMSAK
    PrayerName.SHOROUQ -> PrayerNameUi.SHOROUQ
    PrayerName.FIRST_THIRD -> PrayerNameUi.FIRST_THIRD
    PrayerName.MIDNIGHT -> PrayerNameUi.MIDNIGHT
    PrayerName.LAST_THIRD -> PrayerNameUi.LAST_THIRD
}

fun PrayerNameUi.toDomain(): PrayerName = when (this) {
    PrayerNameUi.FAJR -> PrayerName.FAJR
    PrayerNameUi.DHUHR -> PrayerName.DHUHR
    PrayerNameUi.ASR -> PrayerName.ASR
    PrayerNameUi.MAGHRIB -> PrayerName.MAGHRIB
    PrayerNameUi.ISHA -> PrayerName.ISHA
    PrayerNameUi.IMSAK -> PrayerName.IMSAK
    PrayerNameUi.SHOROUQ -> PrayerName.SHOROUQ
    PrayerNameUi.FIRST_THIRD -> PrayerName.FIRST_THIRD
    PrayerNameUi.MIDNIGHT -> PrayerName.MIDNIGHT
    PrayerNameUi.LAST_THIRD -> PrayerName.LAST_THIRD
}

fun Set<PrayerName>.toUiSet(): Set<PrayerNameUi> = mapTo(mutableSetOf()) { it.toUi() }
```

## app\src\main\java\com\mhq\salati\shared\presentation\model\HijriMonthUi.kt

```kotlin
package com.mhq.salati.shared.presentation.model

/** A Hijri month as the screens know it. */
enum class HijriMonthUi {
    MUHARRAM,
    SAFAR,
    RABI_AL_AWWAL,
    RABI_AL_THANI,
    JUMADA_AL_AWWAL,
    JUMADA_AL_THANI,
    RAJAB,
    SHABAN,
    RAMADAN,
    SHAWWAL,
    DHU_AL_QIDAH,
    DHU_AL_HIJJAH
}
```

## app\src\main\java\com\mhq\salati\shared\presentation\model\MessageUi.kt

```kotlin
package com.mhq.salati.shared.presentation.model

/**
 * A message the screens can show (errors, confirmations).
 * Presentation picks one; the UI layer turns it into text.
 */
enum class MessageUi {
    ALARM_SAVED,
    COULDNT_DELETE_ALARM,
    COULDNT_DETERMINE_LOCATION_NAME,
    COULDNT_LOAD_ALARMS,
    COULDNT_SAVE_ALARM,
    COULDNT_SAVE_LOCATION,
    COULDNT_UPDATE_ADHAN_SOUND,
    COULDNT_UPDATE_ALARM,
    COULDNT_UPDATE_CALCULATION_METHOD,
    COULDNT_UPDATE_CUSTOM_ALARM_SOUND,
    COULDNT_UPDATE_HIJRI_OFFSET,
    COULDNT_UPDATE_LANGUAGE,
    COULDNT_UPDATE_MADHAB,
    COULDNT_UPDATE_NOTIFICATION_SETTINGS,
    COULDNT_UPDATE_THEME,
    COULDNT_UPDATE_TIME_FORMAT,
    DUPLICATE_ALARM_EXISTS,
    FAILED_TO_GET_LOCATION,
    FAILED_TO_GET_LOCATION_NAME,
    FAILED_TO_LOAD_QIBLA_DIRECTION,
    FAILED_TO_UPDATE_PRAYER_STATUS,
    NO_INTERNET_CONNECTION,
    REQUEST_TIMED_OUT_CHECK_CONNECTION,
    SEARCH_FAILED_CHECK_CONNECTION,
    SOMETHING_WENT_WRONG
}
```

## app\src\main\java\com\mhq\salati\shared\presentation\model\PrayerNameUi.kt

```kotlin
package com.mhq.salati.shared.presentation.model

/**
 * A prayer or timing as the screens know it.
 * Mirrors the domain PrayerName; the mapper in shared/presentation/mapper converts between the two.
 */
enum class PrayerNameUi(val isMinorTiming: Boolean) {
    FAJR(false),
    DHUHR(false),
    ASR(false),
    MAGHRIB(false),
    ISHA(false),
    IMSAK(true),
    SHOROUQ(true),
    FIRST_THIRD(true),
    MIDNIGHT(true),
    LAST_THIRD(true);

    companion object {
        val majorEntries: List<PrayerNameUi> = entries.filter { !it.isMinorTiming }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\ActionRow.kt

```kotlin
package com.mhq.salati.shared.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.theme.SalatiTheme

/**
 * Longest a value label may get before it shrinks and then clips. Without a cap, a long Arabic
 * translation (e.g. calculation method names) is measured with the full row width and wraps,
 * growing the row vertically and squeezing the weighted title into a sliver.
 */
private val ValueLabelMaxWidth = 200.dp

/**
 * Settings-style row with a trailing value label and a chevron.
 *
 * The value label stays on ONE line: it shrinks (via [ShrinkToFitText]), then clips, but never
 * wraps, so a longer translation or a narrow pane (landscape, split-screen) cannot grow this row
 * vertically. Titles may still wrap inside their weighted space, which is bounded.
 */
@Composable
fun ActionRow(
    icon: ImageVector,
    title: String,
    valueLabel: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        IconBadge(icon, isEnabled)
        Spacer(Modifier.width(14.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color =
                if (isEnabled)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.weight(1f)
        )

        ShrinkToFitText(
            text = valueLabel,
            fontSize = 13.sp,
            color =
                if (isEnabled)
                    MaterialTheme.colorScheme.onSurfaceVariant
                else
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.widthIn(max = ValueLabelMaxWidth)
        )

        Spacer(Modifier.width(4.dp))

        Icon(
            imageVector = if (isRtl) Icons.Filled.ChevronLeft else Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ActionRowPreview() {
    SalatiTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            ActionRow(
                icon = Icons.Default.Language,
                title = "App Language",
                valueLabel = "English",
                onClick = {}
            )
        }
    }
}

@Preview(name = "Long Arabic Value Label", locale = "ar", showBackground = true)
@Composable
private fun ActionRowLongArabicValuePreview() {
    SalatiTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            ActionRow(
                icon = Icons.Default.Language,
                title = "طريقة الحساب",
                valueLabel = "الهيئة المصرية العامة للمساحة",
                onClick = {}
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\AppLogo.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mhq.salati.R

/**
 * The launcher icon, drawn the way the launcher shows it:
 * the 108dp adaptive foreground on the icon color, cropped to the visible 72dp window.
 */
@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 96.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(colorResource(R.color.ic_launcher_background))
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.requiredSize(size * 1.5f)
        )
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\GroupCard.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun GroupCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(content = content)
    }
}

@Preview
@Composable
private fun GroupCardPreview() {
    SalatiTheme() {
        GroupCard(
            content = {}
        )
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\GroupDivider.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun GroupDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant,
        modifier = modifier.padding(start = 60.dp)
    )
}

@Preview
@Composable
private fun GroupDividerPreview() {
    SalatiTheme() {
        GroupDivider()
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\IconBadge.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun IconBadge(
    icon: ImageVector,
    isEnabled: Boolean = true
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                MaterialTheme.colorScheme.primary.copy(
                    alpha = if (isEnabled) 0.1f else 0.05f
                )
            ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint =
                if (isEnabled)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.primary.copy(
                alpha = 0.35f
            ),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
private fun IconBadgePreview() {
    SalatiTheme() {
        IconBadge(
            icon = Icons.Filled.CheckCircle,
            isEnabled = true
        )
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\LocalSnackbarHostState.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided — must be set in SalatiApp")
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\SelectionSheet.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R

/** One row of a [SelectionSheet]. */
data class SelectionOption(
    @StringRes val labelRes: Int,
    val isSelected: Boolean,
    val onSelect: () -> Unit
)

/** Builds one option per entry; the entry equal to [selected] is marked. */
fun <T> selectionOptions(
    entries: List<T>,
    selected: T,
    label: (T) -> Int,
    onSelect: (T) -> Unit
): List<SelectionOption> = entries.map { entry ->
    SelectionOption(
        labelRes = label(entry),
        isSelected = entry == selected,
        onSelect = { onSelect(entry) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionSheet(
    @StringRes titleRes: Int,
    options: List<SelectionOption>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier.padding(bottom = 24.dp)) {
            Text(
                text = stringResource(titleRes),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )

            LazyColumn {
                items(options) { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { option.onSelect() }
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        Text(
                            text = stringResource(option.labelRes),
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )
                        if (option.isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.is_selected)
                            )
                        }
                    }
                }
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\ShrinkToFitText.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Single-line text that shrinks its font (down to [minFontSize]) until it fits the width it is given.
 *
 * Use it wherever a label must stay on ONE line, so a longer translation (Arabic day / prayer names)
 * or a narrow pane (landscape, split-screen) shrinks the text instead of wrapping and growing the
 * parent vertically. The text stays hidden until the final size is found, so there is no visible jump.
 *
 * The shrink restarts whenever the text, font size, screen width or font scale changes.
 */
@Composable
fun ShrinkToFitText(
    text: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    minFontSize: TextUnit = 9.sp,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null
) {
    val configuration = LocalConfiguration.current
    var sizeSp by remember(text, fontSize, configuration.screenWidthDp, configuration.fontScale) {
        mutableFloatStateOf(fontSize.value)
    }
    var fits by remember(text, fontSize, configuration.screenWidthDp, configuration.fontScale) {
        mutableStateOf(false)
    }

    Text(
        text = text,
        color = color,
        fontSize = sizeSp.sp,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        onTextLayout = { result ->
            if (result.didOverflowWidth && sizeSp > minFontSize.value) {
                sizeSp = (sizeSp * 0.92f).coerceAtLeast(minFontSize.value)
            } else {
                fits = true
            }
        },
        modifier = modifier.drawWithContent { if (fits) drawContent() }
    )
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\StepBadgeIcon.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/** The icon inside a [StepHeader] badge. */
@Composable
fun StepBadgeIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.tertiary,
        modifier = modifier.size(48.dp)
    )
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\StepHeader.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/** Title block of a full-screen step; [badge] fills the inner 96dp circle (an icon or the logo). */
@Composable
fun StepHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    badge: @Composable BoxScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(140.dp)
                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f), CircleShape)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(96.dp)
                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.18f), CircleShape),
                content = badge
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f)
        )
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\components\TabHeader.kt

```kotlin
package com.mhq.salati.shared.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.ScreenHorizontalPadding
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.tabHeaderGradient

/**
 * Header shared by the tab screens.
 *
 * The height is a MINIMUM ([heightIn]), never a fixed value,
 * so a larger font scale or Arabic line heights grow the header instead of clipping it.
 * The gradient runs edge to edge (behind the status bar and any system bars);
 * only the content is pushed clear of them.
 */
@Composable
fun TabHeader(
    icon: ImageVector,
    title: String,
    subtitle: String,
    trailingContent: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isWide = isWideLayout()

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tabHeaderGradient)
            .statusBarsPadding()
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
            )
            .heightIn(min = if (isWide) 64.dp else 112.dp)
            .padding(vertical = if (isWide) 8.dp else 24.dp, horizontal = ScreenHorizontalPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(if (isWide) 40.dp else 56.dp)
                        .clip(RoundedCornerShape(if (isWide) 10.dp else 16.dp))
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f)
                        ),
                ) {
                    Icon(
                        imageVector = icon,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = null,
                        modifier = Modifier.size(if (isWide) 20.dp else 28.dp)
                    )
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = if (isWide) 20.sp else 26.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            lineHeight = if (isWide) 24.sp else 32.sp,
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    )
                    if (!isWide) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = subtitle,
                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.85f),
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                lineHeight = 18.sp,
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.None
                                )
                            )
                        )
                    }
                }
            }
            trailingContent()
        }
    }
}

@Preview
@Composable
private fun TabHeaderPreview() {
    SalatiTheme {
        TabHeader(
            icon = Icons.Default.Explore,
            title = "Title",
            subtitle = "Subtitle"
        )
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\errors\CatchAllError.kt

```kotlin
package com.mhq.salati.shared.ui.errors

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun CatchAllError(
    errorMessage: String,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.error, errorMessage),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetryClicked) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CatchAllErrorPreview() {
    SalatiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CatchAllError(
                errorMessage = "Network request failed with status code 500",
                onRetryClicked = {}
            )
        }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\errors\NoConnectionError.kt

```kotlin
package com.mhq.salati.shared.ui.errors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.connectivity.ui.components.rememberNetworkConnected

@Composable
fun NoConnectionError(
    errorMessage: String,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDeviceConnected by rememberNetworkConnected()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.error, errorMessage),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        if (isDeviceConnected) {
            Spacer(Modifier.height(8.dp))
            Button(onClick = onRetryClicked) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\HijriMonthLabels.kt

```kotlin
package com.mhq.salati.shared.ui

import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.model.HijriMonthUi

val HijriMonthUi.labelRes: Int
    @StringRes get() = when (this) {
        HijriMonthUi.MUHARRAM -> R.string.hijri_muharram
        HijriMonthUi.SAFAR -> R.string.hijri_safar
        HijriMonthUi.RABI_AL_AWWAL -> R.string.hijri_rabi_al_awwal
        HijriMonthUi.RABI_AL_THANI -> R.string.hijri_rabi_al_akhar
        HijriMonthUi.JUMADA_AL_AWWAL -> R.string.hijri_jumada_al_oula
        HijriMonthUi.JUMADA_AL_THANI -> R.string.hijri_jumada_al_akhera
        HijriMonthUi.RAJAB -> R.string.hijri_rajab
        HijriMonthUi.SHABAN -> R.string.hijri_shaban
        HijriMonthUi.RAMADAN -> R.string.hijri_ramadan
        HijriMonthUi.SHAWWAL -> R.string.hijri_shawwal
        HijriMonthUi.DHU_AL_QIDAH -> R.string.hijri_dhu_al_qidah
        HijriMonthUi.DHU_AL_HIJJAH -> R.string.hijri_dhu_al_hijjah
    }
```

## app\src\main\java\com\mhq\salati\shared\ui\MainActivity.kt

```kotlin
package com.mhq.salati.shared.ui

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.core.graphics.toColorInt
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.splash.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private val configurationVersion = mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.dark(
                "#000000".toColorInt()
            )
        )

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.state.value.isLoading
        }

        setContent {
            @Suppress("UNUSED_VARIABLE")
            val version = configurationVersion.intValue
            val splashState by splashViewModel.state.collectAsStateWithLifecycle()

            val darkTheme = when (splashState.themeMode) {
                ThemeModeUi.SYSTEM -> isSystemInDarkTheme()
                ThemeModeUi.LIGHT -> false
                ThemeModeUi.DARK -> true
            }

            SalatiTheme(darkTheme = darkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SalatiApp()
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfiguration: Configuration) {
        super.onConfigurationChanged(newConfiguration)
        configurationVersion.intValue++
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        overrideConfiguration?.let {
            val uiMode = it.uiMode
            it.setTo(baseContext.resources.configuration)
            it.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\MessageLabels.kt

```kotlin
package com.mhq.salati.shared.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.model.MessageUi

// The user-facing text of a message.
// Lives here (not in Presentation) because it is an Android resource.
val MessageUi.textRes: Int
    @StringRes get() = when (this) {
        MessageUi.ALARM_SAVED -> R.string.alarm_saved
        MessageUi.COULDNT_DELETE_ALARM -> R.string.couldn_t_delete_alarm
        MessageUi.COULDNT_DETERMINE_LOCATION_NAME -> R.string.couldn_t_determine_location_name
        MessageUi.COULDNT_LOAD_ALARMS -> R.string.couldn_t_load_alarms
        MessageUi.COULDNT_SAVE_ALARM -> R.string.couldn_t_save_alarm
        MessageUi.COULDNT_SAVE_LOCATION -> R.string.couldn_t_save_location
        MessageUi.COULDNT_UPDATE_ADHAN_SOUND -> R.string.couldn_t_update_adhan_sound
        MessageUi.COULDNT_UPDATE_ALARM -> R.string.couldn_t_update_alarm
        MessageUi.COULDNT_UPDATE_CALCULATION_METHOD -> R.string.couldn_t_update_calculation_method
        MessageUi.COULDNT_UPDATE_CUSTOM_ALARM_SOUND -> R.string.couldn_t_update_custom_alarm_sound
        MessageUi.COULDNT_UPDATE_HIJRI_OFFSET -> R.string.couldn_t_update_hijri_offset
        MessageUi.COULDNT_UPDATE_LANGUAGE -> R.string.couldn_t_update_language
        MessageUi.COULDNT_UPDATE_MADHAB -> R.string.couldn_t_update_madhab
        MessageUi.COULDNT_UPDATE_NOTIFICATION_SETTINGS -> R.string.couldn_t_update_notification_settings
        MessageUi.COULDNT_UPDATE_THEME -> R.string.couldn_t_update_theme
        MessageUi.COULDNT_UPDATE_TIME_FORMAT -> R.string.couldn_t_update_time_format
        MessageUi.DUPLICATE_ALARM_EXISTS -> R.string.duplicate_alarm_exists
        MessageUi.FAILED_TO_GET_LOCATION -> R.string.failed_to_get_location
        MessageUi.FAILED_TO_GET_LOCATION_NAME -> R.string.failed_to_get_location_name
        MessageUi.FAILED_TO_LOAD_QIBLA_DIRECTION -> R.string.failed_to_load_qibla_direction
        MessageUi.FAILED_TO_UPDATE_PRAYER_STATUS -> R.string.failed_to_update_prayer_status
        MessageUi.NO_INTERNET_CONNECTION -> R.string.no_internet_connection
        MessageUi.REQUEST_TIMED_OUT_CHECK_CONNECTION -> R.string.request_timed_out_check_connection
        MessageUi.SEARCH_FAILED_CHECK_CONNECTION -> R.string.search_failed_check_connection
        MessageUi.SOMETHING_WENT_WRONG -> R.string.something_went_wrong
    }

@Composable
fun MessageUi.asString(): String = stringResource(textRes)

fun MessageUi.asString(context: Context): String = context.getString(textRes)
```

## app\src\main\java\com\mhq\salati\shared\ui\navigation\BottomNavBar.kt

```kotlin
package com.mhq.salati.shared.ui.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme

private val NotchRadius = 32.dp
private val BubbleSize = 60.dp
private val CornerRadius = 28.dp
private val MinHorizontalEdgePadding = 16.dp
private const val NotchHalfWidthFactor = 1.35f

@Composable
fun BottomNavBar(
    items: List<BottomNavBarItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLandscape = isWideLayout()

    val bubbleIconColor = MaterialTheme.colorScheme.primary
    val barBackgroundColor = MaterialTheme.colorScheme.primaryContainer
    val selectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    val unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)

    val leftGradientEndColor = MaterialTheme.colorScheme.primary
    val rightSurfaceColor = MaterialTheme.colorScheme.background

    val itemCount = items.size
    val density = LocalDensity.current
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val selectedIndex = items.indexOfFirst { it.route == selectedRoute }.coerceAtLeast(0)
    val visualIndex = if (isRtl) itemCount - 1 - selectedIndex else selectedIndex

    val minEdgePaddingPx = with(density) { MinHorizontalEdgePadding.toPx() }
    val notchRadiusPx = with(density) { NotchRadius.toPx() }
    val cornerRadiusPx = with(density) { CornerRadius.toPx() }
    val notchHalfWidthPx = notchRadiusPx * NotchHalfWidthFactor
    val bubbleSizePx = with(density) { BubbleSize.roundToPx() }

    val notchDepthPx = notchRadiusPx * 1.15f
    val bubbleOffsetYPx = (notchDepthPx / 2f - (bubbleSizePx / 2f)).toInt()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        val barWidthPx = constraints.maxWidth

        val minEdgeCenterPx = cornerRadiusPx + notchHalfWidthPx
        val edgePaddingPx = if (itemCount > 1) {
            val solved = (minEdgeCenterPx - barWidthPx / (2f * itemCount)) / (1f - 1f / itemCount)
            solved.coerceIn(minEdgePaddingPx, (barWidthPx / 4f).coerceAtLeast(minEdgePaddingPx))
        } else {
            minEdgePaddingPx
        }
        val edgePaddingDp = with(density) { edgePaddingPx.toDp() }

        val usableWidthPx = (barWidthPx - (edgePaddingPx * 2f)).coerceAtLeast(0f)
        val itemWidthPx = if (itemCount > 0) usableWidthPx / itemCount else 0f
        val targetCenterPx = edgePaddingPx + (itemWidthPx * visualIndex) + (itemWidthPx / 2f)

        val animatedCenterPx by animateFloatAsState(
            targetValue = targetCenterPx,
            animationSpec = spring(dampingRatio = 0.8f, stiffness = Spring.StiffnessMedium),
            label = "notchCenter"
        )

        val bubbleScale = remember { Animatable(1f) }
        var previousIndex by remember { mutableIntStateOf(selectedIndex) }
        LaunchedEffect(selectedIndex) {
            if (previousIndex != selectedIndex) {
                previousIndex = selectedIndex
                bubbleScale.snapTo(0.6f)
                bubbleScale.animateTo(
                    1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            }
        }

        val notchPath = remember { Path() }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomNavBarDefaults.Height)
        ) {
            val w = size.width
            val h = size.height
            val cx = animatedCenterPx.coerceIn(
                minEdgeCenterPx,
                maxOf(minEdgeCenterPx, w - minEdgeCenterPx)
            )
            val notchStart = cx - notchHalfWidthPx
            val notchEnd = cx + notchHalfWidthPx

            // ONLY apply split backdrop treatment if on Home screen in landscape
            if (isLandscape && selectedRoute == NavigationScreen.Home.route) {
                drawRect(
                    color = if (isRtl) rightSurfaceColor else leftGradientEndColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(w / 2f, h)
                )
                drawRect(
                    color = if (isRtl) leftGradientEndColor else rightSurfaceColor,
                    topLeft = Offset(w / 2f, 0f),
                    size = Size(w / 2f, h)
                )
            } else if (isLandscape) {
                // For Qibla and all other views, fill the standard full width surface color behind the notch curves
                drawRect(color = rightSurfaceColor, topLeft = Offset(0f, 0f), size = Size(w, h))
            }

            notchPath.rewind()
            notchPath.apply {
                moveTo(0f, cornerRadiusPx)
                quadraticTo(0f, 0f, cornerRadiusPx, 0f)
                lineTo(notchStart, 0f)
                cubicTo(
                    cx - notchHalfWidthPx * 0.56f, 0f,
                    cx - notchHalfWidthPx * 0.53f, notchDepthPx,
                    cx, notchDepthPx
                )
                cubicTo(
                    cx + notchHalfWidthPx * 0.53f, notchDepthPx,
                    cx + notchHalfWidthPx * 0.56f, 0f,
                    notchEnd, 0f
                )
                lineTo(w - cornerRadiusPx, 0f)
                quadraticTo(w, 0f, w, cornerRadiusPx)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(path = notchPath, color = barBackgroundColor)
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset {
                    val centerFromStartPx =
                        if (isRtl) barWidthPx - animatedCenterPx.toInt() else animatedCenterPx.toInt()
                    IntOffset(x = centerFromStartPx - (bubbleSizePx / 2), y = bubbleOffsetYPx)
                }
                .size(BubbleSize)
                .graphicsLayer {
                    scaleX = bubbleScale.value
                    scaleY = bubbleScale.value
                }
                .background(selectedContentColor, CircleShape)
                .clearAndSetSemantics { }
        ) {
            items.getOrNull(selectedIndex)?.let { selectedItem ->
                Icon(
                    imageVector = selectedItem.icon,
                    contentDescription = null,
                    tint = bubbleIconColor,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomNavBarDefaults.Height)
                .padding(start = edgePaddingDp, end = edgePaddingDp, bottom = 12.dp)
        ) {
            items.forEachIndexed { index, item ->
                BottomNavItem(
                    item = item,
                    isSelected = index == selectedIndex,
                    selectedColor = selectedContentColor,
                    unselectedColor = unselectedContentColor,
                    onClick = { onItemSelected(item.route) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
@Composable
private fun RowScope.BottomNavItem(
    item: BottomNavBarItem,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = tween(80),
        label = "navItemPress"
    )
    val iconAlpha by animateFloatAsState(
        targetValue = if (isSelected) 0f else 0.7f,
        label = "navIconAlpha"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 0.5f else 1f,
        label = "navIconScale"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxHeight()
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
            }
            .selectable(
                selected = isSelected,
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
    ) {
        Box(modifier = Modifier.size(28.dp), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = unselectedColor,
                modifier = Modifier
                    .size(26.dp)
                    .graphicsLayer {
                        alpha = iconAlpha
                        scaleX = iconScale
                        scaleY = iconScale
                    }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.label,
            color = if (isSelected) selectedColor else unselectedColor.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            softWrap = false
        )
    }
}
@Preview
@Composable
private fun BottomNavBarPreview() {
    val mockItems = listOf(
        BottomNavBarItem("home", "Home", Icons.Filled.CheckCircle),
        BottomNavBarItem("settings", "Settings", Icons.Filled.CheckCircle)
    )
    SalatiTheme {
        BottomNavBar(items = mockItems, selectedRoute = "home", onItemSelected = {})
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\navigation\BottomNavBarDefaults.kt

```kotlin
package com.mhq.salati.shared.ui.navigation

import androidx.compose.ui.unit.dp

object BottomNavBarDefaults {
    val Height = 84.dp
}
```

## app\src\main\java\com\mhq\salati\shared\ui\navigation\BottomNavBarItem.kt

```kotlin
package com.mhq.salati.shared.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavBarItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
```

## app\src\main\java\com\mhq\salati\shared\ui\navigation\NavigationScreen.kt

```kotlin
package com.mhq.salati.shared.ui.navigation

import com.mhq.salati.splash.presentation.model.StartDestinationUi

sealed class NavigationScreen(val route: String) {
    object Onboarding : NavigationScreen("onboarding")
    data object Home : NavigationScreen("home")
    data object Qibla : NavigationScreen("qibla")
    data object PrayerTracker : NavigationScreen("prayer_tracker")
    data object Alarms : NavigationScreen("alarms")
    data object Settings : NavigationScreen("settings")
    data object LocationPicker : NavigationScreen("location_picker")
}

fun StartDestinationUi.toNavigationScreen(): NavigationScreen = when (this) {
    StartDestinationUi.ONBOARDING -> NavigationScreen.Onboarding
    StartDestinationUi.HOME -> NavigationScreen.Home
}
```

## app\src\main\java\com\mhq\salati\shared\ui\PrayerNameLabels.kt

```kotlin
package com.mhq.salati.shared.ui

import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.model.PrayerNameUi

val PrayerNameUi.labelRes: Int
    @StringRes get() = when (this) {
        PrayerNameUi.FAJR -> R.string.fajr
        PrayerNameUi.DHUHR -> R.string.dhuhr
        PrayerNameUi.ASR -> R.string.asr
        PrayerNameUi.MAGHRIB -> R.string.maghrib
        PrayerNameUi.ISHA -> R.string.isha
        PrayerNameUi.IMSAK -> R.string.imsak
        PrayerNameUi.SHOROUQ -> R.string.shorouq
        PrayerNameUi.FIRST_THIRD -> R.string.first_third
        PrayerNameUi.MIDNIGHT -> R.string.midnight
        PrayerNameUi.LAST_THIRD -> R.string.last_third
    }
```

## app\src\main\java\com\mhq\salati\shared\ui\SalatiApp.kt

```kotlin
package com.mhq.salati.shared.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mhq.salati.R
import com.mhq.salati.alarms.ui.screens.CustomAlarmsContainer
import com.mhq.salati.home.ui.screens.HomeContainer
import com.mhq.salati.locationpicker.ui.screens.LocationPickerContainer
import com.mhq.salati.onboarding.ui.screens.OnboardingContainer
import com.mhq.salati.prayertracker.ui.screens.PrayerTrackerContainer
import com.mhq.salati.qibla.ui.screens.QiblaContainer
import com.mhq.salati.settings.ui.screens.SettingsContainer
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.shared.ui.navigation.BottomNavBar
import com.mhq.salati.shared.ui.navigation.BottomNavBarItem
import com.mhq.salati.shared.ui.navigation.NavigationScreen
import com.mhq.salati.shared.ui.navigation.toNavigationScreen
import com.mhq.salati.splash.presentation.viewmodel.SplashViewModel

/** Routes that appear in the bottom bar. */
private val TabRoutes = setOf(
    NavigationScreen.Home.route,
    NavigationScreen.Qibla.route,
    NavigationScreen.PrayerTracker.route,
    NavigationScreen.Alarms.route,
    NavigationScreen.Settings.route
)

/**
 * Cross-dissolve between tabs. A tab opened for the first time needs a moment to produce its first
 * state; the outgoing tab fading out covers that gap, so there is no empty frame.
 */
private const val TAB_FADE_MILLIS = 120

/** Fade used for non-tab navigation (onboarding to home, location picker). */
private const val NON_TAB_FADE_MILLIS = 200

private fun AnimatedContentTransitionScope<NavBackStackEntry>.isTabSwitch(): Boolean =
    initialState.destination.route in TabRoutes && targetState.destination.route in TabRoutes

private val ScreenEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    fadeIn(tween(if (isTabSwitch()) TAB_FADE_MILLIS else NON_TAB_FADE_MILLIS))
}

private val ScreenExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    ExitTransition.None // the old tab stays fully opaque while the new one fades in on top
}

/**
 * App root composable: gates on splash loading only, then hosts the bottom-nav
 * screens behind a single [NavHost] + floating bottom bar.
 *
 * First-run language selection moved to onboarding's Welcome page, so the
 * old language-dialog gate is gone.
 */
@Composable
fun SalatiApp() {
    val splashViewModel: SplashViewModel = hiltViewModel()
    val splashState by splashViewModel.state.collectAsState()

    // 1. Show loading (AndroidX Splash Screen API handles the native splash)
    if (splashState.isLoading) {
        return
    }

    // 2. Render App Content
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val startRoute: String =
        splashState.startDestination?.toNavigationScreen()?.route
            ?: NavigationScreen.Onboarding.route

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                SalatiNavHost(
                    navController = navController,
                    startRoute = startRoute,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        // Floating bottom nav, drawn over the Scaffold content
        SalatiBottomBar(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/** The navigation graph. Takes only stable inputs so it is never rebuilt on tab switches. */
@Composable
private fun SalatiNavHost(
    navController: NavHostController,
    startRoute: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startRoute,
        modifier = modifier,
        enterTransition = ScreenEnter,
        exitTransition = ScreenExit,
        popEnterTransition = ScreenEnter,
        popExitTransition = ScreenExit
    ) {
        composable(NavigationScreen.Onboarding.route) {
            OnboardingContainer(
                onFinished = {
                    navController.navigate(NavigationScreen.Home.route) {
                        popUpTo(NavigationScreen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(NavigationScreen.Home.route) {
            HomeContainer(
                onNavigateToLocationPicker = {
                    navController.navigate(NavigationScreen.LocationPicker.route)
                }
            )
        }
        composable(NavigationScreen.LocationPicker.route) {
            LocationPickerContainer(
                onLocationChosen = { navController.popBackStack() },
                onBackClicked = { navController.popBackStack() }
            )
        }
        composable(NavigationScreen.PrayerTracker.route) { PrayerTrackerContainer() }
        composable(NavigationScreen.Alarms.route) { CustomAlarmsContainer() }
        composable(NavigationScreen.Settings.route) { SettingsContainer() }
        composable(NavigationScreen.Qibla.route) {
            QiblaContainer(
                onNavigateToLocationPicker = {
                    navController.navigate(NavigationScreen.LocationPicker.route)
                }
            )
        }
    }
}

/**
 * Bottom bar that observes the back stack itself, so navigation changes recompose only this
 * composable. Slides away on non-tab screens (e.g. the location picker) instead of popping out.
 */
@Composable
private fun SalatiBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val homeLabel = stringResource(R.string.home)
    val qiblaLabel = stringResource(R.string.qibla)
    val trackerLabel = stringResource(R.string.tracker)
    val alarmsLabel = stringResource(R.string.alarms)
    val settingsLabel = stringResource(R.string.settings)

    val items = remember(homeLabel, qiblaLabel, trackerLabel, alarmsLabel, settingsLabel) {
        listOf(
            BottomNavBarItem(NavigationScreen.Home.route, homeLabel, Icons.Default.Home),
            BottomNavBarItem(NavigationScreen.Qibla.route, qiblaLabel, Icons.Default.Explore),
            BottomNavBarItem(
                NavigationScreen.PrayerTracker.route,
                trackerLabel,
                Icons.Filled.CheckCircle
            ),
            BottomNavBarItem(NavigationScreen.Alarms.route, alarmsLabel, Icons.Default.Alarm),
            BottomNavBarItem(NavigationScreen.Settings.route, settingsLabel, Icons.Default.Settings)
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedRoute = currentRoute?.takeIf { route -> items.any { it.route == route } }

    var lastTabRoute by remember { mutableStateOf(items.first().route) }
    LaunchedEffect(selectedRoute) {
        if (selectedRoute != null) lastTabRoute = selectedRoute
    }

    AnimatedVisibility(
        visible = selectedRoute != null,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut(),
        modifier = modifier
    ) {
        BottomNavBar(
            items = items,
            selectedRoute = selectedRoute ?: lastTabRoute,
            onItemSelected = { route ->
                navController.navigate(route) {
                    popUpTo(NavigationScreen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\ScreenLayout.kt

```kotlin
package com.mhq.salati.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/** Horizontal margin shared by the tab header and the content of every tab screen. */
val ScreenHorizontalPadding = 24.dp

@Composable
fun isWideLayout(): Boolean {
    val c = LocalConfiguration.current
    return c.screenWidthDp > c.screenHeightDp
}
```

## app\src\main\java\com\mhq\salati\shared\ui\screens\LoadingContent.kt

```kotlin
package com.mhq.salati.shared.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.DarkGreen
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = DarkGreen
        )
        Spacer(
            Modifier.height(16.dp)
        )
        Text(
            text = stringResource(R.string.please_wait),
            color = DarkGreen
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoadingContentPreview() {
    SalatiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LoadingContent()
        }
    }
}
```

## app\src\main\java\com\mhq\salati\shared\ui\theme\Color.kt

```kotlin
//package com.mhq.salati.shared.ui.theme
//
//import androidx.compose.ui.graphics.Color
//
//// --- Light Mode Tokens ("Porcelain Garden") ---
//
//// Backgrounds & Surface Core
//val SheetBackground = Color(0xFFF4F6F5)       // Base screen canvas: cool porcelain, no cream
//val CardBackground = Color.White             // Elevated container backgrounds
//
//// Core Typography & Content
//val InkText = Color(0xFF17231D)              // Primary body text (14.9:1 on SheetBackground)
//val Dolphin = Color(0xFF5C6B63)              // Secondary sage-gray text (5.2:1 AA)
//
//// Branding & Accents
//val DarkGreen = Color(0xFF0A5C40)            // Jewel emerald primary (white on it: 8.0:1)
//val DarkGreenLight = Color(0xFF24775A)       // Primary container overlay fill (white: 5.4:1)
//val AccentGreen = Color(0xFF277955)           // Fresh emerald highlight (white: 5.3:1)
//val OnAccentGreen = Color.White              // Content placed on top of secondary chips
//val AccentGold = Color(0xFFD9A73F)           // Champagne gold countdown / timing accent
//
//// Borders & Dividers
//val Timberwolf = Color(0xFFB5C0BA)           // Component outline boundaries (cool sage)
//val QuickSilver = Color(0xFFD3DCD6)          // Secondary layout dividers
//
//// Alerts & Trackers
//val MaterialRed = Color(0xFFB93942)          // Garnet error / missed prayer (white: 5.6:1)
//val ErrorContainerLight = Color(0xFFF9E7E8)  // Soft rose wash for error banners (4.7:1)
//
//// Legacy Reference Tokens
//val MetallicSilver = Color(0xFFE2E8F0)
//val MutedSlate = Color(0xFF64748B)
//val MutedSage = Color(0xFF8A9A90)
//
//
//// --- Dark Mode Tokens ("Emerald Night") ---
//val CelestialObsidian = Color(0xFF0B0F0D)     // Deep neutral-green canvas
//val CosmicGunmetal = Color(0xFF121816)        // Elevated surface containers
//val TwilightSurface = Color(0xFF1A231F)       // Subtle cards and interactive rows
//
//val RadiantEmerald = Color(0xFF57C894)        // Fresh jade: branding text & active icons (9.3:1)
//val EmeraldGlow = Color(0xFF35A272)           // Secondary deep emerald accent
//val DeepSeaTeal = Color(0xFF16382B)           // Muted tracking fields and filled tags
//val AuroraMuted = Color(0xFF12332A)           // Deep background container overlay
//
//val CelestialGold = Color(0xFFE6C468)         // Champagne gold for countdown rings (11.5:1)
//val CelestialGoldMuted = Color(0xFF93783A)     // Border treatment for active elements
//
//val CosmicWhite = Color(0xFFEDF3EF)           // Green-tinted near-white text (17.2:1)
//val AstralSlate = Color(0xFF9FB3A9)           // Mist-sage subheadings (7.3:1 AA)
//val CosmicMuted = Color(0xFF46524B)           // Dimmed borders and disabled states
//
//val ErrorCrimsonDark = Color(0xFFF2705F)      // Coral-rose for missed prayers / alerts (6.7:1)
//val ErrorCrimsonContainer = Color(0xFF3C1D22) // Muted rose-black shade for error alerts




//package com.mhq.salati.shared.ui.theme
//
//import androidx.compose.ui.graphics.Color
//
//// --- Light Mode Tokens ("Parchment Garden") ---
//
//// Backgrounds & Surface Core
//val SheetBackground = Color(0xFFF6F3EC)       // Base screen canvas: warm parchment
//val CardBackground = Color.White             // Elevated container backgrounds
//
//// Core Typography & Content
//val InkText = Color(0xFF1B2A21)              // Primary body text (13.5:1 on SheetBackground)
//val Dolphin = Color(0xFF626F68)              // Secondary sage-gray text (4.75:1 AA)
//
//// Branding & Accents
//val DarkGreen = Color(0xFF10503C)            // Primary brand: rich emerald (white on it: 9.4:1)
//val DarkGreenLight = Color(0xFF2A6A50)       // Primary container overlay fill (white: 6.4:1)
//val AccentGreen = Color(0xFF337E66)          // Muted sage-teal highlight (white: 4.9:1)
//val OnAccentGreen = Color.White              // Content placed on top of secondary chips
//val AccentGold = Color(0xFFB6913F)           // Antique brass countdown / timing accent (ink on it: 5.1:1)
//
//// Borders & Dividers
//val Timberwolf = Color(0xFF9AA39C)           // Component outline boundaries (sage-tinted)
//val QuickSilver = Color(0xFFC6CDC6)          // Secondary layout dividers (sage-tinted)
//
//// Alerts & Trackers
//val MaterialRed = Color(0xFFA8432F)          // Terracotta error / missed prayer (white: 6.0:1)
//val ErrorContainerLight = Color(0xFFF6E4DB) // Warm clay wash for error banners (4.9:1)
//
//// Legacy Reference Tokens
//val MetallicSilver = Color(0xFFE2E8F0)
//val MutedSlate = Color(0xFF64748B)
//val MutedSage = Color(0xFF8A9A90)
//
//
//// --- Dark Mode Tokens ("Night Garden") ---
//val CelestialObsidian = Color(0xFF0D1411)     // Soft green-black canvas (not pure black)
//val CosmicGunmetal = Color(0xFF131B17)        // Elevated surface containers
//val TwilightSurface = Color(0xFF1C2621)       // Subtle cards and interactive rows
//
//val RadiantEmerald = Color(0xFF82C9A2)        // Muted jade: branding text & active icons (8:1+)
//val EmeraldGlow = Color(0xFF5BA982)           // Secondary deep emerald accent
//val DeepSeaTeal = Color(0xFF1C3A2F)           // Muted tracking fields and filled tags
//val AuroraMuted = Color(0xFF16352B)           // Deep background container overlay
//
//val CelestialGold = Color(0xFFD8B75F)         // Champagne gold for countdown rings (9.7:1)
//val CelestialGoldMuted = Color(0xFF8F7434)    // Border treatment for active elements
//
//val CosmicWhite = Color(0xFFECF2ED)           // Green-tinted near-white text (16.4:1)
//val AstralSlate = Color(0xFF9DB0A5)           // Mist-sage subheadings (6.8:1 AA)
//val CosmicMuted = Color(0xFF435049)           // Dimmed borders and disabled states
//
//val ErrorCrimsonDark = Color(0xFFE5836F)      // Dusk-rose for missed prayers / alerts (6.9:1)
//val ErrorCrimsonContainer = Color(0xFF3B1D1E) // Muted plum-red shade for error alerts




package com.mhq.salati.shared.ui.theme

import androidx.compose.ui.graphics.Color

// --- Light Mode Tokens ---

// Backgrounds & Surface Core
val SheetBackground = Color(0xFFFAF7F0)       // Base screen canvas
val CardBackground = Color.White              // Elevated container backgrounds

// Core Typography & Content
val InkText = Color(0xFF1C2B22)               // Primary high-contrast body text
val Dolphin = Color(0xFF6E6E6E)               // Secondary muted text (WCAG AA Compliant)

// Branding & Accents
val DarkGreen = Color(0xFF0B3D2E)             // Primary brand deep green
val DarkGreenLight = Color(0xFF1D5C46)        // Primary container overlay fill
val AccentGreen = Color(0xFF2FA774)           // Secondary teal highlight
val OnAccentGreen = Color.White               // Content placed on top of secondary chips
val AccentGold = Color(0xFFC9A24B)            // Tertiary countdown ring / timing accent

// Borders & Dividers
val Timberwolf = Color(0xFF8C8C8C)            // Component outline boundaries
val QuickSilver = Color(0xFFB5B5B5)           // Secondary layout dividers

// Alerts & Trackers
val MaterialRed = Color(0xFFF44336)           // Error / Missed prayer warning indicator
val ErrorContainerLight = Color(0xFFFBE9E7)   // Background fill for error banners

// Legacy Reference Tokens
val MetallicSilver = Color(0xFFE2E8F0)
val MutedSlate = Color(0xFF64748B)
val MutedSage = Color(0xFF8A9A90)


// --- Dark Mode Tokens ---
val CelestialObsidian = Color(0xFF090B0C)      // Deepest background black
val CosmicGunmetal = Color(0xFF0E1214)         // Elevated surface containers
val TwilightSurface = Color(0xFF161C19)        // Subtle cards and interactive rows

val RadiantEmerald = Color(0xFF00F5A0)         // Luminous main branding text & active icons
val EmeraldGlow = Color(0xFF00C781)            // Secondary deep emerald accent
val DeepSeaTeal = Color(0xFF134E4A)            // Muted tracking fields and filled tags
val AuroraMuted = Color(0xFF062E2B)            // Deep background container overlay

val CelestialGold = Color(0xFFD9B44A)          // Refined metallic astronomical gold for countdown rings
val CelestialGoldMuted = Color(0xFF9E7E2F)     // Border treatment for active elements

val CosmicWhite = Color(0xFFF1F5F9)            // Near-white text for ultimate readability
val AstralSlate = Color(0xFF94A3B8)            // High-contrast subheadings (WCAG AA compliant)
val CosmicMuted = Color(0xFF475569)            // Dimmed borders and disabled states

val ErrorCrimsonDark = Color(0xFFE11D48)       // Vivid crimson red for missed prayers / alerts
val ErrorCrimsonContainer = Color(0xFF4C0519)  // Deep background shade for error alerts
```

## app\src\main\java\com\mhq\salati\shared\ui\theme\Gradients.kt

```kotlin
package com.mhq.salati.shared.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val ColorScheme.prayerGradient: Brush
    @Composable get() = Brush.verticalGradient(
        listOf(
            primaryContainer,
            primary
        )
    )

/**
 * Background for the tab headers (Alarms, Tracker, Qibla, Settings).
 *
 * In dark mode `primary` is a bright emerald, which makes gold and white text unreadable at the
 * bottom of [prayerGradient]. Here the gradient ends in the deep secondary container instead, so
 * header text keeps its contrast across the whole header. Light mode is unchanged.
 */
val ColorScheme.tabHeaderGradient: Brush
    @Composable get() {
        val isDark = background.luminance() < 0.5f
        return Brush.verticalGradient(
            listOf(
                primaryContainer,
                if (isDark) secondaryContainer else primary
            )
        )
    }

val ColorScheme.countdownSweep: Brush
    @Composable get() = Brush.sweepGradient(
        listOf(
            tertiary.copy(alpha = 0.55f),
            tertiary,
            tertiary.copy(alpha = 0.55f)
        )
    )

val ColorScheme.countdownTrack: Color
    @Composable get() = onPrimary.copy(alpha = 0.18f)
```

## app\src\main\java\com\mhq\salati\shared\ui\theme\Theme.kt

```kotlin
package com.mhq.salati.shared.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = DarkGreen,
    onPrimary = Color.White,
    primaryContainer = DarkGreenLight,
    onPrimaryContainer = Color.White,

    secondary = AccentGreen,
    onSecondary = OnAccentGreen,
    secondaryContainer = AccentGreen.copy(alpha = 0.12f),
    onSecondaryContainer = DarkGreen,

    tertiary = AccentGold,
    onTertiary = InkText,
    tertiaryContainer = AccentGold.copy(alpha = 0.15f),
    onTertiaryContainer = InkText,

    background = SheetBackground,
    onBackground = InkText,

    surface = CardBackground,
    onSurface = InkText,
    surfaceVariant = SheetBackground,
    onSurfaceVariant = Dolphin,

    outline = Timberwolf,
    outlineVariant = QuickSilver,

    error = MaterialRed,
    onError = Color.White,
    errorContainer = ErrorContainerLight,
    onErrorContainer = MaterialRed
)

private val DarkColors = darkColorScheme(
    primary = RadiantEmerald,
    onPrimary = CelestialObsidian,
    primaryContainer = DeepSeaTeal,
    onPrimaryContainer = CosmicWhite,

    secondary = EmeraldGlow,
    onSecondary = CelestialObsidian,
    secondaryContainer = AuroraMuted,
    onSecondaryContainer = AstralSlate,

    tertiary = CelestialGold,
    onTertiary = CelestialObsidian,
    tertiaryContainer = AuroraMuted,
    onTertiaryContainer = CelestialGold,

    background = CelestialObsidian,
    onBackground = CosmicWhite,

    surface = CosmicGunmetal,
    onSurface = CosmicWhite,
    surfaceVariant = TwilightSurface,
    onSurfaceVariant = AstralSlate,

    outline = CosmicMuted,
    outlineVariant = CelestialGoldMuted.copy(alpha = 0.4f),

    error = ErrorCrimsonDark,
    onError = CelestialObsidian,
    errorContainer = ErrorCrimsonContainer,
    onErrorContainer = ErrorCrimsonDark
)

@Composable
fun SalatiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

## app\src\main\java\com\mhq\salati\shared\ui\theme\Type.kt

```kotlin
package com.mhq.salati.shared.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

/** Vertical baseline layout tokens
 * to accommodate vertical expansion of UI elements upon language change */
private val baseStyle = TextStyle(
    fontFamily = FontFamily.Default,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)

val Typography = Typography(
    displaySmall = baseStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = baseStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineMedium = baseStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineSmall = baseStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    titleLarge = baseStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 30.sp
    ),
    titleMedium = baseStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = baseStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = baseStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = baseStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = baseStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = baseStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = baseStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = baseStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
```

## app\src\main\java\com\mhq\salati\shared\ui\TimeFormatting.kt

```kotlin
package com.mhq.salati.shared.ui

import com.mhq.salati.settings.presentation.model.TimeFormatUi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val Time24Formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)
private val Time12Formatter = DateTimeFormatter.ofPattern("h:mm", Locale.US)

/**
 * Formats this time for display in the chosen [timeFormat].
 *
 * Digits stay ASCII in both formats (as prayer times always have); only the AM/PM marker follows
 * [locale], e.g. "5:23 AM" in English and "5:23 ص" in Arabic.
 */
fun LocalTime.formatForDisplay(timeFormat: TimeFormatUi, locale: Locale): String =
    when (timeFormat) {
        TimeFormatUi.FORMAT_24_HOUR -> format(Time24Formatter)
        TimeFormatUi.FORMAT_12_HOUR -> {
            val marker = format(DateTimeFormatter.ofPattern("a", locale))
            "${format(Time12Formatter)} $marker"
        }
    }
```

## app\src\main\java\com\mhq\salati\splash\domain\model\StartDestination.kt

```kotlin
package com.mhq.salati.splash.domain.model

enum class StartDestination {
    ONBOARDING,
    HOME
}
```

## app\src\main\java\com\mhq\salati\splash\domain\usecases\GetStartDestinationUseCase.kt

```kotlin
package com.mhq.salati.splash.domain.usecases

import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import com.mhq.salati.splash.domain.model.StartDestination
import javax.inject.Inject

class GetStartDestinationUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
) {
    suspend operator fun invoke(): StartDestination {
        if (!onboardingRepository.hasCompletedOnboarding()) return StartDestination.ONBOARDING
        return StartDestination.HOME
    }
}
```

## app\src\main\java\com\mhq\salati\splash\presentation\contract\SplashContract.kt

```kotlin
package com.mhq.salati.splash.presentation.contract

import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.splash.presentation.model.StartDestinationUi

class SplashContract {
    data class State(
        val isLoading: Boolean = true,
        val startDestination: StartDestinationUi? = null,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val isDestinationReady: Boolean = false,
        val isSettingsReady: Boolean = false,
    )

    sealed class Intent {
        object CheckStartDestination : Intent()
    }
}
```

## app\src\main\java\com\mhq\salati\splash\presentation\mapper\SplashUiMapper.kt

```kotlin
package com.mhq.salati.splash.presentation.mapper

import com.mhq.salati.splash.domain.model.StartDestination
import com.mhq.salati.splash.presentation.model.StartDestinationUi

fun StartDestination.toUi(): StartDestinationUi = when (this) {
    StartDestination.ONBOARDING -> StartDestinationUi.ONBOARDING
    StartDestination.HOME -> StartDestinationUi.HOME
}
```

## app\src\main\java\com\mhq\salati\splash\presentation\model\StartDestinationUi.kt

```kotlin
package com.mhq.salati.splash.presentation.model

enum class StartDestinationUi {
    ONBOARDING,
    HOME
}
```

## app\src\main\java\com\mhq\salati\splash\presentation\viewmodel\SplashViewModel.kt

```kotlin
package com.mhq.salati.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.splash.domain.usecases.GetStartDestinationUseCase
import com.mhq.salati.splash.presentation.contract.SplashContract
import com.mhq.salati.splash.presentation.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getStartDestinationUseCase: GetStartDestinationUseCase,
    observeSettingsUseCase: ObserveSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashContract.State())
    val state = _state.asStateFlow()

    init {
        onIntent(SplashContract.Intent.CheckStartDestination)
        observeSettingsUseCase()
            .onEach { settings ->
                _state.update {
                    it.copy(
                        themeMode = settings.themeMode.toUi(),
                        isSettingsReady = true,
                        isLoading = !it.isDestinationReady
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: SplashContract.Intent) {
        when (intent) {
            is SplashContract.Intent.CheckStartDestination -> checkStartDestination()
        }
    }

    private fun checkStartDestination() {
        viewModelScope.launch {
            try {
                val destination = getStartDestinationUseCase()
                _state.update {
                    it.copy(
                        startDestination = destination.toUi(),
                        isDestinationReady = true,
                        isLoading = !it.isSettingsReady
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update { it.copy(isDestinationReady = true, isLoading = !it.isSettingsReady) }
            }
        }
    }
}
```

## app\src\main\res\drawable\ic_salati_colored.xml

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">

    <!-- Dark green square background -->
    <path
        android:fillColor="#0D2818"
        android:pathData="M0,0 L108,0 L108,108 L0,108 Z"/>

    <!-- Mosque: rectangular base + onion dome + solid crescent.
         The three pointed-arch doorways are cut out of the base
         (counter-wound subpaths, so they render as holes). -->
    <path
        android:fillColor="#4CAF50"
        android:pathData="M18,69 L90,69 L90,95 L18,95 Z
            M36,69 C33,57.5 38,47.5 45,41.5 C49,38.1 52.4,36.5 54,34
            C55.6,36.5 59,38.1 63,41.5 C70,47.5 75,57.5 72,69 Z
            M45.5,16 A11,11 0 1 0 62.5,16 A9,9 0 1 1 45.5,16 Z
            M46,95 L62,95 L62,86 C62,80.5 59,77 54,74.5 C49,77 46,80.5 46,86 Z
            M26,95 L38,95 L38,88 C38,83.5 36,80.5 32,78.5 C28,80.5 26,83.5 26,88 Z
            M70,95 L82,95 L82,88 C82,83.5 80,80.5 76,78.5 C72,80.5 70,83.5 70,88 Z"/>
</vector>
```

## app\src\main\res\drawable\ic_salati_white.xml

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="82"
    android:viewportHeight="82">
    <group android:translateX="-13" android:translateY="-14">
        <path
            android:fillColor="#FFFFFFFF"
            android:pathData="M18,69 L90,69 L90,95 L18,95 Z
                M36,69 C33,57.5 38,47.5 45,41.5 C49,38.1 52.4,36.5 54,34
                C55.6,36.5 59,38.1 63,41.5 C70,47.5 75,57.5 72,69 Z
                M45.5,16 A11,11 0 1 0 62.5,16 A9,9 0 1 1 45.5,16 Z
                M46,95 L62,95 L62,86 C62,80.5 59,77 54,74.5 C49,77 46,80.5 46,86 Z
                M26,95 L38,95 L38,88 C38,83.5 36,80.5 32,78.5 C28,80.5 26,83.5 26,88 Z
                M70,95 L82,95 L82,88 C82,83.5 80,80.5 76,78.5 C72,80.5 70,83.5 70,88 Z"/>
    </group>
</vector>
```

## app\src\main\res\drawable\ic_stop.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#FFFFFFFF"
        android:pathData="M6,6h12v12H6z" />
</vector>
```

## app\src\main\res\mipmap-anydpi-v26\ic_launcher.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@mipmap/ic_launcher_foreground"/>
    <monochrome android:drawable="@mipmap/ic_launcher_foreground"/>
</adaptive-icon>
```

## app\src\main\res\mipmap-anydpi-v26\ic_launcher_round.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@mipmap/ic_launcher_foreground"/>
    <monochrome android:drawable="@mipmap/ic_launcher_foreground"/>
</adaptive-icon>
```

## app\src\main\res\values\colors.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
</resources>
```

## app\src\main\res\values\ic_launcher_background.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="ic_launcher_background">#21372D</color>
</resources>
```

## app\src\main\res\values\strings.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <!-- App -->
    <string name="app_name">Salati</string>
    <string name="app_motto">Never Miss a Prayer</string>

    <!-- Common actions -->
    <string name="retry">Retry</string>
    <string name="save">Save</string>
    <string name="edit">Edit</string>
    <string name="delete">Delete</string>
    <string name="stop">Stop</string>
    <string name="action_open_settings">Open settings</string>
    <string name="please_wait">Please, wait</string>
    <string name="is_selected">Selected</string>

    <!-- Bottom navigation -->
    <string name="home">Home</string>
    <string name="qibla">Qibla</string>
    <string name="tracker">Tracker</string>
    <string name="alarms">Alarms</string>
    <string name="settings">Settings</string>

    <!-- Onboarding: Welcome page -->
    <string name="language">Language</string>
    <string name="language_english">English</string>
    <string name="language_arabic">العربية</string>
    <string name="theme">Theme</string>
    <string name="theme_system_default">System Default</string>
    <string name="theme_light">Light</string>
    <string name="theme_dark">Dark</string>
    <string name="onboarding_change_later_hint">You can change all of this later in Settings.</string>
    <string name="onboarding_continue">Continue</string>
    <string name="get_started">Get started</string>

    <!-- Onboarding: Prayer times page -->
    <string name="onboarding_times_title">Prayer times</string>
    <string name="onboarding_calculation_subtitle">Choose how prayer times are calculated and shown.</string>
    <string name="prayer_calculation">Prayer Calculation</string>
    <string name="calculation_method">Method</string>
    <string name="calc_method_egyptian_general_authority">Egyptian General Authority</string>
    <string name="calc_method_umm_al_qura">Umm Al-Qura, Makkah</string>
    <string name="calc_method_muslim_world_league">Muslim World League</string>
    <string name="calc_method_isna">Islamic Society of North America</string>
    <string name="calc_method_karachi">University of Islamic Sciences, Karachi</string>
    <string name="asr_madhab">Asr Madhab</string>
    <string name="madhab_shafi">Shafi\'i (Standard)</string>
    <string name="madhab_hanafi">Hanafi</string>
    <string name="time_format">Time format</string>
    <string name="time_format_12_hour">12-hour</string>
    <string name="time_format_24_hour">24-hour</string>

    <!-- Onboarding: Notifications page -->
    <string name="notifications">Notifications</string>
    <string name="onboarding_notifications_subtitle">Get the adhan and prayer reminders on time.</string>
    <string name="onboarding_notifications_toggle_title">Enable notifications</string>
    <string name="onboarding_notifications_toggle_subtitle">Adhan and prayer alerts</string>
    <string name="adhan_sound">Adhan Sound</string>
    <string name="adhan_sound_default">Default Beep</string>
    <string name="adhan_sound_egypt">Egypt Adhan</string>
    <string name="adhan_sound_makkah">Makkah Adhan</string>
    <string name="adhan_sound_madinah">Madinah Adhan</string>
    <string name="adhan_sound_silent">Silent (Notification Only)</string>
    <string name="onboarding_notifications_action">Allow notifications</string>
    <string name="onboarding_exact_alarm_note">Prayer alarms also need permission to set exact alarms.</string>
    <string name="onboarding_exact_alarm_action">Allow exact alarms</string>
    <string name="onboarding_not_now">Not now</string>

    <!-- Location prerequisites (Home and Qibla blocked screen) -->
    <string name="prerequisites_title">Salati needs a few adjustments</string>
    <string name="prerequisites_subtitle">Prayer times and Qibla need your location. Fix what\'s missing, or pick your city yourself.</string>
    <string name="prerequisite_internet">Internet connection</string>
    <string name="prerequisite_internet_action">Connect</string>
    <string name="prerequisite_services">Location services</string>
    <string name="prerequisite_permission">Location permission</string>
    <string name="prerequisite_action_allow">Allow</string>
    <string name="prerequisite_action_choose_manually">Choose location manually</string>

    <!-- Home -->
    <string name="next_prayer">Next Prayer: %1$s</string>
    <string name="imsak">Imsak</string>
    <string name="fajr">Fajr</string>
    <string name="shorouq">Shorouq</string>
    <string name="dhuhr">Dhuhr</string>
    <string name="asr">Asr</string>
    <string name="maghrib">Maghrib</string>
    <string name="isha">Isha</string>
    <string name="first_third">First Third</string>
    <string name="midnight">Midnight</string>
    <string name="last_third">Last Third</string>
    <string name="previous_day">Previous day</string>
    <string name="next_day">Next day</string>
    <string name="back_to_today">Back to today</string>
    <string name="end_of_month">End of month</string>
    <string name="unknown_location">Unknown Location</string>
    <string name="muted">Muted</string>
    <string name="enabled">Enabled</string>

    <!-- Hijri months -->
    <string name="hijri_muharram">Muharram</string>
    <string name="hijri_safar">Safar</string>
    <string name="hijri_rabi_al_awwal">Rabi\' al-Awwal</string>
    <string name="hijri_rabi_al_akhar">Rabi\' al-Akhar</string>
    <string name="hijri_jumada_al_oula">Jumada al-Awwal</string>
    <string name="hijri_jumada_al_akhera">Jumada al-Akhera</string>
    <string name="hijri_rajab">Rajab</string>
    <string name="hijri_shaban">Sha\'ban</string>
    <string name="hijri_ramadan">Ramadan</string>
    <string name="hijri_shawwal">Shawwal</string>
    <string name="hijri_dhu_al_qidah">Dhu al-Qi\'dah</string>
    <string name="hijri_dhu_al_hijjah">Dhu al-Hijjah</string>

    <!-- Adhan notification -->
    <string name="adhan_playing_title">Prayer time is due</string>
    <string name="adhan_playing_body">It\'s time for Al-%1$s prayer</string>
    <string name="minor_timing_playing_title">%1$s</string>
    <string name="minor_timing_playing_body">It\'s time for %1$s</string>

    <!-- Qibla -->
    <string name="face_the_kaaba_wherever_you_are">Face the Kaaba wherever you are</string>
    <string name="sensor_accuracy_is_excellent">Sensor accuracy is excellent.</string>
    <string name="sensor_accuracy_is_moderate">Sensor accuracy is moderate.</string>
    <string name="sensor_accuracy_is_poor">Sensor accuracy is poor.</string>
    <string name="recalibrate">Recalibrate</string>
    <string name="qibla_direction_can_t_be_shown_here">Qibla direction can\'t be shown here.</string>
    <string name="this_device_doesn_t_have_the_sensors_needed_for_a_compass">This device does not have the sensors needed for a compass.</string>

    <!-- Qibla: Calibration overlay -->
    <string name="compass_calibrate_title">Calibrate your compass</string>
    <string name="compass_calibrate_body">Move your phone in a figure-8 motion to improve accuracy.</string>
    <string name="compass_calibrate_done">Done</string>
    <string name="accuracy_excellent">Accuracy: Excellent</string>
    <string name="accuracy_moderate">Accuracy: Moderate — Keep going</string>
    <string name="accuracy_poor">Accuracy: Poor — Keep moving in a figure-8 to recalibrate</string>
    <string name="accuracy_unreliable">Accuracy: Unreliable — Move away from metal or magnets then recalibrate</string>

    <!-- Prayer tracker -->
    <string name="track_your_prayers">Track your prayers</string>
    <string name="track_your_five_daily_prayers">Track your five daily prayers</string>
    <string name="did_you_pray_x">Did you pray %1$s today?</string>
    <string name="mark_todays_x_prayer">Mark today\'s %1$s prayer</string>
    <string name="previous_month">Previous month</string>
    <string name="next_month">Next month</string>
    <string name="prayed">Prayed</string>
    <string name="missed">Missed</string>
    <string name="prayer_marking_opens_at_prayer_time">You can mark each prayer once its time arrives</string>
    <plurals name="day_streak">
        <item quantity="one">%1$d Day streak</item>
        <item quantity="other">%1$d Days streak</item>
    </plurals>

    <!-- Custom alarms -->
    <string name="custom_alarms">Custom Alarms</string>
    <string name="create_custom_alarms_before_or_after_prayers">Create custom alarms before or after prayers</string>
    <string name="no_custom_alarms_title">There are no set custom alarms</string>
    <string name="no_custom_alarms_body">Click the button \u002B below to add a new custom alarm.</string>
    <string name="add_custom_alarm">Add custom alarm</string>
    <string name="custom_alarm_default_title">Custom Alarm</string>
    <string name="minutes_short">min</string>
    <string name="alarm_offset_before">before %1$s</string>
    <string name="alarm_offset_after">after %1$s</string>
    <string name="at_prayer_time">At %1$s time</string>
    <string name="repeat_every_day">Repeat everyday</string>
    <plurals name="minutes_before_prayer">
        <item quantity="one">%1$d minute before %2$s</item>
        <item quantity="other">%1$d minutes before %2$s</item>
    </plurals>
    <plurals name="minutes_after_prayer">
        <item quantity="one">%1$d minute after %2$s</item>
        <item quantity="other">%1$d minutes after %2$s</item>
    </plurals>

    <!-- Custom alarms: Editor sheet -->
    <string name="alarm_minutes_field_label">Minutes</string>
    <string name="prayer">Prayer</string>
    <string name="timing">Timing</string>
    <string name="before">Before</string>
    <string name="after">After</string>

    <!-- Custom alarms: Notification channel -->
    <string name="custom_alarms_channel_name" translatable="false">Custom alarms channel name</string>

    <!-- Settings -->
    <string name="customize_your_app_experience">Customize your app experience</string>
    <string name="prayer_notifications">Prayer Notifications</string>
    <string name="get_alerted_and_notified_at_each_prayer_time">Get alerted and notified at each prayer time</string>
    <string name="appearance">Appearance</string>
    <string name="hijri_calendar">Hijri Calendar</string>
    <string name="date_adjustment">Date Adjustment</string>
    <string name="correct_for_local_moon_sighting">Correct for local moon sighting</string>
    <string name="custom_alarm_sound">Custom alarm sound</string>
    <string name="custom_alarm_sound_default">Device default</string>
    <string name="custom_alarm_sound_alert">Alert</string>
    <string name="about">About</string>
    <string name="rate_salati">Rate Salati</string>
    <string name="share_with_friends">Share with Friends</string>
    <string name="contact_support">Contact Support</string>
    <string name="version">Version</string>

    <!-- Location picker -->
    <string name="location_picker_search_hint">Search a city or place</string>
    <string name="location_picker_resolving">Resolving location…</string>
    <string name="location_picker_confirm">Confirm location</string>
    <string name="location_picker_use_current_location">Use my current location</string>

    <!-- Messages: errors and results -->
    <string name="error">Error: %1$s</string>
    <string name="something_went_wrong">Something went wrong.</string>
    <string name="no_internet_connection">No internet connection.</string>
    <string name="request_timed_out_check_connection">Request timed out. Check your connection.</string>
    <string name="failed_to_get_location">Failed to get location</string>
    <string name="failed_to_get_location_name">Failed to get Location name</string>
    <string name="couldn_t_determine_location_name">Couldn\'t determine location name. Check your connection.</string>
    <string name="couldn_t_save_location">Couldn\'t save location. Try again.</string>
    <string name="search_failed_check_connection">Search failed. Check your connection.</string>
    <string name="failed_to_load_qibla_direction">Failed to load Qibla direction</string>
    <string name="failed_to_update_prayer_status">Failed to update prayer status</string>
    <string name="alarm_saved">Alarm saved</string>
    <string name="duplicate_alarm_exists">Same alarm exists</string>
    <string name="couldn_t_load_alarms">Couldn\'t load alarms</string>
    <string name="couldn_t_save_alarm">Couldn\'t save alarm</string>
    <string name="couldn_t_delete_alarm">Couldn\'t delete alarm</string>
    <string name="couldn_t_update_alarm">Couldn\'t update alarm</string>
    <string name="couldn_t_update_time_format">Couldn\'t update time format</string>
    <string name="couldn_t_update_notification_settings">Couldn\'t update notification settings</string>
    <string name="couldn_t_update_calculation_method">Couldn\'t update calculation method</string>
    <string name="couldn_t_update_madhab">Couldn\'t update madhab</string>
    <string name="couldn_t_update_theme">Couldn\'t update theme</string>
    <string name="couldn_t_update_language">Couldn\'t update language</string>
    <string name="couldn_t_update_adhan_sound">Couldn\'t update adhan sound</string>
    <string name="couldn_t_update_hijri_offset">Couldn\'t update Hijri offset</string>
    <string name="couldn_t_update_custom_alarm_sound">Couldn\'t update custom alarm sound</string>

    <!-- Accessibility: content descriptions -->
    <string name="cd_back">Back</string>
    <string name="cd_search">Search</string>
    <string name="cd_status_badge">Status badge</string>
    <string name="cd_prayer_location">Prayer location</string>
    <string name="cd_decrease">Decrease</string>
    <string name="cd_increase">Increase</string>

</resources>
```

## app\src\main\res\values\themes.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.Salati" parent="Theme.AppCompat.DayNight.NoActionBar" />
</resources>
```

## app\src\main\res\values-ar\strings.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <!-- App -->
    <string name="app_name">صلاتي</string>
    <string name="app_motto">إِنَّ الصَّلَاةَ كَانَتْ عَلَى الْمُؤْمِنِينَ كِتَابًا مَوْقُوتاً</string>

    <!-- Common actions -->
    <string name="retry">حاول مرةً أخرى</string>
    <string name="save">حفظ</string>
    <string name="edit">تعديل</string>
    <string name="delete">حذف</string>
    <string name="stop">إيقاف</string>
    <string name="action_open_settings">فتح الإعدادات</string>
    <string name="please_wait">يرجى الانتظار</string>
    <string name="is_selected">مختارة</string>

    <!-- Bottom navigation -->
    <string name="home">الرئيسية</string>
    <string name="qibla">القبلة</string>
    <string name="tracker">المتتبع</string>
    <string name="alarms">المنبهات</string>
    <string name="settings">الإعدادات</string>

    <!-- Onboarding: Welcome page -->
    <string name="language">اللغة</string>
    <string name="language_english">English</string>
    <string name="language_arabic">العربية</string>
    <string name="theme">المظهر</string>
    <string name="theme_system_default">نظام الهاتف (الافتراضي)</string>
    <string name="theme_light">ساطع</string>
    <string name="theme_dark">داكن</string>
    <string name="onboarding_change_later_hint">يمكنك تغيير كل ذلك لاحقًا من الإعدادات.</string>
    <string name="onboarding_continue">متابعة</string>
    <string name="get_started">ابدأ</string>

    <!-- Onboarding: Prayer times page -->
    <string name="onboarding_times_title">مواقيت الصلاة</string>
    <string name="onboarding_calculation_subtitle">اختر طريقة حساب مواقيت الصلاة وكيفية عرضها.</string>
    <string name="prayer_calculation">حساب مواقيت الصلاة</string>
    <string name="calculation_method">مرجعية الحساب</string>
    <string name="calc_method_egyptian_general_authority">الهيئة العامة المصرية</string>
    <string name="calc_method_umm_al_qura">أم القرى، مكة</string>
    <string name="calc_method_muslim_world_league">رابطة العالم الإسلامي</string>
    <string name="calc_method_isna">الجمعية الإسلامية لأميركا الشمالية</string>
    <string name="calc_method_karachi">جامعة العلوم الإسلامية بكاراتشي، باكستان</string>
    <string name="asr_madhab">مذهب حساب ميقات العصر</string>
    <string name="madhab_shafi">المذهب الشافعي (افتراضي)</string>
    <string name="madhab_hanafi">المذهب الحنفي</string>
    <string name="time_format">تنسيق الوقت</string>
    <string name="time_format_12_hour">12 ساعة</string>
    <string name="time_format_24_hour">24 ساعة</string>

    <!-- Onboarding: Notifications page -->
    <string name="notifications">الإشعارات</string>
    <string name="onboarding_notifications_subtitle">احصل على الأذان وتنبيهات الصلاة في وقتها.</string>
    <string name="onboarding_notifications_toggle_title">تفعيل الإشعارات</string>
    <string name="onboarding_notifications_toggle_subtitle">الأذان وتنبيهات الصلاة</string>
    <string name="adhan_sound">نغمة الآذان</string>
    <string name="adhan_sound_default">النغمة الافتراضية</string>
    <string name="adhan_sound_egypt">آذان مصر</string>
    <string name="adhan_sound_makkah">آذان مكة</string>
    <string name="adhan_sound_madinah">آذان المدينة</string>
    <string name="adhan_sound_silent">صامت (إشعارات فقط)</string>
    <string name="onboarding_notifications_action">السماح بالإشعارات</string>
    <string name="onboarding_exact_alarm_note">تحتاج تنبيهات الصلاة أيضاً إلى إذن المنبهات الدقيقة.</string>
    <string name="onboarding_exact_alarm_action">السماح بالمنبهات الدقيقة</string>
    <string name="onboarding_not_now">ليس الآن</string>

    <!-- Location prerequisites (Home and Qibla blocked screen) -->
    <string name="prerequisites_title">يحتاج تطبيق صلاتي إلى ضبط بعض الإعدادات</string>
    <string name="prerequisites_subtitle">تحتاج مواقيت الصلاة والقبلة إلى موقعك. أصلح ما ينقص، أو اختر مدينتك بنفسك.</string>
    <string name="prerequisite_internet">الاتصال بالإنترنت</string>
    <string name="prerequisite_internet_action">اتصال</string>
    <string name="prerequisite_services">خدمات الموقع</string>
    <string name="prerequisite_permission">إذن الموقع</string>
    <string name="prerequisite_action_allow">السماح</string>
    <string name="prerequisite_action_choose_manually">اختيار الموقع يدويًا</string>

    <!-- Home -->
    <string name="next_prayer">الصلاة التالية: %1$s</string>
    <string name="imsak">الإمساك</string>
    <string name="fajr">الفجر</string>
    <string name="shorouq">الشروق</string>
    <string name="dhuhr">الظهر</string>
    <string name="asr">العصر</string>
    <string name="maghrib">المغرب</string>
    <string name="isha">العشاء</string>
    <string name="first_third">ثلث الليل الأول</string>
    <string name="midnight">منتصف الليل</string>
    <string name="last_third">ثلث الليل الأخير</string>
    <string name="previous_day">اليوم الماضي</string>
    <string name="next_day">اليوم التالي</string>
    <string name="back_to_today">العودة إلى اليوم</string>
    <string name="end_of_month">نهاية الشهر</string>
    <string name="unknown_location">موقع غير معروف</string>
    <string name="muted">صامت</string>
    <string name="enabled">مفعل</string>

    <!-- Hijri months -->
    <string name="hijri_muharram">المحرم</string>
    <string name="hijri_safar">صفر</string>
    <string name="hijri_rabi_al_awwal">ربيع الأول</string>
    <string name="hijri_rabi_al_akhar">ربيع الآخر</string>
    <string name="hijri_jumada_al_oula">جمادى الأولى</string>
    <string name="hijri_jumada_al_akhera">جمادى الآخرة</string>
    <string name="hijri_rajab">رجب</string>
    <string name="hijri_shaban">شعبان</string>
    <string name="hijri_ramadan">رمضان</string>
    <string name="hijri_shawwal">شوال</string>
    <string name="hijri_dhu_al_qidah">ذو القعدة</string>
    <string name="hijri_dhu_al_hijjah">ذو الحجة</string>

    <!-- Adhan notification -->
    <string name="adhan_playing_title">حان موعد الصلاة</string>
    <string name="adhan_playing_body">حان الآن موعد صلاة %1$s</string>
    <string name="minor_timing_playing_title">%1$s</string>
    <string name="minor_timing_playing_body">حان الآن وقت %1$s</string>

    <!-- Qibla -->
    <string name="face_the_kaaba_wherever_you_are">توجه ناحية القبلة أينما كنت</string>
    <string name="sensor_accuracy_is_excellent">دقة المستشعر ممتازة</string>
    <string name="sensor_accuracy_is_moderate">دقة المستشعر متوسطة</string>
    <string name="sensor_accuracy_is_poor">دقة المستشعر ضعيفة</string>
    <string name="recalibrate">أعد الضبط</string>
    <string name="qibla_direction_can_t_be_shown_here">لا يمكن إظهار اتجاه القبلة هنا</string>
    <string name="this_device_doesn_t_have_the_sensors_needed_for_a_compass">يفتقد هاتفك المستشعرات اللازمة لتفقد البوصلة</string>

    <!-- Qibla: Calibration overlay -->
    <string name="compass_calibrate_title">معايرة البوصلة</string>
    <string name="compass_calibrate_body">حرّك هاتفك بحركة رقم 8 لتحسين الدقة.</string>
    <string name="compass_calibrate_done">تم</string>
    <string name="accuracy_excellent">دقة المستشعر: ممتازة</string>
    <string name="accuracy_moderate">دقة المستشعر: متوسطة، واصل المعايرة</string>
    <string name="accuracy_poor"> دقة المستشعر: ضعيفة، واصل تحريك الهاتف على شكل رقم 8 لإعادة المعايرة</string>
    <string name="accuracy_unreliable">دقة المستشعر: منعدمة، ابتعد عن الأسطح المعدنية والممغنطة ثم أعد المعايرة</string>

    <!-- Prayer tracker -->
    <string name="track_your_prayers">تتبع صلواتك الخمس</string>
    <string name="track_your_five_daily_prayers">تتبع صلواتك الخمس كل يوم</string>
    <string name="did_you_pray_x">هل صليت %1$s اليوم؟</string>
    <string name="mark_todays_x_prayer">أضف وسماُ لصلاة %1$s اليوم</string>
    <string name="previous_month">الشهر الماضي</string>
    <string name="next_month">الشهر التالي</string>
    <string name="prayed">مقضية</string>
    <string name="missed">منسية</string>
    <string name="prayer_marking_opens_at_prayer_time">يمكنك تسجيل كل صلاة عند حلول وقتها</string>
    <plurals name="day_streak">
        <item quantity="zero">%1$d يوم متتالي</item>
        <item quantity="one">يوم واحد متتالي</item>
        <item quantity="two">يومان متتاليان</item>
        <item quantity="few">%1$d أيام متتالية</item>
        <item quantity="many">%1$d يومًا متتاليًا</item>
        <item quantity="other">%1$d يوم متتالي</item>
    </plurals>

    <!-- Custom alarms -->
    <string name="custom_alarms">المنبهات المخصصة</string>
    <string name="create_custom_alarms_before_or_after_prayers">أنشئ منبهاً خاصاً قبل أو بعد الصلوات المفروضة</string>
    <string name="no_custom_alarms_title">لا توجد منبهات خاصة بك</string>
    <string name="no_custom_alarms_body">اضغط الزر \u002B أدناه لإنشاء منبه جديد</string>
    <string name="add_custom_alarm">أنشئ منبهاً</string>
    <string name="custom_alarm_default_title">منبه خاص</string>
    <string name="minutes_short">min</string>
    <string name="alarm_offset_before">قبل %1$s</string>
    <string name="alarm_offset_after">بعد %1$s</string>
    <string name="at_prayer_time">%1$s في وقت</string>
    <string name="repeat_every_day">تكرار يومي</string>
    <plurals name="minutes_before_prayer">
        <item quantity="zero">%1$d لدى %2$s</item>
        <item quantity="one">%1$d دقيقة واحدة قبل %2$s</item>
        <item quantity="two">%1$d دقيقتان قبل %2$s</item>
        <item quantity="few">%1$d دقائق قبل %2$s</item>
        <item quantity="many">%1$d دقيقة قبل %2$s</item>
        <item quantity="other">%1$d دقائق قبل %2$s</item>
    </plurals>
    <plurals name="minutes_after_prayer">
        <item quantity="zero">%1$d لدى %2$s</item>
        <item quantity="one">%1$d دقيقة واحدة بعد %2$s</item>
        <item quantity="two">%1$d دقيقتان بعد %2$s</item>
        <item quantity="few">%1$d دقائق بعد %2$s</item>
        <item quantity="many">%1$d دقيقة بعد %2$s</item>
        <item quantity="other">%1$d دقائق بعد %2$s</item>
    </plurals>

    <!-- Custom alarms: Editor sheet -->
    <string name="alarm_minutes_field_label">الدقائق</string>
    <string name="prayer">الصلاة</string>
    <string name="timing">التوقيت</string>
    <string name="before">قبل الآذان</string>
    <string name="after">بعد الآذان</string>

    <!-- Custom alarms: Notification channel -->

    <!-- Settings -->
    <string name="customize_your_app_experience">تحكم بمظهر التطبيق حسب رغبتك</string>
    <string name="prayer_notifications">إشعارات الصلاة</string>
    <string name="get_alerted_and_notified_at_each_prayer_time">احصل على تنبيهات لكل صلاة</string>
    <string name="appearance">الهيئة</string>
    <string name="hijri_calendar">التقويم الهجري</string>
    <string name="date_adjustment">ضبط التاريخ</string>
    <string name="correct_for_local_moon_sighting">تصحيح التاريخ بناءً على رؤية الهلال</string>
    <string name="custom_alarm_sound">صوت المنبه الخاص</string>
    <string name="custom_alarm_sound_default">نغمة المنبه الخاص الافتراضية</string>
    <string name="custom_alarm_sound_alert">تنبيه خاص</string>
    <string name="about">المزيد</string>
    <string name="rate_salati">قيم تطبيق صلاتي</string>
    <string name="share_with_friends">شارك مع الأصدقاء</string>
    <string name="contact_support">الاتصال بالدعم الفني</string>
    <string name="version">النسخة</string>

    <!-- Location picker -->
    <string name="location_picker_search_hint">ابحث عن مدينة أو مكان</string>
    <string name="location_picker_resolving">جارٍ تحديد الموقع…</string>
    <string name="location_picker_confirm">تأكيد الموقع</string>
    <string name="location_picker_use_current_location">استخدام موقعي الحالي</string>

    <!-- Messages: errors and results -->
    <string name="error">خطأ: %1$s</string>
    <string name="something_went_wrong">يوجد ثمة خطأ</string>
    <string name="no_internet_connection">لا يوجد اتصال بالإنترنت</string>
    <string name="request_timed_out_check_connection">انتهت مهلة الطلب. تحقق من اتصالك بالإنترنت.</string>
    <string name="failed_to_get_location">فشل العثور على موقع الهاتف</string>
    <string name="failed_to_get_location_name">لم نتمكن من العثور على اسم الموقع</string>
    <string name="couldn_t_determine_location_name">تعذّر تحديد اسم الموقع. تحقق من اتصالك بالإنترنت.</string>
    <string name="couldn_t_save_location">تعذّر حفظ الموقع. حاول مرة أخرى.</string>
    <string name="search_failed_check_connection">فشل البحث. تحقق من اتصالك بالإنترنت.</string>
    <string name="failed_to_load_qibla_direction">فشل تحديد اتجاه القبلة</string>
    <string name="failed_to_update_prayer_status">خطأ أثناء تحديث الصلاة</string>
    <string name="alarm_saved">تم حفظ المنبه</string>
    <string name="duplicate_alarm_exists">لديك منبه مماثل</string>
    <string name="couldn_t_load_alarms">تعذّر تحميل المنبهات</string>
    <string name="couldn_t_save_alarm">تعذّر حفظ المنبه</string>
    <string name="couldn_t_delete_alarm">تعذّر حذف المنبه</string>
    <string name="couldn_t_update_alarm">تعذّر تحديث المنبه</string>
    <string name="couldn_t_update_time_format">تعذّر تحديث تنسيق الوقت</string>
    <string name="couldn_t_update_notification_settings">لم نتمكن من تحديث إعدادات الإشعارات</string>
    <string name="couldn_t_update_calculation_method">لم نتمكن من تحديث وسيلة حساب مواقيت الصلاة</string>
    <string name="couldn_t_update_madhab">لم نتمكن من تغيير مذهب ميقات العصر</string>
    <string name="couldn_t_update_theme">لم نتمكن من تحديث ثيمة التطبيق</string>
    <string name="couldn_t_update_language">لم نتمكن من تحديث لغة التطبيق</string>
    <string name="couldn_t_update_adhan_sound">لم نتمكن من تحديث نغمة الآذان</string>
    <string name="couldn_t_update_hijri_offset">لم نتمكن من ضبط التاريخ الهجري</string>
    <string name="couldn_t_update_custom_alarm_sound">فشل تحديث صوت المنبه الخاص</string>

    <!-- Accessibility: content descriptions -->
    <string name="cd_back">رجوع</string>
    <string name="cd_search">بحث</string>
    <string name="cd_status_badge">شارة الحالة</string>
    <string name="cd_prayer_location">موقع الصلاة</string>
    <string name="cd_decrease">إنقاص</string>
    <string name="cd_increase">زيادة</string>

</resources>
```

## app\src\main\res\xml\backup_rules.xml

```xml
<?xml version="1.0" encoding="utf-8"?><!--
   Sample backup rules file; uncomment and customize as necessary.
   See https://developer.android.com/guide/topics/data/autobackup
   for details.
   Note: This file is ignored for devices older than API 31
   See https://developer.android.com/about/versions/12/backup-restore
-->
<full-backup-content>
    <!--
   <include domain="sharedpref" path="."/>
   <exclude domain="sharedpref" path="device.xml"/>
-->
</full-backup-content>
```

## app\src\main\res\xml\data_extraction_rules.xml

```xml
<?xml version="1.0" encoding="utf-8"?><!--
   Sample data extraction rules file; uncomment and customize as necessary.
   See https://developer.android.com/about/versions/12/backup-restore#xml-changes
   for details.
-->
<data-extraction-rules>
    <cloud-backup>
        <!-- TODO: Use <include> and <exclude> to control what is backed up.
        <include .../>
        <exclude .../>
        -->
    </cloud-backup>
    <!--
    <device-transfer>
        <include .../>
        <exclude .../>
    </device-transfer>
    -->
</data-extraction-rules>
```

## app\src\test\java\com\mhq\salati\home\domain\service\DayNavigatorTest.kt

```kotlin
package com.mhq.salati.home.domain.service

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.testutil.FakeClock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class DayNavigatorTest {

    private val today = LocalDate.of(2026, 11, 11)
    private val clock = FakeClock(today)
    private val navigator = DayNavigator(clock)

    // --- isToday / today / previousOrNull / next ---

    @Test
    fun `isToday is true only for the clock's day`() {
        assertTrue(navigator.isToday(today))
        assertFalse(navigator.isToday(today.minusDays(1)))
        assertFalse(navigator.isToday(today.plusDays(1)))
    }

    @Test
    fun `today returns the clock's day`() {
        assertEquals(today, navigator.today())
    }

    @Test
    fun `previousOrNull returns null for today`() {
        assertNull(navigator.previousOrNull(today))
    }

    @Test
    fun `previousOrNull returns the previous day for any past or future day`() {
        assertEquals(today.minusDays(1), navigator.previousOrNull(today.minusDays(1)))
        assertEquals(today, navigator.previousOrNull(today.plusDays(1)))
    }

    @Test
    fun `next returns the following day`() {
        assertEquals(today.plusDays(1), navigator.next(today))
    }

    // --- rolloverDate ---

    @Test
    fun `rolloverDate returns tomorrow when the window ends after the day`() {
        val window = PrayerWindow(
            nextPrayer = PrayerName.FAJR,
            start = today.atTime(18, 0).atZone(clock.zone()).toInstant(),
            end = today.plusDays(1).atTime(5, 0).atZone(clock.zone()).toInstant()
        )
        assertEquals(today.plusDays(1), navigator.rolloverDate(today, window))
    }

    @Test
    fun `rolloverDate returns null when the window ends within the day`() {
        val window = PrayerWindow(
            nextPrayer = PrayerName.MAGHRIB,
            start = today.atTime(12, 0).atZone(clock.zone()).toInstant(),
            end = today.atTime(18, 0).atZone(clock.zone()).toInstant()
        )
        assertNull(navigator.rolloverDate(today, window))
    }

    @Test
    fun `rolloverDate returns null when there is no window`() {
        assertNull(navigator.rolloverDate(today, null))
    }

    // --- endOfMonth ---

    @Test
    fun `endOfMonth returns the month's last day for a mid-month date`() {
        assertEquals(
            LocalDate.of(2026, 11, 30),
            navigator.endOfMonth(LocalDate.of(2026, 11, 11))
        )
    }

    @Test
    fun `endOfMonth returns the same day when the date already is the month's last day`() {
        assertEquals(
            LocalDate.of(2026, 11, 30),
            navigator.endOfMonth(LocalDate.of(2026, 11, 30))
        )
    }

    @Test
    fun `endOfMonth returns the next month's last day when on the last day`() {
        assertEquals(
            LocalDate.of(2026, 12, 31),
            navigator.endOfMonth(LocalDate.of(2026, 11, 30))
        )
    }

    @Test
    fun `endOfMonth handles short months from their last day`() {
        assertEquals(
            LocalDate.of(2026, 3, 31),
            navigator.endOfMonth(LocalDate.of(2026, 2, 28))
        )
    }

    @Test
    fun `endOfMonth handles leap year February`() {
        assertEquals(
            LocalDate.of(2024, 2, 29),
            navigator.endOfMonth(LocalDate.of(2024, 2, 10))
        )
        // From Feb 29 itself: the next month's last day
        assertEquals(
            LocalDate.of(2024, 3, 31),
            navigator.endOfMonth(LocalDate.of(2024, 2, 29))
        )
    }

    @Test
    fun `endOfMonth crosses the year boundary from December 31`() {
        assertEquals(
            LocalDate.of(2027, 1, 31),
            navigator.endOfMonth(LocalDate.of(2026, 12, 31))
        )
    }
}
```

## app\src\test\java\com\mhq\salati\home\domain\usecases\LoadPrayerTimesUseCaseTest.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerTimesError
import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.prayertimes.domain.model.HijriDate
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.HijriMonth
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import com.mhq.salati.testutil.FakeClock
import com.mhq.salati.testutil.FakeConnectivityChecker
import com.mhq.salati.testutil.FakePrayerTimesRepository
import com.mhq.salati.testutil.FakeSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class LoadPrayerTimesUseCaseTest {

    private val coordinates = Coordinates(30.0444, 31.2357)
    private val date = LocalDate.of(2026, 11, 11)

    private fun timings() = PrayerTimings(
        imsak = LocalTime.of(4, 50),
        fajr = LocalTime.of(5, 0),
        sunrise = LocalTime.of(6, 10),
        dhuhr = LocalTime.of(12, 0),
        asr = LocalTime.of(15, 0),
        sunset = LocalTime.of(17, 55),
        maghrib = LocalTime.of(18, 0),
        isha = LocalTime.of(19, 30),
        midnight = LocalTime.of(22, 30),
        firstThird = LocalTime.of(21, 30),
        lastThird = LocalTime.of(23, 30)
    )

    private fun result() = PrayerTimesResult(
        timings = timings(),
        date = PrayerDate(date, HijriDate(25, HijriMonth.JUMADA_AL_AWWAL, 1448))
    )

    private fun useCase(
        cached: Map<String, PrayerTimesResult> = emptyMap(),
        network: Map<String, Result<PrayerTimesResult>> = emptyMap(),
        online: Boolean = true,
        networkDelayMillis: Long = 0
    ): LoadPrayerTimesUseCase {
        val clock = FakeClock(date)
        val repo = FakePrayerTimesRepository(cached, network, networkDelayMillis)
        val settingsRepo = FakeSettingsRepository()
        val cachedUseCase = GetCachedPrayerTimesUseCase(repo, settingsRepo)
        val networkUseCase = GetPrayerTimesUseCase(repo, settingsRepo)
        return LoadPrayerTimesUseCase(
            getCachedPrayerTimesUseCase = cachedUseCase,
            getPrayerTimesUseCase = networkUseCase,
            resolvePrayerWindowForDisplayUseCase = ResolvePrayerWindowForDisplayUseCase(
                clock = clock,
                getCachedPrayerTimesUseCase = cachedUseCase,
                getPrayerTimesUseCase = networkUseCase,
                calculatePrayerWindowUseCase = CalculatePrayerWindowUseCase(clock, networkUseCase, cachedUseCase)
            ),
            calculatePrayerStatusUseCase = CalculatePrayerStatusUseCase(
                clock = clock,
                calculateCurrentPrayerNameUseCase = CalculateCurrentPrayerNameUseCase(clock),
                calculatePastPrayersUseCase = CalculatePastPrayersUseCase(clock)
            ),
            connectivityChecker = FakeConnectivityChecker(online)
        )
    }

    @Test
    fun `cache hit emits a single Loaded without going online`() = runTest {
        val useCase = useCase(cached = mapOf(date.toDateKey() to result()))

        val emissions = useCase(date, coordinates).toList()

        assertEquals(1, emissions.size)
        val loaded = emissions[0] as PrayerTimesLoad.Loaded
        assertEquals(LocalTime.of(5, 0), loaded.timings.fajr)
        assertEquals(date, loaded.prayerDate.gregorianDate)
    }

    @Test
    fun `cache miss emits FetchingFromNetwork then Loaded`() = runTest {
        val useCase = useCase(network = mapOf(date.toDateKey() to Result.success(result())))

        val emissions = useCase(date, coordinates).toList()

        assertEquals(2, emissions.size)
        assertEquals(PrayerTimesLoad.FetchingFromNetwork, emissions[0])
        val loaded = emissions[1] as PrayerTimesLoad.Loaded
        assertEquals(LocalTime.of(12, 0), loaded.timings.dhuhr)
    }

    @Test
    fun `today's Loaded status carries the current prayer and past prayers`() = runTest {
        val useCase = useCase(cached = mapOf(date.toDateKey() to result()))

        val loaded = useCase(date, coordinates).first() as PrayerTimesLoad.Loaded

        // Clock fixed at 12:00: Dhuhr is the current prayer; Fajr and sunrise have passed.
        assertEquals(PrayerName.DHUHR, loaded.status.currentPrayer)
        assertTrue(loaded.status.pastPrayers.contains(PrayerName.FAJR))
        assertTrue(loaded.status.pastPrayers.contains(PrayerName.DHUHR))
    }

    @Test
    fun `a non-today Loaded status carries no current prayer or past prayers`() = runTest {
        val otherDate = date.minusDays(1)
        val useCase = useCase(cached = mapOf(otherDate.toDateKey() to result()))

        val loaded = useCase(otherDate, coordinates).first() as PrayerTimesLoad.Loaded

        assertEquals(null, loaded.status.currentPrayer)
        assertEquals(emptySet<PrayerName>(), loaded.status.pastPrayers)
    }

    @Test
    fun `network failure while offline maps to PrayerTimesError Offline`() = runTest {
        val useCase = useCase(
            network = mapOf(date.toDateKey() to Result.failure(IllegalStateException("boom"))),
            online = false
        )

        val emissions = useCase(date, coordinates).toList()

        assertEquals(2, emissions.size)
        val failed = emissions[1] as PrayerTimesLoad.Failed
        assertEquals(PrayerTimesError.Offline, failed.error)
    }

    @Test
    fun `network failure while online maps to PrayerTimesError Other with the message`() = runTest {
        val useCase = useCase(
            network = mapOf(date.toDateKey() to Result.failure(IllegalStateException("http 500"))),
            online = true
        )

        val emissions = useCase(date, coordinates).toList()

        val failed = emissions[1] as PrayerTimesLoad.Failed
        assertEquals(PrayerTimesError.Other("http 500"), failed.error)
    }

    @Test
    fun `a network fetch slower than the timeout maps to PrayerTimesError TimedOut`() = runTest {
        val useCase = useCase(
            network = mapOf(date.toDateKey() to Result.success(result())),
            online = true,
            // The fetch timeout is 5s; runTest's virtual clock makes this instantaneous.
            networkDelayMillis = 10_000
        )

        val emissions = useCase(date, coordinates).toList()

        assertEquals(2, emissions.size)
        val failed = emissions[1] as PrayerTimesLoad.Failed
        assertEquals(PrayerTimesError.TimedOut, failed.error)
    }
}
```

## app\src\test\java\com\mhq\salati\home\domain\usecases\ResolvePrayerWindowForDisplayUseCaseTest.kt

```kotlin
package com.mhq.salati.home.domain.usecases

import com.mhq.salati.prayertimes.domain.model.HijriDate
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.HijriMonth
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import com.mhq.salati.testutil.FakeClock
import com.mhq.salati.testutil.FakePrayerTimesRepository
import com.mhq.salati.testutil.FakeSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class ResolvePrayerWindowForDisplayUseCaseTest {

    private val coordinates = Coordinates(30.0444, 31.2357)
    private val today = LocalDate.of(2026, 11, 11)

    private fun timings(fajr: LocalTime, dhuhr: LocalTime, asr: LocalTime, maghrib: LocalTime, isha: LocalTime) =
        PrayerTimings(
            imsak = fajr.minusMinutes(10),
            fajr = fajr,
            sunrise = fajr.plusHours(1),
            dhuhr = dhuhr,
            asr = asr,
            sunset = maghrib.minusMinutes(1),
            maghrib = maghrib,
            isha = isha,
            midnight = isha.plusHours(3),
            firstThird = isha.plusHours(2),
            lastThird = isha.plusHours(4)
        )

    private fun prayerDate(date: LocalDate) = PrayerDate(
        gregorianDate = date,
        hijriDate = HijriDate(25, HijriMonth.JUMADA_AL_AWWAL, 1448)
    )

    private fun result(date: LocalDate, dayTimings: PrayerTimings) =
        PrayerTimesResult(timings = dayTimings, date = prayerDate(date))

    /** Standard day: Fajr 05:00, Dhuhr 12:00, Asr 15:00, Maghrib 18:00, Isha 19:30. */
    private fun standardTimings() =
        timings(LocalTime.of(5, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0), LocalTime.of(19, 30))

    private fun useCase(
        cached: Map<String, PrayerTimesResult> = emptyMap(),
        network: Map<String, Result<PrayerTimesResult>> = emptyMap()
    ): ResolvePrayerWindowForDisplayUseCase {
        val clock = FakeClock(today)
        val repo = FakePrayerTimesRepository(cached, network)
        val settingsRepo = FakeSettingsRepository()
        val cachedUseCase = GetCachedPrayerTimesUseCase(repo, settingsRepo)
        val networkUseCase = GetPrayerTimesUseCase(repo, settingsRepo)
        return ResolvePrayerWindowForDisplayUseCase(
            clock = clock,
            getCachedPrayerTimesUseCase = cachedUseCase,
            getPrayerTimesUseCase = networkUseCase,
            calculatePrayerWindowUseCase = CalculatePrayerWindowUseCase(clock, networkUseCase, cachedUseCase)
        )
    }

    @Test
    fun `browsing today uses the browsed timings and never touches cache or network`() = runTest {
        val browsed = standardTimings()
        val useCase = useCase(
            cached = mapOf(today.toDateKey() to result(today, timings(LocalTime.MIDNIGHT, LocalTime.NOON, LocalTime.NOON, LocalTime.NOON, LocalTime.of(23, 0)))),
            network = mapOf(today.toDateKey() to Result.success(result(today, timings(LocalTime.MIDNIGHT, LocalTime.NOON, LocalTime.NOON, LocalTime.NOON, LocalTime.of(23, 30)))))
        )

        val window = useCase(browsed, today, coordinates)

        // Clock is fixed at 12:00, so the window runs Dhuhr 12:00 -> Asr 15:00 of the browsed day.
        assertEquals(PrayerName.ASR, window.nextPrayer)
        val zone = ZoneId.of("UTC")
        assertEquals(today.atTime(12, 0).atZone(zone).toInstant(), window.start)
        assertEquals(today.atTime(15, 0).atZone(zone).toInstant(), window.end)
    }

    @Test
    fun `browsing another day falls back to today's cached timings`() = runTest {
        val browsedDate = today.minusDays(3)
        val browsed = timings(LocalTime.of(4, 0), LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0), LocalTime.of(18, 30))
        val todayTimings = standardTimings()
        val useCase = useCase(cached = mapOf(today.toDateKey() to result(today, todayTimings)))

        val window = useCase(browsed, browsedDate, coordinates)

        // The window must come from today's cached times, not the browsed ones.
        assertEquals(PrayerName.ASR, window.nextPrayer)
        assertEquals(today.atTime(12, 0).atZone(ZoneId.of("UTC")).toInstant(), window.start)
        assertEquals(today.atTime(15, 0).atZone(ZoneId.of("UTC")).toInstant(), window.end)
    }

    @Test
    fun `browsing another day fetches today from the network on a cache miss`() = runTest {
        val browsedDate = today.minusDays(3)
        val browsed = timings(LocalTime.of(4, 0), LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0), LocalTime.of(18, 30))
        val todayTimings = standardTimings()
        val useCase = useCase(network = mapOf(today.toDateKey() to Result.success(result(today, todayTimings))))

        val window = useCase(browsed, browsedDate, coordinates)

        assertEquals(PrayerName.ASR, window.nextPrayer)
        assertEquals(today.atTime(12, 0).atZone(ZoneId.of("UTC")).toInstant(), window.start)
        assertEquals(today.atTime(15, 0).atZone(ZoneId.of("UTC")).toInstant(), window.end)
    }

    @Test
    fun `browsing another day uses the browsed timings as the last resort`() = runTest {
        val browsedDate = today.minusDays(3)
        val browsed = timings(LocalTime.of(4, 0), LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0), LocalTime.of(18, 30))
        val useCase = useCase(
            network = mapOf(today.toDateKey() to Result.failure(IllegalStateException("offline")))
        )

        val window = useCase(browsed, browsedDate, coordinates)

        // Fallback timings place 12:00 between Dhuhr 11:00 and Asr 14:00.
        assertEquals(PrayerName.ASR, window.nextPrayer)
        assertEquals(today.atTime(11, 0).atZone(ZoneId.of("UTC")).toInstant(), window.start)
        assertEquals(today.atTime(14, 0).atZone(ZoneId.of("UTC")).toInstant(), window.end)
    }

    @Test
    fun `after Isha the window runs into tomorrow's Fajr`() = runTest {
        val browsed = timings(LocalTime.of(5, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0), LocalTime.of(19, 30))
        val repo = FakePrayerTimesRepository()
        val settingsRepo = FakeSettingsRepository()
        val cachedUseCase = GetCachedPrayerTimesUseCase(repo, settingsRepo)
        val networkUseCase = GetPrayerTimesUseCase(repo, settingsRepo)
        val lateClock = FakeClock(today, LocalTime.of(23, 0))
        val useCase = ResolvePrayerWindowForDisplayUseCase(
            clock = lateClock,
            getCachedPrayerTimesUseCase = cachedUseCase,
            getPrayerTimesUseCase = networkUseCase,
            calculatePrayerWindowUseCase = CalculatePrayerWindowUseCase(lateClock, networkUseCase, cachedUseCase)
        )
        val window = useCase(browsed, today, coordinates)

        assertEquals(PrayerName.FAJR, window.nextPrayer)
        assertEquals(today.atTime(19, 30).atZone(ZoneId.of("UTC")).toInstant(), window.start)
        assertEquals(today.plusDays(1).atTime(5, 0).atZone(ZoneId.of("UTC")).toInstant(), window.end)
    }
}
```

## app\src\test\java\com\mhq\salati\location\domain\usecases\CheckLocationPrerequisitesUseCaseTest.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.testutil.FakeConnectivityChecker
import com.mhq.salati.testutil.FakePermissionChecker
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CheckLocationPrerequisitesUseCaseTest {

    private class FakeLocationProvider(private val enabled: Boolean) : LocationProvider {
        override fun isLocationEnabled() = enabled
        override suspend fun getCurrentLocation() = throw UnsupportedOperationException()
    }

    /** Builds the use case over fakes and runs it, returning the resulting [LocationPrerequisites]. */
    private suspend fun check(
        hasInternet: Boolean = true,
        servicesEnabled: Boolean = true,
        permissionGranted: Boolean = true
    ): LocationPrerequisites {
        val useCase = CheckLocationPrerequisitesUseCase(
            connectivityChecker = FakeConnectivityChecker(hasInternet),
            locationProvider = FakeLocationProvider(servicesEnabled),
            permissionChecker = FakePermissionChecker(permissionGranted)
        )
        return useCase()
    }

    @Test
    fun `all conditions met returns met prerequisites`() = runTest {
        val result = check()
        assertEquals(true, result.hasInternet)
        assertEquals(true, result.servicesEnabled)
        assertEquals(true, result.permissionGranted)
        assertEquals(true, result.areMet)
    }

    @Test
    fun `missing internet is reported on its own`() = runTest {
        val result = check(hasInternet = false)
        assertEquals(false, result.hasInternet)
        assertEquals(true, result.servicesEnabled)
        assertEquals(true, result.permissionGranted)
        assertEquals(false, result.areMet)
    }

    @Test
    fun `disabled location services are reported on their own`() = runTest {
        val result = check(servicesEnabled = false)
        assertEquals(true, result.hasInternet)
        assertEquals(false, result.servicesEnabled)
        assertEquals(true, result.permissionGranted)
        assertEquals(false, result.areMet)
    }

    @Test
    fun `missing permission is reported on its own`() = runTest {
        val result = check(permissionGranted = false)
        assertEquals(true, result.hasInternet)
        assertEquals(true, result.servicesEnabled)
        assertEquals(false, result.permissionGranted)
        assertEquals(false, result.areMet)
    }

    @Test
    fun `all failures are reported together, not just the first one`() = runTest {
        val result = check(hasInternet = false, servicesEnabled = false, permissionGranted = false)
        assertEquals(false, result.hasInternet)
        assertEquals(false, result.servicesEnabled)
        assertEquals(false, result.permissionGranted)
        assertEquals(false, result.areMet)
    }
}
```

## app\src\test\java\com\mhq\salati\location\domain\usecases\ResolveLocationUseCaseTest.kt

```kotlin
package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationResolution
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.location.domain.repo.LocationRepository
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.testutil.FakeConnectivityChecker
import com.mhq.salati.testutil.FakePermissionChecker
import com.mhq.salati.testutil.FakeSettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class ResolveLocationUseCaseTest {

    private class FakeLocationProvider(
        private val enabled: Boolean,
        private val lookupResult: CoordinatesLookup
    ) : LocationProvider {
        override fun isLocationEnabled() = enabled

        // Drives GetCurrentCoordinatesUseCase once its own checks pass:
        // a slow fix becomes TimedOut, throws become Failed / PermissionDenied.
        override suspend fun getCurrentLocation(): Coordinates = when (lookupResult) {
            is CoordinatesLookup.Found -> lookupResult.coordinates
            is CoordinatesLookup.TimedOut -> {
                delay(10_000.milliseconds) // GPS timeout is 5s; runTest's virtual clock makes this instantaneous.
                throw IllegalStateException("unreachable")
            }
            is CoordinatesLookup.Failed -> throw IllegalStateException("gps broke")
            is CoordinatesLookup.PermissionDenied -> throw SecurityException("denied")
            is CoordinatesLookup.ServicesDisabled,
            is CoordinatesLookup.Locating -> throw IllegalStateException("unreachable")
        }
    }

    private class FakeGeocoder(private val result: GeocodeResult) : GeocoderProvider {
        override suspend fun reverseGeocode(coordinates: Coordinates, acceptLanguage: String?): GeocodeResult = result

        override suspend fun searchByName(query: String, acceptLanguage: String?): List<LocationSearchResult> = emptyList()
    }

    private class FakeLocationRepository : LocationRepository {
        val saved = mutableListOf<SavedLocation>()
        override val savedLocation: Flow<SavedLocation?> = flowOf(null)
        override suspend fun saveManualLocation(location: SavedLocation) {
            saved.add(location)
        }
        override suspend fun clearSavedLocation() = Unit
    }

    private val coordinates = Coordinates(30.0444, 31.2357)

    private fun useCase(
        hasInternet: Boolean = true,
        servicesEnabled: Boolean = true,
        permissionGranted: Boolean = true,
        gpsServicesEnabled: Boolean = servicesEnabled,
        lookupResult: CoordinatesLookup = CoordinatesLookup.Found(coordinates),
        geocodeResult: GeocodeResult = GeocodeResult.Found("Cairo", "Egypt")
    ): Pair<ResolveLocationUseCase, FakeLocationRepository> {
        val locationRepo = FakeLocationRepository()
        val useCase = ResolveLocationUseCase(
            checkLocationPrerequisitesUseCase = CheckLocationPrerequisitesUseCase(
                connectivityChecker = FakeConnectivityChecker(hasInternet),
                locationProvider = FakeLocationProvider(servicesEnabled, lookupResult),
                permissionChecker = FakePermissionChecker(permissionGranted)
            ),
            getCurrentCoordinatesUseCase = GetCurrentCoordinatesUseCase(
                locationProvider = FakeLocationProvider(gpsServicesEnabled, lookupResult),
                permissionChecker = FakePermissionChecker(permissionGranted)
            ),
            reverseGeocodeLocationUseCase = ReverseGeocodeLocationUseCase(FakeGeocoder(geocodeResult)),
            saveManualLocationUseCase = SaveManualLocationUseCase(locationRepo),
            observeSettingsUseCase = ObserveSettingsUseCase(FakeSettingsRepository())
        )
        return useCase to locationRepo
    }

    // --- Blocked cases ---

    @Test
    fun `no internet is Blocked before any GPS work happens`() = runTest {
        val (useCase, repo) = useCase(hasInternet = false)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(false, blocked.prerequisites.hasInternet)
        assertEquals(true, blocked.prerequisites.servicesEnabled)
        assertEquals(true, blocked.prerequisites.permissionGranted)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `disabled location services is Blocked`() = runTest {
        val (useCase, repo) = useCase(servicesEnabled = false)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(true, blocked.prerequisites.hasInternet)
        assertEquals(false, blocked.prerequisites.servicesEnabled)
        assertEquals(true, blocked.prerequisites.permissionGranted)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `missing permission is Blocked`() = runTest {
        val (useCase, repo) = useCase(permissionGranted = false)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(true, blocked.prerequisites.hasInternet)
        assertEquals(true, blocked.prerequisites.servicesEnabled)
        assertEquals(false, blocked.prerequisites.permissionGranted)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `permission lost between the check and the GPS fix is Blocked`() = runTest {
        val (useCase, repo) = useCase(lookupResult = CoordinatesLookup.PermissionDenied)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(false, blocked.prerequisites.permissionGranted)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `services lost between the check and the GPS fix is Blocked`() = runTest {
        val (useCase, repo) = useCase(gpsServicesEnabled = false)

        val result = useCase()

        val blocked = result as LocationResolution.Blocked
        assertEquals(false, blocked.prerequisites.servicesEnabled)
        assertTrue(repo.saved.isEmpty())
    }

    // --- Success paths ---

    @Test
    fun `a GPS fix with a successful geocode resolves and saves the place`() = runTest {
        val (useCase, repo) = useCase()

        val result = useCase()

        val resolved = result as LocationResolution.Resolved
        assertEquals(coordinates, resolved.location.coordinates)
        assertEquals("Cairo", resolved.location.cityName)
        assertEquals("Egypt", resolved.location.countryName)
        assertEquals(listOf(resolved.location), repo.saved)
    }

    @Test
    fun `a geocode NotFound still resolves with unnamed coordinates`() = runTest {
        val (useCase, repo) = useCase(geocodeResult = GeocodeResult.NotFound)

        val result = useCase()

        val resolved = result as LocationResolution.Resolved
        assertEquals(coordinates, resolved.location.coordinates)
        assertNull(resolved.location.cityName)
        assertNull(resolved.location.countryName)
        assertEquals(listOf(resolved.location), repo.saved)
    }

    // --- Other failure paths ---

    @Test
    fun `a GPS timeout is LocationUnavailable`() = runTest {
        val (useCase, repo) = useCase(lookupResult = CoordinatesLookup.TimedOut)

        val result = useCase()

        assertEquals(LocationResolution.LocationUnavailable, result)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `a GPS failure is LocationUnavailable`() = runTest {
        val (useCase, repo) = useCase(lookupResult = CoordinatesLookup.Failed("gps broke"))

        val result = useCase()

        assertEquals(LocationResolution.LocationUnavailable, result)
        assertTrue(repo.saved.isEmpty())
    }

    @Test
    fun `a geocode failure is GeocodingFailed`() = runTest {
        val (useCase, repo) = useCase(geocodeResult = GeocodeResult.Failed(IllegalStateException("nominatim down")))

        val result = useCase()

        assertEquals(LocationResolution.GeocodingFailed, result)
        assertTrue(repo.saved.isEmpty())
    }
}
```

## app\src\test\java\com\mhq\salati\prayertracker\domain\usecases\GetCurrentStreakUseCaseTest.kt

```kotlin
package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.testutil.FakeClock
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class GetCurrentStreakUseCaseTest {

    // Create a Fake Repository to return controlled data
    private class FakeRepository(
        private val records: Map<LocalDate, Map<PrayerName, PrayerStatus>>
    ) : PrayerTrackerRepository {
        override suspend fun getRecordsForRange(
            start: LocalDate,
            end: LocalDate
        ): Map<LocalDate, Map<PrayerName, PrayerStatus>> {
            return records.filterKeys { it in start..end }
        }

        // Stub out other methods as they aren't used by this UseCase
        override fun observeRecordsForMonth(yearMonth: java.time.YearMonth) =
            throw UnsupportedOperationException()

        override fun observeRecordsForDate(date: LocalDate) = throw UnsupportedOperationException()

        override suspend fun setStatus(date: LocalDate, prayer: PrayerName, status: PrayerStatus) =
            Unit
    }

    private val majorPrayers = PrayerName.majorEntries

    private fun allPrayed() = majorPrayers.associateWith {
        PrayerStatus.PRAYED
    }

    private fun hasMissed() = majorPrayers.associateWith {
        if (it == PrayerName.FAJR) PrayerStatus.MISSED else PrayerStatus.PRAYED
    }

    private fun inProgress() = majorPrayers.associateWith {
        if (it == PrayerName.FAJR) PrayerStatus.PRAYED else PrayerStatus.PENDING
    }

    // --- ORIGINAL TEST CASES (STILL VALID) ---

    @Test
    fun `invoke returns 0 when today has missed prayers`() = runTest {
        val today = LocalDate.of(2026, 9, 10)
        val clock = FakeClock(today)
        val repo = FakeRepository(mapOf(today to hasMissed()))
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(0, useCase())
    }

    @Test
    fun `invoke returns 1 when today is in progress but yesterday was fully prayed`() = runTest {
        val today = LocalDate.of(2026, 9, 10)
        val yesterday = today.minusDays(1)
        val clock = FakeClock(today)
        val repo = FakeRepository(
            mapOf(
                today to inProgress(),
                yesterday to allPrayed()
            )
        )
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(1, useCase())
    }

    @Test
    fun `invoke correctly calculates streak across month and year boundaries`() = runTest {
        val today = LocalDate.of(2026, 1, 2)
        val dec31 = LocalDate.of(2025, 12, 31)
        val jan1 = LocalDate.of(2026, 1, 1)

        val clock = FakeClock(today)
        val repo = FakeRepository(
            mapOf(
                today to inProgress(),
                jan1 to allPrayed(),
                dec31 to allPrayed()
            )
        )
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(2, useCase())
    }

    @Test
    fun `invoke stops streak at the first missed day`() = runTest {
        val today = LocalDate.of(2026, 9, 10)
        val clock = FakeClock(today)
        val repo = FakeRepository(
            mapOf(
                today to inProgress(),
                today.minusDays(1) to allPrayed(),
                today.minusDays(2) to allPrayed(),
                today.minusDays(3) to hasMissed(),
                today.minusDays(4) to allPrayed()
            )
        )
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(2, useCase())
    }

    // --- NEW EXTENDED TEST CASES FOR MULTI-YEAR TRACKING & LEAP YEARS ---

    @Test
    fun `invoke correctly calculates multi year streak including leap years`() = runTest {
        // Track across 4 years (2024 was a leap year, containing Feb 29)
        val today = LocalDate.of(2026, 1, 1)
        val clock = FakeClock(today)

        val mutableRecords = mutableMapOf<LocalDate, Map<PrayerName, PrayerStatus>>()

        // Populate a continuous 500-day pristine prayer streak moving backwards
        for (i in 1..500) {
            mutableRecords[today.minusDays(i.toLong())] = allPrayed()
        }
        // Set today as in-progress (should not break the calculation)
        mutableRecords[today] = inProgress()

        val repo = FakeRepository(mutableRecords)
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(500, useCase())
    }

    @Test
    fun `invoke has no lookback cap and counts a streak longer than ten years`() = runTest {
        val today = LocalDate.of(2026, 1, 1)
        val clock = FakeClock(today)
        val days = 3_700

        // Today and the 3,700 days before it, all fully prayed.
        val records = (0..days).associate { today.minusDays(it.toLong()) to allPrayed() }

        val useCase = GetCurrentStreakUseCase(FakeRepository(records), clock)

        assertEquals(days + 1, useCase())
    }
}
```

## app\src\test\java\com\mhq\salati\testutil\FakeClock.kt

```kotlin
package com.mhq.salati.testutil

import com.mhq.salati.shared.domain.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class FakeClock(
    private val fixedToday: LocalDate,
    private val timeOfDay: LocalTime = LocalTime.NOON,
    private val zoneId: ZoneId = ZoneId.of("UTC")
) : Clock {
    override fun now(): LocalDateTime = fixedToday.atTime(timeOfDay)
    override fun today(): LocalDate = fixedToday
    override fun instant(): Instant = now().atZone(zoneId).toInstant()
    override fun zone(): ZoneId = zoneId
}
```

## app\src\test\java\com\mhq\salati\testutil\FakeConnectivityChecker.kt

```kotlin
package com.mhq.salati.testutil

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker

class FakeConnectivityChecker(private val connected: Boolean) : ConnectivityChecker {
    override fun isConnected() = connected
}
```

## app\src\test\java\com\mhq\salati\testutil\FakePermissionChecker.kt

```kotlin
package com.mhq.salati.testutil

import com.mhq.salati.permissions.domain.repo.PermissionChecker

class FakePermissionChecker(private val granted: Boolean) : PermissionChecker {
    override fun hasLocationPermission() = granted
    override fun hasNotificationPermission() = granted
    override fun canScheduleExactAlarms() = granted
}
```

## app\src\test\java\com\mhq\salati\testutil\FakePrayerTimesRepository.kt

```kotlin
package com.mhq.salati.testutil

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class FakePrayerTimesRepository(
    private val cached: Map<String, PrayerTimesResult> = emptyMap(),
    private val network: Map<String, Result<PrayerTimesResult>> = emptyMap(),
    private val networkDelayMillis: Long = 0
) : PrayerTimesRepository {
    override suspend fun getPrayerTimings(
        date: String,
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): Result<PrayerTimesResult> {
        delay(networkDelayMillis.milliseconds)
        return network[date] ?: Result.failure(IllegalStateException("no network fixture for $date"))
    }

    override suspend fun getCachedTimings(
        date: String,
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): PrayerTimesResult? = cached[date]
}
```

## app\src\test\java\com\mhq\salati\testutil\FakeSettingsRepository.kt

```kotlin
package com.mhq.salati.testutil

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSettingsRepository(
    private val settings: AppSettings = AppSettings()
) : SettingsRepository {
    override fun observeSettings(): Flow<AppSettings> = flowOf(settings)
    override suspend fun setNotificationsEnabled(enabled: Boolean) = Unit
    override suspend fun setCalculationMethod(method: CalculationMethod) = Unit
    override suspend fun setMadhab(madhab: Madhab) = Unit
    override suspend fun setThemeMode(mode: ThemeMode) = Unit
    override suspend fun setLanguage(language: AppLanguage) = Unit
    override suspend fun setAdhanSound(sound: AdhanSound) = Unit
    override suspend fun setCustomAlarmSound(sound: CustomAlarmSound) = Unit
    override suspend fun setHijriDateOffset(offset: Int) = Unit
    override suspend fun setTimeFormat(format: TimeFormat) = Unit
}
```

## .gitignore

```
*.iml
.gradle
/local.properties
/.idea/caches
/.idea/libraries
/.idea/modules.xml
/.idea/workspace.xml
/.idea/navEditor.xml
/.idea/assetWizardSettings.xml
.DS_Store
/build
/captures
/extractions/
.externalNativeBuild
.cxx
*.zip
dumpstate-*
```

## AGENTS.md

```markdown
# Salati rules
- Clean Android Architecture with MVI. Five layers per feature: <feature>/{domain,data,datasource,presentation,ui,di}.
- Domain: pure Kotlin, no Android SDK. Presentation: no Android classes, context or resources. UI depends only on Presentation. Data bridges Domain and DataSource.
- One use case per file. Intent names say what happened, not what to do.
- In files you touch: delete commented-out code; one public composable per file (private helpers and previews may stay).
- Work only on the dev branch, one task at a time, build after each change (./gradlew compileDebugKotlin).
- Never commit or push. Ask me before any big design decision.
- For architecture tasks, read docs/architecture.md first. After fixing a listed violation, update that file.
```

## app\.gitignore

```
/build
```

## app\config\detekt\detekt.yml

```yaml
build:
  maxIssues: 0
  excludeCorrectable: false
  weights:
    # complexity: 2
    # LongParameterList: 1
    # style: 1
    # comments: 1

config:
  validation: true
  warningsAsErrors: false
  checkExhaustiveness: false
  # when writing own rules with new properties, exclude the property path e.g.: 'my_rule_set,.*>.*>[my_property]'
  excludes: ''

processors:
  active: true
  exclude:
    - 'DetektProgressListener'
  # - 'KtFileCountProcessor'
  # - 'PackageCountProcessor'
  # - 'ClassCountProcessor'
  # - 'FunctionCountProcessor'
  # - 'PropertyCountProcessor'
  # - 'ProjectComplexityProcessor'
  # - 'ProjectCognitiveComplexityProcessor'
  # - 'ProjectLLOCProcessor'
  # - 'ProjectCLOCProcessor'
  # - 'ProjectLOCProcessor'
  # - 'ProjectSLOCProcessor'
  # - 'LicenseHeaderLoaderExtension'

console-reports:
  active: true
  exclude:
     - 'ProjectStatisticsReport'
     - 'ComplexityReport'
     - 'NotificationReport'
     - 'FindingsReport'
     - 'FileBasedFindingsReport'
  #  - 'LiteFindingsReport'

output-reports:
  active: true
  exclude:
  # - 'TxtOutputReport'
  # - 'XmlOutputReport'
  # - 'HtmlOutputReport'
  # - 'MdOutputReport'
  # - 'SarifOutputReport'

comments:
  active: true
  AbsentOrWrongFileLicense:
    active: false
    licenseTemplateFile: 'license.template'
    licenseTemplateIsRegex: false
  CommentOverPrivateFunction:
    active: false
  CommentOverPrivateProperty:
    active: false
  DeprecatedBlockTag:
    active: false
  EndOfSentenceFormat:
    active: false
    endOfSentenceFormat: '([.?!][ \t\n\r\f<])|([.?!:]$)'
  KDocReferencesNonPublicProperty:
    active: false
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
  OutdatedDocumentation:
    active: false
    matchTypeParameters: true
    matchDeclarationsOrder: true
    allowParamOnConstructorProperties: false
  UndocumentedPublicClass:
    active: false
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    searchInNestedClass: true
    searchInInnerClass: true
    searchInInnerObject: true
    searchInInnerInterface: true
    searchInProtectedClass: false
  UndocumentedPublicFunction:
    active: false
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    searchProtectedFunction: false
  UndocumentedPublicProperty:
    active: false
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    searchProtectedProperty: false

complexity:
  active: true
  CognitiveComplexMethod:
    active: false
    threshold: 15
  ComplexCondition:
    active: true
    threshold: 4
  ComplexInterface:
    active: false
    threshold: 10
    includeStaticDeclarations: false
    includePrivateDeclarations: false
    ignoreOverloaded: false
  CyclomaticComplexMethod:
    active: true
    threshold: 15
    ignoreSingleWhenExpression: false
    ignoreSimpleWhenEntries: false
    ignoreNestingFunctions: false
    ignoreAnnotated: ['Composable']
    nestingFunctions:
      - 'also'
      - 'apply'
      - 'forEach'
      - 'isNotNull'
      - 'ifNull'
      - 'let'
      - 'run'
      - 'use'
      - 'with'
  LabeledExpression:
    active: false
    ignoredLabels: []
  LargeClass:
    active: true
    threshold: 600
  LongMethod:
    active: true
    threshold: 60
    ignoreAnnotated: ['Composable']
  LongParameterList:
    active: true
    functionThreshold: 6
    constructorThreshold: 7
    ignoreDefaultParameters: false
    ignoreDataClasses: true
    ignoreAnnotatedParameter: []
    ignoreAnnotated: ['Composable']
  MethodOverloading:
    active: false
    threshold: 6
  NamedArguments:
    active: false
    threshold: 3
    ignoreArgumentsMatchingNames: false
  NestedBlockDepth:
    active: true
    threshold: 4
  NestedScopeFunctions:
    active: false
    threshold: 1
    functions:
      - 'kotlin.apply'
      - 'kotlin.run'
      - 'kotlin.with'
      - 'kotlin.let'
      - 'kotlin.also'
  ReplaceSafeCallChainWithRun:
    active: false
  StringLiteralDuplication:
    active: false
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    threshold: 3
    ignoreAnnotation: true
    excludeStringsWithLessThan5Characters: true
    ignoreStringsRegex: '$^'
  TooManyFunctions:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    thresholdInFiles: 11
    thresholdInClasses: 11
    thresholdInInterfaces: 11
    thresholdInObjects: 11
    thresholdInEnums: 11
    ignoreDeprecated: false
    ignorePrivate: false
    ignoreOverridden: false
    ignoreAnnotatedFunctions: []

coroutines:
  active: true
  GlobalCoroutineUsage:
    active: false
  InjectDispatcher:
    active: true
    dispatcherNames:
      - 'IO'
      - 'Default'
      - 'Unconfined'
  RedundantSuspendModifier:
    active: true
  SleepInsteadOfDelay:
    active: true
  SuspendFunSwallowedCancellation:
    active: false
  SuspendFunWithCoroutineScopeReceiver:
    active: false
  SuspendFunWithFlowReturnType:
    active: true

empty-blocks:
  active: true
  EmptyCatchBlock:
    active: true
    allowedExceptionNameRegex: '_|(ignore|expected).*'
  EmptyClassBlock:
    active: true
  EmptyDefaultConstructor:
    active: true
  EmptyDoWhileBlock:
    active: true
  EmptyElseBlock:
    active: true
  EmptyFinallyBlock:
    active: true
  EmptyForBlock:
    active: true
  EmptyFunctionBlock:
    active: true
    ignoreOverridden: false
  EmptyIfBlock:
    active: true
  EmptyInitBlock:
    active: true
  EmptyKtFile:
    active: true
  EmptySecondaryConstructor:
    active: true
  EmptyTryBlock:
    active: true
  EmptyWhenBlock:
    active: true
  EmptyWhileBlock:
    active: true

exceptions:
  active: true
  ExceptionRaisedInUnexpectedLocation:
    active: true
    methodNames:
      - 'equals'
      - 'finalize'
      - 'hashCode'
      - 'toString'
  InstanceOfCheckForException:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
  NotImplementedDeclaration:
    active: false
  ObjectExtendsThrowable:
    active: false
  PrintStackTrace:
    active: true
  RethrowCaughtException:
    active: true
  ReturnFromFinally:
    active: true
    ignoreLabeled: false
  SwallowedException:
    active: true
    ignoredExceptionTypes:
      - 'InterruptedException'
      - 'MalformedURLException'
      - 'NumberFormatException'
      - 'ParseException'
    allowedExceptionNameRegex: '_|(ignore|expected).*'
  ThrowingExceptionFromFinally:
    active: true
  ThrowingExceptionInMain:
    active: false
  ThrowingExceptionsWithoutMessageOrCause:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    exceptions:
      - 'ArrayIndexOutOfBoundsException'
      - 'Exception'
      - 'IllegalArgumentException'
      - 'IllegalMonitorStateException'
      - 'IllegalStateException'
      - 'IndexOutOfBoundsException'
      - 'NullPointerException'
      - 'RuntimeException'
      - 'Throwable'
  ThrowingNewInstanceOfSameException:
    active: true
  TooGenericExceptionCaught:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    exceptionNames:
      - 'ArrayIndexOutOfBoundsException'
      - 'Error'
      - 'Exception'
      - 'IllegalMonitorStateException'
      - 'IndexOutOfBoundsException'
      - 'NullPointerException'
      - 'RuntimeException'
      - 'Throwable'
    allowedExceptionNameRegex: '_|(ignore|expected).*'
  TooGenericExceptionThrown:
    active: true
    exceptionNames:
      - 'Error'
      - 'Exception'
      - 'RuntimeException'
      - 'Throwable'

naming:
  active: true
  BooleanPropertyNaming:
    active: false
    allowedPattern: '^(is|has|are)'
  ClassNaming:
    active: true
    classPattern: '[A-Z][a-zA-Z0-9]*'
  ConstructorParameterNaming:
    active: true
    parameterPattern: '[a-z][A-Za-z0-9]*'
    privateParameterPattern: '[a-z][A-Za-z0-9]*'
    excludeClassPattern: '$^'
  EnumNaming:
    active: true
    enumEntryPattern: '[A-Z][_a-zA-Z0-9]*'
  ForbiddenClassName:
    active: false
    forbiddenName: []
  FunctionMaxLength:
    active: false
    maximumFunctionNameLength: 30
  FunctionMinLength:
    active: false
    minimumFunctionNameLength: 3
  FunctionNaming:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    functionPattern: '[a-z][a-zA-Z0-9]*'
    excludeClassPattern: '$^'
    ignoreAnnotated: ['Composable']
  FunctionParameterNaming:
    active: true
    parameterPattern: '[a-z][A-Za-z0-9]*'
    excludeClassPattern: '$^'
  InvalidPackageDeclaration:
    active: true
    rootPackage: ''
    requireRootInDeclaration: false
  LambdaParameterNaming:
    active: false
    parameterPattern: '[a-z][A-Za-z0-9]*|_'
  MatchingDeclarationName:
    active: true
    mustBeFirst: true
  MemberNameEqualsClassName:
    active: true
    ignoreOverridden: true
  NoNameShadowing:
    active: true
  NonBooleanPropertyPrefixedWithIs:
    active: false
  ObjectPropertyNaming:
    active: true
    constantPattern: '[A-Za-z][_A-Za-z0-9]*'
    propertyPattern: '[A-Za-z][_A-Za-z0-9]*'
    privatePropertyPattern: '(_)?[A-Za-z][_A-Za-z0-9]*'
  PackageNaming:
    active: true
    packagePattern: '[a-z]+(\.[a-z][A-Za-z0-9]*)*'
  TopLevelPropertyNaming:
    active: true
    constantPattern: '[A-Z][_A-Z0-9]*'
    propertyPattern: '[A-Za-z][_A-Za-z0-9]*'
    privatePropertyPattern: '_?[A-Za-z][_A-Za-z0-9]*'
  VariableMaxLength:
    active: false
    maximumVariableNameLength: 64
  VariableMinLength:
    active: false
    minimumVariableNameLength: 1
  VariableNaming:
    active: true
    variablePattern: '[a-z][A-Za-z0-9]*'
    privateVariablePattern: '(_)?[a-z][A-Za-z0-9]*'
    excludeClassPattern: '$^'

performance:
  active: true
  ArrayPrimitive:
    active: true
  CouldBeSequence:
    active: false
    threshold: 3
  ForEachOnRange:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
  SpreadOperator:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
  UnnecessaryPartOfBinaryExpression:
    active: false
  UnnecessaryTemporaryInstantiation:
    active: true

potential-bugs:
  active: true
  AvoidReferentialEquality:
    active: true
    forbiddenTypePatterns:
      - 'kotlin.String'
  CastNullableToNonNullableType:
    active: false
  CastToNullableType:
    active: false
  Deprecation:
    active: false
  DontDowncastCollectionTypes:
    active: false
  DoubleMutabilityForCollection:
    active: true
    mutableTypes:
      - 'kotlin.collections.MutableList'
      - 'kotlin.collections.MutableMap'
      - 'kotlin.collections.MutableSet'
      - 'java.util.ArrayList'
      - 'java.util.LinkedHashSet'
      - 'java.util.HashSet'
      - 'java.util.LinkedHashMap'
      - 'java.util.HashMap'
  ElseCaseInsteadOfExhaustiveWhen:
    active: false
    ignoredSubjectTypes: []
  EqualsAlwaysReturnsTrueOrFalse:
    active: true
  EqualsWithHashCodeExist:
    active: true
  ExitOutsideMain:
    active: false
  ExplicitGarbageCollectionCall:
    active: true
  HasPlatformType:
    active: true
  IgnoredReturnValue:
    active: true
    restrictToConfig: true
    returnValueAnnotations:
      - 'CheckResult'
      - '*.CheckResult'
      - 'CheckReturnValue'
      - '*.CheckReturnValue'
    ignoreReturnValueAnnotations:
      - 'CanIgnoreReturnValue'
      - '*.CanIgnoreReturnValue'
    returnValueTypes:
      - 'kotlin.sequences.Sequence'
      - 'kotlinx.coroutines.flow.*Flow'
      - 'java.util.stream.*Stream'
    ignoreFunctionCall: []
  ImplicitDefaultLocale:
    active: true
  ImplicitUnitReturnType:
    active: false
    allowExplicitReturnType: true
  InvalidRange:
    active: true
  IteratorHasNextCallsNextMethod:
    active: true
  IteratorNotThrowingNoSuchElementException:
    active: true
  LateinitUsage:
    active: false
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
    ignoreOnClassesPattern: ''
  MapGetWithNotNullAssertionOperator:
    active: true
  MissingPackageDeclaration:
    active: false
    excludes: ['**/*.kts']
  NullCheckOnMutableProperty:
    active: false
  NullableToStringCall:
    active: false
  PropertyUsedBeforeDeclaration:
    active: false
  UnconditionalJumpStatementInLoop:
    active: false
  UnnecessaryNotNullCheck:
    active: false
  UnnecessaryNotNullOperator:
    active: true
  UnnecessarySafeCall:
    active: true
  UnreachableCatchBlock:
    active: true
  UnreachableCode:
    active: true
  UnsafeCallOnNullableType:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**']
  UnsafeCast:
    active: true
  UnusedUnaryOperator:
    active: true
  UselessPostfixExpression:
    active: true
  WrongEqualsTypeParameter:
    active: true

style:
  active: true
  AlsoCouldBeApply:
    active: false
  BracesOnIfStatements:
    active: false
    singleLine: 'never'
    multiLine: 'always'
  BracesOnWhenStatements:
    active: false
    singleLine: 'necessary'
    multiLine: 'consistent'
  CanBeNonNullable:
    active: false
  CascadingCallWrapping:
    active: false
    includeElvis: true
  ClassOrdering:
    active: false
  CollapsibleIfStatements:
    active: false
  DataClassContainsFunctions:
    active: false
    conversionFunctionPrefix:
      - 'to'
    allowOperators: false
  DataClassShouldBeImmutable:
    active: false
  DestructuringDeclarationWithTooManyEntries:
    active: true
    maxDestructuringEntries: 3
  DoubleNegativeLambda:
    active: false
    negativeFunctions:
      - reason: 'Use `takeIf` instead.'
        value: 'takeUnless'
      - reason: 'Use `all` instead.'
        value: 'none'
    negativeFunctionNameParts:
      - 'not'
      - 'non'
  EqualsNullCall:
    active: true
  EqualsOnSignatureLine:
    active: false
  ExplicitCollectionElementAccessMethod:
    active: false
  ExplicitItLambdaParameter:
    active: true
  ExpressionBodySyntax:
    active: false
    includeLineWrapping: false
  ForbiddenAnnotation:
    active: false
    annotations:
      - reason: 'it is a java annotation. Use `Suppress` instead.'
        value: 'java.lang.SuppressWarnings'
      - reason: 'it is a java annotation. Use `kotlin.Deprecated` instead.'
        value: 'java.lang.Deprecated'
      - reason: 'it is a java annotation. Use `kotlin.annotation.MustBeDocumented` instead.'
        value: 'java.lang.annotation.Documented'
      - reason: 'it is a java annotation. Use `kotlin.annotation.Target` instead.'
        value: 'java.lang.annotation.Target'
      - reason: 'it is a java annotation. Use `kotlin.annotation.Retention` instead.'
        value: 'java.lang.annotation.Retention'
      - reason: 'it is a java annotation. Use `kotlin.annotation.Repeatable` instead.'
        value: 'java.lang.annotation.Repeatable'
      - reason: 'Kotlin does not support @Inherited annotation, see https://youtrack.jetbrains.com/issue/KT-22265'
        value: 'java.lang.annotation.Inherited'
  ForbiddenComment:
    active: true
    comments:
      - reason: 'Forbidden FIXME todo marker in comment, please fix the problem.'
        value: 'FIXME:'
      - reason: 'Forbidden STOPSHIP todo marker in comment, please address the problem before shipping the code.'
        value: 'STOPSHIP:'
      - reason: 'Forbidden TODO todo marker in comment, please do the changes.'
        value: 'TODO:'
    allowedPatterns: ''
  ForbiddenImport:
    active: false
    imports: []
    forbiddenPatterns: ''
  ForbiddenMethodCall:
    active: false
    methods:
      - reason: 'print does not allow you to configure the output stream. Use a logger instead.'
        value: 'kotlin.io.print'
      - reason: 'println does not allow you to configure the output stream. Use a logger instead.'
        value: 'kotlin.io.println'
  ForbiddenSuppress:
    active: false
    rules: []
  ForbiddenVoid:
    active: true
    ignoreOverridden: false
    ignoreUsageInGenerics: false
  FunctionOnlyReturningConstant:
    active: true
    ignoreOverridableFunction: true
    ignoreActualFunction: true
    excludedFunctions: []
  LoopWithTooManyJumpStatements:
    active: true
    maxJumpCount: 1
  MagicNumber:
    active: true
    excludes: ['**/test/**', '**/androidTest/**', '**/commonTest/**', '**/jvmTest/**', '**/androidUnitTest/**', '**/androidInstrumentedTest/**', '**/jsTest/**', '**/iosTest/**', '**/*.kts']
    ignoreAnnotated: ['Composable']
    ignoreNumbers:
      - '-1'
      - '0'
      - '1'
      - '2'
    ignoreHashCodeFunction: true
    ignorePropertyDeclaration: false
    ignoreLocalVariableDeclaration: false
    ignoreConstantDeclaration: true
    ignoreCompanionObjectPropertyDeclaration: true
    ignoreAnnotation: false
    ignoreNamedArgument: true
    ignoreEnums: false
    ignoreRanges: false
    ignoreExtensionFunctions: true
  MandatoryBracesLoops:
    active: false
  MaxChainedCallsOnSameLine:
    active: false
    maxChainedCalls: 5
  MaxLineLength:
    active: true
    maxLineLength: 120
    excludePackageStatements: true
    excludeImportStatements: true
    excludeCommentStatements: false
    excludeRawStrings: true
  MayBeConst:
    active: true
  ModifierOrder:
    active: true
  MultilineLambdaItParameter:
    active: false
  MultilineRawStringIndentation:
    active: false
    indentSize: 4
    trimmingMethods:
      - 'trimIndent'
      - 'trimMargin'
  NestedClassesVisibility:
    active: true
  NewLineAtEndOfFile:
    active: false
  NoTabs:
    active: false
  NullableBooleanCheck:
    active: false
  ObjectLiteralToLambda:
    active: true
  OptionalAbstractKeyword:
    active: true
  OptionalUnit:
    active: false
  PreferToOverPairSyntax:
    active: false
  ProtectedMemberInFinalClass:
    active: true
  RedundantExplicitType:
    active: false
  RedundantHigherOrderMapUsage:
    active: true
  RedundantVisibilityModifierRule:
    active: false
  ReturnCount:
    active: true
    max: 2
    excludedFunctions:
      - 'equals'
    excludeLabeled: false
    excludeReturnFromLambda: true
    excludeGuardClauses: false
  SafeCast:
    active: true
  SerialVersionUIDInSerializableClass:
    active: true
  SpacingBetweenPackageAndImports:
    active: false
  StringShouldBeRawString:
    active: false
    maxEscapedCharacterCount: 2
    ignoredCharacters: []
  ThrowsCount:
    active: true
    max: 2
    excludeGuardClauses: false
  TrailingWhitespace:
    active: false
  TrimMultilineRawString:
    active: false
    trimmingMethods:
      - 'trimIndent'
      - 'trimMargin'
  UnderscoresInNumericLiterals:
    active: false
    acceptableLength: 4
    allowNonStandardGrouping: false
  UnnecessaryAbstractClass:
    active: true
  UnnecessaryAnnotationUseSiteTarget:
    active: false
  UnnecessaryApply:
    active: true
  UnnecessaryBackticks:
    active: false
  UnnecessaryBracesAroundTrailingLambda:
    active: false
  UnnecessaryFilter:
    active: true
  UnnecessaryInheritance:
    active: true
  UnnecessaryInnerClass:
    active: false
  UnnecessaryLet:
    active: false
  UnnecessaryParentheses:
    active: false
    allowForUnclearPrecedence: false
  UntilInsteadOfRangeTo:
    active: false
  UnusedImports:
    active: false
  UnusedParameter:
    active: true
    allowedNames: 'ignored|expected'
  UnusedPrivateClass:
    active: true
  UnusedPrivateMember:
    active: true
    allowedNames: ''
    ignoreAnnotated: ['Preview']
  UnusedPrivateProperty:
    active: true
    allowedNames: '_|ignored|expected|serialVersionUID'
  UseAnyOrNoneInsteadOfFind:
    active: true
  UseArrayLiteralsInAnnotations:
    active: true
  UseCheckNotNull:
    active: true
  UseCheckOrError:
    active: true
  UseDataClass:
    active: false
    allowVars: false
  UseEmptyCounterpart:
    active: false
  UseIfEmptyOrIfBlank:
    active: false
  UseIfInsteadOfWhen:
    active: false
    ignoreWhenContainingVariableDeclaration: false
  UseIsNullOrEmpty:
    active: true
  UseLet:
    active: false
  UseOrEmpty:
    active: true
  UseRequire:
    active: true
  UseRequireNotNull:
    active: true
  UseSumOfInsteadOfFlatMapSize:
    active: false
  UselessCallOnNotNull:
    active: true
  UtilityClassWithPublicConstructor:
    active: true
  VarCouldBeVal:
    active: true
    ignoreLateinitVar: false
  WildcardImport:
    active: true
    excludeImports:
      - 'java.util.*'
```

## docs\architecture.md

```markdown
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
  - Intent names represent events describing what happened (e.g., `RetryRequested`, `AlarmToggled`), never imperative commands describing what to do (e.g., `LoadData`, `SaveAlarm`).
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
```

## scripts\README.md

````markdown
# 🛠️ Codebase Automation & Extraction Utilities

This directory contains local automation scripts designed to enhance Developer Experience (DX), facilitate rapid code audits, and generate structured documentation logs.

These utilities streamline code metrics tracking and structural reviews without manually digging through deep package hierarchies.

---

## 📋 Utilities Catalog

### 1. `extract_composables.ps1`
* **Purpose:** Scans designated directory structures, filters code for Jetpack Compose annotations, isolates UI-layer functions, and handles structural block-brace validation using regex pattern matching.
* **Output:** A unified Markdown document grouping extracted Composable elements by their localized file paths.
* **Usage:**
  ```powershell
  # Default execution (Processes the entire app codebase):
  .\scripts\extract_composables.ps1

  # Custom scoped execution:
  .\scripts\extract_composables.ps1 -SourceRoot "app/src/main/java/com/mhq/salati/home" -OutputFile "extractions/composables_home.md"
  ```

### 2. `extract_kotlin_files.ps1`
* **Purpose:** Recursively sweeps targeted package folders to capture the entire system logic across all architectures. Unlike the structural Composable extractor, this command creates a verbatim extraction capture of top-level constants, initialization objects, tracking states, and underlying backend services.
* **Output:** A complete architectural snapshot packaged into a single readable Markdown source template.
* **Usage:**
  ```powershell
  # Default execution (Dumps your entire architecture file-tree):
  .\scripts\extract_kotlin_files.ps1

  # Custom scoped execution:
  .\scripts\extract_kotlin_files.ps1 -SourceRoot "app/src/main/java/com/mhq/salati/shared" -OutputFile "extractions/shared_infrastructure.md"
  ```

---

## ⚙️ Workflow Integration & Workspace Setup

### 🔍 Repository Cleanliness & Workspace Safety
* **Zero Machine Traaps:** Both scripts resolve file paths dynamically based on relative script paths (`$PSScriptRoot`), making them completely portable across different operating systems.
* **Git Hygiene:** All script outputs are directed to a local, transient directory (`/extractions/`). This destination folder is added to our primary `.gitignore` block to ensure scratchpads do not leak into our permanent GitHub commit history.

### 💻 IDE Configuration (Android Studio / IntelliJ IDEA)
Because Windows script tools (`.ps1`) fall outside the native Android compilation scope, Android Studio filters them out of the default file navigator to keep your development focus tight.

To run, edit, or check these tools inside your IDE:
1. In the top-left tool window, open the view selection dropdown (which usually defaults to **Android**).
2. Change your active workspace configuration layout to **Project** or **Project Files**.
3. Expand the newly organized `/scripts` tree directory to access your terminal toolset.

---
````

