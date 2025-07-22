package org.kotlin.tools.verbs

import org.kotlin.tools.verbs.ConjugationOptions

/**
 * Represents different conjugation patterns
 */
sealed class ConjugationPattern {
    object Regular : ConjugationPattern()
    object Irregular : ConjugationPattern()
    object Copula : ConjugationPattern()
}

/**
 * Represents verb endings for conjugation
 */
sealed class VerbEnding {
    object YEnding : VerbEnding()
    object SibilantEnding : VerbEnding()
    object ConsonantEnding : VerbEnding()
    object VowelEnding : VerbEnding()
    object CEnding : VerbEnding()
    object EEnding : VerbEnding()
    object IEEnding : VerbEnding()
    object RegularEnding : VerbEnding()
}

object Conjugator {
    // Compile regex patterns once for better performance
    private object Patterns {
        private const val CONSONANTS = "bcdfghjklmnpqrstvwxz"
        private const val CONSONANTS_WITHOUT_C = "bdfghjklmnpqrstvwxz"
        private const val DOUBLED_CONSONANTS_WITHOUT_C = "bdfghjklmnpqrstz"
        private const val VOWELS = "aeiouy"
        
        val CONSONANT_PATTERN = Regex("[$CONSONANTS]")
        val CONSONANTS_WITHOUT_C_PATTERN = Regex("[$CONSONANTS_WITHOUT_C]")
        val DOUBLED_CONSONANT_WITHOUT_C_PATTERN = Regex("[$DOUBLED_CONSONANTS_WITHOUT_C]")
        val VOWEL_PATTERN = Regex("[$VOWELS]")
        
        // Specific patterns for different endings
        val Y_ENDING = Regex("[a-z&&$CONSONANTS]y$", RegexOption.IGNORE_CASE)
        val SIBILANT_ENDING = Regex("(ss|sh|t?ch|zz|x|${CONSONANT_PATTERN.pattern}o)$", RegexOption.IGNORE_CASE)
        val S_ENDING = Regex("[^s]s$", RegexOption.IGNORE_CASE)
        val DOUBLE_CONSONANT_PATTERN = Regex("${CONSONANT_PATTERN.pattern}${VOWEL_PATTERN.pattern}${DOUBLED_CONSONANT_WITHOUT_C_PATTERN.pattern}$")
        val E_ENDING = Regex("(${CONSONANT_PATTERN.pattern}e|ye|oe|nge|ie|ee|ue)$")
        val Y_CONSONANT_ENDING = Regex("${CONSONANT_PATTERN.pattern}y$")
        val C_ENDING = Regex("c$")
        val IE_ENDING = Regex("ie$")
        val VOWEL_CONSONANT_E_ENDING = Regex("${VOWEL_PATTERN.pattern}${CONSONANT_PATTERN.pattern}e$|ue$")
    }

    private val irregularVerbs = mutableMapOf<String, Verb>()
    private val singleTerminalConsonants = mutableSetOf<String>()

    init {
        initializeIrregularVerbs()
    }

    fun conjugate(infinitive: String, options: ConjugationOptions = ConjugationOptions()): String {
        val verb = infinitive.lowercase()
        val conjugated = conjugateVerb(verb, options)
        return addSubject(conjugated, options.subject)
    }

    private fun conjugateVerb(verb: String, options: ConjugationOptions): String {
        // Validate diathesis support
        if (options.diathesis != Diathesis.ACTIVE) {
            throw NotImplementedError("Passive diathesis not implemented")
        }
        
        val aspect = options.aspect ?: defaultAspect(options.tense)
        
        return when (aspect) {
            Aspect.HABITUAL -> conjugateHabitual(verb, options)
            Aspect.PERFECTIVE -> conjugatePerfective(verb, options)
            Aspect.PROGRESSIVE -> conjugateProgressive(verb, options)
            Aspect.PERFECT -> conjugatePerfect(verb, options)
            Aspect.PROSPECTIVE -> conjugateProspective(verb, options)
        }
    }

    private fun conjugateHabitual(verb: String, options: ConjugationOptions): String {
        return when (options.tense) {
            Tense.PRESENT -> present(verb, options.person, options.plurality, options.mood)
            Tense.PAST -> "used to $verb"
            Tense.FUTURE -> "will $verb"
        }
    }

    private fun conjugatePerfective(verb: String, options: ConjugationOptions): String {
        return when (options.tense) {
            Tense.PRESENT -> "be having ${pastParticiple(verb)}"
            Tense.PAST -> past(verb, options.person, options.plurality, options.mood)
            Tense.FUTURE -> "be having ${pastParticiple(verb)}"
        }
    }

    private fun conjugateProgressive(verb: String, options: ConjugationOptions): String {
        val participle = presentParticiple(verb)
        return when (options.tense) {
            Tense.PRESENT -> "${copulaPresent(options.person, options.plurality)} $participle"
            Tense.PAST -> "${copulaPast(options.person, options.plurality)} $participle"
            Tense.FUTURE -> "will be $participle"
        }
    }

    private fun conjugatePerfect(verb: String, options: ConjugationOptions): String {
        val participle = pastParticiple(verb)
        return when (options.tense) {
            Tense.PRESENT -> "have $participle"
            Tense.PAST -> "had $participle"
            Tense.FUTURE -> "have $participle"
        }
    }

    private fun conjugateProspective(verb: String, @Suppress("UNUSED_PARAMETER") options: ConjugationOptions): String {
        return "be about to $verb"
    }

    private fun addSubject(conjugated: String, subject: String?): String {
        return if (subject != null) "$subject $conjugated" else conjugated
    }

    private fun present(infinitive: String, person: Person, plurality: Plurality, mood: Mood): String {
        val irregular = irregularVerbs[infinitive]
        if (irregular != null) {
            return handleIrregularPresent(irregular, person, plurality, mood)
        }
        return handleRegularPresent(infinitive, person, plurality, mood)
    }

    private fun handleIrregularPresent(irregular: Verb, person: Person, plurality: Plurality, mood: Mood): String {
        val form = irregular.getForm(Tense.PRESENT, person, plurality, mood)
        if (form != null) return form
        
        return if (isThirdPersonSingular(person, plurality, mood)) {
            presentThirdPersonSingular(irregular.infinitive)
        } else {
            irregular.infinitive
        }
    }

    private fun handleRegularPresent(infinitive: String, person: Person, plurality: Plurality, mood: Mood): String {
        return if (isThirdPersonSingular(person, plurality, mood)) {
            presentThirdPersonSingular(infinitive)
        } else {
            infinitive
        }
    }

    private fun isThirdPersonSingular(person: Person, plurality: Plurality, mood: Mood): Boolean {
        return person == Person.THIRD && plurality == Plurality.SINGULAR && mood != Mood.SUBJUNCTIVE
    }

    private fun past(infinitive: String, person: Person, plurality: Plurality, mood: Mood): String {
        val irregular = irregularVerbs[infinitive]
        if (irregular != null) {
            val form = irregular.getForm(Tense.PAST, person, plurality, mood)
            if (form != null) return form
            return irregular.preterite ?: irregular.infinitive
        }
        return regularPreterite(infinitive)
    }

    private fun presentThirdPersonSingular(infinitive: String): String {
        return when {
            Patterns.Y_ENDING.containsMatchIn(infinitive) -> infinitive.dropLast(1) + "ies"
            Patterns.SIBILANT_ENDING.containsMatchIn(infinitive) -> infinitive + "es"
            Patterns.S_ENDING.containsMatchIn(infinitive) -> infinitive + "ses"
            else -> infinitive + "s"
        }
    }

    private fun regularPreterite(infinitive: String): String {
        if (shouldDoubleConsonant(infinitive)) {
            return regularPreteriteWithDoubledTerminalConsonant(infinitive)
        }
        
        return when {
            Patterns.E_ENDING.containsMatchIn(infinitive) -> infinitive + "d"
            Patterns.Y_CONSONANT_ENDING.containsMatchIn(infinitive) -> infinitive.dropLast(1) + "ied"
            Patterns.C_ENDING.containsMatchIn(infinitive) -> infinitive.dropLast(1) + "cked"
            else -> infinitive + "ed"
        }
    }

    private fun shouldDoubleConsonant(infinitive: String): Boolean {
        return Patterns.DOUBLE_CONSONANT_PATTERN.containsMatchIn(infinitive) && 
               !singleTerminalConsonants.contains(infinitive)
    }

    private fun regularPreteriteWithDoubledTerminalConsonant(infinitive: String): String {
        val doubled = infinitive + infinitive.last()
        return regularPreterite(doubled)
    }

    private fun presentParticiple(infinitive: String): String {
        val irregular = irregularVerbs[infinitive]
        if (irregular != null) {
            return irregular.infinitive + "ing"
        }
        
        if (shouldDoubleConsonant(infinitive)) {
            return presentParticipleWithDoubledTerminalConsonant(infinitive)
        }
        
        val base = getPresentParticipleBase(infinitive)
        return base + "ing"
    }

    private fun getPresentParticipleBase(infinitive: String): String {
        return when {
            Patterns.C_ENDING.containsMatchIn(infinitive) -> infinitive + "k"
            Patterns.IE_ENDING.containsMatchIn(infinitive) -> infinitive.dropLast(2) + "y"
            Patterns.VOWEL_CONSONANT_E_ENDING.containsMatchIn(infinitive) -> infinitive.dropLast(1)
            else -> infinitive
        }
    }

    private fun presentParticipleWithDoubledTerminalConsonant(infinitive: String): String {
        return if (Patterns.C_ENDING.containsMatchIn(infinitive)) {
            presentParticiple(infinitive)
        } else {
            val doubled = infinitive + infinitive.last()
            presentParticiple(doubled)
        }
    }

    private fun pastParticiple(infinitive: String): String {
        val irregular = irregularVerbs[infinitive]
        if (irregular != null) {
            return irregular.pastParticiple ?: irregular.preterite ?: irregular.infinitive
        }
        return regularPreterite(infinitive)
    }

    private fun defaultAspect(tense: Tense): Aspect {
        return if (tense == Tense.PAST) Aspect.PERFECTIVE else Aspect.HABITUAL
    }

    private fun copulaPresent(person: Person, plurality: Plurality): String {
        return when {
            person == Person.FIRST && plurality == Plurality.SINGULAR -> "am"
            person == Person.SECOND -> "are"
            person == Person.THIRD && plurality == Plurality.SINGULAR -> "is"
            else -> "are"
        }
    }

    private fun copulaPast(person: Person, plurality: Plurality): String {
        return when {
            person == Person.FIRST && plurality == Plurality.SINGULAR -> "was"
            person == Person.SECOND -> "were"
            person == Person.THIRD && plurality == Plurality.SINGULAR -> "was"
            else -> "were"
        }
    }

    private fun initializeIrregularVerbs() {
        initializeBasicIrregularVerbs()
        initializeCopulaVerb()
        initializeSingleTerminalConsonants()
    }

    private fun initializeBasicIrregularVerbs() {
        irregularVerbs["know"] = Verb("know", "knew", "known")
        irregularVerbs["break"] = Verb("break", "broke", "broken")
        
        val have = Verb("have", "had", "had")
        have.form("has", VerbFormOptions(tense = Tense.PRESENT, person = Person.THIRD, plurality = Plurality.SINGULAR))
        irregularVerbs["have"] = have
    }

    private fun initializeCopulaVerb() {
        val be = Verb("be", "was", "been")
        
        // Present forms
        be.form("am", VerbFormOptions(tense = Tense.PRESENT, person = Person.FIRST, plurality = Plurality.SINGULAR))
        be.form("is", VerbFormOptions(tense = Tense.PRESENT, person = Person.THIRD, plurality = Plurality.SINGULAR))
        be.form("are", VerbFormOptions(tense = Tense.PRESENT, person = Person.SECOND, plurality = Plurality.SINGULAR))
        be.form("are", VerbFormOptions(tense = Tense.PRESENT, person = Person.FIRST, plurality = Plurality.PLURAL))
        be.form("are", VerbFormOptions(tense = Tense.PRESENT, person = Person.SECOND, plurality = Plurality.PLURAL))
        be.form("are", VerbFormOptions(tense = Tense.PRESENT, person = Person.THIRD, plurality = Plurality.PLURAL))
        
        // Past forms
        be.form("was", VerbFormOptions(tense = Tense.PAST, person = Person.FIRST, plurality = Plurality.SINGULAR))
        be.form("was", VerbFormOptions(tense = Tense.PAST, person = Person.THIRD, plurality = Plurality.SINGULAR))
        be.form("were", VerbFormOptions(tense = Tense.PAST, person = Person.SECOND, plurality = Plurality.SINGULAR))
        be.form("were", VerbFormOptions(tense = Tense.PAST, person = Person.FIRST, plurality = Plurality.PLURAL))
        be.form("were", VerbFormOptions(tense = Tense.PAST, person = Person.SECOND, plurality = Plurality.PLURAL))
        be.form("were", VerbFormOptions(tense = Tense.PAST, person = Person.THIRD, plurality = Plurality.PLURAL))
        
        // Participles
        be.form("being", VerbFormOptions(tense = Tense.PRESENT, derivative = Derivative.PRESENT_PARTICIPLE))
        be.form("been", VerbFormOptions(tense = Tense.PAST, derivative = Derivative.PAST_PARTICIPLE))
        
        irregularVerbs["be"] = be
    }

    private fun initializeSingleTerminalConsonants() {
        val exceptions = listOf("abandon", "follow", "trigger", "deliver", "color")
        singleTerminalConsonants.addAll(exceptions)
    }

    fun isIrregular(verb: String): Boolean {
        return irregularVerbs.containsKey(verb.lowercase())
    }

    fun getIrregularCount(): Int {
        return irregularVerbs.size
    }

    fun subject(options: ConjugationOptions): String {
        return when {
            options.person == Person.FIRST && options.plurality == Plurality.SINGULAR -> "I"
            options.person == Person.FIRST && options.plurality == Plurality.PLURAL -> "we"
            options.person == Person.SECOND -> "you"
            options.person == Person.THIRD && options.plurality == Plurality.SINGULAR -> "he"
            options.person == Person.THIRD && options.plurality == Plurality.PLURAL -> "they"
            else -> "it"
        }
    }
}
