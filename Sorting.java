import java.util.Random;

import Plotter.Plotter;


public class Sorting {


	final static int BUBBLE_VS_QUICK_LENGTH = 12;
	final static int MERGE_VS_QUICK_LENGTH = 15;
	final static int BUBBLE_VS_QUICK_SORTED_LENGTH = 12;
	final static int ARBITRARY_VS_MEDIAN_LENGTH = 16;
	final static double T = 600.0;
	/**
	 * Sorts a given array using the quick sort algorithm.
	 * At each stage the pivot is chosen to be the rightmost element of the subarray.
	 * 
	 * Should run in average complexity of O(nlog(n)), and worst case complexity of O(n^2)
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void quickSortArbitraryPivot(double[] arr){
		quickSort(arr, 0, arr.length - 1);
	}

	public static void quickSort(double[] arr, int p, int r) {
		if (p < r - 2) {
			int q = partition(arr, p, r);
			quickSort(arr, p, q - 1);
			quickSort(arr, q + 1, r);
		} else {
			mergeSort(arr, p, r);
		}
	}

	public static int partition(double[] arr, int p, int r) {
			double x = arr[r];
			int i = p - 1;
			for (int j = p; j < r; j++) {
				if (arr[j] <= x) {
					i++;
					double temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}

			double temp = arr[i + 1];
			arr[i + 1] = arr[r];
			arr[r] = temp;

		return (i + 1);
		}
	
	/**
	 * Sorts a given array using the quick sort algorithm.
	 * At each stage the pivot is chosen in the following way:
	 * Choose 3 random elements from the array, the pivot is the median of the 3 elements.
	 * 
	 * Should run in average complexity of O(nlog(n)), and worst case complexity of O(n^2)
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void quickSortMedianPivot(double[] arr){

		double[] arr1 = {arr[0], arr[arr.length / 2], arr[arr.length - 1]};
		bubbleSort(arr1);
		double median = arr1[1];
		int i = 0;
		for (i = 0; i < arr.length; i++) {
			if (arr[i] == median) {
				break;
			}
		}

		double temp = arr[i];
		arr[i] = arr[arr.length - 1];
		arr[arr.length - 1] = temp;
		quickSortArbitraryPivot(arr);

	} 
	
	
	/**
	 * Sorts a given array using the merge sort algorithm.
	 * 
	 * Should run in complexity O(nlog(n)) in the worst case.
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void mergeSort(double[] arr) {
		mergeSort(arr, 0, arr.length - 1);
	}

	public static void mergeSort(double[] arr, int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort(arr, p, q);
			mergeSort(arr, q + 1, r);
			merge(arr, p, q, r);
		}
	}

	public static void merge(double[] arr, int p, int q, int r) {
		int nl = q - p + 2;
		int nr = r - q + 1;

		double[] left = new double[nl];
		double[] right = new double[nr];

		for (int i = 0; i < nl - 1; i++) {
			left[i] = arr[p + i];
		}
		for (int i = 0; i < nr - 1; i++) {
			right[i] = arr[q + 1 + i];
		}

		left[nl - 1] = Double.MAX_VALUE;
		right[nr - 1] = Double.MAX_VALUE;

		int i = 0;
		int j = 0;

		for (int k = p; k < r + 1; k++) {
			if (left[i] <= right[j]) {
				arr[k] = left[i];
				i++;
			} else {
				arr[k] = right[j];
				j++;
			}
		}
	}


	/**
	 * Sorts a given array using bubble sort.
	 * If at any time the algorithm recognizes no more inversions are needed it should stop.
	 * 
	 * The algorithm should run in complexity O(n^2) in the worst case.
	 * The algorithm should run in complexity O(n) in the best case.
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void bubbleSort(double[] arr){

		for (int i = 0; i < arr.length; i++) {
			int counter = 0;

			for (int j = 1; j < arr.length - i; j++) {
				if (arr[j - 1] > arr[j]) {
					double temp = arr[j - 1];
					arr[j - 1] = arr[j];
					arr[j] = temp;
					counter++;
				}
			}

			if (counter == 0) {
				break;
			}
		}
	}
	
	
	public static void main(String[] args) {

		bubbleVsQuick();
		mergeVsQuick();
		bubbleVsQuickOnSortedArray();
		arbitraryPivotVsMedianPivot();
		
		//Testing..
		//double [] arr = {3, 7, 1, 4, 9, 5, 8, 1};
		//double [] arr1 = {1, 2, 4, 7};
		//double [] arr2 = {9, 8, 7, 6, 5, 4};

		//quickSortMedianPivot(arr);
		//for (int i = 0; i < arr.length; i++){
		//	System.out.print(arr[i] + " ");
		//}

	}
	
	/**
	 * Compares the selection sort algorithm against quick sort on random arrays
	 */
	public static void bubbleVsQuick(){
		double[] quickTimes = new double[BUBBLE_VS_QUICK_LENGTH];
		double[] bubbleTimes = new double[BUBBLE_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < BUBBLE_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumSelection = 0;
			for(int k = 0; k < T; k++){
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSortArbitraryPivot(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				bubbleSort(b);
				endTime = System.currentTimeMillis();
				sumSelection += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			bubbleTimes[i] = sumSelection/T;
		}
		Plotter.plot("quick sort on random array", quickTimes, "bubble sort on random array", bubbleTimes);
	}
	
	/**
	 * Compares the merge sort algorithm against quick sort on random arrays
	 */
	public static void mergeVsQuick(){
		double[] quickTimes = new double[MERGE_VS_QUICK_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < MERGE_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSortArbitraryPivot(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			mergeTimes[i] = sumMerge/T;
		}
		Plotter.plot("quick sort on random array", quickTimes, "merge sort on random array", mergeTimes);
	}

	/**
	 * Compares the merge sort algorithm against quick sort on pre-sorted arrays
	 */
	public static void bubbleVsQuickOnSortedArray(){
		double[] quickTimes = new double[BUBBLE_VS_QUICK_SORTED_LENGTH];
		double[] bubbleTimes = new double[BUBBLE_VS_QUICK_SORTED_LENGTH];
		long startTime, endTime;
		for (int i = 0; i < BUBBLE_VS_QUICK_SORTED_LENGTH; i++) {
			long sumQuick = 0;
			long sumBubble = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = j;
					b[j] = j;
				}
				startTime = System.currentTimeMillis();
				quickSortArbitraryPivot(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				bubbleSort(b);
				endTime = System.currentTimeMillis();
				sumBubble += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			bubbleTimes[i] = sumBubble/T;
		}
		Plotter.plot("quick sort on sorted array", quickTimes, "bubble sort on sorted array", bubbleTimes);
	}

	/**
	 * Compares the quick sort algorithm once with a choice of an arbitrary pivot and once with a choice of a median pivot
	 */
	public static void arbitraryPivotVsMedianPivot(){
		double[] arbitraryTimes = new double[ARBITRARY_VS_MEDIAN_LENGTH];
		double[] medianTimes = new double[ARBITRARY_VS_MEDIAN_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < ARBITRARY_VS_MEDIAN_LENGTH; i++) {
			long sumArbitrary = 0;
			long sumMedian = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSortArbitraryPivot(a);
				endTime = System.currentTimeMillis();
				sumArbitrary += endTime - startTime;
				startTime = System.currentTimeMillis();
				quickSortMedianPivot(b);
				endTime = System.currentTimeMillis();
				sumMedian += endTime - startTime;
			}
			arbitraryTimes[i] = sumArbitrary/T;
			medianTimes[i] = sumMedian/T;
		}
		Plotter.plot("quick sort with an arbitrary pivot", arbitraryTimes, "quick sort with a median pivot", medianTimes);
	}
	
}
