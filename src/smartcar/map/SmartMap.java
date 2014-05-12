package smartcar.map;

/**
 *
 * @author cgirls
 */
import smartcar.map.SmartMapInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import smartcar.map.SmartMapData;
//import smartcar.map.SmartMapInfo;
//import smartcar.map.SmartMapQRCode;
//import smartcar.map.SmartMapBarrier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import smartcar.core.Point;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import smartcar.core.SystemProperty;
import smartcar.test.env.testLogger;
import smartcar.test.sensor.testArduinoBridge;

//Node对象用于封装节点信息，包括名字和子节点
//Dijkstra对象用于计算起始节点到所有其他节点的最短路径
class Dijkstra {
    public static Log logger = LogFactory.getLog(Dijkstra.class);
    Set<Node> open = new HashSet<Node>();
    Set<Node> close = new HashSet<Node>();
    Map<String, Double> path = new HashMap<String, Double>();//封装路径距离 
    Map<String, String> pathInfo = new HashMap<String, String>();//封装路径信息 

    public Node init(int x, int y, Node map[][]) {
        Node[][] GridMap = map;
        /*
         for(int i = 0; i < numofx; i ++){	
         for(int j=0; j< numofy; j++){
    					
         GridMap[i][j]=new Node("m" + i + j); //give name
    					
         }
         }*/
        //初始路径,因没有A->E这条路径,所以path(E)设置为Double.MAX_VALUE 
        path.put("(1,0)", 1.0);
        // path.put("m10", 1.0);
        pathInfo.put("(1,0)", "(0,0)->(1,0)");
        //pathInfo.put("m10", "m00->m10");
        path.put("(0,1)", 1.0);
        // path.put("m020", 1.0);
        pathInfo.put("(0,1)", "(0,0)->(0,1)");
        // pathInfo.put("m020", "m00->m020");
        path.put("(1,1)", Math.sqrt(2.0));
        // path.put("m021", Math.sqrt(2.0));
        pathInfo.put("(1,1)", "(0,0)->(1,1)");
        //  pathInfo.put("m021", "m00->m021"); 
        for (int i = 0; i < GridMap.length; i++) {

            for (int j = 0; j < GridMap[0].length; j++) {
                //for test
                if (i == x && j == y) {
                    close.add(GridMap[i][j]);
                } else {
                    open.add(GridMap[i][j]);
                }

                if (GridMap[i][j].getblack() == false) {//不能为障碍物
                    if (i == x + 1 && j == y) {
                        path.put("(" + i + "," + j + ")", 1.0);
                        pathInfo.put("(" + i + "," + j + ")", "(" + x + "," + y + ")" + "->" + "(" + i + "," + j + ")");
                    } else if (i == x && j == y + 1) {
                        path.put("(" + i + "," + j + ")", 1.0);
                        pathInfo.put("(" + i + "," + j + ")", "(" + x + "," + y + ")" + "->" + "(" + i + "," + j + ")");
                    } else if (i == x + 1 && j == y + 1) {
                        path.put("(" + i + "," + j + ")", 1.0);
                        pathInfo.put("(" + i + "," + j + ")", "(" + x + "," + y + ")" + "->" + "(" + i + "," + j + ")");
                    } else if (i == x - 1 && j == y) {
                        path.put("(" + i + "," + j + ")", 1.0);
                        pathInfo.put("(" + i + "," + j + ")", "(" + x + "," + y + ")" + "->" + "(" + i + "," + j + ")");
                    } else if (i == x && j == y - 1) {
                        path.put("(" + i + "," + j + ")", 1.0);
                        pathInfo.put("(" + i + "," + j + ")", "(" + x + "," + y + ")" + "->" + "(" + i + "," + j + ")");
                    } else if (i == x - 1 && j == y - 1) {
                        path.put("(" + i + "," + j + ")", 1.0);
                        pathInfo.put("(" + i + "," + j + ")", "(" + x + "," + y + ")" + "->" + "(" + i + "," + j + ")");
                    } else if (i == x - 1 && j == y + 1) {
                        path.put("(" + i + "," + j + ")", 1.0);
                        pathInfo.put("(" + i + "," + j + ")", "(" + x + "," + y + ")" + "->" + "(" + i + "," + j + ")");
                    } else if (i == x + 1 && j == y - 1) {
                        path.put("(" + i + "," + j + ")", 1.0);
                        pathInfo.put("(" + i + "," + j + ")", "(" + x + "," + y + ")" + "->" + "(" + i + "," + j + ")");
                    } else {
                        path.put("(" + i + "," + j + ")", Double.MAX_VALUE);
                        pathInfo.put("(" + i + "," + j + ")", "m00");
                    }
                }
            }

        }

        return GridMap[x][y];

    }

    public void computePath(Node start) {
        Node nearest = getShortestPath(start);//取距离start节点最近的子节点,放入close 
        if (nearest == null) {
            return;
        }
        // System.out.println("nearest.name" + nearest.getName());
        close.add(nearest);
        open.remove(nearest);
        Map<Node, Double> childs = nearest.getChild();
        for (Node child : childs.keySet()) {
            if (open.contains(child)) {//如果子节点在open中 
                //System.out.println("child.name" + child.getName());
                Double newCompute = path.get(nearest.getName()) + childs.get(child);
                //System.out.println("111:" + child.getName());
                // System.out.println("222:" + newCompute);
                if (path.get(child.getName()) > newCompute) {//之前设置的距离大于新计算出来的距离 
                    path.put(child.getName(), newCompute);
                    pathInfo.put(child.getName(), pathInfo.get(nearest.getName()) + "->" + child.getName());
                }
            }
        }
        computePath(start);//重复执行自己,确保所有子节点被遍历 
        computePath(nearest);//向外一层层递归,直至所有顶点被遍历 
    }
    //输出所有最短路径

    public ArrayList printPathInfo(Node ending) {
        Set<Map.Entry<String, String>> pathInfos = pathInfo.entrySet();
        ArrayList<Point> path_nodes = new ArrayList<Point>();
        ArrayList<SmartMapData> data = new ArrayList<SmartMapData>();//记录计算出的初步路径，未合并
        for (Map.Entry<String, String> pathInfo : pathInfos) {
            //System.out.println(ending.getName()+"name");
            //System.out.println(pathInfo.getKey()+"key");
            if (ending.getName().equals(pathInfo.getKey())) {
                logger.info(pathInfo.getKey() + ":" + pathInfo.getValue());
                //System.out.println("~~~~\n");
                String[] path = pathInfo.getValue().split("->");//将各个结点放入数组
                for (int i = 0; i < path.length; i++) {
                    //System.out.println(path[i]);
                    String str = path[i].substring(1, path[i].length() - 1);
                    //System.out.println(str);
                    String a[] = str.split(",");
                    //System.out.println(a[0] + ".........." + a[1]);
                    SmartMap s = new SmartMap();
                    double grid = s.getGrid();
                    double x = Integer.parseInt(a[0]) * grid + grid / 2;
                    double y = Integer.parseInt(a[1]) * grid + grid / 2;
                    Point p = new Point(x, y);
                    path_nodes.add(p);
                }
                SmartMapData data_part1 = new SmartMapData();
                data_part1.start = path_nodes.get(0);
                data_part1.end = path_nodes.get(1);
                data_part1.child = null;
                data.add(data_part1);
                for (int i = 1; i < path_nodes.size() - 1; i++) {

                    SmartMapData data_part = new SmartMapData();
                    data_part.start = path_nodes.get(i);
                    data_part.end = path_nodes.get(i + 1);
                    data_part.child = null;
                    data.add(data_part);
                    data.get(i - 1).child = data_part;
                }
            }
        }
        return data;
    }

    /**
     * 获取与node最近的子节点
     */
    private Node getShortestPath(Node node) {
        Node res = null;
        double minDis = Double.MAX_VALUE;
        Map<Node, Double> childs = node.getChild();
        for (Node child : childs.keySet()) {
            if (open.contains(child)) {
                double distance = childs.get(child);
                if (distance < minDis) {
                    minDis = distance;
                    res = child;
                }
            }
        }
        return res;
    }
}

//Main用于测试Dijkstra对象
public class SmartMap implements SmartMapInterface {

    public static Log logger = LogFactory.getLog(SmartMap.class.getName());

    private final double width = Double.parseDouble(SystemProperty.getProperty("Room.width"));
    private final double length = Double.parseDouble(SystemProperty.getProperty("Room.length"));
    private final double grid = Double.parseDouble(SystemProperty.getProperty("Map.Grid.Size"));
    //int numofgrid = (int)(width * length / (grid * grid));
    private int numofx = (int) (width / grid);
    private int numofy = (int) (length / grid);
    Node[][] GridMap = new Node[numofx][numofy];
    SmartMapBarrier b = new SmartMapBarrier();
    SmartMapQRCode q = new SmartMapQRCode();

    public void build(SmartMapBarrier b, SmartMapQRCode q) throws IOException {
        logger.info("build map");
        json(b, q);
        for (int i = 0; i < numofx; i++) {
            for (int j = 0; j < numofy; j++) {
                GridMap[i][j] = new Node("(" + i + "," + j + ")"); //give name

            }
        }
        //set barrier
        logger.info("set barrier" + b.num);
        for (int i = 0; i < b.num; i++) {
            logger.info("i:" + i);
            double x = b.barriers.get(i).p.x;
            double y = b.barriers.get(i).p.y;
            int x_i = Integer.parseInt(new java.text.DecimalFormat("0").format(x));
            logger.info("x_i:" + x_i);
            //System.out.println("x_i:" + x_i);
            int y_i = Integer.parseInt(new java.text.DecimalFormat("0").format(y));
            logger.info("y_i:" + y_i);
            //System.out.println("y_i:" + y_i);
            
            GridMap[x_i][y_i].setblack(b.barriers.get(i));
            double length = (b.barriers.get(i).length - grid) / 2;//中心点一侧的长度
            if (length != 0) {
                int num_l = (int) (length / grid) + 1;//中心点一侧的网格个数（除中心网格外）（左右）
                for (int j = 1; j <= num_l; j++) {
                    GridMap[x_i][y_i - num_l].setblack(b.barriers.get(i));
                    GridMap[x_i][y_i + num_l].setblack(b.barriers.get(i));
                }
            }
            double width = (b.barriers.get(i).width - grid) / 2;//中心点一侧的宽度
            if (width != 0) {
                int num_w = (int) (width / grid) + 1;//中心点一侧的网格个数（除中心网格外）（上下）
                for (int j = 1; j <= num_w; j++) {
                    GridMap[x_i - num_w][y_i].setblack(b.barriers.get(i));
                    GridMap[x_i + num_w][y_i].setblack(b.barriers.get(i));
                }
            }
        }

        logger.info("set qrcode");
        //set qrcode
        for (int i = 0; i < q.num; i++) {
            double x = q.qrcodes.get(i).location.x;
            double y = q.qrcodes.get(i).location.y;
            int x_i = Integer.parseInt(new java.text.DecimalFormat("0").format(x));
            int y_i = Integer.parseInt(new java.text.DecimalFormat("0").format(y));
            GridMap[x_i][y_i].setQRCode(q.qrcodes.get(i));
        }

        logger.info("numofx=" + numofx);
        logger.info("numofy=" + numofy);
        for (int i = 0; i < numofx; i++) {

            for (int j = 0; j < numofy; j++) {
                //for test
		/*if(i == x && j == y){
                 close.add(GridMap[i][j]);
                 }
                 else
                 open.add(GridMap[i][j]);	*/
                if (GridMap[i][j].getblack() == false) {
                    if (i < numofx - 1 && GridMap[i + 1][j].getblack() == false) {
                        GridMap[i][j].getChild().put(GridMap[i + 1][j], 1.0);
                    }

                    if (j < numofy - 1 && GridMap[i][j + 1].getblack() == false) {
                        GridMap[i][j].getChild().put(GridMap[i][j + 1], 1.0);
                    }

                    if ((i < numofx - 1) && (j < numofy - 1) && GridMap[i + 1][j + 1].getblack() == false) {
                        GridMap[i][j].getChild().put(GridMap[i + 1][j + 1], Math.sqrt(2.0));
                    }

                    if (i > 0 && GridMap[i - 1][j].getblack() == false) {
                        GridMap[i][j].getChild().put(GridMap[i - 1][j], 1.0);
                    }

                    if (j > 0 && GridMap[i][j - 1].getblack() == false) {
                        GridMap[i][j].getChild().put(GridMap[i][j - 1], 1.0);
                    }

                    if (i > 0 && j > 0 && GridMap[i - 1][j - 1].getblack() == false) {
                        GridMap[i][j].getChild().put(GridMap[i - 1][j - 1], Math.sqrt(2.0));
                    }

                    if (i > 0 && (j < numofy - 1) && GridMap[i - 1][j + 1].getblack() == false) {
                        GridMap[i][j].getChild().put(GridMap[i - 1][j + 1], Math.sqrt(2.0));
                    }

                    if (j > 0 && (i < numofx - 1) && GridMap[i + 1][j - 1].getblack() == false) {
                        GridMap[i][j].getChild().put(GridMap[i + 1][j - 1], Math.sqrt(2.0));
                    }

                }
            }
        }
        //return GridMap;		
    }

    public double getGrid() {
        return grid;
    }

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(testArduinoBridge.class.getResourceAsStream("/config/log4j.properties"));
        SmartMap s = new SmartMap();
        /* SmartMapData d = new SmartMapData();
         d = s.getPath(new Point(1,2), new Point(4,3));
         //System.out.print(d.getAngle() + "\n");
         //System.out.print(d.start.x + "," + d.start.y + "->" + d.end.x + "," + d.end.y + "\n");
         //d = d.child;
         while(d != null) {
         //System.out.print(d.getAngle() + "\n");
         //System.out.print("->" + d.end.x + "," + d.end.y);
         System.out.print(d.getAngle() + "\n");
         System.out.print(d.start.x + "," + d.start.y + "->" + d.end.x + "," + d.end.y + "\n");
         d = d.child;
         }*/
        SmartMapInfo info = s.getMapInfo();
        int num = info.GridMap.size();
        System.out.println(num);
        for (int i = 0; i < num; i++) {
            System.out.println("x:" + info.GridMap.get(i).x + " y:" + info.GridMap.get(i).y);
            System.out.println("barrier:" + info.GridMap.get(i).getblack() + " qrcode:" + info.GridMap.get(i).getQRCode());
        }
        /*double f = 1.9f;
         int i = (int)f;
         System.out.println(i);*/
        //s.json(b,q);

    }

    @Override
    public SmartMapBarrier getBarrierInformation() {
        logger.info("get barriers information of the map");
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    @Override
    public SmartMapQRCode getQRCodeInformation() {
        logger.info("get qrcodes information of the map");
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }

    @Override
    public SmartMapBarrier.Barrier getBarrierInformation(Point p) {
        logger.info("get barriers information near the point");
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 0;
        for (; i < b.num; i++) {
            double distance = (b.barriers.get(i).p.y - p.y) * (b.barriers.get(i).p.y - p.y)
                    + (b.barriers.get(i).p.x - p.x) * (b.barriers.get(i).p.x - p.x);
            if (distance < 4) {
                return b.barriers.get(i);
            }
        }
        return null;
    }

    @Override
    public SmartMapQRCode.QRCode getQRCodeInformation(Point p) {
        logger.info("get qrcodes information near the point");
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 0;
        for (; i < q.num; i++) {
            double distance = (q.qrcodes.get(i).location.y - p.y) * (q.qrcodes.get(i).location.y - p.y)
                    + (q.qrcodes.get(i).location.x - p.x) * (q.qrcodes.get(i).location.x - p.x);
            if (distance < 4) {
                return q.qrcodes.get(i);
            }
        }
        return null;
    }

    @Override
    public SmartMapQRCode.QRCode getQRCodeInformation(String str) {
        logger.info("get qrcodes information");
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 0;
        for (; i < q.num; i++) {
            if (q.qrcodes.get(i).data.equals(s)) {
                return q.qrcodes.get(i);
            }
        }
        return null;
    }

    @Override
    public SmartMapData getPath(Point start_real, Point end_real) {
        logger.info("get the path between two points");
        try {
            build(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        double start_x = start_real.x / grid;
        double start_y = start_real.y / grid;
        double end_x = end_real.x / grid;
        double end_y = end_real.y / grid;
        Point start = new Point(start_x, start_y);
        Point end = new Point(end_x, end_y);
        String name = "(" + String.valueOf((int) end.x) + "," + String.valueOf((int) end.y) + ")";
        //System.out.println(name);
        Node ending = new Node(name);
        Dijkstra test = new Dijkstra();
        Node starting = test.init((int) start.x, (int) start.y, GridMap);
        // System.out.println(starting.getName());
        test.computePath(starting);
        ArrayList<SmartMapData> data = new ArrayList<SmartMapData>();
        data = test.printPathInfo(ending);//未合并
        for (int i = 0; i < data.size() - 1; i++) {
            /*PathNode current = new PathNode(path_nodes.get(i).x,path_nodes.get(i).y);
             PathNode next = new PathNode(path_nodes.get(i + 1).x,path_nodes.get(i + 1).y);
             if(current.x == next.x || current.y == next.y || current.x/current.y == next.x/next.y) {
                                
             }*/
            double slope1;
            double dx1 = data.get(i).start.x - data.get(i).end.x;
            if (dx1 == 0) {
                slope1 = 0;
            } else {
                slope1 = (data.get(i).start.y - data.get(i).end.y) / dx1;
            }
            double slope2;
            double dx2 = data.get(i + 1).start.x - data.get(i + 1).end.x;
            if (dx2 == 0) {
                slope2 = 0;
            } else {
                slope2 = (data.get(i + 1).start.y - data.get(i + 1).end.y) / dx2;
            }
            if (slope1 == slope2) {//斜率相等
                data.get(i).end = data.get(i + 1).end;
                data.get(i).child = data.get(i + 1).child;
                data.remove(i + 1);
                i--;
            }
        }

        SmartMapData d = data.get(0);
        return d;
    }

    @Override
    public SmartMapInfo getMapInfo() {
        logger.info("get information of the map");
        try {
            build(b, q);
        } catch (IOException ex) {
            logger.error(ex);
        }
        //System.out.println(b.num + "..." + q.num);
        SmartMapInfo info = new SmartMapInfo();
        info.setNumofx(numofx);
        info.setNumofy(numofy);
        ArrayList<NodeToDisplay> map = new ArrayList<NodeToDisplay>();
        int i;
        int j;
        for (i = 0; i < numofx; i++) {
            for (j = 0; j < numofy; j++) {
                int x = i;
                int y = j;
                NodeToDisplay node = new NodeToDisplay(x, y);
                if (GridMap[i][j].getblack() == false && GridMap[i][j].getQRCode() == false) {

                } else {
                    if (GridMap[i][j].getblack() == true) {
                        node.setblack(GridMap[i][j].getBarrierInfo());
                    }
                    if (GridMap[i][j].getQRCode() == true) {
                        node.setQRCode(GridMap[i][j].getQRCodeInfo());
                    }
                    map.add(node);
                }
            }
        }
        logger.info("barrier and qrcode num:" + map.size());
        info.setGridMap(map);
        return info;
    }

    public void json(SmartMapBarrier b, SmartMapQRCode q) throws FileNotFoundException, IOException {
        logger.info("read the json file");

        String data = ReadFile("/home/SmartCarTest/newjson.json");
//        byte[] content = new byte[1000];
//        SmartMap.class.getResourceAsStream("/config/newjson.json").read(content);
//        String data = "/tmp/newjson.json";
        logger.info("SmarpMap Data file" + data);

//        JSONObject jsonObj = JSONObject.fromString(new String(content));

        JSONObject jsonObj = JSONObject.fromString(data);
        //得到barrier对象
        JSONArray arrayB = jsonObj.getJSONArray("barriers");
        //System.out.println("barrier length:" + arrayB.length());
        for (int i = 0; i < arrayB.length(); i++) {
            //= (SmartMapBarrier.Barrier)JSONObject.toBean((JSONArray.fromObject(arrayB.toString()).getJSONObject(i)),SmartMapBarrier.Barrier.class);
            JSONObject temp = new JSONObject(arrayB.getString(i));
            //System.out.println("b.num" + b.num);
            b.setBarrier(new Point((double) temp.getDouble("centre point x"), (double) temp.getDouble("centre point y")), (double) temp.getDouble("length"), (double) temp.getDouble("width"));
            /*b.p.x = (double)temp.getDouble("centre point x");
             b.p.y = (double)temp.getDouble("centre point y");
             b.width = (double)temp.getDouble("width");
             b.length = (double)temp.getDouble("length");*/
            //System.out.println(b.num);
        }
        //得到qrcode集合
        JSONArray arrayQ = jsonObj.getJSONArray("qrcodes");

        //System.out.println("qrcode length:" + arrayQ.length());
        for (int i = 0; i < arrayQ.length(); i++) {
            //(SmartMapQRCode.QRCode)JSONObject.toBean((JSONArray.fromObject(arrayQ.toString()).getJSONObject(i)),SmartMapQRCode.QRCode.class);
            JSONObject temp = new JSONObject(arrayQ.getString(i));
            //System.out.println("q.num" + q.num);
            q.setQRCode(new Point((double) temp.getDouble("centre point x"), (double) temp.getDouble("centre point y")), temp.getString("content"));
            /*q.p.x = temp.getInt("centre point x");
             q.p.y = temp.getInt("centre point y");
             q.l = temp.getString("content");*/
        }
        logger.info("barrier:" + b.num);
        logger.info("qrcode:" + q.num);
    }

    public String ReadFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            //一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr;
    }
}
