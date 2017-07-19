package store.domain;
/**
 * 购物项 实体
 * @author yang
 *
 */
@SuppressWarnings("unused")
public class CartItemBean {
	private ProductBean productBean; //商品对象
	private Integer itemCount; //  商品数量
	private double subtotal; // 金额小计
	
	public ProductBean getProductBean() {
		return productBean;
	}
	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	
	// 小计金额：数量*商城单价
	public double getSubtotal() {
		return itemCount*productBean.getShop_price();
	}
	
	// 金额小计不应提供人为设置的方法：有商品数量和金额计算得到
	/*public void setsubtotal(double subtotal) {
		this.subtotal = subtotal;
	}*/
	
	
}
