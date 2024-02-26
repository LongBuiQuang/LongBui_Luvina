/**
 * Copyright(C) 2020 Luvina JSC
 * MstGroupEntity.java, 22/11/2020 Bui Quang Long
 */
package manageuser.entities;

/**
 * 
 * class MstGroupEntity khai báo các thuộc tính và phương thức của của đối tượng
 * ứng với bảng mst_group
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class MstGroupEntity {
	private int groupId;
	private String groupName;

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}