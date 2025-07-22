package org.kotlin.tools.verbs

import net.jqwik.api.*
import net.jqwik.api.constraints.*
import net.jqwik.kotlin.api.*
import org.assertj.core.api.Assertions.assertThat

/**
 * Property-based tests for verb conjugation using jqwik.
 * 
 * These tests verify properties that should hold for all valid inputs,
 * rather than testing specific examples.
 */
class PropertyBasedTests {
    
    @Property
    fun `conjugation should always return non-empty string`(
        @ForAll @StringLength(min = 1, max = 20) @AlphaChars verb: String
    ) {
        val result = verb.conjugate()
        assertThat(result).isNotEmpty()
    }
    
    @Property
    fun `conjugation with same options should be consistent`(
        @ForAll @StringLength(min = 1, max = 20) @AlphaChars verb: String,
        @ForAll options: ConjugationOptions
    ) {
        val result1 = verb.conjugate(options)
        val result2 = verb.conjugate(options)
        assertThat(result1).isEqualTo(result2)
    }
    
    @Property
    fun `third person singular present should end with 's' for regular verbs`(
        @ForAll("regularVerbs") verb: String
    ) {
        val options = ConjugationOptions(
            tense = Tense.PRESENT,
            person = Person.THIRD,
            plurality = Plurality.SINGULAR
        )
        val result = verb.conjugate(options)
        
        // Regular verbs in third person singular should end with 's'
        // unless they already end with 's', 'x', 'z', 'ch', 'sh'
        if (!verb.endsWith("s") && !verb.endsWith("x") && 
            !verb.endsWith("z") && !verb.endsWith("ch") && !verb.endsWith("sh")) {
            assertThat(result).endsWith("s")
        }
    }
    
    @Property
    fun `first person singular present should not end with 's'`(
        @ForAll @StringLength(min = 1, max = 20) @AlphaChars verb: String
    ) {
        val options = ConjugationOptions(
            tense = Tense.PRESENT,
            person = Person.FIRST,
            plurality = Plurality.SINGULAR
        )
        val result = verb.conjugate(options)
        assertThat(result).doesNotEndWith("s")
    }
    
    @Property
    fun `past tense should not end with 's'`(
        @ForAll @StringLength(min = 1, max = 20) @AlphaChars verb: String
    ) {
        val options = ConjugationOptions(tense = Tense.PAST)
        val result = verb.conjugate(options)
        assertThat(result).doesNotEndWith("s")
    }
    
    @Property
    fun `conjugation with subject should start with subject`(
        @ForAll @StringLength(min = 1, max = 20) @AlphaChars verb: String,
        @ForAll @StringLength(min = 1, max = 10) @AlphaChars subject: String
    ) {
        val options = ConjugationOptions(subject = subject)
        val result = verb.conjugate(options)
        assertThat(result).startsWith(subject)
    }
    
    @Property
    fun `irregular verb detection should be consistent`(
        @ForAll @StringLength(min = 1, max = 20) @AlphaChars verb: String
    ) {
        val isIrregular1 = verb.isIrregular()
        val isIrregular2 = verb.isIrregular()
        assertThat(isIrregular1).isEqualTo(isIrregular2)
    }
    
    @Provide
    fun regularVerbs(): Arbitrary<String> {
        return Arbitraries.strings()
            .withCharRange('a', 'z')
            .ofMinLength(3)
            .ofMaxLength(10)
            .filter { !it.isIrregular() } // Exclude irregular verbs
    }
} 