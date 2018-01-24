//Auto 2.left

var gameData;
gameData = DriverStation.getInstance().getGameSpecificMessage();

if (gameData.charAt(0) == 'L'){
	driveToSwitch();
	dumpCube();
} else {
	driveToAutoLine();
}


//Auto 2.right

var gameData;
gameData = DriverStation.getInstance().getGameSpecificMessage();

if (gameData.charAt(0) == 'R'){
	driveToSwitch();
	dumpCube();
} else {
	driveToAutoLine();
}