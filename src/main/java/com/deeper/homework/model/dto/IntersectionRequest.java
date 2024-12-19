package com.deeper.homework.model.dto;

import lombok.Data;

@Data
public class IntersectionRequest {
  private int[] lineStart; // [x1, y1]
  private int[] lineEnd;   // [x2, y2]
  private int[][] square;  // [[x1, y1], [x2, y2], [x3, y3], [x4, y4]]
}