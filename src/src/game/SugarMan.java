package game;

import java.util.Random;

/**
 * 糖人类
 * @author zq001
 *
 */
public class SugarMan {

//	public static final int SUGARFALG = -1; //糖人标记
	private int id; //糖人id
	
	private boolean live;
	
	private int view; //糖人视野
	private int sugar; //当前糖分
	private int metabolic; //代谢
	
	private int x;
	private int y;
	
	/**
	 * 获取生命状态
	 * @return
	 */
	
	public boolean isLive(){
		return live;
	}
	
	/**
	 * 获取糖人id
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * 获取当期的X
	 * @return
	 */
	public int getX(){
		return x;
	}
	
	
	/**
	 * 获取当期的Y
	 * @return
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 糖人移动
	 * @param x
	 * @param y
	 */
	public void move(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 框架在更新完后执行该函数
	 */
	public void Ok(){	
		sugar -= metabolic;
		if(sugar<=0){
			this.kill();
		}
	}
	
	/**
	 * 获取当前糖分
	 * @return
	 */
	public int getSugarValue(){
		return sugar;
	}
	
	
	/**
	 * 更新当前糖分
	 * 
	 */
	public void addSugarValue(int sugar){
		this.sugar += sugar;
	}
	
	
	/**
	 * 获取视野范围
	 */
	public int getView(){
		return this.view;
	}
	
	
	/**
	 * 获取其代谢能力
	 * @return
	 */
	public int getMetabolic(){
		return metabolic;
	}
	
	
	/**
	 *糖人死亡
	 */
	public void kill(){
		this.live = false;
	}
	
	/**
	 * 糖人的构造方法
	 * @param id 糖人的id
	 * @param tableI 糖人活动的棋盘的高
	 * @param tableJ 糖人活动的棋盘的宽
	 */
	public SugarMan(int id,int tableI,int tableJ){
		Random random = new Random();
		this.id = id;
		this.live = true;
		this.x = Math.abs(random.nextInt()%tableI);
		this.y =  Math.abs(random.nextInt()%tableJ);
		view = (int)((tableI+tableJ)/(2*10)) +  Math.abs(random.nextInt()%5);
		sugar = 3+Math.abs(random.nextInt()%8);
		metabolic = 1+Math.abs(random.nextInt()%5);
		
	}
	
	
}
