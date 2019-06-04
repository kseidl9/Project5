public interface EntityWithAction extends Entity {
    public int getActionPeriod();
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}
