package com.kulbachniy.homeworks.model.containers.trees;

import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.util.Optional;

public class Node {
        private Derivative derivative;
        private Node left;
        private Node right;

        public Derivative getDerivative() {
            return this.derivative;
        }

        public void setDerivative(Derivative derivative){
            this.derivative = derivative;
        }

        public Node getLeft(){
            return this.left;
        }

        public void setLeft(Node left){
            this.left = left;
        }

        public Node getRight(){
            return this.right;
        }

        public void setRight(Node right){
            this.right= right;
        }

        @Override
        public String toString(){
            return derivative.getTicker();
        }
}
