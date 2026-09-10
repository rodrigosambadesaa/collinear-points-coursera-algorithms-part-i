import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        Point[] sortedPoints = validatedCopy(points);
        ArrayList<LineSegment> found = new ArrayList<LineSegment>();
        for (Point origin : sortedPoints) {
            Point[] bySlope = Arrays.copyOf(sortedPoints, sortedPoints.length);
            Arrays.sort(bySlope, origin.slopeOrder());
            findSegments(origin, bySlope, found);
        }
        lineSegments = found.toArray(new LineSegment[found.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private static void findSegments(Point origin, Point[] bySlope,
                                     ArrayList<LineSegment> found) {
        int start = 1;
        while (start < bySlope.length) {
            double slope = origin.slopeTo(bySlope[start]);
            int end = start + 1;
            while (end < bySlope.length
                    && Double.compare(slope, origin.slopeTo(bySlope[end])) == 0) {
                end++;
            }
            if (end - start >= 3) {
                Point smallest = origin;
                Point largest = origin;
                for (int i = start; i < end; i++) {
                    if (bySlope[i].compareTo(smallest) < 0) {
                        smallest = bySlope[i];
                    }
                    if (bySlope[i].compareTo(largest) > 0) {
                        largest = bySlope[i];
                    }
                }
                if (origin.compareTo(smallest) == 0) {
                    found.add(new LineSegment(smallest, largest));
                }
            }
            start = end;
        }
    }

    private static Point[] validatedCopy(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Point[] copy = Arrays.copyOf(points, points.length);
        for (Point point : copy) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i - 1].compareTo(copy[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        return copy;
    }
}
