package sk.futbalkviz

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import sk.futbalkviz.data.Clubs
import sk.futbalkviz.data.Questions

class QuestionsTest {

    @Test
    fun poolIsLargeEnoughForARound() {
        assertTrue(Questions.all.size >= QUESTIONS_PER_ROUND)
    }

    @Test
    fun everyQuestionHasFourDistinctOptions() {
        Questions.all.forEach { q ->
            val options = q.wrong + q.answer
            assertEquals(q.text, 4, options.size)
            assertEquals(q.text, 4, options.toSet().size)
            assertFalse(q.text, q.explanation.isBlank())
        }
    }

    @Test
    fun questionTextsAreUnique() {
        assertEquals(Questions.all.size, Questions.all.map { it.text }.toSet().size)
    }

    @Test
    fun roundPointsAtCorrectAnswer() {
        repeat(20) {
            val round = buildRound(Questions.all)
            assertEquals(QUESTIONS_PER_ROUND, round.size)
            assertEquals(QUESTIONS_PER_ROUND, round.map { it.text }.toSet().size)
            round.forEach { rq ->
                val source = Questions.all.first { it.text == rq.text }
                assertEquals(source.answer, rq.options[rq.correctIndex])
            }
        }
    }

    @Test
    fun fasterAnswerScoresMore() {
        assertTrue(pointsFor(14) > pointsFor(3))
        assertEquals(10, pointsFor(0))
    }

    @Test
    fun clubKeysAndNamesAreUnique() {
        assertEquals(Clubs.all.size, Clubs.all.map { it.key }.toSet().size)
        assertEquals(Clubs.all.size, Clubs.all.map { it.name }.toSet().size)
    }

    @Test
    fun clubKeysMatchDownloadScript() {
        val script = java.io.File("../scripts/download_logos.py").readText()
        Clubs.all.forEach { club ->
            assertTrue("${club.key} chýba v download_logos.py", script.contains("\"${club.key}\":"))
        }
    }

    @Test
    fun logoRoundPointsAtCorrectClub() {
        repeat(20) {
            val round = buildLogoRound(Clubs.all)
            assertEquals(QUESTIONS_PER_ROUND, round.size)
            round.forEach { rq ->
                val club = Clubs.all.first { it.key == rq.logoKey }
                assertEquals(club.name, rq.options[rq.correctIndex])
                assertEquals(4, rq.options.toSet().size)
            }
        }
    }

    @Test
    fun logoRoundWorksWithFewLogos() {
        val round = buildLogoRound(Clubs.all.take(4))
        assertEquals(4, round.size)
        round.forEach { assertEquals(4, it.options.toSet().size) }
    }
}
