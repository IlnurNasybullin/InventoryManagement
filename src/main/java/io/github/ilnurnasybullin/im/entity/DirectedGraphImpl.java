package io.github.ilnurnasybullin.im.entity;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.util.Iterator;

public class DirectedGraphImpl<V> implements DirectedGraph<V> {

    private final Graph<V, DefaultEdge> graph;

    public DirectedGraphImpl() {
        graph = new DirectedAcyclicGraph<>(DefaultEdge.class);
    }

    @Override
    public void addVertex(V vertex) {
        graph.addVertex(vertex);
    }

    @Override
    public void addEdge(V source, V target) {
        addVertex(source);
        addVertex(target);
        graph.addEdge(source, target);
    }

    @Override
    public Iterator<V> topologicalOrderIterator() {
        return new TopologicalOrderIterator<>(graph);
    }

    @Override
    public void clearAll() {
        graph.edgeSet().clear();
        graph.vertexSet().clear();
    }
}
