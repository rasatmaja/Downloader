package com.techweblearn;

public interface CombiningFileListener {

     void  combineCompleted();
     void  combineProgress(long combining);
     void  combineError();

}
