package org.kotlin.tools.verbs

import org.junit.Test
import org.junit.Assert.*

class VerbsTest {
    
    init {
        // Initialize the library for testing
        VerbsLibrary.initialize()
    }
    
    @Test
    fun testInitialization() {
        // Check if irregular verbs are loaded
        val irregularCount = Conjugator.getIrregularCount()
        println("Loaded $irregularCount irregular verbs")
        assertTrue("Should have loaded irregular verbs", irregularCount > 0)
        
        // Check if specific irregular verbs are loaded
        assertTrue("'know' should be irregular", Conjugator.conjugate("know", ConjugationOptions(tense = Tense.PAST)) == "knew")
    }
    
    @Test
    fun testCopularConjugation() {
        // Present habitual
        assertEquals("am", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("are", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.SECOND, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("is", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        // Past
        assertEquals("was", Conjugator.conjugate("be", ConjugationOptions(tense = Tense.PAST)))
        
        // Past habitual
        assertEquals("used to be", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR, 
            aspect = Aspect.HABITUAL
        )))
        
        // Past perfective
        assertEquals("was", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("were", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.SECOND, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        // Progressive
        assertEquals("was being", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.THIRD, 
            aspect = Aspect.PROGRESSIVE
        )))
        
        assertEquals("is being", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            aspect = Aspect.PROGRESSIVE
        )))
        
        assertEquals("will be being", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.FUTURE, 
            person = Person.THIRD, 
            aspect = Aspect.PROGRESSIVE
        )))
    }
    
    @Test
    fun testIrregularConjugation() {
        assertEquals("break", Conjugator.conjugate("break", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("breaks", Conjugator.conjugate("break", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("broke", Conjugator.conjugate("break", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("has", Conjugator.conjugate("have", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
    }
    
    @Test
    fun testKnow() {
        assertEquals("knew", Conjugator.conjugate("know", ConjugationOptions(tense = Tense.PAST)))
        
        assertEquals("had known", Conjugator.conjugate("know", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECT
        )))
        
        assertEquals("knew", Conjugator.conjugate("know", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("was knowing", Conjugator.conjugate("know", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PROGRESSIVE
        )))
    }
    
    @Test
    fun testIrregularConjugationWithTerminalY() {
        assertEquals("flies", Conjugator.conjugate("fly", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("carried", Conjugator.conjugate("carry", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("stayed", Conjugator.conjugate("stay", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
    }
    
    @Test
    fun testRegularConjugation() {
        assertEquals("accept", Conjugator.conjugate("accept", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("accepts", Conjugator.conjugate("accept", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("accepted", Conjugator.conjugate("accept", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
    }
    
    @Test
    fun testRegularConjugationWithTerminalSingleConsonant() {
        assertEquals("shipped", Conjugator.conjugate("ship", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
    }
    
    @Test
    fun testRegularConjugationWithIrregularTerminalConsonant() {
        assertEquals("abandoned", Conjugator.conjugate("abandon", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("followed", Conjugator.conjugate("follow", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("triggered", Conjugator.conjugate("trigger", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("colored", Conjugator.conjugate("color", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("delivered", Conjugator.conjugate("deliver", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
    }
    
    @Test
    fun testRegularConjugationWithEanSuffix() {
        assertEquals("cleaned", Conjugator.conjugate("clean", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("am cleaning", Conjugator.conjugate("clean", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PROGRESSIVE
        )))
    }
    
    @Test
    fun testRegularNonDoubledEndingConsonant() {
        assertEquals("fixes", Conjugator.conjugate("fix", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("fixed", Conjugator.conjugate("fix", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
        
        assertEquals("faxed", Conjugator.conjugate("fax", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
    }
    
    @Test
    fun testRegularConjugationWithTerminalC() {
        assertEquals("mimicked", Conjugator.conjugate("mimic", ConjugationOptions(
            tense = Tense.PAST, 
            aspect = Aspect.PERFECTIVE
        )))
    }
    
    @Test
    fun testRegularConjugationWithUnusualTerminalE() {
        assertEquals("dyed", Conjugator.conjugate("dye", ConjugationOptions(
            tense = Tense.PAST, 
            person = Person.FIRST, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PERFECTIVE
        )))
    }
    
    @Test
    fun testConjugationWithTerminalSibilance() {
        assertEquals("passes", Conjugator.conjugate("pass", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
        
        assertEquals("passes", Conjugator.conjugate("pass", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL
        )))
    }
    
    @Test
    fun testConjugationWithSubject() {
        assertEquals("Matz is", Conjugator.conjugate("be", ConjugationOptions(
            tense = Tense.PRESENT, 
            person = Person.THIRD, 
            plurality = Plurality.SINGULAR,
            aspect = Aspect.HABITUAL,
            subject = "Matz"
        )))
    }
    
    @Test
    fun testConjugationWithFalseSubject() {
        assertEquals("accepts", Conjugator.conjugate("accept", ConjugationOptions(subject = null)))
    }
    
    @Test
    fun testExtensionFunctions() {
        assertEquals("accepts", "accept".conjugate())
        assertEquals("Matz accepts", "accept".conjugate("Matz"))
        assertEquals("he accepts", "accept".conjugate(true))
        
        assertEquals("Matz is nice", "be nice".verb().conjugate("Matz"))
        assertEquals("I will be sleeping", "sleep".verb().conjugate(true, ConjugationOptions(
            tense = Tense.FUTURE,
            person = Person.FIRST,
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PROGRESSIVE
        )))
    }
    
    @Test
    fun testDSLBuilder() {
        val options = conjugationOptions {
            tense(Tense.PAST)
            person(Person.FIRST)
            plurality(Plurality.SINGULAR)
            aspect(Aspect.PERFECTIVE)
        }
        
        assertEquals("accepted", "accept".conjugate(options))
    }
    
    @Test
    fun testSubjectFunction() {
        val options = ConjugationOptions(
            person = Person.FIRST,
            plurality = Plurality.SINGULAR
        )
        assertEquals("I", Conjugator.subject(options))
        
        val options2 = ConjugationOptions(
            person = Person.SECOND,
            plurality = Plurality.PLURAL
        )
        assertEquals("you", Conjugator.subject(options2))
        
        val options3 = ConjugationOptions(
            person = Person.THIRD,
            plurality = Plurality.SINGULAR
        )
        assertEquals("he", Conjugator.subject(options3))
        
        val options4 = ConjugationOptions(
            person = Person.THIRD,
            plurality = Plurality.PLURAL
        )
        assertEquals("they", Conjugator.subject(options4))
    }

    @Test
    fun testVerbClassFeatures() {
        val verb = Verb("test", "tested", "tested")
        
        // Test hasForm
        assertFalse(verb.hasForm(ConjugationOptions()))
        
        // Test getAllForms
        assertTrue(verb.getAllForms().isEmpty())
        
        // Test toString
        assertTrue(verb.toString().contains("test"))
        
        // Test companion object methods
        val options = VerbFormOptions.forPerson(Tense.PRESENT, Person.FIRST, Plurality.SINGULAR)
        assertEquals(Tense.PRESENT, options.tense)
        assertEquals(Person.FIRST, options.person)
        assertEquals(Plurality.SINGULAR, options.plurality)
    }
    
    @Test
    fun testVerbsLibrary() {
        assertTrue(VerbsLibrary.isInitialized())
        assertTrue(VerbsLibrary.getIrregularVerbCount() > 0)
        assertTrue(VerbsLibrary.isIrregular("break"))
        assertFalse(VerbsLibrary.isIrregular("accept"))
        
        assertEquals("accepts", VerbsLibrary.conjugate("accept"))
        assertEquals("Matz accepts", VerbsLibrary.conjugate("accept", ConjugationOptions(subject = "Matz")))
        
        val options = ConjugationOptions(person = Person.FIRST, plurality = Plurality.SINGULAR)
        assertEquals("I", VerbsLibrary.getSubject(options))
        
        val info = VerbsLibrary.getLibraryInfo()
        assertTrue(info.contains("Verbs Library"))
        assertTrue(info.contains("Initialized: true"))
    }
    
    @Test
    fun testVerbsObject() {
        assertEquals("accepts", Verbs.conjugate("accept"))
        assertEquals("Matz accepts", Verbs.conjugate("accept", ConjugationOptions(subject = "Matz")))
        assertTrue(Verbs.isIrregular("break"))
        assertFalse(Verbs.isIrregular("accept"))
        
        val options = ConjugationOptions(person = Person.FIRST, plurality = Plurality.SINGULAR)
        assertEquals("I", Verbs.getSubject(options))
        
        val info = Verbs.getInfo()
        assertTrue(info.contains("Verbs Library"))
        
        // Test DSL options
        val options2 = Verbs.options {
            tense(Tense.PAST)
            person(Person.FIRST)
            plurality(Plurality.SINGULAR)
            aspect(Aspect.PERFECTIVE)
        }
        assertEquals("accepted", Verbs.conjugate("accept", options2))
        
        // Test verb wrapper
        assertEquals("Matz is nice", Verbs.verb("be nice").conjugate("Matz"))
    }
    
    @Test
    fun testExtensions() {
        // Test isIrregular extension
        assertTrue("break".isIrregular())
        assertFalse("accept".isIrregular())
        
        // Test verb wrapper with complex phrases
        assertEquals("Matz goes to school", "go to school".verb().conjugate("Matz"))
        assertEquals("I am being nice", "be nice".verb().conjugate(true, ConjugationOptions(
            person = Person.FIRST,
            plurality = Plurality.SINGULAR,
            aspect = Aspect.PROGRESSIVE
        )))
        
        // Test DSL conjugation options
        val options = conjugationOptions {
            tense(Tense.FUTURE)
            person(Person.THIRD)
            plurality(Plurality.SINGULAR)
            aspect(Aspect.PROGRESSIVE)
        }
        assertEquals("will be sleeping", "sleep".conjugate(options))
    }
}
