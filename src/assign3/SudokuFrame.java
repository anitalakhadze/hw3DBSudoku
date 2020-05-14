package assign3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SudokuFrame extends JFrame {
 	private Sudoku sudoku;
 	private JTextArea txtAreaSource;
 	private JTextArea txtAreaResult;
 	private JButton btnCheck;
 	private JCheckBox chkBoxAutoCheck;
 	private JComponent container;
 	private JComponent controls;

	/**
	 * Sets up the frame with the basic components for representation.
	 */
	public SudokuFrame() {
		super("Sudoku Solver");
		setLocationByPlatform(true);

		container = (JComponent)getContentPane();
		container.setLayout(new BorderLayout(4, 4));
		txtAreaSource = new JTextArea(15, 20);
		txtAreaSource.setBorder(new TitledBorder("Puzzle"));
		txtAreaResult = new JTextArea(15, 20);
		txtAreaResult.setBorder(new TitledBorder("Solution"));
		txtAreaResult.setEditable(false);
		container.add(txtAreaSource, BorderLayout.CENTER);
		container.add(txtAreaResult, BorderLayout.EAST);

		controls = new JPanel();
		btnCheck = new JButton("Check");
		btnCheck.addActionListener(actionEvent -> check());
		chkBoxAutoCheck = new JCheckBox("Auto Check", true);
		controls.add(btnCheck);
		controls.add(chkBoxAutoCheck);
		container.add(controls, BorderLayout.SOUTH);

		txtAreaResult.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				check();
			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				check();
			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent) {
				check();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * when a check button is clicked, sudoku is constructed and
	 * the computer tries to solve it.
	 */
	 private void check() {
		String result = getSolutionText();
		SwingUtilities.invokeLater(() -> txtAreaResult.setText(result));
	 }

	/**
	 * @return returns the solution as a text.
	 */
	private String getSolutionText(){
		try{
			sudoku = new Sudoku(Sudoku.textToGrid(txtAreaSource.getText()));
			int count = sudoku.solve();
			return sudoku.getSolutionText() +
					"Solutions: " + count + "\n" +
					"Elapsed: " + sudoku.getElapsed() + " ms. \n";
		} catch (Exception ex) {
			return "Parsing problem : \n" + ex.getMessage();
		}
	 }

	 public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
		SwingUtilities.invokeLater(() -> frame.txtAreaSource.setText("1 6 4 0 0 0 0 0 2 " +
				"2 0 0 4 0 3 9 1 0 " +
				"0 0 5 0 8 0 4 0 7 " +
				"0 9 0 0 0 6 5 0 0 " +
				"5 0 0 1 0 2 0 0 8 " +
				"0 0 8 9 0 0 0 3 0 " +
				"8 0 9 0 4 0 2 0 0 " +
				"0 7 3 5 0 9 0 0 1 " +
				"4 0 0 0 0 0 6 7 9")
		);
	}
}
