# Introduction to context-free languages

UCFS solves path queries on edge-labeled directed graphs: the result consists of paths whose concatenated edge labels
form a word in a language $L$ specified by a grammar. In UCFS, $L$ is given by a context-free grammar
(CFG), and the corresponding language is a context-free language (CFL). For more information about CFG/L, see
сhapter 5 of the [book](https://github.com/FormalLanguageConstrainedPathQuerying/FormalLanguageConstrainedReachability-LectureNotes)

The graph defines the set of candidate paths and the grammar restricts it to paths whose label sequences belong to $L$.

## Context-free grammar

A context-free grammar is a finite set of productions of the form $A \to a$, where $A$ is a non-terminal and $a$ is a
string of terminals and non-terminals.

Notation used below:

```
A -> a
```

* **Non-terminal** — a symbol on the left-hand side of a production, expanded by applying rules.
* **Terminal** — a symbol that does not expanded and corresponds to the edge labels in UCFS

**Example.** Grammar for $L = \{a^n b^n \mid n \ge 0\}$:

```
S -> a S b
S -> ε
```

Here $S$ is a non-terminal, $a$ and $b$ are terminals, $\varepsilon$ denotes the empty string. A path matches this
grammar if its label sequence is $ab$, $aabb$, $aaabbb$, etc. The simple examples in this guide use this grammar.
