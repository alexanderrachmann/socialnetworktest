package socialNetworkTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import socialnetwork.UserCompound;
import socialnetwork.UserController;
import socialnetwork.UserModel;
import socialnetwork.UserView;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import socialnetwork.*;

public class StepDefinitions {
	
	Map<String, UserCompound> UserDatabase = 
			new HashMap<String, UserCompound>();

	
	public static UserCompound populateNetwork(String Name) {
		UserModel model = new UserModel(Name);
		UserView view = new UserView();
		UserController controller = new UserController(model, view);
		
		UserCompound compound = 
				new UserCompound(controller, model, view);
		return compound;
	}
	
	public static String showFriendship(Map<String, UserCompound> UserDatabase, String user) {		
		return UserDatabase.get(user).controller.updateView();		
	}
	
	@Given("I populated the network")
	public void i_populated_the_network() {
		UserDatabase.put("Yoda", populateNetwork("Yoda"));
		UserDatabase.put("Lando", populateNetwork("Lando"));
		UserDatabase.put("Han", populateNetwork("Han"));
		UserDatabase.put("Anakin", populateNetwork("Anakin"));  
	    
	}

	@When("I befriended Yoda with all his friends")
	public void i_befriended_Yoda_with_all_his_friends() {
		// Yoda likes everyone
		UserDatabase.get("Lando").controller.addFriend("Yoda");
		UserDatabase.get("Han").controller.addFriend("Yoda");
		UserDatabase.get("Anakin").controller.addFriend("Yoda");
				
		// Han Solo likes Lando and otherwise. Both like Yoda
		UserDatabase.get("Yoda").controller.addFriend("Lando");
		UserDatabase.get("Yoda").controller.addFriend("Han");
	}

	@SuppressWarnings("deprecation")
	@Then("Anakin should be one of his friends")
	public void anakin_should_be_one_of_his_friends() {
		String friendshipOverview = UserDatabase.get("Yoda").controller.updateView();
		Assert.assertTrue(friendshipOverview.contains("Anakin"));
	}

}
