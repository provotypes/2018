//Auto 1
var DISTANCE_TO_AUTOLINE = 10; //units?
var AUTO_SPEED = 0.2;

function init(){};

function periodic(){
	if (drivetrain.getDistance() < DISTANCE_TO_AUTOLINE){
		drivetrain.tankDrive(AUTO_SPEED, AUTO_SPEED);
	} else {
		drivetrain.tankDrive(0, 0);
	}
}