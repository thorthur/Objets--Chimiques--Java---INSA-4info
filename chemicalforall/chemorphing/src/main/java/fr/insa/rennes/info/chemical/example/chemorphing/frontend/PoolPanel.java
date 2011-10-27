package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.MarkedImage;
import fr.insa.rennes.info.chemical.example.chemorphing.middleend.PoolManager;

public class PoolPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8061512060922263630L;

	JPanel poolPanel = new JPanel();
	
	public PoolPanel(){
		super();

		Dimension ppDim = new Dimension(Globals.WINDOW_WIDTH *2/5, Globals.WINDOW_HEIGHT *3/8);
		poolPanel.setPreferredSize(ppDim);
		poolPanel.setMaximumSize(ppDim);
		
		poolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JScrollPane jsp = new JScrollPane(poolPanel);

		Dimension jspDim = new Dimension(Globals.WINDOW_WIDTH *2/5 +20, Globals.WINDOW_HEIGHT *3/8);
		jsp.setPreferredSize(jspDim);
		jsp.setMaximumSize(jspDim);
		
		this.add(jsp);
		
		refreshPanel();
	}
	
	private void loadPoolDisplay(){
		poolPanel.removeAll();
		for(MarkedImage mi : PoolManager.getPool().get_pool()){
			JLabel jl = new JLabel(new ImageIcon(mi.get_image().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
			jl.setToolTipText(mi.get_name());
			poolPanel.add(jl);
		}
	}
	
	public void refreshPanel(){
		loadPoolDisplay();
	}

}