import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driller4_0 {
    static int linesRead = 0;
    //reads file data
    public static Tree.AVLTree readMPFile(String filePath, Tree.AVLTree tree, boolean purge, boolean merge){
        Tree.AVLTree newTree = new Tree.AVLTree();
        Scanner in = new Scanner(System.in);
        try {
            String fileName = filePath;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            line = br.readLine();
            line = br.readLine();
            int lineNum = 0;
            while(line!=null){
                String[] lineString = line.split(",");
                DrillingRecord lineRecord = new DrillingRecord(lineString);
                 lineString = line.split(",");
                 lineRecord = new DrillingRecord(lineString);
                 newTree.insertElement(lineRecord, lineNum);
                lineNum++;
                linesRead++;
                line=br.readLine();
            }
            br.close();
            if(merge){
                tree.setRootNode(tree.mergeTrees(tree.getRootNode(), newTree.getRootNode()));
            }
            if(purge){
                tree = tree.purgeTrees(tree, newTree, tree.getTotalNumberOfNodes());
            }
            System.out.println();
            return tree;
            }
        catch(FileNotFoundException fnfe) {
            System.out.println("File not available");
            manipulate(tree, linesRead);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return tree;
    }
    //reads file data
    public static Tree.AVLTree readFile(String filePath){
        Scanner in = new Scanner(System.in);
        Tree.AVLTree tree = new Tree.AVLTree();
        try {
            String fileName = filePath;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            line = br.readLine();
            line = br.readLine();
            int lineNum = 0;
            while(line != null) {
                String[] lineString = line.split(",");
                DrillingRecord lineRecord = new DrillingRecord(lineString);
                tree.insertElement(lineRecord, lineNum);
                line=br.readLine();
                lineNum++;
                linesRead++;
            }	br.close();

            manipulate(tree, lineNum);
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
        return tree;
    }

    public static void manipulate(Tree.AVLTree list, int index){
        Scanner in = new Scanner(System.in);
        ResizableArray<DrillingRecord> array = list.storeInorder(list.getRootNode());
        System.out.println("Enter (o)utput, (s)ort, (f)ind, (m)erge, (p)urge, (h)ash map, (pre)order, (post)order, (in)order, or (q)uit:");
        String userIn = in.nextLine();
        if(userIn.equals("o")){
            System.out.println("Enter the name of the file");
            String newFile = in.nextLine();
            if(!newFile.equals("")){
               list = readMPFile(newFile, list, false, true);
            }else if(newFile.equals("")){
                System.out.println(list);
                System.out.println("Data lines read: "+ linesRead +"; Valid drilling records: "+ linesRead+"; Drilling records in memory: " +
                        linesRead);
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
                list.inorderTraversal();
            }else{
                int numIndex = Integer.parseInt(input);
                if(numIndex >=0 && numIndex <=17 ){
                    array.bubbleSort(numIndex);
                    array.print(array);
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
                            HashTable.Map<String, DrillingRecord> hashMap = new HashTable.Map<>(list.getTotalNumberOfNodes());
                            HashTable.HashNode node = new HashTable.HashNode(list.getRootNode().data, hashMap.hash(list.getRootNode().data.getTime()));
                            int searchKey = hash(element);
                            while(node!= null){
                                hashMap.add(node.record.getTime(), node.record);
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
            list = readMPFile(input, list, false, true);
            manipulate(list,index);
        }
        else if(userIn.equals("p")){
            System.out.println("Enter data file name:");
            String input = in.nextLine();
            list = readMPFile( input, list, true, false);
            manipulate(list,index);
        }
        else if(userIn.equals("pre")){
            list.preorderTraversal();
            manipulate(list, index);
        }
        else if(userIn.equals("post")){
            list.postorderTraversal();
            manipulate(list, index);
        }
        else if(userIn.equals("in")){
            list.inorderTraversal();
            manipulate(list, index);
        }
        else if(userIn.equals("h")){
            System.out.println("Enter data file name:");
            String input = in.nextLine();
            HashTable.Map<String, DrillingRecord> hashMap = new HashTable.Map<>(list.getTotalNumberOfNodes());
            HashTable.HashNode node = new HashTable.HashNode(list.getRootNode().data, hashMap.hash(list.getRootNode().data.getTime()));
            while(node!= null){
                hashMap.add(node.record.getTime(), node.record);
                node = node.next;
            }
            if(input.equals("")){
                System.out.println(hashMap.toString());
                System.out.println("Base capacity: " + hashMap.getCapacity()+ "; Total capacity: " + hashMap.size()+ "; Load Factor: " + hashMap.getLoadFactor());
                System.out.println("Data lines read: "+ linesRead +"; Valid drilling records: "+ hashMap.size()+"; Drilling records in memory: " +
                        hashMap.size());
            }
            else{
                hashMap.writeToFile(input);
            }
            manipulate(list,index);
        }
        else{
            manipulate(list,index);
        }
    }
    public static void firstPrompt(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter data file name:");
        String userIn = in.nextLine();
        readFile(userIn);
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
