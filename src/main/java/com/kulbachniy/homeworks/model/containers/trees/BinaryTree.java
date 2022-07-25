package com.kulbachniy.homeworks.model.containers.trees;

import com.kulbachniy.homeworks.model.containers.DerivativeComparator;
import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.util.Stack;

public class BinaryTree {
    private Node root;
    private final DerivativeComparator comparator = new DerivativeComparator();
    private int size = 0;

    public BinaryTree(){
        root = null;
    }

    public Node findDerivative(Derivative derivative){
        Node current = root;

        Derivative currentDerivative = current.getDerivative();

        while(!currentDerivative.equals(derivative)){
            if(comparator.compare(derivative, currentDerivative) < 0) {
                current = current.getLeft();
                currentDerivative = current.getDerivative();
            } else {
                current = current.getRight();
                currentDerivative = current.getDerivative();
            }
        }
        return current;
    }

    public void addDerivative(Derivative derivative) {
        size  = size + 1;
        Node node = new Node();
        node.setDerivative(derivative);

        if(root == null) {
            root = node;
        } else {
            Node current = root;
            Node parent;
            while(true)
            {
                parent = current;
                Derivative currentDerivative = current.getDerivative();

                if(derivative.equals(currentDerivative)){
                    return;
                } else if (comparator.compare(derivative, currentDerivative) < 0) {
                    current = current.getLeft();
                    if(current == null){
                        parent.setLeft(node);
                        return;
                    }
                } else {
                    current = current.getRight();
                    if(current == null) {
                        parent.setRight(node);
                        return;
                    }
                }
            }
        }
    }

    public int getSize(){
        return this.size;
    }

    public void printTreeStructure() {
        Stack<Node> nodes = new Stack<>();
        nodes.push(root);
        int spaces = 32;
        boolean emptyRow = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);
        while (!emptyRow) {
            Stack<Node> childs = new Stack<>();
            emptyRow = true;

            for (int j = 0; j < spaces; j++)
                System.out.print(' ');
            while (!nodes.isEmpty()) {
                Node temp = nodes.pop();
                if (temp != null) {
                    System.out.print(temp);
                    childs.push(temp.getLeft());
                    childs.push(temp.getRight());
                    if (temp.getLeft() != null ||
                            temp.getRight() != null)
                        emptyRow = false;
                }
                else {
                    System.out.print("__");
                    childs.push(null);
                    childs.push(null);
                }
                for (int j = 0; j < spaces * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            spaces /= 2;
            while (!childs.isEmpty())
                nodes.push(childs.pop());
        }
        System.out.println(separator);
    }

    private void displayInfo(Node node){
        if(node != null) {
            displayInfo(node.getLeft());
            displayInfo(node.getRight());
            System.out.println("Ticker: " + node.toString() + "(" + node.getDerivative().getPrice() + ")");
        }
        return;
    }

    public void printTreeInfo() {
        displayInfo(root);
    }

    private double getSum(Node node, double result){
       if(node != null) {
           result = getSum(node.getLeft(), result) + node.getDerivative().getPrice() + getSum(node.getRight(), result);
      }
       return result;
    }

    public double getSumLeftBranch() {
        double  result = 0;
        return getSum(this.root.getLeft(), result);
    }

    public double getSumRightBranch() {
        double  result = 0;
        return getSum(this.root.getRight(), result);
    }
}