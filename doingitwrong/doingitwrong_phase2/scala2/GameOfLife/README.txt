###########################################################
# README
# Created: 1/11/2011
###########################################################

    About: BottomUpCellLifeGame
    
    Notes:
    
    - The system is slightly interesting.  You can monitor the balance with the number of live cells. 
      You may notice a shift in mutations as cells grow away from the center.
    
    -  With only a few mutations, the color of cells tend to shift in color over time.
    
    - It takes many iterations for emergent behavior to emerge.
    
    - Code wise not really that interesting but already we can visualize the emergent behavior.
    
    - Cheating to make the simulation feasible 
    
    -----
    Building:
    -----
    Artificial Life Demo - launch the sbt.bat script and type compile at the prompt
    - sbt - at prompt compile
    - sbt - at prompt package
    - sbt - at prompt package-src 
    
    
    Using scala: 2.8.0.r22118-b20106020
    sbt - simple build tool
    -----    
    
    Simple game of life in Scala and using scala.swing api.
    Doing it wrong version, no refactoring.
    
    Keywords: rule30, rule190, squaringrule, wolfram
    
    -----
    Running:
    -----
    Run the applet[1-4].html files in a modern browser.  The java applets will execute. 
    
    -----
    Adding svn propset on html files.
    svn propset svn:mime-type 'text/html' applet.html 
    -----
    
    Berlin Brown - berlin dot brown _at_ gmail dot com
    keywords: cells, dna, replication, gameoflife, scala, java, alife, artificiallife