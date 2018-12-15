package org.mdk.commons;

public class Sigmoid {

	public static double value(double x, double beta) {
		return ((2)/(1+Math.exp(-beta*x)))-(1); 
	}
	
	public static void main(String[] args) throws Exception {
		for(int i=0; i<100; i++) {
			System.out.println(i+":"+value(i, 0.1));
		}
	}

	
}
