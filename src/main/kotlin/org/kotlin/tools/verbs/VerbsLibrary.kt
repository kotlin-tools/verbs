package org.kotlin.tools.verbs

/**
 * Main library entry point that provides high-level access to verb conjugation functionality.
 * This object serves as the primary interface for the Verbs library.
 */
object VerbsLibrary {
    
    private var initialized = false
    
    init {
        initialize()
    }
    
    /**
     * Initialize the library explicitly.
     * This method ensures all irregular verbs and patterns are loaded.
     */
    fun initialize() {
        if (!initialized) {
            // Force initialization of Conjugator
            Conjugator.getIrregularCount()
            initialized = true
        }
    }
    
    /**
     * Check if the library has been initialized.
     * @return true if the library is ready to use
     */
    fun isInitialized(): Boolean = initialized
    
    /**
     * Get the total number of irregular verbs loaded in the library.
     * @return The count of irregular verbs
     */
    fun getIrregularVerbCount(): Int = Conjugator.getIrregularCount()
    
    /**
     * Check if a verb is irregular.
     * @param verb The verb to check
     * @return true if the verb is irregular, false otherwise
     */
    fun isIrregular(verb: String): Boolean = Conjugator.isIrregular(verb)
    
    /**
     * Conjugate a verb with default options (present, third person singular, habitual aspect).
     * @param verb The infinitive form of the verb
     * @return The conjugated form
     */
    fun conjugate(verb: String): String = Conjugator.conjugate(verb)
    
    /**
     * Conjugate a verb with custom options.
     * @param verb The infinitive form of the verb
     * @param options The conjugation options
     * @return The conjugated form
     */
    fun conjugate(verb: String, options: ConjugationOptions): String = Conjugator.conjugate(verb, options)
    
    /**
     * Get the subject pronoun for given conjugation options.
     * @param options The conjugation options
     * @return The appropriate subject pronoun
     */
    fun getSubject(options: ConjugationOptions): String = Conjugator.subject(options)
    
    /**
     * Get library version information.
     * @return A string containing version and initialization status
     */
    fun getLibraryInfo(): String {
        return "Verbs Library v1.0.0 - Initialized: $initialized, Irregular verbs: ${getIrregularVerbCount()}"
    }
}

// Initialize the library when this module is loaded
val library = VerbsLibrary
