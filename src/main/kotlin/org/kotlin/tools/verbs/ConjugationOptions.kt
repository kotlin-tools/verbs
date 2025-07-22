package org.kotlin.tools.verbs

/**
 * Represents the grammatical tense of a verb.
 * 
 * Tense indicates when an action occurs in time relative to the moment of speaking.
 * 
 * @property PAST Actions that occurred before the present moment
 * @property PRESENT Actions occurring at the present moment or generally true
 * @property FUTURE Actions that will occur after the present moment
 */
enum class Tense {
    /** Actions that occurred before the present moment */
    PAST,
    /** Actions occurring at the present moment or generally true */
    PRESENT,
    /** Actions that will occur after the present moment */
    FUTURE
}

/**
 * Represents the grammatical person of a verb.
 * 
 * Person indicates who is performing the action.
 * 
 * @property FIRST The speaker (I, we)
 * @property SECOND The person being spoken to (you)
 * @property THIRD Someone or something else (he, she, it, they)
 */
enum class Person {
    /** The speaker (I, we) */
    FIRST,
    /** The person being spoken to (you) */
    SECOND,
    /** Someone or something else (he, she, it, they) */
    THIRD
}

/**
 * Represents the grammatical plurality of a verb
 */
enum class Plurality {
    SINGULAR, PLURAL
}

/**
 * Represents the grammatical aspect of a verb
 */
enum class Aspect {
    HABITUAL, PERFECT, PERFECTIVE, PROGRESSIVE, PROSPECTIVE
}

/**
 * Represents the grammatical mood of a verb
 */
enum class Mood {
    INDICATIVE, IMPERATIVE, SUBJUNCTIVE
}

/**
 * Represents the grammatical diathesis of a verb
 */
enum class Diathesis {
    ACTIVE, PASSIVE
}

/**
 * Options for verb conjugation.
 * 
 * This data class encapsulates all the grammatical options needed to conjugate a verb.
 * It provides sensible defaults for common conjugation scenarios.
 * 
 * @param tense The grammatical tense (default: [Tense.PRESENT])
 * @param person The grammatical person (default: [Person.THIRD])
 * @param plurality The grammatical plurality (default: [Plurality.SINGULAR])
 * @param aspect The grammatical aspect, null for default based on tense
 * @param mood The grammatical mood (default: [Mood.INDICATIVE])
 * @param diathesis The grammatical diathesis (default: [Diathesis.ACTIVE])
 * @param subject Optional subject string for personalized conjugation
 * 
 * @sample org.kotlin.tools.verbs.ConjugationOptionsSamples.basicUsage
 * @sample org.kotlin.tools.verbs.ConjugationOptionsSamples.complexUsage
 */
data class ConjugationOptions(
    val tense: Tense = Tense.PRESENT,
    val person: Person = Person.THIRD,
    val plurality: Plurality = Plurality.SINGULAR,
    val aspect: Aspect? = null, // Will be set to default based on tense
    val mood: Mood = Mood.INDICATIVE,
    val diathesis: Diathesis = Diathesis.ACTIVE,
    val subject: String? = null
) {
    /**
     * Get the effective aspect, using default if not specified.
     * 
     * The default aspect is [Aspect.PERFECTIVE] for past tense and [Aspect.HABITUAL] for other tenses.
     * 
     * @return The effective aspect to use for conjugation
     */
    fun getEffectiveAspect(): Aspect {
        return aspect ?: if (tense == Tense.PAST) Aspect.PERFECTIVE else Aspect.HABITUAL
    }
} 