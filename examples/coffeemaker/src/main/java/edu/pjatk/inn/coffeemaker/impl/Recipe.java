package edu.pjatk.inn.coffeemaker.impl;

import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * @author   Sarah & Mike
 */
public class Recipe implements Serializable {
    private String name;
    private int price;
    private int amtCoffee;
    private int amtMilk;
    private int amtSugar;
    private int amtChocolate;
    
    public Recipe() {
    	this.name = "";
    	this.price = 0;
    	this.amtCoffee = 0;
    	this.amtMilk = 0;
    	this.amtSugar = 0;
    	this.amtChocolate = 0;
    }
    
    /**
	 * Returns the amount of chocolate
	 * @return   the amount of chocolate
	 */
    public int getAmtChocolate() {
		return amtChocolate;
	}
    /**
	 * Sets the amount of chocolate and it needs to be equal or bigger than 0
	 * @param amtChocolate   the amount of chocolate
	 */
    public void setAmtChocolate(int amtChocolate) {
		if (amtChocolate >= 0) {
			this.amtChocolate = amtChocolate;
		} 
	}
    /**
	 * Returns the amount of coffee
	 * @return   the amount of coffee
	 */
    public int getAmtCoffee() {
		return amtCoffee;
	}
    /**
	 * Sets the amount of coffee and it needs to be equal or bigger than 0
	 * @param amtCoffee   the amount of coffee
	 */
    public void setAmtCoffee(int amtCoffee) {
		if (amtCoffee >= 0) {
			this.amtCoffee = amtCoffee;
		} 
	}
    /**
	 * Returns the amount of milk
	 * @return   the amount of milk
	 */
    public int getAmtMilk() {
		return amtMilk;
	}
    /**
	 * Sets the amount of milk and it needs to be equal or bigger than 0
	 * @param amtMilk   the amount of milk
	 */
    public void setAmtMilk(int amtMilk) {
		if (amtMilk >= 0) {
			this.amtMilk = amtMilk;
		} 
	}
    /**
	 * Returns the amount of sugar
	 * @return   the amount of sugar
	 */
    public int getAmtSugar() {
		return amtSugar;
	}
    /**
	 * Sets the amount of milk and it needs to be equal or bigger than 0
	 * @param amtSugar  amount of sugar
	 */
    public void setAmtSugar(int amtSugar) {
		if (amtSugar >= 0) {
			this.amtSugar = amtSugar;
		} 
	}
    /**
	 * Returns the name of the recipe
	 * @return   the name of recipe.
	 */
    public String getName() {
		return name;
	}
    /**
	 * Set the name of recipe and it needs to be in the menu
	 * @param name   the name of recipe
	 */
    public void setName(String name) {
    	if(name != null) {
    		this.name = name;
    	}
	}
    /**
	 * Returns the price of the coffee
	 * @return   the price of coffee
	 */
    public int getPrice() {
		return price;
	}
    /**
	 * sets the price of the coffee and it needs to be equal to or bigger than 0
	 * @param price   the price of coffee
	 */
    public void setPrice(int price) {
		if (price >= 0) {
			this.price = price;
		} 
	}
	/**
	 * if the name of the recipe is in the menu return true otherwise false
	 */
    public boolean equals(Recipe r) {
        if((this.name).equals(r.getName())) {
            return true;
        }
        return false;
    }


    public String toString() {
    	return name;
    }

	static public Recipe getRecipe(Context context) throws ContextException {
		Recipe r = new Recipe();
		try {
			r.name = (String)context.getValue("key");
			r.price = (int)context.getValue("price");
			r.amtCoffee = (int)context.getValue("amtCoffee");
			r.amtMilk = (int)context.getValue("amtMilk");
			r.amtSugar = (int)context.getValue("amtSugar");
			r.amtChocolate = (int)context.getValue("amtChocolate");
		} catch (RemoteException e) {
			throw new ContextException(e);
		}
		return r;
	}

	static public Context getContext(Recipe recipe) throws ContextException {
		Context cxt = new ServiceContext();
		cxt.putValue("key", recipe.getName());
		cxt.putValue("price", recipe.getPrice());
		cxt.putValue("amtCoffee", recipe.getAmtCoffee());
		cxt.putValue("amtMilk", recipe.getAmtMilk());
		cxt.putValue("amtSugar", recipe.getAmtSugar());
		cxt.putValue("amtChocolate", recipe.getAmtChocolate());
		return cxt;
	}


}
