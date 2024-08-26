package com.veeva.core.Listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * <b>Name :</b> Retry </br>
 * <b>Description : </b> This Class implements IRetryAnalyzer interface which override the retry method
 *  , where we have set an logic to handle the flaky test cases.</br>
 *
 * @author <i>Manmohan Dash</i>
 */
//
public class Retry implements IRetryAnalyzer {

	int count = 0;
	int maxTry = 1;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(count<maxTry)
		{
			count++;
			return true;
		}
		return false;
	}
}
