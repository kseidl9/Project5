
final class ActivityAction extends AbstractAction
{
   private WorldModel world;
   private ImageStore imageStore;

   public ActivityAction(EntityWithAction entity, WorldModel world, ImageStore imageStore)
   {
      super(entity);
      this.world = world;
      this.imageStore = imageStore;
   }

   public void executeAction(EventScheduler scheduler)
   {
      Entity entity = getEntity();
      // must be true because of constructor
      assert(entity instanceof EntityWithAction);
      ((EntityWithAction)entity).executeActivity(world, imageStore, scheduler);

   }
}
