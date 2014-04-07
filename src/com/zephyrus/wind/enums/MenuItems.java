package com.zephyrus.wind.enums;

public enum MenuItems {
	    HOME(1,"HOME","home"),
	    CONTACTS(2,"CONTACTS","contacts");
	    
	    int id;
	    String itemName;
	    String value;
	    
	    MenuItems(int id, String itemName, String value){
	        this.id = id;
	        this.itemName = itemName;
	        this.value = value;
	    }
	    
	    public int getId(){
	        return id;
	    }
	    
	    public String getValue(){
	        return value;
	    }
	    
	    public String getItemName(){
	        return itemName;
	    }
	    
	    public static MenuItems findByName(String name){
	        for(MenuItems item : MenuItems.values())
	            if(item.getItemName().equals(name))
	                return item;
	        return null;
	    }
	    
	     public static MenuItems findByValue(String value){
	        for(MenuItems item : MenuItems.values())
	            if(item.getValue().equals(value))
	                return item;
	        return null;
	    }
	     
	     public static MenuItems findById(int id){
	        for(MenuItems item : MenuItems.values())
	            if(item.getId()== id)
	                return item;
	        return null;
	    }
}
