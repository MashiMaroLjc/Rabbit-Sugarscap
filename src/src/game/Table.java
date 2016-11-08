package game;



/**
 * 二维图
 * @author zq001
 *
 */

public class Table {

	
	private int[][] table;
	
	
	
	protected Table(int x,int y){
		table = new int[x][y];
	}
	
	
	protected void set(int x,int y,int value){
		table[x][y] = value;
	}
	
	/**
	 * 清零
	 * @param x
	 * @param y
	 */
	protected void clear(int x,int y){
		this.set(x, y, 0);
	}
	
	/**
	 * 获取返回值
	 * @param x
	 * @param y
	 * @return
	 */
	public int get(int x,int y){
		return this.table[x][y];
	}
	
	/**
	 * 获取棋盘的高
	 * @return
	 */
	public int getX(){
		return this.table.length;
	}
	
	/**
	 * 获取棋盘的宽
	 * @return
	 */
	public int getY(){
		return this.table[0].length;
	}
	
	
	protected Table copy(){
		Table copy_table = new Table(getX(), getY());
		for(int i = 0;i<getX();i++){
			for(int j=0;j<getY();j++){
				copy_table.set(i, j, get(i,j));
			}
		}
		return copy_table;
	}
	
}
