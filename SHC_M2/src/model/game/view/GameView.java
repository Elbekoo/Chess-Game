package model.game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKick;


@SuppressWarnings("serial")
public  class GameView  extends JFrame implements ActionListener{
	
	private Game game;
	private Player player1;
	private Player player2;
   private JPanel jpboard ;
	private JTextArea info;
	private JPanel jppayload1;
	private JPanel jppayload2;
	private JButton[][] allbuttons ;
   private JPanel arows;
   private JButton up ,down,right,left,upright,upleft,downright,downleft,powerUse ;  
  private JLabel[] payloadposp1;
  private JLabel[] payloadposp2;
	private JPanel winner;
	 private Piece curp=null;
	private Direction d;
    private Point  newPos;
	private boolean pw;
	private int counter=0;
	private Piece  secondp;
	private Piece  secondp2;
	 private JLabel error;
	 private JFrame popUp ;
	 private JPanel listd1;
	 private JPanel listd2;
	 private Player cur;
	 private JButton d1;
	 private JFrame popd1;
	 private JButton d2;
	 private JFrame popd2;
	 private JButton[] jbd1;
	 private JButton[] jbd2;
	public static void main(String[] args) throws Exception {
		new GameView();
		
	}
	
	
public GameView() throws Exception {
	
	//set title
	super("Game for Heros");
	
	popUp = new JFrame();
	popUp.setBounds(600, 250, 300, 200);
	popUp.setLayout(new FlowLayout());
    error = new JLabel();
    
    
    popd1 =new JFrame();
    popd1.setLayout(new FlowLayout());
    popd1.setBounds(1250, 300,600,650 );
     
    popd2 =new JFrame();
    popd2.setLayout(new FlowLayout());
    popd2.setBounds(1250, 300,600,650 );
    
	revalidate();
	 repaint();
	 //set Bounds	
	setBounds(100, 100, 800, 600);
	//set color	
	getContentPane().setBackground(Color.BLACK);
	// close operation	
	setDefaultCloseOperation(EXIT_ON_CLOSE);	
	//the name of the players	
	String p1 = JOptionPane.showInputDialog("Enter first player name");	
	String p2 = JOptionPane.showInputDialog("Enter second player name");
	// update the names of the two players
	 player1 =new Player(p1); 
	 player2 =new Player(p2);
	
	 
	
	 SwingUtilities.updateComponentTreeUI(this);   
	  //force null layout
		 getContentPane().setLayout(null);
	 
	 //initialize the end game
		winner = new JPanel();
		winner.setBounds(737, 10, 300, 50);
		winner.setBackground(Color.ORANGE);
		winner.setOpaque(true);
		JLabel w =new JLabel("'NO WINNER YET'");
		//w.setVerticalAlignment(Alignment.CENTER);
		w.setBounds(400, 0, 200, 50);
		w.setForeground(Color.BLACK);
		w.setForeground(Color.darkGray);
	    w.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		winner.setOpaque(true);
		winner.add(w);
		add(winner);
		//initialize the array dead buttons
		
		jbd1 = new JButton[12];
		jbd2= new JButton[12];
		
		
		
	 //create a layout for board
	 jpboard=new JPanel();
	 jpboard.setLayout(new GridLayout(7,6));
	 jpboard.setOpaque(true);
	 jpboard.setBounds(400,100,1000,700 );
	 add(jpboard, BorderLayout.CENTER);
	 
	 //initialization
	    listd1=new JPanel();
	    listd1.setLayout(new GridLayout(4,3));
	    listd1.setSize(popd1.getWidth(), popd1.getHeight());
	    listd1.setOpaque(true);
	    
	    listd2=new JPanel();
	    listd2.setLayout(new GridLayout(4,3));
	    listd2.setBounds(0, 0, 600, 650);
	    listd2.setOpaque(true);
	    
	    d1 = new JButton();
	    d1.setBounds(1600, 100, 100, 100);
	    d1.setText("dead 1");
	    d1.addActionListener(this);
	    d1.setOpaque(true);
	    add(d1);
	    
	    d2 = new JButton();
	    d2.setBounds(1600, 750, 100, 100);
	    d2.setText("dead 2");
	    d2.addActionListener(this);
	    d2.setOpaque(true);
	    add(d2);
	    

     //create an payload1 area
	 jppayload1 = new JPanel();
	 jppayload1.setLayout(new GridLayout(6,0));
	 jppayload1.setBounds(20,70,100,500);
	 jppayload1.setOpaque(true);
	// jppayload1.add(listener);
	 JLabel  pl1 =new JLabel("player1");
	 pl1.setBounds(0, 0, 100, 50);
	 pl1.setBackground(Color.ORANGE);
	 pl1.setForeground(Color.ORANGE);
	 pl1.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 23));
	 pl1.setToolTipText("PayLoadPos For player1");
	 add(pl1);
	 add(jppayload1);
	 
	 //create a payload2 area
	 jppayload2 = new JPanel();
	 jppayload2.setLayout(new GridLayout(6,0));
	 jppayload2.setBounds(1750,70,100,500);
	 jppayload2.setOpaque(true);
	// jppayload1.add(listener);
	 JLabel  pl2 =new JLabel("player2");
	 pl2.setBounds(1750, 0, 100, 50);
	 pl2.setBackground(Color.BLACK);
	 pl2.setForeground(Color.YELLOW);
	 pl2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 23));
	 pl2.setToolTipText("PayLoadPos For player2");
	 add(pl2);
	 add(jppayload2);
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
     //initialize the payload variables 
	 payloadposp1 = new JLabel[6];
	 payloadposp2 = new JLabel[6];
	//initialize the JLabels
		 for(int i=0;i<6;i++) {
		 		JLabel p = new JLabel();
		 		JLabel k =new JLabel();
		 		p.setSize(100,100);
		 		k.setSize(100,100);
		 		p.setOpaque(true);
		 		k.setOpaque(true);
		 		payloadposp2[i] =p;
		 		payloadposp1[i] =k;
		}
	
		 
	 //create an info area
	 info = new JTextArea();
	 info.setPreferredSize(new Dimension(400, getWidth()));
	 info.setBounds(100, 850, 1250,100);
	 info.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
	 info.setEditable(false);
	 info.setBackground(Color.WHITE);
	 info.setOpaque(true);
	 add(info);
	 
	// GridLayout f101 =new GridLayout(0,3); 
	 arows =  new JPanel();
	 arows.setLayout(new GridLayout(3,3));
	 arows.setBounds(1400,350, 300, 300);
	 this.add(arows);
	 //set the direction buttons 
		
	 
	  
	 up = new JButton("up");
	 down = new JButton("down");
	 right = new JButton("right");
	 left = new JButton("left");
	 upright = new JButton("upright");
	 upleft = new JButton("upleft");
	 downright = new JButton("downright");
	 downleft = new JButton("downleft");
	  powerUse = new JButton("power");
	 //add buttons to arow panel
	 arows.add(upleft);
	 arows.add(up);
	 arows.add(upright);
	 arows.add(left);
	 arows.add(powerUse);
	 arows.add(right);
	 arows.add(downleft);
	 arows.add(down);
	 arows.add(downright);
	 
	 //adding mouse listeners
		up.addActionListener(this);
		 down.addActionListener(this);
		 right.addActionListener(this);
		 left.addActionListener(this);
		 upright.addActionListener(this);
		 upleft.addActionListener(this);
		 downright.addActionListener(this);
		 downleft.addActionListener(this);	 
		 powerUse.addActionListener(this);
	 // put images for arowse buttons
		
	  
	 
	 //initialize the game
	 game = new Game(player1,player2);
	
	// start game
	game = new Game(player1, player2);
	// initialize the current player	  
	info.setText("The current player is "+game.getCurrentPlayer().getName());
	// set visible	
	this.setVisible(true);
	//maximize the screen
	setExtendedState(JFrame.MAXIMIZED_BOTH); 
	//initialize the new board
	allbuttons = new JButton[this.game.getBoardHeight()][this.game.getBoardWidth()];
	intializeeScreen();
	SwingUtilities.updateComponentTreeUI(this);
	}
//updatelist helper



public void intializeeScreen() {
	//store the 2D array in 1D array
		for(int i=0;i<game.getBoardHeight();i++) {
			for(int j=0;j<game.getBoardWidth();j++) {
				//Icon b = new ImageIcon(getClass().getResource("s"));
			    JButton jb =new JButton();
			    jb.addActionListener(this);
			    Piece pn = game.getCellAt(i,j).getPiece();
if(pn!=null&&pn.getOwner()==player1) {	
		if(pn instanceof SideKick  ) {
	jb.setText(pn.toString());
	jb.setToolTipText("SideKick of player1");
	jb.setBounds(50,50, 100, 100);
	jb.setIcon(new ImageIcon(getClass().getResource("Side.jpg")));
						
						
					}
					
						
if(pn instanceof Super) {
 jb.setText(pn.toString());
 jb.setToolTipText("Super of player1");
 jb.setBounds(50,50, 100, 100);
 jb.setIcon(new ImageIcon(getClass().getResource("HULKKK.jpg")));
}
						
if(pn instanceof Medic){
	jb.setText(pn.toString());
    jb.setToolTipText("Medic of player1");
	jb.setBounds(50,50, 100, 100);
	jb.setIcon(new ImageIcon(getClass().getResource("M3.jpg")));
			}
						
if(pn instanceof Ranged){
	jb.setText(pn.toString());
	jb.setToolTipText("Ranged of player1");
	jb.setBounds(50,50, 100, 100);
	jb.setIcon(new ImageIcon(getClass().getResource("R5.jpg")));
	}
								
if(pn instanceof Speedster){ 
 jb.setText(pn.toString());
 jb.setToolTipText("Speedster of player1");
 jb.setBounds(50,50, 100, 100);
 jb.setIcon(new ImageIcon(getClass().getResource("S3.jpg")));
									}
									
 if(pn instanceof Armored){
		jb.setText(pn.toString());
	    jb.setToolTipText("Armored of player1");
		jb.setBounds(50,50, 100, 100);
		jb.setIcon(new ImageIcon(getClass().getResource("STRONG7.jpg")));
						}
										
 if(pn instanceof Tech){
		jb.setText(pn.toString());
		jb.setToolTipText("Tech of player1");
		jb.setBounds(50,50, 100, 100);
		jb.setIcon(new ImageIcon(getClass().getResource("T7.jpg")));							
				}
									}
							
						
					
else if(pn!=null&&pn.getOwner()==player2) {
	if(pn instanceof SideKick  ) {
		jb.setText(pn.toString());
		jb.setToolTipText("Sidekick of player2");
		jb.setBounds(50,50, 100, 100);
	    jb.setIcon(new ImageIcon(getClass().getResource("SIDE2.jpg")));
								
							
						}
						
if(pn instanceof Super) 
		{
	jb.setText(pn.toString());
	jb.setToolTipText("Super of player2");
    jb.setIcon(new ImageIcon(getClass().getResource("HULK6.jpg")));
									
								}
							
 if(pn instanceof Medic)
	{
	jb.setText(pn.toString());
	jb.setToolTipText("Medic of player2");
	jb.setBounds(50,50, 100, 100);
	jb.setIcon(new ImageIcon(getClass().getResource("M7.jpg")));
										
	}
  if(pn instanceof Ranged)
	{
	jb.setText(pn.toString());
	jb.setToolTipText("Ranged of player2");
	jb.setBounds(50,50, 100, 100);
	jb.setIcon(new ImageIcon(getClass().getResource("R10.jpg")));
    }
									
	if(pn instanceof Speedster) 
      {
		jb.setText(pn.toString());
		jb.setToolTipText("Speedster of player2");
	    jb.setBounds(50,50, 100, 100);
		jb.setIcon(new ImageIcon(getClass().getResource("FLASH6.jpg")));
	}
										
	if(pn instanceof Armored)
		{
		jb.setText(pn.toString());
        jb.setToolTipText("Armored of player2");
		jb.setBounds(50,50, 100, 100);
	    jb.setIcon(new ImageIcon(getClass().getResource("SHIELD6.jpg")));
	   }	
											
												
	if(pn instanceof Tech){
			jb.setText(pn.toString());
			jb.setToolTipText("Tech of player2");
			jb.setBounds(50,50, 100, 100);
			jb.setIcon(new ImageIcon(getClass().getResource("T9.jpg")));
													
											}
										}
				else {
					jb.setText(null);
					jb.setToolTipText("No piece");
					
				}
				allbuttons[i][j]=jb;
				jb.setBackground(Color.WHITE);
				jpboard.add(jb);
			}
							}
						
							
							
						
		
}
				
				
		

//updating the board as >> "bekoo" said
 public void updateScreen () {
	 jpboard.removeAll();
	 this.intializeeScreen();
	 SwingUtilities.updateComponentTreeUI(this);
	 
 }

 public void helper(Player p,JPanel l) {
			for(int i=0;i<p.getDeadCharacters().size();i++) {
				if( p.getDeadCharacters().get(i)!=null) {
				Piece p1 = p.getDeadCharacters().get(i);
				JButton j =new JButton(p1.getName());
				j.setSize(500, 500);
				j.addActionListener(this);
				if(l==listd1) {
					jbd1[i]=j;
				}
				else {
					jbd2[i]=j;
				}
				
				l.add(j);
				}
			}
			
		
 }
public void updated(Player p,JPanel l) {
	l.removeAll();
	helper(p,l);
    SwingUtilities.updateComponentTreeUI(this);
}
 
public void updateinfo(JButton jb,int x,int y) {
	Piece curr = this.game.getCellAt(x,y).getPiece();
	String currinfo = "";
	
	if(curr!=null) {
		if(curr.getOwner()==game.getCurrentPlayer()) {
	if(curr.getOwner().getName()==player1.getName()) {
		currinfo+="owner is player1 ";
	}
	else {
		currinfo+="owner is player2 ";
	}
    if(!(curr.getOwner().getDeadCharacters().isEmpty())) {
    
    		currinfo+="dead characters are "+curr.getOwner().getDeadCharacters().toString();
    	
    }
    else{
    	currinfo+=" no dead characters";
    }
	String s =curr.getName();
	if(curr instanceof Tech){
		s = "T";
		currinfo+= "\n Tech ";
	}
	if(curr instanceof Super){
		s = "P";
		currinfo+= "\n Super ";
	}
	if(curr instanceof Ranged){
		s = "R";
		currinfo+= "\n Ranged ";
	}
	if(curr instanceof Armored){
		s = "A";
		currinfo+= "\n Armored ";
	}
	if(curr instanceof SideKick){
		s = "K";
		currinfo+= "\n SideKick ";
	}
	if(curr instanceof Medic){
		s = "M";
		currinfo+= "\n Medic ";
	}
	if(curr instanceof Speedster){
		s = "S";
		currinfo+= "\n Speedster ";
	}
	switch(s) {
	case "K":currinfo+=" passive power ";break;
	case "S":currinfo+=" passive power";break;
	case "P":Super sp = (Super)curr;
		currinfo+="\n  power use state is "+sp.isPowerUsed();break;
	case "M":Medic md =(Medic)curr;
		currinfo+="\n  power use state is "+md.isPowerUsed();break;
	case "T":Tech tc =(Tech)curr;
		currinfo+="\n power use state is "+tc.isPowerUsed();break;
	case "R":Ranged rd = (Ranged)curr;
		currinfo+="\n the power use state is "+rd.isPowerUsed();break;
	case "A":Armored a =(Armored)curr;
		if(a.isArmorUp()) {
		currinfo+="\n  armor state  up";
	}
	else {
		currinfo+="\n armor down";
	}break;
	default : break;	
	}
		}
	info.setText(currinfo);
	
	}
	else {
		currinfo+="Empty Piece ";
		info.setText(currinfo);
		
	}
	
	
}

public void actionPerformed(ActionEvent e) {
	// get the JButton that was clicked
	JButton jb = (JButton) e.getSource();
	//end game if 
	if(player1.getPayloadPos()==6||player2.getPayloadPos()==6) {
	    JOptionPane.showMessageDialog(this, "Game Over");
		System.exit(0);
        SwingUtilities.updateComponentTreeUI(this);   

	}
	//save the current player,payloadpos
	if(jb==d1) {
		updated(player1, listd1);
	    SwingUtilities.updateComponentTreeUI(this);
		popd1.add(listd1,BorderLayout.CENTER);
		popd1.setTitle("dead characters for player1");
		popd1.setVisible(true);
		
	}
	if(jb==d2) {
		updated(player2, listd2);
	    SwingUtilities.updateComponentTreeUI(this);
		popd2.add(listd2);
		popd2.setTitle("dead characters for player2");
		popd2.setVisible(true);
		
	}
	cur = this.game.getCurrentPlayer(); 
	 
	int x = cur.getPayloadPos();
		//get the corresponding piece & updates info panel
		if(counter == 0) {	
	    for(int i=0;i<game.getBoardHeight();i++) {
					for(int j=0;j<game.getBoardWidth();j++) {
					    if( allbuttons[i][j] == jb ) { 
					      this.updateinfo(jb,i,j);
					       curp =game.getCellAt(i, j).getPiece();
					       return;
					    }
					  }
					}
		}
		if(pw&&counter==1){
			if(cur.equals(player1)) {
			 for(int i=0;i<jbd1.length;i++) {
					if(jbd1[i]==jb) {
						 secondp=game.getPlayer1().getDeadCharacters().get(i);
						 counter+=1;
					}
				}
			}
			if(cur.equals(player2)) {
				 for(int i=0;i<jbd2.length;i++) {
						if(jbd2[i]==jb) {
							 secondp=game.getPlayer2().getDeadCharacters().get(i);
							 counter+=1;
						}
					}
				}
			 for(int i=0;i<game.getBoardHeight();i++) {
					for(int j=0;j<game.getBoardWidth();j++) {
					    if( allbuttons[i][j] == jb ) { 
					      this.updateinfo(jb,i,j);
					       secondp =game.getCellAt(i, j).getPiece();
					       counter+=1;
					       return;
					    }
					  }
					}
		}
		if(pw&&counter==2) {
			if(curp instanceof Tech) {
			 for(int i=0;i<game.getBoardHeight();i++) {
					for(int j=0;j<game.getBoardWidth();j++) {
					    if( allbuttons[i][j] == jb ) { 
					      this.updateinfo(jb,i,j);
					      secondp2 = game.getCellAt(i, j).getPiece();
					      newPos=new Point(i,j);
					      
					      if(d==null) {
					      if(secondp==secondp2) {
					      powerUseAction(curp,null,secondp,null);
							curp =null;
							secondp=null;
							newPos =null;
							counter=0;
							d=null;
							pw=false;
							return;
					      }
					      else {
					    	  powerUseAction(curp,null,secondp,newPos);  
								curp =null;
								secondp=null;
								newPos =null;
								counter=0;
								d=null;
								pw=false;
								return;
					      }
					      }
					    }
		}
			 }
			 }
			
					}
		//move after clicking
	
		
		
			if(curp!=null) {
			
				
				
				
			if(jb==up) {
				
				try {
					if(pw) {
						d=  Direction.UP;
						powerUseAction(curp,d,secondp,null);
						updatepayloadpos(cur,x);
						checkendgame(cur);
						curp =null;
						secondp=null;
						newPos =null;
						counter=0;
						d=null;
						pw=false;
						return;
					}
					
					curp.move(Direction.UP);
					if(cur.equals(player1)) {
						updated(cur, listd1);
					}
					else {
						updated(cur, listd2);
					}
					updatepayloadpos(cur,x);
					checkendgame(cur);
					updateScreen();
					
				} catch (UnallowedMovementException e1) {
					 error.setText("Unallowed Movment");
						popUp.add(error);
						    popUp.setVisible(true);
				}
				catch ( OccupiedCellException e2) {
					 error.setText("Occupied Cell");
						popUp.add(error);
						    popUp.setVisible(true);
				}
				catch(WrongTurnException e3) {
					 error.setText("Wrong Turn");
						popUp.add(error);
						    popUp.setVisible(true);
				}
				}
			
			
		if(jb==down) {
			try {
				if(pw) {
					d=  Direction.DOWN;
					powerUseAction(curp,d,secondp,newPos);
					updatepayloadpos(cur,x);
					checkendgame(cur);
					curp =null;
					secondp=null;
					newPos =null;
					counter=0;
					d=null;
					pw=false;
					return;
				}
				
				curp.move(Direction.DOWN);
				if(cur.equals(player1)) {
					updated(cur, listd1);
				}
				else {
					updated(cur, listd2);
				}
				updatepayloadpos(cur,x);
				checkendgame(cur);
				updateScreen();
				
			}catch (UnallowedMovementException e1) {
				 error.setText("Unallowed Movment");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch ( OccupiedCellException e2) {
				 error.setText("Occupied Cell");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch(WrongTurnException e3) {
				 error.setText("Wrong Turn");
					popUp.add(error);
					    popUp.setVisible(true);
			}
		}
		if(jb==right) {
			try {
				if(pw) {
					d=  Direction.RIGHT;
					powerUseAction(curp,d,secondp,newPos);
					
					updatepayloadpos(cur,x);
					checkendgame(cur);
					curp =null;
					secondp=null;
					newPos =null;
					counter=0;
					d=null;
					pw=false;
					return;
				}
				
				curp.move(Direction.RIGHT);
				
				updatepayloadpos(cur,x);
				checkendgame(cur);
				updateScreen();
				
			}catch (UnallowedMovementException e1) {
				 error.setText("Unallowed Movment");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch ( OccupiedCellException e2) {
				 error.setText("Occupied Cell");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch(WrongTurnException e3) {
				 error.setText("Wrong Turn");
					popUp.add(error);
					    popUp.setVisible(true);
			}
		}
		
		
		if(jb==left) {
			try {
				if(pw) {
					d=  Direction.LEFT;
					powerUseAction(curp,d,secondp,newPos);
					
					updatepayloadpos(cur,x);
					checkendgame(cur);
					curp =null;
					secondp=null;
					newPos =null;
					counter=0;
					d=null;
					pw=false;
					return;
				}
				
				curp.move(Direction.LEFT);
				
				updatepayloadpos(cur,x);
				checkendgame(cur);
				updateScreen();
				
			}catch (UnallowedMovementException e1) {
				 error.setText("Unallowed Movment");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch ( OccupiedCellException e2) {
				 error.setText("Occupied Cell");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch(WrongTurnException e3) {
				 error.setText("Wrong Turn");
					popUp.add(error);
					    popUp.setVisible(true);
			}
		}
		if(jb==downright) {
			try {
				if(pw) {
					d=  Direction.DOWNRIGHT;
					powerUseAction(curp,d,secondp,newPos);
					
					updatepayloadpos(cur,x);
					checkendgame(cur);
					curp =null;
					secondp=null;
					newPos =null;
					counter=0;
					d=null;
					pw=false;
					return;
				}
				
				curp.move(Direction.DOWNRIGHT);
				
				updatepayloadpos(cur,x);
				checkendgame(cur);
				updateScreen();
				
			} catch (UnallowedMovementException e1) {
				 error.setText("Unallowed Movment");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch ( OccupiedCellException e2) {
				 error.setText("Occupied Cell");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch(WrongTurnException e3) {
				 error.setText("Wrong Turn");
					popUp.add(error);
					    popUp.setVisible(true);
			}
		}
		if(jb==downleft) {
			try {
				if(pw) {
					d=  Direction.DOWNLEFT;
					powerUseAction(curp,d,secondp,newPos);
					
					updatepayloadpos(cur,x);
					checkendgame(cur);
					curp =null;
					secondp=null;
					newPos =null;
					counter=0;
					d=null;
					pw=false;
					return;
				}
				
				curp.move(Direction.DOWNLEFT);
				
				updatepayloadpos(cur,x);
				checkendgame(cur);
				updateScreen();
				
			}catch (UnallowedMovementException e1) {
				 error.setText("Unallowed Movment");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch ( OccupiedCellException e2) {
				 error.setText("Occupied Cell");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch(WrongTurnException e3) {
				 error.setText("Wrong Turn");
					popUp.add(error);
					    popUp.setVisible(true);
			}
		}
		
		if(jb==upright) {
			try {
				if(pw) {
					d=  Direction.UPRIGHT;
					powerUseAction(curp,d,secondp,newPos);
				
					updatepayloadpos(cur,x);
					checkendgame(cur);
					curp =null;
					secondp=null;
					newPos =null;
					counter=0;
					d=null;
					pw=false;
					return;
				}
				
				curp.move(Direction.UPRIGHT);
				
				updatepayloadpos(cur,x);
				checkendgame(cur);
				updateScreen();
				
			} catch (UnallowedMovementException e1) {
				 error.setText("Unallowed Movment");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch ( OccupiedCellException e2) {
				 error.setText("Occupied Cell");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch(WrongTurnException e3) {
				 error.setText("Wrong Turn");
					popUp.add(error);
					    popUp.setVisible(true);
			}
		}
		if(jb==upleft) {
			try {
				if(pw) {
					d=  Direction.UPLEFT;
					powerUseAction(curp,d,secondp,newPos);
					updatepayloadpos(cur,x);
					checkendgame(cur);
					curp =null;
					secondp=null;
					newPos =null;
					counter=0;
					d=null;
					pw=false;
					return;
				}
				
				curp.move( Direction.UPLEFT);
				updatepayloadpos(cur,x);
				checkendgame(cur);
				updateScreen();
				
			}catch (UnallowedMovementException e1) {
				 error.setText("Unallowed Movment");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch ( OccupiedCellException e2) {
				 error.setText("Occupied Cell");
					popUp.add(error);
					    popUp.setVisible(true);
			}
			catch(WrongTurnException e3) {
				 error.setText("Wrong Turn");
					popUp.add(error);
					    popUp.setVisible(true);
			}
		}
						}
		
			
			if(jb==powerUse) {	
				
				pw = true;
                counter=1;
				
				
			
				}
			}




	

public void powerUseAction(Piece attacker,Direction d,Piece target,Point newPos) {
	
	
	if(attacker instanceof Super) 
	    powerUseSuper(attacker, d, target, newPos);
	    
	else {
		if(attacker instanceof Medic)
			powerUseMedic(attacker, d,target , newPos);
		else {
			if(attacker instanceof Ranged)
				powerUseRanged(attacker, d, target, newPos);
			else {
				if(attacker instanceof Speedster) {
					 error.setText("No Power to use");
						 popUp.add(error);
						    popUp.setVisible(true);
					 }
				else {
					if(attacker instanceof Armored){
						 error.setText("No Power to use");
						popUp.add(error);
						    popUp.setVisible(true);
						
						}
					else {
						if(attacker instanceof Tech)
							powerUseTech(attacker, d, target, newPos);
						
						else {
							 error.setText("No Power to use");
								popUp.add(error);
								    popUp.setVisible(true);
								
						}
					}
				}
		}
	}
}

}




public void powerUseTech(Piece p2,Direction d,Piece target,Point newPos)  {
	Tech x =(Tech)p2;
	try {
		x.usePower(d, target, newPos);
		updateScreen();
	} catch (InvalidPowerUseException e) {
		 error.setText("Invalid Power Use");
			popUp.add(error);
			    popUp.setVisible(true);
		
	} catch (WrongTurnException e) {
		 error.setText("Wrong Turn");
			popUp.add(error);
			    popUp.setVisible(true);
	}
	
}


public void powerUseRanged(Piece p2,Direction d,Piece target,Point newPos) {
	Ranged x = (Ranged)p2;
	try {
		x.usePower(d, target, newPos);
		updateScreen();
	} catch (InvalidPowerUseException e) {
		 error.setText("Invalid Power Use");
			popUp.add(error);
			    popUp.setVisible(true);
	} catch (WrongTurnException e) {
		 error.setText("Wrong Turn");
			popUp.add(error);
			    popUp.setVisible(true);
	}
	
}


public void powerUseMedic(Piece p2,Direction d,Piece target,Point newPos) {
	Medic x = (Medic)p2;
	
	try {
		x.usePower(d, target, newPos);
		updateScreen();
	} catch (InvalidPowerUseException e) {
		error.setText("Invalid Power Use");
		popUp.add(error);
		    popUp.setVisible(true);
	} catch (WrongTurnException e) {
		 error.setText("Wrong Turn");
			popUp.add(error);
			    popUp.setVisible(true);
	}
	
}


public void powerUseSuper(Piece p2,Direction d,Piece target,Point newPos) {
	Super x=(Super)p2;
	try {
		x.usePower(d, target, newPos);
		updateScreen();
	} catch (InvalidPowerUseException e) {
		error.setText("Invalid Power Use");
		popUp.add(error);
		    popUp.setVisible(true);
	} catch (WrongTurnException e) {
		error.setText("Wrong Turn");
		popUp.add(error);
		    popUp.setVisible(true);
	}
	
	
}





public void updatebanel(JLabel[] x, Player curr) {
    if(curr == player1) {
    	jppayload1.removeAll();	
	for(int i=0;i<6;i++) {
		 		jppayload1.add(x[i]);	 
	}
    }else {
    	jppayload2.removeAll();
		for(int i=0;i<6;i++) {
	 		jppayload2.add(x[i]);	
		
	}
}
}
public void updatepayloadpos(Player curr,int x) {
    if(curr==player1) {   
	if((curr.getPayloadPos()>x)) {
		while(curr.getPayloadPos()-x>0) {
		int y = 1;
		while(payloadposp1[ payloadposp1.length -y].getBackground()==Color.green&&y<=payloadposp1.length) {
		  y++;
		}
		 JLabel component = payloadposp1[ payloadposp1.length -y];
		component.setBackground(Color.green);
		 component.setOpaque(true);
		 payloadposp1[ payloadposp1.length -y]=component;
		 updatebanel(payloadposp1,curr);
		x++;
		}
		
        }
	}
    
    if(curr==player2) {
    	if(!(curr.getPayloadPos()==x)) {
    		while(curr.getPayloadPos()-x>0) {
    		int y = 1;
    		while(payloadposp2[ payloadposp2.length -y].getBackground()==Color.green&&y<=payloadposp1.length) {
    		  y++;
    		}
    		 JLabel component = payloadposp2[ payloadposp2.length -y];
    		component.setBackground(Color.green);
    		 component.setOpaque(true);
    		 payloadposp2[ payloadposp2.length -y]=component;
    		 updatebanel(payloadposp2,curr);
    		 x++;	
    		}
    
    }
    }
}
public void checkendgame(Player curr) {
	boolean flag = false;
	if(curr.getPayloadPos()==6) {
		winner.removeAll();
		winner.setBackground(Color.ORANGE);
		JLabel w = new JLabel("THE WINNER IS " +curr.getName());
		w.setBounds(600, 0, 600, 50);
		w.setForeground(Color.black);
		w.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		winner.add(w);
		add(winner);
		flag = true;
		 
	}
	
	if(flag) {
		JOptionPane.showMessageDialog(this, "Game Over");
		
	}
	
}
}
