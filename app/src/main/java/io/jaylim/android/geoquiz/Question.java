package io.jaylim.android.geoquiz;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;

/**
 * Created by iejis on 2016-09-30.
 */

public class Question {
  private int mTextResId;
  private boolean mAnswerTrue;

  public Question(int textResId, boolean answerTrue) {
    mTextResId = textResId;
    mAnswerTrue = answerTrue;
  }


  public int getTextResId() {
    return mTextResId;
  }

  public void setTextResId(int textResId) {
    mTextResId = textResId;
  }

  public boolean isAnswerTrue() {
    return mAnswerTrue;
  }

  public void setAnswerTrue(boolean answerTrue) {
    mAnswerTrue = answerTrue;
  }

}
