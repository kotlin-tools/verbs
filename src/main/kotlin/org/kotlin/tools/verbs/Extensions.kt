package org.kotlin.tools.verbs

/**
 * Extension function to conjugate a string verb with default options.
 * Uses present tense, third person singular, habitual aspect.
 * 
 * @param options The conjugation options (optional)
 * @return The conjugated form of the verb
 * 
 * @example
 * ```kotlin
 * "accept".conjugate() // returns "accepts"
 * "break".conjugate() // returns "breaks"
 * ```
 */
fun String.conjugate(options: ConjugationOptions = ConjugationOptions()): String {
    return Conjugator.conjugate(this, options)
}

/**
 * Extension function to conjugate a string verb with a specific subject.
 * 
 * @param subject The subject to prepend to the conjugation
 * @param options The conjugation options (optional)
 * @return The conjugated form with subject
 * 
 * @example
 * ```kotlin
 * "accept".conjugate("Matz") // returns "Matz accepts"
 * "be".conjugate("I") // returns "I am"
 * ```
 */
fun String.conjugate(subject: String, options: ConjugationOptions = ConjugationOptions()): String {
    return Conjugator.conjugate(this, options.copy(subject = subject))
}

/**
 * Extension function to conjugate a string verb with automatic subject pronoun.
 * 
 * @param subject If true, uses the appropriate pronoun based on options; if false, no subject
 * @param options The conjugation options (optional)
 * @return The conjugated form with or without subject pronoun
 * 
 * @example
 * ```kotlin
 * "accept".conjugate(true) // returns "he accepts" (default third person)
 * "accept".conjugate(true, ConjugationOptions(person = Person.FIRST)) // returns "I accept"
 * ```
 */
fun String.conjugate(subject: Boolean, options: ConjugationOptions = ConjugationOptions()): String {
    return if (subject) {
        val pronoun = Conjugator.subject(options)
        Conjugator.conjugate(this, options.copy(subject = pronoun))
    } else {
        Conjugator.conjugate(this, options)
    }
}

/**
 * Extension function to check if a string is an irregular verb.
 * 
 * @return true if the verb is irregular, false otherwise
 * 
 * @example
 * ```kotlin
 * "break".isIrregular() // returns true
 * "accept".isIrregular() // returns false
 * ```
 */
fun String.isIrregular(): Boolean {
    return Conjugator.isIrregular(this)
}

/**
 * Extension function to get the verb form of a string for DSL-style conjugation.
 * 
 * @return A VerbWrapper for fluent conjugation
 * 
 * @example
 * ```kotlin
 * "be nice".verb().conjugate("Matz") // returns "Matz is nice"
 * "sleep".verb().conjugate(true, ConjugationOptions(tense = Tense.FUTURE)) // returns "he will sleep"
 * ```
 */
fun String.verb(): VerbWrapper {
    return VerbWrapper(this)
}

/**
 * Wrapper class for verb-like conjugation with support for multi-word phrases.
 * This class allows conjugation of phrases like "be nice", "go to school", etc.
 */
class VerbWrapper(private val base: String) {
    
    /**
     * Conjugate the verb phrase with options.
     * 
     * @param options The conjugation options
     * @return The conjugated form of the verb phrase
     */
    fun conjugate(options: ConjugationOptions = ConjugationOptions()): String {
        val words = base.split(" ")
        val (infinitive, remainingWords) = extractVerbAndRemaining(words)
        
        val conjugation = Conjugator.conjugate(infinitive, options)
        
        return if (remainingWords.isNotEmpty()) {
            "$conjugation ${remainingWords.joinToString(" ")}"
        } else {
            conjugation
        }
    }
    
    /**
     * Conjugate with a specific subject.
     * 
     * @param subject The subject to prepend
     * @param options The conjugation options
     * @return The conjugated form with subject
     */
    fun conjugate(subject: String, options: ConjugationOptions = ConjugationOptions()): String {
        return conjugate(options.copy(subject = subject))
    }
    
    /**
     * Conjugate with automatic subject pronoun.
     * 
     * @param subject If true, uses appropriate pronoun; if false, no subject
     * @param options The conjugation options
     * @return The conjugated form with or without subject
     */
    fun conjugate(subject: Boolean, options: ConjugationOptions = ConjugationOptions()): String {
        return if (subject) {
            val pronoun = Conjugator.subject(options)
            conjugate(options.copy(subject = pronoun))
        } else {
            conjugate(options)
        }
    }
    
    /**
     * Extract the main verb and remaining words from a phrase.
     * 
     * @param words The words in the phrase
     * @return A pair of (infinitive, remaining words)
     */
    private fun extractVerbAndRemaining(words: List<String>): Pair<String, List<String>> {
        val infinitive = if (words.first().lowercase() == "to") {
            words.drop(1).first().lowercase()
        } else {
            words.first().lowercase()
        }
        
        val remainingWords = if (words.first().lowercase() == "to") {
            words.drop(2)
        } else {
            words.drop(1)
        }
        
        return Pair(infinitive, remainingWords)
    }
}

/**
 * DSL builder for conjugation options.
 * Provides a fluent API for creating ConjugationOptions.
 */
class ConjugationOptionsBuilder {
    private var tense: Tense = Tense.PRESENT
    private var person: Person = Person.THIRD
    private var plurality: Plurality = Plurality.SINGULAR
    private var aspect: Aspect? = null
    private var mood: Mood = Mood.INDICATIVE
    private var diathesis: Diathesis = Diathesis.ACTIVE
    private var subject: String? = null
    
    /**
     * Set the grammatical tense.
     */
    fun tense(value: Tense) { tense = value }
    
    /**
     * Set the grammatical person.
     */
    fun person(value: Person) { person = value }
    
    /**
     * Set the grammatical plurality.
     */
    fun plurality(value: Plurality) { plurality = value }
    
    /**
     * Set the grammatical aspect.
     */
    fun aspect(value: Aspect) { aspect = value }
    
    /**
     * Set the grammatical mood.
     */
    fun mood(value: Mood) { mood = value }
    
    /**
     * Set the grammatical diathesis.
     */
    fun diathesis(value: Diathesis) { diathesis = value }
    
    /**
     * Set the subject explicitly.
     */
    fun subject(value: String?) { subject = value }
    
    /**
     * Set the subject automatically based on person and plurality.
     */
    fun subject(value: Boolean) { 
        subject = if (value) {
            val options = ConjugationOptions(tense = tense, person = person, plurality = plurality)
            Conjugator.subject(options)
        } else null
    }
    
    /**
     * Build the ConjugationOptions from the builder.
     */
    fun build(): ConjugationOptions {
        return ConjugationOptions(
            tense = tense,
            person = person,
            plurality = plurality,
            aspect = aspect,
            mood = mood,
            diathesis = diathesis,
            subject = subject
        )
    }
}

/**
 * DSL function for building conjugation options.
 * 
 * @param block A lambda that configures the conjugation options
 * @return The configured ConjugationOptions
 * 
 * @example
 * ```kotlin
 * val options = conjugationOptions {
 *     tense(Tense.PAST)
 *     person(Person.FIRST)
 *     plurality(Plurality.SINGULAR)
 *     aspect(Aspect.PERFECTIVE)
 * }
 * ```
 */
fun conjugationOptions(block: ConjugationOptionsBuilder.() -> Unit): ConjugationOptions {
    return ConjugationOptionsBuilder().apply(block).build()
}
