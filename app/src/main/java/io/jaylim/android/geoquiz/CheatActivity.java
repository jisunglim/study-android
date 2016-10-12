package io.jaylim.android.geoquiz;

import static android.app.Activity.RESULT_OK;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    // Logging tag
    private static final String TAG = "geoquiz.CheatActivity";

    // Extra data which is to be set in intent.
    private static final String EXTRA_ANSWER_IS_TRUE =
            "io.jaylim.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "io.jaylim.android.geoquiz.answer_shown";

    // The key which is to be saved with value(instance state)
    private static final String KEY_IS_CHEATED = "isCheated";

    // Instance states
    private boolean mIsCheated = false;
    private boolean mAnswerIsTrue;

    // Inflated View Objects
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private TextView mVersionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle)");
        /* BASIC ROUTINE*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // Get answer from parent activity
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // find objects
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        Log.i(TAG,"Is mShowAnswer visible? " + mShowAnswer.isCursorVisible());
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
                mIsCheated = true;
            }
        });

        mVersionTextView = (TextView) findViewById(R.id.version_text_view);
        mVersionTextView.setText("API Level : " + Build.VERSION.SDK_INT);
        Log.i(TAG, "mShowAnswer => " + mShowAnswer + " | " + mShowAnswer.getWidth() + " | " + mShowAnswer.getHeight());


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        // Store the per-instance state
        outState.putBoolean(KEY_IS_CHEATED, mIsCheated);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (mIsCheated = savedInstanceState.getBoolean(KEY_IS_CHEATED, false)) {
                showAnswer();
                mIsCheated =true;
            }
        }
    }

    // Referring mAnswerIsTrue, show the answer.
    private void showAnswer() {
        Log.i(TAG, "showAnswer()");
        // Show the answer
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_text);
        } else {
            mAnswerTextView.setText(R.string.false_text);
        }

        Log.i(TAG, "mShowAnswer => " + mShowAnswer + " | " + mShowAnswer.getWidth() + " | " + mShowAnswer.getHeight());

        if ((!mIsCheated) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = mShowAnswer.getWidth()/2;
            int cy = mShowAnswer.getHeight()/2;

            float radius = mShowAnswer.getWidth()/2;

            Animator anim = ViewAnimationUtils
                    .createCircularReveal(mShowAnswer, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            mShowAnswer.setVisibility(View.INVISIBLE);
        }

        // Set Result true so that can be sent to parent activity.
        setAnswerShownResult();
    }

    /*
     * Specifically set "the answer shown" Result
     * (Tip : Optimized Result Setter)
     */
    private void setAnswerShownResult() {
        Log.i(TAG, "setAnswerShownResult()");
        // Create an empty intent
        Intent data = new Intent();
        // Set extra data (isAnswerShown)
        data.putExtra(EXTRA_ANSWER_SHOWN, true);
        // Set result
        setResult(RESULT_OK, data);
    }

    /*
     * This method is for caller who want to request CheatActivity to do some action.
     * (Tip : Optimized "request intent" generator)
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Log.i(TAG, "newIntent(Context, boolean)");
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    /*
     * This method is for caller who want to know whether the answer was cheated on CheatActivity.
     * (Tip : Optimized "result intent" opener.)
     */
    public static boolean wasAnswerShown(Intent result) {
        Log.i(TAG, "wasAnswerShown(Intent)");
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }


}
