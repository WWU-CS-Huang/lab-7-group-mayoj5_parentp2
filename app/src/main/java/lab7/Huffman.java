package lab7;
import heap.*;

import java.util.Scanner;

public class Huffman {

    HashTable<String, Character> encodeMap = new HashTable<String, Character>();
    HashTable<Character, String> decodeMap = new HashTable<Character, String>();

    public static void main(String[] args){
        
    }

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