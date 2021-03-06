package discord.modules.command;

import java.util.List;

import discord.modules.config.Configuration.ConfigValue;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

public enum PermissionLevel {

	USER(1, "@everyone"),
	MODERATOR(2, "Moderator"),
	ADMINISTRATOR(3, "Admin"),
	DEVELOPER(4, "Developer"),
	SLY(5, "");
	
	private int val;
	private String role;
	
	PermissionLevel(int val, String role){
		this.val = val;
		this.role = role;
	}
	public int getIntValue(){
		return val;
	}
	public String getRole(){
		if(val == 2){
			return ConfigValue.MODERATOR_ROLE.getValue();
		}else if(val == 3){
			return ConfigValue.ADMIN_ROLE.getValue();
		}
		return role;
	}
	public static PermissionLevel getFromString(String role){
		for(PermissionLevel pl : values()){
			if(role.equalsIgnoreCase(pl.getRole())){
				return pl;
			}
		}
		return USER;
	}
	public static boolean hasPermissionLevel(IGuild guild, IUser user, PermissionLevel roleLevel){
		List<IRole> userRoles = user.getRolesForGuild(guild);
		for(IRole r : userRoles){
			PermissionLevel userLevel = getFromString(r.getName());
			if(userLevel.getIntValue() >= roleLevel.getIntValue()){
				return true;
			}
		}
		return false;
	}
	public static boolean hasRole(IGuild guild, IUser user, String role){
		List<IRole> userRoles = user.getRolesForGuild(guild);
		for(IRole r : userRoles){
			if(r.getName().equalsIgnoreCase(role)){
				return true;
			}
		}
		return false;
	}
	public static boolean isUser(IUser u, String snowflake){
		return u.getID().equalsIgnoreCase(snowflake);
	}
}
