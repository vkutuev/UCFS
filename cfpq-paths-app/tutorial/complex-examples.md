# Complex examples (PointsTo analysis)

The following examples illustrate UCFS on analysis-style graphs.

**Grammar and code for paths extraction:** ```cfpq-paths-app/src/main/kotlin/org.ucfs.paths/Main.kt```

**To run (from project root)**:

```bash
./gradlew :cfpq-paths-app:run
```

> [!NOTE]
> Path extraction uses a naive traversal algorithm intended only to demonstrate SPPF traversal.

Below are code snippets, input graphs, fragments of the resulting SPPFs, and extracted paths.

The analysis uses the following extended points-to grammar (start non-terminal ```S```) to model field-access chains.

```
PointsTo -> ("assign" | ("load_i" Alias "store_i"))* "alloc"
FlowsTo -> "alloc_r" ("assign_r" | ("store_i_r" Alias "load_o_r"))*
Alias -> PointsTo FlowsTo
S -> (Alias? "store_i")* PointsTo
```

In all examples below, the grammar uses indices $i \in [0..3]$.
The corresponding RSM:

![Graph for example 1](../src/main/resources/figures/rsm.dot.svg)

## Example 1

Code snippet:

```java
val n = new X()
val y = new Y()
val z = new Z()
val l = n
val t = y
l.u = y
t.v = z
```

Corresponding graph:

![Graph for example 1](../src/main/resources/figures/graph_1.dot.svg)

The resulting SPPF:

![SPPF for example 1](../src/main/resources/figures/graph_1_sppf.dot.svg)

Three trees are extracted because there are three paths of interest from node 1.
Subpaths derivable from non-terminals ```Alias``` and ```PointsTo``` are omitted, because they do not contribute
to recovering field assignments.

Extracted paths:

* [(1-PointsTo->0)]

  This path is trivial. Such paths will be omitted in further examples.

* [(1-Alias->2), (2-store_0->3), (3-PointsTo->4)]

  This path corresponds to ```n.u = new Y()```. Vertex 2 is an alias of 1 (```n```); the ```store_0``` edge models ```l.u = y```.

* [(1-Alias->2), (2-store_0->3), (3-Alias->5), (5-store_1->6), (6-PointsTo->7)]

  This path corresponds to ```n.u.v = new Z()```.

## Example 2

Code snippet:

```java
val n = new X()
val l = n
while (...){    
    l.next = new X()
    l = l.next
}
```

Corresponding graph:

![Graph for example 2](../src/main/resources/figures/graph_2.dot.svg)

Part of the resulting SPPF:

![SPPF for example 2](../src/main/resources/figures/graph_2_sppf.dot.svg)

This fragment contains a cycle on vertices 27–31–34–37–38–40–42–44–47–49–52–56 (shown in red), indicating infinitely
many paths of interest. A sample of extracted paths:

* [(0-Alias->2), (2-store_0->3), (3-PointsTo->4)]

  ```n.next = new X () // line 4```

* [(0-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-PointsTo->4)]

  ```n.next.next = new X () // line 4```

* [(0-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-PointsTo->4)]

  ```n.next.next.next = new X () // line 4```

* [(0-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-PointsTo->4)]

  ```n.next.next.next.next = new X () // line 4```

* [(0-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-PointsTo->4)]

  ```n.next.next.next.next.next = new X () // line 4```

* [(0-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-Alias->2), (2-store_0->3), (3-PointsTo->4)]

  ```n.next.next.next.next.next.next = new X () // line 4```

More paths can be extracted if needed. Traversal should be tuned accordingly.

## Example 3

Code snippet:

```java
val n = new X()
val l = n
while (...){
    val t = new X()
    l.next = t
    l = t
}
```

Corresponding graph:

![Graph for example 3](../src/main/resources/figures/graph_3.dot.svg)

Part of the resulting SPPF:

![SPPF for example 3](../src/main/resources/figures/graph_3_sppf.dot.svg)

This SPPF also contains a cycle on vertices 3–5–7–11; therefore, infinitely many paths of interest exist. Only a sample
is listed below.

* [(0-Alias->1), (1-store_0->2), (2-PointsTo->3)]

  ```n.next = new X() // line 4```

* [(0-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-PointsTo->3)]

  ```n.next.next = new X() // line 4```

* [(0-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-PointsTo->3)]

  ```n.next.next.next = new X() // line 4```

* [(0-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-PointsTo->3)]

  ```n.next.next.next.next = new X() // line 4```

* [(0-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-PointsTo->3)]

  ```n.next.next.next.next.next = new X() // line 4```

* [(0-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-Alias->1), (1-store_0->2), (2-PointsTo->3)]

  ```n.next.next.next.next.next.next = new X() // line 4```

## Example 4

Code snippet:

```java
val n = new X()
val z = new Z()
val u = new U()
z.x = n
u.y = n
val v = z.x
v.p = new Y()
val r = u.y
r.q = new P()
```

Corresponding graph:

![Graph for example 4](../src/main/resources/figures/graph_4.dot.svg)

For this example, the SPPF figure is omitted due to size; only the extracted paths are listed. The query uses two start
vertices: 1 and 8.

* [(1-Alias->9), (9-store_3->11), (11-PointsTo->13)]

  ```n.q = new P()```

* [(1-Alias->8), (8-store_2->10), (10-PointsTo->12)]

  ```n.p = new Y() ```

* [(8-Alias->9), (9-store_3->11), (11-PointsTo->13)]

  ```v.q = new P() ```

* [(8-store_2->10), (10-PointsTo->12)]

  ```v.p = new Y() ```

* [(8-Alias->8), (8-store_2->10), (10-PointsTo->12)]

  ```v.p = new Y() ```

