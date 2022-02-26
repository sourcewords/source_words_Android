package com.example.sourcewords.ui.login;

import android.os.CountDownTimer;

/**
 * 点击按钮后倒计时
 */
public class CountTimer extends CountDownTimer {
    private CountTimer countTimer;

    public CountTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * 倒计时过程中调用
     *
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {

    }

    /**
     * 倒计时完成后调用
     */
    @Override
    public void onFinish() {

    }
}