package game.strategy;



/**
 * 执行策略进行任务update的结果
 * @author zq001
 *
 */
public class StrategyResult {
	
	private int lastX;
	private int lastY;
	private int x;
	private int y;
	
	/**
	 * 构造方法
	 * @param lastX 上一个x
	 * @param lastY 上一个y
	 * @param x 在完成行动后更新的x
	 * @param y 在完成行动后更新的y
	 */
	public StrategyResult(int lastX,int lastY,int x,int y){
		this.lastX = lastX;
		this.lastY = lastY;
		this.x = x;
		this.y = y;
	}

	/**
	 * 返回当前的X
	 * @return
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * 返回当前的Y
	 * @return
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 返回前一步的X
	 * @return
	 */
	
	public int getLastX(){
		return lastX;
	}
	
	/**
	 * 返回前一步的Y
	 * @return
	 */
	public int getLastY(){
		return lastY;
	}
}
