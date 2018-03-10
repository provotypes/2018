var DISTANCE_HIGH_SPEED = -40; //inches
var DISTANCE_TO_AUTOLINE = -120; //inches
var HIGH_SPEED = .8;
var LOW_SPEED = .3;

var counter = 0;

function init() {
	drivetrain.resetEncoders();
};

function periodic() {
	/*if (drivetrain.getLeftEncoderDistance() > DISTANCE_HIGH_SPEED) {
		drivetrain.tankDrive(HIGH_SPEED, HIGH_SPEED);
	} else if (drivetrain.getLeftEncoderDistance() > DISTANCE_TO_AUTOLINE) {
		drivetrain.tankDrive(LOW_SPEED, LOW_SPEED);
	} else {
		drivetrain.tankDrive(.25, .25);
		if (gameData.length == 3 && gameData.charAt(0) == 'L'){
			arm.set(-1);
		}
	}*/

	if (counter < 250){
		drivetrain.tankDrive(.4, .4);
		counter += 1;
	} else {
		drivetrain.tankDrive(0, 0);
	}
}