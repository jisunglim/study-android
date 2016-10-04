package io.jaylim.android.geoquiz;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

  private static final String TAG = "geoquiz.QuizActivity";
  private static final String KEY_INDEX = "index";

  //
  private TextView mQuestionTextView;

  //
  private Button mTrueButton;
  private Button mFalseButton;

  //
  private ImageButton mPrevButton;
  private ImageButton mNextButton;

  //
  private Question[] mQuestionBank = new Question[] {
      new Question(R.string.question_oceans, true),
      new Question(R.string.question_mideast, true),
      new Question(R.string.question_africas, true),
      new Question(R.string.question_americas, true),
      new Question(R.string.question_asias, true)
  };

  private int mCurrentIndex = 0;

  /*
  Activity method : onCreate(bundle)
  As soon as the subclass of Activity class is instantiated,
  onCreate(bundle) method is called so that shows UI layout to user and process it.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO - What happens on super class?
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }

    // Logging
    Log.d(TAG, "onCreate(Bundle) called");

    // Inflate (XML -> Object) and Show (Object -> Display)
    setContentView(R.layout.activity_quiz);


    // Find (Id -> View Object) and Casting (View -> TextView)
    mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

    // Impl. Listener (By Anonymous Inner Class) and Set Listener
    mQuestionTextView.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
      }
    });


    // Find (Id -> View Object) and Show (View -> Button)
    mTrueButton = (Button) findViewById(R.id.true_button);

    // Impl. Listener (By Anonymous Inner Class) and Set Listener
    mTrueButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            checkAnswer(true);
          }
        }
    );


    mFalseButton = (Button) findViewById(R.id.false_button);
    mFalseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(false);
      }
    });


    mPrevButton = (ImageButton) findViewById(R.id.prev_button);
    mPrevButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
        updateQuestion();
      }
    });


    mNextButton = (ImageButton) findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
      }
    });

    updateQuestion();

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

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.i(TAG, "onSaveInstanceState");
    outState.putInt(KEY_INDEX, mCurrentIndex);
  }

  private void updateQuestion() {
//    Log.d(TAG, "Updating question text for question # " + mCurrentIndex, new Exception());

    // Find a question from 'Question Bank' so that get the resource id of the question text.
    int question = mQuestionBank[mCurrentIndex].getTextResId();
    // Set the question text on the text view.
    mQuestionTextView.setText(question);
  }

  private void checkAnswer(boolean userPressedTrue) {

    // Actual answer of the question
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

    // resource id
    int messageResId = 0;

    // select resource id
    if (userPressedTrue == answerIsTrue) {
      messageResId = R.string.correct_toast;
    } else {
      messageResId = R.string.incorrect_toast;
    }

    // pop up toast
    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        .show();
  }

}
