package com.kulbachniy.homeworks.model.containers.lists;

import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class LinkedDerivativeList extends AbstractDerivativeList {

    private class Node {
        private Node prevNode;
        private Node nextNode;
        private Derivative derivative;
        private int transactionNumber;
        private LocalDateTime time;

        Node(Node prevNode, Node nextNode, Derivative derivative) {
            this.prevNode = prevNode;
            this.nextNode = nextNode;
            this.derivative = derivative;
            this.time = LocalDateTime.now();
        }
    }
    private Node headNode;
    private Node tailNode;
    private int listSize;

    public LinkedDerivativeList() {
        headNode = null;
        tailNode = null;
        listSize = 0;
    }

    private int setNumber(){
        try {
            System.out.print("Enter transaction number: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String transaction = reader.readLine();
            return Math.abs(Integer.parseInt(transaction));
        } catch (IOException | NumberFormatException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public void add(Derivative derivative) throws NullPointerException {
        if (derivative == null) {
            throw new NullPointerException("Derivative cannot be NULL");
        } else {
            ++listSize;
            if (headNode == null) {
                Node node = new Node(null, null, derivative);
                node.transactionNumber = setNumber();
                headNode = node;
                tailNode = node;
            } else if (listSize == 1) {
                tailNode = new Node(tailNode, null, derivative);
                tailNode.transactionNumber = setNumber();
                headNode.nextNode = tailNode;
            } else {
                Node node = new Node(tailNode, null, derivative);
                node.transactionNumber = setNumber();
                tailNode.nextNode = node;
                tailNode = node;
            }
        }
    }

    public void addFirst(Derivative derivative) throws NullPointerException {
        if (derivative == null) {
            throw new NullPointerException("Derivative cannot be NULL");
        } else {
            ++listSize;
            if (headNode == null) {
                Node node = new Node(null, null, derivative);
                node.transactionNumber = setNumber();
                headNode = node;
                tailNode = node;
            } else if (listSize == 1) {
                headNode = new Node(null, headNode, derivative);
                headNode.transactionNumber = setNumber();
                tailNode.prevNode = headNode;
            } else {
                Node node = new Node(null, headNode, derivative);
                node.transactionNumber = setNumber();
                headNode.prevNode = node;
                headNode = node;
            }
        }
    }

    @Override
    public boolean remove(Derivative derivative) {
        if (headNode == null) {
            return false;
        } else {
            Node tempNode = headNode;
            for (int i = 0; i < listSize; i++) {
                if (tempNode.derivative.equals(derivative) && listSize == 1) {
                    --listSize;
                    headNode = null;
                    tailNode = null;
                    return true;
                } else if (tempNode.derivative.equals(derivative) && listSize > 1) {
                    if (tempNode == headNode) {
                        --listSize;
                        headNode = headNode.nextNode;
                        headNode.prevNode = null;
                        return true;
                    } else if (tempNode == tailNode) {
                        --listSize;
                        tailNode = tailNode.prevNode;
                        tailNode.nextNode = null;
                        return true;
                    } else {
                        --listSize;
                        tempNode.prevNode.nextNode = tempNode.nextNode;
                        tempNode.nextNode.prevNode = tempNode.prevNode;
                        return true;
                    }
                }
                tempNode = tempNode.nextNode;
            }
            return false;
        }
    }

    public boolean removeByTransactionNumber(int number) {
        if (headNode == null) {
            return false;
        } else {
            Node tempNode = headNode;
            for (int i = 0; i < listSize; i++) {
                if (tempNode.transactionNumber == number && listSize == 1) {
                    --listSize;
                    headNode = null;
                    tailNode = null;
                    return true;
                } else if (tempNode.transactionNumber == number) {
                    if (tempNode == headNode) {
                        --listSize;
                        headNode = headNode.nextNode;
                        headNode.prevNode = null;
                        return true;
                    } else if (tempNode == tailNode) {
                        --listSize;
                        tailNode = tailNode.prevNode;
                        tailNode.nextNode = null;
                        return true;
                    } else {
                        --listSize;
                        tempNode.prevNode.nextNode = tempNode.nextNode;
                        tempNode.nextNode.prevNode = tempNode.prevNode;
                        return true;
                    }
                }
                tempNode = tempNode.nextNode;
            }
            return false;
        }
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public Optional<Derivative> getDerivative(int index) throws IndexOutOfBoundsException {
        if (index >= listSize || index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative or greater than " + (listSize - 1));
        } else {
            Node tempNode = headNode;
            for (int i = 0; i < listSize; i++) {
                if (i == index) {
                    return Optional.of(tempNode.derivative);
                } else {
                    tempNode = tempNode.nextNode;
                }
            }
            Optional<Derivative> op = Optional.empty();
            return op;
        }
    }

    public Optional<Derivative> getByTransactionNumber(int number) throws IndexOutOfBoundsException {
        if (number < 0) {
            throw new IndexOutOfBoundsException("Transaction number cannot be negative ");
        } else {
            Node tempNode = headNode;
            for (int i = 0; i < listSize; i++) {
                if (number == tempNode.transactionNumber) {
                    return Optional.of(tempNode.derivative);
                } else {
                    tempNode = tempNode.nextNode;
                }
            }
            Optional<Derivative> op = Optional.empty();
            return op;
        }
    }

    public void replaceByTransactionNumber(int number, Derivative derivative) throws IndexOutOfBoundsException {
        if (number < 0) {
            throw new IndexOutOfBoundsException("Transaction number cannot be negative ");
        } else {
            Node tempNode = headNode;
            for (int i = 0; i < listSize; i++) {
                if (number == tempNode.transactionNumber) {
                    tempNode.derivative = derivative;
                } else {
                    tempNode = tempNode.nextNode;
                }
            }
        }
    }

    private LocalDateTime[] getAllDates() {
        if(listSize == 0) {
            return new LocalDateTime[0];
        } else {
            LocalDateTime[] dates = new LocalDateTime[listSize];
            Node tempNode = headNode;
            for(int i = 0; i < listSize; i++){
                dates[i] = tempNode.time;
                tempNode = tempNode.nextNode;
            }
            Arrays.sort(dates);
            return dates;
        }
    }

    public Optional<LocalDateTime> getDateOfFirstTransaction() {
       if(listSize == 0) {
           Optional<LocalDateTime> op = Optional.empty();
           return op;
       } else {
           return Optional.of(getAllDates()[0]);
       }
    }

    public Optional<LocalDateTime> getDateOfLastTransaction() {
        if(listSize == 0) {
            Optional<LocalDateTime> op = Optional.empty();
            return op;
        } else {
            return Optional.of(getAllDates()[listSize-1]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedDerivativeList)) return false;
        LinkedDerivativeList that = (LinkedDerivativeList) o;
        if (this.listSize != that.listSize) {
            return false;
        } else {
            for (int i = 0; i < this.listSize; i++) {
                if (!this.getDerivative(i).equals(that.getDerivative(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int code = 7919;
        Node tempNode = headNode;
        for (int i = 0; i < this.listSize; i++) {
            code += tempNode.derivative.hashCode();
            tempNode = tempNode.nextNode;
        }
        return code;
    }
}
