import processing.core.PImage;

import java.util.List;

abstract class AbstractAnimatedEntity extends AbstractEntityWithAction implements AnimatedEntity
{
   private int imageIndex;
   private int animationPeriod;

   protected AbstractAnimatedEntity(String id, Point position, List<PImage> images,
                                    int actionPeriod, int animationPeriod)
   {
      super(id, position, images, actionPeriod);
      this.imageIndex = 0;
      this.animationPeriod = animationPeriod;
   }

   public void nextImage() {
      imageIndex = (imageIndex + 1) % images.size();
   }

   @Override
   public PImage getCurrentImage() {
      return images.get(imageIndex);
   }

   public int getAnimationPeriod() { return animationPeriod; }
}
