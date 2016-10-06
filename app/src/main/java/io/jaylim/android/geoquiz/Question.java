package io.jaylim.android.geoquiz;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iejis on 2016-09-30.
 */

public class Question implements Parcelable {
  private int mTextResId;
  private boolean mAnswerTrue;
  private boolean mCheated;

  public Question(int textResId, boolean answerTrue) {
    mTextResId = textResId;
    mAnswerTrue = answerTrue;
    mCheated = false;
  }

  public Question(int textResId, boolean answerTrue, boolean cheated) {
    mTextResId = textResId;
    mAnswerTrue = answerTrue;
    mCheated = cheated;
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

  public boolean isCheated() {
    return mCheated;
  }

  public void setCheated(boolean cheated) {
    mCheated = cheated;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(mTextResId);
    dest.writeByte((byte) (mAnswerTrue ? 1 : 0));
    dest.writeByte((byte) (mCheated ? 1 : 0));
  }

  public static final Parcelable.Creator<Question> CREATOR =
          new Parcelable.Creator<Question>() {
            @Override
            public Question createFromParcel(Parcel source) {
              return new Question(source);
            }

            @Override
            public Question[] newArray(int size) {
              return new Question[size];
            }
          };

  private Question(Parcel source) {
    mTextResId = source.readInt();
    mAnswerTrue = source.readByte() != 0;
    mCheated = source.readByte() != 0;
  }
}
