package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.CoffeeMaker;
import edu.pjatk.inn.coffeemaker.impl.Inventory;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.service.ContextException;
import sorcer.service.Routine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sorcer.eo.operator.*;
import static sorcer.so.operator.eval;
import static sorcer.so.operator.exec;

/**
 * @author Mike Sobolewski
 */
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class CoffeeMakerTest {
	private final static Logger logger = LoggerFactory.getLogger(CoffeeMakerTest.class);

	private CoffeeMaker coffeeMaker;
	private Inventory inventory;
	private Recipe espresso, mocha, macchiato, americano;

	@Before
	public void setUp() throws ContextException {
		coffeeMaker = new CoffeeMaker();
		inventory = coffeeMaker.checkInventory();

		espresso = new Recipe();
		espresso.setName("espresso");
		espresso.setPrice(50);
		espresso.setAmtCoffee(6);
		espresso.setAmtMilk(1);
		espresso.setAmtSugar(1);
		espresso.setAmtChocolate(0);

		mocha = new Recipe();
		mocha.setName("mocha");
		mocha.setPrice(100);
		mocha.setAmtCoffee(8);
		mocha.setAmtMilk(1);
		mocha.setAmtSugar(1);
		mocha.setAmtChocolate(2);

		macchiato = new Recipe();
		macchiato.setName("macchiato");
		macchiato.setPrice(40);
		macchiato.setAmtCoffee(7);
		macchiato.setAmtMilk(1);
		macchiato.setAmtSugar(2);
		macchiato.setAmtChocolate(0);

		americano = new Recipe();
		americano.setName("americano");
		americano.setPrice(40);
		americano.setAmtCoffee(7);
		americano.setAmtMilk(1);
		americano.setAmtSugar(2);
		americano.setAmtChocolate(0);
	}

	@Test
	public void testAddRecipe() {
		assertTrue(coffeeMaker.addRecipe(espresso));
	}

	@Test
	public void testContextCofee() throws ContextException {
		assertTrue(espresso.getAmtCoffee() == 6);
	}

	@Test
	public void testContextMilk() throws ContextException {
		assertTrue(espresso.getAmtMilk() == 1);
	}

	@Test
	public void addRecepie() throws Exception {
		coffeeMaker.addRecipe(mocha);
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addContextRecepie() throws Exception {
		coffeeMaker.addRecipe(Recipe.getContext(mocha));
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}



	@Test
	public void addServiceRecepie() throws Exception {
		Routine cmt = task(sig("addRecipe", coffeeMaker),
						context(types(Recipe.class), args(espresso),
							result("recipe/added")));

		logger.info("isAdded: " + exec(cmt));
		assertEquals(coffeeMaker.getRecipeForName("espresso").getName(), "espresso");
	}

	@Test
	public void addRecipes() throws Exception {
		coffeeMaker.addRecipe(mocha);
		coffeeMaker.addRecipe(macchiato);
		coffeeMaker.addRecipe(americano);

		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
		assertEquals(coffeeMaker.getRecipeForName("macchiato").getName(), "macchiato");
		assertEquals(coffeeMaker.getRecipeForName("americano").getName(), "americano");
	}

	@Test
	public void makeCoffee() throws Exception {
		coffeeMaker.addRecipe(espresso);
		assertEquals(coffeeMaker.makeCoffee(espresso, 200), 150);
	}

	//------------------------------------------------------

	@Test
	public void addRecipe()throws Exception {
		coffeeMaker.addRecipe(espresso);
		Recipe r = coffeeMaker.getRecipeForName(espresso.getName());
		assertEquals(espresso.getName(),r.getName());
	}

	@Test
	public void deleteRecipe()throws Exception {
		coffeeMaker.addRecipe(espresso);
		Recipe r = coffeeMaker.getRecipeForName(espresso.getName());
		assertEquals(coffeeMaker.deleteRecipe(r),true);
		assertEquals(null,r);
	}

	@Test
	public void editRecipe()throws Exception {
		coffeeMaker.addRecipe(espresso); //coffeeMaker -> espresso
		coffeeMaker.editRecipe(espresso,macchiato); // coffeeMaker -> macchiato
		Recipe rOld = coffeeMaker.getRecipeForName(espresso.getName()); //null
		Recipe rNew = coffeeMaker.getRecipeForName(macchiato.getName()); //macchiato

		assertEquals(rOld,null);
		assertEquals(rNew.getName(),macchiato.getName());
	}

	@Test
	public void addInventory()throws Exception {
		coffeeMaker.addInventory(10,10,10,10);
		Inventory inventory = coffeeMaker.checkInventory();
		assertEquals(inventory.getCoffee(),25);
		assertEquals(inventory.getChocolate(),25);
		assertEquals(inventory.getMilk(),25);
		assertEquals(inventory.getSugar(),25);
	}

	@Test
	public void checkInventory()throws Exception {
		Inventory inventory = coffeeMaker.checkInventory();
		assertEquals(inventory.getCoffee(),15);
		assertEquals(inventory.getChocolate(),15);
		assertEquals(inventory.getMilk(),15);
		assertEquals(inventory.getSugar(),15);
	}

	@Test
	public void purchaseCoffee()throws Exception {
		coffeeMaker.addRecipe(espresso);
		int change = coffeeMaker.makeCoffee(espresso,100);
		Inventory inventory = coffeeMaker.checkInventory();

		assertEquals(50,change);
		assertEquals(9,inventory.getCoffee());
		assertEquals(14,inventory.getMilk());
		assertEquals(14,inventory.getSugar());
		assertEquals(15,inventory.getChocolate());
	}

	@Test
	public void deleteRecipes()throws Exception {
		coffeeMaker.addRecipe(espresso);
		coffeeMaker.addRecipe(macchiato);
		coffeeMaker.addRecipe(americano);
		boolean deletedAll = coffeeMaker.deleteRecipes();
		Recipe r1 = coffeeMaker.getRecipeForName(espresso.getName());
		Recipe r2 = coffeeMaker.getRecipeForName(macchiato.getName());
		Recipe r3 = coffeeMaker.getRecipeForName(americano.getName());

		assertEquals(true, deletedAll);
		assertEquals(null, r1);
		assertEquals(null, r2);
		assertEquals(null, r3);
	}

	@Test
	public void addALotOfRecipes()throws Exception {
		boolean added1 = coffeeMaker.addRecipe(espresso);
		boolean added2 = coffeeMaker.addRecipe(macchiato);
		boolean added3 = coffeeMaker.addRecipe(americano);
		boolean added4 = coffeeMaker.addRecipe(americano);
		//boolean added4 = coffeeMaker.addRecipe(mocha);
		//boolean added5 = coffeeMaker.addRecipe(mocha);

		assertEquals(true, added1);
		assertEquals(true, added2);
		assertEquals(true, added3);
		assertEquals(false, added4);
		//assertEquals(false, added5);
	}


//	espresso = new Recipe();
//		espresso.setName("espresso");
//		espresso.setPrice(50);
//		espresso.setAmtCoffee(6);
//		espresso.setAmtMilk(1);
//		espresso.setAmtSugar(1);
//		espresso.setAmtChocolate(0);
}

