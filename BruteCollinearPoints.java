import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        Point[] sortedPoints = validatedCopy(points);
        ArrayList<LineSegment> found = new ArrayList<LineSegment>();
        for (int p = 0; p < sortedPoints.length; p++) {
            for (int q = p + 1; q < sortedPoints.length; q++) {
                for (int r = q + 1; r < sortedPoints.length; r++) {
                    double firstSlope = sortedPoints[p].slopeTo(sortedPoints[q]);
                    double secondSlope = sortedPoints[p].slopeTo(sortedPoints[r]);
                    if (Double.compare(firstSlope, secondSlope) != 0) {
                        continue;
                    }
                    for (int s = r + 1; s < sortedPoints.length; s++) {
                        double thirdSlope = sortedPoints[p].slopeTo(sortedPoints[s]);
                        if (Double.compare(firstSlope, thirdSlope) == 0) {
                            found.add(new LineSegment(sortedPoints[p], sortedPoints[s]));
                        }
                    }
                }
            }
        }
        lineSegments = found.toArray(new LineSegment[found.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
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
