# Writing User Documentation

This guide describes conventions for writing user-facing documentation in `docs/docs/`.

## Purpose

User documentation helps external users understand and use UCFS without reading source code. It covers:
- What UCFS is and what problems it solves
- How to define grammars, graphs, and run queries
- How to interpret results (SPPF)
- Worked examples with real input/output

## Prerequisites

- Read existing docs in `docs/docs/` to understand current style.
- Familiarity with MkDocs and Material theme.

## Structure

```
docs/
├── mkdocs.yml          # Site configuration and navigation
├── run.sh              # Local dev server: `bash docs/run.sh`
└── docs/
    ├── index.md        # Landing page — overview, quick start, installation
    ├── dsl.md          # Grammar DSL reference
    ├── graphs.md       # Graph input formats
    ├── sppf.md         # SPPF node types and reading results
    ├── usage-examples.md  # Worked examples with diagrams
    └── assets/         # SVG diagrams and images
```

## Conventions

### Markdown

- **Headings:** ATX style (`#`, `##`, `###`), one space after `#`.
- **Lists:** Use `*` for unordered lists, consistent 2-space indentation.
- **Code blocks:** Always specify language (`kotlin`, `bash`, `dot`).
- **Inline code:** Backtick-wrapped for identifiers, flags, file paths.
- **Math:** Use `$...$` for inline math and `$$...$$` for display math (MathJax via arithmatex).
- **Admonitions:** GitHub/Flavored syntax:
  ```markdown
  > [!NOTE]
  > Important information.
  ```

### Cross-references

- Internal links use relative paths: `[link text](./other-file.md)`
- External links use full URLs with descriptive text, not bare URLs.

### Diagrams

- **Format:** SVG only, placed in `docs/docs/assets/`.
- **Naming:** Descriptive, snake_case, with context (e.g., `example_1_graph_sppf.dot.svg`).
- **Embedding:** Reference from markdown with `![Alt text](assets/filename.svg)`.
- **Alt text:** Brief description of what the diagram shows.

### Paths

Represent paths using TeX `\xrightarrow{}` notation, **not** ASCII edge lists.

**Correct:**
```markdown
$0 \xrightarrow{a} 1 \xrightarrow{b} 2$
$1 \xrightarrow{\text{Alias}} 2 \xrightarrow{\text{store}_0} 3 \xrightarrow{\text{PointsTo}} 4$
```

**Incorrect:**
```markdown
(0 -a-> 1 -b-> 2)
[(1-Alias->2), (2-store_0->3), (3-PointsTo->4)]
```

Rules:
- Simple labels: `\xrightarrow{a}`
- Multi-word labels: `\xrightarrow{\text{PointsTo}}`
- Labels with subscripts: `\xrightarrow{\text{store}_0}`

### Code Examples

- Show complete, runnable snippets when possible.
- Include the file path where the example lives in the codebase:
  ```markdown
  Set the grammar in
  ```cfpq-paths-app/src/main/kotlin/org.ucfs.paths/examples/example_1.kt```
  ```
- For terminal commands, always specify the working directory:
  ```markdown
  **To run (from project root):**
  ```bash
  ./gradlew :cfpq-paths-app:runSimpleExamples
  ```
  ```

### Tone and Style

- **Direct:** Address the reader with imperative instructions ("Define the grammar", "Run the query").
- **Concise:** One idea per paragraph. Avoid filler sentences.
- **Mathematical notation:** Use LaTeX for formal definitions ($L$, $\varepsilon$, $A \to a$).
- **Structured over ASCII:** Prefer tables, lists, and Mermaid diagrams over text-based schemas.

### Navigation

Update `mkdocs.yml` when adding new pages:

```yaml
nav:
  - Home: index.md
  - DSL: dsl.md
  - Graphs: graphs.md
  - SPPF: sppf.md
  - Examples: usage-examples.md
  - New Page: new-page.md
```

## Workflow

1. Write content in `docs/docs/<file>.md`.
2. Place assets in `docs/docs/assets/`.
3. Update `mkdocs.yml` navigation if adding a new page.
4. Preview locally:
   ```bash
   bash docs/run.sh
   ```
5. Verify all links resolve and images render.

## Anti-patterns

- **Don't** duplicate content between pages — use cross-references instead.
- **Don't** embed large ASCII diagrams — use SVG or Mermaid.
- **Don't** reference internal API without explaining what it does for the user.
- **Don't** use ASCII edge lists like `[(v-label->u)]` — always use TeX `$\xrightarrow{}$`.
- **Don't** omit the working directory for shell commands.
