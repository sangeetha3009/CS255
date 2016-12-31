//package com.custardsource.cache.policy;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;


public class QueueAdapter<E> implements Queue<E> {
    private final Collection<E> inner;

    public QueueAdapter(Collection<E> inner) {
        if (inner == null) {
            throw new NullPointerException();
        }
        this.inner = inner;
    }

    public E element() {
        if (inner.isEmpty()) {
            throw new NoSuchElementException();
        }
        return inner.iterator().next();
    }

    public boolean offer(E e) {
        try {
            return inner.add(e);
        } catch (UnsupportedOperationException uoe) {
            return false;
        } catch (IllegalStateException ise) {
            return false;
        }
    }

    public E peek() {
        if (inner.isEmpty()) {
            return null;
        }
        return inner.iterator().next();
    }

    public E poll() {
        if (inner.isEmpty()) {
            return null;
        }
        E value = inner.iterator().next();
        inner.remove(value);
        return value;
    }

    public E remove() {
        E value = poll();
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }
    
    // Everything below this line is just a simple delegation

    public boolean add(E e) {
        return inner.add(e);
    }

    public boolean addAll(Collection<? extends E> c) {
        return inner.addAll(c);
    }

    public void clear() {
        inner.clear();
    }

    public boolean contains(Object o) {
        return inner.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return inner.containsAll(c);
    }

    public boolean isEmpty() {
        return inner.isEmpty();
    }

    public Iterator<E> iterator() {
        return inner.iterator();
    }

    public boolean remove(Object o) {
        return inner.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return inner.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return inner.retainAll(c);
    }

    public int size() {
        return inner.size();
    }

    public Object[] toArray() {
        return inner.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return inner.toArray(a);
    }

    @Override
    public String toString() {
        return inner.toString();
    }
}