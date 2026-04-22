> [!IMPORTANT]
> This file is a tutorial and demonstration of how to use the UCFS tool.

**UCFS** is a universal tool designed to solve problems that lie at the intersection of context-free languages and
labeled directed graphs. It is based on the **GLL** algorithm.

**Generalized LL parsing (GLL)** is a parsing technique that extends traditional LL parsing to handle any context-free
grammar.  
The name *LL* itself stands for

* Left-to-right scanning of the input
* Leftmost derivation (the parse tree is constructed by expanding the leftmost nonterminal first)

> [!TIP]
> You can read more about GLL by following
> the [link](https://link.springer.com/chapter/10.1007/978-3-662-46663-6_5).

**To run (from project root):**

```bash
./gradlew :cfpq-paths-app:runSimpleExamples 
```

**Code for path extraction:** ```src/main/kotlin/simple_examples/example_1.kt```


> [!TIP]
> You can read about how to define a context-free grammar using DSL, including what a terminal and a non-terminal are,
> as well as about operations, by following
> the [link](https://formallanguageconstrainedpathquerying.github.io/UCFS/dsl/).

## $a^nb^n$ Language

**Grammar assignment**

Let's define the grammar of the language $a^n b^n$, which defines a set of words that start with $n$ letters $a$ and end
with $n$ letters $b$ (Examples: $ab$, $aabb$, $aaabbb$)
> [!NOTE]
> Please note that we can do this in several ways.
>```kotlin
>class PointsToAnBnGrammar : Grammar() {
>    val S by Nt().asStart()
>
>    init {
>        S /= "a" * Option(S) * "b"
>    }
>}
>```
>or
>```kotlin
>class PointsToAnBnGrammar : Grammar() {
>    val S by Nt().asStart()
>
>    init {
>        S /= "a" * (Epsilon or S) * "b"
>    }
>}
>```

Let's construct an RSM for the $a^n b^n$ grammar:

![RSM for AnBn Grammar](figures/PointsToAnBnGrammarRsm.dot.svg)

We can see how the starting non-terminal $S$ turns into either a concatenation of terminals $ab$ or a concatenation of a
non-terminal and terminals $aSb$

> [!NOTE]
> To confirm this, look at the labels along the edges of the path from the source node (green circle) to the sink node
> (red circle).

**Example 1: Simple graph with a <ins>finite</ins> set of paths**

**Input graph:**

![Simple AnBn Graph / Finite Set Of Path](figures/example_1_graph.dot.svg)

Let's find *all* words that satisfy the language's grammar:

* $ab$ (0 -a-> 1 -b-> 2)
* $aabb$ (0 -a-> 1 -a-> 2 -b-> 1 -b-> 2)

**Resulting SPPF graph:**

![Simple AnBn Graph / Finite Set Of Path / SPPF](figures/example_1_graph_sppf.dot.svg)

Let's check our hypothesis. We use DFS to search for terminals. Start from number 0 and follow the next
fork. Fork with number 1 gives us two different words. Follow the path 1-2-4-8 and reach the $a$ terminal. Return to
fork number 2 and follow another path 2-5-9 to reach the $b$ terminal. **This way, we get the first word $ab$ as
expected.**

We do the same for other paths: 1-3-6-10-4-8 to reach the $a$ terminal, then return to fork with number 10 and follow
the path 10-11-12-13-14-15-17 to reach the $a$ terminal, then return to fork number 14 and follow the path 14-16-18 to
reach the $b$ terminal, finally return to fork number 3 and follow the path 3-7-9 to reach the $b$ terminal. **This way,
we get the second word $aabb$ as expected.**

**Example 2: Simple graph with an <ins>infinite</ins> number of paths #1**

**Input graph:**

![Simple AnBn Graph / Infinite Set Of Path #1](figures/example_2_graph.dot.svg)

Let's find *some* words that satisfy the language's grammar:

* $ab$ (0 -a-> 1 -b-> 1)
* $aaabbb$ (0 -a-> 1 -a-> 0 -a-> 1 -b-> 1 -b-> 1 -b-> 1)
* ...

> [!NOTE]
> We get an infinite number of words that obey the rule: $a^nb^n$ & $n$ is odd

**Resulting SPPF graph:**

![Simple AnBn Graph / Infinite Set Of Path #1/ SPPF](figures/example_2_graph_sppf.dot.svg)

> [!NOTE]
> This example demonstrates that despite the infinite number of paths, the graph will be finite, as a limit is provided.

Let's check our hypothesis. We use DFS to search for terminals. Start from number 0 and follow the next
fork. Fork with number 1 gives us two different words. Follow the path 1-2-4-8 and reach the $a$ terminal. Return to
fork number 2 and follow another path 2-5-9 to reach the $b$ terminal. **This way, we get the first word $ab$ as
expected.**

We do the same for other paths: 1-3-6-10-4-8 to reach the $a$ terminal, then return to fork with number 10 and follow
the path 10-11-12-13-14-15-16-17-19 to reach the $a$ terminal, then return to fork number 16 and follow the path
16-18-20 to
reach the $S$ non-terminal, then return to fork number 14 and follow the path 14-7-9 to
reach the $b$ terminal, finally return to fork number 3 and follow the path 3-7-9 to reach the $b$ terminal. **This way,
we get the word $aaSbb$ as second word.**

> [!NOTE]
> We can see that there is a cycle in the graph. We can also see that the second word contains a non-terminal, which we
> can also replace with $ab$ or $aaSbb$, so we can get the words $aaabbb$, $aaaaSbbbb$ -> $aaaaabbbbb$ etc. as we
> expected.

**Example 3: Simple graph with an <ins>infinite</ins> set of paths #2**

**Input graph:**

![Simple AnBn Graph / Iinite Set Of Path #2](figures/example_3_graph.dot.svg)

Let's find *some* words that satisfy the language's grammar:

* $ab$ (1 -a-> 2 -b-> 3)
* $aabb$ (0 -a-> 1 -a-> 2 -b-> 3 -b-> 2)
* $aaabbb$ (2 -a-> 0 -a-> 1 -a-> 2 -b-> 3 -b-> 2 -b-> 3)
* ...

> [!NOTE]
> We get an infinite number of words. Words cover the entire language thanks to several starting points.

**Resulting SPPF graph** is too big, you can find it in
```src/main/kotlin/simple_examples/figures/example_3_graph_sppf.dot.svg```

*Default location of all generated SPPFs:* ```src/main/kotlin/simple_examples/gen```
