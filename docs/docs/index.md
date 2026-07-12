# UCFS

> Note: project under heavy development!

Please, see [documentation](https://formallanguageconstrainedpathquerying.github.io/UCFS/) for details.

## What is UCFS?

UCFS is an **U**niversal **C**ontext-**F**ree **S**olver: a GLL‑based tool for problems at the intersection of
context‑free languages
over edge‑labeled directed graphs. Examples of such problems:

- Parsing
- Context-free path querying (CFPQ)
- Context-free language reachability (CFL-R)
- static code analysis

**Highlights**

* Kotlin implementation with a concise Grammar DSL (EBNF‑friendly).
* Input: arbitrary edge‑labeled directed graphs.
* Output: SPPF -- finite structure for all‑paths queries.

### Typical workflow

1) **Describe grammar** in Kotlin DSL.
2) **Load graph** (for now `dot` format is supported).
3) **Run query**.
4) **Inspect results**.

## Quick start
Find all paths in the graph that satisfy the grammar:
1) Describe grammar
```kotlin
class AnBnGrammar : Grammar() {
    val S by Nt().asStart()

    init {
        S /= "a" * Option(S) * "b"
    }
}
```
2) Load the graph

![example_1_graph.dot](cfpq-paths-app/src/main/resources/figures/example_1_graph.dot.svg)
```kotlin
fun main() {
    listOf("example_1_graph.dot").forEach { graphName ->
        val graph = readGraph(graphName)
        val grammar = AnBnGrammar()
        val gll = Gll.gll(grammar.rsm, graph)
        val sppf = gll.parse()
        saveSppf(graphName, sppf)
    }
}
```
3) Inspect results from SPPF
   ![Results as SPPF](cfpq-paths-app/src/main/resources/figures/example_1_graph_sppf.dot.svg)
   SPPF contains a compressed representation of set of paths:

![Path 1 Extracted From SPPF](cfpq-paths-app/src/main/resources/figures/example_1_graph_sppf_1extraction.dot.svg)
![Path 2 Extracted From SPPF](cfpq-paths-app/src/main/resources/figures/example_1_graph_sppf_2extraction.dot.svg)

> [!NOTE]
> At this stage of using UCFS, we get compressed representation of the entire set of extractable paths, and if you need to work with them further, you can add custom
> functions for your task. You can see an example of this [here](./usage-examples.md)
## Core Algorithm

UCFS is based on Generalized LL (GLL) parsing algorithm modified to handle language specification in form of Recursive
State Machines (RSM) and input in form of arbitratry directed edge-labelled graph. Basic ideas
described [here](https://arxiv.org/pdf/2312.11925.pdf).

### Repository layout (high‑level)

```
docs/              # Documentation pages  
generator/         # Parser & AST node‑class generator <in progress>
solver/            # Core UCFS logic (GLL + RSM)
test-shared/       # Testcases, grammars, inputs, ANTLR4 comparison
                   # grammar examples and experiments
cfpq-paths-app/    # Runnable demos and tutorial
```

### Requirements

- JDK 11+ (toolchain targets 11).
- Gradle Wrapper included (`./gradlew`).

### Installation:

* To download the project by **https** enter

```bash
git clone https://github.com/FormalLanguageConstrainedPathQuerying/UCFS.git
```

* To download the project by **ssh** enter:

```bash
git clone git@github.com:FormalLanguageConstrainedPathQuerying/UCFS.git
```
