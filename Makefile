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

gameboard: ./src/BattleShip/GameBoard.java \
	./src/BattleShip/Cell.java \
	./src/BattleShip/Ship.java \
	./src/BattleShip/Position.java \
	./src/BattleShip/HEADING.java \
	./src/BattleShip/Cruiser.java \
	./src/BattleShip/Destroyer.java
	javac -d ./bin/ ./src/BattleSip/Gameboard.java \
		./src/BattleShip/Cell.java \
		./src/BattleShip/Ship.java \
		./src/BattleShip/Position.java \
		./src/BattleShip/HEADING.java \
		./src/BattleShip/Cruiser.java \
		./src/BattleShip/Destroyer.java
# Sneakers, Robert Redford is a hacker
