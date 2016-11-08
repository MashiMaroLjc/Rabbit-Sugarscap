package game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import game.strategy.Strategy;
import game.strategy.StrategyResult;

/**
 * 游戏主体框架
 * @author zq001
 *
 */
public class Game {

	//棋盘规模
	private int tableI; 
	private int tableJ;
	//玩家数量大小
	private int playerNum;
	// 糖人们
	private List<SugarMan> sugarMans;
	//最大迭代数
	private int maxIter;
	// 策略类
	private Strategy strategy; 
	// 日志文件
	private BufferedWriter  logFile; 
	 //糖更新的频率
	private int frequent;
	//多糖数,默认9
	private int moreSugar; 
	//少糖数，默认4
	private int lessSugar; 
	//产生随机数的工具类
	private Random randomUtil = new Random();
	//框架上下文
	private Content content;
	
	/**
	 * 构造类
	 * @param i 棋盘的高度
	 * @param j 棋盘的宽度
	 * @param playerNum 糖人数量
	 * @param maxIter 最大回合数
	 */
	public Game(int i,int j,int playerNum,int maxIter){
		tableI = i;
		tableJ = j;
		moreSugar =9;
		lessSugar = 4;
		this.frequent = 3;
		this.playerNum = playerNum;
		this.maxIter = maxIter;
		//构建上下文
		content = new Content(tableI, tableJ,sugarMans);
		Table peopleTable = content.getPeopleMap();
		sugarMans = new ArrayList<SugarMan>();
		for(int i_=0;i_<playerNum;i_++){
			SugarMan sm = new SugarMan(i_+1,tableI,tableJ);
			int x = sm.getX();
			int y = sm.getY();
			peopleTable.set(x,y, peopleTable.get(x, y)+1);
			sugarMans.add(sm);
		}
	}
	
	/**
	 * 设置糖分更新回合
	 * @param f
	 */
	public void setFrequent(int f){
		this.frequent = f;
	}
	
	/**
	 * 设置多糖的数目
	 * @param more
	 */
	public void setMoreSugar(int more){
		this.moreSugar = more;
	}
	
	/**
	 * 设置少糖的数目
	 * @param less
	 */
	public void setLessSugar(int less){
		this.lessSugar = less;
	}
	
	/**
	 * 获取棋盘的高
	 * @return
	 */
	public int getI(){
		return tableI;
	}
	
	/**
	 * 获取棋盘的宽
	 * @return
	 */
	public int getJ(){
		return tableJ;
	}
	
	/**
	 * 获取糖人数目
	 * @return
	 */
	public int getPlayerNum(){
		return playerNum;
	}
	
	/**
	 * 获取最大回合
	 * @return
	 */
	public int getMaxIter(){
		return maxIter;
	}
	
	/**
	 * 设置策略
	 * @param strategy
	 */
	public void setStrategy(Strategy strategy){
		this.strategy = strategy;
	}
	
	/**
	 * 规定在max内的随机正数
	 * @param max
	 * @return
	 */
	private int randomLimit(int max){
		return Math.abs(randomUtil.nextInt()%max);
	}
	
	private void fuckTryCatch(Table table,int i,int j,int value){
		try{
			table.set(i, j, value);
		}catch(ArrayIndexOutOfBoundsException err){
			
		}
		
	}
	
	/**
	 * 根据随机种子来生成资源
	 * @param seedX
	 * @param seedY
	 */
	private void createSugar(Table sugarTable,int seedX,int seedY){
		int step = 8;
		for(int i = seedX;i<=seedX+step;i++){
			for(int j =seedY;j<=seedY+step;j++){
				int value = moreSugar;
				if((j <= seedY+1||j >= seedY+step-1)
						||(i <= seedX+1||i >= seedX+step-1)
					){
					value = lessSugar;
				}
				fuckTryCatch(sugarTable, i, j, value);
			}
		}
	}
	
	/**
	 * 初始化资源分布
	 */
	private void initSugar(Content content){
		Table sugarMap = new Table(tableI, tableJ);
		createSugar(sugarMap,randomLimit(tableI),randomLimit(tableJ));
		createSugar(sugarMap,randomLimit(tableI),randomLimit(tableJ));
		//打印一下糖山
		content.setSugarMap(sugarMap);
		
	}
	
	/**
	 * 开始游戏
	 * @throws NullPointerException 在未设定糖人策略的时候，抛出该异常
	 */
	public void start() throws Exception{
		if(strategy == null){
			throw new NullPointerException("策略设定为空，游戏异常退出");
		}
		try{
			logFile = new BufferedWriter(new FileWriter("logs.txt"));
		}catch(IOException e){
			System.err.println("日志文件初始化失败： "+ e.getMessage());
		}
		initSugar(content);
		content.setLessSugar(lessSugar);
		content.setMoreSugar(moreSugar);
		content.setNoneSugar(0);
		//开始游戏
		int iter = 1;
		content.dumpPeopleTable("begin.txt");
		content.dumpSugarTable("begin_sugar.txt");
		
		Table peopleMap = content.getPeopleMap();
		while(iter<= maxIter && sugarMans.size()!=0){
			Table sugarMap = content.getSugarMap();
			String toLog = "=====================================\r\n";
			Collections.shuffle(sugarMans); //打乱糖人的顺序
//			
//			for(int i=0;i<sugarMap.getX();i++){
//				for(int j = 0;j<sugarMap.getY();j++){
//					System.out.print(sugarMap.get(i,j) + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();
			for(int i = 0;i<sugarMans.size();i++){
				
				SugarMan sm = sugarMans.get(i);
				
				if(sm.isLive()){
					//每个糖人都执行一次策略
					StrategyResult sr =  strategy.update(sm,content);
					
					int x = sr.getX();
					int y = sr.getY();
					int lx = sr.getLastX();
					int ly = sr.getLastY();
					int getedSugar = sugarMap.get(x, y);
					sm.addSugarValue(getedSugar);
					sm.Ok();
					int sugar = sm.getSugarValue();
					int metabolic = sm.getMetabolic();
					//资源拿走资源，没有资源该操作也可以执行，因为本来就是0
					sugarMap.clear(x, y);
					//人物移动，-1
					peopleMap.set(lx, ly, peopleMap.get(lx, ly) - 1);
					//叠加上去
					peopleMap.set(x, y, peopleMap.get(x, y) + 1);
					sugarMans.get(i).move(x, y);
					String moveInfo = String.format("\tfrom:%d,%d\tTo:%d,%d\tGET:%d\tView:%d\tSugar:%d.\tpay_for:%d .\r\n",
							lx,ly,x,y,getedSugar,sm.getView(),sugar,metabolic);
					toLog += "回合["+iter+"/" + maxIter+"]\t" +  sm.getId() + moveInfo;
				}else{
					sugarMans.remove(i);
					int x = sm.getX();
					int y = sm.getY();
					peopleMap.set(x, y, peopleMap.get(x, y) - 1);
					toLog +="回合["+iter+"/" + maxIter+"]\t" +  sm.getId() + "\tdie!\r\n";
				}
				
			}
			//记录每个糖人的状态
			try {
				logFile.write(toLog);
				if(iter % this.frequent == 0){
					content.updateSugarMap();
					logFile.write("回合["+iter+"/" +maxIter+"]\tupdate sugar\r\n");
				}
				logFile.write("回合["+iter+"/" +maxIter+"]\tLest:"+sugarMans.size()+"\r\n");
				logFile.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			iter ++;
		}
		content.dumpSugarTable("end_sugar.txt");
		content.dumpPeopleTable("end.txt");

	}
}
