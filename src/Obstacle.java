import processing.core.PImage;

import java.util.List;

final class Obstacle extends AbstractEntity {
    public Obstacle(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }
}
