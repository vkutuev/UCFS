# Defining the grammar

UCFS uses a [DSL](https://formallanguageconstrainedpathquerying.github.io/UCFS/dsl/) (domain-specific language) to define context-free grammars.

Basic example structure:

```kotlin
class MyGrammar : Grammar() {
    val S by Nt().asStart()

    init {
        S /= "a" * "b"
    }
}
```

This defines the language $\{ab\}$ (a single $a$ followed by a single $b$).
