# Contributing to UCFS

Thank you for your interest in contributing! This document provides guidelines for contributing to the project.

## Getting Started

1. Fork the repository
2. Clone your fork locally
3. Set up the development environment (see `dev/DEVELOPMENT.md`)
4. Create a feature branch from `main`

## Development Workflow

### Before You Start

- Check existing issues and pull requests to avoid duplicate work
- For significant changes, open an issue to discuss the approach first

### Making Changes

1. Create a descriptive branch name: `feature/description`, `fix/description`, `docs/description`
2. Make focused, logical commits
3. Follow the coding conventions (see below)
4. Ensure all tests pass and coverage thresholds are met
5. Update documentation if your change affects public API or user-facing behavior

### Commit Messages

Use conventional commit format:

```
type(scope): short description

Optional body with more details.
```

**Types:** `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

**Examples:**
- `feat(solver): add support for epsilon transitions`
- `fix(gss): handle cycle detection in GSS pop`
- `docs: update grammar DSL examples`

### Pull Requests

1. Update `CHANGELOG.md` with your change under the "Unreleased" section
2. Ensure CI passes (tests + coverage verification)
3. Provide a clear description of what and why
4. Reference related issues if applicable

## Coding Conventions

- **Kotlin style:** Official style (`kotlin.code.style=official`)
- **Package naming:** `org.ucfs.<subpackage>` for all modules
- **Class naming:** PascalCase
- **Function/property naming:** camelCase
- **Test naming:** Backtick strings with descriptive names: `` `should do something` ``

## Code Quality Requirements

All changes must meet these coverage thresholds:

| Counter | Minimum |
|---------|---------|
| Instruction | 95% |
| Branch | 80% |
| Line | 80% |
| Method | 85% |
| Class | 90% |

Run locally before pushing:
```bash
./gradlew testCodeCoverageReport jacocoTestCoverageVerification
```

## Project Structure

- `solver/` — Core parsing library
- `generator/` — Code generation utilities
- `test-shared/` — Shared test infrastructure
- `cfpq-paths-app/` — Demo application
- `docs/` — User documentation (MkDocs)
- `dev/` — Developer documentation

See `dev/ARCHITECTURE.md` for detailed architecture overview.

## Questions?

If you have questions, open an issue with the "question" label.
