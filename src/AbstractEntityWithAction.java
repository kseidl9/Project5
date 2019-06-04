import processing.core.PImage;

import java.util.List;
import java.util.Random;

abstract class AbstractEntityWithAction extends AbstractEntity implements EntityWithAction
{
   private static final Random rand = new Random();
   private int actionPeriod;

   protected AbstractEntityWithAction(String id, Point position, List<PImage> images, int actionPeriod)
   {
      super(id, position, images);
      this.actionPeriod = actionPeriod;
   }

   protected int nextInt(int bound) {
      return rand.nextInt(bound);
   }

   public int getActionPeriod() {
      return actionPeriod;
   }

   public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

   // default behavior
   public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
      scheduler.scheduleEvent(this,
              new ActivityAction(this, world, imageStore),
              this.getActionPeriod());
   }
}
