# CFPQ-Path-App

This module contains Runnable demos for UCFS.  
For project overview, see [../README.md](../README.md).
<!-- Сюда можно будет добавить ссылку на весь туториал 
(если Вы увидели данный комментарий раньше, чем прочитали комментарий к коммитам, то Вам следует вернуться 
к комментарию, там я дал пояснения) -->

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
