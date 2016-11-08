
import game.*;

import java.util.*;

import game.Content;
import game.SugarMan;
import game.Table;
import game.strategy.*;


 class DemoStrategy implements Strategy{

	/**
	 * 实现决策接口来创建策略
	 */
	@Override
	public StrategyResult update(SugarMan sm,Content content) {
		int view = sm.getView();
		int x = sm.getX();
		int y = sm.getY();
		int nx = x;
		int ny = y;
		Table sugarTable = content.getSugarMap();
		//检测本身位置
		if(sugarTable.get(x, y) != content.getNoneSugar()){
			//System.out.println("本身位置x="+x + " y=" + y);
			return new StrategyResult(x, y, x, y);
		}
		//正上方检测
		for(int i = x;i>x-view;i--){
			if(i>=0&&sugarTable.get(i, y) !=  content.getNoneSugar()){
				//System.out.println("正上方x="+i + " y=" + y);
				return new StrategyResult(x, y, i, y);
			}
		}
		//正下方检测
		for(int i = x;i>x+view;i++){
			if(i<sugarTable.getX()&&sugarTable.get(i, y) !=  content.getNoneSugar()){
				//System.out.println("正下方x="+i + " y=" + y);
				return new StrategyResult(x, y, i, y);
			}
		}
		//正左方检测
		for(int j = y;j>y-view;j--){
			if(j>=0&&sugarTable.get(x, j) != content.getNoneSugar()){
				//System.out.println("正左方x="+x + " y=" + j);
				return new StrategyResult(x, y, x, j);
			}
		}
		//正右方检测
		for(int j = y;j>y+view;j++){
			if(j<sugarTable.getY()&&sugarTable.get(x, j) != content.getNoneSugar()){			
				//System.out.println("正右方x="+x + " y=" + j);
				return new StrategyResult(x, y, x, j);
			}
			
		}		
		//都找不到就随机
		
		Random random = new Random();
		int fuck = Math.abs(random.nextInt()%2);
		int temp = 1 + Math.abs(random.nextInt()%(view+1)); 
		if(fuck!=1){
			if(nx + temp >= sugarTable.getX()){
				nx -= temp;
			}else{
				nx += temp;
			}
		}else{
			if(ny + temp >= sugarTable.getY()){
				ny -= temp;
			}else{
				ny+=temp;
			}
		}
		return new StrategyResult(x, y, nx, ny);
	}

}



public class Sugarscape {
//
	public static void main(String[] args) {

		Game game = new Game(50,50,250,200);
		
		String info  = String.format("游戏规格: \n地图: %d * %d\n玩家:%d \n回合：%d\n",
				game.getI(),game.getJ(),game.getPlayerNum(),game.getMaxIter());
		System.out.println(info);
		game.setStrategy(new DemoStrategy());
		game.setFrequent(2);
		try {
			game.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("游戏完成");
		
	}
	
}
