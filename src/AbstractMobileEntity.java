import processing.core.PImage;

import java.util.List;

abstract class AbstractMobileEntity extends AbstractAnimatedEntity implements MobileEntity{

   private PathingStrategy strategy = new AStarPathingStrategy();
   //PathingStrategy strategy = new SingleStepPathingStrategy();
   protected AbstractMobileEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
   {
      super(id, position, images, actionPeriod, animationPeriod);
   }

   public abstract Point nextPosition(WorldModel world, Point destPos);

   @Override
   public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
      super.scheduleActions(scheduler, world, imageStore);
      scheduler.scheduleEvent(this,
              new AnimationAction(this, 0),
              this.getAnimationPeriod());
   }

   public PathingStrategy getStrategy() {
      return strategy;
   }
}
