/**
 * 9/15/2014
 * Updated for automated solution by:
 * @author Alexander Lemkin
 * COP 3503C
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
 
public class EightQueens  extends Applet implements MouseListener, MouseMotionListener, Runnable, ActionListener
{
 
        
       
        private static final long serialVersionUID = 1L;
 
        static final int NUMROWS = 8;
        static final int NUMCOLS = 8;
        static final int SQUAREWIDTH = 50;
        static final int SQUAREHEIGHT = 50;
        static final int BOARDLEFT = 50;
        static final int BOARDTOP = 50;
       
        Thread _thread;
        Button _solveButton = new Button("Solve it... Please?");
        Button _showSolution = new Button("Just show me the solution...");
        Button _stop = new Button("STOP!!!");
        Button _clear = new Button("Clear The Board");
        boolean _isSolving = false;
        boolean _wantSolved = false;
        String _guiUpdate = "";
        boolean _stopCommand = false;
       
        int m_nBoard[][] = new int[8][8];
        void ClearBoard()
        {
                for( int nRow=0; nRow<NUMROWS; nRow++ )
                {
                        for( int nCol=0; nCol<NUMCOLS; nCol++ )
                        {
                                m_nBoard[nRow][nCol] = 0;
                        }
                }
        }
 
        int m_nMoves[] = new int[8];
        int m_nMoveIndex = 0;
 
        boolean m_bClash = false;
       
        Image m_imgQueen;
        MediaTracker tracker = new MediaTracker(this);
       
        public void init()
        {
        setSize(1020,700);  
        addMouseListener( this );
        addMouseMotionListener( this );        
        add( _solveButton );
        add ( _showSolution );
        add ( _stop );
        add(_clear);
        _solveButton.addActionListener(this);
        _solveButton.setActionCommand("solve");
        _showSolution.addActionListener(this);
        _showSolution.setActionCommand("show");
        _stop.addActionListener(this);
        _stop.setActionCommand("stop");
        _stop.setEnabled(false);
        _clear.addActionListener(this);
        _clear.setActionCommand("clear");
        _clear.setEnabled(false);
        
       
        try
        {
                m_imgQueen = getImage(getCodeBase()/*getDocumentBase()*/,"queen.png");
                tracker.addImage(m_imgQueen, 1);
                tracker.waitForAll();          
        }
        catch (Exception e)
        {
        }
 
        _thread = new Thread(this);
        _thread.start();
        }
       
        void DrawSquares( Graphics canvas )
        {
                canvas.setColor(Color.BLACK);
                for( int nRow=0; nRow<NUMROWS; nRow++ )
                {
                        for( int nCol=0; nCol<NUMCOLS; nCol++ )
                        {
                                canvas.drawRect( BOARDLEFT + nCol * SQUAREWIDTH,
                                        BOARDTOP + nRow * SQUAREHEIGHT, SQUAREWIDTH, SQUAREHEIGHT );
                               
                                if( m_nBoard[nRow][nCol] != 0 )
                                {
                                        canvas.drawImage( m_imgQueen,
                                                BOARDLEFT + nCol * SQUAREWIDTH + 8, BOARDTOP + nRow * SQUAREHEIGHT + 6, null );
                                }
                        }
                }
        }
       
        void CheckColumns( Graphics canvas )
        {
                // Check all columns
                for(  int nCol=0; nCol<NUMCOLS; nCol++ )
                {
                        int nColCount = 0;
                        for( int nRow=0; nRow<NUMROWS; nRow++ )
                        {
                                if( m_nBoard[nRow][nCol] != 0 )
                                {
                                        nColCount++;
                                }
                        }
                        if( nColCount > 1 )
                        {
                                canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                        BOARDTOP + ( SQUAREHEIGHT / 2 ),       
                                        BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                        BOARDTOP + SQUAREHEIGHT * 7 + ( SQUAREHEIGHT / 2 ) );
                               
                                m_bClash = true;
                        }
                }
        }
 
        void CheckRows( Graphics canvas )
        {
                for(  int nRow=0; nRow<NUMROWS; nRow++ )
                {
                        int nRowCount = 0;
                        for( int nCol=0; nCol<NUMCOLS; nCol++ )
                        {
                                if( m_nBoard[nRow][nCol] != 0 )
                                {
                                        nRowCount++;
                                }
                        }
                        if( nRowCount > 1 )
                        {
                                canvas.drawLine( BOARDLEFT + ( SQUAREWIDTH / 2 ),
                                        BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ), 
                                        BOARDLEFT + 7 * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                        BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
                               
                                m_bClash = true;
                        }
                }
        }
       
        void CheckDiagonal1( Graphics canvas )
        {
                // Check diagonal 1
       
                for( int nRow=NUMROWS-1; nRow>=0; nRow-- )
                {
                        int nCol = 0;
                       
                        int nThisRow = nRow;
                        int nThisCol = nCol;
 
                        int nColCount = 0;
                       
                        while( nThisCol < NUMCOLS &&
                                nThisRow < NUMROWS )
                        {
                                if( m_nBoard[nThisRow][nThisCol] != 0 )
                                {
                                        nColCount++;
                                }
                                nThisCol++;
                                nThisRow++;
                        }
                       
                        if( nColCount > 1 )
                        {
                                canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                                BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ), 
                                                BOARDLEFT + ( nThisCol - 1 ) * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                                BOARDTOP + ( nThisRow - 1 ) * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
                               
                                m_bClash = true;
                        }
                }
 
                for( int nCol=1; nCol<NUMCOLS; nCol++)
                {
                        int nRow = 0;
               
                        int nThisRow = nRow;
                        int nThisCol = nCol;
 
                        int nColCount = 0;
                       
                        while( nThisCol < NUMCOLS &&
                                nThisRow < NUMROWS )
                        {
                                if( m_nBoard[nThisRow][nThisCol] != 0 )
                                {
                                        nColCount++;
                                }
                                nThisCol++;
                                nThisRow++;
                        }
                       
                        if( nColCount > 1 )
                        {
                                canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                                BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ), 
                                                BOARDLEFT + ( nThisCol - 1 ) * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                                BOARDTOP + ( nThisRow - 1 ) * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
                               
                                m_bClash = true;
                        }
                       
                }
               
        }
       
        void CheckDiagonal2( Graphics canvas )
        {
                // Check diagonal 2
               
                for( int nRow=NUMROWS-1; nRow>=0; nRow-- )
                {
                        int nCol = NUMCOLS - 1;
                       
                        int nThisRow = nRow;
                        int nThisCol = nCol;
 
                        int nColCount = 0;
                       
                        while( nThisCol >= 0 &&
                                nThisRow < NUMROWS )
                        {
                                if( m_nBoard[nThisRow][nThisCol] != 0 )
                                {
                                        nColCount++;
                                }
                                nThisCol--;
                                nThisRow++;
                        }
                       
                        if( nColCount > 1 )
                        {
                                canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                                BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ), 
                                                BOARDLEFT + ( nThisCol + 1 ) * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                                BOARDTOP + ( nThisRow - 1 ) * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
                               
                                m_bClash = true;
                        }
                }
 
                for( int nCol=NUMCOLS-1; nCol>=0; nCol--)
                {
                        int nRow = 0;
               
                        int nThisRow = nRow;
                        int nThisCol = nCol;
 
                        int nColCount = 0;
                       
                        while( nThisCol >= 0 &&
                                nThisRow < NUMROWS )
                        {
                                if( m_nBoard[nThisRow][nThisCol] != 0 )
                                {
                                        nColCount++;
                                }
                                nThisCol--;
                                nThisRow++;
                        }
                       
                        if( nColCount > 1 )
                        {
                                canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                                BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ), 
                                                BOARDLEFT + ( nThisCol + 1 ) * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
                                                BOARDTOP + ( nThisRow - 1 ) * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
                               
                                m_bClash = true;
                        }
                       
                }
        }
       
        public void paint (Graphics canvas)
        {
 
                m_bClash = false;
               
                DrawSquares( canvas );
               
                canvas.setColor(Color.RED);
               
                CheckColumns( canvas );
                CheckRows( canvas );
                CheckDiagonal1( canvas );
                CheckDiagonal2( canvas );
               
                canvas.setColor(Color.BLUE);
                canvas.drawString(_guiUpdate, BOARDLEFT, BOARDTOP + SQUAREHEIGHT * 8 + 20);
        }
       
        @Override
        public void mouseClicked(MouseEvent e)
        {
        }
       
        @Override
        public void mouseDragged(MouseEvent arg0)
        {
        }
 
        @Override
        public void mouseMoved(MouseEvent arg0)
        {
        }
 
        @Override
        public void mouseEntered(MouseEvent arg0)
        {
        }
 
        @Override
        public void mouseExited(MouseEvent arg0)
        {
        }
 
        @Override
        public void mousePressed(MouseEvent arg0)
        {
                int nCol = ( arg0.getX() - BOARDLEFT ) / SQUAREWIDTH;
                int nRow = ( arg0.getY() - BOARDTOP ) / SQUAREHEIGHT;
                if( nCol >= 0 &&
                        nRow >= 0 &&
                        nCol < NUMCOLS &&
                        nRow < NUMROWS )
                {
                        m_nBoard[nRow][nCol] ^= 1;
                        repaint();
                }
        }
 
        @Override
        public void mouseReleased(MouseEvent arg0)
        {
        }
 
        void showSolution() {
        	_guiUpdate = "Just showing you the solution mate.";
        	_wantSolved = false;
        	int yArray[] = {7, 3, 0, 2, 5, 1, 6, 4};
        	try {
        		for(int i = 0; i< 8 ; i++) {
        			m_nBoard[i][yArray[i]] = 1;
        			repaint();
        		}
        	} catch (Exception e) {}
        }
        boolean solveRec( int col ){             
                if( col >= NUMCOLS ){ return( true ); }
               
                for( int row=0; row<NUMROWS; row++ ){
                	_guiUpdate = "Skynet is cheating at chess... (placing piece)";
                        m_nBoard[row][col] = 1;
                        repaint();
                       if (_stopCommand) {
                    	   _guiUpdate = "OKAY, OKAY. I'm stopping... jeez.";
                    	   break;
                       }
                        try{ Thread.sleep(200); }
                        catch (InterruptedException e){}
                       
                        if( m_bClash ){
                        	_guiUpdate = "Whoopsies, that has a clash";
                                m_nBoard[row][col] = 0;
                                repaint();
                                try{ Thread.sleep(200); }
                                catch (InterruptedException e){}
                        	}
                        else{
                                if( !solveRec( col + 1 ) ){
                                	_guiUpdate = "Backtracking darling, hang on";
                                        m_nBoard[row][col] = 0;
                                        repaint();
                                        try{ Thread.sleep(200); }
                                        catch (InterruptedException e){}
                                }
                                else{return( true );}
                        }
                }               
                return( false );
        }
       
        @Override
        public void run(){
            while(true) {
                    if( _isSolving ) {
                            solveRec(0);
                    }
                    _isSolving = false;
                    _solveButton.setEnabled(true);
                   
                    if (_wantSolved ) {
                    	showSolution();
                    }
                    try { Thread.sleep(50); }
                    catch (InterruptedException e) { e.printStackTrace(); }
            }             
        }
 
        @Override
        public void actionPerformed(ActionEvent action)
        {
             ClearBoard();
             _stop.setBackground(Color.LIGHT_GRAY);
             if(action.getActionCommand() == "clear") {
            	 _guiUpdate = "I've cleared the board, just for you";
            	 _clear.setEnabled(false);
            	 _stop.setEnabled(false);
            	 _solveButton.setEnabled(true);
            	 _showSolution.setEnabled(true);
            	 repaint();
             }
             else if (action.getActionCommand() == "stop") {
            	 _clear.setEnabled(true);
            	 _stop.setEnabled(false);
            	 _solveButton.setEnabled(true);
            	 _showSolution.setEnabled(true);
            	 _stopCommand = true;
             }
             else if (action.getActionCommand() == "show") {
            	 _clear.setEnabled(true);
            	 _stopCommand = false;
            	 _solveButton.setEnabled(true);
            	 _showSolution.setEnabled(false);
            	 repaint();
            	 _wantSolved = true;
            	 
         	 }
             else {
            _stopCommand = false;
            _clear.setEnabled(false);
            _stop.setEnabled(true);
            _stop.setBackground(Color.red);
            _showSolution.setEnabled(false);
             _solveButton.setEnabled(false);
                m_nMoves = new int[8];
                m_nMoveIndex = 0;
                m_bClash = false;
               
                repaint();
                _isSolving = true;
             }
        }
       
}