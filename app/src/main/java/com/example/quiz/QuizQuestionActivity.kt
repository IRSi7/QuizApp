package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.quiz.databinding.ActivityMainBinding
import com.example.quiz.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val questionList = Constants.getQuestions()
        val count = 1
        val maxCount = questionList.size
        binding.progressBar.max = questionList.size

        val q = questionList[0]
       // for (q in questionList) {
            binding.bodyQuestion.text = q.text
            binding.ivImage.setImageResource(q.image)
            binding.firstAnswer.text = q.optionOne
            binding.secondAnswer.text = q.optionTwo
            binding.thirdAnswer.text = q.optionThree
            binding.fourthAnswer.text = q.optionFour
            binding.progressBar.progress = count
            binding.tvProgress.text = "$count / $maxCount"

        //}
    }
}