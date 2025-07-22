package com.verbs

/**
 * Represents the grammatical tense of a verb
 */
enum class Tense {
    PAST, PRESENT, FUTURE
}

/**
 * Represents the grammatical person of a verb
 */
enum class Person {
    FIRST, SECOND, THIRD
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
 * Options for verb conjugation
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
     * Get the effective aspect, using default if not specified
     */
    fun getEffectiveAspect(): Aspect {
        return aspect ?: if (tense == Tense.PAST) Aspect.PERFECTIVE else Aspect.HABITUAL
    }
}
