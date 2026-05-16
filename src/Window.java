
//import java swing GUI
import javax.swing.*;

import java.awt.event.*;

import java.awt.*;

import java.awt.Image.*;

public class Window extends JFrame{
	//constructor
	private static final long serialVersionUID = 1L;

	Window(Bunny bunny){
		//set the window components
		this.setTitle("Bunny Game");
		this.setSize(200, 350);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
		//create the main panel
		JPanel gameScreen = new JPanel();
		gameScreen.setSize(200, 350);
		//gameScreen.setLocation(10, 10);
		
		this.add(gameScreen, BorderLayout.CENTER);

JTextField text = new JTextField("Name", 20);
		gameScreen.add(text, BorderLayout.NORTH);
		String name = text.getText();

		//change name
		bunny.strName = name;

		ImageIcon icon = new ImageIcon("src/bunny-awake.png");
		Image img = icon.getImage();
		Image awake = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon awakeIcon = new ImageIcon(awake);
		JLabel bunnyIcon = new JLabel(awakeIcon);
		bunnyIcon.setSize(100, 100);
		bunnyIcon.setHorizontalAlignment(JLabel.CENTER);
		gameScreen.add(bunnyIcon, BorderLayout.CENTER);

		
		//age
		JLabel age = new JLabel("Age: " + bunny.bytAge);
		age.setSize(200, 30);
		age.setLocation(10, 750);
		gameScreen.add(age, BorderLayout.SOUTH);

		JLabel happinessLabel = new JLabel("Happiness: ");
		happinessLabel.setSize(200, 30);
		happinessLabel.setLocation(10, 600);
		gameScreen.add(happinessLabel);
		//create progress bars for stats
		JProgressBar happiness = new JProgressBar();
		happiness.setMaximum(100);
		happiness.setMinimum(0);
		happiness.setStringPainted(true);
		happiness.setSize(200, 30);
		happiness.setLocation(10, 700);
		happiness.setValue(bunny.bytHappiness);
		gameScreen.add(happiness);
		

		JLabel energyLabel = new JLabel("Energy: ");
		energyLabel.setSize(200, 30);
		energyLabel.setLocation(10, 600);
		gameScreen.add(energyLabel);
		JProgressBar energy = new JProgressBar();
		energy.setMaximum(100);
		energy.setMinimum(0);
		energy.setStringPainted(true);
		energy.setSize(200, 30);
		energy.setLocation(10, 700);
		energy.setValue(bunny.bytEnergy);
		gameScreen.add(energy);
		

		JLabel hungerLabel = new JLabel("Hunger: ");
		hungerLabel.setSize(200, 30);
		hungerLabel.setLocation(10, 600);
		gameScreen.add(hungerLabel);
		JProgressBar hunger = new JProgressBar();
		hunger.setMaximum(100);
		hunger.setMinimum(0);
		hunger.setStringPainted(true);
		hunger.setSize(200, 30);
		hunger.setLocation(10, 700);
		hunger.setValue(bunny.bytHunger);
		gameScreen.add(hunger);
		

		

		//create buttons for methods
		JButton feed = new JButton("Feed");

		feed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bunny.feed();
			}
		});

		feed.setSize(100, 50);
		feed.setLocation(10, 920);
		gameScreen.add(feed, BorderLayout.SOUTH);

		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bunny.play();
			}
		});

		play.setSize(100, 50);
		play.setLocation(120, 920);
		gameScreen.add(play, BorderLayout.SOUTH);

		ActionListener timerListener = e -> {
			bunny.passTime();
			happiness.setValue(bunny.bytHappiness);
			energy.setValue(bunny.bytEnergy);
			hunger.setValue(bunny.bytHunger);
			age.setText("Age: " + bunny.bytAge);

			if (bunny.strState.equals("sleeping")){

				ImageIcon ic = new ImageIcon("src/bunny-asleep.png");
				Image im = ic.getImage();
				Image sleep = im.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				ImageIcon sleepIcon = new ImageIcon(sleep);
				bunnyIcon.setIcon(sleepIcon);
				JOptionPane.showMessageDialog(null, "Your bunny fell asleep!");
			}
			else {
				bunnyIcon.setIcon(awakeIcon);
			}
		};

		Timer timer = new Timer(1000, timerListener);
		timer.start();
		}
	}

