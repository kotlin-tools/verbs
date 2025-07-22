package org.kotlin.tools.verbs

/**
 * Main entry point for the Verbs library.
 * Provides convenient access to verb conjugation functionality with a clean API.
 */
object Verbs {
    
    /**
     * Conjugate a verb with default options (present, third person singular, habitual aspect).
     * @param verb The infinitive form of the verb
     * @return The conjugated form
     */
    fun conjugate(verb: String): String = VerbsLibrary.conjugate(verb)
    
    /**
     * Conjugate a verb with custom options.
     * @param verb The infinitive form of the verb
     * @param options The conjugation options
     * @return The conjugated form
     */
    fun conjugate(verb: String, options: ConjugationOptions): String = VerbsLibrary.conjugate(verb, options)
    
    /**
     * Check if a verb is irregular.
     * @param verb The verb to check
     * @return true if the verb is irregular, false otherwise
     */
    fun isIrregular(verb: String): Boolean = VerbsLibrary.isIrregular(verb)
    
    /**
     * Get the subject pronoun for given conjugation options.
     * @param options The conjugation options
     * @return The appropriate subject pronoun
     */
    fun getSubject(options: ConjugationOptions): String = VerbsLibrary.getSubject(options)
    
    /**
     * Get library information.
     * @return A string containing version and status information
     */
    fun getInfo(): String = VerbsLibrary.getLibraryInfo()
    
    /**
     * Create a conjugation options builder for fluent API usage.
     * @param block A lambda that configures the conjugation options
     * @return The configured ConjugationOptions
     */
    fun options(block: ConjugationOptionsBuilder.() -> Unit): ConjugationOptions {
        return conjugationOptions(block)
    }
    
    /**
     * Create a verb wrapper for DSL-style conjugation.
     * @param verb The infinitive form of the verb
     * @return A VerbWrapper for fluent conjugation
     */
    fun verb(verb: String): VerbWrapper = verb.verb()
}
