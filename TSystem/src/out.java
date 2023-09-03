import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.*;

public class out extends JFrame {
	
	public JTextArea outputTextArea;
	
	public out() {
        setTitle("Output Display");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

	public JTextArea getOutputTextArea() {
		return outputTextArea;
	}

	public void setOutputTextArea(JTextArea outputTextArea) {
		this.outputTextArea = outputTextArea;
	}

}
