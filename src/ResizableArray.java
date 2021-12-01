


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

 class ResizableArray<E>  {
	 final static int DEFAULT_SIZE = 10;
	 int count; //amount of spaces in array that are currently filled
	 int capacity;//size of array
	 E[] data;//array of objects
	 int linesRead;
	 //initializes class setting the data size and capacity to 10 and count to 0
	 public ResizableArray() {
		 data = (E[]) new Object[DEFAULT_SIZE];
		 count = 0;
		 capacity = DEFAULT_SIZE;
		 linesRead =0;
	 }


	 //method to add element at certain index
	 public void addAt(E element, int index) {
		 try {
			 //if count = index and it does not equal capacity the element is added straight to the index
			 if (count == index && count != capacity) {
				 data[index] = element;
				 count++;
			 } else {
				 //if count = capacity that means the array is full so a DoubleDrillingArray class is created
				 //data then equals the doubled array
				 if (count == capacity) {
					 DoubleDrillingArray<E> obj = new DoubleDrillingArray<E>(data);
					 data = (E[]) obj.getDblArray();
					 capacity = obj.getDoubleCapacity();
					 E[] tempArray = (E[]) new Object[capacity];
					 for (int i = 0; i <= count; i++) {
						 if (i < index) {
							 tempArray[i] = data[i];
						 } else if (i == index) {
							 tempArray[i] = element;
						 } else if (i > index) {
							 tempArray[i] = data[i - 1];
						 }
					 }
					 data = tempArray;
					 count++;
				 } //if necessary the array will be halved through the HalfDrillingArray object

				  else {
					 E[] tempArray = (E[]) new Object[capacity];
					 for (int i = 0; i <= count; i++) {
						 if (i < index) {
							 tempArray[i] = data[i];
						 } else if (i == index) {
							 tempArray[i] = element;
						 } else if (i > index) {
							 tempArray[i] = data[i - 1];
						 }
					 }
					 data = (E[]) tempArray;
					 count++;
				 }
			 }
		 } catch (OutOfMemoryError e) {
			 System.out.println("No java heap space");
		 } catch (ArrayIndexOutOfBoundsException ex) {
			 System.out.println("Array index is out of bounds");
		 }
	 }

	 //replaces object in array at the index with another element
	 public void replaceAt(E element, int index) {
		 try {
			 for (int i = 0; i < data.length; i++) {
				 if (i == index) {
					 data[i] = element;
				 }
			 }
		 } catch (ArrayIndexOutOfBoundsException ex) {
			 System.out.println("Array index is out of bounds");
		 }

	 }

	 //removes element at a given index
	 public void removeAt(Object element, int index) {
		 try {
			 for (int i = 0; i < capacity; i++) {
				 if (i > index) {
					 data[i - 1] = data[i];
				 }
			 }
			count--;
		 } catch (OutOfMemoryError e) {
			 System.out.println("No java heap space");
		 } catch (ArrayIndexOutOfBoundsException ex) {
			 System.out.println("Array index is out of bounds");
		 }

	 }

	 //allows for the adding of another element to []. If the capacity of the array is reached,
	 //doubleDrillingArray() is called to double the size
	 public void push(E element) {
		 try {
			 if (count == capacity) {
				 DoubleDrillingArray<E> obj = new DoubleDrillingArray<E>(data);
				 data = (E[]) obj.getDblArray();
				 capacity = data.length;
				 data[count - 1] = element;
				 count++;
			 }
			 if (count < capacity) {
				 data[count] = element;
				 count++;
			 }
		 } catch (OutOfMemoryError e) {
			 System.out.println("No java heap space");
		 } catch (ArrayIndexOutOfBoundsException ex) {
			 System.out.println("Array index is out of bounds");
		 }

	 }
	 //allows for the retrieval of object
	 public E get(int index) {
		 try {
			 if (index < count) {
				 return data[index];
			 }
			 return data[index];
		 } catch (ArrayIndexOutOfBoundsException ex) {
			 System.out.println("Array index is out of bounds");
		 }
		 return data[0];
	 }

	 public boolean contains(DrillingRecord comp){
		 for(int i = 0; i<this.getCount(); i++){
			 if(this.data[i] != null){
				 DrillingRecord record= (DrillingRecord) this.data[i];
				 if(record.getTime().compareTo(comp.getTime()) == 0){
					 return true;
				 }
			 }
		 }
		 return false;
	 }

	 public boolean isEmpty(){
		 if(this.data[0] == null){
			 return true;
		 }
		 return false;
	 }
	//returns capacity
	 public int getSize() {
		 return capacity;
	 }

	//prints the array with the DrillingRecord toString()
	 public void print(ResizableArray array) {
		 try{
			 DrillingRecord record = new DrillingRecord();
			 for (int i = 0; i < array.count; i++) {

				 record = (DrillingRecord) array.data[i];
				 System.out.println(record);
			 }
		 }catch(ArrayIndexOutOfBoundsException ex){
			 System.out.println("Array Index out of Bounds");
		 }

	 }
	 //returns count
	 public int getCount() {
		 return count;
	 }
	//returns object from data[] at a specific index
	 public E getDataArrayIndex(int index) {
		 return data[index];
	 }



	 //made with help from https://www.javatpoint.com/quick-sort
	 /* this partition method goes with the below quicksort method. It takes in a ResizableArray as well as its start, end, and target index.
	 * It sets the end index of the array as a DrillingRecord object and then stores a string from the DrillingRecord's internal data
	 * at the target index to be the pivot.
	 * If index is 0 it converts the pivot string to a LocalDate object as well as the index as which to compore.
	 * If index is 1 it converts the pivot string to a LocalTime object as well as the index as which to compare
	 * If the index is anything else it converts the pivot string and comparison string to double
	 * If the comparison object is less than the pivot it increments the smallest index and
	 * assigns the data at that index to a DrillingRecord object.
	 * The smallest index is then set equal to the index incremented by the for loop and then
	 * the index incremented by the for loop is set to the DrillingRecord object.
	 * After the for loop finishes:
	 * assign a new DrillingRecord to index i+1
	 * set data at index i+1 to the end
	 * set data at end index to the DrillingRecord object
	 * return i+1 as the pivot index */
	 public int partition(ResizableArray array, int start, int end, int index, DrillingRecord drillingRecord){

			 drillingRecord = (DrillingRecord) array.data[end];
			 String pivot = drillingRecord.getDataAt(index);
			 int i = (start-1);
			 for (int j = start; j <= end - 1; j++){
				 if(index ==0){
					 drillingRecord = (DrillingRecord) array.data[j];
					 String compRec = drillingRecord.getDataAt(index);
//					 LocalDate pivotDate = LocalDate.parse(pivot, DateTimeFormatter.ofPattern("M/dd/yyy"));
//					 LocalDate compDate = LocalDate.parse(compRec, DateTimeFormatter.ofPattern("M/dd/yyy"));
					 if(compRec.compareTo(pivot) <= 0){
						 i++;
						 drillingRecord = (DrillingRecord) array.data[i];
						 array.data[i] = array.data[j];
						 array.data[j] = drillingRecord;
					 }
				 }
				 else if(index==1){
					 drillingRecord = (DrillingRecord) array.data[j];
					 String compRec = drillingRecord.getDataAt(index);
//					 LocalTime pivotTime = LocalTime.parse(pivot, DateTimeFormatter.ofPattern("HH:mm:ss"));
//					 LocalTime compTime = LocalTime.parse(compRec, DateTimeFormatter.ofPattern("HH:mm:ss"));
					 if(compRec.compareTo(pivot) <=0){
						 i++;
						 drillingRecord = (DrillingRecord) array.data[i];
						 array.data[i] = array.data[j];
						 array.data[j] = drillingRecord;
					 }
				 }
				 else{
					 drillingRecord = (DrillingRecord) array.data[j];
					 String compRec = drillingRecord.getDataAt(index);
					 double pivotDoub = Double.parseDouble(pivot);
					 double compDoub = Double.parseDouble(compRec);
					 if(compDoub < pivotDoub){
						 i++;
						 drillingRecord = (DrillingRecord) array.data[i];
						 array.data[i] = array.data[j];
						 array.data[j] = drillingRecord;
					 }
				 }
			 }
			 drillingRecord = (DrillingRecord) array.data[i+1];
			 array.data[i+1] =  array.data[end];
			 array.data[end] = drillingRecord;
			 return (i+1);

	 }

	 //made with help from https://www.javatpoint.com/quick-sort
	 /*quickSort: sorting algorithm with aan average time complexity of O(n log n)
	 * calls the above partition method that returns a pivot index and sorts the array from the start to the pivot
	 * after calling the partition method, quicksort then recursively calls itself twice, which sorts the array twice.
	 * once from the start to the pivot and secondly from the pivot to the end */
	 public void quickSort(ResizableArray array, int start, int end, int index){
		 DrillingRecord record = new DrillingRecord();
		 if(start<end && end< array.getCount()){
			 int pIndex = partition(array, start, end, index, record);
			 quickSort(array, start, pIndex-1, index);
			 quickSort(array, pIndex+1, end, index);
		 }
	 }
	//made with help from https://www.javatpoint.com/bubble-sort-in-java
	 /*This bubble sort algorithm works by taking in an index and sorting the ResizableArray off of it
	 * It sorts the array by checking to see if one element is smaller than the one before it then swapping locations*/
	public void bubbleSort(int index){
		try{
			String[] parser;
			DrillingRecord record = new DrillingRecord();
			DrillingRecord tempRecord = new DrillingRecord();
			E temp;
			if(index == 0){//sorts dates
				for(int i = 0; i<count; i++){
					for(int j = 1; j<(count - i); j++){
						tempRecord = (DrillingRecord) data[j-1];
						record = (DrillingRecord) data[j];
						if( record.getDate().compareTo(tempRecord.getDate()) < 0){
							temp = data[j-1];
							data[j-1] = data[j];
							data[j] = temp;
							record = (DrillingRecord) data[j];
						}
					}
				}
			}
			else if(index==1){//sorts times
				for(int i = 0; i<count; i++){
					for(int j = 1; j<(count - i); j++){
						tempRecord = (DrillingRecord) data[j-1];
						record = (DrillingRecord) data[j];
						if( record.getTime().compareTo(tempRecord.getTime())<0 ){
							temp = data[j-1];
							data[j-1] = data[j];
							data[j] = temp;
							record = (DrillingRecord) data[j];
						}
					}
				}
			}
			else{
				for(int i = 0; i<count; i++){
					for(int j = 1; j<(count - i); j++){
						tempRecord = (DrillingRecord) data[j-1];
						record = (DrillingRecord) data[j];
						Double tempNum = Double.parseDouble(tempRecord.getDataAt(index));
						Double num = Double.parseDouble(record.getDataAt(index));

						if( tempNum >  num){
							temp = data[j-1];
							data[j-1] = data[j];
							data[j] = temp;
						}

					}
				}
			}
		}catch(OutOfMemoryError e){
			System.out.println("No java heap space");
		}
		catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("Array index is out of bounds");
		}

	}

	/*This binarySearch method uses a modified linearSearch to find multiple lines that share the target value.
	* It takes in the index of the target, the first index of the array, the last index of the array, a ResizableArray object for
	* memory saving purposes, and a DrillingRecord object that is also used for memory saving purposes.
	*/
	 public String binarySearch(int index, int first, int last, String key, ResizableArray<DrillingRecord> matches, ResizableArray array, ResizableArray<DrillingRecord> tempMatches, DrillingRecord record) {
		 try{
			 if(first<=last){
				 String[] parser; //array for parsing date and time to be stored in localdate and localtime objects
				 int mid = first + (last - first) / 2;
				 record = (DrillingRecord) array.data[mid];
				 LinkedList<String> strings = record.getData();
				 if(index==0){
					 //makes a recursive method call with mid being the start and last being the end to search the right
					 if(record.getDate().compareTo(key) <=0 ){
						 tempMatches = linearSearchModS(key, index, mid, last, array, record);
						 for(int i = 0; i< tempMatches.getCount(); i++){
							 matches.push( tempMatches.get(i));
							 System.out.println(tempMatches.get(i));
						 }
						 return binarySearch(index, first, mid, key, matches, array, tempMatches,record);
					 }
					 //searches the left with mid being the last and first being first
					 else if(key.compareTo(record.getDate())>=1){
						 tempMatches = linearSearchModS(key, index, first, mid, array, record);
						 for(int i = 0; i< tempMatches.getCount(); i++){
							 matches.push( tempMatches.get(i));
							 System.out.println(tempMatches.get(i));
						 }
						 return binarySearch(index, mid, first, key, matches, array, tempMatches, record);
					 }
				 }else if(index==1){
					 //same as above but using localtime instead of local date
					 if(record.getTime().compareTo(key)<=0){
						 tempMatches = linearSearchModS(key, index, mid, last, array, record);
						 for(int i = 0; i< tempMatches.getCount(); i++){
							 matches.push( tempMatches.get(i));
							 System.out.println(tempMatches.get(i));
						 }
						 return binarySearch(index, first, mid, key, matches, array, tempMatches,record);
					 }
					 else if(key.compareTo(record.getTime()) >=1){
						 tempMatches = linearSearchModS(key, index, first, mid, array, record);
						 for(int i = 0; i< tempMatches.getCount(); i++){
							 matches.push( tempMatches.get(i));
							 System.out.println(tempMatches.get(i));
						 }
						 return binarySearch(index, mid, first, key, matches, array, tempMatches, record);
					 }
				 }else{
					 //for finding numeric values
					 Double keyDoub = Double.parseDouble(key);
					 Double compVal = Double.parseDouble(record.getDataAt(index));
					 if(compVal <= keyDoub){
						 tempMatches = linearSearchMod(keyDoub, index, mid, last, array, record);
						 for(int i = 0; i< tempMatches.getCount(); i++){
							 matches.push( tempMatches.get(i));
							 System.out.println(tempMatches.get(i));
						 }
						 return binarySearch(index, first, mid, key, matches, array, tempMatches,record);
					 }
					 else if(compVal >= keyDoub){
						 tempMatches = linearSearchMod(keyDoub, index, first, mid, array, record);
						 for(int i = 0; i< tempMatches.getCount(); i++){
							 matches.push( tempMatches.get(i));
							 System.out.println(tempMatches.get(i));
						 }
						 return binarySearch(index, mid, first, key, matches, array, tempMatches, record);
					 }

				 }

			 }
		 }catch( StackOverflowError e){
			 System.out.print("");
		 }
		 catch(ArrayIndexOutOfBoundsException ex){
			 System.out.println("Array index is out of bounds");
		 }	catch(NullPointerException v){
			 System.out.print("");
		 }
		 if(matches.getCount() == 0){
			 return -(index+1) + System.lineSeparator() + "Drilling records found: "+ matches.getCount();
		 }
		 return "Drilling records found: " + matches.getCount();
	 }

	/*Modified linearSearch method
	* This linear search is modified to work within a binary search. It takes the target element, target index,
	* start index, end index, a ResizableArray object for memory saving purposes, and a Drilling record object for memory saving purposes as well
	*
	* It works by pushing DrillingRecord objects into a ResizableArray */
	 public ResizableArray<DrillingRecord> linearSearchMod(double element, int index, int first, int last, ResizableArray array, DrillingRecord tempRecord){
		 ResizableArray<DrillingRecord> matches = new ResizableArray<>();
		try{
		 for(int i=first;i< last;i++){
			 tempRecord = (DrillingRecord) data[i];
			 double recordDoub = Double.parseDouble(tempRecord.getDataAt(index));
			 if(recordDoub == element){
				 //stores matching number in matches array
				 matches.push(tempRecord);
			 }
		 }
			return matches;
		}catch(OutOfMemoryError e){
			System.out.println("No java heap space");
		}
		catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("Array index is out of bounds");
		}

		 return matches;

	 }
	//Same as above but takes string to search
	 public ResizableArray<DrillingRecord> linearSearchModS(String element, int index, int first, int last, ResizableArray array, DrillingRecord tempRecord){
		 //create new resizable array for matches
		 ResizableArray<DrillingRecord> matches = new ResizableArray<>();
		 try{
			 //search for element to match. if it is found add it to matches
			 for(int i=first;i< last;i++){
				 tempRecord = (DrillingRecord) data[i];
				 if(tempRecord.getDataAt(index).equals(element)){

					 matches.push(tempRecord);
				 }
			 }
			 //return matches array
			 return matches;
		 }catch(OutOfMemoryError e){
			 System.out.println("No java heap space");
		 }
		 catch(ArrayIndexOutOfBoundsException ex){
			 System.out.println("Array index is out of bounds");
		 }

		 return matches;

	 }

	 //Basic linear search method for searching DrillingRecord object
	 public String linearSearch(String element, int index, int amount){
		 try{
			 for(int i=0;i<count;i++){
				 DrillingRecord tempRecord = new DrillingRecord();
				 tempRecord = (DrillingRecord) data[i];
				 if(tempRecord.getDataAt(index).equals(element)){
					 System.out.println(tempRecord);
					 amount++;
				 }
			 }
		 }catch(OutOfMemoryError e){
			 System.out.println("No java heap space");
		 }
		 catch(ArrayIndexOutOfBoundsException ex){
			 System.out.println("Array index is out of bounds");
		 }
		 if(amount == 0){
			 return "-1" + System.lineSeparator() + "Drilling records found: "+ amount;
		 }
		 return "Drilling records found: " + amount;

	 }

	 public void incrementLinesRead(){
		 linesRead++;
	 } //increments lines read

	 public int getLinesRead(){
		 return linesRead;
	 } // returns lines read

	 public void setLinesRead(int num){
		 linesRead = num;
	 }// sets the lines read

	 //removes lines except last
	public ResizableArray<DrillingRecord> removeAllButLast(ResizableArray<DrillingRecord> array){
		try{
			ResizableArray<DrillingRecord> tempArray = new ResizableArray<>();

			tempArray.addAt(array.get(array.getCount()), 0) ;
			tempArray.setLinesRead(array.getLinesRead());
			return tempArray;
		}catch(OutOfMemoryError e){
			System.out.println("No java heap space");
		}
		catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("Array index is out of bounds");
		}
		return array;
	}

	//set size of Resizable Array data
	public void setSize(int size){
		 //create new data array
		 E[] newData = (E[]) new Object[size];
		 //add old data array values to new array
		 for(int i = 0; i<this.count; i++){
			 newData[i] = this.data[i];
		 }
		 //old data equals new data
		 this.data = newData;
		 }

		 //method to turn Resizable Array into DrillingLinkedList
	public DrillingLinkedList<DrillingRecord> makeLinkedList(){
		 //create DrillingLinkedList
		 DrillingLinkedList<DrillingRecord> list = new DrillingLinkedList<>();

		 //traverse ResizableArray
		 for(int i = 0; i< count; i++){
			 //add DrillingRecord data to list
			 list.add((DrillingRecord) this.data[i], list, ((DrillingRecord) this.data[0]).getDate());
		 }
		 //return list
		 return list;
	}
	}
