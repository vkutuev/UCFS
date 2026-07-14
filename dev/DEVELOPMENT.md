# Development Guide

## Prerequisites

- **JDK 11+** (Gradle toolchain will manage version)
- **Git**
- No local Gradle installation required — use the included wrapper

## Initial Setup

```bash
git clone https://github.com/FormalLanguageConstrainedPathQuerying/UCFS.git
cd UCFS
```

## Build Commands

### Build

```bash
./gradlew build
```

Compiles all modules and runs tests.

### Test

```bash
./gradlew test
```

Runs all unit tests across all modules.

### Coverage

```bash
./gradlew testCodeCoverageReport
```

Generates JaCoCo coverage report. Output:
- HTML: `build/reports/jacoco/testCodeCoverageReport/index.html`
- XML: `build/reports/jacoco/testCodeCoverageReport/testCodeCoverageReport.xml`

### Verify Coverage Thresholds

```bash
./gradlew jacocoTestCoverageVerification
```

Checks that coverage meets minimum thresholds (95% instruction, 80% branch/line, 85% method, 90% class).

## Running Examples

The `cfpq-paths-app` module contains runnable examples:

```bash
./gradlew :cfpq-paths-app:runSimpleExamples
```

Or run the main application:

```bash
./gradlew :cfpq-paths-app:run
```

## Module-Specific Commands

```bash
./gradlew :solver:test          # Tests for solver module only
./gradlew :generator:test       # Tests for generator module only
./gradlew :test-shared:test     # Tests for test-shared module only
```

## IDE Setup

### IntelliJ IDEA

1. Open the project root directory
2. IntelliJ will detect Gradle project automatically
3. Ensure JDK 11+ is selected in Project Structure → SDKs

## Writing Tests

Tests use JUnit 5. Place tests in `src/test/kotlin/` following the same package structure as main source.

Example test structure:
```kotlin
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MyTest {
    @Test
    fun `test description`() {
        // test body
    }
}
```

## Publishing

Jars are published to GitHub Packages via CI workflow. Local publishing:

```bash
./gradlew publishToMavenLocal
```

Requires `GITHUB_ACTOR` and `GitHub_TOKEN` environment variables for remote publishing.

## Documentation

User documentation is built with MkDocs.

### Setup

1. Navigate to the `docs/` directory:
   ```bash
   cd docs
   ```

2. Create a virtual environment:
   ```bash
   python3 -m venv venv
   ```

3. Activate the virtual environment:
   ```bash
   source venv/bin/activate
   ```

4. Install MkDocs and required dependencies:
   ```bash
   pip install mkdocs mkdocs-material pymdown-extensions
   ```

### Serve Locally

```bash
./run.sh
```

Or manually:
```bash
mkdocs serve --livereload
```

### Build Site

```bash
mkdocs build
```

Output is placed in `docs/site/`.
