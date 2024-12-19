package com.deeper.homework.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IntersectionResponse {
  private boolean intersects;
  private List<int[]> intersectionPoints;
}