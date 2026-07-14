package org.ucfs.paths.examples

import org.ucfs.grammar.combinator.Grammar
import org.ucfs.grammar.combinator.extension.StringExtension.times
import org.ucfs.grammar.combinator.regexp.Nt
import org.ucfs.grammar.combinator.regexp.option
import org.ucfs.input.DotParser
import org.ucfs.input.InputGraph
import org.ucfs.input.TerminalInputLabel
import org.ucfs.parser.Gll
import org.ucfs.sppf.getSppfDot
import org.ucfs.sppf.node.*
import java.nio.file.Files
import java.nio.file.Path

class AnBnGrammar : Grammar() {
    val S by Nt().asStart()

    init {
        S /= "a" * option(S) * "b"
    }
}

fun readGraph(name: String): InputGraph<Int, TerminalInputLabel> {
    val dotGraph = object {}.javaClass.getResource("/$name")?.readText()
        ?: throw RuntimeException("File $name is not found in resources")
    val dotParser = DotParser()
    return dotParser.parseDot(dotGraph)
}

fun saveSppf(name: String, sppf: Set<RangeSppfNode<Int>>) {
    val graphName = name.removeSuffix(".dot")
    val genPath = Path.of("gen", "sppf")
    Files.createDirectories(genPath)
    val file = genPath.resolve("${graphName}_sppf.dot").toFile()

    file.printWriter().use { out ->
        out.println(getSppfDot(sppf))
    }
}

fun main() {
    listOf("example_1_graph.dot", "example_2_graph.dot", "example_3_graph.dot").forEach { graphName ->
        val graph = readGraph(graphName)
        val grammar = AnBnGrammar()
        val gll = Gll.gll(grammar.rsm, graph)
        val sppf = gll.parse()
        saveSppf(graphName, sppf)
    }
}
