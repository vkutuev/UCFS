package org.ucfs.grammar.combinator.regexp

data class Many internal constructor(
    val exp: Regexp,
) : Regexp {
    override fun derive(symbol: DerivedSymbol): Regexp {
        val newReg = exp.derive(symbol)

        return when (newReg) {
            Epsilon -> Many(exp)
            Empty -> Empty
            else -> Concat(newReg, Many(exp))
        }
    }
}

fun many(some: Regexp): Regexp = Many(some)

fun some(exp: Regexp) = exp * many(exp)