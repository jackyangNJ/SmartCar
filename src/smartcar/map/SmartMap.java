package smartcar.map;

/**
 *
 * @author cgirls
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import smartcar.map.SmartMapData;
import smartcar.map.SmartMapInfo;
import smartcar.map.SmartMapQRCode;
import smartcar.map.SmartMapBarrier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
//import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import smartcar.SmartMapInterface;
import smartcar.core.Point;

//Node对象用于封装节点信息，包括名字和子节点
class Node { 
    private String name; 
    private Map<Node,Double> child=new HashMap<Node,Double>(); 
    private boolean barriermask = false;
    SmartMapBarrier.Barrier b = new SmartMapBarrier.Barrier();
    private boolean qrcodemask = false;
    SmartMapQRCode.QRCode q = new SmartMapQRCode.QRCode();
    public Node(String name){ 
        this.name=name; 
    } 
    public String getName() { 
        return name; 
    } 
    public void setName(String name) { 
        this.name = name; 
    } 
    public Map<Node, Double> getChild() { 
        return child; 
    } 
    public void setChild(Map<Node, Double> child) { 
        this.child = child; 
    } 
    public void setblack(SmartMapBarrier.Barrier b){//设置为障碍物点
    	barriermask = true;
        this.b = b;
    }
    public boolean getblack(){
    	return barriermask;
    }
    public void setQRCode(SmartMapQRCode.QRCode q){//设置二维码
    	qrcodemask = true;
        this.q = q;
    }
    public boolean getQRCode(){
    	return qrcodemask;
    }
    public SmartMapBarrier.Barrier getBarrierInfo() {
        return b;
    }
    public SmartMapQRCode.QRCode getQRCodeInfo() {
        return q;
    }
} 


//Dijkstra对象用于计算起始节点到所有其他节点的最短路径

class Dijkstra { 
	
    Set<Node> open=new HashSet<Node>(); 
    Set<Node> close=new HashSet<Node>(); 
    Map<String,Double> path=new HashMap<String,Double>();//封装路径距离 
    Map<String,String> pathInfo=new HashMap<String,String>();//封装路径信息 
 
    public Node init(int x, int y,Node map[][]){ 
    	 Node[][] GridMap = map;
    	 /*
    		for(int i = 0; i < numofx; i ++){	
    			for(int j=0; j< numofy; j++){
    					
    				GridMap[i][j]=new Node("m" + i + j); //give name
    					
    			}
    		}*/
        //初始路径,因没有A->E这条路径,所以path(E)设置为Double.MAX_VALUE 
        path.put("m10", 1.0); 
       // path.put("m10", 1.0);
        pathInfo.put("m10", "m00->m10"); 
        //pathInfo.put("m10", "m00->m10");
        path.put("m01", 1.0);
       // path.put("m020", 1.0);
        pathInfo.put("m01", "m00->m01"); 
       // pathInfo.put("m020", "m00->m020");
        path.put("m11", Math.sqrt(2.0)); 
       // path.put("m021", Math.sqrt(2.0));
        pathInfo.put("m11", "m00->m11");
      //  pathInfo.put("m021", "m00->m021"); 
        for(int i = 0; i < GridMap.length; i ++){
			
			for(int j = 0; j < GridMap[0].length; j ++){
				//for test
				if(i == x && j == y){
					close.add(GridMap[i][j]);
				}
				else
					open.add(GridMap[i][j]);	
				
				if(GridMap[i][j].getblack()==false){//不能为障碍物
					if(i == x + 1 && j == y){
							path.put("m" + i + j, 1.0); 
					        pathInfo.put("m" + i + j, "m" + x + y + "->" + "m" + i + j); 
					}
					else if(i == x && j == y + 1){
						path.put("m" + i + j, 1.0); 
				        pathInfo.put("m" + i + j, "m" + x + y + " ->" + "m" + i + j); 
					}
					else if(i == x + 1 && j == y + 1){
						path.put("m" + i + j, 1.0); 
				        pathInfo.put("m" + i + j, "m" + x + y + " ->" + "m" + i + j); 
					}
				
					else if(i == x - 1 && j == y){
						path.put("m" + i + j, 1.0); 
				        pathInfo.put("m" + i + j, "m" + x + y + " ->" + "m" + i + j); 
					}
				
					else if(i == x && j == y - 1){
						path.put("m" + i + j, 1.0); 
				        pathInfo.put("m" + i + j, "m" + x + y + " ->" + "m" + i + j); 
					}
				
					else if(i == x - 1 && j == y - 1){
						path.put("m" + i + j, 1.0); 
				        pathInfo.put("m" + i + j, "m" + x + y + " ->" + "m" + i + j); 
					}
				
					else if(i == x - 1 && j == y + 1){
						path.put("m" + i + j, 1.0); 
				        pathInfo.put("m" + i + j, "m" + x + y + " ->" + "m" + i + j); 
					}
				
					else if(i == x + 1 && j == y -1){
						path.put("m" + i + j, 1.0); 
				        pathInfo.put("m" + i + j, "m" + x + y + " ->" + "m" + i + j); 
					}
					else{
						path.put("m" + i + j, Double.MAX_VALUE); 
		        	    pathInfo.put("m" + i + j, "m00"); 
					}
				}
			}
			
		}
      
        return GridMap[x][y];
        
   
        
    }
    
    
    public void computePath(Node start){ 
        Node nearest=getShortestPath(start);//取距离start节点最近的子节点,放入close 
        if(nearest==null){ 
            return; 
        } 
        close.add(nearest); 
        open.remove(nearest); 
        Map<Node,Double> childs=nearest.getChild(); 
        for(Node child:childs.keySet()){ 
            if(open.contains(child)){//如果子节点在open中 
                Double newCompute=path.get(nearest.getName())+childs.get(child); 
         //       System.out.println("111:" + child.getName());
           //     System.out.println("222:" + newCompute);
                if(path.get(child.getName())>newCompute){//之前设置的距离大于新计算出来的距离 
                    path.put(child.getName(), newCompute); 
                    pathInfo.put(child.getName(), pathInfo.get(nearest.getName()) + "->" + child.getName()); 
                } 
            } 
        } 
        computePath(start);//重复执行自己,确保所有子节点被遍历 
        computePath(nearest);//向外一层层递归,直至所有顶点被遍历 
    } 
    //输出所有最短路径
  
    public ArrayList printPathInfo(Node ending){ 
        Set<Map.Entry<String, String>> pathInfos = pathInfo.entrySet();
        ArrayList<Point> path_nodes = new ArrayList<Point>();
        ArrayList<SmartMapData> data = new ArrayList<SmartMapData>();//记录计算出的初步路径，未合并
        for(Map.Entry<String, String> pathInfo:pathInfos){ 
        	//System.out.println(ending.getName()+"name");
        	//System.out.println(pathInfo.getKey()+"key");
        	if(ending.getName().equals(pathInfo.getKey())){
        		System.out.println(pathInfo.getKey() + ":" + pathInfo.getValue()); 
                        System.out.println("~~~~\n");
                        String[] path = pathInfo.getValue().split("->");//将各个结点放入数组
                        for(int i = 0;i < path.length;i++) {
                            int x = Integer.parseInt(String.valueOf(path[i].charAt(1)));
                            int y = Integer.parseInt(String.valueOf(path[i].charAt(2)));
                            Point p = new Point(x,y);
                            path_nodes.add(p);
                        }
                        SmartMapData data_part1 = new SmartMapData();
                        data_part1.start = path_nodes.get(0);
                        data_part1.end = path_nodes.get(1);
                        data_part1.child = null;
                        data.add(data_part1);
                        for(int i = 1;i < path_nodes.size() - 1;i++) {
                            
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
    private Node getShortestPath(Node node){ 
        Node res=null; 
        double minDis=Double.MAX_VALUE; 
        Map<Node,Double> childs=node.getChild(); 
        for(Node child:childs.keySet()){ 
            if(open.contains(child)){ 
                double distance=childs.get(child); 
                if(distance<minDis){ 
                    minDis=distance; 
                    res=child; 
                } 
            } 
        } 
        return res; 
    } 
} 

//Main用于测试Dijkstra对象
public class SmartMap implements SmartMapInterface { 
    final double width = 10;
    final double length = 10;
    final double grid = 2;
    //int numofgrid = (int)(width * length / (grid * grid));
    int numofy=(int)(width/grid);
    int numofx=(int)(length/grid);
    Node[][] GridMap = new Node[numofx][numofy];
    SmartMapBarrier b = new SmartMapBarrier();
    SmartMapQRCode q = new SmartMapQRCode();
    
    public void build(SmartMapBarrier b,SmartMapQRCode q) throws IOException{ 
        json(b,q);
        for(int i = 0; i < numofx; i ++){	
            for(int j=0; j< numofy; j++){			
                GridMap[i][j]=new Node("m" + i + j); //give name
				
            }
        }
	//set barrier
        for(int i =0;i < b.num;i++) {
            float x = b.barriers.get(i).p.x;
            float y = b.barriers.get(i).p.y;
            int x_i = Integer.parseInt(new java.text.DecimalFormat("0").format(x));
            int y_i = Integer.parseInt(new java.text.DecimalFormat("0").format(y));
            GridMap[x_i][y_i].setblack(b.barriers.get(i));
            float length = (b.barriers.get(i).length - 2) / 2;//中心点一侧的长度
            if(length != 0) {
                int num_l = (int)length / 2 + 1;//中心点一侧的网格个数（除中心网格外）（左右）
                for(int j = 1;j <= num_l;j++) {
                    GridMap[x_i][y_i - num_l].setblack(b.barriers.get(i));
                    GridMap[x_i][y_i + num_l].setblack(b.barriers.get(i));
                }
            }
            float width = (b.barriers.get(i).width - 2) / 2;//中心点一侧的宽度
            if(width != 0) {
                int num_w = (int)width / 2 + 1;//中心点一侧的网格个数（除中心网格外）（上下）
                for(int j = 1;j <= num_w;j++) {
                    GridMap[x_i - num_w][y_i].setblack(b.barriers.get(i));
                    GridMap[x_i + num_w][y_i].setblack(b.barriers.get(i));
                }
            }
        }
        
	
        //set qrcode
        for(int i =0;i < q.num;i++) {
            float x = q.qrcodes.get(i).p.x;
            float y = q.qrcodes.get(i).p.y;
            int x_i = Integer.parseInt(new java.text.DecimalFormat("0").format(x));
            int y_i = Integer.parseInt(new java.text.DecimalFormat("0").format(y));
            GridMap[x_i][y_i].setQRCode(q.qrcodes.get(i));
        }
        
        
	for(int i = 0; i < numofx; i ++){
			
            for(int j = 0; j < numofy; j ++){
                //for test
		/*if(i == x && j == y){
                    close.add(GridMap[i][j]);
		}
		else
                    open.add(GridMap[i][j]);	*/
		if(GridMap[i][j].getblack()==false){
                    if(i < numofx-1 && GridMap[i+1][j].getblack() == false)
                        GridMap[i][j].getChild().put(GridMap[i+1][j], 1.0); 
				
                    if(j < numofy - 1 && GridMap[i][j + 1].getblack() == false)
			GridMap[i][j].getChild().put(GridMap[i ][j + 1], 1.0); 
				
                    if((i < numofx - 1) && (j < numofy - 1) && GridMap[i+1][j+1].getblack() == false)
			GridMap[i][j].getChild().put(GridMap[i + 1][j + 1], Math.sqrt(2.0)); 
				
                    if(i > 0 && GridMap[i-1][j].getblack() == false)
			GridMap[i][j].getChild().put(GridMap[i - 1][j], 1.0); 
				
                    if(j > 0 && GridMap[i][j - 1].getblack() == false)
			GridMap[i][j].getChild().put(GridMap[i ][j - 1], 1.0); 
				
                    if(i >0 && j > 0 && GridMap[i-1][j-1].getblack() == false)
                    	GridMap[i][j].getChild().put(GridMap[i - 1][j - 1],Math.sqrt(2.0) ); 
				
                    if(i > 0 && (j < numofy-1) && GridMap[i-1][j+1].getblack() == false)
			GridMap[i][j].getChild().put(GridMap[i - 1][j+1],Math.sqrt(2.0) ); 
				
                    if(j>0 && (i < numofx-1) && GridMap[i+1][j-1].getblack() == false)
			GridMap[i][j].getChild().put(GridMap[i + 1 ][j-1], Math.sqrt(2.0)); 
				
		}
            }		
        }		
	//return GridMap;		
    }

    
    public static void main(String[] args) throws IOException { 
    	 
        SmartMap s = new SmartMap();
        SmartMapData d = new SmartMapData();
        d = s.getPath(new Point(1,2), new Point(4,3));
        System.out.print((int)d.start.x);
        System.out.print((int)d.start.y + "->" + (int)d.end.x);
        System.out.print((int)d.end.y);
        d = d.child;
        while(d != null) {
            System.out.print("->" + (int)d.end.x);
            System.out.print((int)d.end.y);
            d = d.child;
        }
        /*float f = 1.9f;
        int i = (int)f;
        System.out.println(i);*/
        //s.json(b,q);
            
    } 

    @Override
    public SmartMapBarrier getBarrierInformation() {
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
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }

    @Override
    public SmartMapBarrier getBarrierInformation(Point p) {
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    @Override
    public SmartMapQRCode getQRCodeInformation(Point p) {
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }

    @Override
    public SmartMapQRCode getQRCodeInformation(String str) {
        SmartMap s = new SmartMap();
        try {
            s.json(b, q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }
    
    @Override
    public SmartMapData getPath(Point start, Point end) {
        try {
            build(b,q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        String name = "m" + String.valueOf((int)end.x) + String.valueOf((int)end.y);
        Node ending = new Node(name);
        Dijkstra test = new Dijkstra(); 
        Node starting = test.init((int)start.x,(int)start.y,GridMap); 
        test.computePath(starting); 
        ArrayList<SmartMapData> data = new ArrayList<SmartMapData>();
        data = test.printPathInfo(ending);//未合并
        for(int i = 0;i < data.size() - 1;i++) {
            /*PathNode current = new PathNode(path_nodes.get(i).x,path_nodes.get(i).y);
                            PathNode next = new PathNode(path_nodes.get(i + 1).x,path_nodes.get(i + 1).y);
                            if(current.x == next.x || current.y == next.y || current.x/current.y == next.x/next.y) {
                                
                            }*/
            float slope1;
            float dx1 = data.get(i).start.x - data.get(i).end.x;
            if(dx1 == 0)
                slope1 = 0;
            else
                slope1 = (data.get(i).start.y - data.get(i).end.y) / dx1;
            float slope2;
            float dx2 = data.get(i + 1).start.x - data.get(i + 1).end.x;
            if(dx2 == 0)
                slope2 = 0;
            else
                slope2 = (data.get(i + 1).start.y - data.get(i + 1).end.y) / dx2;
            if(slope1 == slope2) {//斜率相等
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
    public SmartMapInfo getMap() {
        try {
            build(b,q);
        } catch (IOException ex) {
            Logger.getLogger(SmartMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        SmartMapInfo info = new SmartMapInfo();
        info.setNumofx(numofx);
        info.setNumofy(numofy);
        info.setGridMap(GridMap);
        return info;
    }
    
    public void json(SmartMapBarrier b,SmartMapQRCode q) throws FileNotFoundException, IOException {

        String data = ReadFile("D:\\2013\\s\\SmartCar\\src\\config\\newjson.json");
        System.out.println(data);
        JSONObject jsonObj = JSONObject.fromString(data);
        //得到barrier对象
        JSONArray arrayB = jsonObj.getJSONArray("barriers");
        
        System.out.println("barrier length:" + arrayB.length());
        for(int i = 0;i < arrayB.length();i++){
            //= (SmartMapBarrier.Barrier)JSONObject.toBean((JSONArray.fromObject(arrayB.toString()).getJSONObject(i)),SmartMapBarrier.Barrier.class);
            JSONObject temp = new JSONObject(arrayB.getString(i));
            b.setBarrier(new Point((float)temp.getDouble("centre point x"),(float)temp.getDouble("centre point y")),(float)temp.getDouble("length"), (float)temp.getDouble("width"));
            /*b.p.x = (float)temp.getDouble("centre point x");
            b.p.y = (float)temp.getDouble("centre point y");
            b.width = (float)temp.getDouble("width");
            b.length = (float)temp.getDouble("length");*/
            //System.out.println(b.num);
        }
        //得到qrcode集合
        JSONArray arrayQ = jsonObj.getJSONArray("qrcodes");
        
        System.out.println("qrcode length:" + arrayQ.length());
        for(int i = 0;i < arrayQ.length();i++){
            //(SmartMapQRCode.QRCode)JSONObject.toBean((JSONArray.fromObject(arrayQ.toString()).getJSONObject(i)),SmartMapQRCode.QRCode.class);
            JSONObject temp = new JSONObject(arrayQ.getString(i)); 
            q.setQRCode(new Point((float)temp.getDouble("centre point x"),(float)temp.getDouble("centre point y")),temp.getString("content"));
            /*q.p.x = temp.getInt("centre point x");
            q.p.y = temp.getInt("centre point y");
            q.l = temp.getString("content");*/
        }
    }
    public String ReadFile(String path){
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
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } 
                catch (IOException e1) {
                }
            }
        }
        return laststr;
   }
} 

