package polynomials;

import std.StdDraw;
import std.StdOut;

public class Polynomial {
	
	private int[] a;    // The coefficient of x^i is stored in a[i]
	private int degree; // The degree of this polynomial

	/**
	 * Constructs a polynomial from the given array of coefficients.
	 * If the given coefficients include trailing zeros, ignores them.
	 * @param coeffArr The given coefficients. The coefficient of the 
	 *             zero's term is represented by coeffArr[0], and so on.
	 */ 
	public Polynomial(int[] coeffArr) {
		// Computes and sets the degree of this polynomial.
		// If the given coefficients include trailing zeros, ignores them.
		degree = 0;
		for (int i = coeffArr.length-1; i >= 0; i--) {
			if (coeffArr[i]!=0) {
				this.degree = i;	
				break;
			}
		}
		int [] temp = new int [degree+1];
		System.arraycopy(coeffArr, 0, temp, 0, degree+1); 		//create a temp array to not change the original array
		this.a = new int [degree+1];
		this.a = temp;

	}
	
	/** Returns the degree of this polynomial.
	 *  @return The degree of this polynomial.
	 */
	public int getDegree() {
		
		return this.degree;
	}
	
	/**
	 * Returns the coefficients of this polynomial.
	 * @return The coefficients of this polynomial, as an int array.
	 */
	public int[] getCoeffArr(){
		// Constructs an array of the right size, copies all the coefficients of this polynomial into it,
		// and returns the array. This is done in order to protect the coefficients of this polynomial. 
		// (If we will simply return the a array, the user can destroy it).
		int [] CoeffArr = new int [a.length];
		System.arraycopy(this.a, 0, CoeffArr, 0, a.length);
		return CoeffArr;
	}
	
	/**
	 * Returns the i'th coefficient of this polynomial. <br>
	 * If i is greater than the degree of this polynomial, returns 0.
	 * @param i The term's power.
	 * @return The i'th coefficient of this polynomial.
	 */
	public int getCoefficient(int i) {
		if (i>=a.length) {	 
			return 0;
		}
		
		return a[i];
	}
	
	/**
	 * Returns the value of this polynomial at the given point.
	 * @param x The value at which this polynomial is computed 
	 * @return The value of this polynomial at the given point.
	 */
	public double value(double x) {
		double result = 0; 
		for (int i = 0; i<a.length; i++) {
			result += a[i] * Math.pow(x, i);
		}
		return result;
	}
	
	/**
	 * Returns a vector of values of this polynomial,
	 * computed over the given vector of values.
	 * @param x The values at which this polynomial is computed 
	 * @return The values of this polynomial at the given points.
	 */
    public double[] value(double x[]) {
    		double [] VectorValues = new double [x.length];
    		for (int i = 0; i<x.length; i++) {
    			VectorValues[i] = value(x[i]);
    			
    		}
        return VectorValues;
    }
    
    /**
	 * Returns the multiplication of this polynomial by the given scalar value.
	 * @param c The scalar
	 * @return The multiplication of this polynomial by the given scalar, as a new polynomial
	 */
	public Polynomial multByScalar(int c) {
		int [] temp = new int [this.a.length];
		for (int i = 0; i<temp.length; i++) {
			temp[i] = c*this.a[i];
		}
		Polynomial multiplied = new Polynomial(temp);
		return multiplied;
	}
	
	/**
	 * Returns the polynomial resulting from the addition of this polynomial
	 * and the other polynomial.
	 * @param other The other polynomial which is added to this polynomial.
	 * @return The sum of this polynomial and the other one, as a new polynomial.
	 */
	
	public Polynomial plus(Polynomial other) {
		int [] otherArr = other.getCoeffArr();
		int[] Arraysum = new int [Math.max(this.a.length, otherArr.length)];
		if (a.length >= otherArr.length) {
			System.arraycopy(otherArr, 0, Arraysum, 0, otherArr.length);
			for (int i = 0; i < Arraysum.length; i++) {
				Arraysum[i] += this.a[i];
			}
		}
		else {
			System.arraycopy(a, 0, Arraysum, 0, a.length);
			for (int i = 0; i < Arraysum.length; i++) {
				Arraysum[i] += otherArr[i];
			}
		}
		Polynomial result = new Polynomial(Arraysum);
		return result;
}
	
	/**
	 * Returns the first derivative of this polynomial.
	 * @return The first derivative of this polynomial, as a new polynomial
	 */
	public Polynomial derivative() {
		int [] basicarr = {0};
		if (a.length==1) {
			Polynomial derivate = new Polynomial(basicarr);
			return derivate;
		}
		int [] arrderiv = new int [a.length-1];
		for (int i = 1; i < a.length; i++ ) {
			arrderiv[i-1] = i *a[i];
		}
		Polynomial derivate = new Polynomial(arrderiv);
		return derivate;
	}
	
	/**
	 * Displays the graph of this polynomial, computed over the given range of values.
	 * @param xMin The5kj left-most x value
	 * @param xMax The right-most x value
	 * @param nSegments The number of line segments used to approximate the graph
	 */
	public void plot(int xMin, int xMax, int nSegments) {
		StdDraw.setXscale(xMin, xMax);
		double[] xArr = xArr(xMin, xMax, nSegments);
		double[] yArr = value(xArr);
		StdDraw.setYscale(min(yArr), max(yArr));
		for (int i = 0; i < nSegments; i++) {
			StdDraw.line(xArr[i], yArr[i], xArr[i+1], yArr[i+1]);
		}
	}
	
	/** Returns a string representation of this polynomial.
	 * @return This polynomial as a string of the form an*x^n + ... + a1*x + a0
	 */
	public String toString() {
		String polToString = "";

		if (a.length == 1) {
			return polToString += a[0];
		}
		if (a.length == 2) {
			if (a[1] > 1 || a[1] < -1) {
				polToString += a[1] + "x";
			}
			else if (a[1] == 1) {
				polToString += "x";
			}
			else if (a[1] == -1) {
				polToString += "-x";
			}
			if (a[0] > 0) {
				polToString += " + " + a[0];
			}
			else if (a[0] < 0) {
				polToString += " - " + (-1) * a[0];
			}
			return polToString;

		}
		if (a[a.length-1] == 1) {
			polToString += "x^" + degree;
		}
		else if (a[a.length-1] == -1) {
			polToString += "-x^" + degree;
		}
		else {
			polToString += a[a.length - 1] + "x^" + degree;
		}

		for (int i = a.length-2; i > 1; i--) {
			if (a[i] > 1) {
				polToString += " + " + a[i] + "x^" + i;
			}
			else if (a[i] < -1){
				polToString += " - " + (-1 * a[i]) + "x^" + i;
			}
			else if (a[i] == -1){
				polToString += " - " + "x^" + i;
			}
			else if (a[i] == 1){
				polToString += " + " + "x^" + i;
			}
		}

		if (a[1] > 1) {
			polToString += " + " + a[1] + "x";
		}
		else if (a[1] < -1) {
			polToString += " - " + (-1) * a[1] + "x";
		}
		else if (a[1] == 1) {
			polToString += " + x";
		}
		else if (a[1] == -1) {
			polToString += " - x";
		}

		if (a[0] > 0) {
			polToString += " + " + a[0];
		}
		else if (a[0] < 0) {
			polToString += " - " + (-1) * a[0];
		}
		return polToString;
	}
	
    // Returns an array that represent N equally-spaced
    // points between a and b
	private static double[] xArr(double a, double b, int N) {
		double maximum = Math.max(a, b);
		double minimum = Math.min(a, b);
		double size = ((maximum-minimum)/N);
		double [] arr = new double [N+1];
		arr [0] = minimum;
		arr [N] = maximum;
		int counter = 0;
		for (double i = (minimum/size); i <= (maximum/size); i++) {
			arr[counter] = i * size;
			counter++;
		}

		return arr;
	}
    
    // Returns the minimum value in the given array
    private static double min(double arr[]) {
        double minimum = arr[0];
        for (int i = 0; i < arr.length; i++) {
        		if (arr[i] < minimum) {
        			minimum = arr[i];
        		}
        }
    	return minimum;
    }

    // Returns the maximum value in the given array
    private static double max(double arr[]) {
    	 double maximum = arr[0];
         for (int i = 0; i < arr.length; i++) {
         		if (arr[i] > maximum) {
         			maximum = arr[i];
         		}
         }
     	return maximum;
    }
}
