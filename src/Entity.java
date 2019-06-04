import processing.core.PImage;

public interface Entity {
    public String getId();
    public PImage getCurrentImage();
    public Point getPosition();
    public void setPosition(Point p);
}
