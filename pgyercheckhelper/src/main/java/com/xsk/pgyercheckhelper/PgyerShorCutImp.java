package com.xsk.pgyercheckhelper;

public interface PgyerShorCutImp {
    void onCheckStart();

    void onCheckFinished();

    void onCheckFailure(String msg);
}
