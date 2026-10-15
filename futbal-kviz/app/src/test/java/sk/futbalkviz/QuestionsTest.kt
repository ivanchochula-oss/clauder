package sk.futbalkviz

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
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
}
