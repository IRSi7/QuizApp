package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quiz.databinding.ActivityMainBinding
import com.example.quiz.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityQuizQuestionBinding
    private var mCurrentPosition : Int = 1
    private var mQuestionList : ArrayList<Question>? = null
    private var mSelectedOptionPosition : Int = 0
    private var mCorrectAnswers: Int = 0
    private var mMultiSelect = false
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionList = Constants.getQuestions()
        binding.progressBar.max = mQuestionList!!.size

        setQuestion()

        binding.firstAnswer.setOnClickListener(this)
        binding.secondAnswer.setOnClickListener(this)
        binding.thirdAnswer.setOnClickListener(this)
        binding.fourthAnswer.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }


    private fun setQuestion(){

        val q = mQuestionList!![mCurrentPosition - 1]

        defaultOptionsView()

        if (mCurrentPosition == mQuestionList!!.size) {
            binding.btnSubmit.text = "FINISH"
        }else{
            binding.btnSubmit.text = "SUBMIT"
        }

        binding.bodyQuestion.text = q.text
        binding.ivImage.setImageResource(q.image)
        binding.firstAnswer.text = q.optionOne
        binding.secondAnswer.text = q.optionTwo
        binding.thirdAnswer.text = q.optionThree
        binding.fourthAnswer.text = q.optionFour
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition / ${binding.progressBar.max}"
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(binding.firstAnswer)
        options.add(binding.secondAnswer)
        options.add(binding.thirdAnswer)
        options.add(binding.fourthAnswer)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.firstAnswer -> {
                selectedOptionView(binding.firstAnswer, 1)
            }
            R.id.secondAnswer -> {
                selectedOptionView(binding.secondAnswer, 2)
            }
            R.id.thirdAnswer -> {
                selectedOptionView(binding.thirdAnswer, 3)
            }
            R.id.fourthAnswer -> {
                selectedOptionView(binding.fourthAnswer, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            mMultiSelect = false
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java).apply {
                                putExtra(Constants.FINAL_SCORE, mCorrectAnswers)
                                putExtra(Constants.USER_NAME, mUserName)
                                putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                            }
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else if(!mMultiSelect){
                        mCorrectAnswers++
                        mMultiSelect = true
                    }
                    answerView(question!!.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList!!.size) {
                        binding.btnSubmit.text = "FINISH"
                    }else{
                        binding.btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> {
                binding.firstAnswer.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 -> {
                binding.secondAnswer.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 -> {
                binding.thirdAnswer.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 -> {
                binding.fourthAnswer.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            }
        }

    private fun selectedOptionView(tv : TextView,
                                   selectedOption : Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOption
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg)
    }
}