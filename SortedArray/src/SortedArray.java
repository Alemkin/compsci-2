/**
 * SortedArray problem for COP3503C Lab Section 12
 * 
 * @author Alexander Lemkin
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * The following program runs in O(n) time due to the fact
 * that it does not run through any of the elements fully,
 * more than once. In the function for parsing, it simply
 * runs through all of the possible configurations until
 * it runs into a match, and breaks based on that. 
 * It can also break if other logic doesn't add up, as explained
 * in my comments pertaining to the functions.
 */
public class SortedArray {
	
	public static void main(String[] args) throws IOException {
		/* Opening a scanner to read through the contents of the file*/
		Scanner sc = null;
		try {
			sc = new Scanner(new File("array.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int numCases = sc.nextInt(); /* Read in the first line as the number of cases */
		
		/* Runs a loop through the number of cases */
		for(int i = 0; i < numCases; i++) {
			int arraySize = sc.nextInt(); /* grabs the next integer as the array size */
			int[] nextArray = new int[arraySize]; 
			
			/* Runs a loop to store the integers into an array of size arraySize */
			for(int j=0;j<arraySize;j++) {
				nextArray[j] = sc.nextInt();
			}
			int target = sc.nextInt(); /* Stores the next integer after the array as the target number */
			
			boolean check = parseArray(target, nextArray); /* Call the parseInt function to check for the target sum */
			
			/* If check returns true, then prints it is achievable, else print not */
			if (check == true) {
				System.out.println("Test case #"+(i+1)+": T"
						+ "he target of "+target+" is achievable");
			}
			else {
				System.out.println("Test case #"+(i+1)+": T"
						+ "he target of "+target+" is NOT achievable");
			}
		}
	}
	/**
	 * Function to parse the next array in the text file for 
	 * a sum of two integers matching the target number
	 * 
	 * @param target
	 * @param nextArray
	 * @return boolean
	 */
	private static boolean parseArray(int target, int[] nextArray) {

		/* Adds the array[n] + array[n+1] until it hits a match to the target
		 * or reaches the end on the array with no matches*/
		for (int i = 0; i<nextArray.length; i++) {
			if(i == nextArray.length-1) { return false; } /*No need to continue if we are at the last integer of the array */
			if(nextArray[i] > target){ return false; } /* We can break if the next int is larger than target, since it is sorted */
			
			for (int j = i+1; j<nextArray.length; j++) { 
				if(nextArray[i] + nextArray[j] == target)/* Breaks if the target is found from a sum */
				{
					return true;
				}
			}
		}
		return false;
	}

}
