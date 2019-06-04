import processing.core.PImage;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Bunny extends AbstractMobileEntity{

    private static final String CARROT_KEY = "carrot";
    private static final String BUNNY_KEY = "bunny";



    public Bunny(String id, Point position,
                 List<PImage> images,
                 int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod); }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        Point position = getPosition();
        PathingStrategy strategy = getStrategy();
        List<Point> pts = strategy.computePath(position, destPos,
                p -> !world.isOccupied(p),
                (s,d) -> s.adjacent(d),
                PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
        if (pts.isEmpty())
            return position;
        else
            return pts.get(pts.size() -2);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> minerTarget = world.findNearest(this.getPosition(), Miner.class);
        long nextPeriod = this.getActionPeriod();

        if (minerTarget.isPresent())
        {
            Point tgtPos = minerTarget.get().getPosition();

            if (moveTo(world, (Miner)minerTarget.get(), scheduler))
            {
                Carrot carrot = new Carrot("carrot", tgtPos, imageStore.getImageList(CARROT_KEY));
                nextPeriod += this.getActionPeriod();
            }
        }

        scheduler.scheduleEvent(this,
                new ActivityAction(this, world, imageStore),
                nextPeriod);
    }

    public static void spawnBunnies(WorldModel world, Point p, ImageStore imageStore){
        //spawn bunnies
        System.out.print("here");
        Random rand = new Random();
        System.out.print("rand: " + rand.nextInt(20));


        for (int i = 0; i < rand.nextInt(20); i++ ){
            Point close = new Point(p.x + rand.nextInt(7), p.y + rand.nextInt(7));
            Bunny bun = new Bunny("bunny",close, imageStore.getImageList(BUNNY_KEY), 6,5 );

            world.addEntity(bun);
            System.out.print("bun");
        }

    }
    private boolean moveTo(WorldModel world, Miner target, EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
}
