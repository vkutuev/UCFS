# CFPQ-Path-App

This module contains Runnable demos for UCFS.  
For project overview, see [../README.md](../README.md).

## Prerequisites

- Input graphs: `cfpq-paths-app/src/main/resources/`
- Generated SPPF: `cfpq-paths-app/gen/sppf/`

## Running the examples

### Simple examples ($a^nb^n$ grammar)
```bash
./gradlew :cfpq-paths-app:runSimpleExamples
```
### Complex examples (PointsTo analysis)
```bash
./gradlew :cfpq-paths-app:run
```
