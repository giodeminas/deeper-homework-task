package com.deeper.homework.controller;

import com.deeper.homework.model.dto.IntersectionRequest;
import com.deeper.homework.model.dto.IntersectionResponse;
import com.deeper.homework.service.GeometryService;
import com.deeper.homework.service.RequestCounterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GeometryController {

  private final GeometryService geometryService;
  private final RequestCounterService requestCounterService;

  @Autowired
  public GeometryController(GeometryService geometryService,
      RequestCounterService requestCounterService) {
    this.geometryService = geometryService;
    this.requestCounterService = requestCounterService;
  }

  @PostMapping("/intersect")
  public ResponseEntity<IntersectionResponse> checkIntersection(
      @RequestBody IntersectionRequest request) {
    requestCounterService.increment(); // Increment active requests
    try {
      List<int[]> intersectionPoints = geometryService.getIntersectionPoints(
          request.getLineStart(),
          request.getLineEnd(),
          request.getSquare()
      );
      boolean intersects = !intersectionPoints.isEmpty();
      return ResponseEntity.ok(new IntersectionResponse(intersects, intersectionPoints));
    } finally {
      requestCounterService.decrement(); // Decrement active requests
    }
  }
}