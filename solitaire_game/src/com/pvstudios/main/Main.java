package com.pvstudios.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class Main extends JMenuBar {

    private static final long serialVersionUID = 1L;
    
    private JButton selectedButton; 
    private BufferedImage selectedBack;
    
    public Main() {
        super();

        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = createMenuItem("New Game", e -> Game.table.newGame());
        JMenuItem statistics = createMenuItem("Statistics", null);
        JMenuItem options = createMenuItem("Options", null);
        JMenuItem selectCardBack = createMenuItem("Select Card Back", e -> selectCardMenu());
        JMenuItem exit = createMenuItem("Exit", null);

        gameMenu.add(newGame);
        gameMenu.add(statistics);
        gameMenu.add(options);
        gameMenu.add(selectCardBack);
        gameMenu.addSeparator();
        gameMenu.add(exit);

        JMenu helpMenu = new JMenu("Help");

        JMenuItem contents = createMenuItem("Contents", null);
        JMenuItem about = createMenuItem("About", null);

        helpMenu.add(contents);
        helpMenu.add(about);

        this.add(gameMenu);
        this.add(helpMenu);
    }

    private JMenuItem createMenuItem(String text, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        if (listener != null) {
            menuItem.addActionListener(listener);
        }
        return menuItem;
    }
    
    private void selectCardMenu() {
    	try {
            // Set Windows look and feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Select Card Back");
        frame.setSize(400, 300);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(212, 208, 200));

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(new Color(212, 208, 200));
        cardPanel.setBorder(new EmptyBorder(35, 0, 0, 0));

        List<BufferedImage> cardsBack = Arrays.asList(
                Game.spritesheet.getSprite(4, 0, 0, 1),
                Game.spritesheet.getSprite(4, 1, 0, 1),
                Game.spritesheet.getSprite(4, 2, 0, 1),
                Game.spritesheet.getSprite(4, 3, 0, 1),
                Game.spritesheet.getSprite(4, 4, 0, 1),
                Game.spritesheet.getSprite(4, 5, 0, 1),
                Game.spritesheet.getSprite(4, 6, 0, 1),
                Game.spritesheet.getSprite(4, 9, 0, 1),
                Game.spritesheet.getSprite(5, 0, 0, 1),
                Game.spritesheet.getSprite(5, 1, 0, 1),
                Game.spritesheet.getSprite(5, 3, 0, 1),
                Game.spritesheet.getSprite(5, 6, 0, 1)
        );

     
        for (BufferedImage cardBack : cardsBack) {
        	
            Image scaledImage = cardBack.getScaledInstance(108, 144, Image.SCALE_FAST);
            ImageIcon cardIcon = new ImageIcon(scaledImage);
            JButton button = new JButton();
            button.setIcon(cardIcon);
            button.setBorder(new BevelBorder(BevelBorder.RAISED, Color.white, Color.LIGHT_GRAY, Color.gray, Color.gray));
 
            button.addActionListener(e -> {
            	  
       
                JButton clickedButton = (JButton) e.getSource();
                if (selectedButton == null) {
                	selectedBack = cardBack;
                    selectedButton = clickedButton;
                    selectedButton.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.white, Color.LIGHT_GRAY, Color.gray, Color.gray));
                } else if (selectedButton != clickedButton) {
                    // Another button was previously selected
                	selectedBack = cardBack;
                    selectedButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.white, Color.LIGHT_GRAY, Color.gray, Color.gray));
                    selectedButton = clickedButton;
                    selectedButton.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.white, Color.LIGHT_GRAY, Color.gray, Color.gray));
                    
                }
            });

            cardPanel.add(button);
        }
        
       panel.setBorder(new EmptyBorder(35,0,0,0));
       
       JPanel buttonPanel = new JPanel();
       buttonPanel.setBackground(new Color(212, 208, 200));

       JButton cancelButton = new JButton("Cancel");
       cancelButton.addActionListener(e -> {
           // Handle cancel button action
       });

       JButton okButton = new JButton("OK");
       okButton.addActionListener(e -> {
		Card.faceDownImage = selectedBack;
		frame.setVisible(false);
       });

       buttonPanel.add(cancelButton);
       buttonPanel.add(okButton);

       panel.add(cardPanel, BorderLayout.CENTER);
       panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setSize(Game.frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
