# Verbs Library

A comprehensive Kotlin library for English verb conjugation, ported from the original Ruby implementation.

## Features

- Complete conjugation support for all tenses, persons, aspects, and moods
- Irregular verb handling with custom forms
- Fluent API with multiple conjugation methods
- Extension functions for natural verb conjugation
- DSL support with builder pattern
- Multi-word phrase support
- Type safety with sealed classes and enums

## Quick Start

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

## Installation

```kotlin
implementation("org.kotlin.tools.verbs:verbs:1.0.0")
``` 