package com.esllo.morpratio;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class MorphemeCounter {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new MorphemeCounter();
	}

	public MorphemeCounter() {
		TreeMap<String, Integer> map = new TreeMap<>();
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "File Not Found.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null, regex = "(/[A-Z]{1,3})";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher;
			while ((line = reader.readLine()) != null) {
				matcher = pattern.matcher(line);
				while (matcher.find()) {
					if (map.containsKey(matcher.group()))
						map.put(matcher.group(), map.get(matcher.group()) + 1);
					else
						map.put(matcher.group(), 1);
				}
			}
			String text = "";
			for (Entry<String, Integer> entry : map.entrySet())
				text += "\n" + entry.getKey() + " : " + entry.getValue();
			text = text.replaceFirst("\n", "");
			JFrame frame = new JFrame("MorpRatio");
			JTextArea area = new JTextArea(text);
			JScrollPane sc = new JScrollPane(area);
			sc.setPreferredSize(new Dimension(600, 400));
			frame.setContentPane(sc);
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
