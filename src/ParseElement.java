public class ParseElement {
    private static final int BGND_ROW = 3;
    private static final int BGND_COL = 2;
    private static final int BGND_ID = 1;
    private static final int BGND_NUM_PROPERTIES = 4;

    private static final int MINER_ANIMATION_PERIOD = 6;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ROW = 3;
    private static final int MINER_COL = 2;
    private static final int MINER_ID = 1;
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final String MINER_KEY = "miner";

    private static final int OBSTACLE_ROW = 3;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final String OBSTACLE_KEY = "obstacle";

    private static final int ORE_ACTION_PERIOD = 4;
    private static final int ORE_ROW = 3;
    private static final int ORE_COL = 2;
    private static final int ORE_ID = 1;
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final String ORE_KEY = "ore";

    private static final int SMITH_ROW = 3;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ID = 1;
    private static final int SMITH_NUM_PROPERTIES = 4;
    private static final String SMITH_KEY = "blacksmith";

    private static final int VEIN_ACTION_PERIOD = 4;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ID = 1;
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final String VEIN_KEY = "vein";



    public static boolean parseBackground(WorldModel world, String[] properties, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt, new Background(imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    public static boolean parseMiner(WorldModel world, String[] properties, ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            Entity entity = new Miner(
                    properties[MINER_ID],
                    pt,
                    imageStore.getImageList(MINER_KEY),
                    Integer.parseInt(properties[MINER_LIMIT]),
                    0,
                    Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MINER_ANIMATION_PERIOD])
            );
            world.tryAddEntity(entity);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    public static boolean parseObstacle(WorldModel world, String[] properties, ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = new Obstacle(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    public static boolean parseOre(WorldModel world, String[] properties, ImageStore imageStore)
    {
        if (properties.length == ORE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            Entity entity = new Ore(properties[ORE_ID],
                    pt, imageStore.getImageList(ORE_KEY),
                    Integer.parseInt(properties[ORE_ACTION_PERIOD]));
            world.tryAddEntity(entity);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }

    public static boolean parseSmith(WorldModel world, String[] properties, ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = new Blacksmith(properties[SMITH_ID],
                    pt, imageStore.getImageList(SMITH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    public static boolean parseVein(WorldModel world, String[] properties, ImageStore imageStore)
    {
        if (properties.length == VEIN_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            Entity entity = new Vein(properties[VEIN_ID], pt,
                    imageStore.getImageList(VEIN_KEY),
                    Integer.parseInt(properties[VEIN_ACTION_PERIOD]));
            world.tryAddEntity(entity);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }
}
