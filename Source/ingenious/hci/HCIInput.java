package ingenious.hci;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.board.Tile;

/**
 * class to display a GUI with input to a human player
 */
public class HCIInput {
	private static final int MILI_PER_SECOND = 1000;
	private static final int WIDTH = 930;
	private static final int HEIGHT = 800;
	private static final Dimension frameSize = new Dimension(WIDTH, HEIGHT);
	
	private Object listener;
	private boolean timedOutFlag;
	private Placement placement;
	
	private static String timerText;
	private Timer timeOutTimer;
	private Timer clockTimer;
	
	private JFrame frame;
	private JTextField distance0Field;
	private JTextField angle0Field;
	private JTextField distance1Field;
	private JTextField angle1Field;
	private JLabel timerDisplay;
	private JComboBox availibleTilesJCB; 
	private JButton submitButton;
	
	/**
	 * Constructor to create a HCIInput object.
	 * listener is any object that needs to be notified when the human inputs a valid placement
	 */
	public HCIInput(Board board, List<Tile> playerHand, Score playerScore, int timeout, Object listener) {
		timedOutFlag = false; 
		this.listener = listener;
		timerText = "<html><FONT COLOR=RED>Time Remaining:</FONT> ";

		frame = initFrame(board, playerHand, playerScore, timeout);
		
		timeOutTimer = new Timer(timeout*MILI_PER_SECOND, new TimeOutListener());
		clockTimer = new Timer(MILI_PER_SECOND, new TimerListener(timeout));
		timeOutTimer.start();
		clockTimer.start();
	}
	
	/**
	 * displays the gui input frame to the user
	 */
	public void display () {
		frame.setVisible(true);
	}
	/**
	 * Boolean check if the HCIInput component has timed-out.
	 */
	public boolean isTimedOut(){
		return timedOutFlag;
		
	}
	/**
	 * set the frame to not be visible. And then disposes of the frame.
	 */
	public void close(){
		frame.setVisible(false);
		//frame.dispose();
	}
	/**
	 * Retrieve the placement
	 */
	public Placement getPlacement () {
		return placement;
	}
	
	/**
	 * action triggered by the submit button
	 * checks if placement is valid, if so notifies the listener. 
	 * otherwise prompts user to try again.
	 */
	private void placementSubmitted(Board board) {
		Tile tile = (Tile)availibleTilesJCB.getSelectedItem();
		try {
			Placement inputPlacement = new Placement(tile.getColor0(),
													 Integer.parseInt(distance0Field.getText()), 
													 Integer.parseInt(angle0Field.getText()),
													 tile.getColor1(),
													 Integer.parseInt(distance1Field.getText()),
													 Integer.parseInt(angle1Field.getText()));
			
		 	if (board.verifyValidPlacement(inputPlacement)) {
					clockTimer.stop();
					timeOutTimer.stop();
					placement = inputPlacement;
					synchronized(listener) {
						listener.notify();
				}
			}
			else {
				JOptionPane.showMessageDialog(frame, "Invalid Placement! Try Again.");
			}
		}
		catch(NumberFormatException e) {
			System.out.println("Bad number specified");
		}
		catch (StateException e) {
			System.out.println(e);
		}
	}

	/**
	 * Creates and populates the frame with the various panels that make up the player-interface.
	 */
	private JFrame initFrame(Board board, List<Tile> playerHand, Score playerScore, int timeout){
		JPanel playerPanel = GUIGameState.createPlayerPanel(board, playerHand, playerScore);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameSize);
		JPanel mainPanel = new JPanel();
		JPanel inputPanel = createInputPanel(board, playerHand, playerScore, timeout);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//playerPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(100,100,100)));
		playerPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		mainPanel.add(playerPanel);
		mainPanel.add(inputPanel);
		frame.setContentPane(mainPanel);
		frame.setMinimumSize(frameSize);
		return frame;
	}
	
	/**
	 * Formats the input panel of the interface
	 */
	private JPanel createInputPanel (Board board, List<Tile> playerHand, Score playerScore, int timeout) {
		Dimension boxSize = new Dimension((int)(WIDTH*.07),(int)(HEIGHT*.07));
		
		JPanel leftPanel  = new JPanel();
		JPanel middlePanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		Component box = Box.createRigidArea(boxSize);
		Component box1 = Box.createRigidArea(boxSize);
		Component box2 = Box.createRigidArea(boxSize);
		
		JLabel colorLabel = new JLabel("Tile: Color0,Color1");
		JLabel distance0Label = new JLabel("distance0:");
		distance0Field = new JTextField();
		JLabel angle0Label = new JLabel("angle0:");
		angle0Field = new JTextField();
		JLabel distance1Label = new JLabel("distance1:");
		distance1Field = new JTextField();
		JLabel angle1Label = new JLabel("angle1:");
		angle1Field = new JTextField();
		
		submitButton = new JButton("Submit");
		
		timerDisplay = new JLabel(timerText  + timeout);
		timerDisplay.setFont(new Font("Serif", Font.BOLD, 20));
		
		availibleTilesJCB = new JComboBox(playerHand.toArray());
		availibleTilesJCB.setSelectedIndex(0);
		availibleTilesJCB.setAlignmentX(0.0f);
		
		leftPanel.add(timerDisplay);
		leftPanel.add(colorLabel);
		leftPanel.add(availibleTilesJCB);
		middlePanel.add(distance0Label);
		middlePanel.add(distance0Field);
		middlePanel.add(angle0Label);
		middlePanel.add(angle0Field);
		rightPanel.add(distance1Label);
		rightPanel.add(distance1Field);
		rightPanel.add(angle1Label);
		rightPanel.add(angle1Field);
		
		submitButton.addActionListener(new SubmitActionListener(board));
		
		mainPanel.add(leftPanel);
		mainPanel.add(box);
		mainPanel.add(middlePanel);
		mainPanel.add(box1);
		mainPanel.add(rightPanel);
		mainPanel.add(box2);
		mainPanel.add(submitButton);
		return mainPanel;
	}
	
	/**
	 * ActionListener for submit button
	 */
	class SubmitActionListener implements ActionListener {
		private final Board board;
		public SubmitActionListener(Board board) {
			this.board = board;
		}
		
		public void actionPerformed(ActionEvent e) {
			placementSubmitted(board);
			
		}
	}
	
	/**
	 * ActionListener for timeout 
	 */
	class TimeOutListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			timeOutTimer.stop();
			clockTimer.stop();
			timerDisplay.setText(timerText + 0);
			JOptionPane.showMessageDialog(frame, "Timed Out!");
			//state = HCIState.TIMEOUT;
			synchronized(listener) {
				timedOutFlag = true; 
				listener.notify();
			}
		}	
	}
	
	/**
	 * ActionListener for timer display
	 */
	class TimerListener implements ActionListener{
		private int timeLeft;
		public TimerListener(int t){
			timeLeft = t;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			timeLeft--;
			timerDisplay.setText(timerText + timeLeft);
		}
		
	}
}
