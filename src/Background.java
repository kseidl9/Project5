import java.util.List;
import processing.core.PImage;

final class Background
{
   private List<PImage> images;

   public Background(List<PImage> images)
   {
      this.images = images;
   }

   public PImage getCurrentImage() {
      return images.get(0);
   }
}
