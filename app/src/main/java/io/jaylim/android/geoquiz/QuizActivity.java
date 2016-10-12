package io.jaylim.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "geoquiz.QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_QUESTION_BANK = "qBank";

    private static final int REQUEST_CODE_CHEAT = 0;

    //
    private TextView mQuestionTextView;

    //
    private Button mTrueButton;
    private Button mFalseButton;

    //
    private ImageButton mPrevButton;
    private ImageButton mNextButton;

    //
    private Button mCheatButton;


    //
    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, true),
            new Question(R.string.question_africas, true),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asias, true)
    };


    /*
    Activity method : onCreate(bundle)
    As soon as the subclass of Activity class is instantiated,
    onCreate(bundle) method is called so that shows UI layout to user and process it.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* BASIC STEP */
        // TODO - What happens on super class?
        super.onCreate(savedInstanceState);

        // Logging
        Log.d(TAG, "onCreate(Bundle) called");

        // Inflate (XML -> Object) and Show (Object -> Display)
        setContentView(R.layout.activity_quiz);


        /*
         * PROCESS [FIND -> CASTING -> IMPL. LISTENER -> SET LISTENER]
         *
         * 1. Find (Id -> View Object) and Casting (View -> ProperType)
         * Find View Object which is constructed by inflation and cast it into proper type.
         *
         * 2. Impl. Listener (By Anonymous Inner Class) and Set Listener
         * Implement Listener Using Anonymous Inner Class and set the listener.
         */
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        // There is no need to set listener since it's just viewer.

        // USER : Is the answer true?
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        // USER : Is the answer false?
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        // USER : Let's go to previous question!
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change index
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
                updateCheatingStatus();
                updateQuestion();
            }
        });

        // USER : Let's go to next question!
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change index
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateCheatingStatus();
                updateQuestion();
            }
        });

        // USER : Let's cheat!
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the correct answer
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                // Construct intent
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                // Start Activity expecting result back with request code
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check result code -> Was setResult() called?
        if (resultCode != AppCompatActivity.RESULT_OK) {
            return;
        }

        // check request code -> Is returned request matched with what we've expected.
        if (requestCode == REQUEST_CODE_CHEAT) {
            // Is there any intent packaged with?
            if (data == null) {
                return;
            }
            // Open the intent from child activity.
            // Check whether the cheated status is changed.
            if (!mQuestionBank[mCurrentIndex].isCheated()) {
                mQuestionBank[mCurrentIndex].setCheated(CheatActivity.wasAnswerShown(data));
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");

        // Save the user's current state
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putParcelableArray(KEY_QUESTION_BANK, mQuestionBank);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore per-instance state
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            Parcelable[] p = savedInstanceState.getParcelableArray(KEY_QUESTION_BANK);
            mQuestionBank = Arrays.copyOf(p, p.length, Question[].class);
        }
        // update question view using restored data
        updateQuestion();
    }

    /* update question text using mCurrentIndex */
    private void updateQuestion() {
        Log.d(TAG, "updateQuestion()");

        // Find a question from 'Question Bank' so that get the resource id of the question text.
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        // Set the question text on the text view.
        mQuestionTextView.setText(question);
    }

    private void updateCheatingStatus() {
        // Udate cheating status
    }

    private void checkAnswer(boolean userPressedTrue) {

        // Actual answer of the question
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        // resource id
        int messageResId = 0;

        if (mQuestionBank[mCurrentIndex].isCheated()) {
            messageResId = R.string.judgement_toast;
        } else {

            // select resource id
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }

        }
        // pop up toast
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
