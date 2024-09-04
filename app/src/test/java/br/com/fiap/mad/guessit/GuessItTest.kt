package br.com.fiap.mad.guessit

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GuessItTest {

    val mainActivity = { test: (MainActivity) -> Unit ->
        Robolectric.buildActivity(MainActivity::class.java).use { controller ->
            controller.setup()

            val activity = controller.get()

            test(activity)
        }
    }

    @Test
    fun shouldShowWelcomeMessage() {
        Robolectric.buildActivity(MainActivity::class.java).use { controller ->
            controller.setup()

            val activity = controller.get()
            val actual = activity.findViewById<TextView>(R.id.mainTextView).text
            assertEquals("Adivinhe o número entre 1 e 25", actual)
        }
    }


    @Test
    fun shouldHaveRightTexts() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            assertEquals("", editText.text.toString())
            val restartButton = activity.findViewById<Button>(R.id.restartButton)
            assertEquals("Jogar novamente", restartButton.text)
            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            assertEquals("Adivinhar", guessButton.text)
        }
    }

    @Test
    fun shouldHideRestartButton() {
        mainActivity { activity ->
            val restartButton = activity.findViewById<Button>(R.id.restartButton)

            assertEquals(View.GONE, restartButton.visibility)
        }
    }

    @Test
    fun shouldHideRemainingAttempts() {
        mainActivity { activity ->
            val remainingAttempts = activity.findViewById<TextView>(R.id.remainingAttemptsTextView)

            assertEquals(View.GONE, remainingAttempts.visibility)
        }
    }

    @Test
    fun shouldHideErrorMessage() {
        mainActivity { activity ->
            val errorMessage = activity.findViewById<TextView>(R.id.errorMessageTextView)
            assertEquals(View.INVISIBLE, errorMessage.visibility)
        }
    }

    @Test
    fun shouldShowErrorWithInvalidInput() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("26")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            guessButton.performClick()

            val errorMessage = activity.findViewById<TextView>(R.id.errorMessageTextView)
            assertEquals(View.VISIBLE, errorMessage.visibility)
            assertEquals("Insira um valor válido!", errorMessage.text)

            editText.setText("12")
            guessButton.performClick()
            assertEquals(View.INVISIBLE, errorMessage.visibility)
        }
    }


    @Test
    fun shouldShowRemainingAttempts() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("12")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            guessButton.performClick()

            val remainingAttempts = activity.findViewById<TextView>(R.id.remainingAttemptsTextView)
            assertEquals("Tentativas restantes: 9", remainingAttempts.text)
            assertEquals(View.VISIBLE, remainingAttempts.visibility)
        }
    }

    @Test
    fun shouldShowLastAttempt() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("12")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            repeat(9) {
                guessButton.performClick()
            }

            val remainingAttempts = activity.findViewById<TextView>(R.id.remainingAttemptsTextView)
            assertEquals("Última tentativa!", remainingAttempts.text)
            assertEquals(View.VISIBLE, remainingAttempts.visibility)
        }
    }

    @Test
    fun shouldProvideFeedbackBasedOnGuessComparison() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("12")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            guessButton.performClick()

            val actual = activity.findViewById<TextView>(R.id.mainTextView).text
            assertNotEquals("Adivinhe o número entre 1 e 25", actual)

            val feedback = setOf("Muito alto!", "Muito baixo!", "Parabéns! Você acertou.")
            assertTrue(actual in feedback)
        }
    }

    @Test
    fun shouldDisplayMessageWhenGuessIsLowerThanTarget() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("1")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            guessButton.performClick()

            val actual = activity.findViewById<TextView>(R.id.mainTextView).text
            assertNotEquals("Adivinhe o número entre 1 e 25", actual)

            val feedback = setOf("Muito baixo!", "Parabéns! Você acertou.")
            assertTrue(actual in feedback)
        }
    }

    @Test
    fun shouldDisplayMessageWhenGuessIsHigherThanTarget() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("25")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            guessButton.performClick()

            val actual = activity.findViewById<TextView>(R.id.mainTextView).text
            val feedback = setOf("Muito alto!", "Parabéns! Você acertou.")
            assertTrue(actual in feedback)
        }
    }

    @Test
    fun shouldShowGameOverWhenMissAllAttempts() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("25")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            guessButton.performClick()

            val mainTextView = activity.findViewById<TextView>(R.id.mainTextView)

            if (mainTextView.text == "Parabéns! Você acertou.") {
                return@mainActivity shouldShowGameOverWhenMissAllAttempts()
            }

            repeat(9) {
                guessButton.performClick()
            }

            println("nao atualizou")
            assertTrue("Que pena, você não acertou o número \\d+!".toRegex().matches(mainTextView.text))
        }
    }

    @Test
    fun shouldShowRestartButtonWhenGameIsFinished() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("25")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)

            repeat(10) {
                guessButton.performClick()
            }

            assertFalse(guessButton.isEnabled)
            val restartButton = activity.findViewById<Button>(R.id.restartButton)
            assertEquals(View.VISIBLE, restartButton.visibility)
        }
    }

    @Test
    fun shouldRestartGame() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("25")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            repeat(10) {
                guessButton.performClick()
            }

            val restartButton = activity.findViewById<Button>(R.id.restartButton)
            restartButton.performClick()

            val actual = activity.findViewById<TextView>(R.id.mainTextView).text

            assertEquals("Adivinhe o número entre 1 e 25", actual)
            assertEquals("", editText.text.toString())
            assertEquals(View.GONE, restartButton.visibility)
            assertTrue(guessButton.isEnabled)
            val remainingAttempts = activity.findViewById<TextView>(R.id.remainingAttemptsTextView)
            assertEquals(View.GONE, remainingAttempts.visibility)
        }
    }

    @Test
    fun shouldResetAttempts() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("25")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            repeat(10) {
                guessButton.performClick()
            }

            val restartButton = activity.findViewById<Button>(R.id.restartButton)
            restartButton.performClick()

            editText.setText("25")
            guessButton.performClick()

            val remainingAttempts = activity.findViewById<TextView>(R.id.remainingAttemptsTextView)
            assertEquals("Tentativas restantes: 9", remainingAttempts.text)
            assertEquals(View.VISIBLE, remainingAttempts.visibility)
        }
    }

    @Test
    fun shouldChangeRandomNumber() {
        mainActivity { activity ->
            val editText = activity.findViewById<EditText>(R.id.guessNumberEditText)
            editText.setText("25")

            val guessButton = activity.findViewById<Button>(R.id.guessButton)
            repeat(10) {
                guessButton.performClick()
            }

            val mainTextView = activity.findViewById<TextView>(R.id.mainTextView)
            val mainText = mainTextView.text.toString()

            val secretNumber = if (mainText == "Parabéns! Você acertou.") {
                editText.text.toString()
            } else {
                val regex = Regex("Que pena, você não acertou o número (\\d+)!")
                val matchResult = regex.matchEntire(mainText)
                val number = matchResult?.groups?.get(1)?.value
                number ?: "25"
            }

            val restartButton = activity.findViewById<Button>(R.id.restartButton)
            restartButton.performClick()

            editText.setText(secretNumber)
            guessButton.performClick()

            val feedback = setOf("Muito alto!", "Muito baixo!")
            assertTrue(mainTextView.text in feedback)
        }
    }
}