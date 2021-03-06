package discord.modules.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import discord.Main;
import sx.blah.discord.handle.obj.IUser;

public enum UserValue {

	SNOWFLAKE("123456789123456789", String.class),
	NAME("Al", String.class),
	DISCRIMINATOR("0000", String.class), 
	LINKED_ACCOUNT("", String.class),
	LINKED_PLATFORM("steam", String.class),
	REGION("US-EAST", String.class),
	RANK("Unranked", String.class);

	Object initialValue;
	Class<?> type;

	UserValue(Object initialValue, Class<?> type) {
		this.initialValue = initialValue;
		this.type = type;
	}

	public DatabaseObject getFor(IUser u) {
		return new DatabaseObject(Database.getCacheFor(u).get(name()));
	}

	public void setFor(IUser u, Object o) {
		Database.getCacheFor(u).put(name(), o);
		Main.databaseModule.syncDatabase(u);
	}

	public List<DatabaseObject> getAll(){
		List<DatabaseObject> list = new ArrayList<>();
		for(String flake : Database.getDatabaseCache().keySet()){
			list.add(new DatabaseObject(Database.getCacheFor(flake).get(name())));
		}
		return list;
	}
	public DatabaseObject getFor(String snowflake) {
		HashMap<String, Object> player_file = new HashMap<String, Object>();
		for (HashMap<String, Object> pfile : Database.getDatabaseCache().values()) {
			if (((String) pfile.get("SNOWFLAKE")).equalsIgnoreCase(snowflake)) {
				player_file = pfile;
			}
		}
		return new DatabaseObject(player_file.get(name()));
	}

	public void setFor(String snowflake, Object o) {
		for (HashMap<String, Object> pfile : Database.getDatabaseCache().values()) {
			if (((String) pfile.get("SNOWFLAKE")).equalsIgnoreCase(snowflake)) {
				pfile.put(name(), o);
			}
		}
	}

	public static boolean isValue(String v) {
		for (UserValue pv : values()) {
			if (pv.name().equalsIgnoreCase(v)) {
				return true;
			}
		}
		return false;
	}
}
