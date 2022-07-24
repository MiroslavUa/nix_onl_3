package com.kulbachniy.homeworks.model.containers.lists;

import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.util.Iterator;
import java.util.NoSuchElementException;
public class DerivativeListIterator implements Iterator<Derivative> {
    private int currentIndex;
    private boolean nextDerivative;
    private AbstractDerivativeList abstractDerivativeList;

    public DerivativeListIterator (AbstractDerivativeList abstractDerivativeList) {
        currentIndex = 0;
        this.abstractDerivativeList = abstractDerivativeList;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < abstractDerivativeList.size();
    }

    @Override
    public Derivative next() throws NoSuchElementException {
        if (currentIndex >= abstractDerivativeList.size()) {
            throw new NoSuchElementException("There is no such elements");
        }
        Derivative derivative = abstractDerivativeList.getDerivative(currentIndex).get();
        ++currentIndex;
        nextDerivative = true;
        return derivative;
    }

    @Override
    public void remove() throws IllegalStateException {
        if (!nextDerivative) {
            throw new IllegalStateException("It is necessary to call next() before removing!");
        } else {
            abstractDerivativeList.remove(abstractDerivativeList.getDerivative(currentIndex-1).get());
            --currentIndex;
            nextDerivative = false;
        }
    }
}
