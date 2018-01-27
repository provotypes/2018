//Auto 2.left

if (gameData.charAt(0) == 'L'){
	driveToSwitch();
	dumpCube();
} else {
	driveToAutoLine();
}


//Auto 2.right

if (gameData.charAt(0) == 'R'){
	driveToSwitch();
	dumpCube();
} else {
	driveToAutoLine();
}
