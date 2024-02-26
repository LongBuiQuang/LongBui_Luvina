/**
 * Copyright(C) 2020 Luvina JSC
 * MstJapanEntity.java, 7/12/2020 Bui Quang Long
 */
package manageuser.entities;

/**
 * class MstJapanEntity khai báo các thuộc tính và phương thức của đối tượng ứng
 * với bảng mst_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class MstJapanEntity {
	private String codeLevel;
	private String nameLevel;

	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}

	/**
	 * @param codeLevel the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}

	/**
	 * @param nameLevel the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}

}
