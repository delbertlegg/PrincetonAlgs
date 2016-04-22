package edu.princeton.Boggle;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main (String[] args) {    
        Result boardResult = JUnitCore.runClasses(BoggleBoardTest.class);
        for (Failure failure : boardResult.getFailures()) {
            System.out.println(failure.toString());
            }
        System.out.println(boardResult.wasSuccessful());
        
        Result solverResult = JUnitCore.runClasses(BoggleBoardTest.class);
        for (Failure failure : solverResult.getFailures()) {
            System.out.println(failure.toString());
            }
        System.out.println(solverResult.wasSuccessful());
    }   
}
