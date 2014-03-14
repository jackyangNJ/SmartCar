package smartcar;

/**
 *
 * @author jack
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//Node对象用于封装节点信息，包括名字和子节点

class Node { 
    private String name; 
    private Map<Node,Double> child=new HashMap<Node,Double>(); 
    private boolean barriermask = false;
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
    public void setblack(){//设置为障碍物点
    	barriermask = true;
    }
    public boolean getblack(){
    	return barriermask;
    }
} 


class MapBuilder { 
	final double width = 10;
	final double length = 10;
	final double grid = 2;
	//int numofgrid = (int)(width * length / (grid * grid));
	int numofy=(int)(width/grid);
	int numofx=(int)(length/grid);
	Node[][] GridMap = new Node[numofx][numofy];
	public Node build(Set<Node> open, Set<Node> close,int x, int y){ 
		for(int i = 0; i < numofx; i ++){	
			for(int j=0; j< numofy; j++){
				
				GridMap[i][j]=new Node("m" + i + j); //give name
				
			}
		}
		
		//set barriet
		GridMap[2][2].setblack();
		GridMap[2][3].setblack();
		
		for(int i = 0; i < numofx; i ++){
			
			for(int j = 0; j < numofy; j ++){
				//for test
				if(i == x && j == y){
					close.add(GridMap[i][j]);
				}
				else
					open.add(GridMap[i][j]);	
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
		
		return GridMap[x][y];
		
	}
}



//Dijkstra对象用于计算起始节点到所有其他节点的最短路径

class Dijkstra { 
	
	final double width = 10;
	final double length = 10;
	final double grid = 2;
	int numofy=(int)(width/grid);
	int numofx=(int)(length/grid);
	
    Set<Node> open=new HashSet<Node>(); 
    Set<Node> close=new HashSet<Node>(); 
    Map<String,Double> path=new HashMap<String,Double>();//封装路径距离 
    Map<String,String> pathInfo=new HashMap<String,String>();//封装路径信息 
 
    public Node init(int x, int y){ 
    	 Node[][] GridMap = new Node[numofx][numofy];
    	 
    		for(int i = 0; i < numofx; i ++){	
    			for(int j=0; j< numofy; j++){
    					
    				GridMap[i][j]=new Node("m" + i + j); //give name
    					
    			}
    		}
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
        for(int i = 0; i < numofx; i ++){
			
			for(int j = 0; j < numofy; j ++){
				//for test
				if(i == x && j == y){
					close.add(GridMap[i][j]);
				}
				else
					open.add(GridMap[i][j]);	
				
				if(GridMap[i][j].getblack()==false){//不能为障碍物
					if(i == x + 1 && j == y){
							path.put("m" + i + j, 1.0); 
					        pathInfo.put("m" + i + j, "m" + x + y + " ->" + "m" + i + j); 
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
       
        Node start=new MapBuilder().build(open,close,x,y); 
        return start;
        
   
        
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
                    pathInfo.put(child.getName(), pathInfo.get(nearest.getName())+"->"+child.getName()); 
                } 
            } 
        } 
        computePath(start);//重复执行自己,确保所有子节点被遍历 
        computePath(nearest);//向外一层层递归,直至所有顶点被遍历 
    } 
    //输出所有最短路径
  

    public void printPathInfo(Node ending){ 
        Set<Map.Entry<String, String>> pathInfos=pathInfo.entrySet(); 
        for(Map.Entry<String, String> pathInfo:pathInfos){ 
        	//System.out.println(ending.getName()+"name");
        	//System.out.println(pathInfo.getKey()+"key");
        	if(ending.getName().equals(pathInfo.getKey())){
        		//System.out.println("fine");
        		System.out.println(pathInfo.getKey()+":"+pathInfo.getValue()); 
        		System.out.println("~~~~\n");
        	}
        } 
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
public class SmartMap  { 
    public static void main(String[] args) { 
    	Node ending = new Node("m44");
        Dijkstra test=new Dijkstra(); 
        Node start=test.init(1,1); 
        test.computePath(start); 
        test.printPathInfo(ending); 
    } 
} 

