package game;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
/**
 * 上下文机制
 * @author zq001
 *
 */
public class Content {
	private Table peopleMap; //人物地图，值为人数
	private Table sugarMap; //糖分地图，值为糖分
	private Table sugarMap2; //糖分地图副本
	private List<SugarMan> sugarMans;
	//配置信息
	private int moreSugar;
	private int lessSugar;
	private int noneSugar;
	
	/**
	 * 获取多糖的数值
	 * @return
	 */
	public int getMoreSugar() {
		return moreSugar;
	}


	/**
	 * 设置多糖的数值
	 * @param moreSugar
	 */
	protected void setMoreSugar(int moreSugar) {
		this.moreSugar = moreSugar;
	}

	
	/**
	 * 获取少值得糖分
	 * @return
	 */
	public int getLessSugar() {
		return lessSugar;
	}


	protected void setLessSugar(int lessSugar) {
		this.lessSugar = lessSugar;
	}

	/**
	 * 获取无糖的值
	 * @return noneSugar
	 */
	public int getNoneSugar() {
		return noneSugar;
	}


	protected void setNoneSugar(int noneSugar) {
		this.noneSugar = noneSugar;
	}

	
	/**
	 * 构造函数
	 * @param i
	 * @param j
	 * @param sugarMans
	 */
	protected Content(int i,int j, List<SugarMan>sugarMans){
		peopleMap = new Table(i, j);
		sugarMap = new Table(i, j);
		this.sugarMans = sugarMans;
	}
	
	
	/**
	 * 获取人物分布图
	 * @return
	 */
	public Table getPeopleMap(){
		return peopleMap;
	}
	
	/**
	 * 获取糖分分布图
	 * @return
	 */
	public Table getSugarMap(){
		return sugarMap;
	}

	/**
	 * 获取table的sizeX
	 * @return
	 */
	public int getSizeX(){
		return peopleMap.getX();
	}
	
	/**
	 * 获取table的sizeY
	 * @return
	 */
	public int getSizeY(){
		return peopleMap.getY();
	}
	
	/**
	 * 根据坐标获取糖人
	 * @param x
	 * @param y
	 * @return
	 */
	public SugarMan getSugarMan(int x,int y){
		for(SugarMan sm:sugarMans){
			if(sm.getX() == x && sm.getY() == y){
				return sm;
			}
		}
		return null;
	}
	
	/**
	 * 设置糖分地图
	 * @param sugarMap
	 */
	public void setSugarMap(Table sugarMap){
		this.sugarMap = sugarMap;
		this.sugarMap2 = sugarMap.copy();
	}
	/**
	 * 更新糖分
	 */
	public void updateSugarMap(){
		this.sugarMap = this.sugarMap2.copy();
	}
	
	
	/**
	 * 序列化人的分布
	 * @param filename
	 */
	public void dumpPeopleTable(String filename){
		dumpTable(peopleMap, filename);
	}
	
	
	/**
	 * 序列化糖的分布
	 * @param filename
	 */
	public void dumpSugarTable(String filename){
		dumpTable(sugarMap, filename);
	}
	
	/**
	 * 序列化Table分布,以二维写入
	 * @param filename
	 */
	protected void dumpTable(Table table,String filename){
		try{
			BufferedWriter dumpFilea  = new BufferedWriter(new FileWriter(filename));
			List<List<String>> list2D = new ArrayList<>();
			for(int i = 0;i<table.getX();i++){
				List<String> list = new ArrayList<>(); 
				for(int j = 0;j<table.getY();j++){
					int num = table.get(i, j);
					String c = String.valueOf(num);
					list.add(c);
				}
				list2D.add(list);
			}
			dumpFilea.write(list2D.toString());
			dumpFilea.flush();
			dumpFilea.close();
		}catch(IOException err){
			err.printStackTrace();
		}
	}
	
	
}
