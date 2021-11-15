import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Driller_2 {
    static int linesRead = 0;
    public static void readNewFile(String filePath, DrillingLinkedList<DrillingRecord> list, int start){
        boolean invalid = false;
        DrillingLinkedList<DrillingRecord> dataLinkedList = new DrillingLinkedList<>();
        dataLinkedList.add(list.getLast().data, dataLinkedList, dataLinkedList.head.data.getDate());

        try{
            Scanner in = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            String headDate = "";
            line = br.readLine();
            line = br.readLine();
            int lineNum = 0;

            while(line != null) {
                if(lineNum == 0){
                    String[] lineString = line.split(",");
                    DrillingRecord lineRecord = new DrillingRecord(lineString);
                    String dateCheck = lineRecord.getDate();
                    lineRecord =  dataLinkedList.getHead().data;
                    headDate= lineRecord.getDate();
                    if(!dateCheck.equals(headDate)){
                        System.out.println("Date mismatch file closed:");
                        invalid =true;
                        manipulate(dataLinkedList, dataLinkedList.length());
                    }
                }
                String[] lineString = line.split(",");
                DrillingRecord lineRecord = new DrillingRecord(lineString);
                dataLinkedList.add(lineRecord, dataLinkedList, headDate);
                line=br.readLine();
                lineNum++;
                linesRead++;

            }	br.close();
            ResizableArray<DrillingRecord> array = new ResizableArray<>();
//            array.makeResizableArray(dataLinkedList);
//            array.checkDateTimeErrors();
//            dataLinkedList = array.makeLinkedList();
//            array.print(array);
//            System.out.println();
            manipulate(dataLinkedList, lineNum+start);
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("File not available");

            manipulate(dataLinkedList, start);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }

    //reads file data
    public static DrillingLinkedList<DrillingRecord> readMergeFile(String filePath, DrillingLinkedList<DrillingRecord> list, int index){
        Scanner in = new Scanner(System.in);
        DrillingLinkedList<DrillingRecord> dataLinkedList = new DrillingLinkedList<>();
        try {
            String fileName = filePath;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String headDate = "";
            String line;
            line = br.readLine();
            line = br.readLine();
            int lineNum = 0;
            while(line != null) {


                String[] lineString = line.split(",");
                DrillingRecord lineRecord = new DrillingRecord(lineString);
                if(lineNum == 0){
                    headDate = lineRecord.getDate();
                }
                dataLinkedList =  dataLinkedList.add(lineRecord, dataLinkedList, headDate);
                line=br.readLine();
                lineNum++;
                linesRead++;

            }	br.close();

            System.out.println();
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("File not available");
            manipulate(list, index);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return dataLinkedList;
    }
    //reads file data
    public static DrillingLinkedList<DrillingRecord> readFile(String filePath){
        Scanner in = new Scanner(System.in);
        DrillingLinkedList<DrillingRecord> dataLinkedList = new DrillingLinkedList<>();
        try {
            String fileName = filePath;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String headDate ="";
            String line;
            line = br.readLine();
            line = br.readLine();
            int lineNum = 0;
            while(line != null) {
                String[] lineString = line.split(",");
                DrillingRecord lineRecord = new DrillingRecord(lineString);
                if(lineNum == 0){
                    headDate = lineRecord.getDate();
                }
                dataLinkedList =  dataLinkedList.add(lineRecord, dataLinkedList, headDate);
                line=br.readLine();
                lineNum++;
                linesRead++;
            }	br.close();

            manipulate(dataLinkedList, lineNum);
            System.out.println();
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("File not available");
            firstPrompt();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return dataLinkedList;
    }

    public static void manipulate(DrillingLinkedList<DrillingRecord> list, int index){
        ResizableArray<DrillingRecord> array= new ResizableArray<>();
        array = list.makeArray();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter (o)utput, (s)ort, (f)ind, (m)erge, (p)urge,(h)ash map (r)ecords, or (q)uit:");
        String userIn = in.nextLine();
        if(userIn.equals("o")){
            System.out.println("Enter the name of the file");
            String newFile = in.nextLine();
            if(!newFile.equals("")){
                readNewFile("C:/Users/taryn/Driller0-4/src/"+ newFile +".csv", list, list.length() );
            }else if(newFile.equals("")){
                System.out.println(list);
                System.out.println("Data lines read: "+ linesRead +"; Valid drilling records: "+ array.getCount()+"; Drilling records in memory: " +
                        array.getCount());
                manipulate(list, index);
            }
        }
        else if(userIn.equals("q")){
            System.out.println("Thanks for using Driller");
            System.exit(0);
        }
        else if(userIn.equals("s")){
            System.out.println("Enter sort field (0-17): ");
            String input = in.nextLine();
            if(input.equals("")){
                array.bubbleSort(1);
                array.print(array);
                list = array.makeLinkedList();
            }else{
                int numIndex = Integer.parseInt(input);
                if(numIndex >=0 && numIndex <=17 ){
                    array.bubbleSort(numIndex);
                    array.print(array);
                    list = array.makeLinkedList();
                }
            }
            manipulate(list, index);
        }
        else if(userIn.equals("f")){
            System.out.println("Enter search field (0-17):");
            String input = in.nextLine();
            if(!input.equals("")){
                int numIndex = Integer.parseInt(input);
                if(numIndex >=0 && numIndex <=17){
                    if(numIndex!=1){
                        array.bubbleSort(numIndex);
                        System.out.println("Enter positive field value:");
                        String elementString = in.nextLine();
                        if(!elementString.equals("")) {
                            DrillingRecord recordData = new DrillingRecord();
                            ResizableArray<DrillingRecord> matches = new ResizableArray<>();
                            ResizableArray<DrillingRecord> temp = new ResizableArray<>();
                            System.out.println(array.binarySearch(numIndex, 0, array.getCount(), elementString, matches, array, temp, recordData));
                            manipulate(list, index);
                        }else{
                            manipulate(list,index);
                        }
                    }
                    else{
                        System.out.println("Enter timestamp as which to search:");
                        String element = in.nextLine();
                        if(!element.equals("")) {
                            HashTable.Map<String, DrillingRecord> hashMap = new HashTable.Map<>();
                            DrillingLinkedList.Node<DrillingRecord> node = list.head;
                            int searchKey = hash(element);
                            while(node!= null){
                                hashMap.add(node.data.getTime(), node.data);
                                node = node.next;
                            }
                            System.out.println(hashMap.get(element));
                            manipulate(list, index);
                        }
                    }
                }
                else{
                    manipulate(list,index);
                }
            }
            else if(input.equals("")){
                manipulate(list, index);
            }
        }
        else if(userIn.equals("m")){
            System.out.println("Enter data file name:");
            String input = in.nextLine();
            DrillingLinkedList<DrillingRecord> mergeList = readMergeFile("C:/Users/taryn/Driller0-4/src/"+ input +".csv", list, index);
            list.head = list.merge(list, mergeList);
            list.setLength(list.length() + mergeList.length());
            manipulate(list,index);
        }
        else if(userIn.equals("p")){
            System.out.println("Enter data file name:");
            String input = in.nextLine();
            DrillingLinkedList<DrillingRecord> listToPurge = readMergeFile("C:/Users/taryn/Driller0-4/src/"+ input +".csv", list, index);
            list =  list.purge(list, listToPurge);
            manipulate(list,index);
        }
        else if(userIn.equals("h")){
            System.out.println("Enter data file name:");
            String input = in.nextLine();
            if(input.equals("")){
                HashTable.Map<String, DrillingRecord> hashMap = new HashTable.Map<>();
                DrillingLinkedList.Node<DrillingRecord> node = list.head;
                while(node!= null){
                    hashMap.add(node.data.getTime(), node.data);
                    node = node.next;
                }
                System.out.println(hashMap.toString());
                System.out.println("Base capacity: " + hashMap.getCapacity()+ "; Total capacity: " + hashMap.size()+ "; Load Factor: " + hashMap.getLoadFactor());
                System.out.println("Data lines read: "+ linesRead +"; Valid drilling records: "+ hashMap.size()+"; Drilling records in memory: " +
                        hashMap.size());
            }
            manipulate(list,index);
        }
        else if(userIn.equals("r")){
            System.out.println(list);
            System.out.println("Data lines read: "+ linesRead +"; Valid drilling records: "+ array.getCount()+"; Drilling records in memory: " +
                    array.getCount());
            manipulate(list, index);
        }
        else{
            manipulate(list,index);
        }

    }




    public static void firstPrompt(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter (o)utput, (s)ort, (f)ind, (m)erge, (p)urge, (r)ecords, or (q)uit:");
        String userIn = in.nextLine();

        if(userIn.equals("o")){
            System.out.println("Enter the name of the file");
            String newFile = in.nextLine();
            if(!newFile.equals("")){
                DrillingLinkedList<DrillingRecord> list = readFile("C:/Users/taryn/Driller0-4/src/"+ newFile +".csv" );
                manipulate(list, list.length());
            }else if(newFile.equals("")){
                System.out.println("No file available to output. Press 'o' to add file.");
                firstPrompt();
            }
        }
        else if(userIn.equals("q")){
            System.out.println("Thanks for using Driller");
            System.exit(0);
        }
        else if(userIn.equals("s")){
            System.out.println("No file available to sort. Press 'o' to add file.");
            firstPrompt();
        }
        else if(userIn.equals("f")){
            System.out.println("No file available to find. Press 'o' to add file.");
            firstPrompt();
        }
        else if(userIn.equals("m")){
            System.out.println("No file available to merge. Press 'o' to add file.");
            firstPrompt();
        }
        else if(userIn.equals("p")){
            System.out.println("No file available to find. Press 'o' to add file.");
            firstPrompt();
        }
        else if(userIn.equals("r")){
            System.out.println("No file available to find. Press 'o' to add file.");
            firstPrompt();
        }
        else{
            firstPrompt();
        }
    }
    public static int hash(String key){
        int code = 0;
        char[] arr = key.toCharArray();
        for(char ch : arr){
            int ascii = ch;
            code += ascii;
        }
        return code;
    }
    public static void main(String[] args){
        firstPrompt();
    }
}
