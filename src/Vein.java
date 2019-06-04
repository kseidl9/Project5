import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class Vein extends AbstractEntityWithAction {

    private static final int ORE_CORRUPT_MAX = 30000;
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final String ORE_ID_PREFIX = "ore -- ";

    public Vein(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = world.findOpenAround(this.getPosition());

        if (openPt.isPresent()) {
            int period = ORE_CORRUPT_MIN + nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN);
            Ore ore = new Ore(ORE_ID_PREFIX + this.getId(), openPt.get(),
                    imageStore.getImageList(WorldModel.ORE_KEY),
                    period);
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduleActions(scheduler, world, imageStore);
    }
}
