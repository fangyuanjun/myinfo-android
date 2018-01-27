package fyj.lib.android.control;

/***
 * 自定义接口
 */
public interface IRefreshLoadingMoreListener {
	/***
	 * // 下拉刷新执行
	 */
	void onRefresh();

	/***
	 * 点击加载更多
	 */
	void onLoadMore();
	
	/***
	 * 初始化刷新时间
	 */
	String getInitRefreshDatetime();
	
}