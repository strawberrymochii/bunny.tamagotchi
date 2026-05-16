
import javax.swing.JOptionPane;

public class Bunny {
	//instance variables for a bunny object
	String strName;
	String strState;
	byte bytAge;
	String strStage;
	byte bytHappiness;
	byte bytEnergy;
	byte bytHunger;
	static int intCount = 0;
	
	//constructor 
	Bunny(String n){
		this.strName = n;
		this.strState = "";
		this.bytAge = 0;
		this.bytHappiness = 100;
		this.bytEnergy = 100;
		this.bytHunger = 100;
		this.strStage = "Baby";
	}
	
	//method to decrease stats based on time
	public void passTime(){
		this.bytHappiness--;
		this.bytHunger--;
		this.bytEnergy--;
		intCount++;

		if (this.bytHappiness < 0){
			this.bytHappiness = 0;
		}

		if (this.bytHunger < 0){
			this.bytHunger = 0;
		}

		if (this.bytEnergy < 0){
			this.bytEnergy = 0;
		}

		if (this.bytEnergy == 0){
			this.strState = "sleeping";
		}
	
		//increase age
		if (intCount%10 == 0) {
			this.bytAge++;
		}
		
		//update life stage
		if(this.bytAge == 5) {
			this.strStage = "Child";
		}
		else if (this.bytAge == 15) {
			this.strStage = "Teen";
		}
		else if (this.bytAge >= 25) {
			this.strStage = "Adult";
		}
	}
	

	//method to feed
	public void feed() {
		if (this.bytHunger < 90){
			this. bytHunger += 10;
		}
		else{
			this.bytHunger = 100;
		}
		
	}

	//method to play
	public void play() {
		this.bytEnergy -= 10;
		
		byte randomNumber = (byte)(Math.round(Math.random()*11));
		//prompt user to guess the number
		String strGuess = JOptionPane.showInputDialog("Guess a number between 0 and 10");
		byte bytGuess = Byte.parseByte(strGuess);

		//logic for guessing game
		if(bytGuess == randomNumber) {
			if (this.bytHappiness < 90){
				this.bytHappiness += 10;
			}
			else{
				this.bytHappiness = 100;
			}
			JOptionPane.showMessageDialog(null, "You guessed the right number!");
		}
		else {
			JOptionPane.showMessageDialog(null, "You guessed the wrong number...");
		}
	}

	// headless-friendly play method (no UI prompts)
	public void playHeadless() {
		if (this.bytEnergy >= 10) {
			this.bytEnergy -= 10;
		} else {
			this.bytEnergy = 0;
		}
		// small chance to increase happiness
		double chance = Math.random();
		if (chance < 0.35) {
			if (this.bytHappiness < 90) {
				this.bytHappiness += 10;
			} else {
				this.bytHappiness = 100;
			}
		}
	}
	

}
