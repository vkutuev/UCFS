# Using UCFS with simple examples

Set the grammar in
```cfpq-paths-app/src/main/kotlin/org.ucfs.paths/examples/example_1.kt```

**Grammar assignment**

Define the grammar for the language $a^n b^n$: words consisting of $n$ occurrences of $a$ followed by $n$
occurrences of $b$ (e.g., $ab$, $aabb$, $aaabbb$).
> [!NOTE]
> The grammar can be defined equivalently as follows.
>```kotlin
>class AnBnGrammar : Grammar() {
>    val S by Nt().asStart()
>
>    init {
>        S /= "a" * Option(S) * "b"
>    }
>}
>```
>or
>```kotlin
>class AnBnGrammar : Grammar() {
>    val S by Nt().asStart()
>
>    init {
>        S /= "a" * (Epsilon or S) * "b"
>    }
>}
>```

A **recursive state machine** ([RSM](https://www.researchgate.net/publication/226965977_Analysis_of_Recursive_State_Machines)) is an automaton-like representation of a context-free grammar.

The RSM for the $a^n b^n$ grammar:

![RSM for AnBn Grammar](../src/main/resources/figures/AnBnGrammarRsm.dot.svg)

The start non-terminal $S$ expands to either the terminal string $ab$ or the string $aSb$.

> [!NOTE]
> To confirm this, look at the labels along the edges of the path from the source node (green circle) to the sink node
> (red circle).

**To run (from project root):**

```bash
./gradlew :cfpq-paths-app:runSimpleExamples 
```

**Example 1: Simple graph with a <ins>finite</ins> set of paths**

**Input graph:**

![Simple AnBn Graph / Finite Set Of Paths](../src/main/resources/figures/example_1_graph.dot.svg)

The following words satisfy the grammar:

* $ab$ (0 -a-> 1 -b-> 2)
* $aabb$ (0 -a-> 1 -a-> 2 -b-> 1 -b-> 2)

**Resulting SPPF graph:**

![Simple AnBn Graph / Finite Set Of Paths / SPPF](../src/main/resources/figures/example_1_graph_sppf.dot.svg)

The SPPF decomposes into two trees:

**The *first* tree:**

![Simple AnBn Graph / Finite Set of Paths / SPPF / 1Tree](../src/main/resources/figures/example_1_graph_sppf_1tree.dot.svg)

The first tree corresponds to the word $ab$.

**The *second* tree:**

![Simple AnBn Graph / Finite Set of Paths / SPPF / 2Tree](../src/main/resources/figures/example_1_graph_sppf_2tree.dot.svg)

The second tree corresponds to the word $aabb$.

The result matches the expected language.

**Example 2: Simple graph with an <ins>infinite</ins> number of paths #1**

**Input graph:**

![Simple AnBn Graph / Infinite Set Of Paths #1](../src/main/resources/figures/example_2_graph.dot.svg)

Examples of words that satisfy the grammar:

* $ab$ (0 -a-> 1 -b-> 1)
* $aaabbb$ (0 -a-> 1 -a-> 0 -a-> 1 -b-> 1 -b-> 1 -b-> 1)
* ...

> [!NOTE]
> The language contains infinitely many words of the form $a^nb^n$ where $n$ is odd.

**Resulting SPPF graph:**

![Simple AnBn Graph / Infinite Set Of Paths #1 / SPPF](../src/main/resources/figures/example_2_graph_sppf.dot.svg)

> [!NOTE]
> This example shows that although the number of paths is infinite, the SPPF remains finite when a depth limit
> is applied.

The SPPF decomposes into two trees:

**The *first* tree:**

![Simple AnBn Graph / Infinite Set of Paths / SPPF / 1Tree](../src/main/resources/figures/example_2_graph_sppf_1tree.dot.svg)

The first tree corresponds to the word $ab$.

**The *second* tree:**

![Simple AnBn Graph / Infinite Set of Paths / SPPF / 2Tree](../src/main/resources/figures/example_2_graph_sppf_2tree.dot.svg)

The second tree corresponds to the word $aaSbb$.

> [!NOTE]
> The SPPF contains a cycle. The label sequence $aaSbb$ includes the non-terminal $S$, which can be expanded further
> to obtain $aaabbb$, $aaaaabbbbb$, and so on.

The result matches the expected language.

**Example 3: Simple graph with an <ins>infinite</ins> set of paths #2**

**Input graph:**

![Simple AnBn Graph / Infinite Set Of Paths #2](../src/main/resources/figures/example_3_graph.dot.svg)

Examples of words that satisfy the grammar:

* $ab$ (1 -a-> 2 -b-> 3)
* $aabb$ (0 -a-> 1 -a-> 2 -b-> 3 -b-> 2)
* $aaabbb$ (2 -a-> 0 -a-> 1 -a-> 2 -b-> 3 -b-> 2 -b-> 3)
* ...

> [!NOTE]
> The graph yields infinitely many words that cover the full language, because it has several start vertices.

The resulting SPPF is too large to include here; see
```src/main/resources/figures/example_3_graph_sppf.dot.svg```
