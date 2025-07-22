package org.kotlin.tools.verbs

/**
 * Sample functions for [ConjugationOptions] documentation.
 */
object ConjugationOptionsSamples {
    
    /**
     * Basic usage example for [ConjugationOptions].
     * 
     * This example shows how to create simple conjugation options:
     * 
     * ```kotlin
     * val options = ConjugationOptions(
     *     tense = Tense.PAST,
     *     person = Person.FIRST
     * )
     * "accept".conjugate(options) // returns "accepted"
     * ```
     */
    fun basicUsage() {
        val options = ConjugationOptions(
            tense = Tense.PAST,
            person = Person.FIRST
        )
        println("accept".conjugate(options)) // "accepted"
    }
    
    /**
     * Complex usage example for [ConjugationOptions].
     * 
     * This example shows advanced conjugation with all options:
     * 
     * ```kotlin
     * val options = ConjugationOptions(
     *     tense = Tense.FUTURE,
     *     person = Person.THIRD,
     *     plurality = Plurality.SINGULAR,
     *     aspect = Aspect.PROGRESSIVE,
     *     mood = Mood.INDICATIVE,
     *     subject = "John"
     * )
     * "sleep".conjugate(options) // returns "John will be sleeping"
     * ```
     */
    fun complexUsage() {
        val options = ConjugationOptions(
            tense = Tense.FUTURE,
            person = Person.THIRD,
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PROGRESSIVE,
            mood = Mood.INDICATIVE,
            subject = "John"
        )
        println("sleep".conjugate(options)) // "John will be sleeping"
    }
} 