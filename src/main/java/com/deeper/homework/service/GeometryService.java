package com.deeper.homework.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GeometryService {

  public List<int[]> getIntersectionPoints(int[] lineStart, int[] lineEnd, int[][] polygon) {
    // Simulate processing time
    try {
      Thread.sleep(20000); // Sleep for 20 seconds
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    List<int[]> intersectionPoints = new ArrayList<>();
    int n = polygon.length;

    for (int i = 0; i < n; i++) {
      int[] startVertex = polygon[i];
      int[] endVertex = polygon[(i + 1) % n];

      if (doIntersect(lineStart, lineEnd, startVertex, endVertex)) {
        int[] intersectionPoint = getIntersectionPoint(lineStart, lineEnd, startVertex, endVertex);
        if (intersectionPoint != null && !isDuplicate(intersectionPoints, intersectionPoint)) {
          intersectionPoints.add(intersectionPoint);
        }
      }
    }
    return intersectionPoints;
  }

  private int[] getIntersectionPoint(int[] p1, int[] q1, int[] p2, int[] q2) {
    double A1 = q1[1] - p1[1];
    double B1 = p1[0] - q1[0];
    double C1 = A1 * p1[0] + B1 * p1[1];

    double A2 = q2[1] - p2[1];
    double B2 = p2[0] - q2[0];
    double C2 = A2 * p2[0] + B2 * p2[1];

    double determinant = A1 * B2 - A2 * B1;

    if (determinant == 0) {
      // The lines are parallel (no intersection)
      return null;
    }

    double x = (B2 * C1 - B1 * C2) / determinant;
    double y = (A1 * C2 - A2 * C1) / determinant;

    return new int[]{(int) x, (int) y}; // Cast to int if the coordinates are integers
  }

  private boolean isDuplicate(List<int[]> points, int[] point) {
    for (int[] existingPoint : points) {
      if (existingPoint[0] == point[0] && existingPoint[1] == point[1]) {
        return true;
      }
    }
    return false;
  }

  private boolean doIntersect(int[] p1, int[] q1, int[] p2, int[] q2) {
    int o1 = orientation(p1, q1, p2);
    int o2 = orientation(p1, q1, q2);
    int o3 = orientation(p2, q2, p1);
    int o4 = orientation(p2, q2, q1);

    // General case
    if (o1 != o2 && o3 != o4) {
      return true;
    }

    // Special Cases
    if (o1 == 0 && onSegment(p1, p2, q1)) {
      return true;
    }
    if (o2 == 0 && onSegment(p1, q2, q1)) {
      return true;
    }
    if (o3 == 0 && onSegment(p2, p1, q2)) {
      return true;
    }
    if (o4 == 0 && onSegment(p2, q1, q2)) {
      return true;
    }

    return false;
  }

  private int orientation(int[] p, int[] q, int[] r) {
    int val = (q[1] - p[1]) * (r[0] - q[0]) - (q[0] - p[0]) * (r[1] - q[1]);
    if (val == 0) {
      return 0;  // Collinear
    }
    return (val > 0) ? 1 : 2;  // Clockwise or Counterclockwise
  }

  private boolean onSegment(int[] p, int[] q, int[] r) {
    return q[0] <= Math.max(p[0], r[0]) && q[0] >= Math.min(p[0], r[0]) &&
        q[1] <= Math.max(p[1], r[1]) && q[1] >= Math.min(p[1], r[1]);
  }
}
