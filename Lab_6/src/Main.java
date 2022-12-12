import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

public class Main {
	private final String NAME="New File";
	private JFileChooser f=new JFileChooser();
	private JTabbedPane tabs=new JTabbedPane();
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
				public void run()
				{
					new Main();
				}
				});
		
	}
	public Main()
	{
		JMenuBar menu=new JMenuBar();
		JMenu file=new JMenu("File");
		JMenuItem newFile=new JMenuItem("Create File");
		JMenuItem openFile=new JMenuItem("Open File");
		JMenuItem saveFile=new JMenuItem("Save File");
		
		file.add(newFile);
		file.add(openFile);
		file.add(saveFile);
		
		menu.add(file);
		
		JFrame window=new JFrame("Notepade");
		window.setSize(800,600);
		
		window.setJMenuBar(menu);
	
		window.add(tabs);
		
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		newFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JTextArea text=new JTextArea();
				Scroll scroll=new Scroll(text,false,"");
				
				tabs.addTab(NAME, scroll);
			}
		});
		saveFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Scroll text=(Scroll) tabs.getSelectedComponent();
				String output= text.getText();

				if(tabs.countComponents()!=0) {
					try
					{
						FileOutputStream writer=new FileOutputStream(text.getPath());
						writer.write(output.getBytes());
					}catch(IOException eq) {eq.printStackTrace();};
				}
				else
				{ //JOptionPane.showMessageDialog(null,output);
				  f.showSaveDialog(null);
				  File file=f.getSelectedFile();
					try
					{
						FileOutputStream writer=new FileOutputStream(file);
						writer.write(output.getBytes());
					}catch(IOException eq) {eq.printStackTrace();};
				}
			}
		});
		openFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					f.showOpenDialog(null);
					File file=f.getSelectedFile();
					String input=new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
					JTextArea text=new JTextArea(input);
					
					Scroll scroll=new Scroll(text,true,file.getAbsolutePath());
					
					tabs.addTab(file.getName(), scroll);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}

}
