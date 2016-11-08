package game.strategy;

import game.SugarMan;
import game.Content;
/**
 * 策略接口
 * @author zq001
 *
 */
public interface Strategy {
	
	/**
	 * 通过实现该接口来自定义糖人的行动策略
	 * @param sugarMan 当前带操作的糖人
	 * @param content 框架上下文
	 * @return
	 */
	public StrategyResult update(SugarMan sugarMan,Content content);
	
}
