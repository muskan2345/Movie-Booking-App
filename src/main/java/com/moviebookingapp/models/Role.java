package com.moviebookingapp.models;





import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="roles")
public class Role {

	//comment
  @Id
  private String roleName;
  private String roleDesc;
public String getRoleName() {
	return roleName;
}
public void setRoleName(String roleName) {
	this.roleName = roleName;
}
public String getRoleDesc() {
	return roleDesc;
}
public void setRoleDesc(String roleDesc) {
	this.roleDesc = roleDesc;
}
public Role(String roleName, String roleDesc) {
	super();
	this.roleName = roleName;
	this.roleDesc = roleDesc;
}
public Role() {
	super();
	// TODO Auto-generated constructor stub
}
  
  
}

