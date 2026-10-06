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
