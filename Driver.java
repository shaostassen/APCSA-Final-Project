import java.util.Arrays;

public class Driver {
	static int mergeCount;
	static int quick1Count;
	static int quick2Count;

	public static void main(String[] args) {
		long totalMergeCount=0;
		long totalQuick1Count=0;
		long totalQuick2Count=0;
		int worstM=0;
		int bestM=Integer.MAX_VALUE;
		int worstQ1=0;
		int bestQ1=Integer.MAX_VALUE;
		int worstQ2=0;
		int bestQ2=Integer.MAX_VALUE;
		for (int j = 0; j < 100; j++) {
			int[] arr = new int[1000000];
			for (int i = 0; i < arr.length; i++)
				arr[i] = (int) (Math.random() * 1000000);
			int[] arr1 = arr.clone();
			int[] arr2 = arr.clone();
			int[] arr3 = arr.clone();
			mergeCount =0;
			mergeSort(arr1);
			totalMergeCount+=mergeCount;
			quick1Count =0;
			quickSort1(arr2, 0, arr2.length - 1);
			totalQuick1Count+=quick1Count;
			quick2Count=0;
			quickSort2(arr3, 0, arr3.length - 1);
			totalQuick2Count+=quick2Count;
		    if(mergeCount<bestM)
		    	bestM=mergeCount;
		    if(mergeCount>worstM)
		    	worstM=mergeCount;
		    if(quick1Count<bestQ1)
		    	bestQ1=quick1Count;
		    if(quick1Count>worstQ1)
		    	worstQ1=quick1Count;
		    if(quick2Count<bestQ2)
		    	bestQ2=quick2Count;
		    if(quick2Count>worstQ2)
		    	worstQ2=quick2Count;
			//System.out.println(mergeCount);
			//System.out.println(quick1Count);
			//System.out.println(quick2Count);
		}
		long averageM = totalMergeCount/100;
		long averageQ1 = totalQuick1Count/100;
		long averageQ2 = totalQuick2Count/100;
		System.out.println("\t\tBest\t\tAverage\t\tWorst\n");
		System.out.println("MergeSort\t" + bestM + "\t"+averageM+"\t"+worstM+"\n");
		System.out.println("QuickSort\t" + bestQ1 + "\t"+averageQ1+"\t"+worstQ1+"\nStart to End");
		System.out.println("QuickSort\t" + bestQ2 + "\t\t"+averageQ2+"\t\t"+worstQ2+"\nFrom Both Ends");
		System.out.println();
		bestQ1=Integer.MAX_VALUE;
		worstQ1=0;
		bestQ2=Integer.MAX_VALUE;
		worstQ2=0;
		int[] bestQuick1Arr= {};
		int[] worstQuick1Arr= {};
		int[] bestQuick2Arr= {};
		int[] worstQuick2Arr= {};
		int merge1=0;
		int merge2=0;
		int merge3=0;
		int merge4=0;
		for (int k = 0; k < 1000; k++) {
			int[] arrr = new int[20];
			for (int i = 0; i < arrr.length; i++)
				arrr[i] = (int) (Math.random() * 20);
			int[] arr4 = arrr.clone();
			int[] arr5 = arrr.clone();
			int[] arr6 = arrr.clone();
			quick1Count =0;
			quickSort1(arr6, 0, arr6.length - 1);
			quick2Count=0;
			quickSort2(arr5, 0, arr5.length - 1);
		    if(quick1Count<bestQ1) {
		    	bestQ1=quick1Count;
		        bestQuick1Arr=arr4.clone();
		    }
		    if(quick1Count>worstQ1) {
		    	worstQ1=quick1Count;
		    	worstQuick1Arr=arr4.clone();
		    }
		    if(quick2Count<bestQ2) {
		    	bestQ2=quick2Count;
		    	bestQuick2Arr=arr4.clone();
		    }
		    if(quick2Count>worstQ2) {
		    	worstQ2=quick2Count;
		    	worstQuick2Arr=arr4.clone();
		    }
		    
		   
		   
		   
		    
		}
		System.out.println("QuickSort Start to End Best Array:\n"+Arrays.toString(bestQuick1Arr));
		mergeCount=0;
	    mergeSort(bestQuick1Arr);
	    merge1=mergeCount;
		System.out.println("Quicksort Swaps vs MergeSort Swaps:\n\t"+bestQ1+"\t\t"+merge1);
		System.out.println("QuickSort Start to End Worst Array:\n"+Arrays.toString(worstQuick1Arr));
		mergeCount=0;
		mergeSort(worstQuick1Arr);
		merge2=mergeCount;
		System.out.println("Quicksort Swaps vs MergeSort Swaps:\n\t"+worstQ1+"\t\t"+merge2);
		System.out.println("QuickSort Both Ends Best Array:\n"+Arrays.toString(bestQuick2Arr));
		mergeCount=0;
		mergeSort(bestQuick2Arr);
		merge3=mergeCount;
		System.out.println("Quicksort Swaps vs MergeSort Swaps:\n\t"+bestQ2+"\t\t"+merge3);
		System.out.println("QuickSort Both Ends Worst Array:\n"+Arrays.toString(worstQuick2Arr));
		mergeCount=0;
		mergeSort(worstQuick2Arr);
		merge4=mergeCount;
		System.out.println("Quicksort Swaps vs MergeSort Swaps:\n\t"+worstQ2+"\t\t"+merge4);
	}

	public static void mergeSort(int[] arr) {
		int[] tempArr = new int[arr.length];
		recurr(arr, tempArr, 0, arr.length - 1);
	}

	public static void recurr(int[] arr, int[] tempArr, int start, int stop) {
		if (start >= stop)// must be sorted
			return;
		recurr(arr, tempArr, start, (start + stop) / 2);
		recurr(arr, tempArr, (start + stop) / 2 + 1, stop);
		merge(arr, tempArr, start, stop, (start + stop) / 2);

	}

	public static void merge(int[] arr, int[] tempArr, int start, int stop, int middle) {
		int i = start; // start of the first section
		int j = middle + 1; // start of the 2nd section

		for (int k = start; k <= stop; k++) {// scan through all the numbers and take
// them in the right order
			if (i > middle) {
				tempArr[k] = arr[j++];
				mergeCount++;
			} else if (j > stop) {
				tempArr[k] = arr[i++];
				mergeCount++;
			} else if (arr[i] < arr[j]) {
				tempArr[k] = arr[i++];
				mergeCount++;
			} else {
				tempArr[k] = arr[j++];
				mergeCount++;
			}
		}
		for (int k = start; k <= stop; k++) {// copy the merged numbers into the array being sorted
			arr[k] = tempArr[k];
			mergeCount++;
		}
	}

	// Lemuto quickSort
	public static void quickSort1(int[] arr, int start, int end) {
		if (start >= end)
			return;
		int pivot = arr[end];
		int pivotSpot = start;
		for (int i = start; i < end; i++) {
			if (arr[i] < pivot) {
				if (i != pivotSpot) {
					int temp = arr[i];
					arr[i] = arr[pivotSpot];
					arr[pivotSpot] = temp;
					quick1Count += 2;

				}
				pivotSpot++;
			}
		}
		int temp = arr[pivotSpot];
		arr[pivotSpot] = arr[end];
		arr[end] = temp;
		quick1Count += 2;
		quickSort1(arr, start, pivotSpot - 1);
		quickSort1(arr, pivotSpot + 1, end);
	}

	// Hoare quickSort
	public static void quickSort2(int[] arr, int start, int end) {
		if (start >= end)
			return;
		int i = start;
		int j = end;
		int pivot = arr[start];
		while (i < j) {
			while (arr[i] < pivot) {
				i++;
			}
			while (arr[j] > pivot) {
				j--;
			}
			if (i < j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				quick2Count += 2;
			}
			if (i <= j)
				i++;j--;
		}
		quickSort2(arr, start, j);
		quickSort2(arr, i, end);
	}
}


