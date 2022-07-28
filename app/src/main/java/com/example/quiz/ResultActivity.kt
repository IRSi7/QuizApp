package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivityMainBinding
import com.example.quiz.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val username = intent.getStringExtra(Constants.USER_NAME)
        binding.tvName.text = username

        val score = intent.getIntExtra(Constants.FINAL_SCORE, 0)
        val total = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        binding.tvScore.text = "Your score is $score out of $total"

        binding.btnSubmit.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}