import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}
	//!NOTICE!//
	// I used DS 2021 lecture note pseudo code making the structure of code.
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void swap(int[] arr, int idx1, int idx2){
		if(arr.length<=idx1||arr.length<=idx2){
			// TODO : FOR DEBUGGING. REMOVE THIS PART BEFORE SUBMITTING CODE
			int tmp = idx1;
			if(idx1<idx2) tmp = idx2;
			System.err.println("!!!!!!!!!!!!swap error!!!!!!!!!!!!");
			System.err.println("!!!!!!out of bound : "+tmp+"!!!!!!!");
		}
		else{
			int tmp = arr[idx1];
			arr[idx1] = arr[idx2];
			arr[idx2] = tmp;
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// TODO : Bubble Sort 를 구현하라.
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		// I used IN-PLACE method for this method.
		for(int last=value.length-1; last>0; last--){
			for(int j=0; j<last; j++){
				if(value[j]>value[j+1]){
					swap(value, j, j+1);
				}
			}
		}
		return (value);
	}

		////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		// TODO : Insertion Sort 를 구현하라.
		for(int i=1; i<value.length; i++){
			int j = i-1;
			int tmp = value[i];
			while(0<=j&&tmp<value[j]){
				value[j+1] = value[j];
				j--;
			}
			value[j+1] = tmp;
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		buildHeap(value);
		for(int i=value.length-1; i>0; i--){
			value[i] = deleteMax(value, i+1);
		}
		return (value);
	}
	private static void percolateDown(int[] heap, int start, int length){
		//when deleteMax call percolateDown -> length index : percolateDown XX
		//deleteMax가 이 함수를 호출할 때 마지막 인덱스는 percolate down의 대상이 아니다. 따라서 length 인자로
		//어디까지 percolateDown하면 되는지를 받아온다.(강의노트에는 length 인자를 사용하지 않으므로 설명을 덧붙임)
		int child = 2*start+1;
		int right = 2*start+2;
		if(child<=length-1){
			if(right<=length-1&&heap[child]<heap[right]){
				child = right;
			}
			if(heap[start]<heap[child]){
				swap(heap, start, child);
				percolateDown(heap, child, length);
			}
		}
	}
	private static int deleteMax(int[] heap, int length){
		int max = heap[0];
		heap[0] = heap[length-1];
		percolateDown(heap, 0, length-1);
		return max;
	}
	private static void buildHeap(int[] heap){
		for(int i = (heap.length-2)/2; i>=0; i--){
			percolateDown(heap, i, heap.length);
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		return mergeSort(value, 0, value.length-1);
	}
	private static int[] mergeSort(int[] arr, int start, int end){
		if(start<end){
			int mid = (start+end)/2;
			mergeSort(arr, start, mid);
			mergeSort(arr, mid+1, end);
			merge(arr, start, mid, end);
		}
		return (arr);
	}
	private static void merge(int[] arr, int start, int mid, int end){
		int i = start; int j = mid+1; int t = 0;
		int[] tmp = new int[end-start+1];
		while(i<=mid&&j<=end){
			if(arr[i]<=arr[j]){
				tmp[t++] = arr[i++];
			}
			else{
				tmp[t++] = arr[j++];
			}
		}
		while(i<=mid)
			tmp[t++] = arr[i++];
		while(j<=end)
			tmp[t++] = arr[j++];
		i = start; t = 0;
		while(i<=end){
			arr[i++] = tmp[t++];
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.
		return quickSort(value, 0, value.length-1);
	}
	private static int[] quickSort(int[] arr, int start, int end){
		if(start<end){
			int pivotIndex = partition(arr, start, end);
			quickSort(arr, start, pivotIndex-1);
			quickSort(arr, pivotIndex+1, end);
		}
		return arr;
	}

	private static int partition(int[] arr, int start, int end){
		int pivot = arr[end];
		int idxS1 = start-1;
		for(int idxS2=start; idxS2<end; idxS2++){
			if(arr[idxS2]<pivot){
				idxS1++;
				swap(arr, idxS1, idxS2);
			}
		}
		swap(arr, idxS1+1, end);
		return idxS1+1;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		int max = value[0];
		for(int i=0; i< value.length; i++){
			if(max<Math.abs(value[i])) max = Math.abs(value[i]);
		}

		int digit = (int)(Math.log10(max)+1);
		//radix sort
		for (int j = 1; j <= digit; j++) {
			value = countingSort(value, j);
		}

		return (value);
	}


	private static int[] countingSort(int[] value, int key){
		//counting sort using key parameter as key

		int[] tmp = new int[20];
		int[] result = new int[value.length];
		for(int i=0; i<=19; i++){
			tmp[i] = 0;
		}
		//count
		for (int j = 0; j < value.length; j++) {
			int keyValue = 0;
			if(value[j]<0){
				keyValue = -(value[j] / (int) Math.pow(10, key - 1) - 10 * (value[j] / (int) Math.pow(10, key)));
				keyValue = 9-keyValue;
			}
			else{
				keyValue = value[j] / (int) Math.pow(10, key - 1) - 10 * (value[j] / (int) Math.pow(10, key))+10;
			}

			tmp[keyValue]++;
		}


		//누적합
		for(int i=1;i<=19;i++){
			tmp[i] = tmp[i] + tmp[i-1];
		}


		for (int j = value.length-1; j >=0; j--) {
			int keyValue = 0;
			if(value[j]<0){
				keyValue = -(value[j] / (int) Math.pow(10, key - 1) - 10 * (value[j] / (int) Math.pow(10, key)));
				keyValue = 9-keyValue;
			}
			else{
				keyValue = value[j] / (int) Math.pow(10, key - 1) - 10 * (value[j] / (int) Math.pow(10, key))+10;
			}
			result[tmp[keyValue] - 1] = value[j];
			tmp[keyValue]--;
		}

		return result;
	}
}
