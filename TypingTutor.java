package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TypingTutor extends JFrame implements ActionListener {
	private JLabel timer;
	private JLabel score;
	private JLabel word;
	private JTextField txtWord;
	private JButton btnStart;
	private JButton btnStop;;

	private Timer time = null;
	private boolean running = false;
	private int timeRemaining = 0;
	private int Score = 0;

	private String[] words = null;

	public TypingTutor(String[] args) {
		this.words = args;
		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);

		Font font = new Font("Comic Sans MS", 1, 100);

		timer = new JLabel("Timer");
		timer.setFont(font);
		super.add(timer);

		score = new JLabel("Score");
		score.setFont(font);
		super.add(score);

		word = new JLabel("");
		word.setFont(font);
		super.add(word);

		txtWord = new JTextField("");
		txtWord.setFont(font);
		txtWord.setBackground(Color.BLACK);
		txtWord.setForeground(Color.WHITE);
		super.add(txtWord);

		btnStart = new JButton("START");
		btnStart.setFont(font);
		btnStart.setBackground(Color.GREEN);
		btnStart.addActionListener(this);
		super.add(btnStart);

		btnStop = new JButton("STOP");
		btnStop.setFont(font);
		// btnStop.setOpaque(true);
		btnStop.setBackground(Color.RED);
		btnStop.addActionListener(this);
		super.add(btnStop);

		super.setTitle("Typing Tutor");
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setVisible(true);

		setUptheGame();
	}

	private void setUptheGame() {
		time = new Timer(2000, this);
		running = false;
		timeRemaining = 50;
		Score = 0;

		timer.setText("Timer: " + timeRemaining);
		score.setText("Score: " + Score);
		word.setText("");
		txtWord.setText("");
		btnStart.setText("START");
		btnStop.setText("STOP");
		txtWord.setEnabled(false);
		btnStop.setEnabled(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			handleStart();
		} else if (e.getSource() == btnStop) {
			handleStop();
		} else if (e.getSource() == time) {
			handleTime();
		}
	}

	private void handleTime() {
		timeRemaining--;

		String actual, expected;
		actual = txtWord.getText();
		expected = word.getText();

		if (expected.length() > 0 && actual.equals(expected)) {
			Score++;
		}

		score.setText("Score: " + Score);

		if (timeRemaining == -1) {
			time.stop();
		} else {
			timer.setText("Timer: " + timeRemaining);

			int ridx = (int) (Math.random() * words.length);
			word.setText(words[ridx]);
			txtWord.setText("");
		}

	}

	private void handleStop() {
		time.stop();
		int choice = JOptionPane.showConfirmDialog(this, "SCORE: " + Score + ".Replay?");

		if (choice == JOptionPane.YES_OPTION) {
			setUptheGame();
		} else if (choice == JOptionPane.NO_OPTION) {
			super.dispose();
		} else {
			if (timeRemaining == -1)
				setUptheGame();
			else
				handleStart();
		}
	}

	private void handleStart() {
		if (running == true) {
			time.stop();
			running = false;
			btnStart.setText("Resume");
			txtWord.setEnabled(false);
			btnStop.setEnabled(false);

		} else {
			time.start();
			running = true;
			btnStart.setText("Pause");
			txtWord.setEnabled(true);
			btnStop.setEnabled(true);

		}
	}
}
