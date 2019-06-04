
final class AnimationAction extends AbstractAction
{
   private int repeatCount;

   public AnimationAction(AnimatedEntity entity, int repeatCount)
   {
      super(entity);
      this.repeatCount = repeatCount;
   }

   public void executeAction(EventScheduler scheduler)
   {
      AnimatedEntity animatedEntity = (AnimatedEntity) getEntity();
      animatedEntity.nextImage();

      if (repeatCount != 1) {
         scheduler.scheduleEvent(getEntity(),
            new AnimationAction(animatedEntity,
               Math.max(repeatCount - 1, 0)),
                 animatedEntity.getAnimationPeriod());
      }
   }
}
