package simple_examples

import org.ucfs.grammar.combinator.Grammar
import org.ucfs.grammar.combinator.extension.StringExtension.times
import org.ucfs.grammar.combinator.regexp.Nt
import org.ucfs.grammar.combinator.regexp.Option
import org.ucfs.input.DotParser
import org.ucfs.input.InputGraph
import org.ucfs.input.TerminalInputLabel
import org.ucfs.parser.Gll
import org.ucfs.sppf.getSppfDot
import org.ucfs.sppf.node.*
import java.nio.file.Files
import java.nio.file.Path
import org.ucfs.rsm.writeRsmToDot

class PointsToAnBnGrammar : Grammar() {
    val S by Nt().asStart()

    init {
        S /= "a" * Option(S) * "b"
    }
}

fun readGraph(name: String): InputGraph<Int, TerminalInputLabel> {
    val dotGraph = object {}.javaClass.getResource("/$name")?.readText()
        ?: throw RuntimeException("File $name is not found in resources")
    val dotParser = DotParser()
    return dotParser.parseDot(dotGraph)
}

data class OutEdge(val start: Int, val symbol: String, val end: Int) {
    override fun toString(): String = "(" + start.toString() + "-" + symbol + "->" + end.toString() + ")"
}

fun getPathFromSppf(node: RangeSppfNode<Int>, maxDepth: Int): List<List<simple_examples.OutEdge>>? {
    if (maxDepth == 0) {
        return null
    }
    when (val nodeType = node.type) {
        is TerminalType<*> -> {
            val range = node.inputRange ?: throw RuntimeException("Null inputRange for TerminalType node of SPPF")
            return listOf(listOf(OutEdge(range.from, nodeType.terminal.toString(), range.to)))
        }

        is EpsilonNonterminalType -> {
            return listOf(emptyList())
        }

        is EmptyType -> {
            throw RuntimeException("SPPF cannot contain EmptyRange")
        }

        is IntermediateType<*>, is NonterminalType -> {
            val subPaths = node.children.map { getPathFromSppf(it, maxDepth - 1) }
            if (subPaths.any { it == null }) {
                return null
            }
            val paths = subPaths.filterNotNull().fold(listOf(listOf<OutEdge>())) { acc, lst ->
                acc.flatMap { list -> lst.map { element -> list + element } }
            }
            return paths
        }

        is Range -> {
            val paths = node.children.map {
                getPathFromSppf(it, maxDepth - 1)?.filterNotNull()
            }.filterNotNull().flatten()
            if (paths.isEmpty()) {
                return null
            }
            return paths
        }

        else -> {
            println("Type of node is ${node.type.javaClass}")
            throw RuntimeException("Unknown RangeType in SPPF")
        }
    }
}

fun saveSppf(name: String, sppf: Set<RangeSppfNode<Int>>) {
    val graphName = name.removeSuffix(".dot")
    val genPath = Path.of("src", "main", "kotlin", "simple_examples", "gen")
    Files.createDirectories(genPath)
    val file = genPath.resolve("${graphName}_sppf.dot").toFile()

    file.printWriter().use { out ->
        out.println(getSppfDot(sppf))
    }
}

fun main() {
    listOf("example_1_graph.dot", "example_2_graph.dot", "example_3_graph.dot").forEach { graphName ->
        val graph = readGraph(graphName)
        val grammar = PointsToAnBnGrammar()
//        writeRsmToDot(grammar.rsm, "${grammar.name}Rsm")
        val gll = Gll.gll(grammar.rsm, graph)
        val sppf = gll.parse()
        saveSppf(graphName, sppf)
    }
}
