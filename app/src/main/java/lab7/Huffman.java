package lab7;
import heap.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Huffman {

    HashTable<String, Character> encodeMap = new HashTable<String, Character>();
    HashTable<Character, String> decodeMap = new HashTable<Character, String>();

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



        // Print results
        System.out.println("Input string: ");
        System.out.println("Encoded string: ");
        System.out.println("Decoded string: ");
        System.out.println("Decoded equals input: ");
        System.out.println("Compression ratio: ");
        
    }

    public static String readFile(Scanner input) {
        String output = "";
        while (input.hasNextLine()) {
            output += input.nextLine();
        }
        return output;
    }

    public class Node {
    public static HashTable<Character, Integer> frequencyCount(String input){
        HashTable<Character, Integer> frequencyTable = new HashTable<Character, Integer>(17);
        for(char c : input.toCharArray()){
            Integer existing = frequencyTable.get(c);
            if(existing == null){
                frequencyTable.put(c, 1);
            } else {
                frequencyTable.put(c, existing + 1);
            }
        }
        return frequencyTable;
    }

    public static class Node {
        public char character;
        public Node right;
        public Node left;

        public Node(char newCharacter) {
            character = newCharacter;
            right = null;
            left = null;
        }

        public Node(Node newLeft, Node newRight) {
            right = newRight;
            left = newLeft;
        }
    }

    
}