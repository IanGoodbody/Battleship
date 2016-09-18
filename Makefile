default: ./src/BattleShip/GameBoard.java \
	./src/BattleShip/Cell.java \
	./src/BattleShip/Ship.java \
	./src/BattleShip/Client.java \
	./src/BattleShip/Cruiser.java \
	./src/BattleShip/Destroyer.java \
	./src/BattleShip/GameManager.java \
	./src/BattleShip/Position.java
	javac -d ./bin/ ./src/BattleShip/GameBoard.java \
		./src/BattleShip/Cell.java \
		./src/BattleShip/Ship.java \
		./src/BattleShip/Client.java \
		./src/BattleShip/Cruiser.java \
		./src/BattleShip/Destroyer.java \
		./src/BattleShip/GameManager.java \
		./src/BattleShip/Position.java 

gameboard: 
	javac -d ./bin/ ./src/BattleShip/GameBoard.java \
		./src/BattleShip/Cell.java \
		./src/BattleShip/Ship.java \
		./src/BattleShip/Position.java \
		./src/BattleShip/HEADING.java \
		./src/BattleShip/Cruiser.java \
		./src/BattleShip/Destroyer.java
	java -cp bin BattleShip.GameBoard
	
# Sneakers, Robert Redford is a hacker
