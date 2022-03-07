package io.github.ilnurnasybullin.im.entity;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.Iterator;
import java.util.List;

public class WeightedDirectedGraphImpl<V> implements WeightedDirectedGraph<V, Edge<V>> {

    private final Graph<V, Edge<V>> graph;

    public WeightedDirectedGraphImpl() {
        @SuppressWarnings("unchecked")
        Class<Edge<V>> type = (Class<Edge<V>>) (Object) Edge.class;
        this.graph = new SimpleGraph<>(type);
    }

    @Override
    public void addVertex(V vertex) {
        graph.addVertex(vertex);
    }

    @Override
    public void addEdge(V from, V to, double fromWeight, double toWeight) {
        addVertex(from);
        addVertex(to);

        graph.addEdge(from, to, new Edge<>(from, to, fromWeight, toWeight));
    }

    @Override
    public void clear() {
        graph.edgeSet().clear();
        graph.vertexSet().clear();
    }

    @Override
    public Iterator<V> breadthIterator(V vertex) {
        return new BreadthFirstIterator<>(graph, vertex);
    }

    @Override
    public List<Edge<V>> path(V source, V target) {
        DijkstraShortestPath<V, Edge<V>> path = new DijkstraShortestPath<>(graph);
        return path.getPath(source, target).getEdgeList();
    }

}
