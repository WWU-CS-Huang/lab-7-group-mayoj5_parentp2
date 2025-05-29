package lab7;
import heap.Heap;

import java.util.Scanner;

public class Huffman {
    public static void main(String[] args){
        
    }

    public class Node {
        public char character;
        public Node right;
        public Node left;

        public Node(char newCharacter) {
            character = newCharacter;
            right = null;
            left = null;
        }

        public Node(Node newLeft, Node newRight) {
            character = null;
            right = newRight;
            left = newLeft;
        }
    }

    
}