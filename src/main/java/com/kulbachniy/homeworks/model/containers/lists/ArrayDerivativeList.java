package com.kulbachniy.homeworks.model.containers.lists;

import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class ArrayDerivativeList extends AbstractDerivativeList{
    private Derivative[] derivativeList;
    private int listSize;
    private int listElements;

    public ArrayDerivativeList() {
        derivativeList = null;
        listSize = 0;
        listElements = 0;
    }

    @Override
    public void add(Derivative derivative) throws NullPointerException {
        if (derivative == null) {
            throw new NullPointerException("Derivative cannot be NULL");
        }
        if (derivativeList == null) {
            listSize = 2;
            derivativeList = new Derivative[listSize];
            derivativeList[0] = derivative;
            listElements = 1;
        } else if ((listElements < listSize)
                && (derivativeList[listElements] == null)) {
            derivativeList[listElements] = derivative;
            listElements++;
        } else {
            int len = listSize;
            Derivative[] temporaryArray = derivativeList;
            listSize = len * 2;
            derivativeList = new Derivative[listSize];
            System.arraycopy(temporaryArray, 0, derivativeList, 0, listElements);
            derivativeList[len] = derivative;
            listElements++;
        }
    }

    @Override
    public boolean remove(Derivative derivative) {
        if (derivativeList == null) {
            return false;
        } else {
            for (int i = 0; i < listElements; i++) {
                if (derivativeList[i].equals(derivative)) {
                    derivativeList[i] = null;
                    listElements--;
                    if (i == listElements) {
                        return decrease();
                    } else {
                        for (int j = i; j < listElements; j++) {
                            derivativeList[j] = derivativeList[j + 1];
                        }
                        derivativeList[listElements] = null;
                        return  decrease();
                    }
                }
            }
            return false;
        }
    }

    private boolean decrease() {
        if (listSize > listElements * 2) {
            int len = listSize;
            Derivative[] temporaryArray = derivativeList;
            listSize = (int) len / 2 + 1;
            derivativeList = new Derivative[listSize];
            System.arraycopy(temporaryArray, 0, derivativeList, 0, listElements);
        }
        return true;
    }

    @Override
    public int size() {
        if (derivativeList == null) {
            return 0;
        } else {
            return listElements;
        }
    }

    @Override
    public Optional<Derivative> getDerivative(int index) throws IndexOutOfBoundsException {
        if (index >= listElements || index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be greater than " + (listElements - 1));
        }
        if (derivativeList == null) {
            Optional<Derivative> op = Optional.empty();
            return op;
        } else {
            return Optional.of(derivativeList[index]);
        }
    }

    @Override
    public Stream<Derivative> getStream() {
        return super.getStream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayDerivativeList)) return false;
        ArrayDerivativeList derivativeList1 = (ArrayDerivativeList) o;
        return listSize == derivativeList1.listSize &&
                listElements == derivativeList1.listElements &&
                Arrays.equals(derivativeList, derivativeList1.derivativeList);
    }

    @Override
    public int hashCode() {
        int code = 7919;
        for (int i = 0; i < listElements; i++) {
            code += derivativeList[i].hashCode();
        }
        return code;
    }
}
