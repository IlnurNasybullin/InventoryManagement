package io.github.ilnurnasybullin.im.entity;

import java.util.Iterator;

public interface DirectedGraph<V> {

    void addVertex(V vertex);
    void addEdge(V source, V target);
    Iterator<V> topologicalOrderIterator();
    void clearAll();

}
