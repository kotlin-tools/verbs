package org.kotlin.tools.verbs

import kotlinx.cli.*

/**
 * Command Line Interface for the Verbs library.
 * 
 * Provides a convenient way to conjugate verbs from the command line.
 * 
 * Usage examples:
 * ```
 * verbs conjugate accept
 * verbs conjugate break --tense past --person first
 * verbs conjugate "be nice" --subject "John" --aspect progressive
 * verbs list-irregular
 * verbs info
 * ```
 */
fun main(args: Array<String>) {
    val parser = ArgParser("verbs")
    
    val conjugateCommand = ConjugateCommand()
    val listIrregularCommand = ListIrregularCommand()
    val infoCommand = InfoCommand()
    
    parser.subcommands(conjugateCommand, listIrregularCommand, infoCommand)
    
    parser.parse(args)
}

/**
 * Command for conjugating verbs.
 */
class ConjugateCommand : Subcommand("conjugate", "Conjugate a verb") {
    private val verb by argument(ArgType.String, description = "Verb to conjugate")
    private val tense by option(ArgType.Choice<Tense>(), shortName = "t", description = "Tense (past, present, future)")
    private val person by option(ArgType.Choice<Person>(), shortName = "p", description = "Person (first, second, third)")
    private val plurality by option(ArgType.Choice<Plurality>(), shortName = "n", description = "Plurality (singular, plural)")
    private val aspect by option(ArgType.Choice<Aspect>(), shortName = "a", description = "Aspect (habitual, perfect, perfective, progressive, prospective)")
    private val mood by option(ArgType.Choice<Mood>(), shortName = "m", description = "Mood (indicative, imperative, subjunctive)")
    private val subject by option(ArgType.String, shortName = "s", description = "Subject")
    
    override fun execute() {
        val options = ConjugationOptions(
            tense = tense ?: Tense.PRESENT,
            person = person ?: Person.THIRD,
            plurality = plurality ?: Plurality.SINGULAR,
            aspect = aspect,
            mood = mood ?: Mood.INDICATIVE,
            subject = subject
        )
        
        val result = verb.conjugate(options)
        println(result)
    }
}

/**
 * Command for listing irregular verbs.
 */
class ListIrregularCommand : Subcommand("list-irregular", "List irregular verbs") {
    override fun execute() {
        val irregularVerbs = listOf("be", "have", "do", "go", "know", "break", "fly", "see", "come", "take")
        println("Irregular verbs in the library:")
        irregularVerbs.forEach { verb ->
            println("  - $verb")
        }
    }
}

/**
 * Command for showing library information.
 */
class InfoCommand : Subcommand("info", "Show library information") {
    override fun execute() {
        println("Verbs Library v${getLibraryVersion()}")
        println("A comprehensive Kotlin library for English verb conjugation")
        println()
        println("Features:")
        println("  - Complete conjugation support for all tenses, persons, aspects, and moods")
        println("  - Irregular verb handling with custom forms")
        println("  - Fluent API with multiple conjugation methods")
        println("  - Extension functions for natural verb conjugation")
        println("  - DSL support with builder pattern")
        println("  - Multi-word phrase support")
        println()
        println("Usage examples:")
        println("  verbs conjugate accept")
        println("  verbs conjugate break --tense past --person first")
        println("  verbs conjugate \"be nice\" --subject \"John\" --aspect progressive")
        println("  verbs list-irregular")
    }
    
    private fun getLibraryVersion(): String = "1.0.0"
} 