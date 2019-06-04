import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new LinkedList<>();
        PriorityQueue<Node> open = new PriorityQueue<>();
        HashSet<Point> closed = new HashSet<>();
        Node endNode = null;

        //add new node to the queue (start)
        Node first = new Node(start, null, end);
        open.add(first);
        closed.add(first.getPos());
        while (!open.isEmpty()) {
            Node last = open.remove();
            if (withinReach.test(last.getPos(), end)) {
                endNode = new Node(end, last, end);
                break;

            }
            //get potential neighbors
            potentialNeighbors.apply(last.getPos()).filter(canPassThrough).filter(p -> !closed.contains(p)).
                    forEach(p -> {
                        Node pNode = new Node(p, last, end);
                        open.add(pNode);
                        closed.add(p);
                    });

        }
        while (endNode != null) {
            path.add(endNode.getPos());
            endNode = endNode.getPriorNode();

        }
        return path;

    }
}
class Node implements Comparable<Node>{
    private Node priorNode;
    private Point pos;
    private int g, h , f;

    public Node(Point current, Node previous, Point end){
        if (previous == null){
            priorNode = null;
            pos = current;
            g = 0;
            h = (int)dist(current, end);
            f = g + h;
        }
        else{
            priorNode = previous;
            pos = current;
            g = previous.getG() + 1;
            h = (int)(dist(current, end));
            f = g + h;
        }

    }

    public Node getPriorNode() {
        return priorNode;
    }
    public Point getPos(){
        return pos;
    }

    public int getG() {
        return g;
    }
    public int getH(){
        return h;
    }
    public int getF(){
        return f;
    }

    private int dist(Point p1, Point p2){
        return Math.max(Math.abs(p2.x-p1.x), Math.abs(p2.y-p1.y));
    }

    @Override
    public int compareTo(Node o) {

        return f - o.getF();

    }
}
