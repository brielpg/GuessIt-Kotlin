package br.com.fiap.mad.guessit

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mainTextView: TextView
    private lateinit var guessEditText: EditText
    private lateinit var guessButton: Button
    private lateinit var restartButton: Button
    private lateinit var remainingAttemptsTextView: TextView
    private lateinit var errorMessageTextView: TextView
    private var nmrAleatorio = 0
    private var tentativas = 0
    private var validRange = (1..25)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainTextView = findViewById(R.id.mainTextView)
        guessEditText = findViewById(R.id.guessNumberEditText)
        guessButton = findViewById(R.id.guessButton)
        restartButton = findViewById(R.id.restartButton)
        remainingAttemptsTextView = findViewById(R.id.remainingAttemptsTextView)
        errorMessageTextView = findViewById(R.id.errorMessageTextView)

        novoJogo()
    }

    fun adivinhar(view: View){
        val palpite = guessEditText.text.toString()
        errorMessageTextView.visibility = View.INVISIBLE

        if (palpite.isBlank() || palpite.toIntOrNull() !in validRange){
            errorMessageTextView.visibility = View.VISIBLE
            errorMessageTextView.text = "Insira um valor válido!"
            return
        }

        tentativas--
        remainingAttemptsTextView.visibility = View.VISIBLE
        remainingAttemptsTextView.text = if (tentativas == 1) "Última tentativa!" else "Tentativas restantes: $tentativas"

        verificarPalpite(palpite.toInt())

        perder()
    }

    fun reiniciar(view: View){
        novoJogo()
    }

    private fun perder(){
        if(tentativas <= 0){
            mainTextView.text = "Que pena, você não acertou o número $nmrAleatorio!"
            guessButton.isEnabled = false
            restartButton.visibility = View.VISIBLE
        }
    }

    private fun verificarPalpite(palpite1: Int){
        when {
            palpite1 > nmrAleatorio -> mainTextView.text = "Muito alto!"
            palpite1 < nmrAleatorio -> mainTextView.text = "Muito baixo!"
            else -> {
                mainTextView.text = "Parabéns! Você acertou."
                guessButton.isEnabled = false
                restartButton.visibility = View.VISIBLE
            }
        }
    }

    private fun novoJogo(){
        nmrAleatorio = validRange.random()
        tentativas = 10
        mainTextView.text = "Adivinhe o número entre 1 e 25"
        guessEditText.setText("")
        guessButton.isEnabled = true
        errorMessageTextView.visibility = View.INVISIBLE
        remainingAttemptsTextView.visibility = View.GONE
        restartButton.visibility = View.GONE
    }
}