package org.kotlin.tools.verbs

/**
 * Represents an irregular verb with its various conjugated forms.
 * This class handles the storage and retrieval of specific verb forms
 * for different tenses, persons, and aspects.
 */
class Verb(
    val infinitive: String,
    val preterite: String? = null,
    val pastParticiple: String? = null
) {
    private val forms = mutableMapOf<Tense, MutableMap<String, String>>()

    /**
     * Add a specific form for this verb.
     * @param word The conjugated form to add
     * @param options The grammatical options that define when this form is used
     */
    fun form(word: String, options: VerbFormOptions) {
        require(options.tense != null) { "Tense must be specified" }
        require(options.derivative != null || options.mood != null || (options.person != null && options.plurality != null)) {
            "Must specify either derivative, mood, or person and plurality"
        }

        val tense = options.tense
        forms.getOrPut(tense) { mutableMapOf() }

        when {
            options.derivative != null -> {
                forms[tense]!![options.derivative.name.lowercase()] = word
            }
            options.mood != null -> {
                forms[tense]!![options.mood.name.lowercase()] = word
            }
            options.person != null && options.plurality != null -> {
                val personKey = "${options.person.name.lowercase()}_${options.plurality.name.lowercase()}"
                forms[tense]!![personKey] = word
            }
        }
    }

    /**
     * Get a specific form based on conjugation options.
     * @param options The conjugation options specifying the desired form
     * @return The conjugated form or null if not found
     */
    operator fun get(options: ConjugationOptions): String? {
        val tense = options.tense
        val person = options.person
        val plurality = options.plurality
        val derivative = when (options.getEffectiveAspect()) {
            Aspect.PROGRESSIVE -> Derivative.PRESENT_PARTICIPLE
            Aspect.PERFECT -> Derivative.PAST_PARTICIPLE
            else -> null
        }
        val mood = options.mood

        val found = forms[tense]?.let { tenseForms ->
            when {
                mood != Mood.INDICATIVE -> tenseForms[mood.name.lowercase()]
                derivative != null -> tenseForms[derivative.name.lowercase()]
                else -> {
                    val personKey = "${person.name.lowercase()}_${plurality.name.lowercase()}"
                    tenseForms[personKey]
                }
            }
        }
        if (found != null) return found
        
        // Fallbacks:
        return when {
            tense == Tense.PAST -> preterite
            tense == Tense.PRESENT && !(person == Person.THIRD && plurality == Plurality.SINGULAR) -> infinitive
            derivative == Derivative.PAST_PARTICIPLE -> pastParticiple
            else -> null
        }
    }

    /**
     * Get a specific form for irregular conjugation.
     * @param tense The grammatical tense
     * @param person The grammatical person
     * @param plurality The grammatical plurality
     * @param mood The grammatical mood
     * @return The conjugated form or null if not found
     */
    fun getForm(tense: Tense, person: Person, plurality: Plurality, mood: Mood): String? {
        val options = ConjugationOptions(tense = tense, person = person, plurality = plurality, mood = mood)
        return this[options]
    }

    /**
     * Check if this verb has a specific form for the given options.
     * @param options The conjugation options to check
     * @return true if a specific form exists, false otherwise
     */
    fun hasForm(options: ConjugationOptions): Boolean {
        return this[options] != null
    }

    /**
     * Get all available forms for this verb.
     * @return A map of all stored forms organized by tense
     */
    fun getAllForms(): Map<Tense, Map<String, String>> {
        return forms.mapValues { it.value.toMap() }
    }

    /**
     * Builder function for adding forms using a DSL-style approach.
     * @param block A lambda that configures the verb forms
     * @return This verb instance for method chaining
     */
    fun configure(block: Verb.() -> Unit): Verb {
        this.block()
        return this
    }

    override fun toString(): String {
        return "Verb(infinitive='$infinitive', preterite='$preterite', pastParticiple='$pastParticiple')"
    }
}

/**
 * Options for defining verb forms.
 * This class provides a flexible way to specify when a particular verb form should be used.
 */
data class VerbFormOptions(
    val tense: Tense? = null,
    val person: Person? = null,
    val plurality: Plurality? = null,
    val derivative: Derivative? = null,
    val mood: Mood? = null
) {
    /**
     * Create a VerbFormOptions for a specific person and plurality.
     */
    companion object {
        fun forPerson(tense: Tense, person: Person, plurality: Plurality): VerbFormOptions {
            return VerbFormOptions(tense = tense, person = person, plurality = plurality)
        }
        
        fun forDerivative(tense: Tense, derivative: Derivative): VerbFormOptions {
            return VerbFormOptions(tense = tense, derivative = derivative)
        }
        
        fun forMood(tense: Tense, mood: Mood): VerbFormOptions {
            return VerbFormOptions(tense = tense, mood = mood)
        }
    }
}

/**
 * Represents verb derivatives (participles).
 * These are verb forms that function as adjectives or parts of compound verb forms.
 */
enum class Derivative {
    PRESENT_PARTICIPLE, PAST_PARTICIPLE
}
