# Verbs - English Verb Conjugation Library for Kotlin

A comprehensive Kotlin library for English verb conjugation, ported from the original Ruby implementation. This library provides fluent APIs for conjugating English verbs across all tenses, persons, aspects, and moods.

## Features

- **Complete Conjugation Support**: All tenses (past, present, future), persons (first, second, third), aspects (habitual, perfective, progressive, perfect, prospective), and moods (indicative, imperative, subjunctive)
- **Irregular Verb Handling**: Built-in support for irregular English verbs with custom forms
- **Fluent API**: Multiple ways to conjugate verbs with clean, readable syntax
- **Extension Functions**: Kotlin extension functions for natural verb conjugation
- **DSL Support**: Builder pattern for creating conjugation options
- **Multi-word Phrases**: Support for phrases like "be nice", "go to school"
- **Type Safety**: Full Kotlin type safety with sealed classes and enums

## Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("org.kotlin.tools.verbs:verbs:1.0.0")
}
```

### Gradle (Groovy)

```groovy
dependencies {
    implementation 'org.kotlin.tools.verbs:verbs:1.0.0'
}
```

## Quick Start

### Basic Usage

```kotlin
import org.kotlin.tools.verbs.*

// Simple conjugation
"accept".conjugate() // returns "accepts"
"break".conjugate() // returns "breaks"

// With custom options
"accept".conjugate(ConjugationOptions(
    tense = Tense.PAST,
    person = Person.FIRST,
    plurality = Plurality.SINGULAR
)) // returns "accepted"
```

### Using the Verbs Object

```kotlin
import org.kotlin.tools.verbs.Verbs.*

// Direct conjugation
Verbs.conjugate("accept") // returns "accepts"

// With subject
Verbs.conjugate("accept", ConjugationOptions(subject = "Matz")) // returns "Matz accepts"

// Check irregular verbs
Verbs.isIrregular("break") // returns true
Verbs.isIrregular("accept") // returns false
```

### Extension Functions

```kotlin
import org.kotlin.tools.verbs.*

// Check if verb is irregular
"break".isIrregular() // returns true

// Conjugate with subject
"accept".conjugate("Matz") // returns "Matz accepts"

// Conjugate with automatic pronoun
"accept".conjugate(true) // returns "he accepts" (default third person)
"accept".conjugate(true, ConjugationOptions(person = Person.FIRST)) // returns "I accept"
```

### DSL for Conjugation Options

```kotlin
import org.kotlin.tools.verbs.*

// Using DSL builder
val options = conjugationOptions {
    tense(Tense.PAST)
    person(Person.FIRST)
    plurality(Plurality.SINGULAR)
    aspect(Aspect.PERFECTIVE)
}

"accept".conjugate(options) // returns "accepted"

// Or using Verbs object
val options2 = Verbs.options {
    tense(Tense.FUTURE)
    person(Person.THIRD)
    plurality(Plurality.SINGULAR)
    aspect(Aspect.PROGRESSIVE)
}

"sleep".conjugate(options2) // returns "will be sleeping"
```

### Multi-word Phrases

```kotlin
import org.kotlin.tools.verbs.*

// Conjugate phrases
"be nice".verb().conjugate("Matz") // returns "Matz is nice"
"go to school".verb().conjugate("I") // returns "I go to school"

// With progressive aspect
"be nice".verb().conjugate("I", ConjugationOptions(
    person = Person.FIRST,
    plurality = Plurality.SINGULAR,
    aspect = Aspect.PROGRESSIVE
)) // returns "I am being nice"
```

### Library Information

```kotlin
import org.kotlin.tools.verbs.*

// Get library status
VerbsLibrary.getLibraryInfo() // returns "Verbs Library v1.0.0 - Initialized: true, Irregular verbs: 4"
VerbsLibrary.getIrregularVerbCount() // returns number of irregular verbs
VerbsLibrary.isInitialized() // returns true
```

## API Reference

### ConjugationOptions

```kotlin
data class ConjugationOptions(
    val tense: Tense = Tense.PRESENT,
    val person: Person = Person.THIRD,
    val plurality: Plurality = Plurality.SINGULAR,
    val aspect: Aspect? = null, // Will be set to default based on tense
    val mood: Mood = Mood.INDICATIVE,
    val diathesis: Diathesis = Diathesis.ACTIVE,
    val subject: String? = null
)
```

### Available Tenses

- `Tense.PRESENT` - Present tense
- `Tense.PAST` - Past tense  
- `Tense.FUTURE` - Future tense

### Available Persons

- `Person.FIRST` - First person (I, we)
- `Person.SECOND` - Second person (you)
- `Person.THIRD` - Third person (he, she, it, they)

### Available Aspects

- `Aspect.HABITUAL` - Habitual actions (default for present/future)
- `Aspect.PERFECTIVE` - Completed actions (default for past)
- `Aspect.PROGRESSIVE` - Ongoing actions
- `Aspect.PERFECT` - Completed actions with present relevance
- `Aspect.PROSPECTIVE` - About to happen

### Available Moods

- `Mood.INDICATIVE` - Statements of fact
- `Mood.IMPERATIVE` - Commands
- `Mood.SUBJUNCTIVE` - Hypothetical situations

## Examples

### Regular Verbs

```kotlin
// Present
"accept".conjugate() // "accepts"
"accept".conjugate(ConjugationOptions(person = Person.FIRST)) // "accept"

// Past
"accept".conjugate(ConjugationOptions(tense = Tense.PAST)) // "accepted"

// Future
"accept".conjugate(ConjugationOptions(tense = Tense.FUTURE)) // "will accept"

// Progressive
"accept".conjugate(ConjugationOptions(aspect = Aspect.PROGRESSIVE)) // "is accepting"
```

### Irregular Verbs

```kotlin
// Break
"break".conjugate() // "breaks"
"break".conjugate(ConjugationOptions(tense = Tense.PAST)) // "broke"

// Be (copula)
"be".conjugate(ConjugationOptions(person = Person.FIRST)) // "am"
"be".conjugate(ConjugationOptions(person = Person.THIRD)) // "is"
"be".conjugate(ConjugationOptions(tense = Tense.PAST, person = Person.FIRST)) // "was"

// Have
"have".conjugate(ConjugationOptions(person = Person.THIRD)) // "has"
"have".conjugate(ConjugationOptions(tense = Tense.PAST)) // "had"
```

### Complex Conjugations

```kotlin
// Past perfect
"know".conjugate(ConjugationOptions(
    tense = Tense.PAST,
    aspect = Aspect.PERFECT
)) // "had known"

// Future progressive
"sleep".conjugate(ConjugationOptions(
    tense = Tense.FUTURE,
    aspect = Aspect.PROGRESSIVE
)) // "will be sleeping"

// Past habitual
"be".conjugate(ConjugationOptions(
    tense = Tense.PAST,
    aspect = Aspect.HABITUAL
)) // "used to be"
```

## Contributing

This library is a Kotlin port of the original Ruby implementation. Contributions are welcome! Please ensure all tests pass before submitting a pull request.

## License

This project is licensed under the same license as the original Ruby implementation.

## Acknowledgments

- Original Ruby implementation by the verbs gem authors
- Kotlin port maintains the same linguistic accuracy and comprehensive coverage
