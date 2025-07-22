package org.kotlin.tools.verbs

/**
 * Examples demonstrating the usage of the Verbs library.
 * This file contains comprehensive examples of all library features.
 */
object Examples {
    
    /**
     * Basic conjugation examples
     */
    fun basicExamples() {
        println("=== Basic Conjugation Examples ===")
        
        // Simple conjugation
        println("accept".conjugate()) // "accepts"
        println("break".conjugate()) // "breaks"
        
        // With custom options
        println("accept".conjugate(ConjugationOptions(
            tense = Tense.PAST,
            person = Person.FIRST,
            plurality = Plurality.SINGULAR
        ))) // "accepted"
        
        println()
    }
    
    /**
     * Irregular verb examples
     */
    fun irregularVerbExamples() {
        println("=== Irregular Verb Examples ===")
        
        // Break
        println("break".conjugate()) // "breaks"
        println("break".conjugate(ConjugationOptions(tense = Tense.PAST))) // "broke"
        
        // Be (copula)
        println("be".conjugate(ConjugationOptions(person = Person.FIRST))) // "am"
        println("be".conjugate(ConjugationOptions(person = Person.THIRD))) // "is"
        println("be".conjugate(ConjugationOptions(tense = Tense.PAST, person = Person.FIRST))) // "was"
        
        // Have
        println("have".conjugate(ConjugationOptions(person = Person.THIRD))) // "has"
        println("have".conjugate(ConjugationOptions(tense = Tense.PAST))) // "had"
        
        println()
    }
    
    /**
     * Aspect examples
     */
    fun aspectExamples() {
        println("=== Aspect Examples ===")
        
        // Habitual
        println("accept".conjugate(ConjugationOptions(aspect = Aspect.HABITUAL))) // "accepts"
        println("accept".conjugate(ConjugationOptions(tense = Tense.PAST, aspect = Aspect.HABITUAL))) // "used to accept"
        
        // Progressive
        println("accept".conjugate(ConjugationOptions(aspect = Aspect.PROGRESSIVE))) // "is accepting"
        println("accept".conjugate(ConjugationOptions(tense = Tense.PAST, aspect = Aspect.PROGRESSIVE))) // "was accepting"
        
        // Perfect
        println("accept".conjugate(ConjugationOptions(aspect = Aspect.PERFECT))) // "have accepted"
        println("accept".conjugate(ConjugationOptions(tense = Tense.PAST, aspect = Aspect.PERFECT))) // "had accepted"
        
        // Prospective
        println("accept".conjugate(ConjugationOptions(aspect = Aspect.PROSPECTIVE))) // "be about to accept"
        
        println()
    }
    
    /**
     * Subject examples
     */
    fun subjectExamples() {
        println("=== Subject Examples ===")
        
        // Named subject
        println("accept".conjugate("Matz")) // "Matz accepts"
        println("be".conjugate("I", ConjugationOptions(person = Person.FIRST, plurality = Plurality.SINGULAR))) // "I am"
        
        // Automatic pronoun
        println("accept".conjugate(true)) // "he accepts"
        println("accept".conjugate(true, ConjugationOptions(person = Person.FIRST))) // "I accept"
        
        println()
    }
    
    /**
     * Multi-word phrase examples
     */
    fun phraseExamples() {
        println("=== Multi-word Phrase Examples ===")
        
        // Basic phrases
        println("be nice".verb().conjugate("Matz")) // "Matz is nice"
        println("go to school".verb().conjugate("I", ConjugationOptions(person = Person.FIRST, plurality = Plurality.SINGULAR))) // "I go to school"
        
        // With progressive aspect
        println("be nice".verb().conjugate("I", ConjugationOptions(
            person = Person.FIRST,
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PROGRESSIVE
        ))) // "I am being nice"
        
        println()
    }
    
    /**
     * DSL examples
     */
    fun dslExamples() {
        println("=== DSL Examples ===")
        
        // Using DSL builder
        val options = conjugationOptions {
            tense(Tense.PAST)
            person(Person.FIRST)
            plurality(Plurality.SINGULAR)
            aspect(Aspect.PERFECTIVE)
        }
        println("accept".conjugate(options)) // "accepted"
        
        // Using Verbs object
        val options2 = Verbs.options {
            tense(Tense.FUTURE)
            person(Person.THIRD)
            plurality(Plurality.SINGULAR)
            aspect(Aspect.PROGRESSIVE)
        }
        println("sleep".conjugate(options2)) // "will be sleeping"
        
        println()
    }
    
    /**
     * Library information examples
     */
    fun libraryInfoExamples() {
        println("=== Library Information Examples ===")
        
        println(VerbsLibrary.getLibraryInfo())
        println("Irregular verb count: ${VerbsLibrary.getIrregularVerbCount()}")
        println("Is initialized: ${VerbsLibrary.isInitialized()}")
        
        println()
    }
    
    /**
     * Run all examples
     */
    fun runAllExamples() {
        basicExamples()
        irregularVerbExamples()
        aspectExamples()
        subjectExamples()
        phraseExamples()
        dslExamples()
        libraryInfoExamples()
    }
}

/**
 * Main function to run examples
 */
fun main() {
    Examples.runAllExamples()
} 