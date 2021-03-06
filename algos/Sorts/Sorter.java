package com.jpa.algos;

import java.util.Random;

public class Sorter<T extends Comparable<T>> implements ISorter<T>{

	public static void check(Comparable[] array) throws Exception{
		for(int i = 0; i < array.length; i++){
			if(i != array.length - 1)
				if(array[i].compareTo(array[i+1]) > 0)
					throw new Exception("Values are not sorted in right order");
		}
	}
	
	private void swap(T[] array, int i, int j){
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	private int getPivot(int left, int right){
		return (left + right) / 2;
	}
	
	private int partition(T[] array, int left, int right, int pivot){
		swap(array, pivot, right);
		int storedIndex = left;
		for(int current = left; current < right; current++){
			if(array[current].compareTo(array[right]) < 0){
				swap(array, current, storedIndex);
				storedIndex++;
			}
		}
		swap(array, right, storedIndex);
		return storedIndex;
	}
	
	private void quickSort(T[] array, int left, int right){
		int pivot;
		if(left < right){
			pivot = getPivot(left, right);
			pivot = partition(array, left, right, pivot);
			quickSort(array, left, pivot - 1);
			quickSort(array, pivot + 1, right);
		}
	}
	
	private void mergeSort(T[] array, int first, int last){
		if(first != last){
			int pivot = getPivot(first, last);
			mergeSort(array, first, pivot);
			mergeSort(array, pivot + 1, last);
			merge(array, first, pivot, last);
		}
	}
	
	private void merge(T[] array, int first, int last, int last2) {
		int first2 = last + 1;
		int index = first;
		int index2 = first2;
		
		T[] arrayTemp = array.clone();
		clear(arrayTemp);
		
		for(int i = first; i <= last; i++)
			arrayTemp[i - first] = array[i];
		
		for(int i = first; i <= last2; i++){
			if(index == first2)
				break;
			else if(index2 == (last2 + 1)){
				array[i] = arrayTemp[index - first];
				index++;
			}
			else if(arrayTemp[index - first].compareTo(array[index2]) < 0){
				array[i] = arrayTemp[index - first];
				index++;
			}
			else{
				array[i] = array[index2];
				index2++;
			}
		}
	}
	
	private void clear(T[] array){
		for(int i = 0; i < array.length; i++)
			array[i] = null;
	}
	
	public void bubbleSort(T[] array) {
		boolean flag;
		do{
			flag = true;
			for(int i = 0; i < array.length - 1; i++){
				if(array[i].compareTo(array[i+1]) > 0){
					flag = false;
					swap(array, i, i+1);
				}
			}
		}while(!flag);
	}

	public void insertionSort(T[] array) {
		for(int i = 1; i < array.length; i++){
			T current = array[i];
			int j = i;
			while(j > 0 && array[j - 1].compareTo(current) > 0){
				array[j] = array[j - 1];
				j--;
			}
			array[j] = current;
		}
	}

	public void mergeSortWrapper(T[] array) {
		if(array.length > 0)
			mergeSort(array, 0, array.length - 1);
	}

	public void quickSortWrapper(T[] array) {
		if(array.length > 0)
			quickSort(array, 0, array.length - 1);
	}

	public void selectionSort(T[] array) {
		for(int i = 0; i < array.length - 1; i++){
			int min = i;
			for(int j = i+1; j < array.length; j++){
				if(array[j].compareTo(array[min]) < 0)
					min = j;
			}
			if(min != i)
				swap(array, i, min);
		}
	}
	
	public static void main(String[] args) {
		int arraySize = 100;
		Integer[] array = new Integer[arraySize];
		Random random = new Random();
		System.out.println("Adding random numbers\n--------------------------------------");
		for(int i = 0; i < arraySize; i++){
			array[i] = random.nextInt(101);
			System.out.print("[" + array[i] + "] ");
		}
		System.out.println("\n--------------------------------------");
		System.out.println("Duplicating arrays to compare sorts...");
		Integer[] array2 = array.clone();
		Integer[] array3 = array.clone();
		Integer[] array4 = array.clone();
		Integer[] array5 = array.clone();
		
		System.out.println("[Done]");
		
		try{
			Sorter.check(array); // Should throw exception due to unsorted array
		}catch (Exception e) {
			System.out.println(e.getMessage()); // Must be called
		}
		
		Sorter<Integer> sorter = new Sorter<Integer>();
		long start = System.currentTimeMillis();
		sorter.insertionSort(array);
		long stop = System.currentTimeMillis();
		System.out.println("insertionSort done in " + (stop - start) + " ms");
		for(int i = 0; i < array.length; i++)
			System.out.print("[" + array[i] + "] ");
		
		System.out.println();
		
		start = System.currentTimeMillis();
		sorter.selectionSort(array2);
		stop = System.currentTimeMillis();
		System.out.println("selectionSort done in " + (stop - start) + " ms");
		for(int i = 0; i < array2.length; i++)
			System.out.print("[" + array2[i] + "] ");
		
		System.out.println();
		
		start = System.currentTimeMillis();
		sorter.bubbleSort(array3);
		stop = System.currentTimeMillis();
		System.out.println("bubbleSort done in " + (stop - start) + " ms");
		for(int i = 0; i < array3.length; i++)
			System.out.print("[" + array3[i] + "] ");
		
		System.out.println();
		
		start = System.currentTimeMillis();
		sorter.quickSortWrapper(array4);
		stop = System.currentTimeMillis();
		System.out.println("quickSortWrapper done in " + (stop - start) + " ms");
		for(int i = 0; i < array4.length; i++)
			System.out.print("[" + array4[i] + "] ");
		
		System.out.println();
		
		start = System.currentTimeMillis();
		sorter.mergeSortWrapper(array5);
		stop = System.currentTimeMillis();
		System.out.println("mergeSortWrapper done in " + (stop - start) + " ms");
		for(int i = 0; i < array5.length; i++)
			System.out.print("[" + array5[i] + "] ");
		
		try {
			Sorter.check(array);
			Sorter.check(array2);
			Sorter.check(array3);
			Sorter.check(array4);
			Sorter.check(array5);
			System.out.println("\nEvery arrays are sorted in right order");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
