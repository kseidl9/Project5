import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Miner extends AbstractMobileEntity
{
   private int resourceLimit;
   private int resourceCount;

   public Miner(String id, Point position,
                List<PImage> images, int resourceLimit, int resourceCount,
                int actionPeriod, int animationPeriod)
   {
      super(id, position, images, actionPeriod, animationPeriod);
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
   }

   @Override
   public Point nextPosition(WorldModel world, Point destPos)
   {
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

   public void incrementResourceCount() {
      resourceCount += 1;
   }

   public boolean isFull() {
      return resourceCount >= resourceLimit;
   }

   public Miner createMinerFull() {
      return new Miner(getId(), getPosition(), images,
                       resourceLimit, resourceLimit, getActionPeriod(), getAnimationPeriod());
   }

   public Miner createMinerNotFull() {
      return new Miner(getId(), getPosition(), images,
                       resourceLimit, 0, getActionPeriod(), getAnimationPeriod());
   }

   @Override
   public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
      if (isFull()) {
         Optional<Entity> fullTarget = world.findNearest(getPosition(), Blacksmith.class);

         if (fullTarget.isPresent() &&
                 moveToFull(world, (Blacksmith)fullTarget.get(), scheduler))
         {
            transformFull(world, scheduler, imageStore);
         }
         else
         {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    getActionPeriod());
         }
      }
      else {
         Optional<Entity> notFullTarget = world.findNearest(getPosition(), Ore.class);

         if (!notFullTarget.isPresent() ||
                 !moveToNotFull(world, (Ore) notFullTarget.get(), scheduler) ||
                 !transformNotFull(world, scheduler, imageStore))
         {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    getActionPeriod());
         }
      }

   }

   private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
   {
      if (this.isFull()) {
         Miner miner = this.createMinerFull();

         world.removeEntity(this);
         scheduler.unscheduleAllEvents(this);

         world.addEntity(miner);
         miner.scheduleActions(scheduler, world, imageStore);

         return true;
      }

      return false;
   }

   private void transformFull(WorldModel world,
                                     EventScheduler scheduler, ImageStore imageStore)
   {
      Miner miner = this.createMinerNotFull();

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      world.addEntity(miner);
      miner.scheduleActions(scheduler, world, imageStore);
   }

   private boolean moveToNotFull(WorldModel world,
                                 Ore target, EventScheduler scheduler)
   {
      if (this.getPosition().adjacent(target.getPosition())) {
         this.incrementResourceCount();
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);

         return true;
      }
      else {
         Point nextPos = this.nextPosition(world, target.getPosition());

         if (!this.getPosition().equals(nextPos)) {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent()) {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   private boolean moveToFull(WorldModel world,
                              Blacksmith target, EventScheduler scheduler)
   {
      if (this.getPosition().adjacent(target.getPosition()))
      {
         return true;
      }
      else
      {
         Point nextPos = this.nextPosition(world, target.getPosition());

         if (!this.getPosition().equals(nextPos)) {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent()) {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }

}
