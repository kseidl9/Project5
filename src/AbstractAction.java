
import java.util.Random;

abstract class AbstractAction implements Action
{
   private Entity entity;

   protected AbstractAction(Entity entity)
   {
      this.entity = entity;
   }

   public Entity getEntity() {
      return entity;
   }

   public abstract void executeAction(EventScheduler scheduler);
}
