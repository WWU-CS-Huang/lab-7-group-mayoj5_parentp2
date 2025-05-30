package lab7;
import heap.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Huffman {

    static HashTable<String, Character> decodeMap = new HashTable<String, Character>();
    static HashTable<Character, String> encodeMap = new HashTable<Character, String>();

    static ArrayList<Character> characterList = new ArrayList<Character>();

    /** Main method.
     * Takes a txt file as an argument, scans it, builds a Huffman tree with a encodeMap and decodeMap,
     * encodes it, then decodes it and prints the results if the string has less than 100 characters.
     */
    public static void main(String[] args){
        String fileName = args[0];
        File file = new File(fileName);
        Scanner input;

        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String inputString = readFile(input);

        HashTable<Character, Integer> frequencyMap = frequencyCount(inputString);
        Heap<Node, Integer> leafHeap = leafHeap(frequencyMap);
        Node rootTree = buildHuffmanTree(leafHeap);
        makeHashMaps(rootTree);

        String encodedString = encodeString(inputString);
        String decodedString = decodeString(encodedString);

        // Print results
        if (inputString.length() < 100) {
            System.out.println("Input string: " + inputString);
            System.out.println("Encoded string: " + encodedString);
            System.out.println("Decoded string: " + decodedString);
        }
        System.out.println("Decoded equals input: " + inputString.equals(decodedString));
        System.out.println("Compression ratio: " + (double)encodedString.length()/inputString.length()/8.0);
        
    }

    /** Encodes a string into binary using the encodeMap
     * Precondition: encodeMap has been filled in
     * Postcondition: returnString is a string of binary digits
     */
    public static String encodeString(String inputString) {
        String returnString = "";
        for (int i = 0; i < inputString.length(); i++) {
            returnString += encodeMap.get(inputString.charAt(i));
        }
        return returnString;
    }

    /** Returns a file as a single String
     * Precondition: Scanner input is properly initialized
     */
    public static String readFile(Scanner input) {
        String output = "";
        while (input.hasNextLine()) {
            output += input.nextLine();
        }
        return output;
    }

    /** Decodes a string of binary digits using the decodeMap
     * Precondition: decodeMap has been filled in
     * Precondition: String encoded is a string of binary digits
     */
    public static String decodeString(String encoded){
        String output = "";
        while(encoded.length() > 0){
            int end = 1;
            while(!decodeMap.containsKey(encoded.substring(0,end))){
                end++;
            }
            output += decodeMap.get(encoded.substring(0,end));
            encoded = encoded.substring(end);

        }
        return output;
    }

    /** Returns a hashTable of character as keys and integers where the 
     * integer is the frequency that the character apears in String input
     */
    public static HashTable<Character, Integer> frequencyCount(String input){
        HashTable<Character, Integer> frequencyTable = new HashTable<Character, Integer>(17);
        for(char c : input.toCharArray()){
            Integer existing = frequencyTable.get(c);
            if(existing == null){
                frequencyTable.put(c, 1);
                //whenever this is called, new character is found. put into characterlist
                characterList.add(c);

            } else {
                frequencyTable.put(c, existing + 1);
            }
        }
        return frequencyTable;
    }

    /** Returns a heap of Nodes with priority based on their frequency according to HashTable frequency.
     * Postcondition: all Nodes in the heap are leaves
     */
    public static Heap<Node, Integer> leafHeap(HashTable<Character, Integer> frequency){
        Heap<Node, Integer> output = new Heap<Node, Integer>();

        //makes leaf node for every character found in string
        for(int i = 0; i < characterList.size(); i++){
            Node node = new Node(characterList.get(i), frequency.get(characterList.get(i)));
            output.add(node, frequency.get(characterList.get(i)));
        }
        return output;
    }

    /** Fills in encodeMap and decodeMap based on the input Huffman coding tree
     * Precondition: Node tree is a proper Huffman coding tree
     */
    public static void makeHashMaps(Node tree){
        makeHashMaps(tree, "");
    }
    public static void makeHashMaps(Node tree, String prefix){
        if(tree == null){
            return;
        }
        if(tree.left == null && tree.right == null){
            decodeMap.put(prefix, tree.character);
            encodeMap.put(tree.character, prefix);
        } else {
            makeHashMaps(tree.left, prefix + "0");
            makeHashMaps(tree.right, prefix + "1");
        }
    }

    /** Returns a node that is the root of a Huffman coding tree of the leaves in heap.
     * Precondition: heap is a properly initialized forest of nodes with priority based on their frequency.
     */
    public static Node buildHuffmanTree(Heap<Node, Integer> heap) {
        while (heap.size() > 1) {
            Node left = heap.poll();
            Node right = heap.poll();
            Node newNode = new Node(left, right, left.frequency + right.frequency);
            heap.add(newNode, left.frequency + right.frequency);
        }
        return heap.poll();
    }


    /** Class node for building Huffman trees. */
    public static class Node {
        public char character;
        public Node right;
        public Node left;
        public int frequency;

        // Constructor for leaf nodes
        public Node(char newCharacter, int newFrequency) {
            character = newCharacter;
            right = null;
            left = null;
            frequency = newFrequency;
        }

        // Constructor for non-leaves
        public Node(Node newLeft, Node newRight, int newFrequency) {
            right = newRight;
            left = newLeft;
            frequency = newFrequency;
        }
    }

    
}