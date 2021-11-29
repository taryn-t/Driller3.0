
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

//class that reads in file data and stores it into ResizableArray objects
public class DrillingRecord<T> implements Comparable<T> {
	//variables for DrillingRecord
	LinkedList<String> recordData; //LinkedList to store strings from file
	boolean floatingErrors = false; // boolean to flag errors with floating values
	//default constructor
	DrillingRecord(){
		//initialize variables
		recordData = new LinkedList<>();
		boolean floatingErrors = false;

	}

	//reads and stores file data into LinkedLists
	DrillingRecord(String[] fileLine){
		 recordData = new LinkedList<>();
		for(int i = 0; i<= 17; i++) {
			if(i <2) {
				recordData.add(fileLine[i]);
			}
			else {
				double temp = Double.parseDouble(fileLine[i]);
				if(temp<0.0){
					floatingErrors = true;
				}
				recordData.add(fileLine[i]);

			}
		}
	}

	//returns the first date
	public String getDate(){
		return recordData.getFirst();
	}
	//returns ResizableArray data
	public LinkedList<String> getData(){
		return recordData;
	}

	//returns data at specific index
	public String getDataAt(int index){
		return recordData.get(index);
	}
	public String getTime(){
		return recordData.get(1);
	}

	//returns floatingErrors
	public  boolean getFloatError(){
		return floatingErrors;
	}


	//turns floatingErrors from false to true
	public void flagFloatError() {
		floatingErrors = true;
	}



	//toString method
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("");
		for(int i = 0; i<=17; i++) {
			if(i<2) {
				str.append(recordData.get(i));
				str.append(" : ");
			}
			else if(i>1) {
				String formatted = String.format("%.2f", Double.parseDouble(recordData.get(i)));
				str.append(formatted);
				if(i<17) {
					str.append(" : ");
				}
			}
		}

		return str.toString();

	}
	public String toStringHash() {
		StringBuilder str = new StringBuilder("");
		for(int i = 0; i<=17; i++) {
			if(i<2) {
				str.append(recordData.get(i));
				str.append(" ; ");
			}
			else if(i>1) {
				String formatted = String.format("%.2f", Double.parseDouble(recordData.get(i)));
				str.append(formatted);
				if(i<17) {
					str.append(" ; ");
				}
			}
		}

		return str.toString();

	}
	//method to determine if two DrillingRecord objects are equal
	public boolean equals(DrillingRecord comp){
		//set a boolean to false
		boolean equals = false;
		//traverse the DrillingRecord Linkedlists
		for(int i = 0; i< comp.getData().size(); i++){
			//compare the strings within the records
			if(this.getDataAt(i).equals(comp.getDataAt(i))){
				//if the string is equal set the boolean to true
				equals = true;
			}else{
				//if at any point a string does not match return false
				return false;
			}
		}
		//return equals which will be true if no non matches are found
		return equals;
	}

//compareTo() made with help from https://stackoverflow.com/questions/33968333/how-to-check-if-a-string-is-date
	/*this toString method will take a string object and attempt to format it as a date. If it cannot be formatted as a date,
	* a ParseException will be thrown and it will then attempt to parse it into a date. If the parsing fails then it will return the large number 999.
	* if no exception is thrown on either the date parsing or time parsing, it will compare the two strings formatted as dates and return 1 if the static
	* element is larger and -1 vice versa*/
	@Override
	public int compareTo(T comp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("M-dd-yyyy");
		dateFormat.setLenient(false);
		try{
			Date date1 = dateFormat.parse(String.valueOf(this));
			Date compDate = dateFormat.parse((String) comp);
			if(date1.compareTo(compDate) >0){
				return 1;
			}else if(date1.compareTo(compDate) < 0){
				return -1;
			}
			else if(date1.compareTo(compDate) == 0){
				return 0;
			}
		} catch (ParseException e) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			timeFormat.setLenient(false);
			try{
				Date time1 =timeFormat.parse(String.valueOf(this));
				Date compTime = timeFormat.parse((String) comp);

				if(time1.compareTo(compTime) >0){
					return 1;
				}else if(time1.compareTo(compTime) < 0){
					return -1;
				}else if(time1.compareTo(compTime) == 0) {
					return 0;
				}
			} catch (ParseException parseException) {
				return 999;
			}
		}
		return 0;
	}
}
