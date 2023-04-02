<h2>G1GC</h2>

| HeapSize | TotalTime, ms | TotalGarbageCollections | TotalGarbageCollectionsTime ||
|----------|---------------|-------------------------|-----------------------------|-------|
| 256m |||| java.lang.OutOfMemoryError: Java heap space|
| 300m | 13530 | 489 | 2773 ||
| 500m | 12204 | 132 | 1931 ||
| **800m**| 11479 | 63 | 1641 ||
| 1g | 11415 | 48 | 1513 ||
| 2g | 11050 | 23 | 919 ||
| 4g | 11575 | 18 | 748 ||
| 8g | 12840 | 15 | 804 ||

Оптимальный размер хипа 800Мб

*after change to primitivies:*

| HeapSize | TotalTime, ms | TotalGarbageCollections | TotalGarbageCollectionsTime ||
|----------|---------------|-------------------------|-----------------------------|-------|
| 256m | 2498 | 77 | 686 ||
| 500m | 2156 | 22 | 353 ||
| **800m** | 2073 | 10 | 190 ||
| 1g| 2139 | 10 | 219 ||
| 2g| 2270 | 8 | 267 ||


<h2>SerialGC</h2>

| HeapSize | TotalTime, ms | TotalGarbageCollections | TotalGarbageCollectionsTime ||
|----------|---------------|-------------------------|-----------------------------|-------|
| 256m |||| java.lang.OutOfMemoryError: Java heap space|
| 300m | 30410 | 215 | 24129||
| 2g | 10558 | 29 | 6930||
| 8g | 5968 | 7 | 1647||

*after change to primitivies:*

| HeapSize | TotalTime, ms | TotalGarbageCollections | TotalGarbageCollectionsTime ||
|----------|---------------|-------------------------|-----------------------------|-------|
| 256m | 3399 | 33 | 2180 ||
| 500m | 2470 | 13 | 1206 ||
| 1g| 1832 | 6 | 524 ||
| 2g| 1752 | 3 | 317 ||


<h2>ParallelGC</h2>

| HeapSize | TotalTime, ms | TotalGarbageCollections | TotalGarbageCollectionsTime ||
|----------|---------------|-------------------------|-----------------------------|-------|
| 256m |||| java.lang.OutOfMemoryError: GC overhead limit exceeded|
| 300m |||| java.lang.OutOfMemoryError: Java heap space|
| 400m | 30032 | 210 | 26565|
| 2g | 14960 | 44 | 11358|
| 8g | 5170 | 7 | 754|

*after change to primitivies:*

| HeapSize | TotalTime, ms | TotalGarbageCollections | TotalGarbageCollectionsTime ||
|----------|---------------|-------------------------|-----------------------------|-------|
| 256m | 10523 | 62 | 9261 ||
| 500m | 5077 | 25 | 3821 ||
| 800m | 2439 | 11 | 1144 ||
| 900m | 1821 | 9 | 518 ||
| 1g| 1789 | 7 | 463 ||
| 2g| 1649 | 3 | 219 ||
