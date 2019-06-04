import processing.core.PImage;

import java.util.List;

public class Bunny extends AbstractMobileEntity{

    private int resourceLimit;
    private int resourceCount;

    public Bunny(String id, Point position,
                 List<PImage> images, int resourceLimit, int resourceCount,
                 int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        return null;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    }
}