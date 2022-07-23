package com.kulbachniy.homeworks.model.containers.lists;

import com.kulbachniy.homeworks.model.derivative.Derivative;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract  class AbstractDerivativeList implements Iterable<Derivative>{

    public abstract void add(Derivative derivative) throws NullPointerException, IOException;

    public abstract boolean remove(Derivative derivative);

    public abstract int size();

    public abstract Derivative getDerivative(int index) throws IndexOutOfBoundsException;

    public Stream<Derivative> getStream() {
        Derivative[] temp = new Derivative[size()];
        for (int i = 0; i < this.size(); i++) {
            temp[i] = this.getDerivative(i);
        }
        return Arrays.stream(temp);
    }

    @Override
    public Iterator<Derivative> iterator() {
        return new DerivativeListIterator(this);
    }

    @Override
    public String toString() throws NullPointerException{
        StringBuilder builder = new StringBuilder();
        if (this instanceof ArrayDerivativeList || this instanceof LinkedDerivativeList) {
            for (int i = 0; i < this.size(); i++ ) {
                builder.append(i+1).append(". ").append(this.getDerivative(i).toString());
                if (i == this.size()-1) {
                    builder.append(".");
                } else {
                    builder.append(",\n ");
                }
            }
            if (this.size() != 0) {
                return "List contains " + this.size() + " task(s). They are:\n " + builder;
            } else {
                return "List contains " + this.size() + " task(s).";
            }
        } else {
            return null;
        }
    }
}
