package com.xuanyimao.polj.modeldeal.bean;
/**
 * 特殊词实体类
 * @author liuming
 *
 */
public class SpecialWord {
	/**ID**/
	private Integer id;
	/**特殊词*/
	private String word;
	/**替换的内容*/
	private String content;
	/**备注*/
	private String desc;
	/**
	 * @return 特殊词
	 */
	public String getWord() {
		return word;
	}
	/**
	 * 设置 特殊词
	 * @param word 特殊词
	 */
	public void setWord(String word) {
		this.word = word;
	}
	/**
	 * @return 替换的内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 替换的内容
	 * @param content 替换的内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return 备注
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置 备注
	 * @param desc 备注
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * id
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置 id
	 * @param id id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SpecialWord [id=" + id + ", word=" + word + ", content=" + content + ", desc=" + desc + "]";
	}
}
