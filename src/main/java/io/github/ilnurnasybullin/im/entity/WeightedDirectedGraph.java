package io.github.ilnurnasybullin.im.entity;

import java.util.Iterator;
import java.util.List;

public interface WeightedDirectedGraph<V, E> {

    void addVertex(V vertex);
    void addEdge(V from, V to, double fromWeight, double toWeight);
    void clear();

    Iterator<V> breadthIterator(V vertex);
    List<Edge<V>> path(V source, V target);
}