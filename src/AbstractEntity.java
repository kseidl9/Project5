import java.util.List;
import processing.core.PImage;

abstract class AbstractEntity implements Entity
{
   private String id;
   private Point position;
   protected List<PImage> images;

   protected AbstractEntity(String id, Point position, List<PImage> images)
   {
      this.id = id;
      this.position = position;
      this.images = images;
   }

   // animated entities will have more than one image, so will
   // need to override this
   public PImage getCurrentImage() {
      return images.get(0);
   }

   public String getId() {
      return id;
   }

   public Point getPosition() {
      return position;
   }

   public void setPosition(Point position) {
      this.position = position;
   }
}
