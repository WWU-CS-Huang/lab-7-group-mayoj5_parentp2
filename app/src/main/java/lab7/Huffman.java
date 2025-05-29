package lab7;
import heap.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Huffman {

    HashTable<String, Character> encodeMap = new HashTable<String, Character>();
    HashTable<Character, String> decodeMap = new HashTable<Character, String>();

    static ArrayList<Character> characterList = new ArrayList<Character>();

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
        //String encodedString = encodeString(inputString);
        //String decodedString = decodeString(encodedString);

        // Print results
        if (inputString.length() < 100) {
            System.out.println("Input string: " + inputString);
            //System.out.println("Encoded string: " + encodedString);
            //System.out.println("Decoded string: " + decodedString);
        }
        //System.out.println("Decoded equals input: " + encodedString.equals(decodedString));
        //System.out.println("Compression ratio: " + encodedString.length()/inputString.length()/8.0);
        
    }

    public static String readFile(Scanner input) {
        String output = "";
        while (input.hasNextLine()) {
            output += input.nextLine();
        }
        return output;
    }

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

    public Heap<Node, Integer> leafHeap(HashTable<Character, Integer> frequency){
        Heap<Node, Integer> output = new Heap<Node, Integer>();

        //makes leaf node for every character found in string
        for(int i = 0; i < characterList.size(); i++){
            Node node = new Node(characterList.get(i));
            output.add(node, frequency.get(characterList.get(i)));
        }
        return output;
    }
    public void makeHashMaps(Node tree){
        makeHashMaps(tree, "");
    }
    public void makeHashMaps(Node tree, String prefix){
        if(tree == null){
            return;
        }
        if(tree.left == null && tree.right == null){
            encodeMap.put(prefix, tree.character);
            decodeMap.put(tree.character, prefix);
        } else {
            makeHashMaps(tree.left, "0");
            makeHashMaps(tree.right, "1");
        }
    }

    public Node buildHuffmanTree(Heap<Node, Integer> heap) {
        while (heap.size() > 1) {
            Node left = heap.poll();
            Node right = heap.poll();
            Node newNode = new Node(left, right, left.frequency + right.frequency);
            heap.add(newNode, left.frequency + right.frequency);
        }
        return heap.poll();
    }

    //

    public static class Node {
        public char character;
        public Node right;
        public Node left;
        public int frequency;

        public Node(char newCharacter, int newFrequency) {
            character = newCharacter;
            right = null;
            left = null;
            frequency = newFrequency;
        }

        public Node(Node newLeft, Node newRight, int newFrequency) {
            right = newRight;
            left = newLeft;
            frequency = newFrequency;
        }
    }

    
}