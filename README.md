# Battleship
The classic battleship game used to explore the world of Java

### Goodies
`PlayerTerminal.py` Provides a python terminal to play the game.  It should be 
platform independent enough to avoid the carriage return bug seen on PuTTy.

### Makefile commands
1. `make` Compiles the source files into the `./bin/` directory
2. `make play` Runs the binary files; starts the server
3. `make gameboard` Compiles and runs the GameBoard test program
4. `make client` Compiles and runs the client test program

### Directory structure
1. `bin` directory containing compiled `.class` files
2. `src` directory containing `.java` source files

### Possible Bugs
I created this on a Unix machine so I cannot attest to the line endings and such
if you use the compiled files.

Likewise `Makefile` should run by default in a Unix terminal
