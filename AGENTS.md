# Salati rules
- Clean Android Architecture with MVI. Five layers per feature: <feature>/{domain,data,datasource,presentation,ui,di}.
- Domain: pure Kotlin, no Android SDK. Presentation: no Android classes, context or resources. UI depends only on Presentation. Data bridges Domain and DataSource.
- One use case per file. Intent names say what happened, not what to do.
- In files you touch: delete commented-out code; one public composable per file (private helpers and previews may stay).
- Work only on the dev branch, one task at a time, build after each change (./gradlew compileDebugKotlin).
- Never commit or push. Ask me before any big design decision.
- For architecture tasks, read docs/architecture.md first. After fixing a listed violation, update that file.
