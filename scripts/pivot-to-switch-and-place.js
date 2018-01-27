//Auto 3

var PIVOT_SPEED = 0.5;

function init(){
	if (gameData.charAt(0) == 'L'){
		drivetrain.tankDrive(0, PIVOT_SPEED); //...for certain amount of time
	} else {
		drivetrain.tankDrive(PIVOT_SPEED, 0); //...for certain amount of time
	}
}

function periodic(){
	if (drivetrain.getDistance() < DISTANCE_TO_SWITCH){
		drivetrain.tankDrive(AUTO_SPEED, AUTO_SPEED); 
	} else {
		drivetrain.tankDrive(0, 0);
	}
}