package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mAnteriorButton;
    private TextView mQuestionTextView;
    private TextView mTextCounter;
    private int mCurrentIndex = 0;  // Índice para las preguntas
    private int mCorrectAnswers = 0;  // Contador de respuestas correctas

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.string1, true),
            new Question(R.string.string2, false),
            new Question(R.string.string3, false),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Enlazar los botones y TextViews
        mTrueButton = findViewById(R.id.mTrueButton);
        mFalseButton = findViewById(R.id.mFalseButton);
        mNextButton = findViewById(R.id.mNextButton);
        mAnteriorButton = findViewById(R.id.mAnteriorButton);
        mQuestionTextView = findViewById(R.id.mQuestionTextView);
        mTextCounter = findViewById(R.id.mTextCounter);

        // Mostrar la primera pregunta
        updateQuestion();

        // Listeners para los botones
        mTrueButton.setOnClickListener(v -> {
            checkAnswer(true);
            disableAnswerButtons();  // Desactivar los botones tras responder
        });

        mFalseButton.setOnClickListener(v -> {
            checkAnswer(false);
            disableAnswerButtons();  // Desactivar los botones tras responder
        });

        mNextButton.setOnClickListener(v -> {
            // Cambiar a la siguiente pregunta
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
            enableAnswerButtons();
        });

        mAnteriorButton.setOnClickListener(v -> {
            // Cambiar a la pregunta anterior
            if (mCurrentIndex > 0) {
                mCurrentIndex--;
            } else {
                mCurrentIndex = mQuestionBank.length - 1;
            }
            updateQuestion();
            enableAnswerButtons();
        });
    }

    // Método para actualizar la pregunta en el TextView
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    // Método para verificar la respuesta
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        if (userPressedTrue == answerIsTrue) {
            Toast.makeText(QuizActivity.this, "Correct answer!", Toast.LENGTH_SHORT).show();
            mCorrectAnswers++;
            updateCorrectAnswerCount();  // Actualizar contador de respuestas correctas
        } else {
            Toast.makeText(QuizActivity.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para actualizar el contador de respuestas correctas
    private void updateCorrectAnswerCount() {
        mTextCounter.setText("Correct answers: " + mCorrectAnswers);
    }

    // Desactivar botones de respuesta
    private void disableAnswerButtons() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    // Reactivar botones de respuesta
    private void enableAnswerButtons() {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }
}

